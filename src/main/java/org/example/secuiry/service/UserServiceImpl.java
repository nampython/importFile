package org.example.secuiry.service;

import lombok.AllArgsConstructor;
import org.example.exception.ApiException;
import org.example.secuiry.model.UserEntity;
import org.example.secuiry.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userByUserName = userRepository.findByUserName(username)
				.orElseThrow(() -> new ApiException("User not found", "", ""));

		return User
				.withUsername(userByUserName.getUserName())
				.password(userByUserName.getPassword())
				.authorities("USER")
				.accountExpired(false)
				.accountLocked(false)
				.credentialsExpired(false)
				.disabled(false)
				.build();
	}
}
