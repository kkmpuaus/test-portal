package com.tradelink.biometric.r2fas.portal.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.tradelink.biometric.r2fas.portal.constant.UserStatus;
import com.tradelink.biometric.r2fas.portal.db.model.FaceUser;

public interface FaceUserDao extends JpaRepository<FaceUser, Long> {
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceUser(o.cardNumber, o.personName, o.galleryType, o.created, o.updated, o.userStatus, o.company, o.title, o.phone, o.email) from FaceUser o"
			)
	Page<FaceUser> findAll(
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceUser(o.cardNumber, o.personName, o.galleryType, o.created, o.updated, o.userStatus, o.company, o.title, o.phone, o.email) from FaceUser o where o.cardNumber = :cardNumber"
			)
	Page<FaceUser> findAllByCardNumber(
			@Param("cardNumber") String cardNumber,
            Pageable pageable
        );
	
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceUser(o.cardNumber, o.personName, o.galleryType, o.created, o.updated, o.userStatus, o.company, o.title, o.phone, o.email) from FaceUser o where UPPER(o.personName) like %:personName%"
			)
	Page<FaceUser> findAllByPersonName(
			@Param("personName") String personName,
            Pageable pageable
        );
	
	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.FaceUser(o.cardNumber, o.personName, o.galleryType, o.created, o.updated, o.userStatus, o.company, o.title, o.phone, o.email) from FaceUser o where  o.cardNumber = :cardNumber and UPPER(o.personName) like %:personName%"
			)
	Page<FaceUser> findAllByCardNumberAndPersonName(
			@Param("cardNumber") String cardNumber,
			@Param("personName") String personName,
            Pageable pageable
        );

	
	@Modifying
	@Query("update FaceUser a set a.userStatus = :userStatus where a.cardNumber in :cardNumbers")
	public void updateUserStatusByCardNumber(
			@Param("cardNumbers") List<String> cardNumbers,
			@Param("userStatus") UserStatus userStatus
			);
	
	@Modifying
	@Query("delete from FaceUser l where l.cardNumber = :cardNumber")
	public void deleteUser(@Param("cardNumber") String cardNumber);

	@Modifying
	@Query("delete from FaceUser l where l.cardNumber in :cardNumbers")
	public void deleteUsers(@Param("cardNumbers") List<String> cardNumbers);
	
	public FaceUser findByCardNumber(String cardNumber);
	
}
