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
@Table(name = "z485device")
public class TemplateDevice implements java.io.Serializable {

	// Fields

	private Long id;
	private String deviceName;
	private int tempId;

	/** default constructor */
	public TemplateDevice() {
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

	@Column(name = "device_name", length = 30)
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "temp_id")
	public int getTempId() {
		return tempId;
	}

	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
}