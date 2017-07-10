package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.service.FarmDeviceServiceI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


@Controller
@RequestMapping("/farmDevice")
public class FarmDeviceController {
	private static Logger logger = Logger.getLogger(FarmDeviceController.class);
	@Resource
	private FarmDeviceServiceI farmDeviceService;

	/**
	 * 获取设备列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getEndPoint")
	public void getEndPoint(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			String houseIeee = request.getParameter("houseIeee");
			String userName = request.getParameter("user_name");
//			String pageIndexStr = request.getParameter("pageIndex");
//			String pageSizeStr = request.getParameter("pageSize");
			int pageIndex = 1;
			int pageSize = 100000000;
			/*if(StringUtils.isNotBlank(pageIndexStr)) {
				pageIndex = Integer.parseInt(pageIndexStr);
			}
			if(StringUtils.isNotBlank(pageSizeStr)) {
				pageSize = Integer.parseInt(pageSizeStr);
			}*/
			List<Map> devList = farmDeviceService.getEndPoint(userName, houseIeee, pageIndex, pageSize);
			String resultStr = JSON.toJSONStringWithDateFormat(devList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":"+resultStr+"})");
			
		}catch(Exception e){
			logger.error("getEndPoint", e);
			resultJson = "{\"result\":0,\"msg\":\"get end point exception\"}";
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 获取图表数据
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getChartData")
	public void getChartData(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map params = JSON.parseObject(json, Map.class);
			Integer chartType = (Integer) params.get("chartType");
			String startTime = (String) params.get("startTime");
			String endTime = (String) params.get("endTime");
			String houseIeee = (String) params.get("houseIeee");
			Integer unit = (Integer) params.get("unit");
			int unitInt = unit == null? 0 : unit.intValue();
			List<Map<String, Object>> chartList = farmDeviceService.getChartData(chartType, startTime, endTime, houseIeee, unitInt);
			String resultStr = JSON.toJSONStringWithDateFormat(chartList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":" + resultStr + "})");
		}catch(Exception e){
			logger.error("getChartData error", e);
			resultJson = "{\"result\":0,\"msg\":\"get chart data exception\"}";
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 推送设备
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/pushDeviceInfo")
	public void pushDeviceInfo(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			String houseIeee = request.getParameter("houseIeee");
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			farmDeviceService.pushDeviceInfo(houseIeee, params);
			resultJson = "{\"result\":1}";
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":"+resultJson+"})");	
		}catch(Exception e){
			logger.error("pushDeviceInfo", e);
			resultJson = "{\"result\":0,\"msg\":\"push device info exception\"}";
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 获取前24小时室内室外PM2.5的图表数据
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getPm25ChartData")
	public void getPm25ChartData(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map params = JSON.parseObject(json, Map.class);
			String houseIeee = (String) params.get("houseIeee"); //houseieee
			String deviceIeee = (String) params.get("deviceIeee");
			String platformType = request.getParameter("platformType");
			
			String clientIp = request.getRemoteAddr(); //客户端 IP
			
//			String houseIeee = "097a356533303836";
//			String clientIp = "218.104.133.14";
			
			List<Map<String,Object>> resultMap = farmDeviceService.getPm25ChartData(houseIeee, deviceIeee, clientIp, platformType);
			String resultStr = JSON.toJSONStringWithDateFormat(resultMap, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			resultJson = "{\"result\":1,\"data\":" + resultStr +"}";
			
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":" + resultJson + "})");
		}catch(Exception e){
			logger.error("getChartData error", e);
			resultJson = "{\"result\":0,\"msg\":\"get chart data exception\"}";
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":"+resultJson+"})");
		}
	}
}
