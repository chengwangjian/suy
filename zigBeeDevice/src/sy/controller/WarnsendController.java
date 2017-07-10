package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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

import sy.model.Warnsend;
import sy.service.WarnsendServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/warnsendController")
public class WarnsendController {

	private WarnsendServiceI warnsendService;
	
	private static final Logger LOGGER = Logger.getLogger(WarnsendController.class);  

	public WarnsendServiceI getWarnsendService() {
		return warnsendService;
	}

	@Autowired
	public void setWarnsendService(WarnsendServiceI warnsendService) {
		this.warnsendService = warnsendService;
	}

	@RequestMapping("/add")
	public void saveWarnsend(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*warnsendService.test();
		return "showWarnsend";*/
		
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
		
		/*List<Warnsend> warnsendList = JSON.parseArray(json,Warnsend.class); 
        for (Warnsend warnsend : warnsendList) { 
        	warnsendService.save(warnsend);
        } */
		
        try {
        	Warnsend warnsend = JSON.parseObject(json,Warnsend.class);
        	warnsend.setLastTime(new Date());
        	Warnsend t = warnsendService.get(warnsend);
        	if (StringUtils.isBlank(warnsend.getHouseIeee()) || warnsend.getHouseIeee().length() < 16 ) {
    			String resultJson = "{\"result\":0}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
    		}
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t == null) {
        		/*if (StringUtils.isBlank(warnsend.getWarnsendIeee()) || warnsend.getWarnsendIeee().length() != 16) {
            		return;
            	}*/
        		//warnsend=warnsendService.find(warnsend);
//        		if (warnsendService.find(warnsend)==null) {
//        			warnsendService.save(warnsend);	
//				}
        		warnsendService.save(warnsend);	
        		resultJson = "{\"result\":1}";//成功1 失败0
        		/*warnsendService.addDeviceAttributeHistoryTable(warnsend.getWarnsendIeee());
        		warnsendService.addDeviceOperateHistoryTable(warnsend.getWarnsendIeee());
        		warnsendService.addDeviceWarnHistoryTable(warnsend.getWarnsendIeee());*/
        	} else {
        		warnsend.setId(t.getId());
//        		t.setIsonline("1");
            	warnsendService.update(warnsend);
        	    resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
    			return;
        	}
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveWarnsend", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveWarnsend";
	}
	
	@RequestMapping("/update")
	public void update(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Warnsend warnsend = JSON.parseObject(json,Warnsend.class); 
        	Warnsend t = warnsendService.get(warnsend);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t != null) {
        		/*warnsend.setId(t.getId());
            	warnsendService.update(warnsend);*/
        		/*if (warnsend.getLatitude() != null) {
        			t.setLatitude(warnsend.getLatitude());
        		}
        		if (warnsend.getLongitude() != null) {
        			t.setLongitude(warnsend.getLongitude());
        		}
        		if (warnsend.getName() != null) {
        			t.setName(warnsend.getName());
        		}
        		if (warnsend.getNetworkAddress() != null) {
        			t.setNetworkAddress(warnsend.getNetworkAddress());
        		}
        		if (warnsend.getPort() != null) {
        			t.setPort(warnsend.getPort());
        		}
        		if (warnsend.getDescription() != null) {
        			t.setDescription(warnsend.getDescription());
        		}
        		t.setLasttime(new Date());*/
            	warnsendService.update(t);
            	resultJson = "{\"result\":1}";//成功1 失败0
        	}
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}        
	}
	
	@RequestMapping("/update2")
	public void update2(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Map<String,Object> paraMap = JSON.parseObject(json,Map.class); 
        	//Warnsend t = warnsendService.get(warnsend);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (paraMap!= null) {
        		/*warnsend.setId(t.getId());
            	warnsendService.update(warnsend);*/
        		/*if (warnsend.getLatitude() != null) {
        			t.setLatitude(warnsend.getLatitude());
        		}
        		if (warnsend.getLongitude() != null) {
        			t.setLongitude(warnsend.getLongitude());
        		}
        		if (warnsend.getName() != null) {
        			t.setName(warnsend.getName());
        		}
        		if (warnsend.getNetworkAddress() != null) {
        			t.setNetworkAddress(warnsend.getNetworkAddress());
        		}
        		if (warnsend.getPort() != null) {
        			t.setPort(warnsend.getPort());
        		}
        		if (warnsend.getDescription() != null) {
        			t.setDescription(warnsend.getDescription());
        		}
        		t.setLasttime(new Date());*/
        		resultJson=warnsendService.update2(paraMap)>0?"1":"0";
        	}
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}        
	}
	
	
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Warnsend warnsend = JSON.parseObject(json,Warnsend.class); 
//        	Warnsend t = warnsendService.find(warnsend);
        	List<Warnsend> t = warnsendService.findList(warnsend);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		} catch (Exception e) {
			LOGGER.info("find", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}   
        
        // 多表关联
      /*  try {
        	Warnsend warnsend = JSON.parseObject(json,Warnsend.class); 
//        	Warnsend t = warnsendService.find(warnsend);
        	List<Warnsend> t = warnsendService.findList(warnsend);
        	List<Object[]> s = warnsendService.findListSql(warnsend);
        	for (Object[] objects : s) {
        		Warnsend warnsend2 = (Warnsend)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Warnsend warnsend2 = (Warnsend)objects[0];
        		DevicewarnhistoryWarnsendidYear devicewarnhistoryWarnsendidYear = (DevicewarnhistoryWarnsendidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}
	
	@RequestMapping("/delete")
	public void delete(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Warnsend warnsend = JSON.parseObject(json,Warnsend.class); 
        	warnsendService.delete(warnsend);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    		String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		} catch (Exception e) {
			LOGGER.info("delete", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

}
