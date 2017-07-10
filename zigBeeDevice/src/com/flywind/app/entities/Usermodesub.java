package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Usermodesub entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="usermodesub")

@XStreamAlias("id")
public class Usermodesub  implements java.io.Serializable,com.flywind.app.data.Intersub {

    // Fields    

     private long id;
     private long houseId; 
     private Long mid;
     private String act;
     private long dest;   
     private int selectss;
     private Long oldId;
    // Constructors

    /** default constructor */
    public Usermodesub() {
    }

    
    /** full constructor */
    public Usermodesub(long mid, long houseId, String act, long dest,int selectss) {
    	this.mid = mid;
        this.houseId=houseId;
        this.act = act;
        this.dest = dest;
        this.selectss=selectss;
    }

   
    // Property accessors
    @Id 
	@GeneratedValue
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


    @Column(name="Selectss")
	public int getSelectss() {
		return selectss;
	}

	public void setSelectss(int selectss) {
		this.selectss = selectss;
	}

	@Transient 
	public Long getOldId() {
		return oldId;
	}


	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}   




}