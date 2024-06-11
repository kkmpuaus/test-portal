package com.tradelink.biometric.r2fas.portal.service;

import java.util.List;

import com.tradelink.biometric.r2fas.portal.db.model.LoginEx;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;

public interface LoginService {

	public LoginEx findLoginEx(String userId);
	public int loginFail(String userId, String reason);
	public void loginSuccess(WebUser webUser, String loginIp);
	public void logout(String userId);
	public List<LoginEx> findUserList();
	public int findNoOfNormalUser();
	public int getMaxNoOfNormalUser();
	public LoginEx findUserForModify(String userId);
	public void deleteUsers(List<String> ids);
	public void unlockUsers(List<String> ids);
	public int saveUser(LoginEx loginEx, int type);
	public LoginEx updateLoginExWithDefaultValues(LoginEx loginEx);
	public int changePassword(String oldPassword, String newPassword, String newPassword1);

}
