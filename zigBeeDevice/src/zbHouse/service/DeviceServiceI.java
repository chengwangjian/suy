package zbHouse.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sy.model.Devicemonitorlog;
import zbHouse.model.Device;
import zbHouse.pageModel.DataGrid;

public interface DeviceServiceI {

	public Device keyUpdate(Device device);

	public Device saveOrUpdate(Device device,Map<String, Object> params);
	
	public int delete(Map<String, Object> params);
	
	public DataGrid find(Map<String, Object> params);
	
	public Device getIsonline(Device device);
	
	public Device updateIsonline(Device newdata) throws Exception;
	
	public Map getListoryCount(Map<String, Object> params);

	public DataGrid findonline(Map<String, Object> params);

	public Device updatefirstIsonline(Device newdata);
	
	public int updatebrin(Device newdata);
	public void execlfindline(HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> params)throws Exception;

	/**
	 * 定时保存数据函数
	 */
	public void saveCacheData();
	
	public Devicemonitorlog save(Devicemonitorlog devicemonitorlog);
	
//	/**
//	 * 设备离线时清除device表中attribute字段
//	 */
//	public int updateDeviceAttribute(String houseIEEE, String deviceIeee, String deviceEp);
//	
	/**
	 * 更新设备是否在线
	 * @param device
	 * @throws Exception
	 */
	public int modeifyDevAndAttrIline(Device js) throws Exception;
	
	/**
	 * 导出设备列表
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
/*	public int exportDeviceExcel(Map<String, Object> params, HttpServletRequest request,HttpServletResponse  response) ;*/
	
	/*public int exportDeviceExcel(HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> params)throws Exception;*/
	
	public int exportDeviceExcel(Map<String, Object> params, HttpServletRequest request,HttpServletResponse  response) ;
			
	public List<Map> getfindlist(Map params) throws Exception;


}
