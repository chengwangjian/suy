package com.flywind.app.resources;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.flywind.app.data.DataGrid;
import com.flywind.app.data.Json;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlhvac;
import com.flywind.app.entities.Modehvacmain;
import com.flywind.app.services.Datahvac;
import com.flywind.app.services.Datamodemain;



@Controller
@RequestMapping("/modehvac")
public class ModehvacResource
{
    private static final Logger LOGGER = Logger.getLogger(ModehvacResource.class);

    private Datahvac data;
    public Datahvac getData() {
		return data;
	}
    @Autowired
	public void setData(Datahvac data) {
		this.data = data;
	}

    private Datamodemain mdata;
	public Datamodemain getMdata() {
		return mdata;
	}
    @Autowired
	public void setMdata(Datamodemain mdata) {
		this.mdata = mdata;
	}
    
	@RequestMapping("/add")	
    public void add(String entities,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {
	    	Xmlhvac es=JSON.parseObject(entities,Xmlhvac.class);    	
	    	Xmlhvac es2=data.create(es.getMain(),es.getSub(),null);     	
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
	    	Xmlhvac es=JSON.parseObject(entities,Xmlhvac.class);
	    	Xmlhvac es2=data.update(es.getMain(),es.getSub(),null);     	
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
	    	Xmlhvac es=JSON.parseObject(entities,Xmlhvac.class);
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
    
    
	@RequestMapping("/hvac")
	public void getHvac(String target,String callback,HttpServletResponse response) {
		Object params = null;//new Xmlhvac();
    	Json j = new Json();
    	DataGrid dg=new DataGrid();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);
			params=data.findmacro(tar);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("getHvac",e);
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
  //返回条件
	@RequestMapping("/getCondition")
  public void getCondition(Long mainID,String callback,HttpServletResponse response)
  {
	  Json j = new Json();
	  List<Modehvacmain> modehvacmin=null;
	  Map<String,Object> Condi1and=new HashMap<String, Object>();
	   double temminValues=0 ;
	   double temmaxValues=0;
	   double humminValues=0;
	   double hummaxValues=0;
	  try {
		    modehvacmin=data.getCondition(mainID);
		    if (modehvacmin.size()==2) {
		    	 //温度
			    if (modehvacmin.get(0).getAttrId().equals("0000")&&modehvacmin.get(0).getClusterId().equals("0402"))
			    {
				
			    	temmaxValues=modehvacmin.get(0).getMaxValues();
					temminValues=modehvacmin.get(0).getMaxValues();
				}
			    //湿度
			    if (modehvacmin.get(0).getAttrId().equals("0000")&&modehvacmin.get(0).getClusterId().equals("0405")) {
					hummaxValues=modehvacmin.get(0).getMaxValues();
					humminValues=modehvacmin.get(0).getMaxValues();
				}
			    //温度
			    if (modehvacmin.get(0).getAttrId().equals("0000")&&modehvacmin.get(1).getClusterId().equals("0402"))
			    {
				
			    	temmaxValues=modehvacmin.get(0).getMaxValues();
					temminValues=modehvacmin.get(0).getMaxValues();
				}
			    //湿度
			    if (modehvacmin.get(0).getAttrId().equals("0000")&&modehvacmin.get(1).getClusterId().equals("0405")) {
					hummaxValues=modehvacmin.get(0).getMaxValues();
					humminValues=modehvacmin.get(0).getMaxValues();
				}
			}
		 // List<String> liscond=new ArrayList<String>();
		
		  //条件一和条件二是与的逻辑；
		  if(modehvacmin.size()==2&&modehvacmin.get(0).getClause()==1)
		  {
			  Condi1and.put("0", "温度大于 "+temmaxValues+"  湿度大于 "+humminValues);
			  Condi1and.put("1", "温度大于"+temmaxValues+"湿度在"+humminValues+"到"+hummaxValues+"的范围内");
			  Condi1and.put("2", "温度大于"+temmaxValues+"湿度小于"+humminValues);
			  Condi1and.put("3", "温度在"+temmaxValues+"到"+temminValues+"范围内，湿度大于"+hummaxValues);
			  Condi1and.put("4", "温度在"+temmaxValues+"到"+temminValues+"范围内，湿度在"+humminValues+"到"+hummaxValues+"范围内");
			  Condi1and.put("5", "温度在"+temmaxValues+"到"+temminValues+"范围内，湿度小于"+humminValues);
			  Condi1and.put("6", "温度小于"+temmaxValues+"，湿度大于"+hummaxValues);
			  Condi1and.put("7", "温度小于"+temmaxValues+"，湿度在"+humminValues+"到"+hummaxValues+"范围内");
			  Condi1and.put("8", "温度小于"+temmaxValues+"，湿度小于"+humminValues);
		  }
		  //2、或的逻辑：
		  //Map<String,Object> Condior=new HashMap<String,Object>();
		  if(modehvacmin.size()==2&&modehvacmin.get(0).getClause()==0)
		  {
			  Condi1and.put("0", "温度大于"+temmaxValues);
			  Condi1and.put("1", "温度在"+temminValues+"到"+temmaxValues+"的范围内");
			  Condi1and.put("2", "温度小于"+temminValues);
			  Condi1and.put("3", "湿度大于"+hummaxValues);
			  Condi1and.put("4", "湿度在"+humminValues+"-"+hummaxValues+"的范围内");
			  Condi1and.put("5", "湿度小于"+humminValues);
		  }
		  j.setResponse_params(Condi1and);
	} catch (Exception e) {
		// TODO: handle exception
	}
	  LOGGER.info("----------"+modehvacmin);
		 writeJson(j,callback,response);
  }
	@RequestMapping("/deleteguizhe")	
    public void deleteguizhe(long id,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {
	    	data.deleteguizhe(id);
			j.setResponse_params(dg); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+id);   
		 writeJson(j,callback,response);
//		 return j;   	
    }  
	//http://localhost:8081/zigBeeDevice/modehvac/twocondit.do?id=64
	@RequestMapping("/twocondit")
	public void twocondit(long id,String callback,HttpServletResponse response)
	{
		Json j = new Json();DataGrid dg=new DataGrid();
    	try {
	    	data.twocondit(id);
			j.setResponse_params(dg); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+id);   
		 writeJson(j,callback,response);
//		 return j;   	
	}
	@RequestMapping("/deleteguizheias")	
    public void deleteguizheias(long id,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {
	    	data.deleteguizheias(id);
			j.setResponse_params(dg); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+id);   
		 writeJson(j,callback,response);
//		 return j;   	
    }  
	@RequestMapping("/showhvac")
	public void showhvac(String json, HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");  
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		String houseid = paramMap.get("houseId").toString();
		String callback = request.getParameter("callback");
		List<Map> t = data.showhvac(houseid);
			try{
				String resultJson = "{\"result\":1}";//成功1 失败0
			if(t==null){
				resultJson = "{\"result\":1,\"dCList\":[]}";
			}
			else{
				String dCListStr = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
				resultJson = "{\"result\":1,\"dCList\":" + dCListStr + "}";
			}
			out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("showhvac",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	
	@RequestMapping("/showias")
	public void showias(String json, HttpServletRequest request,HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");  
		Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
		String houseid = paramMap.get("houseId").toString();
		String callback = request.getParameter("callback");
		List<Map> t = data.showias(houseid);
			try{
				String resultJson = "{\"result\":1}";//成功1 失败0
			if(t==null){
				resultJson = "{\"result\":1,\"dCList\":[]}";
			}
			else{
				String dCListStr = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
				resultJson = "{\"result\":1,\"dCList\":" + dCListStr + "}";
			}
			out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("showias",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback+"({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
}
