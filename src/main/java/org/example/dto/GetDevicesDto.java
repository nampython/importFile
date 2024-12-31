package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.response.DeviceResponse;

import java.util.List;

public interface GetDevicesDto {
	class Request {

	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	class Response {
		private List<DeviceResponse> devices;
	}
}
