package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.response.DeviceDataResponse;

public interface GetDeviceDataById {

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	class Request {
		private String deviceId;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	class Response {
		private DeviceDataResponse deviceData;
	}
}
