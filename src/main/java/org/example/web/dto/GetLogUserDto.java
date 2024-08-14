package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface GetLogUserDto {

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Response {
		private String userName;
		private List<HistoryUser> historyUsers;
	}
}
