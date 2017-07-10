package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Houseieee;
import sy.service.HouseieeeServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/houseieeeController")
public class HouseieeeController {

	private HouseieeeServiceI houseieeeService;
	
	private static final Logger LOGGER = Logger.getLogger(HouseieeeController.class);  

	public HouseieeeServiceI getHouseieeeService() {
		return houseieeeService;
	}

	@Autowired
	public void setHouseieeeService(HouseieeeServiceI houseieeeService) {
		this.houseieeeService = houseieeeService;
	}

	@RequestMapping("/add")
	public void saveHouseieee(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*houseieeeService.test();
		return "showHouseieee";*/
		
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
		
		/*List<Houseieee> houseieeeList = JSON.parseArray(json,Houseieee.class); 
        for (Houseieee houseieee : houseieeeList) { 
        	houseieeeService.save(houseieee);
        } */
		
        try {
        	Houseieee houseieee = JSON.parseObject(json,Houseieee.class);
        	Houseieee t = houseieeeService.get(houseieee);
        	if (StringUtils.isBlank(houseieee.getHouseIeee()) || houseieee.getHouseIeee().length() < 16 ) {
    			String resultJson = "{\"result\":0}";//成功1 失败0
        		    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
    		}
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t == null) {
        		/*if (StringUtils.isBlank(houseieee.getHouseIeee()) || houseieee.getHouseIeee().length() != 16) {
            		return;
            	}*/
        		if (StringUtils.isBlank(houseieee.getSecretKey())) {
        			houseieee.setSecretKey(houseieee.getHouseIeee().substring(6));
        		}
        		if (houseieee.getVendorCode() == null) {
        			houseieee.setVendorCode("NETVOX");
        		}
        		if (houseieee.getEncodemethod() == null) {
        			houseieee.setEncodemethod("0");
        		}
        		houseieeeService.save(houseieee);
        		resultJson = "{\"result\":1}";//成功1 失败0
        		/*houseieeeService.addDeviceAttributeHistoryTable(houseieee.getHouseIeee());
        		houseieeeService.addDeviceOperateHistoryTable(houseieee.getHouseIeee());
        		houseieeeService.addDeviceWarnHistoryTable(houseieee.getHouseIeee());*/
        	} else {
        		/*houseieee.setId(t.getId());
            	houseieeeService.update(houseieee);*/
        		/*String resultJson = "{\"result\":0}";//成功1 失败0
    			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
    			return;*/
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveHouseieee",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveHouseieee";
	}
	
	/**
	 * url接口是否采用加密方式的设置的接口
	http://192.168.1.72:8081/zigBeeDevice/houseieeeController/update.do?json={"houseIEEE":"00137A0000006785","encodemethod":"0"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000006785
	解密密钥修改接口
	http://192.168.1.72:8081/zigBeeDevice/houseieeeController/update.do?json={"houseIEEE":"00137A0000006785","secretKey":"0000006785"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000006785
	 * @author: zhuangxd
	 * 时间：2014-5-29 上午10:56:24
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Houseieee houseieee = JSON.parseObject(json,Houseieee.class); 
        	Houseieee t = houseieeeService.get(houseieee);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t != null) {
        		/*houseieee.setId(t.getId());
            	houseieeeService.update(houseieee);*/
        		/*if (houseieee.getLatitude() != null) {
        			t.setLatitude(houseieee.getLatitude());
        		}
        		if (houseieee.getLongitude() != null) {
        			t.setLongitude(houseieee.getLongitude());
        		}*/
        		if (houseieee.getName() != null) {
        			t.setName(houseieee.getName());
        		}
        		if (houseieee.getSecretKey() != null) {
        			t.setSecretKey(houseieee.getSecretKey());
        		}
        		if (houseieee.getVendorCode() != null) {
        			t.setVendorCode(houseieee.getVendorCode());
        		}
        		if (houseieee.getVendorName() != null) {
        			t.setVendorName(houseieee.getVendorName());
        		}
        		// 取消更改是否加密
        		/*if (houseieee.getEncodemethod() != null) {
        			t.setEncodemethod(houseieee.getEncodemethod());
        		}*/
        		if (houseieee.getXmppIp() != null) {
        			t.setXmppIp(houseieee.getXmppIp());
        		}
        		if (houseieee.getXmppPort() != null) {
        			t.setXmppPort(houseieee.getXmppPort());
        		}
        		if (houseieee.getCloudIp1() != null) {
        			t.setCloudIp1(houseieee.getCloudIp1());
        		}
        		if (houseieee.getCloudPort1() != null) {
        			t.setCloudPort1(houseieee.getCloudPort1());
        		}
        		if (houseieee.getCloudIp2() != null) {
        			t.setCloudIp2(houseieee.getCloudIp2());
        		}
        		if (houseieee.getCloudPort2() != null) {
        			t.setCloudPort2(houseieee.getCloudPort2());
        		}
        		if (houseieee.getEnableFlag() != null) {
        			t.setEnableFlag(houseieee.getEnableFlag());
        		}
        		/*if (houseieee.getNetworkAddress() != null) {
        			t.setNetworkAddress(houseieee.getNetworkAddress());
        		}
        		if (houseieee.getPort() != null) {
        			t.setPort(houseieee.getPort());
        		}*/
        		if (houseieee.getDescription() != null) {
        			t.setDescription(houseieee.getDescription());
        		}
        		t.setLasttime(new Date());
            	houseieeeService.update(t);
            	resultJson = "{\"result\":1}";//成功1 失败0
        	}
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update",e);
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
        	Houseieee houseieee = JSON.parseObject(json,Houseieee.class); 
//        	Houseieee t = houseieeeService.find(houseieee);
        	List<Houseieee> t = houseieeeService.findList(houseieee);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Houseieee houseieee = JSON.parseObject(json,Houseieee.class); 
//        	Houseieee t = houseieeeService.find(houseieee);
        	List<Houseieee> t = houseieeeService.findList(houseieee);
        	List<Object[]> s = houseieeeService.findListSql(houseieee);
        	for (Object[] objects : s) {
        		Houseieee houseieee2 = (Houseieee)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Houseieee houseieee2 = (Houseieee)objects[0];
        		DevicewarnhistoryHouseieeeidYear devicewarnhistoryHouseieeeidYear = (DevicewarnhistoryHouseieeeidYear)objects[1];
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
        	Houseieee houseieee = JSON.parseObject(json,Houseieee.class); 
        	houseieeeService.delete(houseieee);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

}
