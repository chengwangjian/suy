package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Houseieee entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "houseieee")
public class Houseieee implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private String name;
	private String secretKey;
	private String vendorCode; // 默认值NETVOX
	private String vendorName;
	private String encodemethod; // 0为不加密，1为加密。数据库默认值0，bean上尽量不设默认值
	private String xmppIp; // 消息服务器地址
	private String xmppPort;
	private String cloudIp1; // 云端服务器地址1
	private String cloudPort1;
	private String cloudIp2; // 云端服务器地址2
	private String cloudPort2;
	private String description;
	private String enableFlag = "1"; // 1表示启用,0表示不启用。默认值1;
	private Date createtime = new Date();
	private Date lasttime = new Date();

	// Constructors

	/** default constructor */
	public Houseieee() {
	}

	/** full constructor */
	public Houseieee(String houseIeee, String name, String secretKey,
			String vendorCode, String vendorName, String encodemethod,
			String xmppIp, String xmppPort, String cloudIp1, String cloudPort1,
			String cloudIp2, String cloudPort2, String description,
			String enableFlag, Date createtime, Date lasttime) {
		this.houseIeee = houseIeee;
		this.name = name;
		this.secretKey = secretKey;
		this.vendorCode = vendorCode;
		this.vendorName = vendorName;
		this.encodemethod = encodemethod;
		this.xmppIp = xmppIp;
		this.xmppPort = xmppPort;
		this.cloudIp1 = cloudIp1;
		this.cloudPort1 = cloudPort1;
		this.cloudIp2 = cloudIp2;
		this.cloudPort2 = cloudPort2;
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
	
	@Column(name = "secretKey", length = 50)
	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Column(name = "vendorCode", length = 50)
	public String getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name = "vendorName", length = 50)
	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	@Column(name = "encodemethod", length = 20)
	public String getEncodemethod() {
		return this.encodemethod;
	}

	public void setEncodemethod(String encodemethod) {
		this.encodemethod = encodemethod;
	}
	
	@Column(name = "xmppIp", length = 200)
	public String getXmppIp() {
		return this.xmppIp;
	}

	public void setXmppIp(String xmppIp) {
		this.xmppIp = xmppIp;
	}

	@Column(name = "xmppPort", length = 200)
	public String getXmppPort() {
		return this.xmppPort;
	}

	public void setXmppPort(String xmppPort) {
		this.xmppPort = xmppPort;
	}

	@Column(name = "cloudIp1", length = 200)
	public String getCloudIp1() {
		return this.cloudIp1;
	}

	public void setCloudIp1(String cloudIp1) {
		this.cloudIp1 = cloudIp1;
	}

	@Column(name = "cloudPort1", length = 200)
	public String getCloudPort1() {
		return this.cloudPort1;
	}

	public void setCloudPort1(String cloudPort1) {
		this.cloudPort1 = cloudPort1;
	}

	@Column(name = "cloudIp2", length = 200)
	public String getCloudIp2() {
		return this.cloudIp2;
	}

	public void setCloudIp2(String cloudIp2) {
		this.cloudIp2 = cloudIp2;
	}

	@Column(name = "cloudPort2", length = 200)
	public String getCloudPort2() {
		return this.cloudPort2;
	}

	public void setCloudPort2(String cloudPort2) {
		this.cloudPort2 = cloudPort2;
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