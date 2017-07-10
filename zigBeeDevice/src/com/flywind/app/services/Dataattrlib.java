package com.flywind.app.services;

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
import com.flywind.app.entities.Deviceattrlib;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dattrlib")
public class Dataattrlib
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Dataattrlib.class);

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
        
        List<Deviceattrlib> attrs = new ArrayList<Deviceattrlib>();
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
        LOGGER.info("Users " + attrs.toString());
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
    public Map<String,Object> finddevice(Target tar){
    	Map<String, Object> attrMap = new HashMap<String, Object>();
    	if(tar.getBehavior()==2){
	    	List<Deviceattrlib> attrList = dao.executeSql("{CALL ModemacrosubTest(2 ,:Id,:houseId)}",
	    					QueryParameters.with("Id", tar.getId()).and("houseId", tar.getHouseId()).parameters(),Deviceattrlib.class);
	    	attrMap.put("main", attrList);
    	}else if(tar.getBehavior()==4){
	    	List<Modedevice> attrList = dao.executeSql("{CALL ModemacrosubTest(4 ,:Id,:houseId)}",
	    					QueryParameters.with("Id", tar.getId()).and("houseId", tar.getHouseId()).parameters());
	    	attrMap.put("main", attrList);   		
    	}
        LOGGER.info("1111 " + attrMap);
    	return attrMap;
    }
    
    public Map<String, Object> getDevice(Target tar) {
    	Map<String, Object> attrMap = new HashMap<String, Object>();
    	if(tar.getBehavior()==21){
    		Map<String, Object> actMap = new HashMap<String, Object>();
	    	List<Modedevice> attrList = dao.executeSql("{CALL ModemacrosubTest(21 ,:Id,:houseId)}",
	    					QueryParameters.with("Id", 0).and("houseId", tar.getHouseId()).parameters());
	    	String sql ="select m.id,m.roomId,act.ID as actId,act.ActName as ActName,act.DataType as DataType,act.UniqueName as UniqueName FROM modedevice m INNER JOIN modeactlib act ON m.deviceId = act.DeviceID WHERE m.houseId =:houseid"; 
	    	actMap.put("houseid", tar.getHouseId());
	    	List<Modedevice> actList =  dao.executeSql(sql, actMap);
	    	attrMap.put("main", attrList);
	    	attrMap.put("act",actList );
    	}else if(tar.getBehavior()==41){
	    	List<Modedevice> attrList = dao.executeSql("{CALL ModemacrosubTest(41 ,:Id,:houseId)}",
	    					QueryParameters.with("Id", tar.getId()).and("houseId", tar.getHouseId()).parameters());
	    	attrMap.put("main", attrList);   		
    	}
    	else if(tar.getBehavior()==211){
    		Map<String, Object> actMap = new HashMap<String, Object>();
    		List<Modedevice> attrList = dao.executeSql("{CALL ModemacrosubTest(211 ,:Id,:houseId)}",
					QueryParameters.with("Id",0).and("houseId", tar.getHouseId()).parameters());
	    	String sql="SELECT * from ( SELECT act.ExtentionSet,m.id,m.roomId,act.ID AS actId,act.ActName AS ActName,act.DataType AS DataType,act.UniqueName AS UniqueName FROM	modedevice m " +
	    			"INNER JOIN modenodelib nodeL ON m.modelId = nodeL.modelId OR LOCATE(nodeL.modelId, m.modelId) = 1 " +
	    			"INNER JOIN modeeplib EPL ON EPL.nodeId = nodeL.id AND m.ep = epl.ep " +
	    			"INNER JOIN modeactlib act ON m.deviceId = act.DeviceID " +
	    			"WHERE	m.houseId =:houseid ORDER BY act.ExtentionSet) t1 " +
	    			"UNION " +
	    			"SELECT * FROM( SELECT act.ExtentionSet,m.id,m.roomId,act.ID AS actId,act.ActName AS ActName,act.DataType AS DataType,act.UniqueName AS UniqueName " +
	    			"FROM modeactlib act,modedevice m,modenodelib n " +
	    			"WHERE  m.modelId = n.modelId and (n.id=86 OR n.id=145) and act.DeviceID=0400 AND m.houseid = :houseid order by act.ExtentionSet) t2";
    		actMap.put("houseid", tar.getHouseId());
	    	List<Modedevice> actList =  dao.executeSql(sql, actMap);
	    	attrMap.put("main", attrList);
	    	attrMap.put("act",actList );	
    	}else if (tar.getBehavior()==212) {
    		Map<String, Object> actMap = new HashMap<String, Object>();
    		List<Modedevice> attrList = dao.executeSql("SELECT	m.*, r.roomName AS fangjian,d.ID AS aid,d.* FROM modedevice m LEFT JOIN moderoom r ON m.houseId = r.houseId  JOIN modeactlib d ON m.deviceId = d.DeviceID WHERE m.roomId = r.roomId AND m.houseId =:houseId GROUP BY m.id;",
					QueryParameters.with("houseId", tar.getHouseId()).parameters());
	    	String sql="select t.* from ((SELECT act.ExtentionSet,m.id,m.roomId,act.ID AS actId,act.ActName AS ActName,act.DataType AS DataType,act.UniqueName AS UniqueName FROM	modedevice m " +
	    		" JOIN modeactlib act ON m.deviceId = act.DeviceID " +
	    			"WHERE	m.houseId =:houseid)" +
	    			"UNION " +
	    			" (SELECT act.ExtentionSet,m.id,m.roomId,act.ID AS actId,act.ActName AS ActName,act.DataType AS DataType,act.UniqueName AS UniqueName " +
	    			"FROM modeactlib act,modedevice m,modenodelib n " +
	    			"WHERE m.modelId = n.modelId and (n.id=86 OR n.id=145) and act.DeviceID=0400 AND m.houseid = :houseid)) t order by t.ExtentionSet";
    		actMap.put("houseid", tar.getHouseId());
	    	List<Modedevice> actList =  dao.executeSql(sql, actMap);
	    	attrMap.put("main", attrList);
	    	attrMap.put("act",actList );		
		}
        LOGGER.info("1111 " + attrMap);
    	return attrMap;
	}
	public Map<String, Object> getDeviceByRoomId(Target target) {
		Map<String, Object> actMap = new HashMap<String, Object>();
		Map<String, Object> attrMap = new HashMap<String, Object>();
		Map<String, Object> deviceMap = new HashMap<String, Object>();
		attrMap.put("roomid", target.getRoomid());
		attrMap.put("houseId", target.getHouseId());
		String sql="SELECT d.id,m.houseId,m.roomId,m.roomName,d.deviceName from moderoom m,modedevice d WHERE m.roomId=d.roomId and m.houseId=d.houseId and m.roomId=:roomid AND m.houseId=:houseId";
		List<Modedevice>modedevices= dao.executeSql(sql, attrMap);
		deviceMap.put("main", modedevices);
		String sql2 ="select m.id,m.roomId,act.ID as actId,act.ActName as ActName,act.DataType as DataType,act.UniqueName as UniqueName FROM modedevice m INNER JOIN modeactlib act ON m.deviceId = act.DeviceID WHERE m.roomId =:roomid and m.houseId=:houseId"; 
    	actMap.put("roomid", target.getRoomid());
    	actMap.put("houseId", target.getHouseId());
    	List<Modedevice> actList =  dao.executeSql(sql2, actMap);
    	deviceMap.put("act", actList);
		 LOGGER.info("1111 " +deviceMap);
		return deviceMap;
	}
	
	//查询param
	public Map getMacroSubParam(long modelId, long deviceId) {
		String sql = "select a.id,a.param from modemacrosub a left join modemacromain b on a.mid = b.id left join usermodesub c on b.id = c.dest where a.dest = :deviceId and c.mid = :modelId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modelId", modelId);
		params.put("deviceId", deviceId);
		List<Map> mmsubList = dao.executeSql(sql, params);
		if(mmsubList.isEmpty())
			return null;
		else
			return mmsubList.get(0);
	}
}
