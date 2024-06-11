package com.tradelink.biometric.r2fas.portal.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tradelink.biometric.r2fas.portal.constant.FaceUserDetailConstants;
import com.tradelink.biometric.r2fas.portal.constant.GalleryType;
import com.tradelink.biometric.r2fas.portal.constant.UserStatus;
import com.tradelink.biometric.r2fas.portal.dao.AccessCardDao;
import com.tradelink.biometric.r2fas.portal.dao.FaceUserDao;
import com.tradelink.biometric.r2fas.portal.db.model.AccessCard;
import com.tradelink.biometric.r2fas.portal.db.model.FaceUser;
import com.tradelink.biometric.r2fas.portal.rest.entity.DeregistrationInData;
import com.tradelink.biometric.r2fas.portal.rest.entity.RegistrationInData;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;
import com.tradelink.biometric.r2fas.portal.utils.StringUtils;

@Service
@Transactional(rollbackFor = Throwable.class)
public class FaceUserServiceImpl implements FaceUserService {

	private Log log = LogFactory.getLog(this.getClass());

	public static final String UTF8_BOM = "\uFEFF";
	private ObjectMapper objectMapper = new ObjectMapper()
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

	@Autowired
	private FaceUserDao faceUserDao;

	@Autowired
	private AccessCardDao accessCardDao;

	@Autowired
	private SysParamService sysParamService;

	@Override
	public Page<FaceUser> findFaceUserList(FaceUser faceUser, Pageable pageable) {
		WebUser webUser = AuthUtils.currentUserDetails();
		Page<FaceUser> page = null;
		String cardNumber = faceUser.getCardNumber();
		String personName = faceUser.getPersonName();

		if (cardNumber == null && StringUtils.isNullOrEmpty(personName)) {
			log.debug("Start finding User List" + webUser.getSubId());
			page = faceUserDao.findAll(pageable);
		} else if (StringUtils.isNullOrEmpty(personName)) {
			log.debug("Start finding User List for Card Number" + webUser.getSubId());
			page = faceUserDao.findAllByCardNumber(cardNumber, pageable);
		} else if (cardNumber == null) {
			log.debug("Start finding User List for Person Name" + webUser.getSubId());
			personName = personName.toUpperCase();
			page = faceUserDao.findAllByPersonName(personName, pageable);
		} else {
			log.debug("Start finding User List for Card Number and Person Name" + webUser.getSubId());
			personName = personName.toUpperCase();
			page = faceUserDao.findAllByCardNumberAndPersonName(cardNumber, personName, pageable);
		}

		log.debug("Finish finding User List" + webUser.getSubId());
		return page;
	}

	@Override
	public int enrollFaceUser(RegistrationInData registrationInData) {

		log.debug("Enroll face user");

		String cardNumber = registrationInData.getCardNo();
		String personName = registrationInData.getPersonName();
		String phone = registrationInData.getPhone();
		String email = registrationInData.getEmail();
		GalleryType galleryType = registrationInData.getGalleryType();
		String filename = registrationInData.getFilename();
		String fileBase64 = registrationInData.getFileBase64();

		// validate card number
		if (cardNumber.length() != 6) {
			log.debug("Invalid card number format");
			return FaceUserDetailConstants.FACEUSERDETAIL_INVALID_CARDNUMBER;
		}

		// validate person name
		if (StringUtils.isNullOrEmpty(personName)) {
			log.debug("Empty person name");
			return FaceUserDetailConstants.FACEUSERDETAIL_EMPTY_PERSONNAME;
		}
		if (personName.length() > 100) {
			log.debug("Invalid person name. Too long.");
		}
		
		// validate phone
		if (!StringUtils.isNullOrEmpty(phone)) {
			if(!phone.chars().allMatch(Character::isDigit)) {
				log.debug("Invalid phone format");
				return FaceUserDetailConstants.FACEUSERDETAIL_INVALID_PHONE;
			}
		}
		
		// validate email
		if (!StringUtils.isNullOrEmpty(email)) {
			if(!email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_.-]+(\\.[a-zA-Z0-9_.-]+)*\\.[a-zA-Z0-9_.-]{2,48}$")) {
				log.debug("Invalid email format");
				return FaceUserDetailConstants.FACEUSERDETAIL_INVALID_EMAIL;
			}
		}

		// validate gallery type
		if (galleryType == null) {
			log.debug("Empty gallery type");
			return FaceUserDetailConstants.FACEUSERDETAIL_EMPTY_GALLERYTYPE;
		}

		// validate filename
		if (!StringUtils.isValidImageType(FilenameUtils.getExtension(filename))) {
			log.debug("Invalid person photo");
			return FaceUserDetailConstants.FACEUSERDETAIL_INVALID_PHOTO;
		}

		try {
			int resultCode = callFaceRegApi(registrationInData);
			switch (resultCode) {
			case 1000:
				log.debug("Enroll Image Success");
				return FaceUserDetailConstants.FACEUSERDETAIL_SAVE_SUCCESS;
			case 1003:
				log.debug("Card number exists");
				return FaceUserDetailConstants.FACEUSERDETAIL_DUPLICATED_CARDNUMBER;
			case 1004:
				log.debug("Enroll Image fails");
				return FaceUserDetailConstants.FACEUSERDETAIL_ENROLLIMAGEFAIL;
			default:
				log.debug("Unexpected Error," + resultCode);
				return FaceUserDetailConstants.FACEUSERDETAIL_SYSTEM_ERROR;
			}
		} catch (Exception e) {
			log.error("Exception found in call face reg api," + e.toString());
			return FaceUserDetailConstants.FACEUSERDETAIL_SYSTEM_ERROR;
		}
	}

	@Override
	public int updateFaceUser(String cardNumber, String personName) {
		log.debug("Update face user");
		FaceUser faceUser = faceUserDao.findByCardNumber(cardNumber);
		if (faceUser == null) {
			log.debug("Face user not found");
			return FaceUserDetailConstants.FACEUSERDETAIL_USER_NOT_FOUND;
		}
		// validate person name
		if (StringUtils.isNullOrEmpty(personName)) {
			log.debug("Empty person name");
			return FaceUserDetailConstants.FACEUSERDETAIL_EMPTY_PERSONNAME;
		}
		if (personName.length() > 100) {
			log.debug("Invalid person name. Too long.");
		}

		faceUser.setPersonName(personName);
		faceUser.setUpdated(new Date());
		faceUserDao.save(faceUser);
		return FaceUserDetailConstants.FACEUSERDETAIL_SAVE_SUCCESS;
	}

	@Override
	public FaceUser findFacerUserByCardNumber(String cardNumber) {
		log.debug("Find face user for modifying");
		FaceUser faceUser = faceUserDao.findByCardNumber(cardNumber);
		return faceUser;
	}

	@Override
	public void deactiveFaceUsers(List<String> cardNumbers) {
		if ((cardNumbers == null) || (cardNumbers.size() == 0)) {
			log.debug("No user to be deactived!");
			return;
		}
		faceUserDao.updateUserStatusByCardNumber(cardNumbers, UserStatus.DEACTIVE);
		log.debug("Face Users deactived!");
	}

	@Override
	public int deleteFaceUsers(List<String> cardNumbers) {
		if ((cardNumbers == null) || (cardNumbers.size() == 0)) {
			log.debug("No face user to be deleted!");
			return FaceUserDetailConstants.FACEUSERDETAIL_CARDNUMBER_NOT_FOUND;
		}

		int cnt_err = 0;
		for (String cardNumber : cardNumbers) {
			try {
				DeregistrationInData deregistrationInData = new DeregistrationInData();
				deregistrationInData.setCardNo(cardNumber);
				int resultCode = callFaceDeregApi(deregistrationInData);
				if (resultCode == 1000) {
					log.debug("Delete face user successs");
				} else if (resultCode == 1001) {
					log.debug("Card number not found");
					cnt_err++;
				} else {
					log.debug("Unexpected Error," + resultCode);
					return FaceUserDetailConstants.FACEUSERDETAIL_SYSTEM_ERROR;
				}
			} catch (Exception e) {
				log.error("Exception found in call face dereg api," + e.toString());
				return FaceUserDetailConstants.FACEUSERDETAIL_SYSTEM_ERROR;
			}
		}
		if (cnt_err == 0) {
			return FaceUserDetailConstants.FACEUSERDETAIL_SAVE_SUCCESS;
		} else {
			return FaceUserDetailConstants.FACEUSERDETAIL_CARDNUMBER_NOT_FOUND;
		}

	}

	@Override
	public void activeFaceUsers(List<String> cardNumbers) {
		if ((cardNumbers == null) || (cardNumbers.size() == 0)) {
			log.debug("No user to be actived!");
			return;
		}
		faceUserDao.updateUserStatusByCardNumber(cardNumbers, UserStatus.ACTIVE);
		log.debug("Face Users actived!");
	}

	public List<AccessCard> findAccessCardList() {
		return accessCardDao.findAll();
	}

	public int callFaceRegApi(RegistrationInData registrationInData) throws Exception {

		/*
		 * 1001: Card Number not Found (Dereg) 1002: File or Base64 String not Found
		 * (Reg) 1003: Card Number exists (Reg) 1004: Fail to enroll image (Reg)
		 */
		String regUrl = sysParamService.getRegUrl();
		URL url = new URL(regUrl);
		HttpURLConnection httpURLConnection = null;
		int resultCode;

		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(15000);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			log.debug("Start call face reg api for cardNumber," + registrationInData.getCardNo());
			httpURLConnection.connect();

			ObjectWriter objectWriter = this.objectMapper.writer();
			String json = objectWriter.writeValueAsString(registrationInData);

			OutputStreamWriter osw = null;
			try {
				osw = new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8);
				osw.write(json);
				osw.flush();
			} finally {
				if (osw != null) {
					osw.close();
				}
			}

			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				log.error("response code for face reg api unexpected, " + responseCode);
			}

			InputStream is;
			is = httpURLConnection.getInputStream();

			InputStreamReader isr = null;
			String returnJson = null;
			try {
				isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				StringBuilder sb = new StringBuilder();
				char[] buffer = new char[1024];
				int read = isr.read(buffer);
				while (read != -1) {
					sb.append(buffer, 0, read);
					read = isr.read(buffer);
				}
				returnJson = sb.toString();
			} finally {
				if (isr != null) {
					isr.close();
				}
			}

			ObjectReader reader = this.objectMapper.reader();
			JsonNode rootJsonNode = reader.readTree(returnJson);
			resultCode = Integer.parseInt(StringUtils.findStringValue(rootJsonNode, "resultCode"));

		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}

		return resultCode;

	}

	public int callFaceDeregApi(DeregistrationInData deregistrationInData) throws Exception {

		String deregUrl = sysParamService.getDeregUrl();
		URL url = new URL(deregUrl);
		HttpURLConnection httpURLConnection = null;
		int resultCode;

		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(15000);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			log.debug("Start call face dereg api for cardNumber," + deregistrationInData.getCardNo());
			httpURLConnection.connect();

			ObjectWriter objectWriter = this.objectMapper.writer();
			String json = objectWriter.writeValueAsString(deregistrationInData);

			OutputStreamWriter osw = null;
			try {
				osw = new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8);
				osw.write(json);
				osw.flush();
			} finally {
				if (osw != null) {
					osw.close();
				}
			}

			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				log.error("response code for face dereg api unexpected, " + responseCode);
			}

			InputStream is;
			is = httpURLConnection.getInputStream();

			InputStreamReader isr = null;
			String returnJson = null;
			try {
				isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				StringBuilder sb = new StringBuilder();
				char[] buffer = new char[1024];
				int read = isr.read(buffer);
				while (read != -1) {
					sb.append(buffer, 0, read);
					read = isr.read(buffer);
				}
				returnJson = sb.toString();
			} finally {
				if (isr != null) {
					isr.close();
				}
			}

			ObjectReader reader = this.objectMapper.reader();
			JsonNode rootJsonNode = reader.readTree(returnJson);
			resultCode = Integer.parseInt(StringUtils.findStringValue(rootJsonNode, "resultCode"));

		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}

		return resultCode;

	}

}
