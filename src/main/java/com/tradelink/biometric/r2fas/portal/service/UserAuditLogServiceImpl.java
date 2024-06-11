package com.tradelink.biometric.r2fas.portal.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tradelink.biometric.r2fas.portal.dao.UserAuditLogDao;
import com.tradelink.biometric.r2fas.portal.db.model.UserAuditLog;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.service.UserAuditLogService;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserAuditLogServiceImpl implements UserAuditLogService {

	@Autowired
	private UserAuditLogDao userAuditLogDao;

	@Override
	public void log(String subId, String userId, String displayName, String detail) {
		log(subId, userId, displayName, detail, null);
	}

	@Override
	public void log(String subId, String userId, String displayName, String detail,
			String remark) {
		UserAuditLog auditLog = new UserAuditLog();
		auditLog.setSubId(subId);
		auditLog.setAuditDate(new Date());
		auditLog.setUserId(userId);
		auditLog.setDisplayName(displayName);
		auditLog.setDetail(detail);
		auditLog.setRemark(remark);
		userAuditLogDao.save(auditLog);
	}

	@Override
	public void log(String detail, String remark) {
		WebUser webUser = AuthUtils.currentUserDetails();
		log(webUser.getSubId(), webUser.getUsername(),
				webUser.getDisplayName(), detail, remark);
	}
}