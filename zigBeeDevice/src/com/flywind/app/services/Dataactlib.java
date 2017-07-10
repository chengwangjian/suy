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

import sy.model.Modedevice;
import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Target;
import com.flywind.app.entities.Modeactlib;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dactlib")
public class Dataactlib
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Dataactlib.class);

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
        
        List<Modeactlib> acts = new ArrayList<Modeactlib>();
//        rooms.add(new Moderoom(111, "111", "111"));
//        rooms.add(new Moderoom(222, "222", "222"));
//        modes.add(new Usermodemain(17, "aaa", "aaa", "aaa"));
//        modes.add(new Usermodemain(18, "bbb", "bbb", "bbb"));
//        modesubs.add(new Usermodesub(1, "zzz", 111));
//        modesubs.add(new Usermodesub(2, "yyy", 222)); 

        
        LOGGER.info("-- Loading initial demo data");
//        rooms=dao.findWithNamedQuery(Moderoom.BY_houseId,
//				QueryParameters.with("houseId", 111L).parameters());
//        delete(rooms);
        
//      create(rooms);
//      create(modes);   
//      create(modesubs); 
        LOGGER.info("Users " + acts.toString());
        LOGGER.info("Users " );
        LOGGER.info("-- Data Loaded. Exit");
        
    }

    public void create(List<?> entities)
    {
        for (Object e : entities)
        {
            dao.create(e);
        }
    }
    public void update(List<?> entities)
    {
        for (Object e : entities)
        {
            dao.update(e);
        }
        LOGGER.info("3333 " + entities.size());
    }
    public void delete(List<?> entities) {  
    	for (Object e : entities)
        {
    		dao.delete(e);
        }                
    	LOGGER.info("2222 " + entities.size());
    }
    public Map<String,Object> findmodel(Target tar){
    	Map<String, Object> actMap = new HashMap<String, Object>();
    	if(tar.getBehavior()==1){   
	    	List<Modeactlib> actList = dao.executeSql("{CALL ModemacrosubTest(1 ,:Id,:houseId)}",
	    			QueryParameters.with("Id", tar.getId()).and("houseId", tar.getHouseId()).parameters(),Modeactlib.class);
	    	actMap.put("main", actList);    				
    	}else if(tar.getBehavior()==3){   
	    	List<Modedevice> actList = dao.executeSql("{CALL ModemacrosubTest(3 ,:Id,:houseId)}",
					QueryParameters.with("Id", tar.getId()).and("houseId", tar.getHouseId()).parameters());
	    	actMap.put("main", actList); 
    	}else if(tar.getBehavior()==2){//查询全部根据id,houseid分页
	    	List<Modeactlib> actList = dao.executeSql("{CALL Modescheme(7 ,:houseId,0,:Id)}",
	    			QueryParameters.with("Id", tar.getId()*tar.getHouseId()).and("houseId", tar.getHouseId()).parameters(),Modeactlib.class);
	    	actMap.put("main", actList);    		
    	}else if(tar.getBehavior()==4){//查询单条
	    	List<Modeactlib> actList = dao.executeSql("{CALL Modescheme(8 ,:houseId,0,:Id)}",
	    			QueryParameters.with("Id", tar.getId()).and("houseId", tar.getHouseId()).parameters(),Modeactlib.class);
	    	actMap.put("main", actList);    		
    	}else if(tar.getBehavior()==100){  
    		String sqlString = "SELECT a.DataType,a.ActName,a.UniqueName,a.UniqueNameEN,a.ID from modedevice m,modeactlib a WHERE m.deviceId = a.deviceId and m.id=:epid and m.deviceId =:deviceId";
	    	Map<String, Object> param = new HashMap<String,Object>();
    		param.put("epid", tar.getEpId());
    		param.put("deviceId", tar.getAct());
    		List<Modeactlib> actList = dao.executeSql(sqlString, param);
//    		List<Modeactlib> actList = dao.executeSql("{CALL ModemacrosubTest(100 ,:Id,:houseId)}",
//	    			QueryParameters.with("Id", tar.getAct()).and("houseId", tar.getEpId()).parameters());
	    	actMap.put("main", actList);    				
    	}else if(tar.getBehavior()==101){
    		String sqlString = "SELECT m.* from modeactlib m INNER JOIN modedevice d on m.DeviceID=d.deviceId  WHERE d.id=:Id";
    		Map<String, Object> param = new HashMap<String,Object>();
    		param.put("Id", tar.getId());
    		List<Modeactlib> actList = dao.executeSql(sqlString, param);
    		actMap.put("main", actList);    
		}else if(tar.getBehavior()==-1){//编辑ep的动作复选框显示
			String sql = "select m.ID,m.ActName,m.UniqueName,m.UniqueNameEN from modeactlib m";
			Map<String, Object> param = new HashMap<String,Object>();
			List<Modeactlib> actList = dao.executeSql(sql,param);
			actMap.put("main", actList);  
		}
        LOGGER.info("1111 " + actMap);
    	return actMap;
    }
	public int getCount(Map<String, Object> tar){
		List<Map> cList = dao.executeSql("Select Count(*) as aCount from modeactlib t ORDER BY t.id",tar);				
		return ((BigInteger)(cList.get(0).get("aCount"))).intValue();
	}
}
