package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ModenodelibEn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modenodelib_en")
public class ModenodelibEn implements java.io.Serializable {

	// Fields

	private long id;
	private String nodeType;
	private String modelId;
	private String deviceName;
	private String clusterId;
	private String clusterName;
	private String destType;
	private long sourceId;
	private String deviceType;
	private String picName;
	private String description;
	private String powerType;
	private String activationMethod;
	private String resetDefault;
	private String remark;

	// Constructors

	/** default constructor */
	public ModenodelibEn() {
	}

	/** full constructor */
	public ModenodelibEn(String nodeType, String modelId, String deviceName,
			String clusterId, String clusterName, String destType,
			long sourceId, String deviceType, String picName,
			String description, String powerType, String activationMethod,
			String resetDefault, String remark) {
		this.nodeType = nodeType;
		this.modelId = modelId;
		this.deviceName = deviceName;
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.destType = destType;
		this.sourceId = sourceId;
		this.deviceType = deviceType;
		this.picName = picName;
		this.description = description;
		this.powerType = powerType;
		this.activationMethod = activationMethod;
		this.resetDefault = resetDefault;
		this.remark = remark;
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

	@Column(name = "nodeType", length = 100)
	public String getNodeType() {
		return this.nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Column(name = "modelId", length = 50)
	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
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

	@Column(name = "destType", length = 4)
	public String getDestType() {
		return this.destType;
	}

	public void setDestType(String destType) {
		this.destType = destType;
	}

	@Column(name = "source_id")
	public long getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "deviceType", length = 20)
	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "picName", length = 100)
	public String getPicName() {
		return this.picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "description", length = 2000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "powerType", length = 200)
	public String getPowerType() {
		return this.powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}

	@Column(name = "activationMethod", length = 2000)
	public String getActivationMethod() {
		return this.activationMethod;
	}

	public void setActivationMethod(String activationMethod) {
		this.activationMethod = activationMethod;
	}

	@Column(name = "resetDefault", length = 2000)
	public String getResetDefault() {
		return this.resetDefault;
	}

	public void setResetDefault(String resetDefault) {
		this.resetDefault = resetDefault;
	}

	@Column(name = "remark", length = 2000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}