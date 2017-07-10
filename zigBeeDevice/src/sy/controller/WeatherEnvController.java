package sy.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import sy.service.WeatherEnvServiceI;

@Controller
@RequestMapping("/weatherenv")
public class WeatherEnvController {
	private static Logger logger = Logger.getLogger(WeatherEnvController.class);
	private WeatherEnvServiceI weatherEnvService;

	public WeatherEnvServiceI getWeatherEnvService() {
		return weatherEnvService;
	}

	@Autowired
	public void setWeatherEnvService(WeatherEnvServiceI weatherEnvService) {
		this.weatherEnvService = weatherEnvService;
	}

	@RequestMapping("/add")
	public void add(String json, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Map param = JSON.parseObject(json, Map.class);
			// String serverIP = request.getServerName();
			String IP = request.getRemoteAddr();
			String houseIeee = (String) param.get("houseIeee");
			weatherEnvService.add(houseIeee, IP);
			String resultJson = "{\"result\":1,\"Message\":\"添加成功\"}";
			out.print("{\"request_id\":1234,\"response_params\":" + resultJson + "}");

		} catch (Exception e) {
			logger.info("add", e);
			;
			String resultJson = "{\"result\":0,\"Message\":\"添加失败\"}";
			out.print("{\"request_id\":1234,\"response_params\":" + resultJson + "}");
		}
	}

	@RequestMapping("/getWeather")
	public void getWeather(String json, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		String callback = request.getParameter("callback");
		callback = StringUtils.isNoneBlank(callback) ? callback : "1234"; 
		try {
			Map param = JSON.parseObject(json, Map.class);
			// String serverIp = request.getServerName();
			String houseIeee = (String) param.get("houseIeee");
			String IP = request.getRemoteAddr();
			logger.info("获取得到的外网IP地址时=======+++++：" + IP);
			Map weather = weatherEnvService.Weather(houseIeee, IP);
//			respParams.setRequest_id("1234");
//			respParams.setResponse_params(new SuccessBack(weather, "请求成功"));
			String resultstr = JSON.toJSONStringWithDateFormat(weather, "yyyy-MM-dd HH:mm:ss");
			String resultJson = "{\"result\":1,\"data\":" + resultstr + "}";
			out.print("{\"request_id\": " + callback + ",\"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			logger.info("getWeather", e);
			String resultJson = "{\"result\":0,\"Message\":\"获取失败\"}";
			out.print("{\"request_id\":" + callback + ",\"response_params\":" + resultJson + "}");
//			respParams.setRequest_id("1234");
//			String msg = (e instanceof IllegalArgumentException) ? e.getMessage() : "获取失败" ; 
//			respParams.setResponse_params(new ErrorBack(msg));
		}
		
	}
	
}
