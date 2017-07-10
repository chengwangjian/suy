package sy.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Deviceattribute;
import sy.service.DeviceattributeServiceI;
import sy.util.PropertiesUtil;
import zbEnergy.model.Energyhistory;

@Service("deviceattributeService")
public class DeviceattributeServiceImpl implements DeviceattributeServiceI, ApplicationContextAware  {

	private static final Logger logger = Logger.getLogger(DeviceattributeServiceImpl.class);
	private ApplicationContext ac = null;
	private final int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.deviceattribute"));
	private int cacheCount = 0; //批量计数器
	private static SaveAttrThread thread = null;//保存属性的线程
	private BaseDaoI<Deviceattribute> deviceattributeDao;
	private Map<String, List<Deviceattribute>> cacheList = new HashMap<String, List<Deviceattribute>>();
	//缓存设备最新的属性值
	private Map<String, Map<String,Deviceattribute>> cacheNewestAttrs = new HashMap<String, Map<String, Deviceattribute>>();
	
	private BaseDaoI<Energyhistory> energyhistoryDao;
	
	static
	{
		try {
			Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public BaseDaoI<Deviceattribute> getDeviceattributeDao() {
		return deviceattributeDao;
	}

	@Autowired
	public void setDeviceattributeDao(BaseDaoI<Deviceattribute> deviceattributeDao) {
		this.deviceattributeDao = deviceattributeDao;
	}

	public BaseDaoI<Energyhistory> getEnergyhistoryDao() {
		return energyhistoryDao;
	}

	@Autowired
	public void setEnergyhistoryDao(BaseDaoI<Energyhistory> energyhistoryDao) {
		this.energyhistoryDao = energyhistoryDao;
	}
	@Override
	public Deviceattribute save(Deviceattribute deviceattribute) {
		/*Deviceattribute t = new Deviceattribute();
		BeanUtils.copyProperties(deviceattribute, t, new String[] { "pwd" });*/
		/*t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(deviceattribute.getPwd()));*/
		deviceattributeDao.save(deviceattribute);
//		BeanUtils.copyProperties(t, deviceattribute);
		return deviceattribute;
	}

	@Override
	public Deviceattribute get(Deviceattribute deviceattribute) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", deviceattribute.getHouseIeee());
//		params.put("roomId", deviceattribute.getRoomId());
		params.put("deviceIeee", deviceattribute.getDeviceIeee());
		params.put("deviceEp", deviceattribute.getDeviceEp());
		params.put("clusterId", deviceattribute.getClusterId());
		params.put("attributeId", deviceattribute.getAttributeId());
		/*Deviceattribute t = deviceattributeDao.get("from Deviceattribute t where t.houseIeee = :houseIeee " +
				"and t.roomId = :roomId and t.deviceIeee = :deviceIeee and t.deviceEp = :deviceEp and t.clusterId = :clusterId" +
				" and t.attributeId = :attributeId", params);*/
		// HouseIEEE+Devcie_IEEE+Device_EP+ClusterID+Attribute_ID确定一个属性
		Deviceattribute t = deviceattributeDao.get("from Deviceattribute t where t.houseIeee = :houseIeee " +
				"and t.deviceIeee = :deviceIeee and t.deviceEp = :deviceEp and t.clusterId = :clusterId" +
				" and t.attributeId = :attributeId", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public int addEnergyhistory(Deviceattribute deviceattribute) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", deviceattribute.getHouseIeee());
		params.put("deviceIeee", deviceattribute.getDeviceIeee());
		params.put("deviceEp", deviceattribute.getDeviceEp());
		params.put("opTime", new Date());
		params.put("energyValue", deviceattribute.getValue());
		// 调用存储过程
		String sql = "{CALL EnergyCalculate(:houseIeee,:deviceIeee,:deviceEp,:opTime,:energyValue)}";			
		return deviceattributeDao.executeSql2(sql, params);
	}
	
	public int addEnergyhistoryBatch(List<Deviceattribute> attrList){
		int num = 0;
		for(Deviceattribute deviceattribute:attrList){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("houseIeee", deviceattribute.getHouseIeee());
			params.put("deviceIeee", deviceattribute.getDeviceIeee());
			params.put("deviceEp", deviceattribute.getDeviceEp());
			params.put("opTime", new Date());
			params.put("energyValue", deviceattribute.getValue());
			// 调用存储过程
			String sql = "{CALL EnergyCalculate(:houseIeee,:deviceIeee,:deviceEp,:opTime,:energyValue)}";			
			num += deviceattributeDao.executeSql2(sql, params);
		}
		return num;
	}
	
	/**
	 * 批量提交设备属性历史,表是动态创建的
	 * @author: hlw
	 * 时间：2014-9-10 下午2:33:44
	 * @param tmpList
	 * @return
	 */
	@Override
	public int saveDeviceattribute(Deviceattribute deviceattribute1,List<Deviceattribute> deviceAttrList) {
		if(deviceattribute1!=null){
			return addAttrCache(deviceattribute1);
		}else if(deviceAttrList!=null){
			int num = 0;
			for(Deviceattribute attr:deviceAttrList){
				num += addAttrCache(attr);
			}
			return num;
		}else{
			return -1;
		}
		
	}
	/**
	 * 将属性缓存
	 * @param deviceattribute1 属性
	 * @return
	 */
	private int addAttrCache(Deviceattribute deviceattribute1){
		++cacheCount;
		//logger.info("deviceattribute_count=======:" + cacheCount);
		String houseIeee = deviceattribute1.getHouseIeee();
		List<Deviceattribute> tclList = cacheList.get(houseIeee);
		//缓存最新的属性
		Map<String, Deviceattribute> attrMap = cacheNewestAttrs.get(houseIeee);
		if(attrMap == null) {
			attrMap = new HashMap<String, Deviceattribute>();
			attrMap.put(houseIeee + "_" + deviceattribute1.getDeviceIeee() + "_" + deviceattribute1.getDeviceEp() + "_" 
			+ deviceattribute1.getClusterId() + "_" + deviceattribute1.getAttributeId(), deviceattribute1);
			cacheNewestAttrs.put(houseIeee, attrMap);
		}
		else {
			attrMap.put(houseIeee + "_" + deviceattribute1.getDeviceIeee() + "_" + deviceattribute1.getDeviceEp() + "_" 
					+ deviceattribute1.getClusterId() + "_" + deviceattribute1.getAttributeId(), deviceattribute1);
		}
		//缓存属性
		if(tclList == null) {
			tclList = new ArrayList<Deviceattribute>(this.cacheSize + 10);
			tclList.add(deviceattribute1);
			cacheList.put(houseIeee, tclList);
		}
		else {
			tclList.add(deviceattribute1);
		}
		if(thread==null){
			logger.info("保存属性线程启动······");
			thread = this.new SaveAttrThread();
			thread.start();
		}
		//批量更新
		/*if (cacheCount >= this.cacheSize) {
			synchronized (thread) {
				logger.info("线程唤醒,存入数据");
				thread.notify();
			}
		}*/
		return 1;
	}
	
	@Override
	public int addEnergyhistoryTest(Deviceattribute deviceattribute) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", deviceattribute.getHouseIeee());
		params.put("deviceIeee", deviceattribute.getDeviceIeee());
		params.put("deviceEp", deviceattribute.getDeviceEp());
		params.put("opTime", deviceattribute.getLasttime());
		params.put("energyValue", deviceattribute.getValue());
		// 调用存储过程
		String sql = "{CALL EnergyCalculate(:houseIeee,:deviceIeee,:deviceEp,:opTime,:energyValue)}";			
		return deviceattributeDao.executeSql2(sql, params);
	}
	
	@Override
	public List<Deviceattribute> findList(Deviceattribute deviceattribute) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Deviceattribute t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (deviceattribute.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", deviceattribute.getHouseIeee());
		}
		if (deviceattribute.getRoomId() != 0) {
			hql.append("and t.roomId = :roomId ");
			params.put("roomId", deviceattribute.getRoomId());
		}
		if (deviceattribute.getDeviceIeee() != null) {
			hql.append("and t.deviceIeee = :deviceIeee ");
			params.put("deviceIeee", deviceattribute.getDeviceIeee());
		}
		if (deviceattribute.getDeviceEp() != null) {
			hql.append("and t.deviceEp = :deviceEp ");
			params.put("deviceEp", deviceattribute.getDeviceEp());
		}
		if (deviceattribute.getClusterId() != null) {
			hql.append("and t.clusterId = :clusterId ");
			params.put("clusterId", deviceattribute.getClusterId());
		}
		if (deviceattribute.getAttributeId() != null) {
			hql.append("and t.attributeId = :attributeId ");
			params.put("attributeId", deviceattribute.getAttributeId());
		}
		if (deviceattribute.getAttributeName() != null) {
			hql.append("and t.attributeName like :attributeName ");
			params.put("attributeName", "%" +deviceattribute.getAttributeName() + "%");
		}
		/*for (int j=1;j<=12;j++) {
			for (int k=1;k<=30;k++) {
				for (int i=0;i<=24;i++) {
					String datetime = "2013-" + compartTwo(j) + "-" + compartTwo(k) + " " + compartTwo(i) + ":00:00";
					Energyhistory energyhistory = new Energyhistory(); 
					energyhistory.setHouseIeee("00137A0000008131");
					energyhistory.setIeee("00137A000000C03C");
					energyhistory.setEp("01");
					energyhistory.setEnergyPrice(99.08);
					energyhistory.setEnergyValue(55.68);
					try {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                	energyhistory.setOpdatetime(df.parse(datetime));
	                	energyhistoryDao.save(energyhistory);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}        		
		}*/
		List<Deviceattribute> t = deviceattributeDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	public String compartTwo(int i) {
		if (String.valueOf(i).length() < 2) {
			return "0" + i;
		} else {
			return String.valueOf(i);
		}
	}
	
	@Override
	public Deviceattribute update(Deviceattribute deviceattribute) {
		deviceattributeDao.update(deviceattribute);
		return deviceattribute;
	}
	
	@Override
	public int delete(Deviceattribute deviceattribute) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Deviceattribute t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (deviceattribute.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", deviceattribute.getHouseIeee());
		}
		if (deviceattribute.getRoomId() != 0) {
			hql.append("and t.roomId = :roomId ");
			params.put("roomId", deviceattribute.getRoomId());
		}
		if (deviceattribute.getDeviceIeee() != null) {
			hql.append("and t.deviceIeee = :deviceIeee ");
			params.put("deviceIeee", deviceattribute.getDeviceIeee());
		}
		if (deviceattribute.getDeviceEp() != null) {
			hql.append("and t.deviceEp = :deviceEp ");
			params.put("deviceEp", deviceattribute.getDeviceEp());
		}
		if (deviceattribute.getClusterId() != null) {
			hql.append("and t.clusterId = :clusterId ");
			params.put("clusterId", deviceattribute.getClusterId());
		}
		if (deviceattribute.getAttributeId() != null) {
			hql.append("and t.attributeId = :attributeId ");
			params.put("attributeId", deviceattribute.getAttributeId());
		}
		if (deviceattribute.getAttributeName() != null) {
			hql.append("and t.attributeName like :attributeName ");
			params.put("attributeName", "%" +deviceattribute.getAttributeName() + "%");
		}
		return deviceattributeDao.executeHql(hql.toString(), params);
	}

/*	@Override
	public DataGrid datagrid(Deviceattribute deviceattribute) {
		DataGrid dg = new DataGrid();
		String hql = "from Deviceattribute t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(deviceattribute, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(deviceattribute, hql);
		List<Deviceattribute> l = deviceattributeDao.find(hql, params, deviceattribute.getPage(), deviceattribute.getRows());
		List<Deviceattribute> nl = new ArrayList<Deviceattribute>();
		changeModel(l, nl);
		dg.setTotal(deviceattributeDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Deviceattribute> l, List<Deviceattribute> nl) {
		if (l != null && l.size() > 0) {
			for (Deviceattribute t : l) {
				Deviceattribute u = new Deviceattribute();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Deviceattribute deviceattribute, String hql) {
		/*if (deviceattribute.getSort() != null) {
			hql += " order by " + deviceattribute.getSort() + " " + deviceattribute.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Deviceattribute deviceattribute, String hql, Map<String, Object> params) {
		/*if (deviceattribute.getName() != null && !deviceattribute.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + deviceattribute.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Deviceattribute t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		deviceattributeDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}

	public void tmpInsertData(String sqlStr, Statement stmt,String houseIeee) {
		if(StringUtils.isNotBlank(sqlStr)) {
			try {
				stmt .executeUpdate(sqlStr);
			} catch(Exception e) {
				logger.info("save or update DeviceattributehistoryHouseidYear Repeat exception", e);
			} 
		}
	}
	
	/*
	 * 定时执行保存设备属性接口
	 */
	@Override
	public void saveAttrCache() {
		logger.info("定时保存属性begin...");
		if(this.cacheList.isEmpty()) {
			logger.info("定时保存属性，缓存为空");
			return;
		}
		if(thread==null){
			logger.info("保存属性线程启动······");
			thread = this.new SaveAttrThread();
			thread.start();
		}
		synchronized (thread) {
			thread.notify();
		}
	}
	
	/*
	 * 定时保存设备最新的属性
	 */
	public void saveNewestAttrs() {
		if(this.cacheList.isEmpty()) {
			logger.info("--------no newest deviceattr to save");
			return;
		}
		logger.info("--------save deviceattrs");
		Map<String, Map<String, Deviceattribute>> tmpMap = this.cacheNewestAttrs;
		this.cacheNewestAttrs = new HashMap<String, Map<String, Deviceattribute>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Iterator<String> hIeeeItor = tmpMap.keySet().iterator();
		while(hIeeeItor.hasNext()) {
			String houseIeee = hIeeeItor.next();
			Collection<Deviceattribute> col = tmpMap.get(houseIeee).values();
			if(col.isEmpty()) {
				continue;
			}
			Iterator<Deviceattribute> itor = col.iterator();
			String tableName = "devicenewestattribute_" + houseIeee.toLowerCase();
			StringBuilder rSql = new StringBuilder("REPLACE INTO ");
			rSql.append(tableName).append("(houseIeee,room_Id,device_Ieee,device_Ep,cluster_Id,attribute_Id,attributeName,value,opdatetime) VALUES");
			StringBuilder paramSql = new StringBuilder();
			while(itor.hasNext()) {
				Deviceattribute dAttr = itor.next();
				paramSql.append("('").append(dAttr.getHouseIeee()).append("',").append(dAttr.getRoomId()).append(",'").append(dAttr.getDeviceIeee()).append("','")
				.append(dAttr.getDeviceEp()).append("','").append(dAttr.getClusterId()).append("','").append(dAttr.getAttributeId()).append("','")
				.append(dAttr.getAttributeName()).append("','").append(dAttr.getValue()).append("','").append(sdf.format(dAttr.getTime()==null?new Date():dAttr.getTime())).append("'),");
			}
			String rSqlStr = rSql.append(paramSql.deleteCharAt(paramSql.length() - 1)).toString();
			try {
				deviceattributeDao.executeSql2(rSqlStr);
			}
			catch(Exception e) {
				//当表不存在时创建并插入数据
				if(e.getMessage().indexOf(tableName.toLowerCase()) != -1) {
					String crSql = "Create TABLE if not exists " + tableName + " LIKE deviceattributehistory_houseid_year";
					deviceattributeDao.executeSql2(crSql);
					String indSql = "ALTER TABLE " + tableName + " ADD UNIQUE hieee_ieee_ep_cls_attr_unique(houseieee,device_ieee,device_ep,cluster_id,attribute_id);";
					deviceattributeDao.executeSql2(indSql);
					deviceattributeDao.executeSql2(rSqlStr);
				}
				else {
					logger.error("save newest attribute " + tableName + " exception", e);
				}
			}
		}
		
	}
	
	@Override
	public void saveDeviceAttr(Deviceattribute deviceattribute) throws Exception{
		Date time = deviceattribute.getTime();
		// 修改设备表最后更新时间、最后属性值
		String hql = "update Device " + " set deviceattribute=:deviceattribute,lasttime = :lasttime where " +
				"houseIeee = :houseIeee and ieee = :ieee and ep = :ep and houseIeee<>ieee";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", deviceattribute.getHouseIeee());
		params.put("ieee", deviceattribute.getDeviceIeee());
		params.put("ep", deviceattribute.getDeviceEp());
		params.put("lasttime", time==null?new Date():time);
		params.put("deviceattribute",deviceattribute.getAttributeName()+" : "+deviceattribute.getValue());
		deviceattributeDao.executeHql(hql, params);
	}
	
	@Override
	public void saveDeviceAttrBatch(List<Deviceattribute> attrList) throws Exception{
		for(Deviceattribute deviceattribute:attrList){
			Date time = deviceattribute.getTime();
			// 修改设备表最后更新时间、最后属性值
			String hql = "update Device " + " set deviceattribute=:deviceattribute,lasttime = :lasttime where " +
					"houseIeee = :houseIeee and ieee = :ieee and ep = :ep and houseIeee<>ieee";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("houseIeee", deviceattribute.getHouseIeee());
			params.put("ieee", deviceattribute.getDeviceIeee());
			params.put("ep", deviceattribute.getDeviceEp());
			params.put("lasttime", time==null?new Date():time);
			params.put("deviceattribute",deviceattribute.getAttributeName()+" : "+deviceattribute.getValue());
			deviceattributeDao.executeHql(hql, params);
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		this.ac = ac;
	}
	Connection conn = null;
	class SaveAttrThread extends Thread{
		private boolean exit = true;
		
		private DeviceattributeServiceI attrService;
		
		@Override
		public void run(){
			synchronized (this) {
				while(exit){
					/*if(System.currentTimeMillis() - currentTimes > 2*1000){
						logger.info("线程唤醒,开始存入设备属性······");
						this.notify();
					}else{*/
					try {
						//线程等待
						this.wait(30000);
					} catch (InterruptedException e3) {
						logger.error("thread wait exception", e3); 
					}
					//}
					if(attrService==null)
						attrService = (DeviceattributeServiceI) ac.getBean("deviceattributeService");
					logger.info("线程唤醒,开始存入设备属性,共"+cacheCount+"条数据");
					cacheCount = 0;
					Map<String, List<Deviceattribute>> tmpMap = cacheList;
					cacheList = new HashMap<String, List<Deviceattribute>>(cacheSize + 10);
					Connection conn = null;
					Statement stmt = null;
					try {
						Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
						conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
								PropertiesUtil.getProperty("jdbc.user"),
								PropertiesUtil.getProperty("jdbc.password"));
						conn.setAutoCommit(false);
						stmt = conn.createStatement();
						logger.info("第一步-------------");
						List<Deviceattribute> tmpCacheList = new ArrayList<Deviceattribute>(cacheSize);
						Iterator<String> itor = tmpMap.keySet().iterator();
						while(itor.hasNext()) {
							String houseIeee1 = itor.next();
							List<Deviceattribute> dList = tmpMap.get(houseIeee1);
							if(dList != null && !dList.isEmpty()) {
								for(Deviceattribute deviceattribute: dList) {	 
									try {
										if (deviceattribute.getClusterId().equals("0702") && deviceattribute.getAttributeId().equals("E003")) { 
											// 电能属性
											attrService.addEnergyhistory(deviceattribute);
										}
									} catch (Exception e) {
										logger.error("add energy history exception", e);
									}
									Deviceattribute t = attrService.get(deviceattribute);
									if (t == null) {
										attrService.save(deviceattribute);
									} else {
										t.setValue(deviceattribute.getValue());
										t.setLasttime(new Date());
										tmpCacheList.add(t);
									}
									attrService.saveDeviceAttr(deviceattribute);
								}
							}
						}
						
						if(!tmpCacheList.isEmpty()) {
							logger.info("第二步-------------");
							StringBuilder sql3 = new StringBuilder("REPLACE INTO ");
							sql3.append("Deviceattribute");
							sql3.append("(id,houseIeee,room_Id,device_Ieee,device_Ep,cluster_Id,attribute_Id,attributeName,value,lasttime) VALUES");
							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							for(Deviceattribute dv: tmpCacheList) {	  
								Date time = dv.getTime();
								sql3.append("(").append(dv.getId()).append(",'").append(dv.getHouseIeee()).append("',")
								.append(dv.getRoomId()).append(",'").append(dv.getDeviceIeee()).append("','").append(dv.getDeviceEp())
								.append("','").append(dv.getClusterId()).append("','").append(dv.getAttributeId()).append("','")
								.append(dv.getAttributeName()).append("','").append(dv.getValue()).append("','")
								.append(time==null?sdf2.format(dv.getLasttime()):sdf2.format(time)).append("'),");	    				
							}
							String sqlStr3 = sql3.deleteCharAt(sql3.length() - 1).toString();
							stmt.executeUpdate(sqlStr3);
						}
						Iterator<String> itor1 = tmpMap.keySet().iterator();
						logger.info("第三步-------------");
						while(itor1.hasNext()) {
							final String houseIeee = itor1.next();
							final List<Deviceattribute> dList = tmpMap.get(houseIeee); 						
							int year = Calendar.getInstance().get(Calendar.YEAR);
							final String tableName = "deviceAttributeHistory_" + houseIeee + "_" + year;
							StringBuilder sql = new StringBuilder("insert into ");
							sql.append(tableName);
							sql.append("(houseIeee,room_Id,device_Ieee,device_Ep,cluster_Id,attribute_Id,attributeName,value,opdatetime) VALUES");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							for(Deviceattribute dv: dList) {
								Date time = dv.getTime();
								sql.append("('").append(dv.getHouseIeee()).append("',")
								.append(dv.getRoomId()).append(",'").append(dv.getDeviceIeee()).append("','").append(dv.getDeviceEp())
								.append("','").append(dv.getClusterId()).append("','").append(dv.getAttributeId()).append("','")
								.append(dv.getAttributeName()).append("','").append(dv.getValue()).append("','")
								.append(time==null?sdf.format(dv.getLasttime()):sdf.format(time)).append("'),");	    				
							}
							String sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
							try {
								stmt.executeUpdate(sqlStr);
							} catch(Exception e) {
								String msg = e.getMessage();
								//当表不存在时创建并插入数据
								if(msg.indexOf(tableName.toLowerCase()) != -1) {
									String sql2 = "Create TABLE if not exists " + tableName + " LIKE deviceattributehistory_houseid_year";
									try {
										stmt.executeUpdate(sql2);
									} catch (Exception e2) {
										logger.error("create table error", e2);
									}
									tmpInsertData(sqlStr, stmt,houseIeee);
								}
								else
									logger.info("save or update DeviceattributehistoryHouseidYear " + houseIeee + " exception", e);
							} 
						}
						logger.info("第四步-------------");
						conn.commit();
					} catch (Exception e) {
						logger.info("save or update DeviceattributehistoryHouseidYear exception", e);
						try {
							conn.rollback();
						} catch (SQLException e1) {
							logger.error("save deviceattribute rollback exception" , e1);
						}
					} finally {
						try {
							conn.setAutoCommit(true);
						} catch (SQLException e) {
							logger.error("set sql conn auto commit true exception", e);
						}
						try {
							if(stmt != null)
								stmt.close();
						} catch(Exception e) {
							logger.info("stmt close exception", e);
						}finally{
							try {
								if(conn != null)
									conn.close();
							} catch (SQLException e) {
								logger.error("conn close exception",e);
							}
						}
					}
					logger.info("定时保存属性end...");
				}
			}
		}
	}
}
