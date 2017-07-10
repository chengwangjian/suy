package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modeschememain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modeschememain")
public class Modeschememain implements java.io.Serializable,com.flywind.app.data.Intermain {

	// Fields

	private long id;
	private Long houseId;
	private Long modeId;
	private String schemeName;
	private String picName;
	private short bactive=1;
	private short ballmodeActive;
	private Long oldId;

	// Constructors

	/** default constructor */
	public Modeschememain() {
	}

	/** full constructor */
	public Modeschememain(Long houseId, Long modeId, String schemeName,
			String picName, short bactive, short ballmodeActive, Long oldId) {
		this.houseId = houseId;
		this.modeId = modeId;
		this.schemeName = schemeName;
		this.picName = picName;
		this.bactive = bactive;
		this.ballmodeActive = ballmodeActive;
		this.oldId = oldId;
	}

	// Property accessors
	@Override
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "HouseID")
	public Long getHouseId() {
		return this.houseId;
	}

	@Override
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	@Column(name = "ModeID")
	public Long getModeId() {
		return this.modeId;
	}

	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}

	@Column(name = "SchemeName")
	public String getSchemeName() {
		return this.schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	@Column(name = "PicName")
	public String getPicName() {
		return this.picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "bActive")
	public short getBactive() {
		return this.bactive;
	}

	public void setBactive(short bactive) {
		this.bactive = bactive;
	}

	@Column(name = "bAllmodeActive")
	public short getBallmodeActive() {
		return this.ballmodeActive;
	}

	public void setBallmodeActive(short ballmodeActive) {
		this.ballmodeActive = ballmodeActive;
	}

	@Override
	@Column(name = "oldId")
	public Long getOldId() {
		return this.oldId;
	}

	@Override
	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}

}