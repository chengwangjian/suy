package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smarthome.domain.Warntypetable;

import sy.model.DevicewarnhistoryHouseidYear;

public interface DevicewarnhistoryHouseidYearServiceI {

	public DevicewarnhistoryHouseidYear save(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);

	public DevicewarnhistoryHouseidYear login(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);
	
	public List<DevicewarnhistoryHouseidYear> findList(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);

//	public DataGrid datagrid(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);

	public void remove(String ids);
	
	public int saveSql(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);
	
	public void test();	
	
	/**
	 * 报警记录显示（多表查询）
	 * @author pengcq
	 * @param devicewarnhistoryHouseidYear
	 * @return
	 */
	public Map findwarnListory(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);
	/**
	 * 报警记录显示总数
	 * @author pengcq
	 * @param devicewarnhistoryHouseidYear
	 * @return
	 */
	public Map getwarnListory(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);
	
	public List<Map> getHistoryWarnDataPage(String houseIeee,String beginDateTime,String endDateTime, String string, String string2);
	
	/**
	 * 报警历史定时保存
	 */
	public int savedingshiwarn();

	public List<Map> findListPhoneno(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);
	
	public int insertSMS(String content, String houseIeee, String phoneno, String state,DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear);
	
	/**
	 * 查询短信号码
	 * @param houseIEEE
	 * @return
	 */
	public List<Map> getMessPhone(String houseIEEE);
	
	/**
	 * 查询邮箱地址
	 * @param houseIEEE
	 * @return
	 */
	public List<Map> getMessEmail(String houseIEEE);
	
	/**
	 * 批量插入历史记录表
	 * @param sql
	 * @return
	 */
	public int batchSave(String sql);
	
	/**
	 * 发送邮件
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public void sendemail(String msg, String toEmail) throws Exception;
	
	/**
	 * 获取增加是否发送短信、是否发送邮件字段后的告警历史记录
	 * @param warnList
	 * @return
	 * @throws Exception
	 */
	public Map getNewWarnHistory(List warnList) throws Exception;
	public List<Map> getzonewarntype();
	public Warntypetable getwarntype(String w_mode);

	/**
	 * 获取房间及设备名称
	 * @param houseIeee
	 * @param roomId
	 * @param ieee
	 * @param ep
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getRoomAndDevice(String houseIeee, long roomId, String ieee, String ep) throws Exception;
	
	/**
	 * 放入缓存
	 * @param devWarn
	 * @throws Exception
	 */
	public void addDeviceWarnSMS(DevicewarnhistoryHouseidYear devWarn) throws Exception;
	
	/**
	 * 跳转发送消息
	 * @throws Exception
	 */
	public void sendDeviceWarnSMS() throws Exception;

	public List<Map> getHistoryWarnData(String string, String string2,
			String string3);

	public Map getHistoryWarnDataTotalCount(Map<String, Object> paramMap);
	
	/**
	 * 获取告警信息接口
	 * @param userName
	 * @param houseIEEE
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<Map> getFarmWarnData(String userName, String houseIEEE, String startTime, String endTime) throws Exception;
	
	/**
	 * 获取离线设备是否有低压警告
	 * @param houseIEEE
	 * @param lasttime
	 * @param deviceIeee
	 * @param devcieEp
	 * @return
	 * @throws Exception
	 */
	public List<Map> getZoneWmode(String houseIEEE, String deviceIeee,String deviceEp, String lasttime) throws Exception;
	/**
	 * 获取报警类型
	 * @param houseIEEE
	 * @param deviceIeee
	 * @param deviceEp
	 * @return
	 * @throws Exception
	 */
	public List<Map> getWarnMode(String houseIEEE, String deviceIeee, String deviceEp) throws Exception;
	
	//导出报警历史列表
	public int exportWarnLogExcel(Map<String, Object> param, HttpServletRequest request,HttpServletResponse  response) ;
	
	/**
	 * 根据ip获取服务器推送短信方式
	 * @param ipAddress 服务器ip
	 */
	public Map getSMSType(String ipAddress);
	
	/**
	 * 根据houseIeee获取网关的短信推送方式
	 * @param houseIeee
	 * @return
	 */
	public Byte getShcSmsType(String houseIeee);
}
