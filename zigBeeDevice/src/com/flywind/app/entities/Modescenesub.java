package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modescenesub entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modescenesub")
public class Modescenesub implements java.io.Serializable,com.flywind.app.data.Intersub {

	// Fields

	private long id;
	private Long mid;
	private Long houseId;
	private Long deviceId;
	private long deviceOldId;  
	private int actlibId;
	private Long transTime;	
    private Long roomId;
	private String deviceName;	
	private String groupName; 	
    private Long dataType;
    private String scenePara;
    private String para; 
    private String status; 
	// Constructors

	/** default constructor */
	public Modescenesub() {
	}

	/** full constructor */
	public Modescenesub(long id,long mid,long houseId, Long deviceId,long deviceOldId, int actlibId,Long transTime,String scenePara,String para) {
		this.id=id;
		this.mid = mid;
		this.houseId=houseId;
		this.deviceId = deviceId;
		this.deviceOldId = deviceOldId;
		this.actlibId = actlibId;
		this.transTime=transTime;
		this.scenePara=scenePara;
		this.para=para;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	@Column(name = "MID")
	public Long getMid() {
		return this.mid;
	}

	@Override
	public void setMid(Long mid) {
		this.mid = mid;
	}

	@Column(name = "HouseID")	
	public Long getHouseId() {
		return houseId;
	}

	@Override
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	@Column(name = "DeviceID")
	public Long getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "actlibID")
	public int getActlibId() {
		return this.actlibId;
	}

	public void setActlibId(int actlibId) {
		this.actlibId = actlibId;
	}

	@Column(name = "TransTime")
	
    public Long getTransTime() {
		return transTime;
	}

	public void setTransTime(Long transTime) {
		this.transTime = transTime;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getDataType() {
		return dataType;
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}

	public String getScenePara() {
		return scenePara;
	}

	public void setScenePara(String scenePara) {
		this.scenePara = scenePara;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
	
	@Column(name="deviceOldId")
	public long getDeviceOldId() {
		return deviceOldId;
	}

	public void setDeviceOldId(long deviceOldId) {
		this.deviceOldId = deviceOldId;
	}

	
}