package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * IRMatch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="irmatch")

public class IRMatch  implements java.io.Serializable {


    // Fields    

     private long id;
     private String houseIeee;
     private String ieee;
     private String ep;
     private Integer hadaemonIrindex;
     private String data;
     private Date createtime;


    // Constructors

    /** default constructor */
    public IRMatch() {
    }

    
    /** full constructor */
    public IRMatch(String houseIeee, String ieee, String ep, Integer hadaemonIrindex, String data, Date createtime) {
        this.houseIeee = houseIeee;
        this.ieee = ieee;
        this.ep = ep;
        this.hadaemonIrindex = hadaemonIrindex;
        this.data = data;
        this.createtime = createtime;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="houseIEEE", length=50)

    public String getHouseIeee() {
        return this.houseIeee;
    }
    
    public void setHouseIeee(String houseIeee) {
        this.houseIeee = houseIeee;
    }
    
    @Column(name="ieee", length=50)

    public String getIeee() {
        return this.ieee;
    }
    
    public void setIeee(String ieee) {
        this.ieee = ieee;
    }
    
    @Column(name="ep", length=50)

    public String getEp() {
        return this.ep;
    }
    
    public void setEp(String ep) {
        this.ep = ep;
    }
    
    @Column(name="hadaemonIRIndex")

    public Integer getHadaemonIrindex() {
        return this.hadaemonIrindex;
    }
    
    public void setHadaemonIrindex(Integer hadaemonIrindex) {
        this.hadaemonIrindex = hadaemonIrindex;
    }
    
    @Column(name="data", length=2000)

    public String getData() {
        return this.data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    @Column(name="createtime", length=19)

    public Date getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
   


}