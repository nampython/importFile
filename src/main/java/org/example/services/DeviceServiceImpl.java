package org.example.services;

import lombok.AllArgsConstructor;
import org.example.dto.GetDeviceDataById;
import org.example.response.DeviceDataResponse;
import org.example.utils.RestClientUtil;
import org.example.utils.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

	private final RestClientUtil restClientUtil;

	@Override
	public GetDeviceDataById.Response getDeviceDataById(GetDeviceDataById.Request request) {
		final String url = "http://demo.thingsboard.io/api/plugins/telemetry/DEVICE/" + request.getDeviceId()
				+ "/values/timeseries?keys=temperature,humidity,airQuality,lightIntensity&startTs=1735713450000&endTs=1735886586000";
		DeviceDataResponse deviceDataResponse = restClientUtil.exchange(url, DeviceDataResponse.class);
		GetDeviceDataById.MetaDataDeviceDto metaDataDevice = getMetaDataDevice(deviceDataResponse);


		return GetDeviceDataById.Response.builder()
				.deviceData(metaDataDevice)
				.build();
	}

	private GetDeviceDataById.MetaDataDeviceDto getMetaDataDevice(DeviceDataResponse deviceDataResponse) {
		List<GetDeviceDataById.DeviceDataDto> temperature = deviceDataResponse.getTemperature()
				.stream()
				.map(temp -> GetDeviceDataById.DeviceDataDto.builder()
						.value(temp.getValue())
						.createdAt(TimeUtils.toLocalDateTime(temp.getTs()))
						.build())
				.collect(Collectors.toList());

		List<GetDeviceDataById.DeviceDataDto> humidity = deviceDataResponse.getHumidity()
				.stream()
				.map(hum -> GetDeviceDataById.DeviceDataDto.builder()
						.value(hum.getValue())
						.createdAt(TimeUtils.toLocalDateTime(hum.getTs()))
						.build())
				.collect(Collectors.toList());

		List<GetDeviceDataById.DeviceDataDto> airQuality = deviceDataResponse.getAirQuality()
				.stream()
				.map(air -> GetDeviceDataById.DeviceDataDto.builder()
						.value(air.getValue())
						.createdAt(TimeUtils.toLocalDateTime(air.getTs()))
						.build())
				.collect(Collectors.toList());

		List<GetDeviceDataById.DeviceDataDto> lightIntensity = deviceDataResponse.getLightIntensity()
				.stream()
				.map(light -> GetDeviceDataById.DeviceDataDto.builder()
						.value(light.getValue())
						.createdAt(TimeUtils.toLocalDateTime(light.getTs()))
						.build())
				.collect(Collectors.toList());


		return GetDeviceDataById.MetaDataDeviceDto.builder()
				.countTemperature(deviceDataResponse.getTemperature().size())
				.countHumidity(deviceDataResponse.getHumidity().size())
				.countAirQuality(deviceDataResponse.getAirQuality().size())
				.countLightIntensity(deviceDataResponse.getLightIntensity().size())
				.temperature(temperature)
				.humidity(humidity)
				.airQuality(airQuality)
				.lightIntensity(lightIntensity)
				.build();
	}
}
