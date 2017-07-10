package zbEnergy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Energyfield entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "energyfield")
public class Energyfield implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long energyId;
	private long energyFieldId;
	private String name;
	private double startField;
	private double endField;

	// Constructors

	/** default constructor */
	public Energyfield() {
	}

	/** full constructor */
	public Energyfield(String houseIeee, long energyId, long energyFieldId,
			String name, double startField, double endField) {
		this.houseIeee = houseIeee;
		this.energyId = energyId;
		this.energyFieldId = energyFieldId;
		this.name = name;
		this.startField = startField;
		this.endField = endField;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "startField", precision = 22, scale = 0)
	public double getStartField() {
		return this.startField;
	}

	public void setStartField(double startField) {
		this.startField = startField;
	}

	@Column(name = "endField", precision = 22, scale = 0)
	public double getEndField() {
		return this.endField;
	}

	public void setEndField(double endField) {
		this.endField = endField;
	}

}