package com.flywind.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlscheme;
import com.flywind.app.entities.Modemacrosub;
import com.flywind.app.entities.Modeschememain;
import com.flywind.app.entities.Modeschemesub;
import com.flywind.app.entities.Usermodesub;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dscheme")
public class Datascheme
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Datascheme.class);

	private StartDAO dao;
    public StartDAO getDao() {
		return dao;
	}
    @Autowired
	public void setDao(StartDAO dao) {
		this.dao = dao;
	} 

    public void initialize()
    {
        
        List<Modemacrosub> macrosubs = new ArrayList<Modemacrosub>();
        List<Modeschememain> schemes = new ArrayList<Modeschememain>();
        List<Modeschemesub> schemesubs = new ArrayList<Modeschemesub>();
//        modes.add(new Moderoom(111, "111", "111"));
//        modes.add(new Moderoom(222, "222", "222"));
//        schemes.add(new Modeschememain(1,1, "aaa", "aaa", (short)1));
//        schemes.add(new Modeschememain(2,1, "bbb", "bbb", (short)0));
//        schemesubs.add(new Modeschemesub(1,2, (short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1, null,"yyy",111));
//        schemesubs.add(new Modeschemesub(2,3, (short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0, null,"zzz",222));
        xmlscheme=new Xmlscheme();
        xmlscheme.setMain(schemes);
        xmlscheme.setSub(schemesubs);       
               
//        LOGGER.info("-- Loading initial demo data");
//        rooms=dao.findWithNamedQuery(Moderoom.BY_houseId,
//				QueryParameters.with("houseId", 111L).parameters());
        
//      create(modes);
//      create(schemes);   
//      create(schemesubs);       
        LOGGER.info("Users " + schemes.toString());
        LOGGER.info("Users " + schemesubs.toString());
        LOGGER.info("-- Data Loaded. Exit");
        
    }
    
    public Xmlscheme xmlscheme=new Xmlscheme();
    public Xmlscheme create(List<?> main,List<?> sub)
    {
        List schemes = new ArrayList();
        List schemesubs = new ArrayList();
    	for (Object e : main)
        {
    		Modeschememain e1 = (Modeschememain)e;
    		schemes.add(dao.create(e1));   		
    		for(Object u :sub){
    			Modeschemesub u1 = (Modeschemesub)u;
    			u1.setMid(e1.getId());    			
    			schemesubs.add(dao.create(u1));
        	}    		
        }
        xmlscheme.setMain(schemes);
        xmlscheme.setSub(schemesubs); 
        return xmlscheme;
    }
    public Xmlscheme update(List<?> main,List<?> sub)
    {
        List schemes = new ArrayList();
        List schemesubs = new ArrayList();
    	for (Object e : main)
        {
    		Modeschememain shc = (Modeschememain) e;
    		if(shc.getBallmodeActive() == 1) {
    			String delUMS = "delete from usermodesub where dest=:dest";
    			Map<String, Object> params = new HashMap<String, Object>();
    			params.put("dest", shc.getId());
    			dao.executeHql(delUMS, params);
    		}
        	schemes.add(dao.update(e));
        }
        for (Object e : sub)
        {
        	schemesubs.add(dao.update(e));
        	
        }
        xmlscheme.setMain(schemes);
        xmlscheme.setSub(schemesubs);  
        return xmlscheme;          
    }
    public void delete(List<?> main,List<?> sub) {  
    	String delUserModeSubHql = "delete from usermodesub where dest=:schemeMainId";
    	Map<String, Object> params = new HashMap<String, Object>();
    	for (Object e : main)
        {
    		params.put("schemeMainId", ((Modeschememain) e).getId());
    		dao.executeHql(delUserModeSubHql, params);
    		dao.delete(e);
        }  
    	for (Object u : sub)
        {
    		Modeschemesub u1 = (Modeschemesub)u;
    		u1=dao.get(Modeschemesub.class, u1.getId());
    		dao.delete(u1);
//    		deletecheck(u1);
        }                
    	LOGGER.info("2222 " + sub.size());
    }
    public Object findmacro(Target tar){
    	
    	String act1 = "";
    	String act2 = "";
    	List<Modeschemesub> subsList=null;
    	List<Modeschememain> schemeList=null;
    	List<Usermodesub> modeList = null;
    	if(tar.getBehavior()==1){//按macro查
        	if (tar.getAct().equals("hvac")) {
        		act1 = "ActiveHVAC";
        		act2 = "DeactiveHVAC";
        	} else if (tar.getAct().equals("ias")) {
        		act1 = "ActiveIAS";
        		act2 = "DeactiveIAS";
        	} else {
        		act1 = tar.getAct();
        		act2 = tar.getAct();
        	}    		
    		subsList = dao.find(NamedQueries.Modeschemesub_macroId,
					QueryParameters.with("macroId", tar.getMacroId()).and("act1", act1).and("act2", act2).parameters());
    		schemeList = dao.find(NamedQueries.Modeschememain_macroId,
    				QueryParameters.with("macroId", tar.getMacroId()).and("act1", act1).and("act2", act2).parameters());
    	}else if(tar.getBehavior()==2){//按子表查
	    	subsList = dao.executeSql("{CALL Modescheme(1 ,0,0,:id)}",
					QueryParameters.with("id", tar.getId()).parameters(),Modeschemesub.class);
	    	schemeList = dao.executeSql("{CALL Modescheme(2 ,0,0,:id)}",
					QueryParameters.with("id", tar.getId()).parameters(),Modeschememain.class);
    		modeList= dao.executeSql("{CALL Modescheme(3 ,0,0,:id)}",
					QueryParameters.with("id", tar.getId()).parameters());    		
    	}

    	Map<String, Object> schemeMap = new HashMap<String, Object>();

		schemeMap.put("sub", subsList);
		schemeMap.put("main", schemeList);
		schemeMap.put("mode", modeList);    		
  	    
		LOGGER.info("1111 " + JSON.toJSONString(schemeMap));   	
    	return schemeMap;
    }   
    public int deletecheck(Modeschemesub u1){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("dest", u1.getDest());//scheme子表destid
    	params.put("houseId", u1.getHouseId());    	
    	params.put("id", u1.getMid());
		return 0;  	
    }
    public int updatecheck(List<?> main,List<?> sub,Target tar){
    	Map<String, Object> params = new HashMap<String, Object>();
    	String mid="";int count=0;	    		
		params.put("dest", tar.getDest());//scheme主表id
		params.put("houseId", tar.getHouseId());
				
    	for (Object u : sub) { 
    		Usermodesub u1=(Usermodesub)u;
    		if(u1.getSelectss()==0)mid+=u1.getMid()+",";
    	}	
    	if((count=mid.length())>0){
    		mid=mid.substring(0,mid.length()-1);
    	}
    	mid=mid+";";//0禁用
    	for (Object u : sub) { 
    		Usermodesub u1=(Usermodesub)u;
    		if(u1.getSelectss()==1)mid+=u1.getMid()+",";
    	}
    	if((count=mid.length())>1){  	
    		mid=mid.substring(0,mid.length()-1);
    	}
    	mid=mid+";";//1启用
    	for (Object u : sub) { 
    		Usermodesub u1=(Usermodesub)u;
    		if(u1.getSelectss()==2)mid+=u1.getMid()+",";
    	}
    	if((count=mid.length())>2){
    		mid=mid.substring(0,mid.length()-1);
    	}
    	mid=mid+";";//2忽略
    	params.put("mid", mid);
    	LOGGER.info("---count:"+count+"---act---"+tar.getAct()+"---"+tar.getBehavior()
    			+"---houseId---"+tar.getHouseId()+"---dest---"+tar.getDest()+"---mid---"+mid);
   		count=dao.executeHql("{CALL ModesubTest(3 ,:houseId,:dest ,:mid)}", params);  		
    	return 0;
    }    
}
