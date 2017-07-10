package com.flywind.app.services;

import java.math.BigInteger;
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
import com.flywind.app.data.Xmlscene;
import com.flywind.app.entities.Modeactlib;
import com.flywind.app.entities.Modescenemain;
import com.flywind.app.entities.Modescenesub;
import com.flywind.app.entities.Usermodemain;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dscene")
public class Datascene
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Datascene.class);

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
        
        List<Usermodemain> modes = new ArrayList<Usermodemain>();
//        List<Modegroupmain> groups = new ArrayList<Modegroupmain>();
//        List<Modegroupsub> groupsubs = new ArrayList<Modegroupsub>();
//        modes.add(new Moderoom(111, "111", "111"));
//        modes.add(new Moderoom(222, "222", "222"));
//        micros.add(new Usermodemain(17, "aaa", "aaa", "aaa"));
//        micros.add(new Usermodemain(18, "bbb", "bbb", "bbb"));
//        microsubs.add(new Usermodesub(1, "zzz", 111));
//        microsubs.add(new Usermodesub(2, "yyy", 222)); 

        
        LOGGER.info("-- Loading initial demo data");
//        rooms=dao.findWithNamedQuery(Moderoom.BY_houseId,
//				QueryParameters.with("houseId", 111L).parameters());
//        delete(rooms);
        
//      create(modes);
//      create(micros);   
//      create(microsubs);        
//        LOGGER.info("Users " + groups.toString());
//        LOGGER.info("Users " + groupsubs.toString());
        LOGGER.info("-- Data Loaded. Exit");
        
    }

    public Xmlscene xmlscene=new Xmlscene();
    public Xmlscene create(List<?> main,List<?> sub)
    {
        List scenes = new ArrayList();
        List scenesubs = new ArrayList();
    	for (Object e : main)
        {
    		Modescenemain e1=(Modescenemain)e;  		
    		List ls=updatecheck2(sub);
    		if(ls.size()>0){xmlscene.setChecked(2);xmlscene.setSub(ls);}
    		else if(updatecheck(e1)>0)xmlscene.setChecked(1);
    		else{
    			xmlscene.setChecked(0);
    			scenes.add(dao.create(e1));
    			for(Object u :sub){
	    			Modescenesub u1=(Modescenesub)u;
	    			u1.setMid(e1.getId());
	    			scenesubs.add(dao.create(u)); 
    			}
    	    	xmlscene.setMain(scenes);
    	    	xmlscene.setSub(scenesubs);    			
        	}    		
        }  
        return xmlscene;   	
    }
    public Xmlscene update(List<?> main,List<?> sub,Target tar)
    {
        List scenes = new ArrayList();
        List scenesubs = new ArrayList();
    	for (Object e : main)
        {
    		Modescenemain e1=(Modescenemain)e;
    		if(tar.getDest()!=null)e1.setId(tar.getDest());
    		scenes.add(dao.update(e1));
    		deletecheck(e1,1);//修改主表删除子表
    		List ls=updatecheck2(sub);
    		if(ls.size()>0){xmlscene.setChecked(2);xmlscene.setSub(ls);} 
    		else{
    			xmlscene.setChecked(0);      			
	    		for(Object u :sub){
	    			Modescenesub u1=(Modescenesub)u;
	    			u1.setMid(e1.getId());
	    			scenesubs.add(dao.create(u));       	        
	        	} 
	        	xmlscene.setMain(scenes);
	        	xmlscene.setSub(scenesubs);	    		
    		}
        }  
        return xmlscene;   	              
    }
    public void delete(List<?> main,List<?> sub) {  
    	for (Object e : main)
        {
    		Modescenemain e1=(Modescenemain)e;
//    		dao.delete(e);
    		deletecheck(e1,2);     		
        }                
    	for (Object u : sub)
        {
//			Modescenesub u1=(Modescenesub)u;    		
    		dao.delete(u);
//    		deletecheck(e1,1);    		
        }
    	LOGGER.info("2222 " + sub.size());
    }

    public Object findmacro(Target tar){   	
    	List<Modescenesub> subsList = null;
    	List<Modescenemain> sceneList = null;
    	List<Modeactlib> actList=null;
    	if(tar.getBehavior()==9){//按houseid查main
	    	sceneList = dao.executeSql("{CALL ModemacrosubTest(9 ,:Id,:houseId)}",
					QueryParameters.with("Id",tar.getId()).and("houseId", tar.getHouseId()).parameters());
    	}else if(tar.getBehavior()==10){//按mainid查sub
    		subsList=dao.executeSql("{CALL ModemacrosubTest(10 ,:Id,:houseId)}",
					QueryParameters.with("Id",tar.getId()).and("houseId", tar.getHouseId()).parameters());
    	}else if(tar.getBehavior()==11){//按deviceid查act
	    	actList = dao.executeSql("{CALL ModemacrosubTest(11 ,:Id,:houseId)}",
	    			QueryParameters.with("Id", tar.getId()).and("houseId", tar.getHouseId()).parameters(),Modeactlib.class); 
    	}else if(tar.getBehavior()==12){//按houseid查device
    		subsList=dao.executeSql("{CALL ModemacrosubTest(12 ,:Id,:houseId)}",
					QueryParameters.with("Id",tar.getId()).and("houseId", tar.getHouseId()).parameters());
    	}
        Map<String, Object> groupMap = new HashMap<String, Object>();
    	groupMap.put("sub", subsList);
		groupMap.put("main", sceneList);  
		groupMap.put("act", actList); 
        LOGGER.info("1111 groupSCENE:" + JSON.toJSONString(groupMap));
    	return groupMap;
    }
    
    public int deletecheck(Modescenemain e1,int i){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("houseId", e1.getHouseId());    	
    	params.put("id", e1.getId());
    	int count=0; 
    	if(i==1)
    		count=dao.executeHql("{CALL ModedeleteTest(5,:houseId,:id)}",params);
	    else if(i==2)
    		count=dao.executeHql("{CALL ModedeleteTest(6,:houseId,:id)}",params);	    	
    	LOGGER.info("i=="+i+"scenedelete----"+count+"----"+JSON.toJSONString(params));
    	return 0;      	
    }
    public int updatecheck(Modescenemain e1){
		List<Map<String,BigInteger>> aggre=dao.executeSql("select count(*) as Count from Modescenemain m where m.houseId=:houseId and m.groupName=:groupName", 
				QueryParameters.with("houseId", e1.getHouseId()).and("groupName", e1.getGroupName()).parameters());
		LOGGER.info("scenename--count:"+JSON.toJSONString(aggre));
		return aggre.get(0).get("Count").intValue();
    }
    public List updatecheck2(List<?> sub){
    	Map<String, Object> params = new HashMap<String, Object>();
    	String mid="";		
		params.put("dest", -1);
		params.put("houseId", ((Modescenesub)sub.get(0)).getHouseId());

    	for (Object u : sub) { 
    		Modescenesub u1=(Modescenesub)u;
    		mid+=u1.getDeviceId()+",";
    	}	
    	mid=mid.substring(0,mid.length()-1);
    	params.put("mid", mid);	
    	LOGGER.info("---houseId---"+params.get("houseId")+"---mid---"+mid);    	
    	List aggre=dao.executeSql("{CALL ModesubTest(6,:houseId,:dest ,:mid)}", params);
    	LOGGER.info("scenelimit---count:"+JSON.toJSONString(aggre));
    	return aggre;
    }
}
