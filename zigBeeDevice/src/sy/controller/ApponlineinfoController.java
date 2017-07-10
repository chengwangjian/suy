package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.smarthome.model.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Apponlineinfo;
import sy.service.ApponlineinfoControllerServiceI;
import zbHouse.pageModel.Json;

import com.alibaba.fastjson.JSON;

@Controller()
@RequestMapping("/ApponlineinfoController")
public class ApponlineinfoController {
	
	private static final Logger LOGGER = Logger.getLogger(ApponlineinfoController.class);
	
	private ApponlineinfoControllerServiceI apponlineinfoservice;

	public ApponlineinfoControllerServiceI getApponlineinfoservice() {
		return apponlineinfoservice;
	}
	
	@Autowired
	public void setApponlineinfoservice(
			ApponlineinfoControllerServiceI apponlineinfoservice) {
		this.apponlineinfoservice = apponlineinfoservice;
	}
	@RequestMapping("/findapp")
	public void findapp(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
//		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();  
		Map app = JSON.parseObject(json,Map.class);
		String houseIeee="";
		String username="";
		try{
		List<Map> t = apponlineinfoservice.find2(app);
		String name = (String) t.get(0).get("username");
		String houseIeee_username="";
		if ( t != null && t.size() > 0) {
			for(int i= 0;i<t.size();i++){
				houseIeee_username=(String) t.get(i).get("username");
				houseIeee=houseIeee_username.substring(0, 16);
			    t.get(i).put("houseIeee", houseIeee);
				//t.get(i).setHouseIeee(houseIeee);
				username=houseIeee_username.substring(17, houseIeee_username.length());
				//t.get(i).setUsername(username);
				t.get(i).put("username", username);
			}
		}
    	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
		String callback = request.getParameter("callback");
		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
	} catch (Exception e) {
		LOGGER.info("findapp",e);
		String resultJson = "{\"result\":0}";//成功1 失败0
		String callback = request.getParameter("callback");
		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	}   
    
	}
	
	/**
	 * xmpp同步推送的Z203注册的app账户信息
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/addapp")
	public void addapp(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
//		response.addHeader("Access-Control-Allow-Origin", "*");
		
//		http://218.104.133.242:8081/zigBeeDevice/ApponlineinfoController/addapp.do?json={"houseIeee":"00137A0000010136","username":"netvox","ipaddress":"214."}
		
		PrintWriter out=response.getWriter();  
		
		try{
			Map apponlineinfo = JSON.parseObject(json,Map.class);
			Apponlineinfo apponlineinfo2 = JSON.parseObject(json,Apponlineinfo.class);
			List<Apponlineinfo> a = apponlineinfoservice.findList(apponlineinfo2);
			if(a!=null && a.size()>0){
				String resultJson = "{\"result\":-1}";//成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			}else{
				Apponlineinfo t=new Apponlineinfo();
				t.setUsername(apponlineinfo.get("houseIeee")+"_"+apponlineinfo.get("username"));
				t.setHouseIeee(apponlineinfo.get("houseIeee").toString());
				t.setReadstate("2");
				apponlineinfoservice.add(t);
		    	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
				
			}
	} catch (Exception e) {
//		System.out.println("e.message==" + e.getMessage());
//		e.printStackTrace();
		LOGGER.info("addapp",e);
//		logger.info(e.getMessage());
		String resultJson = "{\"result\":0}";//成功1 失败0
		String callback = request.getParameter("callback");
//		logger.info(e.getMessage(), e);
//		logger.info(e.fillInStackTrace());
//		logger.info(e.toString());
		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	}   
    
	}
	
	/*@RequestMapping("/updateapp")
	public void updateapp(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
//		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();  
		Map apponlineinfo = JSON.parseObject(json,Map.class);
		try{
			List<Apponlineinfo> a = apponlineinfoservice.find(apponlineinfo);
			if(a.size()==0){
				String resultJson = "{\"result\":0}";//成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			}else{
				Apponlineinfo t=a.get(0);
				t.setAppOs((String) apponlineinfo.get("appOs"));
				t.setAppVersion((String) apponlineinfo.get("appVersion"));
				t.setInformation((String) apponlineinfo.get("information"));
				apponlineinfoservice.update(t);
		    	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
				
			}
	} catch (Exception e) {
		String resultJson = "{\"result\":0}";//成功1 失败0
		String callback = request.getParameter("callback");
		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		e.printStackTrace();
	}   
    
	}*/
	
	/**
	 * app推送的用户的操作系统，版本号，描述信息
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/addappinfo")
	public void addappinfo(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		//跳转到cloudAddress、cloudPort
//		String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//		String cloudPort = PropertiesUtil.getProperty("cloudPort");
//		String serverHost = request.getServerName();
//		LOGGER.info("addappinfo----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);
//		if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") && !serverHost.equals("127.0.0.1")) {
//			String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/ApponlineinfoController/addappinfo.do";
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("json", json);
//			paramMap.put("encodemethod", request.getParameter("encodemethod"));
//			paramMap.put("sign", request.getParameter("sign"));
//			paramMap.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//			paramMap.put("callback", request.getParameter("callback"));
//			String rsStr = TestHttpclient.postUrlWithParams(url, paramMap, "utf-8");
//			out.write(rsStr);
//			out.flush();
//			return;
//		}
		Apponlineinfo apponlineinfo = JSON.parseObject(json,Apponlineinfo.class);
		
		try{
			List<Apponlineinfo> list = apponlineinfoservice.findList(apponlineinfo);
			if (list.size()==0) {
				/*apponlineinfo.setUsername(apponlineinfo.getHouseIeee()+"_"+apponlineinfo.getUsername());
				apponlineinfo.setHouseIeee(apponlineinfo.getHouseIeee());
				apponlineinfo.setReadstate("2");//新增APP用户时候给readstate赋值，避免新用户会看到广告，初始化readstate为0（未读）
				apponlineinfoservice.add(apponlineinfo);*/
			}else {
				Apponlineinfo t = list.get(0);
				t.setUsername(apponlineinfo.getHouseIeee()+"_"+apponlineinfo.getUsername());
				t.setHouseIeee(apponlineinfo.getHouseIeee());
				t.setAppOs(apponlineinfo.getAppOs());
				t.setAppVersion(apponlineinfo.getAppVersion());
				t.setInformation(apponlineinfo.getInformation());
				apponlineinfoservice.update(t);
			}
			String resultJson = "{\"result\":0}";//成功1 失败0
        	resultJson = "{\"result\":1}";//成功1 失败0
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("addappinfo",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
	}
	/**
	 * 家庭概况中
	 * 推送APP是否在线状态,推送IP地址
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/updateappisonline")
	public void updateappisonline(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		
		try{
			List<Apponlineinfo> apponlineinfo = JSON.parseArray(json, Apponlineinfo.class);
			apponlineinfoservice.addbatchapp(apponlineinfo, request.getParameter("xmppIp"));
			LOGGER.info("updateappisonline:推送APP在线状态-------->"+JSON.toJSONString(apponlineinfo));
			String resultJson = "{\"result\":0}";//成功1 失败0
        	resultJson = "{\"result\":1}";//成功1 失败0
        	
        	
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateappisonline",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
    
	}
	
	/**
	 * 家庭概况中
	 * 推送APP是否在线状态,推送IP地址
	 * @author: zhuangxd
	 * 时间：2014-10-30 上午9:24:39
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	/*@RequestMapping("/updateappisonline")
	public void updateappisonline(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
//		http://192.168.1.123:8080/zigBeeDevice/houseController/updateappisonline.do?json={"onlineApp":[{"ipaddress":"218.104.133.227","username":"00137A00000119B5_admin"},{"ipaddress":"218.104.133.227","username":"00137A0000012D51_admin"}],"offlineApp":[{"houseieee":"00137A00000119B5"},{"houseieee":"00137A0000012D51"}]}		
		try{
			Map<String, List<Apponlineinfo>> map = (Map<String, List<Apponlineinfo>>)JSON.parse(json);
			// 在线houseieee数组
			LOGGER.info("updateappisonline:推送APP在线状态-------->");
			String json2= JSON.toJSONString(map.get("onlineApp"), true); 
			String json3= JSON.toJSONString(map.get("offlineApp"), true); 
			final List<Apponlineinfo> onlineApp = JSON.parseArray(json2,Apponlineinfo.class);
			LOGGER.info("updateappisonline:推送APP在线状态-------->"+JSON.toJSONString(onlineApp));
			// 离线houseieee数组
			final List<Apponlineinfo> offlineApp = JSON.parseArray(json3,Apponlineinfo.class);;
			apponlineinfoservice.addbatchapp(onlineApp,offlineApp);
			LOGGER.info("updateappisonline:推送APP在线状态-------->"+JSON.toJSONString(map));
			String resultJson = "{\"result\":0}";//成功1 失败0
        	resultJson = "{\"result\":1}";//成功1 失败0
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateappisonline",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
    
	}*/
	@RequestMapping("/getCount")	
	public void getCount(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();
		DataGrid dg = new DataGrid();
		try {
			map=JSON.parseObject(json);	
			map.put("total", apponlineinfoservice.getCount(map));
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getCount",e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);		
	}
	
	/*云端手动新增修改广告*/
	@RequestMapping("/updateAdvertisement")
	public void updateAdvertisement(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
        try {
        	Map<String, Object> appMap = JSON.parseObject(json,Map.class);
        	Apponlineinfo apponlineinfo = JSON.parseObject(json,Apponlineinfo.class); 
        	if(apponlineinfo.getReadstate()!=null){
        		apponlineinfo.setReadstate(apponlineinfo.getReadstate());
        	}
        	if(apponlineinfo.getTitle()!=null){
        		apponlineinfo.setTitle(apponlineinfo.getTitle());
        	}
        	int t = apponlineinfoservice.update1(apponlineinfo, appMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateAdvertisement",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
	}
	
	 /*云端查询广告*/
    @RequestMapping("/find1")
	public void find1(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html;charset=utf-8");
    	String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		String orderBy = request.getParameter("orderBy");
		String userid = request.getParameter("userid");
    	try {
    		if(StringUtils.isBlank(startRow))
				startRow = "0";
			if(StringUtils.isBlank(pageSize))
				pageSize = "30";
			Map pMap = JSON.parseObject(json, Map.class);
			Apponlineinfo apponlineinfo = JSON.parseObject(json,Apponlineinfo.class);
			apponlineinfo.setHouseIeee((String) pMap.get("sText"));
    		List<Apponlineinfo> t = apponlineinfoservice.find1(startRow,pageSize,orderBy,userid,apponlineinfo);
    		String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("find1",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
    }
    
    /**
     * APP获取广告
     * @param json
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/appfindad")
	public void appfindad(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html;charset=utf-8");
    	//跳转到cloudAddress、cloudPort
//		String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//		String cloudPort = PropertiesUtil.getProperty("cloudPort");
//		String serverHost = request.getServerName();
//		LOGGER.info("appfindad-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);
//		if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") && !serverHost.equals("127.0.0.1")) {
//			String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/ApponlineinfoController/appfindad.do";
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("json", json);
//			paramMap.put("encodemethod", request.getParameter("encodemethod"));
//			paramMap.put("sign", request.getParameter("sign"));
//			paramMap.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//			paramMap.put("callback", request.getParameter("callback"));
//			String rsStr = TestHttpclient.postUrlWithParams(url, paramMap, "utf-8");
//			out.write(rsStr);
//			out.flush();
//			return;
//		}
    	try {
    		Apponlineinfo apponlineinfo = JSON.parseObject(json,Apponlineinfo.class); 
    		/*if(apponlineinfo.getUsername()!=null){
    			apponlineinfo.setUsername(apponlineinfo.getUsername());
    		}*/
    		List<Apponlineinfo> t = apponlineinfoservice.find(apponlineinfo);
    		if (t != null && t.size() > 0) {
    			int a = apponlineinfoservice.appupdatereadstate(apponlineinfo);
    		}
    		String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("appfindad",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
    }
    
    /*APP修改广告读取状态*/
    @RequestMapping("/updatereadstate")
  	public void updatereadstate(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html;charset=utf-8");
    	//跳转到cloudAddress、cloudPort
//		String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//		String cloudPort = PropertiesUtil.getProperty("cloudPort");
//		String serverHost = request.getServerName();
//		LOGGER.info("updatereadstate----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);
//		if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") && !serverHost.equals("127.0.0.1")) {
//			String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/ApponlineinfoController/updatereadstate.do";
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("json", json);
//			paramMap.put("encodemethod", request.getParameter("encodemethod"));
//			paramMap.put("sign", request.getParameter("sign"));
//			paramMap.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//			paramMap.put("callback", request.getParameter("callback"));
//			String rsStr = TestHttpclient.postUrlWithParams(url, paramMap, "utf-8");
//			out.write(rsStr);
//			out.flush();
//			return;
//		}
    	try {
    		Apponlineinfo apponlineinfo = JSON.parseObject(json,Apponlineinfo.class); 
    		int t = apponlineinfoservice.appupdatereadstate(apponlineinfo);
    		String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("updatereadstate",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
    	}

    
    public void writeJson(Object object,String callback,HttpServletResponse response) {

		try {
			String jstr=callback+"("+JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss")+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson",e);
		}
	}	
}
