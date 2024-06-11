package com.tradelink.biometric.r2fas.portal.rest.entity;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;
import com.tradelink.biometric.r2fas.portal.constant.GalleryType;

public class FaceUserUpload implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String cardNumber;
	
	@NotNull
	private String personName;
	
	private String company;
	
	private String title;
	
	private String phone;
	
	private String email;
	
	@NotNull
	private GalleryType galleryType;
	
	private MultipartFile personPhotoFile;
	
	private MultipartFile cameraPhotoFile;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public GalleryType getGalleryType() {
		return galleryType;
	}

	public void setGalleryType(GalleryType galleryType) {
		this.galleryType = galleryType;
	}

	public MultipartFile getPersonPhotoFile() {
		return personPhotoFile;
	}

	public void setPersonPhotoFile(MultipartFile personPhotoFile) {
		this.personPhotoFile = personPhotoFile;
	}

	public MultipartFile getCameraPhotoFile() {
		return cameraPhotoFile;
	}

	public void setCameraPhotoFile(MultipartFile cameraPhotoFile) {
		this.cameraPhotoFile = cameraPhotoFile;
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
}
