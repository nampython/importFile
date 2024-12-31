package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceDataResponse {

	private List<Temperature> temperature;
	private List<Humidity> humidity;

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	static class Temperature {
		private String value;
		private String ts;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@Builder
	static class Humidity {
		private String value;
		private String ts;
	}
}
