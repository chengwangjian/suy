package zbEnergy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Energyhistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "energyhistory")
public class Energyhistory implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private String ieee;
	private String ep;
	private Date opdatetime;
	private double energyValue;
	private double energyPrice;

	// Constructors

	/** default constructor */
	public Energyhistory() {
	}

	/** full constructor */
	public Energyhistory(String houseIeee, String ieee, String ep,
			Date opdatetime, double energyValue, double energyPrice) {
		this.houseIeee = houseIeee;
		this.ieee = ieee;
		this.ep = ep;
		this.opdatetime = opdatetime;
		this.energyValue = energyValue;
		this.energyPrice = energyPrice;
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

	@Column(name = "IEEE", length = 50)
	public String getIeee() {
		return this.ieee;
	}

	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	@Column(name = "EP", length = 50)
	public String getEp() {
		return this.ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
	}

	@Column(name = "opdatetime", length = 19)
	public Date getOpdatetime() {
		return this.opdatetime;
	}

	public void setOpdatetime(Date opdatetime) {
		this.opdatetime = opdatetime;
	}

	@Column(name = "energyValue", precision = 22, scale = 0)
	public double getEnergyValue() {
		return this.energyValue;
	}

	public void setEnergyValue(double energyValue) {
		this.energyValue = energyValue;
	}

	@Column(name = "energyPrice", precision = 22, scale = 0)
	public double getEnergyPrice() {
		return this.energyPrice;
	}

	public void setEnergyPrice(double energyPrice) {
		this.energyPrice = energyPrice;
	}

}