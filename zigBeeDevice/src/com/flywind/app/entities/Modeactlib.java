package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Modeactlib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modeactlib")

public class Modeactlib  implements java.io.Serializable {

    // Fields    

     private long id;
     private String deviceId;    
     private String actName;
     private Long extentionSet;
     private String scenePara;
     private String uniqueName;
     private Long dataType;
     private int groupabled;
     private int groupLevel;
     private int sceneabled;
     private String uniqueNameEN;
    // Constructors

    public String getUniqueNameEN() {
		return uniqueNameEN;
	}

    @Column(name="UniqueNameEN")
	public void setUniqueNameEN(String uniqueNameEN) {
		this.uniqueNameEN = uniqueNameEN;
	}


	/** default constructor */
    public Modeactlib() {
    }

    
    /** full constructor */
    public Modeactlib(String deviceId,String actName, long extentionSet,String uniqueName,long dataType) {
    	this.deviceId= deviceId;
        this.actName = actName;
        this.extentionSet = extentionSet;
        this.uniqueName= uniqueName;
        this.dataType = dataType;
    }

   
 	// Property accessors
    @Id 
    @GeneratedValue
    @Column(name="ID", unique=true, nullable=false)

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
	@Column(name="DeviceID")   
    
    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name="ActName")

    public String getActName() {
        return this.actName;
    }
    
	public void setActName(String actName) {
        this.actName = actName;
    }
    
    @Column(name="ExtentionSet")

    public Long getExtentionSet() {
        return this.extentionSet;
    }
    
    public void setExtentionSet(Long extentionSet) {
        this.extentionSet = extentionSet;
    }
 
    @Column(name="ScenePara")   
    
    public String getScenePara() {
		return scenePara;
	}


	public void setScenePara(String scenePara) {
		this.scenePara = scenePara;
	}


	@Column(name="UniqueName")

    public String getUniqueName() {
 		return uniqueName;
 	}

 	public void setUniqueName(String uniqueName) {
 		this.uniqueName = uniqueName;
 	}

    @Column(name="DataType")

    public Long getDataType() {
        return this.dataType;
    }
    
    public void setDataType(Long dataType) {
        this.dataType = dataType;
    }


	public int getGroupabled() {
		return groupabled;
	}


	public void setGroupabled(int groupabled) {
		this.groupabled = groupabled;
	}


	public int getGroupLevel() {
		return groupLevel;
	}


	public void setGroupLevel(int groupLevel) {
		this.groupLevel = groupLevel;
	}


	public int getSceneabled() {
		return sceneabled;
	}


	public void setSceneabled(int sceneabled) {
		this.sceneabled = sceneabled;
	}




}