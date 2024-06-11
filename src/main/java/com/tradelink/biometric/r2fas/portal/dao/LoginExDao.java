package com.tradelink.biometric.r2fas.portal.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tradelink.biometric.r2fas.portal.db.model.LoginEx;

public interface LoginExDao extends JpaRepository<LoginEx, String> {

	@Modifying
	@Query("update LoginEx l set l.retry = :retry, l.lastFailedLoginDt = :lastFailedLoginDt where l.userId in :userId")
	public void updateRetryAndLastFailedLoginDateByUserId(
			@Param("retry") final int retry,
			@Param("lastFailedLoginDt") final Date lastFailedLoginDt,
			@Param("userId") final String userId);	

	@Modifying
	@Query("update LoginEx a set a.locked = :locked, a.lockedDt = :lockedDt where a.userId = :userId")
	public void updateLockedAndLockedDtByUserId(
			@Param("userId") final String userId,
			@Param("locked") final boolean locked,
			@Param("lockedDt") final Date lockedDt);	

	@Modifying
	@Query("update LoginEx a set a.lastLoginDt = :lastLoginDt, a.lastLoginIp = :lastLoginIp, a.loginDt = :loginDt, a.loginIp = :loginIp, a.retry = :retry where a.userId = :userId")
	public void updateLoginDateAndIpAndRetryByUserId(
			@Param("userId") final String UserId, 
			@Param("lastLoginDt") final Date lastLoginDt,
			@Param("lastLoginIp") final String lastLoginIp,
			@Param("loginDt") final Date loginDt,
			@Param("loginIp") final String loginIp,
			@Param("retry") final int retry);
	
	@Query("select password from LoginEx l where l.userId = :userId")
	public String findPasswordByUserId(@Param("userId") final String userId);	

	@Modifying
	@Query("update LoginEx a set a.password = :password, a.pwdReset = :pwdReset, a.pwdModifiedDt = :pwdModifiedDt where a.userId = :userId")
	public void updatePasswordByUserId(
			@Param("userId") final String userId, 
			@Param("pwdModifiedDt") final Date lastLpwdModifiedDtoginDt,
			@Param("password") final String password,
			@Param("pwdReset") final boolean pwdReset);
	
	@Modifying
	@Query("delete from LoginEx l where l.userId in :id and l.subId = :subId")
	public void deleteUsers(@Param("id") List<String> id, @Param("subId") String subId);
	
	@Modifying
	@Query("update LoginEx a set a.locked = :locked, a.lockedDt = :lockedDt, a.retry = :retry where a.userId in :ids and a.subId = :subId")
	public void updateLockedAndLockedDtAndRetryBySubId(
			@Param("ids") List<String> ids,
			@Param("subId") final String subId,
			@Param("lockedDt") final Date lockedDt,
			@Param("locked") final boolean locked,
			@Param("retry") final int retry);
	
	@Modifying
	@Query("update LoginEx a set a.displayName = :displayName, a.email = :email, a.password = :password, a.pwdReset = :pwdReset, a.pwdModifiedDt = :pwdModifiedDt where a.userId = :userId and a.subId = :subId")
	public void updateUserWithPasswordByUserIdAndCustomerNo(
			@Param("displayName") final String displayName,
			@Param("email") final String email,
			@Param("password") final String password,
			@Param("pwdReset") final boolean pwdReset,
			@Param("pwdModifiedDt") final Date lastLpwdModifiedDtoginDt,
			@Param("userId") final String userId,
			@Param("subId") final String subId);

	@Modifying
	@Query("update LoginEx a set a.displayName = :displayName, a.email = :email, a.pwdModifiedDt = :pwdModifiedDt where a.userId = :userId and a.subId = :subId")
	public void updateUserWithoutPasswordByUserIdAndCustomerNo(
			@Param("displayName") final String displayName,
			@Param("email") final String email,
			@Param("pwdModifiedDt") final Date lastLpwdModifiedDtoginDt,
			@Param("userId") final String userId,
			@Param("subId") final String subId);
		
	@Query("select userId from LoginEx l where l.userId = :userId")
	public String findUserIdByUserId(@Param("userId") final String userId);
	
	public List<LoginEx> findBySubId(String subId);
	public LoginEx findByUserIdAndSubId(String userId, String subId);
	public int countBySubIdAndUserRole(String subId, String userRole);
	
	

}