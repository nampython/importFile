package org.example.utils;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.thingsboard.rest.client.RestClient;

@Component
@AllArgsConstructor
public class RestClientUtil {

	private final RestTemplate restTemplate;
	private final RestClient restClient;

	public <T> T exchange(String url, Class<T> responseType) {
		final String token = restClient.getToken();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
		return response.getBody();
	}
}
