package org.example.secuiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface LoginDto {

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	class Request {
		private LoginForm loginForm;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	class Response {
		private String token;
		private String refreshToken;
		private String username;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	class LoginForm {
		private String username;
		private String password;
	}
}
