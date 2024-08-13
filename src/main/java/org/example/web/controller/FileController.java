package org.example.web.controller;

import lombok.AllArgsConstructor;
import org.example.web.dto.GetFileById;
import org.example.web.dto.GetFilesByUserId;
import org.example.web.dto.SaveFileDto;
import org.example.web.dto.UpdateFileByIdDto;
import org.example.web.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class FileController {

	private final FileService fileService;

	@GetMapping("/api/user/saveFile")
	public SaveFileDto.Response saveFile(@RequestPart("file") MultipartFile file, @RequestParam String userId) throws IOException {
		return fileService.saveFile(file, userId);
	}

	@GetMapping("/api/user/getFileById")
	public GetFileById.Response getFileById(@RequestParam String userName, @RequestParam String fileId) {
		return fileService.getFileById(userName, fileId);
	}

	@PostMapping("/api/user/updateFileById")
	public UpdateFileByIdDto.Response updateFileById(@RequestBody UpdateFileByIdDto.Request request) {
		return fileService.updateFileById(request);
	}

	@GetMapping("/api/user/getFilesByUserId")
	public GetFilesByUserId.Response getFilesByUserId(@RequestParam String userName) {
		return fileService.getFilesByUserName(userName);
	}
}
