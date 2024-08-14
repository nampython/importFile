package org.example.secuiry.controller;

import lombok.AllArgsConstructor;
import org.example.secuiry.dto.LoginDto;
import org.example.secuiry.dto.SignUpDto;
import org.example.secuiry.service.SecurityService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SecurityController {

	private final SecurityService securityService;

	@CrossOrigin(origins = "*")
	@PostMapping("/login")
	public LoginDto.Response login(@RequestBody LoginDto.Request request) {
		return securityService.login(request);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/sign-up")
	public SignUpDto.Response signUp(@RequestBody SignUpDto.Request request) {
		return securityService.signUp(request);
	}

}
