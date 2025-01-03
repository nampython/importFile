package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.response.DeviceDataResponse;

import java.time.LocalDateTime;
import java.util.List;

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
		private MetaDataDeviceDto deviceData;
	}


	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	class MetaDataDeviceDto {
		private int countTemperature;
		private int countHumidity;
		private int countAirQuality;
		private int countLightIntensity;
		private List<DeviceDataDto> temperature;
		private List<DeviceDataDto> humidity;
		private List<DeviceDataDto> airQuality;
		private List<DeviceDataDto> lightIntensity;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	class DeviceDataDto {
		private String value;
		private LocalDateTime createdAt;
	}
}
