package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Log4j2
@EnableScheduling
public class SimulateDataDeviceSchedule {

	private final RestTemplate restTemplate;


	@Scheduled(fixedRate = 5000)
	public void simulate() {
		String deviceId = "VkkfBB224rWWZjPdlxbJ";
		String url = "http://demo.thingsboard.io/api/v1/" + deviceId + "/telemetry";
		String humidity = String.valueOf(Math.random() * 100);
		String temperature = String.valueOf(Math.random() * 100);
		String payload = "{\n" +
				"    \"humidity\": " + humidity + ",\n" +
				"    \"temperature\": " + temperature + "\n" +
				"}";
		restTemplate.postForEntity(url, payload, String.class);
	}
}
