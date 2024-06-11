package com.tradelink.biometric.r2fas.portal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tradelink.biometric.r2fas.portal.db.model.UserAuditLog;

public interface UserAuditLogDao extends JpaRepository<UserAuditLog, Long> {
	
}
