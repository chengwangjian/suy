package zbHouse.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Node entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "node")
public class Node implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee;
	private long roomId;
	private String ieee;
	private String type;
	private String ip;
	private long nodeId;
	private short nodeType;
	private String nodeName;	
	private Date createtime = new Date(); // 创建时间
	private Date lasttime = new Date(); // 更新时间

	// Constructors

	/** default constructor */
	public Node() {
	}

	/** full constructor */
	public Node(String houseIeee, long roomId, String ieee, String type,
			String ip, long nodeId, short nodeType, String nodeName,Date createtime,
			Date lasttime) {
		this.houseIeee = houseIeee;
		this.roomId = roomId;
		this.ieee = ieee;
		this.type = type;
		this.ip = ip;
		this.nodeId = nodeId;
		this.nodeType = nodeType;
		this.nodeName = nodeName;
		this.createtime = createtime;
		this.lasttime = lasttime;
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

	@Column(name = "ieee", length = 50)
	public String getIeee() {
		return this.ieee;
	}

	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	@Column(name = "type", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "ip", length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "nodeId")
	public long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "nodeType")
	public short getNodeType() {
		return nodeType;
	}

	public void setNodeType(short nodeType) {
		this.nodeType = nodeType;
	}

	@Column(name = "nodeName", length = 50)
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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