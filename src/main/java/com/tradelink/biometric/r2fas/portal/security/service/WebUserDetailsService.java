package com.tradelink.biometric.r2fas.portal.security.service;

import com.tradelink.biometric.r2fas.portal.security.model.WebUser;

public interface WebUserDetailsService {
	
	public WebUser getUser(String loginId);

}
