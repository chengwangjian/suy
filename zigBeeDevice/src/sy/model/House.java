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
 * House entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "house")
public class House extends PageBean implements java.io.Serializable {

	// Fields

	private long id;
	private String houseIeee; // 客户IEEE
	private String name; // 客户名称 
	private String longitude; // 地理位置的经度
	private String latitude; // 地理位置的纬度
	private String networkAddress; // 默认外网ip地址   外网直连地址 默认值192.168.99.255
	private String wanIp; // Z203推送的公网ip地址
	private String lanIp; // Z203推送的局域网ip地址 
	private String port; // 默认81   外网端口
	private String description; // 客户描述
	private String enableFlag = "1"; // 启用标志 1表示启用,0表示不启用。默认值1
	private String regionCode = "厦门"; // 所在区域，默认值厦门
    private String isonline = "0";  // 默认家离线，1在线，0离线   
	private Date createtime = new Date(); // 创建时间
	private Date lasttime = new Date(); // 更新时间
	private String clientCode; // 客户代码
	private String IPK_version= "0.0.0.1";//IPK版本号
	private String HW_version= "203";//IPK版本号
    private int nodeNum;
    private Integer clientId;
    private Byte smsType;
    private String note;
    
    
    @Column(name = "note", length = 50)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	// 非表字段
	private String minLongitude;
	private String maxLongitude;
	private String minLatitude;
	private String maxLatitude;
	
	// Constructors

	/** default constructor */
	public House() {
	}
	
	public House( String name,String clientCode, String houseIeee, String regionCode) {
		super();
		this.name = name;
		this.clientCode = clientCode;
		this.houseIeee = houseIeee;
		this.regionCode = regionCode;
	
	}

	/** full constructor */
	public House(String houseIeee, String name, String longitude,
			String latitude, String networkAddress, String port,
			String description,String regionCode, String enableFlag,String isonline, Date createtime,
			Date lasttime,String IPK_version) {
		this.houseIeee = houseIeee;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.networkAddress = networkAddress;
		this.port = port;
		this.description = description;
		this.enableFlag = enableFlag;
		this.regionCode = regionCode;
        this.isonline = isonline;
		this.createtime = createtime;
		this.lasttime = lasttime;
		this.IPK_version=IPK_version;
	}

	public String getIPK_version() {
		return IPK_version;
	}
	@Column(name = "IPK_version", length = 50)
	public void setIPK_version(String iPK_version) {
		IPK_version = iPK_version;
	}

	public String getHW_version() {
		return HW_version;
	}
	@Column(name = "HW_version", length = 50)
	public void setHW_version(String hW_version) {
		HW_version = hW_version;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "houseIEEE", length = 50)
	public String getHouseIeee() {
		return this.houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Column(name = "longitude", length = 20)
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", length = 20)
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "networkAddress", length = 50)
	public String getNetworkAddress() {
		return this.networkAddress;
	}

	public void setNetworkAddress(String networkAddress) {
		this.networkAddress = networkAddress;
	}
	
	@Column(name = "port", length = 10)
	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getMinLongitude() {
		return minLongitude;
	}

	public void setMinLongitude(String minLongitude) {
		this.minLongitude = minLongitude;
	}

	@Transient
	public String getMaxLongitude() {
		return maxLongitude;
	}

	public void setMaxLongitude(String maxLongitude) {
		this.maxLongitude = maxLongitude;
	}

	@Transient
	public String getMinLatitude() {
		return minLatitude;
	}

	public void setMinLatitude(String minLatitude) {
		this.minLatitude = minLatitude;
	}

	@Transient
	public String getMaxLatitude() {
		return maxLatitude;
	}

	public void setMaxLatitude(String maxLatitude) {
		this.maxLatitude = maxLatitude;
	}
	
	@Column(name = "enableFlag", length = 2)
	public String getEnableFlag() {
		return this.enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	
	@Column(name = "regionCode", length = 30)
	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	@Column(name="isonline", length=50)
    public String getIsonline() {
		return isonline;
	}

	public void setIsonline(String isonline) {
		this.isonline = isonline;
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

	@Column(name = "clientCode")
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	@Column(name = "wanIp", length = 50)
	public String getWanIp() {
		return wanIp;
	}

	public void setWanIp(String wanIp) {
		this.wanIp = wanIp;
	}

	@Column(name = "lanIp", length = 50)
	public String getLanIp() {
		return lanIp;
	}

	public void setLanIp(String lanIp) {
		this.lanIp = lanIp;
	}

	@Column(name = "client_id", length = 50)
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "smsType")
	public Byte getSmsType() {
		return smsType;
	}

	public void setSmsType(Byte smsType) {
		this.smsType = smsType;
	}
}