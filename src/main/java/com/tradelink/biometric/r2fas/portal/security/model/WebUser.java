package com.tradelink.biometric.r2fas.portal.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.tradelink.biometric.r2fas.portal.db.model.LoginEx;

public class WebUser extends User {

	private static final long serialVersionUID = 1L;
	
	private LoginEx loginEx;

	public WebUser() {
		super(null, null, null);
	}

	public WebUser(String loginId, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(loginId, password, authorities);
	}

	public WebUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public LoginEx getLoginEx() {
		return loginEx;
	}

	public void setLoginEx(LoginEx loginEx) {
		this.loginEx = loginEx;
	}
	
	public String getDisplayName() {
		if (loginEx == null) return null;
		return loginEx.getDisplayName();
	}
	
	public String getSubId() {
		if (loginEx == null) return null;
		return loginEx.getSubId();
	}
	
	public String getRole() {
		if (loginEx == null) return null;
		return loginEx.getUserRole();
	}
	
	public boolean isAdmin() {
		return UserRoles.ROLE_ADMIN.equals(getRole());
	}
}
