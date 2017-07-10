package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Proxyserver entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "proxyserver")
public class Proxyserver implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private String name;
	private String serverIp;
	private String serverPort;
	private String type; // 0表示获取xmpp消息服务器的通讯地址的类型，1表示获取云端服务器地址的类型，2表示获取xmpp消息服务器的web应用的地址的类型。默认值0
	private String secondType; // 0表示主服务器，1表示第一个次服务器。2表示第二个次服务器。依次类推。默认值0。默认可以不传这个值。
	private String description;
	private String enableFlag = "1"; // 1表示启用,0表示不启用。默认值1
	private Date createtime = new Date();
	private Date lasttime = new Date();

	// Constructors

	/** default constructor */
	public Proxyserver() {
	}

	/** full constructor */
	public Proxyserver(String houseIeee, String name, String serverIp,
			String serverPort, String type, String secondType,
			String description, String enableFlag, Date createtime,
			Date lasttime) {
		this.houseIeee = houseIeee;
		this.name = name;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.type = type;
		this.secondType = secondType;
		this.description = description;
		this.enableFlag = enableFlag;
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

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "serverIp", length = 200)
	public String getServerIp() {
		return this.serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	@Column(name = "serverPort", length = 200)
	public String getServerPort() {
		return this.serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	@Column(name = "type", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "secondType", length = 20)
	public String getSecondType() {
		return this.secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "enableFlag", length = 2)
	public String getEnableFlag() {
		return this.enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
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