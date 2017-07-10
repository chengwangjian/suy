package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Modehvacsub entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modehvacsub")

@XStreamAlias("id")
public class Modehvacsub  implements java.io.Serializable,com.flywind.app.data.Intersub {

    // Fields    

     private long id;  
     private Long mid = 0L;
     private Long houseId = 0L;        
     private Long actType = 0L;     
     private String act;  
     private Long dest = 0L;  
     private Long durationSec = 0L;  
     private String resumeAct;
     private Long resumeDest = 0L;
     private Long delaySec = 0L;
     


    // Constructors
     @Column(name="DelaySec")
    public Long getDelaySec() {
		return delaySec;
	}


	public void setDelaySec(Long delaySec) {
		this.delaySec = delaySec;
	}

	/** default constructor */
    public Modehvacsub() {
    }

    

    /** full constructor */
    public Modehvacsub(Long mid, Long houseId, Long actType, String act, Long dest, Long durationSec, String resumeAct, Long resumeDest,long delaySec) {
        this.mid = mid;
        this.houseId = houseId;
        this.actType = actType;
        this.act = act;
        this.dest = dest;
        this.durationSec = durationSec;
        this.resumeAct = resumeAct;
        this.resumeDest = resumeDest;
        this.delaySec = delaySec;
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
    
    @Column(name="ActType")

    public Long getActType() {
        return this.actType;
    }
    
    public void setActType(Long actType) {
        this.actType = actType;
    }
    
    @Column(name="Act")

    public String getAct() {
        return this.act;
    }
    
    public void setAct(String act) {
        this.act = act;
    }
    
    @Column(name="Dest")

    public Long getDest() {
        return this.dest;
    }
    
    public void setDest(Long dest) {
        this.dest = dest;
    }
    
    @Column(name="DurationSec")

    public Long getDurationSec() {
        return this.durationSec;
    }
    
    public void setDurationSec(Long durationSec) {
        this.durationSec = durationSec;
    }
    
    @Column(name="ResumeAct")

    public String getResumeAct() {
        return this.resumeAct;
    }
    
    public void setResumeAct(String resumeAct) {
        this.resumeAct = resumeAct;
    }
    
    @Column(name="ResumeDest")

    public Long getResumeDest() {
        return this.resumeDest;
    }
    
    public void setResumeDest(Long resumeDest) {
        this.resumeDest = resumeDest;
    }
   








}