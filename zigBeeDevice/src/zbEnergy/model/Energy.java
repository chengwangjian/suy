package zbEnergy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Energy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "energy")
public class Energy implements java.io.Serializable {

	// Fields

	private long id;
	private String name;
	private String houseIeee;
	private String priceType;
	private boolean dstflag;
	private Date dstbeginTime;
	private Date dstendTime;
	private String regionCode;
	private long energyRefLibId;
	private boolean syncFlag;
	private boolean enabledFlag;

	// Constructors

	/** default constructor */
	public Energy() {
	}

	/** full constructor */
	public Energy(String name, String houseIeee, String priceType,
			boolean dstflag, Date dstbeginTime, Date dstendTime,
			String regionCode, long energyRefLibId, boolean syncFlag,
			boolean enabledFlag) {
		this.name = name;
		this.houseIeee = houseIeee;
		this.priceType = priceType;
		this.dstflag = dstflag;
		this.dstbeginTime = dstbeginTime;
		this.dstendTime = dstendTime;
		this.regionCode = regionCode;
		this.energyRefLibId = energyRefLibId;
		this.syncFlag = syncFlag;
		this.enabledFlag = enabledFlag;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "priceType", length = 2)
	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Column(name = "DSTFlag")
	public boolean getDstflag() {
		return this.dstflag;
	}

	public void setDstflag(boolean dstflag) {
		this.dstflag = dstflag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DSTbeginTime", length = 10)
	public Date getDstbeginTime() {
		return this.dstbeginTime;
	}

	public void setDstbeginTime(Date dstbeginTime) {
		this.dstbeginTime = dstbeginTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DSTendTime", length = 10)
	public Date getDstendTime() {
		return this.dstendTime;
	}

	public void setDstendTime(Date dstendTime) {
		this.dstendTime = dstendTime;
	}

	@Column(name = "regionCode", length = 30)
	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "energyRefLibId")
	public long getEnergyRefLibId() {
		return this.energyRefLibId;
	}

	public void setEnergyRefLibId(long energyRefLibId) {
		this.energyRefLibId = energyRefLibId;
	}

	@Column(name = "syncFlag")
	public boolean getSyncFlag() {
		return this.syncFlag;
	}

	public void setSyncFlag(boolean syncFlag) {
		this.syncFlag = syncFlag;
	}

	@Column(name = "enabledFlag")
	public boolean getEnabledFlag() {
		return this.enabledFlag;
	}

	public void setEnabledFlag(boolean enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

}