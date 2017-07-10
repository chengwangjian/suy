package sy.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * RssiRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="rssirecord")

public class RssiRecord  implements java.io.Serializable {


    // Fields    

     private long id;
     private String house_ieee;
     private String source_ieee;
     private String dest_ieee;
     private Short rssi;
     private Short lqi;
     private Timestamp time = new Timestamp((new Date()).getTime());
     private Timestamp push_time = new Timestamp((new Date()).getTime());


    // Constructors

    /** default constructor */
    public RssiRecord() {
    }

	/** minimal constructor */
    public RssiRecord(long id) {
        this.id = id;
    }
    
    /** full constructor */
    public RssiRecord(long id, String house_ieee, String source_ieee, 
    		String dest_ieee, Short rssi,Short lqi, Timestamp time, Timestamp push_time) {
        this.id = id;
        this.house_ieee = house_ieee;
        this.source_ieee = source_ieee;
        this.dest_ieee = dest_ieee;
        this.rssi = rssi;
        this.time = time;
        this.lqi = lqi;
        this.push_time = push_time;
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
    
    @Column(name="house_ieee", length=50)
    public String getHouse_ieee() {
		return house_ieee;
	}

	public void setHouse_ieee(String house_ieee) {
		this.house_ieee = house_ieee;
	}

	@Column(name="source_ieee", length=50)

    public String getSource_ieee() {
        return this.source_ieee;
    }
    
    public void setSource_ieee(String source_ieee) {
        this.source_ieee = source_ieee;
    }
    
    @Column(name="dest_ieee", length=50)

    public String getDest_ieee() {
        return this.dest_ieee;
    }
    
    public void setDest_ieee(String dest_ieee) {
        this.dest_ieee = dest_ieee;
    }
    
    @Column(name="RSSI")

    public Short getRssi() {
        return this.rssi;
    }
    
    public void setRssi(Short rssi) {
        this.rssi = rssi;
    }
    
    
    
    @Column(name="LQI")
    public Short getLqi() {
		return lqi;
	}

	public void setLqi(Short lqi) {
		this.lqi = lqi;
	}

	@Column(name="count_time", length=19)

    public Timestamp getTime() {
        return this.time;
    }
    
    public void setTime(Timestamp time) {
        this.time = time;
    }
    
    @Column(name="push_time", length=19)

    public Timestamp getPush_time() {
        return this.push_time;
    }
    
    public void setPush_time(Timestamp push_time) {
        this.push_time = push_time;
    }

}