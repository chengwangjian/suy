package sy.service.impl;

import java.io.BufferedOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.RssiRecord;
import sy.service.RssiRecordServiceI;
import sy.util.PropertiesUtil;

/**
 * @author huanglw
 *
 */
@Service("rssiRecordService")
public class RssiRecordServiceImpl implements RssiRecordServiceI {
	private static final Logger logger = Logger.getLogger(RssiRecordServiceImpl.class);
	private BaseDaoI<RssiRecord> rssiRecordDao;
	private BaseDaoI<Map> mapDao;
	//缓存RSSI记录，再批量插入
	private Map<String, List<RssiRecord>> cacheRssiRecord = new HashMap<String, List<RssiRecord>>();
	//缓存的记录数
	private int cacheCount;
	//批量缓存的阀值
	private final int batchNum = Integer.parseInt(PropertiesUtil.getProperty("batch.rssi"));
	
	public BaseDaoI<RssiRecord> getRssiRecordDao() {
		return rssiRecordDao;
	}
	
	@Autowired
	public void setRssiRecordDao(BaseDaoI<RssiRecord> rssiRecordDao) {
		this.rssiRecordDao = rssiRecordDao;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	/**
	 * 创建rssi表
	 * @param houseIEEE
	 */
	private void addRsRecTable(String houseIEEE) {
		String tableName = "rssirecord_" + houseIEEE.toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);
		String sql ="Create TABLE if not exists "+tableName+" LIKE rssirecord";
		rssiRecordDao.executeSql2(sql);
	}
	
	/* (non-Javadoc)
	 * @see sy.service.RssiRecordServiceI#add(sy.model.RssiRecord)
	 */
	@Override
	public void add(RssiRecord rssiRecord) throws Exception {
		++cacheCount;
		String houseIEEE = rssiRecord.getHouse_ieee();
		List<RssiRecord> rssiList = cacheRssiRecord.get(houseIEEE);
		if(rssiList == null) {
			rssiList = new ArrayList<RssiRecord>();
			rssiList.add(rssiRecord);
			cacheRssiRecord.put(houseIEEE, rssiList);
		}
		else {
			rssiList.add(rssiRecord);
		}
		if(cacheCount > batchNum) {
			this.saveRssiRecords();
		}
	}
	

	/* (non-Javadoc)
	 * @see sy.service.RssiRecordServiceI#getRSSICount(java.util.Map)
	 */
	@Override
	public int getRSSICount(String searchText, String houseIeee,String type,String time, Map map) throws Exception {
		// TODO Auto-generated method stub
		//String tableName="";
		Map<String, Object> params = new HashMap<String, Object>();
		//String tableName = "rssirecord_00137a0000012ddf_2015";
		String tableName = "rssirecord_" + houseIeee.toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);
		// tableName = "rssirecord_00137a0000012ddf_2015";
			//tableName = "rssirecord_00137a0000012dff_2015";
			//time="2016-01-11";
		StringBuilder sql = new StringBuilder("select count(*) as rssi_count from ");		
		sql.append(tableName).append(" a ");
		sql.append("left join node e on a.house_ieee = e.houseIEEE and a.source_ieee = e.ieee ");
		sql.append("left join node g on a.house_ieee = g.houseIEEE and a.dest_ieee = g.ieee ");
		sql.append("left join nodeType f on e.nodeType = f.id ");
		sql.append("where a.house_ieee=:houseIeee and LQI != 10000 and LQI IS NOT NULL ");
		if(type.equals("scanresult")){
			sql.append(" and a.push_time>:time ");	
			params.put("time", time);
		}
//		sql.append("where a.house_ieee=").append(houseIeee).append(" ");
//		if(!StringUtils.isBlank(searchText))
//			sql.append("and (").append(getVagueCondition(searchText)).append(") ");
		params.put("houseIeee", houseIeee);
		if(!houseIeee.equals(map.get("deviceIeee")) && StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
			 sql.append(" and a.source_ieee =:sourceIeee ");
			 params.put("sourceIeee", map.get("deviceIeee"));
		}
		/*if(StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
		    sql.append(" and a.source_ieee =:sourceIeee ");
		    params.put("sourceIeee", map.get("deviceIeee"));
		}*/
		if(StringUtils.isNotBlank((String)map.get("starttime"))) {
		    sql.append(" and a.count_time between '").append(map.get("starttime")).append("' and '").append(map.get("endtime")).append("'");   
		}
		if(houseIeee.equals(map.get("deviceIeee")) && !StringUtils.isBlank(searchText)) {
			params.put("searchText", "%" + searchText + "%");
			sql.append("and (").append(getVagueCondition()).append(") ");
		}		
		if(!houseIeee.equals(map.get("deviceIeee")) && !StringUtils.isBlank(searchText)) {
			params.put("searchText", "%" + searchText + "%");
			sql.append("and (").append(getVagueCondition2()).append(") ");
		}
		List<Map> cList = mapDao.executeSql(sql.toString(), params);
		return ((BigInteger) ((Map) cList.get(0)).get("rssi_count")).intValue();
	}

	/* (non-Javadoc)
	 * @see sy.service.RssiRecordServiceI#queryRSSI(java.util.Map, int, int)
	 */
	@Override
	public List<Map> queryRSSI(String searchText, int startRow, int pageSize, String orderBy, String houseIeee,String type,String time, Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		//String tableName="";
		String tableName = "rssirecord_" + houseIeee.toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);
               //tableName = "rssirecord_00137a0000012dff_2015";
           //	time="2016-01-11";
		StringBuilder sql = new StringBuilder("select a.id as id,a.source_ieee as source_ieee,e.nodeName as source_name,f.name as net_role,f.enName as net_enrole,");
		sql.append("a.dest_ieee as dest_ieee,g.nodeName as dest_name,a.LQI as LQI,a.RSSI as RSSI, a.count_time as count_time ");
		sql.append("from ").append(tableName).append(" a ");
		sql.append("left join node e on a.house_ieee = e.houseIEEE and a.source_ieee = e.ieee ");
		sql.append("left join node g on a.house_ieee = g.houseIEEE and a.dest_ieee = g.ieee ");
		sql.append("left join nodeType f on e.nodeType = f.id ");
		sql.append("where a.house_ieee=:houseIeee and LQI != 10000 and LQI IS NOT NULL  ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(type.equals("scanresult")){
			sql.append(" and a.push_time>:time ");	
			params.put("time", time);
		}
		params.put("houseIeee", houseIeee);	
		if(!houseIeee.equals(map.get("deviceIeee")) && StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
			 sql.append(" and a.source_ieee =:sourceIeee ");
			 params.put("sourceIeee", map.get("deviceIeee"));
		}
		/*if(StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
		    sql.append(" and a.source_ieee =:sourceIeee ");
		    params.put("sourceIeee", map.get("deviceIeee"));
		}*/
		if(StringUtils.isNotBlank((String)map.get("starttime"))) {
		    sql.append(" and a.count_time between '").append(map.get("starttime")).append("' and '").append(map.get("endtime")).append("'");   
		}
		if(houseIeee.equals(map.get("deviceIeee")) && !StringUtils.isBlank(searchText)) {
			params.put("searchText", "%" + searchText + "%");
			sql.append("and (").append(getVagueCondition()).append(") ");
		}
		if(!houseIeee.equals(map.get("deviceIeee")) && !StringUtils.isBlank(searchText)) {
			params.put("searchText", "%" + searchText + "%");
			sql.append("and (").append(getVagueCondition2()).append(") ");
		}
//		sql.append("where a.house_ieee=").append(houseIeee).append(" ");
//		if(!StringUtils.isBlank(searchText))
//			sql.append("and (").append(getVagueCondition(searchText)).append(") ");			
		if(StringUtils.isBlank(orderBy))
			sql.append("order by a.count_time desc limit ");
		else
			sql.append("order by ").append(orderBy).append(" limit ");
//			sql.append("order by :orderBy limit ");
		sql.append(startRow).append(",").append(pageSize);
			
//		params.put("startRow", startRow);
//		params.put("pageSize", pageSize);
//		params.put("orderBy", orderBy);
		List<Map> uList = mapDao.executeSql(sql.toString(), params);
		return uList;
	}

	public int getNormalDeviceCount(String searchText,
			int rssiBValue, String houseIeee,String type,String time, Map<String, Object> map) throws Exception {
		        String tableName="";
               tableName = "rssirecord_" + houseIeee.toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);
            	// tableName = "rssirecord_00137a0000012dff_2015";
                // time="2016-01-11";
		StringBuilder sql = new StringBuilder("select count(*) as normal_count from ");
		sql.append(tableName).append(" a ");
		sql.append("left join node e on a.house_ieee = e.houseIEEE and a.source_ieee = e.ieee ");
		sql.append("left join node g on a.house_ieee = g.houseIEEE and a.dest_ieee = g.ieee ");
		sql.append("left join nodeType f on e.nodeType = f.id ");
		sql.append("where LQI > :rssiBValue and LQI != 10000 and LQI IS NOT NULL and a.house_ieee=:houseIeee ");
		
//		sql.append("where RSSI >= ").append(rssiBValue).append(" and a.house_ieee=").append(houseIeee).append(" ");
		Map<String, Object> params = new HashMap<String, Object>();		
		if(type.equals("scanresult")){
			sql.append(" and a.push_time>:time ");	
			params.put("time", time);
		}
		if(!StringUtils.isBlank(searchText)) {
			params.put("searchText", "%" + searchText + "%");
//			sql.append(" and (").append(getVagueCondition(searchText)).append(")");
			sql.append(" and (").append(getVagueCondition()).append(")");
		}
		if(!houseIeee.equals(map.get("deviceIeee")) && StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
			sql.append(" and a.source_ieee =:sourceIeee ");
		    params.put("sourceIeee", map.get("deviceIeee"));
		}
		/*if(StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
		    sql.append(" and a.source_ieee =:sourceIeee ");
		    params.put("sourceIeee", map.get("deviceIeee"));
		}*/
		if(StringUtils.isNotBlank((String)map.get("starttime"))) {
		    sql.append(" and a.count_time between '").append(map.get("starttime")).append("' and '").append(map.get("endtime")).append("'");   
		}
		params.put("houseIeee", houseIeee);
		params.put("rssiBValue", rssiBValue);
		List<Map> cList = mapDao.executeSql(sql.toString(), params);
		return ((BigInteger) ((Map) cList.get(0)).get("normal_count")).intValue();
	}
	/**
	 * 获取模糊查询的条件字符串
	 * @param searchText
	 * @return
	 */
	private String getVagueCondition() {
		StringBuilder conSql = new StringBuilder("a.source_ieee like :searchText or ");
		conSql.append("e.nodeName like :searchText or ");
		conSql.append("a.dest_ieee like :searchText or ");
		conSql.append("g.nodeName like :searchText or ");
		conSql.append("a.LQI like :searchText or ");
		conSql.append("convert(a.count_time using gb2312) like :searchText");
		conSql.append(" or f.name like :searchText");
		return conSql.toString();
	}
	
	/**
	 * 设备获取模糊查询的条件字符串
	 * @param searchText
	 * @return
	 */
	private String getVagueCondition2() {
		StringBuilder conSql = new StringBuilder("a.dest_ieee like :searchText or ");
		conSql.append("e.nodeName like :searchText or ");
		conSql.append("g.nodeName like :searchText or ");
		conSql.append("a.LQI like :searchText or ");
		conSql.append("convert(a.count_time using gb2312) like :searchText");
		conSql.append(" or f.name like :searchText");
		return conSql.toString();
	}
	
	
	/**
	 * RSSI获取模糊查询的条件字符串
	 * @param searchText
	 * @return
	 */
	private String getNewVagueCondition() {
		StringBuilder conSql = new StringBuilder("a.source_ieee like :searchText or ");
		conSql.append("e.nodeName like :searchText or ");
		conSql.append("a.dest_ieee like :searchText or ");
		conSql.append("g.nodeName like :searchText or ");
		conSql.append("a.RSSI like :searchText or ");
		conSql.append("convert(a.count_time using gb2312) like :searchText");
		/*conSql.append(" or f.name like :searchText");*/
		return conSql.toString();
	}
	
	/**
	 * 设备RSSI获取模糊查询的条件字符串
	 * @param searchText
	 * @return
	 */
	private String getNewVagueCondition2() {
		StringBuilder conSql = new StringBuilder("a.dest_ieee like :searchText or ");
		conSql.append("e.nodeName like :searchText or ");
		conSql.append("g.nodeName like :searchText or ");
		conSql.append("a.RSSI like :searchText or ");
		conSql.append("convert(a.count_time using gb2312) like :searchText");
		/*conSql.append(" or f.name like :searchText");*/
		return conSql.toString();
	}

	@Override
	public Object getNoResponseDevice(int startRow, int pageSize, String houseIeee, String type,String time) {
		// TODO Auto-generated method stub
		Map result=null;
		try {	
			String tableName = "rssirecord_" + houseIeee.toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);
	        result=new HashMap<String,Object>();
	        HashMap<String,Object> params=new HashMap<String, Object>();
			StringBuilder noresponseinfosql = new StringBuilder("SELECT node.ieee,f.name,node.nodeName FROM node left join nodeType f ");
			noresponseinfosql.append("on node.nodeType = f.id WHERE node.houseIEEE=:houseIeee and node.ieee not in (SELECT * from (SELECT ")
			.append("DISTINCT a.dest_ieee as  ieee FROM ").append(tableName).append(" a WHERE house_ieee=:houseIeee and push_time>:time UNION ")
			.append("SELECT  DISTINCT a.source_ieee as ieee FROM ").append(tableName).append(" a WHERE house_ieee=:houseIeee and push_time>:time) a) ")
			.append("AND node.ieee IS NOT NULL AND node.ieee<>'' LIMIT ").append(startRow).append(",").append(pageSize);
			params=new HashMap<String, Object>();
			params.put("houseIeee", houseIeee);
			params.put("time", time);
			List<Map> noresponseinfolist=this.mapDao.executeSql(noresponseinfosql.toString(), params);
			if(!noresponseinfolist.isEmpty()){
			  result.put("noresponseinfo", noresponseinfolist);
			}
		} catch (Exception e) {
			HashMap<String,Object> params=new HashMap<String, Object>();
			params.put("houseIeee", houseIeee);
		    String noresponseinfosql="SELECT node.ieee,f.name,node.nodeName FROM node left join nodeType f on node.nodeType = f.id WHERE node.houseIEEE=:houseIeee";
		    noresponseinfosql=noresponseinfosql + " LIMIT " + startRow + "," + pageSize;
		    List<Map> map=this.mapDao.executeSql(noresponseinfosql, params);
		    if(!map.isEmpty()){
		    	result.put("noresponseinfo", map);
		    }
		}  
		
	
		return result;
	}

	@Override
	public Object getNoResponseCount(Map map,String time) {
		    Map result=null;
			String tableName = "rssirecord_" + map.get("HouseIEEE").toString().toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);
			HashMap<String,Object> params=null;
			try {
				 result=new HashMap<String,Object>();
				 String totalsql="SELECT count(*) AS count FROM "+tableName+"  WHERE push_time>:time and house_ieee=:houseIeee";	
			     params=new HashMap<String, Object>();
			     params.put("time", time);
			     params.put("houseIeee", map.get("HouseIEEE"));
			     List<Map> list=this.mapDao.executeSql(totalsql, params);
			     if(!list.isEmpty()){
			    	 result.put("total", list.get(0).get("count")); 
			     }
			     String devicetotal="SELECT count(*) AS count FROM node WHERE node.houseIEEE=:houseIeee and node.ieee IS NOT NULL AND node.ieee<>''";
			     params=new HashMap<String, Object>();
			     params.put("houseIeee", map.get("HouseIEEE"));
			     List<Map> devicetotalmap=this.mapDao.executeSql(devicetotal, params);
			     if(!devicetotalmap.isEmpty()){
			    	 result.put("devicetotal", devicetotalmap.get(0).get("count")); 
			     }
			    String noresponsetotalsql="SELECT count(*) AS count FROM node WHERE node.houseIEEE=:houseIeee and node.ieee not in( SELECT * from (SELECT  DISTINCT a.dest_ieee as  ieee FROM "+tableName+" a WHERE house_ieee=:houseIeee and push_time>:time union SELECT  DISTINCT a.source_ieee as ieee FROM "+tableName+" a WHERE house_ieee=:houseIeee and push_time>:time) a) AND node.ieee IS NOT NULL AND node.ieee<>''";
				params=new HashMap<String, Object>();
				params.put("houseIeee", map.get("HouseIEEE"));
				params.put("time", time);
				List<Map> noresponsetotalmap=this.mapDao.executeSql(noresponsetotalsql, params);
				if(!noresponsetotalmap.isEmpty()){
			       result.put("noresponsetotal", noresponsetotalmap.get(0).get("count"));
			    	 
				}
				String abnormaltotalsql="SELECT  count(DISTINCT a.source_ieee ) AS count FROM "+tableName+" a WHERE house_ieee=:houseIeee and push_time>:time AND RSSI<100";
				params=new HashMap<String, Object>();
				params.put("houseIeee", map.get("HouseIEEE"));
				params.put("time", time);
				List<Map> abnormaltotalmap= this.mapDao.executeSql(abnormaltotalsql, params);
				if(!abnormaltotalmap.isEmpty()){
					result.put("abnormaltotal", abnormaltotalmap.get(0).get("count"));	
				}
				int a=((BigInteger)(devicetotalmap.get(0).get("count"))).intValue()-((BigInteger)(noresponsetotalmap.get(0).get("count"))).intValue()-((BigInteger)(abnormaltotalmap.get(0).get("count"))).intValue();
				result.put("normaltotal",a );	
						
			} catch (Exception e) {
				// TODO: handle exception
				 result.put("total", 0);
				 String devicetotal="SELECT count(*) AS count FROM node WHERE node.houseIEEE=:houseIeee";
			     params=new HashMap<String, Object>();
			     params.put("houseIeee", map.get("HouseIEEE"));
			     List<Map> devicetotalmap=this.mapDao.executeSql(devicetotal, params);
			     result.put("devicetotal", devicetotalmap.get(0).get("count"));
			     result.put("noresponsetotal", devicetotalmap.get(0).get("count"));
			     result.put("abnormaltotal", 0);
			     result.put("normaltotal", 0);
			}
		
		    return result;
	}

	@Override
	public void saveRssiRecords() throws Exception {
		if(cacheRssiRecord.isEmpty()) {
			logger.info("---------no rssi to save");
			return;
		}
		logger.info("----------save rssi");
		//清0
		cacheCount = 0;
		Map<String, List<RssiRecord>> tempCache = cacheRssiRecord;
		cacheRssiRecord = new HashMap<String, List<RssiRecord>>();
		//插入数据
		Iterator<String> itor = tempCache.keySet().iterator();
		while(itor.hasNext()) {
			String houseIEEE = itor.next();
			List<RssiRecord> rssiList = tempCache.get(houseIEEE);
			if(rssiList != null && !rssiList.isEmpty()) {
				String tableName = "rssirecord_" + houseIEEE + "_" + Calendar.getInstance().get(Calendar.YEAR);
				StringBuilder instSql = new StringBuilder("insert into ");
				instSql.append(tableName).append("(source_ieee,dest_ieee,LQI,RSSI,count_time,push_time,house_ieee) values");
				Iterator<RssiRecord> rssiItor = rssiList.iterator();
				while(rssiItor.hasNext()) {
					RssiRecord rssiRecord = rssiItor.next();
					instSql.append("('").append(rssiRecord.getSource_ieee()).append("','").append(rssiRecord.getDest_ieee());
					instSql.append("',").append(rssiRecord.getLqi()).append(",").append(rssiRecord.getRssi()).append(",'").append(rssiRecord.getTime()).append("','");
					instSql.append(rssiRecord.getPush_time()).append("','").append(rssiRecord.getHouse_ieee()).append("'),");
				}
				String instSqlStr = instSql.deleteCharAt(instSql.length() - 1).toString();
				try {
					rssiRecordDao.executeSql2(instSqlStr);
				} catch(Exception e) {
					String msg = e.getMessage();
					//当表不存在时创建并插入数据
					if(msg.indexOf(tableName.toLowerCase()) != -1) {
						addRsRecTable(houseIEEE);
						rssiRecordDao.executeSql2(instSqlStr);
					}
					else {
						throw e;
					}
				}
			}
		}
	}
	
	@Override
	public void saveRssiRecordsImmtly(RssiRecord rssiRecord) throws Exception {
		if(rssiRecord == null || StringUtils.isBlank(rssiRecord.getHouse_ieee())) {
			logger.error("rssiRecord or houseIEEE can't be empty");
			return;
		}
		String houseIEEE = rssiRecord.getHouse_ieee();
		String tableName = "rssirecord_" + houseIEEE + "_" + Calendar.getInstance().get(Calendar.YEAR);
		StringBuilder instSql = new StringBuilder("insert into ");
		instSql.append(tableName).append("(source_ieee,dest_ieee,LQI,RSSI,count_time,push_time,house_ieee) values")
		.append("('").append(rssiRecord.getSource_ieee()).append("','").append(rssiRecord.getDest_ieee())
		.append("',").append(rssiRecord.getLqi()).append(",").append(rssiRecord.getRssi()).append(",'").append(rssiRecord.getTime()).append("','")
		.append(rssiRecord.getPush_time()).append("','").append(rssiRecord.getHouse_ieee()).append("')");
		try {
			logger.info("得到的rssirecords的插入语句是----》："+instSql);
			rssiRecordDao.executeSql2(instSql.toString());
		} catch(Exception e) {
			String msg = e.getMessage();
			//当表不存在时创建并插入数据  
			if(msg.indexOf(tableName.toLowerCase()) != -1) {
				addRsRecTable(houseIEEE);
				rssiRecordDao.executeSql2(instSql.toString());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public Map getLR(String houseIeee, String deviceIeee, String deviceEp, String orderBy) throws Exception {
		Map<String, Object> rt = new HashMap<String, Object>();
 		Map<String, Object> map = new HashMap<String, Object>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "rssirecord_" + houseIeee + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT r.LQI,r.count_time,r.RSSI from ").append(tableName).append(" r ");
		sql.append("left join device t on r.house_ieee = t.houseIEEE and t.ieee = r.source_ieee where 1=1 ");
		if(StringUtils.isNotBlank(houseIeee)) {
			sql.append(" and t.houseIEEE =:house_ieee ");
			map.put("house_ieee", houseIeee);
		}
		if(StringUtils.isNotBlank(deviceIeee)) {
			sql.append(" and t.ieee =:ieee ");
			map.put("ieee", deviceIeee);
		}
		if(StringUtils.isNotBlank(deviceEp)) {
			sql.append(" and t.ep =:ep ");
			map.put("ep", deviceEp);
		}
		if(StringUtils.isNotBlank(orderBy)){
			sql.append(" order by ").append(orderBy);
		}else {
			sql.append(" order by r.count_time Desc ");
		}
		sql.append(" limit 2");
		List<Map> list = mapDao.executeSql(sql.toString(), map);
		rt.put("list", list);
		if(!houseIeee.equals(deviceIeee)) {
			int LRsize = list.size();
			if(LRsize == 0) {
				rt.put("lqi1", "无");
				rt.put("lqi2", "无");
				rt.put("rssi1", "无");
				rt.put("rssi2", "无");
			}
			if(LRsize == 1) {
				String ll = list.get(0).get("LQI")==null?"无":list.get(0).get("LQI").toString();
				if(list.get(0).get("LQI") !=null) {
				      int tt = Integer.parseInt(ll);
					  if(tt > 100 && !ll.equals("10000")) {
							rt.put("lqi1","好" + "(" + ll + ")");
						}else if(ll.equals("10000")){
							rt.put("lqi1", "无效值");
						}else{
							rt.put("lqi1","差" + "(" + ll + ")");
						}
				 }else {
					  rt.put("lqi1","无");
				 }
				String ss = list.get(0).get("RSSI")==null?"无":list.get(0).get("RSSI").toString();
				if(list.get(0).get("RSSI") !=null){
					int pp = Integer.parseInt(ss);
					  if(pp > -70 && !ss.equals("10000")) {
							rt.put("rssi1", "好" + "(" + ss + ")");
						}else if(ss.equals("10000")){
							rt.put("rssi1", "无效值");
						}else {
							rt.put("rssi1", "差" + "(" + ss + ")");
					  } 
				 }else {
					  rt.put("rssi1", "无");
				 }
				rt.put("lqi2", "无");
				rt.put("rssi2", "无");
		    }
			if(LRsize == 2) {
		    String ll = list.get(0).get("LQI")==null?"无":list.get(0).get("LQI").toString();		    
			if(list.get(0).get("LQI") !=null) {
				int tt = Integer.parseInt(ll);
				if(tt > 100 && !ll.equals("10000")) {
					rt.put("lqi1","好" + "(" + ll + ")");
				}else if(ll.equals("10000")){
					rt.put("lqi1", "无效值");
				}else {
					rt.put("lqi1","差" + "(" + ll + ")");
				}
			}else {
					rt.put("lqi1","无");
			}
			String ss = list.get(0).get("RSSI")==null?"无":list.get(0).get("RSSI").toString();
			if(list.get(0).get("RSSI") !=null){
				int pp = Integer.parseInt(ss);
				if(pp > -70 && !ss.equals("10000")) {
					rt.put("rssi1", "好" + "(" + ss + ")");
				}else if(ss.equals("10000")){
					rt.put("rssi1", "无效值");
				}else {
					rt.put("rssi1", "差" + "(" + ss + ")");
				} 
				}else {
					rt.put("rssi1", "无");
				}
			String qq = list.get(1).get("LQI")==null?"无":list.get(1).get("LQI").toString();
			String rr = list.get(1).get("RSSI")==null?"无":list.get(1).get("RSSI").toString();
			if(list.get(1).get("LQI") != null) {
				if(!qq.equals("10000")) {
					String ltime = list.get(1).get("count_time").toString();
					rt.put("lqi2", qq + "  " + ltime);
				}else if(qq.equals("10000")) {
					String ltime = list.get(1).get("count_time").toString();
					rt.put("lqi2", "无效值");
				}
			}else {
				rt.put("lqi2", "无");
			}
			if(list.get(1).get("RSSI") !=null){
				if(!rr.equals("10000")){
					String rtime = list.get(1).get("count_time").toString();
					rt.put("rssi2", rr + " "+rtime);
				}else if(rr.equals("10000")){
					rt.put("rssi2", "无效值");
				}
			}else {
				rt.put("rssi2", "无");
			 }
			}
		}else if(houseIeee.equals(deviceIeee)){
			rt.put("lqi1", "无");
			rt.put("lqi2", "无");
			rt.put("rssi1", "无");
			rt.put("rssi2", "无");
		}
			return rt;
	}

	@Override
	public List<Map> queryNewRSSI(String searchText, int startRow, int pageSize, String orderBy, String houseIeee,String type,String time, Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		//String tableName="";
		String tableName = "rssirecord_" + houseIeee.toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);
               //tableName = "rssirecord_00137a0000012dff_2015";
           //	time="2016-01-11";
		StringBuilder sql = new StringBuilder("select a.id as id,a.source_ieee as source_ieee,e.nodeName as source_name,f.name as net_role,f.enName as net_enrole,");
		sql.append("a.dest_ieee as dest_ieee,g.nodeName as dest_name,a.LQI as LQI,a.RSSI as RSSI, a.count_time as count_time ");
		sql.append("from ").append(tableName).append(" a ");
		sql.append("left join node e on a.house_ieee = e.houseIEEE and a.source_ieee = e.ieee ");
		sql.append("left join node g on a.house_ieee = g.houseIEEE and a.dest_ieee = g.ieee ");
		sql.append("left join nodeType f on e.nodeType = f.id ");
		sql.append("where a.house_ieee=:houseIeee and RSSI != 10000 and RSSI IS NOT NULL ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(type.equals("scanresult")){
			sql.append(" and a.push_time>:time ");	
			params.put("time", time);
		}
		params.put("houseIeee", houseIeee);	
		if(!houseIeee.equals(map.get("deviceIeee")) && StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
			 sql.append(" and a.dest_ieee =:sourceIeee ");
			 params.put("sourceIeee", map.get("deviceIeee"));
		}
		if(StringUtils.isNotBlank((String)map.get("starttime"))) {
		    sql.append(" and a.count_time between '").append(map.get("starttime")).append("' and '").append(map.get("endtime")).append("'");   
		}
		if(houseIeee.equals(map.get("deviceIeee")) && !StringUtils.isBlank(searchText)) {
			params.put("searchText", "%" + searchText + "%");
			sql.append("and (").append(getNewVagueCondition()).append(") ");
		}
		if(!houseIeee.equals(map.get("deviceIeee")) && !StringUtils.isBlank(searchText)) {
			params.put("searchText", "%" + searchText + "%");
			sql.append("and (").append(getNewVagueCondition2()).append(") ");
		}
//		sql.append("where a.house_ieee=").append(houseIeee).append(" ");
//		if(!StringUtils.isBlank(searchText))
//			sql.append("and (").append(getVagueCondition(searchText)).append(") ");			
		if(StringUtils.isBlank(orderBy))
			sql.append("order by a.count_time desc limit ");
		else
			sql.append("order by ").append(orderBy).append(" limit ");
//			sql.append("order by :orderBy limit ");
		sql.append(startRow).append(",").append(pageSize);
			
//		params.put("startRow", startRow);
//		params.put("pageSize", pageSize);
//		params.put("orderBy", orderBy);
		logger.info("得到的sql语句：---"+sql);
		List<Map> uList = mapDao.executeSql(sql.toString(), params);
		return uList;
	}

	@Override
	public int getNewNormalDeviceCount(String searchText, int newrssiBValue, String houseIeee,String type,String time, Map<String, Object> map) throws Exception {
        String tableName="";
       tableName = "rssirecord_" + houseIeee.toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);
    	// tableName = "rssirecord_00137a0000012dff_2015";
        // time="2016-01-11";
		StringBuilder sql = new StringBuilder("select count(*) as normal_count from ");
		sql.append(tableName).append(" a ");
		sql.append("left join node e on a.house_ieee = e.houseIEEE and a.source_ieee = e.ieee ");
		sql.append("left join node g on a.house_ieee = g.houseIEEE and a.dest_ieee = g.ieee ");
		sql.append("left join nodeType f on e.nodeType = f.id ");
		sql.append("where RSSI > :newrssiBValue and RSSI != 10000 and a.house_ieee=:houseIeee and RSSI IS NOT NULL ");
		
		//sql.append("where RSSI >= ").append(rssiBValue).append(" and a.house_ieee=").append(houseIeee).append(" ");
		Map<String, Object> params = new HashMap<String, Object>();		
		if(type.equals("scanresult")){
			sql.append(" and a.push_time>:time ");	
			params.put("time", time);
		}
		if(!StringUtils.isBlank(searchText)) {
			params.put("searchText", "%" + searchText + "%");
		//	sql.append(" and (").append(getVagueCondition(searchText)).append(")");
			sql.append(" and (").append(getVagueCondition()).append(")");
		}
		if(!houseIeee.equals(map.get("deviceIeee")) && StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
			sql.append(" and a.dest_ieee =:sourceIeee ");
		    params.put("sourceIeee", map.get("deviceIeee"));
		}
		/*if(StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
		    sql.append(" and a.source_ieee =:sourceIeee ");
		    params.put("sourceIeee", map.get("deviceIeee"));
		}*/
		if(StringUtils.isNotBlank((String)map.get("starttime"))) {
		    sql.append(" and a.count_time between '").append(map.get("starttime")).append("' and '").append(map.get("endtime")).append("'");   
		}
		params.put("houseIeee", houseIeee);
		params.put("newrssiBValue", newrssiBValue);
		List<Map> cList = mapDao.executeSql(sql.toString(), params);
		return ((BigInteger) ((Map) cList.get(0)).get("normal_count")).intValue();
	}


	@Override
	public int exportLqiLogExcel(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
	String tableName = "rssirecord_" + param.get("houseIeee").toString() + "_" + Calendar.getInstance().get(Calendar.YEAR);
	StringBuilder sql = new StringBuilder("select a.id as id,a.source_ieee as source_ieee,e.nodeName as source_name,f.name as net_role,f.enName as net_enrole,");
	sql.append("a.dest_ieee as dest_ieee,g.nodeName as dest_name,a.LQI as LQI,a.count_time as count_time ");
	sql.append("from ").append(tableName).append(" a ");
	sql.append("left join node e on a.house_ieee = e.houseIEEE and a.source_ieee = e.ieee ");
	sql.append("left join node g on a.house_ieee = g.houseIEEE and a.dest_ieee = g.ieee ");
	sql.append("left join nodeType f on e.nodeType = f.id ");
	sql.append("where a.house_ieee=:houseIeee and LQI != 10000 and LQI IS NOT NULL ");
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("houseIeee", param.get("houseIeee"));	
	if(!param.get("houseIeee").equals(param.get("deviceIeee")) && StringUtils.isNotBlank((String)param.get("deviceIeee"))) {
		sql.append(" and a.source_ieee =:sourceIeee ");
	    params.put("sourceIeee", param.get("deviceIeee"));
	}
	/*if(StringUtils.isNotBlank((String)param.get("deviceIeee"))) {
	    sql.append(" and a.source_ieee =:sourceIeee ");
	    params.put("sourceIeee", param.get("deviceIeee"));
	}*/
	if(StringUtils.isNotBlank((String)param.get("starttime"))) {
	    sql.append(" and a.count_time between '").append(param.get("starttime")).append("' and '").append(param.get("endtime")).append("'");   
	}
	if(param.get("houseIeee").equals(param.get("deviceIeee")) && !StringUtils.isBlank((String)param.get("searchText"))) {
		params.put("searchText", "%" + param.get("searchText") + "%");
		sql.append("and (").append(getVagueCondition()).append(") ");
	}
	if(!param.get("houseIeee").equals(param.get("deviceIeee")) && !StringUtils.isBlank((String)param.get("searchText"))) {
		params.put("searchText", "%" + param.get("searchText") + "%");
		sql.append("and (").append(getVagueCondition2()).append(") ");
	}
	List<Map> t = mapDao.executeSql(sql.toString(), params); 
	int length = t.size();
	BufferedOutputStream os = null;
	try {
		if(param.get("lang").equals("1")){
			//生成excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			sheet.setColumnWidth(1, 3500);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(5, 6000);
			workbook.setSheetName(0, "Network quality");
			HSSFRow row = sheet.createRow((short)0);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("status");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Device name");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Network role");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Device IEEE address");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Target device name");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Target device IEEE address");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("LQI threshold");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)7, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("Time");
			cell.setCellStyle(titleStyle);
			int lqiSize = t.size();
			for(int i = 0; i < lqiSize; i++) {
				row = sheet.createRow(i + 1);			
				cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
				if(t.get(i).get("LQI")==null){
					cell.setCellValue("异常");
				}else{
					int value = (Integer)t.get(i).get("LQI");
					if(value > 100) {
						cell.setCellValue("Normal");
					}else{
						cell.setCellValue("Abnormal");
					}
				}
				
				cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("source_name")==null?"None":t.get(i).get("source_name").toString());
				
				cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("net_enrole")==null?"No information":t.get(i).get("net_enrole").toString());
				
				cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("source_ieee")==null?"---":t.get(i).get("source_ieee").toString());
				
				cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("dest_name")==null?"None":t.get(i).get("dest_name").toString());
				
				cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("dest_ieee")==null?"---":t.get(i).get("dest_ieee").toString());
				
				cell = row.createCell(6, HSSFCell.CELL_TYPE_STRING);
				//String ll = t.get(i).get("LQI")!=null?"无":t.get(i).get("LQI").toString();
				if(t.get(i).get("LQI") != null){
					String ll = t.get(i).get("LQI").toString();
					if(ll.equals("10000")){
						cell.setCellValue("Invalid value");
					}else {
						int tt = Integer.parseInt(ll);
						if(tt > 100){
							cell.setCellValue( "good " + " " +(Integer)t.get(i).get("LQI") );
						}else{
							cell.setCellValue("difference" + " " +(Integer)t.get(i).get("LQI"));
						}
					}
				}else {
					cell.setCellValue("---");
				}
				cell = row.createCell(7, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("count_time").toString());
			}
			//设置response参数
			//对中文字符转码
			String fileName = "Network quality.xls";		
			fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//			response.addHeader("Content-Disposition", "inline;filename=" + fileName);
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
			workbook.setSheetName(0, "网络质量列表");
			HSSFRow row = sheet.createRow((short)0);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("通信状态");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("设备名称");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("网络角色");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("设备IEEE地址");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("目标设备名称");
			cell.setCellStyle(titleStyle);
			
			cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("目标设备IEEE名称");
			cell.setCellStyle(titleStyle);
			
			cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("LQI阀值");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)7, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("时间");
			cell.setCellStyle(titleStyle);
			int lqiSize = t.size();
			for(int i = 0; i < lqiSize; i++) {
				row = sheet.createRow(i + 1);			
				cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
				if(t.get(i).get("LQI")==null){
					cell.setCellValue("异常");
				}else{
					int value = (Integer)t.get(i).get("LQI");
					if(value > 100) {
						cell.setCellValue("正常");
					}else{
						cell.setCellValue("异常");
					}
				}
				
				cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("source_name")==null?"无":t.get(i).get("source_name").toString());
				
				cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("net_role")==null?"无信息":t.get(i).get("net_role").toString());
				
				cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("source_ieee")==null?"---":t.get(i).get("source_ieee").toString());
				
				cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("dest_name")==null?"无":t.get(i).get("dest_name").toString());
				
				cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("dest_ieee")==null?"---":t.get(i).get("dest_ieee").toString());
				
				cell = row.createCell(6, HSSFCell.CELL_TYPE_STRING);
				//String ll = t.get(i).get("LQI")!=null?"无":t.get(i).get("LQI").toString();
				if(t.get(i).get("LQI") != null){
					String ll = t.get(i).get("LQI").toString();
					if(ll.equals("10000")){
						cell.setCellValue("无效值");
					}else {
						int tt = Integer.parseInt(ll);
						if(tt > 100){
							cell.setCellValue( "好" + " " +(Integer)t.get(i).get("LQI") );
						}else{
							cell.setCellValue("差" + " " +(Integer)t.get(i).get("LQI"));
						}
					}
				}else {
					cell.setCellValue("---");
				}
			/*	if(t.get(i).get("LQI")!=null && t.get(i).get("LQI").toString().equals("10000")){
					cell.setCellValue("无效值");
				}else if(t.get(i).get("LQI")!=null && !t.get(i).get("LQI").toString().equals("10000")){
					int tt = Integer.parseInt(ll);
					if(tt > 100){
						cell.setCellValue( "好(" + (Integer)t.get(i).get("LQI") + ")");
					}else{
						cell.setCellValue("差(" +(Integer)t.get(i).get("LQI") + ")");
					}
					
				}else{
					cell.setCellValue("---");
				}*/
				cell = row.createCell(7, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("count_time").toString());
			}
			//设置response参数
			//对中文字符转码
			String fileName = "网络质量列表.xls";		
			fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//			response.addHeader("Content-Disposition", "inline;filename=" + fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
		logger.info("exportLqiLogExcel", e);
	} finally {
		try {
			if(os != null)
				os.close();
		} catch(Exception e) {
//			e.printStackTrace();
			logger.info("exportLqiLogExcel close BufferedOutputStream", e);
		}
	}
		return 0;
	}
	@Override
	public int exportRssiLogExcel(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
		String tableName = "rssirecord_" + param.get("houseIeee").toString() + "_" + Calendar.getInstance().get(Calendar.YEAR);
		StringBuilder sql = new StringBuilder("select a.id as id,a.source_ieee as source_ieee,e.nodeName as source_name,f.name as net_role,f.enName as net_enrole,");
		sql.append("a.dest_ieee as dest_ieee,g.nodeName as dest_name, a.RSSI as RSSI,a.count_time as count_time ");
		sql.append("from ").append(tableName).append(" a ");
		sql.append("left join node e on a.house_ieee = e.houseIEEE and a.source_ieee = e.ieee ");
		sql.append("left join node g on a.house_ieee = g.houseIEEE and a.dest_ieee = g.ieee ");
		sql.append("left join nodeType f on e.nodeType = f.id ");
		sql.append("where a.house_ieee=:houseIeee and RSSI != 10000 and RSSI IS NOT NULL  ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", param.get("houseIeee"));
		if(!param.get("houseIeee").equals(param.get("deviceIeee")) && StringUtils.isNotBlank((String)param.get("deviceIeee"))) {
			sql.append(" and a.dest_ieee =:sourceIeee ");
		    params.put("sourceIeee", param.get("deviceIeee"));
		}
		/*if(StringUtils.isNotBlank((String)param.get("deviceIeee"))) {
		    sql.append(" and a.source_ieee =:sourceIeee ");
		    params.put("sourceIeee", param.get("deviceIeee"));
		}*/
		if(StringUtils.isNotBlank((String)param.get("starttime"))) {
		    sql.append(" and a.count_time between '").append(param.get("starttime")).append("' and '").append(param.get("endtime")).append("'");   
		}
		if(param.get("houseIeee").equals(param.get("deviceIeee")) && !StringUtils.isBlank((String)param.get("searchText"))) {
			params.put("searchText", "%" + param.get("searchText") + "%");
			sql.append("and (").append(getNewVagueCondition()).append(") ");
		}
		if(!param.get("houseIeee").equals(param.get("deviceIeee")) && !StringUtils.isBlank((String)param.get("searchText"))) {
			params.put("searchText", "%" + param.get("searchText") + "%");
			sql.append("and (").append(getNewVagueCondition2()).append(") ");
		}
		//sql.append(b)
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
				workbook.setSheetName(0, "RSSI list");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				
				if(param.get("houseIeee").equals(param.get("deviceIeee"))) {
				cell.setCellValue("status");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Device name");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Network role");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Device IEEE address");
				cell.setCellStyle(titleStyle);
				/*cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("目标设备名称");
				cell.setCellStyle(titleStyle);*/
				
				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Target device name");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Target device IEEE address");
				cell.setCellStyle(titleStyle);
					
				cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("RSSI");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)7, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Time");
				cell.setCellStyle(titleStyle);
				int lqiSize = t.size();
				for(int i = 0; i < lqiSize; i++) {
					row = sheet.createRow(i + 1);
					
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					long value = (Integer)t.get(i).get("RSSI");
					logger.info("得到RSSI的value值是---》："+value);
					if(value > -70) {
						cell.setCellValue("Normal");
					}else{
						cell.setCellValue("Abnormal");
					}
					if(param.get("houseIeee").equals(param.get("deviceIeee"))) {
						cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("source_name")==null?"None":t.get(i).get("source_name").toString());
						cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("source_ieee")==null?"---":t.get(i).get("source_ieee").toString());
					}else {
						cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("dest_name")==null?"None":t.get(i).get("dest_name").toString());
						cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("dest_ieee")==null?"---":t.get(i).get("dest_ieee").toString());
					}
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("net_enrole")==null?"No information":t.get(i).get("net_enrole").toString());
						
					if(param.get("houseIeee").equals(param.get("deviceIeee"))) {
						cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("dest_name")==null?"---":t.get(i).get("dest_name").toString());
						cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("dest_ieee")==null?"---":t.get(i).get("source_ieee").toString());
					}
					cell = row.createCell(6, HSSFCell.CELL_TYPE_STRING);
					if(t.get(i).get("RSSI")!=null) {
						String ss = t.get(i).get("RSSI").toString();
						if(t.get(i).get("RSSI").toString().equals("10000")){
							cell.setCellValue("Invalid value");
						}else {
							int pp = Integer.parseInt(ss);
							if(pp > -70){
								cell.setCellValue("good" + " " + t.get(i).get("RSSI").toString());
							}else {
								cell.setCellValue("difference" + " " + t.get(i).get("RSSI").toString());
							}						
						}
					}else{
						cell.setCellValue("---");
					}
					
					cell = row.createCell(7, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("count_time").toString());
				 }
				}else {
					cell.setCellValue("status");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("Device name");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("Network role");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("Device IEEE address");
					cell.setCellStyle(titleStyle);
																	
					cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("RSSI");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("Time");
					cell.setCellStyle(titleStyle);
					int lqiSize = t.size();
					for(int i = 0; i < lqiSize; i++) {
						row = sheet.createRow(i + 1);
						
						cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
						long value = (Integer)t.get(i).get("RSSI");
						logger.info("得到RSSI的value值是---》："+value);
						if(value > -70) {
							cell.setCellValue("Normal");
						}else{
							cell.setCellValue("Abnormal");
						}
						if(param.get("houseIeee").equals(param.get("deviceIeee"))) {
							cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(t.get(i).get("source_name")==null?"None":t.get(i).get("source_name").toString());
							cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(t.get(i).get("source_ieee")==null?"---":t.get(i).get("source_ieee").toString());
						}else {
							cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(t.get(i).get("dest_name")==null?"None":t.get(i).get("dest_name").toString());
							cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(t.get(i).get("dest_ieee")==null?"---":t.get(i).get("dest_ieee").toString());
						}
						
						cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("net_enrole")==null?"No information":t.get(i).get("net_enrole").toString());
							
						cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
						if(t.get(i).get("RSSI")!=null) {
							String ss = t.get(i).get("RSSI").toString();
							if(t.get(i).get("RSSI").toString().equals("10000")){
								cell.setCellValue("Invalid value");
							}else {
								int pp = Integer.parseInt(ss);
								if(pp > -70){
									cell.setCellValue("good" + " " + t.get(i).get("RSSI").toString());
								}else {
									cell.setCellValue("difference" + " " + t.get(i).get("RSSI").toString());
								}						
							}
						}else{
							cell.setCellValue("---");
						}
						
						cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("count_time").toString());
					 }
				}
				//设置response参数
				//对中文字符转码
				String fileName = "RSSI list.xls";		
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
				workbook.setSheetName(0, "RSSI列表");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				
				if(param.get("houseIeee").equals(param.get("deviceIeee"))) {
				cell.setCellValue("通信状态");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("设备名称");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("网络角色");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("设备IEEE地址");
				cell.setCellStyle(titleStyle);
				/*cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("目标设备名称");
				cell.setCellStyle(titleStyle);*/
				
				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("目标设备名称");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("目标设备IEEE地址");
				cell.setCellStyle(titleStyle);
					
				cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("RSSI");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)7, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("时间");
				cell.setCellStyle(titleStyle);
				int lqiSize = t.size();
				for(int i = 0; i < lqiSize; i++) {
					row = sheet.createRow(i + 1);
					
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					long value = (Integer)t.get(i).get("RSSI");
					logger.info("得到RSSI的value值是---》："+value);
					if(value > -70) {
						cell.setCellValue("正常");
					}else{
						cell.setCellValue("异常");
					}
					if(param.get("houseIeee").equals(param.get("deviceIeee"))) {
						cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("source_name")==null?"无":t.get(i).get("source_name").toString());
						cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("source_ieee")==null?"---":t.get(i).get("source_ieee").toString());
					}else {
						cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("dest_name")==null?"无":t.get(i).get("dest_name").toString());
						cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("dest_ieee")==null?"---":t.get(i).get("dest_ieee").toString());
					}
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("net_role")==null?"无信息":t.get(i).get("net_role").toString());
						
					if(param.get("houseIeee").equals(param.get("deviceIeee"))) {
						cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("dest_name")==null?"---":t.get(i).get("dest_name").toString());
						cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("dest_ieee")==null?"---":t.get(i).get("source_ieee").toString());
					}
					cell = row.createCell(6, HSSFCell.CELL_TYPE_STRING);
					if(t.get(i).get("RSSI")!=null) {
						String ss = t.get(i).get("RSSI").toString();
						if(t.get(i).get("RSSI").toString().equals("10000")){
							cell.setCellValue("无效值");
						}else {
							int pp = Integer.parseInt(ss);
							if(pp > -70){
								cell.setCellValue("好" + " " + t.get(i).get("RSSI").toString());
							}else {
								cell.setCellValue("差" + " " + t.get(i).get("RSSI").toString());
							}						
						}
					}else{
						cell.setCellValue("---");
					}
					
					cell = row.createCell(7, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("count_time").toString());
				 }
				}else {
					cell.setCellValue("通信状态");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("设备名称");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("网络角色");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("设备IEEE地址");
					cell.setCellStyle(titleStyle);
																	
					cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("RSSI");
					cell.setCellStyle(titleStyle);
					cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("时间");
					cell.setCellStyle(titleStyle);
					int lqiSize = t.size();
					for(int i = 0; i < lqiSize; i++) {
						row = sheet.createRow(i + 1);
						
						cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
						long value = (Integer)t.get(i).get("RSSI");
						logger.info("得到RSSI的value值是---》："+value);
						if(value > -70) {
							cell.setCellValue("正常");
						}else{
							cell.setCellValue("异常");
						}
						if(param.get("houseIeee").equals(param.get("deviceIeee"))) {
							cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(t.get(i).get("source_name")==null?"无":t.get(i).get("source_name").toString());
							cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(t.get(i).get("source_ieee")==null?"---":t.get(i).get("source_ieee").toString());
						}else {
							cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(t.get(i).get("dest_name")==null?"无":t.get(i).get("dest_name").toString());
							cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(t.get(i).get("dest_ieee")==null?"---":t.get(i).get("dest_ieee").toString());
						}
						
						cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("net_role")==null?"无信息":t.get(i).get("net_role").toString());
							
						cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
						if(t.get(i).get("RSSI")!=null) {
							String ss = t.get(i).get("RSSI").toString();
							if(t.get(i).get("RSSI").toString().equals("10000")){
								cell.setCellValue("无效值");
							}else {
								int pp = Integer.parseInt(ss);
								if(pp > -70){
									cell.setCellValue("好" + " " + t.get(i).get("RSSI").toString());
								}else {
									cell.setCellValue("差" + " " + t.get(i).get("RSSI").toString());
								}						
							}
						}else{
							cell.setCellValue("---");
						}
						
						cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("count_time").toString());
					 }
				}
				//设置response参数
				//对中文字符转码
				String fileName = "RSSI列表.xls";		
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
	public int getNewRSSICount(String searchText, String houseIeee, String type, String time, Map map) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String tableName = "rssirecord_" + houseIeee.toLowerCase() + "_" + Calendar.getInstance().get(Calendar.YEAR);		
		StringBuilder sql = new StringBuilder("select count(*) as rssi_count from ");		
		sql.append(tableName).append(" a ");
		sql.append("left join node e on a.house_ieee = e.houseIEEE and a.source_ieee = e.ieee ");
		sql.append("left join node g on a.house_ieee = g.houseIEEE and a.dest_ieee = g.ieee ");
		sql.append("left join nodeType f on e.nodeType = f.id ");
		sql.append("where a.house_ieee=:houseIeee and RSSI != 10000 and RSSI IS NOT NULL ");
		if(type.equals("scanresult")){
			sql.append(" and a.push_time>:time ");	
			params.put("time", time);
		}
		params.put("houseIeee", houseIeee);
		if(!houseIeee.equals(map.get("deviceIeee")) && StringUtils.isNotBlank((String)map.get("deviceIeee"))) {
			sql.append(" and a.dest_ieee =:sourceIeee ");
			params.put("sourceIeee", map.get("deviceIeee"));
		}
		if(StringUtils.isNotBlank((String)map.get("starttime"))) {
			sql.append(" and a.count_time between '").append(map.get("starttime")).append("' and '").append(map.get("endtime")).append("'");   
		}
		if(houseIeee.equals(map.get("deviceIeee")) && !StringUtils.isBlank(searchText)) {
			 params.put("searchText", "%" + searchText + "%");
			 sql.append("and (").append(getNewVagueCondition()).append(") ");
		}	
		if(!houseIeee.equals(map.get("deviceIeee")) && !StringUtils.isBlank(searchText)) {
			 params.put("searchText", "%" + searchText + "%");
			 sql.append("and (").append(getNewVagueCondition()).append(") ");
		}	
			List<Map> cList = mapDao.executeSql(sql.toString(), params);
			return ((BigInteger) ((Map) cList.get(0)).get("rssi_count")).intValue();
	}
}
