package com.flywind.app.entities;

import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity

public class Modemacroproc  extends Modemacrosub{

	private Long roomId;
	private String deviceName;
	private String uniqueName;
	private Long dataType;
	private String uniqueNameEN;
	private Long actExtentionSet;
	@Transient
 	public Long getActExtentionSet() {
 		return actExtentionSet;
 	}

 	public void setActExtentionSet(Long actExtentionSet) {
 		this.actExtentionSet = actExtentionSet;
 	}
	public String getUniqueNameEN() {
		return uniqueNameEN;
	}

	public void setUniqueNameEN(String uniqueNameEN) {
		this.uniqueNameEN = uniqueNameEN;
	}

	public Modemacroproc(){		
	}
	
	public Modemacroproc(long mid, long houseId, String name, long delaySec,
			String act, String destType, long dest, long destOldId,String para, int actlibId,Long actExtentionSet) {
		super(mid, houseId, name, delaySec, act, destType, dest,destOldId, para, actlibId);
		// TODO Auto-generated constructor stub
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
	
	
	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public Long getDataType() {
		return dataType;
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}
	
}
