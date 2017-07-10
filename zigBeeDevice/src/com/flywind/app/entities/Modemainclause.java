package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modemainclause entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modemainclause")
public class Modemainclause implements java.io.Serializable {

	// Fields

	private long id;
	private short bactive;
	private short ballmodeActive;
	private long houseId;
    private Long oldId;

	// Constructors

	/** default constructor */
	public Modemainclause() {
	}

	/** full constructor */
	public Modemainclause(short bactive, short ballmodeActive, long houseId) {
		this.bactive = bactive;
		this.ballmodeActive = ballmodeActive;
		this.houseId = houseId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Column(name = "houseId")
	public long getHouseId() {
		return this.houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}
	
	@Column(name="oldId")	
	public Long getOldId() {
		return oldId;
	}

	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}

}