package com.flywind.app.resources;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.flywind.app.data.DataGrid;
import com.flywind.app.data.Json;
import com.flywind.app.data.Xmldevicebind;
import com.flywind.app.services.Databind;



@Controller
@RequestMapping("/devicebind")
public class DevicebindResource
{
    private static final Logger LOGGER = Logger.getLogger(DevicebindResource.class);
    private Databind data;
    public Databind getData() {
		return data;
	}
    @Autowired
	public void setData(Databind data) {
		this.data = data;
	}
    
	@RequestMapping("/add")	
    public Json add(String entities){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try { 
    		Xmldevicebind es=JSON.parseObject(entities,Xmldevicebind.class);
	    	data.create(es.getMain());
	    	j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("add", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);
		 return j;   	
    }

    
	@RequestMapping("/update")	
    public Json update(String entities){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {    	  
	    	Xmldevicebind es=JSON.parseObject(entities,Xmldevicebind.class);
    		data.update(es.getMain());
	    	j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("update", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);
		 return j;   	
    }    
 
    
	@RequestMapping("/delete")	
    public Json delete(String entities){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {    	 
    		Xmldevicebind es=JSON.parseObject(entities,Xmldevicebind.class);
	   		data.delete(es.getMain());
	    	j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("delete", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);    	
		 return j;   	
    }  
    
    
	@RequestMapping("/devicebind")
	public Xmldevicebind getBind(long houseId) {
    	Xmldevicebind params = new Xmldevicebind();
    	DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
			params=data.findhouse(houseId);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("Xmldevicebind", e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);
		 return params;
	}
	
	
	
}
