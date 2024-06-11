package com.tradelink.biometric.r2fas.portal.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractController {

	private Log log = LogFactory.getLog(this.getClass());

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex) {
		if (this.log.isDebugEnabled())
			this.log.debug("handle exception ...");

		if (this.log.isErrorEnabled())
			this.log.error("encounter exception in controller, redirect to error code 500 page", ex);

		return "redirect:/errorCode500.do";
	}

}
