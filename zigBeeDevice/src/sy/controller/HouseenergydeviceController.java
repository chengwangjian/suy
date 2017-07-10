package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.DeviceoperatehistoryHouseidYear;
import sy.model.Houseenergydevice;
import sy.service.DeviceoperatehistoryHouseidYearServiceI;
import sy.service.HouseenergydeviceServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/houseenergydeviceController")
public class HouseenergydeviceController {

	private HouseenergydeviceServiceI houseenergydeviceService;
	private DeviceoperatehistoryHouseidYearServiceI deviceoperatehistoryHouseidYearService;
	
	private static final Logger LOGGER = Logger.getLogger(HouseenergydeviceController.class);  

	public DeviceoperatehistoryHouseidYearServiceI getDeviceoperatehistoryHouseidYearService() {
		return deviceoperatehistoryHouseidYearService;
	}

	@Autowired
	public void setDeviceoperatehistoryHouseidYearService(DeviceoperatehistoryHouseidYearServiceI deviceoperatehistoryHouseidYearService) {
		this.deviceoperatehistoryHouseidYearService = deviceoperatehistoryHouseidYearService;
	}
	
	public HouseenergydeviceServiceI getHouseenergydeviceService() {
		return houseenergydeviceService;
	}

	@Autowired
	public void setHouseenergydeviceService(HouseenergydeviceServiceI houseenergydeviceService) {
		this.houseenergydeviceService = houseenergydeviceService;
	}

	@RequestMapping("/addOrUpdate")
	public void saveHouseenergydevice(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
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
		
		/*List<Houseenergydevice> houseenergydeviceList = JSON.parseArray(json,Houseenergydevice.class); 
        for (Houseenergydevice houseenergydevice : houseenergydeviceList) { 
        	houseenergydeviceService.save(houseenergydevice);
        } */
		
        try {
        	Houseenergydevice houseenergydevice = JSON.parseObject(json,Houseenergydevice.class); 
        	Houseenergydevice t = houseenergydeviceService.get(houseenergydevice);
        	if (t == null) {
        		if (houseenergydeviceService.getExisit(houseenergydevice) == null) {
        			houseenergydeviceService.save(houseenergydevice);
        		}
        	} else {
        		if (houseenergydevice.getDeviceType() != null) {
        			t.setDeviceType(houseenergydevice.getDeviceType());
        		}
        		houseenergydeviceService.update(t);
        	}
        	
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveHouseenergydevice", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveHouseenergydevice";
	}
	
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
		
        try {
        	Houseenergydevice houseenergydevice = JSON.parseObject(json,Houseenergydevice.class); 
        	List<Houseenergydevice> t = houseenergydeviceService.findList(houseenergydevice);
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
        	Houseenergydevice houseenergydevice = JSON.parseObject(json,Houseenergydevice.class); 
        	houseenergydeviceService.delete(houseenergydevice);
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}        
	}
	
	/**
	 * 替换设备功能需要做如下补充：
    在执行设备替换动作的时候，需要通知云端更新电能统计相关的部分替换设备的IEEE地址，防止设备替换的目标正好是电能计算用到的设备，
    造成电能计算逻辑部分设备信息不正确，计算出来的电能值也不正确。修改 device表、新增操作历史记录、修改houseenergydevice表
     如果OldEP和NewEP参数值都为空，那么替换所有OldIEEE信息成newIEEE
	 * @author: zhuangxd
	 * 时间：2014-6-24 下午1:55:04
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/ReplaceDevice")
	public void ReplaceDevice(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		// z203调用
//		http://192.168.1.123:8080/zigBeeDevice/houseenergydeviceController/ReplaceDevice.do?json={"houseIEEE":"00137A000001013B","ieee":"00137A000000DA4D","ep":"","newIeee":"00137A0000000529","newEp":""}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000007785
		PrintWriter out=response.getWriter();  
        try {
        	Houseenergydevice houseenergydevice = JSON.parseObject(json,Houseenergydevice.class); 
        	houseenergydeviceService.replaceDevice(houseenergydevice);
        	DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear = new DeviceoperatehistoryHouseidYear(); 
        	deviceoperatehistoryHouseidYear.setHouseIeee(houseenergydevice.getHouseIeee());
        	deviceoperatehistoryHouseidYear.setDeviceIeee(houseenergydevice.getIeee());
        	deviceoperatehistoryHouseidYear.setDeviceEp(houseenergydevice.getEp());
        	deviceoperatehistoryHouseidYear.setOpname("替换设备");
        	deviceoperatehistoryHouseidYear.setOpparam("newIeee:" + houseenergydevice.getNewIeee() + " newEp:" + houseenergydevice.getNewEp());
        	// 新增操作历史记录
        	deviceoperatehistoryHouseidYearService.saveSql(deviceoperatehistoryHouseidYear);
        	String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("ReplaceDevice", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}        
	}	
}
