package org.example.secuiry.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.access_token_expiration_second}")
	private long accessTokenExpirationSecond;

	@Value("${jwt.refresh_token_expiration_second}")
	private long refreshTokenExpirationSecond;

	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String ACTIVATE_TOKEN = "activate_token";
	public static final String USERNAME = "username";
	public static final String AUTHORITIES = "authorities";


	public Map<String, String> generateTokens(Authentication authentication) {
		String accessToken = createToken(authentication, accessTokenExpirationSecond);
		String refreshToken = createToken(authentication, refreshTokenExpirationSecond);

		Map<String, String> tokens = new HashMap<>();
		tokens.put(ACCESS_TOKEN, accessToken);
		tokens.put(REFRESH_TOKEN, refreshToken);

		return tokens;
	}

	private String createToken(Authentication authentication, long accessTokenExpirationSecond) {
		final String userName = authentication.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Map<String, Object> claimsProperties = Map.of(
				"active", Boolean.TRUE,
				USERNAME, userName,
				AUTHORITIES, authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(","))
		);

		Claims claims = Jwts.claims()
				.add(claimsProperties)
				.build();

		Date from = Date.from(Instant.now());
		Date validity = new Date(System.currentTimeMillis() + accessTokenExpirationSecond * 1000);

		return Jwts.builder()
				.claims(claims)
				.issuedAt(from)
				.expiration(validity)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
}
