package zbHouse.service.impl;
import java.io.BufferedOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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






import com.ctc.wstx.util.StringUtil;

import sy.dao.BaseDaoI;
import sy.model.DeviceattributehistoryHouseidYear;
import sy.model.Devicemonitorlog;
import sy.service.DeviceattributehistoryHouseidYearServiceI;
import sy.util.PropertiesUtil;
import zbHouse.editor.Node2Many;
import zbHouse.model.Device;
import zbHouse.pageModel.DataGrid;
import zbHouse.service.DeviceServiceI;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceServiceI {
	private static final Logger log = Logger.getLogger(DeviceServiceImpl.class);
	private Node2Many nodeCheck;	
	private BaseDaoI<Map> mapDao;
	private BaseDaoI<Devicemonitorlog> devicemonitorlogDao;
//	private static CopyOnWriteArrayList<Device> updateList=new CopyOnWriteArrayList<Device>();
//	private static CopyOnWriteArrayList<Device> saveList=new CopyOnWriteArrayList<Device>();
//	private List<Device> cacheList = new ArrayList(100);
	Map<String, Device> cache = new HashMap<String,Device>(500); 
	private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.device"));
	private DeviceattributehistoryHouseidYearServiceI deviceattributehistoryHouseidYearService;
	
	public Node2Many getNodeCheck() {
		return nodeCheck;
	}
	@Autowired
	public void setNodeCheck(Node2Many nodeCheck) {
		this.nodeCheck = nodeCheck;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	
	public BaseDaoI<Devicemonitorlog> getDevicemonitorlogDao() {
		return devicemonitorlogDao;
	}
	@Autowired
	public void setDevicemonitorlogDao(
			BaseDaoI<Devicemonitorlog> devicemonitorlogDao) {
		this.devicemonitorlogDao = devicemonitorlogDao;
	}
	@Override
	public Device keyUpdate(Device device) {
			
		return device;
		// TODO Auto-generated method stub		
	}
	
	public DeviceattributehistoryHouseidYearServiceI getDeviceattributehistoryHouseidYearService() {
		return deviceattributehistoryHouseidYearService;
	}

	@Autowired
	public void setDeviceattributehistoryHouseidYearService(
			DeviceattributehistoryHouseidYearServiceI deviceattributehistoryHouseidYearService) {
		this.deviceattributehistoryHouseidYearService = deviceattributehistoryHouseidYearService;
	}
	/**
	 * 将Map对象转换为Device对象
	 * @param map
	 * @return
	 */
	private Device convertMapToDevice(ResultSet rs) {
		Device dv = new Device();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dv.setId(rs.getLong(1));
			dv.setHouseIeee(rs.getString(2));
			dv.setRoomId(rs.getLong(3));
			dv.setNodeId(rs.getLong(4));
			dv.setDeviceName(rs.getString(5));
			dv.setIeee(rs.getString(6));
			dv.setEp(rs.getString(7));
			dv.setType(rs.getString(8));
			dv.setIsonline(rs.getString(9));
			dv.setCreatetime(rs.getString(10)==null?null:sdf.parse(rs.getString(10)));
			dv.setLasttime(rs.getString(11)==null?null:sdf.parse(rs.getString(11)));
			dv.setDatecode(rs.getString(12));
			dv.setDeviceattribute(rs.getString(13));
			dv.setUnit_type(rs.getString(14));
			dv.setModelId(rs.getString(15));
			dv.setSolidModelID(rs.getString(16));
		} catch(Exception e) {
			log.info("DeviceServiceImpl convertMapToDevice", e);
		}
		return dv;
	}
	
	@Override
	public Device saveOrUpdate(Device newdata,Map<String, Object> params) {
		StringBuilder isKey = new StringBuilder();
		isKey.append(newdata.getHouseIeee()).append(newdata.getIeee()).append(newdata.getEp());
		String keyString=isKey.toString();
		cache.put(keyString, newdata);
		//批量更新
		if (this.cache.size() >= this.cacheSize) {
			saveCacheData();
//			log.info("定时保存deviceBegin......");
//			final Map<String, Device> tmpList = this.cache;
//			this.cache = new HashMap<String,Device>(this.cacheSize + 10);
////			new Thread() {
////	    		public void run() {
////	    			log.info("Device saveorupdate begin, tmpList.size: " + tmpList.size());
//	    			//查询Device是否存在或nodeId是否合法
//	    			StringBuilder sql = new StringBuilder("REPLACE INTO Device(id,houseIeee,roomId,nodeId,deviceName,ieee,ep,type,isonline,createtime,lasttime,datecode,deviceattribute,unit_type,modelId) VALUES");
//	    			Connection conn = null;
//	    			Statement stmt = null;
//	    			ResultSet rs = null;
//	    			try {
//	    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
//	    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
//	    						PropertiesUtil.getProperty("jdbc.user"),
//	    						PropertiesUtil.getProperty("jdbc.password"));
//	    				stmt = conn.createStatement();
//		    			Iterator iterator = tmpList.keySet().iterator();
//		    			while(iterator.hasNext()){
//		    				Device dv = tmpList.get(iterator.next());
//							StringBuilder sql1 = new StringBuilder("select a.id,a.houseIEEE,a.roomId,a.nodeId,a.deviceName,a.ieee,a.ep,a.type,a.isonline,");
//							sql1.append("a.createtime,a.lasttime,a.datecode,a.deviceattribute,a.unit_type,a.modelId from (")
//							.append("select a.id,a.houseIEEE,a.roomId,a.nodeId,a.deviceName,a.ieee,a.ep,a.type,a.isonline,")
//							.append("a.createtime,a.lasttime,a.datecode,a.deviceattribute,a.unit_type,a.modelId ")
//							.append("from device a ")
//							.append("where a.houseIeee='").append(dv.getHouseIeee()).append("' and a.ieee='")
//							.append(dv.getIeee()).append("' and a.ep='").append(dv.getEp()).append("' limit 1 ")
//							.append("UNION All ")
//							.append("select null id,'' houseIEEE,null roomId,null nodeId,'' deviceName,'' ieee,'' ep,'' type,'' isonline,")
//							.append("'' createtime,'' lasttime,'' datecode,'' deviceattribute,'' unit_type,'' modelId ")
//							.append("from node a ")
//							.append("where a.nodeId=").append(dv.getNodeId()).append(" limit 1")
//							.append(") a order by a.id asc");
//							rs = stmt.executeQuery(sql1.toString());
//							Device device = dv;
//							boolean hasData = false;
//							while(rs.next()) {
//								Long id = rs.getLong(1);
//								if(id != null) {
//									device = convertMapToDevice(rs);
//									BeanUtils.copyProperties(dv, device,new String[]{"id","isonline","createtime"});
//									if(!rs.next())
//										device.setNodeId(-1L);
//								}
//								if(!hasData)
//									hasData = true;
//							}
//							if(!hasData) {
//								device.setNodeId(-1L);
//							}
//							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		    				sql.append("(").append(device.getId()).append(",'").append(device.getHouseIeee()).append("',")
//		    				.append(device.getRoomId()).append(",").append(device.getNodeId()).append(",'").append(device.getDeviceName())
//		    				.append("','").append(device.getIeee()).append("','").append(device.getEp()).append("','").append(device.getType())
//		    				.append("','").append(device.getIsonline()).append("','").append(sdf.format(device.getCreatetime())).append("','")
//		    				.append(sdf.format(device.getLasttime())).append("',").append(device.getDatecode() == null?null:"'"+device.getDatecode()+"'")
//		    				.append(",").append(device.getDeviceattribute() == null?null:"'"+device.getDeviceattribute()+"'")
//		    				.append(",").append(device.getUnit_type() == null?null:"'"+device.getUnit_type()+"'")
//		    				.append(",").append(device.getModelId() == null?null:"'"+device.getModelId()+"'").append("),");	
//		    			}
//		    			String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
//		    			int i = stmt.executeUpdate(sqlStr);
//		    			log.info("Device saveorupdate end, result: " + i);
//	    			}catch(Exception e) {
//	    				log.info("save or update device exception", e);
//	    			} finally {
//	    				try {
//	    					if(rs != null)
//	    						rs.close();
//	    					if(stmt != null)
//	    						stmt.close();
//	    					if(conn != null)
//	    						conn.close();
//	    				} catch(Exception e) {
//	    					log.info("jdbc close exception", e);
//	    				}
//	    			}
//	    			log.info("定时保存deviceEnd......");
////	    		}
////	    	}.start();
		}
		return newdata;	
		
	}
	/**
	 * 修改burnin表，判断设备是否全部在线，全部在线则说明正常
	 * @param newdata
	 * @return
	 */
	@Override
	public int updatebrin(Device newdata){
		Map<String, Object> params = new HashMap<String, Object>();
		if(newdata.getIsonline()=="0"){
			String sqlString="Update brinhouse set deviceAllFlag = 0,rFlag= 2,  where houseIeee=:houseIeee";
			params.put("houseIeee", newdata.getHouseIeee());
			mapDao.executeSql2(sqlString);
		}
		else{
			String sql2 ="Select Count(ieee) as dCount from device where isonline=0 And houseIeee=:houseIeee "; 
			params.put("houseIeee", newdata.getHouseIeee());
			List<Map> i =mapDao.executeSql(sql2, params);
			if(i.get(0).get("dCount").equals("0")){
				String sql3="Update brinhouse set deviceAllFlag = 1 where houseIeee=:houseIeee";
				params.put("houseIeee", newdata.getHouseIeee());
				mapDao.executeSql2(sql3);
			}
			
		}
		return 1;
		
	}
	
	
	@Override
	public Device updateIsonline(Device newdata) throws Exception{	
		/*
		 	当使用非主键索引插入数据，获取得非主键索引锁后，其他事务在该行中获取了主键锁， 则会引起死锁，
		 故先取出主键,再根据主键更新（统一使用主键锁，避免主键锁与非主键锁竞争出现死锁）
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", newdata.getHouseIeee());
		params.put("ieee", newdata.getIeee());
		BaseDaoI<Device> deviceDao = nodeCheck.getDeviceDao();
		String getHql = "FROM Device WHERE houseIeee = :houseIeee and ieee=:ieee and houseIeee <> ieee";
		List<Device> device = deviceDao.find(getHql, params);
		//根据主键更新在线离线状态
		for(Device d:device){
			params.clear();
			String hql="update Device t set t.isonline = :isonline,t.lasttime = now() where t.id = :id";
			params.put("isonline", newdata.getIsonline());
			params.put("id", d.getId());
			deviceDao.executeHql(hql,params);
		}
		return newdata;	
	}
	/**
	 * 修改设备第一次在线状态
	 * @author: lizhihua
	 * @param device
	 * @return
	 */
	@Override
	public Device updatefirstIsonline(Device newdata) {
		/*
 			当使用非主键索引插入数据，获取得非主键索引锁后，其他事务在该行中获取了主键锁， 则会引起死锁，
 			故先取出主键,再根据主键更新（统一使用主键锁，避免主键锁与非主键锁竞争出现死锁）
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", newdata.getHouseIeee());
		params.put("ieee", newdata.getIeee());
		BaseDaoI<Device> deviceDao = nodeCheck.getDeviceDao();
		String getHql = "FROM Device WHERE houseIeee = :houseIeee and ieee=:ieee and houseIeee <> ieee";
		List<Device> device = deviceDao.find(getHql, params);
		for(Device d:device){
			//根据主键更新在线离线状态
			params.clear();
			String hql="update Device t set t.isonline = :isonline,t.lasttime = now() where t.id = :id";
			params.put("isonline", "1");
			params.put("id", d.getId());
			deviceDao.executeHql(hql,params);
		}
		return newdata;	
	}
	
	@Override
	public int delete(Map<String, Object> params) {

		String hql = "delete Device t ";		
		hql = addWhere2(hql, params);		
		return nodeCheck.getDeviceDao().executeHql(hql);
	}

	/**
	 * 查询设备的在线信息
	 * @author: zhuangxd
	 * 时间：2014-7-1 上午10:54:58
	 * @param device
	 * @return
	 */
	@Override
	public Device getIsonline(Device device) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", device.getHouseIeee());
		params.put("ieee", device.getIeee());
		Device t = nodeCheck.getDeviceDao().get("from Device t where t.houseIeee = :houseIeee " +
				"and t.ieee = :ieee ", params);
		if (t != null) {
			return t;
		}
		return null;
	}

	/*查询家与设备的在线状态*/
	@Override
	public DataGrid findonline(Map<String, Object> params) {
		//String hql = "from Device t where t.houseIeee='11' ";
//		String hql = "from Device t where 1=1 ";
//		StringBuffer sql = new StringBuffer("from Device t where 1=1 ");
//		hql = addWhere2(hql, params);
		int year = Calendar.getInstance().get(Calendar.YEAR);
	//	String tableName = "deviceAttributeHistory_" + params.get("houseIeee") + "_" + year;
//		StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode,n.nodeType,t.unit_type,t.deviceattribute  from device t,house h,node n where 1=1 ");
	
		StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.modelId,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode,n.nodeType,t.unit_type,t.deviceattribute,m.voltagestate,m.clusterId,t.lasttime from house h left join device t on h.houseIeee=t.houseIEEE left join node n on t.houseIEEE=n.houseIEEE and t.ieee=n.ieee left join modenodelib m on m.modelId = t.modelId where 1=1 ");
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (params.get("houseIeee") != null) {
//			sql.append("and t.houseIEEE=h.houseIeee and n.houseIEEE=t.houseIEEE and n.ieee=t.ieee and t.houseIEEE = :houseIeee ");
			sql.append(" and h.houseIeee = :houseIeee ");
			params2.put("houseIeee", params.get("houseIeee"));
		}
		if (params.get("deviceName") != null && params.get("optype") == null) {
			sql.append("and t.deviceName like :deviceName ");
			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
		}
		if (params.get("optype") != null && "-99".equals(params.get("optype"))) { // 设备名称模糊查询deviceName或ieee
			sql.append(" and (t.ieee like :ieee");
			sql.append(" or t.deviceName like :deviceName) ");
			params2.put("ieee", "%" + params.get("deviceName")+ "%");
			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
		}		
		String orderBy = (String) params.get("orderBy");
		if(StringUtils.isNotBlank(orderBy))
			sql.append(" order by ").append(orderBy);
		else
			sql.append(" order by t.isonline asc,t.lasttime desc");
		if (params.get("pageSize") != null) {
			sql.append(" limit " + params.get("startRow") + "," + params.get("pageSize"));
		} 
//		String totalHql = "select count(*) " + hql;			
		DataGrid dg = new DataGrid();		
//		List<Device> l=nodeCheck.getDeviceDao().find(hql);
//		List<Device> l=nodeCheck.getDeviceDao().find(sql.toString(),params2);
//		dg.setTotal(nodeCheck.getDeviceDao().count(totalHql));
		List<Map> l = mapDao.executeSql(sql.toString(), params2);
		if (l != null) {
			dg.setTotal(l.size());
		} else {
			dg.setTotal(0);
		}
		dg.setRows(l);
		return dg;
		// TODO Auto-generated method stub		
	}
	
	
//	/*查询家与设备的在线状态*/
//	@Override
//	public DataGrid findonline(Map<String, Object> params) {
//
//		//String hql = "from Device t where t.houseIeee='11' ";
////		String hql = "from Device t where 1=1 ";
////		StringBuffer sql = new StringBuffer("from Device t where 1=1 ");
////		hql = addWhere2(hql, params);
//		StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode  from device t,house h where 1=1 ");
//		Map<String, Object> params2 = new HashMap<String, Object>();
//		if (params.get("houseIeee") != null) {
//			sql.append("and t.houseIEEE=h.houseIeee and t.houseIEEE = :houseIeee ");
//			params2.put("houseIeee", params.get("houseIeee"));
//		}
//		if (params.get("deviceName") != null && params.get("optype") == null) {
//			sql.append("and t.deviceName like :deviceName ");
//			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
//		}
//		if (params.get("optype") != null && "-99".equals(params.get("optype"))) { // 设备名称模糊查询deviceName或ieee
//			sql.append(" and (t.ieee like :ieee");
//			sql.append(" or t.deviceName like :deviceName) ");
//			params2.put("ieee", "%" + params.get("deviceName")+ "%");
//			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
//		}		
//		String orderBy = (String) params.get("orderBy");
//		if(StringUtils.isNotBlank(orderBy))
//			sql.append(" order by ").append(orderBy);
//		else
//			sql.append(" order by t.isonline asc,t.lasttime desc");
//		if (params.get("pageSize") != null) {
//			sql.append(" limit " + params.get("startRow") + "," + params.get("pageSize"));
//		} 
////		String totalHql = "select count(*) " + hql;			
//		DataGrid dg = new DataGrid();		
////		List<Device> l=nodeCheck.getDeviceDao().find(hql);
////		List<Device> l=nodeCheck.getDeviceDao().find(sql.toString(),params2);
////		dg.setTotal(nodeCheck.getDeviceDao().count(totalHql));
//		List<Map> l = mapDao.executeSql(sql.toString(), params2);
//		if (l != null) {
//			dg.setTotal(l.size());
//		} else {
//			dg.setTotal(0);
//		}
//		dg.setRows(l);
//		return dg;
//		// TODO Auto-generated method stub		
//	}
	
	/**
	 * 获取记录总数
	 * @author: zhuangxd
	 * 时间：2014-8-1 下午6:34:17
	 * @param device
	 * @return
	 */
	@Override
	public Map getListoryCount(Map<String, Object> params) {
		Map<String, Object> rt = new HashMap<String, Object>();
		//StringBuffer sql = new StringBuffer("select t.isonline,h.isonline as isonline2,t.device_id,m.voltagestate,m.clusterId,t.modelId,t.lasttime  from house h left join device t on h.houseIeee=t.houseIEEE left join modenodelib m on m.modelId = t.modelId   where 1=1 ");	
		StringBuffer sql = new StringBuffer("select t.isonline,h.isonline as isonline2  from house h left join device t on h.houseIeee=t.houseIEEE  where 1=1 ");	    
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (StringUtils.isNotBlank((String)params.get("houseIeee"))) {
			sql.append(" and h.houseIeee =:houseIeee ");
			params2.put("houseIeee", params.get("houseIeee"));
		}
		if(StringUtils.isNotBlank((String)params.get("deviceName")) && StringUtils.isBlank((String)params.get("optype"))) {
			sql.append("and t.deviceName like :deviceName ");
			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
		}
		if(StringUtils.isNotBlank((String)params.get("optype")) && "-99".equals(params.get("optype"))) {
			sql.append(" and (t.ieee like :ieee");
			sql.append(" or t.deviceName like :deviceName) ");
			params2.put("ieee", "%" + params.get("deviceName")+ "%");
			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
		}
		List<Map> list = mapDao.executeSql(sql.toString(), params2);
		
		if(list != null && list.size() > 0) {
			rt.put("total",list);	
		} else {
			rt.put("total",null);
		}
		return rt;
	}
	
	@Override
	public DataGrid find(Map<String, Object> params) {

		//String hql = "from Device t where t.houseIeee='11' ";
//		String hql = "from Device t where 1=1 ";
//		StringBuffer sql = new StringBuffer("from Device t where 1=1 ");
//		hql = addWhere2(hql, params);
		StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.deviceName,t.ep,t.isonline,t.ieee from device t where 1=1 ");
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (params.get("houseIeee") != null) {
			sql.append("and t.houseIEEE like :houseIeee ");
			params2.put("houseIeee", "%" + params.get("houseIeee") + "%");
		}
		if (params.get("deviceName") != null && params.get("optype") == null) {
			sql.append("and t.deviceName like :deviceName ");
			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
		}
		if (params.get("optype") != null && "-99".equals(params.get("optype"))) { // 设备名称模糊查询deviceName或ieee
			sql.append(" and (t.ieee like :ieee");
			sql.append(" or t.deviceName like :deviceName) ");
			params2.put("ieee", "%" + params.get("deviceName")+ "%");
			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
		}		
		String orderBy = (String) params.get("orderBy");
		if(StringUtils.isNotBlank(orderBy))
			sql.append(" order by ").append(orderBy);
		else
			sql.append(" order by t.isonline asc,t.lasttime desc");
//		String totalHql = "select count(*) " + hql;			
		DataGrid dg = new DataGrid();		
//		List<Device> l=nodeCheck.getDeviceDao().find(hql);
//		List<Device> l=nodeCheck.getDeviceDao().find(sql.toString(),params2);
//		dg.setTotal(nodeCheck.getDeviceDao().count(totalHql));
		List<Map> l = mapDao.executeSql(sql.toString(), params2);
		if (l != null) {
			dg.setTotal(l.size());
		} else {
			dg.setTotal(0);
		}
		dg.setRows(l);
		return dg;
		// TODO Auto-generated method stub		
	}
	
	/*@Override
	public DataGrid find(Map<String, Object> params) {

		//String hql = "from Device t where t.houseIeee='11' ";
		String hql = "from Device t ";
		hql = addWhere2(hql, params);
		hql += " order by t.isonline asc,t.lasttime desc";
		String totalHql = "select count(*) " + hql;			
		DataGrid dg = new DataGrid();		
		List<Device> l=nodeCheck.getDeviceDao().find(hql);
//		dg.setTotal(nodeCheck.getDeviceDao().count(totalHql));
		if (l != null) {
			dg.setTotal(l.size());
		} else {
			dg.setTotal(0);
		}
		dg.setRows(l);
		return dg;
		// TODO Auto-generated method stub		
	}*/
	
	private String addWhere(String hql,Device device,Map<String, Object> params) {
		if (device!= null){
			params.put("houseIeee", device.getHouseIeee());
			params.put("ieee", device.getIeee());
			params.put("ep", device.getEp());
			hql += "where t.houseIeee =:houseIeee and t.ieee =:ieee and t.ep =:ep ";
		}
		//TestLog4j.testInfo("device:"+device.getHouseIeee()+" "+device.getIeee()+" "+device.getEp());
		return hql;
	}
	private String addWhere2(String hql, Map<String, Object> params) {
		if (params!= null){
			boolean first=true;
			hql += "where ";
			  for (String key : params.keySet()) {
				if (key.equals("deviceName") && "-99".equals(params.get("optype"))) { // 设备名称模糊查询deviceName或ieee
					continue;
				}
				if(first){  
					first=false;
					if (key.equals("optype") && "-99".equals(params.get("optype"))) { // 设备名称模糊查询deviceName或ieee
						hql += " (t.ieee" + " like '%" + params.get("deviceName")+ "%'";
						hql += " or t.deviceName" + " like '%" + params.get("deviceName")+ "%')";
						continue;
					}
					if (key.equals("deviceName")) {
						hql += " t." + key + " like '%" + params.get(key)+ "%'";					
					} else {
						hql += " t." + key + " ='" + params.get(key)+ "'";					
					}
				}else{
					if (key.equals("optype") && "-99".equals(params.get("optype"))) { // 设备名称模糊查询deviceName或ieee
						hql += " and (t.ieee" + " like '%" + params.get("deviceName")+ "%'";
						hql += " or t.deviceName" + " like '%" + params.get("deviceName")+ "%')";
						continue;
					}
					if (key.equals("deviceName")) {
						hql += " and t." + key + " like '%" + params.get(key)+ "%'";					
					} else {
						hql += " and t." + key + " ='" + params.get(key)+ "'";					
					}
				}
			}			
		}
		//TestLog4j.testInfo(hql);
		return hql;
	}
	
	@Override
	public void saveCacheData() {
		if(cache.size()==0){
			log.info("定时保存device，缓存为空");
			return;
		}
		log.info("定时保存deviceBegin......");
//		log.info("Device saveorupdate schedule, tmpList.size: " + this.cache.size());
		if(this.cache.size() < 1) return;
		final Map<String, Device> tmpList = this.cache;
		this.cache = new HashMap<String,Device>(this.cacheSize + 10);
		//查询Device是否存在或nodeId是否合法
		Thread th = new Thread() {
			@Override
			public void run() {
				StringBuilder sql = new StringBuilder("REPLACE INTO Device(id,houseIeee,roomId,nodeId,deviceName,ieee,ep,type,isonline,createtime,lasttime,datecode,deviceattribute,unit_type,modelId,SolidModelID) VALUES");
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				try {
					Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
					conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
							PropertiesUtil.getProperty("jdbc.user"),
							PropertiesUtil.getProperty("jdbc.password"));
					stmt = conn.createStatement();
					Iterator iterator = tmpList.keySet().iterator();
					while(iterator.hasNext()){
						Device dv = tmpList.get(iterator.next());
						StringBuilder sql1 = new StringBuilder("select a.id,a.houseIEEE,a.roomId,a.nodeId,a.deviceName,a.ieee,a.ep,a.type,a.isonline,");
						sql1.append("a.createtime,a.lasttime,a.datecode,a.deviceattribute,a.unit_type,a.modelId,a.SolidModelID from (")
						.append("select a.id,a.houseIEEE,a.roomId,a.nodeId,a.deviceName,a.ieee,a.ep,a.type,a.isonline,")
						.append("a.createtime,a.lasttime,a.datecode,a.deviceattribute,a.unit_type,a.modelId,a.SolidModelID ")
						.append("from device a ")
						.append("where a.houseIeee='").append(dv.getHouseIeee()).append("' and a.ieee='")
						.append(dv.getIeee()).append("' and a.ep='").append(dv.getEp()).append("' limit 1 ")
						.append("UNION All ")
						.append("select null id,'' houseIEEE,null roomId,null nodeId,'' deviceName,'' ieee,'' ep,'' type,'' isonline,")
						.append("'' createtime,'' lasttime,'' datecode,'' deviceattribute,'' unit_type,'' modelId,'' SolidModelID ")
						.append("from node a ")
						.append("where a.nodeId=").append(dv.getNodeId()).append(" limit 1")
						.append(") a order by a.id asc");
						rs = stmt.executeQuery(sql1.toString());
						Device device = dv;
						boolean hasData = false;
						while(rs.next()) {
							Long id = rs.getLong(1);
							if(id != null) {
								device = convertMapToDevice(rs);
								BeanUtils.copyProperties(dv, device,new String[]{"id","isonline","createtime"});
								if(!rs.next())
									device.setNodeId(-1L);
							}
							if(!hasData)
								hasData = true;
						}
						if(!hasData) {
							device.setNodeId(-1L);
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						sql.append("(").append(device.getId()).append(",'").append(device.getHouseIeee()).append("',")
						.append(device.getRoomId()).append(",").append(device.getNodeId()).append(",'").append(device.getDeviceName())
						.append("','").append(device.getIeee()).append("','").append(device.getEp()).append("','").append(device.getType())
						.append("','").append(device.getIsonline()).append("','").append(sdf.format(device.getCreatetime())).append("','")
						.append(sdf.format(device.getLasttime())).append("',").append(device.getDatecode() == null?null:"'"+device.getDatecode()+"'")
						.append(",").append(device.getDeviceattribute() == null? null:"'"+device.getDeviceattribute()+"'")
						.append(",").append(device.getUnit_type() == null? null:"'"+device.getUnit_type()+"'")
						.append(",").append(device.getModelId() == null? null:"'"+device.getModelId()+"'")
						.append(",").append(device.getSolidModelID()== null? "'"+device.getModelId()+"'":"'"+device.getSolidModelID()+"'")
						.append("),");	
					}
					String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
					stmt.executeUpdate(sqlStr);
				}catch(Exception e) {
					log.info("save or update device schedule exception", e);
				} finally {
					try {
						if(rs != null)
							rs.close();
						if(stmt != null)
							stmt.close();
						if(conn != null)
							conn.close();
					} catch(Exception e) {
						log.info("jdbc close exception", e);
					}
				}
				log.info("定时保存deviceEnd......");
			}
		};
		th.setName("saveDeviceThread");
		th.start();
	}
	
	@Override
	public Devicemonitorlog save(Devicemonitorlog devicemonitorlog) {
		devicemonitorlogDao.save(devicemonitorlog);
		return devicemonitorlog;
	}
//	@Override
//	public int updateDeviceAttribute(String houseIEEE, String deviceIeee,
//			String deviceEp) {
//		Map<String, Object> paraMap = new HashMap<String, Object>();
//		paraMap.put("houseIEEE", houseIEEE);
//		paraMap.put("ieee", deviceIeee);
//		paraMap.put("ep", deviceEp);
//		String sql = "update device set deviceattribute = null where houseIEEE = :houseIEEE and ieee = :ieee and ep=:ep";
//		int i = mapDao.executeSql2(sql, paraMap);	
//		return i;
//	}
	@Override
	public void execlfindline(HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> t=findonline2(params);
		
		BufferedOutputStream os = null;
		Integer nulindex=0;
		for (Map map : t) {
			if(map.get("modelId")==null)
			{
				nulindex++;
				map.put("modelId","null");
			}	
		}
		
		try {
		//生成excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			
			workbook.setSheetName(0, params.get("houseIEEE").toString());
			HSSFRow row = sheet.createRow((short)2);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			
			
			
			HSSFCell cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue(" ");
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("HosueIEEE");
			
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue(params.get("houseIEEE").toString()==null?" ":params.get("houseIEEE").toString());
			

			row = sheet.createRow((short)(3));
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("name");
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue((t.get(0).get("name")).toString()==null?" ":(t.get(0).get("name")).toString());
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue((t.get(0).get("clientCode")).toString()==null?" ":(t.get(0).get("clientCode")).toString());
			
			
			row = sheet.createRow((short)(5));
			cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue("ModelID");
			cell.setCellStyle(titleStyle);
			
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue("DateCode");
			cell.setCellStyle(titleStyle);
			
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue("Description");
			cell.setCellStyle(titleStyle);
			
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("Quantity");
			cell.setCellStyle(titleStyle);
			
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue("IEEE/EP");
			cell.setCellStyle(titleStyle);
	
			
            int j=6;
            List<String> dList=new ArrayList<String>();

			for(int i = 0; i < t.size(); i++) {
				
				//row = sheet.createRow((short)(i + 1));
//				cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue(t.get(i).get("modelId") == null? "" : t.get(i).get("modelId").toString());
//				
//				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue(t.get(i).get("datecode") == null? "" : t.get(i).get("datecode").toString());
//				
//				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
				
			
				if (i==0) {
					j++;
					row = sheet.createRow((short)(j + 1));
					dList.add(t.get(i).get("ieee") == null? "" : t.get(i).get("ieee").toString());
					cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("ieee") == null? "" : t.get(i).get("ieee").toString());
					String dds=t.get(i).get("ieee") == null? "" : t.get(i).get("ieee").toString();
					//if (t.size()==i+1) {
						cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
					cell.setCellValue( t.get(i).get("Quantity")== null? nulindex.toString() : t.get(i).get("Quantity").toString());	
					
					
					cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("modelId")== null? "null" : t.get(i).get("modelId").toString());
					
					//}
					
				
//					cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(t.get(i).get("datecode") == null? "" : t.get(i).get("datecode").toString());
//					
//					cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
//					
					j++;
//					
					dList.add(t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString());
					row = sheet.createRow((short)(j+1));
					cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString());
					String ss=t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString();
					cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
					cell.setCellValue("");	


//					cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(t.get(i).get("modelId") == null? "" : t.get(i).get("modelId").toString());
					
					cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("datecode") == null? "" : t.get(i).get("datecode").toString());
					
					cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
					cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
					
					
				}
				else {
					if (!(t.get(i).get("ieee")).toString().equals((t.get(i-1).get("ieee")).toString())) {
						j++;

						dList.add(t.get(i).get("ieee") == null? "" : t.get(i).get("ieee").toString());
						row = sheet.createRow((short)(j+1));
						cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("ieee") == null? "" : t.get(i).get("ieee").toString());
						String ddsa=t.get(i).get("ieee") == null? "" : t.get(i).get("ieee").toString();
						
                        if (!(t.get(i).get("modelId").toString()).equals(t.get(i-1).get("modelId").toString())||i+1==t.size()){
                        	cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
    						cell.setCellValue( t.get(i).get("Quantity") == null? nulindex.toString() : t.get(i).get("Quantity").toString());	
    						cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
    						cell.setCellValue(t.get(i).get("modelId")== null? "null" : t.get(i).get("modelId").toString());

						}
						
//						cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
//						cell.setCellValue(t.get(i).get("modelId") == null? "" : t.get(i).get("modelId").toString());
//						
//						cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
//						cell.setCellValue(t.get(i).get("datecode") == null? "" : t.get(i).get("datecode").toString());
//						
//						cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
//						cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
						j++;
//					
						dList.add(t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString());
						row = sheet.createRow((short)(j+1));
						cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString());
						String ddewxString=t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString();
						
//						cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
//						cell.setCellValue(t.get(i).get("modelId") == null? "" : t.get(i).get("modelId").toString());
//						
						cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("datecode") == null? "" : t.get(i).get("datecode").toString());
						
						cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
						cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
						
						
					}
					else {
						j++;
						
						dList.add(t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString());
						row = sheet.createRow((short)(j+1));
						cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString());
						String dexlk=t.get(i).get("ep") == null? "" : t.get(i).get("ep").toString();
						cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
						cell.setCellValue( "");	
						
						
						cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
						cell.setCellValue("");
						
						cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
						cell.setCellValue(t.get(i).get("datecode") == null? "" : t.get(i).get("datecode").toString());
						
						cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
						cell.setCellValue( t.get(i).get("deviceName") == null? "" : t.get(i).get("deviceName").toString());
						
					    
					
					}
				}
				
			}
//			for (int k = 0; k < dList.size(); k++) {
//				row = sheet.createRow((short)(k + 1));
//				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue(dList.get(k));
//			}
			
			String fileName = new String((params.get("houseIEEE").toString()+".xls").getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
			
			
	}
		catch (Exception e) {
			t=null;
		throw e;
	} finally {
		try {
			if(os != null)
				os.close();
		} catch(Exception e) {
			throw e;
		}
	}
		t=null;
	}
	public List<Map> findonline2(Map<String, Object> params) {
		//String hql = "from Device t where t.houseIeee='11' ";
//		String hql = "from Device t where 1=1 ";
//		StringBuffer sql = new StringBuffer("from Device t where 1=1 ");
//		hql = addWhere2(hql, params);
		//int year = Calendar.getInstance().get(Calendar.YEAR);
//
//		StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode,n.nodeType,t.unit_type,t.deviceattribute  from device t,house h,node n where 1=1 ");
//		String sql2="SELECT a.modelId,a.ieee,a.ep,a.deviceName,b.Quantity,a.datecode,c.name,d.clientCode from  " +
//				"(SELECT * from device WHERE houseIEEE='"+params.get("houseIEEE").toString()+
//				"'GROUP BY modelId) a join (SELECT DISTINCT count(ieee) as Quantity,ieee from device" +
//				" where houseIEEE='"+params.get("houseIEEE").toString()+"' GROUP BY modelId) b on a.ieee=b.ieee join houseieee c on a.houseIEEE=c.houseIEEE join house d on d.houseIEEE=c.houseIEEE";
//       
		String sql2="SELECT DISTINCT a.modelId,a.ieee,b.Quantity,a.ep,a.deviceName,a.datecode,c.name,d.clientCode from (SELECT DISTINCT ieee,modelId,ep," +
        		"deviceName,datecode,houseIEEE from device where " +
        		"houseIEEE='"+params.get("houseIEEE").toString()+"'  ORDER BY modelId) a LEFT JOIN " +
        		"(SELECT count( DISTINCT ieee) as Quantity,modelId from device where houseIEEE='"+params.get("houseIEEE").toString()+"'" +
        		" GROUP BY modelId ORDER BY modelId) b on b.modelId=a.modelId join houseieee c on a.houseIEEE=c.houseIEEE join house d on d.houseIEEE=c.houseIEEE";




		
		//		StringBuffer sql = new StringBuffer("SELECT a.modelId,a.ieee,a.ep,a.deviceName,b.Quantity,a.datecode from  (SELECT * from device WHERE houseIEEE= :houseIeee ORDER BY modelId) a join (SELECT count(modelId) as Quantity,modelId from device where houseIEEE= :houseIeee GROUP BY modelId) b on a.modelId=b.modelId");
//		Map<String, Object> params2 = new HashMap<String, Object>();
//		if (params.get("houseIEEE") != null) {
//			params2.put("houseIeee", params.get("houseIEEE"));
//		}
//		List<Map> l = mapDao.executeSql(sql.toString(), params2);
		List<Map> l = mapDao.executeSql(sql2, null);
		
		return l;
		// TODO Auto-generated method stub		
	}
	
	@Override
	public int modeifyDevAndAttrIline(Device js) throws Exception {
		Device device = getIsonline(js);
		// 设备表无记录
		if (device == null) {
//			params.put("result", -1);
//			j.setResponse_params(params);
//			writeJson(j,response);	
//			return; 
			return -1;
		}
    	
    	// 当设备状态发生改变时，保存属性历史记录
    	if (("0".equals(device.getIsonline()) &&  "1".equals(js.getIsonline())) 
    			|| ("1".equals(device.getIsonline()) &&  "0".equals(js.getIsonline()))) {
    		DeviceattributehistoryHouseidYear deviceattributehistoryHouseidYear = new DeviceattributehistoryHouseidYear();
        	deviceattributehistoryHouseidYear.setHouseIeee(js.getHouseIeee());
        	deviceattributehistoryHouseidYear.setDeviceIeee(js.getIeee());
        	if (StringUtils.isNoneBlank(js.getEp())) {
        		deviceattributehistoryHouseidYear.setDeviceEp(js.getEp());
        	} else {
        		deviceattributehistoryHouseidYear.setDeviceEp("01");
        	}
        	deviceattributehistoryHouseidYear.setHouseIeee(js.getHouseIeee());
        	deviceattributehistoryHouseidYear.setClusterId("0000");
        	deviceattributehistoryHouseidYear.setAttributeId("FFFF");
    		deviceattributehistoryHouseidYear.setAttributeName("isonline");
    		if ("1".equals(js.getIsonline())) {
        		deviceattributehistoryHouseidYear.setValue("在线");
        	}
        	else if ("0".equals(js.getIsonline())) {
        		deviceattributehistoryHouseidYear.setValue("离线");
        	}
    		deviceattributehistoryHouseidYearService.saveSql(deviceattributehistoryHouseidYear);
    	}
    	
    	//修改设备第一次在线状态
    	if (("0".equals(device.getIsonline()) &&  "1".equals(js.getIsonline())) ) {
    		updatefirstIsonline(js);
    	}
    	else{
	    	//当前上传状态与原状态未发生改变，则不更新设备状态
	    	if(("1".equals(device.getIsonline()) && "1".equals(js.getIsonline()))
	    			||("0".equals(device.getIsonline()) && "0".equals(js.getIsonline()))) {
	    		
	    	}
	    	else {
	    		updateIsonline(js);
	    	}
//	    	deviceService.updatebrin(js);
    	}
    	return 1;
	}
	

	/**
	 *@author zhangmaolin
	 * @return 
	 * @throws ParseException 
	 *@时间  2016-10-26
	 */
	private Map getLR(String houseIEEE, String deviceIeee, String devcieEp, String lang) throws Exception {
		Map<String, Object> rt = new HashMap<String, Object>();
 		Map<String, Object> map = new HashMap<String, Object>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "rssirecord_" + houseIEEE + "_" + year;
		StringBuffer hww = new StringBuffer();
		Map<String, Object> ppt = new HashMap<String, Object>();
		hww.append("SELECT  isTableExist('"+ tableName +"') as sun  "); 		
 		List<Map> l = mapDao.executeSql(hww.toString()); 
 		String t3 = l.get(0).get("sun") .toString();
 		if (t3.equals("1")) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT r.count_time,r.RSSI from ").append(tableName).append(" r ");
		sql.append("left join device t on r.house_ieee = t.houseIEEE and t.ieee = r.dest_ieee  where 1=1  and RSSI != 10000 and RSSI IS NOT NULL ");
		if (StringUtils.isNotBlank(houseIEEE)) {
			sql.append(" and t.houseIEEE =:house_ieee ");
			map.put("house_ieee", houseIEEE);
		}
		if (StringUtils.isNotBlank(deviceIeee)) {
			sql.append(" and t.ieee =:ieee ");
			map.put("ieee", deviceIeee);
		}
		if (StringUtils.isNotBlank(devcieEp)) {
			sql.append(" and t.ep =:ep ");
			map.put("ep", devcieEp);
		}
		sql.append(" order by r.count_time Desc LIMIT 2;");
		List<Map> list = mapDao.executeSql(sql.toString(), map);
		int LRsize = list.size();		
		if (lang.equals("1")) {
			 if (LRsize == 0) { //长度为0时
					rt.put("rssi1", "None");
					rt.put("rssi2", "None");
			 }else if (LRsize == 1) { //长度为1时
				 String ss = list.get(0).get("RSSI")==null?"无":list.get(0).get("RSSI").toString();		
				 if (list.get(0).get("RSSI") !=null){
					 int pp = Integer.parseInt(ss);
					 if (pp > -70 ) {
						 rt.put("rssi1", "Good" + "(" + ss + ")");
					 }else {
						 rt.put("rssi1", "Difference" + "(" + ss + ")");
					 } 
				 }else {
					rt.put("rssi1", "None");
				 }
					rt.put("rssi2", "None");
		     }else{ //长度为2时
				String ss = list.get(0).get("RSSI")==null?"None":list.get(0).get("RSSI").toString();
				String rr = list.get(1).get("RSSI")==null?"None":list.get(1).get("RSSI").toString();
				//String ltime = list.get(1).get("count_time").toString();
			    String rtime = list.get(1).get("count_time").toString();
			    rtime = StringUtils.substringBetween(rtime, ".0");
			    if (list.get(0).get("RSSI") !=null){
				    int pp = Integer.parseInt(ss);
				    if (pp >= -70) {
					    rt.put("rssi1", "Good" + "(" + ss + ")");
				    }else {
					    rt.put("rssi1", "Difference" + "(" + ss + ")");
				    } 
				}else {
					rt.put("rssi1", "None");
				}
				if (list.get(1).get("RSSI")!=null) {
				    int rr2 = Integer.parseInt(rr);						
					if (rr2 > -70){
						rt.put("rssi2", rr + "  " + rtime);
					}else{
						rt.put("rssi2", "None");
					}										
				}else {
					rt.put("rssi2", "None");
				}
			 }
				return rt; 	
		}else {
			 if (LRsize == 0) { //长度为0时
					rt.put("rssi1", "无");
					rt.put("rssi2", "无");
			 }else if (LRsize == 1) { //长度为1时
				String ss = list.get(0).get("RSSI")==null?"无":list.get(0).get("RSSI").toString();		
				if (list.get(0).get("RSSI") !=null) {
				int pp = Integer.parseInt(ss);
					if (pp > -70 ) {
						rt.put("rssi1", "好" + "(" + ss + ")");
					}else {
						rt.put("rssi1", "差" + "(" + ss + ")");
					} 
				}else {
					rt.put("rssi1", "无");
			    }
				rt.put("rssi2", "无");
			 }else { //长度为2时
				String ss = list.get(0).get("RSSI")==null?"无":list.get(0).get("RSSI").toString();
				String rr = list.get(1).get("RSSI")==null?"无":list.get(1).get("RSSI").toString();
				//String ltime = list.get(1).get("count_time").toString();
				String rtime = list.get(1).get("count_time").toString();					    
				if (list.get(0).get("RSSI") !=null) {
				int pp = Integer.parseInt(ss);
				  if (pp >= -70) {
					rt.put("rssi1", "好" + "(" + ss + ")");
				  }else {
					rt.put("rssi1", "差" + "(" + ss + ")");
				  } 
				}else {
					rt.put("rssi1", "无");
				}
				if (list.get(1).get("RSSI")!=null) {
					int rr2 = Integer.parseInt(rr);						
					if (rr2 > -70) {
						rt.put("rssi2", rr + "  " + rtime);
					}else {
						rt.put("rssi2", "无");
					}										
				}else {
					rt.put("rssi2", "无");
				}
			}
				return rt; 	
		}		 
 	 }else {//对应的表不存在时
 		if (lang.equals("1")) {
 			 rt.put("rssi1", "None");
 			 rt.put("rssi2", "None");
 	    }else {
 			 rt.put("rssi1", "无");
 			 rt.put("rssi2", "无");
 		}		   
		   return rt;
 	}
	}
	
	/**
	 *@author zhangmaolin
	 * @return 
	 * @throws ParseException 
	 *@时间  2016-10-26
	 */
	private Map getLR2(String isonline, String isonline2, String houseIEEE, String deviceIeee, String devcieEp, String lang) throws Exception {
		 Map<String, Object> rt = new HashMap<String, Object>();
 		 Map<String, Object> map = new HashMap<String, Object>();
		 int year = Calendar.getInstance().get(Calendar.YEAR);
		 String tableName = "rssirecord_" + houseIEEE + "_" + year;
		 StringBuffer hww = new StringBuffer();
		 Map<String, Object> ppt = new HashMap<String, Object>();
		 hww.append("SELECT  isTableExist('"+ tableName +"') as sun  "); 		
 		 List<Map> l = mapDao.executeSql(hww.toString()); 
 		 String t3 = l.get(0).get("sun") .toString();
 		 if (t3.equals("1")) {
		 StringBuffer sql = new StringBuffer();
		 sql.append("SELECT r.LQI,r.count_time from ").append(tableName).append(" r ");
		 sql.append("left join device t on r.house_ieee = t.houseIEEE and t.ieee = r.source_ieee where 1=1 and  LQI != 10000  and LQI IS NOT NULL ");
		 if (StringUtils.isNotBlank(houseIEEE)) {
			 sql.append(" and t.houseIEEE =:house_ieee ");
			 map.put("house_ieee", houseIEEE);
		 }
		 if (StringUtils.isNotBlank(deviceIeee)) {
			 sql.append(" and t.ieee =:ieee ");
			 map.put("ieee", deviceIeee);
		 }
		 if (StringUtils.isNotBlank(devcieEp)) {
			 sql.append(" and t.ep =:ep ");
			 map.put("ep", devcieEp);
		 }
		 sql.append(" order by r.count_time Desc LIMIT 2;");
		 List<Map> list = mapDao.executeSql(sql.toString(), map);
		 int LRsize = list.size();	
		 if (lang.equals("1")) {
			 if (LRsize == 0) { //长度为0时
				 rt.put("lqi1", "None");
				 rt.put("lqi2", "None");
			 }else if (LRsize == 1) { //长度为1时
				 if ((isonline.equals("1") && isonline2.equals("1")) || (isonline.equals("2") && isonline2.equals("1"))) {
					 String ll = list.get(0).get("LQI")==null?"None":list.get(0).get("LQI").toString();		
					 if (list.get(0).get("LQI") !=null) {
						 int tt = Integer.parseInt(ll);
						 if (tt > 100) {
							 rt.put("lqi1","Good" + "(" + ll + ")");
						 }else {
							 rt.put("lqi1","Difference" + "(" + ll + ")");
						 }
					 }else {
						 rt.put("lqi1","None");
					 }
				 }else {
					 rt.put("lqi1", "None");
				 }
				 rt.put("lqi2", "None");
			 }else { //长度为2时
				 String ll = list.get(0).get("LQI")==null?"None":list.get(0).get("LQI").toString();
				 String qq = list.get(1).get("LQI")==null?"None":list.get(1).get("LQI").toString();
				 String ltime = list.get(1).get("count_time").toString();
				 if ((isonline.equals("1") && isonline2.equals("1")) || (isonline.equals("2") && isonline2.equals("1"))) {
				     if (list.get(0).get("LQI") !=null) {
					    int tt = Integer.parseInt(ll);
					    if (tt > 100) {
						    rt.put("lqi1","Good" + "(" + ll + ")");
					    }else {
						    rt.put("lqi1","Difference" + "(" + ll + ")");
					    }
					 }else {
						    rt.put("lqi1","None");
					 }
		       }else {
		             rt.put("lqi1","None");
			   }
			   if (list.get(1).get("LQI") != null) {
				   int qq2 = Integer.parseInt(qq);						
					if (qq2 > 100) {
					    rt.put("lqi2", qq + "  " + ltime);
				    }else {
					   rt.put("lqi2", "None");
					}									
				}else {
					rt.put("lqi2","None");
			    }
			 }
			   return rt; 	
		 }else {
			 if (LRsize == 0) { //长度为0时
				 rt.put("lqi1", "无");
				 rt.put("lqi2", "无");
			 }else if (LRsize == 1) { //长度为1时
				 if ((isonline.equals("1") && isonline2.equals("1")) || (isonline.equals("2") && isonline2.equals("1"))) {
					 String ll = list.get(0).get("LQI")==null?"无":list.get(0).get("LQI").toString();		
					 if (list.get(0).get("LQI") !=null) {
						 int tt = Integer.parseInt(ll);
						 if (tt > 100) {
							 rt.put("lqi1","好" + "(" + ll + ")");
						 }else {
							 rt.put("lqi1","差" + "(" + ll + ")");
						 }
					 }else {
						 rt.put("lqi1","无");
					 }
				 }else {
					 rt.put("lqi1", "无");
				 }
				 rt.put("lqi2", "无");
		 }else { //长度为2时
			String ll = list.get(0).get("LQI")==null?"无":list.get(0).get("LQI").toString();
			String qq = list.get(1).get("LQI")==null?"无":list.get(1).get("LQI").toString();
			String ltime = list.get(1).get("count_time").toString();
		    if ((isonline.equals("1") && isonline2.equals("1")) || (isonline.equals("2") && isonline2.equals("1"))) {
				if (list.get(0).get("LQI") !=null) {
				int tt = Integer.parseInt(ll);
				  if (tt > 100) {
					rt.put("lqi1","好" + "(" + ll + ")");
				  }else {
				    rt.put("lqi1","差" + "(" + ll + ")");
				  }
				}else {
				    rt.put("lqi1","无");
				}
			}else {
				rt.put("lqi1","无");
			}	
		    if (list.get(1).get("LQI") != null) {
			   int qq2 = Integer.parseInt(qq);						
			   if (qq2 > 100) {
			   rt.put("lqi2", qq + "  " + ltime);
			   }else {
				 rt.put("lqi2", "无");
			   }									
			}else {
				 rt.put("lqi2","无");
		    }
	    }
			  return rt; 	
	 }	
 	 }else {//对应的表不存在时
 		 if (lang.equals("1")){
 		    rt.put("lqi1", "None");
 		    rt.put("lqi2", "None");
 		 }else {
 		    rt.put("lqi1", "无");
 		    rt.put("lqi2", "无");
 		 } 
		 return rt;
 	 }
	}
	
	
	//离线时长
	private String OfflineDuration(String houseIeee, String ieee, Date lasttime1, Date now, String lang, String offtime) throws ParseException {
		String Offlinetime = "";
		if (houseIeee.equals(ieee) && StringUtils.isNotBlank(offtime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date offtime2 = sdf.parse(offtime);
			long diff = (now.getTime()-offtime2.getTime())/(1000);	
			if (diff >= 3600 ) {
				long day1 = diff/(24*3600);
				long hour1 = diff%(24*3600)/3600;
				if ("1".equals(lang)) {
					if (day1 != 0 && hour1 != 0) {
						Offlinetime = day1 + " day " + hour1 + " hour";
					}else if (day1 != 0 && hour1 == 0 ) {
						Offlinetime = day1 + " day";
					}else  {
						Offlinetime = hour1 + " hour";
					}
				}else {
					if (day1 != 0 && hour1 != 0) {
						Offlinetime = day1 + "天" + hour1 + "小时";
					}else if (day1 != 0 && hour1 == 0 ) {
						Offlinetime = day1 + "天";
					}else {
						Offlinetime = hour1 + "小时";
					}
				}
			}else if (diff < 3600 && diff >= 60) {
				if ("1".equals(lang)) {
					Offlinetime = (long)(diff/60) + " minute";
				}else {
					Offlinetime = (long)(diff/60) + "分钟";
				}				
		    }else {
				if ("1".equals(lang)) {
					Offlinetime = diff + " second";
				} else {
					Offlinetime = diff + "秒";
				}
			}
		}else {
			long diff = (now.getTime()-lasttime1.getTime())/(1000);	
			if (diff >= 3600 ) {
				long day1 = diff/(24*3600);
				long hour1 = diff%(24*3600)/3600;
				if ("1".equals(lang)) {
					if (day1 != 0 && hour1 != 0) {
						Offlinetime = day1 + " day " + hour1 + " hour";
					}else if (day1 != 0 && hour1 == 0 ) {
						Offlinetime = day1 + " day";
					}else  {
						Offlinetime = hour1 + " hour";
					}
				}else {
					if (day1 != 0 && hour1 != 0) {
						Offlinetime = day1 + "天" + hour1 + "小时";
					}else if (day1 != 0 && hour1 == 0 ) {
						Offlinetime = day1 + "天";
					}else {
						Offlinetime = hour1 + "小时";
					}
				}
			}else if (diff < 3600 && diff >= 60) {
				if ("1".equals(lang)) {
					Offlinetime = (long)(diff/60) + " minute";
				}else  {
					Offlinetime = (long)(diff/60) + "分钟";
				}				
		    }else {
				if ("1".equals(lang)) {
					Offlinetime = diff + " second";
				} else {
					Offlinetime = diff + "秒";
				}
			}
		}	
		return Offlinetime;		
	}
	
	
	private String getVolotageValue(String houseIEEE, String deviceIeee, String devcieEp, String lang) throws ParseException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		//int volotageState;
		Map<String, Object> rt = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> part = new HashMap<String, Object>(); 
		String tableName = "deviceAttributeHistory_" + houseIEEE + "_" + year;	
		StringBuffer hww = new StringBuffer();
		Map<String, Object> ppt = new HashMap<String, Object>();
		hww.append("SELECT  isTableExist('"+ tableName +"') as sun  "); 		
 		List<Map> l = mapDao.executeSql(hww.toString()); 
 		String tt = l.get(0).get("sun") .toString();
 		if (tt.equals("1")) {
 		String volotageState = "";
 		StringBuffer sql = new StringBuffer();
		sql.append("select f.value,f.cluster_id from ").append( tableName).append(" f  where 1=1 ");
		//sql.append(" LEFT JOIN modenodelib m on m.modelId = t.modelId and f.cluster_id = m.clusterId  where 1=1 ");
		if (StringUtils.isNotBlank(houseIEEE)) {
			sql.append(" and f.houseIEEE =:houseIeee ");
			params.put("houseIeee", houseIEEE);
		}
		if (StringUtils.isNotBlank(deviceIeee)) {
			sql.append(" and f.device_ieee =:deviceIeee ");
			params.put("deviceIeee", deviceIeee);
		}
		if (StringUtils.isNotBlank(devcieEp)) {
			sql.append(" and f.device_ep =:deviceEp ");
			params.put("deviceEp",devcieEp);
		}
		sql.append("and f.cluster_id IN('0500','0001') ");
		sql.append(" order by f.opdatetime desc limit 1; ");
		//log.info("得到的电压电量的sql==--->:"+ sql);
		List<Map> list = mapDao.executeSql(sql.toString(), params);	
		if(list.size() != 0) {
			String clusterId = list.get(0).get("cluster_id")==null?"":list.get(0).get("cluster_id").toString();
			String value = list.get(0).get("value")==null?"":list.get(0).get("value").toString();
			if("0500".equals(clusterId) && !"".equals(value)) {
				  String[] s = new String[5];
				  s = value.split("");
				  int s1 = Integer.parseInt(s[1],16); 
				  int s2 = Integer.parseInt(s[2],16);
				  if((s1 == 0) && (s2 == 0)) {//直插电源
					  volotageState = "100%";
				  }else {//计算电量
					  int res = s1*16 + s2; 
					  volotageState = res + "%";
				  }			  
			}else if("0001".equals(clusterId) && !"".equals(value)) {
				volotageState =value + "V";
			}else {
				volotageState = "---";
			}
			    return volotageState;
		}else {
			 volotageState = "---";
			 return volotageState;
		}	
     }else{
	    String volotageState = "---";
	    return volotageState;
 	 }
   }
		
	@Override
	public List<Map> getfindlist(Map params) throws Exception {
		//StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.modelId,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode,n.nodeType,t.unit_type,t.deviceattribute,m.voltagestate,m.clusterId,t.lasttime from house h left join device t on h.houseIeee=t.houseIEEE left join node n on t.houseIEEE=n.houseIEEE and t.ieee=n.ieee left join modenodelib m on m.modelId = t.modelId where 1=1 ");
		//StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.modelId,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode,t.deviceattribute,m.voltagestate,m.clusterId,t.lasttime from house h left join device t on h.houseIeee=t.houseIEEE left join modenodelib m on m.modelId = t.modelId where 1=1 ");
		StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.modelId,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode,t.lasttime from house h left join device t on h.houseIeee=t.houseIEEE where 1=1 ");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (StringUtils.isNotBlank((String)params.get("houseIeee"))) {			
			sql.append(" and h.houseIeee =:houseIeee ");
			params2.put("houseIeee", params.get("houseIeee"));
		}
		if (StringUtils.isNotBlank((String)params.get("deviceName")) && StringUtils.isBlank((String)params.get("optype"))) {
			sql.append("and t.deviceName like :deviceName ");
			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
		}
		if (StringUtils.isNotBlank((String)params.get("optype")) && "-99".equals(params.get("optype"))) {  // 设备名称模糊查询deviceName或ieee
		    sql.append(" and (t.ieee like :ieee");
			sql.append(" or t.deviceName like :deviceName) ");
			params2.put("ieee", "%" + params.get("deviceName")+ "%");
			params2.put("deviceName", "%" + params.get("deviceName")+ "%");
		}		
		String orderBy = (String) params.get("orderBy");	
		if (StringUtils.isNotBlank(orderBy) && !(orderBy.equals("t.difftime desc") || orderBy.equals("t.difftime asc"))) {
			sql.append(" order by ").append(orderBy);
		}else if (orderBy.equals("t.difftime asc")) {
			sql.append(" order by t.lasttime asc");
		}else if (orderBy.equals("t.difftime desc")) {
			sql.append(" order by t.lasttime desc");
		}else {
			sql.append(" order by t.isonline desc");
		}			
		if (params.get("pageSize") != null) {
			sql.append(" limit " + params.get("startRow") + "," + params.get("pageSize"));
		} 
		//log.info("获取的二级查询SQL语句是：----》："+ sql);
		List<Map> list = mapDao.executeSql(sql.toString(), params2);
		String lang = params.get("lang")==null?"2":(String)params.get("lang");
		String offtime = params.get("offtime")==null?"":(String)params.get("offtime");
		int length = list.size();
		String voltagestate="";String lqi="";String lqi2="";String rssi="";String rssi2="";String tt="";		
		if (list != null) {
			for(int i = 0; i < length; i ++) {
			    Map map1 = list.get(i);
				String isonline = (String)map1.get("isonline");
				String isonline2 = (String)map1.get("isonline2");
			//	String clusterId = (String)map1.get("clusterId")==null?"":(String)map1.get("clusterId");
				Date now = new Date();
				Date lasttime1 = (Date)map1.get("lasttime");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
				String lasttime = sdf.format(lasttime1);
				String currtime = sdf.format(now);
				String ieee = (String)map1.get("ieee"); 
				String houseIeee =  (String)map1.get("houseIeee");				
				String eq =  (String)map1.get("ep");
	
				voltagestate = getVolotageValue(houseIeee, ieee, eq, lang);
				
				if ((isonline.equals("1") && isonline2.equals("1") || (isonline.equals("2") && isonline2.equals("1")))) {
					tt = "0";
				}else {
					tt = OfflineDuration(houseIeee, ieee, lasttime1, now, lang, offtime); //离线时长
				}
				
				if (houseIeee.equals(ieee)) {
					if("1".equals(lang)){
						lqi = "None";    lqi2 = "None";
						rssi = "None";   rssi2 = "None";	
					}else{
						lqi = "无"; 		 lqi2 = "无";
						rssi = "无";		 rssi2 = "无";
					}
				}else {
					Map t = getLR(houseIeee, ieee, eq, lang);
					Map t2 = getLR2(isonline,isonline2,houseIeee, ieee, eq, lang);
					lqi = (String)t2.get("lqi1");
					lqi2 = (String)t2.get("lqi2");
					rssi = (String)t.get("rssi1");
					rssi2 = (String)t.get("rssi2");	
				}				
			    map1.put("voltage", voltagestate);
				map1.put("difftime", tt);
				map1.put("lqi", lqi);
				map1.put("lqi2", lqi2);
				map1.put("rssi", rssi);
				map1.put("rssi2", rssi2);				
			}
			return list;
		}
		return null;
	}
	@Override
	public int exportDeviceExcel(Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		//StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.modelId,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode,n.nodeType,t.unit_type,t.deviceattribute,m.voltagestate,m.clusterId,t.lasttime  from house h left join device t on h.houseIeee=t.houseIEEE left join node n on t.houseIEEE=n.houseIEEE and t.ieee=n.ieee left join modenodelib m on m.modelId = t.modelId where 1=1 ");
		StringBuffer sql = new StringBuffer("select t.id,t.houseIEEE houseIeee,t.modelId,t.deviceName,t.ep,t.isonline,t.ieee,h.isonline as isonline2,t.datecode,t.unit_type,t.lasttime  from house h left join device t on h.houseIeee=t.houseIEEE where 1=1 ");
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (StringUtils.isNotBlank((String)params.get("houseIEEE"))){
			sql.append(" and h.houseIeee = :houseIeee ");
			params2.put("houseIeee", params.get("houseIEEE"));
		}
		if (StringUtils.isNoneBlank((String)params.get("deviceIeee"))) {
			sql.append(" and t.ieee =:deviceIeee ");
			params2.put("deviceIeee",params.get("deviceIeee"));
		}
		if (StringUtils.isNotBlank((String)params.get("deviceEp"))) {
			sql.append(" and t.ep =:deviceEp ");
			params2.put("deviceEp",params.get("deviceEp"));
		}
		String orderBy = (String) params.get("orderBy");
		if (StringUtils.isNotBlank(orderBy)) {
			sql.append(" order by ").append(orderBy);
		}else {
			sql.append(" order by t.isonline asc,t.lasttime desc");
		}
		if (params.get("pageSize") != null) {
			sql.append(" limit " + params.get("startRow") + "," + params.get("pageSize"));
		} 		
		DataGrid dg = new DataGrid();		
		List<Map> t = mapDao.executeSql(sql.toString(), params2);
		BufferedOutputStream os = null;
		String lang = params.get("lang")==null?"2":(String)params.get("lang");
		String offtime = params.get("offtime")==null?"":(String)params.get("offtime");
		try {
			if (params.get("lang").equals("1")) { //英文 
				//生成excel文件
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				sheet.setColumnWidth(1, 3500);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(5, 6000);
				workbook.setSheetName(0, "Device List");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Status");
				cell.setCellStyle(titleStyle);
				
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("ModelID");
				cell.setCellStyle(titleStyle);
				
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Device name");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Device IEEE and Ep");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Date");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Voltage/Power");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Offline length");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)7, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("LQI");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)8, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Last LQI>100");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)9, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("RSSI");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)10, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("Last time RSSI>-70");
				cell.setCellStyle(titleStyle);
								
				int monlogsize = t.size();
				for (int i = 0; i < monlogsize; i ++) {
					row = sheet.createRow(i + 1);
					
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					if ((t.get(i).get("isonline2").equals("1") && t.get(i).get("isonline").equals("1")) || (t.get(i).get("isonline2").equals("1") && t.get(i).get("isonline").equals("2"))) {
						cell.setCellValue("Online");
					}else {
							cell.setCellValue("Offline");
					}
					
					cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("modelId").toString());
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					if (t.get(i).get("deviceName")==null || t.get(i).get("deviceName").equals("")) {
						cell.setCellValue("None");
					}else {
						cell.setCellValue(t.get(i).get("deviceName").toString());
					}
					
					cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
					if (t.get(i).get("ep")==null || t.get(i).get("ep").equals("")) {
						cell.setCellValue(t.get(i).get("ieee").toString());
					}else{
						cell.setCellValue(t.get(i).get("ieee").toString() + "_" + t.get(i).get("ep").toString() );
					}
					
					cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
					if (t.get(i).get("datecode")==null || t.get(i).get("datecode").equals("")) {
						cell.setCellValue("None");
					}else{
						cell.setCellValue(t.get(i).get("datecode").toString());
					}					
					
					cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
					String voltagestate = "";
					String clusterID = (String)t.get(i).get("clusterId")==null?"":(String)t.get(i).get("clusterId");
					
					voltagestate = getVolotageValue(t.get(i).get("houseIeee").toString(), t.get(i).get("ieee").toString(), t.get(i).get("ep").toString(), lang);
					cell.setCellValue(voltagestate);
					
					cell = row.createCell(6, HSSFCell.CELL_TYPE_STRING);
					String tt ="";
					Date now = new Date();
					Date lasttime1 = (Date)t.get(i).get("lasttime");
					
					if ((t.get(i).get("isonline2").equals("1") && t.get(i).get("isonline").equals("1")) || (t.get(i).get("isonline2").equals("1") && t.get(i).get("isonline").equals("2"))) {
						tt = "0";
					}else {
						tt = OfflineDuration(t.get(i).get("houseIeee").toString(), t.get(i).get("ieee").toString(), lasttime1, now, lang, offtime);
					}									
					cell.setCellValue(tt);
					
					String lqi="";String lqi2="";String rssi="";String rssi2="";	
					if (t.get(i).get("houseIeee").equals(t.get(i).get("ieee"))) {
						lqi ="None";	lqi2 ="None";
						rssi ="None";   rssi2 ="None";
					}else {
						Map list = getLR(t.get(i).get("houseIeee").toString(),t.get(i).get("ieee").toString(),t.get(i).get("ep").toString(), lang);
						Map list2 = getLR2(t.get(i).get("isonline").toString(),t.get(i).get("isonline2").toString(),t.get(i).get("houseIeee").toString(),t.get(i).get("ieee").toString(),t.get(i).get("ep").toString(),lang);
						lqi =(String)list2.get("lqi1");
						lqi2 =(String)list2.get("lqi2");
						rssi =(String)list.get("rssi1");
						rssi2 =(String)list.get("rssi2");
					}					
					cell = row.createCell(7, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(lqi);
					cell = row.createCell(8, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(lqi2);
					cell = row.createCell(9, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(rssi);
					cell = row.createCell(10, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(rssi2);	
				}
				//设置response参数
				//对中文字符转码
				String fileName = "Device List.xls";		
				fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
				response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//				response.addHeader("Content-Disposition", "inline;filename=" + fileName);
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				os = new BufferedOutputStream(response.getOutputStream());
				workbook.write(os);
				os.flush();
			}else{						
				//生成excel文件
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				sheet.setColumnWidth(1, 3500);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(5, 6000);
				workbook.setSheetName(0, "设备列表");
				HSSFRow row = sheet.createRow((short)0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell((short)0, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("设备状态");
				cell.setCellStyle(titleStyle);
				
				cell = row.createCell((short)1, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("ModelID");
				cell.setCellStyle(titleStyle);
				
				cell = row.createCell((short)2, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("设备名称");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)3, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("设备IEEE地址与EP");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)4, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("软件日期");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)5, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("电压/电量");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)6, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("离线时长");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)7, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("LQI");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)8, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("上次LQI>100");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)9, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("RSSI");
				cell.setCellStyle(titleStyle);
				cell = row.createCell((short)10, HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("上次RSSI>-70");
				cell.setCellStyle(titleStyle);
				
				int monlogsize = t.size();
				for (int i = 0; i < monlogsize; i ++) {
					row = sheet.createRow(i + 1);
					
					cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
					if (t.get(i).get("isonline2").equals("1") && t.get(i).get("isonline").equals("1") || (t.get(i).get("isonline2").equals("1") && t.get(i).get("isonline").equals("2"))) {
						cell.setCellValue("在线");
					}else {
							cell.setCellValue("离线");
					}
					
					cell = row.createCell(1, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(t.get(i).get("modelId").toString());
					
					cell = row.createCell(2, HSSFCell.CELL_TYPE_STRING);
					if (t.get(i).get("deviceName")==null || t.get(i).get("deviceName").equals("")) {
						cell.setCellValue("无");
					}else {
						cell.setCellValue(t.get(i).get("deviceName").toString());
					}
					
					cell = row.createCell(3, HSSFCell.CELL_TYPE_STRING);
					if (t.get(i).get("ep")==null || t.get(i).get("ep").equals("")) {
						cell.setCellValue(t.get(i).get("ieee").toString());
					}else {
						cell.setCellValue(t.get(i).get("ieee").toString() + "_" + t.get(i).get("ep").toString() );
					}
					
					cell = row.createCell(4, HSSFCell.CELL_TYPE_STRING);
					if (t.get(i).get("datecode")==null || t.get(i).get("datecode").equals("")) {
						cell.setCellValue("无");
					}else {
						cell.setCellValue(t.get(i).get("datecode").toString());
					}
					cell = row.createCell(5, HSSFCell.CELL_TYPE_STRING);
					String voltagestate = getVolotageValue(t.get(i).get("houseIeee").toString(), t.get(i).get("ieee").toString(), t.get(i).get("ep").toString(), lang);
					cell.setCellValue(voltagestate);
					
					cell = row.createCell(6, HSSFCell.CELL_TYPE_STRING);
					String tt ="";
					Date now = new Date();
					Date lasttime1 = (Date)t.get(i).get("lasttime");					
					if ((t.get(i).get("isonline2").equals("1") && t.get(i).get("isonline").equals("1")) || (t.get(i).get("isonline2").equals("1") && t.get(i).get("isonline").equals("2"))) {
						tt = "0";
					}else {
						tt = OfflineDuration(t.get(i).get("houseIeee").toString(), t.get(i).get("ieee").toString(), lasttime1, now, lang, offtime);
					}									
					cell.setCellValue(tt);
					
					String lqi="";String lqi2="";String rssi="";String rssi2="";	
					if (t.get(i).get("houseIeee").equals(t.get(i).get("ieee"))) {
						lqi = "无";		lqi2 = "无";
						rssi = "无";		rssi2 = "无";
					}else {
						Map list = getLR(t.get(i).get("houseIeee").toString(),t.get(i).get("ieee").toString(),t.get(i).get("ep").toString(),lang);
						Map list2 = getLR2(t.get(i).get("isonline").toString(),t.get(i).get("isonline2").toString(),t.get(i).get("houseIeee").toString(),t.get(i).get("ieee").toString(),t.get(i).get("ep").toString(),lang);
						lqi = (String)list2.get("lqi1");
						lqi2 = (String)list2.get("lqi2");
						rssi = (String)list.get("rssi1");
						rssi2 = (String)list.get("rssi2");
					}
					
					cell = row.createCell(7, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(lqi);
					cell = row.createCell(8, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(lqi2);
					cell = row.createCell(9, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(rssi);
					cell = row.createCell(10, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(rssi2);	
				}
				//设置response参数
				//对中文字符转码
				String fileName = "设备列表.xls";		
				fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
				response.addHeader("Content-Disposition", "attachment;filename="+fileName);
	//			response.addHeader("Content-Disposition", "inline;filename=" + fileName);
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				os = new BufferedOutputStream(response.getOutputStream());
				workbook.write(os);
				os.flush();
		}
		} catch(Exception e) {
			e.printStackTrace();
			log.info("exportDeviceExcel", e);
		} finally {
			try {
				if (os != null)
					os.close();
			} catch(Exception e) {
//				e.printStackTrace();
				log.info("exportLqiLogExcel close BufferedOutputStream", e);
			}
		}
		return 0;
	}	
}