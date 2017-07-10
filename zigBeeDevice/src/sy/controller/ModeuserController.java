package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Modeuser;
import sy.model.Verifycode;
import sy.service.ModeuserServiceI;
import sy.util.Common;
import sy.util.Httpproxy;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/modeuserController")
public class ModeuserController {

	private ModeuserServiceI modeuserService;
	
	private static final Logger LOGGER = Logger.getLogger(ModeuserController.class);  

	public ModeuserServiceI getModeuserService() {
		return modeuserService;
	}

	@Autowired
	public void setModeuserService(ModeuserServiceI modeuserService) {
		this.modeuserService = modeuserService;
	}
	
	/**
	 * 获取验证码
	 * @author: zhuangxd
	 * 时间：2013-11-19 上午11:09:44
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/SendVerifyCodeToUser")
	public void SendVerifyCodeToUser(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*
		 * 发送验证码给客户，参数UserName可以是手机号码或者邮箱地址，程序要自动判断输入的
		 * 是手机号码或者是邮箱地址发送一个随机验证码给客户（目前客户仅仅支持邮箱发送）。实际执行的逻辑是在表VerifyCode
		 * 里面增加一条记录，用来给验证后面的用户注册是否有效，创建之前要先删除时间超过限制的记录。
			目前暂时定为验证码的有效时间为1分钟。
		 */
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8");  
		// & URL 中指定的参数间的分隔符 %26  r&d170@netvox.com.cn改成r%26d170@netvox.com.cn

        try {
        	json = request.getParameter("json");
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class);
        	String language = request.getParameter("language");
        	modeuser.setLanguage(language);
        	Modeuser t = modeuserService.getModeuser(modeuser);
        	if (t != null) { // 用户已注册
        		String resultJson = "{\"result\":-1}";//成功1 失败0
    			String callback = request.getParameter("callback");
    			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	// 生成6位验证码
    		String sRand = Common.createRandom(true, 6);
    		modeuser.setVerifyCode(sRand);
    		// 发送邮件
//    		modeuserService.sendFileMail(modeuser);    		
    		// 发送短信
    		String resultJson = "{\"result\":0}";//成功1 失败0
    		String msg = "尊敬的用户,你的本次验证码为:" + sRand;
    		String rMsg = SendSMS.getSingleSendSMS().sendSMS(modeuser.getPhoneNumber(), msg);
    		LOGGER.info("phonenumber:" + modeuser.getPhoneNumber() + "," + rMsg);
    		if(msg.equals(rMsg)) {
	    		// 删除用户以前的所有验证码
	    		modeuserService.deleteVerifyCode(modeuser);
	    		Verifycode verifycode = new Verifycode();
	    		verifycode.setUserName(modeuser.getPhoneNumber());
	    		verifycode.setVerifyCode(modeuser.getVerifyCode());
	    		// 保存验证码
	    		modeuserService.saveVerifycode(verifycode);
	    		resultJson = "{\"result\":1}";//成功1 失败0
    		}
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("SendVerifyCodeToUser", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}
	}
	
	/**
	 * 找回密码（将密码发送至注册邮箱）
	 * @author: zhuangxd
	 * 时间：2013-11-29 下午4:41:53
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findPwd")
	public void findPwd(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
//		System.out.println("json1==" + request.getParameter("json"));
		response.setContentType("text/html;charset=utf-8");  
//		System.out.println("json==" + json);
				
		/*List<Modeuser> modeuserList = JSON.parseArray(json,Modeuser.class); 
        for (Modeuser modeuser : modeuserList) { 
        	modeuserService.save(modeuser);
        } */
		
		// & URL 中指定的参数间的分隔符 %26  r&d170@netvox.com.cn改成r%26d170@netvox.com.cn
		String resultJson = "{\"result\":0}";//成功1 失败0
        try {
        	json = request.getParameter("json");
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class);
        	Modeuser t = modeuserService.getModeuser(modeuser);
        	String language = request.getParameter("language");
        	if (t == null) { // 用户不存在
        		resultJson = "{\"result\":-1}"; //成功1 失败0
    			String callback = request.getParameter("callback");
    			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	t.setLanguage(language = language == null? "2" : language);
    		// 发送密码
    		String connAdd = t.getUserName();
    		if(connAdd.indexOf("@") == -1) {
    			String msg = "";
    			if(Integer.parseInt(language) == 2) {
    				msg = "你的模式编辑器登录密码为" + t.getPwd();
    			} 
    			else {
    				msg = "your model editor password is " + t.getPwd();
    			}
    			String msg_ = SendSMS.getSingleSendSMS().sendSMS(connAdd, msg);
    			if(msg_.equals(msg))
    				resultJson = "{\"result\":1}";
    			else
    				resultJson = "{\"result\":0}";
    		} 
    		else {
    			modeuserService.sendPwdMail(t);    		
    			resultJson = "{\"result\":1}";//成功1 失败0
    		}
    		String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("findPwd", e);
			resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}
	}

	/**
	 * 用户注册
	 * @author: zhuangxd
	 * 时间：2013-11-19 上午11:00:32
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/Registuser")
	public void Registuser(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*
		 * 需要验证UserName是否已经被注册过了，验证码是否有效。验证有效之后删除验证码表（VerifyCode）里面的相应记录
		 */
		PrintWriter out=response.getWriter();  
//		System.out.println("json==" + json);
		
		/*List<Modeuser> modeuserList = JSON.parseArray(json,Modeuser.class); 
        for (Modeuser modeuser : modeuserList) { 
        	modeuserService.save(modeuser);
        } */
		
        try {
        	json = request.getParameter("json");
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class);
        	Modeuser t2 = modeuserService.getModeuser(modeuser);
        	if (t2 != null) { // 用户已注册
        		String resultJson = "{\"result\":-1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	Modeuser t = modeuserService.get(modeuser);
        	/*if (StringUtils.isBlank(modeuser.getHouseIeee()) || modeuser.getHouseIeee().length() < 16 ) {
    			String resultJson = "{\"result\":0}";//成功1 失败0
        		    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
    		}*/
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t == null) {
        		/*if (StringUtils.isBlank(modeuser.getHouseIeee()) || modeuser.getHouseIeee().length() != 16) {
            		return;
            	}
        		if (StringUtils.isBlank(modeuser.getSecretKey())) {
        			modeuser.setSecretKey(modeuser.getHouseIeee().substring(6));
        		}
        		if (modeuser.getVendorCode() == null) {
        			modeuser.setVendorCode("NETVOX");
        		}
        		if (modeuser.getEncodemethod() == null) {
        			modeuser.setEncodemethod("0");
        		}*/
        		String key = Httpproxy.getKey(modeuser.getUserName());
            	String realPass = Httpproxy.urlCodec(modeuser.getPwd(), key);
            	modeuser.setPwd(realPass);
        		modeuserService.save(modeuser);
        		// 删除验证码
        		modeuserService.deleteVerifyCode(modeuser);
        		resultJson = "{\"result\":1}";//成功1 失败0
        	} else {
        		/*modeuser.setId(t.getId());
            	modeuserService.update(modeuser);*/
        		// 验证码错误
        		resultJson = "{\"result\":-2}";//成功1 失败0
    			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
    			return;
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("Registuser", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveModeuser";
	}
	
	/**
	 * 用户登录
	 * @author: zhuangxd
	 * 时间：2013-11-19 上午11:00:14
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/Loginuser")
	public void Loginuser(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	json = request.getParameter("json");
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class); 
        	Modeuser t2 = modeuserService.getModeuser(modeuser);
        	if (t2 == null) { // 登录名错误
    			String resultJson = "{\"result\":-1,\"userName\":\"" + "\"" + ",\"id\":0" + "}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
//    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
    			return;
        	}
        	//解密密码
        	String key = Httpproxy.getKey(t2.getPwd());
        	String realPwd = Httpproxy.urlCodec(modeuser.getPwd(), key);
        	modeuser.setPwd(realPwd);
        	//查询数据库
        	Modeuser t = modeuserService.find(modeuser);
        	if (t == null) {
        		// 密码错误
    			String resultJson = "{\"result\":-2,\"userName\":\"" + "\"" + ",\"id\":0" + "}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
//    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
    			return;
        	}
        	// 用户登录session
        	request.getSession().setAttribute("modeuser", t);
        	//获取权限
        	List<Map> pList = modeuserService.getPrivilege(t.getId());
        	request.getSession().setAttribute("pList", pList);
//        	List<Modeuser> t = modeuserService.findList(modeuser);
        	String resultJson = "{\"result\":1,\"userName\":\"" + t.getUserName() + "\",\"id\":"+ t.getId() + ",\"level\":" + t.getLevelId() + "}";//成功1 失败0;
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//        	out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("Loginuser", e);
			String resultJson = "{\"result\":0,\"userName\":\"" + "\"" + ",\"id\":0" + "}";//成功1 失败0
//			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}   
        
        // 多表关联
      /*  try {
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class); 
//        	Modeuser t = modeuserService.find(modeuser);
        	List<Modeuser> t = modeuserService.findList(modeuser);
        	List<Object[]> s = modeuserService.findListSql(modeuser);
        	for (Object[] objects : s) {
        		Modeuser modeuser2 = (Modeuser)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modeuser modeuser2 = (Modeuser)objects[0];
        		DevicewarnhistoryModeuseridYear devicewarnhistoryModeuseridYear = (DevicewarnhistoryModeuseridYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}
	
	/**
	 * 判断用户是否登录
	 * @author: zhuangxd
	 * 时间：2014-1-6 下午8:57:02
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/isLoginuser")
	public void isLoginuser(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	json = request.getParameter("json");
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class); 
        	Modeuser t = (Modeuser)request.getSession().getAttribute("modeuser");
        	if (t == null || t.getId() == 0) { // 用户没登录
        		String resultJson = "{\"result\":-1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
//        		out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
        		return;
        	}
        	if (modeuser.getId() != t.getId()) { // 用户没登录
        		String resultJson = "{\"result\":-1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
//        		out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
        		return;
        	}
        	String mJson = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	//获取权限
        	List<Map> pList = (List<Map>) request.getSession().getAttribute("pList");
        	String pJson = JSON.toJSONStringWithDateFormat(pList, "yyyy-MM-dd HH:mm:ss");
        	String resultJson = "{\"result\":1,\"modeuser\":" + mJson + ",\"pList\":" + pJson + "}";//成功1 失败0;
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//        	out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
        } catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
        	LOGGER.info("isLoginuser", e);
			String resultJson = "{\"result\":-1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}   
	}
	
	@RequestMapping("/add")
	public void saveModeuser(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*modeuserService.test();
		return "showModeuser";*/
		
//		String json = request.getParameter("json");
	
		/*resp.setContentType("text/html;charset=gb2312");  
		PrintWriter out=resp.getWriter();  

		out.println(json);  
		out.flush();  */

//		PropertyConfigurator.configure("D:/log4j.properties");        
		/*Map<String,Object> map1 = (Map<String,Object>)JSON.parse(json);
		for (String key : map1.keySet()) { 
			logger.info(key+":"+map1.get(key)); 
		}*/
		
		PrintWriter out=response.getWriter();  
//		System.out.println("json==" + json);
		
		/*List<Modeuser> modeuserList = JSON.parseArray(json,Modeuser.class); 
        for (Modeuser modeuser : modeuserList) { 
        	modeuserService.save(modeuser);
        } */
		
        try {
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class);
        	Modeuser t = modeuserService.get(modeuser);
        	/*if (StringUtils.isBlank(modeuser.getHouseIeee()) || modeuser.getHouseIeee().length() < 16 ) {
    			String resultJson = "{\"result\":0}";//成功1 失败0
        		    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
    		}*/
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t == null) {
        		/*if (StringUtils.isBlank(modeuser.getHouseIeee()) || modeuser.getHouseIeee().length() != 16) {
            		return;
            	}*/
        		/*if (StringUtils.isBlank(modeuser.getSecretKey())) {
        			modeuser.setSecretKey(modeuser.getHouseIeee().substring(6));
        		}
        		if (modeuser.getVendorCode() == null) {
        			modeuser.setVendorCode("NETVOX");
        		}
        		if (modeuser.getEncodemethod() == null) {
        			modeuser.setEncodemethod("0");
        		}*/
        		modeuserService.save(modeuser);
        		resultJson = "{\"result\":1}";//成功1 失败0
        		/*modeuserService.addDeviceAttributeHistoryTable(modeuser.getHouseIeee());
        		modeuserService.addDeviceOperateHistoryTable(modeuser.getHouseIeee());
        		modeuserService.addDeviceWarnHistoryTable(modeuser.getHouseIeee());*/
        	} else {
        		/*modeuser.setId(t.getId());
            	modeuserService.update(modeuser);*/
        		/*String resultJson = "{\"result\":0}";//成功1 失败0
    			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
    			return;*/
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveModeuser", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveModeuser";
	}
	
	/**
	 * 修改密码
	 * @author: zhuangxd
	 * 时间：2014-2-18 上午10:30:30
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/ChangePWD")
	public void ChangePWD(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class); 
        	Modeuser t = modeuserService.getModeuser2(modeuser);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t != null) {
        		/*modeuser.setId(t.getId());
            	modeuserService.update(modeuser);*/
        		/*if (modeuser.getName() != null) {
        			t.setName(modeuser.getName());
        		}
        		if (modeuser.getEnableFlag() != null) {
        			t.setEnableFlag(modeuser.getEnableFlag());
        		}
        		*/
        		String key = Httpproxy.getKey(t.getPwd());
        		String newPass = Httpproxy.urlCodec(modeuser.getPwd(), key);
        		t.setPwd(newPass);
        		t.setLasttime(new Date());
            	modeuserService.update(t);
            	resultJson = "{\"result\":1}";//成功1 失败0
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("ChangePWD", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	@RequestMapping("/update")
	public void update(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class); 
        	Modeuser t = modeuserService.get(modeuser);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t != null) {
        		/*modeuser.setId(t.getId());
            	modeuserService.update(modeuser);*/
        		/*if (modeuser.getName() != null) {
        			t.setName(modeuser.getName());
        		}
        		if (modeuser.getSecretKey() != null) {
        			t.setSecretKey(modeuser.getSecretKey());
        		}
        		if (modeuser.getVendorCode() != null) {
        			t.setVendorCode(modeuser.getVendorCode());
        		}
        		if (modeuser.getVendorName() != null) {
        			t.setVendorName(modeuser.getVendorName());
        		}
        		if (modeuser.getEncodemethod() != null) {
        			t.setEncodemethod(modeuser.getEncodemethod());
        		}
        		if (modeuser.getXmppIp() != null) {
        			t.setXmppIp(modeuser.getXmppIp());
        		}
        		if (modeuser.getXmppPort() != null) {
        			t.setXmppPort(modeuser.getXmppPort());
        		}
        		if (modeuser.getCloudIp1() != null) {
        			t.setCloudIp1(modeuser.getCloudIp1());
        		}
        		if (modeuser.getCloudPort1() != null) {
        			t.setCloudPort1(modeuser.getCloudPort1());
        		}
        		if (modeuser.getCloudIp2() != null) {
        			t.setCloudIp2(modeuser.getCloudIp2());
        		}
        		if (modeuser.getCloudPort2() != null) {
        			t.setCloudPort2(modeuser.getCloudPort2());
        		}
        		if (modeuser.getEnableFlag() != null) {
        			t.setEnableFlag(modeuser.getEnableFlag());
        		}
        		if (modeuser.getDescription() != null) {
        			t.setDescription(modeuser.getDescription());
        		}*/
        		t.setLasttime(new Date());
            	modeuserService.update(t);
            	resultJson = "{\"result\":1}";//成功1 失败0
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
			
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class); 
//        	Modeuser t = modeuserService.find(modeuser);
        	List<Modeuser> t = modeuserService.findList(modeuser);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("find", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class); 
//        	Modeuser t = modeuserService.find(modeuser);
        	List<Modeuser> t = modeuserService.findList(modeuser);
        	List<Object[]> s = modeuserService.findListSql(modeuser);
        	for (Object[] objects : s) {
        		Modeuser modeuser2 = (Modeuser)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modeuser modeuser2 = (Modeuser)objects[0];
        		DevicewarnhistoryModeuseridYear devicewarnhistoryModeuseridYear = (DevicewarnhistoryModeuseridYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}
	
	@RequestMapping("/delete")
	public void delete(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class); 
        	modeuserService.delete(modeuser);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	@RequestMapping("/addModeuser")
	public void addModeuser(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		LOGGER.info("addModeuser json=" + json);
		PrintWriter out=response.getWriter(); 
		try {
			json = request.getParameter("json");
        	Modeuser modeuser = JSON.parseObject(json,Modeuser.class);
        	Modeuser t = modeuserService.getModeuser(modeuser);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t == null) {
        		modeuserService.save(modeuser);
        		resultJson = "{\"result\":1}";//成功1 失败0
        	} else {
        		resultJson = "{\"result\":-1}";//用户已注册
        	}
    		String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("addModeuser", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}
	}
	
	@RequestMapping("/getUserCount")
	public void getUserCount(String json,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("getUserCount json=" + json);
		PrintWriter out = null;
		String resultJson = "{\"result\":1}";//成功1 失败0
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int userCount = modeuserService.getUserCount(paramMap);
			resultJson = "{\"result\":1,\"userCount\":" + userCount + "}";	
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch(Exception e) {
			LOGGER.info("getUserCount", e);
			resultJson = "{\"result\":0,\"message\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getUsers")
	public void getUsers(String json,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("getUsers json=" + json);
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
        try {
        	out=response.getWriter(); 
        	Map<String, Object> paramMap = JSON.parseObject(json, Map.class); 
        	String startRow = request.getParameter("startRow");
        	String pageSize = request.getParameter("pageSize");
        	List<Map> t = modeuserService.queryUser(paramMap, Integer.parseInt(startRow), Integer.parseInt(pageSize));
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("getUsers", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/isPwdRight")
	public void isPwdRight(String json,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("isPwdRight json=" + json);
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
        try {
        	out=response.getWriter(); 
        	Modeuser modeuser = JSON.parseObject(json, Modeuser.class); 
        	//查询modeuser的旧密码
        	Modeuser modeuser2 = modeuserService.getModeuser2(modeuser);
        	String oldPass = modeuser2.getPwd();
        	String key = Httpproxy.getKey(oldPass);
        	String realPass = Httpproxy.urlCodec(modeuser.getPwd(), key);
        	boolean isOldRight = true;
        	if(!realPass.equals(oldPass))
        		isOldRight = false;
//        	boolean isPwdRight = modeuserService.isPwdRight(modeuser.getId(), modeuser.getPwd());
        	String resultJson = "{\"result\":1,\"isPwdRight\":1}";
        	if(!isOldRight) {
        		resultJson = "{\"result\":1,\"isPwdRight\":0}";
        	}
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("isPwdRight", e);
			String resultJson = "{\"result\":0, \"message\":\"" + e.getMessage() + "\"}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/updateModeuser")
	public void updateModeuser(String json,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("updateModeuser json=" + json);
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
        try {
        	out=response.getWriter(); 
        	Map<String, Object> paramMap = JSON.parseObject(json, Map.class); 
        	modeuserService.updateModeuser(paramMap);
        	String resultJson = "{\"result\":1}";			
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("updateModeuser", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/deleteModeuser")
	public void deleteModeuser(String json,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("deleteModeuser json=" + json);
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
        try {
        	out=response.getWriter(); 
        	Modeuser mu = JSON.parseObject(json, Modeuser.class); 
        	modeuserService.deleteUser(mu.getId());
        	String resultJson = "{\"result\":1}";			
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("deleteModeuser", e);
			String resultJson = "{\"result\":0,\"message\":\"" + e.getMessage() + "\"}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		PrintWriter out=null;
		try {
			request.getSession().invalidate();			
			out=response.getWriter(); 
			out.println(callback + "({\"request_id\": 1234, \"response_params\":{\"result\":1}})");
		} catch(Exception e) {
			LOGGER.info("logout", e);
			out.println(callback + "({\"request_id\": 1234, \"response_params\":{\"result\":0,\"\":\"" + e.getMessage() + "\"}})");
		}
	}
}
