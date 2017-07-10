package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * IRData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "irtempdata")
public class IRTempData implements java.io.Serializable {

	// Fields

	private long id;
	private String brandName;
	private String modelName;
	private String houseIEEE;
	private String deviceIEEE;
	private String ep;
	private Integer irType; 
	private String fileName;
	private String irUID;
	private Integer checked;

	// Constructors

	/** default constructor */
	public IRTempData() {
	}

	/** full constructor */
	public IRTempData(long id, String brandName, String modelName,
			String houseIEEE, String deviceIEEE, String ep, Integer irType,
			String fileName, String irUID, Integer checked) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.modelName = modelName;
		this.houseIEEE = houseIEEE;
		this.deviceIEEE = deviceIEEE;
		this.ep = ep;
		this.irType = irType;
		this.fileName = fileName;
		this.irUID = irUID;
		this.checked = checked;
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

	@Column(name = "modelName", length = 200)
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "houseIEEE", length = 30)
	public String getHouseIEEE() {
		return this.houseIEEE;
	}

	public void setHouseIEEE(String houseIEEE) {
		this.houseIEEE = houseIEEE;
	}

	@Column(name = "deviceIEEE", length = 30)
	public String getDeviceIEEE() {
		return this.deviceIEEE;
	}

	public void setDeviceIEEE(String deviceIEEE) {
		this.deviceIEEE = deviceIEEE;
	}

	@Column(name = "ep", length = 10)
	public String getEp() {
		return this.ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
	}

	@Column(name = "irType")
	public Integer getIrType() {
		return this.irType;
	}

	public void setIrType(Integer irType) {
		this.irType = irType;
	}

	@Column(name = "fileName", length = 50)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "irUID", length = 20)
	public String getIrUID() {
		return this.irUID;
	}

	public void setIrUID(String irUID) {
		this.irUID = irUID;
	}

	@Column(name = "checked")
	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

}