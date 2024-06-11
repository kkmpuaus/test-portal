package com.tradelink.biometric.r2fas.portal.service;

public interface SysParamService {

	public String getSysParamValue(String paramName);
	
	public String getHomePath();
	
	public String getArcFacePath();
	
	public String getGuestGalleryFile();
	
	public String getStaffGalleryFile();

	public String getVipGalleryFile();
	
	public String getImagesPath();
	
	public String getThumbnailsPath();
	
	public String getWiegandPath();
	
	public String getROCLicenseFile();
	
	public String getArcFaceLicenseFile();
	
	public String getArcFaceAppID();
	
	public String getArcFaceKey();
	
	public boolean isEnableLiveness();
	
	public boolean isEnableThumbNailRemove();
	
	public int getTimeLock();
	
	public Long getMinFaceSizeAbs();
	
	public float getMinFaceSizeRel();
	
	public float getMinFaceSimilarity();
	
	public float getVideoFps();
	
	public String getLoginRetryMax();
	
	public String getLoginRetryReminder();
	
	public String getMaxNoNormalUser();
	
	public String getRegUrl();
	
	public String getDeregUrl();

}
