package org.example.web.dto;

import lombok.*;

import java.util.List;

public interface SaveFileDto {

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	class Response {
		private String message;
		private String userId;
		private String fileName;
		private List<CsvData> csvData;
		private String createdAt;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	class Request {
		private String userName;
		private String fileName;
		private List<CsvData> csvData;
	}
}
