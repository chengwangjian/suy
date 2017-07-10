package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Usermodedevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "usermodedevice")
public class Usermodedevice implements	java.io.Serializable {

	// Fields

	private long id;
	private long mid;
	private long houseId;
	private long dest;
    private long oldDestId;
	// Constructors

	/** default constructor */
	public Usermodedevice() {
	}

	/** full constructor */
	public Usermodedevice(long mid, long houseId, long dest) {
		this.mid = mid;
		this.houseId = houseId;
		this.dest = dest;
	}
	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "MID")
	public long getMid() {
		return this.mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	@Column(name = "HouseID")
	public long getHouseId() {
		return this.houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	@Column(name = "Dest")
	public long getDest() {
		return this.dest;
	}

	public void setDest(long dest) {
		this.dest = dest;
	}

	@Column(name = "oldDestId")
	public long getOldDestId() {
		return oldDestId;
	}

	public void setOldDestId(long oldDestId) {
		this.oldDestId = oldDestId;
	}
}
