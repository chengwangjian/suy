package com.flywind.app.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.flywind.app.data.DataGrid;
import com.flywind.app.data.Json;
import com.flywind.app.data.Target;
import com.flywind.app.entities.Deviceattrlib;
import com.flywind.app.services.Dataattrlib;



@Controller
@RequestMapping("/deviceattr")
public class DeviceattrResource
{
    private static final Logger LOGGER = Logger.getLogger(DeviceattrResource.class);

    private Dataattrlib data;
    public Dataattrlib getData() {
		return data;
	}
    @Autowired
	public void setData(Dataattrlib data) {
		this.data = data;
	}
    
	@RequestMapping("/add")	
    public void add(String entities,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {    	
	    	Map<String,Object> map = JSON.parseObject(entities);  
	    	List<Deviceattrlib> es=JSON.parseArray(map.get("Main").toString(),Deviceattrlib.class);
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
	    	Map<String,Object> map = JSON.parseObject(entities);  
	    	List<Deviceattrlib> es=JSON.parseArray(map.get("Main").toString(),Deviceattrlib.class);
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
    	try{
	    	Map<String,Object> map = JSON.parseObject(entities);  
	    	List<Deviceattrlib> es=JSON.parseArray(map.get("Main").toString(),Deviceattrlib.class);
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
    
    
	@RequestMapping("/attr")
	public void getAttr(String target,String callback,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);    
			params=data.finddevice(tar);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("getAttr", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);
		 writeJson(j,callback,response);
//		 return params;
	}
	
	
	@RequestMapping("/getDcviceByRoomId")
	public void getDcviceByRoomId(String target,String callback,HttpServletResponse response){
		Map<String, Object> params = new HashMap<String, Object>();
		DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);  
    		params=data.getDeviceByRoomId(tar);
    		j.setResponse_params(params);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("getDeviceByRoomId", e);
		}
		
    	 writeJson(j,callback,response);
		
	}
	
	@RequestMapping("/getDevice")
	public void getDevice(String target,String callback,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);    
			params=data.getDevice(tar);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("getDevice", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);
		 writeJson(j,callback,response);
//		 return params;
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
	
	@RequestMapping("/getMacroSubParam")
	public void getMacroSubParam(String json,String callback,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Map param = JSON.parseObject(json,Map.class);    
			Map mmsub = data.getMacroSubParam(Long.parseLong((String) param.get("modelId")), Long.parseLong((String) param.get("deviceId")));
			j.setResponse_params(mmsub);
		} catch (Exception e) {
			LOGGER.info("getMacroSubParam", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);
		 writeJson(j,callback,response);
	}
	
}
