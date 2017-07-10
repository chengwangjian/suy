package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Modegroupsub entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modegroupsub")
 
@XStreamAlias("id")
public class Modegroupsub  implements java.io.Serializable,com.flywind.app.data.Intersub {

    // Fields    

     private long id;  
     private long mid;
     private long houseId;    
     private Long deviceId;  
     private long deviceOldId;  
     private Long roomId;
 	 private String deviceName;
 	 private String groupName; 	
 	 private String status;
    // Constructors

	/** default constructor */
    public Modegroupsub() {
    }

	/** full constructor */
    public Modegroupsub(long id, long mid, long houseId, long deviceId,long deviceOldId, long roomId,String deviceName) {
    	this.id=id;
    	this.mid=mid;
        this.houseId = houseId;
        this.deviceId=deviceId;
        this.deviceOldId=deviceOldId;
        this.roomId=roomId;
        this.deviceName=deviceName;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="ID", unique=true, nullable=false)

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
  
    @Override
	@Column(name="MID")

    public Long getMid() {
        return this.mid;
    }
    
    @Override
	public void setMid(Long mid) {
        this.mid = mid;
    }
 
    @Column(name="HouseID")

    public Long getHouseId() {
        return this.houseId;
    }
    
    @Override
	public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    @Column(name="DeviceID")
    
    public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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