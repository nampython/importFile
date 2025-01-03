package org.example.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceDataResponse {
	private List<MetaDeviceDataResponse> temperature;
	private List<MetaDeviceDataResponse> humidity;
	private List<MetaDeviceDataResponse> airQuality;
	private List<MetaDeviceDataResponse> lightIntensity;


	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	public static class MetaDeviceDataResponse {
		private String value;
		private String ts;
	}

}
