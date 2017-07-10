package sy.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * ModeSchcUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modeschcuser")
public class ModeSchcUser  implements java.io.Serializable {
    // Fields    
     private long id;
     private long userId;
     private String houseIeee;
     private String schcUserName;
     private String schcPassword;


    // Constructors

    /** default constructor */
    public ModeSchcUser() {
    }

    
    /** full constructor */
    public ModeSchcUser(long userId, String houseIeee, String schcUserName, String schcPassword) {
        this.userId = userId;
        this.houseIeee = houseIeee;
        this.schcUserName = schcUserName;
        this.schcPassword = schcPassword;
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
    
    @Column(name="userId")

    public long getUserId() {
        return this.userId;
    }
    
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    @Column(name="houseIEEE", length=20)

    public String getHouseIeee() {
        return this.houseIeee;
    }
    
    public void setHouseIeee(String houseIeee) {
        this.houseIeee = houseIeee;
    }
    
    @Column(name="schcUserName", length=30)

    public String getSchcUserName() {
        return this.schcUserName;
    }
    
    public void setSchcUserName(String schcUserName) {
        this.schcUserName = schcUserName;
    }
    
    @Column(name="schcPassword", length=30)

    public String getSchcPassword() {
        return this.schcPassword;
    }
    
    public void setSchcPassword(String schcPassword) {
        this.schcPassword = schcPassword;
    }
   
}