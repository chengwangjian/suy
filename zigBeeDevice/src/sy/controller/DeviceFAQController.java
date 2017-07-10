package sy.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import sy.model.DeviceFAQ;
import sy.service.DeviceFAQServiceI;

@Controller
@RequestMapping("/DeviceFAQController")
public class DeviceFAQController {
	
	private static final Logger LOGGER = Logger.getLogger(DeviceFAQController.class);
	
	private DeviceFAQServiceI deviceFaqService;

	public DeviceFAQServiceI getDeviceFaqService() {
		return deviceFaqService;
	}

	@Autowired
	public void setDeviceFaqService(DeviceFAQServiceI deviceFaqService) {
		this.deviceFaqService = deviceFaqService;
	}
	
	@RequestMapping("/addFAQ")
	public void addFAQ(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try{
			DeviceFAQ deviceFaq = JSON.parseObject(json,DeviceFAQ.class);
			String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String lastTime = createTime;
			deviceFaq.setCreateTime(createTime);
			deviceFaq.setLastTime(lastTime);
			int result =deviceFaqService.addFAQ(deviceFaq);
			if(result==1){
				String resultJson = "{\"result\":1,\"Message\":\"添加成功\"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}else{
				String resultJson = "{\"result\":-1,\"Message\":\"型号已添加\"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}
		}catch(Exception e){
			String resultJson = "{\"result\":0,\"Message\":\"添加失败\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}

	@RequestMapping("/updateFAQ")
	public void updateFAQ(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try{
			Map<String,Object> params = JSON.parseObject(json,Map.class);
			String modelNoOld = (String) params.get("modelNoOld");
			String modelNo = (String) params.get("modelNo");
			String descriptionCn = (String) params.get("descriptionCn");
			String descriptionEn = (String) params.get("descriptionEn");
			String lastTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			DeviceFAQ deviceFaq = new DeviceFAQ();
			deviceFaq.setModelNo(modelNo);
			deviceFaq.setDescriptionCn(descriptionCn);
			deviceFaq.setDescriptionEn(descriptionEn);
			deviceFaq.setLastTime(lastTime);
			int result =deviceFaqService.updateFAQ(deviceFaq,modelNoOld);
			if(result==1){
				String resultJson = "{\"result\":1,\"Message\":\"修改成功\"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}else{
				String resultJson = "{\"result\":-1,\"Message\":\"型号已修改\"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}
		}catch(Exception e){
			String resultJson = "{\"result\":0,\"Message\":\"修改失败\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}
	
	
	@RequestMapping("/deleteFAQ")
	public void deleteFAQ(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try{
			Map<String,Object> params = JSON.parseObject(json,Map.class);
			String modelNo =(String) params.get("modelNo");
			deviceFaqService.deleteFAQ(modelNo);
			String resultJson = "{\"result\":1,\"Message\":\"删除成功\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			
		}catch(Exception e){
			String resultJson = "{\"result\":0,\"Message\":\"删除失败\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}
	
	@RequestMapping("/getFAQCount")
	public void getFAQCount(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String resultJson="";
		try{
			Map<String,Object> params = JSON.parseObject(json,Map.class);
			String searchText = (String) params.get("searchText");
			long Faqtoatl = deviceFaqService.getFAQCount(params, searchText);
			resultJson = JSON.toJSONStringWithDateFormat(Faqtoatl, "yyyy-MM-dd HH:mm:ss");
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}catch(Exception e){
			LOGGER.info("get Faqtoatl error", e);
			resultJson = "{\"result\":0,\"msg\":\"get Faqtoatl  exception\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	} 
	
	@RequestMapping("/getFAQList")
	public void getFAQList(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		String resultJson="";
		try{
			
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			String startRow = (String)params.get("startRow");
			String pageSize = (String)params.get("pageSize");
			String searchText =(String) params.get("searchText");
			//将整个的map 对象传到方法中，这边也可以先获取我们要传的指定的参数再传入方法。
			List<Map> list = deviceFaqService.getFAQList(startRow,pageSize,searchText);
			if(list !=null){
				String resultstr = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
				resultJson = "{\"result\":1,\"deviceList\":"+resultstr+"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}else{
				resultJson = "{\"result\":0,\"msg\":\"get deviceFAQlist faild\"}";
			}
		}catch(Exception e){
			LOGGER.info("show deviceFAQlist failed!", e);
			resultJson = "{\"result\":-1,\"msg\":\"get deviceFAQlist  exception\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}
	
	
	@RequestMapping("/getFAQByModelNo")
	public void getFAQByModelNo(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		String resultJson="";
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			String modelNo = (String) params.get("modelNo");
			//将整个的map 对象传到方法中，这边也可以先获取我们要传的指定的参数再传入方法。
			List<Map> list = deviceFaqService.getFAQByModelNo(modelNo);
			if(list !=null){
				String resultstr = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
				resultJson = "{\"result\":1,\"deviceList\":"+resultstr+"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}else{
				resultJson = "{\"result\":0,\"msg\":\"get deviceFAQlist faild\"}";
			}
		}catch(Exception e){
			LOGGER.info("show deviceFAQlist failed!", e);
			resultJson = "{\"result\":-1,\"msg\":\"get deviceFAQlist  exception\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
		
	}
	
	@RequestMapping("/add")
	public void add(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> params = new HashMap<String, Object>();
	
		try{
			//Map params = JSON.parseObject(json, Map.class);
			String modelNo = request.getParameter("modelNo");
			String descriptionCn = request.getParameter("descriptionCn");
			String descriptionEn = request.getParameter("descriptionEn");
			params.put("modelNo", modelNo);
			params.put("descriptionCn", descriptionCn);
			params.put("descriptionEn", descriptionEn);
			String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String lastTime = createTime;
			int result =deviceFaqService.add(params,createTime,lastTime);
			if(result==1){
				String resultJson = "{\"result\":1,\"Message\":\"添加成功\"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}else{
				String resultJson = "{\"result\":-1,\"Message\":\"型号已添加\"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}
		}catch(Exception e){
			LOGGER.info("add", e);;
			String resultJson = "{\"result\":0,\"Message\":\"添加失败\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}
	
	@RequestMapping("/updatedeviceFAQ")
	public void updatedeviceFAQ(String json,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		//response.setCharacterEncoding("text/html;charset=utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		//Map<String, Object> resultMap = new HashMap<String, Object>();	
		try{
			String modelNo = request.getParameter("modelNo");
			String descriptionCn = request.getParameter("descriptionCn");
			String descriptionEn = request.getParameter("descriptionEn");
			Map<String,Object> params = JSON.parseObject(json,Map.class);
			String lastTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			int result =deviceFaqService.updatedeviceFAQ(modelNo,descriptionCn,descriptionEn,lastTime);
			if(result==1){
				String resultJson = "{\"result\":1,\"Message\":\"修改成功\"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}else{
				String resultJson = "{\"result\":-1,\"Message\":\"型号已修改\"}";
				out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
			}
		}catch(Exception e){
			LOGGER.info("updatedeviceFAQ", e);
			String resultJson = "{\"result\":0,\"Message\":\"修改失败\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}
	
	@RequestMapping("/getModelList")
	public void getModelList(String json, HttpServletRequest request,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			Map params = JSON.parseObject(json, Map.class);
			List<Map> list = deviceFaqService.getModelList(params);
			String resultstr = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			String  resultJson = "{\"result\":1,\"deviceList\":"+resultstr+"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		} catch (Exception e) {
			LOGGER.info("getModelList", e);
			String resultJson = "{\"result\":0,\"Message\":\"修改失败\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}
	@RequestMapping("/getModelList2")
	public void getModelList2(String json, HttpServletRequest request,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			Map params = JSON.parseObject(json, Map.class);
			List<Map> list = deviceFaqService.getModelList2(params);
			String resultstr = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			String  resultJson = "{\"result\":1,\"deviceList\":"+resultstr+"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		} catch (Exception e) {
			LOGGER.info("getModelList2", e);
			String resultJson = "{\"result\":0,\"Message\":\"修改失败\"}";
			out.print("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}
	
	
}
