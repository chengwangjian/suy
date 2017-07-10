package zbHouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Video entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "video")
public class Video implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long roomId;
	private String localip;
	private String localport;
	private String ipcamip;
	private String ipcamport;
	private String cache;
	private String st;
	private String uuid;
	private String server;
	private String location;
	private String status;
	private String ipcamname;

	// Constructors

	/** default constructor */
	public Video() {
	}

	/** full constructor */
	public Video(String houseIeee, long roomId, String localip,
			String localport, String ipcamip, String ipcamport, String cache,
			String st, String uuid, String server, String location,
			String status, String ipcamname) {
		this.houseIeee = houseIeee;
		this.roomId = roomId;
		this.localip = localip;
		this.localport = localport;
		this.ipcamip = ipcamip;
		this.ipcamport = ipcamport;
		this.cache = cache;
		this.st = st;
		this.uuid = uuid;
		this.server = server;
		this.location = location;
		this.status = status;
		this.ipcamname = ipcamname;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "roomId")
	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "localip", length = 50)
	public String getLocalip() {
		return this.localip;
	}

	public void setLocalip(String localip) {
		this.localip = localip;
	}

	@Column(name = "localport", length = 50)
	public String getLocalport() {
		return this.localport;
	}

	public void setLocalport(String localport) {
		this.localport = localport;
	}

	@Column(name = "ipcamip", length = 500)
	public String getIpcamip() {
		return this.ipcamip;
	}

	public void setIpcamip(String ipcamip) {
		this.ipcamip = ipcamip;
	}

	@Column(name = "ipcamport", length = 500)
	public String getIpcamport() {
		return this.ipcamport;
	}

	public void setIpcamport(String ipcamport) {
		this.ipcamport = ipcamport;
	}

	@Column(name = "cache", length = 500)
	public String getCache() {
		return this.cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	@Column(name = "st", length = 500)
	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	@Column(name = "uuid", length = 200)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "server", length = 200)
	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Column(name = "location", length = 200)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "status", length = 50)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ipcamname", length = 50)
	public String getIpcamname() {
		return this.ipcamname;
	}

	public void setIpcamname(String ipcamname) {
		this.ipcamname = ipcamname;
	}

}