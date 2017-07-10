package sy.service.impl;

import java.io.BufferedOutputStream;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DeviceoperatehistoryHouseidYear;
import sy.model.FarmUser;
import sy.service.DeviceoperatehistoryHouseidYearServiceI;
import sy.service.HouseServiceI;
import sy.util.HouseieeeListener;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

import com.alibaba.fastjson.JSON;

@Service("deviceoperatehistoryHouseidYearService")
public class DeviceoperatehistoryHouseidYearServiceImpl implements DeviceoperatehistoryHouseidYearServiceI {

	private static final Logger logger = Logger.getLogger(DeviceoperatehistoryHouseidYearServiceImpl.class);

	private BaseDaoI<DeviceoperatehistoryHouseidYear> deviceoperatehistoryHouseidYearDao;
	
	private BaseDaoI<Map> mapDao;
//	private List<DeviceoperatehistoryHouseidYear> cacheList = new ArrayList<DeviceoperatehistoryHouseidYear>(100);
	private Map<String, List<DeviceoperatehistoryHouseidYear>> cacheList = new HashMap<String, List<DeviceoperatehistoryHouseidYear>>();
	private int cacheCount = 0; //批量计数器
	private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.deviceoperatehistoryHouseidYear"));
	private HouseServiceI houseService;
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<DeviceoperatehistoryHouseidYear> getDeviceoperatehistoryHouseidYearDao() {
		return deviceoperatehistoryHouseidYearDao;
	}

	@Autowired
	public void setDeviceoperatehistoryHouseidYearDao(BaseDaoI<DeviceoperatehistoryHouseidYear> deviceoperatehistoryHouseidYearDao) {
		this.deviceoperatehistoryHouseidYearDao = deviceoperatehistoryHouseidYearDao;
	}

	public HouseServiceI getHouseService() {
		return houseService;
	}

	@Autowired
	public void setHouseService(HouseServiceI houseService) {
		this.houseService = houseService;
	}
	
	@Override
	public DeviceoperatehistoryHouseidYear save(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) {
		/*DeviceoperatehistoryHouseidYear t = new DeviceoperatehistoryHouseidYear();
		BeanUtils.copyProperties(deviceoperatehistoryHouseidYear, t, new String[] { "pwd" });*/
		/*t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(deviceoperatehistoryHouseidYear.getPwd()));*/
		deviceoperatehistoryHouseidYearDao.save(deviceoperatehistoryHouseidYear);
//		BeanUtils.copyProperties(t, deviceoperatehistoryHouseidYear);
		return deviceoperatehistoryHouseidYear;
	}

	@Override
	public DeviceoperatehistoryHouseidYear login(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(deviceoperatehistoryHouseidYear.getPwd()));
//		params.put("name", deviceoperatehistoryHouseidYear.getName());
		DeviceoperatehistoryHouseidYear t = deviceoperatehistoryHouseidYearDao.get("from DeviceoperatehistoryHouseidYear t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return deviceoperatehistoryHouseidYear;
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
//			logger.info("DeviceoperatehistoryHouseidYear Repeat saveorupdate begin");
			try {
				int i = stmt.executeUpdate(sqlStr);
//				logger.info("DeviceoperatehistoryHouseidYear Repeat saveorupdate end, result: " + i);
			} catch(Exception e) {
				logger.info("save or update DeviceoperatehistoryHouseidYear Repeat exception", e);
			}
		}
	}
	
	/**
	 * 定时保存操作历史
	 * @return
	 */
	@Override
	public int savedingshi() {
		if(cacheList.size()==0){
			logger.info("定时保存操作历史，缓存为空");
			return 0;
		}
		logger.info("定时保存操作历史begin......");
		cacheCount =0;
		final Map<String, List<DeviceoperatehistoryHouseidYear>> tmpList = this.cacheList;
		this.cacheList = new HashMap<String, List<DeviceoperatehistoryHouseidYear>>(this.cacheSize + 10);
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		Thread th = new Thread() {
    		@Override
			public void run() {
//    			logger.info("DeviceoperatehistoryHouseidYear saveorupdate begin, tmpList.size: " + tmpList.size());	    			
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
						List<DeviceoperatehistoryHouseidYear> dList = tmpList.get(houseIeee);    						
						if(dList != null && !dList.isEmpty()) {
							String tableName = "deviceOperateHistory_" + houseIeee + "_" + year;
							StringBuilder sql = new StringBuilder("insert into ");
    		    			sql.append(tableName);
    		    			sql.append("(houseIeee,username,room_Id,device_Ieee,device_Ep,opname,optype,opparam,opdatetime) VALUES");
    		    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		    			for(DeviceoperatehistoryHouseidYear dv: dList) {	    				
    		    				sql.append("('").append(dv.getHouseIeee()).append("','").append(dv.getUsername()).append("',")
    		    				.append(dv.getRoomId()).append(",'").append(dv.getDeviceIeee()).append("','").append(dv.getDeviceEp())
    		    				.append("','").append(dv.getOpname()).append("','").append(dv.getOptype()).append("','")
    		    				.append(dv.getOpparam()).append("','").append(sdf.format(dv.getOpdatetime())).append("'),");	    				
    		    			}
    		    			String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
	    					try {		    		    			
	    						stmt.executeUpdate(sqlStr);
	    					} catch(Exception e) {
	    						String msg = e.getMessage();
	    						//当表不存在时创建并插入数据
	    	    				if(msg.indexOf(tableName.toLowerCase()) != -1) {
	    	    					houseService.addDeviceOperateHistoryTable(houseIeee);
	    	    					tmpInsertData(sqlStr, stmt);
	    	    				}
	    	    				else {
	    	    					logger.info("save or update DeviceoperatehistoryHouseidYear " + houseIeee + " exception", e);
	    	    				}
	    					}	    					
						}
    				}
//    				logger.info("DeviceoperatehistoryHouseidYear saveorupdate end");
    			} catch(Exception e) {
    				logger.info("save or update DeviceoperatehistoryHouseidYear exception", e);
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
    			logger.info("定时保存操作历史end......");
    		}
    	};
    	th.setName("saveOperateHistory");
    	th.start();
		return 1;
	}
	
	
	
	@Override
	public int saveSql(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) {
//		cacheList.add(deviceoperatehistoryHouseidYear);
		++cacheCount;
		String houseIeee = deviceoperatehistoryHouseidYear.getHouseIeee();
		List<DeviceoperatehistoryHouseidYear> tmpCacheList = cacheList.get(houseIeee); 
//		deviceoperatehistoryHouseidYear.setOpdatetime(new Date());
		if(tmpCacheList == null) {
			tmpCacheList = new ArrayList<DeviceoperatehistoryHouseidYear>(this.cacheSize + 10);
			tmpCacheList.add(deviceoperatehistoryHouseidYear);
			cacheList.put(houseIeee, tmpCacheList);
		}
		else {
			tmpCacheList.add(deviceoperatehistoryHouseidYear);
		}
		//批量更新
		if (cacheCount >= this.cacheSize) {
			savedingshi();
//			logger.info("定时保存操作历史begin......");
//			cacheCount = 0;
//			final Map<String, List<DeviceoperatehistoryHouseidYear>> tmpList = this.cacheList;
//			this.cacheList = new HashMap<String, List<DeviceoperatehistoryHouseidYear>>(this.cacheSize + 10);
//			final int year = Calendar.getInstance().get(Calendar.YEAR);
////			new Thread() {
////				public void tmpInsertData(String sqlStr, Statement stmt) {
////					if(StringUtils.isNotBlank(sqlStr)) {
////						logger.info("DeviceoperatehistoryHouseidYear Repeat saveorupdate begin");
////		    			try {
////		    				int i = stmt.executeUpdate(sqlStr);
////		    				logger.info("DeviceoperatehistoryHouseidYear Repeat saveorupdate end, result: " + i);
////		    			} catch(Exception e) {
////		    				logger.info("save or update DeviceoperatehistoryHouseidYear Repeat exception", e);
////		    			}
////					}
////				}
////				
////	    		public void run() {
////	    			logger.info("DeviceoperatehistoryHouseidYear saveorupdate begin, tmpList.size: " + tmpList.size());	    			
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
//    						List<DeviceoperatehistoryHouseidYear> dList = tmpList.get(houseIEEE);    						
//    						if(dList != null && !dList.isEmpty()) {
//    							String tableName = "deviceOperateHistory_" + houseIEEE + "_" + year;
//    							StringBuilder sql = new StringBuilder("insert into ");
//	    		    			sql.append(tableName);
//	    		    			sql.append("(houseIeee,username,room_Id,device_Ieee,device_Ep,opname,optype,opparam,opdatetime) VALUES");
//	    		    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    		    			for(DeviceoperatehistoryHouseidYear dv: dList) {	    				
//	    		    				sql.append("('").append(dv.getHouseIeee()).append("','").append(dv.getUsername()).append("',")
//	    		    				.append(dv.getRoomId()).append(",'").append(dv.getDeviceIeee()).append("','").append(dv.getDeviceEp())
//	    		    				.append("','").append(dv.getOpname()).append("','").append(dv.getOptype()).append("','")
//	    		    				.append(dv.getOpparam()).append("','").append(sdf.format(dv.getOpdatetime())).append("'),");	    				
//	    		    			}
//	    		    			String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
//		    					try {		    		    			
//		    						stmt.executeUpdate(sqlStr);
//		    					} catch(Exception e) {
//		    						String msg = e.getMessage();
//		    						//当表不存在时创建并插入数据
//		    	    				if(msg.indexOf(tableName.toLowerCase()) != -1) {
//		    	    					houseService.addDeviceOperateHistoryTable(houseIEEE);
//		    	    					tmpInsertData(sqlStr, stmt);
//		    	    				}
//		    	    				else {
//		    	    					logger.info("save or update DeviceoperatehistoryHouseidYear " + houseIEEE + " exception", e);
//		    	    				}
//		    					}	    					
//    						}
//	    				}
////	    				logger.info("DeviceoperatehistoryHouseidYear saveorupdate end");
//	    			} catch(Exception e) {
//	    				logger.info("save or update DeviceoperatehistoryHouseidYear exception", e);
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
//	    			logger.info("定时保存操作历史end......");
////	    		}
////	    	}.start();
		}
		return 1;
	}
	
	/**
	 * 查找历史记录列表(单表查询)
	 * @author: zhuangxd
	 * 时间：2014-6-30 下午2:47:52
	 * @param deviceoperatehistoryHouseidYear
	 * @return
	 */
	@Override
	public List<DeviceoperatehistoryHouseidYear> findList(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceOperateHistory_" + deviceoperatehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName).append(" t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (deviceoperatehistoryHouseidYear.getHouseIeee() != null) {
			sql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", deviceoperatehistoryHouseidYear.getHouseIeee());
		}
		if (deviceoperatehistoryHouseidYear.getUsername() != null) {
			sql.append("and t.username = :username ");
			params.put("username", deviceoperatehistoryHouseidYear.getUsername());
		}
		if (deviceoperatehistoryHouseidYear.getRoomId() != 0) {
			sql.append("and t.room_Id = :roomId ");
			params.put("roomId", deviceoperatehistoryHouseidYear.getRoomId());
		}
		if (deviceoperatehistoryHouseidYear.getDeviceIeee() != null) {
			sql.append("and t.device_Ieee = :deviceIeee ");
			params.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());
		}
		if (deviceoperatehistoryHouseidYear.getDeviceEp() != null) {
			sql.append("and t.device_Ep = :deviceEp ");
			params.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}
		if (deviceoperatehistoryHouseidYear.getOpname() != null) {
			sql.append("and t.opname = :opname ");
			params.put("opname", deviceoperatehistoryHouseidYear.getOpname());
		}
		if (deviceoperatehistoryHouseidYear.getOptype() != null) {
			sql.append("and t.optype = :optype ");
			params.put("optype", deviceoperatehistoryHouseidYear.getOptype());
		}
		if (deviceoperatehistoryHouseidYear.getOpparam() != null) {
			sql.append("and t.opparam = :opparam ");
			params.put("opparam", deviceoperatehistoryHouseidYear.getOpparam());
		}
		if (deviceoperatehistoryHouseidYear.getMinOpdatetime() != null) {
			sql.append("and t.opdatetime >= :minopdatetime ");
			params.put("minopdatetime", deviceoperatehistoryHouseidYear.getMinOpdatetime());
		}
		if (deviceoperatehistoryHouseidYear.getMaxOpdatetime() != null) {
			sql.append("and t.opdatetime <= :maxopdatetime ");
			params.put("maxopdatetime", deviceoperatehistoryHouseidYear.getMaxOpdatetime());
		}	
		sql.append(" and TO_DAYS(NOW()) - TO_DAYS(t.opdatetime) <= 1 order by opdatetime desc"); // 查询两天内的数据
		List<DeviceoperatehistoryHouseidYear> t = deviceoperatehistoryHouseidYearDao.findSql(sql.toString(), params, DeviceoperatehistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/**
	 * 查找历史记录列表(多表关联)
	 * @author: zhuangxd
	 * 时间：2014-6-30 下午2:47:34
	 * @param deviceoperatehistoryHouseidYear
	 * @return
	 */
	@Override
	public Map findListory(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) {
		savedingshi();//列表显示前执行定时保存
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "deviceOperateHistory_" + deviceoperatehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer hww = new StringBuffer();
		Map<String, Object> ppt = new HashMap<String, Object>();
		hww.append("SELECT  isTableExist('"+ tableName +"') as sun  "); 		
 		List<Map> l = mapDao.executeSql(hww.toString()); 
 		String tt = l.get(0).get("sun") .toString();
 		if(tt.equals("1")){
		StringBuffer sql = new StringBuffer();
		//sql.append("select t.*, f.operateName,f.operateNameCn,d.deviceName,d.isonline from ").append(tableName).append(" t join operatelib f on t.opname=f.opname " +
		sql.append("select t.*, f.operateName,f.operateNameCn,d.deviceName,d.isonline from ").append(tableName).append(" t inner join operatelib f on t.opname=f.opname " +
				" and t.optype=f.optype " +
				"left join device d on t.houseIEEE=d.houseIEEE" +
				" and t.device_ieee = d.ieee " +
				" and t.device_ep = d.ep where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getHouseIeee())) {
			sql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", deviceoperatehistoryHouseidYear.getHouseIeee());
		}
		/*if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee())) {
			sql.append(" and t.device_ieee =:deviceIeee ");
			params.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());
		}
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceEp())) {
			sql.append("and t.device_ep = :deviceEp ");
			params.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}*/
		//2017.4.1 update (!deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee()
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee())){
			sql.append(" and t.device_ieee =:deviceIeee ");
			params.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());
		}
		if(!deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceEp())){
			sql.append(" and t.device_ep = :deviceEp ");
			params.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getUsername()) && !deviceoperatehistoryHouseidYear.getUsername().equals("-1") ) {
			if(deviceoperatehistoryHouseidYear.getUsername().equals("No")){
				deviceoperatehistoryHouseidYear.setUsername("");
			}
			sql.append(" and t.username =:username ");
			params.put("username", deviceoperatehistoryHouseidYear.getUsername());
		}
		
		// 设备名、用户名、操作名多项模糊查找
		if (deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) &&  StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceName())){//&& deviceoperatehistoryHouseidYear.getOptype().equals("-99")) {
			sql.append("and ((d.deviceName like :deviceName) or (t.device_Ieee like :deviceIeee)) ");
		    //params.put("username", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
			params.put("deviceName", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
			//params.put("operateNameCn", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
			params.put("deviceIeee", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
		}
		
//		2017.3.27 by shine 优化
		String operateNameCn = deviceoperatehistoryHouseidYear.getOperateNameCn();
		if (StringUtils.isNotBlank(operateNameCn) && !"-1".equals(operateNameCn)) {
			sql.append(" and f.operateName =:operateName ");
			params.put("operateName", deviceoperatehistoryHouseidYear.getOperateNameCn());
		}
		
//		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getOperateNameCn()) && !deviceoperatehistoryHouseidYear.getOperateNameCn().equals("-1")) {
//			if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getLang()) && deviceoperatehistoryHouseidYear.getLang().equals("1")) {
//				sql.append(" and f.operateName =:operateName ");
//				params.put("operateName", deviceoperatehistoryHouseidYear.getOperateNameCn());
//			}else {
//				sql.append(" and f.operateNameCn =:operateNameCn ");
//				params.put("operateNameCn", deviceoperatehistoryHouseidYear.getOperateNameCn());
//			}
//		}
		/*if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getOperateNameCn()) && !deviceoperatehistoryHouseidYear.getOperateNameCn().equals("-1")){
			sql.append(" and f.operateNameCn =:operateNameCn ");
			params.put("operateNameCn", deviceoperatehistoryHouseidYear.getOperateNameCn());
		}*/
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getOperateName())) {
			sql.append("and (f.operateName =:operateName or f.operateNameCn =:operateName) ");
			params.put("operateName", deviceoperatehistoryHouseidYear.getOperateName());
		}
		if (deviceoperatehistoryHouseidYear.getMinOpdatetime() != null) {
			sql.append("and t.opdatetime >= :minopdatetime ");
			params.put("minopdatetime", deviceoperatehistoryHouseidYear.getMinOpdatetime());
		}
		if (deviceoperatehistoryHouseidYear.getMaxOpdatetime() != null) {
			sql.append("and t.opdatetime <= :maxopdatetime ");
			params.put("maxopdatetime", deviceoperatehistoryHouseidYear.getMaxOpdatetime());
		}
		if (StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getStarttime())) {
		    sql.append(" and t.opdatetime between '").append(deviceoperatehistoryHouseidYear.getStarttime()).append("' and '").append(deviceoperatehistoryHouseidYear.getEndtime()).append("'");
		}		
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getOrderBy()))
			sql.append(" order by ").append(deviceoperatehistoryHouseidYear.getOrderBy()).append(" ");
		else
			sql.append(" order by opdatetime desc "); // 查询两天内的数据,限制500条数据
		if (deviceoperatehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceoperatehistoryHouseidYear.getStartRow() + "," + deviceoperatehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); // 查询两天内的数据,限制500条数据
		}
		logger.info("输出的操作历史列表sql语句----》："+ sql);
//		sql.append(" and TO_DAYS(NOW()) - TO_DAYS(t.opdatetime) <= 1 order by opdatetime desc limit 1000"); // 查询两天内的数据,限制500条数据
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		
		if(list != null && list.size() > 0) {
			rt.put("result", 1);
			rt.put("listory",list);	
		} else{
			rt.put("result", 0);
			rt.put("listory",null);
		}
		return rt;
 		}
 		logger.info("没有对应的操作历史表----》："+ tableName);
 		rt.put("result", -3);
 		rt.put("tableName", tableName);
		return rt;
	}
	
	/**
	 * 获取记录总数
	 * @author: zhuangxd
	 * 时间：2014-7-7 下午3:33:00
	 * @param deviceoperatehistoryHouseidYear
	 * @return
	 */
	@Override
	public Map getListoryCount(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "deviceOperateHistory_" + deviceoperatehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as total from (select t.* from ").append(tableName).append(" t inner join operatelib f on t.opname=f.opname " +
				" and t.optype=f.optype " +
				"left join device d on t.houseIEEE=d.houseIEEE" +
				" and t.device_ieee = d.ieee " +
				" and t.device_ep = d.ep where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getHouseIeee())) {
			sql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", deviceoperatehistoryHouseidYear.getHouseIeee());
		}
		/*if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee())) {
			sql.append(" and t.device_ieee =:deviceIeee ");
			params.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());
		}*/
		if(!deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee())){
			sql.append(" and t.device_ieee =:deviceIeee ");
			params.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());
		}
		if(!deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceEp())){
			sql.append(" and t.device_ep = :deviceEp ");
			params.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}
		/*if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceEp())) {
			sql.append("and t.device_ep = :deviceEp ");
			params.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}*/
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getUsername()) && !deviceoperatehistoryHouseidYear.getUsername().equals("-1") ) {
			if(deviceoperatehistoryHouseidYear.getUsername().equals("No")){
				deviceoperatehistoryHouseidYear.setUsername("");
			}
			sql.append(" and t.username =:username ");
			params.put("username", deviceoperatehistoryHouseidYear.getUsername());
		}
		
		// 设备名、用户名、操作名多项模糊查找
		if (deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceName()) ){//&& deviceoperatehistoryHouseidYear.getOptype().equals("-99")) {
			sql.append("and ((d.deviceName like :deviceName) or (t.device_Ieee like :deviceIeee)) ");
			//params.put("username", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
			params.put("deviceName", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
			//params.put("operateNameCn", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
			params.put("deviceIeee", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
		}
		
//		2017.3.27 by shine 优化
		String operateNameCn = deviceoperatehistoryHouseidYear.getOperateNameCn();
		if (StringUtils.isNotBlank(operateNameCn) && !"-1".equals(operateNameCn)) {
			sql.append(" and f.operateName =:operateName ");
			params.put("operateName", deviceoperatehistoryHouseidYear.getOperateNameCn());
		}
		
//		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getOperateNameCn()) && !deviceoperatehistoryHouseidYear.getOperateNameCn().equals("-1")) {
//			if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getLang()) && deviceoperatehistoryHouseidYear.getLang().equals("1")) {
//				sql.append(" and f.operateName =:operateName ");
//				params.put("operateName", deviceoperatehistoryHouseidYear.getOperateNameCn());
//			}else {
//				sql.append(" and f.operateNameCn =:operateNameCn ");
//				params.put("operateNameCn", deviceoperatehistoryHouseidYear.getOperateNameCn());
//			}
//		}
		/*if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getOperateNameCn()) && !deviceoperatehistoryHouseidYear.getOperateNameCn().equals("-1")){
			sql.append(" and f.operateNameCn =:operateNameCn ");
			params.put("operateNameCn", deviceoperatehistoryHouseidYear.getOperateNameCn());
		}*/
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getOperateName())) {
			sql.append("and (f.operateName =:operateName or f.operateNameCn =:operateName) ");
			params.put("operateName", deviceoperatehistoryHouseidYear.getOperateName());
		}
		if (deviceoperatehistoryHouseidYear.getMinOpdatetime() != null) {
			sql.append("and t.opdatetime >= :minopdatetime ");
			params.put("minopdatetime", deviceoperatehistoryHouseidYear.getMinOpdatetime());
		}
		if (deviceoperatehistoryHouseidYear.getMaxOpdatetime() != null) {
			sql.append("and t.opdatetime <= :maxopdatetime ");
			params.put("maxopdatetime", deviceoperatehistoryHouseidYear.getMaxOpdatetime());
		}
		if (StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getStarttime())) {
		    sql.append(" and t.opdatetime between '").append(deviceoperatehistoryHouseidYear.getStarttime()).append("' and '").append(deviceoperatehistoryHouseidYear.getEndtime()).append("'");
		}		
		sql.append(" order by opdatetime desc) m ");
		logger.info("输出的操作历史SQL语句----》："+ sql);
//		sql.append(" order by opdatetime desc limit 3700) m "); // 查询两天内的数据,限制500条数据
		List<Map> list = mapDao.executeSql(sql.toString(), params);		
		if(list != null && list.size() > 0) {
			rt.put("total",list);	
		} else
			rt.put("total",null);
		
		return rt;
	}

/*	@Override
	public DataGrid datagrid(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) {
		DataGrid dg = new DataGrid();
		String hql = "from DeviceoperatehistoryHouseidYear t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(deviceoperatehistoryHouseidYear, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(deviceoperatehistoryHouseidYear, hql);
		List<DeviceoperatehistoryHouseidYear> l = deviceoperatehistoryHouseidYearDao.find(hql, params, deviceoperatehistoryHouseidYear.getPage(), deviceoperatehistoryHouseidYear.getRows());
		List<DeviceoperatehistoryHouseidYear> nl = new ArrayList<DeviceoperatehistoryHouseidYear>();
		changeModel(l, nl);
		dg.setTotal(deviceoperatehistoryHouseidYearDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<DeviceoperatehistoryHouseidYear> l, List<DeviceoperatehistoryHouseidYear> nl) {
		if (l != null && l.size() > 0) {
			for (DeviceoperatehistoryHouseidYear t : l) {
				DeviceoperatehistoryHouseidYear u = new DeviceoperatehistoryHouseidYear();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear, String hql) {
		/*if (deviceoperatehistoryHouseidYear.getSort() != null) {
			hql += " order by " + deviceoperatehistoryHouseidYear.getSort() + " " + deviceoperatehistoryHouseidYear.getOrder();
		}*/
		return hql;
	}

	private String addWhere(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear, String hql, Map<String, Object> params) {
		/*if (deviceoperatehistoryHouseidYear.getName() != null && !deviceoperatehistoryHouseidYear.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + deviceoperatehistoryHouseidYear.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete DeviceoperatehistoryHouseidYear t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		deviceoperatehistoryHouseidYearDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
	
	@Override
	public List<Map> getLogcat(String userName, String startTime, String endTime, int pageIndex, int pageSize) throws Exception {
		if(StringUtils.isBlank(userName)) {
			return Collections.emptyList();
		}
		//根据用户名获取区域
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
		List<Map> areaList = (List<Map>) reslMap.get("response_params");
		//循环获取操作历史记录
		if(areaList.isEmpty()) {
			return Collections.emptyList();
		}
		int startRow = (pageIndex - 1) * pageSize;
		startRow = startRow < 0? 0 : startRow;
		StringBuilder outSql = new StringBuilder("SELECT * FROM (");
		StringBuilder inSql = new StringBuilder();
		Iterator<Map> itor = areaList.iterator();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//获取数据存在的tableName
		StringBuilder getTableSql = new StringBuilder("SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_SCHEMA='zigbeedevice' AND TABLE_NAME in (");
		while(itor.hasNext()) {
			Map aMap = itor.next();
			String houseIEEE = (String) aMap.get("houseIEEE");
			String tableName = "deviceoperatehistory_" + houseIEEE + "_" + year;
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
			String houseIEEE = (String) aMap.get("houseIEEE");
			String houseName = (String) aMap.get("houseName");
			String tableName = "deviceoperatehistory_" + houseIEEE.toLowerCase() + "_" + year;
			//判断当前表是否存在
			if(cacheTable.get(tableName) == null) {
				continue;
			}
			inSql.append("select t.id,'").append(houseName).append("' area,'").append(houseIEEE).append("' house_ieee,t.opdatetime time,")
			.append("f.operateNameCn description,z.device_name deviceName,t.username from ")
			.append(tableName).append(" t left join operatelib f on t.opname=f.opname and t.optype=f.optype ")
			.append("LEFT JOIN farmdevice z ON t.houseIEEE=z.house_ieee AND t.device_ieee=z.ieee AND t.device_ep=z.ep ")
			.append("WHERE t.opdatetime BETWEEN '").append(startTime).append("' and '").append(endTime)
			.append("' AND f.operateName in ('TurnON','TurnOFF') UNION ");
		}
		String inSqlStr = inSql.substring(0, inSql.length() - 7);
		String ourSqlStr = null;
		if(pageSize == -1) { //获取所有记录
			ourSqlStr = outSql.append(inSqlStr).append(") a ORDER BY a.time DESC ").toString();
		}
		else {
			ourSqlStr = outSql.append(inSqlStr).append(") a ORDER BY a.time DESC LIMIT ").append(startRow).append(",").append(pageSize).toString();
		}
		List<Map> opList = mapDao.executeSql(ourSqlStr);
		return opList;
	}
	
	@Override
	public List<Map> getOperator(String userName, String startTime, String endTime, int pageIndex, int pageSize) throws Exception {
		if(StringUtils.isBlank(userName)) {
			return Collections.emptyList();
		}
		//根据用户名获取区域
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
		List<Map> areaList = (List<Map>) reslMap.get("response_params");
		// 循环获取操作历史记录
		if(areaList.isEmpty()) {
			return Collections.emptyList();
		}
		int startRow = (pageIndex - 1) * pageSize;
		startRow = startRow < 0? 0 : startRow;
		StringBuilder outSql = new StringBuilder("SELECT * FROM (");
		StringBuilder inSql = new StringBuilder();
		Iterator<Map> itor = areaList.iterator();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//获取数据存在的tableName
		StringBuilder getTableSql = new StringBuilder("SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_SCHEMA='zigbeedevice' AND TABLE_NAME in (");
		while(itor.hasNext()) {
			Map aMap = itor.next();
			String houseIEEE = (String) aMap.get("houseIEEE");
			String tableName = "deviceoperatehistory_" + houseIEEE + "_" + year;
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
		FarmUser farmuser = HouseieeeListener.farmUserMap.get(userName);
		//生成sql
		itor = areaList.iterator();
		while(itor.hasNext()) {
			Map aMap = itor.next();
			String houseIEEE = (String) aMap.get("houseIEEE");
			String tableName = "deviceoperatehistory_" + houseIEEE.toLowerCase() + "_" + year;
			//判断当前表是否存在
			if(cacheTable.get(tableName) == null) {
				continue;
			}
			inSql.append("select t.id,t.username userName,t.device_ieee ieee,t.device_ep ep,t.opdatetime time,f.operateNameCn description,")
			.append("z.device_name deviceName, t.opparam as opparam from ").append(tableName).append(" t inner join operateieslib f on t.opname=f.opname and t.optype=f.optype ")
			.append("LEFT JOIN farmdevice z ON t.houseIEEE=z.house_ieee AND t.device_ieee=z.ieee AND t.device_ep=z.ep ")
			.append("WHERE t.opdatetime BETWEEN '").append(startTime).append("' and '").append(endTime).append("'");
			if(farmuser!=null&&farmuser.getParentId()!=null&&farmuser.getParentId()>0){
				inSql.append(" and t.username='").append(userName).append("'");
			}
			inSql.append(" UNION ");
		}
		String inSqlStr = inSql.substring(0, inSql.length() - 7);
		String ourSqlStr = null;
		if(pageSize == -1) { //获取所有记录
			ourSqlStr = outSql.append(inSqlStr).append(") a ORDER BY a.time DESC ").toString();
		}
		else {
			ourSqlStr = outSql.append(inSqlStr).append(") a ORDER BY a.time DESC LIMIT ").append(startRow).append(",").append(pageSize).toString();
		}
		List<Map> opList = mapDao.executeSql(ourSqlStr);
		return opList;
	}
	@Override
	public Map getOnlineTime(String houseIeee, String deviceIeee, String deviceEp) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		long diff = 0;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tablename = "deviceoperatehistory_" + houseIeee + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select t.lasttime,f.opdatetime as onlinetime from device t ");
		sql.append(" left join ").append(tablename).append(" f on f.houseIEEE=t.houseIEEE and f.device_ieee=t.ieee and f.device_ep = t.ep where 1=1");
		if(StringUtils.isNotBlank(houseIeee)) {
			sql.append(" and t.houseIEEE =:houseIEEE ");
			params.put("houseIEEE", houseIeee);
		}
		if(StringUtils.isNotBlank(deviceIeee)) {
			sql.append(" and t.ieee =:ieee ");
			params.put("ieee", deviceIeee);
		}
		if(StringUtils.isNotBlank(deviceEp)) {
			sql.append(" and t.ep =:ep ");
			params.put("ep", deviceEp);
		}
		sql.append(" order by f.opdatetime desc limit 1 ");
		List<Map> list = mapDao.executeSql(sql.toString(), params);	
		String date = "2016-10-01 10:10:10.0";
		String last = list.get(0).get("lasttime")==null?"---":list.get(0).get("lasttime").toString();
		String ontime = list.get(0).get("onlinetime")==null?"---":list.get(0).get("onlinetime").toString();
		if(ontime.equals("---")) {
			rt.put("difftime", "---");
		}
		else if(!ontime.equals("---") && ontime.equals(last)) {
			diff = 1;
			rt.put("difftime", "0小时");
		}
		else if(!ontime.equals(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date end = sdf.parse(last);
			Date start = sdf.parse(ontime);
			diff = (end.getTime()-start.getTime())/(1000);
			if(diff >= 3600) {
				rt.put("difftime", (long)(diff/3600) + "小时");
			}
			if(diff < 3600 && diff >= 60) {
				rt.put("difftime", (long)(diff/60) + "分钟");
			}
			if(diff > 0 && diff < 60) {
				rt.put("difftime", diff + "秒");
			}
		}
		rt.put("list", list);
		return rt;
	}
	@Override
	public Map operateActionUser(DeviceoperatehistoryHouseidYear deviceoperatehistoryHouseidYear) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "deviceOperateHistory_" + deviceoperatehistoryHouseidYear.getHouseIeee() + "_" + year;
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select DISTINCT f.operateNameCn,f.operateName from ").append(tableName).append(" t  inner join operatelib f on t.opname=f.opname  and t.optype=f.optype ");
		sql.append(" left join device d on t.houseIEEE=d.houseIEEE and t.device_ieee = d.ieee  and t.device_ep = d.ep  where 1=1 ");
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getHouseIeee())) {
			sql.append(" and t.houseIeee =:houseIeee ");
			param.put("houseIeee", deviceoperatehistoryHouseidYear.getHouseIeee());
		}
		if(!deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee())) {
			sql.append(" and t.device_ieee =:deviceIeee ");
			param.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());
		}
		if(!deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceEp())) {
			sql.append(" and t.device_ep =:deviceEp ");
			param.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}
		/*if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee())) {
			sql.append(" and t.device_ieee =:deviceIeee ");
			param.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());		
		}
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceEp())) {
			sql.append(" and t.device_ep =:deviceEp ");
			param.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}*/
		List<Map> Actionlist = mapDao.executeSql(sql.toString(), param);
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select DISTINCT t.username from ").append(tableName).append(" t  inner join operatelib f on t.opname=f.opname  and t.optype=f.optype ");
		hql.append(" left join device d on t.houseIEEE=d.houseIEEE and t.device_ieee = d.ieee  and t.device_ep = d.ep  where 1=1 ");
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getHouseIeee())) {
			hql.append(" and t.houseIeee =:houseIeee ");
			map.put("houseIeee", deviceoperatehistoryHouseidYear.getHouseIeee());
		}
		if(!deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee())) {
			sql.append(" and t.device_ieee =:deviceIeee ");
			param.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());
		}
		if(!deviceoperatehistoryHouseidYear.getHouseIeee().equals(deviceoperatehistoryHouseidYear.getDeviceIeee()) && StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceEp())) {
			sql.append(" and t.device_ep =:deviceEp ");
			param.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}
		/*if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceIeee())) {
			hql.append(" and t.device_ieee =:deviceIeee ");
			map.put("deviceIeee", deviceoperatehistoryHouseidYear.getDeviceIeee());		
		}
		if(StringUtils.isNotBlank(deviceoperatehistoryHouseidYear.getDeviceEp())) {
			hql.append(" and t.device_ep =:deviceEp ");
			map.put("deviceEp", deviceoperatehistoryHouseidYear.getDeviceEp());
		}*/
		List<Map> Userlist = mapDao.executeSql(hql.toString(), map);
		rt.put("Actionlist", Actionlist);
		rt.put("Userlist", Userlist);
		return rt;
	}

	@Override
	public int exportOperateLogExcel(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "deviceOperateHistory_" + param.get("houseIeee")+ "_" + year;
		StringBuffer sql = new StringBuffer();
		//sql.append("select t.*, f.operateName,f.operateNameCn,d.deviceName,d.isonline from ").append(tableName).append(" t join operatelib f on t.opname=f.opname " +
		sql.append("select t.*, f.operateName,f.operateNameCn,d.deviceName,d.isonline from ").append(tableName).append(" t inner join operatelib f on t.opname=f.opname " +
				" and t.optype=f.optype " +
				"left join device d on t.houseIEEE=d.houseIEEE" +
				" and t.device_ieee = d.ieee " +
				" and t.device_ep = d.ep where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank((String)param.get("houseIeee"))) {
			sql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", (String)param.get("houseIeee"));
		}
		if(!param.get("houseIeee").equals(param.get("deviceIeee")) && StringUtils.isNotBlank((String)param.get("deviceIeee"))){
			sql.append(" and t.device_ieee =:deviceIeee ");
			params.put("deviceIeee", param.get("deviceIeee"));
		}
		if(!param.get("houseIeee").equals(param.get("deviceIeee")) && StringUtils.isNotBlank((String)param.get("deviceEp"))){
			sql.append("and t.device_ep = :deviceEp ");
			params.put("deviceEp", param.get("deviceEp"));
		}
		/*if(StringUtils.isNotBlank((String)param.get("deviceIeee"))) {
			sql.append(" and t.device_ieee =:deviceIeee ");
			params.put("deviceIeee", param.get("deviceIeee"));
		}
		if(StringUtils.isNotBlank((String)param.get("deviceEp"))) {
			sql.append("and t.device_ep = :deviceEp ");
			params.put("deviceEp", param.get("deviceEp"));
		}*/
		if(StringUtils.isNotBlank((String)param.get("username")) && !param.get("username").equals("-1") ) {
			if(param.get("username").equals("No")){
				param.put("username", "");
			}
			sql.append(" and t.username =:username ");
			params.put("username", param.get("username"));
			logger.info("操作者的名称：----:"+params.get("username"));
		}
		
		if(StringUtils.isNotBlank((String)param.get("operateNameCn")) && !param.get("operateNameCn").equals("-1")){
			if(StringUtils.isNotBlank((String) param.get("lang")) && param.get("lang").equals("1")) {
				sql.append(" and f.operateName =:operateName ");
				params.put("operateName", param.get("operateNameCn"));
			}else {
				sql.append(" and f.operateNameCn =:operateNameCn ");
				params.put("operateNameCn", param.get("operateNameCn"));
			}
		}		
		
		/*if(StringUtils.isNotBlank((String)param.get("operateNameCn")) && !param.get("operateNameCn").equals("-1")){
			sql.append(" and f.operateNameCn =:operateNameCn ");
			params.put("operateNameCn", param.get("operateNameCn"));
		}*/
		if(StringUtils.isNotBlank((String)param.get("operateName"))) {
			sql.append("and (f.operateName =:operateName or f.operateNameCn =:operateName) ");
			params.put("operateName", param.get("operateName"));
		}
		// 设备名、用户名、操作名多项模糊查找
		if (param.get("houseIeee").equals(param.get("deviceIeee")) && StringUtils.isNotBlank((String)param.get("deviceName"))){//&& deviceoperatehistoryHouseidYear.getOptype().equals("-99")) {
			sql.append("and ((d.deviceName like :deviceName) or (t.device_Ieee like :deviceIeee)) ");
			//params.put("username", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
			params.put("deviceName", "%" + param.get("deviceName") + "%");
			//params.put("operateNameCn", "%" + deviceoperatehistoryHouseidYear.getDeviceName() + "%");
			params.put("deviceIeee", "%" + param.get("deviceName") + "%");
		}
		if (StringUtils.isNotBlank((String)param.get("starttime"))) {
		    sql.append(" and t.opdatetime between '").append(param.get("starttime")).append("' and '").append(param.get("endtime")).append("'");
		}	
		/*默认导出当前页面*/
		/*if(StringUtils.isBlank((String)param.get("starttime"))&&StringUtils.isBlank((String)param.get("endtime"))&&param.get("username").equals("-1")&&param.get("operateNameCn").equals("-1")&&StringUtils.isBlank((String)param.get("deviceName"))) {
			sql.append(" order by t.opdatetime desc limit 30;");	
		}*/
		logger.info("获得的sql语句-==-=-=-："+sql);
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
				workbook.setSheetName(0, "operation history");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				if(param.get("houseIeee").equals(param.get("deviceIeee"))){
				cell.setCellValue("Action name");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Device name");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Device information");
				cell.setCellStyle(titleStyle);
				
				cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("More operation");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Operator");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Operation time");
				cell.setCellStyle(titleStyle);
				int lqiSize = t.size();
				for(int i = 0; i < lqiSize; i++) {
					row = sheet.createRow(i + 1);					
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("operateName")==null?"None":t.get(i).get("operateName").toString());
					cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("deviceName")==null?"---":t.get(i).get("deviceName").toString());
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					String Ieee = t.get(i).get("device_ieee")==null?"":t.get(i).get("device_ieee").toString();
					String Ep =  t.get(i).get("device_ep")==null?"":t.get(i).get("device_ep").toString();
					if(!Ep.equals("")){
						cell.setCellValue(Ieee +"-"+ Ep);
					}else {
						cell.setCellValue(Ieee);
					}
				    cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
				    cell.setCellValue("Select");
				    
					cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("username")==null?"None":t.get(i).get("username").toString());
					
					cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("opdatetime").toString());
				 }
				}else {
					cell.setCellValue("Action name");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("Device name");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("More operation");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("Operator");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("Operation time");
					cell.setCellStyle(titleStyle);
					int lqiSize = t.size();
					for(int i = 0; i < lqiSize; i++) {
						row = sheet.createRow(i + 1);
						
						cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("operateName")==null?"None ":t.get(i).get("operateName").toString());
						cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("deviceName")==null?"---":t.get(i).get("deviceName").toString());
						
						cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
						String Ieee = t.get(i).get("device_ieee")==null?"":t.get(i).get("device_ieee").toString();
						String Ep =  t.get(i).get("device_ep")==null?"":t.get(i).get("device_ep").toString();
						if(!Ep.equals("")){
							cell.setCellValue(Ieee +"-"+ Ep);
						}else {
							cell.setCellValue(Ieee);
						}					
						cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("username")==null?"None":t.get(i).get("username").toString());
						
						cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("opdatetime").toString());
					}
				}
				//设置response参数
				//对中文字符转码
				String fileName = "operation history.xls";		
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
				workbook.setSheetName(0, "操作历史列表");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				if(param.get("houseIeee").equals(param.get("deviceIeee"))){
				cell.setCellValue("动作名称");
				 cell.setCellStyle(titleStyle);
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("设备名称");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("设备信息");
				cell.setCellStyle(titleStyle);
				
				cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("更多操作");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("操作者");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("操作时间");
				cell.setCellStyle(titleStyle);
				int lqiSize = t.size();
				for(int i = 0; i < lqiSize; i++) {
					row = sheet.createRow(i + 1);
					
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("operateNameCn")==null?"无":t.get(i).get("operateNameCn").toString());
					cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("deviceName")==null?"---":t.get(i).get("deviceName").toString());
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					String Ieee = t.get(i).get("device_ieee")==null?"":t.get(i).get("device_ieee").toString();
					String Ep =  t.get(i).get("device_ep")==null?"":t.get(i).get("device_ep").toString();
					if(!Ep.equals("")){
						cell.setCellValue(Ieee +"-"+ Ep);
					}else {
						cell.setCellValue(Ieee);
					}
				    cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
				    cell.setCellValue("选择");
					cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("username")==null?"无":t.get(i).get("username").toString());
					
					cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("opdatetime").toString());
				 }
				}else {
					cell.setCellValue("动作名称");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("设备名称");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("设备信息");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("操作者");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("操作时间");
					cell.setCellStyle(titleStyle);
					int lqiSize = t.size();
					for(int i = 0; i < lqiSize; i++) {
						row = sheet.createRow(i + 1);
						
						cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("operateNameCn")==null?"无":t.get(i).get("operateNameCn").toString());
						cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("deviceName")==null?"---":t.get(i).get("deviceName").toString());
						
						cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
						String Ieee = t.get(i).get("device_ieee")==null?"":t.get(i).get("device_ieee").toString();
						String Ep =  t.get(i).get("device_ep")==null?"":t.get(i).get("device_ep").toString();
						if(!Ep.equals("")){
							cell.setCellValue(Ieee +"-"+ Ep);
						}else {
							cell.setCellValue(Ieee);
						}					
						cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("username")==null?"无":t.get(i).get("username").toString());
						
						cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("opdatetime").toString());
					}
				}
				//设置response参数
				//对中文字符转码
				String fileName = "操作历史列表.xls";		
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


}
