package com.tradelink.biometric.r2fas.portal.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.util.StringUtils;

/*
import com.tradelink.biometric.r2fas.portal.security.exception.ReCaptchaInvalidException;
import com.tradelink.biometric.r2fas.portal.security.model.ReCaptchaResponse;
import com.tradelink.biometric.r2fas.portal.service.CaptchaService;
import com.tradelink.biometric.r2fas.portal.utils.CaptchaSettings;
*/

public class ReCaptchaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/*
	@Autowired
	private CaptchaService captchaService;
	*/
	private Log log = LogFactory.getLog(this.getClass());

	public ReCaptchaAuthenticationFilter() {}

	public Authentication attemptAuthentication(HttpServletRequest request,
						HttpServletResponse response) throws AuthenticationException {
		/*
		String reCaptchaResponse = request.getParameter(CaptchaSettings.CAPTCHA_RESPONSE_FIELD);
		String remoteAddress = request.getRemoteAddr();
 		*/
		//if (!captchaService.isCaptchaOn()) {
			log.debug("ReCaptcha service is off, bypass reCaptcha checking...");
			return super.attemptAuthentication(request, response);
		//}
		/*
		if (!StringUtils.isEmpty(reCaptchaResponse)) {
			log.debug("ReCaptcha Answser not null, call ReCaptcha to verify it");
			ReCaptchaResponse gResponse = captchaService.getResponse(reCaptchaResponse, remoteAddress);
			if ((gResponse != null) && (gResponse.isSuccess())) {
				log.debug("ReCaptcha response is success, attempt authentication");
				return super.attemptAuthentication(request, response);
			} else {
				log.debug("ReCaptcha response is failed, authentication failed");
				if (gResponse == null) {
					throw new ReCaptchaInvalidException("ReCaptcha failed : response is empty");
				}
				throw new ReCaptchaInvalidException("ReCaptcha failed : " + gResponse.getErrorMsg(0));
			}
		} else {
			log.debug("ReCaptcha response is empty, authentication failed");
			throw new ReCaptchaInvalidException("ReCaptcha failed : empty response");
		}
		*/
	}

	/*
	public CaptchaService getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}
	*/
}