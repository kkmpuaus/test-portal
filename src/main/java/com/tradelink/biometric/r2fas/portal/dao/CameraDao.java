package com.tradelink.biometric.r2fas.portal.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.tradelink.biometric.r2fas.portal.constant.CameraStatus;
import com.tradelink.biometric.r2fas.portal.db.model.Camera;


public interface CameraDao extends JpaRepository<Camera, Long> {

	@Query(
			value ="select NEW com.tradelink.biometric.r2fas.portal.db.model.Camera(c.id, c.url, c.cameraName, c.minFaceSize, c.minLiveness, c.cameraStatus) from Camera c"
			)
	Page<Camera> findAll(
            Pageable pageable
        );
	
	public Camera findById(Long id);
	
	@Modifying
	@Query("delete from Camera c where c.id in :ids")
	public void deleteCameras(@Param("ids") List<Long> ids);
	
	@Modifying
	@Query("update Camera c set c.cameraStatus = :cameraStatus where c.id in :ids")
	public void updateCameraStatusById(
			@Param("ids") List<Long> ids,
			@Param("cameraStatus") CameraStatus cameraStatus
			);
	

	
}
