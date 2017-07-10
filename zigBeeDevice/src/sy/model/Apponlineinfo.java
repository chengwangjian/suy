package sy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Apponlineinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "apponlineinfo")
public class Apponlineinfo implements java.io.Serializable {

	// Fields

	private long id;
	private String username;//用户名
	private String appOs;//操作系统
	private String appVersion;//版本号
	private String ipaddress;//ip
	private String houseIeee;//家的houseIeee
	private String isonline="0";//默认离线  0为离线 1为在线
	private String information;//描述信息
	 private String adurl;//广告地址
     private String readstate="0";//广告读取状态，1为已读，0为未读,默认未读
     private Date createtime = new Date(); // 创建时间
 	 private Date lasttime = new Date(); // APP访问网址时间
 	 private String title = "NETVOX SMART HOME";//标题默认为NETVOX SMART HOME
	
 	 @Column(name="title", length=100)
 	 public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// Constructors
     @Column(name="adurl", length=100)
	public String getAdurl() {
		return adurl;
	}

	public Apponlineinfo(long id, String username, String appOs,
			String appVersion, String ipaddress, String houseIeee,
			String isonline, String information, String adurl,
			String readstate, Date createtime, Date lasttime,String title) {
		this.id = id;
		this.username = username;
		this.appOs = appOs;
		this.appVersion = appVersion;
		this.ipaddress = ipaddress;
		this.houseIeee = houseIeee;
		this.isonline = isonline;
		this.information = information;
		this.adurl = adurl;
		this.readstate = readstate;
		this.createtime = createtime;
		this.lasttime = lasttime;
		this.title = title;
	}

	public void setAdurl(String adurl) {
		this.adurl = adurl;
	}
    @Column(name="readstate", length=2)
	public String getReadstate() {
		return readstate;
	}

	public void setReadstate(String readstate) {
		this.readstate = readstate;
	}
	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column(name = "lasttime", length = 19)
	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	/** default constructor */
	public Apponlineinfo() {
	}

	/** full constructor */
	public Apponlineinfo(String username, String appOs, String appVersion,
			String ipaddress, String houseIeee, String isonline,
			String information) {
		this.username = username;
		this.appOs = appOs;
		this.appVersion = appVersion;
		this.ipaddress = ipaddress;
		this.houseIeee = houseIeee;
		this.isonline = isonline;
		this.information = information;
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

	@Column(name = "username", length = 40)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "app_os", length = 20)
	public String getAppOs() {
		return this.appOs;
	}

	public void setAppOs(String appOs) {
		this.appOs = appOs;
	}

	@Column(name = "app_version", length = 20)
	public String getAppVersion() {
		return this.appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@Column(name = "IPaddress", length = 20)
	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	@Column(name = "houseIeee", length = 20)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "isonline", length = 50)
	public String getIsonline() {
		return this.isonline;
	}

	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}

	@Column(name = "information", length = 200)
	public String getInformation() {
		return this.information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

}