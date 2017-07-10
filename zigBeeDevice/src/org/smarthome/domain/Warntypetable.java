package org.smarthome.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Warntypetable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="warntypetable")

public class Warntypetable  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String chinesewarnType;
     private String englishwarnType;
     private Integer WMode;
     private String sensortype;


    // Constructors



	/** default constructor */
    public Warntypetable() {
    }

    
    /** full constructor */
    public Warntypetable(String chinesewarnType, String englishwarnType, Integer WMode) {
        this.chinesewarnType = chinesewarnType;
        this.englishwarnType = englishwarnType;
        this.WMode = WMode;
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
    
    @Column(name="ChinesewarnType")

    public String getChinesewarnType() {
        return this.chinesewarnType;
    }
    
    public void setChinesewarnType(String chinesewarnType) {
        this.chinesewarnType = chinesewarnType;
    }
    
    @Column(name="EnglishwarnType")

    public String getEnglishwarnType() {
        return this.englishwarnType;
    }
    
    public void setEnglishwarnType(String englishwarnType) {
        this.englishwarnType = englishwarnType;
    }
    
    @Column(name="w_mode")

    public Integer getWMode() {
        return this.WMode;
    }
    
    public void setWMode(Integer WMode) {
        this.WMode = WMode;
    }

    @Transient
	public String getSensortype() {
		return sensortype;
	}


	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}
    

}