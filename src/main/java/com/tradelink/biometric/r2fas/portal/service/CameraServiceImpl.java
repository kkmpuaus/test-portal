package com.tradelink.biometric.r2fas.portal.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tradelink.biometric.r2fas.portal.constant.CameraDetailConstants;
import com.tradelink.biometric.r2fas.portal.constant.CameraStatus;
import com.tradelink.biometric.r2fas.portal.dao.CameraDao;
import com.tradelink.biometric.r2fas.portal.dao.WiegandDeviceDao;
import com.tradelink.biometric.r2fas.portal.db.model.Camera;
import com.tradelink.biometric.r2fas.portal.db.model.WiegandDevice;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;
import com.tradelink.biometric.r2fas.portal.utils.StringUtils;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CameraServiceImpl implements CameraService {

	private Log log = LogFactory.getLog(this.getClass());

	private static String urlFormat = "^(http|https|rtsp)://.*$";
	private static String ipFormat = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
	private static String portFormat = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";

	@Autowired
	private CameraDao cameraDao;

	@Autowired
	private WiegandDeviceDao wiegandDeviceDao;

	@Override
	public Page<Camera> findCameraList(Camera camera, Pageable pageable) {
		WebUser webUser = AuthUtils.currentUserDetails();
		Page<Camera> page = null;
		log.debug("Start finding Camera for Sub ID: " + webUser.getSubId());
		page = cameraDao.findAll(pageable);
		log.debug("Finish finding Face History" + webUser.getSubId());
		return page;
	}

	@Override
	public Camera findCameraById(Long id) {
		log.debug("Find camera for modifying");
		Camera camera = cameraDao.findById(id);
		return camera;
	}

	@Override
	public int saveCamera(Camera camera, String[] wiegandIps, String[] wiegandPorts, String[] wiegandDescs) {

		log.debug("Save camera");

		if (StringUtils.isNullOrEmpty(camera.getUrl())) {
			log.debug("Empty camera url");
			return CameraDetailConstants.CAMERADETAIL_EMPTY_URL;
		}

		if (!camera.getUrl().matches(urlFormat)) {
			log.debug("Invalid camera url");
			return CameraDetailConstants.CAMERADETAIL_INVALID_URL;
		}

		if (camera.getMinFaceSize() == null) {
			log.debug("Empty face size");
			return CameraDetailConstants.CAMERADETAIL_EMPTY_FACESIZE;
		}

		if (camera.getMinFaceSize() < 20) {
			log.debug("Inavalid face size");
			return CameraDetailConstants.CAMERADETAIL_INVALID_FACESIZE;
		}

		if (camera.getMinLiveness() < 0 || camera.getMinLiveness() > 1) {
			log.debug("Invalid liveness");
			return CameraDetailConstants.CAMERADETAIL_INVALID_LIVENESS;
		}

		boolean errFound = false;
		for (int i = 0; i < wiegandIps.length; i++) {
			if (StringUtils.isNullOrEmpty(wiegandIps[i])) {
				continue;
			}

			if (!wiegandIps[i].matches(ipFormat)) {
				log.debug("Invalid wiegand ip");
				errFound = true;
				return CameraDetailConstants.CAMERADETAIL_INVALID_WIEGANDIP;
			}

			if (StringUtils.isNullOrEmpty(wiegandPorts[i])) {
				log.debug("Empty wiegand port");
				errFound = true;
				return CameraDetailConstants.CAMERADETAIL_EMPTY_WIEGANDPORT;
			}

			if (!String.valueOf(wiegandPorts[i]).matches(portFormat)) {
				log.debug("Invalid wiegand port");
				errFound = true;
				return CameraDetailConstants.CAMERADETAIL_INVALID_WIEGANDPORT;
			}
		}

		if (!errFound) {
			camera.setCameraStatus(CameraStatus.ACTIVE);
			camera = cameraDao.save(camera);

			for (int i = 0; i < wiegandIps.length; i++) {
				if (StringUtils.isNullOrEmpty(wiegandIps[i])) {
					continue;
				}
				WiegandDevice wiegandDevice = new WiegandDevice();
				wiegandDevice.setIp(wiegandIps[i]);
				wiegandDevice.setPort(Integer.parseInt(wiegandPorts[i]));
				wiegandDevice.setDescription(wiegandDescs[i]);
				wiegandDevice.setCamera(camera);
				wiegandDeviceDao.save(wiegandDevice);
			}
		}

		return CameraDetailConstants.CAMERADETAIL_SAVE_SUCCESS;
	}

	@Override
	public int updateCamera(Camera camera, String[] wiegandIds, String[] wiegandIps, String[] wiegandPorts,
			String[] wiegandDescs) {
		log.debug("Update camera");

		if (StringUtils.isNullOrEmpty(camera.getUrl())) {
			log.debug("Empty camera url");
			return CameraDetailConstants.CAMERADETAIL_EMPTY_URL;
		}

		if (!camera.getUrl().matches(urlFormat)) {
			log.debug("Invalid camera url");
			return CameraDetailConstants.CAMERADETAIL_INVALID_URL;
		}

		if (camera.getMinFaceSize() == null) {
			log.debug("Empty face size");
			return CameraDetailConstants.CAMERADETAIL_EMPTY_FACESIZE;
		}

		if (camera.getMinFaceSize() < 20) {
			log.debug("Inavalid face size");
			return CameraDetailConstants.CAMERADETAIL_INVALID_FACESIZE;
		}

		if (camera.getMinLiveness() < 0 || camera.getMinLiveness() > 1) {
			log.debug("Invalid liveness");
			return CameraDetailConstants.CAMERADETAIL_INVALID_LIVENESS;
		}

		boolean errFound = false;
		for (int i = 0; i < wiegandIps.length; i++) {
			if (StringUtils.isNullOrEmpty(wiegandIps[i])) {
				continue;
			}

			if (!wiegandIps[i].matches(ipFormat)) {
				log.debug("Invalid wiegand ip");
				errFound = true;
				return CameraDetailConstants.CAMERADETAIL_INVALID_WIEGANDIP;
			}

			if (StringUtils.isNullOrEmpty(wiegandPorts[i])) {
				log.debug("Empty wiegand port");
				errFound = true;
				return CameraDetailConstants.CAMERADETAIL_EMPTY_WIEGANDPORT;
			}

			if (!String.valueOf(wiegandPorts[i]).matches(portFormat)) {
				log.debug("Invalid wiegand port");
				errFound = true;
				return CameraDetailConstants.CAMERADETAIL_INVALID_WIEGANDPORT;
			}
		}

		if (!errFound) {
			camera = cameraDao.save(camera);
			for (int i = 0; i < wiegandIds.length; i++) {

				// new record
				if (StringUtils.isNullOrEmpty(wiegandIds[i])) {
					if (StringUtils.isNullOrEmpty(wiegandIps[i])) {
						continue;
					}
					WiegandDevice wiegandDevice = new WiegandDevice();
					wiegandDevice.setIp(wiegandIps[i]);
					wiegandDevice.setPort(Integer.parseInt(wiegandPorts[i]));
					if (wiegandDescs[i] != null) {
						wiegandDevice.setDescription(wiegandDescs[i]);
					}
					wiegandDevice.setCamera(camera);
					wiegandDeviceDao.save(wiegandDevice);

					// exist record
				} else {
					log.debug("sql:" + wiegandIds[i]);
					WiegandDevice wiegandDevice = wiegandDeviceDao.findById(Long.parseLong(wiegandIds[i]));

					if (StringUtils.isNullOrEmpty(wiegandIps[i])) {
						log.debug("try delete:" + wiegandIds[i]);
						wiegandDeviceDao.deleteWiegandDevice(Long.parseLong(wiegandIds[i]));
						continue;
					}
					wiegandDevice.setIp(wiegandIps[i]);
					wiegandDevice.setPort(Integer.parseInt(wiegandPorts[i]));

					if (wiegandDescs[i] != null) {
						wiegandDevice.setDescription(wiegandDescs[i]);
					}
					wiegandDevice.setCamera(camera);
					wiegandDeviceDao.save(wiegandDevice);
				}

			}
		}
		return CameraDetailConstants.CAMERADETAIL_SAVE_SUCCESS;

	}

	@Override
	public List<WiegandDevice> findWiegandDeviceListByCameraId(Camera camera) {
		return wiegandDeviceDao.findByCamera(camera);
	}

	@Override
	public boolean deleteCameras(List<Long> ids) {
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("No camera to be deleted!");
			return false;
		}

		for (Long id : ids) {
			List<WiegandDevice> wieganddevices = wiegandDeviceDao.findByCameraId(id);
			for (WiegandDevice w : wieganddevices) {
				wiegandDeviceDao.delete(w);
			}
		}
		cameraDao.deleteCameras(ids);

		log.debug("Face Users deleted!");
		return true;
	}

	@Override
	public void deactiveCameras(List<Long> ids) {
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("No camera to be deactived!");
			return;
		}
		cameraDao.updateCameraStatusById(ids, CameraStatus.DEACTIVE);
		log.debug("Cameras deactived!");
	}

	@Override
	public void activeCameras(List<Long> ids) {
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("No camera to be actived!");
			return;
		}
		cameraDao.updateCameraStatusById(ids, CameraStatus.ACTIVE);
		log.debug("Face Users actived!");
	}

}
