package org.smarthome.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Houseselect entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "hardhistory")
public class Hardhistory implements java.io.Serializable {

	// Fields

	private long id;
	private String hwVersion;
	private String extention;
	private long releaseHistoryId;
	private Date updateTime=new Date();
	private int status=1;
	// Constructors

	/** default constructor */
	public Hardhistory() {
	}

	/** full constructor */
	public Hardhistory(String hwVersion, String extention, long releaseHistoryId) {
		this.hwVersion = hwVersion;
		this.extention = extention;
		this.releaseHistoryId = releaseHistoryId;
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

	@Column(name = "HwVersion")
	public String getHwVersion() {
		return hwVersion;
	}

	public void setHwVersion(String hwVersion) {
		this.hwVersion = hwVersion;
	}

	@Column(name = "Extention")
	public String getExtention() {
		return this.extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	@Column(name = "ReleaseHistoryID")
	public long getReleaseHistoryId() {
		return this.releaseHistoryId;
	}

	public void setReleaseHistoryId(long releaseHistoryId) {
		this.releaseHistoryId = releaseHistoryId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Transient
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}