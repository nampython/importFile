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
					processLog(fileInfo.getCsvData(), request.getCsvData(), userEntity);
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

	private void processLog(List<CsvData> oldData, List<CsvData> newData, UserEntity userEntity) {
		List<ActivityLog> activityLogUpdateRows = userEntity.getActivityLogs();
		List<Integer> oldNo = oldData.stream().map(CsvData::getNo).toList();
		List<Integer> newNo = newData.stream().map(CsvData::getNo).toList();

		List<ActivityLogOperation> logForNewData = new ArrayList<>();
		List<ActivityLogOperation> logForDeletedData = new ArrayList<>();
		ActivityLog activityLog = new ActivityLog();

		for (CsvData newDt : newData) {
			if (!oldNo.contains(newDt.getNo())) {
				ActivityLogOperation activityLogUpdateRow = ActivityLogOperation.builder()
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

		activityLog.setActivityLogOperation(logForNewData);
		activityLogUpdateRows.add(activityLog);
		userEntity.setActivityLogs(activityLogUpdateRows);
	}
}
