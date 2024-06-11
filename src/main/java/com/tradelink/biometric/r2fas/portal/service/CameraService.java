package com.tradelink.biometric.r2fas.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tradelink.biometric.r2fas.portal.db.model.Camera;
import com.tradelink.biometric.r2fas.portal.db.model.WiegandDevice;

public interface CameraService {

	public Page<Camera> findCameraList(Camera camera, Pageable pageable);
	
	public Camera findCameraById(Long id);
	
	public int saveCamera(Camera camera, String[] wiegandIps,String[] wiegandPorts, String[] wiegandDescs);
	
	public int updateCamera(Camera camera, String[] wiegandIds, String[] wiegandIps,String[] wiegandPorts, String[] wiegandDescs);

	public List<WiegandDevice> findWiegandDeviceListByCameraId(Camera camera);
	
	public boolean deleteCameras(List<Long> ids);
	
	public void deactiveCameras( List<Long> ids);
	
	public void activeCameras( List<Long> ids);
	
}
