package com.tradelink.biometric.r2fas.portal.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import com.tradelink.biometric.r2fas.portal.constant.HistoryType;

@Entity
@Table(name = "face_history")
@Configurable(autowire = Autowire.BY_TYPE)
public class FaceHistory implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "camera_url", length = 100)
	private String cameraUrl;
	
	@Column(name = "camera_name", length = 100)
	private String cameraName;
	
	@Column(name = "wiegand_ip", length = 100)
	private String wiegandIp;
	
	@Column(name = "wiegand_port")
	private int wiegandPort;
	
	@Column(name = "wiegand_description", length = 100)
	private String wiegandDescription;
	
	@Column(name = "cardnumber")
	private String cardNumber;
	
	@Column(name = "personname", length = 100)
	private String personName;
	
	@Column(name = "company", length = 100)
	private String company;
	
	@Column(name = "title", length = 100)
	private String title;
	
	@Column(name = "phone", length = 100)
	private String phone;
	
	@Column(name = "email", length = 100)
	private String email;
	
	@Column(name = "gallerytype", length = 10)
	@Enumerated(EnumType.STRING)
	private GalleryType galleryType;
	
	@Column(name = "historytype", length = 10)
	@Enumerated(EnumType.STRING)
	private HistoryType historyType;
	
	@Column(name = "created")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@PrePersist
	private void prePersist() {
		this.created = new Date();
	}

	@PostPersist
	private void postPersist() {
	}
	
	
	public FaceHistory() {
		
	}
	
	public FaceHistory( Long id, String cameraUrl, String cameraName, String wiegandIp, int wiegandPort, String wiegandDescription, String cardNumber, String personName, String company, String title, String phone, String email, GalleryType galleryType, HistoryType historyType, Date created) {
		this.id = id;
		this.cameraUrl = cameraUrl;
		this.cameraName = cameraName;
		this.wiegandIp = wiegandIp;
		this.wiegandPort = wiegandPort;
		this.wiegandDescription = wiegandDescription;
		this.cardNumber = cardNumber;
		this.personName = personName;
		this.company = company;
		this.title = title;
		this.phone = phone;
		this.email = email;
		this.galleryType  = galleryType;
		this.historyType = historyType;
		
		this.created = created;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCameraUrl() {
		return cameraUrl;
	}

	public void setCameraUrl(String cameraUrl) {
		this.cameraUrl = cameraUrl;
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getWiegandIp() {
		return wiegandIp;
	}

	public void setWiegandIp(String wiegandIp) {
		this.wiegandIp = wiegandIp;
	}

	public int getWiegandPort() {
		return wiegandPort;
	}

	public void setWiegandPort(int wiegandPort) {
		this.wiegandPort = wiegandPort;
	}
	
	public String getWiegandDescription() {
		return wiegandDescription;
	}

	public void setWiegandDescription(String wiegandDescription) {
		this.wiegandDescription = wiegandDescription;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public GalleryType getGalleryType() {
		return galleryType;
	}

	public void setGalleryType(GalleryType galleryType) {
		this.galleryType = galleryType;
	}

	public HistoryType getHistoryType() {
		return historyType;
	}

	public void setHistoryType(HistoryType historyType) {
		this.historyType = historyType;
	}

}
