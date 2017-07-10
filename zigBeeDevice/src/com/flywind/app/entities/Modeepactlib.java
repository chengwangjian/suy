package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modeepactlib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "modeepactlib")
public class Modeepactlib implements java.io.Serializable {

	// Fields

	private Integer id;
	private String solidModelId;
	private String actId;

	// Constructors

	/** default constructor */
	public Modeepactlib() {
	}

	/** full constructor */
	public Modeepactlib(String solidModelId, String actId) {
		this.solidModelId = solidModelId;
		this.actId = actId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "SolidModelID", length = 20)
	public String getSolidModelId() {
		return this.solidModelId;
	}

	public void setSolidModelId(String solidModelId) {
		this.solidModelId = solidModelId;
	}

	@Column(name = "ActID", length = 20)
	public String getActId() {
		return this.actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

}