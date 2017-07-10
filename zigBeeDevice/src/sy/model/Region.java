package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Region entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "region")
public class Region implements java.io.Serializable {

	// Fields

	private long id;
	private String regionCode;
	private String regionName;
	private String description;

	// Constructors

	/** default constructor */
	public Region() {
	}

	/** full constructor */
	public Region(String regionCode, String regionName, String description) {
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.description = description;
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

	@Column(name = "regionCode", length = 30)
	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "regionName", length = 50)
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}