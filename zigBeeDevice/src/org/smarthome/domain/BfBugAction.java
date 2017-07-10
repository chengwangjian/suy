package org.smarthome.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BfBugAction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bf_bug_action")
public class BfBugAction implements java.io.Serializable {

	// Fields

	private Integer id;
	private Date createdAt=new Date();
	private Integer createdBy=10;
	private String actionType="opened";
	private String actionNote="android_action";
	private Integer bugInfoId;

	// Constructors

	/** default constructor */
	public BfBugAction() {
	}

	/** full constructor */
	public BfBugAction(Date createdAt, Integer createdBy,
			String actionType, String actionNote,Integer bugInfoId) {
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.actionType = actionType;
		this.actionNote = actionNote;
		this.bugInfoId = bugInfoId;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "created_at", nullable = false, length = 19)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "created_by", nullable = false)
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "action_type", nullable = false)
	public String getActionType() {
		return this.actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	@Column(name = "action_note", length = 65535)
	public String getActionNote() {
		return this.actionNote;
	}

	public void setActionNote(String actionNote) {
		this.actionNote = actionNote;
	}
	
	@Column(name = "buginfo_id", nullable = false)
	public Integer getBugInfoId() {
		return bugInfoId;
	}

	public void setBugInfoId(Integer bugInfoId) {
		this.bugInfoId = bugInfoId;
	}



}