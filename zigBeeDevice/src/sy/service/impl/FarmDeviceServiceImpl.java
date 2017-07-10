package sy.service.impl;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import sy.dao.BaseDaoI;
import sy.service.FarmDeviceServiceI;
import sy.util.ChartAttribute;
import sy.util.Constants;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;
import sy.util.WeatherEnvUtil;
@SuppressWarnings("rawtypes")
@Service("farmDeviceService")
public class FarmDeviceServiceImpl implements FarmDeviceServiceI {
	private static final Logger log = Logger.getLogger(FarmDeviceServiceImpl.class);
	
	

	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	/**
	 * 组装参数
	 * @param tempList
	 * @return
	 * @throws Exception
	 */
	private List<Map> composiEndPoint(List<Map> tempList) throws Exception {
		List<Map> epList = new ArrayList<Map>();
		Map<String, Object> devMap = null;
		Map<String, Object> nodeMap = null;
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		Iterator<Map> itor = tempList.iterator();
		while(itor.hasNext()) {
			Map epMap = itor.next();
			String hIeee = (String) epMap.get("house_ieee");
			String ieee = (String) epMap.get("ieee");
			String ep = (String) epMap.get("ep");
			String key = hIeee + "_" + ieee + "_" + ep;
			@SuppressWarnings("unchecked")
			Map<String, Object> devParamMap = (Map<String, Object>) cacheMap.get(key);
			if(devParamMap == null) {
				devMap = new HashMap<String, Object>();
				devParamMap = new HashMap<String, Object>();
				nodeMap = new HashMap<String, Object>();
				nodeMap.put("ieee", ieee);
				nodeMap.put("nwk_addr", epMap.get("nwk_addr"));
				nodeMap.put("name", epMap.get("node_name"));
				nodeMap.put("manufactory", epMap.get("manufactory"));
				nodeMap.put("zcl_version", epMap.get("zcl_version"));
				nodeMap.put("stack_version", epMap.get("stack_version"));
				nodeMap.put("app_version", epMap.get("app_version"));
				nodeMap.put("hw_version", epMap.get("hw_version"));
				nodeMap.put("date_code", epMap.get("date_code"));
				nodeMap.put("model_id", epMap.get("model_id"));
				nodeMap.put("node_type", epMap.get("node_type"));
				nodeMap.put("status", epMap.get("status"));
				nodeMap.put("solid_model_id", epMap.get("solid_model_id"));
				devParamMap.put("node", nodeMap);
				devParamMap.put("ep", ep);
				devParamMap.put("name", epMap.get("device_name"));
				devParamMap.put("ep_model_id", epMap.get("ep_model_id"));
				String attrName = (String) epMap.get("attributeName");
				if(StringUtils.isNotBlank(attrName)) {
					if(StringUtils.contains(attrName, " "))
						attrName = attrName.replaceAll(" ", "_");
					devParamMap.put(attrName, epMap.get("value"));
				}
				cacheMap.put(key, devParamMap);
				devMap.put("devparam", devParamMap);
				devMap.put("allcount", epMap.get("allcount"));
				devMap.put("curcount", epMap.get("curcount"));
				devMap.put("house_ieee", hIeee);
				devMap.put("device_id", epMap.get("device_id"));
				devMap.put("device_level", epMap.get("device_level"));
				epList.add(devMap);
			}
			else {
				String attrName = (String) epMap.get("attributeName");
				if(StringUtils.contains(attrName, " "))
					attrName = attrName.replaceAll(" ", "_");
				devParamMap.put((String) epMap.get("attributeName"), epMap.get("value"));
			}
		}
		return epList;
	}
	
	@Override
	public List<Map> getEndPoint(String userName, String houseIeee, int pageIndex, int pageSize) throws Exception {
		//从代理服务器上获取区域列表
		String proxyId = PropertiesUtil.getProperty("cloudAddress");
		String proxyPort = PropertiesUtil.getProperty("cloudPort");
		String isMain = PropertiesUtil.getProperty("cloudMain");
		if(Integer.parseInt(isMain) == 1) {
			return Collections.emptyList();
		}
		if(StringUtils.isNotBlank(houseIeee)) {
//			int startRow = (pageIndex - 1) * pageSize;
			String tableName = "devicenewestattribute_" + houseIeee.toLowerCase();
			StringBuilder mainSql = new StringBuilder("SELECT d.*,CASE WHEN c.isonline='1' OR c.isonline IS NULL THEN 0 ELSE 1 END status ");
			//b.value,b.attributeName,
			StringBuilder subSql = new StringBuilder("from ( ");
			subSql.append("select a.house_ieee,a.ieee,a.nwk_addr,a.node_name,a.manufactory,a.zcl_version,a.stack_version, ")
			.append("a.app_version,a.hw_version,a.date_code,a.ep,a.device_name,a.allcount,a.curcount,a.device_id,a.node_type,a.ep_model_id, ")
			.append("a.model_id,a.solid_model_id,a.device_level from farmdevice a WHERE a.house_ieee=:houseIeee ) d ");
			//判断是否已经有属性表，若没有则不返回属性值，若存在则返回最新属性
			Map<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("tableName", tableName);
			String sql = "SELECT 1 FROM information_schema.TABLES WHERE TABLE_NAME=:tableName AND TABLE_SCHEMA='zigbeedevice'";
			List<Map> mapList = mapDao.executeSql(sql,pMap);
			log.info("size:"+mapList);
			if(mapList!=null&&mapList.size()==1){
				mainSql.append(",b.value,b.attributeName ");
				subSql.append("left join ").append(tableName).append(" b on d.house_ieee=b.houseieee and d.ieee=b.device_ieee and d.ep=b.device_ep ");
			}
			subSql.append("LEFT JOIN device c ON d.house_ieee=c.houseIEEE AND d.ieee=c.ieee AND d.ep=c.ep");
			pMap.clear();
			pMap.put("houseIeee", houseIeee);
			log.info("sql:"+mainSql.toString()+subSql.toString());
			try {
				List<Map> tempList = mapDao.executeSql(mainSql.toString()+subSql.toString(), pMap);
				return composiEndPoint(tempList);
			}
			catch(Exception e) {
				throw e;
			}
		} 
		else {
			//查询设备时候的起始行
			int startRow = 0;
			//定位的总数
			int locateCountMax = pageIndex * pageSize;
			int locateCountMin = (pageIndex - 1) * pageSize;
			//缓存各个houseIEEE的设备数
			Map<String, Integer> cacheCount = new HashMap<String, Integer>();
			//保存设备总数
			int allCount = 0;
			//查询的区域列表
			List<String> qHList = new ArrayList<String>(); 
			//查找第几段区域
			aLoop:for(int i = 1; i <= pageIndex; i++) {
				String url = "http://" + proxyId + ":" + proxyPort + "/zigBeeDevice/farmArea/getareas.do";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("user_name", userName);
				params.put("callback", "1234");
				params.put("encodemethod", "NONE");
				params.put("sign", "AAA");
				params.put("pageIndex", i + "");
				params.put("pageSize", pageSize + "");
				String reslStr = TestHttpclient.postUrlWithParams(url, params, "utf-8").trim();
//				reslStr = reslStr.substring(reslStr.indexOf("(") + 1, reslStr.length() - 1);
//				log.info("--------reslStr:" + reslStr);
				Map reslMap = JSON.parseObject(reslStr, Map.class);
			
				@SuppressWarnings("unchecked")
				List<Map> areaList = (List<Map>) reslMap.get("response_params");
				Iterator<Map> aItor = areaList.iterator();
				StringBuilder cSql = new StringBuilder();
				while(aItor.hasNext()) {
					Map areaMap = aItor.next();
					String tempHIeee = (String) areaMap.get("houseIEEE");
					cSql.append("SELECT house_ieee,COUNT(*) d_count FROM farmdevice WHERE house_ieee='").append(tempHIeee).append("' UNION ALL ");
				}
				String cSqlStr = cSql.substring(0, cSql.length() - 11);
				List<Map> cList = mapDao.executeSql(cSqlStr);
				Iterator<Map> cItor = cList.iterator();
				while(cItor.hasNext()) {
					Map cMap = cItor.next();
					BigInteger dCount_ = (BigInteger) cMap.get("d_count");
					int dCount = dCount_ == null? 0 : dCount_.intValue();
					String cHouseIeee = (String) cMap.get("house_ieee");
					cacheCount.put(cHouseIeee, dCount);
					allCount += dCount;
					//大于起始行开始算
					if(locateCountMin < allCount) {
						qHList.add(cHouseIeee);
						//大于结束行结束
						if(locateCountMax < allCount) {
							if(qHList.size() == 1) { //在同一houseIeee
								startRow = locateCountMin;
							}
							else {
								int firstHCount = cacheCount.get(qHList.get(0));
								int pageCount = firstHCount / pageSize;
								startRow = pageCount * pageSize;
							}
							break aLoop;
						}
					}
				}
			}
			
			//查询的结束行
			int endCount = 0;
			//已查询的行数
			int aldyCount = 0;
			Iterator<String> itor = qHList.iterator();
			List<Map> allAttrList = new ArrayList<Map>();
			Map<String, Object> pMap = new HashMap<String, Object>();
			while(itor.hasNext()) {
				String tempHIeee = itor.next();
				int dCount = cacheCount.get(tempHIeee);
				if(aldyCount < pageSize) {
					if((dCount - startRow) >= pageSize) {
						endCount = pageSize;
					}
					else {
						endCount = dCount - startRow;
					}
					aldyCount += endCount;
					if(aldyCount > pageSize) {
						endCount = pageSize - (aldyCount - endCount); 
					}
				}
				else {
					break;
				}
				String tableName = "devicenewestattribute_" + tempHIeee.toLowerCase();
				StringBuilder mainSql = new StringBuilder("SELECT d.*,CASE WHEN c.isonline='1' OR c.isonline IS NULL THEN 0 ELSE 1 END status ");
				//b.value,b.attributeName,
				StringBuilder subSql = new StringBuilder("from ( ");
				subSql.append("select a.house_ieee,a.ieee,a.nwk_addr,a.node_name,a.manufactory,a.zcl_version,a.stack_version, ")
				.append("a.app_version,a.hw_version,a.date_code,a.ep,a.device_name,a.allcount,a.curcount,a.device_id,a.node_type,a.ep_model_id, ")
				.append("a.model_id,a.solid_model_id,a.device_level from farmdevice a WHERE a.house_ieee=:houseIeee limit :startRow,:pageSize) d ");
				pMap.clear();
				//判断是否已经有属性表，若没有则不返回属性值，若存在则返回最新属性
				pMap.put("tableName", tableName);
				String sql = "SELECT 1 FROM information_schema.TABLES WHERE TABLE_NAME=:tableName AND TABLE_SCHEMA='zigbeedevice'";
				List<Map> mapList = mapDao.executeSql(sql,pMap);
				log.info("size:"+mapList);
				if(mapList!=null&&mapList.size()==1){
					mainSql.append(",b.value,b.attributeName ");
					subSql.append("left join ").append(tableName).append(" b on d.house_ieee=b.houseieee and d.ieee=b.device_ieee and d.ep=b.device_ep ");
				}
				subSql.append("LEFT JOIN device c ON d.house_ieee=c.houseIEEE AND d.ieee=c.ieee AND d.ep=c.ep");
				pMap.clear();
				pMap.put("houseIeee", tempHIeee);
				pMap.put("startRow", startRow);
				pMap.put("pageSize", endCount);
				log.info("sql:"+mainSql.toString()+subSql.toString());
				try {
					allAttrList.addAll(mapDao.executeSql(mainSql.toString()+subSql.toString(), pMap));
				}
				catch(Exception e) {
					throw e;
				}
				if(endCount == pageSize) {
					break;
				}
				else {
					startRow = 0;
				}
			}
			return composiEndPoint(allAttrList);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getChartData(Integer chartType, String startTime, String endTime, String houseIeee, int unit) throws Exception {
		if(chartType == null) {
			return Collections.emptyList();
		}
		if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
			return Collections.emptyList();
		}
		//判断起始及结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(startTime);
		Date endDate = sdf.parse(endTime);
		if(startDate.getTime() > endDate.getTime()) {
			return Collections.emptyList();
		}
		//判断属性是否为空
		Map<String, Object> attrMap = ChartAttribute.getValue(chartType);
		if(attrMap == null || attrMap.get("clusterId") == null || attrMap.get("attributeId") == null) {
			return Collections.emptyList();
		}
		String tableName = "deviceattributehistory_" + houseIeee.toLowerCase() + "_" + startTime.substring(0, startTime.indexOf("-"));
		StringBuilder qAttrSql = null;
		//按小时统计，返回实时数据
		if(unit == 0) {
			qAttrSql = new StringBuilder();
			qAttrSql.append("SELECT REPLACE(FORMAT(a.value,2),',','')+0 value,a.opdatetime,a.device_ieee,a.device_ep,b.device_name FROM ");
		}
		else {//按天数统计，去平均值
			qAttrSql = new StringBuilder("SELECT REPLACE(FORMAT(SUM(a.value)/COUNT(*),2),',','')+0 value,");
			qAttrSql.append("DATE_FORMAT(a.opdatetime, '%m-%d') opdatetime,a.device_ieee,a.device_ep,b.device_name FROM ");
		}
		qAttrSql.append(tableName).append(" a INNER JOIN farmdevice b ON a.houseIEEE=b.house_ieee AND a.device_ieee=b.ieee AND a.device_ep=b.ep ")
		.append("WHERE a.cluster_id=:clusterId AND a.attribute_id=:attributeId AND (a.opdatetime BETWEEN :startTime AND :endTime) ");
		String qAttrSqlStr = qAttrSql.toString();
		/*String[][] solidModelIdArr = (String[][]) attrMap.get("solidModelIdArr");
		if(solidModelIdArr != null && solidModelIdArr.length != 0) {
			qAttrSql.append("and (");
			Iterator<String[]> itor = Arrays.asList(solidModelIdArr).iterator();
			while(itor.hasNext()) {
				String[] dSmia = itor.next();
				String solidModelId = dSmia[0];
				String ep = dSmia[1];
				if(solidModelId == null) {
					qAttrSql.append("(b.solid_model_id is null OR b.solid_model_id='') OR ");
				}
				else {
					qAttrSql.append("(b.solid_model_id='").append(solidModelId).append("' AND b.ep='").append(ep).append("') OR ");
				}
			}
			qAttrSqlStr = qAttrSql.substring(0, qAttrSql.length() - 4) + ")";
		}*/
		//按小时统计
		if(unit == 0) {
//			qAttrSqlStr += " GROUP BY DATE_FORMAT(a.opdatetime, '%Y-%m-%d %H'),a.device_ieee,a.device_ep";
		}
		else {//按天数统计
			qAttrSqlStr += " GROUP BY DATE_FORMAT(a.opdatetime, '%Y-%m-%d'),a.device_ieee,a.device_ep";
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clusterId", attrMap.get("clusterId"));
		paramMap.put("attributeId", attrMap.get("attributeId"));
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<Map> attrList = Collections.emptyList();
		try {
			attrList = mapDao.executeSql(qAttrSqlStr, paramMap);
		}
		catch(Exception e) {
			log.error("get chart from db exception", e);
			if(e.getMessage().indexOf(tableName) == -1) {
				throw e;
			}
		}
		Map<String, Map<String, Object>> reslMap = new HashMap<String, Map<String, Object>>();
		Iterator<Map> itor = attrList.iterator();
		while(itor.hasNext()) {
			Map attrTMap = itor.next();
			String ieee = (String) attrTMap.get("device_ieee");
			Map<String, Object> resl2Map = reslMap.get(ieee);
			if(resl2Map == null) {
				resl2Map = new HashMap<String, Object>();
				resl2Map.put("ieee", ieee);
				resl2Map.put("ep", attrTMap.get("device_ep"));
				resl2Map.put("name", attrTMap.get("device_name"));
				List<Map> items = new ArrayList<Map>();
				Map<String, Object> valMap = new HashMap<String, Object>();
				Object opdTime = attrTMap.get("opdatetime");
				if(unit == 0) {
//					valMap.put("time", opdTime == null? "0" : Integer.parseInt(opdTime) + "");
					valMap.put("time", opdTime);
				}
				else {
					valMap.put("time", opdTime == null? "0" : opdTime);
				}
				valMap.put("value", attrTMap.get("value"));
				items.add(valMap);
				resl2Map.put("items", items);
				reslMap.put(ieee, resl2Map);
			}
			else {
				List<Map> items = (List<Map>) resl2Map.get("items");
				Map<String, Object> valMap = new HashMap<String, Object>();
				valMap.put("time", attrTMap.get("opdatetime"));
				valMap.put("value", attrTMap.get("value"));
				items.add(valMap);
			}
		}
		List<Map<String, Object>> valList = new ArrayList<Map<String, Object>>();
		//unit不需要计算时间，即时返回
		if(unit == 0) {
			valList.addAll(reslMap.values());
			return valList;
		}
		//计算是否所有时间都返回
		Calendar stCanld = Calendar.getInstance();
		Calendar edCanld = Calendar.getInstance();
		stCanld.setTime(startDate);
		edCanld.setTime(endDate);
		int startH = 0;
		int endH = 0;
		int startM = 0;
		int endM = 0;
		int startY = 0;
		int endY = 0;
		if(unit == 0) {
			startH = stCanld.get(Calendar.HOUR_OF_DAY);
			endH = edCanld.get(Calendar.HOUR_OF_DAY);
		}
		else {
			startH = stCanld.get(Calendar.DAY_OF_MONTH);
			startM = stCanld.get(Calendar.MONTH);
			startY = stCanld.get(Calendar.YEAR);
			endH = edCanld.get(Calendar.DAY_OF_MONTH);
			endM = edCanld.get(Calendar.MONTH);
			endY = edCanld.get(Calendar.YEAR);
		}
		Iterator<String> iitor = reslMap.keySet().iterator();
		while(iitor.hasNext()) {
			String ieee = iitor.next();
			Map iMap = reslMap.get(ieee);
			List<Map> items = (List<Map>) iMap.get("items");
			List<Map> rItems = new ArrayList<Map>();
			//计算小时
			if(unit == 0) {
				rItems = addNotExistVal(startH, endH, items, unit, 0);
			}
			else {//计算天数
				stCanld = Calendar.getInstance();
				stCanld.setTime(startDate);
				int tmpStartH = startH;
				int tmpStartM = startM;
				int tmpStartY = startY;
				String startMStr = String.valueOf(tmpStartM);
				int startMY = Integer.parseInt(tmpStartY + "" + (startMStr.length() == 1? ("0" + tmpStartM) : startMStr));
				String endMStr = String.valueOf(endM);
				int endMY = Integer.parseInt(endY + "" + (endMStr.length() == 1? ("0" + endM) : endMStr));
				while(startMY <= endMY) {
					int maxDay = 0;
					if(startMY == endMY || tmpStartM == endM) {
						maxDay = endH;
					}
					else {
						maxDay = stCanld.getActualMaximum(Calendar.DAY_OF_MONTH);
					}
					rItems.addAll(addNotExistVal(tmpStartH, maxDay, items, unit, (tmpStartM + 1)));
					if(endM == 11) {
						break;
					}
					//重新初始化
					stCanld.add(Calendar.MONTH, 1);
					tmpStartH = 1;
					tmpStartM = stCanld.get(Calendar.MONTH);
					tmpStartY = stCanld.get(Calendar.YEAR);
					startMStr = String.valueOf(tmpStartM);
					startMY = Integer.parseInt(tmpStartY + "" + (startMStr.length() == 1? ("0" + tmpStartM) : startMStr));
				}
			}
			iMap.put("items", rItems);
		}
		valList.addAll(reslMap.values());
		return valList;
	}

	/**
	 * 判断时间是否存在，不存在则补0
	 * @param startH
	 * @param endH
	 * @param items
	 * @param unit
	 * @param currM
	 */
	private List<Map> addNotExistVal(int startH, int endH, List<Map> items, int unit, int currM) {
		List<Map> rItems = new ArrayList<Map>();
		int itemLen = items.size();
		for(int i = startH; i <= endH; i++) {
			boolean exist = false;
			for(int j = 0; j < itemLen; j++) {
				Map item = items.get(j);
				String opdatetime = (String) item.get("time");
				String[] mdArr = null;
				if(StringUtils.isNotBlank(opdatetime)) {
					int opdateH = 0;
					if(unit == 0) {
						opdateH = Integer.parseInt(opdatetime);
						mdArr = new String[]{"0"};
					}
					else {
						mdArr = opdatetime.split("-");
						opdateH = Integer.parseInt(mdArr[1]);
					}
					if(Integer.parseInt(mdArr[0]) == currM && i == opdateH) {
						exist = true;
						rItems.add(item);
						break;
					}
				}
			}
			if(!exist) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("value", 0);
				if(unit == 0) {
					item.put("time", i + "");
				}
				else {
					String iStr = String.valueOf(i).length() == 1? ("0" + i) : String.valueOf(i);
					String currMStr = String.valueOf(currM).length() == 1? ("0" + currM) : String.valueOf(currM);
					item.put("time", currMStr + "-" + iStr);
				}
				rItems.add(item);
			}
		}
		return rItems;
	}
	
	@Override
	public void pushDeviceInfo(String houseIeee, Map params) throws Exception {
		if(params == null || params.isEmpty()) {
			throw new Exception("json can't be empty");
		}
		@SuppressWarnings("unchecked")
		List<Map> resParams = (List<Map>) params.get("response_params");
		if(resParams == null || resParams.isEmpty()) {
			throw new Exception("response_params can't be empty");
		}
		//先删除该houseIEEE下的所有设备
		String clearSql = "delete from farmdevice where house_ieee=:houseIeee";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("houseIeee", houseIeee);
		mapDao.executeSql2(clearSql, paramMap);
		//插入新设备
		Iterator<Map> itor = resParams.iterator();
		StringBuilder resSql = new StringBuilder("replace into farmdevice(allcount,curcount,house_ieee,device_id,rid,picname,profileid,powersource,");
		resSql.append("curpowersource,curpowersourcelevel,ieee,nwk_addr,node_name,manufactory,zcl_version,stack_version,app_version,hw_version,date_code,model_id,")
		.append("solid_model_id,node_type,status,ep,device_name,ep_model_id,device_level) values");
		StringBuilder paramSql = new StringBuilder();
		while(itor.hasNext()) {
			Map pMap = itor.next();
			Map devParamMap = (Map) pMap.get("devparam");
			Map nodeMap = null;
			if(devParamMap == null) {
				devParamMap = new HashMap();
				nodeMap = new HashMap();
			}
			else {
				nodeMap = (Map) devParamMap.get("node");
				if(nodeMap == null) {
					nodeMap = new HashMap();
				}
			}
			paramSql.append("(").append(pMap.get("allcount")).append(",").append(pMap.get("curcount")).append(",'").append(pMap.get("house_ieee"))
			.append("',").append(pMap.get("device_id")).append(",").append(pMap.get("rid")).append(",'").append(pMap.get("picname")).append("','")
			.append(pMap.get("profileid")).append("','").append(pMap.get("powersource")).append("',").append(pMap.get("curpowersource")).append(",")
			.append(pMap.get("curpowersourcelevel")).append(",'").append(nodeMap.get("ieee")).append("','").append(nodeMap.get("nwk_addr")).append("','")
			.append(nodeMap.get("name")).append("','").append(nodeMap.get("manufactory")).append("','").append(nodeMap.get("zcl_version")).append("','")
			.append(nodeMap.get("stack_version")).append("','").append(nodeMap.get("app_version")).append("','").append(nodeMap.get("hw_version"))
			.append("','").append(nodeMap.get("date_code")).append("','").append(nodeMap.get("model_id")).append("','").append(nodeMap.get("solid_model_id"))
			.append("',").append(nodeMap.get("node_type")).append(",").append(nodeMap.get("status")).append(",'").append(devParamMap.get("ep")).append("','")
			.append(devParamMap.get("name")).append("','").append(devParamMap.get("ep_model_id")).append("',").append(pMap.get("device_level")).append("),");
		}
		String resSqlStr = resSql.append(paramSql.deleteCharAt(paramSql.length() - 1)).toString();
//		log.info("----------resSqlStr:" + resSqlStr);
		mapDao.executeSql2(resSqlStr);
	}

	@Override
	public List<Map<String,Object>> getPm25ChartData(String houseIeee, String deviceIeee, String ip, String platformType) throws Exception {
	
		//判断属性是否为空
		Map<String, Object> attrMap = ChartAttribute.getValue(Constants.CHART_PM25);
		if(attrMap == null || attrMap.get("clusterId") == null || attrMap.get("attributeId") == null) {
			return new LinkedList<>();
		}
		
		//开始时间
		Date endTime = new Date();
		endTime = DateUtils.setMinutes(endTime, 0);
		endTime = DateUtils.setSeconds(endTime, 0);
		
//		String startTime = "2017-1-7 10:20:30";
//		String endTime = "2017-3-27 10:20:30";
		
		//24小时之前的时间
		Date startTime = DateUtils.addDays(endTime, -1);

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String tableName = "deviceattributehistory_" + houseIeee.toLowerCase() + "_" + sdf.format(startTime);
		
		String deviceTableName = null;
		String houseIeeeFieldName = null;
		String solidModelIdFieldName = null;
		
		if ("ies".equals(platformType)) {
			 deviceTableName = "farmdevice";
			 houseIeeeFieldName = "b.house_ieee";
			 solidModelIdFieldName = "b.solid_model_id";
			
		} else if ("yun".equals(platformType)){
			deviceTableName = "device";
			houseIeeeFieldName = "b.houseIEEE";
			solidModelIdFieldName = "b.SolidModelID";
		}
		
		//按小时统计，返回实时数据
		StringBuilder qAttrSql = new StringBuilder();
		qAttrSql.append("SELECT ROUND(avg(m.aqi),0)as aqi, m.label as label FROM ( ");
		qAttrSql.append("SELECT ROUND(AVG(a.value),0) as aqi, DATE_FORMAT(a.opdatetime,'%Y-%m-%d %H:00:00') as label FROM ");
		qAttrSql.append(tableName)
		.append(" a INNER JOIN ")
		.append(deviceTableName)
		.append(" b ON a.houseIEEE=")
		.append(houseIeeeFieldName)
		.append(" AND a.device_ieee=b.ieee AND a.device_ep=b.ep ")
		.append("WHERE a.cluster_id=:clusterId AND a.attribute_id=:attributeId AND b.ieee=:deviceIeee AND (a.opdatetime BETWEEN :startTime AND :endTime) ");
		String qAttrSqlStr = qAttrSql.toString();
		String[][] solidModelIdArr = (String[][]) attrMap.get("solidModelIdArr");
		if(solidModelIdArr != null && solidModelIdArr.length != 0) {
			qAttrSql.append("and (");
			Iterator<String[]> itor = Arrays.asList(solidModelIdArr).iterator();
			while(itor.hasNext()) {
				String[] dSmia = itor.next();
				String solidModelId = dSmia[0];
				String ep = dSmia[1];
				if(solidModelId == null) {
					qAttrSql.append("(" + solidModelIdFieldName +" is null OR b.solid_model_id='') OR ");
				}
				else {
					qAttrSql.append("(" + solidModelIdFieldName +"='").append(solidModelId).append("' AND b.ep='").append(ep).append("') OR ");
				}
			}
			qAttrSqlStr = qAttrSql.substring(0, qAttrSql.length() - 4) + ")";
		}
		
		//按小时统计
        qAttrSqlStr += " GROUP BY DATE_FORMAT(a.opdatetime, '%Y-%m-%d %H'),a.device_ieee,a.device_ep";
        qAttrSqlStr += " ) as m GROUP BY label ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clusterId", attrMap.get("clusterId"));
		paramMap.put("attributeId", attrMap.get("attributeId"));
		paramMap.put("deviceIeee", deviceIeee);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<Map> inRoomList = null;
		try {
			inRoomList = mapDao.executeSql(qAttrSqlStr, paramMap);
		}
		catch(Exception e) {
			log.error("get chart from db exception", e);
			if(e.getMessage().indexOf(tableName) == -1) {
				throw e;
			}
		}
		
		//获取室外前24小时PM2.5图表数据
		List<Map> outroomList = WeatherEnvUtil.getPm25ChartData(ip);
		
		List<Map<String,Object>> resultList = new LinkedList<>();
		//室内
		Map<String, Object> inroomItemMap = new HashMap<>();
		inroomItemMap.put("type","inroom");
		inroomItemMap.put("value", inRoomList);
		
		//室外
		Map<String, Object> outroomItemMap = new HashMap<>();
		outroomItemMap.put("type","outroom");
		outroomItemMap.put("value", outroomList);
		
		resultList.add(inroomItemMap);
		resultList.add(outroomItemMap);
		
		return resultList;
	}

	
}
