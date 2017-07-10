package com.flywind.app.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Modemacrocasc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modemacrocasc")
public class Modemacrocasc implements java.io.Serializable {

	// Fields

	private ModemacromainId id;
	private Long houseId;
	private Long modeId;
	private String macroName;
	private String picName;
	private String modeName;	
	private Long oldId;

	// Constructors

	/** default constructor */
	public Modemacrocasc() {
	}

	/** minimal constructor */
	public Modemacrocasc(ModemacromainId id) {
		this.id = id;
	}

	/** full constructor */
	public Modemacrocasc(ModemacromainId id, Long houseId, Long modeId,
			String macroName, String picName, Long oldId) {
		this.id = id;
		this.houseId = houseId;
		this.modeId = modeId;
		this.macroName = macroName;
		this.picName = picName;
		this.oldId = oldId;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false)),
			@AttributeOverride(name = "subId", column = @Column(name = "subID", nullable = false)) })
	public ModemacromainId getId() {
		return this.id;
	}

	public void setId(ModemacromainId id) {
		this.id = id;
	}

	@Column(name = "HouseID")
	public Long getHouseId() {
		return this.houseId;
	}

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

	@Column(name = "MacroName")
	public String getMacroName() {
		return this.macroName;
	}

	public void setMacroName(String macroName) {
		this.macroName = macroName;
	}

	@Column(name = "PicName")
	public String getPicName() {
		return this.picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "oldId")
	public Long getOldId() {
		return this.oldId;
	}

	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	
}