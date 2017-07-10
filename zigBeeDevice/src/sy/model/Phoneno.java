package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Phoneno entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "phoneno")
public class Phoneno implements java.io.Serializable {

	// Fields

	private Integer id;
	private String houseIeee;
	private String phoneNo;

	// Constructors

	/** default constructor */
	public Phoneno() {
	}

	/** full constructor */
	public Phoneno(String houseIeee, String phoneNo) {
		this.houseIeee = houseIeee;
		this.phoneNo = phoneNo;
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

	@Column(name = "phoneNO", length = 50)
	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

}