package com.tradelink.biometric.r2fas.portal.rest.entity;

public class ApiResult {
	
	private int resultCode;
	private String resultDesc;
	
	public ApiResult() {
		this.resultCode = 1000;
		this.resultDesc = "SUCCESS";
	}
	
	public ApiResult(int resultCode, String resultDesc) {
		super();
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	@Override
	public String toString() {
		return "ApiResult [resultCode=" + resultCode + ", resultDesc=" + resultDesc + "]";
	}
	
	
}
