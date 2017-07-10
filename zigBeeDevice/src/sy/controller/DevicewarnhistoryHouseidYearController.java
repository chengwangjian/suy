package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.smarthome.domain.Warntypetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import sy.model.CacheWarnMessage;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.HouseWeixin;
import sy.model.Warnsend;
import sy.service.CacheWarnMessageServiceI;
import sy.service.DevicewarnhistoryHouseidYearServiceI;
import sy.service.HouseWeixinServiceI;
import sy.service.WarnsendServiceI;
import sy.util.Constants;
import sy.util.HouseieeeListener;
import sy.util.Httpproxy;
import sy.util.MapToBeanUtil;
import sy.util.NotificationEngine;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

@Controller
@RequestMapping("/devicewarnhistoryHouseidYearController")
public class DevicewarnhistoryHouseidYearController {

	private DevicewarnhistoryHouseidYearServiceI devicewarnhistoryHouseidYearService;
	//zone设备告警类型
	public static List<Map> zonewarntype = new ArrayList<Map>();
	private static final Logger log = Logger.getLogger(DevicewarnhistoryHouseidYearController.class); 
	private WarnsendServiceI warnsendService;
	private HouseWeixinServiceI houseWeixinService;
	private CacheWarnMessageServiceI messageService;
	private NotificationEngine pushIOS;

	public NotificationEngine getPushIOS() {
		return pushIOS;
	}
	@Autowired
	public void setPushIOS(NotificationEngine pushIOS) {
		this.pushIOS = pushIOS;
	}
	public CacheWarnMessageServiceI getMessageService() {
		return messageService;
	}
	@Autowired
	public void setMessageService(CacheWarnMessageServiceI messageService) {
		this.messageService = messageService;
	}
	public HouseWeixinServiceI getHouseWeixinService() {
		return houseWeixinService;
	}
	@Autowired
	public void setHouseWeixinService(HouseWeixinServiceI houseWeixinService) {
		this.houseWeixinService = houseWeixinService;
	}

	public WarnsendServiceI getWarnsendService() {
		return warnsendService;
	}

	@Autowired
	public void setWarnsendService(WarnsendServiceI warnsendService) {
		this.warnsendService = warnsendService;
	}

	public DevicewarnhistoryHouseidYearServiceI getDevicewarnhistoryHouseidYearService() {
		return devicewarnhistoryHouseidYearService;
	}

	@Autowired
	public void setDevicewarnhistoryHouseidYearService(DevicewarnhistoryHouseidYearServiceI devicewarnhistoryHouseidYearService) {
		this.devicewarnhistoryHouseidYearService = devicewarnhistoryHouseidYearService;
	}

	@RequestMapping("/pushIOSMsg")
	public void pushIOSMsg(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0
		try{
			Map<String,Object> map = JSON.parseObject(json, Map.class);
			List<String> list = new ArrayList<>();
			list.add((String)map.get("token"));
			pushIOS.testPushIOSMessage((String)map.get("message"), list);
		}catch(Exception e){
			log.error("push IOS msg error", e);
			resultJson = "{\"result\":0}";//成功1 失败0
		}
		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	}

	@RequestMapping("/add")
	public void saveDevicewarnhistoryHouseidYear(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";//成功1 失败0

		try {
			long receive_time = System.currentTimeMillis();
			//网关推送至云端的告警信息
			final DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear = JSON.parseObject(json,DevicewarnhistoryHouseidYear.class);
			//没有ep的默认为01
			if(StringUtils.isBlank(devicewarnhistoryHouseidYear.getZoneEp())){
				devicewarnhistoryHouseidYear.setZoneEp("01");
			}
			devicewarnhistoryHouseidYearService.saveSql(devicewarnhistoryHouseidYear);
			devicewarnhistoryHouseidYearService.addDeviceWarnSMS(devicewarnhistoryHouseidYear);
			final Warntypetable warntype = getWarnMsg(devicewarnhistoryHouseidYear);
			if(warntype == null) {
				log.info("["+devicewarnhistoryHouseidYear.getHouseIeee()+"-"+devicewarnhistoryHouseidYear.getZoneIeee()
				+"]:unknown warn type");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			long send_ios_start = System.currentTimeMillis();
			log.info("send message:"+warntype.getChinesewarnType()+" to ios start time:"+send_ios_start);
			//ios消息推送(APNS)
			final String uuid = "["+devicewarnhistoryHouseidYear.getHouseIeee()+"-"+devicewarnhistoryHouseidYear.getZoneIeee()+"]"+UUID.randomUUID();
			new Thread() {
				@Override
				public void run() {
					try {
						Warnsend warnsend = new Warnsend();
						warnsend.setHouseIeee(devicewarnhistoryHouseidYear.getHouseIeee());
						warnsend.setType("1");
						List<Warnsend> t = warnsendService.findList(warnsend);
						log.info(getName()+"-warnserd size:"+t.size());
						if(!t.isEmpty()) {
							pushIOS.pushIOSMessage(t, warntype, devicewarnhistoryHouseidYear);
						}
					} catch (Exception e) {
						log.error(getName()+"-ios send fail",e);
					}
				}
			}.start();

			//推送微信消息
			new Thread(){
				@Override
				public void run(){
					log.info("send msg to weixin start···");
					//家IEEE地址
					String houseIeee = devicewarnhistoryHouseidYear.getHouseIeee();
					//发生告警的时间
					Date date = devicewarnhistoryHouseidYear.getWarndatetime();
					String time = "";

					if(date!=null)
						time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
					else
						time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					//确认家已绑定微信，并向代理服务器获取所有已绑定该家的微信账号的openid
					String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
					String cloudPort = PropertiesUtil.getProperty("cloudPort");
					String url = "http://"+cloudAddress+":"+cloudPort+"/zigBeeDevice/wchat/getWeixinAccount.do?";
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("houseIeee", houseIeee);
					String result;
					try {
						result = TestHttpclient.postUrlWithParams(url, params, "UTF-8");
						if(StringUtils.isNotBlank(result)){
							List<JSONObject> hWeixinAccountList = JSON.parseObject(result, List.class);
							if(hWeixinAccountList!=null){
								for(JSONObject object:hWeixinAccountList){
									HouseWeixin weixin = JSON.toJavaObject(object, HouseWeixin.class);
									try {
										//获取相应的设备名称
										Map map = devicewarnhistoryHouseidYearService.getRoomAndDevice(devicewarnhistoryHouseidYear.getHouseIeee(),
												devicewarnhistoryHouseidYear.getRoomId(), devicewarnhistoryHouseidYear.getZoneIeee(), 
												devicewarnhistoryHouseidYear.getZoneEp());
										String deviceName = (String) map.get("deviceName");
										//当消息类型为故障消息时，设备状态改为离线
										if("5".equals(devicewarnhistoryHouseidYear.getWMode()))
											pushWeChatMessage(warntype.getChinesewarnType(), "离线", time, deviceName, weixin.getAccount(),houseIeee);
										else
											pushWeChatMessage(warntype.getChinesewarnType(), "在线", time, deviceName, weixin.getAccount(),houseIeee);
									} catch (Exception e) {
										log.error("send msg to "+weixin.getAccountName()+" error",e);
									}
								}
							}
						}
					} catch (IOException e1) {
						log.error("get bind weixin account error",e1);
					}
				}
			}.start();

			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			log.error("saveDevicewarnhistoryHouseidYear",e);
			resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}
	}

	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 

		try {
			DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear = JSON.parseObject(json,DevicewarnhistoryHouseidYear.class); 
			List<DevicewarnhistoryHouseidYear> t = devicewarnhistoryHouseidYearService.findList(devicewarnhistoryHouseidYear);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			log.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}	
	/**
	 * 报警历史记录 多张表查询
	 * @author pengcq
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findwarnListory")
	public void findwarnListory(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 

		try {
			DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear = JSON.parseObject(json,DevicewarnhistoryHouseidYear.class);
			//跳转到xmpp服务器获取告警信息
			int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
			if(cloudMain == 1) {
				String cloudAddress = (String) HouseieeeListener.houseieeeProxyserverMap.get(devicewarnhistoryHouseidYear.getHouseIeee());
				String cloudPort = PropertiesUtil.getProperty("cloudAddress.port");
				String serverHost = request.getServerName();
				log.info("cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
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
					}
					//添加告警是否发送短信、是否发送邮件字段
					if(StringUtils.isNotBlank(rsStr)) {
						rsStr = rsStr.trim();
						String jsonStr = rsStr.substring(rsStr.indexOf("(") + 1, rsStr.length() - 1).trim();
						//    					String jsonStr = rsStr.substring(rsStr.indexOf("response_params") + 17).trim();
						//    					jsonStr = jsonStr.substring(0, jsonStr.length() - 2);
						log.info("jsonStr === " + jsonStr);
						Map warnMap = JSON.parseObject(jsonStr, Map.class);
						List warnList = (List) ((Map) warnMap.get("response_params")).get("listory");
						if(warnList != null) {
							Map newWarnMap = devicewarnhistoryHouseidYearService.getNewWarnHistory(warnList);
							String resultJson= JSON.toJSONStringWithDateFormat(newWarnMap, "yyyy-MM-dd HH:mm:ss");
							String callback = request.getParameter("callback");
							out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
						} 
						else {
							out.println(rsStr);
						}
						return;
					} else {
						out.println(rsStr);
						return;
					}
				}
			}
			if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
				devicewarnhistoryHouseidYear.setStartRow(Integer.parseInt(request.getParameter("startRow")));
				devicewarnhistoryHouseidYear.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
			}
			Map t = devicewarnhistoryHouseidYearService.findwarnListory(devicewarnhistoryHouseidYear);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			log.info("findwarnListory",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	/**
	 * 报警历史记录总数 多张表查询
	 * @author pengcq
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getwarnCount")
	public void getwarnCount(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8");

		try {
			DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear = JSON.parseObject(json,DevicewarnhistoryHouseidYear.class); 
			Map t = devicewarnhistoryHouseidYearService.getwarnListory(devicewarnhistoryHouseidYear);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			log.info("getwarnCount",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	@RequestMapping("/getHistoryWarnDataTotalCount")
	public void getHistoryWarnDataTotalCount(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8");

		try {
			Map<String, Object> paramMap =JSON.parseObject(json,Map.class);
			Map t = devicewarnhistoryHouseidYearService.getHistoryWarnDataTotalCount(paramMap);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			log.info("getwarnCount",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	@RequestMapping("/getHistoryWarnDataPage")
	public void getHistoryWarnDataPage(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8");

		try {
			int total=0;
			Map  map=new HashMap<String,Object>();
			Map<String, Object> paramMap =JSON.parseObject(json,Map.class);
			List<Map> t = devicewarnhistoryHouseidYearService.getHistoryWarnDataPage(paramMap.get("houseIeee").toString(), paramMap.get("beginDateTime").toString(), paramMap.get("endDateTime").toString(),paramMap.get("startIndex").toString(),paramMap.get("count").toString());
			total=t.size();
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			log.info("getHistoryWarnData",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	@RequestMapping("/getHistoryWarnData")
	public void getHistoryWarnData(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8");

		try {
			Map<String, Object> paramMap =JSON.parseObject(json,Map.class);
			List<Map> t = devicewarnhistoryHouseidYearService.getHistoryWarnData(paramMap.get("houseIeee").toString(), paramMap.get("beginDateTime").toString(), paramMap.get("endDateTime").toString());
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			log.info("getHistoryWarnData",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	//	public int pushiphone(List<String> Tokens,String path, String password, String message,Integer count,boolean sendCount,List<String> isonList,List<String> titles) throws Exception {  
	/*public void pushiphone(List<Warnsend> tokens, Warntypetable warnType, boolean sendBatch, DevicewarnhistoryHouseidYear devWarn,String uuid) throws Exception {
		   PushNotificationManager pushManager = new PushNotificationManager();
		   String[][] certArrs = {{"1",this.getClass().getResource("/729Push.p12").getPath(),"123456"},
				   {"2",this.getClass().getResource("/push.p12").getPath(),"123456"},
				   {"3",this.getClass().getResource("/distribution-push.p12").getPath(),"123456"},
				   {"4",this.getClass().getResource("/funIOTDevelopment.p12").getPath(),"123456"},
				   {"5",this.getClass().getResource("/funIOTDistribution.p12").getPath(),"123456"}
		   };
//		   boolean onlyOneFooter = true;
		   int certArrsLength = certArrs.length;
		   for(int i = 0; i < certArrsLength; i++) {
			   String[] certArr = certArrs[i];
	    		   try { 
//		           //message是一个json的字符串{“message”:{“fire”:”iphone推送消息火灾”}}  
//		           PushNotificationPayload payLoad =  PushNotificationPayload.fromJSON(message); 
//		           //payLoad.addAlert(titles); // 提示消息内容  
//		           payLoad.addBadge(count); // iphone应用图标上小红圈上的数值 （表示消息数量） 
//		           payLoad.addSound("default"); // 铃音 默认（default）  quot,default,wav
		           //PushNotificationManager pushManager = new PushNotificationManager();  
		           //true：表示的是产品发布推送服务 (gateway.push.apple.com / 2195  )           false：表示的是产品测试推送服务  (gateway.sandbox.push.apple.com /2195 ) 
	    		   boolean isProduct = true; 
		    	   if(certArr[0].equals("2")||certArr[0].equals("4"))
		    		   isProduct = false;
		    	   //日志打印推送证书名
		    	   String certificate =  certArr[1];
	    		   if(StringUtils.isNotBlank(certificate))
	    			   log.info("ios push certificate : "+certificate.substring(certificate.lastIndexOf("/")));
		           pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certArr[1], certArr[2], isProduct));  
		           List<PushedNotification> notifications = new ArrayList<PushedNotification>();   
		           // 发送push消息  
		           String message = "{'aps':{'a':'告警消息（告警时间：）" + devWarn.getWarndatetime() + " 告警模式：" + devWarn.getWMode() + " 告警描述：" + devWarn.getWDescription() + "'}}";
		           if (!sendBatch) {  
			           log.info(uuid+"-------------------apple循环推送-------");
			           for(Warnsend wSend : tokens) {
			        	 //message是一个json的字符串{“message”:{“fire”:”iphone推送消息火灾”}}  
				           PushNotificationPayload payLoad =  PushNotificationPayload.fromJSON(message);
//				           Warnsend wSend = tokens.get(0);
				           Integer footer = wSend.getFooter();
//				           if(Integer.parseInt(wSend.getIsonline()) == 0 && onlyOneFooter) {
				           if(Integer.parseInt(wSend.getIsonline()) == 0 && i == 0) {
				        	   footer++;
			        		   wSend.setFooter(footer);
			        		   warnsendService.update(wSend);
			        	   }
			        	   else {
			        		   wSend.setFooter(0);
			        	   }
//				           log.info("---------deviceToken, footer: " + wSend.getDeviceToken() + ", " + wSend.getFooter());
				           payLoad.addBadge(footer);
				           payLoad.addSound("default");
				           payLoad.addAlert(wSend.getLanguage().equals("China")? warnType.getChinesewarnType() : warnType.getEnglishwarnType());
				           Device device = new BasicDevice(wSend.getDeviceToken());  
				           PushedNotification notification = pushManager.sendNotification(device, payLoad, true);  
				           notifications.add(notification);  
			           }
//			           if(onlyOneFooter) {
//			        	   onlyOneFooter = false;
//			           }
		           } else {  
		               log.info(uuid+"--------------------apple 群推送 -------");
		             //message是一个json的字符串{“message”:{“fire”:”iphone推送消息火灾”}}  
			           PushNotificationPayload payLoad =  PushNotificationPayload.fromJSON(message);
		               List<Device> device = new ArrayList<Device>();  
			           for (Warnsend wSend : tokens) {
			        	   Integer footer = wSend.getFooter();
//			        	   if(Integer.parseInt(wSend.getIsonline()) == 0 && onlyOneFooter) {
			        	   if(Integer.parseInt(wSend.getIsonline()) == 0 && i == 0) {
			        		   footer++;
			        		   wSend.setFooter(footer);
			        		   warnsendService.update(wSend);
			        	   }
			        	   else {
			        		   wSend.setFooter(0);
			        	   }
			        	   payLoad.addBadge(footer); // iphone应用图标上小红圈上的数值 （表示消息数量）
				           payLoad.addSound("default"); // 铃音 默认（default）  quot,default,wav
				           String alert = wSend.getLanguage().equals("China")? warnType.getChinesewarnType() : warnType.getEnglishwarnType();
//			        	   log.info("-------language, alert: " + wSend.getLanguage() + ", " + alert);
				           payLoad.addAlert(alert);
			        	   device.add(new BasicDevice(wSend.getDeviceToken()));  	
			           } 
		           	   notifications = pushManager.sendNotifications(payLoad, device); 
//		           	   if(onlyOneFooter) {
//			        	   onlyOneFooter = false;
//			           }
		           }  
		           List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);  	             
		           List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);  
		             //失败返回
		           int failed = failedNotifications.size();  
		             //成功返回
		           int successful = successfulNotifications.size();  
		           if (successful > 0 && failed == 0) {  	             
		        	   log.info(uuid+","+certArr[1] + "-----All 通知 pushed 成功 (" + successfulNotifications.size() + "):条");
		               pushManager.stopConnection();  
//		               return 1;
		           } else if (successful == 0 && failed > 0) {  
		        	   log.info(uuid+","+certArr[1] + "-----All 通知 失败 (" + failedNotifications.size() + "):");  
		        	   pushManager.stopConnection();  
//		        	   return 0;
		           } else if (successful == 0 && failed == 0) {  
		        	   log.info(uuid+","+certArr[1] + "-----无法发送通知,可能因为一个关键错误(如参数错误)");  
			           pushManager.stopConnection();  
//			           return 0;
		           } else {  
		        	   log.info(uuid+","+certArr[1] + "------一些 通知 失败 (" + failedNotifications.size() + "):");  
		        	   log.info(uuid+","+certArr[1] + "------其他 成功 (" + successfulNotifications.size() + "):");  
		               pushManager.stopConnection();  
//		             return 2;
		           } 
	    	   }
    		   catch (Exception e) {  
    			   	log.info("pushiphone", e);
    		   }
    		   finally {
    		    	try {
    		    		if(pushManager != null)
    		    			pushManager.stopConnection();
    		    	} catch(CommunicationException e1) {
    		    		log.info("pushiphone 停止异常"+e1.getMessage(), e1);
    		    	} catch (KeystoreException e1) {
    					log.info("pushiphone 停止异常"+e1.getMessage(), e1);
    				}
    		    }
	   } 
	}  */

	@RequestMapping({"/sendWarnSMS"})
	public void sendWarnSMS(String json, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		String sign = request.getParameter("sign");
		String prefixKey = PropertiesUtil.getProperty("prefixKey");
		String pKey = prefixKey + new SimpleDateFormat("yyyyMMdd").format(new Date());
		String decodeUri = Httpproxy.urlCodec16(sign, pKey);
		String reqUri = request.getRequestURI();
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":1}";
		if (!decodeUri.equals(reqUri)) {
			resultJson = "{\"result\":-1}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			return;
		}
		log.info("sendWarnSMS解密成功-------------");
		try
		{
			String ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null) {
				ipAddress = request.getHeader("X_FORWARDED_FOR");
				if (ipAddress == null) {
					ipAddress = request.getHeader("X-Forward-For");
					if (ipAddress == null) {
						ipAddress = request.getRemoteAddr();
					}
				}
			}

			Map rMap = this.devicewarnhistoryHouseidYearService.getSMSType(ipAddress);
			int smstype = 1;
			if (rMap != null) {
				smstype = ((Byte)rMap.get("smstype")).byteValue();
				ipAddress = (String)rMap.get("smsip");
			}
			String username = PropertiesUtil.getProperty("dongle.username");

			String pwd = PropertiesUtil.getProperty("dongle.password");

			List<DevicewarnhistoryHouseidYear> warnList = JSON.parseArray(json, DevicewarnhistoryHouseidYear.class);
			if ((warnList != null) && (!warnList.isEmpty()))
				log.info("预警消息发送开始-------------");
			StringBuilder sql = null;
			for (DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear : warnList) {
				Byte smsType = devicewarnhistoryHouseidYearService.getShcSmsType(devicewarnhistoryHouseidYear.getHouseIeee());
				if(smsType==1){
					continue;
				}

				sql = new StringBuilder("insert into messagehistory(messageid,sendtime,type,phonenumber,content,state,remark,houseIeee,emailContent) VALUES");

				int wMode = Integer.parseInt(devicewarnhistoryHouseidYear.getWMode());

				StringBuilder msg = null;
				StringBuilder emailMsg = null;
				if ((wMode == 1) || 
						(wMode == 2) || 
						(wMode == 3)) {
					String deviceName123 = devicewarnhistoryHouseidYear.getDeviceName();
					String roomName123 = devicewarnhistoryHouseidYear.getRoomName();
					SimpleDateFormat warnTimeFmt = new SimpleDateFormat("M月d日H时m分");
					String tailNumber = devicewarnhistoryHouseidYear.getHouseIeee().substring(11);
					String warnTime = warnTimeFmt.format(devicewarnhistoryHouseidYear.getWarndatetime());
					String warnCtn = wMode == 2 ? "触发火警警报" : wMode == 1 ? "有人入侵了" : "触发紧急报警";
					msg = new StringBuilder("您家IEEE尾号为");
					msg.append(tailNumber).append("的").append(roomName123).append("区域的").append(deviceName123).append("于").append(warnTime).append(warnCtn).append("，请重视！");
					emailMsg = new StringBuilder("<html><head></head><body><table><tr><td>尊敬的用户：</td></tr><tr><td style=\"padding-left:35px\">您好，您家尾号为");
					emailMsg.append(tailNumber).append("的").append(roomName123).append("区域的").append(deviceName123).append("于").append(warnTime).append(warnCtn);
					emailMsg.append("，请重视!</td></tr><tr><td align=\"right\">").append("大洋通信</td></tr></table></body></html>");
				}

				if (msg != null) {
					Date currentTime = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sendtime = sdf.format(currentTime);
					int pState = 0; int emState = 0;


					List phoneList = this.devicewarnhistoryHouseidYearService.getMessPhone(devicewarnhistoryHouseidYear.getHouseIeee());

					if (phoneList != null) {
						pState = Integer.parseInt((String)((Map)phoneList.get(0)).get("state"));
						if (pState == 1) {
							try {
								Iterator pItor = phoneList.iterator();
								while (pItor.hasNext()) {
									String phoneNo = (String)((Map)pItor.next()).get("phoneNO");
									if (StringUtils.isNotBlank(phoneNo)) {
										String msgStr = msg.toString();
										String warnsend = "";
										if (smstype == 2) {
											String param = "user_name=" + username + "&message=" + msgStr + "&phoneNumber=" + phoneNo + "&callback=1234";
											String encryStr = param.replaceAll("&", "");
											String context = Httpproxy.urlEncrypt(URLEncoder.encode(encryStr, "UTF-8"), Httpproxy.getKey(pwd));
											param = param + "&encodemethod=AES&sign=" + context;

											warnsend = SendSMSDongle.getDongle().sendSMS(msgStr, URLEncoder.encode(param, "UTF-8"), ipAddress);
										}
										else {
											warnsend = SendSMS.getSingleSendSMS().sendSMS(phoneNo, msgStr);
										}
										sql.append("(").append(devicewarnhistoryHouseidYear.getMessage_id()).append(",'");
										sql.append(sendtime).append("','");
										sql.append("0").append("','");
										sql.append(phoneNo).append("','");
										sql.append(msg).append("','");
										if (warnsend.equals(msgStr))
											sql.append("1").append("','");
										else {
											sql.append("0").append("','");
										}
										sql.append("预警短信").append("','");
										sql.append(devicewarnhistoryHouseidYear.getHouseIeee()).append("','");
										sql.append(msg).append("'),");
									}
								}
							} catch (Exception e) {
								log.info("send SMS fail", e);
							}
							log.info("预警短信发送结束----------");
						}
					}

					List emailList = this.devicewarnhistoryHouseidYearService.getMessEmail(devicewarnhistoryHouseidYear.getHouseIeee());
					if (emailList != null) {
						emState = Integer.parseInt((String)((Map)emailList.get(0)).get("state"));
						if (emState == 1) {
							try {
								Iterator eItor = emailList.iterator();
								while (eItor.hasNext()) {
									String warnEmail = (String)((Map)eItor.next()).get("warnEmail");
									if (StringUtils.isNotBlank(warnEmail))
									{
										this.devicewarnhistoryHouseidYearService.sendemail(emailMsg.toString(), warnEmail);
										sql.append("('").append(devicewarnhistoryHouseidYear.getMessage_id()).append("','");
										sql.append(sendtime).append("','");
										sql.append("2").append("','");
										sql.append(warnEmail).append("','");
										sql.append(emailMsg).append("','");
										sql.append("1").append("','");
										sql.append("预警邮件").append("','");
										sql.append(devicewarnhistoryHouseidYear.getHouseIeee()).append("','");
										sql.append(emailMsg).append("'),");
									}
								}
							} catch (Exception e) {
								log.info("send Email fail", e);
							}
							log.info("预警邮件发送结束----------");
						}
					}

					if ((pState != 0) || (emState != 0))
					{
						this.devicewarnhistoryHouseidYearService.batchSave(sql.deleteCharAt(sql.length() - 1).toString());
					}
				}
			}
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			log.info("saveDevicewarnhistoryHouseidYear", e);
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	/**
	 * 农业获取告警历史接口
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getFarmHistoryWarnData")
	public void getFarmHistoryWarnData(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8");
		try {
			String userName = request.getParameter("user_name");
			Map<String, Object> paramMap =JSON.parseObject(json,Map.class);
			List<Map> t = devicewarnhistoryHouseidYearService.getFarmWarnData(userName, 
					paramMap.get("houseIeee").toString(), paramMap.get("beginDateTime").toString(), paramMap.get("endDateTime").toString());
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			log.info("getFarmHistoryWarnData",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	/**
	 * 调用公共接口推送微信
	 * @param message 告警消息
	 * @param isOnlineStr 设备在线/离线
	 * @param time 告警时间
	 * @param deviceName 设备名称
	 * @param account 微信账号（openid）
	 * @throws Exception
	 */
	public static void pushWeChatMessage(String message, String isOnlineStr, String time, String deviceName, 
			String account,String houseIeee) throws Exception {
		//若消息内容为空，则不需推送消息
		if(StringUtils.isBlank(message)) {
			log.info("-------------wechat message is empty");
			return;
		}
		log.info("-------------push wechat message");
		//推送消息
		String weixinIp = PropertiesUtil.getProperty("weixin.ip");
		String weixinPort = PropertiesUtil.getProperty("weixin.port");
		String url = "http://" + weixinIp + ":" + weixinPort + "/weChat/wchat/pushMessage.do";
		Map<String, Object> params = new HashMap<String, Object>();
		//设置告警消息内容
		params.put("message", message);
		//设备状态（在线/离线）
		params.put("isOnlineStr", isOnlineStr);
		//告警时间
		params.put("time", time);
		//设备名称
		params.put("deviceName", deviceName);
		//微信账号
		params.put("account", account);
		//家IEEE地址
		params.put("houseIeee", houseIeee);
		//应用号
		params.put("appid", "2");
		//代理服务器IP（主要用来区分服务器是正式、测试还是开发的）
		params.put("proxyServer", PropertiesUtil.getProperty("cloud.agenthost"));
		String json = JSON.toJSONStringWithDateFormat(params, "yyyy-MM-dd HH:mm:ss",SerializerFeature.WriteMapNullValue);
		Map<String, Object> reqParams = new HashMap<>();
		reqParams.put("json", json);
		try {
			//发送告警信息
			TestHttpclient.postUrlWithParams(url, reqParams, "utf-8");
		} catch(Exception e) {
			log.error("push message exception", e);
		}
	}

	/**
	 * 获取告警信息实体对象（重组告警消息）
	 * @param devicewarnhistoryHouseidYear
	 * @return 返回告警信息实体对象
	 * @throws Exception
	 */
	private Warntypetable getWarnMsg(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) throws Exception{
		//第一次发生告警时先将所有zone设备告警类型加载到内存中
		if(DevicewarnhistoryHouseidYearController.zonewarntype.isEmpty()){
			DevicewarnhistoryHouseidYearController.zonewarntype = devicewarnhistoryHouseidYearService.getzonewarntype();
		}
		//智能网关上传的zone类型
		String sensortype = devicewarnhistoryHouseidYear.getSensortype();
		String wMode = devicewarnhistoryHouseidYear.getWMode();
		
		Warntypetable warntype = null;
		for(Map map:zonewarntype){
			if(map.get("sensortype")!=null&&map.get("sensortype").toString().equalsIgnoreCase(sensortype)
					&&map.get("WMode")!=null&&map.get("WMode").toString().equalsIgnoreCase(wMode)){
				warntype = (Warntypetable) MapToBeanUtil.convertMap(Warntypetable.class, map);
				break;
			}
		}
		if(warntype==null){
			warntype = devicewarnhistoryHouseidYearService.getwarntype(devicewarnhistoryHouseidYear.getWMode());
		}
		if(warntype == null) {
			log.info("unknow warn type");
			return null;
		}
		String chinesewarnType = warntype.getChinesewarnType();
		String englishwarnType = warntype.getEnglishwarnType();
		Integer w_mode = Integer.parseInt(wMode);

		if(StringUtils.containsIgnoreCase(chinesewarnType, "<用户名称>")
				||StringUtils.containsIgnoreCase(englishwarnType, "<user name>")){
			//门锁类型告警消息重组
			String nameCn = StringUtils.isBlank(devicewarnhistoryHouseidYear.getName())? "有人" : devicewarnhistoryHouseidYear.getName();
			String nameEn = StringUtils.isBlank(devicewarnhistoryHouseidYear.getName())? "someone" : devicewarnhistoryHouseidYear.getName();
			chinesewarnType = chinesewarnType.replace("<用户名称>", nameCn);
			englishwarnType = englishwarnType.replace("<user name>", nameEn);
		}
		if(StringUtils.containsIgnoreCase(chinesewarnType, "<家人名称>")
				||StringUtils.containsIgnoreCase(englishwarnType, "<family name>")){
			chinesewarnType = chinesewarnType.replace("<家人名称>", devicewarnhistoryHouseidYear.getWDescription());
			englishwarnType = englishwarnType.replace("<family name>", devicewarnhistoryHouseidYear.getWDescription());
		}
		//自定义告警消息
		if(w_mode == Constants.WMODE_CUSTOM){
			chinesewarnType = devicewarnhistoryHouseidYear.getWDescription();
			englishwarnType	= devicewarnhistoryHouseidYear.getWDescription();
		}
		//获取相应的房间名和设备名
		Map map = devicewarnhistoryHouseidYearService.getRoomAndDevice(devicewarnhistoryHouseidYear.getHouseIeee(),
				devicewarnhistoryHouseidYear.getRoomId(), devicewarnhistoryHouseidYear.getZoneIeee(), 
				devicewarnhistoryHouseidYear.getZoneEp());
		if(map!=null){
			//真实房间名替换通用符号
			if(StringUtils.containsIgnoreCase(chinesewarnType, "<房间>")
					||StringUtils.containsIgnoreCase(englishwarnType, "<room>")){
				chinesewarnType = chinesewarnType.replace("<房间>", (String)map.get("roomName"));
				englishwarnType = englishwarnType.replace("<room>", (String)map.get("roomName"));
			}
			//真实设备名替换通用符号
			if(StringUtils.containsIgnoreCase(chinesewarnType, "<设备>")
					||StringUtils.containsIgnoreCase(englishwarnType, "<device>")){
					chinesewarnType = chinesewarnType.replace("<设备>", (String)map.get("deviceName"));
					englishwarnType = englishwarnType.replace("<device>", (String)map.get("deviceName"));
			}
			//网关上传滤网层数替换通用符号
			if(chinesewarnType.contains("<滤网>")||englishwarnType.contains("<filters>")){
				if(StringUtils.containsIgnoreCase(devicewarnhistoryHouseidYear.getWDescription(), "first")){
					chinesewarnType = chinesewarnType.replace("<滤网>", "第一层");
					englishwarnType = englishwarnType.replace("<filters>", "first");
				}else if(StringUtils.containsIgnoreCase(devicewarnhistoryHouseidYear.getWDescription(), "second")){
					chinesewarnType = chinesewarnType.replace("<滤网>", "第二层");
					englishwarnType = englishwarnType.replace("<filters>", "second");
				}else if(StringUtils.containsIgnoreCase(devicewarnhistoryHouseidYear.getWDescription(), "third")){
					chinesewarnType = chinesewarnType.replace("<滤网>", "第三层");
					englishwarnType = englishwarnType.replace("<filters>", "third");
				}else if(StringUtils.containsIgnoreCase(devicewarnhistoryHouseidYear.getWDescription(), "forth")){
					chinesewarnType = chinesewarnType.replace("<滤网>", "第四层");
					englishwarnType = englishwarnType.replace("<filters>", "forth");
				}else{
					chinesewarnType = chinesewarnType.replace("<滤网>", "");
					englishwarnType = englishwarnType.replace("<filters>", "");
				}
			}
		}else{
			log.info("get device info fail");
		}
		try{
			//保存推送消息
			CacheWarnMessage cacheMsg = new CacheWarnMessage();
			cacheMsg.setHouseIeee(devicewarnhistoryHouseidYear.getHouseIeee());
			cacheMsg.setPushTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(devicewarnhistoryHouseidYear.getWarndatetime()));
			cacheMsg.setMessageCN(chinesewarnType);
			cacheMsg.setMessageEN(englishwarnType);
			cacheMsg.setDeviceName(map==null?"":(String)map.get("deviceName"));
			messageService.saveMessage(cacheMsg);
			//给消息重新设值
			warntype.setChinesewarnType(chinesewarnType);
			warntype.setEnglishwarnType(englishwarnType);
			log.info(devicewarnhistoryHouseidYear.getHouseIeee()+"-"+devicewarnhistoryHouseidYear.getZoneIeee()
			+",chinese warn info:"+chinesewarnType+" , english warn type:"+englishwarnType);
		}catch(Exception e){
			log.error("save push message error", e);
		}
		return warntype;
	}

	/**
	 * 离线是判断用户是否24小时内是否存在低压警告
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @author zhangmaolin
	 * @Time  2016-10-24
	 */
	@RequestMapping("/getZoneWmode")
	public void getZoneWmode(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map params = JSON.parseObject(json, Map.class);
			String houseIEEE = params.get("houseIEEE").toString();
			String deviceIeee = params.get("deviceIeee").toString();
			String deviceEp = params.get("ep").toString();
			String lasttime = params.get("lasttime").toString();
			List<Map> list = this.devicewarnhistoryHouseidYearService.getZoneWmode(houseIEEE, deviceIeee, deviceEp, lasttime);
			String resultJson= JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			log.info("getZoneWmode",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	@RequestMapping("/getWarnMode")
	public void getWarnMode(String json, HttpServletRequest request, HttpServletResponse response) throws IOException  {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;chartset=utf-8");
		try {
			Map param = JSON.parseObject(json, Map.class);
			String houseIEEE = (String)param.get("houseIeee");
			String deviceIeee = (String)param.get("deviceIeee");
			String deviceEp = (String)param.get("deviceEp");
			List<Map> list = this.devicewarnhistoryHouseidYearService.getWarnMode(houseIEEE, deviceIeee, deviceEp);
			String resultJson= JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			log.info("getWarnMode",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/exportWarnLogExcel")
	public void exportWarnLogExcel(String json, HttpServletResponse response, HttpServletRequest request) throws IOException {
		log.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try {
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int t = devicewarnhistoryHouseidYearService.exportWarnLogExcel(paramMap,request,response);
		} catch (Exception e) {
			log.info("exportLqiLogExcel", e);
			try {
				out = response.getWriter();
			} catch(Exception ex) {
				log.info("exportWarnLogExcel getWriter", ex);
				resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
			}
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback +"{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}	

}
