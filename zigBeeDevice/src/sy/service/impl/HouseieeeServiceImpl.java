package sy.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.House;
import sy.model.Houseieee;
import sy.service.HouseieeeServiceI;

@Service("houseieeeService")
public class HouseieeeServiceImpl implements HouseieeeServiceI {

	private static final Logger logger = Logger.getLogger(HouseieeeServiceImpl.class);

	private BaseDaoI<Houseieee> houseieeeDao;
	
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	
	public BaseDaoI<Houseieee> getHouseieeeDao() {
		return houseieeeDao;
	}

	@Autowired
	public void setHouseieeeDao(BaseDaoI<Houseieee> houseieeeDao) {
		this.houseieeeDao = houseieeeDao;
	}

	@Override
	public Houseieee save(Houseieee houseieee) {
		/*Houseieee t = new Houseieee();
		BeanUtils.copyProperties(houseieee, t, new String[] { "pwd" });*/
		/*t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(houseieee.getPwd()));*/
		houseieeeDao.save(houseieee);
//		BeanUtils.copyProperties(t, houseieee);
		return houseieee;
	}
	
	@Override
	public Houseieee update(Houseieee houseieee) {
		houseieeeDao.update(houseieee);
		return houseieee;
	}
	
	@Override
	public Houseieee get(Houseieee houseieee) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseieee.getHouseIeee());
//		Houseieee t = houseieeeDao.get("from Houseieee t where t.houseIeee = :houseIeee and enableFlag = '1' ", params);
		Houseieee t = houseieeeDao.get("from Houseieee t where t.houseIeee = :houseIeee ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/**
	 * 判断是否通过过滤器，是否提供接口服务
	 * @author: zhuangxd
	 * 时间：2014-7-4 下午1:01:38
	 * @param houseieee
	 * @return
	 */
	@Override
	public Houseieee isFilter(Houseieee houseieee) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseieee.getHouseIeee());
		Houseieee t = houseieeeDao.get("from Houseieee t where t.houseIeee = :houseIeee and enableFlag = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/*查询houseieee表是否有数据*/
	@Override
	public Houseieee get1(Houseieee houseieee) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseieee.getHouseIeee());
		Houseieee t = houseieeeDao.get("from Houseieee t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	@Override
	public Houseieee find(Houseieee houseieee) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseieee.getHouseIeee());
		Houseieee t = houseieeeDao.get("from Houseieee t where t.houseIeee = :houseIeee and enableFlag = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Houseieee> findList(Houseieee houseieee) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseieee.getHouseIeee());
		List<Houseieee> t = houseieeeDao.find("from Houseieee t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;*/
		
		StringBuffer hql = new StringBuffer();
		hql.append("from Houseieee t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (houseieee.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", houseieee.getHouseIeee());
		}
		if (houseieee.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + houseieee.getName() + "%");
		}
		if (houseieee.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", houseieee.getSecretKey());
		}
		if (houseieee.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", houseieee.getVendorCode());
		}
		if (houseieee.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + houseieee.getVendorName() + "%");
		}
		if (houseieee.getEncodemethod() != null) {
			hql.append("and t.encodemethod = :encodemethod ");
			params.put("encodemethod", houseieee.getEncodemethod());
		}
		if (houseieee.getXmppIp() != null) {
			hql.append("and t.xmppIp = :xmppIp ");
			params.put("xmppIp", houseieee.getXmppIp());
		}
		if (houseieee.getXmppPort() != null) {
			hql.append("and t.xmppPort = :xmppPort ");
			params.put("xmppPort", houseieee.getXmppPort());
		}
		if (houseieee.getCloudIp1() != null) {
			hql.append("and t.cloudIp1 = :cloudIp1 ");
			params.put("cloudIp1", houseieee.getCloudIp1());
		}
		if (houseieee.getCloudPort1() != null) {
			hql.append("and t.cloudPort1 = :cloudPort1 ");
			params.put("cloudPort1", houseieee.getCloudPort1());
		}
		if (houseieee.getCloudIp2() != null) {
			hql.append("and t.cloudIp2 = :cloudIp2 ");
			params.put("cloudIp2", houseieee.getCloudIp2());
		}
		if (houseieee.getCloudPort2() != null) {
			hql.append("and t.cloudPort2 = :cloudPort2 ");
			params.put("cloudPort2", houseieee.getCloudPort2());
		}
		if (houseieee.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", houseieee.getEnableFlag());
		}
		/*if (houseieee.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", houseieee.getLongitude());
		}
		if (houseieee.getLatitude() != null) {
			hql.append("and t.latitude = :latitude ");
			params.put("latitude", houseieee.getLatitude());
		}*/
/*		if (houseieee.getMinLongitude() != null) {
			hql.append("and t.longitude >= :minlongitude ");
			params.put("minlongitude", houseieee.getMinLongitude());
		}
		if (houseieee.getMaxLongitude() != null) {
			hql.append("and t.longitude <= :maxlongitude ");
			params.put("maxlongitude", houseieee.getMaxLongitude());
		}
		if (houseieee.getMinLatitude() != null) {
			hql.append("and t.latitude >= :minlatitude ");
			params.put("minlatitude", houseieee.getMinLatitude());
		}			
		if (houseieee.getMaxLatitude() != null) {
			hql.append("and t.latitude <= :maxlatitude ");
			params.put("maxlatitude", houseieee.getMaxLatitude());
		}	
		if (houseieee.getNetworkAddress() != null) {
			hql.append("and t.networkAddress = :networkAddress ");
			params.put("networkAddress", houseieee.getNetworkAddress());
		}
		if (houseieee.getPort() != null) {
			hql.append("and t.port = :port ");
			params.put("port", houseieee.getPort());
		}
*/		List<Houseieee> t = houseieeeDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Object[]> findListSql(Houseieee houseieee) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseieee.getHouseIeee());
		List<Houseieee> t = houseieeeDao.find("from Houseieee t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;*/
		
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + houseieee.getHouseIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from Houseieee s,Deviceattribute e where 1=1 and s.houseIeee = e.houseIEEE ");
		sql.append("select {s.*}, {e.*} from Houseieee s,").append(tableName).append(" e where 1=1 and s.houseIeee = e.houseIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (houseieee.getHouseIeee() != null) {
			sql.append("and s.houseIeee = :houseIeee ");
			params.put("houseIeee", houseieee.getHouseIeee());
		}
		if (houseieee.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + houseieee.getName() + "%");
		}
		/*if (houseieee.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", houseieee.getLongitude());
		}
		if (houseieee.getLatitude() != null) {
			hql.append("and s.latitude = :latitude ");
			params.put("latitude", houseieee.getLatitude());
		}*/
	/*	if (houseieee.getMinLongitude() != null) {
			sql.append("and s.longitude >= :minlongitude ");
			params.put("minlongitude", houseieee.getMinLongitude());
		}
		if (houseieee.getMaxLongitude() != null) {
			sql.append("and s.longitude <= :maxlongitude ");
			params.put("maxlongitude", houseieee.getMaxLongitude());
		}
		if (houseieee.getMinLatitude() != null) {
			sql.append("and s.latitude >= :minlatitude ");
			params.put("minlatitude", houseieee.getMinLatitude());
		}		
		if (houseieee.getMaxLatitude() != null) {
			sql.append("and s.latitude <= :maxlatitude ");
			params.put("maxlatitude", houseieee.getMaxLatitude());
		}	
		if (houseieee.getNetworkAddress() != null) {
			sql.append("and s.networkAddress = :networkAddress ");
			params.put("networkAddress", houseieee.getNetworkAddress());
		}*/
//		List<Object[]> t = houseieeeDao.findSql(sql.toString(), params, Houseieee.class, Deviceattribute.class);
		List<Object[]> t = houseieeeDao.findSql(sql.toString(), params, Houseieee.class, DevicewarnhistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/*删除houseieee表的数据*/
	@Override
	public int delete1(Houseieee houseieee) {
	
		
		StringBuffer hql = new StringBuffer();
		hql.append("delete Houseieee t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (houseieee.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", houseieee.getHouseIeee());
		}
		return houseieeeDao.executeHql(hql.toString(), params);
	}
	
	@Override
	public int delete(Houseieee houseieee) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseieee.getHouseIeee());
		String hql = "delete Houseieee t where t.houseIeee = :houseIeee";
		return houseieeeDao.executeHql(hql, params);*/
		
		StringBuffer hql = new StringBuffer();
		hql.append("delete Houseieee t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (houseieee.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", houseieee.getHouseIeee());
			params2.put("houseIeee", houseieee.getHouseIeee());
			// houseieeeDao.executeHql("delete from Deviceattribute where houseIeee = :houseIeee", params2);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String tableName = "deviceAttributeHistory_" + houseieee.getHouseIeee() + "_" + year;
//			houseieeeDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
			// houseieeeDao.executeSql2("drop table IF EXISTS " + tableName);
			tableName = "deviceOperateHistory_" + houseieee.getHouseIeee() + "_" + year;
			//houseieeeDao.executeSql2("drop table IF EXISTS " + tableName);
//			houseieeeDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
			tableName = "deviceWarnHistory_" + houseieee.getHouseIeee() + "_" + year;
			//houseieeeDao.executeSql2("drop table IF EXISTS " + tableName);
//			houseieeeDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
		}
		if (houseieee.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + houseieee.getName() + "%");
		}
		if (houseieee.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", houseieee.getSecretKey());
		}
		if (houseieee.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", houseieee.getVendorCode());
		}
		if (houseieee.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + houseieee.getVendorName() + "%");
		}
		if (houseieee.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", houseieee.getEnableFlag());
		}
		/*if (houseieee.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", houseieee.getLongitude());
		}
		if (houseieee.getLatitude() != null) {
			hql.append("and t.latitude = :latitude ");
			params.put("latitude", houseieee.getLatitude());
		}*/
		/*if (houseieee.getMinLongitude() != null) {
			hql.append("and t.longitude >= :longitude ");
			params.put("longitude", houseieee.getMinLongitude());
		}
		if (houseieee.getMaxLongitude() != null) {
			hql.append("and t.longitude <= :longitude ");
			params.put("longitude", houseieee.getMaxLongitude());
		}
		if (houseieee.getMinLatitude() != null) {
			hql.append("and t.latitude >= :latitude ");
			params.put("latitude", houseieee.getMinLatitude());
		}			
		if (houseieee.getMaxLatitude() != null) {
			hql.append("and t.latitude <= :latitude ");
			params.put("latitude", houseieee.getMaxLatitude());
		}	
		if (houseieee.getNetworkAddress() != null) {
			hql.append("and t.networkAddress = :networkAddress ");
			params.put("networkAddress", houseieee.getNetworkAddress());
		}
		if (houseieee.getPort() != null) {
			hql.append("and t.port = :port ");
			params.put("port", houseieee.getPort());
		}*/
		return houseieeeDao.executeHql(hql.toString(), params);
	}

	@Override
	public Houseieee login(Houseieee houseieee) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(houseieee.getPwd()));
		params.put("name", houseieee.getName());
		Houseieee t = houseieeeDao.get("from Houseieee t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return houseieee;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(Houseieee houseieee) {
		DataGrid dg = new DataGrid();
		String hql = "from Houseieee t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(houseieee, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(houseieee, hql);
		List<Houseieee> l = houseieeeDao.find(hql, params, houseieee.getPage(), houseieee.getRows());
		List<Houseieee> nl = new ArrayList<Houseieee>();
		changeModel(l, nl);
		dg.setTotal(houseieeeDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Houseieee> l, List<Houseieee> nl) {
		if (l != null && l.size() > 0) {
			for (Houseieee t : l) {
				Houseieee u = new Houseieee();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Houseieee houseieee, String hql) {
		/*if (houseieee.getSort() != null) {
			hql += " order by " + houseieee.getSort() + " " + houseieee.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Houseieee houseieee, String hql, Map<String, Object> params) {
		if (houseieee.getName() != null && !houseieee.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + houseieee.getName().trim() + "%%");
		}
		return hql;
	}
	
	@Override
	public int addDeviceAttributeHistoryTable(String houseIEEE) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + houseIEEE + "_" + year;
		String sql = "CREATE TABLE IF not EXISTS " + "`" + tableName +"` ("+
				"`id` bigint(20) NOT NULL AUTO_INCREMENT," +
				"`houseIEEE` varchar(50) DEFAULT NULL,`room_id` bigint(20) DEFAULT NULL," + 
				"`device_ieee` varchar(50) DEFAULT NULL,`device_ep` varchar(50) DEFAULT NULL," +
				"`cluster_id` varchar(50) DEFAULT NULL,`attribute_id` varchar(50) DEFAULT NULL,  `attributeName` varchar(50) DEFAULT NULL," +
				"`value` varchar(50) DEFAULT NULL,`opdatetime` datetime DEFAULT CURRENT_TIMESTAMP,"+
				"PRIMARY KEY (`id`),KEY `opdatetime` (`opdatetime`),KEY `houseIEEE, room_id, device_ieee, device_ep, " +
				"cluster_id, attribut` (`houseIEEE`,`room_id`,`device_ieee`,`device_ep`,`cluster_id`,`attribute_id`) USING BTREE,KEY `attributeName` (`attributeName`)" +
				") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
		return houseieeeDao.executeSql2(sql);
	}
	
	@Override
	public int addDeviceOperateHistoryTable(String houseIEEE) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceOperateHistory_" + houseIEEE + "_" + year;
		String sql = "CREATE TABLE IF not EXISTS " + "`" + tableName +"` ("+
				 "`id` bigint(20) NOT NULL AUTO_INCREMENT,`houseIEEE` varchar(50) DEFAULT NULL," +
				  "`room_id` bigint(20) DEFAULT NULL,`device_ieee` varchar(50) DEFAULT NULL," +
				  "`device_ep` varchar(50) DEFAULT NULL, `opname` varchar(200) DEFAULT NULL,"+
				  "`optype` varchar(20) DEFAULT NULL,`opparam` varchar(2000) DEFAULT NULL," +
				  "`opdatetime` datetime DEFAULT CURRENT_TIMESTAMP,PRIMARY KEY (`id`)," +
				  "KEY `opname` (`opname`),KEY `optype` (`optype`),KEY `opdatetime` (`opdatetime`)," +
				  "KEY `houseIEEE, room_id, device_ieee, device_ep` (`houseIEEE`,`room_id`,`device_ieee`,`device_ep`) USING BTREE" +
				  ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
		return houseieeeDao.executeSql2(sql);
	}
	
	@Override
	public int addDeviceWarnHistoryTable(String houseIEEE) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + houseIEEE + "_" + year;
		String sql = "CREATE TABLE IF not EXISTS " + "`" + tableName +"` ("+
				 "`id` bigint(20) NOT NULL AUTO_INCREMENT,`houseIEEE` varchar(50) DEFAULT NULL," +
				  "`room_id` bigint(20) DEFAULT NULL,`zone_ieee` varchar(50) DEFAULT NULL,"+
				  "`zone_ep` varchar(50) DEFAULT NULL,`cie_ieee` varchar(50) DEFAULT NULL," +
				  "`cie_ep` varchar(50) DEFAULT NULL, `w_mode` varchar(100) DEFAULT NULL,"+
				  "`w_description` varchar(1000) DEFAULT NULL,`warndatetime` datetime DEFAULT CURRENT_TIMESTAMP,`sendState` varchar(2) DEFAULT '0',"+
				  "PRIMARY KEY (`id`),KEY `w_mode` (`w_mode`),KEY `warndatetime` (`warndatetime`),"+
				  "KEY `houseIEEE, room_id, zone_ieee, zone_ep, cie_ieee, cie_ep` (`houseIEEE`,`room_id`,`zone_ieee`,`zone_ep`,`cie_ieee`,`cie_ep`) USING BTREE" +
				  ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
		return houseieeeDao.executeSql2(sql);
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Houseieee t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		houseieeeDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}

	@Override
	public List<Map> getReliClientByRegion(Map<String ,Object> map, Object object) {
		String sql="SELECT DISTINCT reliclient.client_code,reliclient.region,reliclient.id from reliuser inner JOIN reliuserrole on reliuserrole.user_id=reliuser.id inner JOIN relirole on relirole.id=reliuserrole.role_id LEFT JOIN  reliroleclient on reliroleclient.role_id=relirole.id inner JOIN reliclient on reliclient.id=reliroleclient.client_id where reliuser.id=:userid ";		
		HashMap<String,Object>params=new HashMap<String, Object>();
		params.put("userid",map.get("userid"));
		if(object !="" && object!=null){
			sql+="and reliclient.region=:region ";
			params.put("region", object);
		}	
		String clientCode = (String) map.get("clientCode");
		if(StringUtils.isNotBlank(clientCode)) {
			sql += "and BINARY reliclient.client_code = :clientCode ";
			params.put("clientCode", clientCode);
		}
		sql += "limit 1";
		List<Map> list = this.mapDao.executeSql(sql, params);
		return list;
	}
	
	
}
