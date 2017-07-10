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

import sy.model.Energyreferencelibrary;
import sy.service.EnergyreferencelibraryServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/energyreferencelibraryController")
public class EnergyreferencelibraryController {

	private EnergyreferencelibraryServiceI energyreferencelibraryService;
	
	private static final Logger LOGGER = Logger.getLogger(EnergyreferencelibraryController.class);  

	public EnergyreferencelibraryServiceI getEnergyreferencelibraryService() {
		return energyreferencelibraryService;
	}

	@Autowired
	public void setEnergyreferencelibraryService(EnergyreferencelibraryServiceI energyreferencelibraryService) {
		this.energyreferencelibraryService = energyreferencelibraryService;
	}

	@RequestMapping("/addOrUpdate")
	public void saveEnergyreferencelibrary(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
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
		
		/*List<Energyreferencelibrary> energyreferencelibraryList = JSON.parseArray(json,Energyreferencelibrary.class); 
        for (Energyreferencelibrary energyreferencelibrary : energyreferencelibraryList) { 
        	energyreferencelibraryService.save(energyreferencelibrary);
        } */
		
        try {
        	Energyreferencelibrary energyreferencelibrary = JSON.parseObject(json,Energyreferencelibrary.class); 
        	Energyreferencelibrary t = energyreferencelibraryService.get(energyreferencelibrary);
        	if (t == null) {
        		if (energyreferencelibraryService.getExisit(energyreferencelibrary) == null) {
        			energyreferencelibraryService.save(energyreferencelibrary);
        		}
        	} else {
        		/*if (energyreferencelibrary.getDeviceType() != null) {
        			t.setDeviceType(energyreferencelibrary.getDeviceType());
        		}*/
        		energyreferencelibraryService.update(t);
        	}
        	
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveEnergyreferencelibrary",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveEnergyreferencelibrary";
	}
	
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		//String type=request.getParameter("language");
        try {
        	//Energyreferencelibrary energyreferencelibrary = JSON.parseObject(json,Energyreferencelibrary.class); 
        	Map map=JSON.parseObject(json, Map.class);
        	List<Map> t = energyreferencelibraryService.findList(map);
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
        	//Energyreferencelibrary energyreferencelibrary = JSON.parseObject(json,Energyreferencelibrary.class); 
        	Map map=JSON.parseObject(json, Map.class);
        	energyreferencelibraryService.delete(map);
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}        
	}
	
}
