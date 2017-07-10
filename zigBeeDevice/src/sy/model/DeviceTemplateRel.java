package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * IRBrand entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "z485devicetempldaterel")
public class DeviceTemplateRel implements java.io.Serializable {

	// Fields

	private Long id;
	private String ieee;
	private String ep;
	private Long houseId;
	private Long deviceId;
	private String name;
	// Constructors

	/** default constructor */
	public DeviceTemplateRel() {
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ieee", length = 16)
	public String getIeee() {
		return ieee;
	}

	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	@Column(name = "ep", length = 5)
	public String getEp() {
		return ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
	}

	@Column(name = "house_id")
	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}
	
	@Column(name = "device_id")
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "name", length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}