package org.smarthome.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Updatehistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "updatehistory")
public class Updatehistory implements java.io.Serializable {

	// Fields

	private long id;
	private Date updateTime;
	private String houseIeee;
	private String beforeVer;
	private String afterVer;
	private long releaseHistoryId;
	private String updateStatus;
	private String updateOperate;
	// Constructors

	/** default constructor */
	public Updatehistory() {
	}

	/** full constructor */
	public Updatehistory(Date updateTime, String houseIeee, String beforeVer,
			String afterVer, long releaseHistoryId) {
		this.updateTime = updateTime;
		this.houseIeee = houseIeee;
		this.beforeVer = beforeVer;
		this.afterVer = afterVer;
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

	@Column(name = "UpdateTime", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "HouseIEEE")
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "BeforeVer")
	public String getBeforeVer() {
		return this.beforeVer;
	}

	public void setBeforeVer(String beforeVer) {
		this.beforeVer = beforeVer;
	}

	@Column(name = "AfterVer")
	public String getAfterVer() {
		return this.afterVer;
	}

	public void setAfterVer(String afterVer) {
		this.afterVer = afterVer;
	}

	@Column(name = "ReleaseHistoryID")
	public long getReleaseHistoryId() {
		return this.releaseHistoryId;
	}

	public void setReleaseHistoryId(long releaseHistoryId) {
		this.releaseHistoryId = releaseHistoryId;
	}

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String getUpdateOperate() {
		return updateOperate;
	}

	public void setUpdateOperate(String updateOperate) {
		this.updateOperate = updateOperate;
	}

	
}