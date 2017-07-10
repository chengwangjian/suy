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

import sy.dao.BaseDaoI;
import sy.service.DeviceattributeServiceI;
import sy.service.DeviceoperatehistoryHouseidYearServiceI;
import sy.service.DevicewarnhistoryHouseidYearServiceI;
import sy.service.IRServiceI;
import sy.util.PropertiesUtil;
import zbHouse.model.Video;
import zbHouse.pageModel.DataGrid;
import zbHouse.service.DeviceServiceI;
import zbHouse.service.ModeServiceI;
import zbHouse.service.NodeServiceI;
import zbHouse.service.RoomServiceI;
import zbHouse.service.VideoServiceI;

@Service("videoService")
public class VideoServiceImpl implements VideoServiceI {
	private List<Video> cache = new ArrayList(100);
	private static final Logger log = Logger.getLogger(VideoServiceImpl.class);
	private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.video"));
	//数据库的长连接
	private Connection conn = null;
	private BaseDaoI<Video> videoDao;
	private RoomServiceI roomServiceI;
	private NodeServiceI nodeServiceI;
	private ModeServiceI modeServiceI;
	private DeviceServiceI deviceServiceI;
	private DeviceattributeServiceI deviceattributeServiceI;
	private DevicewarnhistoryHouseidYearServiceI devicewarnhistoryHouseidYearServiceI;
	private DeviceoperatehistoryHouseidYearServiceI deviceoperatehistoryHouseidYearServiceI;
	private IRServiceI irServiceI;

	public RoomServiceI getRoomServiceI() {
		return roomServiceI;
	}
	@Autowired
	public void setRoomServiceI(RoomServiceI roomServiceI) {
		this.roomServiceI = roomServiceI;
	}

	public NodeServiceI getNodeServiceI() {
		return nodeServiceI;
	}
	@Autowired
	public void setNodeServiceI(NodeServiceI nodeServiceI) {
		this.nodeServiceI = nodeServiceI;
	}

	public ModeServiceI getModeServiceI() {
		return modeServiceI;
	}
	@Autowired
	public void setModeServiceI(ModeServiceI modeServiceI) {
		this.modeServiceI = modeServiceI;
	}

	public DeviceServiceI getDeviceServiceI() {
		return deviceServiceI;
	}
	@Autowired
	public void setDeviceServiceI(DeviceServiceI deviceServiceI) {
		this.deviceServiceI = deviceServiceI;
	}

	public DeviceattributeServiceI getDeviceattributeServiceI() {
		return deviceattributeServiceI;
	}
	@Autowired
	public void setDeviceattributeServiceI(
			DeviceattributeServiceI deviceattributeServiceI) {
		this.deviceattributeServiceI = deviceattributeServiceI;
	}

	public DevicewarnhistoryHouseidYearServiceI getDevicewarnhistoryHouseidYearServiceI() {
		return devicewarnhistoryHouseidYearServiceI;
	}
	@Autowired
	public void setDevicewarnhistoryHouseidYearServiceI(
			DevicewarnhistoryHouseidYearServiceI devicewarnhistoryHouseidYearServiceI) {
		this.devicewarnhistoryHouseidYearServiceI = devicewarnhistoryHouseidYearServiceI;
	}

	public DeviceoperatehistoryHouseidYearServiceI getDeviceoperatehistoryHouseidYearServiceI() {
		return deviceoperatehistoryHouseidYearServiceI;
	}
	@Autowired
	public void setDeviceoperatehistoryHouseidYearServiceI(
			DeviceoperatehistoryHouseidYearServiceI deviceoperatehistoryHouseidYearServiceI) {
		this.deviceoperatehistoryHouseidYearServiceI = deviceoperatehistoryHouseidYearServiceI;
	}

	public IRServiceI getIrServiceI() {
		return irServiceI;
	}
	@Autowired
	public void setIrServiceI(IRServiceI irServiceI) {
		this.irServiceI = irServiceI;
	}
	
	
	public BaseDaoI<Video> getVideoDao() {
		return this.videoDao;
	}

	@Autowired
	public void setVideoDao(BaseDaoI<Video> videoDao) {
		this.videoDao = videoDao;
	}

	@Override
	public Video keyUpdate(Video video) {
				
		return video;
		// TODO Auto-generated method stub
		
	}
	/**
	 * 将Map对象转换为Video对象
	 * @param map
	 * @return
	 */
	private Video convertMapToVideo(ResultSet rs) {
		Video oldVideo = new Video();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			oldVideo.setId(rs.getLong(1));
			oldVideo.setHouseIeee(rs.getString(2));
			oldVideo.setRoomId(rs.getLong(3));
			oldVideo.setLocalip(rs.getString(4));
			oldVideo.setLocalport(rs.getString(5));
			oldVideo.setIpcamip(rs.getString(6));
			oldVideo.setIpcamport(rs.getString(7));
			oldVideo.setCache(rs.getString(8));
			oldVideo.setSt(rs.getString(9));
			oldVideo.setUuid(rs.getString(10));
			oldVideo.setServer(rs.getString(11));
			oldVideo.setLocation(rs.getString(12));
			oldVideo.setStatus(rs.getString(13));
			oldVideo.setIpcamname(rs.getString(14));
		} catch(Exception e) {
			log.info("videoServiceImpl convertMapToVideo", e);
		}
		return oldVideo;
	}
	@Override
	public Video saveOrUpdate(Video newdata,final Map<String, Object> params) {
		cache.add(newdata);
//		log.info("Video save or update batch count=" + this.cache.size());
		//批量更新
		if(this.cache.size()>cacheSize){
			dingshiSave();
//			log.info("定时保存videoBegin......");
//			final List<Video> tmpList = this.cache;
//			this.cache = new ArrayList<Video>(this.cacheSize + 10);
////			new Thread() {
////			    public void run() {
//			    	log.info("Video saveorupdate begin, tmpList.size: " + tmpList.size());
//			    	StringBuilder sql = new StringBuilder("REPLACE INTO Video(id,houseIeee,roomId,localip,localport,ipcamip,ipcamport,cache,st,uuid,server,location,status,ipcamname) VALUES");
//			    	Connection conn = null;
//					Statement stmt = null;
//					ResultSet rs = null;
//					try {
//						Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
//						conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
//								PropertiesUtil.getProperty("jdbc.user"),
//								PropertiesUtil.getProperty("jdbc.password"));
//						stmt = conn.createStatement();
//			    		for(Video vv: tmpList) {
//			    			Video olddata =null;
//			    			String hql = "select * from Video  where houseIeee = '"+vv.getHouseIeee()+"' and "+"ipcamip ='"+vv.getIpcamip()+"' and "+"uuid ='"+vv.getUuid()+"'";
//	    					rs = stmt.executeQuery(hql);
//	    					while(rs.next()) {
//	    						olddata = convertMapToVideo(rs);
//	    					}
//			    			if(olddata!= null){
//			    				BeanUtils.copyProperties(vv, olddata,new String[]{"id"});
//			    				sql.append("(").append(olddata.getId()).append(",'").append(olddata.getHouseIeee()).append("',")
//			    	    		.append(olddata.getRoomId()).append(",'").append(olddata.getLocalip()).append("','").append(olddata.getLocalport()).append("','").append(olddata.getIpcamip())
//			    	    		.append("','").append(olddata.getIpcamport()).append("','").append(olddata.getCache()).append("','").append(olddata.getSt()).append("','").append(olddata.getUuid())
//			    	    		.append("','").append(olddata.getServer())
//			    	    		.append("','").append(olddata.getLocalport())
//			    	    		.append("','").append(olddata.getStatus())
//			    	    		.append("','").append(olddata.getIpcamname())
//			    	    		.append("'),");
////			    						roomCheck.getNodeDao().update(olddata);	
//			    			}else{
//			    				sql.append("(").append(vv.getId()).append(",'").append(vv.getHouseIeee()).append("',")
//			    	    		.append(vv.getRoomId()).append(",'").append(vv.getLocalip()).append("','").append(vv.getLocalport()).append("','").append(vv.getIpcamip())
//			    	    		.append("','").append(vv.getIpcamport()).append("','").append(vv.getCache()).append("','").append(vv.getSt()).append("','").append(vv.getUuid())
//			    	    		.append("','").append(vv.getServer())
//			    	    		.append("','").append(vv.getLocalport())
//			    	    		.append("','").append(vv.getStatus())
//			    	    		.append("','").append(vv.getIpcamname())
//			    	    		.append("'),");
////			    						roomCheck.getNodeDao().saveOrUpdate(nv);
//			    			}
//			    		}
//			    		String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
//			    		int i = stmt.executeUpdate(sqlStr);
//			    		log.info("Video saveorupdate end, result: " +i);
//			    	} catch(Exception e) {
//			    		log.info("save or update Video exception", e);
//			    		} finally {
////				    				cacheList.clear();
//				    		try {
//				    			if(rs != null)
//				    				rs.close();
//				    			if(stmt != null)
//				    				stmt.close();
//				    			if(conn != null)
//				    				conn.close();
//				    		} catch(Exception e) {
//				    			log.info("jdbc close exception", e);
//				    		}
//				    	}
//					log.info("定时保存videoEnd......");
////			    }
////			}.start();
		}
			return newdata;
		// TODO Auto-generated method stub
	}
	
	@Override
	public int delete(Map<String, Object> params) {
		
		String hql = "delete Video t ";		
		hql = addWhere2(hql, params);		
		return videoDao.executeHql(hql);
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataGrid find(Map<String, Object> params) {

		DataGrid dg = new DataGrid();
		String hql = "from Video t ";
		hql = addWhere2(hql, params);
		String totalHql = "select count(*) " + hql;	
		
		List<Video> l=videoDao.find(hql);
		dg.setTotal(videoDao.count(totalHql));
		dg.setRows(l);
		return dg;	
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql,Video video, Map<String, Object> params) {
		if (video!= null){
			params.put("houseIeee", video.getHouseIeee());
			params.put("ipcamip",video.getIpcamip());
			params.put("uuid", video.getUuid());
			hql += "where t.houseIeee =:houseIeee and t.ipcamip =:ipcamip and t.uuid =:uuid";
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
			log.info("定时保存video，缓存为空");
			return 0;
		}
		log.info("定时保存videoBegin......");
		final List<Video> tmpList = this.cache;
		this.cache = new ArrayList<Video>(this.cacheSize + 10);
//		log.info("Video saveorupdate begin, tmpList.size: " + tmpList.size());
		Thread th = new Thread() {
			@Override
			public void run() {
				StringBuilder sql = new StringBuilder("REPLACE INTO Video(id,houseIeee,roomId,localip,localport,ipcamip,ipcamport,cache,st,uuid,server,location,status,ipcamname) VALUES");
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				try {
					Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
					conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
							PropertiesUtil.getProperty("jdbc.user"),
							PropertiesUtil.getProperty("jdbc.password"));
					stmt = conn.createStatement();
				    for(Video vv: tmpList) {
				    	Video olddata =null;
				    	String hql = "select * from Video  where houseIeee = '"+vv.getHouseIeee()+"' and "+"ipcamip ='"+vv.getIpcamip()+"' and "+"uuid ='"+vv.getUuid()+"'";
		    			rs = stmt.executeQuery(hql);
		    			while(rs.next()) {
		    				olddata = convertMapToVideo(rs);
		    			}
				    	if(olddata!= null){
				    		BeanUtils.copyProperties(vv, olddata,new String[]{"id"});
				    		sql.append("(").append(olddata.getId()).append(",'").append(olddata.getHouseIeee()).append("',")
				    	    .append(olddata.getRoomId()).append(",'").append(olddata.getLocalip()).append("','").append(olddata.getLocalport()).append("','").append(olddata.getIpcamip())
				    	    .append("','").append(olddata.getIpcamport()).append("','").append(olddata.getCache()).append("','").append(olddata.getSt()).append("','").append(olddata.getUuid())
				    	    .append("','").append(olddata.getServer())
				    	    .append("','").append(olddata.getLocalport())
				    	    .append("','").append(olddata.getStatus())
				    	    .append("','").append(olddata.getIpcamname())
				    	    .append("'),");
//				    				roomCheck.getNodeDao().update(olddata);	
				    	}else{
				    		sql.append("(").append(vv.getId()).append(",'").append(vv.getHouseIeee()).append("',")
				    	    .append(vv.getRoomId()).append(",'").append(vv.getLocalip()).append("','").append(vv.getLocalport()).append("','").append(vv.getIpcamip())
				    	    .append("','").append(vv.getIpcamport()).append("','").append(vv.getCache()).append("','").append(vv.getSt()).append("','").append(vv.getUuid())
				    	    .append("','").append(vv.getServer())
				    	    .append("','").append(vv.getLocalport())
				    	    .append("','").append(vv.getStatus())
				    	    .append("','").append(vv.getIpcamname())
				    	    .append("'),");
//				    				roomCheck.getNodeDao().saveOrUpdate(nv);
				    		}
				    }
				    	String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
				    	int i = stmt.executeUpdate(sqlStr);
				    	log.info("Video saveorupdate end, result: " +i);
				   } catch(Exception e) {
					    log.info("save or update Video exception", e);
				   } finally {
//					    		cacheList.clear();
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
				log.info("定时保存videoEnd......");
//				log.info("定时保存video，缓存为:"+tmpList.size());
			}
		};
		th.setName("saveVideoThread");
		th.start();
		return 1;
	}
	
//	public int allDingshi() {
//		log.info("定时器执行开始");
//		dingshiSave();
//		modeServiceI.dingshiSave();
//		nodeServiceI.dingshiSave();
//		roomServiceI.dingshiSave();
//		deviceServiceI.saveCacheData();
////		deviceattributeServiceI.saveAttrCache();
//		devicewarnhistoryHouseidYearServiceI.savedingshiwarn();
////		deviceoperatehistoryHouseidYearServiceI.savedingshi();
//		irServiceI.dingshiaddUnMatch();
//		log.info("定时器执行结束");
//		return 1;
//		
//	}

}
