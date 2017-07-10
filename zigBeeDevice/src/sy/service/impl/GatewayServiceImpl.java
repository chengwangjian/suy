package sy.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.service.GatewayServiceI;
@Service("gatewayService")
public class GatewayServiceImpl implements GatewayServiceI{
private BaseDaoI<Map> mapDao;
	public BaseDaoI<Map> getMapDao() {
	return mapDao;
}
  @Autowired
  public void setMapDao(BaseDaoI<Map> mapDao) {
	this.mapDao = mapDao;
}

	@Override
	public int saveLog(Map map) {
		// TODO Auto-generated method stub
		try {
			if(org.apache.commons.lang.StringUtils.isBlank((String) map.get("time"))){
				map.put("time", new Date());	
			}
			if(org.apache.commons.lang.StringUtils.isBlank((String) map.get("description"))){
				map.put("description","无");	
			}
			if(StringUtils.isNotBlank((String) map.get("description"))){
				if(map.get("description").equals("Gateway restart!")){
					map.put("startover", 1);
				Map<String, Object> params = new HashMap<String, Object>();
				String houseIEEE = (String)map.get("houseIEEE");
				Date statechangetime = new Date();
				params.put("statechangetime", statechangetime);
				params.put("staterecords", "8");  //staterecords=8的时候为重启
				params.put("houseIEEE", houseIEEE);
				String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);	
			    String sql2 = "INSERT INTO " + tableName +"(houseIEEE,staterecords,statechangetime) values(:houseIEEE,:staterecords,:statechangetime)";
				this.mapDao.executeSql2(sql2, params);						
				}else {
					map.put("startover", 0);
				}
			}
			String sql="INSERT INTO gatewaylog(houseIEEE,description,time,startover) values(:houseIEEE,:description,:time,:startover)";	
			this.mapDao.executeSql2(sql, map);
		  } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		return 1;
	}
	@Override
	public long getGatewayLogCount(String searchText) throws Exception {
		String tableName = "gatewaylog";
		String sql = "select count(*) enmgCount from " + tableName;
		List<Map> iosList = mapDao.executeSql(sql);
		return ((BigInteger) iosList.get(0).get("enmgCount"))
				.intValue();
	}
	
	@Override
	public List<Map> getGatewayLogs(String searchText, int pageSize,
			int startRow) throws Exception {
		//String tableName = "gatewaylog" + new SimpleDateFormat("yyyy").format(new Date());
		String tableName = "gatewaylog";
		String sql = "select * from " + tableName + " order by id asc limit " + startRow + "," + pageSize;
		List<Map> enmgList = mapDao.executeSql(sql);
		return enmgList;
	}
	@Override
	public int deleteGatewayLog(String id) throws Exception {
		//String tableName = "gatewaylog" + new SimpleDateFormat("yyyy").format(new Date());
		String tableName = "gatewaylog";
		String sql = "delete from " + tableName + " where id = :id";
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return mapDao.executeSql2(sql, params);
	}
	
	@Override
	public List<Map> getStartOver(String houseIeee, String lasttime) throws Exception {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endtime = sdf.parse(lasttime);
		Calendar date = Calendar.getInstance();
		date.setTime(endtime);
		date.set(Calendar.DATE, date.get(Calendar.DATE ) - 1);
		Date start = sdf.parse(sdf.format(date.getTime()));
		String starttime = sdf.format(start);
		//System.out.println("时间str==>:" + starttime);
		String tablename = "gatewaylog";
		Map<String, Object>  map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as total from ").append(tablename).append(" t where t.startover=1 and 1=1");
		if(StringUtils.isNotBlank(houseIeee)) {
			sql.append(" and t.houseIEEE =:houseIEEE ");
			map.put("houseIEEE", houseIeee);
		}
		if(StringUtils.isNotBlank(starttime)) {
			sql.append(" and t.time between '").append(starttime).append("' and '").append(lasttime).append("'");
		}		
		List<Map> list = mapDao.executeSql(sql.toString(), map);
		if(list != null) {
			return list;
		}
		return null;
	}
}
