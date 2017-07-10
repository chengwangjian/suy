package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "modefarmnode")
public class ModeFarmNode implements java.io.Serializable {

	// Fields

	private Long id;
	private long houseId;
	private long roomId;
	private long modeNodeLibId;
	private String deviceUniqueId; // 没用的字段
	private String ieee;
	private String deviceName;
	private String description;
	private Date createtime = new Date();
	private Date lasttime = new Date();
	private long oldId;

	// Constructors

	/** default constructor */
	public ModeFarmNode() {
	}

	/** full constructor */
	public ModeFarmNode(long houseId, long roomId, long modeNodeLibId,
			String deviceUniqueId, String ieee, String deviceName,
			String description, Date createtime, Date lasttime, long oldId) {
		this.houseId = houseId;
		this.roomId = roomId;
		this.modeNodeLibId = modeNodeLibId;
		this.deviceUniqueId = deviceUniqueId;
		this.ieee = ieee;
		this.deviceName = deviceName;
		this.description = description;
		this.createtime = createtime;
		this.lasttime = lasttime;
		this.oldId = oldId;
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

	@Column(name = "modeNodeLibId")
	public long getModeNodeLibId() {
		return this.modeNodeLibId;
	}

	public void setModeNodeLibId(long modeNodeLibId) {
		this.modeNodeLibId = modeNodeLibId;
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
	
	@Column(name = "oldId")
	public long getOldId() {
		return this.oldId;
	}

	public void setOldId(long oldId) {
		this.oldId = oldId;
	}

}