package sy.test;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * DeviceoperatehistoryHouseidYear entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "deviceoperatehistory_houseid_year", catalog = "zigbeedevice")
public class DeviceoperatehistoryHouseidYear implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private String username;
	private long roomId;
	private String deviceIeee;
	private String deviceEp;
	private String opname;
	private String optype;
	private String opparam;
	private Date opdatetime;

	// Constructors

	/** default constructor */
	public DeviceoperatehistoryHouseidYear() {
	}

	/** full constructor */
	public DeviceoperatehistoryHouseidYear(String houseIeee, String username,
			long roomId, String deviceIeee, String deviceEp, String opname,
			String optype, String opparam, Date opdatetime) {
		this.houseIeee = houseIeee;
		this.username = username;
		this.roomId = roomId;
		this.deviceIeee = deviceIeee;
		this.deviceEp = deviceEp;
		this.opname = opname;
		this.optype = optype;
		this.opparam = opparam;
		this.opdatetime = opdatetime;
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

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "username", length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "room_id")
	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "device_ieee", length = 50)
	public String getDeviceIeee() {
		return this.deviceIeee;
	}

	public void setDeviceIeee(String deviceIeee) {
		this.deviceIeee = deviceIeee;
	}

	@Column(name = "device_ep", length = 50)
	public String getDeviceEp() {
		return this.deviceEp;
	}

	public void setDeviceEp(String deviceEp) {
		this.deviceEp = deviceEp;
	}

	@Column(name = "opname", length = 200)
	public String getOpname() {
		return this.opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}

	@Column(name = "optype", length = 20)
	public String getOptype() {
		return this.optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	@Column(name = "opparam", length = 2000)
	public String getOpparam() {
		return this.opparam;
	}

	public void setOpparam(String opparam) {
		this.opparam = opparam;
	}

	@Column(name = "opdatetime", length = 19)
	public Date getOpdatetime() {
		return this.opdatetime;
	}

	public void setOpdatetime(Date opdatetime) {
		this.opdatetime = opdatetime;
	}

}