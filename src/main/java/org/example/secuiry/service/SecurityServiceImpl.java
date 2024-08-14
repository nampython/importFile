package org.example.secuiry.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.exception.ApiException;
import org.example.secuiry.dto.LoginDto;
import org.example.secuiry.dto.SignUpDto;
import org.example.secuiry.model.UserEntity;
import org.example.secuiry.repository.UserRepository;
import org.example.secuiry.util.JwtTokenUtil;
import org.example.web.dto.HistoryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class SecurityServiceImpl implements SecurityService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtUtils;

	@Override
	public SignUpDto.Response signUp(SignUpDto.Request request) {
		Optional<UserEntity> byUserName = userRepository.findByUserName(request.getUserName());
		if (byUserName.isPresent()) {
			throw new ApiException("User already exists", "", "");
		}

		UserEntity userEntity = UserEntity.builder()
				.activityLogs(new ArrayList<>())
				.historyUsers(new ArrayList<>())
				.createdAt(LocalDateTime.now())
				.updatedAt(null)
				.fileInfos(new ArrayList<>())
				.userName(request.getUserName())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();
		userRepository.save(userEntity);

		return SignUpDto.Response.builder()
				.message("User created")
				.build();
	}

	@Override
	public LoginDto.Response login(LoginDto.Request request) {

		String username = request.getLoginForm().getUsername();
		String password = request.getLoginForm().getPassword();
		Authentication authenticate;

		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
			authenticate = authenticationManager.authenticate(authentication);
			Map<String, String> tokens = jwtUtils.generateTokens(authenticate);

			HistoryUser historyUser = HistoryUser.builder()
					.location("Ho Chi Minh")
					.createdAt(LocalDateTime.now())
					.build();

			UserEntity userEntity = userRepository.findByUserName(username).get();
			userEntity.getHistoryUsers().add(historyUser);
			userRepository.save(userEntity);


			return LoginDto.Response.builder()
					.username(authentication.getName())
					.token(tokens.get(JwtTokenUtil.ACCESS_TOKEN))
					.refreshToken(tokens.get(JwtTokenUtil.REFRESH_TOKEN))
					.build();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("Login failed", "", "");
		}
	}
}
