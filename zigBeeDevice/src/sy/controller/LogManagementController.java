package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.service.LogManagementServiceI;

@Controller
@RequestMapping("/logManagementController")
public class LogManagementController {
	private static final Logger LOGGER = Logger.getLogger(LogManagementController.class);

	private LogManagementServiceI logManagementService;
	public LogManagementServiceI getLogManagementService() {
		return logManagementService;
	}
	@Autowired
	public void setLogManagementService(LogManagementServiceI logManagementService) {
		this.logManagementService = logManagementService;
	}
	/**
	 * 
	 * 新增日志
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/commitLog")
	public void commitLog(String json, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		response.addHeader("content-type", "text/html; charset=utf-8");
		//response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		try {
			
		  HashMap<String,Object> params=new HashMap<String, Object>();
		  params.put("houseIeee", request.getParameter("houseIeee"));
		  params.put("logType", request.getParameter("logType"));
		  params.put("deviceSN", request.getParameter("deviceSN"));
		  params.put("clazz", request.getParameter("clazz"));
		  params.put("content", request.getParameter("content"));
		  params.put("time", request.getParameter("time"));
		  logManagementService.commitLog(params);
		  resultJson = "{\"status\":1}";
		  String callback = request.getParameter("callback");
		  out.println(callback+ "({\"request_id\": 1234, \"response_params\":"+ resultJson + "})");
		} catch (Exception e) {
			LOGGER.error("Save SmartDevice Data Exception", e);
			resultJson = "{\"status\":-1}";
			String callback = request.getParameter("callback");
			out.println(callback+ "({\"request_id\": 1234, \"response_params\":"+ resultJson + "})");
		}
	}
	
	
	
}
