package org.example.secuiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface SignUpDto {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	class Request {
		private String userName;
		private String password;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	class Response {
		private String message;
	}
}
