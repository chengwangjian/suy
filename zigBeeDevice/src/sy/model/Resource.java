package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;




@Entity
@Table(name = "resource")
public class Resource implements java.io.Serializable {
	private long id;
	private String uuid;
	private String houseieee;
	private String deviceuuid;
	private String resname;
	private String restype;
	private String size;
	private String path;
	private String spath;
	private String taketime;
	//private Date uploadtime;
	private String uploadtime;
	
	public Resource(){
		
	}
	public Resource(String uuid, String houseiee, String deviceuuid, String resname, String restype, String size, String path, String spath, String taketime, String uploadtime) {
		this.uuid = uuid;
		this.houseieee = houseieee;
		this.deviceuuid = deviceuuid;
		this.resname = resname;
		this.restype = restype;
		this.size  = size;
		this.path = path;
		this.spath = spath;
		this.taketime = taketime;
		this.uploadtime = uploadtime;	
	}
	
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "uuid", length = 255,unique = true, nullable = false )
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "houseieee", length = 255)
	public String getHouseieee() {
		return houseieee;
	}
	public void setHouseieee(String houseieee) {
		this.houseieee = houseieee;
	}
	
	@Column(name = "deviceuuid", length = 255)
	public String getDeviceuuid() {
		return deviceuuid;
	}
	public void setDeviceuuid(String deviceuuid) {
		this.deviceuuid = deviceuuid;
	}

	@Column(name = "resname", length = 255)
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	
	@Column(name = "restype", length = 255)
	public String getRestype() {
		return restype;
	}
	public void setRestype(String restype) {
		this.restype = restype;
	}
	
	@Column(name = "size", length = 255)
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Column(name = "path", length = 255)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name = "spath", length = 255)
	public String getSpath() {
		return spath;
	}
	public void setSpath(String spath) {
		this.spath = spath;
	}
	@Column(name = "taketime", length = 19)
	public String getTaketime() {
		return taketime;
	}
	public void setTaketime(String taketime) {
		this.taketime = taketime;
	}
	@Column(name = "uploadtime", length = 19)
	public String getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}
	
	
	
}
