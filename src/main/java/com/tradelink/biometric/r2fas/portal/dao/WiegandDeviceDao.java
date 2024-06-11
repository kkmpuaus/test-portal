package com.tradelink.biometric.r2fas.portal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tradelink.biometric.r2fas.portal.db.model.Camera;
import com.tradelink.biometric.r2fas.portal.db.model.WiegandDevice;

public interface WiegandDeviceDao extends JpaRepository<WiegandDevice, Long> {

	public List<WiegandDevice> findByCamera(Camera camera);
	
	public List<WiegandDevice> findByCameraId(Long cameraId);
	
	public WiegandDevice findById(Long id);
	
	@Modifying
	@Query("delete from WiegandDevice w where w.id = :id")
	public void deleteWiegandDevice(@Param("id") Long id);
	
	/*
	 * @Modifying
	 * 
	 * @Query("delete from WiegandDevice w where w.camera_id in :cameraIds") public
	 * void deleteWiegandDevices(@Param("cameraIds") List<Long> cameraIds);
	 */
}
