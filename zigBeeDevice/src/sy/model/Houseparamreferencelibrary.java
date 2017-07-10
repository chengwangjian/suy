package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Houseparamreferencelibrary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "houseparamreferencelibrary")
public class Houseparamreferencelibrary implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long totalNumber;
	private double totalSquare;

	// Constructors

	/** default constructor */
	public Houseparamreferencelibrary() {
	}

	/** full constructor */
	public Houseparamreferencelibrary(String houseIeee, long totalNumber,
			double totalSquare) {
		this.houseIeee = houseIeee;
		this.totalNumber = totalNumber;
		this.totalSquare = totalSquare;
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

	@Column(name = "totalNumber")
	public long getTotalNumber() {
		return this.totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}

	@Column(name = "totalSquare", precision = 22, scale = 0)
	public double getTotalSquare() {
		return this.totalSquare;
	}

	public void setTotalSquare(double totalSquare) {
		this.totalSquare = totalSquare;
	}

}