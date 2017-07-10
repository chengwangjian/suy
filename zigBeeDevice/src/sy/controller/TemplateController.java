package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.DeviceTemplateRel;
import sy.service.TemplateServiceI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


@Controller
@RequestMapping("/")
public class TemplateController {
	private static Logger logger = Logger.getLogger(TemplateController.class);
	private TemplateServiceI templateService;

	public TemplateServiceI getTemplateService() {
		return templateService;
	}
	@Autowired
	public void setTemplateService(TemplateServiceI templateService) {
		this.templateService = templateService;
	}
	
	/**
	 * 获取指令集
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getCmdDatas")
	public void getCmdDatas(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			DeviceTemplateRel params = JSON.parseObject(json, DeviceTemplateRel.class);
			Map<String,Object> map = templateService.mergeDataByType(params);
			if(map!=null&&!map.isEmpty()){
				String resultStr = JSON.toJSONString(map);
				out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultStr+"})");
			}else{
				resultJson = "{\"result\":-1,\"msg\":\"the type does not exists\"}";
				out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
			}
		}catch(Exception e){
			logger.error("get cmd date error", e);
			resultJson = "{\"result\":0,\"msg\":\"get cmd data exception\"}";
			out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 获取指令集
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getCmdData")
	public void getCmdData(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map params = JSON.parseObject(json, Map.class);
			Map<String,Object> map = templateService.saveAndGetCmdData(params);
			if(map!=null&&!map.isEmpty()){
				String resultStr = JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
				out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultStr+"})");
			}else{
				resultJson = "{\"result\":-1,\"msg\":\"the type does not exists\"}";
				out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
			}
		}catch(Exception e){
			logger.error("getCmdData exception", e);
			resultJson = "{\"result\":0,\"msg\":\"getCmdData exception\"}";
			out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 刷新指令集
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/refreshCmdData")
	public void refreshCmdData(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map params = JSON.parseObject(json, Map.class);
			Map<String,Object> map = templateService.refreshCmdData(params);
			if(map!=null&&!map.isEmpty()){
				String resultStr = JSON.toJSONString(map);
				out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultStr+"})");
			}else{
				resultJson = "{\"result\":-1,\"msg\":\"the relId does not exists\"}";
				out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
			}
		}catch(Exception e){
			logger.error("refreshCmdData exception", e);
			resultJson = "{\"result\":0,\"msg\":\"refreshCmdData exception\"}";
			out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 获取模拟设备列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getEmDeviceList")
	public void getEmDeviceList(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			List<Map> list= templateService.getEmDeviceList(params);
			
			String resultStr = JSON.toJSONString(list);
			resultJson = "{\"result\":1,\"emDeviceList\":"+resultStr+"}";
			out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");	
		}catch(Exception e){
			logger.error("get devicelist error", e);
			resultJson = "{\"result\":0,\"msg\":\"get devicelist exception\"}";
			out.println(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 保存模板
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/saveTemplate")
	public void saveTemplate(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map<String, Object> templMap = JSON.parseObject(json, Map.class);
			int status = templateService.saveTemplate(templMap);
			if(status==0)
				resultJson = "{\"result\":"+status+",\"msg\":\"template type repeat\"}";
			else
				resultJson = "{\"result\":"+status+",\"msg\":\"success\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			if(StringUtils.contains(e.getMessage(),"temp_id_func_num_index")){
				resultJson = "{\"result\":-2,\"msg\":\"func_num repeat\"}";
				logger.error("485action func num repeat", e);
			}
			else if(StringUtils.contains(e.getMessage(),"template_id_unique")) {
				resultJson = "{\"result\":-3,\"msg\":\"templdateId repeat\"}";
				logger.error("templdateId repeat", e);
			}
			else{
				resultJson = "{\"result\":0,\"msg\":\"saveTemplate exception\"}";
				logger.error("save template exception",e);
			}
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 保存设备
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/saveDevice")
	public void saveDevice(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map<String, Object> templMap = JSON.parseObject(json, Map.class);
			int status = templateService.saveDevice(templMap);
			if(status==0) {
				resultJson = "{\"result\":"+status+",\"msg\":\"template type repeat\"}";
			}
			else if(status == -3) {
				resultJson = "{\"result\":"+status+",\"msg\":\"repeat name\"}";
			}
			else {
				resultJson = "{\"result\":"+status+",\"msg\":\"success\"}";
			}
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			if(e!=null&&StringUtils.contains(e.getMessage(),"action_cmd_unique")){
				resultJson = "{\"result\":-2,\"msg\":\"templdate_id repeat\"}";
				logger.error("action_cmd repeat", e);
			}else{
				resultJson = "{\"result\":0,\"msg\":\"saveTemplate exception\"}";
				logger.error("saveDevice exception",e);
			}
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 获取设备详情
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getDevice")
	public void getDevice(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map params = JSON.parseObject(json, Map.class);
			Map<String, Object> device = templateService.getDevice(params);
			String resultStr = JSON.toJSONString(device);
			resultJson = "{\"result\":1,\"data\":"+resultStr+"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("getDevice exception",e);
			resultJson = "{\"result\":0,\"msg\":\"getDevice exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 分页获取设备列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getDevices")
	public void getDevices(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map params = JSON.parseObject(json, Map.class);
			List<Map> list = templateService.getDevices(params);
			String resultStr = JSON.toJSONString(list);
			resultJson = "{\"result\":1,\"data\":"+resultStr+"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("getDevices exception",e);
			resultJson = "{\"result\":0,\"msg\":\"getDevices exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 获取设备总数
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("getDeviceCount")
	public void getDeviceCount(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			int count = templateService.getDeviceCount(params);
			resultJson = "{\"result\":1,\"totle\":"+count+"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e) {
			logger.error("getDeviceCount exception", e);
			resultJson = "{\"result\":0,\"msg\":\"get template count exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 获取模板详情
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getTemplate")
	public void getTemplate(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map params = JSON.parseObject(json, Map.class);
			Map<String, Object> template = templateService.getTemplate(params);
			String resultStr = JSON.toJSONString(template);
			resultJson = "{\"result\":1,\"data\":"+resultStr+"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("getTemplate exception",e);
			resultJson = "{\"result\":0,\"msg\":\"getTemplate exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 分页获取模板列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getTempldates")
	public void getTempldates(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "";
		try{
			Map params = JSON.parseObject(json, Map.class);
			List<Map> list = templateService.getTempldates(params);
			String resultStr = JSON.toJSONString(list);
			resultJson = "{\"result\":1,\"data\":"+resultStr+"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("get templdates exception",e);
			resultJson = "{\"result\":0,\"msg\":\"get templdates exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	/**
	 * 获取模板总数
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("getTemplateCount")
	public void getTemplateCount(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			int count = templateService.getTemplateCount(params);
			resultJson = "{\"result\":1,\"totle\":"+count+"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("get template count exception", e);
			resultJson = "{\"result\":0,\"msg\":\"get template count exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 删除指令
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("deleteCmdData")
	public void deleteCmdData(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			long id = (int) params.get("id");
			int result = templateService.deleteCmdData(id);
			resultJson = "{\"result\":"+result+"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("delete cmd error", e);
			resultJson = "{\"result\":0,\"msg\":\"delete cmd exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 删除模板
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("deleteTemplate")
	public void deleteTemplate(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			Long id = Long.valueOf((int) params.get("id"));
			templateService.deleteTemplate(id);
			resultJson = "{\"result\":1}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("delete cmd error", e);
			resultJson = "{\"result\":0,\"msg\":\"delete cmd exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 删除模板
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("deleteTemplates")
	public void deleteTemplates(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			String ids = (String) params.get("ids");
			templateService.deleteTemplates(ids);
			resultJson = "{\"result\":1}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("deleteTemplates", e);
			resultJson = "{\"result\":0,\"msg\":\"delete cmd exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 删除设备
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("deleteDevice")
	public void deleteDevice(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			Long id = Long.valueOf((int) params.get("id"));
			templateService.deleteDevice(id);
			resultJson = "{\"result\":1}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("deleteDevice error", e);
			resultJson = "{\"result\":0,\"msg\":\"deleteDevice exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 批量删除设备
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("deleteDevices")
	public void deleteDevices(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			String ids = (String) params.get("ids");
			templateService.deleteDevices(ids);
			resultJson = "{\"result\":1}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("deleteDevices", e);
			resultJson = "{\"result\":0,\"msg\":\"deleteDevices exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 查询模板指令
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("getTempById")
	public void getTempById(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			List<Map> actions = templateService.getTemplateActionById(params);
			resultJson = JSON.toJSONStringWithDateFormat(actions, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			resultJson = "{\"result\":1,\"data\":" + resultJson + "}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("getTempById", e);
			resultJson = "{\"result\":0,\"msg\":\"getTempById exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
	
	/**
	 * 查询功能名称列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("getFuncNumLib")
	public void getFuncNumLib(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Controller-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		String callback = request.getParameter("callback");
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			List<Map> funcNumLib = templateService.getFuncNumLib(params);
			resultJson = JSON.toJSONStringWithDateFormat(funcNumLib, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			resultJson = "{\"result\":1,\"data\":" + resultJson + "}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}catch(Exception e){
			logger.error("getFuncNumLib", e);
			resultJson = "{\"result\":0,\"msg\":\"getFuncNumLib exception\"}";
			out.print(callback+"({\"request_id\":1234,\"response_params\":"+resultJson+"})");
		}
	}
}
