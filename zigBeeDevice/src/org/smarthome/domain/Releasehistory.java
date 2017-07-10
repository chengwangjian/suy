package org.smarthome.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Releasehistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "releasehistory")
public class Releasehistory implements java.io.Serializable {

	// Fields

	private long id;
	private Date updateTime=new Date();
	private String fileName;
	private Integer type;
	private Integer ver1;
	private Integer ver2;
	private Integer ver3;
	private Integer ver4;
	private String description;
	private String fileSize;
	private Integer notifier;
	private String downloadInfo;
	private String version;
	private String hwVersion;	
	private String md5;	
	private int status=1;
	// Constructors

	/** default constructor */
	public Releasehistory() {
	}

	/** full constructor */
	public Releasehistory(Date updateTime, String fileName, Integer type,
			Integer ver1, Integer ver2, Integer ver3, Integer ver4,
			String description, String fileSize, Integer notifier) {
		this.updateTime = updateTime;
		this.fileName = fileName;
		this.type = type;
		this.ver1 = ver1;
		this.ver2 = ver2;
		this.ver3 = ver3;
		this.ver4 = ver4;
		this.description = description;
		this.fileSize = fileSize;
		this.notifier = notifier;
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

	@Column(name = "FileName")
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "Type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "Ver1")
	public Integer getVer1() {
		return this.ver1;
	}

	public void setVer1(Integer ver1) {
		this.ver1 = ver1;
	}

	@Column(name = "Ver2")
	public Integer getVer2() {
		return this.ver2;
	}

	public void setVer2(Integer ver2) {
		this.ver2 = ver2;
	}

	@Column(name = "Ver3")
	public Integer getVer3() {
		return this.ver3;
	}

	public void setVer3(Integer ver3) {
		this.ver3 = ver3;
	}

	@Column(name = "Ver4")
	public Integer getVer4() {
		return this.ver4;
	}

	public void setVer4(Integer ver4) {
		this.ver4 = ver4;
	}

	@Column(name = "Description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "FileSize")
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "Notifier")
	public Integer getNotifier() {
		return this.notifier;
	}

	public void setNotifier(Integer notifier) {
		this.notifier = notifier;
	}

	public String getDownloadInfo() {
		return downloadInfo;
	}

	public void setDownloadInfo(String downloadInfo) {
		this.downloadInfo = downloadInfo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHwVersion() {
		return hwVersion;
	}

	public void setHwVersion(String hwVersion) {
		this.hwVersion = hwVersion;
	}
		
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Transient
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}