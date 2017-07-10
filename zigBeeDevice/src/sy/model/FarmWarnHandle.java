package sy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * FarmWarnHandle entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name="farmWarnHandle")
public class FarmWarnHandle implements java.io.Serializable {
        
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//fields
	private int id;
	private String houseieee;
	private Date handletime;
	private String note;
	// Constructors
	
	/** default constructor */
	public FarmWarnHandle() {
	}
	
	/** full constructor */
	public FarmWarnHandle(String houseieee, Date handletime, String note) {
		super();
		this.houseieee = houseieee;
		this.handletime = handletime;
		this.note = note;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "houseieee", length = 50)
	public String getHouseieee() {
		return houseieee;
	}

	public void setHouseieee(String houseieee) {
		this.houseieee = houseieee;
	}

	@Column(name = "handletime", length = 19)
	public Date getHandletime() {
		return handletime;
	}

	public void setHandletime(Date handletime) {
		this.handletime = handletime;
	}

	@Column(name = "note", length = 500)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
    
}
