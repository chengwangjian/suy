package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Modeeplib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modeeplib")
public class Modeeplib implements java.io.Serializable {

	// Fields

	private long id;
	private long nodeId;
	private String deviceId;
	private String internelModelId; // 值为Z815J-1，Z815J-2
	private String deviceName; // 值为Z803一路排插
	private String clusterId; // 值0006:0008或0006
	private String clusterName; // 值为开关:调级控制，开关
	private String picName; // 没用的字段
	private String destType; // 目标设备类型  1代表Group(虚拟ep)，3代表IEEE地址模式(真实ep)
	private String deviceType; // 值为source或dest或sourceDest或""
	private String ep;
	private String description;	// 没用的字段
	private long groupable; // 是否加入组，值为0,1  1是，0否
	private String deviceNameEn;
	private String clusterNameEn;
	private String descriptionEn;
	private String SolidModelID;
	
	@Column(name = "SolidModelID", length = 20)
	public String getSolidModelID() {
		return SolidModelID;
	}
	public void setSolidModelID(String solidModelID) {
		SolidModelID = solidModelID;
	}
	
	@Column(name = "descriptionEn", length = 2000)
	public String getDescriptionEn() {
		return descriptionEn;
	}
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	// 非表字段
	private int num;
	private long houseId;
	private String language;
	// Constructors

	/** default constructor */
	public Modeeplib() {
	}

	/** full constructor */
	public Modeeplib(long nodeId, String deviceId, String internelModelId,
			String deviceName, String clusterId, String clusterName,
			String picName, String destType, String deviceType, String ep,
			String description, long groupable, String deviceNameEn, String clusterNameEn, String descriptionEn) {
		this.nodeId = nodeId;
		this.deviceId = deviceId;
		this.internelModelId = internelModelId;
		this.deviceName = deviceName;
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.picName = picName;
		this.destType = destType;
		this.deviceType = deviceType;
		this.ep = ep;
		this.description = description;
		this.groupable = groupable;
		this.deviceNameEn = deviceNameEn;
		this.clusterNameEn = clusterNameEn;
		this.descriptionEn = descriptionEn;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nodeId")
	public long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "deviceId", length = 20)
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "internelModelId", length = 50)
	public String getInternelModelId() {
		return this.internelModelId;
	}

	public void setInternelModelId(String internelModelId) {
		this.internelModelId = internelModelId;
	}

	@Column(name = "deviceName", length = 200)
	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "picName", length = 100)
	public String getPicName() {
		return this.picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "ep", length = 50)
	public String getEp() {
		return this.ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
	}

	@Column(name = "description", length = 2000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "clusterId", length = 200)
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

	@Column(name = "destType", length = 4)
	public String getDestType() {
		return this.destType;
	}

	public void setDestType(String destType) {
		this.destType = destType;
	}

	@Column(name = "deviceType", length = 20)
	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "groupable")
	public long getGroupable() {
		return this.groupable;
	}

	public void setGroupable(long groupable) {
		this.groupable = groupable;
	}
	
	@Transient
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Transient
	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}
	
	@Transient
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getDeviceNameEn() {
		return deviceNameEn;
	}
	@Column(name = "deviceNameEn", length = 200)
	public void setDeviceNameEn(String deviceNameEn) {
		this.deviceNameEn = deviceNameEn;
	}

	public String getClusterNameEn() {
		return clusterNameEn;
	}
	@Column(name = "clusterNameEn", length = 100)
	public void setClusterNameEn(String clusterNameEn) {
		this.clusterNameEn = clusterNameEn;
	}
}