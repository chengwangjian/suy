/**
 * 
 */
package sy.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author R&D1
 *
 */
@Entity
@Table(name = "relirole")
public class Role {
	private Integer id;
	private String roleName;
	private Integer levelId;
	private List<Integer> privilegeList;
	private List<Integer> clientList;
	
	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	public Role(Integer id, String roleName, Integer levelId,
			List<Integer> privilegeList, List<Integer> clientList) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.levelId = levelId;
		this.privilegeList = privilegeList;
		this.clientList = clientList;
	}

	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "role_name", length = 30)
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Transient
	public List<Integer> getClientList() {
		return clientList;
	}

	public void setClientList(List<Integer> clientList) {
		this.clientList = clientList;
	}
	
	@Transient
	public List<Integer> getPrivilegeList() {
		return privilegeList;
	}

	public void setPrivilegeList(List<Integer> privilegeList) {
		this.privilegeList = privilegeList;
	}

	@Column(name = "level_id")
	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	
}
