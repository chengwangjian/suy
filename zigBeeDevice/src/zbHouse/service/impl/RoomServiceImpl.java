package zbHouse.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.util.PropertiesUtil;
import zbHouse.editor.Room2Many;
import zbHouse.model.Room;
import zbHouse.pageModel.DataGrid;
import zbHouse.service.RoomServiceI;

@Service("roomService")
public class RoomServiceImpl implements RoomServiceI {
	private List<Room> cache = new ArrayList(100);
	private static final Logger log = Logger.getLogger(RoomServiceImpl.class);
	private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.room"));
	//数据库的长连接
	private Connection conn = null;	
	private Room2Many roomCheck;
	
	public Room2Many getRoomCheck() {
		return roomCheck;
	}
	@Autowired
	public void setRoomCheck(Room2Many roomCheck) {
		this.roomCheck = roomCheck;
	}

	@Override
	public Room keyUpdate(Room room) {
					
		return room;
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 将Map对象转换为Room对象
	 * @param map
	 * @return
	 */
	private Room convertMapToRoom(ResultSet rs) {
		Room oldroom = new Room();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			oldroom.setId(rs.getLong(1));
			oldroom.setHouseIeee(rs.getString(2));
			oldroom.setRoomId(rs.getLong(3));
			oldroom.setRoomName(rs.getString(4));
			oldroom.setRoomPic(rs.getString(5));
		} catch(Exception e) {
			log.info("roomService convertMapToRoom", e);
		}
		return oldroom;
	}
	@Override
	public Room saveOrUpdate(Room newdata,final Map<String, Object> params) {
		cache.add(newdata);
//		log.info("Room save or update batch count=" + this.cache.size());
		//批量更新
		if(this.cache.size()>cacheSize){
			dingshiSave();
//			log.info("定时保存roomBegin......");
//			final List<Room> tmpList = this.cache;
//			this.cache = new ArrayList<Room>(this.cacheSize + 10);
////			new Thread() {
////	    		public void run() {
//	    			log.info("Room saveorupdate begin, tmpList.size: " + tmpList.size());
//	    			StringBuilder sql = new StringBuilder("REPLACE INTO Room(id,houseIeee,roomId,roomName,roomPic) VALUES");
//	    			Connection conn = null;
//	    			Statement stmt = null;
//	    			ResultSet rs = null;
//	    			try {
//	    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
//	    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
//	    						PropertiesUtil.getProperty("jdbc.user"),
//	    						PropertiesUtil.getProperty("jdbc.password"));
//	    				stmt = conn.createStatement();
//	    				for(Room rv: tmpList) {	  
//	    					Room olddata =null;
//	    					String hql = "select * from Room where houseIeee ='"+rv.getHouseIeee()+"' and roomId="+rv.getRoomId();
//	    					rs = stmt.executeQuery(hql);
//	    					while(rs.next()) {
//	    						olddata = convertMapToRoom(rs);
//	    					}
//	    					if(olddata!= null){
//	    						BeanUtils.copyProperties(rv, olddata,new String[]{"id"});
//	    						sql.append("(").append(olddata.getId()).append(",'").append(olddata.getHouseIeee()).append("',")
//	    	    				.append(olddata.getRoomId()).append(",'").append(olddata.getRoomName()).append("','").append(olddata.getRoomPic()).append("'),");
////	    						roomCheck.getNodeDao().update(olddata);	
//	    					}else{
//	    						sql.append("(").append(rv.getId()).append(",'").append(rv.getHouseIeee()).append("',")
//	    	    				.append(rv.getRoomId()).append(",'").append(rv.getRoomName()).append("','").append(rv.getRoomPic()).append("'),");
////	    						roomCheck.getNodeDao().saveOrUpdate(nv);
//	    					}
//	    				}
//	    				String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
//	    				int i = stmt.executeUpdate(sqlStr);
//	    				log.info("Room saveorupdate end, result: " +i);
//	    			} catch(Exception e) {
//	    				log.info("save or update Room exception", e);
//	    				} finally {
////		    				cacheList.clear();
//		    				try {
//		    					if(rs != null)
//		    	    				rs.close();
//		    	    			if(stmt != null)
//		    	    				stmt.close();
//		    	    			if(conn != null)
//		    	    				conn.close();
//		    				} catch(Exception e) {
//		    					log.info("jdbc close exception", e);
//		    				}
//		    			}
//	    			log.info("定时保存nodeEnd......");
////	    		}
////			}.start();
		}
		return newdata;
		// TODO Auto-generated method stub

	}
	
	@Override
	public int delete(Map<String, Object> params) {
		
		int dcount=0;
		DataGrid dg = this.find(params);
		if(dg.getTotal()!=0){
			roomCheck.roomDeleteCheck(dg);
			String hql = "delete Room t ";		
			hql = addWhere2(hql, params);
			dcount=roomCheck.getRoomDao().executeHql(hql);
		}
		return dcount;
		// TODO Auto-generated method stub
		
	}
	@Override
	public DataGrid find(Map<String, Object> params) {

		DataGrid dg = new DataGrid();
		String hql = "from Room t ";
		hql = addWhere2(hql, params);
		String totalHql = "select count(*) " + hql;	
		
		List<Room> l=roomCheck.getRoomDao().find(hql);
		dg.setTotal(roomCheck.getRoomDao().count(totalHql));
		dg.setRows(l);
		return dg;	
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql, Room room, Map<String, Object> params) {
		if (room!= null){
			params.put("houseIeee", room.getHouseIeee());
			params.put("roomId", room.getRoomId());
			hql += "where t.houseIeee =:houseIeee and t.roomId =:roomId ";
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
		if(cache.size()==0){
			log.info("定时保存room，缓存为空");
			return 0;
		}
		log.info("定时保存roomBegin......");
		final List<Room> tmpList = this.cache;
		this.cache = new ArrayList<Room>(this.cacheSize + 10);
//    	log.info("Room saveorupdate begin, tmpList.size: " + tmpList.size());
    	Thread th = new Thread() {
    		@Override
			public void run() {
    			StringBuilder sql = new StringBuilder("REPLACE INTO Room(id,houseIeee,roomId,roomName,roomPic) VALUES");
    	    	Connection conn = null;
    			Statement stmt = null;
    			ResultSet rs = null;
    			try {
    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
    						PropertiesUtil.getProperty("jdbc.user"),
    						PropertiesUtil.getProperty("jdbc.password"));
    				stmt = conn.createStatement();
    	    		for(Room rv: tmpList) {	  
    	    			Room olddata =null;
    	    			String hql = "select * from Room where houseIeee ='"+rv.getHouseIeee()+"' and roomId="+rv.getRoomId();
    	    			rs = stmt.executeQuery(hql);
    	    			while(rs.next()) {
    	    				olddata = convertMapToRoom(rs);
    	    			}
    	    			if(olddata!= null){
    	    				BeanUtils.copyProperties(rv, olddata,new String[]{"id"});
    	    				sql.append("(").append(olddata.getId()).append(",'").append(olddata.getHouseIeee()).append("',")
    	    	    		.append(olddata.getRoomId()).append(",'").append(olddata.getRoomName()).append("','").append(olddata.getRoomPic()).append("'),");
//    	    				roomCheck.getNodeDao().update(olddata);	
    	    			}else{
    	    				sql.append("(").append(rv.getId()).append(",'").append(rv.getHouseIeee()).append("',")
    	    	    		.append(rv.getRoomId()).append(",'").append(rv.getRoomName()).append("','").append(rv.getRoomPic()).append("'),");
//    	    				roomCheck.getNodeDao().saveOrUpdate(nv);
    	    			}
    	    		}
    	    		String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
    	    		int i = stmt.executeUpdate(sqlStr);
    	    		log.info("Room saveorupdate end, result: " +i);
    	    	} catch(Exception e) {
    	    		log.info("save or update Room exception", e);
    	    		} finally {
//    		    		cacheList.clear();
    		    		try {
    		    			if(rs != null)
    		    				rs.close();
    		    			if(stmt != null)
    		    				stmt.close();
    		    			if(conn != null)
    		    				conn.close();
    		    		} catch(Exception e) {
    		    			log.info("jdbc close exception", e);
    		    		}
    		    	}
    			log.info("定时保存nodeEnd......");
//    	    	log.info("定时保存room，缓存为:"+tmpList.size());
    		}
    	};
		th.setName("saveRoomThread");
		th.start();
		return 1;
	}
}
