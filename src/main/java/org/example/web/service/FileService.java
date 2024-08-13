package org.example.web.service;

import org.example.web.dto.GetFileById;
import org.example.web.dto.GetFilesByUserId;
import org.example.web.dto.SaveFileDto;
import org.example.web.dto.UpdateFileByIdDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
	SaveFileDto.Response saveFile(MultipartFile file, String userId) throws IOException;

	GetFilesByUserId.Response getFilesByUserName(String userId);

	GetFileById.Response getFileById(String userName, String fileId);

	UpdateFileByIdDto.Response updateFileById(UpdateFileByIdDto.Request request);
}
