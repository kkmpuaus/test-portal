package com.tradelink.biometric.r2fas.portal.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tradelink.biometric.r2fas.portal.constant.CameraDetailConstants;
import com.tradelink.biometric.r2fas.portal.constant.UserDetailConstants;
import com.tradelink.biometric.r2fas.portal.db.model.Camera;
import com.tradelink.biometric.r2fas.portal.db.model.WiegandDevice;
import com.tradelink.biometric.r2fas.portal.security.model.WebUser;
import com.tradelink.biometric.r2fas.portal.service.CameraService;
import com.tradelink.biometric.r2fas.portal.utils.AuthUtils;

@Controller
@RequestMapping("/")
public class CameraController {

	private Log log = LogFactory.getLog(this.getClass());

	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final String[] ORDER_BY_FIELDS_CAMERA = { "id", "url", "cameraName", "cameraStatus" };
	private static final String DEFAULT_ORDER_BY_CAMERA = "id";

	@Autowired
	CameraService cameraService;

	@RequestMapping(value = "/camera")
	public String searchFaceHistoryList(Map<String, Object> model,
			@RequestParam(value = "pageSize", required = false) Integer pageSizeInt,
			@RequestParam(value = "pageNo", required = false) Integer pageNoInt,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "ordering", required = false) String ordering) {

		log.debug("Searching camera ...");
		WebUser webUser = AuthUtils.currentUserDetails();

		int pageNo = 1;
		if (pageNoInt != null) {
			pageNo = pageNoInt.intValue();
		}
		int pageSize = DEFAULT_PAGE_SIZE;
		if (pageSizeInt != null) {
			pageSize = pageSizeInt.intValue();
		}

		if (StringUtils.isEmpty(orderBy)) {
			orderBy = DEFAULT_ORDER_BY_CAMERA;
		} else {
			boolean isValid = false;
			for (String f : ORDER_BY_FIELDS_CAMERA) {
				if (f.equals(orderBy)) {
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				orderBy = DEFAULT_ORDER_BY_CAMERA;
			}
		}
		if (StringUtils.isEmpty(ordering)) {
			ordering = Sort.Direction.DESC.toString();
		}
		log.debug("User ID:" + webUser.getUsername() + " getting camera page, pageNo: " + pageNo + ", pageSize: "
				+ pageSize + ", ordering: " + ordering + ", orderBy: " + orderBy);
		Pageable pageable = new PageRequest(pageNo - 1, pageSize, Sort.Direction.fromString(ordering), orderBy);

		Camera criteria = new Camera();
		/*
		 * if(!StringUtils.isEmpty(cardNumber)) { criteria.setCardNumber(cardNumber); }
		 */

		Page<Camera> result = cameraService.findCameraList(criteria, pageable);
		List<Camera> profiles = null;
		if (result != null) {
			profiles = result.getContent();
			int currentPage = result.getNumber() + 1;
			model.put("resultList", profiles);
			model.put("totalPageNo", (result.getTotalPages() == 0 ? 1 : result.getTotalPages()));
			model.put("pageNo", currentPage);
			model.put("pageSize", result.getSize());
			model.put("totalNo", result.getTotalElements());
			Iterator<Order> currectOrder = result.getSort().iterator();
			if (currectOrder.hasNext()) {
				Order order = currectOrder.next();
				model.put("orderBy", order.getProperty());
				model.put("ordering", order.getDirection().toString());
			}
		}
		model.put("menuCamera", "active");
		log.debug("Searching camera finished!");
		return "camera";
	}

	@RequestMapping(value = "/newcamera", method = RequestMethod.GET)
	public String showAddCameraPage(Map<String, Object> model) {
		if (this.log.isDebugEnabled())
			this.log.debug("display add camera page ...");
		return showCameraDetailPage(model, null, CameraDetailConstants.SAVE_TYPE_ADD);
	}

	@RequestMapping(value = "/modifycamera", method = RequestMethod.POST)
	public String showModifyCameraPage(Map<String, Object> model, @RequestParam(value = "id") Long id) {
		if (this.log.isDebugEnabled())
			this.log.debug("display modify camera page ...");
		return showCameraDetailPage(model, cameraService.findCameraById(id), UserDetailConstants.SAVE_TYPE_MODIFY);
	}

	private String showCameraDetailPage(Map<String, Object> model, Camera camera, int showType) {

		List<WiegandDevice> wiegandList = cameraService.findWiegandDeviceListByCameraId(camera);
		if (showType == UserDetailConstants.SAVE_TYPE_MODIFY) {
			if(wiegandList.size() == 1) {
				WiegandDevice tmp = new WiegandDevice();
				wiegandList.add(tmp);
			}
			if(wiegandList.size() == 0) {
				WiegandDevice tmp = new WiegandDevice();
				wiegandList.add(tmp);
				WiegandDevice tmp1 = new WiegandDevice();
				wiegandList.add(tmp1);
			}
		}

		model.put("wiegandList", wiegandList);
		model.put("camera", camera);
		model.put("menuFaceUser", "active");
		model.put("saveType", showType);
		model.put("isModify", CameraDetailConstants.SAVE_TYPE_MODIFY == showType);
		model.put("totalNoOfErrMsg", CameraDetailConstants.TOTAL_NO_OF_CAMERADETAIL_ERROR);
		return "cameradetail";
	}

	@RequestMapping(value = "/addcamera", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public int addCamera(@ModelAttribute Camera camera, @RequestParam(value = "wiegandIps") String[] wiegandIps,
			@RequestParam(value = "wiegandPorts") String[] wiegandPorts,
			@RequestParam(value = "wiegandDescs") String[] wiegandDescs) {
		if (this.log.isDebugEnabled())
			this.log.debug("Adding camera...");
		if (camera == null) {
			log.debug("camera is null.... unexpteded!");
			return CameraDetailConstants.CAMERADETAIL_CAMERA_NOT_FOUND;
		}

		return cameraService.saveCamera(camera, wiegandIps, wiegandPorts, wiegandDescs);
	}

	@RequestMapping(value = "/editcamera", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public int editCamera(@ModelAttribute Camera camera, @RequestParam(value = "wiegandIds") String[] wiegandIds,
			@RequestParam(value = "wiegandIps") String[] wiegandIps,
			@RequestParam(value = "wiegandPorts") String[] wiegandPorts,
			@RequestParam(value = "wiegandDescs") String[] wiegandDescs) {
		if (this.log.isDebugEnabled())
			this.log.debug("Editing camera...");
		if (camera == null) {
			log.debug("camera is null.... unexpteded!");
			return CameraDetailConstants.CAMERADETAIL_CAMERA_NOT_FOUND;
		}

		return cameraService.updateCamera(camera, wiegandIds, wiegandIps, wiegandPorts, wiegandDescs);
	}

	@RequestMapping(value = "/deactivecamera", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public boolean deactiveFaceUser(Map<String, Object> model, @RequestParam(value = "id") List<Long> ids) {
		log.debug("Deactive user ...");
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("Nothing to be deactived!");
			return true;
		}
		cameraService.deactiveCameras(ids);
		log.info("Deactive face user finish!");
		return true;
	}

	@RequestMapping(value = "/activecamera", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public boolean activeFaceUser(Map<String, Object> model, @RequestParam(value = "id") List<Long> ids) {
		log.debug("Active user ...");
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("Nothing to be actived!");
			return true;
		}
		cameraService.activeCameras(ids);
		log.info("active face user finish!");
		return true;
	}

	@RequestMapping(value = "/deletecamera", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public boolean deleteCamera(Map<String, Object> model, @RequestParam(value = "id") List<Long> ids) {
		log.debug("Delete Camera ...");
		if ((ids == null) || (ids.size() == 0)) {
			log.debug("Nothing to be actived!");
			return true;
		}
		;
		log.info("delete camera finish!");
		return cameraService.deleteCameras(ids);
	}

}