package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import sy.pageModel.PageBean;

/**
 * DeviceattributehistoryHouseidYear entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "deviceattributehistory_houseid_year")
public class DeviceattributehistoryHouseidYear extends PageBean implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long roomId;
	private String deviceIeee;
	private String deviceEp;
	private String clusterId;
	private String attributeId;
	private String attributeName;
	private String value;
	private Date opdatetime = new Date();
	
	
	// 非表字段
	private Date minOpdatetime;
	private Date maxOpdatetime;
	private String times; // 当前记录的操作时间与上一条记录操作时间的间隔秒数
	private String attributeNameCn; // 属性名中文
	private String starttime;  //开始时间
	private String endtime;   //结束时间
	private String lang;  //语言  

	// Constructors

	/** default constructor */
	public DeviceattributehistoryHouseidYear() {
	}

	/** full constructor */
	public DeviceattributehistoryHouseidYear(String houseIeee, long roomId,
			String deviceIeee, String deviceEp, String clusterId,
			String attributeId, String attributeName, String value,
			Date opdatetime) {
		this.houseIeee = houseIeee;
		this.roomId = roomId;
		this.deviceIeee = deviceIeee;
		this.deviceEp = deviceEp;
		this.clusterId = clusterId;
		this.attributeId = attributeId;
		this.attributeName = attributeName;
		this.value = value;
		this.opdatetime = opdatetime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "room_id")
	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "device_ieee", length = 50)
	public String getDeviceIeee() {
		return this.deviceIeee;
	}

	public void setDeviceIeee(String deviceIeee) {
		this.deviceIeee = deviceIeee;
	}

	@Column(name = "device_ep", length = 50)
	public String getDeviceEp() {
		return this.deviceEp;
	}

	public void setDeviceEp(String deviceEp) {
		this.deviceEp = deviceEp;
	}

	@Column(name = "cluster_id", length = 50)
	public String getClusterId() {
		return this.clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	@Column(name = "attribute_id", length = 50)
	public String getAttributeId() {
		return this.attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	@Column(name = "value", length = 50)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "opdatetime", length = 19)
	public Date getOpdatetime() {
		return this.opdatetime;
	}

	public void setOpdatetime(Date opdatetime) {
		this.opdatetime = opdatetime;
	}

	@Transient
	public Date getMinOpdatetime() {
		return minOpdatetime;
	}

	public void setMinOpdatetime(Date minOpdatetime) {
		this.minOpdatetime = minOpdatetime;
	}

	@Transient
	public Date getMaxOpdatetime() {
		return maxOpdatetime;
	}

	public void setMaxOpdatetime(Date maxOpdatetime) {
		this.maxOpdatetime = maxOpdatetime;
	}
	
	@Column(name = "attributeName", length = 50)
	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@Transient
	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	@Transient
	public String getAttributeNameCn() {
		return attributeNameCn;
	}

	public void setAttributeNameCn(String attributeNameCn) {
		this.attributeNameCn = attributeNameCn;
	}
	@Transient
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	@Transient
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	@Transient
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
	
}