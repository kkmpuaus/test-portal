package com.tradelink.biometric.r2fas.portal.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "portal_login")
public class LoginEx implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "display_name")
	private String displayName;
	
	@Column(name = "password")
	private String password;

	@Transient
	private String passwordConfirm;
	
	@Transient
	private String oldPassword;

	@Column(name = "sub_id")
	private String subId;

	@Column(name = "user_role")
	private String userRole;

	@Column(name = "create_date")
	private Date createDt;
	
	@Column(name = "email")
	private String email;

	@Column(name = "pwd_reset", columnDefinition="NUMBER(1)")
	private boolean pwdReset;

	@Column(name = "pwd_modified_date")
	private Date pwdModifiedDt;

	@Column(name = "login_date")
	private Date loginDt;

	@Column(name = "login_ip")
	private String loginIp;

	@Column(name = "last_login_date")
	private Date lastLoginDt;

	@Column(name = "last_login_ip")
	private String lastLoginIp;

	@Column(name = "last_failed_login_date")
	private Date lastFailedLoginDt;

	@Column(name = "retry")
	private int retry;

	@Column(name = "locked", columnDefinition="NUMBER(1)")
	private boolean locked;

	@Column(name = "locked_date")
	private Date lockedDt;
	
	@Column(name = "status")
	private String status;

	/*
	 * @Transient private String statementNot;
	 * 
	 * @Transient private String invoiceNot;
	 * 
	 * @Transient private String reminderNot;
	 */
	
	/*
	 * public String getStatementNot() { return statementNot; }
	 * 
	 * public void setStatementNot(String statementNot) { this.statementNot =
	 * statementNot; }
	 * 
	 * public String getInvoiceNot() { return invoiceNot; }
	 * 
	 * public void setInvoiceNot(String invoiceNot) { this.invoiceNot = invoiceNot;
	 * }
	 * 
	 * public String getReminderNot() { return reminderNot; }
	 * 
	 * public void setReminderNot(String reminderNot) { this.reminderNot =
	 * reminderNot; }
	 */

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPwdReset() {
		return pwdReset;
	}

	public void setPwdReset(boolean pwdReset) {
		this.pwdReset = pwdReset;
	}

	public Date getPwdModifiedDt() {
		return pwdModifiedDt;
	}

	public void setPwdModifiedDt(Date pwdModifiedDt) {
		this.pwdModifiedDt = pwdModifiedDt;
	}

	public Date getLoginDt() {
		return loginDt;
	}

	public void setLoginDt(Date loginDt) {
		this.loginDt = loginDt;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLastLoginDt() {
		return lastLoginDt;
	}

	public void setLastLoginDt(Date lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastFailedLoginDt() {
		return lastFailedLoginDt;
	}

	public void setLastFailedLoginDt(Date lastFailedLoginDt) {
		this.lastFailedLoginDt = lastFailedLoginDt;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Date getLockedDt() {
		return lockedDt;
	}

	public void setLockedDt(Date lockedDt) {
		this.lockedDt = lockedDt;
	}

}

