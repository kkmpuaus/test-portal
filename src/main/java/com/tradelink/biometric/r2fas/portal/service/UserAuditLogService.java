package com.tradelink.biometric.r2fas.portal.service;

public interface UserAuditLogService {
	public void log(String detail, String remark);
	public void log(String subId, String userId, String displayName, String detail);
	public void log(String subId, String userId, String displayName, String detail, String remark);
}
