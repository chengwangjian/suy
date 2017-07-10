package zbHouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Room entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "room")
public class Room implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long roomId;
	private String roomName;
	private String roomPic;

	// Constructors

	/** default constructor */
	public Room() {
	}

	/** full constructor */
	public Room(String houseIeee, long roomId, String roomName, String roomPic) {
		this.houseIeee = houseIeee;
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomPic = roomPic;
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

	@Column(name = "roomName", length = 80)
	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Column(name = "roomPic", length = 200)
	public String getRoomPic() {
		return this.roomPic;
	}

	public void setRoomPic(String roomPic) {
		this.roomPic = roomPic;
	}

}