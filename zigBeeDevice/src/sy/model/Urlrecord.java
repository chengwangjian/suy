package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Urlrecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="urlrecord")

public class Urlrecord  implements java.io.Serializable {


    // Fields    

     private long id;
     private String url;
     private String param;


    // Constructors

    /** default constructor */
    public Urlrecord() {
    }

    
    /** full constructor */
    public Urlrecord(String url, String param) {
        this.url = url;
        this.param = param;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="url", length=500)

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="param", length=200)

    public String getParam() {
        return this.param;
    }
    
    public void setParam(String param) {
        this.param = param;
    }
   








}