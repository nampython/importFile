package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public interface GetFileById {
	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Request {
		private String userName;
		private String fileId;
	}


	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Response {
		private String id;
		private String fileName;
		private List<CsvData> csvData;
		private LocalDateTime createdAt;
	}
}
