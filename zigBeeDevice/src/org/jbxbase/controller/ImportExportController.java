package org.jbxbase.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jbxbase.services.ImportExportServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zbHouse.pageModel.Json;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/ImportExportController")
public class ImportExportController {

	private static final Logger LOGGER = Logger.getLogger(ImportExportController.class);
	private ImportExportServiceI importexport;
				
	public ImportExportServiceI getImportexport() {
		return importexport;
	}
	@Autowired
	public void setImportexport(ImportExportServiceI importexport) {
		this.importexport = importexport;
	}
	@RequestMapping("/Export")
	public void doExport(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);	
		Json j = new Json();
		try {
			Map hard=JSON.parseObject(json,Map.class);
			dg.put("status", 1);
			dg.put("status_msg", importexport.Export(hard));
			j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("showSave", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);			
		}
		writeJson(j,callback,response);
		//request.setAttribute("j",j);
			
	}
	@RequestMapping("/Import")
	public void doImport(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);	
		Json j = new Json();
		try {
			Map hard=JSON.parseObject(json,Map.class);
			dg.put("status", 1);
			dg.put("status_msg", importexport.Import(hard));			
			j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("showUpdate", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);			
		}
		writeJson(j,callback,response);
		//request.setAttribute("j",j);
			
	}
	
	@RequestMapping("/find")
	public void showFind(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		Json j = new Json();
		try {
			Map map=JSON.parseObject(json);	
			dg.put("status", 1);
			dg.put("status_msg", importexport.findList(map));
			j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("showFind", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);
		//request.setAttribute("j",j);			
			
	}
	
	public void writeJson(Object object,String callback,HttpServletResponse response) {

		try {
			String jstr=callback+"("+JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss")+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson", e);
		}
	}	
}
