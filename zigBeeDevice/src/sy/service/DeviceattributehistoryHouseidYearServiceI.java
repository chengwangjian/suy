package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sy.model.DeviceattributehistoryHouseidYear;

public interface DeviceattributehistoryHouseidYearServiceI {

	public DeviceattributehistoryHouseidYear save(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);

	public DeviceattributehistoryHouseidYear login(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);

	public int saveSql(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);
	
	public int getListCount(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);
	
	public List<DeviceattributehistoryHouseidYear> findList(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);
	
//	public DataGrid datagrid(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);
	
	public Map findList2(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);
	
	public int updateDeviceTime(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);

	public void remove(String ids);
	
	public void test();	
	
	/**
	 * 修改device表中的deviceattribute
	 */
//	public int updateAttrnameValue(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear);
	/**
	 * 心跳统计
	 * @param deviceattributehistoryHouseidYear
	 * @param paramMap
	 * @return
	 */
	public Map getHeartbeat(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap);
	public Map getHeartbeatCount(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap);
	public int exportHeartbeatExcel(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap, HttpServletRequest request,HttpServletResponse  response) throws Exception;
	/**
	 * 电能相关 
	 * @param deviceattributehistoryHouseidYear
	 * @param paramMap
	 * @return
	 */
	public Map getElectricityRelated(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap);
	public Map getElectricityRelatedCount(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap);
	public int exportElecExcel(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap, HttpServletRequest request,HttpServletResponse  response) throws Exception;
	/**
	 * 电池电压
	 * @param deviceattributehistoryHouseidYear
	 * @param paramMap
	 * @return
	 */
	public Map getBatteryVoltage(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap);
	public Map getBatteryVoltageCount(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap);
	public int exportBattExcel(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap, HttpServletRequest request,HttpServletResponse  response) throws Exception;
 
	public List<Map> findServerIp(Map<String, Object> params);
	
	public List<Map> attributeState(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) throws Exception;
	
	//导出属性历史列表
	public int exportAttrLogExcel(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear, HttpServletRequest request,HttpServletResponse response) ;
	
	public Map getVolotageValue(Map param) throws Exception;
}
