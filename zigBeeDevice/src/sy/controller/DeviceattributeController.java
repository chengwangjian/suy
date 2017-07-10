package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Deviceattribute;
import sy.service.DeviceattributeServiceI;
import sy.service.DeviceattributehistoryHouseidYearServiceI;
import sy.service.HouseServiceI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/deviceattributeController")
public class DeviceattributeController {

	private DeviceattributeServiceI deviceattributeService;
	private DeviceattributehistoryHouseidYearServiceI deviceattributehistoryHouseidYearService;
	private HouseServiceI houseService;
	private static final Logger LOGGER = Logger.getLogger(DeviceattributeController.class);  
	
	public HouseServiceI getHouseService() {
		return houseService;
	}

	@Autowired
	public void setHouseService(HouseServiceI houseService) {
		this.houseService = houseService;
	}

	public DeviceattributeServiceI getDeviceattributeService() {
		return deviceattributeService;
	}

	@Autowired
	public void setDeviceattributeService(DeviceattributeServiceI deviceattributeService) {
		this.deviceattributeService = deviceattributeService;
	}

	public DeviceattributehistoryHouseidYearServiceI getDeviceattributehistoryHouseidYearService() {
		return deviceattributehistoryHouseidYearService;
	}

	@Autowired
	public void setDeviceattributehistoryHouseidYearService(
			DeviceattributehistoryHouseidYearServiceI deviceattributehistoryHouseidYearService) {
		this.deviceattributehistoryHouseidYearService = deviceattributehistoryHouseidYearService;
	}
	/**
	 * 单条推送属性消息
	 * @param json 属性消息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/addOrUpdate")
	public void saveDeviceattribute(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  

        try {
        	Deviceattribute s = JSON.parseObject(json,Deviceattribute.class);
        	if (StringUtils.isBlank(s.getHouseIeee()) || s.getHouseIeee().length() < 16 ) {
    			String resultJson = "{\"result\":0}";//成功1 失败0
        		String callback = request.getParameter("callback");
        		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
    		}
    	    deviceattributeService.saveDeviceattribute(s,null);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveDeviceattribute",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}
	}
	/**
	 * 批量添加属性
	 * @param json 批量属性消息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/addAttrBatch")
	public void addAttrBatch(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		String resultJson = null;
        try {
        	List<Deviceattribute> s = JSONObject.parseArray(json, Deviceattribute.class);   		
    	    int result = deviceattributeService.saveDeviceattribute(null,s);
    	    String callback = request.getParameter("callback");
    	    if(result>=0){
        		resultJson = "{\"result\":1}";//成功1 失败0
    	    }else{
    	    	resultJson = "{\"result\":0}";//成功1 失败0
    	    }
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveDeviceattribute",e);
			resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}
	}
	
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		
        try {
        	Deviceattribute deviceattribute = JSON.parseObject(json,Deviceattribute.class); 
        	List<Deviceattribute> t = deviceattributeService.findList(deviceattribute);        	
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	@RequestMapping("/delete")
	public void delete(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Deviceattribute deviceattribute = JSON.parseObject(json,Deviceattribute.class); 
        	deviceattributeService.delete(deviceattribute);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	@RequestMapping("/flush")
	public void flush(String json,HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8"); 
		PrintWriter out= null;
        try {
        	out=response.getWriter();
        	deviceattributeService.saveAttrCache();        	
        	String resultJson = "{\"result\":1}";
        	out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
		}        
	}
}
