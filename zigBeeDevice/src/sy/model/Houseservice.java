package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Houseservice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "houseservice")
public class Houseservice implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private Short videoService;
	private Short leaveHomeInform;
	private Short lqiOpen;
	private String lqiOpenTime;
	private Short minLqi;
	private Short deviceIsOnline;
	private Short monitorState;
	private String email;
	private String defaultemail;
	private String emaildescription;

	// Constructors

	/** default constructor */
	public Houseservice() {
	}

	/** full constructor */
	public Houseservice(String houseIeee, Short videoService,
			Short leaveHomeInform, Short lqiOpen, String lqiOpenTime,
			Short minLqi, Short deviceIsOnline, Short monitorState,
			String email, String defaultemail, String emaildescription) {
		this.houseIeee = houseIeee;
		this.videoService = videoService;
		this.leaveHomeInform = leaveHomeInform;
		this.lqiOpen = lqiOpen;
		this.lqiOpenTime = lqiOpenTime;
		this.minLqi = minLqi;
		this.deviceIsOnline = deviceIsOnline;
		this.monitorState = monitorState;
		this.email = email;
		this.defaultemail = defaultemail;
		this.emaildescription = emaildescription;
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

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "videoService")
	public Short getVideoService() {
		return this.videoService;
	}

	public void setVideoService(Short videoService) {
		this.videoService = videoService;
	}

	@Column(name = "leaveHomeInform")
	public Short getLeaveHomeInform() {
		return this.leaveHomeInform;
	}

	public void setLeaveHomeInform(Short leaveHomeInform) {
		this.leaveHomeInform = leaveHomeInform;
	}

	@Column(name = "lqi_open")
	public Short getLqiOpen() {
		return this.lqiOpen;
	}

	public void setLqiOpen(Short lqiOpen) {
		this.lqiOpen = lqiOpen;
	}

	@Column(name = "lqi_open_time", length = 10)
	public String getLqiOpenTime() {
		return this.lqiOpenTime;
	}

	public void setLqiOpenTime(String lqiOpenTime) {
		this.lqiOpenTime = lqiOpenTime;
	}

	@Column(name = "min_lqi")
	public Short getMinLqi() {
		return this.minLqi;
	}

	public void setMinLqi(Short minLqi) {
		this.minLqi = minLqi;
	}

	@Column(name = "device_is_online")
	public Short getDeviceIsOnline() {
		return this.deviceIsOnline;
	}

	public void setDeviceIsOnline(Short deviceIsOnline) {
		this.deviceIsOnline = deviceIsOnline;
	}

	@Column(name = "monitor_state")
	public Short getMonitorState() {
		return this.monitorState;
	}

	public void setMonitorState(Short monitorState) {
		this.monitorState = monitorState;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "defaultemail", length = 50)
	public String getDefaultemail() {
		return this.defaultemail;
	}

	public void setDefaultemail(String defaultemail) {
		this.defaultemail = defaultemail;
	}

	@Column(name = "emaildescription", length = 200)
	public String getEmaildescription() {
		return this.emaildescription;
	}

	public void setEmaildescription(String emaildescription) {
		this.emaildescription = emaildescription;
	}

}