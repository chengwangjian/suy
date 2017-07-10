package org.smarthome.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FileAppinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "file_appinfo", catalog = "zigbeedevice")
public class FileAppinfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String houseIeee;
	private String deviceType;//设备类型
	private String sysVer;//系统版本
	private Date opdatetime;//上传时间
	private String fileName;//文件名
	private String userName;//用户名
	private String errorNo;//错误版本
	private String oderfilename;
	private String versionSdk;//sdk版本
	private String proxyServer;//代理服务器ip
	private String verRelease;//发布版本
	private String cpu;//cpu
	private String packages;//报错位置所在的包
	private String model;//型号
	private String brand;//品牌
	private String resolution;//分辨率
	private String memory;//内存
	private String AppVersion;//app版本
	private String serIp;//所在服务器ip
	private String fileSize;//文件大小
	
	/** default constructor */
	public FileAppinfo() {}

	/** full constructor */
	public FileAppinfo(String houseIeee, String deviceType, String sysVer,
			 Date opdatetime, String fileName,
			String userName, String errorNo, String oderfilename) {
		this.houseIeee = houseIeee;
		this.deviceType = deviceType;
		this.sysVer = sysVer;
		this.opdatetime = opdatetime;
		this.fileName = fileName;
		this.userName = userName;
		this.errorNo = errorNo;
		this.oderfilename = oderfilename;
	}

	public FileAppinfo(long id, String houseIeee, String deviceType,
			String sysVer, Date opdatetime,
			String fileName, String userName, String errorNo,
			String oderfilename, String versionSdk, String proxyServer,
			String cpu, String packages, String model,
			String brand, String resolution, String memory, String appVersion,
			String serIp, String fileSize) {
		super();
		this.id = id;
		this.houseIeee = houseIeee;
		this.deviceType = deviceType;
		this.sysVer = sysVer;
		this.opdatetime = opdatetime;
		this.fileName = fileName;
		this.userName = userName;
		this.errorNo = errorNo;
		this.oderfilename = oderfilename;
		this.versionSdk = versionSdk;
		this.proxyServer = proxyServer;
		this.cpu = cpu;
		this.packages = packages;
		this.model = model;
		this.brand = brand;
		this.resolution = resolution;
		this.memory = memory;
		AppVersion = appVersion;
		this.serIp = serIp;
		this.fileSize = fileSize;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
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

	@Column(name = "device_type", length = 65535)
	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Column(name = "sys_ver", length = 20)
	public String getSysVer() {
		return this.sysVer;
	}

	public void setSysVer(String sysVer) {
		this.sysVer = sysVer;
	}

	@Column(name = "opdatetime", length = 19)
	public Date getOpdatetime() {
		return this.opdatetime;
	}

	public void setOpdatetime(Date opdatetime) {
		this.opdatetime = opdatetime;
	}

	@Column(name = "file_name")
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "error_no")
	public String getErrorNo() {
		return this.errorNo;
	}

	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}

	@Column(name = "oderfilename")
	public String getOderfilename() {
		return this.oderfilename;
	}

	public void setOderfilename(String oderfilename) {
		this.oderfilename = oderfilename;
	}
	@Column(name="app_version")
	public String getAppVersion() {
		return AppVersion;
	}

	public void setAppVersion(String appVersion) {
		AppVersion = appVersion;
	}
	@Column(name="memory")
	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}
	@Column(name="resolution")
	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	@Column(name="brand")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	@Column(name="model")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name="version_sdk")
	public String getVersionSdk() {
		return versionSdk;
	}
	
	public void setVersionSdk(String versionSdk) {
		this.versionSdk = versionSdk;
	}
	@Column(name="proxy_server")
	public String getProxyServer() {
		return proxyServer;
	}

	public void setProxyServer(String proxyServer) {
		this.proxyServer = proxyServer;
	}
	@Column(name="cpu")
	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	@Column(name="package")
	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}
	@Column(name="server_ip")
	public String getSerIp() {
		return serIp;
	}

	public void setSerIp(String serIp) {
		this.serIp = serIp;
	}
	@Column(name="file_size")
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name="version_release")
	public String getVerRelease() {
		return verRelease;
	}

	public void setVerRelease(String verRelease) {
		this.verRelease = verRelease;
	}
}