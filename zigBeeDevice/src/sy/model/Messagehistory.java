package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Messagehistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="messagehistory")

public class Messagehistory  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Date sendtime;
     private String type;
     private String phonenumber;
     private String content;
     private String state;
     private String remark;
     private String houseIeee;
     private String emailcontent;
     
      


    // Constructors

    /** default constructor */
    public Messagehistory() {
    }

    
    /** full constructor */
    public Messagehistory(Date sendtime, String type, String phonenumber, String content, String state, String remark,String houseIeee,String emailcontent) {
        this.sendtime = sendtime;
        this.type = type;
        this.phonenumber = phonenumber;
        this.content = content;
        this.state = state;
        this.remark = remark;
        this.houseIeee=houseIeee;
        this.emailcontent=emailcontent;
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
    
    @Column(name="sendtime", length=19)

    public Date getSendtime() {
        return this.sendtime;
    }
    
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }
    
    @Column(name="type", length=30)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="phonenumber", length=50)

    public String getPhonenumber() {
        return this.phonenumber;
    }
    
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    
    @Column(name="content", length=50)

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="state", length=20)

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="remark", length=50)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name="houseIeee", length=50)

    public String getHouseIeee() {
        return this.houseIeee;
    }
    public void setHouseIeee(String houseIeee) {
        this.houseIeee = houseIeee;
    }
    @Column(name="emailcontent", length=255)

    public String getEmailcontent() {
        return this.emailcontent;
    }
    
    public void setEmailcontent(String emailcontent) {
        this.emailcontent = emailcontent;
    }
}