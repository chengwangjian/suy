package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Houseenergydevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "houseenergydevice")
public class Houseenergydevice implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private String deviceType; // 值0表示家庭用电，1表示空调用电，2表示照明用电，3表示家用电器，4表示其他用电
	private String ieee;
	private String ep;
	
	// 非表字段
	private String newIeee;
	private String newEp;

	// Constructors

	/** default constructor */
	public Houseenergydevice() {
	}

	/** full constructor */
	public Houseenergydevice(String houseIeee, String deviceType, String ieee,
			String ep) {
		this.houseIeee = houseIeee;
		this.deviceType = deviceType;
		this.ieee = ieee;
		this.ep = ep;
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

	@Column(name = "deviceType", length = 30)
	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "ieee", length = 50)
	public String getIeee() {
		return this.ieee;
	}

	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	@Column(name = "ep", length = 50)
	public String getEp() {
		return this.ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
	}

	@Transient
	public String getNewIeee() {
		return newIeee;
	}

	public void setNewIeee(String newIeee) {
		this.newIeee = newIeee;
	}

	@Transient
	public String getNewEp() {
		return newEp;
	}

	public void setNewEp(String newEp) {
		this.newEp = newEp;
	}
}