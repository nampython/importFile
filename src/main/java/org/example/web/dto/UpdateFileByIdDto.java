package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface UpdateFileByIdDto {

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Request {
		private String fileId;
		private String userName;
		private String fileName;
		private List<CsvData> csvData;
	}


	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Response {
		private String message;
		private String userName;
		private String fileName;
		private List<CsvData> csvData;
	}
}
