package sy.test;

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
@Table(name = "modedevicebind", catalog = "zigbeedevice")
public class Modedevicebind implements java.io.Serializable {

	// Fields

	private long id;
	private long houseId;
	private short bindType;
	private long sourceId;
	private String sourceDevicename;
	private String sourceDeviceUniqueId;
	private String sourceIeee;
	private String sourceVirtualEp;
	private String clusterId;
	private String clusterName;
	private String destType;
	private long destId;
	private String destName;
	private String destIeee;
	private String destDeviceUniqueId;
	private String destEp;
	private short hasBind;
	private Date createtime;
	private Date lasttime;

	// Constructors

	/** default constructor */
	public Modedevicebind() {
	}

	/** full constructor */
	public Modedevicebind(long houseId, short bindType, long sourceId,
			String sourceDevicename, String sourceDeviceUniqueId,
			String sourceIeee, String sourceVirtualEp, String clusterId,
			String clusterName, String destType, long destId, String destName,
			String destIeee, String destDeviceUniqueId, String destEp,
			short hasBind, Date createtime, Date lasttime) {
		this.houseId = houseId;
		this.bindType = bindType;
		this.sourceId = sourceId;
		this.sourceDevicename = sourceDevicename;
		this.sourceDeviceUniqueId = sourceDeviceUniqueId;
		this.sourceIeee = sourceIeee;
		this.sourceVirtualEp = sourceVirtualEp;
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.destType = destType;
		this.destId = destId;
		this.destName = destName;
		this.destIeee = destIeee;
		this.destDeviceUniqueId = destDeviceUniqueId;
		this.destEp = destEp;
		this.hasBind = hasBind;
		this.createtime = createtime;
		this.lasttime = lasttime;
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

	@Column(name = "destIeee", length = 50)
	public String getDestIeee() {
		return this.destIeee;
	}

	public void setDestIeee(String destIeee) {
		this.destIeee = destIeee;
	}

	@Column(name = "destDeviceUniqueId", length = 50)
	public String getDestDeviceUniqueId() {
		return this.destDeviceUniqueId;
	}

	public void setDestDeviceUniqueId(String destDeviceUniqueId) {
		this.destDeviceUniqueId = destDeviceUniqueId;
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

}