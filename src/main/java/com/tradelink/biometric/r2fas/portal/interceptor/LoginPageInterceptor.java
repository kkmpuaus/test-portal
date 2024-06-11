package com.tradelink.biometric.r2fas.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginPageInterceptor extends HandlerInterceptorAdapter {
	
	private String loginDeniedURL;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication authentication = securityContext.getAuthentication();
			// the check should be replaced by new user details object later.
			if (authentication != null && authentication.getPrincipal() != null) {
				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					autoLogout(request,response,authentication);
					request.setAttribute("authname", authentication.getName());
					request.getRequestDispatcher(loginDeniedURL).forward(request, response);
					return false;
				}
			}
		return true;
	}

	public String getLoginDeniedURL() {
		return loginDeniedURL;
	}

	public void setLoginDeniedURL(String loginDeniedURL) {
		this.loginDeniedURL = loginDeniedURL;
	}

	private void autoLogout(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) {
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

		SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
		if( auth == null ){
			ctxLogOut.logout(request, response, auth);
		}
	}

}