package org.example.services;

import lombok.AllArgsConstructor;
import org.example.dto.GetDeviceDataById;
import org.example.response.DeviceDataResponse;
import org.example.utils.RestClientUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thingsboard.rest.client.RestClient;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

	private final RestClientUtil restClientUtil;

	@Override
	public GetDeviceDataById.Response getDeviceDataById(GetDeviceDataById.Request request) {
		final String url = "http://demo.thingsboard.io/api/plugins/telemetry/DEVICE/" + request.getDeviceId() + "/values/timeseries?keys=temperature,humidity&startTs=1735021416960&endTs=1735621019000";
		DeviceDataResponse deviceDataResponse = restClientUtil.exchange(url, DeviceDataResponse.class);
		return null;
	}
}
