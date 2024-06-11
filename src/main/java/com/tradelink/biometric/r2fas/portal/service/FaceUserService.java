package com.tradelink.biometric.r2fas.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tradelink.biometric.r2fas.portal.db.model.AccessCard;
import com.tradelink.biometric.r2fas.portal.db.model.FaceUser;
import com.tradelink.biometric.r2fas.portal.rest.entity.RegistrationInData;

public interface FaceUserService {

	public Page<FaceUser> findFaceUserList(FaceUser faceUser, Pageable pageable);
	
	public int enrollFaceUser(RegistrationInData registrationInData);
	
	public int updateFaceUser(String cardNumber, String personName);
	
	public FaceUser findFacerUserByCardNumber(String cardNumber);
	
	public void deactiveFaceUsers( List<String> cardNumbers);
	
	public void activeFaceUsers( List<String> cardNumbers);
	
	public int deleteFaceUsers( List<String> cardNumbers);
	
	public List<AccessCard> findAccessCardList();
	
}
