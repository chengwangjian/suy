package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Userordermain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userordermain")
public class Userordermain implements java.io.Serializable {

	// Fields

	private long id;
	private String orderName;
	private long userId;
	private long houseId;
	private String userType; // 值0表示公司，1表示个人
	private String userName;
	private String houseName;
	private String tel;
	private String email;
	private String address;
	private String contact;
	private Integer count;
	private String suggestion;
	private String description;
	private String xmlFile;
	private Date createtime = new Date();
	private Date lasttime = new Date();
	// 非表字段
	private String password;
	private String houseIeee;
	

	// Constructors

	/** default constructor */
	public Userordermain() {
	}

	/** full constructor */
	public Userordermain(String orderName, long userId, long houseId,
			String userType, String userName, String houseName, String tel,
			String email, String address, String contact, Integer count,
			String suggestion, String description, String xmlFile,
			Date createtime, Date lasttime) {
		this.orderName = orderName;
		this.userId = userId;
		this.houseId = houseId;
		this.userType = userType;
		this.userName = userName;
		this.houseName = houseName;
		this.tel = tel;
		this.email = email;
		this.address = address;
		this.contact = contact;
		this.count = count;
		this.suggestion = suggestion;
		this.description = description;
		this.xmlFile = xmlFile;
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

	@Column(name = "orderName", length = 200)
	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@Column(name = "userId")
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "houseId")
	public long getHouseId() {
		return this.houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}
	
	@Column(name = "userType", length = 2)
	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "userName", length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "houseName", length = 50)
	public String getHouseName() {
		return this.houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	@Column(name = "tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "address", length = 600)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "contact", length = 50)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "suggestion", length = 2000)
	public String getSuggestion() {
		return this.suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	@Column(name = "description", length = 2000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "xmlFile", length = 1000)
	public String getXmlFile() {
		return this.xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
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

	@Transient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
	public String getHouseIeee() {
		return houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

}