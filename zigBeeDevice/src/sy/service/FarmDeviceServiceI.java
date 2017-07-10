package sy.service;

import java.util.List;
import java.util.Map;



/**
 * @author huanglw  设备列表
 *
 */

public interface FarmDeviceServiceI {

	/**
	 * 获取设备列表
	 * @param userName
	 * @param houseIeee
	 * @throws Exception
	 */
	public List<Map> getEndPoint(String userName, String houseIeee, int pageIndex, int pageSize) throws Exception;
	
	/**
	 * 获取图表数据
	 * @param chartType
	 * @param startTime
	 * @param endTime
	 * @param houseIeee
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getChartData(Integer chartType, String startTime, String endTime, String houseIeee, int unit) throws Exception;
	
	/**
	 * 推送设备信息
	 * @param houseIeee
	 * @param params
	 * @throws Exception
	 */
	public void pushDeviceInfo(String houseIeee, Map params) throws Exception;
	
	
	/**
	 * 获取过去小时室内室外PM2.5图表数据
	 * @param houseIeee
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getPm25ChartData(String houseIeee, String deviceIeee, String ip, String platformType) throws Exception;
}


