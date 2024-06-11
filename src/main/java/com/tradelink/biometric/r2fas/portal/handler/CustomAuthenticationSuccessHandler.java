package com.tradelink.biometric.r2fas.portal.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.service.LoginService;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private Log log = LogFactory.getLog(this.getClass());
	private String successURL;
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		WebUser webUser = (WebUser) authentication.getPrincipal();
		loginService.loginSuccess(webUser, request.getRemoteAddr());	
		log.info(webUser.getUsername() + " has logged in !!");
		
		if(webUser.getRole().equals("ROLE_ADMIN") || webUser.getRole().equals("ROLE_HR")) {
			response.sendRedirect("faceuser.do");
		} else {
			response.sendRedirect("camera.do");
		}
		
		//response.sendRedirect(successURL);
		return;
	}

	public String getSuccessURL() {
		return successURL;
	}

	public void setSuccessURL(String successURL) {
		this.successURL = successURL;
	}

}
