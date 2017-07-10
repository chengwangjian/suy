package com.flywind.app.resources;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.flywind.app.data.MyException;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlgroup;
import com.flywind.app.data.Xmlhvac;
import com.flywind.app.data.Xmlias;
import com.flywind.app.data.Xmlmacro;
import com.flywind.app.data.Xmlmodemain;
import com.flywind.app.data.Xmlscene;
import com.flywind.app.services.Datagroup;
import com.flywind.app.services.Datahvac;
import com.flywind.app.services.Dataias;
import com.flywind.app.services.Datamacro;
import com.flywind.app.services.Datamodemain;
import com.flywind.app.services.Datascene;



@Controller
@RequestMapping("/modemacro")
public class ModemacroResource
{
    private static final Logger LOGGER = Logger.getLogger(ModemacroResource.class);

    private Datamacro data;
    public Datamacro getData() {
		return data;
	}
    @Autowired
	public void setData(Datamacro data) {
		this.data = data;
	}

    private Datagroup gdata;  
	public Datagroup getGdata() {
		return gdata;
	}
    @Autowired	
	public void setGdata(Datagroup gdata) {
		this.gdata = gdata;
	}
    private Datascene sdata;  
	public Datascene getSdata() {
		return sdata;
	}
    @Autowired	
	public void setSdata(Datascene sdata) {
		this.sdata = sdata;
	}    
    private Datamodemain mdata;
    public Datamodemain getMData() {
		return mdata;
	}
    @Autowired
	public void setMData(Datamodemain mdata) {
		this.mdata = mdata;
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
    
	@RequestMapping("/add")	
    public void add(String entities,String groups,String iashvac,String target,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	Object ob=null;
    	try {
    		Target tar=JSON.parseObject(target,Target.class);
    		if(tar.getBehavior()<1){
    			Xmlmacro es2=updatemacro(entities,groups,tar,1);
    			ob=es2;
    		}
    		else if(tar.getBehavior()>=1){//1主表添加
    			if(tar.getDest()==3){//添加mode子表
	    			Xmlmodemain es=JSON.parseObject(iashvac,Xmlmodemain.class);
    				tar.setAct("Macro");
    				tar.setMid(es.getSub().get(0).getMid());
	    			Xmlmacro es2=updatemacro(entities,groups,tar,1);
	    			ob=es2;
	    			tar.setDest(es2.getMain().get(0).getId());
	    	    	mdata.create(es.getMain(),es.getSub(),tar);      				
    			}
    			else if(tar.getDest()==2){//添加ias子表
	    	    	Xmlias es=JSON.parseObject(iashvac,Xmlias.class);
    				tar.setAct("IAS");
    				tar.setMid(es.getSub().get(0).getMid());
	    			Xmlmacro es2=updatemacro(entities,groups,tar,1);
	    			ob=es2;
	    			tar.setDest(es2.getMain().get(0).getId());
//	    			if(tar.getType()==00){
//	    				tar.setType(0L);
//	    			}
//	    			if(tar.getType()==02){
//	    				tar.setType(1L);
//	    			}
//	    			if(tar.getType()==20){
//	    				tar.setType(2L);
//	    			}
//	    			if(tar.getType()==22){
//	    				tar.setType(3L);
//	    			}
	    	    	idata.create(es.getMain(),es.getSub(),tar);    
	    		}
	    		else if(tar.getDest()==1){//添加hvac子表正向
	    			Xmlhvac es=JSON.parseObject(iashvac,Xmlhvac.class);	    			
	    			tar.setAct("HVAC");
    				tar.setMid(es.getSub().get(0).getMid());
	    			Xmlmacro es2=updatemacro(entities,groups,tar,1);
	    			ob=es2;
	    			tar.setDest(es2.getMain().get(0).getId());
	    			hdata.create(es.getMain(),es.getSub(),tar); 			
	    		}
	    		else if(tar.getDest()==0){//添加hvac子表反向
	    			Xmlhvac es=JSON.parseObject(iashvac,Xmlhvac.class);
	    			tar.setAct("HVAC2");
    				tar.setMid(es.getSub().get(0).getMid());
	    			Xmlmacro es2=updatemacro(entities,groups,tar,1);
	    			ob=es2;
	    			tar.setDest(es2.getMain().get(0).getId());
	    			hdata.update(es.getMain(),es.getSub(),tar); 			
	    		} 
    		}
   		 	LOGGER.info("tar:"+JSON.toJSONString(tar));
			j.setResponse_params(ob);    		
		} catch (MyException e) {
			if(e.getMessage().equalsIgnoreCase("GROUP_ERROR"))
				{dg.setStatus(-1);dg.setStatus_msg("GROUP_ERROR");dg.setStatus_ob(e.getStatus_ob());j.setResponse_params(dg);}
			else if(e.getMessage().equalsIgnoreCase("GROUP_NUMBER"))
				{dg.setStatus(-2);dg.setStatus_msg("GROUP_NUMBER");dg.setStatus_ob(e.getStatus_ob());j.setResponse_params(dg);}
		} catch (Exception e) {
			LOGGER.info("add",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}

		 writeJson(j,callback,response);
//		 return j;   	
    }
	
	@RequestMapping("/update")	
    public void update(String entities,String groups,String iashvac,String target,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid(); 
    	try {
    		Target tar=JSON.parseObject(target,Target.class);
    		if(tar.getBehavior()>=2){//1主表修改2批量修改
    			if(tar.getDest()==3){//修改modeid
	    			Xmlmodemain es=JSON.parseObject(iashvac,Xmlmodemain.class);    	
	    	    	mdata.create(es.getMain(),es.getSub(),tar);    				
    			}   
    			else if(tar.getDest()==2){//修改ias子表
	    	    	Xmlias es=JSON.parseObject(iashvac,Xmlias.class);    	
	    	    	idata.create(es.getMain(),es.getSub(),tar);   
    			}
    			else if(tar.getDest()==1){//修改hvac正向
	    			Xmlhvac es=JSON.parseObject(iashvac,Xmlhvac.class);
	    			hdata.create(es.getMain(),es.getSub(),tar); 
    			}
    			else if(tar.getDest()==0){//修改hvac反向
	    			Xmlhvac es=JSON.parseObject(iashvac,Xmlhvac.class);
	    			hdata.update(es.getMain(),es.getSub(),tar); 
    			}    			
    		}
	    	//0子表修改1主表修改    		
	    	Xmlmacro es2=updatemacro(entities,groups,tar,2);
	    	if(es2 == null) {
	    		dg.setStatus(-10); //表示名称已存在
	    		dg.setStatus_msg("fail");
	    		j.setResponse_params(dg);
	    	} 
	    	else { 
	    		j.setResponse_params(es2); 
	    	}
		} catch (MyException e) {
			if(e.getMessage().equalsIgnoreCase("GROUP_ERROR"))
				{dg.setStatus(-1);dg.setStatus_msg("GROUP_ERROR");dg.setStatus_ob(e.getStatus_ob());j.setResponse_params(dg);}
			else if(e.getMessage().equalsIgnoreCase("GROUP_NUMBER"))
				{dg.setStatus(-2);dg.setStatus_msg("GROUP_NUMBER");dg.setStatus_ob(e.getStatus_ob());j.setResponse_params(dg);}
		} catch (Exception e) {
			LOGGER.info("update",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 writeJson(j,callback,response);
//		 return j;   	
    }    
	public Xmlmacro updatemacro(String entities,String groups,Target tar,int flag)throws Exception{
		Xmlmacro es=JSON.parseObject(entities,Xmlmacro.class);
		Target tar2=new Target(0);
		if(groups!=null&&tar.getDesttype()==1){	
			if(flag==1)flag=3; else flag=4; 
			//当组id!=0时修改dest指向
			Xmlgroup gs=JSON.parseObject(groups,Xmlgroup.class);
			if(gs.getMain().get(0).getId()!=0) 
				tar2.setId(gs.getMain().get(0).getId());
			else {
				Xmlgroup gs2=null;//当组id=0且flag=4时取出dest指向
				tar2.setDest(es.getSub().get(0).getDest());
				if(flag==3)	gs2=gdata.create(gs.getMain(),gs.getSub());
				else if(flag==4) gs2=gdata.update(gs.getMain(),gs.getSub(),tar2);
				LOGGER.info("tar2---"+tar2+"Xmlgroup---"+JSON.toJSONString(gs2));
				if(gs2.getChecked()==0)tar2.setId(gs2.getMain().get(0).getId());
    			else if(gs2.getChecked()==1) throw new MyException("GROUP_ERROR",gs2.getSub());
    			else if(gs2.getChecked()==2) throw new MyException("GROUP_NUMBER",gs2.getSub());
			}
		}
		else if(groups!=null&&tar.getDesttype()==3){
			if(flag==1)flag=3; else flag=4; 
			//当组id!=0时修改dest指向
			Xmlscene gs=JSON.parseObject(groups,Xmlscene.class);
			if(gs.getMain().get(0).getId()!=0) 
				tar2.setId(gs.getMain().get(0).getId());
			else{
				Xmlscene gs2=null;//当组id=0且flag=4时取出dest指向
				tar2.setDest(es.getSub().get(0).getDest());
				if(flag==3)	gs2=sdata.create(gs.getMain(),gs.getSub());
				else if(flag==4) gs2=sdata.update(gs.getMain(),gs.getSub(),tar2);
				LOGGER.info("tar2---"+tar2+"Xmlscene---"+JSON.toJSONString(gs2));
				if(gs2.getChecked()==0)tar2.setId(gs2.getMain().get(0).getId());
    			else if(gs2.getChecked()==1) throw new MyException("GROUP_ERROR",gs2.getSub());
    			else if(gs2.getChecked()==2) throw new MyException("GROUP_NUMBER",gs2.getSub());
			}
		}
		Xmlmacro es2=null;
		if(flag==1)		es2=data.create(es.getMain(),es.getSub(),tar);
		else if(flag==2) es2=data.update(es.getMain(),es.getSub(),tar);
		else if(flag==3) es2=data.create(es.getMain(),es.getSub(),tar2);		
		else if(flag==4) es2=data.update(es.getMain(),es.getSub(),tar2);
		LOGGER.info("tar---"+tar+"Xmlmacro---"+JSON.toJSONString(es2));
		return es2;
	}

	@RequestMapping("/delete")	
    public void delete(String entities,String groups,String iashvac,String target,String callback,HttpServletResponse response){
    	Json j = new Json();DataGrid dg=new DataGrid();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);
    		Xmlmacro es2=JSON.parseObject(entities,Xmlmacro.class);	    		
	    	if(tar.getBehavior()>=1){//1删除执行2取消执行
    			if(tar.getDest()==3){//删除mode子表
	    			Xmlmodemain es=JSON.parseObject(iashvac,Xmlmodemain.class); 
	    	    	mdata.delete(es.getMain(),es.getSub());       				
    			}
    			else if(tar.getDest()==2){//删除ias子表
	    	    	Xmlias es=JSON.parseObject(iashvac,Xmlias.class); 
	    	    	idata.delete(es.getMain(),es.getSub()); 	    	    	
	    		}
	    		else if(tar.getDest()==1){//删除hvac子表正向
	    			Xmlhvac es=JSON.parseObject(iashvac,Xmlhvac.class);			
	    			hdata.delete(es.getMain(),es.getSub()); 	
	    		}
	    		else if(tar.getDest()==0){//删除hvac子表反向
	    			Xmlhvac es=JSON.parseObject(iashvac,Xmlhvac.class);
	    			hdata.update(es.getMain(),es.getSub(),tar);    			
	    		}    		
	    	}
	    	if(tar.getBehavior()<=1){//0子表删除1主表删除
	    		data.delete(es2.getMain(),es2.getSub());	    		
	    	}
			j.setResponse_params(dg); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+entities);  
		 writeJson(j,callback,response);
//		 return j;   	
    }  
    

	@RequestMapping("/macro")
	public void getMacro(String target,String callback,HttpServletResponse response) {
    	Object params = null;//= new Xmlmacro();
    	DataGrid dg=new DataGrid();
    	Json j = new Json();
    	try {
    		Target tar=JSON.parseObject(target,Target.class);    
			params=data.findmode(tar);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("getMacro",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);
		 writeJson(j,callback,response);
//		 return params;
	}
	
	public void writeJson(Object object,String callback,HttpServletResponse response) {
		try {
			String jstr=callback+"("+JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect)+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson",e);
		}
	}	
	
	@RequestMapping("/deleteByMid")
	public void deleteByMid(String json,HttpServletResponse response,HttpServletRequest request){
		PrintWriter out=null;
		try {
			out=response.getWriter(); 
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String callback = request.getParameter("callback");
			int mid = Integer.parseInt(paramMap.get("mid").toString());
			int i = data.deleteByMid(mid);
			String resultJson = "{\"result\":1}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("deleteByMid",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	
	@RequestMapping("/deleteActList")
	public void deleteActList(String json,HttpServletResponse response,HttpServletRequest request) {
		PrintWriter out=null;
		try {
			out=response.getWriter(); 
			Map<String, Object> paramMap = JSON.parseObject(json, Map.class);
			String callback = request.getParameter("callback");
			int i = data.deleteActList(paramMap);
			String resultJson = "{\"result\":1}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("deleteActList",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
}
