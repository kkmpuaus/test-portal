package com.tradelink.biometric.r2fas.portal.security.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tradelink.biometric.r2fas.portal.security.model.WebUser;

public class WebUserAuthService implements UserDetailsService {
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private WebUserDetailsService webUserDetailsService;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		log.info("Start loading user by username:" + username);

		WebUser webUser = null;
		try {
			webUser = webUserDetailsService.getUser(username);
		} catch (Exception e) {
			log.info("Username not found:" + username);
			e.printStackTrace();
			throw new UsernameNotFoundException("Username not found!");
		}
		if (webUser == null) {
			log.info("User is null! Username not found:" + username);
			throw new UsernameNotFoundException("Username not found!");
		}
		log.debug("Username is found:" + username);
		return webUser;
	}

}
