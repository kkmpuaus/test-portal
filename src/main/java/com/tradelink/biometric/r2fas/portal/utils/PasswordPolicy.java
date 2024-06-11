package com.tradelink.biometric.r2fas.portal.utils;

public class PasswordPolicy {

	private static final String UPPERCASE_PATTERN = "^(?=.*[A-Z]).+$";
	private static final String LOWERCASE_PATTERN = "^(?=.*[a-z]).+$";
	private static final String NUMERICAL_PATTERN = "^(?=.*[0-9]).+$";
	private static final String SYMBOLS_PATTERN   = "^(?=.*[@#$%^&+=]).+$";
	
	private static final int TYPES_OF_CHARACTER = 3;
	private static final int LEN_OF_PASSWORD = 8;
	
	public static final boolean isValid(String password) {

		if (password == null) {
			return false;
		}
		if(password.length() < LEN_OF_PASSWORD) {
			return false;
		}
		int noOfTypes = 0;
		if (password.matches(UPPERCASE_PATTERN)) {
			noOfTypes++;
		}
		if (password.matches(LOWERCASE_PATTERN)) {
			noOfTypes++;
		}
		if (password.matches(NUMERICAL_PATTERN)) {
			noOfTypes++;
		}
		if (password.matches(SYMBOLS_PATTERN)) {
			noOfTypes++;
		}
		if (noOfTypes >= TYPES_OF_CHARACTER) {
			return true;
		}
		return false;
	}

}
