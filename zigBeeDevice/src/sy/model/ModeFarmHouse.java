package sy.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="modefarmhouse")
public class ModeFarmHouse {
	
    private int id;
    private int userId;
    private String hosueGuid;
    private String houseIEEE;
    private String houseName;
    private int roomNums;
    private String description;
    private String cloudIp;
    private Date createtime;
    private Date lasttime;
    private String xmlVersion;
    private String note;
    
	public ModeFarmHouse() {
		super();
	}

	public ModeFarmHouse(int id, int userId, String hosueGuid, String houseIEEE, String houseName, int roomNums,
			String description, String cloudIp, Date createtime, Date lasttime, String xmlVersion, String note) {
		super();
		this.id = id;
		this.userId = userId;
		this.hosueGuid = hosueGuid;
		this.houseIEEE = houseIEEE;
		this.houseName = houseName;
		this.roomNums = roomNums;
		this.description = description;
		this.cloudIp = cloudIp;
		this.createtime = createtime;
		this.lasttime = lasttime;
		this.xmlVersion = xmlVersion;
		this.note = note;
	}
    
	@Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}
   
	@Column(name="userId")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
    
	@Column(name="hosueGuid",length=80)
	public String getHosueGuid() {
		return hosueGuid;
	}

	public void setHosueGuid(String hosueGuid) {
		this.hosueGuid = hosueGuid;
	}
     
	@Column(name="houseIEEE", length=50)
	public String getHouseIEEE() {
		return houseIEEE;
	}

	public void setHouseIEEE(String houseIEEE) {
		this.houseIEEE = houseIEEE;
	}
    
	@Column(name="houseName", length=50)
	public String getHouseName() {
		return houseName;
	}
    
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
    
	@Column(name="roomNums")
	public int getRoomNums() {
		return roomNums;
	}
   
	public void setRoomNums(int roomNums) {
		this.roomNums = roomNums;
	}
    
	@Column(name="description", length=200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
	@Column(name="cloudIp", length=200)
	public String getCloudIp() {
		return cloudIp;
	}

	public void setCloudIp(String cloudIp) {
		this.cloudIp = cloudIp;
	}
    
	@Column(name="createtime")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
    
	@Column(name="lasttime")
	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}
    
	@Column(name="xmlVersion", length=200)
	public String getXmlVersion() {
		return xmlVersion;
	}

	public void setXmlVersion(String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}
    
	@Column(name="note", length=500)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
    
    
}
