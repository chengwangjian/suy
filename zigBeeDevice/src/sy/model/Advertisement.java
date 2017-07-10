package sy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Advertisement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="advertisement")

public class Advertisement  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String houseIeee;//家的houseIeee
     private String username;//APP用户名	
     private String adurl;//广告地址
     private String readstate="0";//广告读取状态，1为已读，0为未读,默认未读
     private Date createtime = new Date(); // 创建时间
 	 private Date lasttime = new Date(); // APP访问网址时间

    // Constructors
 	@Column(name = "createtime", length = 19)
    public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "lasttime", length = 19)
	public Date getLasttime() {
		return lasttime;
	}


	public Advertisement(Integer id, String houseIeee, String username,
			String adurl, String readstate, Date createtime, Date lasttime) {
		this.id = id;
		this.houseIeee = houseIeee;
		this.username = username;
		this.adurl = adurl;
		this.readstate = readstate;
		this.createtime = createtime;
		this.lasttime = lasttime;
	}


	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}


	/** default constructor */
    public Advertisement() {
    }

    
    /** full constructor */
    public Advertisement(String houseIeee, String username, String adurl, String readstate) {
        this.houseIeee = houseIeee;
        this.username = username;
        this.adurl = adurl;
        this.readstate = readstate;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="houseIeee", length=50)

    public String getHouseIeee() {
        return this.houseIeee;
    }
    
    public void setHouseIeee(String houseIeee) {
        this.houseIeee = houseIeee;
    }
    
    @Column(name="username", length=50)

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="adurl", length=100)

    public String getAdurl() {
        return this.adurl;
    }
    
    public void setAdurl(String adurl) {
        this.adurl = adurl;
    }
    
    @Column(name="readstate", length=2)

    public String getReadstate() {
        return this.readstate;
    }
    
    public void setReadstate(String readstate) {
        this.readstate = readstate;
    }
   








}