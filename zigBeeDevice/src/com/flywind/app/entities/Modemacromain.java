package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Modemacromain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modemacromain")

@XStreamAlias("ID")
public class Modemacromain  implements java.io.Serializable,com.flywind.app.data.Intermain {

	// Fields

	private long id;
    private long houseId; 
	private long modeId;
	private String macroName;
	private String picName="";
	private Long oldId;
	// Constructors

	/** default constructor */
	public Modemacromain() {
	}

	/** full constructor */
	public Modemacromain(long houseId, long modeId, String macroName, String picName) {
		this.houseId=houseId;
		this.modeId = modeId;
		this.macroName = macroName;
		this.picName = picName;
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
 
    @Column(name="HouseID")

    public Long getHouseId() {
        return this.houseId;
    }
    
    @Override
	public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
	
	@Column(name = "ModeID")
	public long getModeId() {
		return this.modeId;
	}

	public void setModeId(long modeId) {
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

	@Override
	@Column(name="oldId")	
	public Long getOldId() {
		return oldId;
	}

	@Override
	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}
		
	
}