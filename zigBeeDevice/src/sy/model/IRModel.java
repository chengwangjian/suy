package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * IRModel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "irmodel")
public class IRModel implements java.io.Serializable {

	// Fields

	private long id;
	private long brandId;
	private String modelName;
	private Integer controllType;
	private short checked;
	private Integer dlTimes = new Integer(0);
	private long checkUserId;
	private Integer fileSize;
	private Date uploadTime = new Date();
	private long uploadUserId;
	private String deviceStyle;//备用字段
	private String remoteControlStyle;//备用字段

	// Constructors

	/** default constructor */
	public IRModel() {
	}

	/** full constructor */
	public IRModel(long brandId, String modelName, Integer controllType,
			short checked, Integer dlTimes, long checkUserId, Integer fileSize,
			Date uploadTime, long uploadUserId, String deviceStyle,
			String remoteControlStyle) {
		this.brandId = brandId;
		this.modelName = modelName;
		this.controllType = controllType;
		this.checked = checked;
		this.dlTimes = dlTimes;
		this.checkUserId = checkUserId;
		this.fileSize = fileSize;
		this.uploadTime = uploadTime;
		this.uploadUserId = uploadUserId;
		this.deviceStyle = deviceStyle;
		this.remoteControlStyle = remoteControlStyle;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "brandId")
	public long getBrandId() {
		return this.brandId;
	}

	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	@Column(name = "modelName", length = 50)
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "controllType")
	public Integer getControllType() {
		return this.controllType;
	}

	public void setControllType(Integer controllType) {
		this.controllType = controllType;
	}

	@Column(name = "checked")
	public short getChecked() {
		return this.checked;
	}

	public void setChecked(short checked) {
		this.checked = checked;
	}

	@Column(name = "dlTimes")
	public Integer getDlTimes() {
		return this.dlTimes;
	}

	public void setDlTimes(Integer dlTimes) {
		this.dlTimes = dlTimes;
	}

	@Column(name = "checkUserId")
	public long getCheckUserId() {
		return this.checkUserId;
	}

	public void setCheckUserId(long checkUserId) {
		this.checkUserId = checkUserId;
	}

	@Column(name = "fileSize")
	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "uploadTime", length = 19)
	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name = "uploadUserId")
	public long getUploadUserId() {
		return this.uploadUserId;
	}

	public void setUploadUserId(long uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	@Column(name = "deviceStyle", length = 50)
	public String getDeviceStyle() {
		return this.deviceStyle;
	}

	public void setDeviceStyle(String deviceStyle) {
		this.deviceStyle = deviceStyle;
	}

	@Column(name = "remoteControlStyle", length = 50)
	public String getRemoteControlStyle() {
		return this.remoteControlStyle;
	}

	public void setRemoteControlStyle(String remoteControlStyle) {
		this.remoteControlStyle = remoteControlStyle;
	}

}