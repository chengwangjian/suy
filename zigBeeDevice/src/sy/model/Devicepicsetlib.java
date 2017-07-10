package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Devicepicsetlib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "devicepicsetlib")
public class Devicepicsetlib implements java.io.Serializable {

	// Fields

	private long id;
	private long houseId = -1;
	private long mid;
	private String deviceId;
	private String picName;
	private Date createtime = new Date();
	private Date lasttime = new Date();

	// Constructors

	/** default constructor */
	public Devicepicsetlib() {
	}

	/** full constructor */
	public Devicepicsetlib(long houseId,long mid, String deviceId, String picName,
			Date createtime, Date lasttime) {
		this.houseId = houseId;
		this.mid = mid;
		this.deviceId = deviceId;
		this.picName = picName;
		this.createtime = createtime;
		this.lasttime = lasttime;
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
	
	@Column(name = "houseId")
	public long getHouseId() {
		return this.houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	@Column(name = "mid")
	public long getMid() {
		return this.mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	@Column(name = "deviceId", length = 20)
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "picName", length = 100)
	public String getPicName() {
		return this.picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
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