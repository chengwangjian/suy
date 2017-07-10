package sy.test;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * House entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "house")
public class House implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private String name;
	private String longitude;
	private String latitude;
	private String networkAddress;
	private String port;
	private String description;
	private String enableFlag;
	private Date createtime;
	private Date lasttime;

	// Constructors

	/** default constructor */
	public House() {
	}

	/** full constructor */
	public House(String houseIeee, String name, String longitude,
			String latitude, String networkAddress, String port,
			String description, String enableFlag, Date createtime,
			Date lasttime) {
		this.houseIeee = houseIeee;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.networkAddress = networkAddress;
		this.port = port;
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

	@Column(name = "longitude", length = 20)
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", length = 20)
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "networkAddress", length = 50)
	public String getNetworkAddress() {
		return this.networkAddress;
	}

	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}

	@Column(name = "port", length = 10)
	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
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