package com.tradelink.biometric.r2fas.portal.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tradelink.biometric.r2fas.portal.security.model.UserRoles;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;
import com.tradelink.biometric.r2fas.portal.constant.UserDetailConstants;
import com.tradelink.biometric.r2fas.portal.constant.AccountConstants;
import com.tradelink.biometric.r2fas.portal.constant.UserAuditLogConstants;
import com.tradelink.biometric.r2fas.portal.dao.LoginExDao;
import com.tradelink.biometric.r2fas.portal.db.model.LoginEx;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.utils.PasswordPolicy;

@Service
@Transactional(rollbackFor = Throwable.class)
public class LoginServiceImpl implements LoginService {

	private static final String LOGIN_RETRY_MAX_PARAM = "LOGIN_RETRY_MAX";
	private static final String LOGIN_RETRY_REMINDER_PARAM = "LOGIN_RETRY_REMINDER";
	private static final String MAX_NO_OF_NORMAL_USER_PARAM = "MAX_NO_OF_NORMAL_USER";
	private static int loginRetryMax = -1;
	private static int loginRetryReminder = -1;
	private static int maxNoOfNormalUser = 10;

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private LoginExDao loginExDao;

	@Autowired
	private UserAuditLogService userAuditLogService;

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public LoginEx findLoginEx(String userId) {
		if (userId == null || userId.length() == 0) {
			return null;
		}

		LoginEx loginEx = loginExDao.findOne(userId);
		return loginEx;
	}

	@Override
	public int loginFail(String userId, String reason) {
		LoginEx loginEx = findLoginEx(userId);
		if (loginEx == null) {
			log.info("Nothing to update for User Id: " + userId);
			if (UserAuditLogConstants.LOGIN_FAIL_REASON_INVALID_USERNAME_PASSWORD.equals(reason)) {
				reason = UserAuditLogConstants.LOGIN_FAIL_REASON_INVALID_USERNAME;
			}
			userAuditLogService.log(null, userId, null, UserAuditLogConstants.AUDIT_DETAIL_LOGIN_FAIL, reason);
			return -1;
		}
		userAuditLogService.log(loginEx.getSubId(), userId, loginEx.getDisplayName(),
				UserAuditLogConstants.AUDIT_DETAIL_LOGIN_FAIL, reason);
		int retry = loginEx.getRetry() + 1;
		if (retry >= getLoginRetryMax()) {
			if (!loginEx.isLocked()) {
				loginExDao.updateLockedAndLockedDtByUserId(userId, true, new Date());
				log.info("The account [" + userId + "] has been locked!");
				return 0;
			}
		}
		loginExDao.updateRetryAndLastFailedLoginDateByUserId(retry, new Date(), userId);
		log.debug("Login fail updated for User Id :" + userId + " retry :" + retry);
		return ((retry >= getLoginRetryReminder()) ? getLoginRetryMax() - retry : -1);
	}

	@Override
	public void loginSuccess(WebUser webUser, String loginIp) {
		if (webUser == null) {
			log.debug("WebUser is null, cannot update for login success....");
			return;
		}
		if (webUser.getLoginEx() == null) {
			log.debug("WebUser.loginEx is null, cannot update for login success....");
			return;
		}

		String userId = webUser.getUsername();
		if ((userId != null) && (userId.length() > 0)) {
			String lastLoginIp = webUser.getLoginEx().getLoginIp();
			Date lastLoginDt = webUser.getLoginEx().getLoginDt();
			loginExDao.updateLoginDateAndIpAndRetryByUserId(userId, lastLoginDt, lastLoginIp, new Date(), loginIp, 0);
			userAuditLogService.log(webUser.getSubId(), webUser.getUsername(), webUser.getDisplayName(),
					UserAuditLogConstants.AUDIT_DETAIL_LOGIN_SUCCESS);
			log.debug("User Id [" + userId + "] login success!");
		} else {
			log.debug("User Id is null, cannot update for login success....");
		}
	}

	@Override
	public void logout(String userId) {
		LoginEx loginEx = findLoginEx(userId);
		if (loginEx == null) {
			userAuditLogService.log(null, null, userId, null, UserAuditLogConstants.AUDIT_DETAIL_LOGOUT_SUCCESS);
		} else {
			userAuditLogService.log(loginEx.getSubId(), userId, loginEx.getDisplayName(),
					UserAuditLogConstants.AUDIT_DETAIL_LOGOUT_SUCCESS);
		}
	}

	private int getLoginRetryMax() {
		if (loginRetryMax <= 0) {
			String retryStringValue = sysParamService.getLoginRetryMax();
			if (retryStringValue != null) {
				loginRetryMax = Integer.parseInt(retryStringValue);
			} else {
				loginRetryMax = AccountConstants.DEFAULT_LOGIN_RETRY_MAX;
			}
		}
		return loginRetryMax;
	}

	private int getLoginRetryReminder() {
		if (loginRetryReminder <= 0) {
			String retryReminderStringValue = sysParamService.getLoginRetryReminder();
			if (retryReminderStringValue != null) {
				loginRetryReminder = Integer.parseInt(retryReminderStringValue);
			} else {
				loginRetryReminder = AccountConstants.DEFAULT_LOGIN_RETRY_REMINDER;
			}
		}
		return loginRetryReminder;
	}

	@Override
	public List<LoginEx> findUserList() {
		WebUser webUser = AuthUtils.currentUserDetails();
		if (!UserRoles.ROLE_ADMIN.equals(webUser.getRole())) {
			return null;
		}
		return loginExDao.findBySubId(webUser.getSubId());
	}

	@Override
	public int findNoOfNormalUser() {
		WebUser webUser = AuthUtils.currentUserDetails();
		return loginExDao.countBySubIdAndUserRole(webUser.getSubId(), UserRoles.ROLE_HR) + loginExDao.countBySubIdAndUserRole(webUser.getSubId(), UserRoles.ROLE_IT);
	}

	@Override
	public int getMaxNoOfNormalUser() {
		if (maxNoOfNormalUser <= 0) {
			String maxNoOfNormalUserStringValue = sysParamService.getMaxNoNormalUser();
			if (maxNoOfNormalUserStringValue != null) {
				maxNoOfNormalUser = Integer.parseInt(maxNoOfNormalUserStringValue);
			} else {
				maxNoOfNormalUser = AccountConstants.DEFAULT_MAX_NO_OF_NORMAL_USER;
			}
		}
		return maxNoOfNormalUser;
	}

	@Override
	public LoginEx findUserForModify(String userId) {
		log.debug("Find user for modifying");
		if (userId == null) {
			log.debug("UserId is null, no user is found!");
			return null;
		}
		WebUser webUser = AuthUtils.currentUserDetails();
		LoginEx user = loginExDao.findByUserIdAndSubId(userId, webUser.getSubId());
		return user;
	}

	@Override
	public void deleteUsers(List<String> ids) {
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("No user to be deleted!");
			return;
		}
		WebUser webUser = AuthUtils.currentUserDetails();
		for (int i = 0; i < ids.size(); i++) {
			LoginEx user = findLoginEx(ids.get(i));
			String displayName = null;
			if (user != null)
				displayName = user.getDisplayName();
			userAuditLogService.log(webUser.getSubId(), webUser.getUsername(), webUser.getDisplayName(),
					UserAuditLogConstants.AUDIT_DETAIL_USER_DELETED, user.getUserId());
		}
		loginExDao.deleteUsers(ids, webUser.getSubId());
		// passwordHistoryService.delete(ids);
		log.debug("Users deleted!");
	}

	@Override
	public void unlockUsers(List<String> ids) {
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("No user to be unlocked!");
			return;
		}
		WebUser webUser = AuthUtils.currentUserDetails();
		loginExDao.updateLockedAndLockedDtAndRetryBySubId(ids, webUser.getSubId(), null, false, 0);
		for (int i = 0; i < ids.size(); i++) {
			LoginEx user = findLoginEx(ids.get(i));
			String displayName = null;
			if (user != null)
				displayName = user.getDisplayName();
			userAuditLogService.log(webUser.getSubId(), webUser.getUsername(), webUser.getDisplayName(),
					UserAuditLogConstants.AUDIT_DETAIL_USER_UNLOCKED, user.getUserId());
		}
		log.debug("Users unlocked!");
	}

	@Override
	public int saveUser(LoginEx loginEx, int type) {
		log.debug("Saving user...");
		if (loginEx == null) {
			log.debug("User detail is null, nothing to save...");
			return UserDetailConstants.USERDETAIL_USER_NOT_FOUND;
		}

		WebUser webUser = AuthUtils.currentUserDetails();
		loginEx.setSubId(webUser.getSubId());
		// loginEx.setUserId(loginEx.getUserId());

		int result = validateUser(loginEx, type);
		if (result == UserDetailConstants.USERDETAIL_SAVE_SUCCESS) {

			if ((loginEx.getPassword() != null) && (loginEx.getPassword().length() > 0)) {
				String encodedPassword = passwordEncoder.encode(loginEx.getPassword());
				loginEx.setPassword(encodedPassword);
			}
			if (type == UserDetailConstants.SAVE_TYPE_ADD) {
				log.debug("Adding user...");
				// loginEx.setUserRole(UserRoles.ROLE_NORMAL_USER);
				loginEx.setStatus(AccountConstants.LoginStatus.active.getActualValue());
				loginEx = updateLoginExWithDefaultValues(loginEx);
				loginExDao.save(loginEx);
				/* passwordHistoryService.save(loginEx.getUserId(), loginEx.getPassword()); */
				userAuditLogService.log(webUser.getSubId(), webUser.getUsername(), webUser.getDisplayName(),
						UserAuditLogConstants.AUDIT_DETAIL_USER_ADDED, loginEx.getUserId());

			} else if (type == UserDetailConstants.SAVE_TYPE_MODIFY) {
				log.debug("Modifying user...");
				userAuditLogService.log(webUser.getSubId(), webUser.getUsername(), webUser.getDisplayName(),
						UserAuditLogConstants.AUDIT_DETAIL_USER_MODIFIED, loginEx.getUserId());
				if ((loginEx.getPassword() == null) || (loginEx.getPassword().length() == 0)) {
					loginExDao.updateUserWithoutPasswordByUserIdAndCustomerNo(loginEx.getDisplayName(),
							loginEx.getEmail(), new Date(), loginEx.getUserId(), webUser.getSubId());
				} else {
					/* passwordHistoryService.save(loginEx.getUserId(), loginEx.getPassword()); */
					loginExDao.updateUserWithPasswordByUserIdAndCustomerNo(loginEx.getDisplayName(), loginEx.getEmail(),
							loginEx.getPassword(), false, new Date(), loginEx.getUserId(), webUser.getSubId());
					userAuditLogService.log(webUser.getSubId(), webUser.getUsername(), webUser.getDisplayName(),
							UserAuditLogConstants.AUDIT_DETAIL_PASSWORD_CHANGED, loginEx.getUserId());
				}
			}
		}
		return result;
	}

	private int validateUser(LoginEx loginEx, int type) {
		if (loginEx == null) {
			return UserDetailConstants.USERDETAIL_USER_NOT_FOUND;
		}
		if ((loginEx.getUserId() == null) || (loginEx.getUserId().length() == 0)) {
			return UserDetailConstants.USERDETAIL_EMPTY_USERID;
		}
		if ((loginEx.getDisplayName() == null) || (loginEx.getDisplayName().length() == 0)) {
			return UserDetailConstants.USERDETAIL_EMPTY_DISPLAYNAME;
		}
		if (type == UserDetailConstants.SAVE_TYPE_ADD) {
			int noOfNormalUser = findNoOfNormalUser();
			if (noOfNormalUser >= getMaxNoOfNormalUser()) {
				return UserDetailConstants.USERDETAIL_MAX_NO_OF_USER_REACHED;
			}
			String userIdFromDB = loginExDao.findUserIdByUserId(loginEx.getUserId());
			if (userIdFromDB != null) {
				return UserDetailConstants.USERDETAIL_USER_ALREADY_EXIST;
			}
			if ((loginEx.getPassword() == null) || (loginEx.getPassword().length() == 0)) {
				return UserDetailConstants.USERDETAIL_EMPTY_PASSWORD;
			}
			if ((loginEx.getPasswordConfirm() == null) || (loginEx.getPasswordConfirm().length() == 0)) {
				return UserDetailConstants.USERDETAIL_EMPTY_PASSWORD_CONFIRM;
			}
			if (!loginEx.getPassword().equals(loginEx.getPasswordConfirm())) {
				return UserDetailConstants.USERDETAIL_PASSWORD_NOTMATCH;
			}
			if (!PasswordPolicy.isValid(loginEx.getPassword())) {
				return UserDetailConstants.USERDETAIL_INVALID_PASSWORD;
			}
		}
		if (type == UserDetailConstants.SAVE_TYPE_MODIFY) {
			if ((loginEx.getPassword() == null) || (loginEx.getPassword().length() == 0)) {
				if ((loginEx.getPasswordConfirm() != null) && (loginEx.getPasswordConfirm().length() > 0)) {
					return UserDetailConstants.USERDETAIL_EMPTY_PASSWORD;
				}
			}
			if ((loginEx.getPasswordConfirm() == null) || (loginEx.getPasswordConfirm().length() == 0)) {
				if ((loginEx.getPassword() != null) && (loginEx.getPassword().length() > 0)) {
					return UserDetailConstants.USERDETAIL_EMPTY_PASSWORD_CONFIRM;
				}
			}
			if ((loginEx.getPassword() != null) && (loginEx.getPassword().length() > 0)) {
				if (!loginEx.getPassword().equals(loginEx.getPasswordConfirm())) {
					return UserDetailConstants.USERDETAIL_PASSWORD_NOTMATCH;
				}
				if (!PasswordPolicy.isValid(loginEx.getPassword())) {
					return UserDetailConstants.USERDETAIL_INVALID_PASSWORD;
				}
				WebUser loginedUser = AuthUtils.currentUserDetails();
				if (loginedUser.getUsername().equals(loginEx.getUserId())) {
					if ((loginEx.getOldPassword() == null) || (loginEx.getOldPassword().length() == 0)) {
						return UserDetailConstants.USERDETAIL_OLD_PWD_EMPTY;
					}
				}
			}
		}

		if ((loginEx.getOldPassword() != null) && (loginEx.getOldPassword().length() > 0)) {
			String oldPwdFromDb = loginExDao.findPasswordByUserId(loginEx.getUserId());
			if (!passwordEncoder.matches(loginEx.getOldPassword(), oldPwdFromDb)) {
				return UserDetailConstants.USERDETAIL_OLD_PWD_NOT_MATCH;
			}
		}

		return UserDetailConstants.USERDETAIL_SAVE_SUCCESS;
	}

	@Override
	public LoginEx updateLoginExWithDefaultValues(LoginEx loginEx) {
		if (loginEx == null) {
			loginEx = new LoginEx();
		}
		loginEx.setCreateDt(new Date());
		loginEx.setPwdReset(false);
		loginEx.setRetry(0);
		loginEx.setLocked(false);
		return loginEx;
	}
	
	@Override
	public int changePassword(String oldPassword, String newPassword, String newPassword1) {

		WebUser webUser = AuthUtils.currentUserDetails();
		String userId = webUser.getUsername();
		log.debug("Changing password for user: " + userId);
		
		if ((oldPassword == null) || oldPassword.length() == 0) {
			return UserDetailConstants.CHGPWD_PASSWORD_EMPTY;
		}
		if ((newPassword == null) || newPassword.length() == 0) {
			return UserDetailConstants.CHGPWD_NEWPWD_EMPTY;
		}
		if ((newPassword1 == null) || newPassword1.length() == 0) {
			return UserDetailConstants.CHGPWD_NEWPWD1_EMPTY;
		}
		if (!(newPassword.equals(newPassword1))) {
			return UserDetailConstants.CHGPWD_NEWPWD_NOT_MATCH;
		}
		if ((newPassword.equals(oldPassword))) {
			return UserDetailConstants.CHGPWD_OLD_NEW_PWD_SAME;
		}

		String oldPwdFromDb = loginExDao.findPasswordByUserId(userId);

		if (!passwordEncoder.matches(oldPassword, oldPwdFromDb)) { 			
			return UserDetailConstants.CHGPWD_OLD_PWD_NOT_MATCH;
		}
		
		if (!PasswordPolicy.isValid(newPassword)) {
			return UserDetailConstants.CHGPWD_INVLID_PASSWORD;
		}	
		
		String encodedPassword = passwordEncoder.encode(newPassword);
		loginExDao.updatePasswordByUserId(userId, new Date(), encodedPassword, false);
		userAuditLogService.log(webUser.getSubId(), userId, webUser.getDisplayName(), UserAuditLogConstants.AUDIT_DETAIL_PASSWORD_CHANGED);
		return UserDetailConstants.CHGPWD_SAVE_SUCCESS;

	}

}
