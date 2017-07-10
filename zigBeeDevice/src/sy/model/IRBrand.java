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
@Table(name = "irbrand")
public class IRBrand implements java.io.Serializable {

	// Fields

	private long id;
	private String brandName;
	private String enbrandName;
	private String region;//地区
	private String firstchar;//品牌首字母

	// Constructors

	/** default constructor */
	public IRBrand() {
	}

	/** full constructor */
	public IRBrand(String brandName, String enbrandName, String region,
			String firstchar) {
		this.brandName = brandName;
		this.enbrandName = enbrandName;
		this.region = region;
		this.firstchar = firstchar;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "brandName", length = 50)
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name = "enbrandName", length = 100)
	public String getEnbrandName() {
		return this.enbrandName;
	}

	public void setEnbrandName(String enbrandName) {
		this.enbrandName = enbrandName;
	}

	@Column(name = "region", length = 100)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "firstchar", length = 20)
	public String getFirstchar() {
		return this.firstchar;
	}

	public void setFirstchar(String firstchar) {
		this.firstchar = firstchar;
	}

}