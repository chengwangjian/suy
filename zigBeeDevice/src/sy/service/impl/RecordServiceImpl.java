package sy.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Historyrecord;
import sy.model.Urlrecord;
import sy.service.RecordServiceI;
import sy.util.PropertiesUtil;


@Service("recordService")
public class RecordServiceImpl implements RecordServiceI{
	private BaseDaoI<Urlrecord> urlrecordDao;
	//private List<Urlrecord> cache = new ArrayList(100);
	private List<Historyrecord> cache2 = new ArrayList(100);
	private static final Logger LOGGER = Logger.getLogger(RecordServiceImpl.class);
	//private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.urlrecord"));
	private int cacheSize2 = Integer.parseInt(PropertiesUtil.getProperty("batch.historyrecord"));
	private Connection conn = null;
	
	
	public BaseDaoI<Urlrecord> getUrlrecordDao() {
		return urlrecordDao;
	}
	@Autowired
	public void setUrlrecordDao(BaseDaoI<Urlrecord> urlrecordDao) {
		this.urlrecordDao = urlrecordDao;
	}
	
	
	private Historyrecord convertMapToHistoryrecord(ResultSet rs) {
		Historyrecord oldHistoryrecord = new Historyrecord();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			oldHistoryrecord.setId(rs.getLong(1));
			oldHistoryrecord.setUrlId(rs.getInt(2));
			oldHistoryrecord.setUrl(rs.getString(3));
			oldHistoryrecord.setIpaddress(rs.getString(4));
			oldHistoryrecord.setParam(rs.getString(5));
			oldHistoryrecord.setOptime(rs.getString(6)==null?null:sdf.parse(rs.getString(6)));
		} catch (Exception e) {
			LOGGER.info("convertMapToHistoryrecord", e);
		}
		return oldHistoryrecord;
	}

	@Override
	public Historyrecord HistoryrecordCacheSave(final Historyrecord newdata) {
		cache2.add(newdata);
		LOGGER.info(" historyrecord cache2 size------>"+cache2.size());
		if(this.cache2.size()>cacheSize2){
			final List<Historyrecord> tmpList = this.cache2;
			this.cache2 = new ArrayList<Historyrecord>(this.cacheSize2 + 10);
			Thread th = new Thread() {
			    @Override
				public void run() {
			    	StringBuilder sql = new StringBuilder("REPLACE INTO Historyrecord(id,urlId,url,ipaddress,param,optime) VALUES");
			    	Statement stmt = null;	
			    	ResultSet rs = null;
			    	try {
			    		if(conn == null){
			    		Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
			    		conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
			    				PropertiesUtil.getProperty("jdbc.user"),
			    				PropertiesUtil.getProperty("jdbc.password"));
			    		}
			    		stmt = conn.createStatement();
			    		for(Historyrecord hr: tmpList) {
			    			Historyrecord olddata =null;
			    			String hql = "select * from Historyrecord  where urlId = "+hr.getUrlId()+" and url ='"+hr.getUrl()+"'and ipaddress = '"+hr.getIpaddress()+"'";
			    			LOGGER.info(hql);
			    			rs = stmt.executeQuery(hql);
	    					while(rs.next()) {
	    						olddata = convertMapToHistoryrecord(rs);
	    					}
	    					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    			if(olddata!= null){
			    				BeanUtils.copyProperties(hr, olddata,new String[]{"id"});
			    				sql.append("(").append(olddata.getId()).append(",").append(olddata.getUrlId()).append(",'")
			    				.append(olddata.getUrl()).append("','").append(olddata.getIpaddress()).append("','").append(olddata.getParam())
			    	    		.append("','").append(sdf.format(olddata.getOptime())).append("'),");
			    			}else{
			    				sql.append("(").append(hr.getId()).append(",").append(hr.getUrlId()).append(",'")
			    	    		.append(hr.getUrl()).append("','").append(hr.getIpaddress()).append("','").append(hr.getParam())
			    	    		.append("','").append(sdf.format(hr.getOptime())).append("'),");
			    			}
			    		}
			    		String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
			    		int i = stmt.executeUpdate(sqlStr);
			    	} catch(Exception e) {
			    		LOGGER.info("save or update historyrecord exception", e);
		    		} finally {
			    		try {
			    			if(stmt != null)
			    				stmt.close();
			    		} catch(Exception e) {
			    			LOGGER.info("jdbc close exception", e);
			    		}
			    	}
			    }
			};
			th.setName("saveRecord");
			th.start();
		}
			return newdata;
	}


	@Override
	public List<Urlrecord> findList(Urlrecord urlrecord) {
		StringBuffer hql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append(" from Urlrecord t where 1=1 ");
		if (urlrecord.getId() != 0) {
			hql.append(" and t.id = :id ");
			params.put("id", urlrecord.getId());
		}
		if (urlrecord.getUrl() != null) {
			hql.append(" and t.url = :url ");
			params.put("url", urlrecord.getUrl());
		}
		if (urlrecord.getParam() != null) {
			hql.append(" and t.param = :param ");
			params.put("param", urlrecord.getParam());
		}
		List<Urlrecord> t = urlrecordDao.find(hql.toString());
		if (t != null) {
			return t;
		};
		return null;
	}
	


	@Override
	public int HistoryrecordQuartzSave() {
		if(cache2.size()==0){
			LOGGER.info("定时保存historyrecord，缓存为空");
			return 0;
		}
		final List<Historyrecord> tmpList = this.cache2;
		this.cache2 = new ArrayList<Historyrecord>(this.cacheSize2 + 10);
    	LOGGER.info("historyrecord saveorupdate begin, tmpList.size: " + tmpList.size());
    	StringBuilder sql = new StringBuilder("REPLACE INTO Historyrecord(id,urlId,url,ipaddress,param,optime) VALUES");
    	Statement stmt = null;	 
    	ResultSet rs = null;
    	try {
   			if(conn == null){
    		Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
    		conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
    				PropertiesUtil.getProperty("jdbc.user"),
    				PropertiesUtil.getProperty("jdbc.password"));
    		}
    		stmt = conn.createStatement();
    		for(Historyrecord hr: tmpList) {	  
    			Historyrecord olddata =null;
    			String hql = "select * from Historyrecord where urlId = "+hr.getUrlId()+" and url ='"+hr.getUrl()+"'and ipaddress = '"+hr.getIpaddress()+"'";
				rs = stmt.executeQuery(hql);
				while(rs.next()) {
					olddata = convertMapToHistoryrecord(rs);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			if(olddata!= null){
    				BeanUtils.copyProperties(hr, olddata,new String[]{"id"});
    				sql.append("(").append(olddata.getId()).append(",").append(olddata.getUrlId()).append(",'")
    				.append(olddata.getUrl()).append("','").append(olddata.getIpaddress()).append("','").append(olddata.getParam())
    	    		.append("','").append(sdf.format(olddata.getOptime())).append("'),");
    			}else{
    				sql.append("(").append(hr.getId()).append(",'").append(hr.getUrlId()).append(",'")
    	    		.append(hr.getUrl()).append("','").append(hr.getIpaddress()).append("','").append(hr.getParam())
    	    		.append("','").append(sdf.format(hr.getOptime())).append("'),");
    			}
    		}
    		String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
    		int i = stmt.executeUpdate(sqlStr);
    	} catch(Exception e) {
		} finally {
    		try {
    			if(stmt != null)
    				stmt.close();
    		} catch(Exception e) {
    			LOGGER.info("jdbc close exception", e);
    		}
    	}
    	LOGGER.info("定时保存historyrecord，缓存为:"+tmpList.size());
		return 1;
	}
	
}
