package zbEnergy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import zbEnergy.service.GeneralServiceI;
import zbHouse.pageModel.General;
import zbHouse.pageModel.Json;
import zbHouse.util.TestLog4j;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/generalController")
public class GeneralController {

	private static final Logger LOGGER = Logger.getLogger(GeneralController.class);
	private GeneralServiceI generalService;

	public GeneralServiceI getGeneralService() {
		return generalService;
	}
	@Autowired
	public void setGeneralService(GeneralServiceI generalService) {
		this.generalService = generalService;
	}

	@RequestMapping("/add")
	public void showSave(String json,String callback,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
		
		Json j = new Json();
		try {
			General js=JSON.parseObject(json, General.class);
			if(generalService.saveOrUpdate(js)==1)
				params.put("result", 1);
			else
				params.put("result", 0);				
			j.setResponse_params(params);
			writeJson(j,callback,response);
		} catch (Exception e) {
			LOGGER.info("showSave", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,callback,response);			
		}
		TestLog4j.testInfo("ADD:"+json);
		//request.setAttribute("j",j);
			
	}
	
	@RequestMapping("/delete")
	public void showDelete(String json,String callback,HttpServletRequest request,HttpServletResponse response)throws IOException {	
		Map<String, Object> params = new HashMap<String, Object>();
		//request.setAttribute("js",js);			
	       
		Json j = new Json();
		try {
			
			General js=JSON.parseObject(json, General.class);
			j.setResponse_params(generalService.delete(js));
			writeJson(j,callback,response);
		} catch (Exception e) {
			LOGGER.info("showDelete", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,callback,response);
		}
		TestLog4j.testInfo("DEL:"+json);
		//request.setAttribute("j",j);
		
	}

	@RequestMapping("/find")
	public void showFind(String json,String callback,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();
		
		Json j = new Json();
		try {
			
			General js=JSON.parseObject(json, General.class);
			j.setResponse_params(generalService.find(js));
			writeJson(j,callback,response);
		} catch (Exception e) {
			LOGGER.info("showFind", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,callback,response);
		}		
		TestLog4j.testInfo("FIND:"+json);
		//request.setAttribute("j",j);			
			
	}
	
	public void writeJson(Object object,String callback,HttpServletResponse response) throws IOException{

		try {
			String jstr= JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",SerializerFeature.WriteMapNullValue);
			jstr= callback + "(" + jstr + ")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson", e);
		}
	}	
}
