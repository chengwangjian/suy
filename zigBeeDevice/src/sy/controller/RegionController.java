package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Region;
import sy.service.RegionServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/regionController")
public class RegionController {

	private RegionServiceI regionService;
	
	private static final Logger LOGGER = Logger.getLogger(RegionController.class);  

	public RegionServiceI getRegionService() {
		return regionService;
	}

	@Autowired
	public void setRegionService(RegionServiceI regionService) {
		this.regionService = regionService;
	}

	@RequestMapping("/addOrUpdate")
	public void saveRegion(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
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
		
		/*List<Region> regionList = JSON.parseArray(json,Region.class); 
        for (Region region : regionList) { 
        	regionService.save(region);
        } */
		
        try {
        	Region region = JSON.parseObject(json,Region.class); 
        	Region t = regionService.get(region);
        	if (t == null) {
        		if (regionService.getExisit(region) == null) {
        			regionService.save(region);
        		}
        	} else {
        		/*if (region.getDeviceType() != null) {
        			t.setDeviceType(region.getDeviceType());
        		}*/
        		regionService.update(t);
        	}
        	
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveRegion", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveRegion";
	}
	
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		
        try {
        	Map map=JSON.parseObject(json, Map.class);
        	//String language=request.getParameter("language");
            String language=(String) map.get("language");
//        	Region region = JSON.parseObject(json,Region.class); 
        	Region region = new Region();
        	List<Map> t = regionService.findList(region,language);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}        
	}
	
	@RequestMapping("/delete")
	public void delete(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Region region = JSON.parseObject(json,Region.class); 
        	regionService.delete(region);
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}        
	}
	
}
