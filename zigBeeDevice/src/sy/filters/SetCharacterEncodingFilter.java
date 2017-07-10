package sy.filters;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import sy.model.FarmUser;
import sy.model.Houseieee;
import sy.model.ReliUser;
import sy.service.HouseieeeServiceI;
import sy.util.EncodeUtils;
import sy.util.HouseieeeListener;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.ServerRegisterUtil;
import sy.util.TestHttpclient;

public class SetCharacterEncodingFilter implements Filter {
	protected String encoding;
	protected FilterConfig filterConfig;
	protected boolean ignore;
	String cloudMain = PropertiesUtil.getProperty("cloudMain");
	String cloudPort = PropertiesUtil.getProperty("cloudPort");
	String cloudAddressport = PropertiesUtil.getProperty("cloudAddress.port");
	String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
	private HouseieeeServiceI houseieeeService;
	private static final Logger LOGGER = Logger.getLogger(SetCharacterEncodingFilter.class);

	public HouseieeeServiceI getHouseieeeService() {
		return houseieeeService;
	}

	@Autowired
	public void setHouseieeeService(HouseieeeServiceI houseieeeService) {
		this.houseieeeService = houseieeeService;
	}

	public SetCharacterEncodingFilter() {
		encoding = null;
		filterConfig = null;
		ignore = true;
	}

	@Override
	public void destroy() {
		encoding = null;
		filterConfig = null;
	}

	private boolean redirectToBackEnd(String json, String uri, HttpServletRequest request, HttpServletResponse response,
			int callFlag) throws Throwable {
		String cloudAddress = null;
		if (callFlag == 1) {
			String tmpJson = json.toLowerCase();
			int preIndex = tmpJson.indexOf("houseieee");
			int houseIeeeLen = 9;
			if (preIndex == -1) {
				preIndex = tmpJson.indexOf("house_ieee");
				if (preIndex == -1)
					return false;
				houseIeeeLen = 10;
			}
			int suffixIndex = preIndex + houseIeeeLen;
			json = json.substring(0, preIndex) + "houseIeee" + json.substring(suffixIndex);
			Houseieee houseieee4 = new Houseieee();
			houseieee4 = JSON.parseObject(json, Houseieee.class);
			cloudAddress = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseieee4.getHouseIeee());
		} else if (callFlag == 2) {
			cloudAddress = PropertiesUtil.getProperty("bugfree.ip");
		}
		if (StringUtils.isBlank(cloudAddress))
			return false;
		String serverHost = request.getServerName();
		LOGGER.info("cloudAddress:" + cloudAddress + ",cloudPort:" + cloudAddressport + ",localHost:" + serverHost);
		if (!cloudAddress.equals(serverHost) && !cloudAddress.equals("localhost") && !cloudAddress.equals("127.0.0.1")
				&& !serverHost.equals("localhost") && !serverHost.equals("127.0.0.1")) {
			response.setContentType("text/html;charset=utf-8");
			String rsStr = "";
			String callUrl = "http://" + cloudAddress + ":" + cloudAddressport + uri;
			Map<String, String[]> rMap = request.getParameterMap();
			if (rMap != null) {
				Map<String, Object> pMap = new HashMap<String, Object>();
				Iterator<String> it = rMap.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					// post方法去除json参数
					String value = (rMap.get(key))[0].toString();
					pMap.put(key, value);
				}
				if (callFlag == 1) {
					rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
					response.getWriter().write(rsStr);
					response.getWriter().flush();
				} else if (callFlag == 2) {
					boolean isMultipart = ServletFileUpload.isMultipartContent(request);
					if (isMultipart) {
						DiskFileItemFactory factory = new DiskFileItemFactory();
						ServletFileUpload upload = new ServletFileUpload(factory);
						// 设置上传的普通字段的名称和文件字段的文件名所采用的字符集编码
						upload.setHeaderEncoding("utf-8");
						// 得到所有表单字段对象的集合
						List<FileItem> file = upload.parseRequest(request);
						Iterator itor = file.iterator();
						while (itor.hasNext()) {
							FileItem fi = (FileItem) itor.next();
							rsStr = TestHttpclient.postUrlWithFile(callUrl, pMap,
									(FileInputStream) fi.getInputStream());
							response.getWriter().write(rsStr);
							response.getWriter().flush();
						}
					} else {
						rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
						response.getWriter().write(rsStr);
						response.getWriter().flush();
					}
				}
				return true;
			}
			return false;
		} else
			return false;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		String callback = request.getParameter("callback");
		HttpServletRequest request2 = (HttpServletRequest) request;
		final HttpServletResponse response2 = (HttpServletResponse) response;
		response2.setHeader("Access-Control-Allow-Origin", "*");
		String url = request2.getRequestURL().toString();
		String httpMethod = request2.getMethod();
		String json = request.getParameter("json");
		// 以下链接需要跳转到数据存放的云端
		String uri = request2.getRequestURI();
		try {
			// 若用户用旧的模式编辑器上下文，则实现跳转
			if (url.indexOf("/zigBeeDevice/model_editor/login.htm") > -1) {
				LOGGER.info("redirect to cloudEditor-------------");
				url = url.replaceAll("zigBeeDevice", "cloudEditor");
				response2.sendRedirect(url);
				return;
			}
			// 判断服务器注册是否合法
			if (ServerRegisterUtil.isRegisterValid == 0) {
				LOGGER.info("--------------server register not valid");
				PrintWriter out = response.getWriter();
				response.setContentType("text/html;charset=utf-8");
				String resultJson = "{\"result\":-20,\"message\":\"server not register\"}";// 注册不合法-20
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			// 同步调用
			if (StringUtils.contains(url, "IR/getBMs.do") || StringUtils.contains(url, "IR/getBrands.do")
					|| StringUtils.contains(url, "IR/getModels.do") || StringUtils.contains(url, "IR/getIRXml.do")
					|| StringUtils.contains(url, "IR/getBMsByName.do")
					|| StringUtils.contains(url, "IR/getIRBrandList.do")
					|| StringUtils.contains(url, "IR/getPowerOn.do")
					|| StringUtils.contains(url, "IR/getIRModelList.do") || StringUtils.contains(url, "IR/findbrand.do")
					|| StringUtils.contains(url, "IR/getBrandByIRType.do")
					|| StringUtils.contains(url, "IR/uploadIRxml.do")
					|| StringUtils.contains(url, "IR/downloadIRxml.do")
					|| StringUtils.contains(url, "ApponlineinfoController/addappinfo.do")
					|| StringUtils.contains(url, "ApponlineinfoController/appfindad.do")
					|| StringUtils.contains(url, "ApponlineinfoController/updatereadstate.do")
					|| StringUtils.contains(url, "getCmdData.do") || StringUtils.contains(url, "refreshCmdData.do")) {
				String serverHost = request.getServerName();
				LOGGER.info("cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);
				if (!cloudAddress.equals(serverHost) && !cloudAddress.equals("localhost")
						&& !cloudAddress.equals("127.0.0.1") && !serverHost.equals("localhost")
						&& !serverHost.equals("127.0.0.1")) {
					String rsStr = "";
					String callUrl = "http://" + cloudAddress + ":" + cloudPort + uri;
					Map<String, String[]> rMap = request2.getParameterMap();
					if (rMap != null) {
						Map<String, Object> pMap = new HashMap<String, Object>();
						Iterator<String> it = rMap.keySet().iterator();
						while (it.hasNext()) {
							String key = it.next();
							// post方法去除json参数
							String value = (rMap.get(key))[0].toString();
							pMap.put(key, value);
						}
						rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
					}

					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(rsStr);
					response.getWriter().flush();
					return;
				}
			} else if (StringUtils.contains(url, "deviceController/getListoryCount.do")
					|| StringUtils.contains(url, "deviceController/findonline.do")
					// || StringUtils.contains(url,
					// "deviceController/exportDeviceExcel.do")
					|| StringUtils.contains(url, "deviceoperatehistoryHouseidYearController/getListoryCount.do")
					|| StringUtils.contains(url, "deviceoperatehistoryHouseidYearController/findListory.do")
					|| StringUtils.contains(url, "deviceoperatehistoryHouseidYearController/getOnlinTime.do")
					|| StringUtils.contains(url, "deviceoperatehistoryHouseidYearController/operateActionUser.do")
					// || StringUtils.contains(url,
					// "deviceoperatehistoryHouseidYearController/exportOperateLogExcel.do")//导出操作列表
					|| StringUtils.contains(url, "rssi/getRssiCount.do") || StringUtils.contains(url, "rssi/getRSSI.do")
					|| StringUtils.contains(url, "rssi/getNewRssiCount.do")
					|| StringUtils.contains(url, "rssi/getNewRSSI.do")
					|| StringUtils.contains(url, "rssi/getNoResponseCount.do")
					|| StringUtils.contains(url, "rssi/getNoResponseDevice.do")
					// || StringUtils.contains(url, "rssi/exportLqiLogExcel.do")
					// //导出LQI列表
					// || StringUtils.contains(url,
					// "rssi/exportRssiLogExcel.do") //导出RSSI列表
					|| StringUtils.contains(url, "rssi/startLQI.do") || StringUtils.contains(url, "rssi/getProgress.do")
					|| StringUtils.contains(url, "rssi/getLR.do")
					|| StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getListCount.do")
					|| StringUtils.contains(url, "deviceattributehistoryHouseidYearController/find2.do")
					// || StringUtils.contains(url,
					// "deviceattributehistoryHouseidYearController/exportAttrLogExcel.do")
					|| StringUtils.contains(url, "deviceattributehistoryHouseidYearController/attributeState.do")
					|| StringUtils.contains(url, "rssi/add.do")
					|| StringUtils.contains(url, "deviceController/updateIsonline.do")
					|| StringUtils.contains(url, "devicewarnhistoryHouseidYearController/getwarnCount.do")
					|| StringUtils.contains(url, "devicewarnhistoryHouseidYearController/getZoneWmode.do")
					|| StringUtils.contains(url, "devicewarnhistoryHouseidYearController/getWarnMode.do")
					|| StringUtils.contains(url, "devicewarnhistoryHouseidYearController/findwarnListory.do")
					// || StringUtils.contains(url,
					// "devicewarnhistoryHouseidYearController/exportWarnLogExcel.do")
					// //导出报警列表
					|| StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getHeartbeatCount.do")
					|| StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getHeartbeat.do")
					|| StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getElectricityRelated.do")
					|| StringUtils.contains(url,
							"deviceattributehistoryHouseidYearController/getElectricityRelatedCount.do")
					|| StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getBatteryVoltage.do")
					|| StringUtils.contains(url,
							"deviceattributehistoryHouseidYearController/getBatteryVoltageCount.do")
					|| StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getVolotageValue.do")
					// || StringUtils.contains(url,
					// "deviceattributehistoryHouseidYearController/exportAttrLogExcel.do")//导出属性列表
					|| StringUtils.contains(url, "roomController/add.do")) {
				if (redirectToBackEnd(json, uri, request2, response2, 1))
					return;
			}

			if (ignore || request.getCharacterEncoding() == null) {
				String encoding = selectEncoding(request);
				if (encoding != null) {
					request.setCharacterEncoding(encoding);
					response.setCharacterEncoding(encoding);
				}
			}

			// 如果是get request，则用request.getQueryString()获取request information
			String requestInfo = "";
			// 获取协议(根据不同协议对参数进行不同方式的编解码)
			String scheme = request2.getScheme();
			if (json != null && "https".equalsIgnoreCase(scheme))
				json = EncodeUtils.urlDecode(json, "UTF-8");
			if (httpMethod.equals("GET")) { // get请求后台http请求 utf-8编码 中文不会乱码,ie
											// http请求gbk不会乱码
				if (request2.getQueryString() != null) {
					// get中文乱码
					if ("https".equalsIgnoreCase(scheme))
						requestInfo = EncodeUtils.urlDecode(request2.getQueryString(), "UTF-8");
					else
						requestInfo = EncodeUtils.encodeStr(request2.getQueryString(), "gbk"); // ie
																								// http请求gbk不会乱码,其他浏览器乱码
					requestInfo = requestInfo.replaceAll("&", ";");
				}
			} else if (httpMethod.equals("POST")) { // http post请求 utf-8编码
													// 中文不会乱码
				// 如果是post request，则用BufferedReader获取request information
				StringBuffer stringValue = new StringBuffer();
				String keyandValue = "";
				String key = "";
				String value = "";
				if (request.getParameterMap() != null) {
					Iterator<String> it = request.getParameterMap().keySet().iterator();
					while (it.hasNext()) {

						key = it.next();
						// post方法去除json参数
						if ((StringUtils.contains(url, "updateZ203Isonline.do")
								|| StringUtils.contains(url, "updateappisonline.do")) && key.equalsIgnoreCase("json"))
							continue;
						if ("https".equalsIgnoreCase(scheme))
							value = EncodeUtils.urlDecode((request.getParameterMap().get(key))[0].toString(), "UTF-8");
						else
							value = (request.getParameterMap().get(key))[0].toString();
						keyandValue = key + "=" + value;
						stringValue.append(keyandValue + ";");
					}
					if (stringValue.length() > 0) {
						stringValue.deleteCharAt(stringValue.lastIndexOf(";"));
					}
				}
				requestInfo = stringValue.toString();
			}

			if (StringUtils.isNotBlank(requestInfo)) {
				url = url + "?" + requestInfo;
			}
			LOGGER.info("url==" + url);
			LOGGER.info("json==" + json);

			if (StringUtils.contains(url, "reliUser/isLoginuser.do")
					// || SringUtils.contains(url, "reliUser/function.do")
					|| StringUtils.contains(url, "houseController/find2.do")
					|| StringUtils.contains(url, "houseController/getHouseCount.do")
					|| StringUtils.contains(url, "houseController/getHouses.do")

					|| StringUtils.contains(url, "houseController/findpage.do")
					|| StringUtils.contains(url, "houseController/getCount.do")
					|| StringUtils.contains(url, "houseController/findhouseAndhouseieee.do")
					|| StringUtils.contains(url, "houseController/getGateWays.do")
					|| StringUtils.contains(url, "houseController/findMonitorlog.do")
					|| StringUtils.contains(url, "houseController/findMonitorlog.do")// 日志监控
					|| StringUtils.contains(url, "houseController/getMonitorlogCount.do")// 日志监控总数
					|| StringUtils.contains(url, "ReleasehistoryController/getCount.do")
					|| StringUtils.contains(url, "HardhistoryController/")
					|| StringUtils.contains(url, "AdvertisementController/find1.do")
					|| StringUtils.contains(url, "AdvertisementController/updateAdvertisement.do")
					|| StringUtils.contains(url, "ApponlineinfoController/updateAdvertisement.do")
					|| StringUtils.contains(url, "brinController/getCount.do")
					|| StringUtils.contains(url, "brinController/findbrin.do")
					|| StringUtils.contains(url, "brinController/onlineCount.do")
					|| StringUtils.contains(url, "houseController/NoteEmailBatchOpen.do")
					|| StringUtils.contains(url, "houseController/down.do")
					|| StringUtils.contains(url, "houseController/importhouseieee.do")
					|| StringUtils.contains(url, "houseController/getServerCount.do")
					|| StringUtils.contains(url, "houseController/findServerlib.do")
					|| StringUtils.contains(url, "messagehistoryController/successCount.do")
					|| StringUtils.contains(url, "messagehistoryController/findList2.do")) {
				ReliUser t4 = (ReliUser) request2.getSession().getAttribute("reliUser");
				if (t4 == null || t4.getId() == 0) { // 用户没登录
					LOGGER.info("url filter==failure cloudManageWeb");
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					String resultJson = "{\"result\":-99}";// 成功1 失败0
					out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
					return;
				}
			}

			boolean flag2 = false;
			if (StringUtils.contains(url, ".do")
					&& (StringUtils.contains(url, "houseIeee") || StringUtils.contains(url, "houseIEEE")
							|| StringUtils.contains(url, "enableFlag"))
					&& !StringUtils.contains(url, "houseController/add.do")
					&& !StringUtils.contains(url, "houseController/find.do")
					&& !StringUtils.contains(url, "houseController/updateshc.do")
					&& !StringUtils.contains(url, "houseController/find2.do")
					&& !StringUtils.contains(url, "houseController/findhouseAndhouseieee.do")
					&& !StringUtils.contains(url, "houseController/getshc.do")
					&& !StringUtils.contains(url, "houseController/delete1.do")
					&& !StringUtils.contains(url, "houseieeeController/add.do")
					&& !StringUtils.contains(url, "houseieeeController/find.do")
					&& !StringUtils.contains(url, "proxyserverController")
					&& !StringUtils.contains(url, "generalController")
					&& !StringUtils.contains(url, "houseController/addBatch.do")
					&& !StringUtils.contains(url, "houseController/updatecloseshc.do")
					&& !StringUtils.contains(url, "houseController/getListoryCount.do")
					&& !StringUtils.contains(url, "houseController/addmany.do")
					&& !StringUtils.contains(url, "houseController/update2.do")
					&& !StringUtils.contains(url, "AdvertisementController/updateAdvertisement.do")
					&& !StringUtils.contains(url, "AdvertisementController/find1.do")
					&& !StringUtils.contains(url, "brinController/add.do")
					&& !StringUtils.contains(url, "FarmWarnHandleController/addupdate.do")
					&& !StringUtils.contains(url, "FarmWarnHandleController/find.do")
					&& !StringUtils.contains(url, "farmArea/getarealist.do")
					&& !StringUtils.contains(url, "ModeFarmHouseController/updateAreaNote.do")
					&& !StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getHeartbeat.do")

					&& !StringUtils.contains(url,
							"deviceattributehistoryHouseidYearController/getElectricityRelated.do")
					&& !StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getBatteryVoltage.do")
					&& !StringUtils.contains(url, "deviceattributehistoryHouseidYearController/getHeartbeatCount.do")
					&& !StringUtils.contains(url,
							"deviceattributehistoryHouseidYearController/getElectricityRelatedCount.do")
					&& !StringUtils.contains(url,
							"deviceattributehistoryHouseidYearController/getBatteryVoltageCount.do")
					&& !StringUtils.contains(url, "devicewarnhistoryHouseidYearController/getFarmHistoryWarnData.do")) {
				Houseieee houseieee = null;
				try {
					houseieee = JSON.parseObject(json, Houseieee.class);
				} catch (Exception e) {
					houseieee = new Houseieee();
				}
				if (houseieee == null) {
					houseieee = new Houseieee();
					flag2 = true;
				}
				if (houseieee.getHouseIeee() != null) {
					Houseieee t = (Houseieee) HouseieeeListener.houseieeeMap.get(houseieee.getHouseIeee());
					if (t != null) {
						flag2 = true;
					} else {
						LOGGER.info("houseieee过滤失败");
						flag2 = false;
					}
				} else {
					flag2 = true;
				}
			} else {
				flag2 = true;
			}

			Boolean flag1 = false; // 默认解密失败
			String encodeMethod = request.getParameter("encodemethod");
			String platformType = request.getParameter("platformType");
			// LOGGER.info("--------"+ StringUtils.contains(url,
			// "farmUser/saveUser.do"));
			// AES加密
			if (StringUtils.contains(url, "deviceController/SendStandRequest.do")) {
				if (encodeMethod == null) {
					flag1 = false;
				} else if (!encodeMethod.equals("NONE")) {
					Map<String, Object> jsonMap = JSON.parseObject(json, Map.class);
					String houseIEEE = (String) jsonMap.get("houseIeee");
					String sign = request.getParameter("sign");
					flag1 = validDecodeParams(url, houseIEEE, httpMethod, requestInfo, sign);
				} else {
					flag1 = true;
				}
			} else if (StringUtils.contains(url, "farmDevice/getEndPoint.do")
					|| StringUtils.contains(url, "farmDevice/getChartData.do")
					// || (StringUtils.contains(url,
					// "farmDevice/getPm25ChartData.do") &&
					// "ies".equals(platformType))
					|| StringUtils.contains(url, "farmUser/deleteUser.do")
					|| StringUtils.contains(url, "farmUser/saveUser.do")
					|| StringUtils.contains(url, "farmArea/getarealist.do")
					|| StringUtils.contains(url, "FarmWarnHandleController/find.do")
					|| StringUtils.contains(url, "FarmWarnHandleController/addupdate.do")
					|| StringUtils.contains(url, "ModeFarmHouseController/updateAreaNote.do")
					|| StringUtils.contains(url, "ModeFarmHouseController/get.do")
					|| StringUtils.contains(url, "houseController/getLongLati.do")
					|| StringUtils.contains(url, "farmUser/getAppUserInfo.do")
					|| StringUtils.contains(url, "farmUser/modifyAppPassword.do")
					|| StringUtils.contains(url, "farmArea/addModeAeFarm.do")
					|| StringUtils.contains(url, "deviceoperatehistoryHouseidYearController/getLogcat.do")
					|| StringUtils.contains(url, "devicewarnhistoryHouseidYearController/getFarmHistoryWarnData.do")
					|| StringUtils.contains(url, "deviceoperatehistoryHouseidYearController/getOperator.do")
			// ||StringUtils.contains(url, "farmSmsController/addPhone.do")
			// ||StringUtils.contains(url, "farmSmsController/setsmsFlag.do")
			// ||StringUtils.contains(url, "farmSmsController/deletePhone.do")
			// ||StringUtils.contains(url, "farmSmsController/getSmsList.do")
			// ||StringUtils.contains(url, "farmSmsController/sendFlagSMS")
			) {// 解密农业项目接口
				if ("AES".equals(encodeMethod)) {
					String userName = request.getParameter("user_name");
					String sign = request.getParameter("sign");
					flag1 = validDecodeFarmParams(url, userName, httpMethod, requestInfo, sign, 0);
					// flag1返回null则表示用户名不存在
					if (flag1 == null) {
						PrintWriter out = response.getWriter();
						String resultJson = "{\"status\":16,\"status_msg\":\"user not exist\"}";// 成功1
																								// 失败-89
						out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
						return;
					}
				} else {
					flag1 = false;
				}
			} else if (StringUtils.contains(url, "farmUser/LoginUserInfo.do")) {
				// 农业用户登陆接口使用用户输入的密码作为秘钥解密
				if ("AES".equals(encodeMethod)) {
					String userName = request.getParameter("user_name");
					if (StringUtils.isBlank(userName)) {
						LOGGER.info("user check name error");
						PrintWriter out = response.getWriter();
						String resultJson = "{\"status\":4,\"status_msg\":\"user check name error\"}";// 成功1
																										// 失败-89
						out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
						return;
					}
					String password = request.getParameter("password");
					if (StringUtils.isBlank(password)) {
						LOGGER.info("user check password error");
						PrintWriter out = response.getWriter();
						String resultJson = "{\"status\":5,\"status_msg\":\"user check password error\"}";// 成功1
																											// 失败-89
						out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
						return;
					}
					// 以user_name为秘钥解密用户输入的密码
					String pwd = Httpproxy.urlCodec(password, Httpproxy.getKey(userName));
					LOGGER.info("login pwd = " + pwd);
					String sign = request.getParameter("sign");
					flag1 = validDecodeFarmParams(url, pwd, httpMethod, requestInfo, sign, 1);
				} else {
					flag1 = false;
				}
			} else {
				if ("AES".equals(encodeMethod)) {
					String houseIeeeSecret = request.getParameter("houseIeeeSecret");
					String sign = request.getParameter("sign");
					flag1 = validDecodeParams(url, houseIeeeSecret, httpMethod, requestInfo, sign);
				} else if ("0".equals(request.getParameter("encodemethod"))) { // 不加密
					flag1 = true;
				} else if ("NONE".equals(request.getParameter("encodemethod"))) {
					flag1 = true;
				} else {
					if (StringUtils.contains(url, ".do")) { // 安全认证
						Houseieee houseieee = new Houseieee();
						try {
							houseieee = JSON.parseObject(json, Houseieee.class);
						} catch (Exception e) {
							houseieee = new Houseieee();
						}
						if (houseieee == null) {
							houseieee = new Houseieee();
							flag1 = true;
						}
						if (houseieee.getHouseIeee() != null) {
							Houseieee t = (Houseieee) HouseieeeListener.houseieeeMap.get(houseieee.getHouseIeee());
							if (t != null) {
								if ("1".equals(t.getEncodemethod())) { // 需要加密
									LOGGER.info("url接口需要加密getEncodemethod()==" + t.getEncodemethod());
									flag1 = true;
								} else {
									flag1 = true;
								}
							} else {
								flag1 = true;
							}
						} else {
							flag1 = true;
						}
					} else {
						flag1 = true;
					}
				}
			}

			if (flag1 && flag2) {
				LOGGER.info("url filter==success");
				chain.doFilter(request, response);
			} else {
				LOGGER.info("url filter==failure");
				PrintWriter out = response.getWriter();
				String resultJson = "{\"result\":-89}";// 成功1 失败-89
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			}
		} catch (Throwable e) {
			LOGGER.error("filter throwable", e);
		}
	}

	/**
	 * 农业接口解密验证
	 * 
	 * @param url
	 * @param userName
	 * @param httpMethod
	 * @param requestInfo
	 * @param sign
	 * @param funcType
	 *            1：登陆 2：其他接口
	 * @return
	 */
	private Boolean validDecodeFarmParams(String url, String userName, String httpMethod, String requestInfo,
			String sign, int funcType) throws Exception {
		Boolean flag1 = false;
		String pwd = "";
		// funcType==1时为登陆接口，使用用户输入的密码为秘钥解密
		if (funcType == 1) {
			pwd = userName;
		} else {
			// 根据用户名获取用户（用户名唯一）
			FarmUser user = HouseieeeListener.farmUserMap.get(userName);
			// 用户名不存在解密失败
			if (user == null) {
				LOGGER.info("用户名不存在TTT-->:" + userName);
				return null;
			}
			// 密码为空解密失败
			pwd = user.getPassword();
			if (StringUtils.isBlank(pwd))
				return flag1;
		}
		// 获取秘钥
		String password = Httpproxy.getKey(pwd);
		LOGGER.info("httpMethod=" + httpMethod + ",requestInfo=" + requestInfo + ",password=" + password);
		String strDecrypt = "", strArray[] = null;
		StringBuilder sbEncrypt = new StringBuilder();
		if (httpMethod.equals("GET")) {
			String str = requestInfo;
			strArray = StringUtils.split(str, ";");
			for (int i = 0; i < strArray.length; i++) {
				if (!StringUtils.contains(strArray[i], "encodemethod=AES")) {
					if (StringUtils.contains(strArray[i], "sign="))
						strDecrypt = Httpproxy.urlCodec(strArray[i].substring(5), password);
					else
						sbEncrypt.append(strArray[i]);
				}
			}

			// wifi数据推送,%20指空格
			if (strDecrypt != null) {
				strDecrypt = URLDecoder.decode(strDecrypt, "utf-8");
				String sbEncryptStr = URLDecoder.decode(sbEncrypt.toString(), "utf-8");
				// strDecrypt = strDecrypt.replaceAll("\\s*",
				// "").replaceAll("%20", "").replace("%7B", "{").replace("%7D",
				// "}").replace("%22", "\"").replace("%3A", ":").replace("%2C",
				// ",").replace("%5B", "[").replace("%5D", "]").trim();
				// String sbEncryptStr = sbEncrypt.toString().replaceAll("\\s*",
				// "").replaceAll("%20", "").replace("%7B", "{").replace("%7D",
				// "}").replace("%22", "\"").replace("%3A", ":").replace("%2C",
				// ",").replace("%5B", "[").replace("%5D", "]").trim();
				LOGGER.info("encode  GET-----------\nuser_name:" + userName + "\npassword:" + password + "\n解密串:"
						+ strDecrypt + "\n参数串:" + sbEncryptStr);
				if (strDecrypt.equalsIgnoreCase(sbEncryptStr)) {
					flag1 = true;
				} else {
					LOGGER.info("url get解密失败");
				}
			}
		} else if (httpMethod.equals("POST")) {
			if (sign != null) {
				strDecrypt = Httpproxy.urlCodec16(sign, password);// .urlCodec(sign,
																	// password);
			}
			strDecrypt = URLDecoder.decode(strDecrypt, "utf-8");
			String str = URLDecoder.decode(requestInfo, "utf-8");
			// 去除空白字符 .replaceAll("\\s*", "")
			// strDecrypt = strDecrypt.replaceAll("\\s*", "").replaceAll("%20",
			// "").replace("%7B", "{").replace("%7D", "}").replace("%22",
			// "\"").replace("%3A", ":").replace("%2C", ",").replace("%5B",
			// "[").replace("%5D", "]").trim();
			// String str= requestInfo.replaceAll("\\s*", "").replaceAll("%20",
			// "").replace("%7B", "{").replace("%7D", "}").replace("%22",
			// "\"").replace("%3A", ":").replace("%2C", ",").replace("%5B",
			// "[").replace("%5D", "]").trim();
			LOGGER.info("encode  POST-----------\nuser_name:" + userName + "\npassword:" + password + "\n解密串:"
					+ strDecrypt + "\n参数(含加密)串:" + str);
			strArray = StringUtils.split(str, ";");
			for (int i = 0; i < strArray.length; i++) {
				if (strArray[i].indexOf("encodemethod=") != 0 && strArray[i].indexOf("sign=") != 0) {
					if (strDecrypt.contains(strArray[i])) {
						flag1 = true;
					} else {
						flag1 = false;
						LOGGER.info("url post解密失败");
						break;
					}
				}
			}
		}

		return flag1;
	}

	/**
	 * 通用解密验证
	 * 
	 * @param url
	 * @param houseIeeeSecret
	 * @param httpMethod
	 * @param requestInfo
	 * @param sign
	 * @return
	 */
	private boolean validDecodeParams(String url, String houseIeeeSecret, String httpMethod, String requestInfo,
			String sign) throws Exception {
		boolean flag1 = false;
		String password = "";
		// 对该接口需要做特殊处理
		if (StringUtils.isNotBlank(houseIeeeSecret)) {
			if (StringUtils.contains(url, "houseController/updateHouseInfo.do")) {
				password = houseIeeeSecret;
			} else {
				Houseieee houseieee = new Houseieee();
				houseieee.setHouseIeee(houseIeeeSecret);
				Houseieee t = (Houseieee) HouseieeeListener.houseieeeMap.get(houseieee.getHouseIeee());
				if (t != null) {
					password = t.getSecretKey() + t.getVendorCode();
				} else {
					password = houseIeeeSecret.substring(6) + "NETVOX";
				}
			}
		} else {
			password = "000000DBB5" + "NETVOX";
		}

		String strDecrypt = "", strArray[] = null;
		StringBuilder sbEncrypt = new StringBuilder();
		if (httpMethod.equals("GET")) {
			String str = requestInfo;
			strArray = StringUtils.split(str, ";");
			for (int i = 0; i < strArray.length; i++) {
				if (!StringUtils.contains(strArray[i], "encodemethod=AES")) {
					if (StringUtils.contains(strArray[i], "sign=")) {
						// IR接口中文问题用GBK解密
						if (StringUtils.contains(url, "/IR/")) {
							// if(MacAddressUtil.getOSName().equals("windows")){
							strDecrypt = Httpproxy.urlCodecGBK(strArray[i].substring(5), password);
						} else {
							strDecrypt = Httpproxy.urlCodec(strArray[i].substring(5), password);
						}
					} else
						sbEncrypt.append(strArray[i]);
				}
			}

			// wifi数据推送,%20指空格
			if (strDecrypt != null) {
				strDecrypt = strDecrypt.replaceAll("\\s*", "").replaceAll("%20", "").replace("%7B", "{")
						.replace("%7D", "}").replace("%22", "\"").replace("%3A", ":").replace("%2C", ",")
						.replace("%25", "%").replace("%2B", "+").replace("%2F", "/").replace("%3F", "?")
						.replace("%23", "#").replace("%26", "&").replace("%3D", "=").replace("%5B", "[")
						.replace("%5D", "]").trim();
				String sbEncryptStr = sbEncrypt.toString().replaceAll("\\s*", "").replaceAll("%20", "")
						.replace("%7B", "{").replace("%7D", "}").replace("%22", "\"").replace("%3A", ":")
						.replace("%2C", ",").replace("%25", "%").replace("%2B", "+").replace("%2F", "/")
						.replace("%3F", "?").replace("%23", "#").replace("%26", "&").replace("%3D", "=")
						.replace("%5B", "[").replace("%5D", "]").trim();
				LOGGER.info("encode  GET-----------\nhouseIeee:" + houseIeeeSecret + "\npassword:" + password + "\n解密串:"
						+ strDecrypt + "\n参数串:" + sbEncryptStr);
				strDecrypt = URLDecoder.decode(strDecrypt, "urf-8");
				sbEncryptStr = URLDecoder.decode(sbEncryptStr, "utf-8");
				if (strDecrypt.equalsIgnoreCase(sbEncryptStr)) {
					flag1 = true;
				} else {
					LOGGER.info("url get解密失败");
				}
			}
		} else if (httpMethod.equals("POST")) {
			if (sign != null) {
				// IR接口中文问题用GBK解密
				if (StringUtils.contains(url, "/IR/")) {
					// if(MacAddressUtil.getOSName().equals("windows")){
					strDecrypt = Httpproxy.urlCodecGBK(sign, password);
				} else {
					strDecrypt = Httpproxy.urlCodec(sign, password);
				}
			}
			// 去除空白字符 .replaceAll("\\s*", "")
			strDecrypt = strDecrypt.replaceAll("\\s*", "").replaceAll("%20", "").replace("%7B", "{").replace("%7D", "}")
					.replace("%22", "\"").replace("%3A", ":").replace("%2C", ",").replace("%25", "%")
					.replace("%2B", "+").replace("%2F", "/").replace("%3F", "?").replace("%23", "#").replace("%26", "&")
					.replace("%3D", "=").trim();
			String str = requestInfo.replaceAll("\\s*", "").replaceAll("%20", "").replace("%7B", "{")
					.replace("%7D", "}").replace("%22", "\"").replace("%3A", ":").replace("%2C", ",")
					.replace("%25", "%").replace("%2B", "+").replace("%2F", "/").replace("%3F", "?").replace("%23", "#")
					.replace("%26", "&").replace("%3D", "=").trim();
			LOGGER.info("encode  POST-----------\nhouseIeee:" + houseIeeeSecret + "\npassword:" + password + "\n解密串:"
					+ strDecrypt + "\n参数(含加密)串:" + str);
			strArray = StringUtils.split(str, ";");
			for (int i = 0; i < strArray.length; i++) {
				if (strArray[i].indexOf("encodemethod=") != 0 && strArray[i].indexOf("sign=") != 0) {
					if (strDecrypt.contains(strArray[i])) {
						flag1 = true;
					} else {
						flag1 = false;
						LOGGER.info("url post解密失败");
						break;
					}
				}
			}
		}

		return flag1;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 启动异步调用
		// try {
		// httpclient = new DefaultHttpAsyncClient();
		// httpclient.start();
		// } catch(Exception e) {
		// LOGGER.info("filter httpclient:", e);
		// }
		// 判断服务器是否注册
		// ServerRegisterUtil.isServerValid();

		this.filterConfig = filterConfig;
		encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null) {
			ignore = true;
		} else {
			if (value.equalsIgnoreCase("true"))
				ignore = true;
			else {
				if (value.equalsIgnoreCase("yes"))
					ignore = true;
				else
					ignore = false;
			}
		}
	}

	protected String selectEncoding(ServletRequest request) {
		return encoding;
	}

}
