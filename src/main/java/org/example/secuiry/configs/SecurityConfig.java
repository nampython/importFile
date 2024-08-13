package org.example.secuiry.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(
						(authorizeRequests) -> authorizeRequests
								.requestMatchers("/api/**").permitAll()
								.requestMatchers(HttpMethod.POST, "/api/**").permitAll()
//								.requestMatchers("/login").permitAll()
//								.requestMatchers(HttpMethod.GET, "/foo").permitAll()
//								.requestMatchers(HttpMethod.POST, "/foo").permitAll()
//								.requestMatchers("/client").permitAll()
								.anyRequest().permitAll())
				.csrf().disable()
				.build();
	}
}
