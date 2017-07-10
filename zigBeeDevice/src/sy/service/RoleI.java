package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.Role;


/**
 * @author huanglw  权限
 *
 */
public interface RoleI {
	/**
	 * 获取级别
	 * @return
	 * @throws Exception
	 */
	public List<Map> getLevel() throws Exception;
	
	/**
	 * 保存/更新角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public int saveRole(Role role, String oldRoleName) throws Exception;
	
	/**
	 * 获取角色总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Integer getRoleCount(Map condition) throws Exception;
	
	/**
	 * 获取角色
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRoles(Map condition) throws Exception;
	
	/**
	 * 删除角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public int deleteRole(Role role) throws Exception;
	
	/**
	 * 编辑角色时获取角色，包含权限、客户代码
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getRole(Role role) throws Exception;
	
	/**
	 * 查询未关联的客户代码
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List<Map> getClientCodeNotRels(Map<String, Object> condition) throws Exception;
	
	/**
	 * 查询已关联的客户代码
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List<Map> getClientCodeRels(Map<String, Object> condition) throws Exception;
	
	/**
	 * 获取区域
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRegions() throws Exception;
	
	/**
	 * 根据级别获取权限
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getRoleByLevel(Integer levelId) throws Exception;
}
