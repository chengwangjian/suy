package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Notewarncontrol entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="notewarncontrol")

public class Notewarncontrol  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Date startDate;
     private Date endDate;
     private String state;
     private String houseIeee;


    // Constructors

    /** default constructor */
    public Notewarncontrol() {
    }

    
    /** full constructor */
    public Notewarncontrol(Date startDate, Date endDate, String state, String houseIeee) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.houseIeee = houseIeee;
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
    
    @Column(name="StartDate", length=19)

    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @Column(name="EndDate", length=19)

    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @Column(name="state", length=10)

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="houseIEEE", length=50)

    public String getHouseIeee() {
        return this.houseIeee;
    }
    
    public void setHouseIeee(String houseIeee) {
        this.houseIeee = houseIeee;
    }
   








}