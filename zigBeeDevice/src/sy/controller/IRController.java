package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.IRBrand;
import sy.model.IRBranddictionary;
import sy.model.IRTempData;
import sy.service.IRServiceI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author huanglw
 *
 */
@Controller
@RequestMapping("/IR")
public class IRController {
	private static final Logger LOGGER = Logger.getLogger(IRController.class);
	
	private IRServiceI IRService;

	public IRServiceI getIRService() {
		return IRService;
	}

	@Autowired
	public void setIRService(IRServiceI iRService) {
		IRService = iRService;
	}
	
	@RequestMapping("/getBMs")
	public void getBMs(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("callback");
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getBMs-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getBMs.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String data = (String) paramMap.get("IRData");
			if(StringUtils.isBlank(data)) {
				resultJson = "{\"result\":0,\"msg\":\"IRData must not be empty\"}";
				out.println(callback + "({\"request_id\":1234, \"response_params\":" + resultJson + "})");
				return;
			}
			Integer irType = (Integer) paramMap.get("irType");
			if(irType == null) {
				resultJson = "{\"result\":0,\"msg\":\"irType must not be empty\"}";
				out.println(callback + "({\"request_id\":1234, \"response_params\":" + resultJson + "})");
				return;
			}
			String houseIeeeSecret = request.getParameter("houseIeeeSecret");
			
			List<Map> irList = IRService.abtainIRBrandModels(data, irType,houseIeeeSecret);
			String irListStr = "[]";
			if(irList != null && !irList.isEmpty())
				irListStr = JSON.toJSONStringWithDateFormat(irList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			resultJson = "{\"result\":1,\"irList\":" + irListStr + "}";
			out.println(callback + "({\"request_id\":1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getBMs", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\":1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/getBrands")
	public void getBrands(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("callback");
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getBrands-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getBrands.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			List<Map> brandList = IRService.abtainBrand((Integer) paramMap.get("irType"), 
					(String) paramMap.get("brandName"));
//			String brandListStr = "[]";
//			if(brandList != null && !brandList.isEmpty())
//				brandListStr = JSON.toJSONStringWithDateFormat(brandList, "yyyy-MM-dd HH:mm:ss");
//			resultJson = "{\"result\":1,\"brandList\":" + brandListStr + "}";
//			out.println(callback + "({\"response_id\": \"wifi\", \"response_params\":" + resultJson + "})");
			out.println(getStrIn1024(brandList, callback, "brandList"));
		} catch (Exception e) {
			LOGGER.info("getBrands", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/getModels")
	public void getModels(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("callback");
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getModels-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getModels.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			List<Map> modelList = IRService.abtainModel((Integer) paramMap.get("brandId"), 
					(String) paramMap.get("brandName"));
//			String modelListStr = "[]";
//			if(modelList != null && !modelList.isEmpty())
//				modelListStr = JSON.toJSONStringWithDateFormat(modelList, "yyyy-MM-dd HH:mm:ss");
//			resultJson = "{\"result\":1,\"modelList\":" + modelListStr + "}";
//			out.println(callback + "({\"response_id\": \"wifi\", \"response_params\":" + resultJson + "})");
			out.println(getStrIn1024(modelList, callback, "modelList"));
		} catch (Exception e) {
			LOGGER.info("getModels", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/getIRXml")
	public void getIRXml(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("callback");
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getIRXml-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getIRXml.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String ftpPath = IRService.abtainIRXmlData((String) paramMap.get("brandName"), 
					(String) paramMap.get("modelName"));
			resultJson = "{\"result\":1,\"xmlPath\":\"" + ftpPath + "\"}";
			out.println(callback + "({\"request_id\":1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getIRXml", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\":1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/getBMsByName")
	public void getBMsByName(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("callback");
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getBMsByName-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getBMsByName.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			List<Map> irList = IRService.abtainIRBrandModelsByName(
					(String) paramMap.get("name"), (Integer) paramMap.get("irType"));
//			String irListStr = "[]";
//			if(!irList.isEmpty())
//				irListStr = JSON.toJSONStringWithDateFormat(irList, "yyyy-MM-dd HH:mm:ss");
//			resultJson = "{\"result\":1,\"irList\":" + irListStr + "}";
//			out.println(callback + "({\"response_id\": \"wifi\", \"response_params\":" + resultJson + "})");
			out.println(getStrIn1024(irList, callback, "irList"));
		} catch (Exception e) {
			LOGGER.info("getBMsByName", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	/**
	 * IRXMl文件导入读取文件内容
	 */
	@RequestMapping("/showIRxml")
	public void showIRxml(String json,HttpServletRequest request,HttpServletResponse response){
		LOGGER.info("json=" +json);
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		StringBuilder filePath = new StringBuilder();
		try {
			out = response.getWriter();
//			Map<String,Object> param = JSON.parseObject(json,Map.class);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			List fileItems = upload.parseRequest(request);
			Map<Document, Integer> docMap = new HashMap<Document, Integer>();
			Map<String, Object> params = new HashMap<String, Object>();
			for(Object fi : fileItems) {
				FileItem fii = (FileItem) fi;
				if(!fii.isFormField()) {
					SAXReader reader = new SAXReader();
					Document doc = reader.read(fii.getInputStream());
					long fileSize = fii.getSize() / 1024L; //转换为KB
					docMap.put(doc, new Long(fileSize).intValue());
//					if( StringUtils.isNotBlank(overwrite) && overwrite.equals("0")) {
					params=IRService.showIRxml(doc);
						//区分ie和火狐、谷歌传递的文件名，ie带斜杠
						String fileName_ = fii.getName();
						int xgIndex = fileName_.lastIndexOf("\\"); 
						if( xgIndex != -1)
							fileName_ = fileName_.substring(xgIndex);
						filePath.append(fileName_).append(",");
						params.put("fileName", fileName_);
						params.put("fileSize", fileSize);
					}
				}
//			}
			String resultJson= JSON.toJSONStringWithDateFormat(params, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			out.println("{\"request_id\":1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
			LOGGER.info("jiexiIRxml", e);
			resultJson = "{\"result\":0}";
			out.println("{\"request_id\":1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	
	
	@RequestMapping("/jiexiIRxml")
	public void jiexiIRxml(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
//		String callback = request.getParameter("callback");
		StringBuilder filePath = new StringBuilder();
//		log.info("aa:" + AopUtils.isAopProxy(IRService));
//		log.info("bb:" + AopUtils.isCglibProxy(IRService));
//		log.info("cc:" + AopUtils.isJdkDynamicProxy(IRService));
		try {
			out = response.getWriter();
			Map<String, Object> param = JSON.parseObject(json, Map.class);
			String overwrite = (String) param.get("overwrite");
			String userId = (String) param.get("userId");
			String checked = (String) param.get("checked");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			List fileItems = upload.parseRequest(request);
			Map<Document, Integer> docMap = new HashMap<Document, Integer>();
			for(Object fi : fileItems) {
				FileItem fii = (FileItem) fi;
				if(!fii.isFormField()) {
					SAXReader reader = new SAXReader();
					Document doc = reader.read(fii.getInputStream());
					long fileSize = fii.getSize() / 1024L; //转换为KB
					docMap.put(doc, new Long(fileSize).intValue());
					if(IRService.isModelExist(doc,param) != null && StringUtils.isNotBlank(overwrite) && overwrite.equals("0")) {
						//区分ie和火狐、谷歌传递的文件名，ie带斜杠
						String fileName_ = fii.getName();
						int xgIndex = fileName_.lastIndexOf("\\"); 
						if( xgIndex != -1)
							fileName_ = fileName_.substring(xgIndex);
						filePath.append(fileName_).append(",");
					}
				}
				
			}
			//判断是否IR是否已存在
			if(filePath.length() > 0) {
				filePath = filePath.deleteCharAt(filePath.length() - 1);
				resultJson = "{\"result\":-1,\"existFile\":\"" + filePath + "\"}";
				out.println("{\"response_id\": \"wifi\", \"response_params\":" + resultJson + "}");
				return;
			}	
			//保存IR数据
			Iterator itor = docMap.keySet().iterator();
			while(itor.hasNext()) {
				Document doc = (Document) itor.next();
				Integer fileSize = docMap.get(doc);
				IRService.saveIRxml(doc, userId, checked, fileSize,param);
			}
			out.println("{\"request_id\":1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("jiexiIRxml", e);
			resultJson = "{\"result\":0}";
			out.println("{\"request_id\":1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getIRCount")
	public void getIRCount(String json, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
//		String callback = (String) paramMap.get("callback");
		try {
			out = response.getWriter();			
			String searchText = (String) paramMap.get("searchText");			
			int total = IRService.getIRCount(searchText);
			resultJson = "{\"result\":1,\"total\":" + total + "}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getIRCount", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getIR")
	public void getIR(String json, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("json=" + json);
		List<Map> irList = null;
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
//		String callback = request.getParameter("callback");
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
			String searchText = (String) paramMap.get("searchText");
			irList = IRService.queryIR(searchText, Integer.parseInt(startRow), Integer.parseInt(pageSize), orderBy);
			String irJson = JSON.toJSONStringWithDateFormat(irList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			String resultJson = "{\"result\":1,\"irList\":" + irJson + "}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getIR", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/updateModelCheck")
	public void updateModelCheck(String json, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
//		String callback = (String) paramMap.get("callback");
		try {
			out = response.getWriter();	
			String userId = (String) paramMap.get("userId");
			String modelId = (String) paramMap.get("modelId");
			String checked = (String) paramMap.get("checked");
			IRService.updateModeldlCheck(checked, userId, modelId);
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("updateModelCheck", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/deleteModel")
	public void deleteModel(String json, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			out = response.getWriter();	
			String modelId = (String) paramMap.get("modelId");
			IRService.deleteModelAndIRData(modelId);
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("deleteModel", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	/**
	 * 获取字符串长度不能多于1024
	 * @param irList
	 * @param callback
	 * @return
	 */
	private String getStrIn1024(List<Map> irList, String callback, String listName) {
		String irListStr = "[]";
		String resultJson = "";
		String reStr = "";
		if(!irList.isEmpty()) {
			irListStr = JSON.toJSONStringWithDateFormat(irList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			resultJson ="{\"result\":1,\"" + listName + "\":" + irListStr + "}";
			reStr = callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})";
			while(reStr.length() > 10240) {
				irList.remove(irList.size() - 1);
				irListStr = JSON.toJSONStringWithDateFormat(irList, "yyyy-MM-dd HH:mm:ss");
				resultJson ="{\"result\":1,\"" + listName + "\":" + irListStr + "}";
				reStr = callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})";
			}
		}
		else {
			resultJson ="{\"result\":1,\"" + listName + "\":" + irListStr + "}";
			reStr = callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})";
		}
		return reStr;
	}
	
	@RequestMapping("/IRdown")
	public void IRdown(String json, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		LOGGER.info("json=" + json);
//		response.addHeader("Access-Control-Allow-Origin", "*");
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
//		String callback = (String) paramMap.get("callback");
		try {
			//PrintWriter out = response.getWriter();
//			out = response.getWriter();
//			OutputStream out1 = response.getOutputStream();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String ftpPath = IRService.IRdown(Long.valueOf(paramMap.get("modelId").toString()), request,response);
			//out1.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("IRdown", e);
			try {
				out = response.getWriter();
			} catch(Exception ex) {
				LOGGER.info("IRdown getWriter", ex);
				resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
			}
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	/**
	 * IR编辑保存
	 * HttpServletRequest request, HttpServletResponse response
	 */
	@RequestMapping("/enbrandedit")
	public void enbrandedit(String json,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		LOGGER.info("json=" + json);
		response.addHeader("Access-Control-Allow-Origin", "*");
		String resultJson ="{\"result\":1}";
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");  
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int t = IRService.update(paramMap);
			if(t!=0){
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
			}
			else{
				out.println("{\"request_id\": 1234, \"response_params\":{\"result\":0}}");
			}
		} catch (Exception e) {
			LOGGER.info("enbrandedit", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	/**
	 * 供app获取返回IR的品牌列表
	 * @param json
	 * @param request
	 * @param response
	 */
	int irType ;
	@RequestMapping("/getIRBrandList")
	public void getIRBrandList(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		
		if (StringUtils.isBlank(pageIndex))
			pageIndex = "1";
		if (StringUtils.isBlank(pageSize))
			pageSize = "30";
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getIRBrandList-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getIRBrandList.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("pageIndex", pageIndex);
//				paramMap2.put("pageSize", pageSize);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int pageSize_ = Integer.parseInt(pageSize);
			int startRow = (Integer.parseInt(pageIndex) - 1) * pageSize_;
			List<Map> IRbrandList = IRService.getBrandList((Integer)paramMap.get("irType"),startRow,pageSize_);
			out.println(getStrIn1024(IRbrandList, callback, "IRbrandList"));
		} catch (Exception e) {
			LOGGER.info("getIRBrandList", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	} 
	/**
	 * 根据某个品牌获取poweron所有型号
	 * @param json
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPowerOn")
	public void getPowerOn(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageIndex))
			pageIndex = "1";
		if (StringUtils.isBlank(pageSize))
			pageSize = "30";
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getPowerOn-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getPowerOn.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("pageIndex", pageIndex);
//				paramMap2.put("pageSize", pageSize);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int pageSize_ = Integer.parseInt(pageSize);
			int startRow = (Integer.parseInt(pageIndex) - 1) * pageSize_;
			List<Map> IRmodelList = IRService.getPowerOn((Integer) paramMap.get("irType"),(String)paramMap.get("brandName"),startRow,pageSize_);
			out.println(getStrIn1024(IRmodelList, callback, "IRmodelList"));
		} catch (Exception e) {
			LOGGER.info("getPowerOn", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
		}
	/**
	 * 根据irType和品牌供app获取所有型号
	 * @param json
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getIRModelList")
	public void getIRModelList(String json, HttpServletRequest request,	HttpServletResponse response) {
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageIndex))
			pageIndex = "1";
		if (StringUtils.isBlank(pageSize))
			pageSize = "30";
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getIRModelList-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getIRModelList.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("pageIndex", pageIndex);
//				paramMap2.put("pageSize", pageSize);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int pageSize_ = Integer.parseInt(pageSize);
			int startRow = (Integer.parseInt(pageIndex) - 1) * pageSize_;
			List<Map> IRmodelList = IRService.getModelList((Integer) paramMap.get("irType"), 
					(String) paramMap.get("brandName"),startRow,pageSize_);

			out.println(getStrIn1024(IRmodelList, callback, "IRmodelList"));
		} catch (Exception e) {
			LOGGER.info("getIRModelList", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	} 

	
	/**
	 * 根据字母的字典表，查找到品牌
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/findbrand")
	public void findbrand(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter(); 
//		response.addHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("callback");
		response.setContentType("text/html;charset=utf-8");  
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("findbrand-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/findbrand.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			IRBrand irbrand=JSON.parseObject(json, IRBrand.class);
			IRBranddictionary irbranddictionary =JSON.parseObject(json,IRBranddictionary.class);
			List<IRBrand> t = IRService.findbrand(irbrand, irbranddictionary);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("findbrand", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	/**
	 *  编辑显示接口，不能共用，编辑品牌中获取品牌列表
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/findbrandedit")
	public void findbrandedit(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter(); 
		response.addHeader("Access-Control-Allow-Origin", "*");
//		response.setContentType("text/html;charset=utf-8");  
		try {
			IRBrand irbrand=JSON.parseObject(json, IRBrand.class);
			List<IRBrand> t = IRService.findbrandedit(irbrand);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("findbrand", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getBrandByIRType")
	public void getBrandByIRType(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageIndex))
			pageIndex = "1";
		if (StringUtils.isBlank(pageSize))
			pageSize = "30";
		try {
//			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
//			String cloudPort = PropertiesUtil.getProperty("cloudPort");
//			String serverHost = request.getServerName();
//			LOGGER.info("getBrandByIRType-----> cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
//			if(!cloudAddress.equals(serverHost) && !serverHost.equals("localhost") 
//					&& !serverHost.equals("127.0.0.1")) {
//				String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/IRController/getBrandByIRType.do";
//				Map<String, Object> paramMap2 = new HashMap<String, Object>();
//				paramMap2.put("json", json);
//				paramMap2.put("callback", callback);
//				paramMap2.put("pageIndex", pageIndex);
//				paramMap2.put("pageSize", pageSize);
//				paramMap2.put("encodemethod", request.getParameter("encodemethod"));
//				paramMap2.put("sign", request.getParameter("sign"));
//				paramMap2.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//				String rsStr = TestHttpclient.postUrlWithParams(url, paramMap2, "utf-8");
//				response.setContentType("text/html;charset=utf-8");
//				response.getWriter().write(rsStr);
//				response.getWriter().flush();
//				return;
//			}
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			int pageSize_ = Integer.parseInt(pageSize);
			int startRow = (Integer.parseInt(pageIndex) - 1) * pageSize_;
			List<Map> IRbrandList = IRService.getBrandByIRType((Integer) paramMap.get("irType"),(String)paramMap.get("brandName"),startRow,pageSize_);
			out.println(getStrIn1024(IRbrandList, callback, "IRbrandList"));
		} catch (Exception e) {
			LOGGER.info("getBrandByIRType", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	/**
	 * 未解码的ir数据保存
	 * @param json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/addUnMatch")
	public void addUnMatch(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			IRService.addUnMatch((String)paramMap.get("houseIeee"),(String)paramMap.get("ieee"), (String)paramMap.get("ep"), (Integer)paramMap.get("hadaemonIrindex"), (String)paramMap.get("data"), (String)paramMap.get("createtime"));
			resultJson = "{\"result\":1}";// 成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("addUnMatch", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/uploadIRxml")
	public void uploadIRxml(String json,HttpServletRequest request,HttpServletResponse response) {
		String resultJson = null;// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			IRTempData irTmpData = JSON.parseObject(json, IRTempData.class);
			String irUID = IRService.uploadIRxml(irTmpData);
			if(irUID == null) {
				resultJson = "{\"result\":-1}"; // 未找到文件
			}
			else {
				resultJson = "{\"result\":1,\"irUID\":\"" + irUID + "\"}"; // 成功1 失败0
			}
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("uploadIRxml", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/downloadIRxml")
	public void downloadIRxml(String json,HttpServletRequest request,HttpServletResponse response) {
		String resultJson = null;// 成功1 失败0
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String callback = request.getParameter("callback");
		try {
			out = response.getWriter();
			Map<String, Object> params = JSON.parseObject(json, Map.class);
			String xmlPath = IRService.downloadIRxml(params);
			resultJson = "{\"result\":1,\"xmlPath\":\"" + xmlPath + "\"}"; // 成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("downloadIRxml", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	@RequestMapping("/getIRTempCount")
	public void getIRTempCount(String json, HttpServletRequest request,
			HttpServletResponse response) {
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			out = response.getWriter();			
			String searchText = (String) paramMap.get("searchText");			
			int total = IRService.getIRTempCount(searchText);
			resultJson = "{\"result\":1,\"total\":" + total + "}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getIRCount", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/getIRTemp")
	public void getIRTemp(String json, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("json=" + json);
		List<Map> irList = null;
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
			String searchText = (String) paramMap.get("searchText");
			irList = IRService.queryIRTemp(searchText, Integer.parseInt(startRow), Integer.parseInt(pageSize), orderBy);
			String irJson = JSON.toJSONStringWithDateFormat(irList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
			String resultJson = "{\"result\":1,\"irList\":" + irJson + "}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("getIR", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage()	+ "\"}";// 成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}

	@RequestMapping("/deleteIRTemp")
	public void deleteIRTemp(String json, HttpServletRequest request, HttpServletResponse response) {
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = null;
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			out = response.getWriter();	
			String id = (String) paramMap.get("id");
			IRService.deleteIRTemp(id);
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("deleteIRTemp", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/passIRTemp")
	public void passIRTemp(String json, HttpServletRequest request, HttpServletResponse response) {
		String resultJson = null;// 成功1 失败0
		PrintWriter out = null;
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		try {
			out = response.getWriter();	
			String id = (String) paramMap.get("id");
			String userId = (String) paramMap.get("userId");
			int result = IRService.passIRTemp(id, userId);
			resultJson = "{\"result\":" + result + "}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		} catch (Exception e) {
			LOGGER.info("passIRTemp", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
}
