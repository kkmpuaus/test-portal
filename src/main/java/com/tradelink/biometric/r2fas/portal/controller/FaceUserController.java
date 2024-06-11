package com.tradelink.biometric.r2fas.portal.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tradelink.biometric.r2fas.portal.constant.FaceUserDetailConstants;
import com.tradelink.biometric.r2fas.portal.constant.UserDetailConstants;
import com.tradelink.biometric.r2fas.portal.db.model.AccessCard;
import com.tradelink.biometric.r2fas.portal.db.model.FaceUser;
import com.tradelink.biometric.r2fas.portal.rest.entity.FaceUserUpload;
import com.tradelink.biometric.r2fas.portal.rest.entity.RegistrationInData;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.service.FaceUserService;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;
import com.tradelink.biometric.r2fas.portal.utils.FileUtils;

@Controller
@RequestMapping("/")
public class FaceUserController {

	private Log log = LogFactory.getLog(this.getClass());

	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final String[] ORDER_BY_FIELDS_FACEUSER = { "cardNumber", "personName", "galleryType", "created",
			"updated", "archived", "userStatus" };
	private static final String DEFAULT_ORDER_BY_FACEUSER = "created";

	@Autowired
	FaceUserService faceUserService;

	@RequestMapping(value = "/faceuser")
	public String searchAccessActivityList(Map<String, Object> model,
			@RequestParam(value = "pageSize", required = false) Integer pageSizeInt,
			@RequestParam(value = "pageNo", required = false) Integer pageNoInt,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "ordering", required = false) String ordering,
			@RequestParam(value = "cardNumber", required = false) String cardNumber,
			@RequestParam(value = "personName", required = false) String personName) {

		log.debug("Searching Face user  ...");
		WebUser webUser = AuthUtils.currentUserDetails();

		int pageNo = 1;
		if (pageNoInt != null) {
			pageNo = pageNoInt.intValue();
		}
		int pageSize = DEFAULT_PAGE_SIZE;
		if (pageSizeInt != null) {
			pageSize = pageSizeInt.intValue();
		}

		if (StringUtils.isEmpty(orderBy)) {
			orderBy = DEFAULT_ORDER_BY_FACEUSER;
		} else {
			boolean isValid = false;
			for (String f : ORDER_BY_FIELDS_FACEUSER) {
				if (f.equals(orderBy)) {
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				orderBy = DEFAULT_ORDER_BY_FACEUSER;
			}
		}
		if (StringUtils.isEmpty(ordering)) {
			ordering = Sort.Direction.DESC.toString();
		}
		log.debug("User ID:" + webUser.getUsername() + " getting face user page, pageNo: " + pageNo + ", pageSize: "
				+ pageSize + ", ordering: " + ordering + ", orderBy: " + orderBy);
		Pageable pageable = new PageRequest(pageNo - 1, pageSize, Sort.Direction.fromString(ordering), orderBy);

		FaceUser criteria = new FaceUser();
		if(cardNumber != null) {
			criteria.setCardNumber(cardNumber);
		}
		
		if(!StringUtils.isEmpty(personName)) {
			criteria.setPersonName(personName);	
		}

		Page<FaceUser> result = faceUserService.findFaceUserList(criteria, pageable);
		List<FaceUser> profiles = null;
		if (result != null) {
			profiles = result.getContent();
			int currentPage = result.getNumber() + 1;
			model.put("resultList", profiles);
			model.put("totalPageNo", (result.getTotalPages() == 0 ? 1 : result.getTotalPages()));
			model.put("pageNo", currentPage);
			model.put("pageSize", result.getSize());
			model.put("totalNo", result.getTotalElements());
			Iterator<Order> currectOrder = result.getSort().iterator();
			if (currectOrder.hasNext()) {
				Order order = currectOrder.next();
				model.put("orderBy", order.getProperty());
				model.put("ordering", order.getDirection().toString());
			}
		}
		model.put("menuUserProfile", "active");
		model.put("totalNoOfErrMsg", FaceUserDetailConstants.TOTAL_NO_OF_FACEUSERDETAIL_ERROR);
		log.debug("Searching Face User finished!");
		return "faceuser";
	}

	@RequestMapping(value = "/newfaceuser", method = RequestMethod.GET)
	public String showAddFaceUserPage(Map<String, Object> model) {
		if (this.log.isDebugEnabled())
			this.log.debug("display add face user page ...");
		List<AccessCard> accessCardList = faceUserService.findAccessCardList();
		model.put("accessCardList", accessCardList);
		return showFaceUserDetailPage(model, null, FaceUserDetailConstants.SAVE_TYPE_ADD);
	}
	
	@RequestMapping(value = "/modifyfaceuser",  method = RequestMethod.POST)
	public String showModifyFaceUserPage(Map<String, Object> model, @RequestParam(value = "cardNumber") String cardNumber) {
		if (this.log.isDebugEnabled())
			this.log.debug("display modify face user page ...");
		return showFaceUserDetailPage(model, faceUserService.findFacerUserByCardNumber(cardNumber),
				UserDetailConstants.SAVE_TYPE_MODIFY);
	}
	
	private String showFaceUserDetailPage(Map<String, Object> model, FaceUser faceUser, int showType) {
		model.put("faceUser", faceUser);
		model.put("menuFaceUser", "active");
		model.put("saveType", showType);
		model.put("isModify", FaceUserDetailConstants.SAVE_TYPE_MODIFY == showType);
		model.put("totalNoOfErrMsg", FaceUserDetailConstants.TOTAL_NO_OF_FACEUSERDETAIL_ERROR);
		return "faceuserdetail";
	}
	
	@RequestMapping(value = "/addfaceuser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public int uploadFaceUser(@ModelAttribute FaceUserUpload faceUserUpload) {
		
		log.info("Upload faceuser ...");
		try {
			String base64str = null;
			if(!StringUtils.isEmpty(faceUserUpload.getPersonPhotoFile().getOriginalFilename())) {
				log.info("Using Upload Photo," + faceUserUpload.getPersonPhotoFile().getOriginalFilename());
				base64str = FileUtils.ImagetoBase64(faceUserUpload.getPersonPhotoFile().getBytes(), faceUserUpload.getPersonPhotoFile().getOriginalFilename());
			} else if(faceUserUpload.getCameraPhotoFile() != null) {
				log.info("Using Camera Photo," + faceUserUpload.getCameraPhotoFile().getOriginalFilename());
				base64str = FileUtils.ImagetoBase64(faceUserUpload.getCameraPhotoFile().getBytes(), faceUserUpload.getCameraPhotoFile().getOriginalFilename());
			} else {
				log.info("No Photo");
				return FaceUserDetailConstants.FACEUSERDETAIL_EMPTY_PHOTO;
			}
				
			RegistrationInData registrationInData = new RegistrationInData();
			registrationInData.setCardNo(faceUserUpload.getCardNumber());
			registrationInData.setPersonName(faceUserUpload.getPersonName());
			registrationInData.setCompany(faceUserUpload.getCompany());
			registrationInData.setTitle(faceUserUpload.getTitle());
			registrationInData.setPhone(faceUserUpload.getPhone());
			registrationInData.setEmail(faceUserUpload.getEmail());	
			registrationInData.setGalleryType(faceUserUpload.getGalleryType());
			registrationInData.setFilename("selfie.jpg");
			registrationInData.setFileBase64(base64str);
			
			return faceUserService.enrollFaceUser(registrationInData);
		} catch (IOException e) {
			log.error("Fail to encode image to base64");
			return FaceUserDetailConstants.FACEUSERDETAIL_SYSTEM_ERROR;
		}
	}
	
	@RequestMapping(value = "/editfaceuser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public int editFaceUser(Map<String, Object> model, FaceUserUpload faceUserUpload) {
		if (this.log.isDebugEnabled())
			this.log.debug("Modifying faceuser...");
		model.put("menuFaceUser", "active");
		return faceUserService.updateFaceUser(faceUserUpload.getCardNumber(), faceUserUpload.getPersonName());
	}
	
	@RequestMapping(value = "/deactivefaceuser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public boolean deactiveFaceUser(Map<String, Object> model,
			@RequestParam(value = "cardNumber") List<String> cardNumbers) {
		log.debug("Deactive user ...");
		if ((cardNumbers == null) || (cardNumbers.size() == 0)) {
			log.debug("Nothing to be deactived!");
			return true;
		}
		faceUserService.deactiveFaceUsers(cardNumbers);
		log.info("Deactive face user finish!");
		return true;
	}
	
	@RequestMapping(value = "/activefaceuser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public boolean activeFaceUser(Map<String, Object> model,
			@RequestParam(value = "cardNumber") List<String> cardNumbers) {
		log.debug("Active user ...");
		if ((cardNumbers == null) || (cardNumbers.size() == 0)) {
			log.debug("Nothing to be actived!");
			return true;
		}
		faceUserService.activeFaceUsers(cardNumbers);
		log.info("active face user finish!");
		return true;
	}
	
	@RequestMapping(value = "/deletefaceuser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public int deleteFaceUser(Map<String, Object> model,
			@RequestParam(value = "cardNumber") List<String> cardNumbers) {
		log.debug("Delete user ...");
		log.info("delete face user finish!");
		return faceUserService.deleteFaceUsers(cardNumbers);
	}

}
