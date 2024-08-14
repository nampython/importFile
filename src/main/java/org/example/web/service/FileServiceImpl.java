package org.example.web.service;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.bson.types.ObjectId;
import org.example.exception.ApiException;
import org.example.secuiry.model.UserEntity;
import org.example.secuiry.repository.UserRepository;
import org.example.web.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

	private final UserRepository userRepository;


	@Override
	public SaveFileDto.Response saveFile(MultipartFile file, String userId) throws IOException {

		try {

			String fileName = file.getOriginalFilename();
			// only process files with .csv and .xlsx extensions
			assert fileName != null;
			if (!fileName.endsWith(".csv") && !fileName.endsWith(".xlsx")) {
				throw new ApiException("Invalid file type", "", "");
			}

			BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.EXCEL.withHeader());

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			List<CsvData> csvData = new ArrayList<>();

			for (CSVRecord csvRecord : csvRecords) {
				Integer no = Integer.valueOf(csvRecord.get("No"));
				String nme = csvRecord.get("Name");
				String type = csvRecord.get("Type");
				String number = csvRecord.get("Number");
				String keeperOne = csvRecord.get("Keeper One");
				String keeperSecond = csvRecord.get("Keeper Second");
				String borrowDate = csvRecord.get("Borrow Date");
				String dateReceived = csvRecord.get("Date Received");
				String productCode = csvRecord.get("Product Code");
				String transferMethod = csvRecord.get("Transfer Method");
				String note = csvRecord.get("Note");


				CsvData fileDt = CsvData.builder()
						.no(no)
						.name(nme)
						.type(type)
						.number(number)
						.keeperOne(keeperOne)
						.keeperSecond(keeperSecond)
						.borrowDate(borrowDate)
						.dateReceived(dateReceived)
						.productCode(productCode)
						.transferMethod(transferMethod)
						.code(note)
						.build();
				csvData.add(fileDt);
				System.out.println(fileDt);
			}

			Optional<UserEntity> userById = userRepository.findById(userId);
			FileInfo fileInfo = FileInfo.builder()
					.id(new ObjectId())
					.fileName(fileName)
					.csvData(csvData)
					.createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now())
					.build();


			if (userById.isPresent()) {
				UserEntity userEntity = userById.get();
				List<FileInfo> fileInfosEntity = userEntity.getFileInfos();
				fileInfosEntity.add(fileInfo);
				userEntity.setFileInfos(fileInfosEntity);
				userRepository.save(userEntity);
			}

			return SaveFileDto.Response.builder()
					.userId(userId)
					.fileName(fileName)
					.csvData(csvData)
					.createdAt(LocalDateTime.now().toString())
					.message("File uploaded successfully")
					.build();
		} catch (IOException e) {
			throw new ApiException("Error occurred while uploading file", "", "");
		}

	}

	@Override
	public GetFilesByUserId.Response getFilesByUserName(String userName) {
		Optional<UserEntity> byUserName = userRepository.findByUserName(userName);
		if (byUserName.isPresent()) {
			UserEntity userEntity = byUserName.get();
			List<FileInfo> fileInfos = userEntity.getFileInfos();
			List<GetFilesByUserId.FileInfoDTO> fileInfoDTOS = new ArrayList<>();
			for (FileInfo fileInfo : fileInfos) {
				GetFilesByUserId.FileInfoDTO fileInfoDTO = GetFilesByUserId.FileInfoDTO.builder()
						.id(fileInfo.getId().toString())
						.fileName(fileInfo.getFileName())
						.createdAt(fileInfo.getCreatedAt())
						.build();
				fileInfoDTOS.add(fileInfoDTO);
			}
			return GetFilesByUserId.Response.builder()
					.fileInfos(fileInfoDTOS)
					.build();
		}
		return null;
	}


	@Override
	public GetFileById.Response getFileById(String userName, String fileId) {

		Optional<UserEntity> byUserName = userRepository.findByUserName(userName);
		if (byUserName.isPresent()) {
			UserEntity userEntity = byUserName.get();
			List<FileInfo> fileInfos = userEntity.getFileInfos();
			for (FileInfo fileInfo : fileInfos) {
				if (fileInfo.getId().toString().equals(fileId)) {
					return GetFileById.Response.builder()
							.id(fileInfo.getId().toString())
							.fileName(fileInfo.getFileName())
							.csvData(fileInfo.getCsvData())
							.createdAt(fileInfo.getCreatedAt())
							.build();
				}
			}
		}
		return null;
	}


	@Override
	public UpdateFileByIdDto.Response updateFileById(UpdateFileByIdDto.Request request) {
		Optional<UserEntity> byUserName = userRepository.findByUserName(request.getUserName());

		if (byUserName.isPresent()) {
			UserEntity userEntity = byUserName.get();
			List<FileInfo> fileInfos = userEntity.getFileInfos();
			for (FileInfo fileInfo : fileInfos) {
				if (fileInfo.getId().toString().equals(request.getFileId())) {
					processLog(fileInfo.getCsvData(), request.getCsvData(), userEntity, fileInfo.getId().toString());
					fileInfo.setFileName(request.getFileName());
					fileInfo.setCsvData(request.getCsvData());
					fileInfo.setUpdatedAt(LocalDateTime.now());
					userRepository.save(userEntity);
				}
			}
		}

		return UpdateFileByIdDto.Response.builder()
				.message("Updated successfully")
				.build();
	}

	private void processLog(List<CsvData> oldData, List<CsvData> newData, UserEntity userEntity, String fileId) {
		List<ActivityLog> activityLogs = userEntity.getActivityLogs();
		List<Integer> oldNo = oldData.stream().map(CsvData::getNo).toList();
		List<Integer> newNo = newData.stream().map(CsvData::getNo).toList();

		ActivityLog activityLog = new ActivityLog();


		List<ActivityLogOperation> logForNewData = new ArrayList<>();
		for (CsvData newDt : newData) {
			if (!oldNo.contains(newDt.getNo())) {
				ActivityLogOperation activityLogUpdateRow = ActivityLogOperation.builder()
						.fileId(fileId)
						.action(Action.ADD)
						.no(newDt.getNo())
						.oldData(null)
						.newData(newDt)
						.createdAt(LocalDateTime.now())
						.updatedAt(LocalDateTime.now())
						.build();
				logForNewData.add(activityLogUpdateRow);
			}
		}

		List<ActivityLogOperation> logForDeletedData = new ArrayList<>();
		for (CsvData oldDt : oldData) {
			if (!newNo.contains(oldDt.getNo())) {
				ActivityLogOperation activityLogUpdateRow = ActivityLogOperation.builder()
						.fileId(fileId)
						.action(Action.DELETE)
						.no(oldDt.getNo())
						.oldData(oldDt)
						.newData(null)
						.createdAt(LocalDateTime.now())
						.updatedAt(LocalDateTime.now())
						.build();
				logForDeletedData.add(activityLogUpdateRow);
			}
		}

		List<ActivityLogOperation> activityLogOperations = new ArrayList<>();
		if (!logForNewData.isEmpty()) {
			activityLogOperations.addAll(logForNewData);
		}

		if (!logForDeletedData.isEmpty()) {
			activityLogOperations.addAll(logForDeletedData);
		}

		activityLogUpdateRow(activityLog, oldData, newData);
		activityLog.setActivityLogOperation(activityLogOperations);
		activityLogs.add(activityLog);
		userEntity.setActivityLogs(activityLogs);
	}

	public void activityLogUpdateRow(ActivityLog activityLog, List<CsvData> oldData, List<CsvData> newData) {
		int indexForOldData = 0;
		int indexForNewData = 0;
		List<ActivityLogUpdateRow> activityLogUpdateRow = new ArrayList<>();

		while (indexForOldData < oldData.size() && indexForNewData < newData.size()) {

			CsvData oldCsvData = oldData.get(indexForOldData);
			CsvData newCsvData = newData.get(indexForNewData);

			ActivityLogUpdateRow updateRow = new ActivityLogUpdateRow();
			activityLogUpdateRow(oldCsvData, newCsvData, updateRow);

			if (!updateRow.isNull()) {
				updateRow.setAction(Action.UPDATE);
				updateRow.setCreatedAt(LocalDateTime.now());
				activityLogUpdateRow.add(updateRow);
			}
			indexForOldData++;
			indexForNewData++;
		}
		if (!activityLogUpdateRow.isEmpty()) {
			activityLog.setActivityLogUpdateRow(activityLogUpdateRow);
		}
	}

	private void activityLogUpdateRow(CsvData oldCsvData, CsvData newCsvData, ActivityLogUpdateRow activityLogUpdateRow) {
		if (!oldCsvData.getName().equals(newCsvData.getName())) {
			activityLogUpdateRow.setColumnName("Name");
			activityLogUpdateRow.setOldValue(oldCsvData.getName());
			activityLogUpdateRow.setNewValue(newCsvData.getName());
		}

		if (!oldCsvData.getType().equals(newCsvData.getType())) {
			activityLogUpdateRow.setColumnName("Type");
			activityLogUpdateRow.setOldValue(oldCsvData.getType());
			activityLogUpdateRow.setNewValue(newCsvData.getType());
		}

		if (!oldCsvData.getNumber().equals(newCsvData.getNumber())) {
			activityLogUpdateRow.setColumnName("Number");
			activityLogUpdateRow.setOldValue(oldCsvData.getNumber());
			activityLogUpdateRow.setNewValue(newCsvData.getNumber());
		}

		if (!oldCsvData.getKeeperOne().equals(newCsvData.getKeeperOne())) {
			activityLogUpdateRow.setColumnName("Keeper One");
			activityLogUpdateRow.setOldValue(oldCsvData.getKeeperOne());
			activityLogUpdateRow.setNewValue(newCsvData.getKeeperOne());
		}

		if (!oldCsvData.getKeeperSecond().equals(newCsvData.getKeeperSecond())) {
			activityLogUpdateRow.setColumnName("Keeper Second");
			activityLogUpdateRow.setOldValue(oldCsvData.getKeeperSecond());
			activityLogUpdateRow.setNewValue(newCsvData.getKeeperSecond());
		}

		if (!oldCsvData.getBorrowDate().equals(newCsvData.getBorrowDate())) {
			activityLogUpdateRow.setColumnName("Borrow Date");
			activityLogUpdateRow.setOldValue(oldCsvData.getBorrowDate());
			activityLogUpdateRow.setNewValue(newCsvData.getBorrowDate());
		}

		if (!oldCsvData.getDateReceived().equals(newCsvData.getDateReceived())) {
			activityLogUpdateRow.setColumnName("Date Received");
			activityLogUpdateRow.setOldValue(oldCsvData.getDateReceived());
			activityLogUpdateRow.setNewValue(newCsvData.getDateReceived());
		}

		if (!oldCsvData.getProductCode().equals(newCsvData.getProductCode())) {
			activityLogUpdateRow.setColumnName("Product Code");
			activityLogUpdateRow.setOldValue(oldCsvData.getProductCode());
			activityLogUpdateRow.setNewValue(newCsvData.getProductCode());
		}

		if (!oldCsvData.getTransferMethod().equals(newCsvData.getTransferMethod())) {
			activityLogUpdateRow.setColumnName("Transfer Method");
			activityLogUpdateRow.setOldValue(oldCsvData.getTransferMethod());
			activityLogUpdateRow.setNewValue(newCsvData.getTransferMethod());
		}

		if (!oldCsvData.getCode().equals(newCsvData.getCode())) {
			activityLogUpdateRow.setColumnName("Code");
			activityLogUpdateRow.setOldValue(oldCsvData.getCode());
			activityLogUpdateRow.setNewValue(newCsvData.getCode());
		}

	}

	@Override
	public GetLogUserDto.Response getLogsUser(String userName) {
		Optional<UserEntity> byId = userRepository.findByUserName(userName);
		if (byId.isPresent()) {
			UserEntity userEntity = byId.get();
			List<HistoryUser> historyUsers = userEntity.getHistoryUsers();
			return GetLogUserDto.Response.builder()
					.userName(userName)
					.historyUsers(historyUsers)
					.build();
		} else {
			throw  new ApiException("User not found", "", "");
		}
	}


	@Override
	public GetActivityFile.Response getActivityFile(String userName) {
		UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(() -> new ApiException("User not found", "", ""));
		List<ActivityLog> activityLogs = userEntity.getActivityLogs();
		return GetActivityFile.Response.builder()
				.userName(userName)
				.activityLogs(activityLogs)
				.build();
	}
}