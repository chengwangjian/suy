package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Modehvacmain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modehvacmain")

@XStreamAlias("ID")
public class Modehvacmain  implements java.io.Serializable,com.flywind.app.data.Intermain2{


    // Fields    

     private long id;
     private long houseId; 
     private long modeId;
     private long modeEpId;
     private long modeEpOldId;
     private double minValues;
     private double maxValues;    
     private short bactive=1;    
     private String attributes;    
     private String device;
     private long attrlibId;
     private String clusterId;      
     private String attrId; 
 	 private short ballmodeActive;
     private Long oldId;
     private Long mainId;
     private short clause=0;//0: or 1:and
     private String condition2;//区分条件一、条件二
     @Column(name="condition2")
    public String getCondition2() {
		return condition2;
	}


	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}


	// Constructors
     @Column(name="clause")
    public short getClause() {
		return clause;
	}


	public void setClause(short clause) {
		this.clause = clause;
	}


	/** default constructor */
    public Modehvacmain() {
    }

    
    /** full constructor */
    public Modehvacmain(long houseId, long modeId, long modeEpId,long modeEpOldId, double minValues, double maxValues, short bactive, String attributes, String device, long attrlibId,Long mainId,short clause,String condition2) {
    	this.houseId=houseId;
        this.modeId = modeId;
        this.modeEpId = modeEpId;
        this.modeEpOldId = modeEpOldId;
        this.minValues = minValues;
        this.maxValues = maxValues;
        this.bactive = bactive;
        this.attributes =attributes;
        this.device =device;
        this.attrlibId=attrlibId;
        this.mainId=mainId;
        this.clause=clause;
        this.condition2 = condition2;
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
    
    @Column(name="MinValues", precision=22, scale=0)

    public double getMinValues() {
        return this.minValues;
    }
    
    public void setMinValues(double minValues) {
        this.minValues = minValues;
    }
    
    @Column(name="MaxValues", precision=22, scale=0)

    public double getMaxValues() {
        return this.maxValues;
    }
    
    public void setMaxValues(double maxValues) {
        this.maxValues = maxValues;
    }
    
    @Column(name="bActive")

    public short getBactive() {
        return this.bactive;
    }
    
    public void setBactive(short bactive) {
        this.bactive = bactive;
    }
   
    @Column(name="Attributes")
    
    public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
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
	
    @Column(name="ClusterID")	
	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

    @Column(name="AttrID")
	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
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

	@Override
	@Column(name = "mainID")
	public Long getMainId() {
		return mainId;
	}

	
	@Override
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}
}