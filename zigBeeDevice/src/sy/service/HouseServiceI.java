package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sy.model.House;
import sy.model.Houseieee;
import sy.model.Houseservice;
import sy.model.Messagehistory;
import sy.model.Modenode;
import sy.model.Proxyserver;
import sy.pageModel.HouseInfo;

public interface HouseServiceI {

	public House save(House house);
	
	public House find(House house);
	
//	public List<House> findList(House house);
	public List<Map> findList(House house,String userid) throws Exception;//修改成返回Map元素的List
	
	public List<House> findkeyword(House house);
	
	public List<Object[]> findListSql(House house);
	
	public Map getListoryCount(House house);

	public House login(House house);
	
	public House get(House house);
	
	public House update(House house);
	
	public List<House> findList2(House house);
	
	/*public int updateAllIsonline(List<String> list);
	
	public int updateAllIsonline();*/
	
	public int delete(House house);
	
	public int addDeviceAttributeHistoryTable(String houseIeee);
	
	public int addDeviceWarnHistoryTable(String houseIEEE);
	
	public int addDeviceOperateHistoryTable(String houseIEEE);

//	public DataGrid datagrid(House house);

	public void remove(String ids);
	
	public void test();	

	public int updateHouseSwitch(Map<String, Object> paramMap, List<String> houseIeeeList);
	
	public int getHouseCount(Map<String, Object> conditionMap,String userid);
	
	public List<Map> getHouses(Map<String, Object> conditionMap,String userid, int startRow, int pageSize);
	
	/*查询house与houseieee表*/
	public List<Map> findhouseAndhouseieee(House house,Houseieee houseieee,Proxyserver proxyserver);
	/**
	 * 获取操作未成功house
	 * @return
	 */
	public List<Map> getServiceFailHouses();
	
	/**
	 * 执行向z203发送house的服务状态操作
	 */
	public void executeAction();


	/**
	 * execl批量导入
	 */
	public int  insertExeclBatch(HouseInfo houseInfo);

	public House get1(House house);

	public int delete1(House house);
	/**
	 * 家庭注册
	 * 搜索 分页 排序 
	 * @param startRow
	 * @param pageSize
	 * @param orderBy
	 * @param house
	 * @return
	 */
	public List<House> findList(String startRow, String pageSize,String orderBy, Map<String, Object> house);
	
	public int getCount(Map<String, Object> update);

	List<House> findkeyword(String startRow, String pageSize,House house);

	/**
	 * 获取house的经纬度信息
	 * @return
	 */
	public List<Map> getHouseXY();


	public List<Houseservice> getshc(Houseservice Houseservice);

	public int openshc(Houseservice Houseservice);

	public int closeshc(Houseservice Houseservice);

	
	/**
	 * 查询出发送邮件地址并发送邮件  updateAllIsonline（旧版）
	 * @param houseIeees
	 * @throws Exception
	 */
	public void abtainEmailAndSend(List<String> houseIeees) throws Exception;
	/**
	 * 查询出发送邮件地址并发送邮件  updateZ203Isonline（新版）
	 * @param houseIeees
	 * @throws Exception
	 */
//	public void abtainEmailAndSendSHC(List<House> houseIeees) throws Exception;
	int updateIPKversion(Map ipk);
	/**
	 * 推送203是否在线，推送IP地址
	 * @param list
	 * @throws Exception
	 */
	public void updateZ203Isonline(List<Map> liston, List<Map> listoff,String xmppIp) throws Exception;
	
	/**
	 * 判断家是否存在203或206设备
	 * @throws Exception
	 */
	public List<Modenode> isExist203206(Modenode modenode) throws Exception;
	
	
	public Map get203user(String houseieee) throws Exception;
	

	/**
	 * 查询监控日志
	 * @param param
	 * @param pageSize_ 
	 * @param startRow 
	 * @return
	 * @throws Exception
	 */
	public Map findMonitorlog(Map<String, Object> param, int startRow, int pageSize_,String orderBy) throws Exception;
	/**
	 * 统计监控日志条数
	 * @param param
	 * @return
	 */
	public int getMonitorlogCount(Map<String, Object> param);
//	/**
//	 * 统计一直不在线
//	 * @param param
//	 * @return
//	 */
//	public int stilloffline();
//	
//	/**
//	 * 统计监控总数
//	 * @param paraMap
//	 * @return
//	 */
//	public int monitorlogCount() ;
//	/**
//	 * 统计状态发生变化的台数
//	 * @param paraMap
//	 * @return
//	 */
//	public int monitorchange() ;
	
	/**
	 * 导出监控日志
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	public int exportMonLogExcel(Map<String, Object> param, HttpServletRequest request,HttpServletResponse  response) ;
	
	/**
	 * 统计shc监控情况台数
	 */
	public List<Map> shcCount(Integer userId);
		
	/**
	 * 更新z203设备的状态
	 * @param onHouseIEEEs
	 * @return
	 */
	public int updateZ203DeviceIsonline(String onHouseIEEEs);
	
	/**
	 * 获取拾联科技平台相关信息（地址、账户、密码）
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSLPlatformInfo(Map<String,Object> params) throws Exception;
	/**
	 * 获取云端服务器IP
	 * @param paramMap
	 * @return
	 */
	public List<Map> getServerIP(Map<String, Object> paramMap);
	/**
	 * 列表
	 * @param startRow
	 * @param pageSize
	 * @param orderBy
	 * @param house
	 * @return
	 */
	public List<Map> findhouseList(String startRow, String pageSize,String orderBy, Map<String, Object> house,String userid);
	/**
	 * Cloud服务器计数
	 * @param paramMap
	 * @return
	 */
	public List<Map> cloudCount(Map<String, Object> paramMap);
	/**
	 * 迁移
	 * @param houseIEEEs
	 * @return
	 */
	public List<Map> getHousesInfo(List<String> houseIEEEs);
	
	/**
	 *新增服务器
	 */
	public int addServer(Map<String, Object> param);
	
	/**
	 * 服务器列表
	 * @param startRow
	 * @param pageSize
	 * @param orderBy
	 * @param param
	 * @return
	 */
	public List<Map> findServerlib(String startRow, String pageSize,String orderBy, Map<String, Object> param);
	
	/**
	 * 服务器列表分页
	 * @param param
	 * @return
	 */
	public int getServerCount(Map<String, Object> param); 
	/**
	 * 服务器编辑
	 * @param param
	 * @return
	 */
	public int updateServer(Map<String, Object> param) ;
	
	/**
	 * 服务器删除
	 * 
	 */
	public int deleteServer(Map<String, Object> param) ;
	public List<Map> findhouseList2(String startRow, String pageSize,String orderBy, Map<String, Object> paramMap, String house, String uncheckedHouses);
	public int getCount2(Map<String, Object> paramMap,String house, String uncheckedHouses);

	//短信邮件批量开启
	public int NoteEmailBatchOpen(Map<String,Object> param,List<String> houseieee);
    public String notebatchSend(Messagehistory messagehistory,List<String> houseieee);
    public List<Map>  findimport(List<Map<String,Object>> huosmapli);
	
	void abtainEmailAndSendSHC(List<Map> houseonIeees,
			List<Map> houseoffIeees) throws Exception;

	/**
	 * 定时更新house的短信、邮件状态
	 */
	public void updateSMSEmailState();
	
	/**
	 * 批量发送邮件
	 * @param subject 邮件主题
	 * @param messString 发送内容
	 * @param emailToList 发送对象
	 * @return
	 * @throws Exception
	 */
	public int sendemail(String subject, String messString, List<String> emailToList) throws Exception;

	public void loadProperties();
	
	/**
	 * 获取某个云端服务器已注册的家的个数
	 * @param cloudIp 云端地址
	 * @return
	 */
	public int getRegisterCount(String cloudIp) throws Exception ;
	
	public List<Map> getReliClient(Map<String, Object> pMap);

	public List<Map> getReliClientByregion(String code,String region);

	public List<Map> getReliClientByregion(Object region,Object userid);
     
	public List<Map> getReliClientByCode(String code);
	
	public List<Map> getReliClientByUserId(String clientCode,String userid);
	/**
	 * 检查服务器是否注册或注册是否过期
	 * @throws Exception
	 */
	public void validServer() throws Exception;

	public List<Map> getServerInfo();
	
	/**
	 * 是否所有注册家的云端服务器ip相同
	 * @param params
	 * @return
	 */
	public boolean isServerAllTheSame(Map<String, Object> params, String uncheckedHouses);
	
	public List<Map> getMonitorState (String houseIeee) throws Exception;
	//查找网关的连接状态和改变时间
	public List<Map> getGateWays(String houseIeee,String userid) throws Exception;
	/**
	 * 往数据库添加拾联云平台账户
	 * @param user 拾联云平台账户
	 * @return
	 * @throws Exception
	 */
	public int saveSLPlatformUser(Map<String,Object> user) throws Exception;
	/**
	 * 从数据库删除拾联云平台账户
	 * @param user 拾联云平台账户
	 * @return
	 * @throws Exception
	 */
	public int deleteSLPlatformUser(Map<String,Object> user) throws Exception;
	/**
	 * 往拾联云平台创建账号
	 * @param house
	 * @return
	 * @throws Exception
	 */
	public int addSLAccount(House house) throws Exception;
	/**
	 * 往拾联云平台删除账号
	 * @param house
	 * @return
	 * @throws Exception
	 */
	public int deleteSLAccount(House house) throws Exception;
	/**
	 * 
	 * @param params
	 * @return
	 */
	public String getServerDomain(String serverIp);
	
	public List<Map> FindServerUser();
	/**
	 * 返回经纬度业务接口
	 * @return
	 */
	public List<Map> getLong();
	
}
