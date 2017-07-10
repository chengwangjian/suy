package org.smarthome.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.smarthome.domain.BfTestFile;
import org.smarthome.model.DataGrid;
import org.smarthome.services.BfTestFileServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zbHouse.pageModel.Json;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/BfTestFileController")
public class BfTestFileController {

	private static final Logger LOGGER = Logger.getLogger(BfTestFileController.class);
	private BfTestFileServiceI bfTestFile;
			
	public BfTestFileServiceI getBfTestFile() {
		return bfTestFile;
	}
	@Autowired
	public void setBfTestFile(BfTestFileServiceI bfTestFile) {
		this.bfTestFile = bfTestFile;
	}
	@RequestMapping("/add")
	public void showSave(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);	
		Json j = new Json();
		try {
			json=URLDecoder.decode(json, "gbk");
			BfTestFile hard=JSON.parseObject(json,BfTestFile.class);
			j.setResponse_params(bfTestFile.saveOrUpdate(hard));
		} catch (Exception e) {
			LOGGER.info("showSave", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);			
		}
		writeJson(j,callback,response);
		//request.setAttribute("j",j);
			
	}
	@RequestMapping("/update")
	public void showUpdate(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);	
		Json j = new Json();
		try {
			json=URLDecoder.decode(json, "gbk");
			BfTestFile hard=JSON.parseObject(json,BfTestFile.class);
			j.setResponse_params(bfTestFile.keyUpdate(hard));
		} catch (Exception e) {
			LOGGER.info("showUpdate", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);			
		}
		writeJson(j,callback,response);
		//request.setAttribute("j",j);
			
	}
	
	@RequestMapping("/delete")
	public void showDelete(String json,String callback,HttpServletRequest request,HttpServletResponse response) {	
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);		      
		Json j = new Json();
		try {
			BfTestFile hard=JSON.parseObject(json,BfTestFile.class);
			j.setResponse_params(bfTestFile.delete(hard));			
		} catch (Exception e) {
			LOGGER.info("showDelete", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);	
		}
		writeJson(j,callback,response);
		//request.setAttribute("j",j);
		
	}

	@RequestMapping("/find")
	public void showFind(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
//		{"id":1, "file_title":"IMG_3802.jpg", "file_location":"Project2\/201311\/115001160.jpg", "file_type":"jpg", "file_size":"689.2207KB", "is_dropped":"0", "target_id":52, "target_type":"bug", "add_action_id":134, "delete_action_id":null } 
		Json j = new Json();DataGrid dg = new DataGrid();
		try {
			map=JSON.parseObject(json);	
			j.setResponse_params(bfTestFile.findList(map));
		} catch (Exception e) {
			LOGGER.info("showFind", e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
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
