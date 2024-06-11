package com.tradelink.biometric.r2fas.portal.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.tradelink.biometric.r2fas.portal.constant.CameraStatus;

@Entity
@Table(name = "camera")
@Configurable(autowire = Autowire.BY_TYPE)
public class Camera implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "url", length = 100)
	@NotNull
	private String url;
	
	@Column(name = "cameraName", length = 100)
	@NotNull
	private String cameraName;

	@Column(name = "min_face_size")
	@NotNull
	private Long minFaceSize;
	
	@Column(name = "min_liveness")
	@NotNull
	private float minLiveness;

	@Column(name = "status", length = 10)
	@NotNull
	@Enumerated(EnumType.STRING)
	private CameraStatus cameraStatus;
	
	@Transient
	private String ip1, ip2;
	
	@Transient
	private String port1, port2;
	
	@Transient
	private String desc1, desc2;

	@PrePersist
	private void prePersist() {
		this.cameraStatus = CameraStatus.ACTIVE;
	}
	
	@PostPersist
	private void postPersist() {
	}
	
	public Camera() {
		
	}
	
	public Camera(Long id, String url, String cameraName, Long minFaceSize, float minLiveness, CameraStatus cameraStatus) {
		this.id = id;
		this.url = url;
		this.cameraName = cameraName;
		this.minFaceSize = minFaceSize;
		this.minLiveness = minLiveness;
		this.cameraStatus = cameraStatus;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getMinFaceSize() {
		return minFaceSize;
	}

	public void setMinFaceSize(Long minFaceSize) {
		this.minFaceSize = minFaceSize;
	}

	public CameraStatus getCameraStatus() {
		return cameraStatus;
	}

	public void setCameraStatus(CameraStatus cameraStatus) {
		this.cameraStatus = cameraStatus;
	}
	
	public float getMinLiveness() {
		return minLiveness;
	}

	public void setMinLiveness(float minLiveness) {
		this.minLiveness = minLiveness;
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public String getIp1() {
		return ip1;
	}

	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}

	public String getIp2() {
		return ip2;
	}

	public void setIp2(String ip2) {
		this.ip2 = ip2;
	}

	public String getPort1() {
		return port1;
	}

	public void setPort1(String port1) {
		this.port1 = port1;
	}

	public String getPort2() {
		return port2;
	}

	public void setPort2(String port2) {
		this.port2 = port2;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

}
