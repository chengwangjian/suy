package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Modenodelib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modenodelib")
public class Modenodelib implements java.io.Serializable {

	// Fields

	private long id;
	private String nodeType;
	private String modelId;
	private String deviceName;
	private String clusterId; // 没用的字段
	private String clusterName; // 没用的字段
	private String destType; // 没用的字段
	private String deviceType; // 没用的字段
	private String picName;
	private String description;
	private String powerType;
	private String activationMethod;
	private String resetDefault;
	private String remark;
	private String deviceNameEn;
	private String clusterNameEn;
	private String descriptionEn;
	private String powerTypeEn;
	private String activationMethodEn;
	private String resetDefaultEn;
	private String remarkEn;
	private String voltagestate;
	
	// Constructors
	//非表字段
	private String language;
	/** default constructor */
	public Modenodelib() {
	}

	/** full constructor */
	public Modenodelib(String nodeType, String modelId, String deviceName,
			String clusterId, String clusterName, String destType,
			String deviceType, String picName, String description,
			String powerType, String activationMethod, String resetDefault,
			String remark, String descriptionEn,
			String powerTypeEn, String activationMethodEn, String resetDefaultEn,
			String remarkEn, String deviceNameEn,String clusterNameEn,String voltagestate) {
		this.nodeType = nodeType;
		this.modelId = modelId;
		this.deviceName = deviceName;
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.destType = destType;
		this.deviceType = deviceType;
		this.picName = picName;
		this.description = description;
		this.powerType = powerType;
		this.activationMethod = activationMethod;
		this.resetDefault = resetDefault;
		this.remark = remark;
		this.descriptionEn = descriptionEn;
		this.powerTypeEn = powerTypeEn;
		this.activationMethodEn = activationMethodEn;
		this.resetDefaultEn =resetDefaultEn;
		this.remarkEn = remarkEn;
		this.deviceNameEn = deviceNameEn;
		this.clusterNameEn = clusterNameEn;
		this.voltagestate = voltagestate;
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

	public String getDescriptionEn() {
		return descriptionEn;
	}
	@Column(name = "descriptionEn", length = 2000)
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public String getPowerTypeEn() {
		return powerTypeEn;
	}
	@Column(name = "powerTypeEn",length = 200)
	public void setPowerTypeEn(String powerTypeEn) {
		this.powerTypeEn = powerTypeEn;
	}

	public String getActivationMethodEn() {
		return activationMethodEn;
	}
	@Column(name = "activationMethodEn",length = 2000)
	public void setActivationMethodEn(String activationMethodEn) {
		this.activationMethodEn = activationMethodEn;
	}

	public String getResetDefaultEn() {
		return resetDefaultEn;
	}
	@Column(name = "resetDefaultEn",length = 2000)
	public void setResetDefaultEn(String resetDefaultEn) {
		this.resetDefaultEn = resetDefaultEn;
	}

	public String getRemarkEn() {
		return remarkEn;
	}
	@Column(name = "remarkEn",length = 2000)
	public void setRemarkEn(String remarkEn) {
		this.remarkEn = remarkEn;
	}
	@Column(name = "voltagestate",length = 50)
	public String getVoltagestate() {
		return voltagestate;
	}

	public void setVoltagestate(String voltagestate) {
		this.voltagestate = voltagestate;
	}
	
	
}