package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Modemacrosub entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modemacrosub")
@Inheritance(strategy=InheritanceType.JOINED)
@XStreamAlias("id")
public class Modemacrosub  implements java.io.Serializable,com.flywind.app.data.Intersub {

	// Fields    

     private long id;  
     private Long mid = 0L;
     private Long houseId = 0L;  
     private String name; 
     private Long delaySec = 0L;    
     private String act;     
     private String destType;  
     private Long dest = 0L;
     private Long destOldId = 0L; 
     private String para; 
     private String extension;
     private String param; 
     private Integer actlibId = 0; 
  
    // Constructors

	/** default constructor */
    public Modemacrosub() {
    }

    
    /** full constructor */
    public Modemacrosub(Long mid, Long houseId, String name, Long delaySec, String act, String destType, Long dest,Long destOldId, String para,  Integer actlibId) {
        this.mid = mid;
        this.houseId=houseId;
        this.name = name;
        this.delaySec = delaySec;
        this.act = act;
        this.destType = destType;
        this.dest = dest;
        this.destOldId = destOldId;
        this.para = para;
        this.actlibId=actlibId;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="ID", unique=true, nullable=false)

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Override
	@Column(name="MID")

    public Long getMid() {
        return this.mid;
    }
    
    @Override
	public void setMid(Long mid) {
        this.mid = mid;
    }
 
    @Column(name="HouseID")

    public Long getHouseId() {
        return this.houseId;
    }
    
    @Override
	public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
    
    @Column(name="Name")

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="DelaySec")

    public Long getDelaySec() {
        return this.delaySec;
    }
    
    public void setDelaySec(Long delaySec) {
        this.delaySec = delaySec;
    }
    
    @Column(name="Act")

    public String getAct() {
        return this.act;
    }
    
    public void setAct(String act) {
        this.act = act;
    }
    
    @Column(name="DestType")

    public String getDestType() {
        return this.destType;
    }
    
    public void setDestType(String destType) {
        this.destType = destType;
    }
    
    @Column(name="Dest")

    public Long getDest() {
        return this.dest;
    }
    
    public void setDest(Long dest) {
        this.dest = dest;
    }
    
    @Column(name="Para")
    public String getPara() {
        return this.para;
    }
    
    public void setPara(String para) {
        this.para = para;
    }

    @Column(name="Extension")    
    public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

    @Column(name="Param") 
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}


	@Column(name="ActlibId")
	public Integer getActlibId() {
		return actlibId;
	}

	public void setActlibId(Integer actlibId) {
		this.actlibId = actlibId;
	}

	@Column(name="destOldId")
	public Long getDestOldId() {
		return destOldId;
	}

	public void setDestOldId(Long destOldId) {
		this.destOldId = destOldId;
	}
}