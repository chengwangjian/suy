package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Historyrecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="historyrecord")

public class Historyrecord  implements java.io.Serializable {


    // Fields    

     private long id;
     private Integer urlId;
     private String url;
     private String ipaddress;
     private String param;
     private Date optime = new Date();


    // Constructors

    /** default constructor */
    public Historyrecord() {
    }

    
    /** full constructor */
    public Historyrecord(Integer urlId, String url, String ipaddress, String param, Date optime) {
        this.urlId = urlId;
        this.url = url;
        this.ipaddress = ipaddress;
        this.param = param;
        this.optime = optime;
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
    
    @Column(name="urlId")

    public Integer getUrlId() {
        return this.urlId;
    }
    
    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }
    
    @Column(name="url", length=500)

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="ipaddress", length=100)

    public String getIpaddress() {
        return this.ipaddress;
    }
    
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
    
    @Column(name="param", length=200)

    public String getParam() {
        return this.param;
    }
    
    public void setParam(String param) {
        this.param = param;
    }
    
    @Column(name="optime", length=19)

    public Date getOptime() {
        return this.optime;
    }
    
    public void setOptime(Date optime) {
        this.optime = optime;
    }
   








}