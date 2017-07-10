package com.flywind.app.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.flywind.app.data.DataGrid;
import com.flywind.app.data.Json;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlscene;
import com.flywind.app.services.Datascene;



@Controller
@RequestMapping("/modescene")
public class ModesceneResource
{
    private static final Logger LOGGER = Logger.getLogger(ModesceneResource.class);

    private Datascene data;
    public Datascene getData() {
		return data;
	}
    @Autowired
	public void setData(Datascene data) {
		this.data = data;
	}
    
	@RequestMapping("/add")	
    public void add(String entities,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {
	    	Xmlscene es=JSON.parseObject(entities,Xmlscene.class);
	    	Xmlscene es2=data.create(es.getMain(),es.getSub());
			j.setResponse_params(es2); 
		} catch (Exception e) {
			LOGGER.info("add",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);
		 writeJson(j,callback,response);
//		 return j;   	
    }

    
	@RequestMapping("/update")	
    public void update(String entities,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {
	    	Xmlscene es=JSON.parseObject(entities,Xmlscene.class);
	    	Xmlscene es2=data.create(es.getMain(),es.getSub());
			j.setResponse_params(es2); 
		} catch (Exception e) {
			LOGGER.info("update",e);
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
	    	Xmlscene es=JSON.parseObject(entities,Xmlscene.class);
	    	data.delete(es.getMain(),es.getSub());
			j.setResponse_params(dg); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);    
		 writeJson(j,callback,response);
//		 return j;   	
    }  
    
    
	@RequestMapping("/scene")
	public void getScene(String target,String callback,HttpServletResponse response) {
		Object params = null; //new Xmlscene();
    	DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);      		
			params=data.findmacro(tar);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("getScene",e);
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
			LOGGER.info("writeJson",e);
		}
	}	
	
}
