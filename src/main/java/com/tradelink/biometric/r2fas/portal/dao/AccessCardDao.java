package com.tradelink.biometric.r2fas.portal.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradelink.biometric.r2fas.portal.db.model.AccessCard;

public interface AccessCardDao extends JpaRepository<AccessCard, Long> {


}
