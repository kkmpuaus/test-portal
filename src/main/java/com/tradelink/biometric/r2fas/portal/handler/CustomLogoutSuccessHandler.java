package com.tradelink.biometric.r2fas.portal.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.tradelink.biometric.r2fas.portal.service.LoginService;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		if (auth != null) {
			log.info("User " + auth.getName() + " logged out the system!");
		}
		loginService.logout(auth.getName());
		response.sendRedirect("loggedout.do?logout=1");
	}

}