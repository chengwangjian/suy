package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import sy.model.RespParams;
import sy.service.FarmAreaI;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/farmArea")
public class FarmAreaController {
	private static Logger logger = Logger.getLogger(FarmAreaController.class);

	private FarmAreaI farmAreaService;

	public FarmAreaI getFarmAreaService() {
		return farmAreaService;
	}

	@Autowired
	public void setFarmAreaService(FarmAreaI farmAreaService) {
		this.farmAreaService = farmAreaService;
	}

	@RequestMapping("/getarealist")
	public void getarealist(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		RespParams resp = new RespParams();
		String callback = request.getParameter("callback");
		if (callback == null) {
			callback = "";
		}
		try {
			String username = request.getParameter("user_name");
			List<Map> list = farmAreaService.getAreaList(username);
			resp.setRequest_id(callback);
			resp.setResponse_params(list);
			out.println(callback + "(" + JSON.toJSONStringWithDateFormat(resp, "yyyy-MM-dd HH:mm:ss",
					SerializerFeature.WriteNullStringAsEmpty) + ")");
		} catch (Exception e) {
			logger.error("get area list exception", e);
			resp.setRequest_id(callback);
			resp.setResponse_params("");
			out.println(callback + "(" + JSON.toJSONString(resp) + ")");
		}

	}

	

	@RequestMapping("/getareas") 
	public void getareas(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		RespParams resp = new RespParams();
		String callback = "1234";
		try {
			String username = request.getParameter("user_name");
			String pageIndexStr = request.getParameter("pageIndex");
			String pageSizeStr = request.getParameter("pageSize");
			int pageIndex = 1;
			int pageSize = 20;
			if (StringUtils.isNotBlank(pageIndexStr)) {
				pageIndex = Integer.parseInt(pageIndexStr);
			}
			if (StringUtils.isNotBlank(pageSizeStr)) {
				pageSize = Integer.parseInt(pageSizeStr);
			}
			List<Map> list = farmAreaService.getAreas(username, pageIndex, pageSize);
			resp.setRequest_id(callback);
			resp.setResponse_params(list);
			out.println(JSON.toJSONString(resp));
		} catch (Exception e) {
			logger.error("get areas exception", e);
			resp.setRequest_id(callback);
			resp.setResponse_params("");
			out.println(JSON.toJSONString(resp));
		}
	}

	@RequestMapping("/getAreaAttrs")
	public void getAreaAttrs(String json, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String rJson = "";
		try {
			List<String> hIeeeList = JSON.parseArray(json, String.class);

			Map<String, Map> areaMap = farmAreaService.getAreaAttrs(hIeeeList);
			String areaJson = JSON.toJSONStringWithDateFormat(areaMap, "yyyy-MM-dd HH:mm:ss",
					SerializerFeature.WriteMapNullValue);
			rJson = "{\"result\":1,\"areas\":" + areaJson + "}";
		} catch (Exception e) {
			rJson = "{\"result\":0}";
			logger.error("getAreaAttrs exception", e);
		}
		out.println(rJson);
	}
	
	/**
	 * 添加区域
	 * 创建时间 2017-7-4
	 * @author chengwangjian
	 * @param houseIEEE
	 * @param username
	 * @param houseName
	 * 
	 */
	
	@RequestMapping("/addModeAeFarm")
	public void addModeAeFarm(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;setchar=utf-8");
		String callback = request.getParameter("callback");
		int n;
		String rJson = "";
		try{

			Map<String,Object> map = new HashMap<String, Object>();
			map = JSON.parseObject(json);
			Map userMap = new HashMap<String, Object>();
			userMap = (Map) JSON.parse(json);
			n = farmAreaService.JoinHouse(map, userMap);
			String result = JSON.toJSONString(n);
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":" + result + "})");
			
		}catch(Exception e){
			e.printStackTrace();
			 int t = 1;
			String result = JSON.toJSONString(t);
			out.println(callback+"({\"request_id\":\"" + callback + "\",\"response_params\":" + result + "})");
		}
		
	}
}
