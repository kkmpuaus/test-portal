package com.tradelink.biometric.r2fas.portal.constant;

public class UserDetailConstants {

	public static final int TOTAL_NO_OF_CHGPWD_ERROR = 8;
	
	public static final int CHGPWD_SAVE_SUCCESS = 0;
	public static final int CHGPWD_PASSWORD_EMPTY = 1;
	public static final int CHGPWD_NEWPWD_EMPTY = 2;
	public static final int CHGPWD_NEWPWD1_EMPTY = 3;
	public static final int CHGPWD_NEWPWD_NOT_MATCH = 4;
	public static final int CHGPWD_OLD_PWD_NOT_MATCH = 5;
	public static final int CHGPWD_INVLID_PASSWORD = 6;
	public static final int CHGPWD_PASSWORD_USED = 7;
	public static final int CHGPWD_OLD_NEW_PWD_SAME = 8;

	public static final int TOTAL_NO_OF_USERDETAIL_ERROR = 17;
	
	public static final int USERDETAIL_SAVE_SUCCESS = 0;
	public static final int USERDETAIL_EMPTY_USERID = 1;
	public static final int USERDETAIL_EMPTY_DISPLAYNAME = 3;
	public static final int USERDETAIL_EMPTY_PASSWORD = 4;
	public static final int USERDETAIL_EMPTY_PASSWORD_CONFIRM = 5;
	public static final int USERDETAIL_PASSWORD_NOTMATCH = 6;
	public static final int USERDETAIL_INVALID_PASSWORD = 7;
	public static final int USERDETAIL_PASSWORD_USED = 8;
	public static final int USERDETAIL_SAME_PASSWORD = 9;
	public static final int USERDETAIL_USER_NOT_FOUND = 10;
	public static final int USERDETAIL_USER_ALREADY_EXIST = 11;
	public static final int USERDETAIL_MAX_NO_OF_USER_REACHED = 12;
	public static final int USERDETAIL_AT_LEAST_ONE_STMT_ALERT = 13;
	public static final int USERDETAIL_AT_LEAST_ONE_INV_ALERT = 14;
	public static final int USERDETAIL_AT_LEAST_ONE_REMINDER_ALERT = 15;
	public static final int USERDETAIL_OLD_PWD_NOT_MATCH = 16;
	public static final int USERDETAIL_OLD_PWD_EMPTY = 17;

	public static final int SAVE_TYPE_ADD = 0;
	public static final int SAVE_TYPE_MODIFY = 1;

	public static final String NOTIFICATION_ON = "Y";
	public static final String NOTIFICATION_OFF = "N";

}
