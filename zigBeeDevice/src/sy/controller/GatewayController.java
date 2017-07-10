package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.service.GatewayServiceI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;



@Controller
@RequestMapping("/gatewayController")
public class GatewayController {
	private static final Logger LOGGER = Logger.getLogger(GatewayController.class);
	private GatewayServiceI gatewayService;
	
	public GatewayServiceI getGatewayService() {
		return gatewayService;
	}
     @Autowired
	public void setGatewayService(GatewayServiceI gatewayService) {
		this.gatewayService = gatewayService;
	}

	@RequestMapping("/saveLog")
	public void saveLog(String json,HttpServletRequest request,HttpServletResponse response){
		PrintWriter out=null;
	    String resultJson="";
	try {
		  response.addHeader("Access-Control-Allow-Origin", "*");
		  out=response.getWriter();
	      Map map=JSON.parseObject(json, Map.class);
	      int status= this.gatewayService.saveLog(map);
	      String callback = request.getParameter("callback");
		  resultJson = "{\"status\":0,\"status_msg\":\"sucess\"}";
		  out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
	} catch (Exception e) {
		// TODO: handle exception
		 String callback = request.getParameter("callback");
		  resultJson = "{\"status\":-1,\"status_msg\":\"fail\"}";
		  out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
	}	
		
	}
	@RequestMapping("/getGatewayLogCount")
	public void getGatewayLogCount(String json, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, Object> param = JSON.parseObject(json, Map.class);
			long enmgCount = gatewayService.getGatewayLogCount((String) param.get("searchText"));
			String resultJson = "{\"result\":1, \"total\":" + enmgCount + "}";
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.error("getGatewayLogCount", e);
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/getGatewayLogs")
	public void getGatewayLogs(String json, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map jsonMap = JSON.parseObject(json, Map.class);
			List<Map> iosList = gatewayService.getGatewayLogs(
					(String) jsonMap.get("searchText"),
					(Integer) jsonMap.get("pageSize"),
					(Integer) jsonMap.get("startRow"));
			String resultJson = JSON.toJSONStringWithDateFormat(iosList, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.error("getGatewayLogs", e);
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/deleteGatewayLog")
	public void deleteGatewayLog(String json, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, Object> jsonMap = JSON.parseObject(json, Map.class);
			gatewayService.deleteGatewayLog((String) jsonMap.get("id"));
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":{\"result\":1}})");
		} catch (Exception e) {
			LOGGER.error("deleteGatewayLog", e);
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	/*查看重启的次数*/
	@RequestMapping("/getStartOver")
	public void getStartOver(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, Object> param = JSON.parseObject(json, Map.class);
			String houseIeee = param.get("houseIeee").toString();
			String lasttime = param.get("lasttime").toString();
		//	LOGGER.info("得到的时间====》：" + lasttime);
			if(StringUtils.isBlank(houseIeee)) {
				String resultJson = "{\"result\":-1}";// -1 没有重启
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			if(StringUtils.isBlank(lasttime)) {
				String resultJson = "{\"result\":-1}";// -1没有重启
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			List<Map> list = this.gatewayService.getStartOver(houseIeee, lasttime);
			String resultJson =  JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
		    String callback = request.getParameter("callback");
		    out.println("{\"request_id\": 1234, \"response_params\":" + "{\"result\":1" + "," + "\"getList\": " + resultJson + "}}");			
		} catch (Exception e) {
			LOGGER.info("出错的是：", e);
			String resultJson = "{\"result\":0}";// -1没有重启
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
		
		
	}
	
}
