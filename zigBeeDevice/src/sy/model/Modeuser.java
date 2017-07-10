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
 * Modeuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modeuser")
public class Modeuser implements java.io.Serializable {

	// Fields

	private long id;
	private String userName;
	private String pwd;
	private String enabled = "1"; // 1表示启用,0表示不启用。默认值1;
	private Date registTime = new Date();
	private Date lasttime = new Date();
	private int levelId = 3; // 默认值普通用户
	private String phoneNumber; //手机号码
	
	// 非表字段
	private String verifyCode;
	private String language;

	// Constructors

	/** default constructor */
	public Modeuser() {
	}

	/** full constructor */
	public Modeuser(String userName, String pwd, String enabled,
			Date registTime, Date lasttime, String phoneNumber) {
		this.userName = userName;
		this.pwd = pwd;
		this.enabled = enabled;
		this.registTime = registTime;
		this.lasttime = lasttime;
		this.phoneNumber = phoneNumber;
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

	@Column(name = "userName", length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "pwd", length = 40)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "enabled", length = 2)
	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "registTime", length = 19)
	public Date getRegistTime() {
		return this.registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	@Column(name = "lasttime", length = 19)
	public Date getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	@Column(name = "level_id")
	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	@Transient
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Transient
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Transient
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}