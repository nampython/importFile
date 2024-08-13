package org.example.secuiry.service;

import org.example.secuiry.dto.LoginDto;
import org.example.secuiry.dto.SignUpDto;

public interface SecurityService {
	SignUpDto.Response signUp(SignUpDto.Request request);
	LoginDto.Response login(LoginDto.Request request);
}
