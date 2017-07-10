package sy.controller;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import sy.model.Houseieee;
import sy.model.ModeSchcUser;
import sy.model.Modenode;
import sy.model.Userordermain;
import sy.service.HouseServiceI;
import sy.service.UserordermainServiceI;
import sy.util.AESCodec;
import sy.util.HouseieeeListener;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

import com.alibaba.fastjson.JSON;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/userordermainController")
public class UserordermainController {
	private HouseServiceI houseService;
	public HouseServiceI getHouseService() {
		return houseService;
	}
	@Autowired
	public void setHouseService(HouseServiceI houseService) {
		this.houseService = houseService;
	}

	private UserordermainServiceI userordermainService;
	private Configuration configuration = null;

	private static final Logger LOGGER = Logger.getLogger(UserordermainController.class);

	public UserordermainServiceI getUserordermainService() {
		return userordermainService;
	}

	@Autowired
	public void setUserordermainService(
			UserordermainServiceI userordermainService) {
		this.userordermainService = userordermainService;
	}

	@RequestMapping("/addOrUpdate")
	public void addOrUpdate(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * userordermainService.test(); return "showUserordermain";
		 */

		// String json = request.getParameter("json");

		/*
		 * resp.setContentType("text/html;charset=gb2312"); PrintWriter
		 * out=resp.getWriter();
		 * 
		 * out.println(json); out.flush();
		 */

		// PropertyConfigurator.configure("D:/log4j.properties");
		/*
		 * Map<String,Object> map1 = (Map<String,Object>)JSON.parse(json); for
		 * (String key : map1.keySet()) { logger.info(key+":"+map1.get(key)); }
		 */

		PrintWriter out = response.getWriter();
		// System.out.println("json==" + json);

		/*
		 * List<Userordermain> userordermainList =
		 * JSON.parseArray(json,Userordermain.class); for (Userordermain
		 * userordermain : userordermainList) {
		 * userordermainService.save(userordermain); }
		 */

		try {
			/*
			 * UserordermainAndRoom userordermainAndRoom=JSON.parseObject(json,
			 * UserordermainAndRoom.class); if
			 * (userordermainAndRoom.getUserordermain() == null ||
			 * userordermainAndRoom.getModeroom() == null) { String resultJson =
			 * "{\"result\":0,\"houseId\":0}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})"); return; }
			 * userordermainService.saveOrUpdateUserordermainAndRoom
			 * (userordermainAndRoom); String resultJson =
			 * "{\"result\":0}";//成功1 失败0 resultJson =
			 * "{\"result\":1,\"houseId\":" +
			 * userordermainAndRoom.getUserordermain().getId() + "}";//成功1 失败0;
			 * String callback =
			 * request.getParameter("callback");out.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})");
			 */
		} catch (Exception e) {
			LOGGER.info("addOrUpdate", e);
			String resultJson = "{\"result\":0,\"houseId\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}

		// String resultJson = "{\"result\":\"1\"}";

		// out.flush();

		// System.out.println("resultJson==" + resultJson);

		// return "saveUserordermain";
	}

	@RequestMapping("/update")
	public void update(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		try {
			Userordermain userordermain = JSON.parseObject(json,
					Userordermain.class);
			Userordermain t = userordermainService.get(userordermain);
			String resultJson = "{\"result\":0}";// 成功1 失败0
			if (t != null) {
				/*
				 * userordermain.setId(t.getId());
				 * userordermainService.update(userordermain);
				 */
				/*
				 * if (userordermain.getName() != null) {
				 * t.setName(userordermain.getName()); } if
				 * (userordermain.getSecretKey() != null) {
				 * t.setSecretKey(userordermain.getSecretKey()); } if
				 * (userordermain.getVendorCode() != null) {
				 * t.setVendorCode(userordermain.getVendorCode()); } if
				 * (userordermain.getVendorName() != null) {
				 * t.setVendorName(userordermain.getVendorName()); } if
				 * (userordermain.getEncodemethod() != null) {
				 * t.setEncodemethod(userordermain.getEncodemethod()); } if
				 * (userordermain.getXmppIp() != null) {
				 * t.setXmppIp(userordermain.getXmppIp()); } if
				 * (userordermain.getXmppPort() != null) {
				 * t.setXmppPort(userordermain.getXmppPort()); } if
				 * (userordermain.getCloudIp1() != null) {
				 * t.setCloudIp1(userordermain.getCloudIp1()); } if
				 * (userordermain.getCloudPort1() != null) {
				 * t.setCloudPort1(userordermain.getCloudPort1()); } if
				 * (userordermain.getCloudIp2() != null) {
				 * t.setCloudIp2(userordermain.getCloudIp2()); } if
				 * (userordermain.getCloudPort2() != null) {
				 * t.setCloudPort2(userordermain.getCloudPort2()); } if
				 * (userordermain.getEnableFlag() != null) {
				 * t.setEnableFlag(userordermain.getEnableFlag()); } if
				 * (userordermain.getDescription() != null) {
				 * t.setDescription(userordermain.getDescription()); }
				 */
				t.setLasttime(new Date());
				userordermainService.update(t);
				resultJson = "{\"result\":1}";// 成功1 失败0
			}
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("update", e);
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}
	}

	/**
	 * 根据某一个HouseID的设备列表模板和用户输入的信息生成设备采购订单， 并且要根据XML的格式生成一个文件保存到主表的XMlFile字段
	 * XML文件的格式参考以前设计的Mode执行规则（203上面的Mode.XML文件）
	 * 
	 * @author: zhuangxd 时间：2013-12-4 下午2:54:46
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GenOrder")
	public void GenOrder(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			Modenode modenode = JSON.parseObject(json,
					Modenode.class);
			List<Modenode> m = houseService.isExist203206(modenode);
			if(m.size()>1){
				String resultJson = "{\"result\":-2}";
				String callback = request.getParameter("callback");
				out.println(callback
						+ "({\"request_id\": 1234, \"response_params\":"+resultJson+"})");//存在多个203或者206，或者同时存在203、206
				return;
			}
			else if(m.size()==0){
				String resultJson = "{\"result\":-3}";
				String callback = request.getParameter("callback");
				out.println(callback
						+ "({\"request_id\": 1234, \"response_params\":"+resultJson+"})");//家不存在203或者206
				return;
			}
			Userordermain userordermain = JSON.parseObject(json,
					Userordermain.class);
			Userordermain t = userordermainService.findOrder(userordermain);
			String resultJson = "{\"result\":0}";// 成功1 失败0
			if (t != null) {
				// 已经存在订单
				resultJson = "{\"result\":-1}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback
						+ "({\"request_id\": 1234, \"response_params\":"
						+ resultJson + "})");
				return;
			}
			userordermainService.createOrder(userordermain);
			resultJson = "{\"result\":1}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
			// String callback =
			// request.getParameter("callback");out.println(callback +
			// "({\"request_id\": 1234, \"response_params\":" + resultJson +
			// "})");
		} catch (Exception e) {			
		
			/*
			 * String resultJson = "{\"result\":0}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})");
			 */
			LOGGER.info("GenOrder", e);	
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}

		// 多表关联
		/*
		 * try { Userordermain userordermain =
		 * JSON.parseObject(json,Userordermain.class); // Userordermain t =
		 * userordermainService.find(userordermain); List<Userordermain> t =
		 * userordermainService.findList(userordermain); List<Object[]> s =
		 * userordermainService.findListSql(userordermain); for (Object[]
		 * objects : s) { Userordermain userordermain2 =
		 * (Userordermain)objects[0]; Deviceattribute deviceattribute =
		 * (Deviceattribute)objects[1]; } for (Object[] objects : s) {
		 * Userordermain userordermain2 = (Userordermain)objects[0];
		 * DevicewarnhistoryUserordermainidYear
		 * devicewarnhistoryUserordermainidYear =
		 * (DevicewarnhistoryUserordermainidYear)objects[1]; } // String
		 * resultJson= JSON.toJSONStringWithDateFormat(t,
		 * "yyyy-MM-dd HH:mm:ss"); String resultJson=
		 * JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss"); String
		 * callback = request.getParameter("callback");out.println(callback +
		 * "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		 * } catch (Exception e) { String resultJson = "{\"result\":0}";//成功1
		 * 失败0 String callback =
		 * request.getParameter("callback");out.println(callback +
		 * "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		 * }
		 */
	}

	/**
	 * 订单下载
	 * 
	 * @author: zhuangxd 时间：2014-1-11 下午2:35:15
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("orderDownload")
	public void orderDownload(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out2 = response.getWriter();
		// response.setContentType("text/html;charset=utf-8");
		try {
			Userordermain userordermain = new Userordermain();
			// 用户验证 迅雷浏览器下载不到文件。迅雷监视ie浏览器会保存 中文名字会出错 又没报错
			userordermain.setHouseId(Long.parseLong(request
					.getParameter("houseid")));
			String type = request.getParameter("type");
			/*
			 * Modeuser t2 =
			 * (Modeuser)request.getSession().getAttribute("modeuser"); if (t2
			 * == null || t2.getId() == 0) { // 用户没登录 String resultJson =
			 * "{\"result\":-1}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out2.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})"); return; } if
			 * (Long.parseLong(request.getParameter("userid")) != t2.getId()) {
			 * // 用户没登录 String resultJson = "{\"result\":-1}";//成功1 失败0 String
			 * callback = request.getParameter("callback");out2.println(callback
			 * + "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})"); return; }
			 */
			Userordermain t = userordermainService.findOrder(userordermain);
			String filename = t.getXmlFile();
			String[] file = filename.split(";");
			if (type == null || type.equals("1")) {
				filename = file[0];
			} else if (type.equals("2")) {
				filename = file[1];
			} else if (type.equals("4")) {
				if (file.length > 2) {
					filename = file[3];//下载ndd文件
				} else {
					filename = file[0];
				}
			}
			File fl = new File(filename);// 被下载的文件
			if (!fl.exists()) {
				// fl.mkdir();
				String resultJson = "{\"result\":0}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out2.println(callback
						+ "({\"request_id\": 1234, \"response_params\":"
						+ resultJson + "})");
				return;
			}
			BufferedInputStream buffer = new BufferedInputStream(
					new FileInputStream(fl));
			byte[] bt = new byte[1024];
			int len = 0;
			response.reset();// 很重要
			// 纯下载方式
			/*if (filename.contains("modexml\\")) {
				filename = filename.substring(PropertiesUtil.getProperty("modexmlPath").length());
			} else {
				filename = filename.substring(3);
			}*/
			filename = filename.substring(PropertiesUtil.getProperty("modexmlPath").length());
			filename = java.net.URLEncoder.encode(filename, "utf-8"); // ie不会乱码，火狐乱码
			// filename = new String(filename.getBytes(),"ISO-8859-1"); //
			// ie不会乱码，火狐也不会乱码 (中文不会乱码，英文或空格会乱码)

			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ filename);
			OutputStream out = response.getOutputStream();
			while ((len = buffer.read(bt)) > 0) {
				out.write(bt, 0, len);
			}
			buffer.close();
			out.close();
		} catch (Exception e) {
			LOGGER.info("orderDownload", e);	
		}
	}

	/**
	 * 导出安装说明文档
	 * 
	 * @author: zhuangxd 时间：2014-2-26 下午3:04:39
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("exportInstallationDocumentation")
	public void exportInstallationDocumentation(String json,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out2 = response.getWriter();
		// response.setContentType("text/html;charset=utf-8");
		try {
			configuration = new Configuration();
			configuration.setDefaultEncoding("utf-8");
			Map dataMap = new HashMap();

			Userordermain userordermain = new Userordermain();
			// 用户验证 迅雷浏览器下载不到文件。迅雷监视ie浏览器会保存 中文名字会出错 又没报错
			userordermain.setHouseId(Long.parseLong(request
					.getParameter("houseid")));
			Modenode modenode = new Modenode();
			String houseid = request.getParameter("houseid");
			Long a = Long.parseLong(houseid);
			modenode.setHouseId(a);
			/*
			 * Modeuser t2 =
			 * (Modeuser)request.getSession().getAttribute("modeuser"); if (t2
			 * == null || t2.getId() == 0) { // 用户没登录 String resultJson =
			 * "{\"result\":-1}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out2.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})"); return; } if
			 * (Long.parseLong(request.getParameter("userid")) != t2.getId()) {
			 * // 用户没登录 String resultJson = "{\"result\":-1}";//成功1 失败0 String
			 * callback = request.getParameter("callback");out2.println(callback
			 * + "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})"); return; }
			 */
			Userordermain t2 = userordermainService.findOrder(userordermain);
			// 获取设备信息表
			Map modenodeMap = userordermainService
					.findNodeDeviceList(userordermain);
			dataMap.put("nodeData", modenodeMap.get("modenode"));

			// 模板放在sy.util.ftl包下面，通过classpath装载
			configuration.setClassForTemplateLoading(this.getClass(),
					"/sy/util/ftl");

			Template t = null;
			try {
				List<Modenode> m = houseService.isExist203206(modenode);
				Modenode modenode2 = m.get(0);
				if(m.get(0).getModeNodeLibId()==145){
					LOGGER.info("Z206安装文档");
					t = configuration.getTemplate("exportModelHelpWord.ftl");// 设置要装载的Z206模板
				}
				else if(m.get(0).getModeNodeLibId()==86)
				{
					LOGGER.info("Z203安装文档");
				t = configuration.getTemplate("exportModelHelpWord.ftl");// 设置要装载的Z203模板
				}
			} catch (IOException e) {
				LOGGER.info("exportInstallationDocumentation", e);	
			}
			// 先生成文件
			File outFile = new File(PropertiesUtil.getProperty("modexmlPath") + "安装说明文档" + "-" + "houseid为"
					+ t2.getHouseId() + ".doc");
			// File outFile = new File("D:/modexml/exportModelHelpWord"+"-"
			// +(new
			// java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new
			// Date())+".doc");
			// File outFile = new File("D:/modexml/exportModelHelpWord.doc");

			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("exportInstallationDocumentation", e);	
			} catch (FileNotFoundException e) {
				LOGGER.info("exportInstallationDocumentation", e);	
			}

			try {
				t.process(dataMap, out);
				out.close();
			} catch (TemplateException e) {
				LOGGER.info("exportInstallationDocumentation", e);	
			} catch (IOException e) {
				LOGGER.info("exportInstallationDocumentation", e);	
			}

			// 文件下载
			String filename = PropertiesUtil.getProperty("modexmlPath") + "安装说明文档" + "-" + "houseid为"
					+ t2.getHouseId() + ".doc";
			File fl = new File(filename);// 被下载的文件
			BufferedInputStream buffer = new BufferedInputStream(
					new FileInputStream(fl));
			byte[] bt = new byte[1024];
			int len = 0;
			response.reset();// 很重要
			// 纯下载方式
			/*if (filename.contains("modexml/")) {
//				filename = filename.substring(11);
				filename = filename.substring(PropertiesUtil.getProperty("modexmlPath").length());
			} else {
				filename = filename.substring(3);
			}*/
			filename = filename.substring(PropertiesUtil.getProperty("modexmlPath").length());
			// filename = java.net.URLEncoder.encode(filename, "utf-8");
			filename = new String(filename.getBytes(), "ISO-8859-1");

			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ filename);
			OutputStream out3 = response.getOutputStream();
			while ((len = buffer.read(bt)) > 0) {
				out3.write(bt, 0, len);
			}
			buffer.close();
			out3.close();
		} catch (Exception e) {
			LOGGER.info("exportInstallationDocumentation", e);	
		}
	}

	/**
	 * 查找某一个HouseID的订单信息
	 * 
	 * @author: zhuangxd 时间：2013-12-6 下午4:40:23
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findOrder")
	public void findOrder(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");

		// new XmlUtil().readFILE();

		try {
			Userordermain userordermain = JSON.parseObject(json,
					Userordermain.class);
			Userordermain t = userordermainService.findOrder(userordermain);
			String resultJson = JSON.toJSONStringWithDateFormat(t,
					"yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
			// String callback =
			// request.getParameter("callback");out.println(callback +
			// "({\"request_id\": 1234, \"response_params\":" + resultJson +
			// "})");
		} catch (Exception e) {
			/*
			 * String resultJson = "{\"result\":0}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})");
			 */
			LOGGER.info("findOrder", e);	
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}

		// 多表关联
		/*
		 * try { Userordermain userordermain =
		 * JSON.parseObject(json,Userordermain.class); // Userordermain t =
		 * userordermainService.find(userordermain); List<Userordermain> t =
		 * userordermainService.findList(userordermain); List<Object[]> s =
		 * userordermainService.findListSql(userordermain); for (Object[]
		 * objects : s) { Userordermain userordermain2 =
		 * (Userordermain)objects[0]; Deviceattribute deviceattribute =
		 * (Deviceattribute)objects[1]; } for (Object[] objects : s) {
		 * Userordermain userordermain2 = (Userordermain)objects[0];
		 * DevicewarnhistoryUserordermainidYear
		 * devicewarnhistoryUserordermainidYear =
		 * (DevicewarnhistoryUserordermainidYear)objects[1]; } // String
		 * resultJson= JSON.toJSONStringWithDateFormat(t,
		 * "yyyy-MM-dd HH:mm:ss"); String resultJson=
		 * JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss"); String
		 * callback = request.getParameter("callback");out.println(callback +
		 * "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		 * } catch (Exception e) { String resultJson = "{\"result\":0}";//成功1
		 * 失败0 String callback =
		 * request.getParameter("callback");out.println(callback +
		 * "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		 * }
		 */
	}

	/**
	 * 奈伯思标准情景模式列表userId=-1
	 * 
	 * @author: zhuangxd 时间：2013-12-10 下午2:08:46
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/standardModeList")
	public void standardModeList(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");

		try {
			Userordermain userordermain = JSON.parseObject(json,
					Userordermain.class);
			List<Userordermain> t = userordermainService
					.findStandardModeList(userordermain);
			String resultJson = JSON.toJSONStringWithDateFormat(t,
					"yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("standardModeList", e);	
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}
	}

	/**
	 * 奈伯思标准情景模式导入
	 * 
	 * @author: zhuangxd 时间：2013-12-11 上午11:34:22
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/ImportFromLib")
	public void ImportFromLib(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");

		try {
			Userordermain userordermain = JSON.parseObject(json,
					Userordermain.class);
			/*
			 * Userordermain t = userordermainService.findOrder(userordermain);
			 * String resultJson = "{\"result\":0}";//成功1 失败0 if (t !=null) { //
			 * 已经存在订单 resultJson = "{\"result\":-1}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})"); return; }
			 */
			String resultJson = "{\"result\":0}";// 成功1 失败0
			long i = userordermainService.ImportFromLib(userordermain);
			if (i == -1) { // 家同名，导入失败
				resultJson = "{\"result\":-1}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback
						+ "({\"request_id\": 1234, \"response_params\":"
						+ resultJson + "})");
				return;
			}
			if (i == 0) { // 导入失败
				resultJson = "{\"result\":0}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback
						+ "({\"request_id\": 1234, \"response_params\":"
						+ resultJson + "})");
				return;
			}
			userordermainService.ImportFromLib2(i); // houseid
			resultJson = "{\"result\":1,\"houseId\":" + i + "}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
			// String callback =
			// request.getParameter("callback");out.println(callback +
			// "({\"request_id\": 1234, \"response_params\":" + resultJson +
			// "})");
		} catch (Exception e) {
			LOGGER.info("ImportFromLib", e);	
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}
	}
	
	/**
	 * 上传模式文件，并导入模式编辑器
	 * @author: zhuangxd
	 * 时间：2014-11-14 下午4:18:14
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/ImportModeFile")
	public void ImportModeFile(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		String pathSrc = "";
		String uploadDir = "";
		Userordermain userordermain = JSON.parseObject(json,
				Userordermain.class);

		try {
			 try {
				 	String spetor = File.separator;
					// 设置保存上传文件的目录
					uploadDir = request.getSession().getServletContext().getRealPath(spetor + "model_editor/doc");
					//判断是multipart/form-data类型的数据
					boolean isMultipart = ServletFileUpload.isMultipartContent(request);
					if (isMultipart) {
						DiskFileItemFactory factory = new DiskFileItemFactory();
						// 超过1M的字段数据采用临时文件缓存
//						factory.setSizeThreshold(1024 * 1024);
						ServletFileUpload upload = new ServletFileUpload(factory);
						// 最多上传30M数据
//						upload.setSizeMax(1024 * 1024 * 30);
						upload.setSizeMax(1024 * 1024 * 30); 
						// 每个文件最大1M
//						upload.setFileSizeMax(1024 * 1024 * 1);
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
//							out.print("单个文件的大小不能超出1M"); 
							String resultJson = "{\"result\":-3}";//成功1 失败0 
//							String callback = request.getParameter("callback");
							out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
							LOGGER.info("ImportModeFile", e);
							return;
						}catch(SizeLimitExceededException e){
//							out.print("所有文件的总大小不能超出30M"); 
							String resultJson = "{\"result\":-4}";//成功1 失败0 
//							String callback = request.getParameter("callback");
							out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
							LOGGER.info("ImportModeFile", e);
							return;
						}catch (FileUploadException e) {
//							out.print("解析数据时出现如下问题");
							String resultJson = "{\"result\":0}";//成功1 失败0
//							String callback = request.getParameter("callback");
							out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
							LOGGER.info("ImportModeFile", e);
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
									pathSrc = fi.getName();
									//如果用户没有在FORM表单的文件字段中选择任何文件， 那么忽略对该字段项的处理
									if (pathSrc == null || pathSrc.trim().equals("")){
										continue;
									}
									int start = pathSrc.lastIndexOf('\\');
									String fileName = pathSrc.substring(start + 1);
									//处理文件名乱码的问题
									fileName = new String(fileName.getBytes(), "utf-8");
									if(!fileName.contains(".ndd")){ // 只允许上传ndd文件格式
//										out.print("" + fileName + "为不被允许的上传类型");
										String resultJson = "{\"result\":-2}";//成功1 失败0
//										String callback = request.getParameter("callback");
										out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
										return;
									}
									File pathDest = new File(uploadDir, pathSrc.substring(start + 1));
									//上传文件
										fi.write(pathDest);
										
											
								} catch (Exception e) {
//									e.printStackTrace();
									//out.print("存储文件时出现如下问题：");
									//e.printStackTrace(out);
									String resultJson = "{\"result\":0}";//成功1 失败0
//									String callback = request.getParameter("callback");
									out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
									LOGGER.info("ImportModeFile", e);
									return;
								} finally{
									// 总是立即删除保存表单字段内容的临时文件
									fi.delete();
								}
							}
						}
					}
		        	/*String resultJson = "{\"result\":1}";//成功1 失败0
	    			out.println("{\"request_id\": 1234, \"response_params\":{\"result\":1,\"houseList\":" + resultJson + "}}"); */
				} catch (Exception e) {
					LOGGER.info("ImportModeFile", e);
//					e.printStackTrace();
					String resultJson = "{\"result\":0}";//成功1 失败0
					out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
				}        
			 
			// 设置上传文件的路径和文件名
			userordermain.setXmlFile(uploadDir + File.separator + pathSrc);
			/*
			 * Userordermain t = userordermainService.findOrder(userordermain);
			 * String resultJson = "{\"result\":0}";//成功1 失败0 if (t !=null) { //
			 * 已经存在订单 resultJson = "{\"result\":-1}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})"); return; }
			 */
			String resultJson = "{\"result\":0}";// 成功1 失败0
			long i = userordermainService.ImportZ203ModelAndNode(userordermain);
			if (i == -1) { // 家同名，导入失败
				resultJson = "{\"result\":-1}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out.println("{\"request_id\": 1234, \"response_params\":"+ resultJson + "}");
				return;
			}
			if (i == 0) { // 导入失败
				resultJson = "{\"result\":0}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out.println("{\"request_id\": 1234, \"response_params\":"+ resultJson + "}");
				return;
			}
			// 更新表间关联
			userordermainService.ImportFromLib2(i); // houseid
			resultJson = "{\"result\":1}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":"+ resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("ImportModeFile", e);	
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":"+ resultJson + "}");
		}
	}

	/**
	 * 导入Z203的Model文件和Node文件(同步云端的情景模式)
	 * 
	 * @author: zhuangxd 时间：2014-1-23 下午4:24:14
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/ImportZ203ModelAndNode")
	public void ImportZ203ModelAndNode(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Userordermain userordermain=JSON.parseObject(json, Userordermain.class);
        	String resultJson = "{\"result\":-6}";//成功0
        	//获取代理服务器IP
//        	String serverIp = abtainXmppIp(userordermain.getHouseIeee());
//        	String serverIp = PropertiesUtil.getProperty("xmpp.host");
        	String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(userordermain.getHouseIeee());
        	String vendorCode = ((Houseieee) HouseieeeListener.houseieeeMap.get(userordermain.getHouseIeee())).getVendorCode();
			//登录到203服务器
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			//ftp IP/Port
        	String modelftpIp = PropertiesUtil.getProperty("modelftpIp");
        	String modelftpPort = PropertiesUtil.getProperty("modelftpPort");
        	StringBuilder loginUrl = new StringBuilder("http://");
        	loginUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=xmllogin/")
        	.append(userordermain.getUserName()).append("/").append(userordermain.getPassword()).append("/").append(vendorCode)
        	.append("&username=").append(userordermain.getHouseIeee());
        	
        	//加密
        	String content="context=xmllogin/"+userordermain.getUserName()+"/"+userordermain.getPassword()+"&username="+userordermain.getHouseIeee();
        	while(content.getBytes().length%16!=0)
				content=content+"#";
        	String key = userordermain.getHouseIeee().substring(6, 16)+"NETVOX";
        	byte[] encryptResult = encrypt2(content, key);
        	String encryptResultStr = parseByte2HexStr(encryptResult);
        	loginUrl.append("&sign=").append(encryptResultStr).append("&encodemethod=AES&houseIeeeSecret=").append(userordermain.getHouseIeee());
    		
        	//加密结束
  //      	System.out.println(loginUrl);
        	String str = TestHttpclient.getUrl(loginUrl.toString(),"utf-8");

        	Map<String, Object> result = JSON.parseObject(str, Map.class);
        	int status = (int) ((Map) result.get("message")).get("status");
        	if(status != 0) {
        		resultJson = "{\"result\":" + status + "}";//成功0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	//获取xmlfile文件路径
        	StringBuilder upUrl = new StringBuilder("http://");
        	//upUrl.append(xmppHost).append(":").append(xmppPort).append("/z203chat/poll?context=xmlup&")
        	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=xmlup/")
        	.append(modelftpIp).append("/").append(modelftpPort).append("&")
        	.append("username=").append(userordermain.getHouseIeee());
        	
        	//加密
        	String c="context=xmlup&username="+userordermain.getHouseIeee();
        	while(c.getBytes().length%16!=0)
				c=c+"#";
          	String keyc = userordermain.getHouseIeee().substring(6, 16)+"NETVOX";
          	byte[] encryptResultc = encrypt2(c, keyc);
          	String encryptResultStrc = parseByte2HexStr(encryptResultc);
          	upUrl.append("&sign=").append(encryptResultStrc).append("&encodemethod=AES&houseIeeeSecret=").append(userordermain.getHouseIeee());
 //       	System.out.println(upUrl);
          	str = TestHttpclient.getUrl(upUrl.toString(),"utf-8");
        	result = JSON.parseObject(str, Map.class);
        	status = (int) ((Map) result.get("message")).get("status");
        	if(status != 1) {
        		if(status == -1)
        			resultJson = "{\"result\":-2}"; //获取xml文件路径超时
        		else
        			resultJson = "{\"result\":-3}";//获取xml文件路径出错
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	userordermain.setXmlFile((String)((Map) result.get("message")).get("status_msg"));
        	long i = userordermainService.ImportZ203ModelAndNode(userordermain);
        	if (i == -1) { 
        		resultJson = "{\"result\":-4}";// 家同名，导入失败
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	if (i == 0) { // 导入失败
				resultJson = "{\"result\":-5}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback
						+ "({\"request_id\": 1234, \"response_params\":"
						+ resultJson + "})");
				return;
			}
        	userordermainService.ImportFromLib2(i); // houseid
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    		String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("ImportZ203ModelAndNode", e);	
			String resultJson = "{\"result\":-5,\"message\":\"" + e.getMessage() + "\"}";//成功0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
	}

	/**
	 * 判断某一个HouseID的订单是否存在
	 * 
	 * @author: zhuangxd 时间：2013-12-6 下午4:40:23
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/isExisitOrder")
	public void isExisitOrder(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");

		try {
			Userordermain userordermain = JSON.parseObject(json,
					Userordermain.class);
			Userordermain t = userordermainService.findOrder(userordermain);
			String resultJson = "{\"result\":0}";// 成功1 失败0
			if (t != null) {
				// 已经存在订单
				resultJson = "{\"result\":1}";// 成功1 失败0
			} else {
				// 不存在订单
				resultJson = "{\"result\":-1}";// 成功1 失败0
			}
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
			// String callback =
			// request.getParameter("callback");out.println(callback +
			// "({\"request_id\": 1234, \"response_params\":" + resultJson +
			// "})");
		} catch (Exception e) {
			/*
			 * String resultJson = "{\"result\":0}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})");
			 */
			LOGGER.info("isExisitOrder", e);	
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}

		// 多表关联
		/*
		 * try { Userordermain userordermain =
		 * JSON.parseObject(json,Userordermain.class); // Userordermain t =
		 * userordermainService.find(userordermain); List<Userordermain> t =
		 * userordermainService.findList(userordermain); List<Object[]> s =
		 * userordermainService.findListSql(userordermain); for (Object[]
		 * objects : s) { Userordermain userordermain2 =
		 * (Userordermain)objects[0]; Deviceattribute deviceattribute =
		 * (Deviceattribute)objects[1]; } for (Object[] objects : s) {
		 * Userordermain userordermain2 = (Userordermain)objects[0];
		 * DevicewarnhistoryUserordermainidYear
		 * devicewarnhistoryUserordermainidYear =
		 * (DevicewarnhistoryUserordermainidYear)objects[1]; } // String
		 * resultJson= JSON.toJSONStringWithDateFormat(t,
		 * "yyyy-MM-dd HH:mm:ss"); String resultJson=
		 * JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss"); String
		 * callback = request.getParameter("callback");out.println(callback +
		 * "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		 * } catch (Exception e) { String resultJson = "{\"result\":0}";//成功1
		 * 失败0 String callback =
		 * request.getParameter("callback");out.println(callback +
		 * "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		 * }
		 */
	}

	/**
	 * 返回某一个HouseID的所有的Node名称和数量列表
	 * 
	 * @author: zhuangxd 时间：2013-12-9 下午1:57:13
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeNodeSumList")
	public void GetModeNodeList(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");

		try {
			Userordermain userordermain = JSON.parseObject(json,
					Userordermain.class);
			Map t = userordermainService.findModeNodeSumList(userordermain);
			String resultJson = JSON.toJSONStringWithDateFormat(t,
					"yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
			// String callback =
			// request.getParameter("callback");out.println(callback +
			// "({\"request_id\": 1234, \"response_params\":" + resultJson +
			// "})");
		} catch (Exception e) {
			/*
			 * String resultJson = "{\"result\":0}";//成功1 失败0 String callback =
			 * request.getParameter("callback");out.println(callback +
			 * "({\"request_id\": 1234, \"response_params\":" + resultJson +
			 * "})");
			 */
			LOGGER.info("GetModeNodeList", e);	
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}

		// 多表关联
		/*
		 * try { Modedevice modedevice =
		 * JSON.parseObject(json,Modedevice.class); // Modedevice t =
		 * modedeviceService.find(modedevice); List<Modedevice> t =
		 * modedeviceService.findList(modedevice); List<Object[]> s =
		 * modedeviceService.findListSql(modedevice); for (Object[] objects : s)
		 * { Modedevice modedevice2 = (Modedevice)objects[0]; Deviceattribute
		 * deviceattribute = (Deviceattribute)objects[1]; } for (Object[]
		 * objects : s) { Modedevice modedevice2 = (Modedevice)objects[0];
		 * DevicewarnhistoryModedeviceidYear devicewarnhistoryModedeviceidYear =
		 * (DevicewarnhistoryModedeviceidYear)objects[1]; } // String
		 * resultJson= JSON.toJSONStringWithDateFormat(t,
		 * "yyyy-MM-dd HH:mm:ss"); String resultJson=
		 * JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss"); String
		 * callback = request.getParameter("callback");out.println(callback +
		 * "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		 * } catch (Exception e) { String resultJson = "{\"result\":0}";//成功1
		 * 失败0 String callback =
		 * request.getParameter("callback");out.println(callback +
		 * "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		 * }
		 */
	}

	/**
	 * 订单删除
	 * 
	 * @author: zhuangxd 时间：2014-1-11 下午3:54:51
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		try {
			Userordermain userordermain = JSON.parseObject(json,
					Userordermain.class);
			userordermainService.delete(userordermain);
			String resultJson = "{\"result\":1}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("delete", e);	
			String resultJson = "{\"result\":0}";// 成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback
					+ "({\"request_id\": 1234, \"response_params\":"
					+ resultJson + "})");
		}
	}

	/**
	 * 导出model和node文件到z203
	 * @param json json字符串
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/exportZ203ModelAndNode")
	public void exportZ203ModelAndNode(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		String resultJson = null;
        try {
        	Userordermain userordermain=JSON.parseObject(json, Userordermain.class);
        	//读取SCHC用户
        	ModeSchcUser modesu = null;
        	boolean isUpdate = false;
        	if(userordermain.getUserName() == null || userordermain.getPassword() == null) {
	        	modesu = userordermainService.getSCHCUser(userordermain.getUserId(), userordermain.getHouseIeee());
	        	if(modesu != null) {
	        		isUpdate = true;
	        		userordermain.setUserName(modesu.getSchcUserName());
	        		userordermain.setPassword(modesu.getSchcPassword());
	        	}
        	}
        	//获取代理服务器IP
        	String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(userordermain.getHouseIeee());
        	String vendorCode = ((Houseieee) HouseieeeListener.houseieeeMap.get(userordermain.getHouseIeee())).getVendorCode();
			//验证用户名、密码
        	String serverPort = PropertiesUtil.getProperty("xmpp.port");
        	//ftp IP/Port
        	String modelftpIp = PropertiesUtil.getProperty("modelftpIp");
        	String modelftpPort = PropertiesUtil.getProperty("modelftpPort");
        	StringBuilder loginUrl = new StringBuilder("http://");
        	//loginUrl.append(xmppHost).append(":").append(xmppPort).append("/z203chat/poll?context=xmllogin/")
        	loginUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=xmllogin/")
        	.append(userordermain.getUserName()).append("/").append(userordermain.getPassword()).append("/").append(vendorCode)
        	.append("&username=").append(userordermain.getHouseIeee());
        	//加密
        	String content="context=xmllogin/"+userordermain.getUserName()+"/"+userordermain.getPassword()+"&username="+userordermain.getHouseIeee();
        	while(content.getBytes().length%16!=0)
				content=content+"#";
        	String key = userordermain.getHouseIeee().substring(6, 16)+"NETVOX";
        	byte[] encryptResult = encrypt2(content, key);
        	String encryptResultStr = parseByte2HexStr(encryptResult);
        	loginUrl.append("&sign=").append(encryptResultStr).append("&encodemethod=AES&houseIeeeSecret=").append(userordermain.getHouseIeee());
        	//加密结束        	
        	String str = TestHttpclient.getUrl(loginUrl.toString(),"utf-8");
        	Map<String, Object> result = JSON.parseObject(str, Map.class);
        	int status = (int) ((Map) result.get("message")).get("status");
        	if(status != 0) {
        		resultJson = "{\"result\":" + status + "}";//登录失败
    			String callback = request.getParameter("callback");
    			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
        		return;
        	}
        	//更新/保存SCHC的用户
        	if(modesu == null) {
        		modesu = new ModeSchcUser();
        		modesu.setUserId(userordermain.getUserId());
        		modesu.setHouseIeee(userordermain.getHouseIeee());
        		modesu.setSchcUserName(userordermain.getUserName());
        		modesu.setSchcPassword(userordermain.getPassword());
        		if(isUpdate)
        			userordermainService.updateSCHCUser(modesu);
        		else
        			userordermainService.saveSCHCUser(modesu);
        	}
        	//导出xml文件到z203
        	if(userordermain.getHouseId() == 0) { 
        		resultJson = "{\"result\":-2}";//请先选择家
        		String callback = request.getParameter("callback");
    			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
        	}
        	//查找对应的xml path
        	String xmlPath = userordermainService.findXmlPath(userordermain.getUserId(), userordermain.getHouseId());
        	if(StringUtils.isBlank(xmlPath)) {
        		resultJson = "{\"result\":-3}";//请先生成订单
    			String callback = request.getParameter("callback");
    			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	//转换成e:\\user\\mms.xml
        	xmlPath = xmlPath.replaceAll("\\\\", "\\\\\\\\");
        	StringBuilder downUrl = new StringBuilder("http://");
        	//downUrl.append(xmppHost).append(":").append(xmppPort).append("/z203chat/poll");
        	downUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll");

        	//对z203Map加密
        	
        	String str1="context=xmldown/"+xmlPath+"username="+userordermain.getHouseIeee();
        	while(str1.getBytes().length%16!=0){
        		str1=str1+"#";}
        	String key1 =userordermain.getHouseIeee().substring(6, 16)+"NETVOX";
        	byte[] encryptResulth = encrypt3(str1, key1);
        	String encryptResultStrh = parseByte2HexStr(encryptResulth);
        	downUrl.append("?sign=").append(encryptResultStrh).append("&encodemethod=AES&houseIeeeSecret=").append(userordermain.getHouseIeee());
        	Map<String, String> params = new HashMap<String, String>();       	
        	//加密结束
        	String[] copys=xmlPath.split(";");
			long size=new File(copys[0]).length();
			long size2=new File(copys[1]).length();
			long size3=new File(copys[2]).length();
            params.put("context", "xmldown/" +modelftpIp + "/" +modelftpPort + "/" + xmlPath+ "/" + size+ "/" + size2+"/" + size3);
        	params.put("username", userordermain.getHouseIeee());        	
        	str = TestHttpclient.postUrlWithParams(downUrl.toString(), params, "utf-8");
        	result = JSON.parseObject(str, Map.class);
        	status = (int) ((Map) result.get("message")).get("status");
        	if(status != 1) {
        		if(status == -1)
        			resultJson = "{\"result\":-4}";//导出到z203超时
        		else
        			resultJson = "{\"result\":-5}";//导出到z203错误
        		String callback = request.getParameter("callback");
    			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
 //       		out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
        		return;
        	}
        	resultJson = "{\"result\":-7}";//成功0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//        	out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
        } catch (Exception e) {
			LOGGER.info("exportZ203ModelAndNode", e);	
			resultJson = "{\"result\":-6}";//成功0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
		}
	}
	 public static byte[] encrypt2(String content, String password)  
			 throws Exception {	
         SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");  
         Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
         byte[] byteContent = content.getBytes("utf-8");  
         cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
         byte[] result = cipher.doFinal(byteContent);  
         return result; // 加密   
	 }
	 
	 public static byte[] encrypt3(String content, String password)  
			 throws Exception {	
		 
         SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");  
         Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
         byte[] byteContent = content.getBytes();  
         cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
         byte[] result = cipher.doFinal(byteContent);  
         return result; // 加密   
	 }
	 /**将二进制转换成16进制 
		 * @param buf 
		 * @return 
		 */  
		public static String parseByte2HexStr(byte buf[]) {  
		        StringBuffer sb = new StringBuffer();  
		        for (int i = 0; i < buf.length; i++) {  
		                String hex = Integer.toHexString(buf[i] & 0xFF);  
		                if (hex.length() == 1) {  
		                        hex = '0' + hex;  
		                }  
		                sb.append(hex.toUpperCase());  
		        }  
		        return sb.toString();  
		}  	
		
	/**
	 * 获取代理服务器上配置的云端ip地址
	 * @param houseIeee 
	 * @return
	 * @throws Exception
	 */
	private String abtainXmppIp(String houseIeee) throws Exception {
		String agenthost = PropertiesUtil.getProperty("cloud.agenthost");
		String agentport = PropertiesUtil.getProperty("cloud.agentport");
		StringBuilder endpoint = new  StringBuilder("http://");
		endpoint.append(agenthost).append(":").append(agentport)
		.append("/zigBeeDevice/proxyserverController/find.do?").append("json={")
		.append("\"houseIeee\"").append(":").append("\"").append(houseIeee).append("\",")
		.append("\"type\"").append(":").append("\"2\"").append(",")
		.append("\"secondType\"").append(":").append("\"0\"").append("}");
		
		String strresult="json={"+"\"houseIeee\""+":"+"\""+houseIeee+"\","+"\"type\""+":"+"\"2\""+","+
				"\"secondType\""+":"+"\"0\""+"}houseIeeeSecret="+houseIeee;
		strresult = strresult.replace("{", "%7B").replace("}", "%7D")
				.replace("\"", "%22").replace(" ", "%20");
		while (strresult.getBytes().length%16!=0) {
			strresult=strresult+"#";
		}    		
		String keyproxy = houseIeee.substring(6, 16)+"NETVOX";
		byte[] encryptResultproxy = AESCodec.encrypt2(strresult, keyproxy);
		String encryptResultStrproxy = AESCodec.parseByte2HexStr(encryptResultproxy);
		endpoint.append("&sign=").append(encryptResultStrproxy)
		.append("&encodemethod=AES&houseIeeeSecret=").append(houseIeee);
		
		String rRul = endpoint.toString().replace("{", "%7B").replace("}", "%7D")
				.replace("\"", "%22").replace(" ", "%20");
		String strurl = TestHttpclient.getUrl(rRul,"utf-8");
		int i = strurl.indexOf("(");
		strurl =strurl.substring(0,i)+strurl.substring(i+1);
		int j = strurl.indexOf(")");
		strurl =strurl.substring(0,j)+strurl.substring(j+1);    		
		strurl = strurl.substring(4, strurl.length());
		Map<String, List<Map>> AddrIPresult = JSON.parseObject(strurl, Map.class);
		String serverIp =(String) AddrIPresult.get("response_params").get(0).get("serverIp");
		return serverIp;
	}
	
	/**
	 * 第三方验证创建用户接口
	 */
	@RequestMapping("/addnewthirdpartuser")
	public void addnewthirdpartuser(String json,HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8");  
//		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String userid_third_part = (String) paramMap.get("userid_third_part");
			String PWD = (String)paramMap.get("PWD");
			String verify_code = (String)paramMap.get("verify_code");
			String houseIeee = (String)paramMap.get("houseIeee");
	//		http://localhost:8081/spring-async/z203chat/poll?context=getUserDataInfo/00137A0000012EAB&username=00137A0000012EAB
	    	//获取代理服务器IP
//	    	String serverIp = "192.168.1.186";
	    	String serverIp = PropertiesUtil.getProperty("xmpp.host");
			//登录到203服务器
//			String serverPort = "8081";
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
/*			//返回用户列表
 * 			StringBuilder callUrl = new StringBuilder("http://");
			callUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=getUserDataInfo/").append(houseIeee)
			.append("&").append("username=").append(houseIeee);
			//加密
	    	String content="context=getUserDataInfo/"+houseIeee+"&username="+houseIeee;
	    	while(content.getBytes().length%16!=0)
				content=content+"#";
	    	String key = houseIeee.substring(6, 16)+"NETVOX";
	    	byte[] encryptResult = encrypt2(content, key);
	    	String encryptResultStr = parseByte2HexStr(encryptResult);
	    	callUrl.append("&sign=").append(encryptResultStr).append("&encodemethod=AES&houseIeeeSecret=").append(houseIeee);
	    	//加密结束
	    	String str = TestHttpclient.getUrl(callUrl.toString(),"utf-8");
	    	Map<String, Object> result = JSON.parseObject(str, Map.class);
	    	List<Map> list = (List)result.get("response_params");
	    	for (int i = 0; i < list.size(); i++) {
				if(list.get(i).get("id").equals(userid_third_part)){
					String resultJson = "{\"result\":2}";//已存在
	    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
	    			return;
				}
			}*/
	    	//http://localhost:8081/spring-async/z203chat/poll?context=adduser/username/pass/verer&username=00137A0000012EAB
	    	StringBuilder callUrl1 = new StringBuilder("http://");
	    	callUrl1.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=adduser/").append(userid_third_part)
			.append("/").append(PWD).append("/").append(verify_code)
			.append("&").append("username=").append(houseIeee);
			//加密
		    String content1="context=getUserDataInfo/"+houseIeee+"&username="+houseIeee;
		    while(content1.getBytes().length%16!=0)
				content1=content1+"#";
		    String key1 = houseIeee.substring(6, 16)+"NETVOX";
		    byte[] encryptResult1 = encrypt2(content1, key1);
		    String encryptResultStr1 = parseByte2HexStr(encryptResult1);
		    callUrl1.append("&sign=").append(encryptResultStr1).append("&encodemethod=AES&houseIeeeSecret=").append(houseIeee);
		    //加密结束
			String str1 = TestHttpclient.getUrl(callUrl1.toString(),"utf-8");
	    	Map<String, Object> result1 = JSON.parseObject(str1, Map.class);
	    	int status = (int)((Map)result1.get("message")).get("status");
    		if(status == 0){
    			String resultJson ="{\"result\":0}";//创建成功
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
    		}
    		if(status == -1) {
    			String resultJson ="{\"result\":-1}";//验证码错误
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
			}
    		if(status == -2) {
    			String resultJson ="{\"result\":-2}";//密码和验证密码不一致
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
			}
    		if(status == -3) {
    			String resultJson ="{\"result\":-3}";//XML加载失败
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
			}
    		if(status == -4) {
    			String resultJson ="{\"result\":-4}";//用户存在
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
			}
    		if(status == -5) {
    			String resultJson ="{\"result\":-5}";//密码加密出错
    			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
			}
		} catch (Exception e) {
			LOGGER.info("addnewthirdpartuser", e);	
			String resultJson = "{\"result\":-6,\"message\":\"" + e.getMessage() + "\"}";//出错
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
}
