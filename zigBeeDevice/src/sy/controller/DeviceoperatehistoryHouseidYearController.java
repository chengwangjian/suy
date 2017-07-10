package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import sy.model.DeviceoperatehistoryHouseidYear;
import sy.model.RespParams;
import sy.service.DeviceoperatehistoryHouseidYearServiceI;

@Controller
@RequestMapping("/deviceoperatehistoryHouseidYearController")
public class DeviceoperatehistoryHouseidYearController {

	private DeviceoperatehistoryHouseidYearServiceI deviceoperatehistoryHouseidYearService;
//	private HouseServiceI houseService;
	
//	public HouseServiceI getHouseService() {
//		return houseService;
//	}
//
//	@Autowired
//	public void setHouseService(HouseServiceI houseService) {
//		this.houseService = houseService;
//	}
	
	private static final Logger LOGGER = Logger.getLogger(DeviceoperatehistoryHouseidYearController.class);  

	public DeviceoperatehistoryHouseidYearServiceI getDeviceoperatehistoryHouseidYearService() {
		return deviceoperatehistoryHouseidYearService;
	}

	@Autowired
	public void setDeviceoperatehistoryHouseidYearService(DeviceoperatehistoryHouseidYearServiceI deviceoperatehistoryHouseidYearService) {
		this.deviceoperatehistoryHouseidYearService = deviceoperatehistoryHouseidYearService;
	}

	@RequestMapping("/add")
	public void saveDeviceoperatehistoryHouseidYear(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*deviceoperatehistoryHouseidYearService.test();
		return "showDeviceoperatehistoryHouseidYear";*/
		
//		String json = request.getParameter("json");
	
		/*resp.setContentType("text/html;charset=gb2312");  
		PrintWriter out=resp.getWriter();  

		out.println(json);  
		out.flush();  */

//		PropertyConfigurator.configure("D:/log4j.properties");        
		/*Map<String,Object> map1 = (Map<String,Object>)JSON.parse(json);
		for (String key : map1.keySet()) { 
			logger.info(key+":"+map1.get(key)); 
		}*/
		
		PrintWriter out=response.getWriter();  
//		System.out.println("json==" + json);
		
		/*List<DeviceoperatehistoryHouseidYear> deviceoperatehistoryHouseidYearList = JSON.parseArray(json,DeviceoperatehistoryHouseidYear.class); 
        for (DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear : deviceoperatehistoryHouseidYearList) { 
        	deviceoperatehistoryHouseidYearService.save(deviceoperatehistoryHouseidYear);
        } */
		
        try {
        	DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear = JSON.parseObject(json,DeviceoperatehistoryHouseidYear.class); 
//        	houseService.addDeviceOperateHistoryTable(deviceoperatehistoryHouseidYear.getHouseIeee());
//        	deviceoperatehistoryHouseidYearService.save(deviceoperatehistoryHouseidYear);
//        	System.out.println("gbk==" + EncodeUtils.encodeStr(deviceoperatehistoryHouseidYear.getOpname(), "gb2312","iso-8859-1"));
        	deviceoperatehistoryHouseidYearService.saveSql(deviceoperatehistoryHouseidYear);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveDeviceoperatehistoryHouseidYear",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveDeviceoperatehistoryHouseidYear";
	}
	
	/**
	 * 查找历史记录列表(单表查询)
	 * @author: zhuangxd
	 * 时间：2014-6-30 下午2:46:59
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		
        try {
        	DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear = JSON.parseObject(json,DeviceoperatehistoryHouseidYear.class); 
        	List<DeviceoperatehistoryHouseidYear> t = deviceoperatehistoryHouseidYearService.findList(deviceoperatehistoryHouseidYear);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 查找历史记录列表(多表关联)  可靠性平台历史记录列表查询
	 * @author: zhuangxd
	 * 时间：2014-5-22 下午4:11:04
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findListory")
	public void findListory(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		
        try {
        	DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear = JSON.parseObject(json,DeviceoperatehistoryHouseidYear.class);
        	if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
        		deviceoperatehistoryHouseidYear.setStartRow(Integer.parseInt(request.getParameter("startRow")));
        		deviceoperatehistoryHouseidYear.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        	}
        	Map t = deviceoperatehistoryHouseidYearService.findListory(deviceoperatehistoryHouseidYear);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("findListory",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	/**
	 * 获取记录总数
	 * @author: zhuangxd
	 * 时间：2014-7-7 下午3:45:50
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getListoryCount")
	public void getListoryCount(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		
        try {
        	DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear = JSON.parseObject(json,DeviceoperatehistoryHouseidYear.class);
        	Map t = deviceoperatehistoryHouseidYearService.getListoryCount(deviceoperatehistoryHouseidYear);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("getListoryCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	/**
	 * 获取操作历史记录
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getLogcat")
	public void getLogcat(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		RespParams resp = new RespParams();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String userName = request.getParameter("user_name");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String count = request.getParameter("count");
			String startIndex = request.getParameter("startIndex");
			int pageIndex = 1, pageSize = 30;
			if(StringUtils.isNotBlank(startIndex)) {
				pageIndex = Integer.parseInt(startIndex);
			}
			if(StringUtils.isNotBlank(count)) {
				pageSize = Integer.parseInt(count);
			}
			List<Map> opList = this.deviceoperatehistoryHouseidYearService.getLogcat(userName, startTime, endTime, pageIndex, pageSize);
			resp.setRequest_id(callback);
			resp.setResponse_params(opList);
			out.println(callback+"("+JSON.toJSONStringWithDateFormat(resp, "yyyy-MM-dd HH:mm:ss" , SerializerFeature.WriteMapNullValue)+")");
		}catch(Exception e){
			LOGGER.error("getLogcat exception", e);
			resp.setRequest_id(callback);
			resp.setResponse_params("");
			out.println(callback+"("+JSON.toJSONString(resp)+")");
		}
		
	}
	
	/**
	 * 根据用户名获取操作历史记录
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getOperator")
	public void getOperator(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		RespParams resp = new RespParams();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String userName = request.getParameter("username");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String count = request.getParameter("count");
			String startIndex = request.getParameter("startIndex");
			int pageIndex = 1, pageSize = 30;
			if(StringUtils.isNotBlank(startIndex)) {
				pageIndex = Integer.parseInt(startIndex);
			}
			if(StringUtils.isNotBlank(count)) {
				pageSize = Integer.parseInt(count);
			}
			List<Map> opList = this.deviceoperatehistoryHouseidYearService.getOperator(userName, startTime, endTime, pageIndex, pageSize);
			resp.setRequest_id(callback);
			resp.setResponse_params(opList);
			out.println(callback+"("+JSON.toJSONStringWithDateFormat(resp, "yyyy-MM-dd HH:mm:ss" , SerializerFeature.WriteMapNullValue)+")");
		}catch(Exception e){
			LOGGER.error("getOperator exception", e);
			resp.setRequest_id(callback);
			resp.setResponse_params("");
			out.println(callback+"("+JSON.toJSONString(resp)+")");
		}
		
	}
	/**
	 * 判断用户离线时长
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @author zhangmaolin
	 * @Time  2016-10-24
	 */
	@RequestMapping("/getOnlinTime")
	public void getOnlineTime(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map params = JSON.parseObject(json, Map.class);
			String houseIeee = (String)params.get("houseIeee");
			String deviceIeee = (String)params.get("ieee");
			String deviceEp = (String)params.get("ep");
			String lastTime = (String)params.get("lasttime");
			Map list = deviceoperatehistoryHouseidYearService.getOnlineTime(houseIeee, deviceIeee, deviceEp);
			String resultJson= JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getOnlineTime",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/operateActionUser")
	public void operateActionUser(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
	     PrintWriter out = response.getWriter();
	     response.setContentType("text/html;charset=utf-8");
	     try {
	    	 DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear = JSON.parseObject(json, DeviceoperatehistoryHouseidYear.class);
	    	 Map AUlist = deviceoperatehistoryHouseidYearService.operateActionUser(deviceoperatehistoryHouseidYear);
	    	 String resultJson = JSON.toJSONStringWithDateFormat(AUlist, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			 String callback = request.getParameter("callback");
			 out.println(callback +"({\"request_id\": 1234, \"response_params\":{\"result\":1,\"getList\":" + resultJson + "}})");
	     } catch (Exception e) {
	    	 LOGGER.info("operateActionUser",e);
			 String resultJson = "{\"result\":0}";//成功1 失败0
			 String callback = request.getParameter("callback");
			 out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	     }
    }
	
    @RequestMapping("/exportOperateLogExcel")
	public void exportOperateLogExcel(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try {
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String name = (String)paramMap.get("operateNameCn");
			LOGGER.info("得到的动作名称operateNameCn是=======》》》："+ name);
			int t = deviceoperatehistoryHouseidYearService.exportOperateLogExcel(paramMap,request,response);
		} catch (Exception e) {
			LOGGER.info("exportLqiLogExcel", e);
			try {
				out = response.getWriter();
			} catch(Exception ex) {
				LOGGER.info("exportOperateLogExcel getWriter", ex);
				resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
			}
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback +"{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
}
