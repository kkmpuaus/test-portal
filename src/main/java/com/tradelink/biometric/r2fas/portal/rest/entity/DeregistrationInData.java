package com.tradelink.biometric.r2fas.portal.rest.entity;

public class DeregistrationInData {
	
	private String cardNo;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	@Override
	public String toString() {
		return "DeregistrationInData [cardNo=" + cardNo + "]";
	}

}
