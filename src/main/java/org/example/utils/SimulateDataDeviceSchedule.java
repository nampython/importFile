package org.example.utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.MetaDataDevice;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Log4j2
@Component
@EnableScheduling
public class SimulateDataDeviceSchedule {

	private final RestTemplate restTemplate;
	private static final String DEVICE_ID = "VkkfBB224rWWZjPdlxbJ";


//	@Scheduled(fixedRate = 2000)
	public void simulate() {
		try {
			log.info("-----------------Simulate data device-------------------------------");
			String url = "http://demo.thingsboard.io/api/v1/" + DEVICE_ID + "/telemetry";
			MetaDataDevice metaDataDevice = getMetaDataDevice();
			restTemplate.postForObject(url, metaDataDevice, String.class);
			log.info("Data device: {}", metaDataDevice);
			log.info("--------------------------------------------------------------------");
		} catch (RestClientException e) {
			 log.error("Error when post data to thingsboard: {}", e.getMessage());
		} catch (Exception e) {
			log.error("Error when simulating data: {}", e.getMessage());
		}

	}


	private MetaDataDevice getMetaDataDevice() {
		final double temperature = Math.random() * 20 + 25;
		final double humidity = Math.random() * 10 + 50;
		final double airQuality = Math.random() * 5 + 55;
		final double lightIntensity = Math.random() * 80 + 100;
		return MetaDataDevice.builder()
				.temperature(temperature)
				.humidity(humidity)
				.airQuality(airQuality)
				.lightIntensity(lightIntensity)
				.build();
	}
}
