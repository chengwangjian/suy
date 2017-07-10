package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ModeeplibEn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modeeplib_en")
public class ModeeplibEn implements java.io.Serializable {

	// Fields

	private long id;
	private long nodeId;
	private String deviceId;
	private String internelModelId;
	private String deviceName;
	private String clusterId;
	private String clusterName;
	private String picName;
	private String destType;
	private String deviceType;
	private long deviceTypeV2;
	private String ep;
	private String description;
	private long groupable;

	// Constructors

	/** default constructor */
	public ModeeplibEn() {
	}

	/** full constructor */
	public ModeeplibEn(long nodeId, String deviceId, String internelModelId,
			String deviceName, String clusterId, String clusterName,
			String picName, String destType, String deviceType,
			long deviceTypeV2, String ep, String description, long groupable) {
		this.nodeId = nodeId;
		this.deviceId = deviceId;
		this.internelModelId = internelModelId;
		this.deviceName = deviceName;
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.picName = picName;
		this.destType = destType;
		this.deviceType = deviceType;
		this.deviceTypeV2 = deviceTypeV2;
		this.ep = ep;
		this.description = description;
		this.groupable = groupable;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
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

	@Column(name = "picName", length = 100)
	public String getPicName() {
		return this.picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
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

	@Column(name = "deviceTypeV2")
	public long getDeviceTypeV2() {
		return this.deviceTypeV2;
	}

	public void setDeviceTypeV2(long deviceTypeV2) {
		this.deviceTypeV2 = deviceTypeV2;
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

	@Column(name = "Groupable")
	public long getGroupable() {
		return this.groupable;
	}

	public void setGroupable(long groupable) {
		this.groupable = groupable;
	}

}