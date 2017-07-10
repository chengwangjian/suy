package sy.controller;

import java.io.IOException;
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

import sy.service.BrinServiceI;
import sy.service.HouseServiceI;
import sy.service.HouseieeeServiceI;
import sy.service.ProxyserverServiceI;
import sy.util.HouseieeeListener;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/brinController")
public class BrinController {
	private static final Logger LOGGER = Logger.getLogger(BrinController.class); 
	private BrinServiceI brinService;
	public BrinServiceI getBrinService() {
		return brinService;
	}
	@Autowired
	public void setBrinService(BrinServiceI brinService) {
		this.brinService = brinService;
	}
	private HouseServiceI houseService;
	
	
	private static final String[] allowTypes = {".xls"};
	
	private HouseieeeServiceI houseieeeService;
	
	private ProxyserverServiceI proxyserverService;

	public ProxyserverServiceI getProxyserverService() {
		return proxyserverService;
	}

	@Autowired
	public void setProxyserverService(ProxyserverServiceI proxyserverService) {
		this.proxyserverService = proxyserverService;
	}

	public HouseieeeServiceI getHouseieeeService() {
		return houseieeeService;
	}

	@Autowired
	public void setHouseieeeService(HouseieeeServiceI houseieeeService) {
		this.houseieeeService = houseieeeService;
	}

	public HouseServiceI getHouseService() {
		return houseService;
	}

	@Autowired
	public void setHouseService(HouseServiceI houseService) {
		this.houseService = houseService;
	}

	/**
	 * burn in 添加
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/add")
	public void add(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> param = JSON.parseObject(json,Map.class);		
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();  
        try {   
        	int i=brinService.isRegister(param.get("houseIEEE").toString());
        	if(i!=0){
        		int t1 = brinService.add(param);
        		if(t1==1){
        			String resultJson1 = "{\"result\":1}";//成功1
            		out.println("{\"request_id\": 1234, \"response_params\":" + resultJson1 + "}"); 
        		}
        		if(t1==2){
        			String resultJson1 = "{\"result\":0}";//0 已经存在
        			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson1 + "}"); 
        		}
        	}
        	else {
        		String resultJson1 = "{\"result\":2}";//未注册2
        		out.println("{\"request_id\": 1234, \"response_params\":" + resultJson1 + "}"); 
			}
		} catch (Exception e) {
			LOGGER.info("add",e);
			String resultJson = "{\"result\":3}";//3 程序出错
		    out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");  
		    return;
		}
	}

	
//	@RequestMapping("/updateDeviceAllFlag")
//	public void updateDeviceAllFlag(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
//		PrintWriter out=response.getWriter();  
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		try {
//			String houseIEEE = request.getParameter("houseIEEE"); 
//			String deviceAllFlag = request.getParameter("deviceAllFlag"); 
//			int t = brinService.updateDeviceAllFlag(houseIEEE,deviceAllFlag);
//			String resultJson = "{\"result\":1}";//成功1 失败0 
//			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//		} catch (Exception e) {
//			LOGGER.info("updateDeviceAllFlag", e);
//			String resultJson = "{\"result\":0}";//成功1 失败0 
//			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			e.printStackTrace();
//		}
//	}
	
	@RequestMapping("/findWrongdevice")
	public void findWrongdevice(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out=null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			out=response.getWriter(); 
			Map<String, Object> map = JSON.parseObject(json,Map.class);
			List<Map> t = brinService.findWrongdevice(map);
			String resultJson = "{\"result\":1}";//成功1 失败0
			if(t==null){
				resultJson = "{\"result\":1,\"dCList\":[]}";
			}
			else{
				String dCListStr = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
				resultJson = "{\"result\":1,\"dCList\":" + dCListStr + "}";
			}
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("findWrongdevice",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	
	
	/**
	 * burnin搜索和列表显示
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/findbrin")
	public void findbrin(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
			PrintWriter out=response.getWriter();  
			response.addHeader("Access-Control-Allow-Origin", "*");
			try {
				Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
				String pageIndex = paramMap.get("startRow").toString();
				String pageSize = paramMap.get("pageSize").toString();
				int pageIndex_ = Integer.parseInt(pageIndex);
				int pageSize_ = Integer.parseInt(pageSize);
				List<Map> t = brinService.findbrin(paramMap, pageIndex_,pageSize_ , "");
			 	// 统计
//	        	String cloudAddress = PropertiesUtil.getProperty("cloudAddress.data");
	    		String cloudPort = PropertiesUtil.getProperty("cloudAddress.port");
	    		Map<String, Object> pMap= new HashMap<String,Object>();
//	        	pMap.put("json",JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss"));
	        	List<Map> dCList =null;
	        	Map<String, Object> tmpMap = new HashMap<String,Object>();
//	        	for (int i = 0; i < t.size(); i++) {
//					Map map1 = (Map) t.get(i);
//    				/*if (map1.get("houseIEEE")!=null) {
//						if (HouseController.maponline.get(map1.get("houseIEEE")) != null) {
//							map1.put("isonline", HouseController.maponline.get(map1.get("houseIEEE")) > 0 ? 1 : 0);
//					} else {
//						map1.put("isonline", 0);
//					}
//				}*/
//	        		String cloudAddress =  (String) HouseieeeListener.houseieeeProxyserverMap.get(t.get(i).get("houseIEEE"));
////	        		String cloudAddress = "192.168.1.251";
//	        		String callUrl = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/brinController/getdeviceCount.do";
//	            	tmpMap.put("houseIEEE", t.get(i).get("houseIEEE"));
//	            	pMap.put("json",JSON.toJSONStringWithDateFormat(tmpMap, "yyyy-MM-dd HH:mm:ss"));
//	    			String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
//	    			Map<String, Object> map = JSON.parseObject(rsStr,Map.class);
//	    			Map<String, Object> paMap = (Map<String, Object>) map.get("response_params");
//	    			dCList=(List<Map>) paMap.get("dCList");
//
////	    			for (Map tMap : t) {
////						String houseIeee = (String) tMap.get("houseIEEE");
////						for(Map dMap : dCList) {
////							String houseIeee2 = (String) dMap.get("houseIEEE");
////							if(houseIeee.equals(houseIeee2)) {
//	    			if(dCList.size()>0){
//	    				t.get(i).put("deviceCount", dCList.get(0).get("dCount"));
//	    				if(dCList.get(0).get("aCount")!=null){
//								if((int)(dCList.get(0).get("aCount"))==0){
//									t.get(i).put("deviceAllFlag", 1);
//								}else {
//									t.get(i).put("deviceAllFlag",0);
//								}
//							}
//	    				else {
//	    					t.get(i).put("deviceAllFlag",0);
//						}
//	    			}
//	    			else if(dCList.size()==0){
//	    				t.get(i).put("deviceCount", 0);
//						t.get(i).put("deviceAllFlag",1);
//					}
//	    			else {
//	    				t.get(i).put("deviceCount", 0);
//						t.get(i).put("deviceAllFlag",0);
//					}
////							}
////						}
////					}
//	        	}
//	    		brinService.updateDeviceAllFlag(offhouseIEEEs.toString(), onhouseIEEEs.toString());
	    		Date currentTime = new Date();
		    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	String servertime = formatter.format(currentTime);
				String resultJson=JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + ",\"servertime\": \""+ servertime +"\"}");
			} catch (Exception e) {
				LOGGER.info("findbrin", e);
				String resultJson = "{\"result\":0}";//成功1 失败0 
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//				e.printStackTrace();
			}
	}
	/**
	 * 开启加网
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	@RequestMapping("/opennet")
	public void opennet(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
		try {
			String houseIEEE = (String) paramMap.get("houseIEEE");
			String resultJson = "{\"result\":1}";//成功0
//			String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String userz203 = PropertiesUtil.getProperty("user.z203");
        	StringBuilder upUrl = new StringBuilder("http://");
        	//upUrl.append(xmppHost).append(":").append(xmppPort).append("/z203chat/poll?context=xmlup&")
        	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=setPermitJoinOn/255/")
        	.append(userz203)
        	.append("&").append("username=").append(houseIEEE);
        	String str = TestHttpclient.getUrl(upUrl.toString(),"utf-8");
//        	Map<String, Object> result = JSON.parseObject(str, Map.class);
//        	int status = (int) ((Map) result.get("message")).get("status");
//        	if(status == 0) {
        		int t =brinService.opennet(paramMap);
        		resultJson = "{\"result\":1}";//成功1
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
    			return;
//        	}else{
//        		out.println( "{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//        	}
		} catch (Exception e) {
			LOGGER.info("opennet", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			e.printStackTrace();
		}
	}
	/**
	 * 关闭加网
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/closenet")
	public void closenet(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
		PrintWriter out=response.getWriter();  
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			String houseIEEE = (String) paramMap.get("houseIEEE");
			String resultJson = "{\"result\":1}";//成功0
//			String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String userz203 = PropertiesUtil.getProperty("user.z203");
        	StringBuilder upUrl = new StringBuilder("http://");
        	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=setPermitJoinOn/0/")
        	.append(userz203)
        	.append("&").append("username=").append(houseIEEE);
        	String str = TestHttpclient.getUrl(upUrl.toString(),"utf-8");
        	Map<String, Object> result = JSON.parseObject(str, Map.class);
//        	int status = (int) ((Map) result.get("message")).get("status");
//        	if(status == 0) {
        		int t =brinService.closenet(paramMap);
        		resultJson = "{\"result\":0}";//成功0
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
    			return;
//        	}else{
//        		out.println( "{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//        	}
		} catch (Exception e) {
			LOGGER.info("closenet", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			e.printStackTrace();
		}
	}
	/**
	 * 统计设备数量
	 * @param json
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getdeviceCount")
	public void getdeviceCount(String json,HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			out=response.getWriter(); 
			Map<String, Object> map = JSON.parseObject(json,Map.class);
			List<Map> t = brinService.getdeviceCount(map);
			String resultJson = "{\"result\":1}";//成功1 失败0
			if(t==null){
				resultJson = "{\"result\":1,\"dCList\":[]}";
			}
			else{
				String dCListStr = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
				resultJson = "{\"result\":1,\"dCList\":" + dCListStr + "}";
			}
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("getdeviceCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	/**
	 * 完成
	 * @param json
	 * @param request
	 * @param response
	 */
	@RequestMapping("/finish")
	public void finish(String json,HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out=null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			out=response.getWriter(); 
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int i= brinService.finish(paramMap);
			String resultJson = "{\"result\":1}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("finish",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	/**
	 * 重新
	 * @param json
	 * @param request
	 * @param response
	 */
	@RequestMapping("/restart")
	public void restart(String json,HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out=null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			out=response.getWriter(); 
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int i= brinService.restart(paramMap);
			String resultJson = "{\"result\":1,\"intFlag\":" + i + "}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("restart",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	/**
	 * 点击忽略调用此接口开始burn in
	 * @param json
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/burninIgnore")
	public void burninIgnore(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
		PrintWriter out=response.getWriter();  
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			int t =brinService.updateComplete(paramMap);
			String resultJson =null;
			resultJson = "{\"result\":1}";//成功1
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
		} catch (Exception e) {
			LOGGER.info("burninIgnore",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
		
	}
	/**
	 * 点击确定调用此接口开始burn in
	 * @param json
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/burninSure")
	public void burninSure(String json,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
		PrintWriter out=response.getWriter();  
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			int t =brinService.updateSure(paramMap);
			String resultJson =null;
			resultJson = "{\"result\":1}";//成功1
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
		} catch (Exception e) {
			LOGGER.info("burninIgnore",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
		
	}
	/**
	 * 初始化
	 * @param json
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getInit")
	public void getInit(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
		PrintWriter out=response.getWriter();  
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			String resultJson = "{\"result\":0}";//失败0
			//String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String houseIEEE = (String)paramMap.get("houseIEEE");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String userz203 = PropertiesUtil.getProperty("user.z203");
        	StringBuilder upUrl = new StringBuilder("http://");
        	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=zbInitializeGS/")
        	.append(userz203)
        	.append("&").append("username=").append(paramMap.get("houseIEEE"));
        	String str = TestHttpclient.getUrl(upUrl.toString(),"utf-8");
        	Map<String, Object> result = JSON.parseObject(str, Map.class);
        	Map<String, Object> param = new HashMap<String, Object>();
        	int status = Integer.parseInt(((Map) result.get("message")).get("time").toString());
        	if(status > 0) {
//        		int t =brinService.updateInit(paramMap);
        		param.put("userid", paramMap.get("userid"));
        		param.put("houseIEEE", paramMap.get("houseIEEE"));
        		param.put("initResult", 1);
        		int a = brinService.updateInitResult(param);//保存初始化状态
//            	StringBuilder upUrl2 = new StringBuilder("http://");
//            	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=checkZ203Integrity/")
//            	.append(userz203).append("&").append("username=").append(houseIEEE);
//        		
//            	String str2 = TestHttpclient.getUrl(upUrl2.toString(),"utf-8");
            	
            	//getZ203Integrity(json, request, response);
            	//int t2 = brinService.getZ203Integrity(paramMap);
            	
        		resultJson = "{\"result\":1}";//成功1
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
    			return;
        	}else{
        		param.put("userid", paramMap.get("userid"));
        		param.put("houseIEEE", paramMap.get("houseIEEE"));
        		param.put("initResult", 0);
        		int a = brinService.updateInitResult(param);
        		out.println( "{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
        	}
		} catch (Exception e) {
			LOGGER.info("getInit", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getCount")	
	public void getCount(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 
        try {
        	Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
        	Map t = brinService.getCount(paramMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
    		out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("getCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
		}        
	}
	
	//checkZ203Integrity
	@RequestMapping("/getZ203Integrity")	
	public void getZ203Integrity(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
		try {
			String resultJson = "{\"result\":1}";//成功0
			String houseIEEE=(String) paramMap.get("houseIEEE");
			//String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String userz203 = PropertiesUtil.getProperty("user.z203");
        	StringBuilder upUrl = new StringBuilder("http://");
        	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=checkZ203Integrity/")
        	.append(userz203)
        	.append("&").append("username=").append(houseIEEE);
        	String str = TestHttpclient.getUrl(upUrl.toString(),"utf-8");
			out.println(str);   
			return;
		} catch (Exception e) {
			LOGGER.info("getZ203Integrity", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			e.printStackTrace();
		}
	}
	
	public void writeJson(Object object,String callback,HttpServletResponse response) {
		try {
			String jstr=""+JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss")+"";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson",e);
		}
	}	
	@RequestMapping("/getZ203IntegrityProgress")
	public void getZ203IntegrityProgress(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			String resultJson = "{\"result\":1}";//成功0
			String houseIEEE=(String) paramMap.get("houseIEEE");
			//String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String userz203 = PropertiesUtil.getProperty("user.z203");
        	
        	StringBuilder upUrl2 = new StringBuilder("http://");
        	upUrl2.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=checkZ203IntegrityProgress/")
        	.append(userz203).append("&").append("username=").append(houseIEEE);
        	String str2 = TestHttpclient.getUrl(upUrl2.toString(),"utf-8");
        	Map<String, Object> result = JSON.parseObject(str2, Map.class);
        	int status =Integer.parseInt(((Map) result.get("message")).get("status").toString());
        	if(status==-1 || status==6){
        		StringBuilder upUrl3 = new StringBuilder("http://");
            	upUrl3.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=readCheckZ203Integrity/")
            	.append(userz203).append("&").append("username=").append(houseIEEE);
            	String str3 = TestHttpclient.getUrl(upUrl3.toString(),"utf-8");
            	Map<String, Object> paramMap2 = new HashMap<String,Object>();
            	Map<String, Object> paramMap3 = JSON.parseObject(str3, Map.class);
            	int a= ((List)(((Map)paramMap3.get("message")).get("infomation"))).size();
            	if(a > 0){
            		paramMap2.put("intFlag", 0);//0表示不完整
            	}
            	else {
            		paramMap2.put("intFlag", 1);//1表示完整
				}
            	paramMap2.put("description", str3);
            	paramMap2.put("houseIEEE", houseIEEE);
            	paramMap2.put("userid", paramMap.get("userId"));
            	brinService.addIntegrity(paramMap2);
//            	if(status == 6){
//            		paramMap.put("rFlag", 1);
//            		int t =brinService.updateInit(paramMap);
//            	}
            	
        	}
			out.println(str2);   
			return;
		} catch (Exception e) {
			LOGGER.info("getZ203IntegrityProgress", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/getZ203IntegrityInformation")
	public void getZ203IntegrityInformation(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			String resultJson = "{\"result\":1}";//成功0
			String houseIEEE=(String) paramMap.get("houseIEEE");
			//String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String userz203 = PropertiesUtil.getProperty("user.z203");
        	StringBuilder upUrl2 = new StringBuilder("http://");
        	upUrl2.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=readCheckZ203Integrity/")
        	.append(userz203).append("&").append("username=").append(houseIEEE);
        	String str2 = TestHttpclient.getUrl(upUrl2.toString(),"utf-8");
			out.println(str2);   
			return;
		} catch (Exception e) {
			LOGGER.info("getZ203IntegrityInformation", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			e.printStackTrace();
		}
		
	}
/*	@RequestMapping("/addIntegrity")
	public void addIntegrity(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			String resultJson = "{\"result\":1}";//成功0
			String houseIEEE=(String) paramMap.get("houseIEEE");
			//String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String userz203 = PropertiesUtil.getProperty("user.z203");
        	StringBuilder upUrl2 = new StringBuilder("http://");
        	upUrl2.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=readCheckZ203Integrity/")
        	.append(userz203).append("&").append("username=").append(houseIEEE);
        	String str2 = TestHttpclient.getUrl(upUrl2.toString(),"utf-8");
        	Map<String, Object> result = JSON.parseObject(str2, Map.class);
        	List status = (List) ((Map) result.get("message")).get("infomation");
        	if (status!=null) {
				brinService.addIntegrity(paramMap);
			}
			out.println(str2);   
			return;
		} catch (Exception e) {
			LOGGER.info("addIntegrity", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
			e.printStackTrace();
		}
	}*/
/*	@RequestMapping("/getIntegrity")
	public void getIntegrity(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			String resultJson = "{\"result\":1}";//成功0
			String houseIEEE=(String) paramMap.get("houseIEEE");
			//String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String userz203 = PropertiesUtil.getProperty("user.z203");
        	StringBuilder upUrl = new StringBuilder("http://");
        	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=checkZ203Integrity/")
        	.append(userz203)
        	.append("&").append("username=").append(houseIEEE);
        	TestHttpclient.getUrl(upUrl.toString(),"utf-8");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getIntegrity", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
			e.printStackTrace();
		}
	}*/
//	@RequestMapping("/isonlineCount")
//	public void  isonlineCount(String json,String callback,HttpServletRequest request,HttpServletResponse response) throws IOException {
//		PrintWriter out=response.getWriter();  
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		try {
//			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
//		 	// 统计
//        	String cloudAddress = PropertiesUtil.getProperty("cloudAddress.data");
//    		String cloudPort = PropertiesUtil.getProperty("cloudAddress.port");
////        	pMap.put("json",JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss"));
//            	String callUrl = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/brinController/isonlineCount.do";    
//    			String rsStr = TestHttpclient.postUrlWithParams(callUrl, paramMap, "utf-8");
//    			Map<String, Object> map = JSON.parseObject(rsStr,Map.class);
//    			Map<String, Object> paMap = (Map<String, Object>) map.get("response_params");
//    			
//    		
//			String resultJson=JSON.toJSONStringWithDateFormat(, "yyyy-MM-dd HH:mm:ss");
//			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//		} catch (Exception e) {
//			LOGGER.info("findbrin", e);
//			String resultJson = "{\"result\":0}";//成功1 失败0 
//			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			e.printStackTrace();
//		}
//	}
	/**
	 * 设备确认
	 * @param json
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getInitTwo")
	public void getInitTwo(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);	
		PrintWriter out=response.getWriter();  
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			String resultJson = "{\"result\":0}";//失败0
			String houseIEEE = (String)paramMap.get("houseIEEE");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
//			String serverIp = PropertiesUtil.getProperty("xmpp.host");
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
//			String serverIp = "192.168.1.186";
//			String serverPort ="8081";
			String userz203 = PropertiesUtil.getProperty("user.z203");
//			String userz203 ="00137A0000012EAB";
        	StringBuilder upUrl = new StringBuilder("http://");
        	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=initCheckAllDevice/")
        	.append(userz203)
        	.append("&").append("username=").append(houseIEEE);
        	String str = TestHttpclient.getUrl(upUrl.toString(),"utf-8");
        	Map<String, Object> result = JSON.parseObject(str, Map.class);
        	int status = (Integer) ((Map) result.get("message")).get("status");
        	if(status==0) {
        		//int t =brinService.updateInit(paramMap);
        		resultJson = "{\"result\":1}";//成功1
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
    			return;
        	}else{
        		out.println( "{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
        	}
		} catch (Exception e) {
			LOGGER.info("getInitTwo", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	/**
	 *显示burnin 异常列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/findBrinwrong")
	public void findBrinwrong(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> param = JSON.parseObject(json,Map.class);		
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();  
        try {   
        	List<Map> tList =brinService.findBrinwrong(param);
        	String resultJson=JSON.toJSONStringWithDateFormat(tList, "yyyy-MM-dd HH:mm:ss");
            out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("findBrinwrong",e);
			String resultJson = "{\"result\":0}";//0 程序出错
		    out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");  
		    return;
		}
	}
	/**
	 * 统计在线、离线个数
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/onlineCount")
	public void onlineCount(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> param = JSON.parseObject(json,Map.class);		
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();  
		String callback = request.getParameter("callback");
        try {   
        	List<Map> tList =brinService.onlineCount(param);
        	String resultJson=JSON.toJSONStringWithDateFormat(tList.get(0), "yyyy-MM-dd HH:mm:ss");
            out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("onlineCount",e);
			String resultJson = "{\"result\":0}";//0 程序出错
		    out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		    return;
		}
	}
	
	@RequestMapping("/findExce")
	public void findExce(String json,HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> param = JSON.parseObject(json,Map.class);		
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();  
        try {   
        	int i =brinService.findExce(param);
        	String resultJson = "{\"result\":"+i+"}";//返回1,表示有异常；返回-1，表示正常
            out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("findExce",e);
			String resultJson = "{\"result\":0}";//0 程序出错
		    out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");  
		    return;
		}
		
	}
}
