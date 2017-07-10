package sy.service.impl;

import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.smarthome.domain.Warntypetable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import sy.dao.BaseDaoI;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.House;
import sy.service.DevicewarnhistoryHouseidYearServiceI;
import sy.service.HouseServiceI;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

@Service("devicewarnhistoryHouseidYearService")
public class DevicewarnhistoryHouseidYearServiceImpl implements DevicewarnhistoryHouseidYearServiceI, ApplicationContextAware {
	private static final Logger logger = Logger.getLogger(DevicewarnhistoryHouseidYearServiceImpl.class);
	private ApplicationContext ac = null;
	private BaseDaoI<DevicewarnhistoryHouseidYear> devicewarnhistoryHouseidYearDao;	
	private BaseDaoI<Map> mapDao;
//	private List<DevicewarnhistoryHouseidYear> cacheList = new ArrayList<DevicewarnhistoryHouseidYear>(100);
	private Map<String, List<DevicewarnhistoryHouseidYear>> cacheList = new HashMap<String, List<DevicewarnhistoryHouseidYear>>();
	private int cacheCount = 0; //批量计数器
	private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.devicewarnhistoryHouseidYear"));
	private int cacheSmsCount = 0; //sms批量计数器
	private int cacheSmsSize = Integer.parseInt(PropertiesUtil.getProperty("batch.devicewarnhistorySms"));
	private List<DevicewarnhistoryHouseidYear> smsWarnList = new ArrayList<DevicewarnhistoryHouseidYear>();
	private HouseServiceI houseService;
	private BaseDaoI<Warntypetable> warntypetablebaeBaseDaoI;
	
	public BaseDaoI<Warntypetable> getWarntypetablebaeBaseDaoI() {
		return warntypetablebaeBaseDaoI;
	}
	@Autowired
	public void setWarntypetablebaeBaseDaoI(
			BaseDaoI<Warntypetable> warntypetablebaeBaseDaoI) {
		this.warntypetablebaeBaseDaoI = warntypetablebaeBaseDaoI;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<DevicewarnhistoryHouseidYear> getDevicewarnhistoryHouseidYearDao() {
		return devicewarnhistoryHouseidYearDao;
	}

	@Autowired
	public void setDevicewarnhistoryHouseidYearDao(BaseDaoI<DevicewarnhistoryHouseidYear> devicewarnhistoryHouseidYearDao) {
		this.devicewarnhistoryHouseidYearDao = devicewarnhistoryHouseidYearDao;
	}

	public HouseServiceI getHouseService() {
		return houseService;
	}

	@Autowired
	public void setHouseService(HouseServiceI houseService) {
		this.houseService = houseService;
	}
	@Override
	public DevicewarnhistoryHouseidYear save(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
		/*DevicewarnhistoryHouseidYear t = new DevicewarnhistoryHouseidYear();
		BeanUtils.copyProperties(devicewarnhistoryHouseidYear, t, new String[] { "pwd" });*/
		/*t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(devicewarnhistoryHouseidYear.getPwd()));*/
		devicewarnhistoryHouseidYearDao.save(devicewarnhistoryHouseidYear);
//		BeanUtils.copyProperties(t, devicewarnhistoryHouseidYear);
		return devicewarnhistoryHouseidYear;
	}

	@Override
	public DevicewarnhistoryHouseidYear login(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(devicewarnhistoryHouseidYear.getPwd()));
//		params.put("name", devicewarnhistoryHouseidYear.getName());
		DevicewarnhistoryHouseidYear t = devicewarnhistoryHouseidYearDao.get("from DevicewarnhistoryHouseidYear t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return devicewarnhistoryHouseidYear;
		}
		return null;
	}
	/**
	 * 插入缓存数据
	 * @param sqlStr
	 * @param stmt
	 */
	public void tmpInsertData(String sqlStr, Statement stmt) {
		if(StringUtils.isNotBlank(sqlStr)) {
//			logger.info("DevicewarnhistoryHouseidYear Repeat saveorupdate begin");
			try {
				int i = stmt.executeUpdate(sqlStr);
//				logger.info("DevicewarnhistoryHouseidYear Repeat saveorupdate end, result: " + i);
			} catch(Exception e) {
				logger.info("save or update DevicewarnhistoryHouseidYear Repeat exception", e);
			} 
		}
	}
	/**
	 * 定时保存报警信息
	 * @return
	 */
	@Override
	public int savedingshiwarn(){
		if(cacheList.size()==0){
			logger.info("定时保存报警历史，缓存为空");
			return 0;
		}
		logger.info("定时保存报警历史begin......");
		cacheCount =0;
		final Map<String, List<DevicewarnhistoryHouseidYear>> tmpList = this.cacheList;
		this.cacheList = new HashMap<String, List<DevicewarnhistoryHouseidYear>>(this.cacheSize + 10);
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		Thread th = new Thread() {
    		@Override
			public void run() {
//    			logger.info("DevicewarnhistoryHouseidYear saveorupdate begin, tmpList.size: " + tmpList.size());
    			Connection conn = null;
    			Statement stmt = null;
    			try {
    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
    						PropertiesUtil.getProperty("jdbc.user"),
    						PropertiesUtil.getProperty("jdbc.password"));
    				stmt = conn.createStatement();    				
    				Iterator<String> itor = tmpList.keySet().iterator();
    				while(itor.hasNext()) {
    					String houseIeee = itor.next();
						List<DevicewarnhistoryHouseidYear> dList = tmpList.get(houseIeee);    						
						if(dList != null && !dList.isEmpty()) {
							String tableName = "deviceWarnHistory_" + houseIeee + "_" + year;
							StringBuilder sql = new StringBuilder("insert into ");
			    			sql.append(tableName);
			    			sql.append("(houseIeee,room_Id,zone_ieee,zone_ep,cie_ieee,cie_ep,w_mode,w_description,warndatetime,message_id,name) VALUES");
			    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    			for(DevicewarnhistoryHouseidYear dv: dList) {	    				
			    				sql.append("('").append(dv.getHouseIeee()).append("',").append(dv.getRoomId()).append(",'")
			    				.append(dv.getZoneIeee()).append("','").append(dv.getZoneEp()).append("','").append(dv.getCieIeee())
			    				.append("','").append(dv.getCieEp()).append("','").append(dv.getWMode()).append("','")
			    				.append(dv.getWDescription()).append("','").append(sdf.format(dv.getWarndatetime())).append("',").append(dv.getMessage_id())
			    				.append(",'").append(StringUtils.isBlank(dv.getName())? "":dv.getName()).append("'").append("),");	    				
			    			}
    		    			String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
	    					try {		    		    			
	    						stmt.executeUpdate(sqlStr);
	    					} catch(Exception e) {
	    						String msg = e.getMessage();
	    						//当表不存在时创建并插入数据
	    	    				if(msg.indexOf(tableName.toLowerCase()) != -1) {
	    	    					houseService.addDeviceWarnHistoryTable(houseIeee);
	    	    					tmpInsertData(sqlStr, stmt);
	    	    				}
	    	    				else {
	    	    					logger.info("save or update DevicewarnhistoryHouseidYear " + houseIeee + " exception", e);
	    	    				}
	    					}	    					
						}
    				}
//    				logger.info("DevicewarnhistoryHouseidYear saveorupdate end");
    			} catch(Exception e) {
    				logger.info("save or update DevicewarnhistoryHouseidYear exception", e);
    			} finally {
    				try {
    					if(stmt != null)
    						stmt.close();
    					if(conn != null)
    						conn.close();
    				} catch(Exception e) {
    					logger.info("jdbc close exception", e);
    				}
    			}
    			logger.info("定时保存报警历史end......");
    		}
    	};
    	th.setName("saveWarnHistory");
    	th.start();
    	return 1;
	}
	
	
	
	
	
	@Override
	public int saveSql(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
		++cacheCount;
		String houseIeee = devicewarnhistoryHouseidYear.getHouseIeee();
		List<DevicewarnhistoryHouseidYear> tmpCacheList = cacheList.get(houseIeee); 
//		devicewarnhistoryHouseidYear.setWarndatetime(new Date());
		if(tmpCacheList == null) {
			tmpCacheList = new ArrayList<DevicewarnhistoryHouseidYear>(this.cacheSize + 10);
			tmpCacheList.add(devicewarnhistoryHouseidYear);
			cacheList.put(houseIeee, tmpCacheList);
		}
		else {
			tmpCacheList.add(devicewarnhistoryHouseidYear);
		}
		//批量更新 this.cacheSize
		if (cacheCount >= this.cacheSize) {
			savedingshiwarn();
//			logger.info("定时保存报警历史begin......");
//			cacheCount = 0;
//			final Map<String, List<DevicewarnhistoryHouseidYear>> tmpList = this.cacheList;
//			this.cacheList = new HashMap<String, List<DevicewarnhistoryHouseidYear>>(this.cacheSize + 10);
//			final int year = Calendar.getInstance().get(Calendar.YEAR);
////			new Thread() {
////				public void tmpInsertData(String sqlStr, Statement stmt) {
////					if(StringUtils.isNotBlank(sqlStr)) {
////						logger.info("DevicewarnhistoryHouseidYear Repeat saveorupdate begin");
////		    			try {
////		    				int i = stmt.executeUpdate(sqlStr);
////		    				logger.info("DevicewarnhistoryHouseidYear Repeat saveorupdate end, result: " + i);
////		    			} catch(Exception e) {
////		    				logger.info("save or update DevicewarnhistoryHouseidYear Repeat exception", e);
////		    			} 
////					}
////				}
////				
////	    		public void run() {
////	    			logger.info("DevicewarnhistoryHouseidYear saveorupdate begin, tmpList.size: " + tmpList.size());
//	    			Connection conn = null;
//	    			Statement stmt = null;
//	    			try {
//	    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
//	    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
//	    						PropertiesUtil.getProperty("jdbc.user"),
//	    						PropertiesUtil.getProperty("jdbc.password"));
//	    				stmt = conn.createStatement();	    				
//	    				Iterator<String> itor = tmpList.keySet().iterator();
//	    				while(itor.hasNext()) {
//	    					String houseIEEE = itor.next();
//    						List<DevicewarnhistoryHouseidYear> dList = tmpList.get(houseIEEE);    						
//    						if(dList != null && !dList.isEmpty()) {
//    							String tableName = "deviceWarnHistory_" + houseIEEE + "_" + year;
//    							StringBuilder sql = new StringBuilder("insert into ");
//    			    			sql.append(tableName);
//    			    			sql.append("(houseIeee,room_Id,zone_ieee,zone_ep,cie_ieee,cie_ep,w_mode,w_description,warndatetime,message_id) VALUES");
//    			    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    			    			for(DevicewarnhistoryHouseidYear dv: dList) {	    				
//    			    				sql.append("('").append(dv.getHouseIeee()).append("',").append(dv.getRoomId()).append(",'")
//    			    				.append(dv.getZoneIeee()).append("','").append(dv.getZoneEp()).append("','").append(dv.getCieIeee())
//    			    				.append("','").append(dv.getCieEp()).append("','").append(dv.getWMode()).append("','")
//    			    				.append(dv.getWDescription()).append("','").append(sdf.format(dv.getWarndatetime())).append("',").append(dv.getMessage_id()).append(" ),");	    				
//    			    			}
//	    		    			String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
//		    					try {		    		    			
//		    						stmt.executeUpdate(sqlStr);
//		    					} catch(Exception e) {
//		    						String msg = e.getMessage();
//		    						//当表不存在时创建并插入数据
//		    	    				if(msg.indexOf(tableName.toLowerCase()) != -1) {
//		    	    					houseService.addDeviceWarnHistoryTable(houseIEEE);
//		    	    					tmpInsertData(sqlStr, stmt);
//		    	    				}
//		    	    				else {
//		    	    					logger.info("save or update DevicewarnhistoryHouseidYear " + houseIEEE + " exception", e);
//		    	    				}
//		    					}	    					
//    						}
//	    				}
//	    				logger.info("DevicewarnhistoryHouseidYear saveorupdate end");
//	    			} catch(Exception e) {
//	    				logger.info("save or update DevicewarnhistoryHouseidYear exception", e);
//	    			} finally {
//	    				try {
//	    					if(stmt != null)
//	    						stmt.close();
//	    					if(conn != null)
//	    						conn.close();
//	    				} catch(Exception e) {
//	    					logger.info("jdbc close exception", e);
//	    				}
//	    			}
//	    			logger.info("定时保存报警历史end......");
////	    		}
////	    	}.start();
		}
		return 1;
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		String tableName = "deviceWarnHistory_" + devicewarnhistoryHouseidYear.getHouseIeee() + "_" + year;
//		String sql = "insert into " + tableName + "(houseIeee,room_Id,zone_ieee,zone_ep,cie_ieee,cie_ep,w_mode,w_description,warndatetime) values(:houseIeee," +
//				":roomId,:zone_ieee,:zone_ep,:cie_ieee,:cie_ep,:w_mode,:w_description,:warndatetime)";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("houseIeee", devicewarnhistoryHouseidYear.getHouseIeee());
//		params.put("roomId", devicewarnhistoryHouseidYear.getRoomId());
//		params.put("zone_ieee", devicewarnhistoryHouseidYear.getZoneIeee());
//		params.put("zone_ep", devicewarnhistoryHouseidYear.getZoneEp());
//		params.put("cie_ieee", devicewarnhistoryHouseidYear.getCieIeee());
//		params.put("cie_ep", devicewarnhistoryHouseidYear.getCieEp());
//		params.put("w_mode", devicewarnhistoryHouseidYear.getWMode());
//		params.put("w_description", devicewarnhistoryHouseidYear.getWDescription());
//		/*if (devicewarnhistoryHouseidYear.getWarndatetime() != null) { //该时间需要推送
//			params.put("warndatetime", devicewarnhistoryHouseidYear.getWarndatetime());
//		} else {
//			params.put("warndatetime", new Date());
//		}*/
//		params.put("warndatetime", new Date());
//		return devicewarnhistoryHouseidYearDao.executeSql2(sql, params);
	}
	
	@Override
	public List<DevicewarnhistoryHouseidYear> findList(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + devicewarnhistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName).append(" t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (devicewarnhistoryHouseidYear.getHouseIeee() != null) {
			sql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", devicewarnhistoryHouseidYear.getHouseIeee());
		}
		if (devicewarnhistoryHouseidYear.getRoomId() != 0) {
			sql.append("and t.room_Id = :roomId ");
			params.put("roomId", devicewarnhistoryHouseidYear.getRoomId());
		}
		if (devicewarnhistoryHouseidYear.getZoneIeee() != null) {
			sql.append("and t.zone_ieee = :zone_ieee ");
			params.put("zone_ieee", devicewarnhistoryHouseidYear.getZoneIeee());
		}
		if (devicewarnhistoryHouseidYear.getZoneEp() != null) {
			sql.append("and t.zone_ep = :zone_ep ");
			params.put("zone_ep", devicewarnhistoryHouseidYear.getZoneEp());
		}
		if (devicewarnhistoryHouseidYear.getCieIeee() != null) {
			sql.append("and t.cie_ieee = :cie_ieee ");
			params.put("cie_ieee", devicewarnhistoryHouseidYear.getCieIeee());
		}
		if (devicewarnhistoryHouseidYear.getCieEp() != null) {
			sql.append("and t.cie_ep = :cie_ep ");
			params.put("cie_ep", devicewarnhistoryHouseidYear.getCieEp());
		}
		if (devicewarnhistoryHouseidYear.getWMode() != null) {
			sql.append("and t.w_mode = :w_mode ");
			params.put("w_mode", devicewarnhistoryHouseidYear.getWMode());
		}
		if (devicewarnhistoryHouseidYear.getMinWarndatetime() != null) {
			sql.append("and t.warndatetime >= :minwarndatetime ");
			params.put("minwarndatetime", devicewarnhistoryHouseidYear.getMinWarndatetime());
		}
		if (devicewarnhistoryHouseidYear.getMaxWarndatetime() != null) {
			sql.append("and t.warndatetime <= :maxwarndatetime ");
			params.put("maxwarndatetime", devicewarnhistoryHouseidYear.getMaxWarndatetime());
		}	
		List<DevicewarnhistoryHouseidYear> t = devicewarnhistoryHouseidYearDao.findSql(sql.toString(), params, DevicewarnhistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
		DataGrid dg = new DataGrid();
		String hql = "from DevicewarnhistoryHouseidYear t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(devicewarnhistoryHouseidYear, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(devicewarnhistoryHouseidYear, hql);
		List<DevicewarnhistoryHouseidYear> l = devicewarnhistoryHouseidYearDao.find(hql, params, devicewarnhistoryHouseidYear.getPage(), devicewarnhistoryHouseidYear.getRows());
		List<DevicewarnhistoryHouseidYear> nl = new ArrayList<DevicewarnhistoryHouseidYear>();
		changeModel(l, nl);
		dg.setTotal(devicewarnhistoryHouseidYearDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<DevicewarnhistoryHouseidYear> l, List<DevicewarnhistoryHouseidYear> nl) {
		if (l != null && l.size() > 0) {
			for (DevicewarnhistoryHouseidYear t : l) {
				DevicewarnhistoryHouseidYear u = new DevicewarnhistoryHouseidYear();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear, String hql) {
		/*if (devicewarnhistoryHouseidYear.getSort() != null) {
			hql += " order by " + devicewarnhistoryHouseidYear.getSort() + " " + devicewarnhistoryHouseidYear.getOrder();
		}*/
		return hql;
	}

	private String addWhere(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear, String hql, Map<String, Object> params) {
		/*if (devicewarnhistoryHouseidYear.getName() != null && !devicewarnhistoryHouseidYear.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + devicewarnhistoryHouseidYear.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete DevicewarnhistoryHouseidYear t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		devicewarnhistoryHouseidYearDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}

	@Override
	public Map findwarnListory(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
		savedingshiwarn();//列表显示前执行定时保存
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "deviceWarnHistory_" + devicewarnhistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer hww = new StringBuffer();
		Map<String, Object> ppt = new HashMap<String, Object>();
		hww.append("SELECT  isTableExist('"+ tableName +"') as sun  "); 		
 		List<Map> l = mapDao.executeSql(hww.toString()); 
 		String tt = l.get(0).get("sun") .toString();
 		if(tt.equals("1")){		
		
		StringBuffer sql = new StringBuffer();
//		sql.append("select a.*,b.roomName,c.deviceName ");
		sql.append("select a.message_id,a.zone_ieee,a.zone_ep,a.w_mode,a.warndatetime,b.roomName,c.deviceName,")
		//7,8,9,10,11,12添加要用英文显示的w_mode编码就行了
		/*.append("CASE WHEN d.w_mode in (0,1,2,3,4,6,14,15,16) THEN d.ChinesewarnType ")
		.append("WHEN d.w_mode IN (7,8,9,10,11,12) THEN REPLACE(d.ChinesewarnType,'<家人名称>',a.w_description) ")
		.append("WHEN d.w_mode IN (5,13,44,46) THEN REPLACE(d.ChinesewarnType,'<房间>-<设备>',CONCAT(b.roomName,'-',c.deviceName)) ")
		.append("WHEN d.w_mode IN (47) THEN REPLACE(d.ChinesewarnType,'<用户名称>',a.name) ")
		.append("ELSE a.w_description END w_description , ")
		.append("CASE WHEN d.w_mode in (0,1,2,3,4,6,14,15,16) THEN d.EnglishwarnType ")
		.append("WHEN d.w_mode IN (7,8,9,10,11,12) THEN REPLACE(d.EnglishwarnType,'<family name>',a.w_description) ")
		.append("WHEN d.w_mode IN (5,13,44,46) THEN REPLACE(d.EnglishwarnType,'<room>-<device>',CONCAT(b.roomName,'-',c.deviceName)) ")
		.append("WHEN d.w_mode IN (47) THEN REPLACE(d.EnglishwarnType,'<user name>',a.name) ")
		.append("ELSE a.w_description END w_descriptionEn ")*/
		.append("CASE WHEN d.w_mode in (0,1,2,3,4,6,14,15,16) THEN d.ChinesewarnType ")
		.append("WHEN d.w_mode IN (7,8,9,10,11,12) THEN REPLACE(d.ChinesewarnType,'<家人名称>,','') ")
		.append("WHEN d.w_mode IN (5,13,44,46) THEN REPLACE(d.ChinesewarnType,'<房间>-<设备>','') ")
		.append("WHEN d.w_mode IN (47) THEN REPLACE(d.ChinesewarnType,'<用户名称>','') ")
		.append("WHEN a.w_mode in(70) THEN REPLACE(a.w_description,a.w_description,'自定义')")
		.append("ELSE a.w_description END w_description , ")
		.append("CASE WHEN d.w_mode in (0,1,2,3,4,6,14,15,16) THEN d.EnglishwarnType ")
		.append("WHEN d.w_mode IN (7,8,9,10,11,12) THEN REPLACE(d.EnglishwarnType,'<family name>,','') ")
		.append("WHEN d.w_mode IN (5,13,44,46) THEN REPLACE(d.EnglishwarnType,'<room>-<device>','') ")
		.append("WHEN d.w_mode IN (47) THEN REPLACE(d.EnglishwarnType,'<user name>','') ")
		.append("WHEN a.w_mode in(70) THEN REPLACE(a.w_description,a.w_description,'Custom')")
		.append("ELSE a.w_description END w_descriptionEn ")
		.append("from ").append(tableName).append(" a left join room b on a.room_id = b.roomId and a.houseIEEE = b.houseIEEE ")
		.append("LEFT JOIN device c on a.zone_ieee = c.ieee and a.zone_ep = c.ep and a.houseIEEE = c.houseIEEE LEFT JOIN warntypetable d ON a.w_mode=d.w_mode ")
		.append("where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (devicewarnhistoryHouseidYear.getHouseIeee() != null){
			sql.append("and a.houseIEEE = :houseIeee ");
			params.put("houseIeee", devicewarnhistoryHouseidYear.getHouseIeee());
		}
		/*if (devicewarnhistoryHouseidYear.getZoneIeee() != null) {
			sql.append("and a.zone_ieee =:zoneIeee ");
			params.put("zoneIeee", devicewarnhistoryHouseidYear.getZoneIeee());
		}
		if (devicewarnhistoryHouseidYear.getZoneEp() != null) {
			sql.append("and a.zone_ep =:zoneEp ");
			params.put("zoneEp", devicewarnhistoryHouseidYear.getZoneEp());
		}*/
		
		if(!devicewarnhistoryHouseidYear.getZoneIeee().equals(devicewarnhistoryHouseidYear.getHouseIeee()) && StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getZoneIeee())) {
			sql.append(" and a.zone_ieee =:zoneIeee ");
			params.put("zoneIeee", devicewarnhistoryHouseidYear.getZoneIeee());
		}
		if(!devicewarnhistoryHouseidYear.getZoneIeee().equals(devicewarnhistoryHouseidYear.getHouseIeee()) && StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getZoneEp())) {
			sql.append(" and a.zone_ep =:zoneEp ");
			params.put("zoneEp", devicewarnhistoryHouseidYear.getZoneEp());
		}
		
		if(StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getStarttime())) {
			sql.append(" and a.warndatetime between '").append(devicewarnhistoryHouseidYear.getStarttime()).append("' and '").append(devicewarnhistoryHouseidYear.getEndtime()).append("'");
		}
		/*if (devicewarnhistoryHouseidYear.getWMode() != null) {
			sql.append("and a.w_mode = :WMode ");
			params.put("WMode", devicewarnhistoryHouseidYear.getWMode());
		}*/
		if(StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getWMode()) && !devicewarnhistoryHouseidYear.getWMode().equals("-1")) {
			sql.append(" and a.w_mode =:WMode ");
			params.put("WMode", devicewarnhistoryHouseidYear.getWMode());
		}

		if (devicewarnhistoryHouseidYear.getWDescription() != null) {
			sql.append("and a.w_description like :WDescription ");
			params.put("WDescription", "%"+devicewarnhistoryHouseidYear.getWDescription()+"%");
		}
		if (devicewarnhistoryHouseidYear.getRoomId() != 0) {
			sql.append("and a.room_id = :roomId ");
			params.put("roomId", devicewarnhistoryHouseidYear.getRoomId());
		}
		if (devicewarnhistoryHouseidYear.getRoomName() != null) {
			sql.append("and b.roomName like :roomName ");
			params.put("roomName", "%"+devicewarnhistoryHouseidYear.getRoomName()+"%");
		}
		/*if (devicewarnhistoryHouseidYear.getDeviceName() != null) {
			sql.append("and c.deviceName like :deviceName ");
			params.put("deviceName", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
		}*/
		//报警历史 zoneIeee、房间区域模糊搜索
		if(devicewarnhistoryHouseidYear.getHouseIeee().equals(devicewarnhistoryHouseidYear.getZoneIeee()) && StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getDeviceName())){
			sql.append("and ((c.deviceName like :deviceName) or (a.zone_ieee like :zoneIeee)) ");
			params.put("deviceName", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
			//params.put("roomName", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
			params.put("zoneIeee", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
			//params.put("zoneEp", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
			//params.put("WDescription", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
		}
		if(StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getOrderBy()))
			sql.append(" order by ").append(devicewarnhistoryHouseidYear.getOrderBy()).append(" ");
		else
			sql.append(" order by a.warndatetime desc "); 
		if (devicewarnhistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + devicewarnhistoryHouseidYear.getStartRow() + "," + devicewarnhistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000"); 
		}
		logger.info("输出的报警历史列表sql语句--》："+ sql);
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		
		if(list != null && list.size() > 0) {
			rt.put("listory",list);	
		} else {
			rt.put("listory",null);	
		}
		return rt;
 		}
 		logger.info("没有相应的报警历史列表--》："+ tableName);
 		rt.put("result", -3);
 		rt.put("tableName", tableName);
 		rt.put("listory",null);	
 		return rt;
	}

	@Override
	public Map getwarnListory(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "deviceWarnHistory_" + devicewarnhistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as total from (select a.* from ").append(tableName).append(" a left join room b on a.room_id = b.roomId and a.houseIEEE = b.houseIEEE " +
				"LEFT JOIN device c on a.zone_ieee = c.ieee and a.zone_ep = c.ep and a.houseIEEE = c.houseIEEE  where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (devicewarnhistoryHouseidYear.getHouseIeee() != null) {
			sql.append("and a.houseIeee = :houseIeee ");
			params.put("houseIeee", devicewarnhistoryHouseidYear.getHouseIeee());
		}
		if (devicewarnhistoryHouseidYear.getZoneIeee() != null) {
			sql.append("and a.zone_ieee like :zoneIeee ");
			params.put("zoneIeee", "%"+devicewarnhistoryHouseidYear.getZoneIeee()+"%");
		}
		if (devicewarnhistoryHouseidYear.getZoneEp() != null) {
			sql.append("and a.zone_ep like :zoneEp ");
			params.put("zoneEp", "%"+devicewarnhistoryHouseidYear.getZoneEp()+"%");
		}*/
		if(StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getHouseIeee())) {
			sql.append(" and a.houseIEEE =:houseIeee ");
			params.put("houseIeee", devicewarnhistoryHouseidYear.getHouseIeee());
		}
		/*if(StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getZoneIeee())) {
			sql.append(" and a.zone_ieee =:zoneIeee ");
			params.put("zoneIeee", devicewarnhistoryHouseidYear.getZoneIeee());
		}
		if(StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getZoneEp())) {
			sql.append(" and a.zone_ep =:zoneEp ");
			params.put("zoneEp", devicewarnhistoryHouseidYear.getZoneEp());
		}*/
		if(!devicewarnhistoryHouseidYear.getZoneIeee().equals(devicewarnhistoryHouseidYear.getHouseIeee()) && StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getZoneIeee())) {
			sql.append(" and a.zone_ieee =:zoneIeee ");
			params.put("zoneIeee", devicewarnhistoryHouseidYear.getZoneIeee());
		}
		if(!devicewarnhistoryHouseidYear.getZoneIeee().equals(devicewarnhistoryHouseidYear.getHouseIeee()) && StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getZoneEp())) {
			sql.append(" and a.zone_ep =:zoneEp ");
			params.put("zoneEp", devicewarnhistoryHouseidYear.getZoneEp());
		}
		if(StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getWMode()) && !devicewarnhistoryHouseidYear.getWMode().equals("-1")) {
			sql.append(" and a.w_mode =:WMode ");
			params.put("WMode", devicewarnhistoryHouseidYear.getWMode());
		}
		if(StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getStarttime())) {
			sql.append(" and a.warndatetime between '").append(devicewarnhistoryHouseidYear.getStarttime()).append("' and '").append(devicewarnhistoryHouseidYear.getEndtime()).append("'");
		}
		/*if (devicewarnhistoryHouseidYear.getWMode() != null) {
			sql.append("and a.w_mode = :WMode ");
			params.put("WMode", devicewarnhistoryHouseidYear.getWMode());
		}*/
		if (devicewarnhistoryHouseidYear.getWDescription() != null) {
			sql.append("and a.w_description like :WDescription ");
			params.put("WDescription", "%"+devicewarnhistoryHouseidYear.getWDescription()+"%");
		}
		if (devicewarnhistoryHouseidYear.getRoomId() != 0) {
			sql.append("and a.room_id = :roomId ");
			params.put("roomId", devicewarnhistoryHouseidYear.getRoomId());
		}
		if (devicewarnhistoryHouseidYear.getRoomName() != null) {
			sql.append("and b.roomName like :roomName ");
			params.put("roomName", "%"+devicewarnhistoryHouseidYear.getRoomName()+"%");
		}
		
		/*if (devicewarnhistoryHouseidYear.getDeviceName() != null) {
			sql.append("and c.deviceName like :deviceName ");
			params.put("deviceName", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
		}*/
		//报警历史 zoneIeee、房间区域模糊搜索
		if(devicewarnhistoryHouseidYear.getZoneIeee().equals(devicewarnhistoryHouseidYear.getHouseIeee()) && StringUtils.isNotBlank(devicewarnhistoryHouseidYear.getDeviceName())){
			sql.append("and ((c.deviceName like :deviceName) or (a.zone_ieee like :zoneIeee)) ");
			params.put("deviceName", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
			params.put("zoneIeee", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
		}
		sql.append(" order by a.warndatetime desc) m "); 
//		sql.append(" order by a.warndatetime desc limit 3700) m "); 
		logger.info("输出的报警历史总是sql语句是===》："+ sql);
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		
		if(list != null && list.size() > 0) {
			rt.put("total",list);	
		} else
			rt.put("total",null);
		
		return rt;
	}

	@Override
	public List<Map> getHistoryWarnDataPage(
			String houseIeee, String beginDateTime, String endDateTime,String start,String count) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + houseIeee + "_" + year;
		StringBuffer sql = new StringBuffer();
		try {		
			sql.append(" SELECT houseIEEE as houseIeee,room_id as roomId,daemonDeviceId,source_id,deviceType,zone_ieee,zone_ep,cie_ieee,cie_ep,w_mode,w_description,sendState,message_id,warndatetime as time FROM ").append(tableName)
			   .append(" where warndatetime>= ").append("'").append(beginDateTime).append("'")
			   .append(" and warndatetime<= ").append("'").append(endDateTime).append("'");
			sql.append(" LIMIT ").append(start).append(",").append(count);
			//logger.info(sql);
		} catch (Exception e) {
		}
		List<Map> t = mapDao.executeSql(sql.toString());
		if (t != null) {
			return t;
		}
		return null;
	}
	@Override
	public Map getHistoryWarnDataTotalCount(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		int year = Calendar.getInstance().get(Calendar.YEAR);
	    String tableName = "deviceWarnHistory_" + paramMap.get("houseIeee") + "_" + year;
		//String tableName="devicewarnhistory_00137a000007a65b_2015";
		String sql="SELECT count(*) AS total FROM  "+tableName+" WHERE houseIEEE=:houseIeee";
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("houseIeee", paramMap.get("houseIeee"));
		List<Map> list=this.mapDao.executeSql(sql, params);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	@Override
	public List<Map> getHistoryWarnData(String houseIeee, String beginDateTime,
			String endDateTime) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + houseIeee + "_" + year;
		StringBuffer sql = new StringBuffer();
		try {		
			sql.append(" SELECT houseIEEE as houseIeee,room_id as roomId,daemonDeviceId,source_id,deviceType,zone_ieee,zone_ep,cie_ieee,cie_ep,w_mode,w_description,sendState,message_id,warndatetime as time,name FROM ").append(tableName)
			   .append(" where warndatetime>= ").append("'").append(beginDateTime).append("'")
			   .append(" and warndatetime<= ").append("'").append(endDateTime).append("'");
		} catch (Exception e) {
		}
		List<Map> t = mapDao.executeSql(sql.toString());
		if (t != null) {
			return t;
		}
		return null;
	}

	  @Override
	public List<Map> findListPhoneno(DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
	    StringBuffer sql = new StringBuffer();
	    String tableName = "phoneno";
	    sql.append("select * from ").append(tableName).append(" t left join notewarncontrol  n on t.houseIEEE = n.houseIEEE where 1=1 and n.state='1' ");
	    Map params = new HashMap();
	    if (devicewarnhistoryHouseidYear.getHouseIeee() != null){
	      sql.append("and t.houseIeee = :houseIeee ");
	      params.put("houseIeee", devicewarnhistoryHouseidYear.getHouseIeee());
	    }
	    List t = this.mapDao.executeSql(sql.toString(), params);
	    if (t != null) {
	      return t;
	    }
	    return null;
	  }
	@Override
	public int insertSMS(String content, String houseIeee, String phonenumber,String state,
			DevicewarnhistoryHouseidYear devicewarnhistoryHouseidYear) {
		String sql = "insert into messagehistory(messageid,phonenumber,content,houseIeee,state,SMSstate,emailState) values(:messageid,:phonenumber,:content,:houseIeee,:state) ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("messageid", devicewarnhistoryHouseidYear.getId());
		params.put("phonenumber", phonenumber);
		params.put("content", content);
		params.put("houseIeee", houseIeee);
		params.put("state", state);
		int i = mapDao.executeSql2(sql, params);
		return i;
	}
	
	@Override
	public List<Map> getMessPhone(String houseIEEE) {
		StringBuilder sql = new StringBuilder("select a.state,b.phoneNO from notewarncontrol a left join phoneno b on a.houseIEEE=b.houseIEEE ");
		sql.append(" where a.houseIEEE=:houseIEEE and a.StartDate <= now() and a.EndDate >= now()");
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("houseIEEE", houseIEEE);
		List<Map> rList = mapDao.executeSql(sql.toString(), pMap);
		if(rList.isEmpty())
			return null;
		return rList;
	}
	
	@Override
	public List<Map> getMessEmail(String houseIEEE) {
		StringBuilder sql = new StringBuilder("select a.state,b.warnEmail from notewarncontrol a left join warnemail b on a.houseIEEE=b.houseIEEE ");
		sql.append(" where a.houseIEEE=:houseIEEE and a.StartDate <= now() and a.EndDate >= now()");
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("houseIEEE", houseIEEE);
		List<Map> rList = mapDao.executeSql(sql.toString(), pMap);
		if(rList.isEmpty())
			return null;
		return rList;
	}
	
	@Override
	public int batchSave(String sql) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
			conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
					PropertiesUtil.getProperty("jdbc.user"),
					PropertiesUtil.getProperty("jdbc.password"));
			stmt = conn.createStatement();	    				
			return stmt.executeUpdate(sql);
		} catch(Exception e) {
			logger.info("batchSave warn message history exception", e);
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch(Exception e) {
				logger.info("jdbc close exception", e);
			}
		}
		return 0;
	}
	
	@Override
	public void sendemail(String msg, String toEmail) throws Exception {
		String subject = "预警提示";
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
		senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
		// 密码解密
		senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
		// 建立HTML邮件消息
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// true表示开始附件模式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
		// 设置收件人，寄件人
		messageHelper.setTo(toEmail);
		messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
		messageHelper.setSubject(subject);
		messageHelper.setText(msg, true);
		senderImpl.send(mailMessage);	
	}
	
	@Override
	public Map getNewWarnHistory(List warnList) throws Exception {
		//组装houseIEEE、message_id字符串
		if(warnList != null) {
//			List<Map> newWarnList = new ArrayList<Map>();
			StringBuilder hSql = new StringBuilder();
			StringBuilder mSql = new StringBuilder();
			Iterator warnIterator = warnList.iterator();
			while (warnIterator.hasNext()) {
				Map singleWarnMap = (Map) warnIterator.next();
				singleWarnMap.put("SMSstate", 0);
				singleWarnMap.put("emailState", 0);
				hSql.append("'").append(singleWarnMap.get("houseIEEE")).append("',");
				mSql.append(singleWarnMap.get("message_id")).append(",");
			}
			String hSqlStr = "(" + hSql.deleteCharAt(hSql.length() - 1) + ")";
			String mSqlStr = "(" + mSql.deleteCharAt(mSql.length() - 1) + ")";
			//根据houseIEEE,message_id查询短信历史记录
			StringBuilder sql = new StringBuilder("select a.houseIeee,a.messageid,a.type from messagehistory a ");
			sql.append("where a.houseIeee in ").append(hSqlStr).append(" and ");
			sql.append("a.messageid in ").append(mSqlStr).append(" and ");
			sql.append("a.type in (0, 2)");
			List<Map> hisList = mapDao.executeSql(sql.toString());
			//将短信历史记录列表hisList转换为hisCacheMap，便于后面判断是否发送短信、是否发送邮件
			Map hisCacheMap = new HashMap();
			if(!hisList.isEmpty()) {
				Iterator hisIterator = hisList.iterator();
				while(hisIterator.hasNext()) {
					Map hisMap = (Map) hisIterator.next();
					String key = (String) hisMap.get("houseIeee") + (hisMap.get("messageid") == null? "": hisMap.get("messageid").toString()) + (String) hisMap.get("type");
					hisCacheMap.put(key, 1);
				}
				//添加是否发送短信、是否发送邮件字段
//				for(Object obj:warnList) {
				warnIterator = warnList.iterator();
				
				while(warnIterator.hasNext()) {
					Map warnMap = (Map) warnIterator.next();
//					Map warnMap = (Map) obj;
					String keyTmp = (String) warnMap.get("houseIEEE") + (warnMap.get("message_id") == null? "": warnMap.get("message_id").toString());
					String SMSKey = keyTmp + "0";
					String emailKey = keyTmp + "2";
					warnMap.put("SMSstate", hisCacheMap.get(SMSKey) == null? 0:hisCacheMap.get(SMSKey));
					warnMap.put("emailState", hisCacheMap.get(emailKey) == null? 0:hisCacheMap.get(emailKey));
//					newWarnList.add(warnMap);
				}
			}
			//组装返回数据
			Map rWarnMap = new HashMap();
			rWarnMap.put("listory", warnList);
			return rWarnMap;
		} 
		else {
			Map rWarnMap = new HashMap();
			rWarnMap.put("listory", null);
			return rWarnMap;
		}
	}

	@Override
	public List<Map> getzonewarntype(){
		String sql="select w_mode as WMode,ChinesewarnType as chinesewarnType,EnglishwarnType as englishwarnType,sensortype from zonewarntypetable";
		List<Map> t = mapDao.executeSql(sql);
		if(t!=null&&!t.isEmpty()){
			return t;
		}
		return null;
	}
	
	@Override
	public Warntypetable getwarntype(String w_mode) {
		// TODO Auto-generated method stub
		//warntypetablebaeBaseDaoI
		String hql="from Warntypetable a where a.WMode=:w_mode";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("w_mode",Integer.parseInt(w_mode));
		return warntypetablebaeBaseDaoI.get(hql, params);
	}
	
	@Override
	public Map<String,Object> getRoomAndDevice(String houseIeee, long roomId, String ieee, String ep) throws Exception {
		StringBuilder sqlB = new StringBuilder("select a.roomName,c.deviceName from room a right join ");
		sqlB.append("(select b.houseIEEE,b.ieee,b.deviceName from device b where b.houseIEEE='").append(houseIeee);
		sqlB.append("' and b.ieee='").append(ieee).append("' and b.ep='").append(ep).append("') c on a.houseIEEE = c.houseIEEE where a.roomId=");
		sqlB.append(roomId).append(" or a.roomId is NULL");
		List<Map> hList = mapDao.executeSql(sqlB.toString());
		if(!hList.isEmpty()) {
			return hList.get(0);
		}else
			return null;
	}
	
	@Override
	public void addDeviceWarnSMS(DevicewarnhistoryHouseidYear devWarn) throws Exception {
		++cacheSmsCount;
		smsWarnList.add(devWarn);
		if(cacheSmsCount >= this.cacheSmsSize) {
			sendDeviceWarnSMS();
			logger.info("进入短信转发过程！！！！！！");
		}
	}
	
	@Override
	public void sendDeviceWarnSMS() throws Exception {
		if(smsWarnList.isEmpty()) {
			logger.info("no sms rewardto send----------------");
			return;
		}
		logger.info("rewardto send sms----------------");
		cacheSmsCount = 0;
		final List<DevicewarnhistoryHouseidYear> tmpWarnList = smsWarnList;
		smsWarnList = new ArrayList<DevicewarnhistoryHouseidYear>(this.cacheSmsCount + 10);
		new Thread() {
			private DevicewarnhistoryHouseidYearServiceI warnService;
			public void run() {
				try {
					if(warnService == null)
						warnService = (DevicewarnhistoryHouseidYearServiceI) ac.getBean("devicewarnhistoryHouseidYearService");
										
					for(DevicewarnhistoryHouseidYear tDevWarn : tmpWarnList) {
						Map<String, Object> pMap = warnService.getRoomAndDevice(tDevWarn.getHouseIeee(), tDevWarn.getRoomId(), tDevWarn.getZoneIeee(), tDevWarn.getZoneEp());
						if(pMap != null) {
							tDevWarn.setRoomName((String) pMap.get("roomName"));
							tDevWarn.setDeviceName((String) pMap.get("deviceName"));
						}else{
							
						}
					}
					String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
			    	String cloudPort = PropertiesUtil.getProperty("cloudPort");
			    	String serverHost = InetAddress.getLocalHost().getHostAddress();
			    	logger.info("获取跳转的IP地址和端口号：-------》：");
			    	logger.info("warnmessage_cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);			
					/*if(!cloudAddress.equals(serverHost) 
						&& !cloudAddress.equals("localhost") 
						&& !cloudAddress.equals("127.0.0.1")
						&& !serverHost.equals("localhost") 
						&& !serverHost.equals("127.0.0.1")) {*/
						String callPrefix = "http://" + cloudAddress + ":" + cloudPort;
						String callUri = "/zigBeeDevice/devicewarnhistoryHouseidYearController/sendWarnSMS.do";
						logger.info("跳转的callUri地址====》：" + callUri);
						String callUri1 = "/zigBeeDevice/farmSmsController/sendFlagSMS.do";						
						logger.info("跳转的callUri1地址-------》：" + callUri1);
						String prefixKey = PropertiesUtil.getProperty("prefixKey");
						String pKey = prefixKey + new SimpleDateFormat("yyyyMMdd").format(new Date());
			        	String sign = Httpproxy.urlEncrypt(callUri, pKey);
			        	String sign1 = Httpproxy.urlEncrypt(callUri1, pKey);
						String warnJson = JSON.toJSONStringWithDateFormat(tmpWarnList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
						Map<String, Object> paramMap = new HashMap<String, Object>();
						Map<String, Object> param = new HashMap<String, Object>();
						paramMap.put("json", warnJson);
						paramMap.put("sign", sign);
						TestHttpclient.postUrlWithParams(callPrefix + callUri, paramMap, "utf-8");
						logger.info("跳转1-------》：");
						param.put("json", warnJson);
						param.put("sign", sign1);
						TestHttpclient.postUrlWithParams(callPrefix + callUri1, param, "utf-8");
						logger.info("跳转2-------》：");
						logger.info("得到的内容是：" + param.toString());
					//}
				} catch(Exception e) {
					logger.error("sendSmsFail", e);
				}
			}
		}.start();
	}
	
	public void getRoomAndDeviceName(String houseIeee,int roomId){
		
	}

	@Override
	public List<Map> getFarmWarnData(String userName, String houseIEEE, String startTime, String endTime) throws Exception {
		if(StringUtils.isBlank(userName)) {
			return Collections.emptyList();
		}
		//根据用户名获取区域
		List<Map> areaList = null;
		if(StringUtils.isBlank(houseIEEE)) {
			String proxyIp = PropertiesUtil.getProperty("cloudAddress");
			String proxyPort = PropertiesUtil.getProperty("cloudPort");
			String isMain = PropertiesUtil.getProperty("cloudMain");
			if(Integer.parseInt(isMain) == 1) {
				return Collections.emptyList();
			}
			String url = "http://" + proxyIp + ":" + proxyPort + "/zigBeeDevice/farmArea/getareas.do";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_name", userName);
			params.put("callback", "1234");
			params.put("encodemethod", "NONE");
			params.put("sign", "AAA");
			String reslStr = TestHttpclient.postUrlWithParams(url, params, "utf-8").trim();
			Map reslMap = JSON.parseObject(reslStr, Map.class);
			areaList = (List<Map>) reslMap.get("response_params");
		}
		else {
			areaList = new ArrayList<Map>();
			Map<String, Object> aMap = new HashMap<String, Object>();
			aMap.put("houseIEEE", houseIEEE);
			areaList.add(aMap);
		}
		//循环获取操作历史记录
		if(areaList.isEmpty()) {
			return Collections.emptyList();
		}
		StringBuilder outSql = new StringBuilder("SELECT * FROM (");
		StringBuilder inSql = new StringBuilder();
		Iterator<Map> itor = areaList.iterator();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//获取数据存在的tableName
		StringBuilder getTableSql = new StringBuilder("SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_SCHEMA='zigbeedevice' AND TABLE_NAME in (");
		while(itor.hasNext()) {
			Map aMap = itor.next();
			String houseIeee = (String) aMap.get("houseIEEE");
			String tableName = "devicewarnhistory_" + houseIeee + "_" + year;
			getTableSql.append("'").append(tableName).append("',");
		}
		String getTableSqlStr = getTableSql.substring(0, getTableSql.length() - 1) + ")";
		List<Map> tableList = mapDao.executeSql(getTableSqlStr);
		Map<String, Integer> cacheTable = new HashMap<String, Integer>();
		Iterator<Map> tItor = tableList.iterator();
		while(tItor.hasNext()) {
			Map tMap = tItor.next();
			String tableName = (String) tMap.get("TABLE_NAME");
			tableName = tableName.toLowerCase();
			cacheTable.put(tableName, 1);
		}
		if(cacheTable == null || cacheTable.isEmpty()) {
			return Collections.emptyList();
		}
		//生成sql
		itor = areaList.iterator();
		while(itor.hasNext()) {
			Map aMap = itor.next();
			String houseIeee = (String) aMap.get("houseIEEE");
			String tableName = "devicewarnhistory_" + houseIeee.toLowerCase() + "_" + year;
			//判断当前表是否存在
			if(cacheTable.get(tableName) == null) {
				continue;
			}
			inSql.append("SELECT hanlde_flag as hanlde_flag,houseIEEE as houseIeee,room_id as roomId,daemonDeviceId,source_id,deviceType,zone_ieee,zone_ep,cie_ieee,");
			inSql.append("cie_ep,w_mode,w_description,sendState,message_id,warndatetime as time,name FROM ").append(tableName)
			.append(" WHERE warndatetime BETWEEN '").append(startTime).append("' and '").append(endTime).append("' UNION ");
		}
		String inSqlStr = inSql.substring(0, inSql.length() - 7);
		String ourSqlStr = outSql.append(inSqlStr).append(") a ORDER BY a.time DESC ").toString();
		List<Map> opList = mapDao.executeSql(ourSqlStr);
		return opList;
	}
	@Override
	public List<Map> getZoneWmode(String houseIEEE, String deviceIeee,String deviceEp, String lasttime) throws Exception {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endtime = sdf.parse(lasttime);
		Calendar date = Calendar.getInstance();
		date.setTime(endtime);
		date.set(Calendar.DATE, date.get(Calendar.DATE ) - 1);
		Date start = sdf.parse(sdf.format(date.getTime()));
		String starttime = sdf.format(start);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + houseIEEE + "_" + year;
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as sum from  ").append(tableName).append(" t where 1=1  and t.w_mode= 13 ");
		if(StringUtils.isNotBlank(houseIEEE)) {
			sql.append(" and t.houseIEEE=:houseIEEE");
			map.put("houseIEEE", houseIEEE);
		}
		if(StringUtils.isNotBlank(deviceIeee)) {
			sql.append(" and t.zone_ieee=:zone_ieee");
			map.put("zone_ieee", deviceIeee);
		}
		if(StringUtils.isNotBlank(deviceEp)) {
			sql.append(" and t.zone_ep=:zone_ep ");
			map.put("zone_ep", deviceEp);
		}
		if(StringUtils.isNotBlank(starttime)) {
			sql.append(" and t.warndatetime between '").append(starttime).append("' and '").append(lasttime).append("'");
		}
		List<Map> list = mapDao.executeSql(sql.toString(), map);
		if(list != null) {
			return list;
		}
		return null;
	}
	@Override
	public List<Map> getWarnMode(String houseIEEE, String deviceIeee, String deviceEp) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + houseIEEE + "_" + year;
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select DISTINCT (a.w_mode),")
		.append("CASE WHEN d.w_mode in (0,1,2,3,4,6,14,15,16) THEN d.ChinesewarnType ")
		.append("WHEN d.w_mode IN (7,8,9,10,11,12) THEN REPLACE(d.ChinesewarnType,'<家人名称>,','') ")
		.append("WHEN d.w_mode IN (5,13,44,46) THEN REPLACE(d.ChinesewarnType,'<房间>-<设备>','') ")
		.append("WHEN d.w_mode IN (47) THEN REPLACE(d.ChinesewarnType,'<用户名称>','') ")
		.append("WHEN a.w_mode in(70) THEN REPLACE(a.w_description,a.w_description,'自定义')")
		.append("ELSE a.w_description END w_description , ")
		.append("CASE WHEN d.w_mode in (0,1,2,3,4,6,14,15,16) THEN d.EnglishwarnType ")
		.append("WHEN d.w_mode IN (7,8,9,10,11,12) THEN REPLACE(d.EnglishwarnType,'<family name>,','') ")
		.append("WHEN d.w_mode IN (5,13,44,46) THEN REPLACE(d.EnglishwarnType,'<room>-<device>','') ")
		.append("WHEN d.w_mode IN (47) THEN REPLACE(d.EnglishwarnType,'<user name>','') ")
		.append("WHEN a.w_mode in(70) THEN REPLACE(a.w_description,a.w_description,'Custom')")
		.append("ELSE a.w_description END w_descriptionEn ")
		.append("from ").append(tableName).append(" a LEFT JOIN device c on a.zone_ieee = c.ieee and a.zone_ep = c.ep and a.houseIEEE = c.houseIEEE ")
		.append(" LEFT JOIN warntypetable d on a.w_mode = d.w_mode ")
		.append("where 1=1 ");		
		/*sql.append("SELECT DISTINCT a.w_mode,d.ChinesewarnType,d.EnglishwarnType from ");
		sql.append(tableName).append(" a LEFT JOIN device c on a.zone_ieee = c.ieee and a.zone_ep = c.ep and a.houseIEEE = c.houseIEEE ");
		sql.append(" LEFT JOIN warntypetable d on a.w_mode = d.w_mode  where 1=1 ");*/
		if(StringUtils.isNotBlank(houseIEEE)) {
			sql.append(" and a.houseIEEE =:houseIEEE ");
			params.put("houseIEEE", houseIEEE);
		}
		if(!houseIEEE.equals(deviceIeee) && StringUtils.isNotBlank(deviceIeee)){
			sql.append(" and a.zone_ieee =:deviceIeee ");
			params.put("deviceIeee", deviceIeee);
		}
		if(!houseIEEE.equals(deviceIeee) && StringUtils.isNotBlank(deviceEp)){
			sql.append(" and a.zone_ep =:deviceEp ");
			params.put("deviceEp", deviceEp);
		}
		/*if(StringUtils.isNotBlank(deviceIeee)) {
			sql.append(" and a.zone_ieee =:deviceIeee ");
			params.put("deviceIeee", deviceIeee);
		}
		if(StringUtils.isNotBlank(deviceEp)) {
			sql.append(" and a.zone_ep =:deviceEp ");
			params.put("deviceEp", deviceEp);
		}*/
		logger.info("打印sql输出语句====》："+ sql);
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		if(list != null) {
			return list;
		}
		return null;
	}
	@Override
	public int exportWarnLogExcel(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "deviceWarnHistory_" + param.get("houseIeee") + "_" + year;
		StringBuffer sql = new StringBuffer();
//		sql.append("select a.*,b.roomName,c.deviceName ");
		sql.append("select a.message_id,a.zone_ieee,a.zone_ep,a.w_mode,a.warndatetime,b.roomName,c.deviceName,")
		//7,8,9,10,11,12添加要用英文显示的w_mode编码就行了
		.append("CASE WHEN d.w_mode in (0,1,2,3,4,6,14,15,16) THEN d.ChinesewarnType ")
		.append("WHEN d.w_mode IN (7,8,9,10,11,12) THEN REPLACE(d.ChinesewarnType,'<家人名称>,','') ")
		.append("WHEN d.w_mode IN (5,13,44,46) THEN REPLACE(d.ChinesewarnType,'<房间>-<设备>','') ")
		.append("WHEN d.w_mode IN (47) THEN REPLACE(d.ChinesewarnType,'<用户名称>','') ")
		.append("WHEN a.w_mode in(70) THEN REPLACE(a.w_description,a.w_description,'自定义')")
		.append("ELSE a.w_description END w_description , ")
		.append("CASE WHEN d.w_mode in (0,1,2,3,4,6,14,15,16) THEN d.EnglishwarnType ")
		.append("WHEN d.w_mode IN (7,8,9,10,11,12) THEN REPLACE(d.EnglishwarnType,'<family name>,','') ")
		.append("WHEN d.w_mode IN (5,13,44,46) THEN REPLACE(d.EnglishwarnType,'<room>-<device>','') ")
		.append("WHEN d.w_mode IN (47) THEN REPLACE(d.EnglishwarnType,'<user name>','') ")
		.append("WHEN a.w_mode in(70) THEN REPLACE(a.w_description,a.w_description,'Custom')")
		.append("ELSE a.w_description END w_descriptionEn ")
		.append("from ").append(tableName).append(" a left join room b on a.room_id = b.roomId and a.houseIEEE = b.houseIEEE ")
		.append("LEFT JOIN device c on a.zone_ieee = c.ieee and a.zone_ep = c.ep and a.houseIEEE = c.houseIEEE LEFT JOIN warntypetable d ON a.w_mode=d.w_mode ")
		.append("where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank((String)param.get("houseIeee"))) {
			sql.append("and a.houseIEEE = :houseIeee ");
			params.put("houseIeee", param.get("houseIeee"));
		}
		if(!param.get("houseIeee").equals(param.get("zoneIeee")) && StringUtils.isNotBlank((String)param.get("zoneIeee"))) {
			sql.append("and a.zone_ieee =:zoneIeee ");	
			params.put("zoneIeee", param.get("zoneIeee"));
		}
		if(!param.get("houseIeee").equals(param.get("zoneIeee")) && StringUtils.isNotBlank((String)param.get("zoneEp"))) {
			sql.append("and a.zone_ieee =:zoneIeee ");	
			params.put("zoneIeee", param.get("zoneIeee"));
		}
		/*if(StringUtils.isNotBlank((String)param.get("zoneIeee"))) {
			sql.append("and a.zone_ieee =:zoneIeee ");	
			params.put("zoneIeee", param.get("zoneIeee"));
		}
		if(StringUtils.isNotBlank((String)param.get("zoneEp"))) {
			sql.append("and a.zone_ep =:zoneEp ");
			params.put("zoneEp", param.get("zoneEp"));
		}*/
		if(StringUtils.isNotBlank((String)param.get("starttime"))) {
			sql.append(" and a.warndatetime between '").append(param.get("starttime")).append("' and '").append(param.get("endtime")).append("'");
		}
		
		if(StringUtils.isNotBlank((String)param.get("wMode")) && !param.get("wMode").equals("-1")) {
			sql.append(" and a.w_mode =:WMode ");
			params.put("WMode", param.get("wMode"));
		}

		if (StringUtils.isNotBlank((String)param.get("wDescription"))) {
			sql.append("and a.w_description like :WDescription ");
			params.put("WDescription", "%"+param.get("wDescription")+"%");
		}
		if (StringUtils.isNotBlank((String)param.get("roimId"))) {
			sql.append("and a.room_id = :roomId ");
			params.put("roomId", param.get("roimId"));
		}
		if (StringUtils.isNotBlank((String)param.get("roomName"))) {
			sql.append("and b.roomName like :roomName ");
			params.put("roomName", "%"+param.get("roomName")+"%");
		}
		//报警历史 zoneIeee、房间区域模糊搜索
		if(param.get("houseIeee").equals(param.get("zoneIeee")) && StringUtils.isNotBlank((String)param.get("deviceName"))){
			sql.append("and ((c.deviceName like :deviceName) or (a.zone_ieee like :zoneIeee)) ");
			params.put("deviceName", "%"+param.get("deviceName")+"%");
			//params.put("roomName", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
			params.put("zoneIeee", "%"+param.get("deviceName")+"%");
			//params.put("zoneEp", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
			//params.put("WDescription", "%"+devicewarnhistoryHouseidYear.getDeviceName()+"%");
		}
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		BufferedOutputStream os = null;
		try {
			if(param.get("lang").equals("1")){
				//生成excel文件
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				sheet.setColumnWidth(1, 3500);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(5, 6000);
				workbook.setSheetName(0, "Alarm history list");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Types of alarm");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Zone device name");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Zone device information");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Room area");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Alarm time");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Send short message");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Send e-mail");
				cell.setCellStyle(titleStyle);
				int lqiSize = t.size();
				for(int i = 0; i < lqiSize; i++) {
					row = sheet.createRow(i + 1);
					
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("w_descriptionEn")==null?"None":t.get(i).get("w_descriptionEn").toString());
					cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("deviceName")==null?"---":t.get(i).get("deviceName").toString());
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					String Ieee=t.get(i).get("zone_ieee").toString()==null?"":t.get(i).get("zone_ieee").toString();
					String Ep=t.get(i).get("zone_ep")==null?"":t.get(i).get("zone_ep").toString();
					if(Ep.equals("")){
						cell.setCellValue(Ieee);
					}else{
						cell.setCellValue(Ieee + "-" + Ep);
					}
					
					
					cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("roomName")==null?"---":t.get(i).get("roomName").toString());
					
					cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("warndatetime").toString());
					
					cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("---");
					
					cell = row.createCell(6, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("---");
				}
				//设置response参数
				//对中文字符转码
				String fileName = "Alarm history list.xls";		
				fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
				response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//				response.addHeader("Content-Disposition", "inline;filename=" + fileName);
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				os = new BufferedOutputStream(response.getOutputStream());
				workbook.write(os);
				os.flush();
			}else {
				//生成excel文件
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				sheet.setColumnWidth(1, 3500);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(5, 6000);
				workbook.setSheetName(0, "报警历史列表");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("报警类型");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Zone设备名称");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Zone设备信息");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("房间区域");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("报警时间");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("短信推送");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("邮件推送");
				cell.setCellStyle(titleStyle);
				int lqiSize = t.size();
				for(int i = 0; i < lqiSize; i++) {
					row = sheet.createRow(i + 1);
					
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("w_description")==null?"无":t.get(i).get("w_description").toString());
					cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("deviceName")==null?"---":t.get(i).get("deviceName").toString());
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					String Ieee=t.get(i).get("zone_ieee").toString()==null?"":t.get(i).get("zone_ieee").toString();
					String Ep=t.get(i).get("zone_ep")==null?"":t.get(i).get("zone_ep").toString();
					if(Ep.equals("")){
						cell.setCellValue(Ieee);
					}else{
						cell.setCellValue(Ieee + "-" + Ep);
					}
					
					
					cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("roomName")==null?"---":t.get(i).get("roomName").toString());
					
					cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("warndatetime").toString());
					
					cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("---");
					
					cell = row.createCell(6, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("---");
				}
				//设置response参数
				//对中文字符转码
				String fileName = "报警历史列表.xls";		
				fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
				response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//				response.addHeader("Content-Disposition", "inline;filename=" + fileName);
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				os = new BufferedOutputStream(response.getOutputStream());
				workbook.write(os);
				os.flush();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.info("exportLqiLogExcel", e);
		} finally {
			try {
				if(os != null)
					os.close();
			} catch(Exception e) {
//				e.printStackTrace();
				logger.info("exportLqiLogExcel close BufferedOutputStream", e);
			}
		}
		return 0;
	}	
	
	@Override
	public Map getSMSType(String ipAddress){
		String sql = "SELECT smstype,smsip FROM serverlib WHERE serverip = :serverip";
	    Map<String,Object> params = new HashMap<>();
	    params.put("serverip", ipAddress);

	    List<Map> list = this.mapDao.executeSql(sql, params);
	    if ((list == null) || (list.isEmpty())) {
	      return null;
	    }
	    return (Map)list.get(0);
	}
	
	@Override
	public Byte getShcSmsType(String houseIeee){
		House house = new House();
		house.setHouseIeee(houseIeee);
		House h = houseService.find(house);
		if(h!=null){
			return h.getSmsType();
		}
		return 0;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		this.ac = ac;
	}
}
