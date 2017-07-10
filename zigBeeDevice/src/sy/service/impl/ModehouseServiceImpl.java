package sy.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.Modedevice;
import sy.model.Modehouse;
import sy.model.Modenode;
import sy.model.Moderoom;
import sy.pageModel.ModehouseAndRoom;
import sy.service.ModehouseServiceI;


@Service("modehouseService")
public class ModehouseServiceImpl implements ModehouseServiceI {

	private static final Logger logger = Logger.getLogger(ModehouseServiceImpl.class);

	private BaseDaoI<Modehouse> modehouseDao;
	private BaseDaoI<Moderoom> moderoomDao;
	private BaseDaoI<Modedevice> modedeviceDao;
	private BaseDaoI<Modenode> modenodeDao;
	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<Modehouse> getModehouseDao() {
		return modehouseDao;
	}

	@Autowired
	public void setModehouseDao(BaseDaoI<Modehouse> modehouseDao) {
		this.modehouseDao = modehouseDao;
	}
	
	public BaseDaoI<Moderoom> getModeroomDao() {
		return moderoomDao;
	}

	public BaseDaoI<Modedevice> getModedeviceDao() {
		return modedeviceDao;
	}

	@Autowired
	public void setModedeviceDao(BaseDaoI<Modedevice> modedeviceDao) {
		this.modedeviceDao = modedeviceDao;
	}

	public BaseDaoI<Modenode> getModenodeDao() {
		return modenodeDao;
	}

	@Autowired
	public void setModenodeDao(BaseDaoI<Modenode> modenodeDao) {
		this.modenodeDao = modenodeDao;
	}

	@Autowired
	public void setModeroomDao(BaseDaoI<Moderoom> moderoomDao) {
		this.moderoomDao = moderoomDao;
	}

	@Override
	public Modehouse save(Modehouse modehouse) {
		modehouseDao.save(modehouse);
		return modehouse;
	}
	
	@Override
	public int addModehouseAndRoom(ModehouseAndRoom modehouseAndRoom) {
				
		StringBuffer hql = new StringBuffer();
		List<Moderoom> moderoom = modehouseAndRoom.getModeroom();
		modehouseAndRoom.getModehouse().setRoomNums(moderoom.size());
		Modehouse modehouse = modehouseAndRoom.getModehouse();
		hql.append("delete Modehouse t where ");
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("t.userId = :userId ");
		params.put("userId", modehouse.getUserId());
//		modehouseDao.executeHql(hql.toString(), params);
		Modehouse t = get(modehouse);
		long houseId = 0;
		if (t != null) {
			return -1;
		} else {
			modehouseDao.save(modehouseAndRoom.getModehouse());
			houseId = modehouseAndRoom.getModehouse().getId();
		}
//		moderoomDao.executeHql("delete Moderoom t where t.userId = :userId ", params);	
		
		Moderoom moderoom2 = new Moderoom();
		moderoom2.setRoomId(-1);
		if("1".equals(modehouseAndRoom.getLanguage())){
			moderoom2.setRoomName("Home");
		}
		else if("2".equals(modehouseAndRoom.getLanguage())||modehouseAndRoom.getLanguage()==null){
			moderoom2.setRoomName("家全局");
		}
		
		moderoom2.setUserId(modehouse.getUserId());
		moderoom2.setHouseId(houseId);
		moderoomDao.save(moderoom2);
		
//		// 家全局下默认有Z203，且不可删除
//		// 添加默认设备
//		Modenode modenode2 = new Modenode();
//		List<Modedevice> list2 = new ArrayList<Modedevice>();
//		modenode2.setHouseId(houseId);
//		modenode2.setRoomId(-1);
//		modenode2.setModeNodeLibId(86);
////		modenode2.setDeviceName("Z203云智能家居控制中心1");
//		modenode2.setDeviceName(PropertiesUtil.getProperty("Z203.deviceName"));
//		modenode2.setDescription("Z203 作为奈伯思智能家居系统的核心网关，率先实现云端技术、" +
//				"WiFi移动联网技术与ZigBee智能家居物联网术的完美结合，让用户只需要通过手机APP连接" +
//				"Wifi即可控制家里的灯光、窗帘、各种电器等设备的开关，" +
//				"出门在外通过2G/3G网络可监控家中的一切变化。");
//		modenodeDao.save(modenode2);
//		modenode2.setDeviceUniqueId("Z203" + modenode2.getId());
//		modenodeDao.update(modenode2);
//		Modedevice ob2 = new Modedevice();
//		ob2.setHouseId(modenode2.getHouseId());
//		ob2.setRoomId(modenode2.getRoomId());
//		ob2.setModeNodeId(modenode2.getId());
//		ob2.setDeviceId("0007");
//		ob2.setModelId("Z203");
//		ob2.setEp("0A");
//		ob2.setDeviceUniqueId(modenode2.getDeviceUniqueId() + ob2.getEp());
////		ob2.setDeviceName("Z203云智能家居控制中心1");
//		ob2.setDeviceName(PropertiesUtil.getProperty("Z203.deviceName"));
//		modedeviceDao.save(ob2);
		
		int roomNums = modehouse.getRoomNums();
		int roomId = 1;
		for(Moderoom ob1:moderoom){
			ob1.setUserId(modehouse.getUserId());
			ob1.setHouseId(houseId);
			if (ob1.getRoomId() == 0) {
				ob1.setRoomId(roomId);
				roomId++;
			} else {
				roomId++;
			}
			moderoomDao.save(ob1);
		}
		
		modehouseAndRoom.getModehouse().setId(houseId);
		return 1;
	}
	
	@Override
	public int updateModehouseAndRoom(ModehouseAndRoom modehouseAndRoom) {
				
		StringBuffer hql = new StringBuffer();
		List<Moderoom> moderoom = modehouseAndRoom.getModeroom();
//		modehouseAndRoom.getModehouse().setRoomNums(moderoom.size()-1);
		Modehouse modehouse = modehouseAndRoom.getModehouse();
		hql.append("delete Modehouse t where ");
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("t.userId = :userId ");
		params.put("userId", modehouse.getUserId());
//		modehouseDao.executeHql(hql.toString(), params);
		Modehouse t = get(modehouse);
		long houseId = 0;
		if (t != null) {
			t.setLasttime(new Date());
			t.setHouseName(modehouse.getHouseName());
			t.setRoomNums(modehouse.getRoomNums());
			modehouseDao.update(t);
			houseId = t.getId();
		} else {
		}
//		moderoomDao.executeHql("delete Moderoom t where t.userId = :userId ", params);		
		
		int roomNums = modehouse.getRoomNums();
		params = new HashMap<String, Object>();
		params.put("houseId", houseId);
		long roomId = moderoomDao.count("select max(roomId) from Moderoom t where t.houseId = :houseId", params);
		if (roomId == -1) {
			roomId = 0;
		}
		for(Moderoom ob1:moderoom){
			if (ob1.getRoomId() == -1) { // 家全局，不修改
				//continue;
			}
			ob1.setUserId(modehouse.getUserId());
			ob1.setHouseId(houseId);
			Moderoom t2 = getModeroom(ob1);
			if (t2 != null) {
				t2.setRoomName(ob1.getRoomName());
				t2.setLasttime(new Date());
				moderoomDao.update(t2);
			} else {
				if (ob1.getRoomId() == 0) {
					roomId++;
					ob1.setRoomId(roomId);
				} 
				moderoomDao.save(ob1);
			}
		}
		
		modehouseAndRoom.getModehouse().setId(houseId);
		return 1;
	}
	
	/*
	@Override
	public int saveOrUpdateModehouseAndRoom(ModehouseAndRoom modehouseAndRoom) {
				
		StringBuffer hql = new StringBuffer();
		Modehouse modehouse = modehouseAndRoom.getModehouse();
		hql.append("delete Modehouse t where ");
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("t.userId = :userId ");
		params.put("userId", modehouse.getUserId());
//		modehouseDao.executeHql(hql.toString(), params);
		Modehouse t = get(modehouse);
		long houseId = 0;
		if (t != null) {
			t.setLasttime(new Date());
			t.setHouseName(modehouse.getHouseName());
			t.setRoomNums(modehouse.getRoomNums());
			modehouseDao.update(t);
			houseId = t.getId();
		} else {
			modehouseDao.save(modehouseAndRoom.getModehouse());
			houseId = modehouseAndRoom.getModehouse().getId();
		}
//		moderoomDao.executeHql("delete Moderoom t where t.userId = :userId ", params);		
		
		List<Moderoom> moderoom = modehouseAndRoom.getModeroom();
		int roomNums = modehouse.getRoomNums();
		int roomId = 1;
		if (t != null) {
			roomId = moderoom.size();
		}
		for(Moderoom ob1:moderoom){
			ob1.setUserId(modehouse.getUserId());
			ob1.setHouseId(houseId);
			Moderoom t2 = getModeroom(ob1);
			if (t2 != null) {
				t2.setRoomName(ob1.getRoomName());
				t2.setLasttime(new Date());
				moderoomDao.update(t2);
			} else {
				if (ob1.getRoomName().contains("家") || ob1.getRoomName().contains("home")) {
					ob1.setRoomId(-1);
				}
				if (ob1.getRoomId() == 0) {
					ob1.setRoomId(roomId);
					roomId++;
				}
				moderoomDao.save(ob1);
			}
		}
		
		modehouseAndRoom.getModehouse().setId(houseId);
		return 1;
	}
	*/
	
	@Override
	public Map findModehouseAndRoom(ModehouseAndRoom modehouseAndRoom) {

		String hql = "";
		Modehouse modehouse = modehouseAndRoom.getModehouse();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseId", modehouse.getId());
		Map<String, Object> rt = new HashMap<String, Object>();
		hql = "from Modehouse t where t.id = :houseId ";
		List<Modehouse> list = modehouseDao.find(hql,params);
		if(list != null && list.size() > 0)
			rt.put("modehouse",list.get(0));
		else
			rt.put("modehouse",null);
//		hql = "from Moderoom t where t.houseId = :houseId order by t.roomId asc";
		hql = "from Moderoom t where t.houseId = :houseId order by t.roomId asc";
		List<Moderoom> list2 = moderoomDao.find(hql,params);
		if(list2 != null && list2.size() > 0)
			rt.put("moderoom",list2);
		else
			rt.put("moderoom",null);
		
		return rt;
	}
	
	@Override
	public Map findModehouse(ModehouseAndRoom modehouseAndRoom) {

		String hql = "";
		Modehouse modehouse = modehouseAndRoom.getModehouse();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", modehouse.getUserId());
		Map<String, Object> rt = new HashMap<String, Object>();
		hql = "from Modehouse t where t.userId = :userId ";
		List<Modehouse> list = modehouseDao.find(hql,params);
		if(list != null && list.size() > 0)
			rt.put("modehouse",list);
		else
			rt.put("modehouse",null);
		
		return rt;
	}
	
	@Override
	public Modehouse update(Modehouse modehouse) {
		modehouseDao.update(modehouse);
		return modehouse;
	}
	
	@Override
	public Modehouse get(Modehouse modehouse) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", modehouse.getUserId());
		params.put("houseName", modehouse.getHouseName());
		Modehouse t = modehouseDao.get("from Modehouse t where t.userId = :userId " +
				" and t.houseName = :houseName ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Modehouse getModehouse(Modehouse modehouse) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modehouse.getId());
		Modehouse t = modehouseDao.get("from Modehouse t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Moderoom getModeroom(Moderoom moderoom) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseId", moderoom.getHouseId());
		params.put("roomId", moderoom.getRoomId());
		Moderoom t = moderoomDao.get("from Moderoom t where t.houseId = :houseId " +
				" and t.roomId = :roomId ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Modehouse find(Modehouse modehouse) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userName", modehouse.getUserName());
//		params.put("pwd", modehouse.getPwd());
		Modehouse t = modehouseDao.get("from Modehouse t where t.userName = :userName and t.pwd = :pwd and t.enabled = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Modehouse> findList(Modehouse modehouse) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Modehouse t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (modehouse.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", modehouse.getHouseIeee());
		}
		if (modehouse.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + modehouse.getName() + "%");
		}
		if (modehouse.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", modehouse.getSecretKey());
		}
		if (modehouse.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", modehouse.getVendorCode());
		}
		if (modehouse.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + modehouse.getVendorName() + "%");
		}
		if (modehouse.getEncodemethod() != null) {
			hql.append("and t.encodemethod = :encodemethod ");
			params.put("encodemethod", modehouse.getEncodemethod());
		}
		if (modehouse.getXmppIp() != null) {
			hql.append("and t.xmppIp = :xmppIp ");
			params.put("xmppIp", modehouse.getXmppIp());
		}
		if (modehouse.getXmppPort() != null) {
			hql.append("and t.xmppPort = :xmppPort ");
			params.put("xmppPort", modehouse.getXmppPort());
		}
		if (modehouse.getCloudIp1() != null) {
			hql.append("and t.cloudIp1 = :cloudIp1 ");
			params.put("cloudIp1", modehouse.getCloudIp1());
		}
		if (modehouse.getCloudPort1() != null) {
			hql.append("and t.cloudPort1 = :cloudPort1 ");
			params.put("cloudPort1", modehouse.getCloudPort1());
		}
		if (modehouse.getCloudIp2() != null) {
			hql.append("and t.cloudIp2 = :cloudIp2 ");
			params.put("cloudIp2", modehouse.getCloudIp2());
		}
		if (modehouse.getCloudPort2() != null) {
			hql.append("and t.cloudPort2 = :cloudPort2 ");
			params.put("cloudPort2", modehouse.getCloudPort2());
		}
		if (modehouse.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", modehouse.getEnableFlag());
		}*/
		List<Modehouse> t = modehouseDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Object[]> findListSql(Modehouse modehouse) {
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
//		String tableName = "deviceWarnHistory_" + modehouse.getHouseIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from Modehouse s,").append(tableName).append(" e where 1=1 and s.houseIeee = e.houseIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (modehouse.getHouseIeee() != null) {
			sql.append("and s.houseIeee = :houseIeee ");
			params.put("houseIeee", modehouse.getHouseIeee());
		}
		if (modehouse.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + modehouse.getName() + "%");
		}*/
		List<Object[]> t = modehouseDao.findSql(sql.toString(), params, Modehouse.class, DevicewarnhistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public int deleteModeroom(Moderoom moderoom) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Moderoom t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (moderoom.getHouseId() != -1) {
			hql.append("and t.houseId = :houseId ");
			params.put("houseId", moderoom.getHouseId());
		}
		if (moderoom.getRoomName() != null) {
			hql.append("and t.roomName = :roomName ");
			params.put("roomName", moderoom.getRoomName());
		}
		if (moderoom.getRoomId() != 0) {
			hql.append("and t.roomId = :roomId ");
			params.put("roomId", moderoom.getRoomId());
		}
		//moderoomDao.executeHql(hql.toString(), params);
		params = new HashMap<String, Object>();
		params.put("behavior", 22);
		params.put("houseId", moderoom.getHouseId());
		params.put("id", moderoom.getRoomId());
		// 调用存储过程
		/*
		 * 					
		delete t.*,p.* from usermodemain t,usermodesub p where t.houseId = houseId and t.roomId = id
									and t.id =p.mid;
		 */		
		/*
		 * delete u from modegroupsub u inner join modedevice d on u.DeviceID=d.id
							where d.houseId = houseId and d.roomId = id;
		delete u from modescenesub u inner join modedevice d on u.DeviceID=d.id
							where d.houseId = houseId and d.roomId = id;
		delete t from modemacrosub t inner join modedevice d on t.dest=d.id
							where d.houseId = houseId and d.roomId = id and t.DestType=0;		
		delete u from modedevicebind u inner join modedevice d on u.SourceID=d.id
							where d.houseId = houseId and d.roomId = id ;							
		delete u from modedevicebind u inner join modedevice d on u.DestID=d.id
							where d.houseId = houseId and d.roomId = id ;							
		delete t from Modedevice t where t.houseId = houseId and t.roomId = id;		
		delete t from Modenode t where t.houseId = houseId and t.roomId = id;							
		delete p.* from usermodemain t,usermodesub p where t.houseId = houseId and t.roomId = id
									and t.id =p.mid;
		delete t.* from usermodemain t where t.houseId = houseId and t.roomId = id;
		delete t from Moderoom t where t.houseId = houseId and t.roomId = id;
		 */
		String sql = "{CALL ModedeleteTest(:behavior,:houseId,:id)}";			
		moderoomDao.executeSql2(sql, params);
		
		params = new HashMap<String, Object>();
		params.put("houseId", moderoom.getHouseId());		
		moderoomDao.executeHql("update Modehouse set roomNums = roomNums - 1 where id = :houseId", params);
		return 1;
	}
	
	@Override
	public int delete(Modehouse modehouse) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Modehouse t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (modehouse.getHouseName() != null) {
			hql.append("and t.houseName = :houseName ");
			params.put("houseName", modehouse.getHouseName());
		}
		if (modehouse.getUserId() != 0) {
			hql.append("and t.userId = :userId ");
			params.put("userId", modehouse.getUserId());
		}
		if (modehouse.getId() != 0) {
			hql.append("and t.id = :id ");
			params.put("id", modehouse.getId());
		}
		
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("houseId", modehouse.getId());		
		moderoomDao.executeHql("delete Moderoom t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modenode t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modedevice t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modedevicebind t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modegroupmain t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modegroupsub t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modemainclause t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modehvacmain t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modehvacsub t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modeiasmain t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modeiassub t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modemacromain t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modemacrosub t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modescenemain t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modescenesub t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modeschememain t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Modeschemesub t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Usermodemain t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Usermodesub t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Userordermain t where t.houseId = :houseId", params2);
		moderoomDao.executeHql("delete Devicepicsetlib t where t.houseId = :houseId", params2);
		moderoomDao.executeSql2("delete from familyeppage where houseId = :houseId", params2);
		moderoomDao.executeSql2("delete from familymodepage where houseId = :houseId", params2);
		moderoomDao.executeSql2("delete from smartmodepage where houseId = :houseId", params2);
		moderoomDao.executeSql2("delete from smarteppage where houseId = :houseId", params2);
		return modehouseDao.executeHql(hql.toString(), params);
	}

	@Override
	public Modehouse login(Modehouse modehouse) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(modehouse.getPwd()));
//		params.put("name", modehouse.getName());
		Modehouse t = modehouseDao.get("from Modehouse t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return modehouse;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(Modehouse modehouse) {
		DataGrid dg = new DataGrid();
		String hql = "from Modehouse t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(modehouse, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(modehouse, hql);
		List<Modehouse> l = modehouseDao.find(hql, params, modehouse.getPage(), modehouse.getRows());
		List<Modehouse> nl = new ArrayList<Modehouse>();
		changeModel(l, nl);
		dg.setTotal(modehouseDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Modehouse> l, List<Modehouse> nl) {
		if (l != null && l.size() > 0) {
			for (Modehouse t : l) {
				Modehouse u = new Modehouse();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Modehouse modehouse, String hql) {
		/*if (modehouse.getSort() != null) {
			hql += " order by " + modehouse.getSort() + " " + modehouse.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Modehouse modehouse, String hql, Map<String, Object> params) {
		/*if (modehouse.getName() != null && !modehouse.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + modehouse.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Modehouse t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		modehouseDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
	
	
	@Override
	public int hasDevice(Long houseId, Long roomId) {
		String sql = "select t.id from modedevice t where t.houseId = :houseId and t.roomId = :roomId limit 1";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("houseId", houseId);
		param.put("roomId", roomId);
		List devList = mapDao.executeSql(sql, param);
		return devList.isEmpty()? 0:1;
	}
}
