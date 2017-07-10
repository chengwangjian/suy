package sy.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Modegrouplib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modegrouplib", catalog = "zigbeedevice")
public class Modegrouplib implements java.io.Serializable {

	// Fields

	private long id;
	private String modelId;
	private String deviceEp;
	private String deviceNameEn;
	private String deviceName;
	private String ep;
	private String clusterId;
	private String clusterName;

	// Constructors

	/** default constructor */
	public Modegrouplib() {
	}

	/** full constructor */
	public Modegrouplib(String modelId, String deviceEp, String deviceNameEn,
			String deviceName, String ep, String clusterId, String clusterName) {
		this.modelId = modelId;
		this.deviceEp = deviceEp;
		this.deviceNameEn = deviceNameEn;
		this.deviceName = deviceName;
		this.ep = ep;
		this.clusterId = clusterId;
		this.clusterName = clusterName;
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

	@Column(name = "modelId", length = 50)
	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	@Column(name = "deviceEp", length = 50)
	public String getDeviceEp() {
		return this.deviceEp;
	}

	public void setDeviceEp(String deviceEp) {
		this.deviceEp = deviceEp;
	}

	@Column(name = "deviceNameEn", length = 200)
	public String getDeviceNameEn() {
		return this.deviceNameEn;
	}

	public void setDeviceNameEn(String deviceNameEn) {
		this.deviceNameEn = deviceNameEn;
	}

	@Column(name = "deviceName", length = 200)
	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "ep", length = 50)
	public String getEp() {
		return this.ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
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

}