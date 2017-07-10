package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Userorderroom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userorderroom")
public class Userorderroom implements java.io.Serializable {

	// Fields

	private long id;
	private long orderId;
	private long roomId;
	private String roomName;
	private String roomPic;
	private Date createtime = new Date();
	private Date lasttime = new Date();

	// Constructors

	/** default constructor */
	public Userorderroom() {
	}

	/** full constructor */
	public Userorderroom(long orderId, long roomId, String roomName,
			String roomPic, Date createtime, Date lasttime) {
		this.orderId = orderId;
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomPic = roomPic;
		this.createtime = createtime;
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

	@Column(name = "orderId")
	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "roomId")
	public long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "roomName", length = 50)
	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Column(name = "roomPic", length = 100)
	public String getRoomPic() {
		return this.roomPic;
	}

	public void setRoomPic(String roomPic) {
		this.roomPic = roomPic;
	}

	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "lasttime", length = 19)
	public Date getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

}