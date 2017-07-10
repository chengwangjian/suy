package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import sy.model.FarmUser;
import sy.model.RespParams;
import sy.service.FarmUserServiceI;
import sy.util.HouseieeeListener;
import sy.util.Httpproxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/farmUser")
public class FarmUserController {
	private static final Logger LOGGER = Logger.getLogger(ApponlineinfoController.class);
	
	private FarmUserServiceI fUService;

	public FarmUserServiceI getfUService() {
		return fUService;
	}
	@Autowired
	public void setfUService(FarmUserServiceI fUService) {
		this.fUService = fUService;
	}
	
	@RequestMapping("/LoginUserInfo")
	public void appLogin(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		int status = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String username = request.getParameter("name");
			String password = request.getParameter("password");
			if(StringUtils.isBlank(username)){
				params.put("status", 4);
				params.put("status_msg", "user check name error");
				resp.setResponse_params(params);
				out.println(callback+"("+JSON.toJSONString(resp)+")");
				return;
			}
			if(StringUtils.isBlank(password)){
				params.put("status", 5);
				params.put("status_msg", "user check password error");
				resp.setResponse_params(params);
				out.println(callback+"("+JSON.toJSONString(resp)+")");
				return;
			}
			Map<String,Object> user= fUService.getFarmUser(username);
			if(user==null){
				status = 6;
				params.put("status_msg", "no user");
			}else{
				String key = Httpproxy.getKey(username);
				String pass = Httpproxy.urlCodec(password, key);
				if(pass.equalsIgnoreCase((String) user.get("pwd"))){
					params.put("user_type", user.get("roleId"));
					params.put("mode", user.get("mode"));
					params.put("status_msg", "success");
				}else{
					status = 5;
					params.put("status_msg", "user check password error");
				}
			}
		}catch(Exception e){
			LOGGER.error("App Login Error",e);
			status = 1;
			params.put("status_msg", "user xml load error");
		}
		params.put("status", status);
		resp.setRequest_id(callback);
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	/**
	 * 新增或修改普通用户(非老板账号)
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/saveUser")
	public void saveUser(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		int status = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String user_name = request.getParameter("user_name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String userType = request.getParameter("user_type");
			String areas = request.getParameter("areas");
			List<String> areaList = null;
			if(StringUtils.isNotBlank(areas)) {
				areaList = JSON.parseArray(areas, String.class);
			}
			
			if(StringUtils.isBlank(username)||StringUtils.isBlank(password)||StringUtils.isBlank(userType)){
				status = 6;
				params.put("status_msg", "no user");
			}
			else if("admin".equalsIgnoreCase(username)){
				status = -2;
				params.put("status_msg", "can not add user by username as 'admin'");
			}
			else{
				Integer type = Integer.parseInt(userType);
				List<Map> noticeNList = new ArrayList<Map>();
				List<Map> noticeRMList = new ArrayList<Map>();
				int s= fUService.saveUser(user_name,username, password, type, areaList, noticeNList, noticeRMList);
				if(s>0){
					fUService.noticeUpdate(noticeRMList);
					fUService.noticeUpdate(noticeNList);
					status = 0;//保存成功
					params.put("status_msg", "success");
				}
				else if(s==-1){
					status = -1;//权限不足
					params.put("status_msg", "Permission denied");
				}
				else if(s==-4){
					status = -4;//区域存在没权限
					params.put("status_msg", "Not all areas permit");
				}
				else if(s==-33){
					status = -33;//用户名已经存在
					params.put("status_msg", "User name already exists");
				}
				else{
					status = 9;//保存失败
					params.put("status_msg", "save user error");
				}
			}
		}catch(Exception e){
			if(e!=null){
				if(StringUtils.contains(e.getMessage(), "for key 'farmuser_name_index'")){
					LOGGER.error("the user has existed",e);
					status = -4;
					params.put("status_msg", "the user has existed");
				}else if(StringUtils.contains(e.getMessage(), "notice 203 exception")){
					LOGGER.error("notice 203/206 exception",e);
					status = -5;
					params.put("status_msg", "notice 203/206 exception");
				}else if(StringUtils.contains(e.getMessage(), "Permission denied")){
					status = -1;//权限不足
					params.put("status_msg", "Permission denied");
				}else{
					LOGGER.error("Save User Error",e);
					status = 9;//保存错误
					params.put("status_msg", "save user faild");
				}
			}
			
		}
		params.put("status", status);
		resp.setRequest_id(callback);
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	
	@RequestMapping("/deleteUser")
	public void deleteUser(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		int status = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String user_name = request.getParameter("user_name");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String userType = request.getParameter("user_type");
			Map<String,Object> user = fUService.getFarmUser(user_name);
			//首先需判户断用户是否有权限
			if(user!=null){
				Object level = user.get("roleId");
				if(level!=null){
					int lev = (int) level;
					if(lev!=1){
						status = -1;//权限不足
						params.put("status_msg", "Permission denied");
					}else{
						if(StringUtils.isBlank(username)){
							status = 6;
							params.put("status_msg", "the user does not exists");
						}else{
							int type = 0;
							if(StringUtils.isNotBlank(userType)){
								type = Integer.parseInt(userType);
							}else{
								type = 0;
							}
							if(StringUtils.isNotBlank(password)){
								String key = Httpproxy.getKey(user_name);
								password = Httpproxy.urlCodec(password, key);
							}else{
								password = null;
							}
							int s= fUService.deleteUser(username, password, type);
							if(s>0){
								status = 0;
								params.put("status_msg", "success");
							}else if(s==-1){
								status = -1;//不能删除管理员
								params.put("status_msg", "Permission denied");
							}else{
								status = 6;
								params.put("status_msg", "the user does not exists");
							}
						}
					}
				}else{
					status = -1;//权限不足
					params.put("status_msg", "Permission denied");
				}
			}else{
				status = 6;
				params.put("status_msg", "the user does not exists");
			}
			
		}catch(Exception e){
			LOGGER.error("Delete User Error",e);
			status = 8;
			params.put("status_msg", "delete user faild");
		}
		params.put("status", status);
		resp.setRequest_id(callback);
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	
	/**
	 * 推送设备
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getUserInfo")
	public void getUserInfo(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		try{ 
			String houseIeee = request.getParameter("houseIeee");
			String mode = request.getParameter("mode");
			int modeInt = 0;
			if(StringUtils.isNotBlank(mode)) {
				modeInt = Integer.parseInt(mode);
			}
			List<Map> userList = fUService.updateUserInfo(houseIeee, modeInt);
			resultJson = JSON.toJSONStringWithDateFormat(userList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			out.println(resultJson);	
		}catch(Exception e){
			LOGGER.error("getUserInfo", e);
			resultJson = "{\"result\":0,\"msg\":\"get user info exception\"}";
			out.println(resultJson);
		}
	}
	
	/**
	 * 根据登录用户名获取用户信息
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getUserServer")
	public void getUserServer(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		try{
			//{"status":1,"data":{"serverIp":"218.104.133.242","id":"245","user_name":"caixy"}}
			Map param = JSON.parseObject(json, Map.class); 
			List<Map> userList = fUService.getUserServer(param);
			//String ll = (String)JSON.toJSONString(userList);			
			if(userList.size() != 0) {
				String serverIp = (String)userList.get(0).get("serverIp")==null?"":(String)userList.get(0).get("serverIp");
				BigInteger id = (BigInteger)userList.get(0).get("id");
				String user_name = (String)userList.get(0).get("user_name")==null?"":(String)userList.get(0).get("user_name");
				String List = "{\"serverIp\":\""+serverIp+"\",\"id\":\""+id+"\",\"user_name\":\""+user_name+"\"}";
				String userStr = 
				resultJson = "{\"request_id\":1234,\"response_params\":{\"status\":1,\"data\":" + List + "}}";
				out.print(resultJson);
			}else {
				resultJson = "{\"request_id\":1234,\"response_params\":{\"status\":0,\"msg\":\"get user info exception\"}}";
				out.println(resultJson);
			}			
		}catch(Exception e){
			LOGGER.error("getUserServer", e);
			resultJson = "{\"request_id\":1234,\"response_params\":{\"status\":0,\"msg\":\"get user info exception\"}}";
			out.println(resultJson);
		}
	}
	
	
	@RequestMapping("/getpassword")
	public void getpassword(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int status = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String username = request.getParameter("username");
			if(StringUtils.isBlank(username)){
				params.put("status", 4);
				params.put("status_msg", "user check name error");
				resp.setResponse_params(params);
				out.println(callback+"("+JSON.toJSONString(resp)+")");
				return;
			}
			Map<String,Object> user= fUService.getFarmUser(username);
			if(user==null){
				status = 6;
				params.put("status_msg", "no user");
			}else{
				status = 0;
				params.put("status_msg",user.get("pwd"));
			}
		}catch(Exception e){
			LOGGER.error("App Login Error",e);
			status = 1;
			params.put("status_msg", "user xml load error");
		}
		params.put("status", status);
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(JSON.toJSONString(resp));
	}
	/**
	 * 获取所有农场老板账号
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getBossUsers")
	public void getBossUsers(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String searchParam = request.getParameter("search");
			String startRow = request.getParameter("startRow");
			String pageSize = request.getParameter("pageSize");
			List<Map> list = fUService.getBossUsers(startRow,pageSize,searchParam);
			if(list!=null){
				result = 0;
				params.put("userList", list);
			}else{
				result = 2;
				params.put("msg", "no boss user");
			}
		}catch(Exception e){
			LOGGER.error("Get Users Error",e);
			result = 1;
			params.put("msg", "get users error");
		}
		params.put("result", result);
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	/**
	 * 获取所有农场老板账号数量
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getUsersCount")
	public void getUsersCount(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String searchParam = request.getParameter("search");
			int totle = fUService.getUsersCount(searchParam);
			result = 0;
			params.put("count", totle);
		}catch(Exception e){
			LOGGER.error("Get Users Error",e);
			result = 1;
			params.put("msg", "get users error");
		}
		params.put("result", result);
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/resetPwd")
	public void resetPwd(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			long id = 0;
			String strId = request.getParameter("id");
			String newPwd = request.getParameter("newPwd");
			String serverIp = request.getParameter("re_serverIp");
			if(StringUtils.isNotBlank(strId)){
				id = Long.parseLong(strId);
				result = fUService.resetPwd(id,newPwd,serverIp);
			}else
				result = 0; 
		}catch(Exception e){
			LOGGER.error("reset pwd error",e);
			result = -1;
			params.put("msg", "reset pwd faild");
		}
		params.put("result", result); //-1 ：系统报错 ；1：成功；0：用户不存在； 2：旧密码不正确
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	
	/**
	 * 新增管理员用户(老板账号)
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/saveBossUser")
	public void saveBossUser(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		LOGGER.info("新增管理员用户json信息---:"+json);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			FarmUser user = JSON.parseObject(json, FarmUser.class);
			if("admin".equalsIgnoreCase(user.getUsername())){
				result = -2;//添加的用户用户名不能为admin
			}else{
				result = fUService.addBossUser(user);
			}
		}catch(Exception e){
			LOGGER.error("reset pwd error",e);
			result = -1;
			params.put("msg", "save boss user faild");
		}
		params.put("result", result); //-1 ：系统报错 ；1：成功；0：失败
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	
	/**
	 * 验证用户名唯一性
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/checkNameUnique")
	public void checkNameUnique(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String username = request.getParameter("username");
			result = fUService.checkNameUnique(username);
		}catch(Exception e){
			LOGGER.error("reset pwd error",e);
			result = -1;
			params.put("msg", "check name unique error");
		}
		params.put("result", result); //-1 ：系统报错 ；1：用户名已存在；0：用户名唯一
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	
	/**
	 * 删除老板用户
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/delUserById")
	public void delUserById(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			long id = 0;
			String strId = request.getParameter("id");
			if(StringUtils.isNotBlank(strId)){
				id = Long.parseLong(strId);
				result = fUService.delUserById(id);
			}else
				result = 0; 
		}catch(Exception e){
			LOGGER.error("reset pwd error",e);
			result = -1;
			params.put("msg", "delete user faild");
		}
		params.put("result", result); //-1 ：系统报错 ；1：成功；0：失败
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	
	/**
	 * 根据用户名获取用户
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getFarmUser")
	public void getFarmUser(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String userName = request.getParameter("userName");
			FarmUser user = fUService.getFUser(userName);
			result = 0;
			if(user!=null){
				result = 0;
				params.put("user", user);
			}else{
				result = 2;
				params.put("msg", "no boss user");
			}
		}catch(Exception e){
			LOGGER.error("Get Users Error",e);
			result = 1;
			params.put("msg", "get users error");
		}
		params.put("result", result);
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(JSON.toJSONString(resp));
	}
	
	@RequestMapping("/getFarmUsers")
	public void getFarmUsers(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		int result = 1;
		try{
			List<Map> list = fUService.getFarmUsers();
			params.put("userList", list);
		}catch(Exception e){
			LOGGER.error("Get Users Error",e);
			result = 0;
			params.put("msg", "get users error");
		}
		params.put("result", result);
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(JSON.toJSONString(resp));
	}
	
	/**
	 * 获取APP管理员下的用户
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/getAppUserInfo")
	public void getAppUserInfo(String json,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String resultJson = "";
		try{
			String userName = request.getParameter("user_name");
			List<Map> userList = fUService.getAppUserInfo(userName);
			String userStr = JSON.toJSONStringWithDateFormat(userList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			resultJson = "{\"request_id\":1234,\"response_params\":" + userStr + "}";
			out.println(resultJson);	
		}catch(Exception e){
			LOGGER.error("getAppUserInfo", e);
			resultJson = "{\"request_id\":1234,\"response_params\":{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}}";
			out.println(resultJson);
		}
	}
	
	/**
	 * APP修改老板账户密码
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/modifyAppPassword")
	public void modifyAppPassword(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = 0;
		RespParams resp = new RespParams();
		Map<String,Object> params = new HashMap<>();
		String callback = request.getParameter("callback");
		if(callback==null){
			callback = "";
		}
		try{
			String userName = request.getParameter("user_name");
			String password = request.getParameter("password");
			fUService.modifyAppPassword(userName, password);
		}catch(Exception e){
			LOGGER.error("modifyAppPassword error",e);
			result = -1;
			params.put("msg", "modifyAppPassword faild");
		}
		params.put("result", result); //-1 ：系统报错 ；1：成功；0：用户不存在； 2：旧密码不正确
		resp.setRequest_id("1234");
		resp.setResponse_params(params);
		out.println(callback+"("+JSON.toJSONString(resp)+")");
	}
	
	
	@RequestMapping("/getCurrentUser")
	@ResponseBody
	public Object getCurrentUser() {
		return HouseieeeListener.farmUserMap;
	}
	
}
