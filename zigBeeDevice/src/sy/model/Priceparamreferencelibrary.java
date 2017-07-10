package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Priceparamreferencelibrary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "priceparamreferencelibrary")
public class Priceparamreferencelibrary implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long energyId;
	private long energyFieldId;
	private long energyTimeId;
	private String name;
	private double price;
	private double priceDst;

	// Constructors

	/** default constructor */
	public Priceparamreferencelibrary() {
	}

	/** full constructor */
	public Priceparamreferencelibrary(String houseIeee, long energyId,
			long energyFieldId, long energyTimeId, String name, double price,
			double priceDst) {
		this.houseIeee = houseIeee;
		this.energyId = energyId;
		this.energyFieldId = energyFieldId;
		this.energyTimeId = energyTimeId;
		this.name = name;
		this.price = price;
		this.priceDst = priceDst;
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

	@Column(name = "energyId")
	public long getEnergyId() {
		return this.energyId;
	}

	public void setEnergyId(long energyId) {
		this.energyId = energyId;
	}

	@Column(name = "energyFieldId")
	public long getEnergyFieldId() {
		return this.energyFieldId;
	}

	public void setEnergyFieldId(long energyFieldId) {
		this.energyFieldId = energyFieldId;
	}

	@Column(name = "energyTimeId")
	public long getEnergyTimeId() {
		return this.energyTimeId;
	}

	public void setEnergyTimeId(long energyTimeId) {
		this.energyTimeId = energyTimeId;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "price", precision = 22, scale = 0)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "priceDST", precision = 22, scale = 0)
	public double getPriceDst() {
		return this.priceDst;
	}

	public void setPriceDst(double priceDst) {
		this.priceDst = priceDst;
	}

}