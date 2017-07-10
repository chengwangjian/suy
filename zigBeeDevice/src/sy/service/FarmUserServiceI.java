package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.FarmUser;

public interface FarmUserServiceI {
	
	/**
	 * 获取所有农业项目用户
	 * @return
	 */
	public List<Map> getFarmUsers();
	
	/**
	 * 根据用户名获取用户对象
	 * @param username
	 * @return
	 */
	public FarmUser getFUser(String username);
	
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	public Map<String,Object> getFarmUser(String username);
	/**
	 * 获取所有老板账号数量
	 * @return
	 */
	public int getUsersCount(String searchParam);
	/**
	 * 获取所有老板账号
	 * @return
	 */
	public List<Map> getBossUsers(String startRow,String pageSize,String searchParam);
	/**
	 * 保存用户
	 * @param username
	 * @param password
	 * @param userType
	 * @return
	 */
	public int saveUser(String user_name,String username,String password,int userType, List<String> areaList,  
			List<Map> noticeNList, List<Map> noticeRMList) throws Exception;
	/**
	 * 删除用户
	 * @param username
	 * @param password
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	public int deleteUser(String username,String password,int userType) throws Exception;
	
	/**
	 * 获取用户列表，并更新mode值
	 * @throws Exception
	 */
	public List<Map> updateUserInfo(String houseIeee, int mode) throws Exception;
	
	/**
	 * 获取用户信息
	 * @throws Exception
	 */
	public List<Map> getUserServer(Map params) throws Exception;
	
	/**
	 * 重置密码
	 * @param id 用户id
	 * @param newPwd 新密码
	 * @return
	 */
	public int resetPwd(long id,String newPwd,String serverIp) throws Exception;
	
	/**
	 * 根据用户id,删除用户
	 * @param id
	 * @return
	 */
	public int delUserById(long id) throws Exception;
	
	/**
	 * 验证名称唯一性
	 * @param username
	 * @return
	 */
	public int checkNameUnique(String username);
	
	/**
	 * 添加管理员账号
	 * @param user
	 * @return
	 */
	public int addBossUser(FarmUser user) throws Exception;
	
	/**
	 * 定时更新用户密码缓存
	 * @param user
	 * @return
	 */
	public void scheduleUpdateUserPassword();
	
	/**
	 * 获取APP用户列表
	 * @throws Exception
	 */
	public List<Map> getAppUserInfo(String userName) throws Exception;
	
	/**
	 * APP修改老板账户密码
	 * @param userName
	 * @throws Exception
	 */
	public void modifyAppPassword(String userName, String password) throws Exception;
	
	/**
	 * 通知203更新账户信息
	 * @param userList
	 */
	public void noticeUpdate(final List<Map> userList);
}
