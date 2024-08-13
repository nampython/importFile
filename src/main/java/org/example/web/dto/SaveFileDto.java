package org.example.web.dto;

import lombok.*;

public interface SaveFileDto {

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	class Response {
		private String message;
	}
}
