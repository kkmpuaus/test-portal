package com.tradelink.biometric.r2fas.portal.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tradelink.biometric.r2fas.portal.controller.AbstractController;

@Controller
@RequestMapping("/")
public class MainController extends AbstractController {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/test")
	public String processTest(Map<String, Object> model) {
		if (this.log.isDebugEnabled())
			this.log.debug("display test page ...");
		return "test";
	}

	@RequestMapping(value = "/login")
	public String processLogin(Map<String, Object> model) {
		if (this.log.isDebugEnabled())
			this.log.debug("display login page ...");
		
		this.log.info("Create Password");
		String encodedPassword = passwordEncoder.encode("password");
		this.log.info("Create Password = " + encodedPassword);
		
		return "login";
	}

	@RequestMapping(value = "/loggedout")
	public String processLogout(Map<String, Object> model, 
			@RequestParam(value="logout",required=false) String loggedout) {
		if (this.log.isDebugEnabled())
			this.log.debug("process logout (loggedout="+loggedout+") ...");
		if (!StringUtils.isEmpty(loggedout)) {
			model.put("loggedOutAlready", "true");
		}
		return "logout";
	}

	@RequestMapping(value = "/invalidUser")
	public String displayInvalidUserPage() {
		if (this.log.isDebugEnabled())
			this.log.debug("display invalid user page ...");

		return "error/invalidUser";
	}

	@RequestMapping(value = "/sessionTimeout")
	public String displaySessionTimeoutPage(Map<String, Object> model) {
		if (this.log.isDebugEnabled())
			this.log.debug("display session timeout page ...");
		return "error/sessionTimeout";
	}

	@RequestMapping(value = "/accessDenied")
	public String displayAccessDeniedPage() {
		if (this.log.isDebugEnabled())
			this.log.debug("display access denied page ...");
		return "error/accessDenied";
	}

	@RequestMapping(value = "/loginDenied")
	public String displayLoginDeniedPage() {
		if (this.log.isDebugEnabled())
			this.log.debug("display login denied page ...");
		return "error/loginDenied";
	}

	@RequestMapping(value = "/duplicateAccess")
	public String displayDuplicateAccessPage() {
		if (this.log.isDebugEnabled())
			this.log.debug("display duplicate access page ...");
		return "error/duplicateaccess";
	}

	@RequestMapping(value = "/errorCode404")
	public String handle404InternalServerError() {
		return "error/404";
	}

	@RequestMapping(value = "/errorCode405")
	public String handle405InternalServerError() {
		return "error/405";
	}

	@RequestMapping(value = "/errorCode500")
	public String handle500InternalServerError() {
		return "error/500";
	}

}
