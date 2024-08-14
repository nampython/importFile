package org.example.secuiry.configs;

import lombok.AllArgsConstructor;
import org.example.secuiry.filter.JwtAuthFilter;
import org.example.secuiry.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

	private final UserServiceImpl userServiceImpl;
	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		 http
				.authorizeHttpRequests(
						(authorizeRequests) -> authorizeRequests
								.requestMatchers("/api/**").permitAll()
								.requestMatchers(HttpMethod.POST, "/api/**").permitAll()
//								.requestMatchers("/login").permitAll()
//								.requestMatchers(HttpMethod.GET, "/foo").permitAll()
//								.requestMatchers(HttpMethod.POST, "/foo").permitAll()
//								.requestMatchers("/client").permitAll()
								.anyRequest().permitAll())
				.csrf().disable();

		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(this.userServiceImpl);
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(List.of("*")); // Use allowedOriginPatterns instead of allowedOrigins
		config.setAllowedHeaders(List.of("*")); // Allow all headers
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow all methods
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
