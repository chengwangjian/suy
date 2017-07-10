package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import sy.pageModel.PageBean;

/**
 * DevicewarnhistoryHouseidYear entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "devicewarnhistory_houseid_year")
public class DevicewarnhistoryHouseidYear extends PageBean implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long roomId;
	private String zoneIeee;
	private String zoneEp;
	private String cieIeee;
	private String cieEp;
	private String WMode;
	private String WDescription;
	private Date warndatetime = new Date();
	private String sendState = "0"; // 0表示告警消息未推送(默认值)，1表示已推送。
	private Integer message_id;
	private int hanlde_flag = 0;//默认未警告0，已警告未1
	
	// 非表字段
	private String sensortype;
	private Date minWarndatetime;
	private Date maxWarndatetime;
	private String deviceName;
	private String roomName;
	private String name;
	private String starttime;
	private String endtime;
	// Constructors

	/** default constructor */
	public DevicewarnhistoryHouseidYear() {
	}

	/** full constructor */
	public DevicewarnhistoryHouseidYear(String houseIeee, long roomId,
			String zoneIeee, String zoneEp, String cieIeee, String cieEp,
			String WMode, String WDescription, Date warndatetime,
			String sendState,Integer message_id) {
		this.houseIeee = houseIeee;
		this.roomId = roomId;
		this.zoneIeee = zoneIeee;
		this.zoneEp = zoneEp;
		this.cieIeee = cieIeee;
		this.cieEp = cieEp;
		this.WMode = WMode;
		this.WDescription = WDescription;
		this.warndatetime = warndatetime;
		this.sendState = sendState;
		this.message_id = message_id;
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

	@Column(name = "room_id")
	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "zone_ieee", length = 50)
	public String getZoneIeee() {
		return this.zoneIeee;
	}

	public void setZoneIeee(String zoneIeee) {
		this.zoneIeee = zoneIeee;
	}

	@Column(name = "zone_ep", length = 50)
	public String getZoneEp() {
		return this.zoneEp;
	}

	public void setZoneEp(String zoneEp) {
		this.zoneEp = zoneEp;
	}

	@Column(name = "cie_ieee", length = 50)
	public String getCieIeee() {
		return this.cieIeee;
	}

	public void setCieIeee(String cieIeee) {
		this.cieIeee = cieIeee;
	}

	@Column(name = "cie_ep", length = 50)
	public String getCieEp() {
		return this.cieEp;
	}

	public void setCieEp(String cieEp) {
		this.cieEp = cieEp;
	}

	@Column(name = "w_mode", length = 100)
	public String getWMode() {
		return this.WMode;
	}

	public void setWMode(String WMode) {
		this.WMode = WMode;
	}

	@Column(name = "w_description", length = 1000)
	public String getWDescription() {
		return this.WDescription;
	}

	public void setWDescription(String WDescription) {
		this.WDescription = WDescription;
	}

	@Column(name = "warndatetime", length = 19)
	public Date getWarndatetime() {
		return this.warndatetime;
	}

	public void setWarndatetime(Date warndatetime) {
		this.warndatetime = warndatetime;
	}

	@Transient
	public Date getMinWarndatetime() {
		return minWarndatetime;
	}

	public void setMinWarndatetime(Date minWarndatetime) {
		this.minWarndatetime = minWarndatetime;
	}

	@Transient
	public Date getMaxWarndatetime() {
		return maxWarndatetime;
	}

	public void setMaxWarndatetime(Date maxWarndatetime) {
		this.maxWarndatetime = maxWarndatetime;
	}
	
	@Column(name = "sendState", length = 2)
	public String getSendState() {
		return this.sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	@Transient
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	@Transient
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	@Column(name="message_id")
	public Integer getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Integer message_id) {
		this.message_id = message_id;
	}
	@Transient
	public String getSensortype() {
		return sensortype;
	}

	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	@Transient
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	@Column(name="hanlde_flag")
	public int getHanld_flag() {
		return hanlde_flag;
	}
   
	public void setHanld_flag(int hanld_flag) {
		this.hanlde_flag = hanld_flag;
	}
	
}