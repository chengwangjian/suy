package sy.test;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Userordernode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userordernode", catalog = "zigbeedevice")
public class Userordernode implements java.io.Serializable {

	// Fields

	private long id;
	private long orderRoomId;
	private long modeNodeLibId;
	private String deviceUniqueId;
	private String ieee;
	private String deviceName;
	private String description;
	private Date createtime = new Date();
	private Date lasttime = new Date();

	// Constructors

	/** default constructor */
	public Userordernode() {
	}

	/** full constructor */
	public Userordernode(long orderRoomId, long modeNodeLibId,
			String deviceUniqueId, String ieee, String deviceName,
			String description, Date createtime, Date lasttime) {
		this.orderRoomId = orderRoomId;
		this.modeNodeLibId = modeNodeLibId;
		this.deviceUniqueId = deviceUniqueId;
		this.ieee = ieee;
		this.deviceName = deviceName;
		this.description = description;
		this.createtime = createtime;
		this.lasttime = lasttime;
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

	@Column(name = "orderRoomId")
	public long getOrderRoomId() {
		return this.orderRoomId;
	}

	public void setOrderRoomId(long orderRoomId) {
		this.orderRoomId = orderRoomId;
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

}