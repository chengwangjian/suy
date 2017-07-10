package sy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ReliUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reliuser")
public class ReliUser implements java.io.Serializable {

	// Fields

	private Long id;
	private Date regist_time;
	private String user_name;
	private String pwd;
	private Short level_id;
	private String enabled;
	private Date last_time;

	// Constructors

	/** default constructor */
	public ReliUser() {
	}

	/** minimal constructor */
	public ReliUser(Long id) {
		this.id = id;
	}

	/** full constructor */
	public ReliUser(Long id, Date regist_time, String user_name, String pwd, Short level_id,
			String enabled, Date last_time) {
		this.id = id;
		this.regist_time = regist_time;
		this.user_name = user_name;
		this.pwd = pwd;
		this.level_id = level_id;
		this.enabled = enabled;
		this.last_time = last_time;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_name", length = 100)
	public String getUser_name() {
		return this.user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Column(name = "pwd", length = 40)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "level_id")
	public Short getLevel_id() {
		return this.level_id;
	}

	public void setLevel_id(Short level_id) {
		this.level_id = level_id;
	}

	@Column(name = "enabled", length = 2)
	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "last_time", length = 19)
	public Date getLast_time() {
		return this.last_time;
	}

	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}
	
	@Column(name = "regist_time", nullable = true, length = 19)
	public Date getRegist_time() {
		return regist_time;
	}

	public void setRegist_time(Date regist_time) {
		this.regist_time = regist_time;
	}

}