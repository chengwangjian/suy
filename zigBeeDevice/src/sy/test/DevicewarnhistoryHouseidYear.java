package sy.test;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * DevicewarnhistoryHouseidYear entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "devicewarnhistory_houseid_year")
public class DevicewarnhistoryHouseidYear implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long roomId;
	private String zoneIeee;
	private String zoneEp;
	private String cieIeee;
	private String cieEp;
	private String WMode;
	private String WDescription;
	private Date warndatetime;
	private String sendState;

	// Constructors

	/** default constructor */
	public DevicewarnhistoryHouseidYear() {
	}

	/** full constructor */
	public DevicewarnhistoryHouseidYear(String houseIeee, long roomId,
			String zoneIeee, String zoneEp, String cieIeee, String cieEp,
			String WMode, String WDescription, Date warndatetime,
			String sendState) {
		this.houseIeee = houseIeee;
		this.roomId = roomId;
		this.zoneIeee = zoneIeee;
		this.zoneEp = zoneEp;
		this.cieIeee = cieIeee;
		this.cieEp = cieEp;
		this.WMode = WMode;
		this.WDescription = WDescription;
		this.warndatetime = warndatetime;
		this.sendState = sendState;
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

	@Column(name = "zone_ieee", length = 50)
	public String getZoneIeee() {
		return this.zoneIeee;
	}

	public void setZoneIeee(String zoneIeee) {
		this.zoneIeee = zoneIeee;
	}

	@Column(name = "zone_ep", length = 50)
	public String getZoneEp() {
		return this.zoneEp;
	}

	public void setZoneEp(String zoneEp) {
		this.zoneEp = zoneEp;
	}

	@Column(name = "cie_ieee", length = 50)
	public String getCieIeee() {
		return this.cieIeee;
	}

	public void setCieIeee(String cieIeee) {
		this.cieIeee = cieIeee;
	}

	@Column(name = "cie_ep", length = 50)
	public String getCieEp() {
		return this.cieEp;
	}

	public void setCieEp(String cieEp) {
		this.cieEp = cieEp;
	}

	@Column(name = "w_mode", length = 100)
	public String getWMode() {
		return this.WMode;
	}

	public void setWMode(String WMode) {
		this.WMode = WMode;
	}

	@Column(name = "w_description", length = 1000)
	public String getWDescription() {
		return this.WDescription;
	}

	public void setWDescription(String WDescription) {
		this.WDescription = WDescription;
	}

	@Column(name = "warndatetime", length = 19)
	public Date getWarndatetime() {
		return this.warndatetime;
	}

	public void setWarndatetime(Date warndatetime) {
		this.warndatetime = warndatetime;
	}

	@Column(name = "sendState", length = 2)
	public String getSendState() {
		return this.sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}

}