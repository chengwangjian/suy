package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sy.model.DeviceoperatehistoryHouseidYear;

public interface DeviceoperatehistoryHouseidYearServiceI {

	public DeviceoperatehistoryHouseidYear save(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear);

	public DeviceoperatehistoryHouseidYear login(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear);

//	public DataGrid datagrid(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear);
	
	public List<DeviceoperatehistoryHouseidYear> findList(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear);
	
	public int saveSql(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear);
	
	public Map findListory(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear);
	
	public Map getListoryCount(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear);

	public void remove(String ids);
	
	public void test();	
	
	/**
	 * 操作历史定时保存
	 */
	public int savedingshi();

	/**
	 * 农业APP获取操作历史记录
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Map> getLogcat(String userName, String startTime, String endTime, int pageIndex, int pageSize) throws Exception;

	/**
	 * 根据用户名获取操作历史记录
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Map> getOperator(String userName, String startTime, String endTime, int pageIndex, int pageSize) throws Exception;
	
    public Map getOnlineTime(String houseIeee, String deviceIeee, String deviceEp) throws Exception;
	
	//public List<Map> getVolotageValue(Map param) throws Exception;
	/*根据用户网关、设备ieee、设备ep查找操作者和动作名称*/
	public Map operateActionUser(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) throws Exception;
	
	//导出报警历史列表
	public int exportOperateLogExcel(Map<String, Object> param, HttpServletRequest request,HttpServletResponse  response) ;
}
