package zbHouse.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import sy.util.HouseieeeListener;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;
import zbHouse.model.Device;
import zbHouse.pageModel.Json;
import zbHouse.service.DeviceServiceI;
import zbHouse.util.TestLog4j;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/deviceController")
public class DeviceController {

	private static final Logger LOGGER = Logger.getLogger(DeviceController.class);
	private DeviceServiceI deviceService;
	
	private Httpproxy httpproxy;
	public DeviceServiceI getDeviceService() {
		return deviceService;
	}

	@Autowired
	public void setDeviceService(DeviceServiceI deviceService) {
		 this.deviceService =deviceService;
	}

	public Httpproxy getHttpproxy() {
		return httpproxy;
	}

	@Autowired
	public void setHttpproxy(Httpproxy httpproxy) {
		this.httpproxy = httpproxy;
	}

	/**
	 * 增加或修改设备信息
	 * @author: zhuangxd
	 * 时间：2014-5-21 下午4:18:25
	 * @param json
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void showSave(String json,HttpServletRequest request,HttpServletResponse response) {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
		
//		httpproxy.urlEncrypt("5717188", "00137A0000012E2B", null);		
		Json j = new Json();
		try {
			Device js=JSON.parseObject(json, Device.class);
			
			deviceService.saveOrUpdate(js,params);
			params.put("result", 1);
			j.setResponse_params(params);
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showSave",e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,response);			
		}
		//TestLog4j.testInfo(json);
		//request.setAttribute("j",j);
			
	}
	
	/**
	 * 修改设备的在线状态
	 * @author: zhuangxd
	 * 时间：2014-5-26 下午2:07:22
	 * @param json
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateIsonline")
	public void updateIsonline(String json,HttpServletRequest request,HttpServletResponse response) {
//		request.setAttribute("js",js);
//		http://192.168.1.123:8080/zigBeeDevice/deviceController/updateIsonline.do?json={"houseIeee":"00137A000000DBB5","ieee":"00137A000000602B","isonline":"1"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000007785
		/*设备状态改变的时候，请添加一个动作，在设备属性历史记录表同步添加一条特殊属性的信息，该属性的ClusterID为0000，AttrID为FFFF，保存当前推送上来的设备是否在线信息。
	    	该动作仅仅在设备是否在线状态变化的时候执行，如下两种情况
	        1、设备状态从“在线”变化为“不在线”。
	        2、设备状态从“不在线”变化为“在线”。*/
		Map<String, Object> params = new HashMap<String, Object>();
		Json j = new Json();
		try {
			Device js = JSON.parseObject(json, Device.class);
			int result = deviceService.modeifyDevAndAttrIline(js);
			params.put("result", result);
			j.setResponse_params(params);
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("updateIsonline",e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,response);			
		}
	}
	
	@RequestMapping("/delete")
	public void showDelete(String json,HttpServletRequest request,HttpServletResponse response) {	

		//request.setAttribute("js",js);		
		Map<String, Object> params = new HashMap<String, Object>();
       
		Json j = new Json();
		try {
			params = (Map<String,Object>)JSON.parse(json); 
			deviceService.delete(params);
			params.put("result", 1);
			j.setResponse_params(params);
			writeJson(j,response);
		} catch (Exception e) {
			LOGGER.info("showDelete",e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,response);	
		}
		TestLog4j.testInfo(json);
		//request.setAttribute("j",j);
		
	}

	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request,HttpServletResponse response) {
		
		// 设备查找
//		 http://192.168.1.123:8080/zigBeeDevice/deviceController/find.do?json={"houseIeee":"00137A000001013B","deviceName":"%36%36%46%46","optype":"-99"}&callback=jQuery182049742922194443_1402032536609&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000007785&_=1402032572656
		
		//request.setAttribute("js",js);

		Map<String, Object> params = new HashMap<String, Object>();
	       
		Json j = new Json();
		try {
			params = (Map<String,Object>)JSON.parse(json);
			j.setResponse_params(deviceService.find(params));
			TestLog4j.writeJson2(j,response,request);						
		} catch (Exception e) {
			LOGGER.info("find",e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,response);

		}
		//request.setAttribute("j",j);			
			
	}
	
	
	@RequestMapping("/findonline")
	public void findonline(String json,HttpServletRequest request,HttpServletResponse response) {
		
		// 设备查找
//		 http://192.168.1.123:8080/zigBeeDevice/deviceController/find.do?json={"houseIeee":"00137A000001013B","deviceName":"%36%36%46%46","optype":"-99"}&callback=jQuery182049742922194443_1402032536609&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000007785&_=1402032572656
		
		//request.setAttribute("js",js);

		Map<String, Object> params = new HashMap<String, Object>();
		Json j = new Json();
		try {
			params = (Map<String,Object>)JSON.parse(json);
			j.setResponse_params(deviceService.findonline(params));
			TestLog4j.writeJson2(j,response,request);	
			LOGGER.info("得到的列表为===》：" + j);
		} catch (Exception e) {
			LOGGER.info("findonline",e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,response);

		}
		//request.setAttribute("j",j);			
			
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
        	//刷新缓存数据到数据库
        	deviceService.saveCacheData();
    		Map<String, Object> params = new HashMap<String, Object>();
        	params = (Map<String,Object>)JSON.parse(json);
        	Map t = deviceService.getListoryCount(params);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("getListoryCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	public void writeJson(Object object,HttpServletResponse response) {

		try {
			String jstr= JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson",e);
		}
	}
	@RequestMapping("/execlfindline")
	public void execlfindline(String json,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		// 设备查找
//		 http://192.168.1.123:8080/zigBeeDevice/deviceController/find.do?json={"houseIeee":"00137A000001013B","deviceName":"%36%36%46%46","optype":"-99"}&callback=jQuery182049742922194443_1402032536609&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000007785&_=1402032572656
		
		//request.setAttribute("js",js);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params = (Map<String,Object>)JSON.parse(json);
		Map<String, Object> params = JSON.parseObject(json,Map.class);	
		
		
		BufferedOutputStream os = null;
		try {
			deviceService.execlfindline(request,response,params);
		} catch (Exception e) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			
			workbook.setSheetName(0, "Houseiee_所有设备列表");
			HSSFRow row = sheet.createRow((short)0);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue("modeid");
			cell.setCellStyle(titleStyle);
			
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue("deviceName");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("ep");
			cell.setCellStyle(titleStyle);
//			cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue("isonline");
//			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue("ieee");
			cell.setCellStyle(titleStyle);
//			cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue("isonline2");
//			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue("datecode");
			cell.setCellStyle(titleStyle);
//			cell = row.createCell((short)8, HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue("nodeType");
//			cell.setCellStyle(titleStyle);
//			cell = row.createCell((short)9, HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue("unit_type");
//			cell.setCellStyle(titleStyle);
//			cell = row.createCell((short)10, HSSFCell.CELL_TYPE_STRING);
//			cell.setCellValue("deviceattribute");
//			cell.setCellStyle(titleStyle);

			row = sheet.createRow((short)(1));
			String fileName = new String("Houseiee_所有设备列表.xls".getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
			throw e;
		}
		//request.setAttribute("j",j);			
			
	}
	
	
	@RequestMapping("/SendStandRequest")
	public void SendStandRequest(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		String callback = request.getParameter("callback");
        try {
        	//刷新缓存数据到数据库
    		Map<String, Object> params = JSON.parseObject(json, Map.class);
    		String houseIeee = ((String) params.get("houseIeee")).toUpperCase();
    		String userName = houseIeee + "|" + params.get("Data");
    		Map<String, Object> paramMap = new HashMap<>();
    		String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIeee);
			//登录到203服务器
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
        	StringBuilder loginUrl = new StringBuilder("http://");
        	loginUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/pollc");
        	paramMap.put("context", "redirectToCSHC");
        	paramMap.put("username", userName);
        	//加密
        	String content="context=redirectToCSHC";
        	String key = houseIeee.substring(6,16) + "NETVOX";
        	String encryptResult = Httpproxy.urlEncrypt(content, key);
        	//加密结束
        	paramMap.put("sign", encryptResult);
        	paramMap.put("encodemethod", "AES");
        	paramMap.put("houseIeeeSecret", houseIeee);
        	TestHttpclient.postUrlWithParams(loginUrl.toString(), paramMap, "utf-8");
        	String resultJson = "{\"result\":1}";//成功1 失败0
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("SendStandRequest",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 模拟haier云服网接口
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/StandMessageReturn")
	public void StandMessageReturn(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		String callback = request.getParameter("callback");
        try {
        	//刷新缓存数据到数据库
        	String resultJson = "{\"result\":1}";//成功1 失败0
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("StandMessageReturn",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	@RequestMapping("/exportDeviceExcel")
	public void exportDeviceExcel(String json,HttpServletRequest request,HttpServletResponse response)throws Exception{
		LOGGER.info("json="+ json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try{
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int t = deviceService.exportDeviceExcel(paramMap,request, response);
		}catch(Exception e) {
			LOGGER.info("exportDeviceExcel", e);
			try {
				out = response.getWriter();
			} catch(Exception ex) {
				LOGGER.info("exportDeviceExcel getWriter", ex);
				resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
			}
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback +"{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getfindlist")
	public void getfindlist(String json, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;chartset=utf-8");
		try {
			Map params = JSON.parseObject(json, Map.class);
			List<Map> list = this.deviceService.getfindlist(params);
			LOGGER.info("得到的list值是------》："+ list);
			String resultJson= JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			LOGGER.info("getfindlist", e);
			String callback = request.getParameter("callback");
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
		
	}
}
