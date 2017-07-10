package org.smarthome.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AbstractBfBugInfo entity provides the base persistence definition of the
 * BfBugInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bf_bug_info")
public class BfBugInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Date createdAt=new Date();
	private Integer createdBy=10;
	private Date updatedAt=new Date();
	private Integer updatedBy=10;
	private String bugStatus="Active";
	private Integer assignTo=10;
	private String title="";
	private String mailTo="10";
	private String repeatStep="";
	private short lockVersion;
	private Date resolvedAt;
	private Integer resolvedBy;
	private Date closedAt;
	private Integer closedBy;
	private String relatedBug;
	private String relatedCase;
	private String relatedResult;
	private Integer productmoduleId;
	private String modifiedBy="10";
	private String solution="用户反馈";
	private String duplicateId;
	private Integer productId=2;
	private Integer reopenCount=0;
	private short priority;
	private short severity;

	// Constructors

	/** default constructor */
	public BfBugInfo() {
	}

	/** full constructor */
	public BfBugInfo(Integer productId, Date createdAt,
			Integer createdBy, Date updatedAt, Integer updatedBy,
			String bugStatus, Integer assignTo, String title, String mailTo,
			String repeatStep, short lockVersion, Date resolvedAt,
			Integer resolvedBy, Date closedAt, Integer closedBy,
			String relatedBug, String relatedCase, String relatedResult,
			Integer productmoduleId, String modifiedBy, String solution,
			String duplicateId, Integer reopenCount, short priority,
			short severity) {
		this.productId = productId;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.bugStatus = bugStatus;
		this.assignTo = assignTo;
		this.title = title;
		this.mailTo = mailTo;
		this.repeatStep = repeatStep;
		this.lockVersion = lockVersion;
		this.resolvedAt = resolvedAt;
		this.resolvedBy = resolvedBy;
		this.closedAt = closedAt;
		this.closedBy = closedBy;
		this.relatedBug = relatedBug;
		this.relatedCase = relatedCase;
		this.relatedResult = relatedResult;
		this.productmoduleId = productmoduleId;
		this.modifiedBy = modifiedBy;
		this.solution = solution;
		this.duplicateId = duplicateId;
		this.reopenCount = reopenCount;
		this.priority = priority;
		this.severity = severity;
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

	@Column(name = "product_id", nullable = false)
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	@Column(name = "updated_at", nullable = false, length = 19)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "updated_by", nullable = false)
	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "bug_status", nullable = false, length = 45)
	public String getBugStatus() {
		return this.bugStatus;
	}

	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}

	@Column(name = "assign_to")
	public Integer getAssignTo() {
		return this.assignTo;
	}

	public void setAssignTo(Integer assignTo) {
		this.assignTo = assignTo;
	}

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "mail_to", length = 65535)
	public String getMailTo() {
		return this.mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	@Column(name = "repeat_step", length = 65535)
	public String getRepeatStep() {
		return this.repeatStep;
	}

	public void setRepeatStep(String repeatStep) {
		this.repeatStep = repeatStep;
	}

	@Column(name = "lock_version", nullable = false)
	public short getLockVersion() {
		return this.lockVersion;
	}

	public void setLockVersion(short lockVersion) {
		this.lockVersion = lockVersion;
	}

	@Column(name = "resolved_at", length = 19)
	public Date getResolvedAt() {
		return this.resolvedAt;
	}

	public void setResolvedAt(Date resolvedAt) {
		this.resolvedAt = resolvedAt;
	}

	@Column(name = "resolved_by")
	public Integer getResolvedBy() {
		return this.resolvedBy;
	}

	public void setResolvedBy(Integer resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	@Column(name = "closed_at", length = 19)
	public Date getClosedAt() {
		return this.closedAt;
	}

	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}

	@Column(name = "closed_by")
	public Integer getClosedBy() {
		return this.closedBy;
	}

	public void setClosedBy(Integer closedBy) {
		this.closedBy = closedBy;
	}

	@Column(name = "related_bug")
	public String getRelatedBug() {
		return this.relatedBug;
	}

	public void setRelatedBug(String relatedBug) {
		this.relatedBug = relatedBug;
	}

	@Column(name = "related_case")
	public String getRelatedCase() {
		return this.relatedCase;
	}

	public void setRelatedCase(String relatedCase) {
		this.relatedCase = relatedCase;
	}

	@Column(name = "related_result")
	public String getRelatedResult() {
		return this.relatedResult;
	}

	public void setRelatedResult(String relatedResult) {
		this.relatedResult = relatedResult;
	}

	@Column(name = "productmodule_id")
	public Integer getProductmoduleId() {
		return this.productmoduleId;
	}

	public void setProductmoduleId(Integer productmoduleId) {
		this.productmoduleId = productmoduleId;
	}

	@Column(name = "modified_by", nullable = false, length = 65535)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "solution", length = 45)
	public String getSolution() {
		return this.solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	@Column(name = "duplicate_id")
	public String getDuplicateId() {
		return this.duplicateId;
	}

	public void setDuplicateId(String duplicateId) {
		this.duplicateId = duplicateId;
	}

	@Column(name = "reopen_count", nullable = false)
	public Integer getReopenCount() {
		return this.reopenCount;
	}

	public void setReopenCount(Integer reopenCount) {
		this.reopenCount = reopenCount;
	}

	@Column(name = "priority")
	public short getPriority() {
		return this.priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	@Column(name = "severity", nullable = false)
	public short getSeverity() {
		return this.severity;
	}

	public void setSeverity(short severity) {
		this.severity = severity;
	}


}