package com.tradelink.biometric.r2fas.portal.rest.entity;

import com.tradelink.biometric.r2fas.portal.constant.GalleryType;

public class RegistrationInData {
	
	private String cardNo;
	private GalleryType galleryType;
	private String personName;
	private String company;
	private String title;
	private String phone;
	private String email;
	private String filename;
	private String fileBase64;
	
	public String getCardNo() {
		return cardNo;
	}
	public String getFileBase64() {
		return fileBase64;
	}
	public void setFileBase64(String fileBase64) {
		this.fileBase64 = fileBase64;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public GalleryType getGalleryType() {
		return galleryType;
	}
	public void setGalleryType(GalleryType galleryType) {
		this.galleryType = galleryType;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public String toString() {
		return "RegistrationInData [cardNo=" + cardNo + ", personName=" + personName + ", galleryType=" + galleryType
				+ ", filename=" + filename + "]";
	}

}
