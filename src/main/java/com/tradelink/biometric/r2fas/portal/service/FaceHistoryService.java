package com.tradelink.biometric.r2fas.portal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tradelink.biometric.r2fas.portal.db.model.FaceHistory;

public interface FaceHistoryService {

	public Page<FaceHistory> findFaceHistoryList(FaceHistory faceHistory, String startDate, String endDate, Pageable pageable);
	
}
