package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.thingsboard.rest.client.RestClient;

@Configuration
public class Configs {

	@Value("${thingsboard.username}")
	private String username;

	@Value("${thingsboard.password}")
	private String password;

	@Value("${thingsboard.host}")
	private String host;

	@Bean()
	public RestClient restClient() {
		RestClient client = new RestClient(host);
		client.login(username, password);
		return client;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
