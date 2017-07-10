package sy.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jbx.test.TestLog4j;
import org.smarthome.model.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sy.model.House;
import sy.model.Houseieee;
import sy.model.Houseservice;
import sy.model.Messagehistory;
import sy.model.Modenode;
import sy.model.Proxyserver;
import sy.pageModel.HouseInfo;
import sy.service.BrinServiceI;
import sy.service.HouseServiceI;
import sy.service.HouseWeixinServiceI;
import sy.service.HouseieeeServiceI;
import sy.service.ProxyserverServiceI;
import sy.util.AESCodec;
import sy.util.CharStream;
import sy.util.HouseieeeListener;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;
import zbHouse.pageModel.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


@Controller
@RequestMapping("/houseController")
@SuppressWarnings("all")
public class HouseController {
	private HouseServiceI houseService;
	private static final Logger LOGGER = Logger.getLogger(HouseController.class); 

	public static  Map<String,Integer> maponline = new HashMap<String,Integer>();	
	private static final String[] allowTypes = {".xls"};
	private List<Map> moveResultList = new ArrayList<Map>();
	private HouseieeeServiceI houseieeeService;
	private HouseWeixinServiceI weixinService;
	private ProxyserverServiceI proxyserverService;
	private BrinServiceI brinService;

	public BrinServiceI getBrinService() {
		return brinService;
	}
	@Autowired
	public void setBrinService(BrinServiceI brinService) {
		this.brinService = brinService;
	}

	public HouseWeixinServiceI getWeixinService() {
		return weixinService;
	}
	@Autowired
	public void setWeixinService(HouseWeixinServiceI weixinService) {
		this.weixinService = weixinService;
	}

	private int isMail = Integer.parseInt(PropertiesUtil.getProperty("is.cshc.mail"));

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
	 * 判断文件是否是允许上传的类型 @chengqin
	 * @param fileName
	 * @return
	 */
	public boolean isAllowTypes(String fileName){
		if (fileName!= null && !fileName.equals("")) {
			fileName = fileName.toLowerCase();
			for (String s : allowTypes) {
				if (fileName.endsWith(s)) {
					return true;
				}
			}
		} return false;
	}
	public static List<House> getHouses() throws Exception{
		List<House> list = new ArrayList<House>();
		House house1 = new House("小窝1","CS6","00137A0000001555","厦门");
		House house2 = new House("小窝2","CS6","00137A0000001222","厦门");
		House house3 = new House("小窝3","CS6","00137A0000001333","厦门");
		list.add(house1);
		list.add(house2);
		list.add(house3);
		return list;
	}
	/**
	 * 特殊接口，不能公用
	 * houseieee初始化注册，Z203启动时调用推送增加或修改
	 * 注册ieee地址的接口
http://192.168.1.72:8081/zigBeeDevice/houseController/add.do?json={"houseIEEE":"00137A00000117B5","name":"user2","longitude":"22","latitude":"33","networkAddress":"192.168.99.255","port":"81","description":"192.168.1.230","serverIp":"192.168.1.72"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A00000117B5
	 * @author: zhuangxd
	 * 时间：2014-5-21 下午4:43:52
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/add")
	public void add(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*houseService.test();
		return "showHouse";*/

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

		/*List<House> houseList = JSON.parseArray(json,House.class); 
        for (House house : houseList) { 
        	houseService.save(house);
        } */

		try {        	
			House house = JSON.parseObject(json,House.class);
			Houseieee houseieee = JSON.parseObject(json,Houseieee.class);
			Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class);
			House t = houseService.get(house);
			if (StringUtils.isBlank(house.getHouseIeee()) || house.getHouseIeee().length() < 16 ) {
				String resultJson = "{\"result\":0}";//成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
				return;
			}
			String resultJson = "{\"result\":0}";//成功1 失败0
			if (t == null) {
				/*if (StringUtils.isBlank(house.getHouseIeee()) || house.getHouseIeee().length() != 16) {
            		return;
            	}*/
				if (house.getNetworkAddress() == null) {
					house.setNetworkAddress("192.168.99.255"); // 默认值
					house.setDescription("192.168.99.255"); // 默认值
				}
				// 客户端公网ip地址
				/*String ipAddress = getAddress(request);
            	house.setNetworkAddress(ipAddress);
            	house.setPort("81");*/
				if (house.getClientCode() == null) {
					house.setClientCode("CS6");
				}
				houseService.save(house);        		
				Houseieee t2 = houseieeeService.get(houseieee);
				if (t2 == null) {
					houseieee.setSecretKey(houseieee.getHouseIeee().substring(6));
					if(houseieee.getVendorCode()==null||"".equals(houseieee.getVendorCode())){
						houseieee.setVendorCode("NETVOX");
					}
					if(houseieee.getEncodemethod()==null||"".equals(houseieee.getEncodemethod())){
						houseieee.setEncodemethod("0");
					}
					houseieeeService.save(houseieee);
					HouseieeeListener.houseieeeMap.put(houseieee.getHouseIeee(), houseieee);
				}
				proxyserver.setType("0");
				proxyserver.setSecondType("0");
				Proxyserver t3 = proxyserverService.get(proxyserver);
				if (t3 == null) {
					//                	proxyserver.setServerIp("218.104.133.242");
					proxyserver.setServerPort("5222");
					proxyserverService.save(proxyserver);
				}            	
				proxyserver.setType("1");
				proxyserver.setSecondType("0");
				Proxyserver t4 = proxyserverService.get(proxyserver);
				if (t4 == null) {
					//            		proxyserver.setServerIp("218.104.133.242");
					//                	proxyserver.setServerPort("8081");
					proxyserver.setServerPort(PropertiesUtil.getProperty("cloud.port"));
					HouseieeeListener.houseieeeProxyserverMap.put(proxyserver.getHouseIeee(), proxyserver.getServerIp());
					proxyserverService.save(proxyserver);
				}
				proxyserver.setType("2");
				proxyserver.setSecondType("0");
				Proxyserver t5 = proxyserverService.get(proxyserver);
				if (t5 == null) {
					//            		proxyserver.setServerIp("218.104.133.242");
					proxyserver.setServerPort("9090");
					proxyserverService.save(proxyserver);
				}

				resultJson = "{\"result\":1}";//成功1 失败0

				// 家庭注册时，默认开启shc监控邮箱
				Houseservice houseservice = new Houseservice();
				houseservice.setDefaultemail(PropertiesUtil.getProperty("mail.to"));
				houseservice.setHouseIeee(house.getHouseIeee());
				houseService.openshc(houseservice);

				/*houseService.addDeviceAttributeHistoryTable(house.getHouseIeee());
        		houseService.addDeviceOperateHistoryTable(house.getHouseIeee());
        		houseService.addDeviceWarnHistoryTable(house.getHouseIeee());*/
			} else {
				// houseieee已存在，不做处理
				resultJson = "{\"result\":-1}";//成功1 失败0
				// 客户端公网ip地址
				//String ipAddress = getAddress(request);
				/*t.setNetworkAddress(ipAddress);
            	t.setPort("81");*/
				if (json !=null && !json.contains("regionCode")) {
					house.setRegionCode(null);
				}
				if (json !=null && !json.contains("isonline")) {
					house.setIsonline(null);
				}
				if (json !=null && !json.contains("enableFlag")) {
					house.setEnableFlag(null);
					houseieee.setEnableFlag(null);
					proxyserver.setEnableFlag(null);
				}
				if (json !=null && !json.contains("IPK_version")) {
					house.setIPK_version(null);
				}
				/*if (house.getDescription() != null) {
        			t.setDescription(house.getDescription());
        		}*/
				if (house.getIPK_version() != null) {
					t.setIPK_version(house.getIPK_version());
				}
				if (house.getLatitude() != null) {
					t.setLatitude(house.getLatitude());
				}
				if (house.getLongitude() != null) {
					t.setLongitude(house.getLongitude());
				}
				if (house.getName() != null) {
					t.setName(house.getName());
				}
				if(house.getEnableFlag()!=null){        			
					t.setEnableFlag(house.getEnableFlag());
				}
				if (house.getClientCode() != null) {
					t.setClientCode(house.getClientCode());
				}
				// 这个一定要注释
				/*if (house.getIsonline() != null) {
        			t.setIsonline(house.getIsonline());
        		}*/
				if (house.getRegionCode() != null) {
					t.setRegionCode(house.getRegionCode());
				}
				t.setLasttime(new Date());
				houseService.update(t);

				Houseieee t2 = houseieeeService.get(houseieee);
				if (t2 != null) {
					if (houseieee.getName() != null) {
						t2.setName(houseieee.getName());
					}
					/*if (houseieee.getDescription() != null) {
            			t2.setDescription(houseieee.getDescription());
            		} */           		
					//            		if(houseieee.getVendorCode()!=null){
					//            			t2.setVendorCode(houseieee.getVendorCode());
					//            		}
					/*if(houseieee.getEncodemethod()!=null){//--------------------改动的地方
            			t2.setEncodemethod(houseieee.getEncodemethod());
            		}*/
					if(houseieee.getEnableFlag()!=null){//-----------------改动的地方            			
						t2.setEnableFlag(house.getEnableFlag());
					}
					t2.setLasttime(new Date());
					houseieeeService.update(t2);
					HouseieeeListener.houseieeeMap.put(t2.getHouseIeee(), t2);
				}
				proxyserver.setType("0");
				proxyserver.setSecondType("0");
				Proxyserver t3 = proxyserverService.get(proxyserver);
				if (t3 != null) {
					if (proxyserver.getName() != null) {
						t3.setName(proxyserver.getName());
					}
					/*if (proxyserver.getDescription() != null) {
            			t3.setDescription(proxyserver.getDescription());
            		}*/
					if(proxyserver.getEnableFlag()!=null){//------------------改动的地方
						t3.setEnableFlag(proxyserver.getEnableFlag());
					}
					/*if(proxyserver.getServerIp()!=null){
            			t3.setServerIp(proxyserver.getServerIp());
            		}*/
					t3.setLasttime(new Date());
					proxyserverService.update(t3);
				}            	
				proxyserver.setType("1");
				proxyserver.setSecondType("0");
				Proxyserver t4 = proxyserverService.get(proxyserver);
				if (t4 != null) {
					if (proxyserver.getName() != null) {
						t4.setName(proxyserver.getName());
					}
					/*if (proxyserver.getDescription() != null) {
            			t4.setDescription(proxyserver.getDescription());
            		}*/
					if(proxyserver.getEnableFlag()!=null){//------------------改动的地方
						t4.setEnableFlag(proxyserver.getEnableFlag());
					}
					/*if(proxyserver.getServerIp()!=null){
            			t4.setServerIp(proxyserver.getServerIp());
            		}*/
					t4.setLasttime(new Date());
					HouseieeeListener.houseieeeProxyserverMap.put(t4.getHouseIeee(), t4.getServerIp());
					proxyserverService.update(t4);
				}
				proxyserver.setType("2");
				proxyserver.setSecondType("0");
				Proxyserver t5 = proxyserverService.get(proxyserver);
				if (t5 != null) {
					if (proxyserver.getName() != null) {
						t5.setName(proxyserver.getName());
					}
					/*if (proxyserver.getDescription() != null) {
            			t5.setDescription(proxyserver.getDescription());
            		}*/
					if(proxyserver.getEnableFlag()!=null){//------------------改动的地方
						t5.setEnableFlag(proxyserver.getEnableFlag());
					}
					/*if(proxyserver.getServerIp()!=null){
            			t5.setServerIp(proxyserver.getServerIp());
            		}*/
					t5.setLasttime(new Date());
					proxyserverService.update(t5);
				}
				/*house.setId(t.getId());
            	houseService.update(house);*/
				/*String resultJson = "{\"result\":0}";//成功1 失败0
    			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
    			return;*/
			}

			//        	// 往配置文件指定的云端服务器注册houseieee
			//        	String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
			//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
			//			String uri = request.getRequestURI();
			//			String serverHost = request.getServerName();
			//			int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
			//			if(cloudMain == 1 && !cloudAddress.equals(serverHost) && !cloudAddress.equals("localhost") 
			//					&& !cloudAddress.equals("127.0.0.1")) {
			//	        	String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
			//	        	Map<String, String[]> rMap = request.getParameterMap();
			//				if(rMap != null) {
			//					Map<String, Object> pMap = new HashMap<String, Object>();
			//					Iterator<String> it=rMap.keySet().iterator();   
			//					while(it.hasNext()){ 
			//						String key=it.next();  
			//						//post方法去除json参数
			//						String value = ((Object[])(rMap.get(key)))[0].toString();
			//						pMap.put(key, value);
			//					}
			//					String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");					
			//				}
			//			}
			//			
			//			// 往serverip字段指定的云端服务器注册houseieee
			//			cloudAddress = proxyserver.getServerIp();
			//			if(cloudMain == 1 && !cloudAddress.equals(serverHost) && !cloudAddress.equals("localhost") 
			//					&& !cloudAddress.equals("127.0.0.1")) {
			//	        	String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
			//	        	Map<String, String[]> rMap = request.getParameterMap();
			//				if(rMap != null) {
			//					Map<String, Object> pMap = new HashMap<String, Object>();
			//					Iterator<String> it=rMap.keySet().iterator();   
			//					while(it.hasNext()){ 
			//						String key=it.next();  
			//						//post方法去除json参数
			//						String value = ((Object[])(rMap.get(key)))[0].toString();
			//						pMap.put(key, value);
			//					}
			//					String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");					
			//				}
			//			}

			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("add",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

		//        String resultJson = "{\"result\":\"1\"}";

		//		out.flush();

		//		System.out.println("resultJson==" + resultJson);

		//		return "saveHouse";
	}
	/**
	 * 批量注册添加house
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/addBatch")
	public void addBatch(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {		 
		int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain")); 
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter(); 		 
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);//转义字符
			HouseInfo houseInfo = mapper.readValue(json, HouseInfo.class);

			//判断服务器的数量，先解析xml获取服务器数量限制，若无配置文件则无法注册
			if(cloudMain == 1) {
				int exceedResult = isHouseExceed(houseInfo.getProxyserver().getServerIp(), houseInfo.getHouse().size()); 
				if(exceedResult != 1) {
					out.println("{\"request_id\": 1234, \"response_params\":{\"result\":" + exceedResult + "}}");
					return;
				}
			}

			//			HouseInfo houseInfo = JSON.parseObject(json,HouseInfo.class);
			// 往配置文件指定的云端服务器注册houseieee
			String cloudAddress = PropertiesUtil.getProperty("proxy.ip");
			String cloudPort = PropertiesUtil.getProperty("proxy.port");
			String uri = request.getRequestURI();
			String serverHost = request.getServerName();
			//InputStream inputStream = null;
			boolean proxySuccess = false, xmppSuccess = false;

			if(cloudMain == 1 
					&& !cloudAddress.equals(serverHost) 
					&& !cloudAddress.equals("localhost") 
					&& !cloudAddress.equals("127.0.0.1")
					&& !serverHost.equals("localhost") 
					&& !serverHost.equals("127.0.0.1")) {
				String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
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
					String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");					
					Map<String, Object> resultMap = JSON.parseObject(rsStr, Map.class);
					int result = Integer.parseInt(((Map) resultMap.get("response_params")).get("result").toString()); 
					if(result == -1 || result == 1)
						proxySuccess = true;
				}
			}
			else
				proxySuccess = true;

			// 往serverip字段指定的云端服务器注册houseieee
			//cloudAddress = proxyserver.getServerIp();
			cloudAddress = houseInfo.getProxyserver().getServerIp();
			if(cloudMain == 1) {
				if(!cloudAddress.equals(serverHost)) { 
					if(!cloudAddress.equals("localhost") 
							&& !cloudAddress.equals("127.0.0.1")
							&& !serverHost.equals("localhost") 
							&& !serverHost.equals("127.0.0.1")) {
						String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
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
							String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
							Map<String, Object> resultMap = JSON.parseObject(rsStr, Map.class);
							int result = Integer.parseInt(((Map) resultMap.get("response_params")).get("result").toString()); 
							if(result == -1 || result == 1)
								xmppSuccess = true;
						}
					}
					else
						xmppSuccess = true;
				} else
					xmppSuccess = false;
			} else
				xmppSuccess = true;

			String resultJson = "{\"result\":1}";//成功1 失败0
			if(proxySuccess && xmppSuccess) {
				int status = houseService.insertExeclBatch(houseInfo);
				if(cloudMain==1&&status>0){
					//是否添加思维盒标识字段
					Boolean thinkingbox = Boolean.parseBoolean(request.getParameter("thinkingbox"));
					if(thinkingbox){
						//存放创建拾联账号失败的houseIEEE
						List<String> faildHouseIeee = new ArrayList<>();
						for(House house:houseInfo.getHouse()){
							int result = houseService.addSLAccount(house);
							if(result!=0){
								faildHouseIeee.add(house.getHouseIeee());
							}
						}
						resultJson = "{\"result\":1,\"\":"+JSON.toJSONString(faildHouseIeee)+"}";
					}
				}
			}
			else {
				resultJson = "{\"result\":0}";//成功1 失败0
			}
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("addBatch",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
		}
	}

	@RequestMapping("/downexcel")
	public void downexcel(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();

		try {
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("家庭列表模板一");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow(0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式

			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("家的名称");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("客户代码");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("IEEE地址");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
			cell.setCellValue("地区");
			cell.setCellStyle(style);

			List list = HouseController.getHouses();
			for (int i = 0; i < list.size(); i++)
			{
				row = sheet.createRow(i + 1);
				House house = (House) list.get(i);
				// 第四步，创建单元格，并设置值
				row.createCell((short) 0).setCellValue(house.getName());
				row.createCell((short) 1).setCellValue(house.getClientCode());
				row.createCell((short) 2).setCellValue(house.getHouseIeee());
				row.createCell((short) 3).setCellValue(house.getRegionCode());
			}
			// 第六步，将文件存到指定位置

			FileOutputStream fout = new FileOutputStream("E:/house.xls");
			wb.write(fout);
			fout.close();

			String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("downexcel",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
		}
	}

	/**
	 * 家庭注册模块的手工注册家
	 * @author: zhuangxd
	 * 时间：2014-7-31 下午5:23:17
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/addmany")
	public void addmany(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();  
		String resultJson = null;
		try {        	
			House house = JSON.parseObject(json, House.class);
			int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
			int result = 1;
			//首先判断是否代理服务器，是则往拾联添加帐号,否则跳过此步骤
			if(cloudMain==1){
				Boolean thinkingbox = Boolean.parseBoolean(request.getParameter("thinkingbox"));
				LOGGER.info("thinkingbox:"+thinkingbox);
				if(result == 1&&thinkingbox){
					LOGGER.info("添加思维盒");
					int status = houseService.addSLAccount(house);
					if(status==0){
						result = 1;
					}else{
						result = -2;
						resultJson = "{\"result\":" + result + "}";//1成功 0失败
						out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
						return;
					}
				}
				result = addRelative(json, request.getServerName(), request.getRequestURI(), request.getParameterMap(), 1, request.getParameter("userId"));
				//网关注册失败，删除已经建立的拾联帐号
				if(result!=1){
					houseService.deleteSLAccount(house);
				}
			}else{
				result = addRelative(json, request.getServerName(), request.getRequestURI(), request.getParameterMap(), 1, request.getParameter("userId"));
			}
			resultJson = "{\"result\":" + result + "}";//1成功 0失败
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("addmany",e);
			resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}

	/**
	 * 特殊接口，不能公用
	 * 修改houseieee在线状态 isonline默认家离线，1在线，0离线
http://192.168.1.123:8080/zigBeeDevice/houseController/update.do?json={"houseIEEE":"00137A000000DBB5","isonline":"1"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A000000DBB5
	 * 接收Z203修改推送 不推送外网地址
	 * @author: zhuangxd
	 * 时间：2014-6-27 下午4:02:53
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void update(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		try {
			House house = JSON.parseObject(json,House.class); 
			Houseieee houseieee = JSON.parseObject(json,Houseieee.class);
			Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class);
			House t = houseService.get(house);
			String resultJson = "{\"result\":0}";//成功1 失败0
			if (t != null) {
				// 客户端公网ip地址
				//String ipAddress = getAddress(request);
				/*t.setNetworkAddress(ipAddress);
            	t.setPort("81");*/
				if (json !=null && !json.contains("regionCode")) {
					house.setRegionCode(null);
				}
				if (json !=null && !json.contains("isonline")) {
					house.setIsonline(null);
				}
				if (json !=null && !json.contains("enableFlag")) {
					house.setEnableFlag(null);
					houseieee.setEnableFlag(null);
					proxyserver.setEnableFlag(null);
				}
				if (json !=null && !json.contains("IPK_version")) {
					house.setIPK_version(null);
				}
				/*if (house.getDescription() != null) {
        			t.setDescription(house.getDescription());
        		}*/
				if (house.getIPK_version() != null) {
					t.setIPK_version(house.getIPK_version());
				}
				if (house.getLatitude() != null) {
					t.setLatitude(house.getLatitude());
				}
				if (house.getLongitude() != null) {
					t.setLongitude(house.getLongitude());
				}
				if (house.getName() != null) {
					t.setName(house.getName());
				}
				if(house.getEnableFlag()!=null){        			
					t.setEnableFlag(house.getEnableFlag());
				}
				if (house.getClientCode() != null) {
					t.setClientCode(house.getClientCode());
				}
				// 这个一定要注释
				/*if (house.getIsonline() != null) {
        			t.setIsonline(house.getIsonline());
        		}*/
				if (house.getRegionCode() != null) {
					t.setRegionCode(house.getRegionCode());
				}
				t.setLasttime(new Date());
				houseService.update(t);

				Houseieee t2 = houseieeeService.get(houseieee);
				if (t2 != null) {
					if (houseieee.getName() != null) {
						t2.setName(houseieee.getName());
					}
					/*if (houseieee.getDescription() != null) {
            			t2.setDescription(houseieee.getDescription());
            		} */           		
					/*if(houseieee.getVendorCode()!=null){//-------------------改动的地方
            			t2.setVendorCode(houseieee.getVendorCode());
            		}*/
					/*if(houseieee.getEncodemethod()!=null){//--------------------改动的地方
            			t2.setEncodemethod(houseieee.getEncodemethod());
            		}*/
					if(houseieee.getEnableFlag()!=null){//-----------------改动的地方            			
						t2.setEnableFlag(house.getEnableFlag());
					}
					t2.setLasttime(new Date());
					houseieeeService.update(t2);
					HouseieeeListener.houseieeeMap.put(t2.getHouseIeee(), t2);
				}
				proxyserver.setType("0");
				proxyserver.setSecondType("0");
				Proxyserver t3 = proxyserverService.get(proxyserver);
				if (t3 != null) {
					if (proxyserver.getName() != null) {
						t3.setName(proxyserver.getName());
					}
					/*if (proxyserver.getDescription() != null) {
            			t3.setDescription(proxyserver.getDescription());
            		}*/
					if(proxyserver.getEnableFlag()!=null){//------------------改动的地方
						t3.setEnableFlag(proxyserver.getEnableFlag());
					}
					/*if(proxyserver.getServerIp()!=null){
            			t3.setServerIp(proxyserver.getServerIp());
            		}*/
					t3.setLasttime(new Date());
					proxyserverService.update(t3);
				}            	
				proxyserver.setType("1");
				proxyserver.setSecondType("0");
				Proxyserver t4 = proxyserverService.get(proxyserver);
				if (t4 != null) {
					if (proxyserver.getName() != null) {
						t4.setName(proxyserver.getName());
					}
					/*if (proxyserver.getDescription() != null) {
            			t4.setDescription(proxyserver.getDescription());
            		}*/
					if(proxyserver.getEnableFlag()!=null){//------------------改动的地方
						t4.setEnableFlag(proxyserver.getEnableFlag());
					}
					/*if(proxyserver.getServerIp()!=null){
            			t4.setServerIp(proxyserver.getServerIp());
            		}*/
					t4.setLasttime(new Date());
					HouseieeeListener.houseieeeProxyserverMap.put(t4.getHouseIeee(), t4.getServerIp());
					proxyserverService.update(t4);
				}
				proxyserver.setType("2");
				proxyserver.setSecondType("0");
				Proxyserver t5 = proxyserverService.get(proxyserver);
				if (t5 != null) {
					if (proxyserver.getName() != null) {
						t5.setName(proxyserver.getName());
					}
					/*if (proxyserver.getDescription() != null) {
            			t5.setDescription(proxyserver.getDescription());
            		}*/
					if(proxyserver.getEnableFlag()!=null){//------------------改动的地方
						t5.setEnableFlag(proxyserver.getEnableFlag());
					}
					/*if(proxyserver.getServerIp()!=null){
            			t5.setServerIp(proxyserver.getServerIp());
            		}*/
					t5.setLasttime(new Date());
					proxyserverService.update(t5);
				}
				/*house.setId(t.getId());
            	houseService.update(house);*/
				/*String resultJson = "{\"result\":0}";//成功1 失败0
    			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
    			return;*/
				resultJson = "{\"result\":1}";//成功1 失败0
			}
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	/**
	 * 接收Z203推送的公网ip地址和局域网ip地址 
	 * @author: zhuangxd
	 * 时间：2014-12-17 下午3:06:11
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/updateWanAndLanIp")
	public void updateWanAndLanIp(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		try {
			House house = JSON.parseObject(json,House.class); 
			House t = houseService.get(house);
			String resultJson = "{\"result\":0}";//成功1 失败0
			if (t != null) {
				if (house.getWanIp() != null) {
					t.setWanIp(house.getWanIp());
				}
				if (house.getLanIp() != null) {
					t.setLanIp(house.getLanIp());
				}
				t.setLasttime(new Date());
				houseService.update(t);
				resultJson = "{\"result\":1}";//成功1 失败0
			} else { // 
				resultJson = "{\"result\":-1}";//成功1 失败0
			}
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateWanAndLanIp",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	/**
	 * 修改心跳发送次数
	 * 使用203的心跳来判断是否离线，时间频率是5分钟判断一次，如果连续3次没有收到心跳，那么判定该CSHC设备为离线状态,
                  如果收到心跳或者其他推送上来的数据，那么判断该HouseIEEE地址对应的CSHC设备为在线状态
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/updateCountOnline")
	public void updateCountOnline(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 		  
		try {
			House house = JSON.parseObject(json,House.class); 
			//        	House t = houseService.get(house);
			String resultJson = "{\"result\":1}";//成功1 失败0
			//        	if (t != null){
			maponline.put(house.getHouseIeee(), 3);
			//			} else {
			//			}
			out.println(resultJson);
		} catch (Exception e) {
			LOGGER.info("updateCountOnline", e);
			String resultJson = "{\"result\":0}";
			out.println(resultJson);
		}

	}

	@RequestMapping("/updateCountOnlineBatch")
	public void updateCountOnlineBatch(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 		  
		try {
			List<House> houseList = JSONObject.parseArray(json, House.class); 
			String resultJson = "{\"result\":1}";//成功1 失败0
			for(House house:houseList){
				maponline.put(house.getHouseIeee(), 3);
			}
			out.println(resultJson);
		} catch (Exception e) {
			LOGGER.info("updateCountOnline", e);
			String resultJson = "{\"result\":0}";
			out.println(resultJson);
		}  
	}

	/**
	 * 同步代理服务器是否在线接口
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/syncProxyServerOnline")
	public void syncProxyServerOnline(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 		  
		try {
			List<Map> houseIEEEs = JSON.parseArray(json, Map.class);
			for(Map houseIEEE : houseIEEEs) {
				String hIEEE = (String) houseIEEE.get("houseIEEE");
				Integer times = (Integer) houseIEEE.get("times");
				if(times == null) {
					maponline.put(hIEEE, 3);
				}
				else if(times >= 0){
					maponline.put(hIEEE, times);
				}
				else {
					maponline.put(hIEEE, 0);
				}
			}
			out.println("{\"result\":1}");
		} catch (Exception e) {
			LOGGER.info("syncProxyServerOnline", e);
		}
	}

	/**
	 * 接收xmpp批量修改houseieee在线状态 isonline默认家离线，1在线，0离线
	 * http://192.168.1.123:8080/zigBeeDevice/houseController/updateAllIsonline.do?json=["00137A000000DBB5","00137A0000010136"]&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A000000DBB5
	 * @author: zhuangxd
	 * 时间：2014-7-11 上午11:00:59
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/updateAllIsonline")
	public void updateAllIsonline(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		//		http://192.168.1.123:8080/zigBeeDevice/houseController/updateAllIsonline.do?json=["00137A000000DBB5","00137A000000DB9D","00137A000001013B","00137A0000063101"]&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A000000DBB5
		//		http://192.168.1.123:8080/zigBeeDevice/houseController/updateAllIsonline.do?json=["00137A000001013B","00137A0000063101"]&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A000000DBB5
		//		http://192.168.1.123:8080/zigBeeDevice/houseController/updateAllIsonline.do?json=[]&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A000000DBB5

		PrintWriter out=response.getWriter();  

		try {
			List<String> list = JSON.parseArray(json,String.class);
			// SHC断开(Z203在线变离线)   发送邮件
			houseService.abtainEmailAndSend(list);
			// 所有在线改成离线
			//        	houseService.updateAllIsonline();
			// 指定的houseieee数组都改成在线,不在改离线
			//houseService.updateAllIsonline(list);
			String resultJson = "{\"result\":0}";//成功1 失败0
			resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateAllIsonline",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		}        
	}
	/**
	 * 家庭概况
	 * 推送Z203是否在线，推送IP地址
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/updateZ203Isonline")
	public void updateZ203Isonline(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 		
		//		http://192.168.1.123:8080/zigBeeDevice/houseController/updateZ203Isonline.do?json=[{"houseieee":"00137A00000119B5","networkaddress":"218.104.133.227"},{"houseieee":"00137A0000012E2B","networkaddress":"218.104.133.227"},{"houseieee":"00137A00000117B5","networkaddress":"218.104.133.227"},{"houseieee":"00137A0000012D51","networkaddress":"218.104.133.227"}]	
		//		http://192.168.1.123:8080/zigBeeDevice/houseController/updateZ203Isonline.do?json=[{"houseieee":"00137A00000119B5","networkaddress":"218.104.133.227"},{"houseieee":"00137A0000012E2B","networkaddress":"218.104.133.227"}]
		//		http://192.168.1.123:8080/zigBeeDevice/houseController/updateZ203Isonline.do?json=[]			
		try {
			final Map map = JSON.parseObject(json,Map.class);
			final List<Map> liston=(List<Map>) map.get("houseonIeees");
			final List<Map> listoff=(List<Map>) map.get("houseoffIeees");
			if(isMail == 1) {
				Thread th = new Thread() {
					@Override
					public void run(){
						try {
							houseService.abtainEmailAndSendSHC(liston,listoff);
							LOGGER.info("发送邮件结束");
						} catch (Exception e){
							LOGGER.info("updateZ203Isonline",e);
						}
					}
				};
				th.setName("sendEmailThread");
				th.start();
			}
			houseService.updateZ203Isonline(liston, listoff,request.getParameter("xmppIp"));
			LOGGER.info("推送Z203在线状态结束");
			String resultJson = "{\"result\":0}";//成功1 失败0
			resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateZ203Isonline",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	/**
	 * 家庭注册模块的手工注册的家信息修改
	 * @author: zhuangxd
	 * 时间：2014-7-31 下午5:22:37
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/update2")
	public void update2(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();  
		String resultJson = null;
		try {
			int result = updateRelative(json, request.getServerName(), request.getRequestURI(), request.getParameterMap(), 1, request.getParameter("userId"));
			resultJson = "{\"result\":" + result + "}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("update2",e);
			resultJson = "{\"result\":0}";//成功1 失败0
			//			    			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
			//			    			out.println("({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	/*关键字搜索*/
	@RequestMapping("/findkey")
	public void findkey(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 
		Map<String, Object> map = new HashMap<String, Object>();
		response.setContentType("text/html;charset=utf-8");  
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		try {
			//        	if(StringUtils.isBlank(startRow))
			startRow = "0";
			//			if(StringUtils.isBlank(pageSize))
			pageSize = "30";
			map=JSON.parseObject(json);	
			House house = JSON.parseObject(json,House.class); 
			String orderBy = request.getParameter("orderBy");
			//        	if (json !=null && !json.contains("regionCode")) {
			//        		house.setRegionCode(null);
			//        	}
			//        	if (json !=null && !json.contains("isonline")) {
			//        		house.setIsonline(null);
			//        	}if (json !=null && !json.contains("regionCode")) {
			//        		house.setRegionCode(null);
			//        	}
			//        	if (json !=null && !json.contains("enableFlag")) {
			//        		house.setEnableFlag(null);
			//        	}
			if(house.getRegionCode()!=null){
				house.setRegionCode(house.getRegionCode());
			}
			if(house.getHouseIeee()!=null){
				house.setHouseIeee(house.getHouseIeee());
			}
			if(house.getClientCode()!=null){
				house.setClientCode(house.getClientCode());
			}
			if(house.getEnableFlag()!=null){
				house.setEnableFlag(house.getEnableFlag());
			}
			if(house.getName()!=null){
				house.setName(house.getName());
			}
			if (StringUtils.isNotBlank(orderBy)) {
				house.setOrderBy(orderBy);
			}

			//        	House t = houseService.find(house);
			List<House> t = houseService.findkeyword(startRow, pageSize,house );
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
			//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("findkey",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   

		// 多表关联
		/*  try {
        	House house = JSON.parseObject(json,House.class); 
//        	House t = houseService.find(house);
        	List<House> t = houseService.findList(house);
        	List<Object[]> s = houseService.findListSql(house);
        	for (Object[] objects : s) {
        		House house2 = (House)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		House house2 = (House)objects[0];
        		DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear = (DevicewarnhistoryHouseidYear)objects[1];
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
	 * 查找所有houseieee列表(ajax post方式) 关键字模糊查询
	 *  可靠性平台家庭概况查询接口
	 * @author: zhuangxd
	 * 时间：2014-6-27 下午4:05:08
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/find2")
	public void find2(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		String userid=request.getParameter("userid");
		try {
			House house = JSON.parseObject(json,House.class); 
			String orderBy = request.getParameter("orderBy");
			if (json !=null && !json.contains("regionCode")){
				house.setRegionCode(null);
			}
			if (json !=null && !json.contains("isonline")) {
				house.setIsonline(null);
			}
			if (StringUtils.isNotBlank(orderBy)) {
				house.setOrderBy(orderBy);
			}
			if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
				house.setStartRow(Integer.parseInt(request.getParameter("startRow")));
				house.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
			}
			//        	List<House> t = houseService.findList(house);
			List<Map> t = houseService.findList(house,userid);
			/*for (Iterator iterator = t.iterator(); iterator.hasNext();) {
					Map map = (Map) iterator.next();
					if (map.get("houseIeee")!=null) {
						if (HouseController.maponline.get(map
								.get("houseIeee")) != null) {
						map.put("isonline", HouseController.maponline.get(map
								.get("houseIeee")) > 0 ? 1 : 0);
					} else {
						map.put("isonline", 0);
					}
				}

			}*/
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
			//       	out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("find2",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}   

		// 多表关联
		/*  try {
        	House house = JSON.parseObject(json,House.class); 
//        	House t = houseService.find(house);
        	List<House> t = houseService.findList(house);
        	List<Object[]> s = houseService.findListSql(house);
        	for (Object[] objects : s) {
        		House house2 = (House)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		House house2 = (House)objects[0];
        		DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear = (DevicewarnhistoryHouseidYear)objects[1];
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
	 * 获取记录总数
	 * @author: zhuangxd
	 * 时间：2014-8-5 上午11:20:29
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getListoryCount")
	public void getListoryCount(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8"); 

		try {
			House house = JSON.parseObject(json,House.class);
			Map t = houseService.getListoryCount(house);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("getListoryCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	@RequestMapping("/findpage")
	public void findpage(String json,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter out=response.getWriter(); 
		Json j = new Json();
		DataGrid dg = new DataGrid();
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		String orderBy = request.getParameter("orderBy");
		try {
			if(StringUtils.isBlank(startRow))
				startRow = "0";
			if(StringUtils.isBlank(pageSize))
				pageSize = "30";
			map=JSON.parseObject(json);			
			List<House> t = houseService.findList(startRow, pageSize, orderBy, map);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("findpage",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");

			//			dg.setStatus(0);
			//			dg.setStatus_msg("fail");
			//			j.setResponse_params(dg);
		}
		//		writeJson(j,callback,response);
		//request.setAttribute("j",j);			

	}

	@RequestMapping("/getCount")	
	public void getCount(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();
		DataGrid dg = new DataGrid();
		try {
			map=JSON.parseObject(json);	
			map.put("total", houseService.getCount(map));
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getCount",e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);		
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


	/**
	 * 该接口不能修改，不能公用
	 * 查找所有houseieee列表(getjson) 安卓获取外网直连地址的接口
	 * @author: zhuangxd
	 * 时间：2014-6-27 下午4:05:08
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  

		try {
			House house = JSON.parseObject(json,House.class); 
			String orderBy = request.getParameter("orderBy");
			if (json !=null && !json.contains("regionCode")) {
				house.setRegionCode(null);
			}
			if (json !=null && !json.contains("isonline")) {
				house.setIsonline(null);
			}
			if (json !=null && !json.contains("enableFlag")) {
				house.setEnableFlag(null);
			}
			if (StringUtils.isNotBlank(orderBy)) {
				house.setOrderBy(orderBy);
			}
			//        	House t = houseService.find(house);
			List<House> t = houseService.findList2(house);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
			//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find", e);
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}   

		// 多表关联
		/*  try {
        	House house = JSON.parseObject(json,House.class); 
//        	House t = houseService.find(house);
        	List<House> t = houseService.findList(house);
        	List<Object[]> s = houseService.findListSql(house);
        	for (Object[] objects : s) {
        		House house2 = (House)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		House house2 = (House)objects[0];
        		DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear = (DevicewarnhistoryHouseidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}

	@RequestMapping("/findhouseAndhouseieee")
	public void findhouseAndhouseieee(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		try {
			House house = JSON.parseObject(json,House.class); 
			Houseieee houseieee = JSON.parseObject(json,Houseieee.class); 
			Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class); 
			String orderBy = request.getParameter("orderBy");
			if (json !=null && !json.contains("regionCode")) {
				house.setRegionCode(null);
			}
			if (json !=null && !json.contains("isonline")) {
				house.setIsonline(null);
			}

			if (json !=null && !json.contains("enableFlag")) {
				house.setEnableFlag(null);
				houseieee.setEnableFlag(null);
				proxyserver.setEnableFlag(null);
			}
			if(house.getEnableFlag()!=null){
				house.setEnableFlag(house.getEnableFlag());
				houseieee.setEnableFlag(houseieee.getEnableFlag());
				proxyserver.setEnableFlag(proxyserver.getEnableFlag());
			}
			//        	if (StringUtils.isNotBlank(orderBy)) {
			//        		house.setOrderBy(orderBy);
			//        	}

			List<Map> t = houseService.findhouseAndhouseieee(house,houseieee,proxyserver);
			//        	for (Object[] objects : t) {
			//        		House house2 = (House)objects[0];
			//        		Houseieee houseieee2 = (Houseieee)objects[1];
			//			}
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("findhouseAndhouseieee", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}   


	}
	/**
	 * 查询客户代码
	 * @param json
	 * @param request
	 * @param callback
	 * @param response
	 */
	@RequestMapping("/getReliClient")
	public void getReliClient(String json, HttpServletRequest request,String callback, HttpServletResponse response) {	
		DataGrid dg=new DataGrid();
		Json j = new Json();
		HashMap<String,Object>params=new HashMap<String, Object>();
		Map<String ,Object> map=JSON.parseObject(json, Map.class);
		try {
			List<Map> clientList=houseService.getReliClient(map);
			//			List<Map> clientList=houseieeeService.getReliClientByRegion();
			//			params.put("reliClient",list);
			params.put("clientList", clientList);
			j.setResponse_params(params);
		} catch (Exception e) {
			// TODO: handle exception
			dg.setStatus(0);dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);	

	}

	@RequestMapping("/getReliClientByregion")
	public  @org.springframework.web.bind.annotation.ResponseBody Map<String, Object>getReliClientByregion(String json,@RequestParam("code") String code,@RequestParam("region") String region,
			HttpServletRequest request,String callback, HttpServletResponse response) {	
		DataGrid dg=new DataGrid();
		Json j = new Json();
		HashMap<String,Object>params=new HashMap<String, Object>();
		try {
			List<Map> list=houseService.getReliClientByregion(code,region);
			params.put("datas",list);

		} catch (Exception e) {
		}

		return params;	

	}

	@RequestMapping("/getReliClientFromRegion")
	public void  getReliClientFromRegion(String json,
			HttpServletRequest request,String callback, HttpServletResponse response) {	
		DataGrid dg=new DataGrid();
		Json j = new Json();
		HashMap<String,Object>params=new HashMap<String, Object>();
		Map<String,Object> requestparamsMap=JSON.parseObject(json, Map.class);

		try {
			List<Map> list=houseService.getReliClientByregion(requestparamsMap.get("region"),requestparamsMap.get("userid"));
			params.put("datas",list);
			j.setResponse_params(params);
			writeJson(j,callback,response);
		} catch (Exception e) {
		}



	}




	/*关闭SHC*/
	@RequestMapping("/updatecloseshc")
	public void updatecloseshc(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		try {
			Houseservice houseservice = JSON.parseObject(json,Houseservice.class); 
			int t = houseService.closeshc(houseservice);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updatecloseshc", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}   


	}

	/*开启SHC监控*/
	@RequestMapping("/updateshc")
	public void updateshc(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		try {

			Houseservice houseservice = JSON.parseObject(json,Houseservice.class); 
			int t = houseService.openshc(houseservice);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateshc", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}   


	}
	/**
	 * 监控SHC服务弹框邮箱显示
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getshc")
	public void getshc(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		try {
			Houseservice houseservice = JSON.parseObject(json,Houseservice.class); 
			List<Houseservice> t = houseService.getshc(houseservice);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("getshc", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}   


	}

	/**
	 * 删除表数据，公用
	 * @author: zhuangxd
	 * 时间：2014-7-4 下午4:24:17
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  

		try {
			House house = JSON.parseObject(json,House.class); 
			Houseieee houseieee = JSON.parseObject(json,Houseieee.class);
			Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class);
			houseService.delete(house);
			if (house.getHouseIeee() != null) {
				houseieeeService.delete1(houseieee);
				HouseieeeListener.houseieeeMap.remove(houseieee.getHouseIeee());
				HouseieeeListener.houseieeeProxyserverMap.remove(proxyserver.getHouseIeee());
				proxyserverService.delete1(proxyserver);
			}
			String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete", e);
			//			e.printStackTrace();
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	/**
	 * 批量导入Excel文件：家的名称,houseIeee,客户代码,地区
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/importExceltoHouse")
	public void importExceltoHouse(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		List<House> houselist = new ArrayList<House>(); 
		Map paramMap = JSON.parseObject(json, Map.class);
		try {
			String spetor = File.separator;
			// 设置保存上传文件的目录
			String uploadDir = request.getSession().getServletContext().getRealPath(spetor + "cloudManageWeb/POI2Excel");
			//判断是multipart/form-data类型的数据
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 超过1M的字段数据采用临时文件缓存
				//					factory.setSizeThreshold(1024 * 1024);
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 最多上传30M数据
				//					upload.setSizeMax(1024 * 1024 * 30);
				upload.setSizeMax(1024 * 1024 * 30); 
				// 每个文件最大1M
				//					upload.setFileSizeMax(1024 * 1024 * 1);
				upload.setFileSizeMax(1024 * 1024 * 30); 
				// 采用默认的临时文件存储位置
				// diskFileItem.setRepositoryPath(...);
				// 设置上传的普通字段的名称和文件字段的文件名所采用的字符集编码
				upload.setHeaderEncoding("utf-8");
				//upload.setProgressListener(new UploadProgressListener(request));
				// 得到所有表单字段对象的集合
				List fileItems = null;
				try {
					fileItems = upload.parseRequest(request);
				}catch (FileSizeLimitExceededException e) {
					//						out.print("单个照片的大小不能超出1M"); 
					String resultJson = "{\"result\":-3}";//成功1 失败0 
					//						String callback = request.getParameter("callback");
					out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
					LOGGER.info("importExceltoHouse", e);
					return;
				}catch(SizeLimitExceededException e){
					//						out.print("所有照片的总大小不能超出30M"); 
					String resultJson = "{\"result\":-4}";//成功1 失败0 
					//						String callback = request.getParameter("callback");
					out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
					LOGGER.info("importExceltoHouse", e);
					return;
				}catch (FileUploadException e) {
					//						out.print("解析数据时出现如下问题");
					String resultJson = "{\"result\":0}";//成功1 失败0
					//						String callback = request.getParameter("callback");
					out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
					LOGGER.info("importExceltoHouse", e);
					return;
				}
				// 处理每个表单字段
				Iterator i = fileItems.iterator();
				ArrayList<String> keys = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (fi.isFormField()){}
					else {
						try {
							String pathSrc = fi.getName();
							//如果用户没有在FORM表单的文件字段中选择任何文件， 那么忽略对该字段项的处理
							if (pathSrc == null || pathSrc.trim().equals("")){
								continue;
							}
							int start = pathSrc.lastIndexOf('\\');
							String fileName = pathSrc.substring(start + 1);
							//处理文件名乱码的问题
							fileName = new String(fileName.getBytes(), "utf-8");
							if(!isAllowTypes(fileName)){ // 只允许上传xls文件格式
								//									out.print("" + fileName + "为不被允许的上传类型");
								String resultJson = "{\"result\":-2}";//成功1 失败0
								//									String callback = request.getParameter("callback");
								out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
								return;
							}
							File pathDest = new File(uploadDir, pathSrc.substring(start + 1));
							//如果文件存在不做处理,读出excel数据
							fi.write(pathDest);
							//									out.println(pathDest);
							FileInputStream fis = new FileInputStream(pathDest);
							//创建工作薄
							Workbook  workbook = new HSSFWorkbook(fis);
							Sheet sheet = workbook.getSheetAt(0); 
							int rowNum = sheet.getLastRowNum()+1;
							for(int k=1;k<rowNum;k++){
								House house = new House(); 
								Row row = sheet.getRow(k);
								int cellNum = row.getLastCellNum(); 
								for(int j=0;j<cellNum;j++){  
									Cell cell = row.getCell(j);  
									String cellValue = null;  
									switch(cell.getCellType()){
									case Cell.CELL_TYPE_NUMERIC : cellValue = String.valueOf((int)cell.getNumericCellValue()); break;  
									case Cell.CELL_TYPE_STRING : cellValue = cell.getStringCellValue(); break;  
									case Cell.CELL_TYPE_FORMULA : cellValue = String.valueOf(workbook.getCreationHelper().createFormulaEvaluator().evaluate(cell).getStringValue()); break;  
									case Cell.CELL_TYPE_BLANK : cellValue = ""; break;  
									case Cell.CELL_TYPE_BOOLEAN : cellValue = String.valueOf(cell.getBooleanCellValue()); break;  
									case Cell.CELL_TYPE_ERROR : cellValue = String.valueOf(cell.getErrorCellValue()); break;  
									}
									switch(j){//通过列数来判断对应插如的字段  
									case 0 : house.setName(cellValue);break;  
									case 1 : house.setClientCode(cellValue);break;
									case 2 : house.setHouseIeee(cellValue);break;  
									case 3 : house.setRegionCode(cellValue);break;
									}  
								}
								House t = houseService.get(house);
								if (t == null) {
									paramMap.put("clientCode", house.getClientCode());
									List<Map> list = houseieeeService.getReliClientByRegion(paramMap, null);
									if(list.isEmpty()){
										String resultJson = "{\"result\":-5}";//
										out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
										return;
									}
									Integer clientId = (Integer) (list.get(0).get("id")); 
									house.setClientId(clientId);
									String region = (String) (list.get(0).get("region"));
									house.setRegionCode(region);
									houselist.add(house);
								}	
								// 显示处理结果
								StringBuilder txtContent = new StringBuilder(); 
								txtContent.append("=====================" + new Date().toString() + "=================\r\n");
								fis.close();
							}
							String resultJson= JSON.toJSONStringWithDateFormat(houselist, "yyyy-MM-dd HH:mm:ss");
							out.println("{\"request_id\": 1234, \"response_params\":{\"result\":1,\"houseList\":" + resultJson + "}}");	
						} catch (Exception e) {
							//out.print("存储文件时出现如下问题：");
							//e.printStackTrace(out);
							String resultJson = "{\"result\":0}";//成功1 失败0
							//								String callback = request.getParameter("callback");
							out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
							LOGGER.info("importExceltoHouse", e);
							return;
						} finally{
							// 总是立即删除保存表单字段内容的临时文件
							fi.delete();
						}
					}
				}


			}else{
				/*String resultJson = "{\"result\":1}";//成功1 失败0
	    			out.println("({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); */
				String resultJson= JSON.toJSONStringWithDateFormat(houselist, "yyyy-MM-dd HH:mm:ss");
				out.println("{\"request_id\": 1234, \"response_params\":{\"result\":1,\"houseList\":" + resultJson + "}}");
			}

		} catch (Exception e) {
			LOGGER.info("importExceltoHouse", e);
			//				e.printStackTrace();
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}   

	}

	/**
	 * 获取客户端公网ip地址
	 * @author: zhuangxd
	 * 时间：2014-1-24 下午7:56:47
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getAddress")
	public void getAddress(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  

		try {
			// 客户端公网ip地址
			String ipAddress = getAddress(request);
			//logger.info("ip=="+ipAddress); 
			String resultJson = "{\"result\":1,\"address\":\"" + ipAddress + "\"}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("getAddress", e);
			//			e.printStackTrace();
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	/**
	 * 修改客户端公网ip地址   修改houseieee的外网地址
	 * @author: zhuangxd
	 * 时间：2014-1-24 下午9:17:18
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/updateAddress")
	public void updateAddress(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		try {
			House house = JSON.parseObject(json,House.class); 
			House t = houseService.get(house);
			String resultJson = "{\"result\":0}";//成功1 失败0
			if (t != null) {
				// 推送外网地址
				if (house.getNetworkAddress() != null) {
					t.setNetworkAddress(house.getNetworkAddress());
					t.setDescription(house.getNetworkAddress());
				}
				if (house.getPort() != null) {
					t.setPort(house.getPort()); // 默认81端口
				}
				if (house.getDescription() != null) {
					//t.setDescription(house.getDescription());
				}
				/*if (house.getIsonline() != null) {
        			t.setIsonline(house.getIsonline());
        		}*/
				//    			t.setIsonline("1"); // 在线
				t.setLasttime(new Date());
				houseService.update(t);

				resultJson = "{\"result\":1}";//成功1 失败0
			}
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("updateAddress", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	/**
	 * 获取客户端公网ip地址  获取http请求的客户端的外网地址
	 * @author: zhuangxd
	 * 时间：2014-1-24 下午7:57:06
	 * @param request
	 * @return
	 */
	public String getAddress(HttpServletRequest request) {
		String ipAddress = null;       
		//ipAddress = this.getRequest().getRemoteAddr();       
		ipAddress = request.getHeader("x-forwarded-for");     
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {         
			ipAddress = request.getHeader("Proxy-Client-IP");       
		}       
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {        
			ipAddress = request.getHeader("WL-Proxy-Client-IP");      
		}  
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
			ipAddress = request.getRemoteAddr();        
			if(ipAddress.equals("127.0.0.1")){         
				//根据网卡取本机配置的IP         
				InetAddress inet=null;       
				try {        
					inet = InetAddress.getLocalHost();     
				} catch (UnknownHostException e) {  
					//					e.printStackTrace(); 
					LOGGER.info("getAddress", e);
				}      
				ipAddress= inet.getHostAddress();      
			}                   
		}        
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割      
		if(ipAddress!=null && ipAddress.length()>15){ 
			//"***.***.***.***".length() = 15           
			if(ipAddress.indexOf(",")>0){          
				ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
			}       
		}
		LOGGER.info("ip=="+ipAddress);   
		return ipAddress;
	}

	@RequestMapping("/updateService")
	public void updateService(String json,HttpServletRequest request, HttpServletResponse response) {	
		LOGGER.info("json=" + json);
		PrintWriter out = null;
		String resultJson = "{\"result\":1}";//成功1 失败0
		String callback = request.getParameter("callback");		
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String houseIeees = request.getParameter("houseIeees");//以逗号分隔的houseIeee及其他参数
			String columnName = request.getParameter("columnName");//列名
			Integer service = (Integer) paramMap.get(columnName);//列名对应的值
			String paramIndex = request.getParameter("paramIndex");//开关参数在houseIeees元素中的位置
			if(service == null) {
				resultJson = "{\"result\":-1}"; //service参数不能为空
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			if(StringUtils.isBlank(houseIeees)) {
				resultJson = "{\"result\":-2}"; //houseIeee参数不能为空
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			if(StringUtils.isBlank(paramIndex)) {
				resultJson = "{\"result\":-3}"; //paramIndex参数不能为空
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			//执行更新
			//0:houseIeee,1:videoService,2:leaveHomeInform,3:isonline,4:lqi_open,5:device_is_online
			executeAction(service.intValue(), houseIeees, paramMap, columnName, Short.parseShort(paramIndex));	
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch(Exception e) {
			LOGGER.info("updateService", e);
			//			e.printStackTrace();
			resultJson = "{\"result\":0,\"message\":'" + e.getMessage() + "'}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	@RequestMapping("/getHouseCount")
	public void getHouseCount(String json,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("getHouseCount json=" + json);
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		String resultJson = "{\"result\":1}";//成功1 失败0
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String userid = request.getParameter("userid");
			int houseCount = houseService.getHouseCount(paramMap,userid);
			resultJson = "{\"result\":1,\"houseCount\":" + houseCount + "}";	
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch(Exception e) {
			LOGGER.info("getHouseCount", e);
			//			e.printStackTrace();
			resultJson = "{\"result\":0,\"message\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}

	@RequestMapping("/getHouses")
	public void getHouses(String json,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("getHouses json=" + json);
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		try {
			out=response.getWriter(); 
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class); 
			String startRow = request.getParameter("startRow");
			String pageSize = request.getParameter("pageSize");
			String userid = request.getParameter("userid");
			List<Map> t = houseService.getHouses(paramMap,userid, Integer.parseInt(startRow), Integer.parseInt(pageSize));
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("getHouses", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}   
	}

	@RequestMapping("/getHouseXY")
	public void getHouseXY(String json,HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("getHouses json=" + json);
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		try {
			out=response.getWriter();
			List<Map> t = houseService.getHouseXY();
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("getHouseXY", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}   
	}	
	@RequestMapping("/reboot")	
	public void getReboot(String json,HttpServletRequest request, HttpServletResponse response) throws Exception {		
		//		String xmppHost="218.104.133.247";
		//		String xmppPort="8081";
		PrintWriter out=response.getWriter(); 
		Map<String, Object> result = JSON.parseObject(json, Map.class);
		//		String xmppHost=PropertiesUtil.getProperty("xmpp.host");
		String xmppHost = (String) HouseieeeListener.houseieeeProxyserverMap.get(result.get("houseIeee"));
		String xmppPort=PropertiesUtil.getProperty("xmpp.port");
		StringBuffer urlBuffer=new StringBuffer("http://");
		urlBuffer.append(xmppHost).append(":").append(xmppPort).append("/spring-async/z203chat/poll");
		String str1 = "{\"result\":1}";
		String callback = request.getParameter("callback");
		try {			
			Map<String, Object> z203PMap = new HashMap<String, Object>();
			//通过get203user传入houseieee，返回map包含username；
			//			String username=houseService.get203user(result.get("houseIeee").toString()).get("username").toString();
			//			LOGGER.info("get203user=" + username);
			//			String z203json="reboot/"+username.substring(17, username.length());
			//z203默认用户名为watchdog
			String z203json="reboot/" + PropertiesUtil.getProperty("user.z203");
			LOGGER.info("z203Json=" + z203json);
			z203PMap.put("context", z203json);
			z203PMap.put("username", result.get("houseIeee"));
			String rbResultStr=TestHttpclient.postUrlWithParams(urlBuffer.toString(), z203PMap, "utf-8");
			Map<String, Object> rbResultMap = JSON.parseObject(rbResultStr, Map.class);
			if(!((String) ((Map) rbResultMap.get("message")).get("status_msg")).equals("success")) {
				str1 = "{\"result\":0}";
			}
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + str1 + "})");
		} catch (Exception e) {
			LOGGER.info("reboot", e);
			//			e.printStackTrace();
			str1 = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + str1 + "})");
		}

	}
	/**
	 * 更新z203的状态
	 * @param z203Status 开启或关闭的状态，开启（1），关闭（0）
	 * @param houseIeees 包含houseIeee和各服务开启或关闭的状态
	 * @param paramMap 更新到数据库或z203的参数
	 * @param z203ColumnName 开启或关闭对应的数据库列名
	 * @param z203OrientStatusIndex 在houseIeees元素里对应的服务状态地址
	 * @throws Exception
	 */
	private void executeAction(int z203Status, String houseIeees, 
			Map<String, Object> paramMap, String z203ColumnName, short z203OrientStatusIndex) throws Exception {
		//判断z203原来的服务状态在houseIeeeStr字符串的位置,1:videoService,2:leaveHomeInform
		//		int z203OrientStatusIndex = z203ColumnName.equals("videoService")? 1:2;
		String[] houseIeeeArr = houseIeees.split(",");
		//先修改z203上的设置
		//		String xmppHost = PropertiesUtil.getProperty("xmpp.host");
		//		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		List<String> houseIeeeStrB = new ArrayList<String>();
		List<String> failHouseIeees = new ArrayList<String>();
		String actionStr = z203Status == 1? "start" : "stop";
		for(String houseIeeeStr:houseIeeeArr) {//0表示关闭，1表示开启，2表示关闭未成功，3表示开启未成功
			String[] houseIeeePArr = houseIeeeStr.split(";");//0:houseIeee,1:videoService,2:leaveHomeInform,3:isonline,4:lqi_open,5:device_is_online
			//开启未成功点击关闭、关闭未成功点击开启不需要提交给z203
			if((z203Status == 0 && Integer.parseInt(houseIeeePArr[z203OrientStatusIndex]) == 3)
					|| (z203Status == 1 && Integer.parseInt(houseIeeePArr[z203OrientStatusIndex]) == 2)) {
				houseIeeeStrB.add(houseIeeePArr[0]);
			}
			//开启属于开启状态的设备直接过掉
			else if((z203Status == 0 && Integer.parseInt(houseIeeePArr[z203OrientStatusIndex]) == 0)
					|| (z203Status == 1 && Integer.parseInt(houseIeeePArr[z203OrientStatusIndex]) == 1)) {
				continue;
			}
			else {
				//    			LOGGER.info("p houseIEEE--------------:" + houseIeeePArr[0]);
				//    			LOGGER.info("p houseieeeProxyserverMap--------------:" + HouseieeeListener.houseieeeProxyserverMap);
				String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIeeePArr[0]);
				String xmppPort = PropertiesUtil.getProperty("xmpp.port");
				StringBuilder callUrl = new StringBuilder("http://");
				callUrl.append(serverIp).append(":").append(xmppPort).append("/spring-async/z203chat/polla");
				//未在线状态不需要提交给z203
				if(Integer.parseInt(houseIeeePArr[3]) == 1) {
					Map<String, Object> z203PMap = new HashMap<String, Object>();
					String z203Json = "{\"msg_type\":\"go_out_manage\",\"msg\":{\"house_ieee\":\"" + houseIeeePArr[0] + "\",\"bstartservice\":" + z203Status + "}}";
					if(z203OrientStatusIndex == 1)
						z203Json = "{\"msg_type\":\"video_manage\",\"msg\":{\"house_ieee\":\"" + houseIeeePArr[0] + "\",\"oper\":\"" + actionStr + "\"}}";
					else if(z203OrientStatusIndex == 4) {
						if(z203Status == 1) {
							String lqi_open_time = (String) paramMap.get("lqi_open_time");
							if(lqi_open_time.length() < 6)
								lqi_open_time += ":00"; //时间格式是13:25:00
							Integer min_lqi = (Integer) paramMap.get("min_lqi");
							z203Json = "{\"msg_type\":\"lqi_monitor\",\"msg\":{\"house_ieee\":\"" + houseIeeePArr[0] + "\",\"bOpen\":\"" + actionStr + "\",\"open_time\":\"" + lqi_open_time + "\",\"min_lqi\":" + min_lqi + "}}";
						}
						else {
							z203Json = "{\"msg_type\":\"lqi_monitor\",\"msg\":{\"house_ieee\":\"" + houseIeeePArr[0] + "\",\"bOpen\":\"" + actionStr + "\"}}";
						}
					} 
					else if(z203OrientStatusIndex == 5) {
						z203Json = "{\"msg_type\":\"check_online_manage\",\"msg\":{\"house_ieee\":\"" + houseIeeePArr[0] + "\",\"bOpen\":\"" + actionStr + "\"}}";
					}

					//对z203Map加密
					String str1 ="context="+z203Json+"username="+houseIeeePArr[0];
					while(str1.getBytes().length%16!=0)
						str1=str1+"#";
					String key =houseIeeePArr[0].substring(6, 16)+"NETVOX";
					byte[] encryptResult = AESCodec.encrypt2(str1, key);
					String encryptResultStr = AESCodec.parseByte2HexStr(encryptResult);
					callUrl.append("?sign=").append(encryptResultStr).append("&encodemethod=AES&houseIeeeSecret=").append(houseIeeePArr[0]);
					//		        	String context="context";
					//		        	String username="username";
					//		        	while(context.getBytes().length%16!=0)
					//		        		context=context+"#";
					//		        	while(username.getBytes().length%16!=0)
					//		        		username=username+"#";
					//		        	while(z203Json.getBytes().length%16!=0)
					//		        		z203Json=z203Json+"#";
					//		        	while(houseIeeePArr[0].getBytes().length%16!=0)
					//		        		houseIeeePArr[0]=houseIeeePArr[0]+"#";
					//		        	String key =houseIeeePArr[0].substring(6, 16)+"NETVOX";
					//		        	byte[] encryptResult = encrypt2(z203Json, key);
					//		        	String encryptResultStr = parseByte2HexStr(encryptResult);
					//		        	byte[] encryptResult1 = encrypt2(houseIeeePArr[0], key);
					//		        	String encryptResultStr1 = parseByte2HexStr(encryptResult1);
					//		        	byte[] encryptResult2 = encrypt2(context, key);
					//		        	String encryptResultStr2 = parseByte2HexStr(encryptResult2);
					//		        	byte[] encryptResult3 = encrypt2(username, key);
					//		        	String encryptResultStr3 = parseByte2HexStr(encryptResult3);
					//		        	z203PMap.put(encryptResultStr2, encryptResultStr);
					//		        	z203PMap.put(encryptResultStr3,encryptResultStr1);
					z203PMap.put("context", z203Json);
					z203PMap.put("username",houseIeeePArr[0]);
					//		        	callUrl.append("&encodemethod=AES&houseIeeeSecret=").append(houseIeeePArr[0]);
					//加密结束
					String str = TestHttpclient.postUrlWithParams(callUrl.toString(), z203PMap, "utf-8");
					Map<String, Object> result = JSON.parseObject(str, Map.class);
					int status = Integer.parseInt(((Map) result.get("message")).get("status").toString());
					if(status == 0) {
						houseIeeeStrB.add(houseIeeePArr[0]);
					}
					else {
						failHouseIeees.add(houseIeeePArr[0]);
					}
				} else {
					failHouseIeees.add(houseIeeePArr[0]);
				}
			}
		}
		if(!houseIeeeStrB.isEmpty()) {
			houseService.updateHouseSwitch(paramMap, houseIeeeStrB);
		}        	
		if(!failHouseIeees.isEmpty()) {//0表示关闭，1表示开启，2表示关闭未成功，3表示开启未成功
			if(z203Status == 0)
				paramMap.put(z203ColumnName, 2);
			else
				paramMap.put(z203ColumnName, 3);
			houseService.updateHouseSwitch(paramMap, failHouseIeees);
		}
	}

	/*通过houseieee删除house、houseieee、proxyserver表中数据*/
	@RequestMapping("/delete1")
	public void delete1(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter();  
		try {
			House house = JSON.parseObject(json,House.class); 
			Houseieee houseieee = JSON.parseObject(json,Houseieee.class);
			Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class);
			int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
			String cloudAddress = proxyserver.getServerIp();
			String cloudPort = PropertiesUtil.getProperty("proxy.port");
			String serverHost = request.getServerName();
			if(cloudMain == 1) {
				int result = 1;
				if(!cloudAddress.equals(serverHost)) { 
					if(!cloudAddress.equals("localhost") 
							&& !cloudAddress.equals("127.0.0.1")
							&& !serverHost.equals("localhost") 
							&& !serverHost.equals("127.0.0.1")) {
						Map<String, String[]> rMap = request.getParameterMap();
						String uri = request.getRequestURI();
						String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
						if(rMap != null) {
							Map<String, Object> pMap = null;
							pMap = new HashMap<String, Object>();
							Iterator<String> it=rMap.keySet().iterator();   
							while(it.hasNext()){ 
								String key=it.next();  
								//post方法去除json参数
								String value = (rMap.get(key))[0].toString();
								pMap.put(key, value);
							}
							String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8").trim();	
							if(StringUtils.isNotBlank(rsStr)) {
								rsStr = rsStr.substring(rsStr.indexOf("(") + 1, rsStr.length() - 1);
								LOGGER.info("return str: " + rsStr);
								Map<String, Object> resultMap = JSON.parseObject(rsStr, Map.class);
								result = Integer.parseInt(((Map) resultMap.get("response_params")).get("result").toString());
							}
						}
					}
				} 
				if(result == 0) {
					String resultJson = "{\"result\":0}";//成功1 失败0
					String callback = request.getParameter("callback");
					out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
					return;
				}
				houseService.deleteSLAccount(house);
			}

			Map<String, Object> paramMap = new HashMap<String,Object>();
			if (house.getHouseIeee() != null) {
				houseService.delete1(house);
				houseieeeService.delete1(houseieee);
				HouseieeeListener.houseieeeMap.remove(houseieee.getHouseIeee());
				HouseieeeListener.houseieeeProxyserverMap.remove(proxyserver.getHouseIeee());
				proxyserverService.delete1(proxyserver);
				int i = proxyserverService.findBurnin(house);
				if(i == 1){
					List<String> list = new ArrayList<String>();
					list.add(house.getHouseIeee());
					paramMap.put("hList",list );
					paramMap.put("rFlag", 2);
					brinService.finishzhuce(paramMap);
				}
			}
			String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("通过houseieee删除house、houseieee、proxyserver表中数据", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}	

	/**
	 * 注册完成后，重新加载houseieee到内存中。表中手工注册houseieee后，要执行该接口，才能过滤生效。
	 * @author: zhuangxd
	 * 时间：2014-9-9 下午1:43:29
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/initHouseieee")
	public void initHouseieee(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

		PrintWriter out=response.getWriter(); 
		try {
			HouseieeeListener s = new HouseieeeListener();
			s.initHouseieee();
			String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("initHouseieee", e);
			//			e.printStackTrace();
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

	@RequestMapping("/isExist203206")
	public void isExist203206(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Modenode modenode = JSON.parseObject(json, Modenode.class);
		try {
			List<Modenode> t = houseService.isExist203206(modenode);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  			
		} catch (Exception e) {
			LOGGER.info("isExist203206", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}
	/**
	 * 监控日志搜索功能
	 * @param json
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findMonitorlog")
	public void findMonitorlog(String json,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");
		//		String orderBy = request.getParameter("orderBy");
		String orderBy = (String) paramMap.get("orderBy");
		String callback = request.getParameter("callback");
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		//		if (StringUtils.isBlank(pageIndex))
		//			pageIndex = "1";
		//		if (StringUtils.isBlank(pageSize))
		//			pageSize = "30";
		try {
			int startRow_ = Integer.parseInt(startRow);
			int pageSize_ = Integer.parseInt(pageSize);
			Map tlist = houseService.findMonitorlog(paramMap,startRow_,pageSize_,orderBy);
			String resultJson=JSON.toJSONStringWithDateFormat(tlist, "yyyy-MM-dd HH:mm:ss");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("findMonitorlog", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}
	/**
	 * 监控日志分页
	 * @param json
	 * @param callback
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getMonitorlogCount")	
	public void getMonitorlogCount(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();
		DataGrid dg = new DataGrid();
		try {
			map=JSON.parseObject(json);	
			map.put("total", houseService.getMonitorlogCount(map));
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getMonitorlogCount",e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);		
	}

	/**
	 * 统计监控SHC总台数、状态变化、一直不在线
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/monitorcount")
	public void monitorcount(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out =  response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			//			int t = houseService.monitorlogCount();//统计监控SHC总数
			//			int t1 = houseService.stilloffline();//统计一直不在线台数
			//			int t2 = houseService.monitorchange();//统计状态变化台数
			Map<String, Object> params = JSON.parseObject(json, Map.class);
			List<Map> t =houseService.shcCount((Integer) params.get("userId"));
			String resultJson=JSON.toJSONStringWithDateFormat(t.get(0).get("mCount"), "yyyy-MM-dd HH:mm:ss");
			String resultJson2=JSON.toJSONStringWithDateFormat(t.get(1).get("mCount"), "yyyy-MM-dd HH:mm:ss");
			String resultJson1=JSON.toJSONStringWithDateFormat(t.get(2).get("mCount"), "yyyy-MM-dd HH:mm:ss");
			out.println(callback +"({\"request_id\": 1234, \"monitorlogCount\":" + resultJson + ", \"stilloffline\":" + resultJson1 + ", \"monitorchange\":" + resultJson2 + ",\"response_params\":1})");
		} catch (Exception e) {
			LOGGER.info("monitorcount", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}
	/**
	 * 导出监控日志
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/exportMonLogExcel")
	public void exportMonLogExcel(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		String callback = request.getParameter("callback");
		try {
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int t = houseService.exportMonLogExcel(paramMap,request,response);
		} catch (Exception e) {
			LOGGER.info("exportMonLogExcel", e);
			try {
				out = response.getWriter();
			} catch(Exception ex) {
				LOGGER.info("exportMonLogExcel getWriter", ex);
				resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
			}
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback +"{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	/**
	 * 批量迁移
	 * @param json
	 * @param request
	 * @param response
	 */
	@RequestMapping("/batchMove")
	public void batchMove(String json, HttpServletRequest request, HttpServletResponse response) {
		moveResultList.clear();
		response.setContentType("text/html;charset=utf-8");
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String userId = request.getParameter("userId");
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			//跨页全选才有
			String uncheckedHouses = request.getParameter("uncheckedHouses");
			List<Map> houseList = null;
			if(StringUtils.isNotBlank(uncheckedHouses) && !uncheckedHouses.equals("-2")) {
				houseList = houseService.findhouseList2(null, null, null, paramMap, null, uncheckedHouses);
			}
			else {
				List<String> houseIEEEs = (List<String>) paramMap.get("houseIEEEs");
				houseList = houseService.getHousesInfo(houseIEEEs);
			}
			String targetIp = paramMap.get("targetIp") == null? request.getParameter("targetIp") : (String) paramMap.get("targetIp");
			boolean isConnRefused = false;
			for(Map<String, Object> houseMap : houseList) {
				String houseIEEE = (String) houseMap.get("houseIEEE");
				HouseieeeListener.houseieeeProxyserverMap.put(houseIEEE, targetIp);
				String name = (String) houseMap.get("name");
				String rJson = "{\"houseIEEE\":\"" + houseIEEE + "\",\"serverIp\":\"" + targetIp +"\",\"name\":\"" + name + "\",\"clientCode\":\"" + houseMap.get("clientCode") + "\"}";
				//				String str1 ="json=" + rJson;
				//	        	while(str1.getBytes().length%16!=0)
				//	        		str1=str1+"#";
				//	        	String key =houseIEEE.substring(6, 16) + ((Houseieee) HouseieeeListener.houseieeeMap.get(houseIEEE)).getVendorCode();
				//	        	byte[] encryptResult = AESCodec.encrypt2(str1, key);
				//	        	String encryptResultStr = AESCodec.parseByte2HexStr(encryptResult);
				Map<String, Object> pMap = new HashMap<String, Object>();
				pMap.put("json", rJson);
				//				pMap.put("encodemethod", "AES");
				//				pMap.put("sign", encryptResultStr);
				//				pMap.put("houseIeeeSecret", houseIEEE);
				String serverHost = request.getServerName();
				try {
					if(isConnRefused) {
						houseMap.put("moveResult", 0);
						continue;
					}
					int updateResult = updateRelative(rJson, serverHost, "/zigBeeDevice/houseController/update2.do", pMap, 0, userId);
					int addResult = addRelative(rJson, serverHost, "/zigBeeDevice/houseController/addmany.do", pMap, 0, userId);
					if(updateResult == 0 || addResult == 0 || updateResult == -3 || addResult == -3)
						houseMap.put("moveResult", 0);
					else
						houseMap.put("moveResult", 1);
				} catch(Exception ex) {
					LOGGER.info("---move " + houseIEEE + " exception", ex);
					if(ex.getMessage().indexOf("refused") > -1) {
						isConnRefused = true;
					}
					houseMap.put("moveResult", 0);
					//					moveResultList.add(houseMap);
				}
				//				moveResultList.add(houseMap);
			}
			//跨页全选
			if(StringUtils.isNotBlank(uncheckedHouses) && !uncheckedHouses.equals("-2")) {
				//				List<Map> pageResultList = new ArrayList<>();
				//				int size = moveResultList.size();
				//				int pageSize = Integer.parseInt(request.getParameter("pageSize")); 
				//				for(int i = 0; i < pageSize; i++) {
				//					pageResultList.add(moveResultList.get(i));
				//				}
				moveResultList = houseList;
				resultJson = "{\"result\":1}";
			}
			else {
				resultJson = JSON.toJSONStringWithDateFormat(houseList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			}
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("batchMove", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}


	/**
	 * 更新
	 * @param json
	 * @return 1成功，0失败
	 */
	private int updateRelative(String json, String serverHost, String uri, Map rMap, int callFlag, String userId) throws Exception {
		House house = JSON.parseObject(json, House.class);
		Proxyserver proxyserver = JSON.parseObject(json, Proxyserver.class);
		String cloudAddress = proxyserver.getServerIp();
		String cloudPort = PropertiesUtil.getProperty("proxy.port");
		int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
		if (cloudMain == 1) {
			//检查客户代码是否合法
			List<Map> list = houseService.getReliClientByUserId(house.getClientCode(), userId);
			if(list.isEmpty()){
				return -3;
			}
			else {
				house.setClientId((Integer) ((list.get(0)).get("id")));
			}
			//不能修改成自己
			if(serverHost.equals(cloudAddress))
				return 0;
			if(!cloudAddress.equals(serverHost)
					&& !cloudAddress.equals("localhost") 
					&& !cloudAddress.equals("127.0.0.1")
					&& !serverHost.equals("localhost") 
					&& !serverHost.equals("127.0.0.1")) {
				String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
				if(rMap != null) {
					Map<String, Object> pMap = null;
					if(callFlag == 1) {
						pMap = new HashMap<String, Object>();
						Iterator<String> it=rMap.keySet().iterator();   
						while(it.hasNext()){ 
							String key=it.next();  
							//post方法去除json参数
							String value = ((Object[])(rMap.get(key)))[0].toString();
							pMap.put(key, value);
						}
					}
					else
						pMap = rMap;
					String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
					Map<String, Object> resultMap = JSON.parseObject(rsStr, Map.class);
					int i = ((Integer)((Map)resultMap.get("response_params")).get("result")).intValue();
					if(i == 0)
						return 0;
				}
			}
		}
		Houseieee houseieee = JSON.parseObject(json, Houseieee.class);
		House t = this.houseService.get1(house);

		if (t != null) {
			if(cloudMain == 1 && house.getSmsType() != t.getSmsType()){
				String result = noticeSmsEnable(proxyserver.getServerIp(), house.getHouseIeee(), house.getSmsType(),true);
				if(result==null)
					return 0;
				Map<String,Object> resultMap = JSON.parseObject(result, Map.class);
				Object message = resultMap.get("message");
				if(!StringUtils.contains(message.toString(), "success")){
					return 0;
				}
			}
			if ((json != null) && (!json.contains("regionCode"))) {
				house.setRegionCode(null);
			}
			if ((json != null) && (!json.contains("isonline"))) {
				house.setIsonline(null);
			}
			if ((json != null) && (!json.contains("enableFlag"))) {
				house.setEnableFlag(null);
				houseieee.setEnableFlag(null);
				proxyserver.setEnableFlag(null);
			}
			if ((json != null) && (!json.contains("IPK_version"))) {
				house.setIPK_version(null);
			}
			if (house.getIPK_version() != null) {
				t.setIPK_version(house.getIPK_version());
			}
			if (house.getLatitude() != null) {
				t.setLatitude(house.getLatitude());
			}
			if (house.getLongitude() != null) {
				t.setLongitude(house.getLongitude());
			}
			if (house.getName() != null) {
				t.setName(house.getName());
			}
			if (house.getEnableFlag() != null) {
				t.setEnableFlag(house.getEnableFlag());
			}
			if (house.getDescription() != null) {
				t.setDescription(house.getDescription());
			}
			if (house.getIsonline() != null) {
				t.setIsonline(house.getIsonline());
			}
			if (house.getRegionCode() != null) {
				t.setRegionCode(house.getRegionCode());
			}
			if (house.getNetworkAddress() != null) {
				t.setNetworkAddress(house.getNetworkAddress());
			}
			if (house.getPort() != null) {
				t.setPort(house.getPort());
			}
			if (house.getClientCode() != null) {
				t.setClientCode(house.getClientCode());
			}
			t.setLasttime(new Date());
			if(house.getClientId() != null) {
				t.setClientId(house.getClientId());
			}
			if(t.getSmsType() != house.getSmsType()){
				t.setSmsType(house.getSmsType());
			}
			this.houseService.update(t);
			//修改家名称时，修改微信绑定中所对应家的名臣
			try{
				weixinService.updateHouseName(house);
			}catch(Exception e){
				LOGGER.error("upde weixin bind account house name error");
			}
			Houseieee t2 = this.houseieeeService.get1(houseieee);
			if (t2 != null) {
				if (houseieee.getName() != null) {
					t2.setName(houseieee.getName());
				}
				if (houseieee.getDescription() != null) {
					t2.setDescription(houseieee.getDescription());
				}

				if (houseieee.getVendorCode() != null) {
					t2.setVendorCode(houseieee.getVendorCode());
				}
				if (houseieee.getEncodemethod() != null) {
					t2.setEncodemethod(houseieee.getEncodemethod());
				}
				if (houseieee.getEnableFlag() != null)
				{
					t2.setEnableFlag(house.getEnableFlag());
				}
				t2.setLasttime(new Date());
				this.houseieeeService.update(t2);
				HouseieeeListener.houseieeeMap.put(t2.getHouseIeee(), t2);
			}
			else {
				houseieee.setSecretKey(houseieee.getHouseIeee().substring(6));
				if ((houseieee.getVendorCode() == null) || ("".equals(houseieee.getVendorCode()))) {
					houseieee.setVendorCode("NETVOX");
				}
				if ((houseieee.getEncodemethod() == null) || ("".equals(houseieee.getEncodemethod()))) {
					houseieee.setEncodemethod("0");
				}
				this.houseieeeService.save(houseieee);
				HouseieeeListener.houseieeeMap.put(houseieee.getHouseIeee(), houseieee);
			}
			proxyserver.setType("0");
			proxyserver.setSecondType("0");
			Proxyserver t3 = this.proxyserverService.get1(proxyserver);
			if (t3 != null) {
				if (proxyserver.getName() != null) {
					t3.setName(proxyserver.getName());
				}
				if (proxyserver.getDescription() != null) {
					t3.setDescription(proxyserver.getDescription());
				}
				if (proxyserver.getEnableFlag() != null) {
					t3.setEnableFlag(proxyserver.getEnableFlag());
				}
				if (proxyserver.getServerIp() != null) {
					t3.setServerIp(proxyserver.getServerIp());
				}
				t3.setLasttime(new Date());
				this.proxyserverService.update(t3);
			}
			else  if (t3 == null) {
				proxyserver.setServerPort("5222");
				this.proxyserverService.save(proxyserver);
			}
			proxyserver.setType("1");
			proxyserver.setSecondType("0");
			Proxyserver t4 = this.proxyserverService.get1(proxyserver);
			if (t4 != null) {
				if (proxyserver.getName() != null) {
					t4.setName(proxyserver.getName());
				}
				if (proxyserver.getDescription() != null) {
					t4.setDescription(proxyserver.getDescription());
				}
				if (proxyserver.getEnableFlag() != null) {
					t4.setEnableFlag(proxyserver.getEnableFlag());
				}
				if (proxyserver.getServerIp() != null) {
					t4.setServerIp(proxyserver.getServerIp());
				}
				t4.setLasttime(new Date());
				HouseieeeListener.houseieeeProxyserverMap.put(t4.getHouseIeee(), t4.getServerIp());
				this.proxyserverService.update(t4);
			}
			else if (t4 == null) {
				proxyserver.setServerPort(PropertiesUtil.getProperty("cloud.port"));
				HouseieeeListener.houseieeeProxyserverMap.put(proxyserver.getHouseIeee(), proxyserver.getServerIp());
				this.proxyserverService.save(proxyserver);
			}
			proxyserver.setType("2");
			proxyserver.setSecondType("0");
			Proxyserver t5 = this.proxyserverService.get1(proxyserver);
			if (t5 != null) {
				if (proxyserver.getName() != null) {
					t5.setName(proxyserver.getName());
				}
				if (proxyserver.getDescription() != null) {
					t5.setDescription(proxyserver.getDescription());
				}
				if (proxyserver.getEnableFlag() != null) {
					t5.setEnableFlag(proxyserver.getEnableFlag());
				}
				if (proxyserver.getServerIp() != null) {
					t5.setServerIp(proxyserver.getServerIp());
				}
				t5.setLasttime(new Date());
				this.proxyserverService.update(t5);
			}
			else	if (t5 == null) {
				proxyserver.setServerPort("9090");
				this.proxyserverService.save(proxyserver);
			}
			
			proxyserver.setType("3");
			proxyserver.setSecondType("0");
			Proxyserver t6 = proxyserverService.get(proxyserver);
			if(t6 != null){
				if (proxyserver.getName() != null) {
					t6.setName(proxyserver.getName());
				}
				if (proxyserver.getDescription() != null) {
					t6.setDescription(proxyserver.getDescription());
				}
				if (proxyserver.getEnableFlag() != null) {
					t6.setEnableFlag(proxyserver.getEnableFlag());
				}
				//通过当前的服务器IP获取域名
				String serverDomain = houseService.getServerDomain(proxyserver.getServerIp());
				//若是新增的服务器，则通过配置文件读取相应的域名（以IP为键，域名为值）
				if(StringUtils.isBlank(serverDomain)){
					serverDomain = proxyserver.getServerIp();
				}
				t6.setServerIp(serverDomain);
				//通过配置文件读取端口，默认为8443
				String serviePort = PropertiesUtil.getProperty("domain.ios.port");
				if(StringUtils.isBlank(serviePort)){
					serviePort = "8443";
				}
				/*if (proxyserver.getServerIp() != null) {
					t5.setServerIp(proxyserver.getServerIp());
				}*/
				t6.setServerPort(serviePort);
				t6.setLasttime(new Date());
				this.proxyserverService.update(t6);
			}else{
				//通过当前的服务器IP获取域名
				String serverDomain = houseService.getServerDomain(proxyserver.getServerIp());
				//若是新增的服务器，则通过配置文件读取相应的域名（以IP为键，域名为值）
				if(StringUtils.isBlank(serverDomain)){
					serverDomain = proxyserver.getServerIp();
				}
				proxyserver.setServerIp(serverDomain);
				//通过配置文件读取端口，默认为8443
				String serviePort = PropertiesUtil.getProperty("domain.ios.port");
				if(StringUtils.isBlank(serviePort)){
					serviePort = "8443";
				}
				proxyserver.setServerPort(serviePort);
				proxyserverService.save(proxyserver);
			}
		}
		else {
			if (house.getNetworkAddress() == null) {
				house.setNetworkAddress("192.168.99.255");
				house.setDescription("192.168.99.255");
			}
			if (house.getClientCode() == null) {
				house.setClientCode("CS6");
			}
			this.houseService.save(house);
			Houseieee t2 = this.houseieeeService.get(houseieee);
			if (t2 == null) {
				houseieee.setSecretKey(houseieee.getHouseIeee().substring(6));
				if ((houseieee.getVendorCode() == null) || ("".equals(houseieee.getVendorCode()))) {
					houseieee.setVendorCode("NETVOX");
				}
				if ((houseieee.getEncodemethod() == null) || ("".equals(houseieee.getEncodemethod()))) {
					houseieee.setEncodemethod("0");
				}
				this.houseieeeService.save(houseieee);
				HouseieeeListener.houseieeeMap.put(houseieee.getHouseIeee(), houseieee);
			}
			if(cloudMain == 1){
				noticeSmsEnable(proxyserver.getServerIp(), house.getHouseIeee(), house.getSmsType(),false);
			}
			proxyserver.setType("0");
			proxyserver.setSecondType("0");
			Proxyserver t3 = this.proxyserverService.get(proxyserver);
			if (t3 == null) {
				proxyserver.setServerPort("5222");
				this.proxyserverService.save(proxyserver);
			}
			proxyserver.setType("1");
			proxyserver.setSecondType("0");
			Proxyserver t4 = this.proxyserverService.get(proxyserver);
			if (t4 == null) {
				proxyserver.setServerPort(PropertiesUtil.getProperty("cloud.port"));
				HouseieeeListener.houseieeeProxyserverMap.put(proxyserver.getHouseIeee(), proxyserver.getServerIp());
				this.proxyserverService.save(proxyserver);
			}
			proxyserver.setType("2");
			proxyserver.setSecondType("0");
			Proxyserver t5 = this.proxyserverService.get(proxyserver);
			if (t5 == null) {
				proxyserver.setServerPort("9090");
				this.proxyserverService.save(proxyserver);
			}

			proxyserver.setType("3");
			proxyserver.setSecondType("0");
			Proxyserver t6 = proxyserverService.get(proxyserver);
			if(t6 == null){
				//通过当前的服务器IP获取域名
				String serverDomain = houseService.getServerDomain(proxyserver.getServerIp());
				//若是新增的服务器，则通过配置文件读取相应的域名（以IP为键，域名为值）
				if(StringUtils.isBlank(serverDomain)){
					serverDomain = proxyserver.getServerIp();
				}
				proxyserver.setServerIp(serverDomain);
				//通过配置文件读取端口，默认为8443
				String serviePort = PropertiesUtil.getProperty("domain.ios.port");
				if(StringUtils.isBlank(serviePort)){
					serviePort = "8443";
				}
				proxyserver.setServerPort(serviePort);
				proxyserverService.save(proxyserver);
			}

			Houseservice houseservice = new Houseservice();
			houseservice.setDefaultemail(PropertiesUtil.getProperty("mail.to"));
			houseservice.setHouseIeee(house.getHouseIeee());
			this.houseService.openshc(houseservice);
			LOGGER.info("手动注册家----->默认开启shc监控邮箱");
		}
		return 1;
	}


	/**
	 * 新增
	 * @param json
	 * @param request
	 * @return 1表示成功，0表示失败
	 * @throws Exception
	 */
	private int addRelative(String json, String serverHost, String uri, Map rMap, int callFlag, String userId) throws Exception {
		int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
		House house = JSON.parseObject(json,House.class);
		Houseieee houseieee = JSON.parseObject(json,Houseieee.class);
		Proxyserver proxyserver = JSON.parseObject(json,Proxyserver.class);
		if(cloudMain == 1) {
			//判断服务器的数量，先解析xml获取服务器数量限制，若无配置文件则无法注册
			int exceedResult = isHouseExceed(proxyserver.getServerIp(), 1); 
			if(exceedResult != 1) {
				return exceedResult;
			}

			//检查客户代码是否合法
			List<Map> list = houseService.getReliClientByUserId(house.getClientCode(), userId);
			if(list.isEmpty()){
				return -3;
			}
			else {
				house.setClientId((Integer) ((list.get(0)).get("id")));
			}
		}

		//注册家
		House t = houseService.get(house);
		if (StringUtils.isBlank(house.getHouseIeee()) || house.getHouseIeee().length() < 16 ) {
			return 0;
		}
		boolean proxySuccess = false, xmppSuccess = false;
		// 往配置文件指定的云端服务器注册houseieee
		String cloudAddress = PropertiesUtil.getProperty("proxy.ip");
		String cloudPort = PropertiesUtil.getProperty("proxy.port");

		if(cloudMain == 1 
				&& !cloudAddress.equals(serverHost) 
				&& !cloudAddress.equals("localhost") 
				&& !cloudAddress.equals("127.0.0.1")
				&& !serverHost.equals("localhost") 
				&& !serverHost.equals("127.0.0.1")) {
			String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
			if(rMap != null) {
				Map<String, Object> pMap = null;
				if(callFlag == 1) {
					pMap = new HashMap<String, Object>();
					Iterator<String> it=rMap.keySet().iterator();   
					while(it.hasNext()){ 
						String key=it.next();  
						//post方法去除json参数
						String value = ((Object[])(rMap.get(key)))[0].toString();
						pMap.put(key, value);
					}
				}
				else 
					pMap = rMap;
				String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
				Map<String, Object> resultMap = JSON.parseObject(rsStr, Map.class);
				int result = Integer.parseInt(((Map) resultMap.get("response_params")).get("result").toString()); 
				if(result == -1 || result == 1)
					proxySuccess = true;
			}
		}
		else
			proxySuccess = true;

		// 往serverip字段指定的云端服务器注册houseieee
		cloudAddress = proxyserver.getServerIp();
		if(cloudMain == 1) {
			if(!cloudAddress.equals(serverHost)) { 
				if(!cloudAddress.equals("localhost") 
						&& !cloudAddress.equals("127.0.0.1")
						&& !serverHost.equals("localhost") 
						&& !serverHost.equals("127.0.0.1")) {
					String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
					if(rMap != null) {
						Map<String, Object> pMap = null;
						if(callFlag == 1) {
							pMap = new HashMap<String, Object>();
							Iterator<String> it=rMap.keySet().iterator();   
							while(it.hasNext()){ 
								String key=it.next();  
								//post方法去除json参数
								String value = ((Object[])(rMap.get(key)))[0].toString();
								pMap.put(key, value);
							}
						}
						else
							pMap = rMap;
						String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");	
						Map<String, Object> resultMap = JSON.parseObject(rsStr, Map.class);
						int result = Integer.parseInt(((Map) resultMap.get("response_params")).get("result").toString()); 
						if(result == -1 || result == 1)
							xmppSuccess = true;
					}
				}
				else
					xmppSuccess = true;
			} else
				xmppSuccess = false;
		} else
			xmppSuccess = true;

		if(proxySuccess && xmppSuccess){
			
			if (t == null) {
				if (house.getNetworkAddress() == null) {
					house.setNetworkAddress("192.168.99.255"); // 默认值
					house.setDescription("192.168.99.255"); // 默认值
				}
				if (house.getClientCode() == null) {
					house.setClientCode("CS6");
				}
				houseService.save(house);
				Houseieee t2 = houseieeeService.get(houseieee); 
				if (t2 == null) {
					houseieee.setSecretKey(houseieee.getHouseIeee().substring(6));
					if(houseieee.getVendorCode()==null||"".equals(houseieee.getVendorCode())){
						houseieee.setVendorCode("NETVOX");
					}
					if(houseieee.getEncodemethod()==null||"".equals(houseieee.getEncodemethod())){
						houseieee.setEncodemethod("0");
					}
					houseieeeService.save(houseieee);
					HouseieeeListener.houseieeeMap.put(houseieee.getHouseIeee(), houseieee);
				}
				if(cloudMain == 1){
					noticeSmsEnable(proxyserver.getServerIp(), house.getHouseIeee(), house.getSmsType(),false);
				}
				proxyserver.setType("0");
				proxyserver.setSecondType("0");
				Proxyserver t3 = proxyserverService.get(proxyserver);
				if (t3 == null) {
					proxyserver.setServerPort("5222");
					proxyserverService.save(proxyserver);
				}            	
				proxyserver.setType("1");
				proxyserver.setSecondType("0");
				Proxyserver t4 = proxyserverService.get(proxyserver);
				if (t4 == null) {
					proxyserver.setServerPort(PropertiesUtil.getProperty("cloud.port"));
					HouseieeeListener.houseieeeProxyserverMap.put(proxyserver.getHouseIeee(), proxyserver.getServerIp());
					proxyserverService.save(proxyserver);
				}
				proxyserver.setType("2");
				proxyserver.setSecondType("0");
				Proxyserver t5 = proxyserverService.get(proxyserver);
				if (t5 == null) {
					proxyserver.setServerPort("9090");
					proxyserverService.save(proxyserver);
				}

				proxyserver.setType("3");
				proxyserver.setSecondType("0");
				Proxyserver t6 = proxyserverService.get(proxyserver);
				if(t6 == null){
					//通过当前的服务器IP获取域名
					String serverDomain = houseService.getServerDomain(proxyserver.getServerIp());
					//若是新增的服务器，则通过配置文件读取相应的域名（以IP为键，域名为值）
					if(StringUtils.isBlank(serverDomain)){
						serverDomain = proxyserver.getServerIp();
					}
					proxyserver.setServerIp(serverDomain);
					//通过配置文件读取端口，默认为8443
					String serviePort = PropertiesUtil.getProperty("domain.ios.port");
					if(StringUtils.isBlank(serviePort)){
						serviePort = "8443";
					}
					proxyserver.setServerPort(serviePort);
					proxyserverService.save(proxyserver);
				}

				// 家庭注册时，默认开启shc监控邮箱
				Houseservice houseservice = new Houseservice();
				houseservice.setDefaultemail(PropertiesUtil.getProperty("mail.to"));
				houseservice.setHouseIeee(house.getHouseIeee());
				houseService.openshc(houseservice);
				LOGGER.info("手动注册家----->默认开启shc监控邮箱");
			} 
			else {
				// houseieee已存在，不做处理
				return -1;
			}
		}
		else{
			return 0;
		}
		return 1;
	}

	/**
	 * 获取拾联科技平台相关信息（地址、账户、密码）
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getSLPlatformInfo")
	public void getSLPlatformInfo(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		if(StringUtils.isBlank(callback)){
			callback = "callback";
		}
		String resultJson = "";
		try{
			Map<String,Object> params = JSON.parseObject(json, Map.class);
			Map<String,Object> rMap = houseService.getSLPlatformInfo(params);
			if(rMap!=null&&!rMap.isEmpty()){
				resultJson = "{\"status\":1,\"status_msg\":\"success\",\"data\":"+JSON.toJSONString(rMap)+"}";
			}else{
				resultJson = "{\"status\":0,\"status_msg\":\"faild\"}";
			}
			out.println("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}catch(Exception e){
			LOGGER.error("get ShiLian platforminfo error", e);
			resultJson = "{\"status\":-1,\"status_msg\":\"faild\"}";
			out.println("{\"request_id\":1234,\"response_params\":"+resultJson+"}");
		}
	}

	@RequestMapping("/getServerIP")
	public void getServerIP(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOGGER.info("json=" + json);
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			out = response.getWriter();
			List<Map> t = houseService.getServerIP(paramMap);
			String resultJson=JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");

		} catch (Exception e) {
			LOGGER.info("getServerIP", e);
			String resultJson = "{\"result\":0}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/findhouseList")
	public void findhouseList(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		Json j = new Json();
		DataGrid dg = new DataGrid();
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		String orderBy = request.getParameter("orderBy");
		String userid = request.getParameter("userid");
		try {
			if(StringUtils.isBlank(startRow))
				startRow = "0";
			if(StringUtils.isBlank(pageSize))
				pageSize = "30";
			map=JSON.parseObject(json);	

			List<Map> t = houseService.findhouseList(startRow, pageSize, orderBy, map,userid);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("findhouseList",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");

		}

	}

	@RequestMapping("/getCloudCount")
	public void getCloudCount(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out =  response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			List<Map> t =houseService.cloudCount(paramMap);
			String resultJson=JSON.toJSONStringWithDateFormat(t.get(0).get("tCount"), "yyyy-MM-dd HH:mm:ss");
			String resultJson2=JSON.toJSONStringWithDateFormat(t.get(1).get("tCount"), "yyyy-MM-dd HH:mm:ss");
			//String resultJson5 = "";
			List list = new ArrayList();
			for (Map map :t) {
				int hcount= ((BigInteger)map.get("hCount")).intValue();
				if (hcount == 1 || hcount == 2) continue;
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("cloud_houseCount", map.get("tCount"));
				map2.put("serverIp", map.get("serverIp"));
				list.add(map2);
				//String resultJson1=JSON.toJSONStringWithDateFormat(t.get(2).get("tCount"), "yyyy-MM-dd HH:mm:ss");
				//String resultJson4=JSON.toJSONStringWithDateFormat(t.get(2).get("serverIp"), "yyyy-MM-dd HH:mm:ss");

			}
			String resultJson6=JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			out.println(callback +"({\"request_id\": 1234, \"cloudCount\":" + resultJson + ", \"houseCount\":" + resultJson2 + ", \"serverIpCount\":" + resultJson6 + ",\"response_params\":1})");

		} catch (Exception e) {
			LOGGER.info("getCloudCount", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}
	/**
	 * 服务器列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/findServerlib")
	public void findServerlib(String json, HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");  
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		String orderBy = request.getParameter("orderBy");
		Map<String , Object> param = JSON.parseObject(json, Map.class);
		try{
			List<Map> list = houseService.findServerlib(startRow,pageSize,orderBy,param);
			String resultJson=JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("findServerlib", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}

	/**
	 * 新增服务器
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/addServer")
	public void addServer(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		String callback = request.getParameter("callback");
		Map<String , Object> param = JSON.parseObject(json, Map.class);
		try{
			String resultJson = "{\"result\":0}";//成功1 失败0 
			//获取最大CSHC账户数
			int maxServerNum = getMaxServerNum(this.getClass().getResource("/").getPath());
			if(maxServerNum == -10) {
				resultJson = "{\"result\":"+maxServerNum+"}";
				out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			param.put("maxcshc", maxServerNum);
			int i = houseService.addServer(param);
			resultJson = "{\"result\":"+i+"}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("addServer", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}

	@RequestMapping("/getServerCount")	
	public void getServerCount(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();
		DataGrid dg = new DataGrid();
		try {
			map=JSON.parseObject(json);	
			map.put("total", houseService.getServerCount(map));
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getServerCount",e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);		
	}
	/**
	 * 服务器编辑
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/updateServer")
	public void updateServer(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		String callback = request.getParameter("callback");
		Map<String , Object> param = JSON.parseObject(json, Map.class);
		try{
			int i = houseService.updateServer(param);
			String resultJson = "{\"result\":"+i+"}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("updateServer", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}
	/**
	 * 删除服务器
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/deleteServer")
	public void deleteServer(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		String callback = request.getParameter("callback");
		Map<String , Object> param = JSON.parseObject(json, Map.class);
		try{
			int i = houseService.deleteServer(param);
			String resultJson = "{\"result\":"+i+"}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("deleteServer", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}
	@RequestMapping("/findhouseList2")
	public void findhouseList2(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		String ho=request.getParameter("hous");
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		Json j = new Json();
		DataGrid dg = new DataGrid();
		String startRow = request.getParameter("startRow");
		String pageSize = request.getParameter("pageSize");
		String orderBy = request.getParameter("orderBy");
		String uncheckedHouses = request.getParameter("uncheckedHouses");
		try {
			if(StringUtils.isBlank(startRow))
				startRow = "0";
			if(StringUtils.isBlank(pageSize))
				pageSize = "30";
			map=JSON.parseObject(json);	

			List<Map> t = houseService.findhouseList2(startRow, pageSize, orderBy, map, ho, uncheckedHouses);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("findhouseList",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");

		}

	}
	@RequestMapping("/getCount2")	
	public void getCount2(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String ho=request.getParameter("hous");
		String uncheckedHouses = request.getParameter("uncheckedHouses");
		Json j = new Json();
		DataGrid dg = new DataGrid();
		try {
			map=JSON.parseObject(json);	
			Integer d=houseService.getCount2(map, ho, uncheckedHouses);
			map.put("total",d);
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getCount2",e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);		
	}

	/**
	 * 删除家庭注册时，检查该houseieee是否在burn in列表中
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/findBurnin")
	public void findBurnin(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		String callback = request.getParameter("callback");
		House house = JSON.parseObject(json,House.class);
		try{
			int i = proxyserverService.findBurnin(house);
			String resultJson = "{\"result\":"+i+"}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("deleteServer", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			//			e.printStackTrace();
		}
	}



	//	/**
	//	 * 设备属性推送上来的，更新house表中的isonline
	//	 * @param json
	//	 * @param request
	//	 * @param response
	//	 * @throws IOException
	//	 */
	//	@RequestMapping("/updateHouseisonline")
	//	public void updateHouseisonline(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
	//		PrintWriter out=response.getWriter(); 
	//		response.setContentType("text/html;charset=utf-8");  
	//		String callback = request.getParameter("callback");
	//		House house = JSON.parseObject(json,House.class);
	//		try{
	//			int i = proxyserverService.updateHouseisonline(house);
	//			String resultJson = "{\"result\":"+i+"}";//成功1 失败0 
	//			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	//		} catch (Exception e) {
	//			LOGGER.info("updateHouseisonline", e);
	//			String resultJson = "{\"result\":0}";//成功1 失败0 
	//			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	//			e.printStackTrace();
	//		}
	//	}
	//
	@RequestMapping("/updatehousenode")
	public void updatehousenode(String json,String sign,String encodemethod,String houseIeeeSecret, HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		PrintWriter out=response.getWriter();
		String json243;
		House params=JSON.parseObject(json,House.class);
		Map<String, Object> params1=new HashMap<String, Object>();
		params1.put("houseIEEE",params.getHouseIeee());
		params1.put("nodeNum", params.getNodeNum());
		response.setContentType("text/html;charset=utf-8");
		String cloudAddress = PropertiesUtil.getProperty("proxy.ip");
		String cloudPort = PropertiesUtil.getProperty("proxy.port");
		int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));

		String uri=request.getRequestURI();
		try {
			proxyserverService.updatehousenode(params1);
			if (cloudMain==0) {
				TestHttpclient.postUrlWithParams("http://"+cloudAddress+":"+cloudPort+uri,params1,"utf-8");
			}			
			String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}
	}
	//短信邮件批量开启
	@RequestMapping("/NoteEmailBatchOpen")
	public void NoteEmailBatchOpen(String json,String houseieelis,HttpServletRequest request, HttpServletResponse response)
	{
		PrintWriter out = null;

		response.setContentType("text/html;charset=utf-8");
		try {
			out=response.getWriter(); 
			Map<String,Object> param=JSON.parseObject(json,Map.class);
			List<String> housieesLi=JSON.parseObject(houseieelis,List.class);
			int t=houseService.NoteEmailBatchOpen(param,housieesLi);
			String res="";
			if(t>0)
			{
				res="1";
			}
			if(t==0)
			{
				res="0"; 
			}
			if(t==-1)
			{
				res="2"; 
			}

			String resultJson = "{\"result\":"+res+"}";//成功1 失败0,2未开启过
			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("getHouses", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");

		}
	}

	@RequestMapping("/notebatchSend")
	public void notebatchSend(String json,String houseieelis,HttpServletRequest request, HttpServletResponse response)
	{
		LOGGER.info("notebatchSend json="+json);
		PrintWriter out=null;
		Messagehistory messagehistory=JSON.parseObject(json,Messagehistory.class);
		List<String> lishuose=JSON.parseObject(houseieelis,List.class);
		response.setContentType("text/html;charset=utf-8");
		try {
			out=response.getWriter();
			String sucerrString=houseService.notebatchSend(messagehistory,lishuose);

			String resultJson = "{\"result\":"+sucerrString+"}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":" + sucerrString + "}");
		} catch (Exception e) {
			// TODO: handle exception
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	//下载
	@RequestMapping("down")
	public void down(HttpServletRequest request, HttpServletResponse response)
	{
		PrintWriter out=null;
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		basePath+="cloudManageWeb/Filetemplate/22.xls";	
		try {
			response.sendRedirect(basePath);
			out=response.getWriter();
			out.print("\"dow\":\"下载成功\"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			LOGGER.info("down", e);
		}
	}
	public void SaveFileFromInputStream(InputStream stream,String filename) throws IOException{      
		try{    
			FileOutputStream fs=new FileOutputStream(filename);
			byte[] buffer =new byte[1024*1024];
			int bytesum = 0;
			int byteread = 0; 
			while ((byteread=stream.read(buffer))!=-1)
			{
				bytesum+=byteread;
				fs.write(buffer,0,byteread);
				fs.flush();
			} 
			fs.close();
			stream.close();    
		}catch(IOException e){
			LOGGER.info("SaveFileFromInputStream", e);
		}
	}    

	//导入
	@RequestMapping("importhouseieee")
	public void importhouseieee(String filepath, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException
	{
		response.addHeader("Access-Control-Allow-Origin", "*");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		List<Map<String,Object>> result =null;
		//      HashMap<String, String> request = new HashMap<String, String>(); 
		//		{"title":"aaa","infomation":"user:ssss\nmobile:ss\n","description":"cccc"}
		Json j = new Json();
		DataGrid dg = new DataGrid();
		File file=null;
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024 * 20, null);  
			ServletFileUpload upload = new ServletFileUpload(factory);  
			upload.setHeaderEncoding("UTF-8");  
			try{
				List<FileItem> fileItems = upload.parseRequest(request);  
				Iterator<FileItem> iter = fileItems.iterator(); 

				while (iter.hasNext()) {  
					FileItem item = iter.next();  
					if (item.isFormField()) {  
						String name = item.getFieldName();  
						String value = item.getString("UTF-8");                   
						TestLog4j.testInfo("name----"+name+"value----"+value);                   
					} 
					else {  
						if (StringUtils.isBlank(item.getName())) {  
							continue;  
						}
						File files=new File("C:/javaimportfile");
						if(!files.exists()&&!files.isDirectory())
						{
							//files.createNewFile();文件
							files.mkdirs();//文件夹
						}
						TestLog4j.testInfo("----this is a file!"); 
						SaveFileFromInputStream(item.getInputStream(),
								"C:/javaimportfile/"+item.getName());
						String lsopeString="C:/javaimportfile/"+item.getName();
						file=new File("C:/javaimportfile/"+item.getName());
						result = getData(file, 0);
					}  
				}      
				List<String> liresult = new ArrayList<String>();
				List<String> lihhList = new ArrayList<String>();
				for(int i=1; i<result.size() ;i++){
					liresult.add((String) result.get(i).get("houseIEEE"));
				}
				List<Map> hhList=houseService.findimport(result);
				for (int i = 0; i < hhList.size(); i++) {
					lihhList.add((String) hhList.get(i).get("houseIEEE"));
				}
				String temp1 = liresult.toString ().replaceAll ("[\\[\\]]", ",").replaceAll ("\\s+", "");
				String temp2 = lihhList.toString ().replaceAll ("[\\[\\]]", ",").replaceAll ("\\s+", "");
				String result2 = "";
				List<String> list1=new ArrayList<String>();
				for ( int i = 0; i < liresult.size (); i++ )
				{
					if (temp2.indexOf ("," + liresult.get (i) + ",") == -1)
					{
						list1.add(liresult.get (i));
					}
				}
				String json=JSON.toJSONString(hhList);
				String json1=JSON.toJSONString(list1);
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.println("{\"request_id\": 1234, \"existList\":" + json + ", \"unexistList\":" + json1 + "}");
			}catch (Exception e) {
				LOGGER.info("upload", e);
				dg.setStatus(0);
				dg.setStatus_msg("fail");
				j.setResponse_params(dg);	
				String json=JSON.toJSONString(j);
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.println(json);  
			}
		}
		//	       
		//		   int rowLength = result.length;
		//           String jsonString="";
		//	       for(int i=0;i<rowLength;i++) {
		//
		//	           for(int j=0;j<result[i].length;j++) {
		//
		//	        	   jsonString+=result[i][j]+"\t\t";
		//	             // System.out.print(result[i][j]+"\t\t");
		//	           }
		//	       }
	}
	public static String rightTrim(String str) {

		if (str == null) {

			return "";

		}

		int length = str.length();

		for (int i = length - 1; i >= 0; i--) {

			if (str.charAt(i) != 0x20) {

				break;

			}

			length--;

		}

		return str.substring(0, length);

	}
	public static List<Map<String,Object>> getData(File file, int ignoreRows)

			throws FileNotFoundException, IOException {

		List<String[]> result = new ArrayList<String[]>();
		List<Map<String,Object>> lismap=new ArrayList<Map<String,Object>>();
		int rowSize = 0;

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(

				file));

		// 打开HSSFWorkbook

		POIFSFileSystem fs = new POIFSFileSystem(in);

		HSSFWorkbook wb = new HSSFWorkbook(fs);

		HSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

			HSSFSheet st = wb.getSheetAt(sheetIndex);
			HSSFRow row0 = st.getRow(0);
			// 第一行为标题，不取

			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

				HSSFRow row = st.getRow(rowIndex);

				if (row == null) {

					continue;

				}

				int tempRowSize = row.getLastCellNum() + 1;

				if (tempRowSize > rowSize) {

					rowSize = tempRowSize;

				}

				String[] values = new String[rowSize];

				Arrays.fill(values, "");

				boolean hasValue = false;
				Map<String,Object> mapliMap=new HashMap<String,Object>();
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {

					String value = "";

					cell = row.getCell(columnIndex);

					if (cell != null) {

						// 注意：一定要设成这个，否则可能会出现乱码

						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:

							value = cell.getStringCellValue();
							if ((row0.getCell(columnIndex).toString()).equals("houseIEEE")) {
								mapliMap.put("houseIEEE",value);
							}
							//clientCode
							if ((row0.getCell(columnIndex).toString()).equals("clientCode")) {
								mapliMap.put("clientCode",value);
							}
							if ((row0.getCell(columnIndex).toString()).equals("name")) {
								mapliMap.put("name",value);
							}
							break;

						case Cell.CELL_TYPE_NUMERIC:

							if (DateUtil.isCellDateFormatted(cell)) {

								Date date = cell.getDateCellValue();

								if (date != null) {

									value = new SimpleDateFormat("yyyy-MM-dd")

											.format(date);

								} else {

									value = "";

								}

							} else {

								value = new DecimalFormat("0").format(cell

										.getNumericCellValue());
								//value = cell.getStringCellValue();
								if ((row0.getCell(columnIndex).toString()).equals("houseIEEE")) {
									mapliMap.put("houseIEEE",value);
								}
								//clientCode
								if ((row0.getCell(columnIndex).toString()).equals("clientCode")) {
									mapliMap.put("clientCode",value);
								}
								if ((row0.getCell(columnIndex).toString()).equals("name")) {
									mapliMap.put("name",value);
								}
							}

							break;

						case Cell.CELL_TYPE_FORMULA:

							// 导入时如果为公式生成的数据则无值

							if (!cell.getStringCellValue().equals("")) {

								value = cell.getStringCellValue();

							} else {

								value = cell.getNumericCellValue() + "";

							}

							break;

						case Cell.CELL_TYPE_BLANK:

							break;

						case Cell.CELL_TYPE_ERROR:

							value = "";

							break;

						case Cell.CELL_TYPE_BOOLEAN:

							value = (cell.getBooleanCellValue() == true ? "Y"

									: "N");

							break;

						default:

							value = "";

						}

					}

					if (columnIndex == 0 && value.trim().equals("")) {

						break;

					}

					values[columnIndex] = rightTrim(value);

					hasValue = true;

				}



				if (hasValue) {

					result.add(values);
					lismap.add(mapliMap);

				}

			}

		}

		in.close();

		String[][] returnArray = new String[result.size()][rowSize];

		for (int i = 0; i < returnArray.length; i++) {

			returnArray[i] = result.get(i);

		}

		return lismap;

	}

	@RequestMapping("/updateHouseInfo")
	public void updateHouseInfo(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		String callback = request.getParameter("callback");
		String resultJson = "{\"result\":0}";//成功1 失败0
		try {        	
			House house = JSON.parseObject(json,House.class);
			Houseieee houseieee = JSON.parseObject(json,Houseieee.class);
			//往云端服务器推
			String cloudAddress = (String) HouseieeeListener.houseieeeProxyserverMap.get(house.getHouseIeee());
			String cloudPort = PropertiesUtil.getProperty("proxy.port");
			int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
			String serverHost = request.getServerName();
			if(cloudMain == 1) {
				//修改云端服务器的厂商代码
				if(!cloudAddress.equals(serverHost) 
						&& !cloudAddress.equals("localhost") 
						&& !cloudAddress.equals("127.0.0.1")
						&& !serverHost.equals("localhost") 
						&& !serverHost.equals("127.0.0.1")) {
					String callUrl = "http://" + cloudAddress + ":" + cloudPort + request.getRequestURI();
					Map<String, String[]> rMap = request.getParameterMap();
					if(rMap != null) {
						Map<String, Object> pMap = null;
						pMap = new HashMap<String, Object>();
						Iterator<String> it=rMap.keySet().iterator();   
						while(it.hasNext()){ 
							String key=it.next();  
							//post方法去除json参数
							String value = (rMap.get(key))[0].toString();
							pMap.put(key, value);
						}
						String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
						rsStr = rsStr.trim();
						rsStr = rsStr.substring(rsStr.indexOf("callback(") + 9, rsStr.length() - 1);
						Map<String, Object> resultMap = JSON.parseObject(rsStr, Map.class);
						int result = Integer.parseInt(((Map) resultMap.get("response_params")).get("result").toString()); 
						if(result == 0) {
							out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
							return;
						}
					}
				}
			}

			Houseieee t2 = houseieeeService.get(houseieee);
			if (StringUtils.isBlank(house.getHouseIeee()) || house.getHouseIeee().length() < 16 ) {
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
				return;
			}
			if (t2 != null) {
				if(houseieee.getVendorCode()!=null){
					t2.setVendorCode(houseieee.getVendorCode());
				}
				houseieeeService.update(t2);
				HouseieeeListener.houseieeeMap.put(t2.getHouseIeee(), t2);
				resultJson = "{\"result\":1}";//成功1 失败0
			}
			else {
				resultJson = "{\"result\":-1}";//成功1 失败0
			}

			if(cloudMain == 1) {
				//修改对应模式编辑器的内存中的厂商代码
				String modelUrl = "http://" + serverHost + ":" + cloudPort + "/cloudEditor/houseController/update.do";
				Map<String, Object> modelParamMap = new HashMap<String,Object>();
				modelParamMap.put("json", "{\"houseIeee\":\"" + house.getHouseIeee() + "\"}");
				TestHttpclient.postUrlWithParams(modelUrl, modelParamMap, "utf-8");
			}
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("add",e);
			resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}
	}

	@RequestMapping("/getVendorCode")
	public void getVendorCode(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		try {        	
			Houseieee houseieee = JSON.parseObject(json,Houseieee.class);
			Houseieee t2 = houseieeeService.get(houseieee);
			out.println(t2.getVendorCode()); 
		} catch (Exception e) {
			LOGGER.info("getVendorCode",e);
			out.println("-10");   
		}
	}

	@RequestMapping("/sendOfMemWarn")
	public void sendOfMemWarn(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		try {        	
			Map sendParam = JSON.parseObject(json, Map.class);
			String[] emailList = PropertiesUtil.getProperty("warnMail").split(";");
			String[] mobileList = PropertiesUtil.getProperty("warnMobile").split(";");
			sendParam.put("emailList", emailList);
			sendParam.put("mobileList", mobileList);
			sendParam.put("message", sendParam.get("warnIp") + "的openfire内存占用率超过了" + sendParam.get("warnLevel") + "%，当前占用率为" + sendParam.get("percentUsed") + "%");
			String jsonTmp = JSON.toJSONStringWithDateFormat(sendParam, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			sendSMSAndEmail(jsonTmp, request.getParameter("sign"), request, response);
		} catch (Exception e) {
			LOGGER.info("sendOfMemWarn", e);
		}
	}

	@RequestMapping("/sendSMSAndEmail")
	public void sendSMSAndEmail(String json,String sign, HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		try {        	
			if(StringUtils.isBlank(sign)) { 
				out.println("{\"result\":-1}");
				return;
			}
			String prefixKey = PropertiesUtil.getProperty("prefixKey");
			String pKey = prefixKey + new SimpleDateFormat("yyyyMMdd").format(new Date());
			String decodeUri = Httpproxy.urlCodec16(sign, pKey);
			String reqUri = request.getRequestURI();
			if(!decodeUri.equals(reqUri)) {
				out.println("{\"result\":-2}");
				return;
			}
			Map sendParam = JSON.parseObject(json, Map.class);
			String message = (String) sendParam.get("message");
			List<String> emailList = (List<String>) sendParam.get("emailList");
			List<String> mobileList = (List<String>) sendParam.get("mobileList");
			//发送邮件
			if(emailList != null && !emailList.isEmpty())
				houseService.sendemail("openfire内存告警", message, emailList);
			//发短信
			if(mobileList != null && !mobileList.isEmpty())
				SendSMS.getSingleSendSMS().SendMultiSMS(message, mobileList);
			out.println("{\"result\":1}");
		} catch (Exception e) {
			LOGGER.info("sendSMSAndEmail", e);
			out.println("{\"result\":0}");   
		}
	}

	/**
	 * 判断是否超过限定，否则返回1
	 * @param cloudIp
	 * @param addCount
	 * @return
	 */
	private int isHouseExceed(String cloudIp, int addCount) throws Exception {
		String classPath = this.getClass().getResource("/").getPath();
		int serverNum = getMaxServerNum(classPath);
		if(serverNum == -10) {
			return serverNum;
		}
		int serverNumHaved = houseService.getRegisterCount(cloudIp);
		if((serverNumHaved + addCount) > serverNum) {
			return -11; //家庭数量已超过最大数限制
		}
		return 1;
	}

	/**
	 * 获取注册文件中的最大账户数
	 * @param classPath
	 * @return
	 * @throws Exception
	 */
	private int getMaxServerNum(String classPath) throws Exception {
		String serverNumPath = classPath + "serverNum.xml";
		LOGGER.info("-------serverNumPath:" + serverNumPath);
		File serverNumFile = new File( serverNumPath);
		if(!serverNumFile.exists()) {
			return -10; //文件不存在则返回-11
		}
		String serverNumDecodePath = classPath + "serverNumDecode.xml";
		LOGGER.info("-------serverNumDecodePath:" + serverNumDecodePath);
		File serverNumDecodeFile = new File(serverNumDecodePath); 
		if(serverNumDecodeFile.exists()) {
			serverNumDecodeFile.delete();
		}
		CharStream.reader(serverNumPath);
		CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
		CharStream.writer(serverNumDecodePath);
		serverNumDecodeFile = new File(serverNumDecodePath);
		SAXReader reader = new SAXReader();
		Document document = reader.read(serverNumDecodeFile);// 读取XML文件
		Element root = document.getRootElement();
		String serverNum = root.getTextTrim();
		return Integer.parseInt(serverNum);
	}

	@RequestMapping("/getMaxServerNum")
	public void getMaxServerNum(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter(); 
		String resultJson = "{\"result\":0}";
		String callback = request.getParameter("callback");
		try {        	
			int serverNum = getMaxServerNum(this.getClass().getResource("/").getPath());
			resultJson = "{\"result\":1,\"maxServerNum\":" + serverNum + "}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getMaxServerNum", e);
			out.println("{\"result\":0}");   
		}
	}
	@RequestMapping("/getServerInfo")
	public void getServerInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			List<Map> serverInfoList = houseService.getServerInfo();
			String resultJson=JSON.toJSONStringWithDateFormat(serverInfoList, "yyyy-MM-dd HH:mm:ss");
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");

		} catch (Exception e) {
			LOGGER.info("getServerIP", e);
			String resultJson = "{\"result\":0}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	@RequestMapping("/isAllServerTheSame")
	public void isAllServerTheSame(String json, HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String uncheckedHouses = request.getParameter("uncheckedHouses");
			boolean isAllSame = houseService.isServerAllTheSame(paramMap, uncheckedHouses);
			out.println(callback +"({\"request_id\": 1234, \"response_params\":{\"result\":1,\"isServerAllTheSame\":" + isAllSame + "}})");
		} catch (Exception e) {
			LOGGER.info("getServerIP", e);
			String resultJson = "{\"result\":0}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	@RequestMapping("/getMoveResultCount")
	public void getMoveResultCount(String json, HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			out.println(callback +"({\"request_id\": 1234, \"response_params\":{\"result\":1,\"total\":" + moveResultList.size() + "}})");
		} catch (Exception e) {
			LOGGER.info("getServerIP", e);
			String resultJson = "{\"result\":0}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	@RequestMapping("/getMoveResultList")
	public void getMoveResultList(String json, HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			int startRow = Integer.parseInt(request.getParameter("startRow"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			int maxRow = startRow + pageSize;
			maxRow = maxRow > moveResultList.size()? moveResultList.size() : maxRow;
			List<Map> pageResultList = new ArrayList<>();
			for(int i = startRow; i < maxRow; i++) {
				pageResultList.add(moveResultList.get(i));
			}
			String resultJson=JSON.toJSONStringWithDateFormat(pageResultList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			out.println(callback +"({\"request_id\": 1234, \"response_params\":{\"result\":1,\"pageResultList\":" + resultJson + "}})");
		} catch (Exception e) {
			LOGGER.info("getServerIP", e);
			String resultJson = "{\"result\":0}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/getGateWays")
	public void getGateWays(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			Map param = JSON.parseObject(json, Map.class);
			String houseIeee = param.get("houseIEEE").toString();
			String userid = param.get("userid").toString();
			if(StringUtils.isBlank(houseIeee)){
				String resultJson = "{\"result\":0}";
				out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			List<Map> list = houseService.getGateWays(houseIeee, userid);
			String resultJson = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			out.println(callback +"({\"request_id\": 1234, \"response_params\":{\"result\":1,\"getList\":" + resultJson + "}})");
		} catch (Exception e) {
			LOGGER.info("getGateWays", e);
			String resultJson = "{\"result\":0}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}

	}
	@RequestMapping("/getMonitorState")
	public void getMonitorState(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			Map<String, Object> params = JSON.parseObject(json, Map.class);
			String house = (String) params.get("houseIeee");
			if(StringUtils.isBlank((String)params.get("houseIeee"))) {
				String resultJson = "{\"result\":0}";
				out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			List<Map> list = this.houseService.getMonitorState(house);
			String resultJson = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			out.println(callback +"({\"request_id\": 1234, \"response_params\":{\"result\":1,\"getList\":" + resultJson + "}})");
		} catch (Exception e) {
			LOGGER.info("getMonitorState", e);
			String resultJson = "{\"result\":0}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	/*手机用户注册时获取Ip地址*/
	@RequestMapping("/FindServerUser")
	public void FindServerUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			List<Map> serverInfoList = houseService.FindServerUser();
			String resultJson=JSON.toJSONStringWithDateFormat(serverInfoList, "yyyy-MM-dd HH:mm:ss");
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");

		} catch (Exception e) {
			LOGGER.info("FindServerUser", e);
			String resultJson = "{\"result\":0}";
			out.println(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}

	/**
	 * 
	 * @param serverIp 网关连接的云端服务器ip地址
	 * @param houseIeee 网关ieee地址
	 * @param smsType 短信推送方式 0：默认（走第三方接口）      1：本网关网卡推送
	 * @param isNeedResult 是否需要返回结果
	 */
	private String noticeSmsEnable(final String serverIp,final String houseIeee,final byte smsType,final boolean isNeedResult){
		if(StringUtils.isBlank(serverIp)){
			LOGGER.info("server ip can not be empty");
			return null;
		}
		if(StringUtils.isBlank(houseIeee)){
			LOGGER.info("houseIeee can not be empty");
			return null;
		}
		LOGGER.info("Create Notice Thread Start···");
		if(isNeedResult){
			return sendNotice(serverIp,houseIeee,smsType);
		}else{
			//通知网关开启本网关Dongle短信推送
			Thread thread = new Thread(){
				public void run(){
					sendNotice(serverIp,houseIeee,smsType);
				}
			};
			thread.start();
			return null;
		}
	}
	
	/**
	 * 下发通知
	 * @param serverIp
	 * @param houseIeee
	 * @param smsType
	 * @return
	 */
	private String sendNotice(String serverIp,String houseIeee,Byte smsType){
		LOGGER.info("send notice to gateway start···");
		String serverPort = PropertiesUtil.getProperty("xmpp.port");
		StringBuilder sb = new StringBuilder("http://");
		sb.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=setsmsenable/");
		sb.append(smsType).append("&username=").append(houseIeee);
		try{
        	//加密(也可不加密，但根据网关接口的要求，sign字段的长度需要大于8位)
        	String content="context=setsmsenable/"+smsType+"&username="+houseIeee;
        	String key = houseIeee.substring(6)+"NETVOX";
        	String encryptResultStr = Httpproxy.urlEncrypt(content, key);
			sb.append("&sign=").append(encryptResultStr).append("&encodemethod=AES&houseIeeeSecret=").append(houseIeee);
    		
        	String str = TestHttpclient.getUrl(sb.toString(),"utf-8");
        	Map<String, Object> result = JSON.parseObject(str, Map.class);
        	LOGGER.info(str);
        	LOGGER.info("Notice GateWay End···");
        	return str;
		}catch(Exception e){
			LOGGER.error("Notice GateWay Upate SmsType Error", e);
		}
		return null;
	}
	
	/**
	 * 返回经纬度
	 * 创建时间 2017-06-04
	 * @author chengwangjian
	 */
	
	@RequestMapping("/getLongLati")
	public void getLongLati(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    PrintWriter out = response.getWriter();
	    response.setContentType("text/html;setchar=utf-8");
	    String callback = request.getParameter("callback");
		try{
			List<Map> map = houseService.getLong();
			String resultJson = JSON.toJSONString(map);
			out.print(callback +"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
