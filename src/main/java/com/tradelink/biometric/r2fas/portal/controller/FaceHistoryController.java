package com.tradelink.biometric.r2fas.portal.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tradelink.biometric.r2fas.portal.db.model.FaceHistory;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.service.FaceHistoryService;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;

@Controller
@RequestMapping("/")
public class FaceHistoryController {

	private Log log = LogFactory.getLog(this.getClass());
	
	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final String[] ORDER_BY_FIELDS_FACEHISTORY = {"historyType", "cameraName", "wiegandIp","wiegandPort","cardNumber","galleryType","created" };
	private static final String DEFAULT_ORDER_BY_FACEHISTORY = "created";
	
	@Autowired
	FaceHistoryService faceUserInfoService;	

	@RequestMapping(value = "/facehistory")
	public String searchFaceHistoryList(Map<String, Object> model,
			@RequestParam(value = "pageSize", required = false) Integer pageSizeInt,
			@RequestParam(value = "pageNo", required = false) Integer pageNoInt,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "ordering", required = false) String ordering,
			@RequestParam(value = "cardNumber", required = false) String cardNumber,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {

		log.debug("Searching face history ...");
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
			orderBy = DEFAULT_ORDER_BY_FACEHISTORY;
		} else {
			boolean isValid = false;
			for (String f : ORDER_BY_FIELDS_FACEHISTORY) {
				if (f.equals(orderBy)) {
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				orderBy = DEFAULT_ORDER_BY_FACEHISTORY;
			}
		}
		if (StringUtils.isEmpty(ordering)) {
			ordering = Sort.Direction.DESC.toString();
		}
		log.debug("User ID:" + webUser.getUsername() + " getting face history page, pageNo: " + pageNo + ", pageSize: "
				+ pageSize + ", ordering: " + ordering + ", orderBy: " + orderBy);
		Pageable pageable = new PageRequest(pageNo - 1, pageSize, Sort.Direction.fromString(ordering), orderBy);
		
		FaceHistory criteria = new FaceHistory();
		if(!StringUtils.isEmpty(cardNumber)) {
			criteria.setCardNumber(cardNumber);	
		}
		
		Page<FaceHistory> result = faceUserInfoService.findFaceHistoryList(criteria, startDate, endDate, pageable);
		List<FaceHistory> profiles = null;
		if (result != null) {
			profiles = result.getContent();
			int currentPage = result.getNumber() + 1;
			model.put("resultList", profiles);
			model.put("totalPageNo", (result.getTotalPages() == 0 ? 1 : result.getTotalPages()));
			model.put("pageNo", currentPage);
			model.put("pageSize", result.getSize());
			model.put("totalNo", result.getTotalElements());
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			model.put("cardNumber", cardNumber);
			Iterator<Order> currectOrder = result.getSort().iterator();
			if (currectOrder.hasNext()) {
				Order order = currectOrder.next();
				model.put("orderBy", order.getProperty());
				model.put("ordering", order.getDirection().toString());
			}
		}
		model.put("menuFaceHistory", "active");
		log.debug("Searching face history finished!");
		return "facehistory";
	}

}