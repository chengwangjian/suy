package sy.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sy.model.RssiRecord;

/**
 * 
 * @author huanglw RSSI 推送数据保存接口
 * 
 */
public interface RssiRecordServiceI {
	/**
	 * 新增RSSI推送记录
	 * 
	 * @param rssiRecord
	 * @return
	 * @throws Exception
	 */
	public void add(RssiRecord rssiRecord) throws Exception;

	/**
	 * 获取RSSI总行数
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 * @throws Exception
	 */
	public int getRSSICount(String searchText, String houseIeee,String type,String time, Map map) throws Exception;

	/**
	 * 分页查询RSSI记录
	 * 
	 * @param condition
	 *            查询条件
	 * @param pageIndex
	 *            第几页
	 * @param pageSize
	 *            一页行数
	 * @param orderB
	 *            排序逻辑
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryRSSI(String searchText, int pageIndex, int pageSize,
			String orderBy, String houseIeee,String type,String time, Map<String, Object> map) throws Exception;

	/**
	 * 获取信号正常的设备数量
	 * 
	 * @param condition
	 *            查询条件
	 * @param rssiBValue
	 *            rssi值得临界值
	 * @return
	 * @throws Exception
	 */
	public int getNormalDeviceCount(String searchText, int rssiBValue, String houseIeee,String type,String time,Map<String, Object> map)
			throws Exception;

	/**
	 * 获取没有响应的设备信息
	 * @param parseInt
	 * @param parseInt2
	 * @param houseIeee
	 * @param type
	 * @param time
	 * @return
	 */
	public Object getNoResponseDevice(int parseInt, int parseInt2,
			String houseIeee, String type,String time);

	/**
	 * 获取本次搜索的设备统计信息
	 * @param map
	 * @param time
	 * @return
	 */
	public Object getNoResponseCount(Map map,String time);

	//public int deleteRecord(Date t,String houseIEEE);

	/**
	 * 保存RSSI数据
	 * @throws Exception
	 */
	public void saveRssiRecords() throws Exception;
	
	/**
	 * 立即保存rssi值
	 * @param rssiRecord
	 * @throws Exception
	 */
	public void saveRssiRecordsImmtly(RssiRecord rssiRecord) throws Exception;
	
	/**
	 * 查找用户的RSSI和用户的LQI的值
	 * @throws Exception
	 */
	public Map getLR(String houseIeee,String deviceIeee,String deviceEp,String orderBy) throws Exception;
	
	public List<Map> queryNewRSSI(String searchText, int pageIndex, int pageSize,
			String orderBy, String houseIeee,String type,String time, Map<String, Object> map) throws Exception;
	
	public int getNewNormalDeviceCount(String searchText, int newrssiBValue, String houseIeee,String type,String time,Map<String, Object> map)
			throws Exception;
	
	//导出网络质量列表
	public int exportLqiLogExcel(Map<String, Object> param, HttpServletRequest request,HttpServletResponse  response) ;
	public int exportRssiLogExcel(Map<String, Object> param, HttpServletRequest request,HttpServletResponse  response) ;
	
	public int getNewRSSICount(String searchText, String houseIeee,String type,String time, Map map) throws Exception;
}

