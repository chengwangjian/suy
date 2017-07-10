package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
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

import sy.model.Proxyserver;
import sy.service.HouseieeeServiceI;
import sy.service.ProxyserverServiceI;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/proxyserverController")
public class ProxyserverController {

	private ProxyserverServiceI proxyserverService;
	
	private HouseieeeServiceI houseieeeService;
	
	private static final Logger LOGGER = Logger.getLogger(ProxyserverController.class);  

	public HouseieeeServiceI getHouseieeeService() {
		return houseieeeService;
	}

	@Autowired
	public void setHouseieeeService(HouseieeeServiceI houseieeeService) {
		this.houseieeeService = houseieeeService;
	}
	
	public ProxyserverServiceI getProxyserverService() {
		return proxyserverService;
	}

	@Autowired
	public void setProxyserverService(ProxyserverServiceI proxyserverService) {
		this.proxyserverService = proxyserverService;
	}

	@RequestMapping("/add")
	public void saveProxyserver(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*proxyserverService.test();
		return "showProxyserver";*/
		
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
		
		/*List<Proxyserver> proxyserverList = JSON.parseArray(json,Proxyserver.class); 
        for (Proxyserver proxyserver : proxyserverList) { 
        	proxyserverService.save(proxyserver);
        } */
		
        try {
        	Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class);
        	Proxyserver t = proxyserverService.get(proxyserver);
        	if (StringUtils.isBlank(proxyserver.getHouseIeee()) || proxyserver.getHouseIeee().length() < 16 ) {
    			String resultJson = "{\"result\":0}";//成功1 失败0
        		    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
    		}
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t == null) {
        		/*if (StringUtils.isBlank(proxyserver.getHouseIeee()) || proxyserver.getHouseIeee().length() != 16) {
            		return;
            	}*/
        		if (proxyserver.getType() == null) {
        			proxyserver.setType("0");
        		}
        		if (proxyserver.getSecondType() == null) {
        			proxyserver.setSecondType("0");
        		}
        		proxyserverService.save(proxyserver);
        		resultJson = "{\"result\":1}";//成功1 失败0
        		/*proxyserverService.addDeviceAttributeHistoryTable(proxyserver.getHouseIeee());
        		proxyserverService.addDeviceOperateHistoryTable(proxyserver.getHouseIeee());
        		proxyserverService.addDeviceWarnHistoryTable(proxyserver.getHouseIeee());*/
        	} else {
        		/*proxyserver.setId(t.getId());
            	proxyserverService.update(proxyserver);*/
        		/*String resultJson = "{\"result\":0}";//成功1 失败0
    			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
    			return;*/
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveProxyserver",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveProxyserver";
	}
	
	@RequestMapping("/update")
	public void update(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class); 
        	Proxyserver t = proxyserverService.get(proxyserver);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t != null) {
        		/*proxyserver.setId(t.getId());
            	proxyserverService.update(proxyserver);*/
        		/*if (proxyserver.getLatitude() != null) {
        			t.setLatitude(proxyserver.getLatitude());
        		}
        		if (proxyserver.getLongitude() != null) {
        			t.setLongitude(proxyserver.getLongitude());
        		}*/
        		if (proxyserver.getName() != null) {
        			t.setName(proxyserver.getName());
        		}
        		if (proxyserver.getServerIp() != null) {
        			t.setServerIp(proxyserver.getServerIp());
        		}
        		if (proxyserver.getServerPort() != null) {
        			t.setServerPort(proxyserver.getServerPort());
        		}
        		/*if (proxyserver.getNetworkAddress() != null) {
        			t.setNetworkAddress(proxyserver.getNetworkAddress());
        		}
        		if (proxyserver.getPort() != null) {
        			t.setPort(proxyserver.getPort());
        		}*/
        		if (proxyserver.getDescription() != null) {
        			t.setDescription(proxyserver.getDescription());
        		}
        		t.setLasttime(new Date());
            	proxyserverService.update(t);
            	resultJson = "{\"result\":1}";//成功1 失败0
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 获取所有家的云端地址列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findAllCloudIp")
	public void findAllCloudIp(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
        try {
        	Map<String,Object> houseieeeProxyserverMap = new HashMap();
        	Proxyserver proxyserver = new Proxyserver();
    		proxyserver.setEnableFlag("1");
    		proxyserver.setType("1");// 查找云端服务器的ip
        	List<Proxyserver> t2 = proxyserverService.findList(proxyserver);
        	if (t2 !=null && t2.size() > 0) {
    			for (Proxyserver m:t2) {
    				houseieeeProxyserverMap.put(m.getHouseIeee(), m.getServerIp());
    			}
    		}
        	String resultJson= JSON.toJSONString(houseieeeProxyserverMap);
			out.println(resultJson); 
			LOGGER.info("proxyserver find log==" + resultJson);
		} catch (Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(resultJson);
		}   
	}
	
	/**
	 * 根据houseIEEE获取家云端地址
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findCloudAddrByIeee")
	public void findCloudAddrByIeee(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
        try {
        	Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class); 
        	Map<String,Object> houseieeeProxyserverMap = new HashMap();
    		proxyserver.setEnableFlag("1");
    		proxyserver.setType("1");// 查找云端服务器的ip
        	List<Proxyserver> t2 = proxyserverService.findList(proxyserver);
        	if (t2 !=null && t2.size() == 1) {
        		Proxyserver m = t2.get(0);
    			out.print(m.getServerIp());
    		}else{
    			out.print("{\"result\":-1}");
    		}
        	
		} catch (Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.print(resultJson);
		}   
	}
	
	/**
	 * 通过代理服务器获取指定houseIEEE的云端服务器地址的接口
http://192.168.1.72:8081/zigBeeDevice/proxyserverController/find.do?json={"houseIEEE":"00137A0000007795"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000007795
	 * @author: zhuangxd
	 * 时间：2014-7-4 下午3:57:58
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		/*HouseieeeListener s = new HouseieeeListener();
		s.initHouseieee();*/
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class); 
//        	Proxyserver t = proxyserverService.find(proxyserver);
        	List<Proxyserver> t = proxyserverService.findList(proxyserver);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
			LOGGER.info("proxyserver find log==" + resultJson);
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class); 
//        	Proxyserver t = proxyserverService.find(proxyserver);
        	List<Proxyserver> t = proxyserverService.findList(proxyserver);
        	List<Object[]> s = proxyserverService.findListSql(proxyserver);
        	for (Object[] objects : s) {
        		Proxyserver proxyserver2 = (Proxyserver)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Proxyserver proxyserver2 = (Proxyserver)objects[0];
        		DevicewarnhistoryProxyserveridYear devicewarnhistoryProxyserveridYear = (DevicewarnhistoryProxyserveridYear)objects[1];
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
        	Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class); 
        	proxyserverService.delete(proxyserver);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	@RequestMapping("/spaceWarn")
	public void spaceWarn(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		PrintWriter out=response.getWriter();
		//String callback = request.getParameter("callback");
		try{
			float status = proxyserverService.spaceWarn();
			float totle = proxyserverService.serverMemoryWarn();
			out.println("{\"request_id\": 1234, \"result\":" + status + ",\"totle\":"+totle+"}");
		}catch(Exception e){
			out.println("{\"request_id\": 1234, \"result\":-1,\"totle\":-1}");
		}
	}

}
