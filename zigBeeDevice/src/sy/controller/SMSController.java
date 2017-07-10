package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.service.SMSServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/SMSController")
public class SMSController {
	private static final Logger LOGGER = Logger.getLogger(SMSController.class);
	private SMSServiceI smsServiceI;
	public SMSServiceI getSmsServiceI() {
		return smsServiceI;
	}
	@Autowired
	public void setSmsServiceI(SMSServiceI smsServiceI) {
		this.smsServiceI = smsServiceI;
	}
	
	@RequestMapping("/addSMS")
	public void addSMS(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> param = JSON.parseObject(json,Map.class);		
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		try {
			String houseIeee = (String)param.get("houseIeee");
			String phoneNO = (String)param.get("phoneNO");
			String warnEmail = (String)param.get("warnEmail");
			String behavior = (String)param.get("behavior");
			int i = smsServiceI.addSMSandWE(houseIeee, phoneNO, warnEmail,behavior);
			if(i==-1){
	    		String resultJson1 = "{\"result\":-1}";//已存在
	        	out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson1 + "})"); 
	    	}
	    	else {
	    		String resultJson1 = "{\"result\":1}";//成功
	    		out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson1 + "})"); 
			}
		} catch (Exception e) {
			LOGGER.info("addSMS",e);
			String resultJson1 = "{\"result\":0}";//
    		out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson1 + "})"); 
		}  
	}
	
	@RequestMapping("/findSMS")
	public void findSMS(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> params = JSON.parseObject(json, Map.class);
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		try {
			String houseIeee = (String)params.get("houseIeee");
			List<Map> list = smsServiceI.findSMS(houseIeee);
			List<Map> list2 = smsServiceI.findEmail(houseIeee);
			String resultJson=JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			String resultJson1=JSON.toJSONStringWithDateFormat(list2, "yyyy-MM-dd HH:mm:ss");
			out.println(callback+"({\"request_id\": 1234, \"SMS\":" + resultJson + ", \"warnEmail\":" + resultJson1 + "})");
			
		} catch (Exception e) {
			LOGGER.info("findSMS",e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deleteSW")
	public void deleteSW(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> params = JSON.parseObject(json,Map.class);
		String callback = request.getParameter("callback");
		String behavior = request.getParameter("behavior");
		PrintWriter out = response.getWriter();
		try {
			int i = smsServiceI.deleteSW(params, behavior);
			String resultJson = "{\"result\":1}";//成功1 失败0
			out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("deleteSW",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}
	}
}
