package sy.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Warnsend;
import sy.service.WarnsendServiceI;

@Service("warnsendService")
public class WarnsendServiceImpl implements WarnsendServiceI {

	private static final Logger logger = Logger.getLogger(WarnsendServiceImpl.class);

	private BaseDaoI<Warnsend> warnsendDao;

	public BaseDaoI<Warnsend> getWarnsendDao() {
		return warnsendDao;
	}

	@Autowired
	public void setWarnsendDao(BaseDaoI<Warnsend> warnsendDao) {
		this.warnsendDao = warnsendDao;
	}

	@Override
	public Warnsend save(Warnsend warnsend) {
		/*Warnsend t = new Warnsend();
		BeanUtils.copyProperties(warnsend, t, new String[] { "pwd" });*/
		/*t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(warnsend.getPwd()));*/
		warnsendDao.save(warnsend);
//		BeanUtils.copyProperties(t, warnsend);
		return warnsend;
	}
	
	@Override
	public Warnsend update(Warnsend warnsend) {
		warnsendDao.update(warnsend);
		return warnsend;
	}
	
	@Override
	public Warnsend get(Warnsend warnsend) {
		Map<String, Object> params = new HashMap<String, Object>();
		Warnsend t =  null;
		if (warnsend.getType().equals("0")) { // 安卓
			params.put("houseIeee", warnsend.getHouseIeee());
			params.put("username", warnsend.getUsername());
			t = warnsendDao.get("from Warnsend t where t.houseIeee = :houseIeee and t.username = :username and t.type = '0' ", params);
			/*// 安卓客户端每次启动用户名都不一样
			if (t == null) {
				params.clear();
				params.put("houseIeee", warnsend.getHouseIeee());
				warnsendDao.executeHql("delete Warnsend t where t.houseIeee = :houseIeee and t.type = '0' ", params);				
			}*/
		} else if (warnsend.getType().equals("1")) { // ios
			//params.put("houseIeee", warnsend.getHouseIeee());
			params.put("deviceToken", warnsend.getDeviceToken());
			//t = warnsendDao.get("from Warnsend t where t.houseIeee = :houseIeee and t.deviceToken = :deviceToken and t.type = '1' ", params);
			t = warnsendDao.get("from Warnsend t where t.deviceToken = :deviceToken and t.type = '1' ", params);
		}
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Warnsend find(Warnsend warnsend) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", warnsend.getHouseIeee());
		Warnsend t = warnsendDao.get("from Warnsend t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Warnsend> findList(Warnsend warnsend) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Warnsend t where t.isLogout = 0 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (warnsend.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", warnsend.getHouseIeee());
		}
		if (warnsend.getType() != null) {
			hql.append("and t.type = :type ");
			params.put("type", warnsend.getType());
		}
		List<Warnsend> t = warnsendDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Object[]> findListSql(Warnsend warnsend) {
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
//		String tableName = "deviceWarnHistory_" + warnsend.getWarnsendIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from Warnsend s,").append(tableName).append(" e where 1=1 and s.warnsendIeee = e.warnsendIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (warnsend.getWarnsendIeee() != null) {
			sql.append("and s.warnsendIeee = :warnsendIeee ");
			params.put("warnsendIeee", warnsend.getWarnsendIeee());
		}
		if (warnsend.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + warnsend.getName() + "%");
		}
		if (warnsend.getMinLongitude() != null) {
			sql.append("and s.longitude >= :minlongitude ");
			params.put("minlongitude", warnsend.getMinLongitude());
		}
		if (warnsend.getMaxLongitude() != null) {
			sql.append("and s.longitude <= :maxlongitude ");
			params.put("maxlongitude", warnsend.getMaxLongitude());
		}
		if (warnsend.getMinLatitude() != null) {
			sql.append("and s.latitude >= :minlatitude ");
			params.put("minlatitude", warnsend.getMinLatitude());
		}		
		if (warnsend.getMaxLatitude() != null) {
			sql.append("and s.latitude <= :maxlatitude ");
			params.put("maxlatitude", warnsend.getMaxLatitude());
		}	
		if (warnsend.getNetworkAddress() != null) {
			sql.append("and s.networkAddress = :networkAddress ");
			params.put("networkAddress", warnsend.getNetworkAddress());
		}*/
//		List<Object[]> t = warnsendDao.findSql(sql.toString(), params, Warnsend.class, Deviceattribute.class);
//		List<Object[]> t = warnsendDao.findSql(sql.toString(), params, Warnsend.class, DevicewarnhistoryWarnsendidYear.class);
		/*if (t != null) {
			return t;
		}*/
		return null;
	}
	
	@Override
	public int delete(Warnsend warnsend) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Warnsend t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		/*if (warnsend.getWarnsendIeee() != null) {
			hql.append("and t.warnsendIeee = :warnsendIeee ");
//			params2.put("warnsendIeee", warnsend.getWarnsendIeee());
			warnsendDao.executeHql("delete from Deviceattribute where warnsendIeee = :warnsendIeee", params2);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String tableName = "deviceAttributeHistory_" + warnsend.getWarnsendIeee() + "_" + year;
//			warnsendDao.executeSql2("delete from " + tableName + " where warnsendIeee = :warnsendIeee", params2);
			warnsendDao.executeSql2("drop table IF EXISTS " + tableName);
			tableName = "deviceOperateHistory_" + warnsend.getWarnsendIeee() + "_" + year;
			warnsendDao.executeSql2("drop table IF EXISTS " + tableName);
//			warnsendDao.executeSql2("delete from " + tableName + " where warnsendIeee = :warnsendIeee", params2);
			tableName = "deviceWarnHistory_" + warnsend.getWarnsendIeee() + "_" + year;
			warnsendDao.executeSql2("drop table IF EXISTS " + tableName);
//			warnsendDao.executeSql2("delete from " + tableName + " where warnsendIeee = :warnsendIeee", params2);
		}*/
		/*if (warnsend.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + warnsend.getName() + "%");
		}
		if (warnsend.getPort() != null) {
			hql.append("and t.port = :port ");
			params.put("port", warnsend.getPort());
		}*/
		return warnsendDao.executeHql(hql.toString(), params);
	}

	@Override
	public Warnsend login(Warnsend warnsend) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(warnsend.getPwd()));
//		params.put("name", warnsend.getName());
		Warnsend t = warnsendDao.get("from Warnsend t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return warnsend;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(Warnsend warnsend) {
		DataGrid dg = new DataGrid();
		String hql = "from Warnsend t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(warnsend, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(warnsend, hql);
		List<Warnsend> l = warnsendDao.find(hql, params, warnsend.getPage(), warnsend.getRows());
		List<Warnsend> nl = new ArrayList<Warnsend>();
		changeModel(l, nl);
		dg.setTotal(warnsendDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Warnsend> l, List<Warnsend> nl) {
		if (l != null && l.size() > 0) {
			for (Warnsend t : l) {
				Warnsend u = new Warnsend();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Warnsend warnsend, String hql) {
		/*if (warnsend.getSort() != null) {
			hql += " order by " + warnsend.getSort() + " " + warnsend.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Warnsend warnsend, String hql, Map<String, Object> params) {
		/*if (warnsend.getName() != null && !warnsend.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + warnsend.getName().trim() + "%%");
		}*/
		return hql;
	}
	
	@Override
	public int addDeviceAttributeHistoryTable(String warnsendIEEE) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + warnsendIEEE + "_" + year;
		String sql = "CREATE TABLE IF not EXISTS " + "`" + tableName +"` ("+
				"`id` bigint(20) NOT NULL AUTO_INCREMENT," +
				"`warnsendIEEE` varchar(50) DEFAULT NULL,`room_id` bigint(20) DEFAULT NULL," + 
				"`device_ieee` varchar(50) DEFAULT NULL,`device_ep` varchar(50) DEFAULT NULL," +
				"`cluster_id` varchar(50) DEFAULT NULL,`attribute_id` varchar(50) DEFAULT NULL,  `attributeName` varchar(50) DEFAULT NULL," +
				"`value` varchar(50) DEFAULT NULL,`opdatetime` datetime DEFAULT CURRENT_TIMESTAMP,"+
				"PRIMARY KEY (`id`),KEY `opdatetime` (`opdatetime`),KEY `warnsendIEEE, room_id, device_ieee, device_ep, " +
				"cluster_id, attribut` (`warnsendIEEE`,`room_id`,`device_ieee`,`device_ep`,`cluster_id`,`attribute_id`) USING BTREE,KEY `attributeName` (`attributeName`)" +
				") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
		return warnsendDao.executeSql2(sql);
	}
	
	@Override
	public int addDeviceOperateHistoryTable(String warnsendIEEE) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceOperateHistory_" + warnsendIEEE + "_" + year;
		String sql = "CREATE TABLE IF not EXISTS " + "`" + tableName +"` ("+
				 "`id` bigint(20) NOT NULL AUTO_INCREMENT,`warnsendIEEE` varchar(50) DEFAULT NULL," +
				  "`room_id` bigint(20) DEFAULT NULL,`device_ieee` varchar(50) DEFAULT NULL," +
				  "`device_ep` varchar(50) DEFAULT NULL, `opname` varchar(200) DEFAULT NULL,"+
				  "`optype` varchar(20) DEFAULT NULL,`opparam` varchar(2000) DEFAULT NULL," +
				  "`opdatetime` datetime DEFAULT CURRENT_TIMESTAMP,PRIMARY KEY (`id`)," +
				  "KEY `opname` (`opname`),KEY `optype` (`optype`),KEY `opdatetime` (`opdatetime`)," +
				  "KEY `warnsendIEEE, room_id, device_ieee, device_ep` (`warnsendIEEE`,`room_id`,`device_ieee`,`device_ep`) USING BTREE" +
				  ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
		return warnsendDao.executeSql2(sql);
	}
	
	@Override
	public int addDeviceWarnHistoryTable(String warnsendIEEE) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + warnsendIEEE + "_" + year;
		String sql = "CREATE TABLE IF not EXISTS " + "`" + tableName +"` ("+
				 "`id` bigint(20) NOT NULL AUTO_INCREMENT,`warnsendIEEE` varchar(50) DEFAULT NULL," +
				  "`room_id` bigint(20) DEFAULT NULL,`zone_ieee` varchar(50) DEFAULT NULL,"+
				  "`zone_ep` varchar(50) DEFAULT NULL,`cie_ieee` varchar(50) DEFAULT NULL," +
				  "`cie_ep` varchar(50) DEFAULT NULL, `w_mode` varchar(100) DEFAULT NULL,"+
				  "`w_description` varchar(1000) DEFAULT NULL,`warndatetime` datetime DEFAULT CURRENT_TIMESTAMP,`sendState` varchar(2) DEFAULT '0',"+
				  "PRIMARY KEY (`id`),KEY `w_mode` (`w_mode`),KEY `warndatetime` (`warndatetime`),"+
				  "KEY `warnsendIEEE, room_id, zone_ieee, zone_ep, cie_ieee, cie_ep` (`warnsendIEEE`,`room_id`,`zone_ieee`,`zone_ep`,`cie_ieee`,`cie_ep`) USING BTREE" +
				  ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
		return warnsendDao.executeSql2(sql);
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Warnsend t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		warnsendDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}

	@Override
	public int update2(Map<String, Object> mapparame) {
		String sqlString="update warnsend set isonline=:isonline,Language=:Language WHERE houseIEEE=:houseIEEE and deviceToken=:deviceToken";
		int reust=warnsendDao.executeSql2(sqlString,mapparame);
				// "update device set deviceattribute = null where houseIEEE = :houseIEEE and ieee = :ieee and ep=:ep";

		return reust;
	}
	
	@Override
	public int deleteByDeviceToken(String deviceToken){
		try{
			String sql = "DELETE FROM warnsend WHERE deviceToken=:deviceToken";
			Map<String,Object> params = new HashMap<>();
			params.put("deviceToken", deviceToken);
			return warnsendDao.executeSql2(sql, params);
		}catch(Exception e){
			logger.error("delete warnsend error",e);
		}
		return 0;
	}
}
