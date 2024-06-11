package com.tradelink.biometric.r2fas.portal.constant;

public class AccountConstants {

	public static final int DEFAULT_LOGIN_RETRY_MAX = 3;
	public static final int DEFAULT_LOGIN_RETRY_REMINDER = 2;
	public static final int DEFAULT_MAX_NO_OF_NORMAL_USER = 5;
	
	// account login status	
	public static enum LoginStatus {
		active("A"), inactive("I"), pending("P");
		
		private String actualValue = null;
		private LoginStatus(String value) {
			this.actualValue = value;
		}
		
		public String getActualValue() {
			return this.actualValue;
		}
		
		public boolean isEqual(String actualValue) {
			return this.actualValue.equals(actualValue);
		}
				
		@Override
		public String toString() {
			return this.getActualValue();
		}		
	}

}
