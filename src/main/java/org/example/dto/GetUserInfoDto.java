package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.response.UserResponse;

public interface GetUserInfoDto {

	@AllArgsConstructor
	@Data
	@Builder
	class Request {

	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	class Response {
		private UserResponse user;
	}
}
