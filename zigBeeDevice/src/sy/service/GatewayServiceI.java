package sy.service;

import java.util.List;
import java.util.Map;

public interface GatewayServiceI {
	/**
	 * 保存记录
	 * @return
	 * @throws Exception
	 */
	public int saveLog(Map map);
	
	/**
	 * 查询总数
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	public long getGatewayLogCount(String searchText) throws Exception;
	/**
	 * 查询数量
	 * @return
	 * @throws Exception
	 */
	public List<Map> getGatewayLogs(String searchText, int pageSize, int startRow) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteGatewayLog(String id) throws Exception;
	
	/**
	 * 获取重启次数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map> getStartOver(String houseIeee,String lasttime) throws Exception;



}
