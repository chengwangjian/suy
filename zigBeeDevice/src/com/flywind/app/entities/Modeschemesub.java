package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modeschemesub entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modeschemesub")
public class Modeschemesub implements java.io.Serializable,com.flywind.app.data.Intersub {

	// Fields

	private long id;
	private Long mid;
	private Long houseId;
	private Short sun;
	private Short mon;
	private Short tue;
	private Short wed;
	private Short thr;
	private Short fri;
	private Short sat;
	private String time;
	private String act;
	private Long dest;

	// Constructors

	/** default constructor */
	public Modeschemesub() {
	}

	/** full constructor */
	public Modeschemesub(Long mid, Long houseId, Short sun, Short mon,
			Short tue, Short wed, Short thr, Short fri, Short sat, String time,
			String act, Long dest) {
		this.mid = mid;
		this.houseId = houseId;
		this.sun = sun;
		this.mon = mon;
		this.tue = tue;
		this.wed = wed;
		this.thr = thr;
		this.fri = fri;
		this.sat = sat;
		this.time = time;
		this.act = act;
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

	@Override
	@Column(name = "MID")
	public Long getMid() {
		return this.mid;
	}

	@Override
	public void setMid(Long mid) {
		this.mid = mid;
	}

	@Column(name = "HouseID")
	public Long getHouseId() {
		return this.houseId;
	}

	@Override
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	@Column(name = "Sun")
	public Short getSun() {
		return this.sun;
	}

	public void setSun(Short sun) {
		this.sun = sun;
	}

	@Column(name = "Mon")
	public Short getMon() {
		return this.mon;
	}

	public void setMon(Short mon) {
		this.mon = mon;
	}

	@Column(name = "TUE")
	public Short getTue() {
		return this.tue;
	}

	public void setTue(Short tue) {
		this.tue = tue;
	}

	@Column(name = "WED")
	public Short getWed() {
		return this.wed;
	}

	public void setWed(Short wed) {
		this.wed = wed;
	}

	@Column(name = "THR")
	public Short getThr() {
		return this.thr;
	}

	public void setThr(Short thr) {
		this.thr = thr;
	}

	@Column(name = "FRI")
	public Short getFri() {
		return this.fri;
	}

	public void setFri(Short fri) {
		this.fri = fri;
	}

	@Column(name = "Sat")
	public Short getSat() {
		return this.sat;
	}

	public void setSat(Short sat) {
		this.sat = sat;
	}

	@Column(name = "Time", length = 20)
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "Act")
	public String getAct() {
		return this.act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	@Column(name = "Dest")
	public Long getDest() {
		return this.dest;
	}

	public void setDest(Long dest) {
		this.dest = dest;
	}

}