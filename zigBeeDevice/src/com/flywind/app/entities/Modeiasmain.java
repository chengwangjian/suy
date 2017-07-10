package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Modeiasmain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modeiasmain")

@XStreamAlias("ID")
public class Modeiasmain  implements java.io.Serializable,com.flywind.app.data.Intermain {

    // Fields    

     private long id;
     private long houseId;
     private long modeId;
     private long modeEpId;
     private long modeEpOldId;
     private short bactive=1;
     private String device;
     private long attrlibId;
 	 private short ballmodeActive;
     private Long oldId;
     private Long mainId;
    // Constructors
     @Column(name="mainID")
    public Long getMainId() {
		return mainId;
	}


	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}


	/** default constructor */
    public Modeiasmain() {
    }

    
    /** full constructor */
    public Modeiasmain(long houseId, long modeId, long modeEpId,long modeEpOldId, short bactive, String device, long attrlibId,Long mainId) {
    	this.houseId=houseId;
        this.modeId = modeId;
        this.modeEpId = modeEpId;
        this.modeEpOldId = modeEpOldId;
        this.bactive = bactive;
        this.device=device;
        this.attrlibId=attrlibId;
        this.mainId = mainId;
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
    
    @Column(name="ModeID")

    public long getModeId() {
        return this.modeId;
    }
    
    public void setModeId(long modeId) {
        this.modeId = modeId;
    }
    
    @Column(name="ModeEpID")

    public long getModeEpId() {
        return this.modeEpId;
    }
    
    public void setModeEpId(long modeEpId) {
        this.modeEpId = modeEpId;
    }
    
    @Column(name="bActive")

    public short getBactive() {
        return this.bactive;
    }
    
    public void setBactive(short bactive) {
        this.bactive = bactive;
    }

    @Column(name="Device")
    
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

    @Column(name="AttrlibID")	
	public long getAttrlibId() {
		return attrlibId;
	}

	public void setAttrlibId(long attrlibId) {
		this.attrlibId = attrlibId;
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

	@Column(name = "bAllmodeActive")
	public Short getBallmodeActive() {
		return this.ballmodeActive;
	}

	public void setBallmodeActive(Short ballmodeActive) {
		this.ballmodeActive = ballmodeActive;
	}

	@Column(name = "modeEpOldId")
	public long getModeEpOldId() {
		return modeEpOldId;
	}

	public void setModeEpOldId(long modeEpOldId) {
		this.modeEpOldId = modeEpOldId;
	}
}