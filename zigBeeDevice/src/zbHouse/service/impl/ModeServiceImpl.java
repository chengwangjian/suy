package zbHouse.service.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.util.PropertiesUtil;
import zbHouse.editor.Room2Many;
import zbHouse.model.Mode;
import zbHouse.pageModel.DataGrid;
import zbHouse.service.ModeServiceI;

@Service("modeService")
public class ModeServiceImpl implements ModeServiceI {
	private static final Logger log = Logger.getLogger(ModeServiceImpl.class);
	private Room2Many roomCheck;
	private BaseDaoI<Map> mapDao;
//	private static CopyOnWriteArrayList<Mode> updateList=new CopyOnWriteArrayList<Mode>();
//	private static CopyOnWriteArrayList<Mode> saveList=new CopyOnWriteArrayList<Mode>();
	private List<Mode> cacheList = new ArrayList(100);
	private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.mode"));
	
	public Room2Many getRoomCheck() {
		return roomCheck;
	}
	@Autowired
	public void setRoomCheck(Room2Many roomCheck) {
		this.roomCheck = roomCheck;
	}
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	@Override
	public Mode keyUpdate(Mode mode) {
			
		return mode;
		// TODO Auto-generated method stub
	}
	private Mode convertMapToDevice(Map<String, Object> map) {
		Mode dv =new Mode();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dv.setId(((BigInteger) map.get("id")).longValue());
			dv.setHouseIeee((String) map.get("houseIEEE"));
			dv.setRoomId(map.get("roomId")==null?null:((BigInteger) map.get("roomId")).longValue());
			dv.setModeId(map.get("modeId")==null?null:((BigInteger) map.get("modeId")).longValue());
			dv.setModeName((String) map.get("deviceName"));
			dv.setPicName((String) map.get("ieee"));
			dv.setDescription((String) map.get("ep"));
			dv.setLastTime(map.get("lasttime")==null?null:sdf.parse((String) map.get("lasttime")));
		} catch(Exception e) {
			log.info("ModeServiceImpl convertMapToDevice", e);
		}
		return dv;		
	}
	
	@Override
	public Mode saveOrUpdate(Mode newdata,Map<String, Object> params) {
		//查询Device是否存在或nodeId是否合法
		StringBuilder sql = new StringBuilder("select a.id,a.houseIEEE,a.roomId,a.modeId,a.modeName,a.picName,a.description,");
		sql.append("a.lasttime from (")
		.append("select a.id,a.houseIEEE,a.roomId,a.modeId,a.modeName,a.picName,a.description,")
		.append("a.lasttime from mode a ")
		.append("where a.houseIeee =:houseIeee and a.modeId =:modeId limit 1 ")
		.append("UNION All ")
		.append("select null id,'' houseIEEE,null roomId,null modeId,'' modeName,'' picName,'' description,")
		.append("'' lasttime from room a ")
		.append("where a.roomId = :roomId limit 1")
		.append(") a order by a.id asc");
		params.put("houseIeee", newdata.getHouseIeee());
		params.put("modeId", newdata.getModeId());
		params.put("roomId", newdata.getRoomId());
		List<Map> dList = mapDao.executeSql(sql.toString(), params);
		if(dList.isEmpty()) {
			newdata.setRoomId(-1L);
			cacheList.add(newdata);
		} 
		else {
			//都存在
			if(dList.size() > 1) {
				Map map = dList.get(0);
				Mode dv = convertMapToDevice(map);
				BeanUtils.copyProperties(newdata, dv,new String[]{"id","isonline","lastTime"});
				cacheList.add(dv);
			}
			else {
				Map map = dList.get(0);				
				//只有roomId存在（即合法）
				if(map.get("id") == null) 
					cacheList.add(newdata);
				else {
					Mode dv = convertMapToDevice(map);
					BeanUtils.copyProperties(newdata, dv,new String[]{"id","isonline","lastTime"});
					dv.setRoomId(-1L);
					cacheList.add(dv);
				}
			}
		}
		//批量更新
		if (this.cacheList.size() >= this.cacheSize) {
			dingshiSave();
//			log.info("定时保存modeBegin......");
//			final List<Mode> tmpList = this.cacheList;
//			this.cacheList = new ArrayList<Mode>(this.cacheSize + 10);
////			new Thread() {
////	    		public void run() {
//	    			log.info("Mode saveorupdate begin, tmpList.size: " + tmpList.size());
//	    			StringBuilder sqlRep = new StringBuilder("REPLACE INTO Mode(id,houseIeee,roomId,modeId,modeName,picName,description,lasttime) VALUES");
//	    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    			for(Mode dv: tmpList) {	    				
//	    				sqlRep.append("(").append(dv.getId()).append(",'").append(dv.getHouseIeee()).append("',")
//	    				.append(dv.getRoomId()).append(",").append(dv.getModeId()).append(",'").append(dv.getModeName())
//	    				.append("','").append(dv.getPicName()).append("','").append(dv.getDescription()).append("','")
//	    				.append(sdf.format(dv.getLastTime())).append("'),");	    				
//	    			}
//	    			String sqlStr = sqlRep.deleteCharAt(sqlRep.length() - 1).toString();
//	    			Connection conn = null;
//	    			Statement stmt = null;	    			
//	    			try {
//	    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
//	    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
//	    						PropertiesUtil.getProperty("jdbc.user"),
//	    						PropertiesUtil.getProperty("jdbc.password"));
//	    				stmt = conn.createStatement();
//	    				int i = stmt.executeUpdate(sqlStr);
//	    				log.info("Mode saveorupdate end, result: " + i);
//	    			} catch(Exception e) {
//	    				log.info("save or update device exception", e);
//	    			} finally {
////	    				cacheList.clear();
//	    				try {
//	    					if(stmt != null)
//	    						stmt.close();
//	    					if(conn != null)
//	    						conn.close();
//	    				} catch(Exception e) {
//	    					log.info("jdbc close exception", e);
//	    				}
//	    			}
//	    			log.info("定时保存modeEnd......");
////	    		}
////	    	}.start();
		}
		return newdata;		
	}
	
	@Override
	public int delete(Map<String, Object> params) {
		
		String hql = "delete Mode t ";		
		hql = addWhere2(hql, params);		
		
		return roomCheck.getModeDao().executeHql(hql);
		// TODO Auto-generated method stub
		
	}
	@Override
	public DataGrid find(Map<String, Object> params) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from Mode t ";
		hql = addWhere2(hql, params);
		String totalHql = "select count(*) " + hql;	
		
		List<Mode> l=roomCheck.getModeDao().find(hql);
		dg.setTotal(roomCheck.getModeDao().count(totalHql));
		dg.setRows(l);
		return dg;	
	}
	
	private String addWhere(String hql, Mode mode, Map<String, Object> params) {
		if (mode!= null){
			params.put("houseIeee", mode.getHouseIeee());
			params.put("modeId", mode.getModeId());
			hql += "where t.houseIeee =:houseIeee and t.modeId =:modeId ";
		}
		return hql;
	}

	private String addWhere2(String hql, Map<String, Object> params) {
		if (params!= null){
			boolean first=true;
			hql += "where ";
			  for (String key : params.keySet()) {
				if(first){  
					first=false;
					hql += "t." + key + " ='" + params.get(key)+ "'";
				}else{
					hql += " and t." + key + " ='" + params.get(key)+ "'";					
				}
			}			
		}
		return hql;
	}
	
	@Override
	public int dingshiSave() {
		if(cacheList.size()==0){
			log.info("定时保存mode，缓存为空");
			return 0;
		}
		log.info("定时保存modeBegin......");
		final List<Mode> tmpList = this.cacheList;
		this.cacheList = new ArrayList<Mode>(this.cacheSize + 10);
//    	log.info("Mode saveorupdate begin, tmpList.size: " + tmpList.size());
    	Thread th = new Thread() {
    		@Override
			public void run() {
    			StringBuilder sql = new StringBuilder("REPLACE INTO Mode(id,houseIeee,roomId,modeId,modeName,picName,description,lasttime) VALUES");
    	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    	for(Mode dv: tmpList) {	    				
    	    		sql.append("(").append(dv.getId()).append(",'").append(dv.getHouseIeee()).append("',")
    	    		.append(dv.getRoomId()).append(",").append(dv.getModeId()).append(",'").append(dv.getModeName())
    	    		.append("','").append(dv.getPicName()).append("','").append(dv.getDescription()).append("','")
    	    		.append(sdf.format(dv.getLastTime())).append("'),");	    				
    	    	}
    	    	String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
    	    	Connection conn = null;
    			Statement stmt = null;
    			try {
    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
    						PropertiesUtil.getProperty("jdbc.user"),
    						PropertiesUtil.getProperty("jdbc.password"));
    				stmt = conn.createStatement();
    	    		int i = stmt.executeUpdate(sqlStr);
//    	    		log.info("Mode saveorupdate end, result: " + i);
    	    	} catch(Exception e) {
    	    		log.info("save or update device exception", e);
    	    	} finally {
    	    		try {
    	    			if(stmt != null)
    	    				stmt.close();
    	    			if(conn != null)
    						conn.close();
    	    		} catch(Exception e) {
    	    			log.info("jdbc close exception", e);
    	    		}
    	    	}
    			log.info("定时保存modeEnd......");
    		}
    	};
		th.setName("saveModeThread");
		th.start();
		return 1;
	}
}
