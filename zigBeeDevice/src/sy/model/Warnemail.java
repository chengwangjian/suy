package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Warnemail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "warnemail", catalog = "zigbeedevice")
public class Warnemail implements java.io.Serializable {

	// Fields

	private Integer id;
	private String houseIeee;
	private String warnEmail;

	// Constructors

	/** default constructor */
	public Warnemail() {
	}

	/** full constructor */
	public Warnemail(String houseIeee, String warnEmail) {
		this.houseIeee = houseIeee;
		this.warnEmail = warnEmail;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "warnEmail", length = 50)
	public String getWarnEmail() {
		return this.warnEmail;
	}

	public void setWarnEmail(String warnEmail) {
		this.warnEmail = warnEmail;
	}

}