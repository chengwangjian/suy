package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.smarthome.model.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Advertisement;
import sy.service.AdvertisementServiceI;
import zbHouse.pageModel.Json;

import com.alibaba.fastjson.JSON;

@Controller()
@RequestMapping("/AdvertisementController")
public class AdvertisementController {
	private static final Logger LOGGER = Logger.getLogger(ApponlineinfoController.class);
	private AdvertisementServiceI AdvertisementService;
	
	public AdvertisementServiceI getAdvertisementService() {
		return AdvertisementService;
	}
	@Autowired
	public void setAdvertisementService(AdvertisementServiceI advertisementService) {
		AdvertisementService = advertisementService;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
	
	@RequestMapping("/getCount")	
	public void getCount(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();
		DataGrid dg = new DataGrid();
		try {
			map=JSON.parseObject(json);	
			map.put("total", AdvertisementService.getCount(map));
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getCount",e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);		
	}
	
	/*云端手动新增修改广告*/
	@RequestMapping("/updateAdvertisement")
	public void updateAdvertisement(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
        try {
        	Advertisement advertisement = JSON.parseObject(json,Advertisement.class); 
        	if(advertisement.getReadstate()!=null){
        		advertisement.setReadstate(advertisement.getReadstate());
        	}
        	int t = AdvertisementService.update(advertisement);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateAdvertisement",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
	}
	
	 /*云端查询广告*/
    @RequestMapping("/find1")
	public void find1(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html;charset=utf-8");
    	String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		String orderBy = request.getParameter("orderBy");
    	try {
    		if(StringUtils.isBlank(startRow))
				startRow = "0";
			if(StringUtils.isBlank(pageSize))
				pageSize = "30";
    		Advertisement advertisement = JSON.parseObject(json,Advertisement.class); 
    		List<Advertisement> t = AdvertisementService.find1(startRow,pageSize,orderBy,advertisement);
    		String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("find1",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
    }
	  /*APP查询广告*/
    @RequestMapping("/appfindad")
	public void appfindad(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html;charset=utf-8");
    	try {
    		Advertisement advertisement = JSON.parseObject(json,Advertisement.class); 
    		if(advertisement.getUsername()!=null){
    			advertisement.setUsername(advertisement.getUsername());
    		}
    		List<Advertisement> t = AdvertisementService.find(advertisement);
    		String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("appfindad",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
    }
    
    /*APP修改广告读取状态*/
    @RequestMapping("/updatereadstate")
  	public void updatereadstate(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html;charset=utf-8");
    	try {
    		Advertisement advertisement = JSON.parseObject(json,Advertisement.class); 
    		int t = AdvertisementService.appupdatereadstate(advertisement);
    		String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("updatereadstate",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
    	}
/*    public static byte[] encrypt2(String content, String password)  
			 throws Exception {	
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");  
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
        byte[] byteContent = content.getBytes("utf-8");  
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
        byte[] result = cipher.doFinal(byteContent);  
        return result; // 加密   
	 }
    
    *//**将二进制转换成16进制 
	 * @param buf 
	 * @return 
	 *//*  
	public static String parseByte2HexStr(byte buf[]) {  
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                }  
	                sb.append(hex.toUpperCase());  
	        }  
	        return sb.toString();  
	} */
    
    public void writeJson(Object object,String callback,HttpServletResponse response) {

		try {
			String jstr=callback+"("+JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss")+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson",e);
		}
	}	
   }

