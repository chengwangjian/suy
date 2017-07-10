package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Warnsend entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "warnsend", catalog = "zigbeedevice")
public class Warnsend implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private String username;
	private String deviceToken;
	private String type;
	private Date lastTime;
	private String isonline;
	private String language;
	private Integer footer;
	private Integer isLogout;

	// Constructors

	/** default constructor */
	public Warnsend() {
	}

	public Warnsend(long id, String houseIeee, String username,
			String deviceToken, String type, Date lastTime, String isonline,
			String language, Integer footer) {
		super();
		this.id = id;
		this.houseIeee = houseIeee;
		this.username = username;
		this.deviceToken = deviceToken;
		this.type = type;
		this.lastTime = lastTime;
		this.isonline = isonline;
		this.language = language;
		this.footer = footer;
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

	@Column(name = "username", length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "deviceToken", length = 150)
	public String getDeviceToken() {
		return this.deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Column(name = "type", length = 2)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "lastTime", length = 19)
	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Column(name = "isonline", length = 10)
	public String getIsonline() {
		return this.isonline;
	}

	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}

	@Column(name = "Language")
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "footer")
	public Integer getFooter() {
		return footer;
	}

	public void setFooter(Integer footer) {
		this.footer = footer;
	}

	@Column(name = "is_logout")
	public Integer getIsLogout() {
		return isLogout;
	}

	public void setIsLogout(Integer isLogout) {
		this.isLogout = isLogout;
	}
}