package com.tradelink.biometric.r2fas.portal.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ReCaptchaInvalidException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public ReCaptchaInvalidException(String msg) {
		super(msg);
	}

	public ReCaptchaInvalidException(String msg, Throwable t) {
		super(msg, t);
	}

}