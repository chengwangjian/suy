package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.CacheWarnMessage;

public interface CacheWarnMessageServiceI {
	/**
	 * 保存告警消息
	 * @param message 告警消息
	 */
	public void saveMessage(CacheWarnMessage message);
	
	public void deleteMessage();
	
	/**
	 * 获取默认最近某一段时间的告警日期信息
	 * @param map
	 */
	public List<Map> getTimeInfo(Map<String,Object> map);
	
	/**
	 * 获取某一天往前十天的告警日期信息
	 * @param map
	 * @return
	 */
	public List<Map> getTimeInfo2(Map<String,Object> map);
	
	/**
	 * 获取默认20条告警信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDeviceInfoDetail(Map<String,Object> map) throws Exception;
	
	/**
	 * 获取某一个时间往前的20条告警消息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDeviceInfoDetail2(Map<String,Object> map) throws Exception;
	
	
}
