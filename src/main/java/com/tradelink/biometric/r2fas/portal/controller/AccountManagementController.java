package com.tradelink.biometric.r2fas.portal.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tradelink.biometric.r2fas.portal.constant.UserDetailConstants;
import com.tradelink.biometric.r2fas.portal.db.model.LoginEx;
import com.tradelink.biometric.r2fas.portal.security.model.UserRoles;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.service.LoginService;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;

@Controller
@RequestMapping("/")
public class AccountManagementController {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private LoginService loginService;
	
	@PreAuthorize("hasRole('"+UserRoles.ROLE_ADMIN+"')")
	@RequestMapping(value = "/accountmanage")
	public String processAccountManagement(Map<String, Object> model) {
		if (this.log.isDebugEnabled())
			this.log.debug("display Account Management page ...");
		model.put("menuAccountManage", "active");
		List<LoginEx> userList = loginService.findUserList();
		int noOfNormalUser = loginService.findNoOfNormalUser();
		model.put("enableAdd", (noOfNormalUser < loginService.getMaxNoOfNormalUser()));
		model.put("userList", userList);
		model.put("maxNormalUser", loginService.getMaxNoOfNormalUser());
		return "accountmanage";
	}
	
	
	
	@PreAuthorize("hasRole('"+UserRoles.ROLE_ADMIN+"')")
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public boolean deleteUser(Map<String, Object> model,
			@RequestParam(value = "userId") List<String> ids) {
		log.debug("delete user ...");
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("Nothing to be deleted!");
			return true;
		}
		loginService.deleteUsers(ids);
		log.info("Delete user finish!");
		return true;
	}
	
	@PreAuthorize("hasRole('"+UserRoles.ROLE_ADMIN+"')")
	@RequestMapping(value = "/unlockuser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public boolean unlockUser(Map<String, Object> model,
			@RequestParam(value = "userId") List<String> ids) {
		log.debug("unlock user ...");
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("Nothing to be unlocked!");
			return true;
		}
		loginService.unlockUsers(ids);
		log.info("Unlock user finish!");
		return true;
	}
	
	@PreAuthorize("hasRole('"+UserRoles.ROLE_ADMIN+"')")
	@RequestMapping(value = "/adduser",  method = RequestMethod.GET)
	public String showAddUserPage(Map<String, Object> model) {
		if (this.log.isDebugEnabled())
			this.log.debug("display add user page ...");
		return showUserDetailPage(model, null, UserDetailConstants.SAVE_TYPE_ADD);
	}

	@PreAuthorize("hasRole('"+UserRoles.ROLE_ADMIN+"')")
	@RequestMapping(value = "/adduser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public int addUser(Map<String, Object> model, LoginEx loginEx) {
		if (this.log.isDebugEnabled())
			this.log.debug("Adding user...");
		
		if (loginEx == null) {
			log.debug("LoginEx is null.... unexpteded!");
			return UserDetailConstants.USERDETAIL_USER_NOT_FOUND;
		}
		model.put("menuAccountManage", "active");
		return loginService.saveUser(loginEx, UserDetailConstants.SAVE_TYPE_ADD);
	}

	@PreAuthorize("hasRole('"+UserRoles.ROLE_ADMIN+"')")
	@RequestMapping(value = "/edituser",  method = RequestMethod.POST)
	public String showModifyUserPage(Map<String, Object> model, @RequestParam(value = "userId") String id) {
		if (this.log.isDebugEnabled())
			this.log.debug("display modify user page ...");
		return showUserDetailPage(model, loginService.findUserForModify(id),
				UserDetailConstants.SAVE_TYPE_MODIFY);
	}
	
	private String showUserDetailPage(Map<String, Object> model, LoginEx user, int showType) {
		if (user != null) {
			WebUser webUser = AuthUtils.currentUserDetails();
			if (webUser.getUsername().equals(user.getUserId())) {
				model.put("oldPwdChk", true);
			}
		}
		model.put("user", user);
		model.put("menuAccountManage", "active");
		model.put("maxNoOfNormalUser", loginService.getMaxNoOfNormalUser());
		model.put("saveType", showType);
		model.put("isModify", UserDetailConstants.SAVE_TYPE_MODIFY == showType);
		model.put("totalNoOfErrMsg", UserDetailConstants.TOTAL_NO_OF_USERDETAIL_ERROR);
		return "userdetail";		
	}

	@PreAuthorize("hasRole('"+UserRoles.ROLE_ADMIN+"')")
	@RequestMapping(value = "/edituser", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public int modifyUser(Map<String, Object> model, LoginEx loginEx) {
		if (this.log.isDebugEnabled())
			this.log.debug("Modifying user...");
		
		if (loginEx == null) {
			log.debug("LoginEx is null.... unexpteded!");
			return UserDetailConstants.USERDETAIL_USER_NOT_FOUND;
		}
		model.put("menuAccountManage", "active");
		return loginService.saveUser(loginEx, UserDetailConstants.SAVE_TYPE_MODIFY);
	}
	
	@RequestMapping(value = "/changepwd", method = RequestMethod.GET)
	public String processChangePwd(Map<String, Object> model) {
		if (this.log.isDebugEnabled())
			this.log.debug("display change password page ...");
		model.put("totalNoOfError", UserDetailConstants.TOTAL_NO_OF_CHGPWD_ERROR);
		return "changepwd";
	}
	
	@RequestMapping(value = "/changepwd", method = RequestMethod.POST, produces={"application/json"})
	@ResponseBody
	public int changePassword(Map<String, Object> model, @RequestParam(value = "oldpwd") String oldPassword,
			@RequestParam(value = "newpwd") String newPassword, @RequestParam(value = "newpwd1") String newPassword1) {
		log.info("Change User Password");
		return loginService.changePassword(oldPassword, newPassword, newPassword1);
	}



}
