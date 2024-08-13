package org.example.secuiry.execption;

import org.example.exception.ApiException;

public class UserAlreadyExistException extends ApiException {
	public UserAlreadyExistException(String message, String code, String shortDesc) {
		super(message, code, shortDesc);
	}

	public UserAlreadyExistException(String code, String shortDesc, String message, Throwable cause) {
		super(code, shortDesc, message, cause);
	}
}
