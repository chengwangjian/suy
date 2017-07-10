package zbHouse.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mode")
public class Mode implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long roomId;
	private long modeId;
	private String modeName;
	private String picName;
	private String description;
	private Date lastTime = new Date();

	// Constructors

	/** default constructor */
	public Mode() {
	}

	/** full constructor */
	public Mode(String houseIeee, long roomId, long modeId, String modeName,
			String picName, String description) {
		this.houseIeee = houseIeee;
		this.roomId = roomId;
		this.modeId = modeId;
		this.modeName = modeName;
		this.picName = picName;
		this.description = description;
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

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "roomId")
	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "modeId")
	public long getModeId() {
		return this.modeId;
	}

	public void setModeId(long modeId) {
		this.modeId = modeId;
	}

	@Column(name = "modeName", length = 80)
	public String getModeName() {
		return this.modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	@Column(name = "picName", length = 200)
	public String getPicName() {
		return this.picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "lastTime")	
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}	

}