package com.tradelink.biometric.r2fas.portal.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tradelink.biometric.r2fas.portal.security.model.WebUser;

public class AuthUtils {
	public static WebUser currentUserDetails() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			return (WebUser) (principal instanceof WebUser ? principal : null);
		}
		return null;
	}
}
