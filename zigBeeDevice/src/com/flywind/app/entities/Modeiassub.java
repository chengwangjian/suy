package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Modeiassub entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modeiassub")

@XStreamAlias("id")
public class Modeiassub  implements java.io.Serializable,com.flywind.app.data.Intersub {

    // Fields    

     private long id;
     private long mid;
     private long houseId; 
     private short ciestatus;
     private short zoneActType;    
     private String act;
     private long dest;


    // Constructors

    /** default constructor */
    public Modeiassub() {
    }

    
    /** full constructor */
    public Modeiassub(long mid, long houseId, short ciestatus, short zoneActType, String act, long dest) {
        this.mid = mid;
        this.houseId=houseId;
        this.ciestatus = ciestatus;
        this.zoneActType = zoneActType;
        this.act = act;
        this.dest = dest;
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
    
    @Column(name="CIEStatus")

    public short getCiestatus() {
        return this.ciestatus;
    }
    
    public void setCiestatus(short ciestatus) {
        this.ciestatus = ciestatus;
    }
    
    @Column(name="ZoneActType")

    public short getZoneActType() {
        return this.zoneActType;
    }
    
    public void setZoneActType(short zoneActType) {
        this.zoneActType = zoneActType;
    }
    
    @Column(name="Act")

    public String getAct() {
        return this.act;
    }
    
    public void setAct(String act) {
        this.act = act;
    }
    
    @Column(name="Dest")

    public long getDest() {
        return this.dest;
    }
    
    public void setDest(long dest) {
        this.dest = dest;
    }
   








}