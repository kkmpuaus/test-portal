package com.tradelink.biometric.r2fas.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradelink.biometric.r2fas.portal.dao.SysParamDao;
import com.tradelink.biometric.r2fas.portal.db.model.SysParam;

@Service
public class SysParamServiceImpl implements SysParamService {
	
	@Autowired
	private SysParamDao sysParamRepository;
	
	public String getSysParamValue(String paramName) {
		
		if(paramName !=null && !paramName.isEmpty()) {
			SysParam sysParam = sysParamRepository.findByParamName(paramName);
			if(sysParam != null && !sysParam.getParamValue().isEmpty()) {
				return sysParam.getParamValue();
			}
		}
		return null;
	}
	
	public String getHomePath() {
		return getSysParamValue("tal.dir.base");
	}
	
	public String getArcFacePath() {
		String root = getHomePath();
		String path = getSysParamValue("tal.dir.acrsoft");
		if(root != null && path != null) {
			return root + path;
		}
		return null;
	}
	
	public String getGuestGalleryFile() {
		String root = getHomePath();
		String path = getSysParamValue("tal.dir.gallery.guest");
		if(root != null && path != null) {
			return root + path;
		}
		return null;
	}
	
	public String getStaffGalleryFile() {
		String root = getHomePath();
		String path = getSysParamValue("tal.dir.gallery.staff");
		if(root != null && path != null) {
			return root + path;
		}
		return null;
	}

	public String getVipGalleryFile() {
		String root = getHomePath();
		String path = getSysParamValue("tal.dir.gallery.vip");
		if(root != null && path != null) {
			return root + path;
		}
		return null;
	}
	
	public String getImagesPath() {
		String root = getHomePath();
		String path = getSysParamValue("tal.dir.image");
		if(root != null && path != null) {
			return root + path;
		}
		return null;
	}
	
	public String getThumbnailsPath() {
		String root = getHomePath();
		String path = getSysParamValue("tal.dir.thumbnails");
		if(root != null && path != null) {
			return root + path;
		}
		return null;
	}
	
	public String getWiegandPath() {
		//String root = getHomePath();
		String path = getSysParamValue("tal.dir.wiegand");
		/*
		 * if(root != null && path != null) { return root + path; }
		 */
		if(path != null) {
			return path;
		}
		return null;
	}
	
	public String getROCLicenseFile() {
		String root = getHomePath();
		String path = getSysParamValue("tal.dir.license");
		if(root != null && path != null) {
			return root + path;
		}
		return null;
	}
	
	public String getArcFaceLicenseFile() {
		String root = getHomePath();
		String path = getSysParamValue("tal.dir.license.arcface");
		if(root != null && path != null) {
			return root + path;
		}
		return null;
	}
	
	public String getArcFaceAppID() {
		return getSysParamValue("tal.face.liveness.appId");
	}
	
	public String getArcFaceKey() {
		return getSysParamValue("tal.face.liveness.sdkKey");
	}
	
	public boolean isEnableLiveness() {
		String isEnableLivenessStr = getSysParamValue("tal.face.liveness");
		if (isEnableLivenessStr != null) {
			if (isEnableLivenessStr.equals("ON")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEnableThumbNailRemove() {
		String isEnableThumbNailRemoveStr = getSysParamValue("tal.face.thumbnail.remove");
		if (isEnableThumbNailRemoveStr != null) {
			if (isEnableThumbNailRemoveStr.equals("ON")) {
				return true;
			}
		}
		return false;
	}
	
	public int getTimeLock() {
		return Integer.parseInt(getSysParamValue("tal.face.liveness.timecamlock"));
	}
	
	public Long getMinFaceSizeAbs() {
		return Long.parseLong(getSysParamValue("tal.face.min.size.abs"));
	}
	
	public float getMinFaceSizeRel() {
		return Float.parseFloat(getSysParamValue("tal.face.min.size.rel"));
	}
	
	public float getMinFaceSimilarity() {
		return Float.parseFloat(getSysParamValue("tal.face.min.similarity"));
	}
	
	public float getVideoFps() {
		return Float.parseFloat(getSysParamValue("tal.video.fps"));
	}
	
	public String getLoginRetryMax() {
		return getSysParamValue("tal.portal.login.retry.max");
	}
	
	public String getLoginRetryReminder() {
		return getSysParamValue("tal.portal.login.retry.reminder");
	}
	
	public String getMaxNoNormalUser() {
		return getSysParamValue("tal.portal.max.normal.user");
	}

	public String getRegUrl() {
		return getSysParamValue("tal.endpoint.reg");
	}
	
	public String getDeregUrl() {
		return getSysParamValue("tal.endpoint.dereg");
	}


}
