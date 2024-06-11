package com.tradelink.biometric.r2fas.portal.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tradelink.biometric.r2fas.portal.db.model.FaceHistory;

public interface FaceHistoryDao extends JpaRepository<FaceHistory, Long> {
	
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceHistory(o.id, o.cameraUrl, o.cameraName, o.wiegandIp, o.wiegandPort, o.wiegandDescription, o.cardNumber, o.personName, o.company, o.title, o.phone, o.email, o.galleryType, o.historyType, o.created) from FaceHistory o where o.created >= :startDate and o.created < :endDate"
			)
	Page<FaceHistory> findAllByStartEndDate(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate, 
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceHistory(o.id, o.cameraUrl, o.cameraName, o.wiegandIp, o.wiegandPort, o.wiegandDescription, o.cardNumber, o.personName, o.company, o.title, o.phone, o.email, o.galleryType, o.historyType, o.created) from FaceHistory o where o.created >= :startDate"
			)
	Page<FaceHistory> findAllByStartDate(
            @Param("startDate") Date startDate,
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceHistory(o.id, o.cameraUrl, o.cameraName, o.wiegandIp, o.wiegandPort, o.wiegandDescription, o.cardNumber, o.personName, o.company, o.title, o.phone, o.email, o.galleryType, o.historyType, o.created) from FaceHistory o where o.created < :endDate"
			)
	Page<FaceHistory> findAllByEndDate(
            @Param("endDate") Date endDate, 
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceHistory(o.id, o.cameraUrl, o.cameraName, o.wiegandIp, o.wiegandPort, o.wiegandDescription, o.cardNumber, o.personName, o.company, o.title, o.phone, o.email, o.galleryType, o.historyType, o.created) from FaceHistory o"
			)
	Page<FaceHistory> findAll(
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceHistory(o.id, o.cameraUrl, o.cameraName, o.wiegandIp, o.wiegandPort, o.wiegandDescription, o.cardNumber, o.personName, o.company, o.title, o.phone, o.email, o.galleryType, o.historyType, o.created) from FaceHistory o where o.created >= :startDate and o.created < :endDate and o.cardNumber = :cardNumber"
			)
	Page<FaceHistory> findAllByCardNumberAndStartEndDate(
			@Param("cardNumber") String cardNumber,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate, 
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceHistory(o.id, o.cameraUrl, o.cameraName, o.wiegandIp, o.wiegandPort, o.wiegandDescription, o.cardNumber, o.personName, o.company, o.title, o.phone, o.email, o.galleryType, o.historyType, o.created) from FaceHistory o where o.created >= :startDate and o.cardNumber = :cardNumber"
			)
	Page<FaceHistory> findAllByCardNumberAndStartDate(
			@Param("cardNumber") String cardNumber,
			@Param("startDate") Date startDate,
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceHistory(o.id, o.cameraUrl, o.cameraName, o.wiegandIp, o.wiegandPort, o.wiegandDescription, o.cardNumber, o.personName, o.company, o.title, o.phone, o.email, o.galleryType, o.historyType, o.created) from FaceHistory o where o.created < :endDate and o.cardNumber = :cardNumber"
			)
	Page<FaceHistory> findAllByCardNumberAndEndDate(
			@Param("cardNumber") String cardNumber,
			@Param("endDate") Date endDate, 
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceHistory(o.id, o.cameraUrl, o.cameraName, o.wiegandIp, o.wiegandPort, o.wiegandDescription, o.cardNumber, o.personName, o.company, o.title, o.phone, o.email, o.galleryType, o.historyType, o.created) from FaceHistory o where o.cardNumber = :cardNumber"
			)
	Page<FaceHistory> findAllByCardNumber(
			@Param("cardNumber") String cardNumber,
			Pageable pageable
        );
	
	
}
