package sy.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 绑定微信、推送微信告警消息接口
 * @author zhanghc
 * @since 2016-06-21
 */
public interface WechatServiceI {
	/**
	 * 绑定用户
	 * @param userMap 奈伯思账户和微信账户信息
	 * @return 绑定结果
	 */
	public int bindUser(Map<String,Object> userMap);
	
	/**
	 * 解绑用户
	 * @param map
	 * @return
	 */
	public int disbind(Map<String,Object> map); 
	
	/**
	 * 获取微信openid
	 * @param map
	 * @return
	 */
	public String getweixinopenid(Map<String,Object> map);
	
	/**
	 * 获取用户信息
	 * @param map
	 * @return
	 */
	public List<Map> getUserInfo(Map<String,Object> map);
	
	/**
	 * 下拉屏幕时，默认获取十条时间数据
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<Map> getTimeInfo2(Map<String,Object> map) throws Exception;
	
	/**
	 * 默认获取刚好满屏的告警时间列表
	 * @param map
	 * @return
	 */
	public List<Map> getTimeInfo(Map<String,Object> map)  throws Exception;
	
	/**
	 * 获取20条告警信息
	 * @param map
	 * @return
	 */
	public List<Map> getDeviceInfoDetail(Map<String,Object> map) throws Exception;
	
	/**
	 * 默认获取20条详细告警信息
	 * @param map
	 * @return
	 */
	public List<Map> getDeviceInfoDetail2(Map<String,Object> map) throws Exception;
}
