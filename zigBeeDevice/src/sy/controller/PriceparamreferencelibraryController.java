package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Energyreferencelibrary;
import sy.model.Priceparamreferencelibrary;
import sy.service.EnergyreferencelibraryServiceI;
import sy.service.PriceparamreferencelibraryServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/priceparamreferencelibraryController")
public class PriceparamreferencelibraryController {

	private PriceparamreferencelibraryServiceI priceparamreferencelibraryService;	
	private EnergyreferencelibraryServiceI energyreferencelibraryService;
	
	private static final Logger LOGGER = Logger.getLogger(PriceparamreferencelibraryController.class);  	

	public EnergyreferencelibraryServiceI getEnergyreferencelibraryService() {
		return energyreferencelibraryService;
	}

	@Autowired
	public void setEnergyreferencelibraryService(EnergyreferencelibraryServiceI energyreferencelibraryService) {
		this.energyreferencelibraryService = energyreferencelibraryService;
	}

	public PriceparamreferencelibraryServiceI getPriceparamreferencelibraryService() {
		return priceparamreferencelibraryService;
	}

	@Autowired
	public void setPriceparamreferencelibraryService(PriceparamreferencelibraryServiceI priceparamreferencelibraryService) {
		this.priceparamreferencelibraryService = priceparamreferencelibraryService;
	}

	@RequestMapping("/update")
	public void savePriceparamreferencelibrary(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
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
		
		/*List<Priceparamreferencelibrary> priceparamreferencelibraryList = JSON.parseArray(json,Priceparamreferencelibrary.class); 
        for (Priceparamreferencelibrary priceparamreferencelibrary : priceparamreferencelibraryList) { 
        	priceparamreferencelibraryService.save(priceparamreferencelibrary);
        } */
		
        try {
         //String language= request.getParameter("language");
     	  Map map=JSON.parseObject(json, Map.class);
        	//Energyreferencelibrary energyreferencelibrary = JSON.parseObject(json,Energyreferencelibrary.class); 
        	// 获取电价参考库主表数据
        	Energyreferencelibrary t = energyreferencelibraryService.getById((long) map.get("id"));
        	t.setHouseIeee((String) map.get("houseIeee"));
        	// 删除电价相关表数据
        	energyreferencelibraryService.delete(map);
        	// 从电价参考库表复制，插入电价相关表数据
        	energyreferencelibraryService.saveSql(t,(String) map.get("language"));
        	
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("savePriceparamreferencelibrary", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "savePriceparamreferencelibrary";
	}
	
	@RequestMapping("/updateLib")
	public void saveLib(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
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
		
		/*List<Priceparamreferencelibrary> priceparamreferencelibraryList = JSON.parseArray(json,Priceparamreferencelibrary.class); 
        for (Priceparamreferencelibrary priceparamreferencelibrary : priceparamreferencelibraryList) { 
        	priceparamreferencelibraryService.save(priceparamreferencelibrary);
        } */
		
        try {
        	Energyreferencelibrary energyreferencelibrary = JSON.parseObject(json,Energyreferencelibrary.class); 
        	// 从电价参数表更新到电价库的接口
        	energyreferencelibraryService.saveSqlLib(energyreferencelibrary);
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveLib", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "savePriceparamreferencelibrary";
	}
	
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
	  //String type=request.getParameter("language");
        try {
        	Map map=JSON.parseObject(json, Map.class);
        	//Energyreferencelibrary energyreferencelibrary = JSON.parseObject(json,Energyreferencelibrary.class); 
        	// 获取电价库列表
        	Map t = energyreferencelibraryService.findListFieldTimeLib(map);
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
        	Priceparamreferencelibrary priceparamreferencelibrary = JSON.parseObject(json,Priceparamreferencelibrary.class); 
        	priceparamreferencelibraryService.delete(priceparamreferencelibrary);
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}        
	}
	
}
