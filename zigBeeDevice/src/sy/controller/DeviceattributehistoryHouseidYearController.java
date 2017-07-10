package sy.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.DeviceattributehistoryHouseidYear;
import sy.service.DeviceattributehistoryHouseidYearServiceI;
import sy.service.HouseServiceI;
import sy.util.HouseieeeListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
@SuppressWarnings("all")
@Controller
@RequestMapping("/deviceattributehistoryHouseidYearController")
public class DeviceattributehistoryHouseidYearController {

	private DeviceattributehistoryHouseidYearServiceI deviceattributehistoryHouseidYearService;
	private HouseServiceI houseService;
	
	public HouseServiceI getHouseService() {
		return houseService;
	}

	@Autowired
	public void setHouseService(HouseServiceI houseService) {
		this.houseService = houseService;
	}
	
	private static final Logger LOGGER = Logger.getLogger(DeviceattributehistoryHouseidYearController.class);  

	public DeviceattributehistoryHouseidYearServiceI getDeviceattributehistoryHouseidYearService() {
		return deviceattributehistoryHouseidYearService;
	}

	@Autowired
	public void setDeviceattributehistoryHouseidYearService(DeviceattributehistoryHouseidYearServiceI deviceattributehistoryHouseidYearService) {
		this.deviceattributehistoryHouseidYearService = deviceattributehistoryHouseidYearService;
	}
/**
 * 此接口暂时没用
 * @param json
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
//	@RequestMapping("/add")
//	public void saveDeviceattributehistoryHouseidYear(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
//		/*deviceattributehistoryHouseidYearService.test();
//		return "showDeviceattributehistoryHouseidYear";*/
//		
////		String json = request.getParameter("json");
//	
//		/*resp.setContentType("text/html;charset=gb2312");  
//		PrintWriter out=resp.getWriter();  
//
//		out.println(json);  
//		out.flush();  */
//
////		PropertyConfigurator.configure("D:/log4j.properties");        
//		/*Map<String,Object> map1 = (Map<String,Object>)JSON.parse(json);
//		for (String key : map1.keySet()) { 
//			logger.info(key+":"+map1.get(key)); 
//		}*/
//		PrintWriter out=response.getWriter();  
////		System.out.println("json==" + json);
//		
//		/*List<DeviceattributehistoryHouseidYear> deviceattributehistoryHouseidYearList = JSON.parseArray(json,DeviceattributehistoryHouseidYear.class); 
//        for (DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear : deviceattributehistoryHouseidYearList) { 
//        	deviceattributehistoryHouseidYearService.save(deviceattributehistoryHouseidYear);
//        } */
//		
//        try {
//        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class); 
//        	houseService.addDeviceAttributeHistoryTable(deviceattributehistoryHouseidYear.getHouseIeee());
////        	deviceattributehistoryHouseidYearService.save(deviceattributehistoryHouseidYear);
//        	deviceattributehistoryHouseidYearService.saveSql(deviceattributehistoryHouseidYear);
//        	//修改device表中的deviceattribute
//        	deviceattributehistoryHouseidYearService.updateAttrnameValue(deviceattributehistoryHouseidYear);
//        	String resultJson = "{\"result\":1}";//成功1 失败0
//			String callback = request.getParameter("callback");
//			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//		} catch (Exception e) {
//			LOGGER.info("saveDeviceattributehistoryHouseidYear",e);
//			String resultJson = "{\"result\":0}";//成功1 失败0
//			String callback = request.getParameter("callback");
//			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
//		}
//
////        String resultJson = "{\"result\":\"1\"}";
//        
////		out.flush();
//		
////		System.out.println("resultJson==" + resultJson);
//		
////		return "saveDeviceattributehistoryHouseidYear";
//	}
//	
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		response.setContentType("text/html;charset=utf-8"); 
		
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class); 
        	List<DeviceattributehistoryHouseidYear> t = deviceattributehistoryHouseidYearService.findList(deviceattributehistoryHouseidYear);
        	List<DeviceattributehistoryHouseidYear> t2 = new ArrayList<DeviceattributehistoryHouseidYear>();
        	
			String times = "0";
        	long times1 = 0;
        	long times2 = 0;
        	long times3 = 0;
        	Date opdatetime = new Date();
        	if (t != null && t.size() > 0) {
        		for (int i = t.size()-1;i>=0;i--) { 
        			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear2 = t.get(i);
        			if (i== t.size()-1) {
        				deviceattributehistoryHouseidYear2.setTimes("0");
            			opdatetime = deviceattributehistoryHouseidYear2.getOpdatetime();
        			} else {
        				times1 = opdatetime.getTime();
        				times2 = deviceattributehistoryHouseidYear2.getOpdatetime().getTime();
        				times3 = (times2 - times1) / 1000; // 间隔毫秒数/1000=间隔秒数      
        				deviceattributehistoryHouseidYear2.setTimes(String.valueOf(times3));
            			opdatetime = deviceattributehistoryHouseidYear2.getOpdatetime();
        			}
                }
        	}
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 可靠性平台属性报告列表查询(显示属性中文名)
	 * @author: zhuangxd
	 * 时间：2014-7-8 下午4:15:10
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/find2")
	public void find2(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		LOGGER.info("获取得到的json的值是---》：" + json);
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
        	if(deviceattributehistoryHouseidYear.getHouseIeee().equals(deviceattributehistoryHouseidYear.getDeviceIeee())){
        		String resultJson = "{\"result\":0}";//成功1 失败0
    			String callback = request.getParameter("callback");
    			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
    			return;
        	}
        	int length = Integer.parseInt(request.getParameter("pageSize"));
        	if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
        		deviceattributehistoryHouseidYear.setStartRow(Integer.parseInt(request.getParameter("startRow")));
        	    deviceattributehistoryHouseidYear.setPageSize(length + 1);
        	}
        	Map t3 = deviceattributehistoryHouseidYearService.findList2(deviceattributehistoryHouseidYear);
        	List<Map> t = (List<Map>) t3.get("listory");
        	List<DeviceattributehistoryHouseidYear> t2 = new ArrayList<DeviceattributehistoryHouseidYear>();
        	String times = "0";
        	long times1 = 0;
        	long times2 = 0;
        	long times3 = 0;
        	Date opdatetime = new Date();
        	if (t != null && t.size() > 0) {
        		for (int i = t.size()-1;i >= 0; i --) { 
        			Map deviceattributehistoryHouseidYear2 = t.get(i);
        			if (i == t.size()-1) {
        				deviceattributehistoryHouseidYear2.put("times", "0");
            			opdatetime = (Date)deviceattributehistoryHouseidYear2.get("opdatetime");
        			} else {
        				times1 = opdatetime.getTime();
        				times2 = ((Date)deviceattributehistoryHouseidYear2.get("opdatetime")).getTime();
        				times3 = (times2 - times1) / 1000; // 间隔毫秒数/1000=间隔秒数      
        				deviceattributehistoryHouseidYear2.put("times",String.valueOf(times3));
            			opdatetime = (Date)deviceattributehistoryHouseidYear2.get("opdatetime");
        			}
                }
        	}
        	
        	if(t != null && t.size() > length ){
        		t.remove(t.size() - 1);
        	}
        	
        	String resultJson= JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find2",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 可靠性平台属性报告列表查询（统计总数量）
	 * @author: zhuangxd
	 * 时间：2014-7-8 下午4:15:10
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getListCount")
	public void getListCount(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		LOGGER.info("获取得到的json的值是---》：" + json);
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class); 
        	int total = deviceattributehistoryHouseidYearService.getListCount(deviceattributehistoryHouseidYear);
//        	String resultJson= JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		String resultJson = "{\"result\":1,\"total\":" + total + "}";
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("getListCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	/**
	 * 203稳定数据统计显示
	 * @author pengcq
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getHeartbeat")
	public void getHeartbeat(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
        	if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
        		deviceattributehistoryHouseidYear.setStartRow(Integer.parseInt(request.getParameter("startRow")));
        		deviceattributehistoryHouseidYear.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        	}
        	Map t3 = deviceattributehistoryHouseidYearService.getHeartbeat(deviceattributehistoryHouseidYear, paramMap);
        	long daystr = getDayStr(paramMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + ",\"daystr\":"+daystr+"})"); 
		} catch (Exception e) {
			LOGGER.info("getHeartbeat",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	@RequestMapping("/getHeartbeatCount")
	public void getHeartbeatCount(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		String cloudAddress = (String) HouseieeeListener.houseieeeProxyserverMap.get(paramMap.get("houseIeee"));
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
        	Map t3 = deviceattributehistoryHouseidYearService.getHeartbeatCount(deviceattributehistoryHouseidYear, paramMap);
        	long daystr = getDayStr(paramMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + ",\"daystr\":"+daystr+",\"cloudAddress\":\""+cloudAddress+"\"})"); 
		} catch (Exception e) {
			LOGGER.info("getHeartbeatCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	@RequestMapping("/getElectricityRelated")
	public void getElectricityRelated(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
        	if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
        		deviceattributehistoryHouseidYear.setStartRow(Integer.parseInt(request.getParameter("startRow")));
        		deviceattributehistoryHouseidYear.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        	}
        	Map t3 = deviceattributehistoryHouseidYearService.getElectricityRelated(deviceattributehistoryHouseidYear, paramMap);
        	long daystr = getDayStr(paramMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + ",\"daystr\":"+daystr+"})"); 
		} catch (Exception e) {
			LOGGER.info("getElectricityRelated",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	@RequestMapping("/getElectricityRelatedCount")
	public void getElectricityRelatedCount(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		String cloudAddress = (String) HouseieeeListener.houseieeeProxyserverMap.get(paramMap.get("houseIeee"));
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
        	Map t3 = deviceattributehistoryHouseidYearService.getElectricityRelatedCount(deviceattributehistoryHouseidYear, paramMap);
        	long daystr = getDayStr(paramMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + ",\"daystr\":"+daystr+",\"cloudAddress\":\""+cloudAddress+"\"})"); 
		} catch (Exception e) {
			LOGGER.info("getElectricityRelatedCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	@RequestMapping("/getBatteryVoltage")
	public void getBatteryVoltage(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
        	if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
        		deviceattributehistoryHouseidYear.setStartRow(Integer.parseInt(request.getParameter("startRow")));
        		deviceattributehistoryHouseidYear.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        	}
        	Map t3 = deviceattributehistoryHouseidYearService.getBatteryVoltage(deviceattributehistoryHouseidYear, paramMap);
        	long daystr = getDayStr(paramMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + ",\"daystr\":"+daystr+"})"); 
		} catch (Exception e) {
			LOGGER.info("getBatteryVoltage",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	@RequestMapping("/getBatteryVoltageCount")
	public void getBatteryVoltageCount(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		String cloudAddress = (String) HouseieeeListener.houseieeeProxyserverMap.get(paramMap.get("houseIeee"));
        try {
        	DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
        	Map t3 = deviceattributehistoryHouseidYearService.getBatteryVoltageCount(deviceattributehistoryHouseidYear, paramMap);
        	long daystr = getDayStr(paramMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + ",\"daystr\":"+daystr+",\"cloudAddress\":\""+cloudAddress+"\"})"); 
		} catch (Exception e) {
			LOGGER.info("getBatteryVoltageCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	@RequestMapping("/exportHeartbeatExcel")
	public void exportHeartbeatExcel(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
			int t = deviceattributehistoryHouseidYearService.exportHeartbeatExcel(deviceattributehistoryHouseidYear, paramMap, request, response);
		} catch (Exception e) {
			LOGGER.info("exportHeartbeatExcel", e);
			//生成excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			
			workbook.setSheetName(0, "心跳统计");
			HSSFRow row = sheet.createRow((short)0);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue("主管");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue("IEEE地址");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备名称");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备IEEE");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备EP");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
			cell.setCellValue("动作ID");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
			cell.setCellValue("属性ID");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
			cell.setCellValue("属性名称");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
			cell.setCellValue("推送次数");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
			cell.setCellValue("记录次数");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
			cell.setCellValue("误差次数");
			cell.setCellStyle(titleStyle);
			row = sheet.createRow((short)(1));
			String fileName = new String("心跳统计.xls".getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			BufferedOutputStream os = null;
			os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
		}
	}
	@RequestMapping("/exportElecExcel")
	public void exportElecExcel(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
			int t = deviceattributehistoryHouseidYearService.exportElecExcel(deviceattributehistoryHouseidYear, paramMap, request, response);
		} catch (Exception e) {
			LOGGER.info("exportElecExcel", e);
			//生成excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			
			workbook.setSheetName(0, "电能相关统计");
			HSSFRow row = sheet.createRow((short)0);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue("主管");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue("IEEE地址");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备名称");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备IEEE");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备EP");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
			cell.setCellValue("动作ID");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
			cell.setCellValue("属性ID");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
			cell.setCellValue("属性名称");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
			cell.setCellValue("推送次数");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
			cell.setCellValue("记录次数");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
			cell.setCellValue("误差次数");
			cell.setCellStyle(titleStyle);
			row = sheet.createRow((short)(1));
			
			
			String fileName = new String("电能相关统计.xls".getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			BufferedOutputStream os = null;
			os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
		}
	}
	@RequestMapping("/exportBattExcel")
	public void exportBattExcel(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json,DeviceattributehistoryHouseidYear.class);
			int t = deviceattributehistoryHouseidYearService.exportBattExcel(deviceattributehistoryHouseidYear, paramMap, request, response);
		} catch (Exception e) {
			LOGGER.info("exportBattExcel", e);
			//生成excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			
			workbook.setSheetName(0, "电池电压统计");
			HSSFRow row = sheet.createRow((short)0);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue("主管");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue("IEEE地址");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备名称");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备IEEE");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue("设备EP");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
			cell.setCellValue("动作ID");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
			cell.setCellValue("属性ID");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
			cell.setCellValue("属性名称");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
			cell.setCellValue("推送次数");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
			cell.setCellValue("记录次数");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
			cell.setCellValue("误差次数");
			cell.setCellStyle(titleStyle);
row = sheet.createRow((short)(1));
			
		
			String fileName = new String("电池电压统计.xls".getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			BufferedOutputStream os = null;
			os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
		}
	}
	public long getDayStr(Map<String, Object> paramMap) throws Exception{
		long daystr = 0;
		Date t1 = new Date();
    	String beginDateStr = null,endDateStr = null;
	    Date beginDate,endDate;
	    
	    if(paramMap.get("fanwei").equals("")){
	    	
		    daystr = 1; 
		    
		}
	    if(paramMap.get("fanwei").equals("today")){
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
		    beginDateStr = str;
		    endDateStr = strcal;
		    beginDate = sdf.parse(beginDateStr);
		    endDate = sdf.parse(endDateStr);
		    daystr = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000); 
		    
		}
		if(paramMap.get("fanwei").equals("yesterday")){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    String strcal=sdf.format(calendar.getTime());
		    beginDateStr = str;
		    endDateStr = strcal;
		    beginDate = sdf.parse(beginDateStr);
		    endDate = sdf.parse(endDateStr);
		    daystr = (beginDate.getTime()-endDate.getTime())/(24*60*60*1000); 
		}
    	if(paramMap.get("fanwei").equals("nearseven")){
    		Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.DAY_OF_MONTH, -7);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
		    beginDateStr = str;
		    endDateStr = strcal;
		    beginDate = sdf.parse(beginDateStr);
		    endDate = sdf.parse(endDateStr);
		    daystr = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000)-1; 
		    
		}
    	if(paramMap.get("fanwei").equals("nearmouth")){
    		Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -1);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime()); 
		    beginDateStr = str;
		    endDateStr = strcal;
		    beginDate = sdf.parse(beginDateStr);
		    endDate = sdf.parse(endDateStr);
		    daystr = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000)-1; 
		    
		}
    	if(paramMap.get("fanwei").equals("bannian")){
    		Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -6);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime()); 
		    beginDateStr = str;
		    endDateStr = strcal;
		    beginDate = sdf.parse(beginDateStr);
		    endDate = sdf.parse(endDateStr);
		    daystr = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000)-1; 
		    
		}
    	if(paramMap.get("fanwei").equals("yinian")){
    		Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -12);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime()); 
		    beginDateStr = str;
		    endDateStr = strcal;
		    beginDate = sdf.parse(beginDateStr);
		    endDate = sdf.parse(endDateStr);
		    daystr = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000)-1; 
		    
		}
    	return daystr;
	}
	@RequestMapping("/searchServerIp")
	public void searchServerIp(String json, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> pMap = JSON.parseObject(json, Map.class);
		PrintWriter out = response.getWriter();
		List<Map> list = deviceattributehistoryHouseidYearService.findServerIp(pMap);
		String serverIp="";
		if(list.size()!=0){
			serverIp = list.get(0).get("serverIp").toString();
		}
		String callback = request.getParameter("callback");
		out.println(callback + "({\"request_id\": 1234, \"serverIp\":\"" + serverIp + "\"})"); 
	}
	
	@RequestMapping("/exportAttrLogExcel")
	public void exportAttrLogExcel(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try {
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json, DeviceattributehistoryHouseidYear.class);
			int t = deviceattributehistoryHouseidYearService.exportAttrLogExcel(deviceattributehistoryHouseidYear,request,response);
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
	/**
	* 获取用户电压值
	* @param request
	* @param response
	* @throws IOException
	* @throws ServletException
	* @author zhangmaolin
	* @Time  2016-10-24
	*/
	@RequestMapping("/getVolotageValue")
	public void getVolotageValue(String json, HttpServletResponse response, HttpServletRequest request) throws IOException {
	    PrintWriter out = response.getWriter();
	    response.setContentType("tetx/html;charset=utf-8");
	    try {
	    	Map param = JSON.parseObject(json, Map.class);
	    	if(StringUtils.isNotBlank((String)param.get("houseIEEE"))) {
	    		String resultJson = "{\"result\":0}";//成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return ;
	    	}
	    	Map Volotagelist = deviceattributehistoryHouseidYearService.getVolotageValue(param);
	    	String resultJson = JSON.toJSONStringWithDateFormat(Volotagelist, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
		    String callback = request.getParameter("callback");
			out.println(callback +"({\"request_id\": 1234, \"response_params\":{\"result\":1,\"getList\":" + resultJson + "}})");
	    } catch (Exception e) {
	    	LOGGER.info("getVolotageValue",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	    }
	 }	
	@RequestMapping("/attributeState")
	public void attributeState(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = JSON.parseObject(json, DeviceattributehistoryHouseidYear.class);
			List<Map> list = deviceattributehistoryHouseidYearService.attributeState(deviceattributehistoryHouseidYear);
			String resultJson = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			String callback = request.getParameter("callback");
			out.println(callback +"({\"request_id\": 1234, \"response_params\":{\"result\":1,\"getList\":" + resultJson + "}})");
		} catch (Exception e) {
			LOGGER.info("attributeState",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	 }	    
}
