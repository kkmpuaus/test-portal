package com.tradelink.biometric.r2fas.portal.db.model;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.tradelink.biometric.r2fas.portal.constant.CameraStatus;
import com.tradelink.biometric.r2fas.portal.constant.Gender;

@Entity
@Table(name = "access_card")
@Configurable(autowire = Autowire.BY_TYPE)
public class AccessCard implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cardnumber")
	@NotNull
	private String cardNumber;
	
	@Column(name = "gender", length = 20)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
