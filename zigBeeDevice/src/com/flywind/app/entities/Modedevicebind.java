package com.flywind.app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * Modedevicebind entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modedevicebind")

public class Modedevicebind  implements java.io.Serializable {

    // Fields    

     private long id;
     private long houseId;
     private short bindType; // 绑定的类型，0为正常绑定，1为虚拟EP绑定
     private long sourceId; // 关联ModeDevice表的ID字段
     private long sourceOldId;
     private String sourceDevicename;
 	 private String sourceDeviceUniqueId; // 没用的字段
 	 private String sourceIeee;
     private String sourceVirtualEp; // 虚拟EP信息，如果BindType不为1的话则为空
     private String clusterId;
     private String clusterName;
     private String clusterNameEn;
     private String destType;// 目标设备类型  1代表Group，3代表IEEE地址模式 现在都是保存3
     private long destId; // 目标设备的ID，DestType为1的时候字段为GroupID，3的时候为ModeDevcie表的ID字段值
     private long destOldId;
     private String destName;
 	 private String destIeee;
 	 private String destDeviceUniqueId; // 没用的字段
     private String destEp;
     private short hasBind = 0;// 是否已经绑定，目前字段值固定为0
     private Date createtime = new Date();
 	 private Date lasttime = new Date();


    // Constructors

     /** default constructor */
 	public Modedevicebind() {
 	}

 	/** full constructor */
	public Modedevicebind(long houseId, short bindType, long sourceId,long sourceOldId,
			String sourceDevicename, String sourceDeviceUniqueId,
			String sourceIeee, String sourceVirtualEp, String clusterId,
			String clusterName, String destType, long destId, long destOldId,String destName,
			String destIeee, String destDeviceUniqueId, String destEp,
			short hasBind, Date createtime, Date lasttime,String clusterNameEn) {
		this.houseId = houseId;
		this.bindType = bindType;
		this.sourceId = sourceId;
		this.sourceOldId = sourceOldId;
		this.sourceDevicename = sourceDevicename;
		this.sourceDeviceUniqueId = sourceDeviceUniqueId;
		this.sourceIeee = sourceIeee;
		this.sourceVirtualEp = sourceVirtualEp;
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.destType = destType;
		this.destId = destId;
		this.destOldId = destOldId;
		this.destName = destName;
		this.destIeee = destIeee;
		this.destDeviceUniqueId = destDeviceUniqueId;
		this.destEp = destEp;
		this.hasBind = hasBind;
		this.createtime = createtime;
		this.lasttime = lasttime;
		this.clusterNameEn = clusterNameEn;
	}
 	
 	// Property accessors
 	@GenericGenerator(name = "generator", strategy = "identity")
 	@Id
 	@GeneratedValue(generator = "generator")
 	@Column(name = "ID", unique = true, nullable = false)
 	public long getId() {
 		return this.id;
 	}

 	public void setId(long id) {
 		this.id = id;
 	}

 	@Column(name = "HouseID")
 	public long getHouseId() {
 		return this.houseId;
 	}

 	public void setHouseId(long houseId) {
 		this.houseId = houseId;
 	}

 	@Column(name = "BindType")
 	public short getBindType() {
 		return this.bindType;
 	}

 	public void setBindType(short bindType) {
 		this.bindType = bindType;
 	}

 	@Column(name = "SourceID")
 	public long getSourceId() {
 		return this.sourceId;
 	}

 	public void setSourceId(long sourceId) {
 		this.sourceId = sourceId;
 	}

 	@Column(name = "sourceDevicename", length = 200)
 	public String getSourceDevicename() {
 		return this.sourceDevicename;
 	}

 	public void setSourceDevicename(String sourceDevicename) {
 		this.sourceDevicename = sourceDevicename;
 	}
 	
 	@Column(name = "sourceDeviceUniqueId", length = 50)
	public String getSourceDeviceUniqueId() {
		return this.sourceDeviceUniqueId;
	}

	public void setSourceDeviceUniqueId(String sourceDeviceUniqueId) {
		this.sourceDeviceUniqueId = sourceDeviceUniqueId;
	}
	
	@Column(name = "sourceIeee", length = 50)
	public String getSourceIeee() {
		return this.sourceIeee;
	}

	public void setSourceIeee(String sourceIeee) {
		this.sourceIeee = sourceIeee;
	}

 	@Column(name = "SourceVirtualEP", length = 50)
 	public String getSourceVirtualEp() {
 		return this.sourceVirtualEp;
 	}

 	public void setSourceVirtualEp(String sourceVirtualEp) {
 		this.sourceVirtualEp = sourceVirtualEp;
 	}

 	@Column(name = "ClusterID", length = 20)
 	public String getClusterId() {
 		return this.clusterId;
 	}

 	public void setClusterId(String clusterId) {
 		this.clusterId = clusterId;
 	}

 	@Column(name = "clusterName", length = 100)
 	public String getClusterName() {
 		return this.clusterName;
 	}

 	public void setClusterName(String clusterName) {
 		this.clusterName = clusterName;
 	}

 	@Column(name = "DestType", length = 4)
 	public String getDestType() {
 		return this.destType;
 	}

 	public void setDestType(String destType) {
 		this.destType = destType;
 	}

 	@Column(name = "DestID")
 	public long getDestId() {
 		return this.destId;
 	}

 	public void setDestId(long destId) {
 		this.destId = destId;
 	}

 	@Column(name = "destName", length = 200)
 	public String getDestName() {
 		return this.destName;
 	}

 	public void setDestName(String destName) {
 		this.destName = destName;
 	}
 	
 	@Column(name = "destDeviceUniqueId", length = 50)
	public String getDestDeviceUniqueId() {
		return this.destDeviceUniqueId;
	}

	public void setDestDeviceUniqueId(String destDeviceUniqueId) {
		this.destDeviceUniqueId = destDeviceUniqueId;
	}
	
	@Column(name = "destIeee", length = 50)
	public String getDestIeee() {
		return this.destIeee;
	}

	public void setDestIeee(String destIeee) {
		this.destIeee = destIeee;
	}

 	@Column(name = "destEp", length = 50)
 	public String getDestEp() {
 		return this.destEp;
 	}

 	public void setDestEp(String destEp) {
 		this.destEp = destEp;
 	}

 	@Column(name = "HasBind")
 	public short getHasBind() {
 		return this.hasBind;
 	}

 	public void setHasBind(short hasBind) {
 		this.hasBind = hasBind;
 	} 
 	
 	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "lasttime", length = 19)
	public Date getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	@Column(name = "sourceOldId")
	public long getSourceOldId() {
		return sourceOldId;
	}

	public void setSourceOldId(long sourceOldId) {
		this.sourceOldId = sourceOldId;
	}

	@Column(name = "destOldId")
	public long getDestOldId() {
		return destOldId;
	}

	public void setDestOldId(long destOldId) {
		this.destOldId = destOldId;
	}
	@Column(name = "clusterNameEn", length = 100)  
    public String getClusterNameEn() {
		return clusterNameEn;
	}
	public void setClusterNameEn(String clusterNameEn) {
		this.clusterNameEn = clusterNameEn;
	}
}