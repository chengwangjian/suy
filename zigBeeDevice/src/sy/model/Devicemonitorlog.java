package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Devicemonitorlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="devicemonitorlog")

public class Devicemonitorlog  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String houseIeee;
     private String ieee;
     private String ep;
     private short staterecords;
     private Date statechangetime;


    // Constructors

    /** default constructor */
    public Devicemonitorlog() {
    }

    
    /** full constructor */
    public Devicemonitorlog(String houseIeee, String ieee, String ep, short staterecords, Date statechangetime) {
        this.houseIeee = houseIeee;
        this.ieee = ieee;
        this.ep = ep;
        this.staterecords = staterecords;
        this.statechangetime = statechangetime;
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
    
    @Column(name="ep", length=100)

    public String getEp() {
        return this.ep;
    }
    
    public void setEp(String ep) {
        this.ep = ep;
    }
    
    @Column(name="staterecords")

    public short getStaterecords() {
        return this.staterecords;
    }
    
    public void setStaterecords(short staterecords) {
        this.staterecords = staterecords;
    }
    
    @Column(name="statechangetime", length=19)

    public Date getStatechangetime() {
        return this.statechangetime;
    }
    
    public void setStatechangetime(Date statechangetime) {
        this.statechangetime = statechangetime;
    }
   








}