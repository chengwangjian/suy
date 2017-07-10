package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import sy.model.Modeuser;
import sy.model.Verifycode;

public interface ModeuserServiceI {

	public Modeuser save(Modeuser modeuser);
	
	public Modeuser find(Modeuser modeuser);
	
	public List<Modeuser> findList(Modeuser modeuser);
	
	public List<Object[]> findListSql(Modeuser modeuser);

	public Modeuser login(Modeuser modeuser);
	
	public Modeuser get(Modeuser modeuser);
	
	public Modeuser update(Modeuser modeuser);
	
	public int delete(Modeuser modeuser);
	
	/**
	 * 找回密码
	 * @author: zhuangxd
	 * 时间：2013-11-29 下午5:27:25
	 * @param modeuser
	 * @throws MessagingException
	 */
	public void sendPwdMail(Modeuser modeuser) throws MessagingException;
	
	/**
	 * 判断用户是否已注册
	 * @author: zhuangxd
	 * 时间：2013-11-29 下午5:00:02
	 * @param modeuser
	 * @return
	 */
	public Modeuser getModeuser(Modeuser modeuser);
	
	/**
	 * 判断用户是否已存在
	 * @author: zhuangxd
	 * 时间：2014-2-18 上午10:27:25
	 * @param modeuser
	 * @return
	 */
	public Modeuser getModeuser2(Modeuser modeuser);
	
	/**
	 * 保存验证码
	 * @author: zhuangxd
	 * 时间：2013-11-19 下午2:26:48
	 * @param verifycode
	 * @return
	 */
	public Verifycode saveVerifycode(Verifycode verifycode);
	
	/**
	 * 删除验证码
	 * @author: zhuangxd
	 * 时间：2013-11-19 上午10:35:36
	 * @param modeuser
	 * @return
	 */
	public int deleteVerifyCode(Modeuser modeuser);
	
	public void sendFileMail(Modeuser modeuser) throws MessagingException;

//	public DataGrid datagrid(Modeuser modeuser);

	public void remove(String ids);
	
	public void test();	

	/**
	 * 获取用户数
	 * @param condition 查询条件
	 * @return
	 * @throws Exception
	 */
	public int getUserCount(Map<String, Object> condition) throws Exception;
	
	/**
	 * 获取用户
	 * @param condition 查询条件
	 * @param pageIndex 页码
	 * @param pageSize 每页条数
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryUser(Map<String, Object> condition, int startRow,
			int pageSize) throws Exception;
	
	/**
	 * 判断密码是否正确
	 * @param id
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public boolean isPwdRight(Long id, String pwd) throws Exception;
	
	/**
	 * 更新自己密码
	 * @param modeuser
	 * @throws Exception
	 */
	public void updateModeuser(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 删除用户
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(Long id) throws Exception;
	
	/**
	 * 获取权限
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map> getPrivilege(Long id) throws Exception;
}
