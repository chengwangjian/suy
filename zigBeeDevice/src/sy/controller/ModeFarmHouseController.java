package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import sy.model.House;
import sy.model.ModeFarmHouse;
import sy.service.ModeFarmHouseServiceI;

@SuppressWarnings("all")
@Controller
@RequestMapping("/ModeFarmHouseController")
public class ModeFarmHouseController {
	private static final Logger LOGGER = Logger.getLogger(ModeFarmHouseController.class);

	@Autowired
	private ModeFarmHouseServiceI ModeFarmHouseService;
   
	public static Logger getLogger() {
		return LOGGER;
	}

	/* pc端根据houseIeee修改note字段 */
	@RequestMapping("/updateAreaNote")
	public void addUpdate(String json, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int n;
		PrintWriter out = response.getWriter();
		try {
			Map<String, Object> map = JSON.parseObject(json, Map.class);
			n = ModeFarmHouseService.update(map);
			@SuppressWarnings("unused")
			String resultJson = JSON.toJSONStringWithDateFormat(n, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("find", e);
			String resultJson = "{\"result\":1}";// 成功0 失败1
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}

	}

	/**
	 * 获得note一个字段
	 * 时间 2017-6-3
	 * @author chengwangjian
	 * @param json
	 * @param request
	 * @param response
	 */
	
	@RequestMapping("/get")
	public void getNote(String json, HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Map<String, Object> map = JSON.parseObject(json, Map.class);
		try{
			House house = ModeFarmHouseService.get(map);
			String note = house.getNote();
			
			String resultJson = JSON.toJSONString(note);
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"note\":" + resultJson + "})");
			
		}catch(Exception e){
			
			LOGGER.info("/get",e);
		
		}

	}

}
