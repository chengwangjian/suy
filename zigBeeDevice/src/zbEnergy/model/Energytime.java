package zbEnergy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Energytime entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "energytime")
public class Energytime implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long energyId;
	private long energyTimeId;
	private String name;
	private Date beginTime;
	private Date endTime;

	// Constructors

	/** default constructor */
	public Energytime() {
	}

	/** full constructor */
	public Energytime(String houseIeee, long energyId, long energyTimeId,
			String name, Date beginTime, Date endTime) {
		this.houseIeee = houseIeee;
		this.energyId = energyId;
		this.energyTimeId = energyTimeId;
		this.name = name;
		this.beginTime = beginTime;
		this.endTime = endTime;
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

	@Column(name = "beginTime", length = 19)
	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "endTime", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}