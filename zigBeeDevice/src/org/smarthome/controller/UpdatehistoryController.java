package org.smarthome.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.smarthome.domain.Updatehistory;
import org.smarthome.model.DataGrid;
import org.smarthome.services.UpdatehistoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zbHouse.pageModel.Json;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/UpdatehistoryController")
public class UpdatehistoryController {

	private static final Logger LOGGER = Logger.getLogger(UpdatehistoryController.class);
	private UpdatehistoryServiceI updatehistory;
	
	public UpdatehistoryServiceI getUpdatehistory() {
		return updatehistory;
	}
	@Autowired
	public void setUpdatehistory(UpdatehistoryServiceI updatehistory) {
		this.updatehistory = updatehistory;
	}	
	
	@RequestMapping("/add")
	public void showSave(String json,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);	
		Json j = new Json();
		try {

			j.setResponse_params(dg);

		} catch (Exception e) {
			LOGGER.info("showSave", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);			
		}
		writeJson(j,response);
		//request.setAttribute("j",j);
			
	}
	
	@RequestMapping("/delete")
	public void showDelete(String json,HttpServletRequest request,HttpServletResponse response) {	
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);		      
		Json j = new Json();
		try {

			j.setResponse_params(dg);
			
		} catch (Exception e) {
			LOGGER.info("showDelete", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);	
		}
		writeJson(j,response);
		//request.setAttribute("j",j);
		
	}

	@RequestMapping("/FinishDownloadVersion")
	public void showFind(String json,HttpServletRequest request,HttpServletResponse response) {
		DataGrid dg = new DataGrid();
		//{"UpdateTime":"2014-6-21 11:17:33","HouseIEEE":"1013b","BeforeVer":"1.0","AfterVer":"1.1","UpdateStatus":"success","UpdateOperate":"A"}
		Json j = new Json();
		try {
			Updatehistory u=JSON.parseObject(json,Updatehistory.class);
			updatehistory.saveOrUpdate(u);
			j.setResponse_params(dg);					
		} catch (Exception e) {
			LOGGER.info("showFind", e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,response);
		//request.setAttribute("j",j);			
			
	}
	
	public void writeJson(Object object,HttpServletResponse response) {

		try {
			String jstr= JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson", e);
		}
	}	
}
