package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * IRControlltype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ircontrolltype")

public class IRControlltype  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String controllTypeName;


    // Constructors

    /** default constructor */
    public IRControlltype() {
    }

    
    /** full constructor */
    public IRControlltype(String controllTypeName) {
        this.controllTypeName = controllTypeName;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="controllTypeName", length=50)

    public String getControllTypeName() {
        return this.controllTypeName;
    }
    
    public void setControllTypeName(String controllTypeName) {
        this.controllTypeName = controllTypeName;
    }
   








}