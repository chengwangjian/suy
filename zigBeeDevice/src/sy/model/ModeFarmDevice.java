package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "modefarmdevice")
public class ModeFarmDevice implements java.io.Serializable {

	// Fields

	private Long id;
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
	private String solidModelId;

	// Constructors

	/** default constructor */
	public ModeFarmDevice() {
	}

	/** full constructor */
	public ModeFarmDevice(long houseId, long roomId, long modeNodeId,long oldModeNodeId,
			String modelId, String deviceId, String deviceUniqueId,
			String ieee, String ep, String deviceName, String description,
			Date createtime, Date lasttime, String roomName, long oldId, String picName) {
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
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	@Column(name = "SolidModelID")
	public String getSolidModelId() {
		return solidModelId;
	}

	public void setSolidModelId(String solidModelId) {
		this.solidModelId = solidModelId;
	}
	
	
}