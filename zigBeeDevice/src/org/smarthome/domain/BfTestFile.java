package org.smarthome.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BfTestFile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bf_test_file")
public class BfTestFile implements java.io.Serializable {

	// Fields

	private Integer id;
	private String fileTitle;
	private String fileLocation;
	private String fileType;
	private String fileSize;
	private String isDropped="0";
	private Integer targetId;
	private String targetType="bug";
	private Integer addActionId;
	private Integer deleteActionId;

	// Constructors

	/** default constructor */
	public BfTestFile() {
	}

	/** minimal constructor */
	public BfTestFile(String fileTitle, String fileLocation, String fileSize,
			String isDropped, Integer targetId, String targetType,
			Integer addActionId) {
		this.fileTitle = fileTitle;
		this.fileLocation = fileLocation;
		this.fileSize = fileSize;
		this.isDropped = isDropped;
		this.targetId = targetId;
		this.targetType = targetType;
		this.addActionId = addActionId;
	}

	/** full constructor */
	public BfTestFile(String fileTitle, String fileLocation, String fileType,
			String fileSize, String isDropped, Integer targetId,
			String targetType, Integer addActionId, Integer deleteActionId) {
		this.fileTitle = fileTitle;
		this.fileLocation = fileLocation;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.isDropped = isDropped;
		this.targetId = targetId;
		this.targetType = targetType;
		this.addActionId = addActionId;
		this.deleteActionId = deleteActionId;
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

	@Column(name = "file_title", nullable = false)
	public String getFileTitle() {
		return this.fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	@Column(name = "file_location", nullable = false, length = 65535)
	public String getFileLocation() {
		return this.fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Column(name = "file_type", length = 45)
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "file_size", nullable = false, length = 45)
	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "is_dropped", nullable = false, length = 2)
	public String getIsDropped() {
		return this.isDropped;
	}

	public void setIsDropped(String isDropped) {
		this.isDropped = isDropped;
	}

	@Column(name = "target_id", nullable = false)
	public Integer getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	@Column(name = "target_type", nullable = false, length = 6)
	public String getTargetType() {
		return this.targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	@Column(name = "add_action_id", nullable = false)
	public Integer getAddActionId() {
		return this.addActionId;
	}

	public void setAddActionId(Integer addActionId) {
		this.addActionId = addActionId;
	}

	@Column(name = "delete_action_id")
	public Integer getDeleteActionId() {
		return this.deleteActionId;
	}

	public void setDeleteActionId(Integer deleteActionId) {
		this.deleteActionId = deleteActionId;
	}

}