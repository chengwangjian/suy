package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Modehouse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modehouse")
public class Modehouse implements java.io.Serializable {

	// Fields

	
	private long id;
	private long userId;
	private String hosueGuid;
	private String houseIeee;
	private String houseName;
	private Integer roomNums;
	private String description;
	private String cloudIp;
	private Date createtime = new Date();
	private Date lasttime = new Date();	
	private String xmlVersion;


	// Constructors

	/** default constructor */
	public Modehouse() {
	}

	/** full constructor */
	public Modehouse(long userId, String hosueGuid,String houseIeee, String houseName,
			Integer roomNums, String description, String cloudIp, Date createtime, Date lasttime,String xmlVersion) {
		this.userId = userId;
		this.hosueGuid = hosueGuid;
		this.houseIeee = houseIeee;
		this.houseName = houseName;
		this.roomNums = roomNums;
		this.description = description;
		this.cloudIp = cloudIp;
		this.xmlVersion = xmlVersion;
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

	@Column(name = "userId")
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}
	
	@Column(name = "hosueGuid", length = 80)
	public String getHosueGuid() {
		return hosueGuid;
	}

	public void setHosueGuid(String hosueGuid) {
		this.hosueGuid = hosueGuid;
	}

	@Column(name = "houseName", length = 50)
	public String getHouseName() {
		return this.houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	@Column(name = "roomNums")
	public Integer getRoomNums() {
		return this.roomNums;
	}

	public void setRoomNums(Integer roomNums) {
		this.roomNums = roomNums;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "cloudIp", length = 200)
	public String getCloudIp() {
		return cloudIp;
	}

	public void setCloudIp(String cloudIp) {
		this.cloudIp = cloudIp;
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

	@Column(name = "xmlVersion", length = 200)
	public String getXmlVersion() {
		return xmlVersion;
	}

	public void setXmlVersion(String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}
	
}