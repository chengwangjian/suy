package sy.service.impl;

import java.util.ArrayList;
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
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.Modedevice;
import sy.model.Modeeplib;
import sy.model.Modegrouplib;
import sy.model.Modehouse;
import sy.model.Modenode;
import sy.model.Modenodelib;
import sy.model.Moderoom;
import sy.model.Modeuser;
import sy.model.Userorderep;
import sy.model.Userordernode;
import sy.model.Userorderroom;
import sy.pageModel.Modedevicebind2;
import sy.service.ModedevicebindServiceI;

import com.flywind.app.entities.Modedevicebind;
import com.flywind.app.services.XmlUtil;


@Service("modedevicebindService")
public class ModedevicebindServiceImpl implements ModedevicebindServiceI {

	private static final Logger logger = Logger.getLogger(ModedevicebindServiceImpl.class);

	private BaseDaoI<Modedevicebind> modedevicebindDao;
	private BaseDaoI<Map> mapDao;
	private BaseDaoI<Userorderroom> userorderroomDao;
	private BaseDaoI<Modegrouplib> modegrouplibDao;
	private BaseDaoI<Userordernode> userordernodeDao;
	private BaseDaoI<Userorderep> userorderepDao;
	private BaseDaoI<Modenodelib> modenodelibDao;
	private BaseDaoI<Modeeplib> modeeplibDao;
	private BaseDaoI<Modenode> modenodeDao;
	private BaseDaoI<Modehouse> modehouseDao;
	private BaseDaoI<Moderoom> moderoomDao;
	private BaseDaoI<Modeuser> modeuserDao;
	private BaseDaoI<Modedevice> modedeviceDao;
	private XmlUtil dxmlutil;

	public BaseDaoI<Modedevice> getModedeviceDao() {
		return modedeviceDao;
	}

	@Autowired
	public void setModedeviceDao(BaseDaoI<Modedevice> modedeviceDao) {
		this.modedeviceDao = modedeviceDao;
	}

	public BaseDaoI<Modegrouplib> getModegrouplibDao() {
		return modegrouplibDao;
	}

	@Autowired
	public void setModegrouplibDao(BaseDaoI<Modegrouplib> modegrouplibDao) {
		this.modegrouplibDao = modegrouplibDao;
	}

	public XmlUtil getDxmlutil() {
		return dxmlutil;
	}

	@Autowired
	public void setDxmlutil(XmlUtil dxmlutil) {
		this.dxmlutil = dxmlutil;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<Userorderep> getUserorderepDao() {
		return userorderepDao;
	}

	@Autowired
	public void setUserorderepDao(BaseDaoI<Userorderep> userorderepDao) {
		this.userorderepDao = userorderepDao;
	}

	public BaseDaoI<Userordernode> getUserordernodeDao() {
		return userordernodeDao;
	}

	@Autowired
	public void setUserordernodeDao(BaseDaoI<Userordernode> userordernodeDao) {
		this.userordernodeDao = userordernodeDao;
	}

	public BaseDaoI<Userorderroom> getUserorderroomDao() {
		return userorderroomDao;
	}

	@Autowired
	public void setUserorderroomDao(BaseDaoI<Userorderroom> userorderroomDao) {
		this.userorderroomDao = userorderroomDao;
	}

	public BaseDaoI<Modeuser> getModeuserDao() {
		return modeuserDao;
	}

	@Autowired
	public void setModeuserDao(BaseDaoI<Modeuser> modeuserDao) {
		this.modeuserDao = modeuserDao;
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

	@Autowired
	public void setModeroomDao(BaseDaoI<Moderoom> moderoomDao) {
		this.moderoomDao = moderoomDao;
	}

	public BaseDaoI<Modedevicebind> getModedevicebindDao() {
		return modedevicebindDao;
	}

	@Autowired
	public void setModedevicebindDao(BaseDaoI<Modedevicebind> modedevicebindDao) {
		this.modedevicebindDao = modedevicebindDao;
	}

	public BaseDaoI<Modenodelib> getModenodelibDao() {
		return modenodelibDao;
	}

	@Autowired
	public void setModenodelibDao(BaseDaoI<Modenodelib> modenodelibDao) {
		this.modenodelibDao = modenodelibDao;
	}

	public BaseDaoI<Modeeplib> getModeeplibDao() {
		return modeeplibDao;
	}

	@Autowired
	public void setModeeplibDao(BaseDaoI<Modeeplib> modeeplibDao) {
		this.modeeplibDao = modeeplibDao;
	}

	public BaseDaoI<Modenode> getModenodeDao() {
		return modenodeDao;
	}

	@Autowired
	public void setModenodeDao(BaseDaoI<Modenode> modenodeDao) {
		this.modenodeDao = modenodeDao;
	}

	@Override
	public Modedevicebind save(Modedevicebind modedevicebind) {
		modedevicebindDao.save(modedevicebind);
		return modedevicebind;
	}
	
	@Override
	public Map findBindList(Modedevicebind modedevicebind, String language) {
		
//		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", modedevicebind.getHouseId());
		StringBuffer hql = new StringBuffer("SELECT t.*,r.roomName as SourceName,q.roomName as destNameS from modedevicebind t LEFT JOIN modedevice m on t.SourceID = m.id ");
		hql.append("LEFT JOIN modedevice s on t.DestID = s.id ");
		hql.append("LEFT JOIN moderoom r on r.houseId = m.houseId and r.roomId = m.roomId ");
		hql.append("LEFT JOIN moderoom q on q.houseId = s.houseId and q.roomId = s.roomId ");
		hql.append(" WHERE t.HouseID = :houseId");
//		hql = "from Modedevicebind t where t.houseId = :houseId ";
		List<Map> list = mapDao.executeSql(hql.toString(), params);
//		List<Modedevicebind> list = modedevicebindDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("modedevicebind",list);	
		} else
			rt.put("modedevicebind",null);
		
		return rt;
	}
	
	@Override
	public Map findBindableSourceDeviceList(Modedevicebind modedevicebind,String behavior) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", modedevicebind.getHouseId());
		
		/*hql = "select t from Modedevice t,Modenode s,Modenodelib r where t.modeNodeId=s.id and s.modeNodeLibId=r.id " +
				"and t.houseId = :houseId and r.deviceType='source' ";*/
		/*hql = "select new map(t.id as id,t.ep as ep,t.deviceName as deviceName,t.deviceUniqueId as deviceUniqueId," +
				"r.clusterId as clusterId,r.clusterName as clusterName,r.destType as destType,r.modelId as modelId) from Modedevice t," +
				"Modenode s,Modenodelib r where t.modeNodeId=s.id and s.modeNodeLibId=r.id " +
				"and t.houseId = :houseId and r.deviceType='source' ";*/
		if(behavior!=null&&behavior.equals("2")){
			params.put("sourceId", modedevicebind.getId());
			hql = "select new map(t.id as id,t.ep as ep,t.deviceName as deviceName,t.deviceUniqueId as deviceUniqueId," +
					"m.clusterId as clusterId,m.clusterName as clusterName,m.clusterNameEn as clusterNameEn,m.destType as destType,r.modelId as modelId,t.ieee as ieee) from Modedevice t," +
					"Modenode s,Modenodelib r,Modeeplib m where t.modeNodeId=s.id and s.modeNodeLibId=r.id and r.id = m.nodeId and m.ep = t.ep " +
					"and t.houseId = :houseId and (m.deviceType='source' or m.deviceType='sourceDest') and t.id = :sourceId";
		}
		else{
			hql = "select new map(t.id as id,t.ep as ep,t.deviceName as deviceName,t.deviceUniqueId as deviceUniqueId," +
					"m.clusterId as clusterId,m.clusterName as clusterName,m.clusterNameEn as clusterNameEn,m.destType as destType,r.modelId as modelId,t.ieee as ieee) from Modedevice t," +
					"Modenode s,Modenodelib r,Modeeplib m where t.modeNodeId=s.id and s.modeNodeLibId=r.id and r.id = m.nodeId and m.ep = t.ep " +
					"and t.houseId = :houseId and (m.deviceType='source' or m.deviceType='sourceDest') ";
		}
		/*hql = "select distinct new map(t.id as id,t.ep as ep,t.deviceName as deviceName,t.deviceUniqueId as deviceUniqueId," +
				"m.clusterId as clusterId,m.clusterName as clusterName,m.destType as destType,r.modelId as modelId,m.id ad mid) from Modeeplib m," +
				"Modenodelib r,Modenode s,Modedevice t " +
				" where t.modeNodeId=s.id and s.modeNodeLibId=r.id and r.id = m.nodeId " +
				"and t.houseId = :houseId and (m.deviceType='source' or m.deviceType='sourceDest') ";*/
		List<Map> list = mapDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("sourceDevice",list);	
		} else
			rt.put("sourceDevice",null);
		
		return rt;
	}
	
	@Override
	public Map findBindAbleDestDeviceList(Modedevicebind modedevicebind,String language,String behavior) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", modedevicebind.getHouseId());
		/*hql = "select new map(t.id as id,t.ep as ep,t.deviceName as deviceName,t.deviceUniqueId as deviceUniqueId," +
				"r.clusterId as clusterId,r.clusterName as clusterName,r.destType as destType,r.modelId as modelId) from Modedevice t," +
				"Modenode s,Modenodelib r where t.modeNodeId=s.id and s.modeNodeLibId=r.id " +
				"and t.houseId = :houseId and r.deviceType='dest' ";*/
//		if(language.equals("1")){
//			hql = "select new map(t.id as id,t.ep as ep,t.deviceName as deviceName,t.deviceUniqueId as deviceUniqueId," +
//					"m.clusterId as clusterId,m.clusterNameEn as clusterNameEn,m.destType as destType,r.modelId as modelId,t.ieee as ieee) from Modedevice t," +
//					"Modenode s,Modenodelib r,Modeeplib m where t.modeNodeId=s.id and s.modeNodeLibId=r.id and r.id = m.nodeId and m.ep = t.ep " +
//					"and t.houseId = :houseId and (m.deviceType='dest' or m.deviceType='sourceDest')";	
//		}else {
				
			
			if(behavior!=null&&behavior.equals("2")){
				params.put("id", modedevicebind.getId());
				hql = "select new map(t.id as id,t.ep as ep,t.deviceName as deviceName,t.deviceUniqueId as deviceUniqueId," +
						"m.clusterId as clusterId,m.clusterName as clusterName,m.clusterNameEn as clusterNameEn,m.destType as destType,r.modelId as modelId,t.ieee as ieee) from Modedevice t," +
						"Modenode s,Modenodelib r,Modeeplib m where t.modeNodeId=s.id and s.modeNodeLibId=r.id and r.id = m.nodeId and m.ep = t.ep " +
						"and t.houseId = :houseId and (m.deviceType='dest' or m.deviceType='sourceDest') and t.id = :id";	
			}
			else {
				hql = "select new map(t.id as id,t.ep as ep,t.deviceName as deviceName,t.deviceUniqueId as deviceUniqueId," +
						"m.clusterId as clusterId,m.clusterName as clusterName,m.clusterNameEn as clusterNameEn,m.destType as destType,r.modelId as modelId,t.ieee as ieee) from Modedevice t," +
						"Modenode s,Modenodelib r,Modeeplib m where t.modeNodeId=s.id and s.modeNodeLibId=r.id and r.id = m.nodeId and m.ep = t.ep " +
						"and t.houseId = :houseId and (m.deviceType='dest' or m.deviceType='sourceDest')";
			}
//		}
		List<Map> list = mapDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("destDevice",list);	
		} else
			rt.put("destDevice",null);
		
		return rt;
	}
	
	@Override
	public int insertModedevicebind(Modedevicebind2 modedevicebind2) {

		/*List<Map> list3 = new ArrayList<Map>();
    	list3 = (List<Map>)map.get("modeNodeAndDevice");
    	for(Map<String, Object> ob1:list3){
    		Modenode modenode2 = new Modenode();
    		List<Modedevice> list2 = new ArrayList<Modedevice>();
    		modenode2 = (Modenode)ob1.get("modenode");
    		modenodeDao.save(modenode2);
    		list2 = (List<Modedevice>)ob1.get("modedevice");
    		for(Modedevice ob2:list2){
    			ob2.setHouseId(modenode2.getHouseId());
    			ob2.setRoomId(modenode2.getRoomId());
    			ob2.setModeNodeId(modenode2.getId());
    			modedeviceDao.save(ob2);
			}
		}*/
    	
    	List<Modedevicebind> list3 = new ArrayList<Modedevicebind>();
    	list3 = modedevicebind2.getModedevicebind();
    	for(Modedevicebind ob1:list3){
    		ob1.setSourceDeviceUniqueId(String.valueOf(ob1.getSourceId()));
    		ob1.setBindType((short)1);
    		ob1.setDestType("3");
    		if (get(ob1) == null){
    			modedevicebindDao.save(ob1);
    		}
    	}
    	
		return 1;
	}
	
	@Override
	public Map findBindAbleVirtualEPList(Modegrouplib modegrouplib) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("modelId", modegrouplib.getModelId());
		params.put("deviceEp", modegrouplib.getDeviceEp());
		hql = "from Modegrouplib t where t.modelId = :modelId and t.deviceEp = :deviceEp";
		List<Modegrouplib> list = modegrouplibDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("modegrouplib",list);	
		} else
			rt.put("modegrouplib",null);
		
		return rt;
	}
	
	@Override
	public Modedevicebind update(Modedevicebind modedevicebind) {
		modedevicebindDao.update(modedevicebind);
		return modedevicebind;
	}
	
	@Override
	public Modedevicebind get(Modedevicebind modedevicebind) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sourceId", modedevicebind.getSourceId());
		params.put("destId", modedevicebind.getDestId());
		Modedevicebind t = null;
		// 绑定的类型，0为正常绑定，1为虚拟EP绑定
		if (modedevicebind.getBindType() == 0) { // 0为正常绑定
			t = modedevicebindDao.get("from Modedevicebind t where t.sourceId = :sourceId and t.destId = :destId and t.bindType = 0 ", params);
		}
		if (modedevicebind.getBindType() == 1) { // 1为虚拟EP绑定
			params.put("sourceVirtualEp", modedevicebind.getSourceVirtualEp());
			t = modedevicebindDao.get("from Modedevicebind t where t.sourceId = :sourceId and t.destId = :destId and t.sourceVirtualEp = :sourceVirtualEp " +
					"and t.bindType = 1 ", params);
		}
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Modedevicebind find(Modedevicebind modedevicebind) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userName", modedevicebind.getUserName());
//		params.put("pwd", modedevicebind.getPwd());
		Modedevicebind t = modedevicebindDao.get("from Modedevicebind t where t.userName = :userName and t.pwd = :pwd and t.enabled = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Modedevicebind> findList(Modedevicebind modedevicebind) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Modedevicebind t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (modedevicebind.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", modedevicebind.getHouseIeee());
		}
		if (modedevicebind.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + modedevicebind.getName() + "%");
		}
		if (modedevicebind.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", modedevicebind.getSecretKey());
		}
		if (modedevicebind.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", modedevicebind.getVendorCode());
		}
		if (modedevicebind.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + modedevicebind.getVendorName() + "%");
		}
		if (modedevicebind.getEncodemethod() != null) {
			hql.append("and t.encodemethod = :encodemethod ");
			params.put("encodemethod", modedevicebind.getEncodemethod());
		}
		if (modedevicebind.getXmppIp() != null) {
			hql.append("and t.xmppIp = :xmppIp ");
			params.put("xmppIp", modedevicebind.getXmppIp());
		}
		if (modedevicebind.getXmppPort() != null) {
			hql.append("and t.xmppPort = :xmppPort ");
			params.put("xmppPort", modedevicebind.getXmppPort());
		}
		if (modedevicebind.getCloudIp1() != null) {
			hql.append("and t.cloudIp1 = :cloudIp1 ");
			params.put("cloudIp1", modedevicebind.getCloudIp1());
		}
		if (modedevicebind.getCloudPort1() != null) {
			hql.append("and t.cloudPort1 = :cloudPort1 ");
			params.put("cloudPort1", modedevicebind.getCloudPort1());
		}
		if (modedevicebind.getCloudIp2() != null) {
			hql.append("and t.cloudIp2 = :cloudIp2 ");
			params.put("cloudIp2", modedevicebind.getCloudIp2());
		}
		if (modedevicebind.getCloudPort2() != null) {
			hql.append("and t.cloudPort2 = :cloudPort2 ");
			params.put("cloudPort2", modedevicebind.getCloudPort2());
		}
		if (modedevicebind.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", modedevicebind.getEnableFlag());
		}*/
		List<Modedevicebind> t = modedevicebindDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Object[]> findListSql(Modedevicebind modedevicebind) {
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
//		String tableName = "deviceWarnHistory_" + modedevicebind.getHouseIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from Modedevicebind s,").append(tableName).append(" e where 1=1 and s.houseIeee = e.houseIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (modedevicebind.getHouseIeee() != null) {
			sql.append("and s.houseIeee = :houseIeee ");
			params.put("houseIeee", modedevicebind.getHouseIeee());
		}
		if (modedevicebind.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + modedevicebind.getName() + "%");
		}*/
		List<Object[]> t = modedevicebindDao.findSql(sql.toString(), params, Modedevicebind.class, DevicewarnhistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public int deleteModeNode(Modenode modenode) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Modenode t where t.id = :id ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenode.getId());
		modenodeDao.executeHql(hql.toString(), params);
		modedevicebindDao.executeHql("delete Modedevicebind t where t.modeNodeId = :id ", params);		
		return 1;
	}
	
	@Override
	public int delete(Modedevicebind modedevicebind) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Modedevicebind t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		/*if (modedevicebind.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", modedevicebind.getHouseIeee());
			params2.put("houseIeee", modedevicebind.getHouseIeee());
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String tableName = "deviceAttributeHistory_" + modedevicebind.getHouseIeee() + "_" + year;
			tableName = "deviceOperateHistory_" + modedevicebind.getHouseIeee() + "_" + year;
			tableName = "deviceWarnHistory_" + modedevicebind.getHouseIeee() + "_" + year;
		}*/
		/*
		if (modedevicebind.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + modedevicebind.getVendorName() + "%");
		}
		if (modedevicebind.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", modedevicebind.getEnableFlag());
		}		*/
		if (modedevicebind.getId() != 0) {
			hql.append("and t.id = :id ");
			params.put("id", modedevicebind.getId());
		}
		return modedevicebindDao.executeHql(hql.toString(), params);
	}

	@Override
	public Modedevicebind login(Modedevicebind modedevicebind) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(modedevicebind.getPwd()));
//		params.put("name", modedevicebind.getName());
		Modedevicebind t = modedevicebindDao.get("from Modedevicebind t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return modedevicebind;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(Modedevicebind modedevicebind) {
		DataGrid dg = new DataGrid();
		String hql = "from Modedevicebind t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(modedevicebind, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(modedevicebind, hql);
		List<Modedevicebind> l = modedevicebindDao.find(hql, params, modedevicebind.getPage(), modedevicebind.getRows());
		List<Modedevicebind> nl = new ArrayList<Modedevicebind>();
		changeModel(l, nl);
		dg.setTotal(modedevicebindDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Modedevicebind> l, List<Modedevicebind> nl) {
		if (l != null && l.size() > 0) {
			for (Modedevicebind t : l) {
				Modedevicebind u = new Modedevicebind();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Modedevicebind modedevicebind, String hql) {
		/*if (modedevicebind.getSort() != null) {
			hql += " order by " + modedevicebind.getSort() + " " + modedevicebind.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Modedevicebind modedevicebind, String hql, Map<String, Object> params) {
		/*if (modedevicebind.getName() != null && !modedevicebind.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + modedevicebind.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Modedevicebind t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		modedevicebindDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}

	@Override
	public Map findBind(Modedevicebind modedevicebind) {
		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("id", modedevicebind.getId());
		hql = "from Modedevicebind t where t.id = :id ";
		List<Modedevicebind> list = modedevicebindDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("modedevicebind",list);	
		} else
			rt.put("modedevicebind",null);
		
		return rt;
	}

	@Override
	public int updateBind(Modedevicebind modedevicebind) {
		
		modedevicebindDao.update(modedevicebind);
		
		return 0;
	}

	@Override
	public Modedevicebind getDeviceBind(Modedevicebind modedevicebind) {
		String hql = "from Modedevicebind t where t.id = :id ";
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("id", modedevicebind.getId());
		Modedevicebind t = modedevicebindDao.get(hql,param);
		return t;
	}
}
