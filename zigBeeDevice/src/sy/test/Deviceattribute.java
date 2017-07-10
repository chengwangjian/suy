package sy.test;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Deviceattribute entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "deviceattribute")
public class Deviceattribute implements java.io.Serializable {

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
	private Date lasttime;

	// Constructors

	/** default constructor */
	public Deviceattribute() {
	}

	/** full constructor */
	public Deviceattribute(String houseIeee, long roomId, String deviceIeee,
			String deviceEp, String clusterId, String attributeId,
			String attributeName, String value, Date lasttime) {
		this.houseIeee = houseIeee;
		this.roomId = roomId;
		this.deviceIeee = deviceIeee;
		this.deviceEp = deviceEp;
		this.clusterId = clusterId;
		this.attributeId = attributeId;
		this.attributeName = attributeName;
		this.value = value;
		this.lasttime = lasttime;
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

	@Column(name = "attributeName", length = 50)
	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@Column(name = "value", length = 50)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "lasttime", length = 19)
	public Date getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

}