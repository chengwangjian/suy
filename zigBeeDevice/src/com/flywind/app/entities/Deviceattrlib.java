package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Deviceattrlib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="deviceattrlib")

public class Deviceattrlib  implements java.io.Serializable {

    // Fields    

     private long id;
     private String modelId;
     private String deviceId;
     private String clusterId;
     private String attrId;
     private String attrName;
     private int dataType=0;
     private String uniqueName;


    // Constructors

    /** default constructor */
    public Deviceattrlib() {
    }

    
    /** full constructor */
    public Deviceattrlib(String modelId, String deviceId, String clusterId, String attrId, String attrName, int dataType, String uniqueName) {
        this.modelId = modelId;
        this.deviceId = deviceId;
        this.clusterId = clusterId;
        this.attrId = attrId;
        this.attrName = attrName;
        this.dataType = dataType;
        this.uniqueName = uniqueName;
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
    
    @Column(name="ModelID")

    public String getModelId() {
        return this.modelId;
    }
    
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    
    @Column(name="DeviceID")

    public String getDeviceId() {
        return this.deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    @Column(name="ClusterID")

    public String getClusterId() {
        return this.clusterId;
    }
    
    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }
    
    @Column(name="AttrID")

    public String getAttrId() {
        return this.attrId;
    }
    
    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }
    
    @Column(name="AttrName")

    public String getAttrName() {
        return this.attrName;
    }
    
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    
    @Column(name="DataType")

    public int getDataType() {
        return this.dataType;
    }
    
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
  
    @Column(name="UniqueName")

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}





}