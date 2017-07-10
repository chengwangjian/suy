package sy.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.service.BrinServiceI;
import sy.util.HouseieeeListener;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

import com.alibaba.fastjson.JSON;

@Service("brinService")
public class BrinServiceImpl implements BrinServiceI {
	private BaseDaoI<Map> mapDao;
	private static final Logger logger = Logger.getLogger(HouseServiceImpl.class);
	private List<String> cacheList = new ArrayList(100);
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	public static Logger getLogger() {
		return logger;
	}
	@Override
	public int add(Map<String, Object> paramMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", paramMap.get("userid"));
		params.put("houseIEEE", paramMap.get("houseIEEE"));
		String sqlString="select 1 from brinhouse a where a.houseIEEE=:houseIEEE and a.userid=:userid limit 1";
		List<Map> t1 = mapDao.executeSql(sqlString,params);
		params.put("limittime", paramMap.get("limittime"));
		if(t1.isEmpty()){
			String sql = "insert into brinhouse(userid,houseIEEE,rFlag,netFlag,limittime) values(:userid,:houseIEEE,0,0,:limittime) ";
			int t = mapDao.executeSql2(sql,params);
			return t;
		}
//			else {
//			Map<String, Object> aMap=t1.get(0);
//			if(t1.get(0).get("rFlag").toString().equals("2")){
//				String sqlString2 = "insert into brinhousehistory(userid,houseIEEE,rFlag,deviceAllFlag,netFlag) values(:userid,:houseIEEE,:rFlag,:deviceAllFlag,:netFlag)";
//				int t = mapDao.executeSql2(sqlString2, aMap);
//				String sqlString3 = "delete from brinhouse where houseIEEE=:houseIEEE and userid=:userid and rFlag = '2'";
//				mapDao.executeSql2(sqlString3,params);
//			}
			return 2;
//		}
	}
	@Override
	public List<Map> findbrin(Map<String, Object> param, int startRow,
			int pageSize_, String orderBy) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
//		Select t.* ,case when t.aCount=0 then 1 else 0 end as deviceAllFlag from (
//				select a.initResult,a.endtime,a.rFlag,a.netFlag,a.houseIEEE,a.starttime,b.isonline,
//				a.deviceAllFlag,b.clientCode,b.IPK_version,a.limittime,
//				Sum(case when d.isonline=0 then 1 else 0 end) as aCount ,Count(d.houseIEEE) as dCount  
//				from brinhouse a left join house b on a.houseIEEE=b.houseIEEE 
//				left join device d on d.houseIEEE=b.houseIEEE
//				 where userid='24' and rFlag <> 2 
//						 group by a.houseIEEE 
//				ORDER BY case when a.rFlag=3 then 0 else 1 end asc,starttime desc ) t
		String searchText = (String) param.get("searchText");
		String searchSql="";
		if(StringUtils.isNotBlank(searchText)){
			params.put("searchText", "%"+searchText+"%");
			searchSql=" and (a.houseIEEE like :searchText or b.clientCode like :searchText or b.IPK_version like :searchText)";
		}
		String sql = "Select t.* ,case when t.aCount=0 then 1 else 0 end as deviceAllFlag from ("
				+" select a.initResult,a.endtime,a.rFlag,a.netFlag,a.houseIEEE,a.starttime,b.isonline,"
				+" b.clientCode,b.IPK_version,a.limittime,"
				+" Sum(case when d.isonline=0 then 1 else 0 end) as aCount ,Count(d.houseIEEE) as dCount"  
				+" from brinhouse a left join house b on a.houseIEEE=b.houseIEEE" 
				+" left join device d on d.houseIEEE=b.houseIEEE where userid=:userid"+searchSql
				+" group by a.houseIEEE " 
				+" ORDER BY case when a.rFlag=3 then 0 else 1 end asc,starttime desc ) t";
//		String sql = "select a.initResult,a.endtime,a.rFlag,a.netFlag,a.houseIEEE,a.starttime,b.isonline,a.deviceAllFlag,b.clientCode,b.IPK_version,a.limittime  from brinhouse a left join house b on a.houseIEEE=b.houseIEEE where userid=:userid and rFlag <> 2 ";
		
		params.put("userid", param.get("userid"));
//		if(StringUtils.isNotBlank(searchText)){
//			params.put("searchText", "%"+searchText+"%");
//			sql+=" and (a.houseIEEE like :searchText or b.clientCode like :searchText or b.IPK_version like :searchText)";
//		}
//		sql+=" ORDER BY case when a.rFlag=3 then 0 else 1 end asc,starttime desc ";
//		sql+=" ORDER BY a.rFlag DESC";
		sql+=" limit ";
		sql+=startRow;
		sql+=",";
		sql+=pageSize_;
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		return t;
	}
	
	public List<Map> getdeviceCount1(List<Map> list) {
		if (list != null && list.size() > 0) {
//			StringBuilder sql =new StringBuilder("Select houseieee,Sum(case when isonline=0 then 1 else 0 end) as aCount,Count(*) as dCount from device where houseIeee in (");
			StringBuilder sql =new StringBuilder("SELECT d.houseieee,h.isonline,Sum(CASE WHEN d.isonline = 0 THEN 1 ELSE 0 END) AS aCount,Count(*) AS dCount FROM device d LEFT JOIN house h ON d.houseIeee = h.houseIeee WHERE d.houseIeee IN (");
			for(int i = 0 ; i<list.size();i++){
				sql.append("'").append(list.get(i).get("houseIEEE")).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1);
			sql.append(")");
			sql.append(" GROUP BY d.houseIeee");
//			StringBuilder sql =new StringBuilder("select count(*) as dCount,houseIEEE,");
//			sql.append("case when (select count(*) from device a where a.isonline=0 and a.houseIEEE=b.houseIEEE)=0 then 1 ");
//			sql.append("else 0 end deviceAllFlag from device b where b.houseIeee = :houseIEEE ");
//    		sql.append(" group by b.houseIEEE");
    		List<Map> tList =mapDao.executeSql(sql.toString());
    		return tList;
    	}
		return null;
	}
	
	@Override
	public int opennet(Map<String, Object> param) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "update brinhouse set netFlag = 1 where userid=:userid and houseIEEE =:houseIEEE";
		params.put("houseIEEE", param.get("houseIEEE"));
		params.put("userid", param.get("userid"));
		int i = mapDao.executeSql2(sql, param);
		if(i!=0){
			return i;
		}else{
			return i;
		}
		
	}
		
	@Override
	public int finish(Map<String, Object> paramMap) {
		StringBuilder sql = new StringBuilder("update brinhouse set endtime=now(),rFlag=:rFlag where userid = :userid and houseIEEE in (");
		List<String> hList = (List<String>) paramMap.get("hList");
		if(!hList.isEmpty()) {
			for (String houseIeee:hList) { 
				sql.append( "'"+ houseIeee + "',");
	    	}
			sql = sql.deleteCharAt(sql.length()-1);
			sql.append(")");
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			paramMap2.put("userid", paramMap.get("userid"));
			paramMap2.put("rFlag", paramMap.get("rFlag"));
			int i =mapDao.executeSql2(sql.toString(), paramMap2);
			if(((Integer)(paramMap.get("rFlag"))).intValue()==2){
				StringBuilder sqlString1 = new StringBuilder("insert into brinhousehistory(userid,starttime,endtime,rFlag,houseIEEE,netFlag,limittime,initResult,wrongResult) select userid,starttime,endtime,rFlag,houseIEEE,netFlag,limittime,initResult,wrongResult from brinhouse where houseIEEE = :houseIEEE and userid =:userid");
				StringBuilder sqlString3 = new StringBuilder("delete from brinhouse where userid=:userid and houseIEEE =:houseIEEE");
				Map<String, Object> paramMap4 = new HashMap<String, Object>();
				paramMap4.put("userid", paramMap.get("userid"));
				paramMap4.put("houseIEEE", hList.get(0));
				mapDao.executeSql2(sqlString1.toString(),paramMap4);
				mapDao.executeSql2(sqlString3.toString(),paramMap4);
			}
			return i;
		}
		return -1;
	}
	
	@Override
	public int finishzhuce(Map<String, Object> paramMap) {
		StringBuilder sql = new StringBuilder("update brinhouse set endtime=now(),rFlag=:rFlag where userid = :userid and houseIEEE in (");
		List<String> hList = (List<String>) paramMap.get("hList");
		if(!hList.isEmpty()) {
			for (String houseIeee:hList) { 
				sql.append( "'"+ houseIeee + "',");
	    	}
			sql = sql.deleteCharAt(sql.length()-1);
			sql.append(")");
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			paramMap2.put("userid", paramMap.get("userid"));
			paramMap2.put("rFlag", paramMap.get("rFlag"));
			int i =mapDao.executeSql2(sql.toString(), paramMap2);
			if(((Integer)(paramMap.get("rFlag"))).intValue()==2){
				StringBuilder sqlString1 = new StringBuilder("insert into brinhousehistory(userid,starttime,endtime,rFlag,houseIEEE,netFlag,limittime,initResult,wrongResult) select userid,starttime,endtime,rFlag,houseIEEE,netFlag,limittime,initResult,wrongResult from brinhouse where houseIEEE = :houseIEEE ");
				StringBuilder sqlString3 = new StringBuilder("delete from brinhouse where  houseIEEE =:houseIEEE");
				Map<String, Object> paramMap4 = new HashMap<String, Object>();
				paramMap4.put("houseIEEE", hList.get(0));
				mapDao.executeSql2(sqlString1.toString(),paramMap4);
				mapDao.executeSql2(sqlString3.toString(),paramMap4);
			}
			return i;
		}
		return -1;
	}
	@Override
	public int closenet(Map<String, Object> param) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "update brinhouse set netFlag = 0 where houseIEEE =:houseIEEE and userid=:userid";
		params.put("houseIEEE", param.get("houseIEEE"));
		params.put("userid", param.get("userid"));
		int i = mapDao.executeSql2(sql, param);
		if(i!=0){
			return i;
		}else{
			return i;
		}
		
	}

	@Override
	public int restart(Map<String, Object> paramMap) {
		String sql2 = "select intFlag from brinhouse where houseIEEE=:houseIEEE and userid=:userid ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIEEE", paramMap.get("houseIEEE"));
		params.put("userid", paramMap.get("userid"));
		List<Map> list = mapDao.executeSql(sql2,params);
		if(list.isEmpty()) {
			return -1;
		} 
		else {
			Map map = list.get(0);
			if(map == null)
				return -1;
			else {
				Object intFlag1 = map.get("intFlag");
				if(intFlag1 == null)
					return -1;
				int intFlag = ((Byte) intFlag1).intValue();
				if (intFlag <= -1) {
					return intFlag;
				}
			}
		}
		String sql = "update brinhouse set rFlag=:rFlag,starttime=now() where userid=:userid and houseIEEE=:houseIEEE ";
		int i =mapDao.executeSql2(sql,paramMap);
		return i;
		
	}
	@Override
	public int updateInit(Map<String, Object> paramMap) {
		String sql = "update brinhouse set rFlag=:rFlag,starttime=now() where userid=:userId and houseIEEE=:houseIEEE ";
		int i =mapDao.executeSql2(sql,paramMap);
		return i;
	}
	
	/**
	 * 忽略
	 */
	@Override
	public int updateComplete(Map<String, Object> paramMap) {
		Map<String, Object> paMap = new HashMap<String , Object>();
		String sql = "update brinhouse set intFlag = 1 where userid=:userId and houseIEEE=:houseIEEE ";
		paMap.put("userId", paramMap.get("userid"));
		paMap.put("houseIEEE", paramMap.get("houseIEEE"));
		if(paramMap.get("logo").equals("0")){//点击初始化忽略执行此处,1表示完整，0表示不完整
			int i =mapDao.executeSql2(sql,paMap);
//			int a = backup(paramMap);//执行数据备份
//			if(a == 1){
				paMap.put("rFlag", 1);
				updateInit(paMap);//初始化点击忽略开始burn in
//			}
			
			return i;
		}else {
			int i =mapDao.executeSql2(sql,paMap);//点击完整性忽略执行此处，只需修改完整性状态为完整
			return i;
		}
	}
	/**
	 * 确定
	 * (non-Javadoc)
	 * @see sy.service.BrinServiceI#updateSure(java.util.Map)
	 */
	@Override
	public int updateSure(Map<String, Object> paramMap) {
		Map<String, Object> paMap = new HashMap<String , Object>();
		String sql = "select intFlag from brinhouse where userid=:userId and houseIEEE=:houseIEEE";
		List<Map> tList = null;
		if(paramMap.get("logo").equals("0")){//初始化点击确定
			paMap.put("userId", paramMap.get("userid"));
			paMap.put("houseIEEE", paramMap.get("houseIEEE"));
			tList =mapDao.executeSql(sql.toString(),paMap);
			int intFlag = ((Byte) (tList.get(0).get("intFlag"))).intValue();
			if(tList != null && intFlag ==1){
//				int a = backup(paramMap);//执行203备份数据，返回0 失败，1 成功，2连接超时
//				if(a == 1){
					paMap.put("rFlag", 1);
					updateInit(paMap);//初始化点击确定并且完整性检查通过，开始burn in
//				}
			}
		}
		return 1;
	}
	
	/**
	 * 203执行备份数据
	 * @param paramMap
	 * @return
	 */
	public int backup(Map<String, Object> paramMap) {
		int i=2;//程序出错
		String serverIp = PropertiesUtil.getProperty("xmpp.host");
		String serverPort = PropertiesUtil.getProperty("xmpp.port");
		String userz203 = PropertiesUtil.getProperty("user.z203");
    	StringBuilder upUrl = new StringBuilder("http://");
    	upUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=backUpdataForMntJffS2Xml/")
    	.append(userz203)
    	.append("&").append("username=").append(paramMap.get("houseIEEE"));
    	try {
			String str = TestHttpclient.getUrl(upUrl.toString(),"utf-8");
			Map<String, Object> result = JSON.parseObject(str, Map.class);
			int status = (Integer) ((Map) result.get("message")).get("status");
        	if(status==0) {
        		i=1;//执行成功
        	}else{
        		i=0;//执行失败
        	}
		} catch (Exception e) {
			logger.info("backup", e);
//			e.printStackTrace();
		}
		return i;
	}
	
//	@Override
//	public int updateintFlag(Map<String, Object> paramMap) {
//		String sql = "update brinhouse set intFlag=:intFlag,starttime=now() where userid=:userId and houseIEEE=:houseIEEE ";
//		int i =mapDao.executeSql2(sql,paramMap);
//		return i;
//	}
//	
	@Override
	public Map getCount(Map<String, Object> param) {
		Map<String, Object> params = new HashMap<String, Object>();
//		String sql = "select b.isonline,b.deviceAllFlag  from brinhouse a left join house b on a.houseIEEE=b.houseIEEE where userid=:userid ";
		String sql = "select count(*) burnCount from brinhouse a LEFT JOIN house b on a.houseIEEE=b.houseIEEE where a.userid=:userid ";
		String searchText = (String) param.get("searchText");
		params.put("userid", param.get("userid"));
		if(StringUtils.isNotBlank(searchText)){
			params.put("searchText", "%"+searchText+"%");
			sql+="and (a.houseIEEE like :searchText or b.clientCode like :searchText or b.IPK_version like :searchText)";
		}
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		return t.get(0);
	}
//	@Override
//	public void updateDeviceAllFlag(String offHouseIEEEs, String onHouseIEEEs) throws Exception{
//		Map<String, Object> params = new HashMap<String, Object>();
//		if(StringUtils.isNotBlank(onHouseIEEEs)) {
//			String onStr = onHouseIEEEs.substring(0, onHouseIEEEs.length() - 1);
//			String sql ="Update brinhouse set deviceAllFlag = 1 where houseIeee in (" + onHouseIEEEs + ")"; 
//			mapDao.executeSql2(sql);
//		}
//		if(StringUtils.isNotBlank(offHouseIEEEs)) {
//			String onStr = onHouseIEEEs.substring(0, offHouseIEEEs.length() - 1);
//			String sql ="Update brinhouse set deviceAllFlag = 0 where houseIeee in (" + offHouseIEEEs + ")"; 
//			mapDao.executeSql2(sql);
//		}
//	}
//	@Override
//	public Map isonlineCount(Map<String, Object> paramMap) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		String sql ="select count(*) as bCount from brinhouse a left join house b on a.houseIEEE = b.houseIEEE where a.userid =:userid and a.deviceAllFlag =1 and b.isonline =1";
//		List<Map> t = mapDao.executeSql(sql.toString(), paramMap);
//		params.put("onisonline", t.get(0).get("bCount"));
//		sql="select count(*) as bCount from brinhouse a left join house b on a.houseIEEE = b.houseIEEE where a.userid =:userid and (a.deviceAllFlag <>1 or b.isonline <>1)";
//		List<Map> t1 = mapDao.executeSql(sql.toString(), paramMap);
//		params.put("offisonline", t1.get(0).get("bCount"));
//		return params;
//	}
//	
	@Override
	public int isRegister(String houseIEEE) {
		String sql ="select 1 from house where binary houseIEEE =:houseIEEE limit 1";
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("houseIEEE", houseIEEE);
		List<Map> i = mapDao.executeSql(sql,paramMap);
		return i.size();
	}
	@Override
	public int addIntegrity(Map<String, Object> paramMap) {
		String sql = "update brinhouse set intFlag=:intFlag,description=:description where userid=:userid and houseIEEE=:houseIEEE ";
		int i =mapDao.executeSql2(sql,paramMap);
		return i;
	}
	@Override
	public int updateInitResult(Map<String, Object> param) {
		String sql ="update brinhouse set initResult =:initResult where userid=:userid and houseIEEE=:houseIEEE ";
		int i =mapDao.executeSql2(sql,param);
		return i;
	}
	/**
	 * 定时检查burn in是否有异常，如有异常，则保存burn in结果
	 */
	@Override
	public void updateRflag() throws IOException {
//		更新burnin完成后的状态
		String sql = "update brinhouse set rFlag = 2,endtime=now() where (unix_timestamp(now())-unix_timestamp(starttime)) >= (limittime * 3600) and rFlag = 1";
		mapDao.executeSql2(sql);
//		saveWrongResult();//保存burn in出错原因
		
//		String sql1 ="select houseIEEE from brinhouse where rFlag = 1";
		
//		正在burnin中的状态
		String sql1 = "select DISTINCT b.houseIEEE,Sum(case when a.isonline=0 then 1 else 0 end) as aCount,c.isonline from device a left join brinhouse b on a.houseIEEE=b.houseIEEE " 
				+ "left join house c on a.houseIEEE = c.houseIEEE where b.rFlag = 1 group by b.houseIEEE,b.userid";
		List<Map> t1 = mapDao.executeSql(sql1);
//		Map<String, Object> tmpMap = new HashMap<String,Object>();
//		Map<String, Object> pMap= new HashMap<String,Object>();
//		List<Map> dCList =null;
//		List<String> offhouse = new ArrayList<String>();
//		String cloudPort = PropertiesUtil.getProperty("cloudAddress.port");
//		for (int i = 0; i < t1.size(); i++) {
////			String cloudAddress = "192.168.1.251";
//			String cloudAddress =  (String) HouseieeeListener.houseieeeProxyserverMap.get(t1.get(i).get("houseIEEE"));
//	    	String callUrl = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/brinController/getdeviceCount.do";
//	    	tmpMap.put("houseIEEE", t1.get(i).get("houseIEEE"));
//	    	pMap.put("json",JSON.toJSONStringWithDateFormat(tmpMap, "yyyy-MM-dd HH:mm:ss"));
//			String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
//			Map<String, Object> map = JSON.parseObject(rsStr,Map.class);
//			Map<String, Object> paMap = (Map<String, Object>) map.get("response_params");
//			dCList=(List<Map>) paMap.get("dCList");
//			if((int)(dCList.get(0).get("aCount"))!=0){
//				offhouse.add((String)(dCList.get(0).get("houseieee")));
//			}
//		}
		if(!t1.isEmpty()) {
			StringBuilder hValue = new StringBuilder();
			Iterator<Map> hItor = t1.iterator();
			while(hItor.hasNext()) {
				Map hMap = hItor.next();
				int dSum = ((BigDecimal) hMap.get("aCount")).intValue();
				String hIsonline = (String) hMap.get("isonline");
				if(dSum > 0 
						|| hIsonline == null 
						|| Integer.parseInt(hIsonline) == 0)
					hValue.append("'").append(hMap.get("houseIEEE")).append("',");
			}
			if(StringUtils.isNotBlank(hValue.toString())) {
				String hValueStr = hValue.deleteCharAt(hValue.length() - 1).toString();
				//更改状态为异常
				String uSql = "update brinhouse set rFlag = 3,endtime = now() where houseIeee in (" + hValueStr + ")";
				mapDao.executeSql2(uSql);
				//将异常设备信息写入数据库   
				String expSql = "SELECT * FROM ((SELECT d.houseIEEE,d.deviceName,d.ieee,d.ep FROM device d WHERE d.isonline = 0) as dState RIGHT JOIN (SELECT h.houseIEEE as hhouseIEEE FROM	house h	WHERE	h.isonline = 1) AS hState ON dState.houseIEEE = hState.hhouseIEEE) WHERE dState.houseIEEE IN (" + hValueStr + ") ";
//				String expSql = "SELECT d.houseIEEE,d.deviceName,d.ieee,d.ep,h.isonline FROM device d LEFT JOIN house h on d.houseIEEE = h.houseIEEE WHERE (d.isonline = 0 AND h.isonline = 1) AND d.houseIEEE in (" + hValueStr + ")";
				List<Map> expDvList = mapDao.executeSql(expSql);
				Map<String, List<Map>> tMap = new HashMap<String, List<Map>>();
				if(!expDvList.isEmpty()) {
					Iterator<Map> eItor = expDvList.iterator();
					while(eItor.hasNext()) {
						Map hMap = eItor.next();
						String houseIEEE = (String) hMap.get("houseIEEE");
						if(tMap.get(houseIEEE) == null) {
							List<Map> hList = new ArrayList<Map>();
							hList.add(hMap);
							tMap.put(houseIEEE, hList);
						} 
						else {
							tMap.get(houseIEEE).add(hMap);
						}
					}
					Iterator<String> kItor = tMap.keySet().iterator();
					while(kItor.hasNext()) {
						String houseIEEE = kItor.next();
						List<Map> expHDvList = tMap.get(houseIEEE);
						String wrongResult = JSON.toJSONStringWithDateFormat(expHDvList, "yyyy-MM-dd HH:mm:ss");
						String sqlString = "update brinhouse set wrongResult ='"+ wrongResult+"' where houseIEEE ='" + houseIEEE + "'";
						mapDao.executeSql2(sqlString);
					}
				}
				logger.info("burn in 有异常，更新状态结束-------");
			}
//		String cloudPort = PropertiesUtil.getProperty("cloudAddress.port");
//		for (int i = 0; i < t1.size(); i++) {
////			String cloudAddress = "192.168.1.251";
//			String cloudAddress =  (String) HouseieeeListener.houseieeeProxyserverMap.get(t1.get(i).get("houseIEEE"));
//	    	String callUrl = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/brinController/getdeviceCount.do";
//	    	tmpMap.put("houseIEEE", t1.get(i).get("houseIEEE"));
//	    	pMap.put("json",JSON.toJSONStringWithDateFormat(tmpMap, "yyyy-MM-dd HH:mm:ss"));
//			String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
//			Map<String, Object> map = JSON.parseObject(rsStr,Map.class);
//			Map<String, Object> paMap = (Map<String, Object>) map.get("response_params");
//			dCList=(List<Map>) paMap.get("dCList");
//			if((int)(dCList.get(0).get("aCount"))!=0){
//				offhouse.add((String)(dCList.get(0).get("houseieee")));
//			}
//		}
			else
				logger.info("burn in定时保存，无异常设备-------");
		}
		else
			logger.info("burn in定时保存，无异常设备-------");
//		if(offhouse.size()>0){
//			StringBuilder sql =new StringBuilder("update brinhouse set rFlag = 3,endtime = now() where houseIeee in (");
//			for(int i = 0; i<offhouse.size() ; i++){
//				sql.append("'").append(offhouse.get(i)).append("',");
//			}
//			sql.deleteCharAt(sql.length()-1);
//			sql.append(")");
//			int i =mapDao.executeSql2(sql.toString());
//			logger.info("burn in 异常结束houseIEEE数量有："+offhouse.size());
//		}else {
//			logger.info("burn in定时保存，无异常设备");
//		}
	}
	
	/**
	 * 显示burn in过程中是否有设备出现异常
	 */
	@Override
	public List<Map> findBrinwrong(Map<String , Object> paraMap) {
//		String sql = "SELECT a.wrongResult,a.houseIEEE,a.starttime,a.endtime,b.deviceName,b.ieee,b.isonline,b.ep from brinhouse a left JOIN device b ON a.houseIEEE = b.houseIEEE WHERE a.rFlag = 3 and b.isonline = 0 and a.userid = :userid";
		String sql ="select a.wrongResult,a.houseIEEE,a.starttime,a.endtime from brinhouse a where a.rFlag = 3 and a.userid = :userid";
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("userid", paraMap.get("userid"));
		List<Map> t1 = mapDao.executeSql(sql,params);
		return t1;
	}
	
	@Override
	public List<Map> findWrongdevice(Map<String, Object> params) {
		String sql1 ="SELECT d.houseIEEE,d.deviceName,d.ieee,d.ep FROM device d LEFT JOIN house h on d.houseIEEE = h.houseIEEE WHERE(d.isonline = 0 or h.isonline = 0) AND d.houseIEEE = :houseIEEE";
//		for (int i = 0; i < t1.size(); i++) {
//			sql1+="'";
//			sql1+=t1.get(i).get("houseIEEE");
//			sql1+="',";
//		}
//		sql1.substring(0, sql1.length()-1);
//		sql1+=")";
		List<Map> t2 = mapDao.executeSql(sql1,params);
		return t2;
	}
	public void saveWrongResult() throws IOException{
		String sql ="select houseIEEE from brinhouse where rFlag = 1";
		List<Map> t1 = mapDao.executeSql(sql);
		String cloudPort = PropertiesUtil.getProperty("cloudAddress.port");
		Map<String, Object> pMap= new HashMap<String,Object>();
		Map<String, Object> pamap= new HashMap<String,Object>();
//    	pMap.put("json",JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss"));
    	List<Map> dCList =null;
    	Map<String, Object> tmpMap = new HashMap<String,Object>();
    	for (int i = 0; i < t1.size(); i++) {
    		String cloudAddress =  (String) HouseieeeListener.houseieeeProxyserverMap.get(t1.get(i).get("houseIEEE"));
//    		String cloudAddress = "192.168.1.251";
        	String callUrl = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/brinController/findWrongdevice.do";
        	tmpMap.put("houseIEEE", t1.get(i).get("houseIEEE"));
        	pMap.put("json",JSON.toJSONStringWithDateFormat(tmpMap, "yyyy-MM-dd HH:mm:ss"));
			String rsStr = TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
			Map<String, Object> map = JSON.parseObject(rsStr,Map.class);
			Map<String, Object> paMap = (Map<String, Object>) map.get("response_params");
			dCList=(List<Map>) paMap.get("dCList");
			if(dCList.size()>0){
				String wrongResult = JSON.toJSONStringWithDateFormat(dCList, "yyyy-MM-dd HH:mm:ss");
				String sqlString = "update brinhouse set wrongResult ='"+ wrongResult+"' where houseIEEE ='"+t1.get(i).get("houseIEEE")+"'";
				int a = mapDao.executeSql2(sqlString);
			}
    	}
	}
	@Override
	public List<Map> onlineCount(Map<String, Object> paraMap) {
		StringBuilder sqlBuilder = new StringBuilder("select sum(case when h.isonline = 0 then 1 else 0 end) as offline,sum(case when h.isonline = 1 then 1 else 0 end) as online from ");
		sqlBuilder.append("brinhouse b left join house h on h.houseIEEE = b.houseIEEE where userid = :userid");
		List<Map> list = mapDao.executeSql(sqlBuilder.toString(),paraMap);
		return list;
	}
	@Override
	public int findExce(Map<String, Object> paraMap) {
		StringBuilder sql= new StringBuilder("select 1 from brinhouse where userid = :userid and rFlag = 3 limit 1");
		List<Map> list = mapDao.executeSql(sql.toString(),paraMap);
		if(list.size()!=0){
			return 1;
		}else {
			return -1;
		}
	}
	/*旧的统计设备总数与离线总数*/
	@Override
	public List<Map> getdeviceCount(Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			StringBuilder sql =new StringBuilder("Select houseieee,Sum(case when isonline=0 then 1 else 0 end) as aCount,Count(*) as dCount from device where houseIeee = :houseIEEE");
//			StringBuilder sql =new StringBuilder("select count(*) as dCount,houseIEEE,");
//			sql.append("case when (select count(*) from device a where a.isonline=0 and a.houseIEEE=b.houseIEEE)=0 then 1 ");
//			sql.append("else 0 end deviceAllFlag from device b where b.houseIeee = :houseIEEE ");
//    		sql.append(" group by b.houseIEEE");
    		List<Map> tList =mapDao.executeSql(sql.toString(),params);
    		return tList;
    	}
		return null;
	}
	
}


