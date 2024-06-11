package com.tradelink.biometric.r2fas.portal.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.tradelink.biometric.r2fas.portal.constant.UserAuditLogConstants;
import com.tradelink.biometric.r2fas.portal.security.exception.ReCaptchaInvalidException;
import com.tradelink.biometric.r2fas.portal.service.LoginService;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private Log log = LogFactory.getLog(this.getClass());
	private String loginFailURL;
	private String loginDeniedURL;

	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private LoginService loginService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException ex) throws IOException, ServletException {
		
		String userId = request.getParameter("username");
		String queryString = "";

		int reminderNo = loginService.loginFail(userId, getLoginFailReason(ex));
		if (ex instanceof SessionAuthenticationException) {
			List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
			for (Object p : allPrincipals) {
				User user = (User) p;
				if (user.getUsername().equals(userId)) {
					List<SessionInformation> sessions = sessionRegistry.getAllSessions(p, false);

					for (SessionInformation session : sessions) {
						if (!session.isExpired())
							session.expireNow();
					}
				}
			}
			log.info("Login to the system failure [Second Login] - User Id: " + userId);
			response.sendRedirect(loginDeniedURL);
			return;
		} else if (ex instanceof AccountStatusException) {
			if (ex instanceof AccountExpiredException) {
				log.info("Login to the system failure [Account Expired] - User Id: " + userId);
				queryString = "acExpired=1";
			} else if (ex instanceof CredentialsExpiredException) {
				log.info("Login to the system failure [Login Expired] - User Id: " + userId);
				queryString = "expired=1";
			} else if (ex instanceof DisabledException) {
				log.info("Login to the system failure [Login Disabled] - User Id: " + userId);
				queryString = "disabled=1";
			} else if (ex instanceof LockedException) {
				log.info("Login to the system failure [Login Locked] - User Id: " + userId);
				queryString = "locked=1";
			} else {
				log.info("Login to the system failure [Invalid Account Status] - User Id: " + userId);
				queryString = "invalid=1";
			}
		} else if (ex instanceof BadCredentialsException) {
			log.info("Login to the system failure [Invalid Password] - User Id: " + userId);
			queryString = "error=1&r="+reminderNo;
		} else if (ex instanceof ReCaptchaInvalidException) {
			log.info("Login to the system failure [Invalid ReCaptcha] - User Id: " + userId);
			queryString = "captchaerr=1";
		} else {
			log.error("Login to the system failure [System Error] - User Id: " + userId);
			log.error(ex.getMessage());
			queryString = "error=1";
		}
		
		if (loginFailURL != null) {
			log.debug("Redirect to loginFailURL:" + loginFailURL);
			response.sendRedirect(loginFailURL+"?"+queryString);
		}
		return;
	}

	public String getLoginFailURL() {
		return loginFailURL;
	}

	public void setLoginFailURL(String loginFailURL) {
		this.loginFailURL = loginFailURL;
	}

	public String getLoginDeniedURL() {
		return loginDeniedURL;
	}

	public void setLoginDeniedURL(String loginDeniedURL) {
		this.loginDeniedURL = loginDeniedURL;
	}
	
	private String getLoginFailReason(AuthenticationException ex) {
		if (ex instanceof SessionAuthenticationException) {
			return UserAuditLogConstants.LOGIN_FAIL_REASON_DUPLICATE_LOGIN;
		} else if (ex instanceof AccountStatusException) {
			if (ex instanceof AccountExpiredException) {
				return UserAuditLogConstants.LOGIN_FAIL_REASON_ACCOUNT_EXPIRED;
			} else if (ex instanceof CredentialsExpiredException) {
				return UserAuditLogConstants.LOGIN_FAIL_REASON_LOGIN_EXPIRED;
			} else if (ex instanceof DisabledException) {
				return UserAuditLogConstants.LOGIN_FAIL_REASON_ACCOUNT_DISABLED;
			} else if (ex instanceof LockedException) {
				return UserAuditLogConstants.LOGIN_FAIL_REASON_ACCOUNT_LOCKED;
			} else {
				return UserAuditLogConstants.LOGIN_FAIL_REASON_INVALID_ACCOUNT;
			}
		} else if (ex instanceof BadCredentialsException) {
			return UserAuditLogConstants.LOGIN_FAIL_REASON_INVALID_USERNAME_PASSWORD;			
		} else if (ex instanceof ReCaptchaInvalidException) {
			return UserAuditLogConstants.LOGIN_FAIL_REASON_BAD_CAPTCHA;
		}
		return UserAuditLogConstants.LOGIN_FAIL_REASON_INVALID_USERNAME_PASSWORD;			
	}
}