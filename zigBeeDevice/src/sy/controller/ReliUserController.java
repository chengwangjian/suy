package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import sy.model.ReliUser;
import sy.service.ReliUserServiceI;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.flywind.app.data.DataGrid;
import com.flywind.app.data.Json;
import com.flywind.app.data.RoleInfo;

/**
 * 可靠性管理用户类
 * 
 * @author hlw
 * 
 */
@Controller
@RequestMapping("/reliUser")
public class ReliUserController {
	private static final Logger LOGGER = Logger.getLogger(ReliUserController.class);
	private ReliUserServiceI reliUserService;
	private int isMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
	
	public ReliUserServiceI getReliUserService() {
		return reliUserService;
	}

	@Autowired
	public void setReliUserService(ReliUserServiceI reliUserService) {
		this.reliUserService = reliUserService;
	}

	/**
	 * 新增管理员
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addReliUser")
	public Map<String, Object> addReliUser(String json,
			HttpServletRequest request, HttpServletResponse response) {
//		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMapParams = new HashMap<String, Object>();
		ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
		try {
			// 判断用户名是否存在
			if (reliUserService.isUserExist(reliUser.getUser_name())) {
				resultMapParams.put("result", -1);
				resultMapParams.put("msg", "该用户已存在");
				resultMap
						.put("response_id", request.getParameter("request_id"));
				resultMap.put("response_params", resultMapParams);
				return resultMap;
			}
			//解密
			String key = Httpproxy.getKey(reliUser.getUser_name());
        	String realPass = Httpproxy.urlCodec(reliUser.getPwd(), key);
        	reliUser.setPwd(realPass);
			reliUser.setRegist_time(new Date());
			reliUser.setLast_time(new Date());
			reliUser.setEnabled("1");
			reliUserService.addReliUser(reliUser);
		} catch (Exception e) {
			LOGGER.info("addReliUser", e);
			resultMapParams.put("result", 0);
			resultMapParams.put("msg", e.getMessage());
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			return resultMap;
		}
		resultMapParams.put("result", 1);
		resultMapParams.put("msg", "success");
		resultMap.put("response_id", request.getParameter("request_id"));
		resultMap.put("response_params", resultMapParams);
		return resultMap;
	}
	/**
	 * 查询角色
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryReliRole")
	public void queryReliRole(String json,
			HttpServletRequest request,String callback, HttpServletResponse response) {	
		DataGrid dg=new DataGrid();
		Json j = new Json();
		HashMap<String,Object>params=new HashMap<String, Object>();
	    Map<String,Object> requestparamsMap=JSON.parseObject(json, Map.class);
	   // JSON.parseObject(json, ReliRole.class);
		try {
		List<Map>list =	reliUserService.queryRole(requestparamsMap);
		params.put("userRole",list);
		j.setResponse_params(params);
		writeJson(j,callback,response);	
	} catch (Exception e) {
		// TODO: handle exception
		dg.setStatus(0);dg.setStatus_msg("fail");
		j.setResponse_params(dg);
	}	
		
	}
	@RequestMapping("/getRoleCount")
	public void getRoleCount(String json,
		HttpServletRequest request,String callback, HttpServletResponse response) {	
		DataGrid dg=new DataGrid();
		Json j = new Json();
		Map params=JSON.parseObject(json, Map.class);
		try {
		List<Map>list =	reliUserService.queryRoleCount(params);
		params.put("RoleCount",list.get(0).get("roleCount"));
		j.setResponse_params(params);
		writeJson(j,callback,response);	
	} catch (Exception e) {
		// TODO: handle exception
		dg.setStatus(0);dg.setStatus_msg("fail");
		j.setResponse_params(dg);
	}
		
	}
	
	
	@RequestMapping("/getRoleByUserid")
	public void getRoleByUserid(String json,
			HttpServletRequest request,String callback, HttpServletResponse response) {	
		DataGrid dg=new DataGrid();
		Json j = new Json();
		HashMap<String,Object>params=new HashMap<String, Object>();
	    Map<String,Object> requestparamsMap=JSON.parseObject(json, Map.class);
	   // JSON.parseObject(json, ReliRole.class);
		try {
		List<Map>list =	reliUserService.getRoleByUserid(requestparamsMap);
		params.put("userRoleList",list);
		j.setResponse_params(params);
		writeJson(j,callback,response);	
	} catch (Exception e) {
		// TODO: handle exception
		dg.setStatus(0);dg.setStatus_msg("fail");
		j.setResponse_params(dg);
	}	
	
	
	}
	
	
	
	/**
	 * 为用户添加角色
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addRoleToUser")
	public void addRoleToUser(String json,
	  HttpServletRequest request,String callback, HttpServletResponse response) {	
		DataGrid dg=new DataGrid();
		Json j = new Json();
		HashMap<String,Object>params=new HashMap<String, Object>();
		RoleInfo roleInfo=JSON.parseObject(json, RoleInfo.class);
		try {
		reliUserService.addRoleToUser(roleInfo);
		//params.put("userLevel",list);
		params.put("result","success");
		j.setResponse_params(params);
		
		
	} catch (Exception e) {
		// TODO: handle exception
		params.put("result","fail");
		j.setResponse_params(params);
	}
		writeJson(j,callback,response);	
	}
	
	
	/**
	 * 查询用户所有级别
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryReliUserLevel")
	public void queryReliUserLevel(String json,
			HttpServletRequest request,String callback, HttpServletResponse response) {	
		DataGrid dg=new DataGrid();
		Json j = new Json();
		HashMap<String,Object>params=new HashMap<String, Object>();
		
		try {
		List<Map>list =	reliUserService.queryReliUserLevel();
		params.put("userLevel",list);
		j.setResponse_params(params);	
	} catch (Exception e) {
		// TODO: handle exception
		dg.setStatus(0);dg.setStatus_msg("fail");
		j.setResponse_params(dg);
	}	
		writeJson(j,callback,response);	
		
	}
	public void writeJson(Object object,String callback,HttpServletResponse response) {
		try {
			String jstr=callback+"("+JSON.toJSONString(object)+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson",e);
		}
	}
	/**
	 * 查询管理员
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryReliUser")
	public void queryReliUser(String json,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("json=" + json);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		Map<String, Object> resultMapParams = new HashMap<String, Object>();		
		List<Map> uList = null;
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out=response.getWriter(); 
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String pageIndex = (String) paramMap.get("pageIndex");
			String pageSize = (String) paramMap.get("pageSize");
			if (StringUtils.isBlank(pageIndex))
				pageIndex = "1";
			if (StringUtils.isBlank(pageSize))
				pageSize = "30";
			uList = reliUserService.queryUser(null,
					Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
			String uJson= JSON.toJSONStringWithDateFormat(uList, "yyyy-MM-dd HH:mm:ss");
			String resultJson = "{\"result\":1,\"uList\":" + uJson + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("queryReliUser", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//			resultMapParams.put("result", 0);
//			resultMapParams.put("msg", e.getMessage());
//			resultMap.put("response_id", request.getParameter("request_id"));
//			resultMap.put("response_params", resultMapParams);
//			return resultMap;
		}
//		resultMapParams.put("result", 1);
//		resultMapParams.put("msg", "success");
//		resultMapParams.put("uList", uList);
//		resultMap.put("response_id", request.getParameter("request_id"));
//		resultMap.put("response_params", resultMapParams);
//		return resultMap;
	}

	/**
	 * 更新管理员
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/updateReliUser")
	public void updateReliUser(String json,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		LOGGER.info("json=" + json);
		ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
		try {
			//解密
			String key = Httpproxy.getKey(reliUser.getId() + "");
    		String newPass = Httpproxy.urlCodec(reliUser.getPwd(), key);
    		reliUser.setPwd(newPass);
			reliUser.setLast_time(new Date());
			reliUserService.updateReliUser(reliUser);
		} catch (Exception e) {
			LOGGER.info("updateReliUser", e);
//			resultMapParams.put("result", 0);
//			resultMapParams.put("msg", e.getMessage());
//			resultMap.put("response_id", request.getParameter("request_id"));
//			resultMap.put("response_params", resultMapParams);
			out.println(callback + "({\"request_id\": 1234, \"result\":0,\"msg\":"+e.getMessage()+",\"response_id\":"+request.getParameter("request_id")+"})");
//			return resultMap;
		}
//		resultMapParams.put("result", 1);
//		resultMapParams.put("msg", "success");
//		resultMap.put("response_id", request.getParameter("request_id"));
//		resultMap.put("response_params", resultMapParams);
		out.println(callback + "({\"request_id\": 1234, \"result\":1,\"msg\":\"success\",\"response_id\":"+request.getParameter("request_id")+"})");
//		return resultMap;
	}

	/**
	 * 判断密码是否正确
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/isPwdRight")
	public void isPwdRight(String json,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("callback");
		PrintWriter out = response.getWriter();
		LOGGER.info("json=" + json);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMapParams = new HashMap<String, Object>();
		ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
		boolean isPwdRight_ = false;
		try {
			String pass = reliUserService.getPass(reliUser);
			if(pass != null) {
				String key = Httpproxy.getKey(pass);
	    		String realPass = Httpproxy.urlCodec(reliUser.getPwd(), key);
	    		if(pass.equals(realPass))
	    			isPwdRight_ = true;
			}
//			isPwdRight_ = reliUserService.isPwdRight(reliUser.getId(),
//					reliUser.getPwd());
		} catch (Exception e) {
			LOGGER.info("isPwdRight", e);
			resultMapParams.put("result", 0);
			resultMapParams.put("msg", e.getMessage());
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			out.println(callback + "({\"request_id\": 1234, \"result\":0,\"msg\":"+e.getMessage()+"})");
		}
//		resultMapParams.put("result", 1);
//		resultMapParams.put("msg", "success");
//		resultMapParams.put("isPwdRight", "" + isPwdRight_);
//		resultMap.put("response_id", request.getParameter("request_id"));
//		resultMap.put("response_params", resultMapParams);
		out.println(callback + "({\"request_id\": 1234, \"result\":1,\"msg\":\"success\",\"isPwdRight\":\""+isPwdRight_+"\"})");
//		return resultMap;
	}

	/**
	 * 删除用户
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteReliUser")
	public Map<String, Object> deleteReliUser(String json,
			HttpServletRequest request, HttpServletResponse response) {
//		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMapParams = new HashMap<String, Object>();
		ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
		try {
			reliUserService.deleteUser(reliUser.getId());
		} catch (Exception e) {
			LOGGER.info("deleteReliUser", e);
			resultMapParams.put("result", 0);
			resultMapParams.put("msg", e.getMessage());
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			return resultMap;
		}
		resultMapParams.put("result", 1);
		resultMapParams.put("msg", "success");
		resultMap.put("response_id", request.getParameter("request_id"));
		resultMap.put("response_params", resultMapParams);
		return resultMap;
	}

	/**
	 * 登录
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String, Object> login(String json, HttpServletRequest request,
			HttpServletResponse response) {
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		response.setContentType("application/json");  
		LOGGER.info("json=" + json);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMapParams = new HashMap<String, Object>();
		ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
		HttpSession session = request.getSession();
		try {
			//根据用户名获取用户
			ReliUser ru = reliUserService.getReliUserByName(reliUser);
			ReliUser reliUser1 = null;
			if(ru != null) {
				String key = Httpproxy.getKey(ru.getPwd());
	    		String realPass = Httpproxy.urlCodec(reliUser.getPwd(), key);
				reliUser1 = reliUserService.checkUser(
						reliUser.getUser_name(), realPass);
				if(reliUser1 != null) {
					Map<String, Integer> pObject = reliUserService.getPrivilege(reliUser1.getId());
					session.setAttribute("reliUser", reliUser1);
					session.setAttribute("pObject", pObject);
				}
			}
			resultMapParams.put("result", 1);
			resultMapParams.put("msg", "success");
			resultMapParams.put("reliUser", reliUser1 == null? "null": reliUser1);
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			return resultMap;
		} catch (Exception e) {
			LOGGER.info("login", e);
			resultMapParams.put("result", 0);
			resultMapParams.put("msg", e.getMessage());
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			return resultMap;
		}		
	}
	@RequestMapping("/checkuser")
	public void checkuser(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
		ReliUser reliUser2 = reliUserService.checkUseridAndName(reliUser.getId());
		String resultJson= JSON.toJSONStringWithDateFormat(reliUser2, "yyyy-MM-dd HH:mm:ss");
		out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");  
	}
	
	
	/**
	 * 判断是否登录
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/isLoginuserById")
	@ResponseBody
	public void isLoginuserById(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {		
		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
		PrintWriter out=response.getWriter(); 
		String callback = request.getParameter("callback");
		Map<String, Object> pMap = new HashMap<String, Object>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
        try { 	
        	Map<String, Object> params = JSON.parseObject(json, Map.class);
        	ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
        	tmpMap.put("id", reliUser.getId());
        	pMap.put("json",JSON.toJSONStringWithDateFormat(tmpMap, "yyyy-MM-dd HH:mm:ss"));
        	String serverIp = PropertiesUtil.getProperty("cloudAddress");
        	String serverPort = PropertiesUtil.getProperty("cloudPort");
        	String serverHost = request.getServerName();
        	LOGGER.info("cloudAddress:" + serverIp + ",cloudPort:" + serverPort + ",localHost:" + serverHost);			
			if(!serverIp.equals(serverHost) 
					&& !serverIp.equals("localhost") 
					&& !serverIp.equals("127.0.0.1")
					&& !serverHost.equals("localhost") 
					&& !serverHost.equals("127.0.0.1")) {
	        	StringBuilder upUrl = new StringBuilder("http://");
	        	upUrl.append(serverIp).append(":").append(serverPort).append("/zigBeeDevice/reliUser/checkuser.do");
	        	String str = TestHttpclient.postUrlWithParams(upUrl.toString(), pMap, "utf-8");
	        	Map<String, Object> map = JSON.parseObject(str,Map.class);
	        	Map<String, Object> paMap = (Map<String, Object>)map.get("response_params");
	        	ReliUser reliUser2 = JSON.parseObject(paMap.toString(), ReliUser.class);
	//        	ReliUser reliUser2 = reliUserService.checkUseridAndName(reliUser.getId());
	        	String resultJson1=null ;
	        	if(reliUser2!=null){
	        		HttpSession session = request.getSession();
	        		session.setAttribute("reliUser", reliUser2);
	        		session.setAttribute("pObject", params.get("pObject"));
	        		resultJson1 = "{\"result\":1,\"pObject\":" + JSON.toJSONStringWithDateFormat(request.getSession().getAttribute("pObject"), "yyyy-MM-dd HH:mm:ss",SerializerFeature.WriteMapNullValue)+"}";//1 已经存在
	        	}
	        	else {
	        		resultJson1 = "{\"result\":-1}";//-1 不存在
				}
	        	out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson1 + "})");
			} 
			else { 
				String resultJson1 = "{\"result\":0}";//0
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson1 + "})");
			}
		} catch (Exception e) {
			LOGGER.info("isLoginuserById", e);
			String resultJson1 = "{\"result\":0}";//0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson1 + "})");
		}
	}
	
	/**
	 * 判断是否登录
	 * @param json
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/isLoginuser")
	@ResponseBody
	public void isLoginuser(String json,HttpServletRequest request, HttpServletResponse response) {		
//		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		Map<String, Object> resultMapParams = new HashMap<String, Object>();	
		PrintWriter out = null;
		String resultJson = null;
    	String callback = request.getParameter("callback");
        try { 
        	out = response.getWriter();
        	ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
        	ReliUser t = (ReliUser)request.getSession().getAttribute("reliUser");
        	if (t == null 
        		|| t.getId() == 0 
        		|| reliUser.getId().longValue() != t.getId().longValue()) { // 用户没登录
//        		resultMapParams.put("result", -1);
//    			resultMapParams.put("msg", "用户未登录");
//    			resultMap.put("response_id", request.getParameter("request_id"));
//    			resultMap.put("response_params", resultMapParams);
//    			return resultMap;
        		resultJson = "{\"result\":-1}";
        		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
        		return;
        	}
//        	if (reliUser.getId().longValue() != t.getId().longValue()) { // 用户没登录
//        		resultMapParams.put("result", -1);
//    			resultMapParams.put("msg", "用户未登录");
//    			resultMap.put("response_id", request.getParameter("request_id"));
//    			resultMap.put("response_params", resultMapParams);
//    			return resultMap;
//        	} 
//        	resultMapParams.put("result", 1);
//			resultMapParams.put("msg", "用户已登录");
//			resultMapParams.put("reliUser", t);
//			resultMapParams.put("pList", request.getSession().getAttribute("pList"));
//			resultMap.put("response_id", request.getParameter("request_id"));
//			resultMap.put("response_params", resultMapParams);
//			return resultMap;
        	resultJson = "{\"result\":1,\"reliUser\":" + JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue) 
        			+ ",\"pObject\":" + JSON.toJSONStringWithDateFormat(request.getSession().getAttribute("pObject"), "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue) 
        			+ "}";
        	out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("isLoginuser", e);
			resultJson = "{\"result\":-1}";
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//			resultMapParams.put("result", -1);
//			resultMapParams.put("msg", e.getMessage());
//			resultMap.put("response_id", request.getParameter("request_id"));
//			resultMap.put("response_params", resultMapParams);
//			return resultMap;
		}   
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String responseId = request.getParameter("request_id");
		request.getSession().invalidate();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMapParams = new HashMap<String, Object>();
		resultMapParams.put("result", 1);
		resultMapParams.put("msg", "成功退出");
		resultMap.put("response_id", responseId);
		resultMap.put("response_params", resultMapParams);
		out.println(callback + "({\"request_id\": 1234, \"result\":1,\"msg\":\"成功退出\"})");
	}
	
	@RequestMapping("/getUserCount")
	@ResponseBody
	public void getUserCount(String json,HttpServletRequest request, HttpServletResponse response) {		
//		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		Map<String, Object> resultMapParams = new HashMap<String, Object>();
		String resultJson = "{\"result\":1}";//成功1 失败0
		PrintWriter out = null;
        try { 
//        	ReliUser reliUser = JSON.parseObject(json, ReliUser.class);
        	out = response.getWriter();
        	int total = reliUserService.getUserCount(null); 
        	resultJson = "{\"result\":1,\"total\":" + total + "}";	
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//        	resultMapParams.put("result", 1);
//			resultMapParams.put("msg", "获取用户总数成功");
//			resultMapParams.put("total", total);
//			resultMap.put("response_id", request.getParameter("request_id"));
//			resultMap.put("response_params", resultMapParams);
//			return resultMap;
		} catch (Exception e) {
			LOGGER.info("getUserCount", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			resultMapParams.put("result", -1);
//			resultMapParams.put("msg", e.getMessage());
//			resultMap.put("response_id", request.getParameter("request_id"));
//			resultMap.put("response_params", resultMapParams);
//			return resultMap;
		}   
	}
	
	@RequestMapping("/function")
	public void function(String json,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("json=" + json);
		List<Map> uList = null;
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try {
			//xmpp服务器从云端管理中心服务器中获取权限
        	if(isMain == 0) {
    			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
    			String cloudPort = PropertiesUtil.getProperty("cloudPort");
    			String serverHost = request.getServerName();
    			LOGGER.info("cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
    			if(!cloudAddress.equals(serverHost) 
    				&& !cloudAddress.equals("localhost") 
    				&& !cloudAddress.equals("127.0.0.1")
    				&& !serverHost.equals("localhost") 
    				&& !serverHost.equals("127.0.0.1")) {
    				response.setContentType("text/html;charset=utf-8");
    				String rsStr = "";
    				String callUrl = "http://" + cloudAddress + ":" + cloudPort + request.getRequestURI();
    				Map<String, String[]> rMap = request.getParameterMap();
    				if(rMap != null) {
    					Map<String, Object> pMap = new HashMap<String, Object>();
    					Iterator<String> it=rMap.keySet().iterator();   
    					while(it.hasNext()){ 
    						String key=it.next();  
    						//post方法去除json参数
    						String value = (rMap.get(key))[0].toString();
    						pMap.put(key, value);
    					}
    					rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
    					response.getWriter().write(rsStr);
    					response.getWriter().flush();
    				}
    			}
    			return;
    		}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
			String userid = (String)paramMap.get("userid");
			uList = reliUserService.function(userid);
			String uJson= JSON.toJSONStringWithDateFormat(uList, "yyyy-MM-dd HH:mm:ss");
			String resultJson = "{\"result\":1,\"uList\":" + uJson + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (IOException e) {
			LOGGER.info("function", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/functionAble")
	public void functionAble(String json,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("json=" + json);
		List<Map> uList = null;
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
			String userid = (String)paramMap.get("userid");
			uList = reliUserService.functionAble(userid);
			String uJson= JSON.toJSONStringWithDateFormat(uList, "yyyy-MM-dd HH:mm:ss");
			String resultJson = "{\"result\":1,\"uList\":" + uJson + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (IOException e) {
			LOGGER.info("function", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/checkUserid")
	public void checkUserid(String json,HttpServletRequest request, HttpServletResponse response){}
	
}
