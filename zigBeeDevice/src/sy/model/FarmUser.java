package sy.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="farmuser")
public class FarmUser {
	private Long id;
	private String username;
	private String password;
	private Byte levelId;
	private String enabled;
	private String registTime;
	private String lastTime;
	private Long parentId;
	private Byte roleId;
	private String smsflag;  //1,1,1,1表示全部启用，1,0,1,1表示设备异常故障没启用
	private String phone;
	private String serverIp;
	
	public FarmUser(){}
	
	public FarmUser(String username,String password){
		this.username = username;
		this.password = password;
	}
	
	@Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="user_name")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name="pwd")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="level_id")
	public Byte getLevelId() {
		return levelId;
	}
	public void setLevelId(Byte levelId) {
		this.levelId = levelId;
	}
	@Column(name="enabled")
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	@Column(name="regist_time")
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	@Column(name="last_time")
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	@Column(name="parent_id")
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Column(name="smsflag")
	public String getSmsflag() {
		return smsflag;
	}

	public void setSmsflag(String smsflag) {
		this.smsflag = smsflag;
	}
	@Column(name="phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Transient
	public Byte getRoleId() {
		return roleId;
	}
	public void setRoleId(Byte roleId) {
		this.roleId = roleId;
	}
	
	@Column(name="serverIp")
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	
	
}
