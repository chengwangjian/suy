package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import sy.model.RssiRecord;
import sy.service.RssiRecordServiceI;
import sy.util.AESCodec;
import sy.util.HouseieeeListener;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

/**
 * @author huanglw
 * 
 */
@Controller
@RequestMapping("/rssi")
public class RssiRecordController {
	private static final Logger LOGGER = Logger.getLogger(RssiRecordController.class);
	private RssiRecordServiceI recordService;
    //查看设备状态时，缓存每个家的执行信息
    private Map<String, Map<String, Object>> cacheRssiImtly = new HashMap<String, Map<String, Object>>();
    private long noDataTimeRange = 100000L;
    
	public RssiRecordServiceI getRecordService() {
		return recordService;
	}

	@Autowired
	public void setRecordService(RssiRecordServiceI recordService) {
		this.recordService = recordService;
	}
	
	@RequestMapping("/startLQI")
	public void pushLQI(String json, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out =null;
		String resultJson="";
		response.setContentType("text/html;charset=utf-8");
		try {
			out = response.getWriter();
			Map map=JSON.parseObject(json, Map.class);
			String houseIEEE = (String) map.get("HouseIEEE");
			Map<String, Object> rssiMap = cacheRssiImtly.get(houseIEEE);
			if(rssiMap == null) {
				rssiMap = new HashMap<String, Object>();
				rssiMap.put("startTimes", System.currentTimeMillis());
				rssiMap.put("lastRssiCount", 0);
				List<RssiRecord> rssiList = new ArrayList<RssiRecord>();
				rssiMap.put("rssiList", rssiList);
				cacheRssiImtly.put(houseIEEE, rssiMap);
			}else {
				rssiMap.put("startTimes", System.currentTimeMillis());
				rssiMap.put("lastRssiCount", 0);
				((List<RssiRecord>) rssiMap.get("rssiList")).clear();
			}
			//获取203的时间
			String context="gettime";
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			String cloudPort = PropertiesUtil.getProperty("xmpp.port");
			/*StringBuilder url = new StringBuilder("http://");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			url.append(serverIp).append(":").append(PropertiesUtil.getProperty("xmpp.port")).append("/spring-async/z203chat/poll");*/
			String callPrefix = "http://" + serverIp + ":" + cloudPort;
			String callUri = "/spring-async/z203chat/poll";
			Map<String, Object> PMap = new HashMap<String, Object>();
			PMap.put("context", context);
			PMap.put("username", map.get("HouseIEEE"));
			//String str =TestHttpclient.postUrlWithParams(callPrefix + callUri.toString(), PMap, "utf-8");
			String str = TestHttpclient.postUrlWithParams(callPrefix + callUri, PMap, "utf-8");
			LOGGER.info("获取得到的str===》："+ str);
			LOGGER.info("获取得到的PMap===》："+ PMap);
			Map timemap=JSON.parseObject(str, Map.class);
			Map m = (Map) timemap.get("message");
			String time = (String) m.get("time");
			LOGGER.info("获得的时间是---=："+time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date = sdf.parse(time);
			LOGGER.info("转化后的时间是++++++："+date);
			Date afterDate = new Date(date .getTime() + 30000);
			String t=sdf.format(afterDate);
			t=t.substring(11);
			String now=sdf.format(new Date());
//			datemap.put("time", now);
			rssiMap.put("time", now);
			//通知203推送rssi数据
			String z203Json="{\"msg_type\":\"lqi_monitor\",\"msg\":"
					 +"{\"house_ieee\":\""+map.get("HouseIEEE")+"\",\"bOpen\":\"start\",\"open_time\":\""+t+"\",\"min_lqi\":255}}";//
			Map<String, Object> z203PMap = new HashMap<String, Object>();
			z203PMap.put("context", z203Json);
			z203PMap.put("username", map.get("HouseIEEE"));
			StringBuilder callUrl = new StringBuilder("http://");
			callUrl.append(serverIp).append(":").append(PropertiesUtil.getProperty("xmpp.port")).append("/spring-async/z203chat/polla");
			//解析调用返回
			String str1 ="context="+z203Json+"username="+map.get("HouseIEEE");
        	while(str1.getBytes().length%16!=0)
        		str1=str1+"#";
        	String key =map.get("HouseIEEE").toString().substring(6, 16)+"NETVOX";
        	byte[] encryptResult = AESCodec.encrypt2(str1, key);
        	String encryptResultStr = AESCodec.parseByte2HexStr(encryptResult);
        	callUrl.append("?sign=").append(encryptResultStr).append("&encodemethod=AES&houseIeeeSecret=").append(map.get("HouseIEEE"));
			String str2 =TestHttpclient.postUrlWithParams(callUrl.toString(), z203PMap, "utf-8");
			Map marp=JSON.parseObject(str2, Map.class);
			Map  messagemap=(Map) marp.get("message");
			String msg= messagemap.get("status_msg")+"";
			String callback = request.getParameter("callback");
			if(msg.equals("success")){
				resultJson = "{\"result\":1}";
			    out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  	
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("startLQI Exception", e);
		}
	}
		
	@RequestMapping("/getProgress")
	public void getProgress(String json, HttpServletRequest request, HttpServletResponse response) {
		   String resultJson = "";
		   String callback = request.getParameter("callback");
		   PrintWriter out = null;
		   try {
			   out = response.getWriter();
			   Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			   String houseIEEE = (String) paramMap.get("HouseIEEE");
			   Map<String, Object> rssiMap = cacheRssiImtly.get(houseIEEE);
			   List<RssiRecord> rssiList = (List<RssiRecord>) rssiMap.get("rssiList");
			   Integer lastRssiCount = (Integer) rssiMap.get("lastRssiCount");
			   Long startTimes = (Long) rssiMap.get("startTimes");
			   if(rssiList.size() == lastRssiCount) {
				   long timeRelRange = System.currentTimeMillis() - startTimes.longValue();
				   //未到结束时间返回获取数据未结束
				   if(timeRelRange <= noDataTimeRange) {
					   resultJson = "{\"result\":2}";
					   out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
				   }
				   else {
					   //插入rssi值
					   for(RssiRecord rr : rssiList){
			 				recordService.saveRssiRecordsImmtly(rr);   
					   }
					   //清空缓存
					   rssiMap.put("startTimes", 0L);
					   rssiMap.put("lastRssiCount", 0);
					   rssiList.clear();
					   //返回
					   resultJson = "{\"result\":1}";
					   out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
				   }
			   }
			   else {
				   rssiMap.put("startTimes", System.currentTimeMillis());
				   rssiMap.put("lastRssiCount", rssiList.size());
				   resultJson = "{\"result\":3}";
				   out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
			   }
		  } 
		  catch (Exception e) {
			// TODO: handle exception
			   LOGGER.error("getProgress Exception", e);
			   resultJson = "{\"result\":0}";
			   out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		  }
	
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(String json, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMapParams = new HashMap<String, Object>();
		
		try {
			RssiRecord rssiRecord = JSON.parseObject(json, RssiRecord.class);
			Map<String, Object> rssiMap = cacheRssiImtly.get(rssiRecord.getHouse_ieee());
			if(rssiMap != null) {
				List<RssiRecord> rssiList = (List<RssiRecord>) rssiMap.get("rssiList");
				Long startTimes = (Long) rssiMap.get("startTimes");
				long timeRange = System.currentTimeMillis() - startTimes.longValue();
				if(timeRange <= noDataTimeRange) {
					rssiList.add(rssiRecord);
				}
				else {
					recordService.add(rssiRecord);
				}
			}
			else {
				recordService.add(rssiRecord);
			}
			resultMapParams.put("result", 1);
			resultMapParams.put("msg", "success");
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			return resultMap;
		} catch (Exception e) {
			LOGGER.info("add",e);
			resultMapParams.put("result", 0);
			resultMapParams.put("msg", e.getMessage());
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			return resultMap;
		}
	}
	
	@ResponseBody
	@RequestMapping("/addBatch")
	public Map<String, Object> addBatch(String json, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMapParams = new HashMap<String, Object>();
		
		try {
			List<RssiRecord> rssiRecordList = JSONObject.parseArray(json, RssiRecord.class);
			for(RssiRecord rssiRecord:rssiRecordList){
				Map<String, Object> rssiMap = cacheRssiImtly.get(rssiRecord.getHouse_ieee());
				if(rssiMap != null) {
					List<RssiRecord> rssiList = (List<RssiRecord>) rssiMap.get("rssiList");
					Long startTimes = (Long) rssiMap.get("startTimes");
					long timeRange = System.currentTimeMillis() - startTimes.longValue();
					if(timeRange <= noDataTimeRange) {
						rssiList.add(rssiRecord);
					}
					else {
						recordService.add(rssiRecord);
					}
				}
				else {
					recordService.add(rssiRecord);
				}
			}
			resultMapParams.put("result", 1);
			resultMapParams.put("msg", "success");
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			return resultMap;
		} catch (Exception e) {
			LOGGER.info("add",e);
			resultMapParams.put("result", 0);
			resultMapParams.put("msg", e.getMessage());
			resultMap.put("response_id", request.getParameter("request_id"));
			resultMap.put("response_params", resultMapParams);
			return resultMap;
		}
	}

	@RequestMapping("/getRssiCount")
	public void getRssiCount(String json, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String searchText = (String) paramMap.get("searchText");
			String houseIeee = (String) paramMap.get("houseIeee");
			String type = (String) paramMap.get("type");
			if(StringUtils.isBlank(houseIeee)) {
				resultJson = "{\"result\":-1}";
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
				return;
			}
			String time = null;
			Map<String, Object> rssiMap = cacheRssiImtly.get(houseIeee);
			if(rssiMap != null) {
				time = (String) rssiMap.get("time");
			}
			else {
				time = (new Date()).toString();
			}
//			if(datemap.get("time")==null){
//				datemap.put("time", new Date());
//			}
			int total = recordService.getRSSICount(searchText, houseIeee,type, time, paramMap);
			resultJson = "{\"result\":1,\"total\":" + total + "}";
			out.println("{\"request_id\": 1234, \"response_params\":"
					+ resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getRssiCount",e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":"
					+ resultJson + "}");
		}
	}

	@RequestMapping("/getRSSI")
	public void getRSSI(String json, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
		List<Map> rssiList = null;
		PrintWriter out = null;
//		 count=3;
//		 history=0;;
		response.setContentType("text/html;charset=utf-8");
//		String callback = request.getParameter("callback");
		String orderBy = request.getParameter("orderBy");
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(startRow))
			startRow = "0";
		if (StringUtils.isBlank(pageSize))
			pageSize = "30";
		String rssiBValue = request.getParameter("rssiBValue");
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String searchText = (String) paramMap.get("searchText");
			String houseIeee = (String) paramMap.get("houseIeee");
			String type = (String) paramMap.get("type");
			if(StringUtils.isBlank(houseIeee)) {
				String resultJson = "{\"result\":-1}";
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
				return;
			}
			String time = null;
			Map<String, Object> rssiMap = cacheRssiImtly.get(houseIeee);
			if(rssiMap != null) {
				time = (String) rssiMap.get("time");
			}
			else {
				time = (new Date()).toString();
			}
//			if(datemap.get("time")==null){
//				datemap.put("time", new Date());
//			}
			rssiList = recordService.queryRSSI(searchText, Integer.parseInt(startRow), Integer.parseInt(pageSize), orderBy, houseIeee, type, time, paramMap );
			if(StringUtils.isBlank(rssiBValue))
				rssiBValue = "100";
//			if(datemap.get("time")==null){
//				datemap.put("time", new Date());
//			}
			int rssiBValueCount = recordService.getNormalDeviceCount(searchText, Integer.parseInt(rssiBValue), houseIeee, type, time, paramMap);
			String rssiJson = JSON.toJSONStringWithDateFormat(rssiList, "yyyy-MM-dd HH:mm:ss");
			String resultJson = "{\"result\":1,\"rssiList\":" + rssiJson + ",\"nBValueCount\":" + rssiBValueCount + "}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getRSSI",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getNoResponseCount")
	public void getNoResponseCount(String json, HttpServletRequest request,	HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		String resultJson="";
		response.setContentType("text/html;charset=utf-8");
		try {
			out=response.getWriter();
			Map map=JSON.parseObject(json, Map.class);
			String houseIeee = (String) map.get("HouseIEEE");
			String time = null;
			Map<String, Object> rssiMap = cacheRssiImtly.get(houseIeee);
			if(rssiMap != null) {
				time = (String) rssiMap.get("time");
			}
			else {
				time = (new Date()).toString();
			}
//			if(datemap.get("time")==null){
//				datemap.put("time", new Date());
//			}
			 Object a=this.recordService.getNoResponseCount(map, time);
			 String callback = request.getParameter("callback");
			 resultJson = JSON.toJSONStringWithDateFormat(a, "yyyy-MM-dd HH:mm:ss");
			 out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  	
		} catch (Exception e) {
			// TODO: handle exception
			   String callback = request.getParameter("callback");
				resultJson = "{\"result\":0}";
			    out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		}
		
	}
	@RequestMapping("/getNoResponseDevice")
	public void getNoResponseDevice(String json, HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
		Object rssiList = null;
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String orderBy = request.getParameter("orderBy");
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(startRow))
			startRow = "0";
		if (StringUtils.isBlank(pageSize))
			pageSize = "30";
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String houseIeee = (String) paramMap.get("HouseIEEE");
			if(StringUtils.isBlank(houseIeee)) {
				String resultJson = "{\"result\":-1}";
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
				return;
			}
			String time = null;
			Map<String, Object> rssiMap = cacheRssiImtly.get(houseIeee);
			if(rssiMap != null) {
				time = (String) rssiMap.get("time");
			}
			else {
				time = (new Date()).toString();
			}
//			if(datemap.get("time")==null){
//				datemap.put("time", new Date());
//			}
			rssiList = recordService.getNoResponseDevice(Integer.parseInt(startRow), Integer.parseInt(pageSize), houseIeee, null, time);
			String rssiJson = JSON.toJSONStringWithDateFormat(rssiList, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			    out.println(callback + "({\"request_id\": 1234, \"response_params\":" + rssiJson + "})");  	
		} catch (Exception e) {
			LOGGER.info("getRSSI",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	@RequestMapping("/getLR")
	public void getLR(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, Object> params = JSON.parseObject(json, Map.class);
			String houseIeee = (String)params.get("houseIeee");
			String deviceIeee = (String)params.get("ieee");
			String deviceEp = (String)params.get("ep");
			String orderBy = (String)params.get("orderBy");
			Map list = recordService.getLR(houseIeee, deviceIeee, deviceEp, orderBy);
			String resultJson= JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			LOGGER.info("getZoneWmode",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/exportLqiLogExcel")
	public void exportLqiLogExcel(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try {
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int t = recordService.exportLqiLogExcel(paramMap,request,response);
		} catch (Exception e) {
			LOGGER.info("exportLqiLogExcel", e);
			try {
				out = response.getWriter();
			} catch(Exception ex) {
				LOGGER.info("exportLqiLogExcel getWriter", ex);
				resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
			}
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback +"{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/exportRssiLogExcel")
	public void exportRssiLogExcel(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try {
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int t = recordService.exportRssiLogExcel(paramMap,request,response);
		} catch (Exception e) {
			LOGGER.info("exportLqiLogExcel", e);
			try {
				out = response.getWriter();
			} catch(Exception ex) {
				LOGGER.info("exportLqiLogExcel getWriter", ex);
				resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
			}
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback +"{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	@RequestMapping("/getNewRSSI")
	public void getNewRSSI(String json, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
		List<Map> rssiList = null;
		PrintWriter out = null;
//		 count=3;
//		 history=0;;
		response.setContentType("text/html;charset=utf-8");
//		String callback = request.getParameter("callback");
		String orderBy = request.getParameter("orderBy");
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(startRow))
			startRow = "0";
		if (StringUtils.isBlank(pageSize))
			pageSize = "30";
		String newrssiBValue = request.getParameter("newrssiBValue");
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String searchText = (String) paramMap.get("searchText");
			String houseIeee = (String) paramMap.get("houseIeee");
			String type = (String) paramMap.get("type");
			if(StringUtils.isBlank(houseIeee)) {
				String resultJson = "{\"result\":-1}";
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
				return;
			}
			String time = null;
			Map<String, Object> rssiMap = cacheRssiImtly.get(houseIeee);
			if(rssiMap != null) {
				time = (String) rssiMap.get("time");
			}
			else {
				time = (new Date()).toString();
			}
//			if(datemap.get("time")==null){
//				datemap.put("time", new Date());
//			}
			rssiList = recordService.queryNewRSSI(searchText, Integer.parseInt(startRow), Integer.parseInt(pageSize), orderBy, houseIeee, type, time, paramMap );
			if(StringUtils.isBlank(newrssiBValue))
				newrssiBValue = "-70";
//			if(datemap.get("time")==null){
//				datemap.put("time", new Date());
//			}
			int rssiBValueCount = recordService.getNewNormalDeviceCount(searchText, Integer.parseInt(newrssiBValue), houseIeee, type, time, paramMap);
			String rssiJson = JSON.toJSONStringWithDateFormat(rssiList, "yyyy-MM-dd HH:mm:ss");
			String resultJson = "{\"result\":1,\"rssiList\":" + rssiJson + ",\"nBValueCount\":" + rssiBValueCount + "}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getRSSI",e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getNewRssiCount")
	public void getNewRssiCount(String json, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String searchText = (String) paramMap.get("searchText");
			String houseIeee = (String) paramMap.get("houseIeee");
			String type = (String) paramMap.get("type");
			if(StringUtils.isBlank(houseIeee)) {
				resultJson = "{\"result\":-1}";
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
				return;
			}
			String time = null;
			Map<String, Object> rssiMap = cacheRssiImtly.get(houseIeee);
			if(rssiMap != null) {
				time = (String) rssiMap.get("time");
			}
			else {
				time = (new Date()).toString();
			}
//			if(datemap.get("time")==null){
//				datemap.put("time", new Date());
//			}
			int total = recordService.getNewRSSICount(searchText, houseIeee,type, time, paramMap);
			resultJson = "{\"result\":1,\"total\":" + total + "}";
			out.println("{\"request_id\": 1234, \"response_params\":"+ resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getNewRssiCount",e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":"
					+ resultJson + "}");
		}
	}
	
	
}
