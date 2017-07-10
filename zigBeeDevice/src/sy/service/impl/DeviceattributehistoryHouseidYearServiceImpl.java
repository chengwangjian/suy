package sy.service.impl;

import java.io.BufferedOutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DeviceattributehistoryHouseidYear;
import sy.service.DeviceattributehistoryHouseidYearServiceI;
import zbHouse.editor.Node2Many;

@Service("deviceattributehistoryHouseidYearService")
public class DeviceattributehistoryHouseidYearServiceImpl implements DeviceattributehistoryHouseidYearServiceI {

	private static final Logger logger = Logger.getLogger(DeviceattributehistoryHouseidYearServiceImpl.class);

	private BaseDaoI<DeviceattributehistoryHouseidYear> deviceattributehistoryHouseidYearDao;
	
	private BaseDaoI<Map> mapDao;
	private Node2Many nodeCheck;
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<DeviceattributehistoryHouseidYear> getDeviceattributehistoryHouseidYearDao() {
		return deviceattributehistoryHouseidYearDao;
	}

	@Autowired
	public void setDeviceattributehistoryHouseidYearDao(BaseDaoI<DeviceattributehistoryHouseidYear> deviceattributehistoryHouseidYearDao) {
		this.deviceattributehistoryHouseidYearDao = deviceattributehistoryHouseidYearDao;
	}

	@Override
	public DeviceattributehistoryHouseidYear save(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		/*DeviceattributehistoryHouseidYear t = new DeviceattributehistoryHouseidYear();
		BeanUtils.copyProperties(deviceattributehistoryHouseidYear, t, new String[] { "pwd" });*/
		/*t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(deviceattributehistoryHouseidYear.getPwd()));*/
		deviceattributehistoryHouseidYearDao.save(deviceattributehistoryHouseidYear);
//		BeanUtils.copyProperties(t, deviceattributehistoryHouseidYear);
		return deviceattributehistoryHouseidYear;
	}
	
	@Override
	public int saveSql(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String houseIEEE = deviceattributehistoryHouseidYear.getHouseIeee();
		String tableName = "deviceAttributeHistory_" + houseIEEE + "_" + year;
		String sql = "insert into " + tableName + "(houseIeee,room_Id,device_Ieee,device_Ep,cluster_Id,attribute_Id,attributeName,value,opdatetime) values(:houseIeee," +
				":roomId,:deviceIeee,:deviceEp,:clusterId,:attributeId,:attributeName,:value,:opdatetime)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseIEEE);
		params.put("roomId", deviceattributehistoryHouseidYear.getRoomId());
		params.put("deviceIeee", deviceattributehistoryHouseidYear.getDeviceIeee());
		params.put("deviceEp", deviceattributehistoryHouseidYear.getDeviceEp());
		params.put("clusterId", deviceattributehistoryHouseidYear.getClusterId());
		params.put("attributeId", deviceattributehistoryHouseidYear.getAttributeId());
		params.put("attributeName", deviceattributehistoryHouseidYear.getAttributeName());
		params.put("value", deviceattributehistoryHouseidYear.getValue());
		params.put("opdatetime", new Date());
		try {
			return deviceattributehistoryHouseidYearDao.executeSql2(sql, params);
		}
		catch(Exception e) {
			if(e != null && e.getMessage().toLowerCase().indexOf(tableName.toLowerCase()) != -1) {
				String sql2 = "Create TABLE if not exists " + tableName + " LIKE deviceattributehistory_houseid_year";
				try {
					mapDao.executeSql2(sql2);
					return deviceattributehistoryHouseidYearDao.executeSql2(sql, params);		
				} catch (Exception ex) {
					logger.error("create " + houseIEEE + " attr exception", ex);
					return 0;
				}
			}
			else {
				logger.error("insert " + houseIEEE + " isonline attr exception", e);
				return 0;
			}
		}
	}
	
	/**
	 * 修改设备表最后更新时间
	 * @author: zhuangxd
	 * 时间：2014-6-18 下午2:07:09
	 * @param deviceattributehistoryHouseidYear
	 * @return
	 */
	@Override
	public int updateDeviceTime(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		String hql = "update Device " + " set lasttime = :lasttime,deviceattribute=:deviceattribute where " +
				"houseIeee = :houseIeee and ieee = :ieee and ep = :ep and houseIeee <> ieee";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		params.put("ieee", deviceattributehistoryHouseidYear.getDeviceIeee());
		params.put("ep", deviceattributehistoryHouseidYear.getDeviceEp());
		params.put("lasttime", new Date());
		params.put("deviceattribute",deviceattributehistoryHouseidYear.getAttributeName()+" : "+deviceattributehistoryHouseidYear.getValue());
		return deviceattributehistoryHouseidYearDao.executeHql(hql, params);
	}
	
	@Override
	public List<DeviceattributehistoryHouseidYear> findList(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName).append(" t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		if (deviceattributehistoryHouseidYear.getRoomId() != 0) {
			sql.append("and t.room_Id = :roomId ");
			params.put("roomId", deviceattributehistoryHouseidYear.getRoomId());
		}
		if (deviceattributehistoryHouseidYear.getDeviceIeee() != null) {
			sql.append("and t.device_Ieee = :deviceIeee ");
			params.put("deviceIeee", deviceattributehistoryHouseidYear.getDeviceIeee());
		}
		if (deviceattributehistoryHouseidYear.getDeviceEp() != null) {
			sql.append("and t.device_Ep = :deviceEp ");
			params.put("deviceEp", deviceattributehistoryHouseidYear.getDeviceEp());
		}
		if (deviceattributehistoryHouseidYear.getClusterId() != null) {
			sql.append("and t.cluster_Id = :clusterId ");
			params.put("clusterId", deviceattributehistoryHouseidYear.getClusterId());
		}
		if (deviceattributehistoryHouseidYear.getAttributeId() != null) {
			sql.append("and t.attribute_Id = :attributeId ");
			params.put("attributeId", deviceattributehistoryHouseidYear.getAttributeId());
		}
		if (deviceattributehistoryHouseidYear.getAttributeName() != null) {
			sql.append("and t.attributeName like :attributeName ");
			params.put("attributeName", "%" + deviceattributehistoryHouseidYear.getAttributeName()  + "%");
		}
		if (deviceattributehistoryHouseidYear.getMinOpdatetime() != null) {
			sql.append("and t.opdatetime >= :minopdatetime ");
			params.put("minopdatetime", deviceattributehistoryHouseidYear.getMinOpdatetime());
		}
		if (deviceattributehistoryHouseidYear.getMaxOpdatetime() != null) {
			sql.append("and t.opdatetime <= :maxopdatetime ");
			params.put("maxopdatetime", deviceattributehistoryHouseidYear.getMaxOpdatetime());
		}	
//		sql.append(" and TO_DAYS(NOW()) - TO_DAYS(t.opdatetime) <= 1 order by opdatetime desc"); // 查询两天内的数据
		sql.append(" order by opdatetime desc limit 500"); // 查询两天内的数据,限制500条数据
		List<DeviceattributehistoryHouseidYear> t = deviceattributehistoryHouseidYearDao.findSql(sql.toString(), params, DeviceattributehistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}

	@Override
	public DeviceattributehistoryHouseidYear login(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(deviceattributehistoryHouseidYear.getPwd()));
//		params.put("name", deviceattributehistoryHouseidYear.getName());
		DeviceattributehistoryHouseidYear t = deviceattributehistoryHouseidYearDao.get("from DeviceattributehistoryHouseidYear t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return deviceattributehistoryHouseidYear;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		DataGrid dg = new DataGrid();
		String hql = "from DeviceattributehistoryHouseidYear t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(deviceattributehistoryHouseidYear, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(deviceattributehistoryHouseidYear, hql);
		List<DeviceattributehistoryHouseidYear> l = deviceattributehistoryHouseidYearDao.find(hql, params, deviceattributehistoryHouseidYear.getPage(), deviceattributehistoryHouseidYear.getRows());
		List<DeviceattributehistoryHouseidYear> nl = new ArrayList<DeviceattributehistoryHouseidYear>();
		changeModel(l, nl);
		dg.setTotal(deviceattributehistoryHouseidYearDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<DeviceattributehistoryHouseidYear> l, List<DeviceattributehistoryHouseidYear> nl) {
		if (l != null && l.size() > 0) {
			for (DeviceattributehistoryHouseidYear t : l) {
				DeviceattributehistoryHouseidYear u = new DeviceattributehistoryHouseidYear();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear, String hql) {
		/*if (deviceattributehistoryHouseidYear.getSort() != null) {
			hql += " order by " + deviceattributehistoryHouseidYear.getSort() + " " + deviceattributehistoryHouseidYear.getOrder();
		}*/
		return hql;
	}

	private String addWhere(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear, String hql, Map<String, Object> params) {
		/*if (deviceattributehistoryHouseidYear.getName() != null && !deviceattributehistoryHouseidYear.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + deviceattributehistoryHouseidYear.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete DeviceattributehistoryHouseidYear t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		deviceattributehistoryHouseidYearDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
	
	@Override
	public Map findList2(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select t.houseIeee,t.id,t.opdatetime,t.attributeName,t.value,t.cluster_id,t.attribute_id,b.attributeNameCn from ").append(tableName).append(" t ");
			sql.append(" left join  attributenamelib b on t.cluster_id = b.clusterID and t.attribute_id = b.attrID ");
			sql.append("where t.houseIeee = :houseIeee and t.device_Ieee = :deviceIeee and t.device_Ep = :deviceEp ");
//		if(deviceattributehistoryHouseidYear.getAttributeNameCn() != null) {
//			sql.append("and b.attributeNameCn like :attributeNameCn ");
//			params.put("attributeNameCn", "%" + deviceattributehistoryHouseidYear.getAttributeNameCn()  + "%");
//		}
		if(deviceattributehistoryHouseidYear.getAttributeNameCn().equals("lineState"))
		{
			sql.append("and t.attributeName = :attributeNameCn");
			params.put("attributeNameCn", "isonline");
		}
		else if(deviceattributehistoryHouseidYear.getAttributeNameCn().equals("actionState"))
		{
			sql.append("and t.attributeName != :attributeNameCn");
			params.put("attributeNameCn", "isonline");
		}
		
		params.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		params.put("deviceIeee", deviceattributehistoryHouseidYear.getDeviceIeee());
		params.put("deviceEp", deviceattributehistoryHouseidYear.getDeviceEp());
			
		if (deviceattributehistoryHouseidYear.getRoomId() != 0) {
			sql.append("and t.room_Id = :roomId ");
			params.put("roomId", deviceattributehistoryHouseidYear.getRoomId());
		}
			
		if (deviceattributehistoryHouseidYear.getClusterId() != null) {
			sql.append("and t.cluster_Id = :clusterId ");
			params.put("clusterId", deviceattributehistoryHouseidYear.getClusterId());
		}
		if (deviceattributehistoryHouseidYear.getAttributeId() != null) {
			sql.append("and t.attribute_Id = :attributeId ");
			params.put("attributeId", deviceattributehistoryHouseidYear.getAttributeId());
		}
		/*if (deviceattributehistoryHouseidYear.getAttributeName() != null) {
			sql.append("and t.attributeName like :attributeName ");
			params.put("attributeName", "%" + deviceattributehistoryHouseidYear.getAttributeName()  + "%");
		}*/
		if (deviceattributehistoryHouseidYear.getAttributeName() != null) {
			sql.append("and t.attributeName = :attributeName ");
			params.put("attributeName",  deviceattributehistoryHouseidYear.getAttributeName());
		}
		if (deviceattributehistoryHouseidYear.getMinOpdatetime() != null) {
			sql.append("and t.opdatetime >= :minopdatetime ");
			params.put("minopdatetime", deviceattributehistoryHouseidYear.getMinOpdatetime());
		}
		if (deviceattributehistoryHouseidYear.getMaxOpdatetime() != null) {
			sql.append("and t.opdatetime <= :maxopdatetime ");
			params.put("maxopdatetime", deviceattributehistoryHouseidYear.getMaxOpdatetime());
		}
		
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getAttributeNameCn()) && !deviceattributehistoryHouseidYear.getAttributeNameCn().equals("-1")) {
			if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getLang()) && deviceattributehistoryHouseidYear.getLang().equals("1")){
				sql.append(" and b.attributeName =:attributeName ");
				params.put("attributeName", deviceattributehistoryHouseidYear.getAttributeNameCn());
			}else {
				sql.append(" and b.attributeNameCn =:attributeNameCn ");
				params.put("attributeNameCn", deviceattributehistoryHouseidYear.getAttributeNameCn());
			}
			
		}	
		/*if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getAttributeName()) && !deviceattributehistoryHouseidYear.getAttributeName().equals("-1")) {
			sql.append(" and b.attributeName =:attributeName ");
			params.put("attributeName", deviceattributehistoryHouseidYear.getAttributeNameCn());
		}	*/
		if (StringUtils.isNotBlank((String)deviceattributehistoryHouseidYear.getStarttime())) {
		    sql.append(" and t.opdatetime between '").append(deviceattributehistoryHouseidYear.getStarttime()).append("' and '").append(deviceattributehistoryHouseidYear.getEndtime()).append("'");
		}
//		sql.append(" and TO_DAYS(NOW()) - TO_DAYS(t.opdatetime) <= 1 order by opdatetime desc"); // 查询两天内的数据
//		sql.append(" order by opdatetime desc limit 500"); // 查询两天内的数据,限制500条数据
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy()))
			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
		else
			sql.append(" order by t.opdatetime desc "); // 查询两天内的数据,限制500条数据
		sql.append("limit ").append(deviceattributehistoryHouseidYear.getStartRow()).append(",").append(deviceattributehistoryHouseidYear.getPageSize());
		/*if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 500 "); // 查询两天内的数据,限制500条数据
		}*/
//		sql.append(" limit 200 "); // 查询两天内的数据,限制500条数据
		logger.info("输出设备查询语句sql=-==》："+sql);
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		
		if(list != null && list.size() > 0) {
			rt.put("listory",list);	
		} else
			rt.put("listory",null);
		
		return rt;
	}

	/*@Override
	public Map findList2(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getAttributeNameCn() != null) {
			sql.append("select t, d.attributeNameCn from ").append(tableName)
			.append(" t join attributenamelib d on t.attributeName = d.attributeName where 1=1 ");
			if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
				sql.append("and t.houseIeee = :houseIeee ");
				params.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
			}
			if (deviceattributehistoryHouseidYear.getRoomId() != 0) {
				sql.append("and t.room_Id = :roomId ");
				params.put("roomId", deviceattributehistoryHouseidYear.getRoomId());
			}
			if (deviceattributehistoryHouseidYear.getDeviceIeee() != null) {
				sql.append("and t.device_Ieee = :deviceIeee ");
				params.put("deviceIeee", deviceattributehistoryHouseidYear.getDeviceIeee());
			}
			if (deviceattributehistoryHouseidYear.getDeviceEp() != null) {
				sql.append("and t.device_Ep = :deviceEp ");
				params.put("deviceEp", deviceattributehistoryHouseidYear.getDeviceEp());
			}
			if (deviceattributehistoryHouseidYear.getClusterId() != null) {
				sql.append("and t.cluster_Id = :clusterId ");
				params.put("clusterId", deviceattributehistoryHouseidYear.getClusterId());
			}
			if (deviceattributehistoryHouseidYear.getAttributeId() != null) {
				sql.append("and t.attribute_Id = :attributeId ");
				params.put("attributeId", deviceattributehistoryHouseidYear.getAttributeId());
			}
			if (deviceattributehistoryHouseidYear.getAttributeName() != null) {
				sql.append("and t.attributeName like :attributeName ");
				params.put("attributeName", "%" + deviceattributehistoryHouseidYear.getAttributeName()  + "%");
			}
			if (deviceattributehistoryHouseidYear.getAttributeNameCn() != null) {
				sql.append("and d.attributeNameCn like :attributeNameCn ");
				params.put("attributeNameCn", "%" + deviceattributehistoryHouseidYear.getAttributeNameCn()  + "%");
			}
			if (deviceattributehistoryHouseidYear.getMinOpdatetime() != null) {
				sql.append("and t.opdatetime >= :minopdatetime ");
				params.put("minopdatetime", deviceattributehistoryHouseidYear.getMinOpdatetime());
			}
			if (deviceattributehistoryHouseidYear.getMaxOpdatetime() != null) {
				sql.append("and t.opdatetime <= :maxopdatetime ");
				params.put("maxopdatetime", deviceattributehistoryHouseidYear.getMaxOpdatetime());
			}	
//			sql.append(" and TO_DAYS(NOW()) - TO_DAYS(t.opdatetime) <= 1 order by opdatetime desc"); // 查询两天内的数据
//			sql.append(" order by opdatetime desc limit 500"); // 查询两天内的数据,限制500条数据
			if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy()))
				sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
			else
				sql.append(" order by opdatetime desc "); // 查询两天内的数据,限制500条数据
			if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
				sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
			} else {
				sql.append(" limit 500 "); // 查询两天内的数据,限制500条数据
			}
		} else {
			sql.append("select m,d.attributeNameCn from (select t from ").append(tableName)
			.append(" t where 1=1 ");
			if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
				sql.append("and t.houseIeee = :houseIeee ");
				params.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
			}
			if (deviceattributehistoryHouseidYear.getRoomId() != 0) {
				sql.append("and t.room_Id = :roomId ");
				params.put("roomId", deviceattributehistoryHouseidYear.getRoomId());
			}
			if (deviceattributehistoryHouseidYear.getDeviceIeee() != null) {
				sql.append("and t.device_Ieee = :deviceIeee ");
				params.put("deviceIeee", deviceattributehistoryHouseidYear.getDeviceIeee());
			}
			if (deviceattributehistoryHouseidYear.getDeviceEp() != null) {
				sql.append("and t.device_Ep = :deviceEp ");
				params.put("deviceEp", deviceattributehistoryHouseidYear.getDeviceEp());
			}
			if (deviceattributehistoryHouseidYear.getClusterId() != null) {
				sql.append("and t.cluster_Id = :clusterId ");
				params.put("clusterId", deviceattributehistoryHouseidYear.getClusterId());
			}
			if (deviceattributehistoryHouseidYear.getAttributeId() != null) {
				sql.append("and t.attribute_Id = :attributeId ");
				params.put("attributeId", deviceattributehistoryHouseidYear.getAttributeId());
			}
			if (deviceattributehistoryHouseidYear.getAttributeName() != null) {
				sql.append("and t.attributeName like :attributeName ");
				params.put("attributeName", "%" + deviceattributehistoryHouseidYear.getAttributeName()  + "%");
			}
			if (deviceattributehistoryHouseidYear.getMinOpdatetime() != null) {
				sql.append("and t.opdatetime >= :minopdatetime ");
				params.put("minopdatetime", deviceattributehistoryHouseidYear.getMinOpdatetime());
			}
			if (deviceattributehistoryHouseidYear.getMaxOpdatetime() != null) {
				sql.append("and t.opdatetime <= :maxopdatetime ");
				params.put("maxopdatetime", deviceattributehistoryHouseidYear.getMaxOpdatetime());
			}	
//			sql.append(" and TO_DAYS(NOW()) - TO_DAYS(t.opdatetime) <= 1 order by opdatetime desc"); // 查询两天内的数据
//			sql.append(" order by opdatetime desc limit 500"); // 查询两天内的数据,限制500条数据
			if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy()))
				sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
			else
				sql.append(" order by opdatetime desc "); // 查询两天内的数据,限制500条数据
			if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
				sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
			} else {
				sql.append(" limit 500 "); // 查询两天内的数据,限制500条数据
			}
			sql.append(" ) m join attributenamelib d on m.attributeName = d.attributeName "); // 查询两天内的数据,限制500条数据
		}
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		
		if(list != null && list.size() > 0) {
			rt.put("listory",list);	
		} else
			rt.put("listory",null);
		
		return rt;
	}*/
	
	@Override
	public int getListCount(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
		if(!deviceattributehistoryHouseidYear.getHouseIeee().equals(deviceattributehistoryHouseidYear.getDeviceIeee())) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> params = new HashMap<String, Object>();
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer hww = new StringBuffer();
		Map<String, Object> ppt = new HashMap<String, Object>();
		hww.append("SELECT  isTableExist('"+ tableName +"') as sun  "); 		
 		List<Map> l = mapDao.executeSql(hww.toString()); 
 		String tt = l.get(0).get("sun") .toString();
		if(tt.equals("1")){
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) total from ").append(tableName).append(" t ");
		sql.append(" left join  attributenamelib b on t.cluster_id = b.clusterID and t.attribute_id = b.attrID ");
		sql.append("where t.houseIeee =:houseIeee and t.device_Ieee =:deviceIeee and t.device_Ep =:deviceEp ");
//		if(deviceattributehistoryHouseidYear.getAttributeNameCn() != null) {
//			 sql.append("and b.attributeNameCn like :attributeNameCn ");
//			 params.put("attributeNameCn", "%" + deviceattributehistoryHouseidYear.getAttributeNameCn()  + "%");
//		}
		if(deviceattributehistoryHouseidYear.getAttributeNameCn().equals("lineState"))
		{
			sql.append("and t.attributeName = :attributeNameCn");
			params.put("attributeNameCn", "isonline");
		}
		else if(deviceattributehistoryHouseidYear.getAttributeNameCn().equals("actionState"))
		{
			sql.append("and t.attributeName != :attributeNameCn");
			params.put("attributeNameCn", "isonline");
		}
		params.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		params.put("deviceIeee", deviceattributehistoryHouseidYear.getDeviceIeee());
		params.put("deviceEp", deviceattributehistoryHouseidYear.getDeviceEp());
		
		if (deviceattributehistoryHouseidYear.getRoomId() != 0) {
			sql.append("and t.room_Id = :roomId ");
			params.put("roomId", deviceattributehistoryHouseidYear.getRoomId());
		}
			
		if (deviceattributehistoryHouseidYear.getClusterId() != null) {
			sql.append("and t.cluster_Id = :clusterId ");
			params.put("clusterId", deviceattributehistoryHouseidYear.getClusterId());
		}
		if (deviceattributehistoryHouseidYear.getAttributeId() != null) {
			sql.append("and t.attribute_Id = :attributeId ");
			params.put("attributeId", deviceattributehistoryHouseidYear.getAttributeId());
		}
		/*if (deviceattributehistoryHouseidYear.getAttributeName() != null) {
			sql.append("and t.attributeName like :attributeName ");
			params.put("attributeName", "%" + deviceattributehistoryHouseidYear.getAttributeName()  + "%");
		}*/
		if (deviceattributehistoryHouseidYear.getAttributeName() != null) {
			sql.append("and t.attributeName = :attributeName ");
			params.put("attributeName",  deviceattributehistoryHouseidYear.getAttributeName());
		}
//		if (deviceattributehistoryHouseidYear.getAttributeNameCn() != null) {
//			sql.append("and d.attributeNameCn like :attributeNameCn ");
//			params.put("attributeNameCn", "%" + deviceattributehistoryHouseidYear.getAttributeNameCn()  + "%");
//		}
		//String da1 = deviceattributehistoryHouseidYear.get
		if (deviceattributehistoryHouseidYear.getMinOpdatetime() != null) {
			sql.append("and t.opdatetime >= :minopdatetime ");
			params.put("minopdatetime", deviceattributehistoryHouseidYear.getMinOpdatetime());
		}
		if (deviceattributehistoryHouseidYear.getMaxOpdatetime() != null) {
			sql.append("and t.opdatetime <= :maxopdatetime ");
			params.put("maxopdatetime", deviceattributehistoryHouseidYear.getMaxOpdatetime());
		}	
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getAttributeNameCn()) && !deviceattributehistoryHouseidYear.getAttributeNameCn().equals("-1")) {
			if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getLang()) && deviceattributehistoryHouseidYear.getLang().equals("1")){
				sql.append(" and b.attributeName =:attributeName ");
				params.put("attributeName", deviceattributehistoryHouseidYear.getAttributeNameCn());
			}else {
				sql.append(" and b.attributeNameCn =:attributeNameCn ");
				params.put("attributeNameCn", deviceattributehistoryHouseidYear.getAttributeNameCn());
			}
			
		}	
		/*if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getAttributeNameCn()) && !deviceattributehistoryHouseidYear.getAttributeNameCn().equals("-1") ) {
			sql.append(" and b.attributeNameCn =:attributeNameCn ");
			params.put("attributeNameCn", deviceattributehistoryHouseidYear.getAttributeNameCn());
		}
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getAttributeName()) && deviceattributehistoryHouseidYear.getAttributeName().equals("-1")) {
			sql.append(" and b.attributeName =:attributeName ");
			params.put("attributeName", deviceattributehistoryHouseidYear.getAttributeName());
		}*/
		if (StringUtils.isNotBlank((String)deviceattributehistoryHouseidYear.getStarttime())) {
		    sql.append(" and t.opdatetime between '").append(deviceattributehistoryHouseidYear.getStarttime()).append("' and '").append(deviceattributehistoryHouseidYear.getEndtime()).append("'");
		}
		logger.info("输出的设备查询总数的sql语句--》："+ sql);
		List<Map> totalList = mapDao.executeSql(sql.toString(), params);
		return ((BigInteger) ((Map) totalList.get(0)).get("total")).intValue();
		}
		}
		return 0;
	}
/**
 * 修改device表中的deviceattribute字段
 */
//	@Override
//	public int updateAttrnameValue(
//			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) {
//		String sql="update device set deviceattribute=:deviceattribute where houseIEEE=:houseIEEE and ep=:device_ep and ieee=:device_ieee";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("deviceattribute",deviceattributehistoryHouseidYear.getAttributeName()+" : "+deviceattributehistoryHouseidYear.getValue());
//		params.put("houseIEEE",deviceattributehistoryHouseidYear.getHouseIeee());
//		params.put("device_ep",deviceattributehistoryHouseidYear.getDeviceEp());
//		params.put("device_ieee",deviceattributehistoryHouseidYear.getDeviceIeee());
//		return deviceattributehistoryHouseidYearDao.executeSql2(sql, params);
//	}

	@Override
	public Map getHeartbeat(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap) {
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select h.name,d.deviceName,t.pushCount,tSum.*,t.attributeName from (")
			.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,Count(*) total from  ")
			.append(tableName)
			.append(" m USE INDEX(cluster_attr_opdatetime_index) where cluster_id='0500' and attribute_id='0002' ");
			getRange(paramMap, sql);
			sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
			.append(" LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
			
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
			
		}
		
		sql.append(" and t.attributeName is not null ");
		
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
		}
		else {
			sql.append(" order by tsum.device_ieee desc ");
		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		logger.info("getHeartbeat-->"+sql);
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		if(t != null && t.size() > 0) {
			rt.put("listory",t);	
		} else
			rt.put("listory",null);
		
		return rt;
	}

	@Override
	public Map getElectricityRelated(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,
			Map<String, Object> paramMap) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select h.name,d.deviceName,t.pushCount,t.attributeName attributeNameCn,tSum.* from (")
		.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,attributeName,Count(*) total from  ")
		.append(tableName)
		.append(" m USE INDEX(cluster_attr_opdatetime_index) where  ((cluster_id='0006' and attribute_id='0000') or " +
				"(cluster_id='0405' and attribute_id='0000') or " +
				"(cluster_id='0402' and attribute_id='0000') or " +
				"(cluster_id='0406' and attribute_id='0000') or " +
				"(cluster_id='0400' and attribute_id='0000') or " +
				"(cluster_id='0702' and attribute_id='E000') or " +
				"(cluster_id='0702' and attribute_id='E001') or " +
				"(cluster_id='0702' and attribute_id='E002') or " +
				"(cluster_id='0702' and attribute_id='E003') or " +
				"(cluster_id='0008' and attribute_id='0000'))  ");
		getRange(paramMap, sql);
		sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
		.append(" LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
			
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
		}
		
		sql.append(" and t.attributeName is not null ");
		
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
		}
		else {
			sql.append(" order by tsum.device_ieee desc ");
		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		logger.info("ElectricityRelated---->"+sql);
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		if(t != null && t.size() > 0) {
			rt.put("listory",t);	
		} else
			rt.put("listory",null);
		
		return rt;
	}

	@Override
	public Map getBatteryVoltage(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,
			Map<String, Object> paramMap) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select h.name,d.deviceName,t.pushCount,tSum.*,t.attributeName from (")
		.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,Count(*) total from  ")
		.append(tableName)
		.append(" m USE INDEX(cluster_attr_opdatetime_index) where cluster_id='0001' and attribute_id='0020' ");
		getRange(paramMap, sql);
		sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
		.append("LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep ")
		.append("LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
		}
	
		sql.append(" and t.attributeName is not null ");
		
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
		}
		else {
			sql.append(" order by tsum.device_ieee desc ");
		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		logger.info("BatteryVoltage---->"+sql);
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		if(t != null && t.size() > 0) {
			rt.put("listory",t);	
		} else
			rt.put("listory",null);
		
		return rt;
	}

	@Override
	public Map getHeartbeatCount(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,Map<String, Object> paramMap) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT COUNT(*) total FROM ( ");
		sql.append("select h.name,d.deviceName,t.pushCount,tSum.* from (")
			.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,attributeName,Count(*) total from  ")
			.append(tableName)
			.append(" m USE INDEX(cluster_attr_opdatetime_index) where cluster_id='0500' and attribute_id='0002' ");
			getRange(paramMap, sql);
			sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
			.append(" LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
			
		}
		
		sql.append(" and t.attributeName is not null ");
		
//		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
//			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
//		}
//		else {
//			sql.append(" order by tsum.device_ieee desc ");
//		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		sql.append(" ) a");
		logger.info("HeartbeatCount---->"+sql);
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		if(t != null && t.size() > 0) {
			rt.put("listory",t);	
		} else
			rt.put("listory",null);
		
		return rt;
	}

	@Override
	public Map getElectricityRelatedCount(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,
			Map<String, Object> paramMap) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select count(*) as total from ( ");
		sql.append("select h.name,d.deviceName,t.pushCount,tSum.attributeName attributeNameCn,tSum.* from (")
		.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,attributeName,Count(*) total from  ")
		.append(tableName)
		.append(" m USE INDEX(cluster_attr_opdatetime_index) where  ((cluster_id='0006' and attribute_id='0000') or " +
				"(cluster_id='0405' and attribute_id='0000') or " +
				"(cluster_id='0402' and attribute_id='0000') or " +
				"(cluster_id='0406' and attribute_id='0000') or " +
				"(cluster_id='0400' and attribute_id='0000') or " +
				"(cluster_id='0702' and attribute_id='E000') or " +
				"(cluster_id='0702' and attribute_id='E001') or " +
				"(cluster_id='0702' and attribute_id='E002') or " +
				"(cluster_id='0702' and attribute_id='E003') or " +
				"(cluster_id='0008' and attribute_id='0000'))  ");
		getRange(paramMap, sql);
		sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
		.append(" LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
			
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
			
		}
		
		sql.append(" and t.attributeName is not null ");
		
//		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
//			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
//		}
//		else {
//			sql.append(" order by tsum.device_ieee desc ");
//		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		sql.append(" ) a");
		logger.info("ElectricityRelatedCount-------->"+sql);
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		if(t != null && t.size() > 0) {
			rt.put("listory",t);	
		} else
			rt.put("listory",null);
		
		return rt;
	}

	@Override
	public Map getBatteryVoltageCount(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,
			Map<String, Object> paramMap) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select count(*) as total from ( ");
		sql.append("select h.name,d.deviceName,t.pushCount,tSum.* from (")
		.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,attributeName,Count(*) total from  ")
		.append(tableName)
		.append(" m USE INDEX(cluster_attr_opdatetime_index) where cluster_id='0001' and attribute_id='0020' ");
		getRange(paramMap, sql);
		sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
		.append(" LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
		}
		
		sql.append(" and t.attributeName is not null ");
		
//		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
//			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
//		}
//		else {
//			sql.append(" order by tsum.device_ieee desc ");
//		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		sql.append(" ) a ");
		logger.info("BatteryVoltageCount-------->"+sql);
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		if(t != null && t.size() > 0) {
			rt.put("listory",t);	
		} else
			rt.put("listory",null);
		
		return rt;
	}
	
	public StringBuffer getRange(Map<String, Object> rangeMap,StringBuffer sql){
		Date t1 = new Date();
		if(rangeMap.get("fanwei").equals("today")){
			//日期转换成字符串：  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append(" and m.opdatetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(rangeMap.get("fanwei").equals("yesterday")){
			//日期转换成字符串：  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append(" and m.opdatetime between '").append(strcal).append("' and '").append(str).append("'");
		}
		if(rangeMap.get("fanwei").equals("nearseven")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.DAY_OF_MONTH, -7);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append(" and m.opdatetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(rangeMap.get("fanwei").equals("nearmouth")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -1);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append(" and m.opdatetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		
		if(rangeMap.get("fanwei").equals("bannian")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -6);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append(" and m.opdatetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		
		if(rangeMap.get("fanwei").equals("yinian")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -12);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append(" and m.opdatetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		return sql;
	}
	
	@Override
	public int exportHeartbeatExcel(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,
			Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select h.name,d.deviceName,t.pushCount,tSum.* from (")
		.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,attributeName,Count(*) total from  ")
		.append(tableName)
		.append(" m where cluster_id='0500' and attribute_id='0002' ");
		getRange(paramMap, sql);
		sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
		.append(" LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
		
	
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
			
		}
		
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
		}
		else {
			sql.append(" order by tsum.device_ieee desc ");
		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		
		logger.info("export heartbeat:" + sql);
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		BufferedOutputStream os = null;
		try {
		//生成excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		workbook.setSheetName(0, "心跳统计");
		HSSFRow row = sheet.createRow((short)0);
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		HSSFFont titleFont = workbook.createFont();
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleStyle.setFont(titleFont);
		HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("主管");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
		cell.setCellValue("IEEE地址");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备名称");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备IEEE");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备EP");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
		cell.setCellValue("动作ID");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
		cell.setCellValue("属性ID");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
		cell.setCellValue("属性名称");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
		cell.setCellValue("推送次数");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
		cell.setCellValue("记录次数");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
		cell.setCellValue("误差次数");
		cell.setCellStyle(titleStyle);
		for(int i = 0; i < t.size(); i++) {
			
			row = sheet.createRow((short)(i + 1));
			
			cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("name") == null? "" : t.get(i).get("name").toString());
			
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("houseieee") == null? "" : t.get(i).get("houseieee").toString());
			
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
			
			
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("device_ieee") == null? "" : t.get(i).get("device_ieee").toString());
			
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("device_ep") == null? "" : t.get(i).get("device_ep").toString());
			
			cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("cluster_id") == null? "" : t.get(i).get("cluster_id").toString());
			
			cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("attribute_id") == null? "" : t.get(i).get("attribute_id").toString());
			
			cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("attributeName") == null? "" : t.get(i).get("attributeName").toString());
			
			cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
		
			Date t1 = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(t1);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			String str=sdf.format(calendar.getTime()); 
			calendar = Calendar.getInstance();	
			
			int pushCount =0;
			if(paramMap.get("fanwei").equals("today")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if(pCount == null){
					cell.setCellValue(pushCount);
				}else{
					pushCount = pCount.intValue();
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("yesterday")){
				int yesterday = 1;
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if(pCount == null){
					cell.setCellValue(pushCount);
				}else{
					pushCount = pCount.intValue() * yesterday;
					cell.setCellValue(pushCount + "");
				}
			}			
			else if (paramMap.get("fanwei").equals("nearseven")) {
				int nearseven = 7;
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if (pCount == null) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount.intValue() * nearseven;
					cell.setCellValue(pushCount + "");
				}
			}
			else if (paramMap.get("fanwei").equals("nearmouth")) {
				Integer pCount = (Integer) t.get(i).get("pushCount");
				calendar.setTime(t1);
				calendar.add(Calendar.MONTH, -1);
				String strcal=sdf.format(calendar.getTime());
				Date tt = sdf.parse(strcal);
				Date tt2 = sdf.parse(str);
				int nearmouth = (int)((tt2.getTime()-tt.getTime()-1)/(1000*60*60*24)) ;
				if (pCount == null) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * nearmouth;
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("bannian")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
				calendar.setTime(t1);
				calendar.add(Calendar.MONTH, -6);
				String strcal=sdf.format(calendar.getTime());
				Date tt = sdf.parse(strcal);	
				Date tt2 = sdf.parse(str);
				int bannian = (int)((tt2.getTime()-tt.getTime()-1)/(1000*60*60*24)) ;
			    if (pCount == null ) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * bannian;
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("yinian")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
				//calendar = Calendar.getInstance();
				calendar.setTime(t1);
				int yinian = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
				if (pCount == null ) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * yinian;
					cell.setCellValue(pushCount + "");
				}
			}			
			cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("total") == null? "" : t.get(i).get("total").toString());
			
			cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
			
			int cloudCount = Integer.parseInt(t.get(i).get("total") == null? "0" : t.get(i).get("total").toString());
			int push_sum = pushCount-cloudCount;
			cell.setCellValue(push_sum);
		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
//		String datestr = sdf.format(new Date());
//		String fileName = "心跳统计"+datestr+".xls";		
		String fileName = new String("心跳统计.xls".getBytes("gb2312"), "ISO8859-1" );
		response.addHeader("Content-Disposition", "attachment;filename="+fileName);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		os = new BufferedOutputStream(response.getOutputStream());
		workbook.write(os);
		os.flush();
	} catch (Exception e) {
		throw e;
	} finally {
		try {
			if(os != null)
				os.close();
		} catch(Exception e) {
			throw e;
		}
	}
		return 1;
	}

	@Override
	public int exportElecExcel(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,
			Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select h.name,d.deviceName,t.pushCount,t.attributeName attributeNameCn,tSum.* from (")
		.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,attributeName,Count(*) total from  ")
		.append(tableName)
		.append(" m where  ((cluster_id='0006' and attribute_id='0000') or " +
				"(cluster_id='0405' and attribute_id='0000') or " +
				"(cluster_id='0402' and attribute_id='0000') or " +
				"(cluster_id='0406' and attribute_id='0000') or " +
				"(cluster_id='0400' and attribute_id='0000') or " +
				"(cluster_id='0702' and attribute_id='E000') or " +
				"(cluster_id='0702' and attribute_id='E001') or " +
				"(cluster_id='0702' and attribute_id='E002') or " +
				"(cluster_id='0702' and attribute_id='E003') or " +
				"(cluster_id='0008' and attribute_id='0000'))  ");
		getRange(paramMap, sql);
		sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
		.append(" LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
			
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
			
		}
		
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
		}
		else {
			sql.append(" order by tsum.device_ieee desc ");
		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		
		logger.info("export elec:" + sql);
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		BufferedOutputStream os = null;
		try {
		//生成excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		workbook.setSheetName(0, "电能相关统计");
		HSSFRow row = sheet.createRow((short)0);
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		HSSFFont titleFont = workbook.createFont();
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleStyle.setFont(titleFont);
		HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("主管");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
		cell.setCellValue("IEEE地址");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备名称");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备IEEE");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备EP");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
		cell.setCellValue("动作ID");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
		cell.setCellValue("属性ID");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
		cell.setCellValue("属性名称");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
		cell.setCellValue("推送次数");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
		cell.setCellValue("记录次数");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
		cell.setCellValue("误差次数");
		cell.setCellStyle(titleStyle);

		for(int i = 0; i < t.size(); i++) {
			
			row = sheet.createRow((short)(i + 1));
			
			cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("name") == null? "" : t.get(i).get("name").toString());
			
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("houseieee") == null? "" : t.get(i).get("houseieee").toString());
			
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
			
			
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("device_ieee") == null? "" : t.get(i).get("device_ieee").toString());
			
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("device_ep") == null? "" : t.get(i).get("device_ep").toString());
			
			cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("cluster_id") == null? "" : t.get(i).get("cluster_id").toString());
			
			cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("attribute_id") == null? "" : t.get(i).get("attribute_id").toString());
			
			cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("attributeNameCn") == null? "" : t.get(i).get("attributeNameCn").toString());
			cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
			
			Date t1 = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(t1);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			String str=sdf.format(calendar.getTime()); 
			calendar = Calendar.getInstance();	
			
			int pushCount =0;
			if(paramMap.get("fanwei").equals("today")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if(pCount == null){
					cell.setCellValue(pushCount);
				}else{
					pushCount = pCount.intValue();
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("yesterday")){
				int yesterday = 1;
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if(pCount == null){
					cell.setCellValue(pushCount);
				}else{
					pushCount = pCount.intValue() * yesterday;
					cell.setCellValue(pushCount + "");
				}
			}			
			else if (paramMap.get("fanwei").equals("nearseven")) {
				int nearseven = 7;
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if (pCount == null) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount.intValue() * nearseven;
					cell.setCellValue(pushCount + "");
				}
			}
			else if (paramMap.get("fanwei").equals("nearmouth")) {
				Integer pCount = (Integer) t.get(i).get("pushCount");
				calendar.setTime(t1);
				calendar.add(Calendar.MONTH, -1);
				String strcal=sdf.format(calendar.getTime());
				Date tt = sdf.parse(strcal);
				Date tt2 = sdf.parse(str);
				int nearmouth = (int)((tt2.getTime()-tt.getTime()-1)/(1000*60*60*24)) ;
				if (pCount == null) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * nearmouth;
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("bannian")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
				calendar.setTime(t1);
				calendar.add(Calendar.MONTH, -6);
				String strcal=sdf.format(calendar.getTime());
				Date tt = sdf.parse(strcal);	
				Date tt2 = sdf.parse(str);
				int bannian = (int)((tt2.getTime()-tt.getTime()-1)/(1000*60*60*24)) ;
			    if (pCount == null ) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * bannian;
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("yinian")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
			    calendar = Calendar.getInstance();
				int yinian = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
				if (pCount == null ) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * yinian;
					cell.setCellValue(pushCount + "");
				}
			}			
			
			cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("total") == null? "" : t.get(i).get("total").toString());
			
			cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
			
			int cloudCount = Integer.parseInt(t.get(i).get("total") == null? "0" : t.get(i).get("total").toString());
			int push_sum = pushCount-cloudCount;
			cell.setCellValue(push_sum);
			
			
		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
//		String datestr = sdf.format(new Date());
//		String fileName = "电能相关统计"+datestr+".xls";		
		String fileName = new String("电能相关统计.xls".getBytes("gb2312"), "ISO8859-1" );
		response.addHeader("Content-Disposition", "attachment;filename="+fileName);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		os = new BufferedOutputStream(response.getOutputStream());
		workbook.write(os);
		os.flush();
	} catch (Exception e) {
		throw e;
	} finally {
		try {
			if(os != null)
				os.close();
		} catch(Exception e) {
			throw e;
		}
	}
		return 1;
	}

	@Override
	public int exportBattExcel(
			DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear,
			Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		
		sql.append("select h.name,d.deviceName,t.pushCount,tSum.* from (")
		.append(" SELECT houseieee,device_ieee,device_ep,cluster_id,attribute_id,attributeName,Count(*) total from  ")
		.append(tableName)
		.append(" m where cluster_id='0001' and attribute_id='0020' ");
		getRange(paramMap, sql);
		sql.append(" GROUP BY device_ieee,device_ep,cluster_id,attribute_id) tsum ")
		.append(" LEFT JOIN  house h on tsum.houseieee=h.houseieee LEFT JOIN device d on tsum.houseieee=d.houseieee and tsum.device_ieee=d.ieee and tsum.device_ep=d.ep LEFT JOIN deviceattributehistorydictionary t on d.modelId = t.modelId and tsum.cluster_id =t.cluster_id and tsum.attribute_id=t.attribute_id ");
		
	
		Map<String, Object> param = new HashMap<String, Object>();
		if (deviceattributehistoryHouseidYear.getHouseIeee() != null) {
			sql.append(" where tsum.houseIeee = :houseIeee ");
			param.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		}
		
		
		Map<String, Object> rt = new HashMap<String, Object>();
		String sText = (String) paramMap.get("sText");
		if(StringUtils.isNotBlank(sText)){
			sql.append(" and (tsum.houseIeee like :sText ");
			sql.append("or tsum.device_ieee like :sText ");
			sql.append("or h.name like :sText ");
			sql.append("or d.deviceName like :sText ");
			sql.append("or t.pushCount like :sText ");
			sql.append("or tsum.device_ep like :sText ");
			sql.append("or tsum.attributeName like :sText ");
			sql.append("or tsum.cluster_id like :sText ");
			sql.append("or tsum.attribute_id like :sText) ");
			param.put("sText", "%"+sText+"%");
			
		}
	
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getOrderBy())){
			sql.append(" order by ").append(deviceattributehistoryHouseidYear.getOrderBy()).append(" ");
		}
		else {
			sql.append(" order by tsum.device_ieee desc ");
		}
		
		if (deviceattributehistoryHouseidYear.getPageSize() > 0 ) {
			sql.append(" limit " + deviceattributehistoryHouseidYear.getStartRow() + "," + deviceattributehistoryHouseidYear.getPageSize());
		} else {
			sql.append(" limit 1000 "); 
		}
		logger.info("export BatteryVoltage---->"+sql);
		
		List<Map> t = mapDao.executeSql(sql.toString(), param);
		BufferedOutputStream os = null;
		try {
		//生成excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		workbook.setSheetName(0, "电池电压统计");
		HSSFRow row = sheet.createRow((short)0);
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		HSSFFont titleFont = workbook.createFont();
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleStyle.setFont(titleFont);
		HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("主管");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
		cell.setCellValue("IEEE地址");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备名称");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备IEEE");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
		cell.setCellValue("设备EP");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
		cell.setCellValue("动作ID");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
		cell.setCellValue("属性ID");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
		cell.setCellValue("属性名称");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
		cell.setCellValue("推送次数");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
		cell.setCellValue("记录次数");
		cell.setCellStyle(titleStyle);
		cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
		cell.setCellValue("误差次数");
		cell.setCellStyle(titleStyle);

		for(int i = 0; i < t.size(); i++) {
			
			row = sheet.createRow((short)(i + 1));
			
			cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("name") == null? "" : t.get(i).get("name").toString());
			
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("houseieee") == null? "" : t.get(i).get("houseieee").toString());
			
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
			
			
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("device_ieee") == null? "" : t.get(i).get("device_ieee").toString());
			
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue(t.get(i).get("device_ep") == null? "" : t.get(i).get("device_ep").toString());
			
			cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("cluster_id") == null? "" : t.get(i).get("cluster_id").toString());
			
			cell = row.createCell((short)6, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("attribute_id") == null? "" : t.get(i).get("attribute_id").toString());
			
			cell = row.createCell((short)7, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("attributeName") == null? "" : t.get(i).get("attributeName").toString());
			
			cell = row.createCell((short)8, Cell.CELL_TYPE_STRING);
			
			 Date t1 = new Date();
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(t1);
			 calendar.add(Calendar.DAY_OF_MONTH, 1);
			 String str=sdf.format(calendar.getTime()); 
			 calendar = Calendar.getInstance();	
			
			int pushCount =0;
			if(paramMap.get("fanwei").equals("today")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if(pCount == null){
					cell.setCellValue(pushCount);
				}else{
					pushCount = pCount.intValue();
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("yesterday")){
				int yesterday = 1;
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if(pCount == null){
					cell.setCellValue(pushCount);
				}else{
					pushCount = pCount.intValue() * yesterday;
					cell.setCellValue(pushCount + "");
				}
			}			
			else if (paramMap.get("fanwei").equals("nearseven")) {
				int nearseven = 7;
				Integer pCount = (Integer) t.get(i).get("pushCount");
				if (pCount == null) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount.intValue() * nearseven;
					cell.setCellValue(pushCount + "");
				}
			}
			else if (paramMap.get("fanwei").equals("nearmouth")) {
				Integer pCount = (Integer) t.get(i).get("pushCount");
				calendar.setTime(t1);
				calendar.add(Calendar.MONTH, -1);
				String strcal=sdf.format(calendar.getTime());
				Date tt = sdf.parse(strcal);
				Date tt2 = sdf.parse(str);
				int nearmouth = (int)((tt2.getTime()-tt.getTime()-1)/(1000*60*60*24)) ;
				if (pCount == null) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * nearmouth;
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("bannian")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
				calendar.setTime(t1);
				calendar.add(Calendar.MONTH, -6);
				String strcal=sdf.format(calendar.getTime());
				Date tt = sdf.parse(strcal);	
				Date tt2 = sdf.parse(str);
				int bannian = (int)((tt2.getTime()-tt.getTime()-1)/(1000*60*60*24)) ;
			    if (pCount == null ) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * bannian;
					cell.setCellValue(pushCount + "");
				}
			}
			else if(paramMap.get("fanwei").equals("yinian")){
				Integer pCount = (Integer) t.get(i).get("pushCount");
			    calendar = Calendar.getInstance();
				int yinian = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
				if (pCount == null ) {
					cell.setCellValue(pushCount);
				} else {
					pushCount = pCount * yinian;
					cell.setCellValue(pushCount + "");
				}
			}			
			cell = row.createCell((short)9, Cell.CELL_TYPE_STRING);
			cell.setCellValue( t.get(i).get("total") == null? "" : t.get(i).get("total").toString());
			
			cell = row.createCell((short)10, Cell.CELL_TYPE_STRING);
			
			int cloudCount = Integer.parseInt(t.get(i).get("total") == null? "0" : t.get(i).get("total").toString());
			int push_sum = pushCount-cloudCount;
			cell.setCellValue(push_sum);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
//		String datestr = sdf.format(new Date()).replace("-", "");
//		StringBuilder fileName1 = new StringBuilder("电池电压统计");
//		fileName1.append(datestr).append(".xls");		
		String fileName = new String("电池电压统计.xls".toString().getBytes("gb2312"), "ISO8859-1" );
		response.addHeader("Content-Disposition", "attachment;filename="+fileName);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		os = new BufferedOutputStream(response.getOutputStream());
		workbook.write(os);
		os.flush();
	} catch (Exception e) {
		throw e;
	} finally {
		try {
			if(os != null)
				os.close();
		} catch(Exception e) {
			throw e;
		}
	}
		return 1;
	}
/**
 * 查询对应houseieee的serverIP
 */
	@Override
	public List<Map> findServerIp(Map<String, Object> params) {
		//获取云服务器地址（只获取端口为8081的ip）
		String sql = "select serverIp from proxyserver where houseIEEE =:houseIEEE and type='1'";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("houseIEEE", params.get("houseIEEE"));
		List<Map> list = mapDao.executeSql(sql,map);
		return list;
	}
	@Override
	public List<Map> attributeState(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> params = new HashMap<String, Object>();
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer hww = new StringBuffer();
		Map<String, Object> ppt = new HashMap<String, Object>();
		hww.append("SELECT  isTableExist('"+ tableName +"') as sun  "); 		
 		List<Map> l = mapDao.executeSql(hww.toString()); 
 		String tt = l.get(0).get("sun") .toString();
 		if(tt.equals("1")){
 			StringBuffer sql = new StringBuffer();
 			sql.append("select DISTINCT b.attributeNameCn,b.attributeName from ").append(tableName).append(" t ");
 				sql.append(" left join  attributenamelib b on t.cluster_id = b.clusterID and t.attribute_id = b.attrID ");
 				sql.append(" where 1=1 and b.attributeNameCn <>'null' ");
 				if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getHouseIeee())) {
 					sql.append(" and t.houseIeee =:houseIeee");
 					params.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
 				}
 				if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getDeviceIeee())) {
 					sql.append(" and t.device_Ieee =:device_Ieee");
 					params.put("device_Ieee", deviceattributehistoryHouseidYear.getDeviceIeee());
 				}
 				if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getDeviceEp())) {
 					sql.append(" and t.device_Ep =:deviceEp");
 					params.put("deviceEp", deviceattributehistoryHouseidYear.getDeviceEp());
 				}
 			List<Map> list = mapDao.executeSql(sql.toString(), params);
 			if(list != null) {
 				return list;
 			}else{
 				return null;
 			}	
 		}
 			return null;		
	}

	@Override
	public int exportAttrLogExcel(DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear, HttpServletRequest request, HttpServletResponse response) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String tableName = "deviceAttributeHistory_" + deviceattributehistoryHouseidYear.getHouseIeee() + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select t.houseIeee,t.id,t.opdatetime,t.attributeName,t.value,t.cluster_id,t.attribute_id,b.attributeNameCn from ").append(tableName).append(" t ");
			sql.append(" left join  attributenamelib b on t.cluster_id = b.clusterID and t.attribute_id = b.attrID ");
			sql.append("where t.houseIeee = :houseIeee and t.device_Ieee = :deviceIeee and t.device_Ep = :deviceEp ");
		if(deviceattributehistoryHouseidYear.getAttributeNameCn().equals("lineState"))
		{
			sql.append("and t.attributeName = :attributeNameCn");
			params.put("attributeNameCn", "isonline");
		}
		else if(deviceattributehistoryHouseidYear.getAttributeNameCn().equals("actionState"))
		{
			sql.append("and t.attributeName != :attributeNameCn");
			params.put("attributeNameCn", "isonline");
		}
		
		params.put("houseIeee", deviceattributehistoryHouseidYear.getHouseIeee());
		params.put("deviceIeee", deviceattributehistoryHouseidYear.getDeviceIeee());
		params.put("deviceEp", deviceattributehistoryHouseidYear.getDeviceEp());
			
		if (deviceattributehistoryHouseidYear.getRoomId() != 0) {
			sql.append("and t.room_Id = :roomId ");
			params.put("roomId", deviceattributehistoryHouseidYear.getRoomId());
		}
			
		if (deviceattributehistoryHouseidYear.getClusterId() != null) {
			sql.append("and t.cluster_Id = :clusterId ");
			params.put("clusterId", deviceattributehistoryHouseidYear.getClusterId());
		}
		if (deviceattributehistoryHouseidYear.getAttributeId() != null) {
			sql.append("and t.attribute_Id = :attributeId ");
			params.put("attributeId", deviceattributehistoryHouseidYear.getAttributeId());
		}
		if (deviceattributehistoryHouseidYear.getAttributeName() != null) {
			sql.append("and t.attributeName = :attributeName ");
			params.put("attributeName",  deviceattributehistoryHouseidYear.getAttributeName());
		}
		if (deviceattributehistoryHouseidYear.getMinOpdatetime() != null) {
			sql.append("and t.opdatetime >= :minopdatetime ");
			params.put("minopdatetime", deviceattributehistoryHouseidYear.getMinOpdatetime());
		}
		if (deviceattributehistoryHouseidYear.getMaxOpdatetime() != null) {
			sql.append("and t.opdatetime <= :maxopdatetime ");
			params.put("maxopdatetime", deviceattributehistoryHouseidYear.getMaxOpdatetime());
		}
		if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getAttributeNameCn()) && !deviceattributehistoryHouseidYear.getAttributeNameCn().equals("-1")) {
			if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getLang()) && deviceattributehistoryHouseidYear.getLang().equals("1")){
				sql.append(" and b.attributeName =:attributeName ");
				params.put("attributeName", deviceattributehistoryHouseidYear.getAttributeNameCn());
			}else {
				sql.append(" and b.attributeNameCn =:attributeNameCn ");
				params.put("attributeNameCn", deviceattributehistoryHouseidYear.getAttributeNameCn());
			}
			
		}	
		/*if(StringUtils.isNotBlank(deviceattributehistoryHouseidYear.getAttributeNameCn()) && !deviceattributehistoryHouseidYear.getAttributeNameCn().equals("-1")) {
			sql.append(" and b.attributeNameCn =:attributeNameCn ");
			params.put("attributeNameCn", deviceattributehistoryHouseidYear.getAttributeNameCn());
		}	*/	
		if (StringUtils.isNotBlank((String)deviceattributehistoryHouseidYear.getStarttime())) {
		    sql.append(" and t.opdatetime between '").append(deviceattributehistoryHouseidYear.getStarttime()).append("' and '").append(deviceattributehistoryHouseidYear.getEndtime()).append("'");
		}
       // if(StringUtils.isBlank((String)deviceattributehistoryHouseidYear.getStarttime())&&StringUtils.isBlank((String)deviceattributehistoryHouseidYear.getEndtime())&&StringUtils.isBlank((String)deviceattributehistoryHouseidYear.getAttributeNameCn())){
		   sql.append(" limit 10000;");   
	 	//}
		
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		BufferedOutputStream os = null;
		try {
			if(deviceattributehistoryHouseidYear.getLang().equals("1")){
				//生成excel文件
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				sheet.setColumnWidth(1, 3500);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(5, 6000);
				workbook.setSheetName(0, "Device attribute report");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Report content");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Interval");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Time");
				cell.setCellStyle(titleStyle);
				int lqiSize = t.size();
				for(int i = 0; i < lqiSize; i++) {
					row = sheet.createRow(i + 1);
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					String attributeName = t.get(i).get("attributeName")==null?"None":t.get(i).get("attributeName").toString();
					if(attributeName.equals("heartbeat") || attributeName.equals("Zone_Status")){attributeName="Equipment capacity";}
					String clusterId = t.get(i).get("cluster_id").toString();
					String arr = t.get(i).get("value")==null?"0":t.get(i).get("value").toString();
					String attributename = t.get(i).get("attributeName")==null?"None":t.get(i).get("attributeName").toString();
					String value = "";
					if(clusterId.equals("0500")) {					 
						 String[] s = new String[5];
						 s = arr.split("");
						 int s1 = Integer.parseInt(s[1],16);int s2 = Integer.parseInt(s[2],16);int s3 = Integer.parseInt(s[3],16);int s4 = Integer.parseInt(s[4],16);
						 String[] arr1 = new String[5];String[] ss1 = new String[4]; 
						 int ll = Integer.parseInt(s[4],16);
						 String str2 = Integer.toBinaryString(ll);
						 arr1 = str2.split("");
						 if(arr1.length == 2){
								ss1[0]=ss1[1]=ss1[2]="0";
								ss1[3]=arr1[1];
							}
							if(arr1.length == 3){
								ss1[0]=ss1[1]="0";
								ss1[2]=arr1[1];
								ss1[3]=arr1[2];
							}
							if(arr1.length == 4) {
								ss1[0]="0";
								ss1[1]=arr1[1];ss1[2]=arr1[2];ss1[3]=arr1[3];
							}if(arr1.length == 5) {
								ss1[0]=arr1[1];ss1[1]=arr1[2];ss1[2]=arr1[3];ss1[3]=arr1[4];
							}
							String v11 = "";String v22 = "";String v33 = "";
							if(ss1[3].equals("1")) {v11="警报1";}
							if(ss1[2].equals("1")) {v22="警报2";}
							if(ss1[1].equals("1")) {v33="防拆";}
						 if(s[1].equals("0")&&s[2].equals("0")){
							 value = "正常";
						 }else {
						 if(s4 < 8 ){
							 int res = s1*16 + s2;
							 // value = "正常" +"(" + res + ")";  
							  if(!ss1[3].equals("1") && !ss1[1].equals("1") && !ss1[2].equals("1")){ value = res + "%";}
							  if(!ss1[3].equals("1") && ss1[1].equals("1") && !ss1[2].equals("1")){ value = res + "%" + "("+v33+")";}
							  if(!ss1[3].equals("1") && !ss1[1].equals("1") && ss1[2].equals("1")){ value = res + "%" + "("+v22+ ")";}
							  if(ss1[3].equals("1") && ss1[1].equals("1") && !ss1[2].equals("1")){ value = res + "%" + "("+v11+ "、"+v33+")";}
							  if(ss1[3].equals("1") && !ss1[1].equals("1") && ss1[2].equals("1")){ value = res + "%" + "("+v11+ "、"+v22+")";}
							  if(ss1[3].equals("1") && ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+v11+ "、"+v22+"、"+v33+")";}
							  if(!ss1[3].equals("1") && ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+v22+ "、"+v33+")";}
							  if(ss1[3].equals("1") && !ss1[1].endsWith("1") && !ss1[2].endsWith("1")){ value = res + "%" + "("+v11+")";}	
						 }else{
							 int res = s1*16 + s2;
							 // value = "低压" + "(" + res + ")";
							  if(!ss1[3].equals("1") && ss1[1]!="1" && !ss1[2].endsWith("1")){ value = res + "%"+"("+ "Weak alarm" +")";}
							  if(!ss1[3].equals("1") && ss1[1].endsWith("1") && !ss1[2].endsWith("1")){ value = res + "%" + "("+ "Weak alarm、" +v33+")";}
							  if(!ss1[3].equals("1") && ss1[1]!="1" && ss1[2].endsWith("1")){ value = res + "%" + "("+ "Weak alarm、"+v22+ ")";}
							  if(ss1[3].equals("1") && ss1[1].endsWith("1") && !ss1[2].endsWith("1")){ value = res + "%" + "("+ "Weak alarm、"+v11+ "、"+v33+")";}
							  if(ss1[3].equals("1") && !ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+ "Weak alarm、"+v11+ "、"+v22+")";}
							  if(ss1[3].equals("1") && ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+ "Weak alarm、"+v11+ "、"+v22+"、"+v33+")";}
							  if(!ss1[3].equals("1") && ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+ "Weak alarm、"+v22+ "、"+v33+")";}
							  if(ss1[3].equals("1") && !ss1[1].endsWith("1") && !ss1[2].endsWith("1")){ value = res + "%" + "("+ "Weak alarm、"+v11+")";}
						 }	
					 }
					}else {
						value = getAttributeValue(attributename,arr,deviceattributehistoryHouseidYear.getLang());
						// value = arr;
					}	
					cell.setCellValue(attributeName + ":" + value);
					cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
					String dat1 = "";
					String dat2 = "";
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					long times=0;
					if(i == 0) {
						 dat1 = t.get(i).get("opdatetime").toString();
						 dat2 = t.get(i).get("opdatetime").toString();
						 Date st1 = sd.parse(dat1);
						 Date st2 = sd.parse(dat2);
						 times = (st2.getTime() - st1.getTime())/1000;
					}else {
						 dat1 = t.get(i-1).get("opdatetime").toString();
						 dat2 = t.get(i).get("opdatetime").toString();
						 Date st1 = sd.parse(dat1);
						 Date st2 = sd.parse(dat2);
						 times = (st2.getTime() - st1.getTime())/1000;
					}
					String timesStr="Interval" + times + "s";			   
					if (times<60) {
					    timesStr="Interval" + times + "s";
					}
					if (times>=60 && times<3600) {
						//timesStr="间隔" + parseInt(times/60) + "min";
						timesStr="Interval" + (long)(times/60) + "min";
					}
					//2016.8.3 规定间隔时间不能超过24h ,超过24h 则直接按照24h计算
					if (times>=3600) {
						timesStr="Interval" + (long)(times/3600) + "h";
					}
					cell.setCellValue(timesStr);
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("opdatetime").toString());
					
				}
				//设置response参数
				//对中文字符转码
				String fileName = "Device attribute report.xls";		
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
				workbook.setSheetName(0, "属性报告列表");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("报告内容");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("间隔时间");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("时间");
				cell.setCellStyle(titleStyle);
				int lqiSize = t.size();
				for(int i = 0; i < lqiSize; i++) {
					row = sheet.createRow(i + 1);
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					String attributeName = t.get(i).get("attributeNameCn")==null?"无":t.get(i).get("attributeNameCn").toString();
					if(attributeName == "设备心跳"){attributeName="设备电量";}
					String clusterId = t.get(i).get("cluster_id").toString();
					String arr = t.get(i).get("value")==null?"0":t.get(i).get("value").toString();
					String attributename = t.get(i).get("attributeName")==null?"无":t.get(i).get("attributeName").toString();
					String value = "";
					if(clusterId.equals("0500")) {					 
						 String[] s = new String[5];
						 s = arr.split("");
						 int s1 = Integer.parseInt(s[1],16);int s2 = Integer.parseInt(s[2],16);int s3 = Integer.parseInt(s[3],16);int s4 = Integer.parseInt(s[4],16);
						 String[] arr1 = new String[5];String[] ss1 = new String[4]; 
						 int ll = Integer.parseInt(s[4],16);
						 String str2 = Integer.toBinaryString(ll);
						 arr1 = str2.split("");
						 if(arr1.length == 2){
								ss1[0]=ss1[1]=ss1[2]="0";
								ss1[3]=arr1[1];
							}
							if(arr1.length == 3){
								ss1[0]=ss1[1]="0";
								ss1[2]=arr1[1];
								ss1[3]=arr1[2];
							}
							if(arr1.length == 4) {
								ss1[0]="0";
								ss1[1]=arr1[1];ss1[2]=arr1[2];ss1[3]=arr1[3];
							}if(arr1.length == 5) {
								ss1[0]=arr1[1];ss1[1]=arr1[2];ss1[2]=arr1[3];ss1[3]=arr1[4];
							}
							/*String v11 = "";String v22 = "";String v33 = "";
							if(ss1[3].equals("1")) {v11="警报1";}
							if(ss1[2].equals("1")) {v22="警报2";}
							if(ss1[1].equals("1")) {v33="防拆";}*/
						 if(s[1].equals("0")&&s[2].equals("0")){
							 value = "正常";
						 }else {
							    String v11 = "";String v22 = "";String v33 = "";
								if(ss1[3].equals("1")) {v11="警报1";}
								if(ss1[2].equals("1")) {v22="警报2";}
								if(ss1[1].equals("1")) {v33="防拆";}
						 if(s4 < 8 ){
							  int res = s1*16 + s2;
							 // value = "正常" +"(" + res + ")";
							  if(!ss1[3].equals("1") && !ss1[1].equals("1") && !ss1[2].equals("1")){ value = res + "%";}
							  if(!ss1[3].equals("1") && ss1[1].equals("1") && !ss1[2].equals("1")){ value = res + "%" + "("+v33+")";}
							  if(!ss1[3].equals("1") && !ss1[1].equals("1") && ss1[2].equals("1")){ value = res + "%" + "("+v22+ ")";}
							  if(ss1[3].equals("1") && ss1[1].equals("1") && !ss1[2].equals("1")){ value = res + "%" + "("+v11+ "、"+v33+")";}
							  if(ss1[3].equals("1") && !ss1[1].equals("1") && ss1[2].equals("1")){ value = res + "%" + "("+v11+ "、"+v22+")";}
							  if(ss1[3].equals("1") && ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+v11+ "、"+v22+"、"+v33+")";}
							  if(!ss1[3].equals("1") && ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+v22+ "、"+v33+")";}
							  if(ss1[3].equals("1") && !ss1[1].equals("1") && !ss1[2].equals("1")){ value = res + "%" + "("+v11+")";}	
						 }else{
							  int res = s1*16 + s2;
							  // value = "低压" + "(" + res + ")";
							  if(!ss1[3].equals("1") && ss1[1]!="1" && !ss1[2].endsWith("1")){ value = res + "%"+"("+ "弱电" +")";}
							  if(!ss1[3].equals("1") && ss1[1].endsWith("1") && !ss1[2].endsWith("1")){ value = res + "%" + "("+ "弱电、" +v33+")";}
							  if(!ss1[3].equals("1") && ss1[1]!="1" && ss1[2].endsWith("1")){ value = res + "%" + "("+ "弱电、"+v22+ ")";}
							  if(ss1[3].equals("1") && ss1[1].endsWith("1") && !ss1[2].endsWith("1")){ value = res + "%" + "("+ "弱电、"+v11+ "、"+v33+")";}
							  if(ss1[3].equals("1") && !ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+ "弱电、"+v11+ "、"+v22+")";}
							  if(ss1[3].equals("1") && ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+ "弱电、"+v11+ "、"+v22+"、"+v33+")";}
							  if(!ss1[3].equals("1") && ss1[1].endsWith("1") && ss1[2].endsWith("1")){ value = res + "%" + "("+ "弱电、"+v22+ "、"+v33+")";}
							  if(ss1[3].equals("1") && !ss1[1].endsWith("1") && !ss1[2].endsWith("1")){ value = res + "%" + "("+ "弱电、"+v11+")";}
						 }	
					  }
					}else {
						value = getAttributeValue(attributename,arr,deviceattributehistoryHouseidYear.getLang());
						// value = arr;
					}	
					cell.setCellValue(attributeName + ":" + value);
					cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
					String dat1 = "";
					String dat2 = "";
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					long times=0;
					if(i == 0) {
						 dat1 = t.get(i).get("opdatetime").toString();
						 dat2 = t.get(i).get("opdatetime").toString();
						 Date st1 = sd.parse(dat1);
						 Date st2 = sd.parse(dat2);
						 times = (st2.getTime() - st1.getTime())/1000;
					}else {
						 dat1 = t.get(i-1).get("opdatetime").toString();
						 dat2 = t.get(i).get("opdatetime").toString();
						 Date st1 = sd.parse(dat1);
						 Date st2 = sd.parse(dat2);
						 times = (st2.getTime() - st1.getTime())/1000;
					}
					String timesStr="间隔" + times + "s";			   
					if (times<60) {
					    timesStr="间隔" + times + "s";
					}
					if (times>=60 && times<3600) {
						//timesStr="间隔" + parseInt(times/60) + "min";
						timesStr="间隔" + (long)(times/60) + "min";
					}
					//2016.8.3 规定间隔时间不能超过24h ,超过24h 则直接按照24h计算
					if (times>=3600) {
						timesStr="间隔" + (long)(times/3600) + "h";
					}
					cell.setCellValue(timesStr);
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("opdatetime").toString());
					
				}
				//设置response参数
				//对中文字符转码
				String fileName = "属性报告列表.xls";		
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
			logger.info("exportAttrLogExcel", e);
		} finally {
			try {
				if(os != null)
					os.close();
			} catch(Exception e) {
//				e.printStackTrace();
				logger.info("exportAttrLogExcel close BufferedOutputStream", e);
			}
		}
		return 0;
	}
	
	
	public String getAttributeValue(String attributeName,String value,String lang) {
		if(StringUtils.isNotBlank(lang) && lang.equals("1")){
			if (attributeName.equals("on_off_status") && value.equals("0")) {
				value = "Close";
			} else if(attributeName.equals("on_off_status")  && value.equals("1")){
				value = "Open";
			} else if(attributeName.equals("lock_status") && value.equals("1")){
				value = "Lock";
			} else if(attributeName.equals("lock_status") && value.equals("2")){
				value = "Unlock";
			} else if(attributeName.equals("DoorState")  && value.equals("0")){
				value = "Open";
			} else if(attributeName.equals("DoorState") && value.equals("1")){
				value = "Close";
			} else if(attributeName.equals("Occupied")  && value.equals("1")){
				value = "Occupied";
			} else if(attributeName.equals("Occupied") && value.equals("0")){
				value = "Unoccupied";
			} else if(attributeName.equals("lock_type") && value.equals("0")){
				value = "Bolt";
			} else if(attributeName.equals("lock_type")  && value.equals("1")){
				value = "Magnetic";
			} else if(attributeName.equals("lock_type") && value.equals("2")){
				value = "Other";
			} else if(attributeName.equals("current")){
				value = value + "mA";
			} else if(attributeName.equals("voltage") ){
				value = value + "V";
			} else if(attributeName.equals("power")){
				value = value + "W";
			} else if(attributeName.equals("energy")){
				value = value + "WH";
			} else if(attributeName.equals("level")){
				int tt = Integer.parseInt(value);
				long pp = (Integer)tt/255*100;
				value = pp + "%";
				logger.info("得到的值为："+ pp );
			} 
			return value;
		}else {
			if (attributeName == "on_off_status" && value=="0") {
				value = "关";
			} else if(attributeName.equals("on_off_status") && value.equals("1")){
				value = "开";
			} else if(attributeName.equals("lock_status") && value.equals("1")){
				value = "关锁";
			} else if(attributeName.equals("lock_status") && value.equals("2")){
				value = "开锁";
			} else if(attributeName.equals("DoorState") && value.equals("0")){
				value = "开";
			} else if(attributeName.equals("DoorState") && value.equals("1")){
				value = "关";
			} else if(attributeName.equals("Occupied") && value.equals("1")){
				value = "占用";
			} else if(attributeName.equals("Occupied") && value.equals("0")){
				value = "空闲";
			} else if(attributeName.equals("lock_type") && value.equals("0")){
				value = "门栓";
			} else if(attributeName.equals("lock_type") && value.equals("1")){
				value = "磁性的";
			} else if(attributeName.equals("lock_type") && value.equals("2")){
				value = "其他";
			} else if(attributeName.equals("current")){
				value = value + "mA";
			} else if(attributeName.equals("voltage")){
				value = value + "V";
			} else if(attributeName.equals("power")){
				value = value + "W";
			} else if(attributeName.equals("energy")){
				value = value + "WH";
			} else if(attributeName.equals("level")){
				int tt = Integer.parseInt(value);
				long pp = (Integer)tt/255*100;
				value = pp + "%";
				logger.info("得到的值为："+ pp );
			} 
			return value;
		}
		
	}

	@Override
	public Map getVolotageValue(Map param) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> rt = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String tableName = "deviceAttributeHistory_" + param.get("houseIEEE") + "_" + year;
		StringBuffer sql = new StringBuffer();
		sql.append("select f.attribute_id,f.cluster_id,f.opdatetime,t.lasttime,f.value,m.modelId,m.voltagestate from ").append( tableName).append(" f LEFT JOIN device t on f.houseIEEE = t.houseIEEE and f.device_ieee = t.ieee and f.device_ep = t.ep ");
		sql.append(" LEFT JOIN modenodelib m on m.modelId = t.modelId and f.cluster_id = m.clusterId  where 1=1 ");
		if(StringUtils.isNotBlank((String)param.get("houseIEEE"))) {
			sql.append(" and f.houseIEEE =:houseIeee ");
			params.put("houseIeee", param.get("houseIEEE"));
		}
		if(StringUtils.isNotBlank((String)param.get("deviceIeee"))) {
			sql.append(" and f.device_ieee =:deviceIeee ");
			params.put("deviceIeee", param.get("deviceIeee"));
		}
		if(StringUtils.isNotBlank((String)param.get("deviceEp"))) {
			sql.append(" and f.device_ep =:deviceEp ");
			params.put("deviceEp", param.get("deviceEp"));
		}
		sql.append(" order by f.opdatetime desc limit 1 ");
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		if(list.size() != 0 && list.get(0).get("cluster_id").equals("0500")) {
			 String value = list.get(0).get("value").toString();
			 String[] s = new String[5];
			 s = value.split("");
			 if(s[1].equals("1") || s[1].equals("0")) {
					Map<String, Object> map = new HashMap<String, Object>();
					StringBuffer hql = new StringBuffer();
					hql.append("select f.cluster_id,f.opdatetime,t.lasttime,f.value,m.voltagestate from ").append( tableName).append(" f LEFT JOIN device t on f.houseIEEE = t.houseIEEE and f.device_ieee = t.ieee and f.device_ep = t.ep ");
					hql.append(" LEFT JOIN modenodelib m on m.modelId = t.modelId and f.cluster_id = m.clusterId  where 1=1 and f.cluster_id='0500' ");
					if(StringUtils.isNotBlank((String)param.get("houseIEEE"))) {
						hql.append(" and f.houseIEEE =:houseIeee ");
						map.put("houseIeee", param.get("houseIEEE"));
					}
					if(StringUtils.isNotBlank((String)param.get("deviceIeee"))) {
						hql.append(" and f.device_ieee =:deviceIeee ");
						map.put("deviceIeee", param.get("deviceIeee"));
					}
					if(StringUtils.isNotBlank((String)param.get("deviceEp"))) {
						hql.append(" and f.device_ep =:deviceEp ");
						map.put("deviceEp", param.get("deviceEp"));
					}
					hql.append(" order by f.opdatetime desc ");
					List<Map> t = mapDao.executeSql(hql.toString(), map);
					int countsize = t.size();
					int count = 0;
					for (int i = 0; i < countsize; i ++){
						 value = t.get(i).get("value").toString();
						 s = value.split("");
						 if(s[1].equals("1") || s[1].equals("0")) {
							 count ++;
						 }else {
							 break;
						 }
					}
					rt.put("volotagestate", "弱电(" + count +"次)");
					
			}else {
				    rt.put("volotagestate", "正常");
			}
			rt.put("list", list);
			return rt;
		}
		if(list.size() != 0 && list.get(0).get("cluster_id").equals("0001")) {
		String volotage = list.get(0).get("voltagestate")==null?"0":list.get(0).get("voltagestate").toString();
		String values = list.get(0).get("value")==null?"0":list.get(0).get("value").toString();
		int result = values.compareTo(volotage);
		if(result > 0) {
			rt.put("volotagestate", "正常");
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuffer hql = new StringBuffer();
			//hql.append("select f.attribute_id,f.cluster_id,f.opdatetime,t.lasttime,f.value,m.modelId,m.voltagestate from ").append( tableName).append(" f LEFT JOIN device t on f.houseIEEE = t.houseIEEE and f.device_ieee = t.ieee and f.device_ep = t.ep ");
			hql.append("select f.cluster_id,f.opdatetime,t.lasttime,f.value,m.voltagestate from ").append( tableName).append(" f LEFT JOIN device t on f.houseIEEE = t.houseIEEE and f.device_ieee = t.ieee and f.device_ep = t.ep ");
			hql.append(" LEFT JOIN modenodelib m on m.modelId = t.modelId and f.cluster_id = m.clusterId  where 1=1 and f.cluster_id='0001' ");
			if(StringUtils.isNotBlank((String)param.get("houseIEEE"))) {
				hql.append(" and f.houseIEEE =:houseIeee ");
				map.put("houseIeee", param.get("houseIEEE"));
			}
			if(StringUtils.isNotBlank((String)param.get("deviceIeee"))) {
				hql.append(" and f.device_ieee =:deviceIeee ");
				map.put("deviceIeee", param.get("deviceIeee"));
			}
			if(StringUtils.isNotBlank((String)param.get("deviceEp"))) {
				hql.append(" and f.device_ep =:deviceEp ");
				map.put("deviceEp", param.get("deviceEp"));
			}
			hql.append(" order by f.opdatetime desc ");
			List<Map> t = mapDao.executeSql(hql.toString(), map);
			int countsize = t.size();
			int count = 0;
			for (int i = 0; i < countsize; i ++){
				 volotage = t.get(i).get("voltagestate").toString();
				 values = t.get(i).get("value").toString();
				 result = values.compareTo(volotage); 
				 if(result < 0) {
					 count ++;
				 }else {
					 break;
				 }
			}
			rt.put("volotagestate", "弱电(" + count +"次)");
		}
			rt.put("list", list);
			return rt;
		}else {
			rt.put("list", list);
			rt.put("volotagestate", "---");
			return rt;
		}		
	}
	
}
