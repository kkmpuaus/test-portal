package com.tradelink.biometric.r2fas.portal.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.tradelink.biometric.r2fas.portal.constant.GalleryType;
import com.tradelink.biometric.r2fas.portal.constant.UserStatus;


@Entity
@Table(name = "face_user")
@Configurable(autowire = Autowire.BY_TYPE)
public class FaceUser implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cardnumber")
	@NotNull
	private String cardNumber;
	
	@Column(name = "personname", length = 100)
	private String personName;
	
	@Column(name = "gallerytype", length = 10)
	@Enumerated(EnumType.STRING)
	private GalleryType galleryType;

	@Column(name = "galleryindex")
	private Long galleryIndex;

	@Column(name = "personid", length = 40)
	private String personId;

	/*
	 * @Column(name = "filename", length = 100) private String fileName;
	 */
	
	@Column(name = "created")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	
	@Column(name = "status", length = 10)
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	
	@Column(name = "company", length = 100)
	private String company;
	
	@Column(name = "title", length = 100)
	private String title;
	
	@Column(name = "phone", length = 100)
	private String phone;
	
	@Column(name = "email", length = 100)
	private String email;
	
	
	@PrePersist
	private void prePersist() {
		this.created = new Date();
		this.updated = null;
		this.userStatus = UserStatus.ACTIVE;
	}

	@PostPersist
	private void postPersist() {
	}
	
	public FaceUser() {
		
	}
	
	public FaceUser(String cardNumber, String personName, GalleryType galleryType, Date created, Date updated, UserStatus userStatus, String company, String title, String phone, String email) {
		this.cardNumber = cardNumber;
		this.personName = personName;
		this.galleryType = galleryType;
		this.created= created;
		this.updated = updated;
		this.userStatus = userStatus;
		this.company = company;
		this.title = title;
		this.phone = phone;
		this.email = email;
	}

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

	public Long getGalleryIndex() {
		return galleryIndex;
	}

	public void setGalleryIndex(Long galleryIndex) {
		this.galleryIndex = galleryIndex;
	}
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
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
	
	
	
	/*
	 * public String getFileName() { return fileName; }
	 * 
	 * public void setFileName(String fileName) { this.fileName = fileName; }
	 */

	


}
