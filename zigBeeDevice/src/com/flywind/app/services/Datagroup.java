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
import com.flywind.app.data.Xmlgroup;
import com.flywind.app.entities.Modeactlib;
import com.flywind.app.entities.Modegroupmain;
import com.flywind.app.entities.Modegroupsub;
import com.flywind.app.entities.Usermodemain;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dgroup")
public class Datagroup
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Datagroup.class);

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
        List<Modegroupmain> groups = new ArrayList<Modegroupmain>();
        List<Modegroupsub> groupsubs = new ArrayList<Modegroupsub>();
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
        LOGGER.info("Users " + groups.toString());
        LOGGER.info("Users " + groupsubs.toString());
        LOGGER.info("-- Data Loaded. Exit");
        
    }

    public Xmlgroup xmlgroup=new Xmlgroup();
    public Xmlgroup create(List<?> main,List<?> sub)
    {
        List groups = new ArrayList();
        List groupsubs = new ArrayList();
    	for (Object e : main)
        {
    		Modegroupmain e1=(Modegroupmain)e;   		
    		List ls=updatecheck2(sub);
    		if(ls.size()>0){xmlgroup.setChecked(2);xmlgroup.setSub(ls);}   
    		else if(updatecheck(e1)>0)xmlgroup.setChecked(1);
    		else{ 
    			xmlgroup.setChecked(0);    			
    			groups.add(dao.create(e1));
        		for(Object u :sub){
        			Modegroupsub u1=(Modegroupsub)u;
        			u1.setMid(e1.getId());
        			groupsubs.add(dao.create(u));       	        
            	} 
            	xmlgroup.setMain(groups);
            	xmlgroup.setSub(groupsubs);          		
    		}   		
        }
        return xmlgroup;   	
    }
    public Xmlgroup update(List<?> main,List<?> sub,Target tar)
    {
        List groups = new ArrayList();
        List groupsubs = new ArrayList();
    	for (Object e : main)
        {
    		Modegroupmain e1=(Modegroupmain)e; 
    		if(tar.getDest()!=null)e1.setId(tar.getDest());
    		groups.add(dao.update(e1));
    		deletecheck(e1,1);//修改主表删除子表
    		List ls=updatecheck2(sub);
    		if(ls.size()>0){xmlgroup.setChecked(2);xmlgroup.setSub(ls);}      		
    		else{
    			xmlgroup.setChecked(0);  
	    		for(Object u :sub){
	    			Modegroupsub u1=(Modegroupsub)u;
	    			u1.setMid(e1.getId());
	    			groupsubs.add(dao.create(u));       	        
	        	} 
	        	xmlgroup.setMain(groups);
	        	xmlgroup.setSub(groupsubs);	    		
    		}
        }  
        return xmlgroup;   	              
    }
    public void delete(List<?> main,List<?> sub) {  
    	for (Object e : main)
        {
    		Modegroupmain e1=(Modegroupmain)e;     		
//    		dao.delete(e);
    		deletecheck(e1,2);      		
        }                
    	for (Object u : sub)
        {
//			Modegroupsub u1=(Modegroupsub)u;  		
    		dao.delete(u);
//   		deletecheck(e1,1);  
        }
    	LOGGER.info("2222 " + sub.size());
    }

    public Object findmacro(Target tar){   	
    	List<Modegroupsub> subsList = null;
    	List<Modegroupmain> groupList = null;
    	List<Modeactlib> actList=null;
    	if(tar.getBehavior()==5){//按houseid查main
	    	groupList = dao.executeSql("{CALL ModemacrosubTest(5 ,:Id,:houseId)}",
					QueryParameters.with("Id",tar.getId()).and("houseId", tar.getHouseId()).parameters());
    	}else if(tar.getBehavior()==6){//按mainid查sub
    		subsList=dao.executeSql("{CALL ModemacrosubTest(6 ,:Id,:houseId)}",
					QueryParameters.with("Id",tar.getId()).and("houseId", tar.getHouseId()).parameters());
    	}else if(tar.getBehavior()==7){//按mainid查act
	    	actList = dao.executeSql("{CALL ModemacrosubTest(7 ,:Id,:houseId)}",
	    			QueryParameters.with("Id", tar.getId()).and("houseId", tar.getHouseId()).parameters(),Modeactlib.class);    		
    	}else if(tar.getBehavior()==8){//按actid查device
    		subsList=dao.executeSql("{CALL ModemacrosubTest(8 ,:Id,:houseId)}",
					QueryParameters.with("Id",tar.getId()).and("houseId", tar.getHouseId()).parameters());
    	}
        Map<String, Object> groupMap = new HashMap<String, Object>();
    	groupMap.put("sub", subsList);
		groupMap.put("main", groupList);  
		groupMap.put("act", actList); 		
        LOGGER.info("1111 groupMap:" + JSON.toJSONString(groupMap));
    	return groupMap;
    }
    
    public int deletecheck(Modegroupmain e1,int i){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("houseId", e1.getHouseId());    	
    	params.put("id", e1.getId());
    	int count=0; 
    	if(i==1)
    		count=dao.executeHql("{CALL ModedeleteTest(3,:houseId,:id)}",params);
	    else if(i==2)
    		count=dao.executeHql("{CALL ModedeleteTest(4,:houseId,:id)}",params);	    	
    	LOGGER.info("i=="+i+"groupdelete----"+count+"----"+JSON.toJSONString(params));
    	return 0;    	
    }
    public int updatecheck(Modegroupmain e1){
		List<Map<String,BigInteger>> aggre=dao.executeSql("select count(*) as Count from modegroupmain m where m.houseId=:houseId and m.groupName=:groupName", 
				QueryParameters.with("houseId", e1.getHouseId()).and("groupName", e1.getGroupName()).parameters());
		LOGGER.info("groupname--count:"+JSON.toJSONString(aggre));
		return aggre.get(0).get("Count").intValue();
    }
    public List updatecheck2(List<?> sub){
    	Map<String, Object> params = new HashMap<String, Object>();
    	String mid="";		
		params.put("dest", -1);
		params.put("houseId", ((Modegroupsub)sub.get(0)).getHouseId());

    	for (Object u : sub) { 
    		Modegroupsub u1=(Modegroupsub)u;
    		mid+=u1.getDeviceId()+",";
    	}	
    	mid=mid.substring(0,mid.length()-1);
    	params.put("mid", mid);
    	LOGGER.info("---houseId---"+params.get("houseId")+"---mid---"+mid);	    	
    	List aggre=dao.executeSql("{CALL ModesubTest(5,:houseId,:dest ,:mid)}", params);
    	LOGGER.info("grouplimit---count:"+JSON.toJSONString(aggre));
    	return aggre;
    }
}
