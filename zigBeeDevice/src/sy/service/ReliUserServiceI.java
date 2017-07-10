package sy.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.flywind.app.data.RoleInfo;

import sy.model.ReliUser;

/**
 * 可靠性管理用户服务类
 * @author hlw
 *
 */
public interface ReliUserServiceI {
	/**
	 * 新增用户
	 * @param reliUser reli用户实体类
	 * @return
	 * @throws Exception
	 */
	public Serializable addReliUser(ReliUser reliUser) throws Exception;
	
	/**
	 * 判断用户名是否存在
	 * @param userName 用户名
	 * @return
	 * @throws Exception
	 */
	public boolean isUserExist(String userName) throws Exception;
	
	/**
	 * 获取总行数 
	 * @param condition 查询条件
	 * @return
	 * @throws Exception
	 */
	public int getUserCount(Map<String, Object> condition) throws Exception;
	
	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param pageIndex 第几页
	 * @param pageSize 一页行数
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryUser(Map<String, Object> condition, int pageIndex, int pageSize) throws Exception;
	
	/**
	 * 更新ReliUser
	 * @param reliUser reliUser实体类
	 * @return
	 * @throws Exception
	 */
	public void updateReliUser(ReliUser reliUser) throws Exception;
	
	/**
	 * 查询密码是否正确
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public boolean isPwdRight(Long id, String pwd) throws Exception;
	
	/**
	 * 删除用户
	 * @param id 用户id
	 * @return
	 * @throws Exception
	 */
	public void deleteUser(Long id) throws Exception;
	
	/**
	 * 校验用户名密码
	 * @param user_name 用户名
	 * @param pwd 密码
	 * @return
	 * @throws Exception
	 */
	public ReliUser checkUser(String user_name, String pwd) throws Exception;
	
	/**
	 * 获取用户权限
	 * @param id 用户id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getPrivilege(Long id) throws Exception;
	
	/**
	 * 获取旧密码
	 * @param reliUser
	 * @return
	 * @throws Exception
	 */
	public String getPass(ReliUser reliUser) throws Exception;
	
	/**
	 * 根据用户名获取用户
	 * @param reliUser
	 * @return
	 * @throws Exception
	 */
	public ReliUser getReliUserByName(ReliUser reliUser) throws Exception;
	/**
	 * 返回要隐藏的功能
	 * @param userid
	 * @return
	 */
	public  List<Map> function(String userid) ;
	
	public List<Map> functionAble(String userid) ;		
	
	/**
	 * 检查userid与level_id是否一致
	 * @param userid
	 * @param level_id
	 * @return
	 */
	public int checkUserid(String userid, String level_id);
	
	public ReliUser checkUseridAndName(Long id) ;

	public List<Map> queryReliUserLevel();

	public List<Map> queryRole(Map requestparamsMap);

	public void addRoleToUser(RoleInfo requestparamsMap);

	public List<Map> getRoleByUserid(Map<String, Object> requestparamsMap);

	public List<Map> queryRoleCount(Map requestparamsMap);
		
	
}
