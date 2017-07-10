package sy.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="conditionmain")
public class Conditionmain implements java.io.Serializable {
	private Integer id;
    private  String name;
    private  Integer userid;
    
   @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="name", length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="userid", length=50)
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}
