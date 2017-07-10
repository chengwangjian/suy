package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Modedevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modedevice")
public class Modedevice implements java.io.Serializable {

	// Fields

	private long id;
	private long houseId;
	private long roomId;
	private long modeNodeId;
	private long oldModeNodeId;
	private String modelId;
	private String deviceId;
	private String deviceUniqueId; // 没用的字段
	private String ieee;
	private String ep;
	private String deviceName;
	private String description; // 没用的字段
	private Date createtime = new Date();
	private Date lasttime = new Date();
	private String roomName;
	private long oldId;
	private String picName; //图片名称
	private String fangjian;
	private String ClusterID;
	private String AttrID;
	private String UniqueName;
	private String DataType;
	private long actId;
	private String ActName;
	@Transient
	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}
	@Transient
	public String getActName() {
		return ActName;
	}

	public void setActName(String actName) {
		ActName = actName;
	}

	// Constructors
	@Transient
	public String getDataType() {
		return DataType;
	}

	public void setDataType(String dataType) {
		DataType = dataType;
	}
	@Transient
	public String getClusterID() {
		return ClusterID;
	}

	public void setClusterID(String clusterID) {
		ClusterID = clusterID;
	}

	@Transient
	public String getAttrID() {
		return AttrID;
	}

	public void setAttrID(String attrID) {
		AttrID = attrID;
	}
	@Transient
	public String getUniqueName() {
		return UniqueName;
	}

	public void setUniqueName(String uniqueName) {
		UniqueName = uniqueName;
	}
	@Transient
	public String getFangjian() {
		return fangjian;
	}

	public void setFangjian(String fangjian) {
		this.fangjian = fangjian;
	}

	/** default constructor */
	public Modedevice() {
	}

	/** full constructor */
	public Modedevice(long houseId, long roomId, long modeNodeId,long oldModeNodeId,
			String modelId, String deviceId, String deviceUniqueId,
			String ieee, String ep, String deviceName, String description,
			Date createtime, Date lasttime, String roomName, long oldId, String picName,String fangjian,String ClusterID,String AttrID,String UniqueName,String DataType,String ActName,long actId) {
		this.houseId = houseId;
		this.roomId = roomId;
		this.modeNodeId = modeNodeId;
		this.oldModeNodeId = oldModeNodeId;
		this.modelId = modelId;
		this.deviceId = deviceId;
		this.deviceUniqueId = deviceUniqueId;
		this.ieee = ieee;
		this.ep = ep;
		this.deviceName = deviceName;
		this.description = description;
		this.createtime = createtime;
		this.lasttime = lasttime;
		this.roomName = roomName;
		this.oldId = oldId;
		this.picName = picName;
		this.fangjian = fangjian;
		this.ClusterID = ClusterID;
		this.AttrID = AttrID;
		this.UniqueName = UniqueName;
		this.DataType = DataType;
		this.ActName = ActName;
		this.actId =actId;
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

	@Column(name = "houseId")
	public long getHouseId() {
		return this.houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	@Column(name = "roomId")
	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "modeNodeId")
	public long getModeNodeId() {
		return this.modeNodeId;
	}

	public void setModeNodeId(long modeNodeId) {
		this.modeNodeId = modeNodeId;
	}
	
	@Column(name = "oldModeNodeId")
	public long getOldModeNodeId() {
		return oldModeNodeId;
	}

	public void setOldModeNodeId(long oldModeNodeId) {
		this.oldModeNodeId = oldModeNodeId;
	}

	@Column(name = "modelId", length = 50)
	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Column(name = "deviceId", length = 20)
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "deviceUniqueId", length = 50)
	public String getDeviceUniqueId() {
		return this.deviceUniqueId;
	}

	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}

	@Column(name = "ieee", length = 50)
	public String getIeee() {
		return this.ieee;
	}

	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	@Column(name = "ep", length = 50)
	public String getEp() {
		return this.ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
	}

	@Column(name = "deviceName", length = 200)
	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "description", length = 2000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "roomName", length = 50)
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	@Column(name = "oldId")
	public long getOldId() {
		return oldId;
	}

	public void setOldId(long oldId) {
		this.oldId = oldId;
	}

	@Column(name = "picName")
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}
	
	
}