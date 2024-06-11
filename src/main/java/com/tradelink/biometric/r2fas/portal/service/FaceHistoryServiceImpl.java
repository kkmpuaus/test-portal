package com.tradelink.biometric.r2fas.portal.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tradelink.biometric.r2fas.portal.dao.FaceHistoryDao;
import com.tradelink.biometric.r2fas.portal.db.model.FaceHistory;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;

@Service
@Transactional(rollbackFor = Throwable.class)
public class FaceHistoryServiceImpl implements FaceHistoryService {

	private Log log = LogFactory.getLog(this.getClass());
	
	public static final String UTF8_BOM = "\uFEFF";
	
	@Autowired
	private FaceHistoryDao faceHistoryDao;

	@Override
	public Page<FaceHistory> findFaceHistoryList(FaceHistory faceHistory, String startDate, String endDate, Pageable pageable) {
		WebUser webUser = AuthUtils.currentUserDetails();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Page<FaceHistory> page = null;
		
		log.debug("Start finding Token Activity for Sub ID: " + webUser.getSubId());

		Date fStartDate = new Date();
		Date fEndDate = new Date();
		
		try {
			fStartDate = sdf.parse(startDate);
		} catch (Exception e) {
			fStartDate= null;
		}
		try {
			fEndDate = sdf.parse(endDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fEndDate);
			calendar.add(Calendar.DATE, 1);
			fEndDate = calendar.getTime();
		} catch (Exception e) {
			fEndDate= null;
		}
		
		String cardNUmber = faceHistory.getCardNumber();
		if(cardNUmber == null) {
			if(fStartDate != null && fEndDate != null) {
				page = faceHistoryDao.findAllByStartEndDate(fStartDate, fEndDate, pageable);
			}
			else if(fStartDate != null && fEndDate == null) {
				page = faceHistoryDao.findAllByStartDate(fStartDate, pageable);
			}
			else if(fStartDate == null && fEndDate != null) {
				page = faceHistoryDao.findAllByEndDate(fEndDate, pageable);
			}
			else {
				page = faceHistoryDao.findAll(pageable);
			}
		} else {
			if(fStartDate != null && fEndDate != null) {
				page = faceHistoryDao.findAllByCardNumberAndStartEndDate(cardNUmber, fStartDate, fEndDate, pageable);
			}
			else if(fStartDate != null && fEndDate == null) {
				page = faceHistoryDao.findAllByCardNumberAndStartDate(cardNUmber, fStartDate, pageable);
			}
			else if(fStartDate == null && fEndDate != null) {
				page = faceHistoryDao.findAllByCardNumberAndEndDate(cardNUmber, fEndDate, pageable);
			}
			else {
				page = faceHistoryDao.findAllByCardNumber(cardNUmber, pageable);
			}
			
		}
		
		log.debug("Finish finding Face History" + webUser.getSubId());
		return page;
	}


}
