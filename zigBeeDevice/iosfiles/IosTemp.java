package sy.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Advertisement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="iosTemp")
public class IosTemp  implements java.io.Serializable {
    // Fields    
	private Integer id;
	private String UUID;
	private String distance;
	private String event;
	private String time;
     
	public IosTemp() {
		
	}
	
	public IosTemp(Integer id, String uUID, String distance, String event,
			String time) {
		super();
		this.id = id;
		UUID = uUID;
		this.distance = distance;
		this.event = event;
		this.time = time;
	}

	@Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="UUID", length=50)
	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	@Column(name="distance", length=200)
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}	
	
}