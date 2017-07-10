package com.flywind.app.resources;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.flywind.app.data.DataGrid;
import com.flywind.app.data.Json;
import com.flywind.app.data.Target;
import com.flywind.app.entities.Modeactlib;
import com.flywind.app.services.Dataactlib;



@Controller
@RequestMapping("/modeact")
public class ModeactResource
{
    private static final Logger LOGGER = Logger.getLogger(ModeactResource.class);

    private Dataactlib data;
    public Dataactlib getData() {
		return data;
	}
    @Autowired 
	public void setData(Dataactlib data) {
		this.data = data;
	}
    
	@RequestMapping("/add")	
    public void add(String entities,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {
    		
    		entities=URLDecoder.decode(entities, "UTF-8");
	    	List<Modeactlib> es = JSON.parseArray(entities,Modeactlib.class);  	    	
    		data.create(es);
			j.setResponse_params(dg); 
		} catch (Exception e) {
			LOGGER.info("add", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);
		 writeJson(j,callback,response);
//		 return j;   	
    }

    
	@RequestMapping("/update")	
    public void update(String entities,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try{ 
    		entities=URLDecoder.decode(entities, "UTF-8");
    		List<Modeactlib> es = JSON.parseArray(entities,Modeactlib.class);	    	
    		data.update(es);
			j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("update", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);
		 writeJson(j,callback,response);
//		 return j;   	
    }    
 
    
	@RequestMapping("/delete")	
    public void delete(String entities,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {  
    		List<Modeactlib> es = JSON.parseArray(entities,Modeactlib.class);	    	
	    	data.delete(es);
			j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("delete", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);    
		 writeJson(j,callback,response);
//		 return j;   	
    }  
    
    
	@RequestMapping("/act")
	public void getAct(String target,String callback,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
    	Json j = new Json();
    	DataGrid dg=new DataGrid();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);      		
			params=data.findmodel(tar);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("getAct", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);
		 writeJson(j,callback,response);
//		 return params;
	}
	@RequestMapping("/getCount")
	public void getCount(String target,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();DataGrid dg = new DataGrid();
		try {
//			map=JSON.parseObject(target);	
			map.put("total", data.getCount(map));
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getCount", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}	
		 LOGGER.info("----------"+map);
		 writeJson(j,callback,response);
	}
	
	public void writeJson(Object object,String callback,HttpServletResponse response) {
		try {
			String jstr=callback+"("+JSON.toJSONString(object)+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson", e);
		}
	}		
	
}
