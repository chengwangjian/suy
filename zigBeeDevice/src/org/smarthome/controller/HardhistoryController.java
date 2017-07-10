package org.smarthome.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.smarthome.domain.Hardhistory;
import org.smarthome.model.DataGrid;
import org.smarthome.services.HardhistoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import zbHouse.pageModel.Json;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/HardhistoryController")
public class HardhistoryController {

	private static final Logger LOGGER = Logger.getLogger(HardhistoryController.class);
	private HardhistoryServiceI hardhistory;
		
	public HardhistoryServiceI getHardhistory() {
		return hardhistory;
	}
	@Autowired
	public void setHardhistory(HardhistoryServiceI hardhistory) {
		this.hardhistory = hardhistory;
	}

	@RequestMapping("/add")
	public void showSave(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);	
		Json j = new Json();
		try {
			json=URLDecoder.decode(json, "gbk");
			Hardhistory hard=JSON.parseObject(json,Hardhistory.class);
			j.setResponse_params(hardhistory.saveOrUpdate(hard));
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
			Hardhistory hard=JSON.parseObject(json,Hardhistory.class);
			j.setResponse_params(hardhistory.keyUpdate(hard));
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
			Hardhistory hard=JSON.parseObject(json,Hardhistory.class);
			j.setResponse_params(hardhistory.delete(hard));			
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
//		{"type":"all","id":0,"search":"203","orderBy":"h.updateTime desc","pageIndex":"0","pageSize":"5"}
		Json j = new Json();DataGrid dg = new DataGrid();

		try {
			map=JSON.parseObject(json);	
			j.setResponse_params(hardhistory.findList(map));
		} catch (Exception e) {
			LOGGER.info("showFind", e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);
		//request.setAttribute("j",j);			
			
	}
	@RequestMapping("/getCount")	
	public void getCount(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();DataGrid dg = new DataGrid();
		try {
			map=JSON.parseObject(json);	
			map.put("total", hardhistory.getCount(map));
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getCount", e);
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
			LOGGER.info("writeJson", e);
		}
	}
	@RequestMapping("/deletehardhistory")	
	public void deletehardhistory(String id,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		int jstr=0;
		try {
			jstr=hardhistory.deletehardhistory(id);
			out.println("{\"result\":"+jstr+"}");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("deletehardhistory", e);
			out.println("{\"result\":"+jstr+"}");
		}
		
		
	}
}
