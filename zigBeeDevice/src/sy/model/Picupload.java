package sy.model;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "picupload")
public class Picupload implements java.io.Serializable {
	private long id;   //自增id
    private String houseieee; //家的ieee
    private String camuuid;   //摄像头的uuid
    private String picname;   //图片名称
    private String pictype;   //图片类型
    private String picpath;   //图片路径
    private String picspath;  //图片压缩路径
    private String  taketime;  //拍照日期
    private Date   uploadtime; //上传图片日期
    
    public Picupload (){
    	
    }
    public Picupload(String houseieee,String camuuid,String picname,String pictype,String picpath,String picspath,String takrtime,Date uploadtime) {
    	this.houseieee = houseieee;
    	this.camuuid = camuuid;
    	this.picname = picname;
    	this.pictype = pictype;
    	this.picpath = picpath;
    	this.picspath = picspath;
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
	
	@Column(name = "houseieee", length = 50)
	public String getHouseieee() {
		return houseieee;
	}
	public void setHouseieee(String houseieee) {
		this.houseieee = houseieee;
	}
	
	@Column(name = "camuuid", length = 100)
	public String getCamuuid() {
		return camuuid;
	}
	public void setCamuuid(String camuuid) {
		this.camuuid = camuuid;
	}
	
	@Column(name = "picname", length = 100)
	public String getPicname() {
		return picname;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	
	@Column(name = "pictype", length = 4)
	public String getPictype() {
		return pictype;
	}
	public void setPictype(String pictype) {
		this.pictype = pictype;
	}
	
	@Column(name = "picpath", length = 255)
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	
	@Column(name = "picspath", length = 255)
	public String getPicspath() {
		return picspath;
	}
	public void setPicspath(String picspath) {
		this.picspath = picspath;
	}
	
	/*@Column(name = "taketime", length = 19)
	public Date getTaketime() {
		return taketime;
	}
	public void setTaketime(Date taketime) {
		this.taketime = taketime;
	}*/
	
	@Column(name = "uoloadtime", length = 19)
	public Date getUploadtime() {
		return uploadtime;
	}
	public String getTaketime() {
		return taketime;
	}
	public void setTaketime(String taketime) {
		this.taketime = taketime;
	}
	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}
    
    

}
