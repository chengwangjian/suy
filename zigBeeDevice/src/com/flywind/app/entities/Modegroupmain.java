package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Modegroupmain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modegroupmain")

@XStreamAlias("ID")
public class Modegroupmain  implements java.io.Serializable,com.flywind.app.data.Intermain {

    // Fields    
     
     private long id;
     private long houseId; 
     private long groupId;
     private String groupName;
     private Long oldId;

    // Constructors

    /** default constructor */
    public Modegroupmain() {
    }

    
    /** full constructor */
    public Modegroupmain(long houseId, long groupId, String groupName) {
    	this.houseId=houseId;
        this.groupId = groupId;
        this.groupName = groupName;
    }

   
    // Property accessors
    @Override
	@Id @GeneratedValue
    
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
    
    @Column(name="GroupID")
    
    public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	@Column(name="GroupName")

    public String getGroupName() {
        return this.groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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






}