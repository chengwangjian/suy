package org.smarthome.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zonewarntypetable")
public class Zonewarntypetable implements java.io.Serializable {

	// Fields
	private Integer id;
	private String chinesewarnType;
	private String englishwarnType;
	private Integer WMode;
	private String sensortype;
	

	// Constructors
	public Zonewarntypetable() {}
	
	public Zonewarntypetable(String chinesewarnType, String englishwarnType, Integer WMode) {
		this.chinesewarnType = chinesewarnType;
		this.englishwarnType = englishwarnType;
		this.WMode = WMode;
	}


	public Zonewarntypetable(Integer id, String chinesewarnType,
			String englishwarnType, Integer wMode, String sensortype) {
		super();
		this.id = id;
		this.chinesewarnType = chinesewarnType;
		this.englishwarnType = englishwarnType;
		this.WMode = wMode;
		this.sensortype = sensortype;
	}

	// Property accessors
	@Id @GeneratedValue(strategy=IDENTITY)

	@Column(name="id", unique=true, nullable=false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ChinesewarnType")

	public String getChinesewarnType() {
		return this.chinesewarnType;
	}

	public void setChinesewarnType(String chinesewarnType) {
		this.chinesewarnType = chinesewarnType;
	}

	@Column(name="EnglishwarnType")

	public String getEnglishwarnType() {
		return this.englishwarnType;
	}

	public void setEnglishwarnType(String englishwarnType) {
		this.englishwarnType = englishwarnType;
	}

	@Column(name="w_mode")
	public Integer getWMode() {
		return this.WMode;
	}

	public void setWMode(Integer WMode) {
		this.WMode = WMode;
	}
	@Column(name="sensortype")
	public String getSensortype() {
		return sensortype;
	}

	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}
}

