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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sy.model.ModeCommand;
import com.alibaba.fastjson.JSON;
import com.flywind.app.data.DataGrid;
import com.flywind.app.data.Json;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlhvac;
import com.flywind.app.data.Xmlias;
import com.flywind.app.data.Xmlmodemain;
import com.flywind.app.services.Datahvac;
import com.flywind.app.services.Dataias;
import com.flywind.app.services.Datamodemain;




@Controller
@RequestMapping("/modemain")
public class ModemainResource
{
    private static final Logger LOGGER = Logger.getLogger(ModemainResource.class);

    private Datamodemain data;
    public Datamodemain getData() {
		return data;
	}
    @Autowired
	public void setData(Datamodemain data) {
		this.data = data;
	}
    
    private Dataias idata;
    public Dataias getIData() {
		return idata;
	}
    @Autowired
	public void setIData(Dataias idata) {
		this.idata = idata;
	}    
 
    private Datahvac hdata;
    public Datahvac getHData() {
		return hdata;
	}
    @Autowired
	public void setHData(Datahvac hdata) {
		this.hdata = hdata;
	}
    
    @RequestMapping("/editMode")	
    public @ResponseBody HashMap<String, Object> editSaveMode(String callback,String entities ,HttpServletResponse response){
    	HashMap<String, Object>result=new HashMap<String, Object>();
    	try {
    		ModeCommand command=JSON.parseObject(entities,ModeCommand.class);
    		Integer size=data.findModeByName(command.getModename(),command.getHouseid(),command.getModeid());
        	if(size!=null){
        		result.put("result","0");
        		   }else{
    		data.update(command);
    		result.put("result","2");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result","3");
			
		}
	return result;
    } 
    
    
    @RequestMapping("/addMode")	
    public @ResponseBody HashMap<String, Object> addMode(@RequestParam("entities")String entities,HttpServletResponse response){
    	HashMap<String, Object>result=new HashMap<String, Object>();
    	try {
    	ModeCommand command=JSON.parseObject(entities,ModeCommand.class);
    	Integer size=data.findModeByName(command.getModename(),command.getHouseid());
    	if(size!=null){
    		result.put("result","0");
    		   }else{
    		Long mid=data.create(command);
    		result.put("result","1");
    		result.put("mid", mid);  
		}
    	} catch (Exception e) {
			e.printStackTrace();
			result.put("result","-1");
			
		}
	return result;
    }
    
	@RequestMapping("/add")	
    public void add(String entities,String iashvac,String target,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	Object ob=null;
    	try { 
    		Target tar=JSON.parseObject(target,Target.class);
	    	Xmlmodemain ms=JSON.parseObject(entities,Xmlmodemain.class);
    		if(tar.getBehavior()>=1){//1主表添加2批量添加
	    		if(tar.getDest()==2){//添加ias主表
	    	    	Xmlias es=JSON.parseObject(iashvac,Xmlias.class);    	
	    	    	Xmlias es2=idata.create(es.getMain(),es.getSub(),null); 
	    	    	if(es2.getChecked()==0){
	    	    	tar.setAct("ias");
//	    	    	tar.setDest(es2.getMain().get(0).getMainId());
	    	    	tar.setDest(es2.getMain().get(0).getId());
	    	    	tar.setHouseId(es2.getMain().get(0).getHouseId());
	    	    	ob=es2;
	    	    	
	    	    	}
	    	    	else throw new Exception("IAS_EXIT");
	    		}
	    		else if(tar.getDest()==1){//添加hvac主表
	    			Xmlhvac es=JSON.parseObject(iashvac,Xmlhvac.class);
	    			Xmlhvac es2=hdata.create(es.getMain(),es.getSub(),null); 
	    	    	if(es2.getChecked()==0){   			
	    	    	tar.setAct("hvac");	    			
	    	    	tar.setDest(es2.getMain().get(0).getMainId());
	    	    	tar.setHouseId(es2.getMain().get(0).getHouseId());
//	    	    	tar.setMainId(es2.getMain().get(0).getMainId());
	    	    	ob=es2;
	    	    	}
	    	    	else throw new Exception("HVAC_EXIT");	    	    	
	    		}	
    			LOGGER.info("tar-----"+tar);	    		
	    		if(tar.getBehavior()==2)//2批量添加
    			data.updatecheck(ms.getMain(),ms.getSub(), tar);	    		
    		}
    		else//0主子表添加
    			ob=data.create(ms.getMain(),ms.getSub(),null);
    		j.setResponse_params(ob);
		} catch (Exception e) {
			if(e.getMessage().equalsIgnoreCase("IAS_EXIT"))
				{dg.setStatus(-1);dg.setStatus_msg("IAS_EXIT");j.setResponse_params(dg);}
			else if(e.getMessage().equalsIgnoreCase("HVAC_EXIT"))
				{dg.setStatus(-1);dg.setStatus_msg("HVAC_EXIT");j.setResponse_params(dg);}			
			else{
				LOGGER.info("add",e);
				dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
			}
		}
		 LOGGER.info("----------"+j.getResponse_params());
		 writeJson(j,callback,response);
//		 return j;   	
    }
	
	

    
	@RequestMapping("/update")	
    public void update(String entities,String iashvac,String target,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	Object ob=null;
    	try {   
    		Target tar=JSON.parseObject(target,Target.class);
	    	Xmlmodemain ms=JSON.parseObject(entities,Xmlmodemain.class);    		
    		if(tar.getBehavior()>=1){//1主表修改2批量修改
	    		if(tar.getDest()==2){//修改ias主表
	    	    	Xmlias es=JSON.parseObject(iashvac,Xmlias.class);    	
	    	    	Xmlias es2=idata.update(es.getMain(),es.getSub(),null); 
	    	    	tar.setAct("ias");
	    	    	tar.setDest(es.getMain().get(0).getMainId());
	    	    	tar.setHouseId(es.getMain().get(0).getHouseId());
	    	    	ob=es2;	    	    	
	    		}
	    		else if(tar.getDest()==1){//修改hvac主表
	    			Xmlhvac es=JSON.parseObject(iashvac,Xmlhvac.class);
	    			Xmlhvac es2=hdata.update(es.getMain(),es.getSub(),null); 	
	    	    	tar.setAct("hvac");	    			
	    	    	tar.setDest(es.getMain().get(0).getMainId());
	    	    	tar.setHouseId(es.getMain().get(0).getHouseId());	
	    	    	ob=es2;	    	    	
	    		}
    			LOGGER.info("tar-----"+tar);
	    		if(tar.getBehavior()==2)//2批量修改		
		    	data.updatecheck(ms.getMain(),ms.getSub(), tar);
    		}
    		else//0主子表修改
    			ob=data.update(ms.getMain(),ms.getSub());
    		j.setResponse_params(ob);
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
	    	Xmlmodemain es=JSON.parseObject(entities,Xmlmodemain.class);
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
	
    
	@RequestMapping("/mode")
	public void getMode(String target,String callback,HttpServletResponse response) {
    	Object params=null;DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);    		
    		params=data.findroom(tar);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("mode",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);		
		 writeJson(j,callback,response);
	}
	
	@RequestMapping("/updatedevice")
	public void ModeDevice(String target,String callback,HttpServletResponse response) {
		DataGrid dg=new DataGrid();
		Json j = new Json();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);   
    		data.updatecheck2(tar);
    		j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("ModeDevice",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}   
		 LOGGER.info("----------"); 
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
			LOGGER.info("writeJson",e);
		}
	}
	
	@RequestMapping("/modes")
	public void getModes(String json,String callback,HttpServletResponse response) {
    	Object params=null;DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Map param = JSON.parseObject(json, Map.class);    		
    		params=data.getDeviceList((String) param.get("houseId"));
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("modes",e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		LOGGER.info("----------"+params);		
		writeJson(j,callback,response);
	}
	@RequestMapping("/edit")
	public void edit(String json,String callback,HttpServletResponse response) {
    	HashMap<String,Object>params=new HashMap<String, Object>();
    	
    	DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Map paraMap=JSON.parseObject(json,Map.class); 
    		List<Map>modeList=data.findModeById(paraMap.get("modeId").toString());
    		List<Map> destList=data.findDestByMid(paraMap.get("modeId").toString());
    		//List<Map>actList=data.findActIdByMid(paraMap.get("modeId").toString());

    		//params=data.findroom(tar);
    		params.put("modeList",modeList);
    		params.put("destList", destList);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("mode",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);		
		 writeJson(j,callback,response);
	}

    @RequestMapping("/getmoderoomid")
	public void getmoderoomid(String json,String callback,HttpServletResponse response) {
    	HashMap<String,Object>params=new HashMap<String, Object>();
    	
    	DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Map paraMap=JSON.parseObject(json,Map.class); 
    		List<Map>modeList=data.findModeById(paraMap.get("modeId").toString());
        		params.put("modeList",modeList);
   			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("mode",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);		
		 writeJson(j,callback,response);
	}
	
//	@RequestMapping("/getDAction")
//	public void getDAction(String json,String callback,HttpServletResponse response) {
//    	Object params=null;DataGrid dg=new DataGrid();
//    	Json j = new Json();
//    	try {
//    		Map param = JSON.parseObject(json, Map.class);    		
//    		params=data.getDeviceAction((String) param.get("deviceId"));
//			j.setResponse_params(params);
//		} catch (Exception e) {
//			LOGGER.info("modes",e);
//			dg.setStatus(0);
//			dg.setStatus_msg("fail");
//			j.setResponse_params(dg);
//		}
//		LOGGER.info("----------"+params);		
//		writeJson(j,callback,response);
//	}
}
