package zbEnergy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import zbEnergy.service.AnalysisServiceI;
import zbHouse.pageModel.Analysis;
import zbHouse.pageModel.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/analysisController")
public class AnalysisController {

	private static final Logger LOGGER = Logger.getLogger(AnalysisController.class);
	private AnalysisServiceI analysisService;

	public AnalysisServiceI getAnalysisService() {
		return analysisService;
	}
	@Autowired
	public void setAnalysisService(AnalysisServiceI analysisService) {
		this.analysisService = analysisService;
	}
	
	@RequestMapping("/findAll")
	public void showFindAll(String json,String callback,HttpServletRequest request,HttpServletResponse response) throws IOException {	

		//request.setAttribute("js",js);			
		Map<String, Object> params = new HashMap<String, Object>();
 
		Json j = new Json();
		try {
			Analysis js=JSON.parseObject(json, Analysis.class);
			j.setResponse_params(analysisService.findAll(js,params));
			writeJson(j,callback,response);
		
		} catch (Exception e) {
			LOGGER.info("showFindAll", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,callback,response);

		}
		//request.setAttribute("j",j);
		
	}

	@RequestMapping("/findEach")
	public void showFindEach(String json,String callback,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		//request.setAttribute("js",js); 	
		Map<String, Object> params = new HashMap<String, Object>();

		Json j = new Json();
		try {
			Analysis js=JSON.parseObject(json, Analysis.class);
			j.setResponse_params(analysisService.findEach(js,params));
			writeJson(j,callback,response);
		
		} catch (Exception e) {
			LOGGER.info("showFindEach", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,callback,response);

		}
		//request.setAttribute("j",j);			
			
	}
	
	@RequestMapping("/findRegion")
	public void showFindRegion(String json,String callback,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		//request.setAttribute("js",js);
		Map<String, Object> params = new HashMap<String, Object>();	
		
		Json j = new Json();
		try {
			Analysis js=JSON.parseObject(json, Analysis.class);
			j.setResponse_params(analysisService.findRegion(js,params));
			writeJson(j,callback,response);
		
		} catch (Exception e) {
			LOGGER.info("showFindRegion", e);
			params.put("result", 0);
			j.setResponse_params(params);
			writeJson(j,callback,response);

		}		
	}
	@RequestMapping("/temperature_hum_valuelist")
	public void temperaturehumvaluelist(String json,String callback,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		//request.setAttribute("js",js);
//		select max(value+0.0) as maxvale from deviceattributehistory_00137a000001012e_2014_copy
//		select min(value+0.0) as maxvale from deviceattributehistory_00137a000001012e_2014_copy
//		select avg(value+0.0) as maxvale from deviceattributehistory_00137a000001012e_2014_copy
		Map<String, Object> params = new HashMap<String, Object>();	
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");
		try {
			params=JSON.parseObject(json);
			Map<String, Object> t=analysisService.temperature_hum_valuelist(params);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			 callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		
		} catch (Exception e) {
			LOGGER.info("temperature_hum_valuelist",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			 callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}		
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
