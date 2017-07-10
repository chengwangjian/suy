package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Usermodemain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="usermodemain")

@XStreamAlias("ID")
public class Usermodemain  implements java.io.Serializable,com.flywind.app.data.Intermain {

    // Fields 

     private long id;
     private long houseId;     
     private long roomId;
     private String modeName;
     private String picName="";
     private String description;
     private Long oldId;
     private Integer orderId;
     private Short enableCheckOnOff = 0;
    // Constructors

	/** default constructor */
    public Usermodemain() {
    }

    
    /** full constructor */
    public Usermodemain(long houseId, long roomId, String modeName, String picName, String description) {
    	this.houseId=houseId;
        this.roomId = roomId;
        this.modeName = modeName;
        this.picName = picName;
        this.description = description;
    }

   
    // Property accessors
    @Override
	@Id 
	@GeneratedValue
    @Column(name="ID", unique=true, nullable=false)

    public long getId() {
        return this.id;
    }
    
    @Override
	public void setId(long id) {
        this.id = id;
    }
 
    @Column(name="HouseID")

    public Long getHouseId() {
        return this.houseId;
    }
    
    @Override
	public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
    
    @Column(name="RoomID")

    public long getRoomId() {
        return this.roomId;
    }
    
    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
    
    @Column(name="ModeName")

    public String getModeName() {
        return this.modeName;
    }
    
    public void setModeName(String modeName) {
        this.modeName = modeName;
    }
    
    @Column(name="PicName")

    public String getPicName() {
        return this.picName;
    }
    
    public void setPicName(String picName) {
        this.picName = picName;
    }
    
    @Column(name="Description")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
	@Column(name="oldId")	
	public Long getOldId() {
		return oldId;
	}

	@Override
	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}      

    public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Short getEnableCheckOnOff() {
		return enableCheckOnOff;
	}
	
	public void setEnableCheckOnOff(Short enableCheckOnOff) {
		this.enableCheckOnOff = enableCheckOnOff;
	}



}