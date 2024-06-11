package com.tradelink.biometric.r2fas.portal.utils;

//import org.springframework.beans.factory.annotation.Value;

public class CaptchaSettings {
	public static final String CAPTCHA_RESPONSE_FIELD = "g-recaptcha-response";

	//@Value("${google.recaptcha.key.site}")
	private String site;
	
	//@Value("${google.recaptcha.key.secret}")
	private String secret;

	//@Value("${google.recaptcha.url}")
	private String url;

	//@Value("${google.recaptcha.on}")
	private boolean isOn = false;

	public boolean isOn() {
		return isOn;
	}
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}

}
