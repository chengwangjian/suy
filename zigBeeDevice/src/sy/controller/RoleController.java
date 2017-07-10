package sy.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.service.RoleI;
import sy.model.Role;

import com.alibaba.fastjson.JSON;

/**
 * @author huanglw
 *
 */
@Controller
@RequestMapping("/roleController")
public class RoleController {
	private static final Logger log = Logger.getLogger(IRController.class);
	
	@Resource
	private RoleI roleService;
	
	@RequestMapping("/getLevel")
	public void getLevel(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			List<Map> levelList = roleService.getLevel();
			String level = JSON.toJSONStringWithDateFormat(levelList, "yyyy-MM-dd HH:mm:ss");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":{\"result\":1,\"level\":" + level + "}})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("getLevel", e);
		}
	}
	
	@RequestMapping("/saveRole")
	public void saveRole(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			Role role = JSON.parseObject(json, Role.class);
			Map<String, Object> pMap = JSON.parseObject(json, Map.class);
			int isSuccess = roleService.saveRole(role, (String) pMap.get("oldRoleName"));
			resultJson = "{\"result\":" + isSuccess + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("saveRole", e);
		}
	}
	
	
	@RequestMapping("/getRoleCount")
	public void getRoleCount(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			Map params = JSON.parseObject(json, Map.class);
			int total = roleService.getRoleCount(params);
			resultJson = "{\"result\":1,\"total\":" + total + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("getRoleCount", e);
		}
	}
	
	
	@RequestMapping("/getRoles")
	public void getRoles(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			Map params = JSON.parseObject(json, Map.class);
			List<Map> roles = roleService.getRoles(params);
			String rolesStr = JSON.toJSONStringWithDateFormat(roles, "yyyy-MM-dd HH:mm:ss");
			resultJson = "{\"result\":1,\"roles\":" + rolesStr + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("getRoles", e);
		}
	}
	
	
	@RequestMapping("/deleteRole")
	public void deleteRole(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			Role role = JSON.parseObject(json, Role.class);
			int isSuccess = roleService.deleteRole(role);
			resultJson = "{\"result\":" + isSuccess + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("deleteRole", e);
		}
	}
	
	@RequestMapping("/getRole")
	public void getRole(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			Role role = JSON.parseObject(json, Role.class);
			Map<String, Object> roleMap = roleService.getRole(role);
			String roleStr = JSON.toJSONStringWithDateFormat(roleMap, "yyyy-MM-dd HH:mm:ss");
			resultJson = "{\"result\":1,\"role\":" + roleStr + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("getRole", e);
		}
	}
	
	@RequestMapping("/getClientCodeNotRels")
	public void getClientCodeNotRels(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			Map params = JSON.parseObject(json, Map.class);
			List<Map> clients = roleService.getClientCodeNotRels(params);
			String clientStr = JSON.toJSONStringWithDateFormat(clients, "yyyy-MM-dd HH:mm:ss");
			resultJson = "{\"result\":1,\"clients\":" + clientStr + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("getClientCodeNotRels", e);
		}
	}
	
	@RequestMapping("/getClientCodeRels")
	public void getClientCodeRels(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			Map params = JSON.parseObject(json, Map.class);
			List<Map> clients = roleService.getClientCodeRels(params);
			String clientStr = JSON.toJSONStringWithDateFormat(clients, "yyyy-MM-dd HH:mm:ss");
			resultJson = "{\"result\":1,\"clients\":" + clientStr + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("getClientCodeRels", e);
		}
	}
	
	@RequestMapping("/getRegions")
	public void getRegions(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			List<Map> regions = roleService.getRegions();
			String regionStr = JSON.toJSONStringWithDateFormat(regions, "yyyy-MM-dd HH:mm:ss");
			resultJson = "{\"result\":1,\"regions\":" + regionStr + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("getClientCodeRels", e);
		}
	}
	
	@RequestMapping("/getRoleByLevel")
	public void getRoleByLevel(String json, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null; 
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			Integer levelId = (Integer) paramMap.get("levelId");
			Map<String, Object> roleMap = roleService.getRoleByLevel(levelId);
			String roleStr = JSON.toJSONStringWithDateFormat(roleMap, "yyyy-MM-dd HH:mm:ss");
			resultJson = "{\"result\":1,\"role\":" + roleStr + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			log.info("getRole", e);
		}
	}
}
