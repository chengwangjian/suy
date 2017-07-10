package zbHouse.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Device entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="device")
public class Device  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String houseIeee;
     private Long roomId;
     private Long nodeId;
     private String deviceName;
     private String ieee;
     private String ep;
     private String type;     
     private String isonline = "1";  // 默认设备离线，1在线，0离线   
     private Date createtime = new Date();
 	 private Date lasttime = new Date();
 	 private String datecode;//设备修改时间
 	 private String deviceattribute;//设备属性报告内容
 	 private String unit_type;//设备类型，用于区分是否是zone设备（注：设备类型是有node表中的nodetype决定的）
 	 private String modelId;//增加设备modelId推送字段
 	 private String SolidModelID;
 	 // Constructors
 	@Column(name="SolidModelID", length=50)
 	public String getSolidModelID() {
		return SolidModelID;
	}


	public void setSolidModelID(String solidModelID) {
		this.SolidModelID = solidModelID;
	}


	@Column(name="deviceattribute", length=50)
    public String getDeviceattribute() {
		return deviceattribute;
	}


	public void setDeviceattribute(String deviceattribute) {
		this.deviceattribute = deviceattribute;
	}
	
	@Column(name="unit_type", length=50)
	public String getUnit_type() {
		return unit_type;
	}


	public void setUnit_type(String unit_type) {
		this.unit_type = unit_type;
	}

	@Column(name="modelId",length=50)
	public String getModelId() {
		return modelId;
	}


	public void setModelId(String modelId) {
		this.modelId = modelId;
	}


	/** default constructor */
    public Device() {
    }

    
    /** full constructor */
    public Device(String houseIeee, Long roomId, Long nodeId, String deviceName, String ieee, String ep, String type,String isonline,Date createtime, Date lasttime,String datecode,String deviceattribute,String unit_type,String modelId,String SolidModelID) {
        this.houseIeee = houseIeee;
        this.roomId = roomId;
        this.nodeId = nodeId;
        this.deviceName = deviceName;
        this.ieee = ieee;
        this.ep = ep;
        this.type = type;
        this.isonline = isonline;
        this.createtime = createtime;
		this.lasttime = lasttime;
		this.datecode = datecode;
		this.deviceattribute = deviceattribute;
		this.unit_type = unit_type;
		this.modelId = modelId;
		this.SolidModelID = SolidModelID;
    }

    @Column(name="datecode", length=50)
    public String getDatecode() {
		return datecode;
	}


	public void setDatecode(String datecode) {
		this.datecode = datecode;
	}


	// Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="houseIEEE", length=50)

    public String getHouseIeee() {
        return this.houseIeee;
    }
    
    public void setHouseIeee(String houseIeee) {
        this.houseIeee = houseIeee;
    }
    
    @Column(name="roomId")

    public Long getRoomId() {
        return this.roomId;
    }
    
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    
    @Column(name="nodeId")

    public Long getNodeId() {
        return this.nodeId;
    }
    
    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
    
    @Column(name="deviceName", length=200)

    public String getDeviceName() {
        return this.deviceName;
    }
    
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    
    @Column(name="ieee", length=50)

    public String getIeee() {
        return this.ieee;
    }
    
    public void setIeee(String ieee) {
        this.ieee = ieee;
    }
    
    @Column(name="ep", length=100)

    public String getEp() {
        return this.ep;
    }
    
    public void setEp(String ep) {
        this.ep = ep;
    }
    
    @Column(name="type", length=50)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="isonline", length=50)
    public String getIsonline() {
		return isonline;
	}

	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}

	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "lasttime", length = 19)
	public Date getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}
}