package com.tradelink.biometric.r2fas.portal.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import com.tradelink.biometric.r2fas.portal.constant.AccountConstants;
import com.tradelink.biometric.r2fas.portal.db.model.LoginEx;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.security.service.WebUserDetailsService;
import com.tradelink.biometric.r2fas.portal.service.LoginService;

@Transactional(value = "transactionManager", rollbackFor = Throwable.class)
public class WebUserDetailsServiceImpl implements WebUserDetailsService {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private LoginService loginService;

	@Override
	public WebUser getUser(String userId) {
		log.debug("Getting user object for User Id: ["+ userId + "]");
		LoginEx loginEx = loginService.findLoginEx(userId);
		if (loginEx == null) {
			log.debug("LoginEx is null for User Id: ["+ userId + "]");
			return null;
		}
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (loginEx.getUserRole() != null) {
			authorities.add(new SimpleGrantedAuthority(loginEx.getUserRole()));
		}
		WebUser webUser = new WebUser(loginEx.getUserId(),loginEx.getPassword(),
				AccountConstants.LoginStatus.active.isEqual(loginEx.getStatus()),
				true,true,!loginEx.isLocked(),authorities);
		webUser.setLoginEx(loginEx);

		return webUser;
	}

}

