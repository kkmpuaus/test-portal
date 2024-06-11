package com.tradelink.biometric.r2fas.portal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tradelink.biometric.r2fas.portal.db.model.SysParam;

public interface SysParamDao extends JpaRepository<SysParam, String> {

	public SysParam findByParamName (String paramName);
}