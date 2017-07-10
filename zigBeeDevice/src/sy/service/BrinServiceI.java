package sy.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BrinServiceI {
	/**
	 * 添加
	 * @param paramMap
	 * @return
	 */
	public int add(Map<String, Object> paramMap); 
	
	/**
	 * 搜索和列表显示
	 * @param param
	 * @param startRow
	 * @param pageSize_
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<Map> findbrin(Map<String, Object> param, int startRow, int pageSize_,String orderBy) throws Exception;
	/**
	 * 开启加网
	 * @param param
	 * @return
	 */
	public int opennet(Map<String, Object> param) ;
	/**
	 * 	完成
	 * @param paramMap
	 * @return
	 */
	public int finish(Map<String, Object> paramMap);
	public int finishzhuce(Map<String, Object> paramMap);
	/**
	 * 关闭加网
	 * @param param
	 * @return
	 */
	public int closenet(Map<String, Object> param) ;
	
	/**
	 * 获取burnin设备个数
	 * @param params
	 * @return
	 */
	public List<Map> getdeviceCount(Map<String, Object> paramMap);
	
	/**
	 * 重新
	 * @param paramMap
	 * @return
	 */
	public int restart(Map<String, Object> paramMap); 
	
	/**
	 * 初始化
	 * @param param
	 * @return
	 */
	public int updateInit(Map<String, Object> param);
	/**
	 * 分页
	 * @param update
	 * @return
	 */
	public Map getCount(Map<String, Object> update);
	
//	public void updateDeviceAllFlag(String offHouseIEEEs, String onHouseIEEEs) throws Exception;
	
	public int isRegister(String houseIEEE) ;	
//	
//	public Map isonlineCount(Map<String, Object> paramMap) ;
//		
//	
	public int addIntegrity(Map<String, Object> param);
	/**
	 * 保存初始化结果
	 * @param param
	 * @return
	 */
	public int updateInitResult(Map<String, Object> param) ;
		
//	public int updateintFlag(Map<String, Object> paramMap);
	
	/**
	 * 忽略
	 */
	public int updateComplete(Map<String, Object> paramMap);
	/**
	 * 确定
	 * @param paramMap
	 * @return
	 */
	public int updateSure(Map<String, Object> paramMap);
	
	/**
	 * 定时修改burn in状态
	 * @throws IOException
	 */
	public void updateRflag() throws IOException;
	
	/**
	 * 列表显示有问题的设备
	 */
	public List<Map> findBrinwrong(Map<String, Object> paramMap); 
	
	/**
	 * 查询device表离线的设备
	 */
	public List<Map> findWrongdevice(Map<String , Object> paraMap);
	
	/**
	 * 统计在线、离线个数
	 */
	public List<Map> onlineCount(Map<String , Object> paraMap);
	
	public int findExce(Map<String , Object> paraMap);
}
