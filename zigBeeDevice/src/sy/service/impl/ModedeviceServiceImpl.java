package sy.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.Modedevice;
import sy.model.Modeeplib;
import sy.model.ModeeplibEn;
import sy.model.Modenode;
import sy.model.Modenodelib;
import sy.model.ModenodelibEn;
import sy.model.Moderoom;
import sy.pageModel.ModeNodeAndDevice;
import sy.pageModel.ModeNodeAndDevice2;
import sy.service.ModedeviceServiceI;

import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlmoderoom;
import com.flywind.app.entities.Modedevicebind;
import com.flywind.app.entities.Usermodemain;
import com.flywind.app.services.QueryParameters;


@Service("modedeviceService")
public class ModedeviceServiceImpl implements ModedeviceServiceI {

	private static final Logger logger = Logger.getLogger(ModedeviceServiceImpl.class);
	private StartDAO dao;
	private BaseDaoI<Map> mapDao;
	private BaseDaoI<Modedevice> modedeviceDao;
	private BaseDaoI<Modenodelib> modenodelibDao;
	private BaseDaoI<ModenodelibEn> modenodelibenDao;
    int kk=0;
    public StartDAO getDao() {
		return dao;
	}
    @Autowired
	public void setDao(StartDAO dao) {
		this.dao = dao;
	} 
	
	
	public BaseDaoI<ModenodelibEn> getModenodelibenDao() {
		return modenodelibenDao;
	}
	@Autowired
	public void setModenodelibenDao(BaseDaoI<ModenodelibEn> modenodelibenDao) {
		this.modenodelibenDao = modenodelibenDao;
	}
	private BaseDaoI<Modeeplib> modeeplibDao;
	private BaseDaoI<ModeeplibEn> modeeplibenDao;
	public BaseDaoI<ModeeplibEn> getModeeplibenDao() {
		return modeeplibenDao;
	}
	@Autowired
	public void setModeeplibenDao(BaseDaoI<ModeeplibEn> modeeplibenDao) {
		this.modeeplibenDao = modeeplibenDao;
	}
	private BaseDaoI<Modenode> modenodeDao;
	private BaseDaoI<Modedevicebind> modedevicebindDao;
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	public BaseDaoI<Modedevice> getModedeviceDao() {
		return modedeviceDao;
	}

	@Autowired
	public void setModedeviceDao(BaseDaoI<Modedevice> modedeviceDao) {
		this.modedeviceDao = modedeviceDao;
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
	public Modedevice save(Modedevice modedevice) {
		modedeviceDao.save(modedevice);
		return modedevice;
	}
	
	@Override
	public Map findModeNodeLibDataByDeviceID(Modeeplib modeeplib) {

		String hql = "";
		String deviceId = modeeplib.getDeviceId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", deviceId);
		Map<String, Object> rt = new HashMap<String, Object>();
		hql = "select t from Modenodelib t,Modeeplib e where t.id = e.nodeId  and e.deviceId = :deviceId";
		List<Modenodelib> list = modenodelibDao.find(hql,params);
		if(list != null && list.size() > 0)
			rt.put("modenodelib",list);
		else
			rt.put("modenodelib",null);
		
		return rt;
	}
	
	@Override
	public Map findModeNodeLibDataByNodeType(Modenodelib modenodelib){
		String language = modenodelib.getLanguage();
		StringBuffer hql = new StringBuffer();
		if("1".equals(language)){
			hql.append("select id,nodeType,imgdetails,modelId,deviceNameEn,clusterId,clusterNameEn,destType,source_id,deviceType,picName,descriptionEn,powerTypeEn,activationMethodEn,resetDefaultEn,remarkEn ");
			hql.append("from modenodelib left join imgdetailtab on modenodelib.id=imgdetailtab.modeNodeLibIds where 1=1 ");
//			hql.append("from ModenodelibEn t where 1=1 ");
		}
		else if("2".equals(language)||language==null){
			hql.append("select id,nodeType,imgdetails,modelId,deviceName,clusterId,clusterName,destType,source_id,deviceType,picName,description,powerType,activationMethod,resetDefault,remark ");
			hql.append("from modenodelib left join imgdetailtab on modenodelib.id=imgdetailtab.modeNodeLibIds where 1=1 ");
//			hql.append("from Modenodelib t where 1=1 ");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if (modenodelib.getNodeType() != null && StringUtils.isNotBlank(modenodelib.getNodeType())) {
			hql.append("and nodeType = :nodeType ");
			params.put("nodeType", modenodelib.getNodeType());
		}
		if (modenodelib.getModelId() != null && StringUtils.isNotBlank(modenodelib.getModelId())) {
			hql.append("and modelId = :modelId ");
			params.put("modelId", modenodelib.getNodeType());
		}
		if (modenodelib.getDeviceName() != null && StringUtils.isNotBlank(modenodelib.getDeviceName())) {
			if(language.equals("1")){
				hql.append("and deviceNameEn like :deviceNameEn ");
				params.put("deviceNameEn", "%" + modenodelib.getDeviceName() + "%");
			}
			else{
				hql.append("and deviceName like :deviceName ");
				params.put("deviceName", "%" + modenodelib.getDeviceName() + "%");
			}
		}
		Map<String, Object> rt = new HashMap<String, Object>();
//		if("1".equals(language)){
//			List<ModenodelibEn> listen = modenodelibenDao.find(hql.toString(),params);
//			if(listen != null && listen.size() > 0)
//				rt.put("modenodelib",listen);
//			else
//				rt.put("modenodelib",null);
//		}
//		else if("2".equals(language)||language==null){
//			List<Modenodelib> list = modenodelibDao.find(hql.toString(),params);
			List<Map> list = mapDao.executeSql(hql.toString(), params);
			System.out.println(hql.toString());
			if(list != null && list.size() > 0)
				rt.put("modenodelib",list);
			else
				rt.put("modenodelib",null);
//		}
		return rt;
	}
	
	@Override
	public Map findModeNodeLibDataByNodeLibId(Modenodelib modenodelib) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenodelib.getId());
		Map<String, Object> rt = new HashMap<String, Object>();
		hql = "from Modenodelib t where t.id = :id";
		List<Modenodelib> list = modenodelibDao.find(hql,params);
		if(list != null && list.size() > 0)
			rt.put("modenodelib",list.get(0));
		else
			rt.put("modenodelib",null);
		
		return rt;
	}
	
	@Override
	public Map findModeEPLibDataByNode(Modeeplib modeeplib) {
		//先默认1
		modeeplib.setLanguage("2");
		int language = Integer.parseInt(modeeplib.getLanguage());
		String hql = "";
		long nodeId = modeeplib.getNodeId();
		int num = modeeplib.getNum();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
//		if("1".equals(language)){
//			if (nodeId != 0) {
//				params.put("nodeId", nodeId);
//				hql = "from ModenodelibEn t where t.id = :nodeId ";
//			} 
//			// 型号查找
//			if (modeeplib.getInternelModelId() != null) {
//				params.put("modelId", modeeplib.getInternelModelId());
//				hql = "from ModenodelibEn t where t.modelId = :modelId ";
//			}
//			List<ModenodelibEn> list = modenodelibenDao.find(hql,params);
//			if (list != null && list.size() > 0) {			
//			} else {
//				rt.put("modeNodeAndEpLib",null);		
//				return rt;
//			}
//			ModenodelibEn modenodeliben = list.get(0);
//			
//			// 统计已有节点数量
//			hql = "select count(*) from Modenode where modeNodeLibId = :nodeId and houseId = :houseId";
//			params = new HashMap<String, Object>();
//			params.put("nodeId", modenodeliben.getId());
//			params.put("houseId", modeeplib.getHouseId());
//			Long sum = modenodeDao.count(hql, params);
//			
//			params = new HashMap<String, Object>();
//			params.put("nodeId", modenodeliben.getId());
//			hql = "from ModeeplibEn t where t.nodeId = :nodeId ";
//			List<ModeeplibEn> list2 = modeeplibenDao.find(hql,params);
//			
//			int i = 1;
//			List<Map> list3 = new ArrayList<Map>();
//			Long k = 1l;
//			k = ++sum;
//			while (i <= num) {
//				Map<String, Object> rt2 = new HashMap<String, Object>();
//				Modenodelib modenodelib2 = new Modenodelib();
//				if(list != null && list.size() > 0) {
//					BeanUtils.copyProperties(modenodeliben,modenodelib2);
//					modenodelib2.setDeviceName(modenodeliben.getDeviceName() + k);
//					rt2.put("modenodelib",modenodelib2);
//				} else
//					rt2.put("modenodelib",null);
//				
//				List<ModeeplibEn> list4 = new ArrayList<ModeeplibEn>();
//				if(list2 != null && list2.size() > 0) {
//					int j = 1;
//					for(ModeeplibEn ob1:list2){
//						ModeeplibEn ob2 = new ModeeplibEn();
//						BeanUtils.copyProperties(ob1,ob2);
////						ob2.setDeviceName(modenodelib.getDeviceName() + k + "-" + j);
////						ob2.setDeviceName(k + "(" + ob2.getDeviceName() + ")");
//						ob2.setDeviceName(ob2.getDeviceName() + k);
//						list4.add(ob2);
//						j++;
//					}
//					rt2.put("modeeplib",list4);
//				} else
//					rt2.put("modeeplib",null);
//				list3.add(rt2);
//				i++;
//				k++;
//			}
//			rt.put("modeNodeAndEpLib",list3);	
//		}
//		else if("2".equals(language)||language==null){
			if (nodeId != 0) {
				params.put("nodeId", nodeId);
				hql = "from Modenodelib t where t.id = :nodeId ";
			} 
			// 型号查找
			if (modeeplib.getInternelModelId() != null) {
				params.put("modelId", modeeplib.getInternelModelId());
				hql = "from Modenodelib t where t.modelId = :modelId ";
			}
			List<Modenodelib> list = modenodelibDao.find(hql,params);
			if (list != null && list.size() > 0) {			
			} else {
				rt.put("modeNodeAndEpLib",null);		
				return rt;
			}
			Modenodelib modenodelib = list.get(0);
			
			// 统计已有节点数量
			//hql = "select count(*) from Modenode where modeNodeLibId = :nodeId and houseId = :houseId";
			hql = "SELECT Max(m.deviceName) deviceName from modenode m where m.deviceName REGEXP '[0-9]+$' and m.houseId = :houseId and m.modeNodeLibId = :nodeId";
			params = new HashMap<String, Object>();
			params.put("nodeId", modenodelib.getId());
			params.put("houseId", modeeplib.getHouseId());
//			Long sum = modenodeDao.count(hql, params);
			int sum = 0;
			List dvs = mapDao.executeSql(hql, params);
			if(!dvs.isEmpty()) {
				String deviceName = (String) ((Map) dvs.get(0)).get("deviceName");
				//提取数字
				if(StringUtils.isNotBlank(deviceName)) {
//					String regEx="[^0-9]";   
//					Pattern p = Pattern.compile(regEx);   
//					Matcher m = p.matcher(deviceName);
//					sum = Integer.parseInt(m.replaceAll("").trim());
					String numStr = "";
					int devLen = deviceName.length();
					for(int i = devLen - 1; i > -1; i--) {
						if(deviceName.charAt(i)>=48 && deviceName.charAt(i)<=57){
							numStr = deviceName.charAt(i) + numStr;
						}
						else
							break;
					}
					if(StringUtils.isNotBlank(numStr))
						sum = Integer.parseInt(numStr);
				}
			}
			
			params = new HashMap<String, Object>();
			params.put("nodeId", modenodelib.getId());
			hql = "from Modeeplib t where t.nodeId = :nodeId ";
			List<Modeeplib> list2 = modeeplibDao.find(hql,params);
			
			int i = 1;
			List<Map> list3 = new ArrayList<Map>();
//			int k = ++sum;
			while (i <= num) {
				Map<String, Object> rt2 = new HashMap<String, Object>();
				Modenodelib modenodelib2 = new Modenodelib();
				if(list != null && list.size() > 0) {
					BeanUtils.copyProperties(modenodelib,modenodelib2);
					if(language==1)
//						modenodelib2.setDeviceName(modenodelib.getDeviceNameEn() + k);
						modenodelib2.setDeviceName(modenodelib.getDeviceNameEn());
					else
//						modenodelib2.setDeviceName(modenodelib.getDeviceName() + k);
						modenodelib2.setDeviceName(modenodelib.getDeviceName());
					rt2.put("modenodelib",modenodelib2);
				} else
					rt2.put("modenodelib",null);
				
				List<Modeeplib> list4 = new ArrayList<Modeeplib>();
				if(list2 != null && list2.size() > 0) {
					int j = 1;
					for(Modeeplib ob1:list2){
						Modeeplib ob2 = new Modeeplib();
						BeanUtils.copyProperties(ob1,ob2);
//						ob2.setDeviceName(modenodelib.getDeviceName() + k + "-" + j);
//						ob2.setDeviceName(k + "(" + ob2.getDeviceName() + ")");
						if(language==1)
//							ob2.setDeviceName(ob2.getDeviceNameEn() + k);
							ob2.setDeviceName(ob2.getDeviceNameEn());
						else
//							ob2.setDeviceName(ob2.getDeviceName() + k);
							ob2.setDeviceName(ob2.getDeviceName());
						list4.add(ob2);
						j++;
					}
					rt2.put("modeeplib",list4);
				} else
					rt2.put("modeeplib",null);
				list3.add(rt2);
				i++;
//				k++;
			}
			
			rt.put("modeNodeAndEpLib",list3);	
			rt.put("sum", sum);
//		}
		return rt;
	}
	
	@Override
	public Map findModeNodeList(Modenode modenode,List<Integer> romidlias) {
		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", modenode.getHouseId());
		params.put("roomId", romidlias);
		//select * from modenode where roomid in(-1,1,2,3,4,5) and houseId=1563
		//hql="select *from (select * from modenode where roomid in(:roomId) and houseId=:houseId) a left join imgdetailtab b on a.modeNodeLibId=b.modeNodeLibId";
		hql="select * from modenode a left join (select id as cid,picName from modenodelib) c on c.cid=a.modeNodeLibId left join imgdetailtab b on a.modeNodeLibId=b.modeNodeLibIds where a.roomid in(:roomId) and a.houseId=:houseId ORDER BY deviceName";
		List<Map> list = mapDao.executeSql(hql,params);
		if(list != null && list.size() > 0) {
			rt.put("modenode",list);	
		} else
			rt.put("modenode",null);
		
		return rt;
	}
	
	@Override
	public Map findModeNodeList(Modenode modenode) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", modenode.getHouseId());
		params.put("roomId", modenode.getRoomId());
		hql = "from Modenode t where t.houseId = :houseId and t.roomId = :roomId";
		List<Modenode> list = modenodeDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("modenode",list);	
		} else
			rt.put("modenode",null);
		
		return rt;
	}
	
	@Override
	public Map findModeDeviceList(Modedevice modedevice) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", modedevice.getHouseId());
		params.put("modeNodeId", modedevice.getModeNodeId());
		hql = "from Modedevice t where t.houseId = :houseId and t.modeNodeId = :modeNodeId";
		List<Modedevice> list = modedeviceDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("modedevice",list);	
		} else
			rt.put("modedevice",null);
		
		return rt;
	}
	
	@Override
	public Map findModeDeviceByNodeId(Modenode modenode) {

//		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("modeNodeId", modenode.getId());
		String sql = "select m.*,f.id as fid from modedevice m left join familyeppage f on m.id = f.ep_id and f.houseid = m.houseId where m.modeNodeId = :modeNodeId";
//		hql = "from Modedevice t where t.modeNodeId = :modeNodeId";
		List<Map> list = mapDao.executeSql(sql,params);
//		List<Modedevice> list = modedeviceDao.find(hql,params);
		if(list != null && list.size() > 0) {
			rt.put("modedevice",list);	
		} else
			rt.put("modedevice",null);
		
		return rt;
	}
	
	@Override
	public int insertDeviceToRoom(Map map) {
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
    	
    	List<ModeNodeAndDevice2> list3 = new ArrayList<ModeNodeAndDevice2>();
    	list3 = (List<ModeNodeAndDevice2>)map.get("modeNodeAndDevice");
    	for(ModeNodeAndDevice2 ob1:list3){
    		Modenode modenode2 = new Modenode();
    		List<Modedevice> list2 = new ArrayList<Modedevice>();
    		modenode2 = ob1.getModenode();
    		modenodeDao.save(modenode2);
    		list2 = ob1.getModedevice();
    		for(Modedevice ob2:list2){
    			ob2.setHouseId(modenode2.getHouseId());
    			ob2.setRoomId(modenode2.getRoomId());
    			ob2.setModeNodeId(modenode2.getId());
    			modedeviceDao.save(ob2);
    		}
    	}
    	
		return 1;
	}
	
	@Override
	public int insertDeviceToRoom(ModeNodeAndDevice modeNodeAndDevice) {
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
		
		
    	
    	List<ModeNodeAndDevice2> list3 = new ArrayList<ModeNodeAndDevice2>();
    	list3 = modeNodeAndDevice.getModeNodeAndDevice();
    	long houseId = 0;
    	long roomId = 0;
    	Map<String, Object> params = new HashMap<String, Object>();
    	
		List<Modedevice> deviceNameList = new ArrayList<Modedevice>();
    	for(ModeNodeAndDevice2 ob1:list3){
    		Modenode modenode2 = new Modenode();
    		List<Modedevice> list2 = new ArrayList<Modedevice>();
    		modenode2 = ob1.getModenode();
    		houseId = modenode2.getHouseId();
    		list2 = ob1.getModedevice();
    		for(Modedevice ob2:list2){
    			String hql = "";
    			params = new HashMap<String, Object>();
    			params.put("deviceName", ob2.getDeviceName());
    			params.put("houseId", houseId);
    			// 判断设备是否同名
    			hql = "from Modedevice t where t.deviceName = :deviceName and t.houseId = :houseId";
    			List<Modedevice> list = modedeviceDao.find(hql,params);    			
    			if(list != null && list.size() > 0) {
    				//return -1;	
    			} 
    			deviceNameList.add(ob2);
    		}
    	}
    	
    	int j = 0;
    	int m = 0;
    	for(Modedevice ob2:deviceNameList){
    		j++;
    		m = 0;
    		for(Modedevice ob3:deviceNameList){
    			m++;
    			if (j == m) {
    				continue;
    			}  
    			// 判断上传上来的设备名是否同名
    			if (ob2.getDeviceName().equals(ob3.getDeviceName())) {
    				//return -1;	
    			}
    		}
    	}
    	for(ModeNodeAndDevice2 ob1:list3){
    		Modenode modenode2 = new Modenode();
    		List<Modedevice> list2 = new ArrayList<Modedevice>();
    		modenode2 = ob1.getModenode();
    		houseId = modenode2.getHouseId();
    		roomId = modenode2.getRoomId();
    		Map<String, Object> paramss = new HashMap<String, Object>();
    		paramss.put("deviceName",modenode2.getDeviceName());
    		paramss.put("houseId", houseId);
			// 判断设备是否同名
//			String hql = "from Modenode t where t.deviceName = :deviceName and t.houseId = :houseId";
//			List<Modenode> list = modenodeDao.find(hql,paramss);
//			if(list != null && list.size() > 0) {
//				System.out.print(kk+"545348");
//				kk=kk+1;
//				modenode2.setDeviceName(modenode2.getDeviceName()+kk);
//			} 
    		modenodeDao.save(modenode2);
    		params = new HashMap<String, Object>();
    		params.put("houseId", houseId);
    		params.put("roomId", roomId);
    		params.put("id", modenode2.getId());
    		/*modenodeDao.executeSql2("update Modenode t,Modenodelib p set t.deviceUniqueId = concat(p.modelId,concat(t.id,'')),t.description" +
    				"= p.description where t.modeNodeLibId" +
    				"= p.id and t.houseId = :houseId and t.roomId = :roomId and t.id = :id", params);*/
    		list2 = ob1.getModedevice();
    		for(Modedevice ob2:list2){
    			ob2.setHouseId(modenode2.getHouseId());
//    			ob2.setRoomId(modenode2.getRoomId());
    			ob2.setModeNodeId(modenode2.getId());
    			ob2.setModelId(modenode2.getDeviceUniqueId());// modelid
    			modedeviceDao.save(ob2);
    			params = new HashMap<String, Object>();
        		params.put("houseId", houseId);
        		params.put("roomId", roomId);
        		params.put("id", ob2.getId());
    			/*modedeviceDao.executeSql2("update Modedevice t,Modenode p set t.deviceUniqueId = concat(p.deviceUniqueId,t.ep) where t.modeNodeId" +
    					"= p.id and t.houseId = :houseId and t.roomId = :roomId and t.id = :id", params);*/
    		}
    	}
    	
		/*modenodeDao.executeSql2("update Modenode t,Modenodelib p set t.deviceUniqueId = concat(p.modelId,concat(t.id,'')),t.description" +
				"= p.description where t.modeNodeLibId" +
				"= p.id and t.houseId = :houseId and t.roomId = :roomId", params);
		modedeviceDao.executeSql2("update Modedevice t,Modenode p set t.deviceUniqueId = concat(p.deviceUniqueId,t.ep) where t.modeNodeId" +
				"= p.id and t.houseId = :houseId and t.roomId = :roomId", params);*/
    	
		return 1;
	}
	
	@Override
	public int insertDeviceToRoom2(ModeNodeAndDevice modeNodeAndDevice) {
    	
    	List<ModeNodeAndDevice2> list3 = new ArrayList<ModeNodeAndDevice2>();
    	list3 = modeNodeAndDevice.getModeNodeAndDevice();
    	long houseId = 0;
    	long roomId = 0;
    	for(ModeNodeAndDevice2 ob1:list3){
    		Modenode modenode2 = new Modenode();
    		List<Modedevice> list2 = new ArrayList<Modedevice>();
    		modenode2 = ob1.getModenode();
    		houseId = modenode2.getHouseId();
    		roomId = modenode2.getRoomId();
    		break;
    	}
    	
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseId", houseId);
		params.put("roomId", roomId);
		// 更新deviceUniqueId
		modenodeDao.executeSql2("update Modenode t,Modenodelib p set t.deviceUniqueId = concat(p.modelId,concat(t.id,'')),t.description" +
				"= p.description where t.modeNodeLibId" +
				"= p.id and t.houseId = :houseId and t.roomId = :roomId", params);
		modedeviceDao.executeSql2("update Modedevice t,Modenode p set t.deviceUniqueId = concat(p.deviceUniqueId,t.ep) where t.modeNodeId" +
				"= p.id and t.houseId = :houseId and t.roomId = :roomId", params);
    	
		return 1;
	}
	
	/**
	 * 修改节点ieee和设备ieee
	 * @author: zhuangxd
	 * 时间：2014-1-10 上午11:04:20
	 * @param modenode
	 * @return
	 */
	@Override
	public int updateNodeAndDeviceIeee(Modenode modenode) {
    	
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenode.getId());
		params.put("ieee", modenode.getIeee());
		
		modenodeDao.executeSql2("update Modenode t set t.ieee = :ieee " +
				" where t.id = :id", params);
		modedeviceDao.executeSql2("update Modedevice t set t.ieee = :ieee where t.modeNodeId" +
				"= :id", params);
		/*modedevicebindDao.executeSql2("update Modenode m,Modedevice p,Modedevicebind t set t.sourceIeee = :ieee,p.ieee = :ieee,m.ieee = :ieee where m.id" +
				"= :id and m.id = p.modeNodeId and p.id = t.sourceId", params);
*/		modedevicebindDao.executeSql2("update Modenode m,Modedevice p,Modedevicebind t set t.sourceIeee = :ieee where m.id" +
				"= :id and m.id = p.modeNodeId and p.id = t.sourceId", params);
		modedevicebindDao.executeSql2("update Modenode m,Modedevice p,Modedevicebind t set t.destIeee = :ieee where m.id" +
				"= :id and m.id = p.modeNodeId and p.id = t.destId", params);
    	
		return 1;
	}
	
	@Override
	public Modedevice update(Modedevice modedevice) {
		modedeviceDao.update(modedevice);
		return modedevice;
	}
	
	@Override
	public Modenode updateModenode(Modenode modenode) {
		modenodeDao.update(modenode);
		return modenode;
	}
	
	@Override
	public Modedevice get(Modedevice modedevice) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modedevice.getId());
		Modedevice t = modedeviceDao.get("from Modedevice t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Modenode getModenode(Modenode modenode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenode.getId());
		Modenode t = modenodeDao.get("from Modenode t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/**
	 * 查找设备是否存在
	 * @author: zhuangxd
	 * 时间：2014-1-3 下午2:43:04
	 * @param modedevice
	 * @return
	 */
	@Override
	public Modedevice getDevice(Modedevice modedevice) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseId", modedevice.getHouseId());
		params.put("deviceName", modedevice.getDeviceName());
		Modedevice t = modedeviceDao.get("from Modedevice t where t.houseId = :houseId and t.deviceName = :deviceName", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/**
	 * 	查找node是否同名
	 * @author: zhuangxd
	 * 时间：2014-2-21 下午1:45:13
	 * @param modenode
	 * @return
	 */
	@Override
	public Modenode getModenode2(Modenode modenode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseId", modenode.getHouseId());
		params.put("deviceName", modenode.getDeviceName());
		Modenode t = modenodeDao.get("from Modenode t where t.houseId = :houseId and t.deviceName = :deviceName ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Modedevice find(Modedevice modedevice) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userName", modedevice.getUserName());
//		params.put("pwd", modedevice.getPwd());
		Modedevice t = modedeviceDao.get("from Modedevice t where t.userName = :userName and t.pwd = :pwd and t.enabled = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Modedevice> findList(Modedevice modedevice) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Modedevice t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (modedevice.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", modedevice.getHouseIeee());
		}
		if (modedevice.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + modedevice.getName() + "%");
		}
		if (modedevice.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", modedevice.getSecretKey());
		}
		if (modedevice.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", modedevice.getVendorCode());
		}
		if (modedevice.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + modedevice.getVendorName() + "%");
		}
		if (modedevice.getEncodemethod() != null) {
			hql.append("and t.encodemethod = :encodemethod ");
			params.put("encodemethod", modedevice.getEncodemethod());
		}
		if (modedevice.getXmppIp() != null) {
			hql.append("and t.xmppIp = :xmppIp ");
			params.put("xmppIp", modedevice.getXmppIp());
		}
		if (modedevice.getXmppPort() != null) {
			hql.append("and t.xmppPort = :xmppPort ");
			params.put("xmppPort", modedevice.getXmppPort());
		}
		if (modedevice.getCloudIp1() != null) {
			hql.append("and t.cloudIp1 = :cloudIp1 ");
			params.put("cloudIp1", modedevice.getCloudIp1());
		}
		if (modedevice.getCloudPort1() != null) {
			hql.append("and t.cloudPort1 = :cloudPort1 ");
			params.put("cloudPort1", modedevice.getCloudPort1());
		}
		if (modedevice.getCloudIp2() != null) {
			hql.append("and t.cloudIp2 = :cloudIp2 ");
			params.put("cloudIp2", modedevice.getCloudIp2());
		}
		if (modedevice.getCloudPort2() != null) {
			hql.append("and t.cloudPort2 = :cloudPort2 ");
			params.put("cloudPort2", modedevice.getCloudPort2());
		}
		if (modedevice.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", modedevice.getEnableFlag());
		}*/
		List<Modedevice> t = modedeviceDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Object[]> findListSql(Modedevice modedevice) {
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
//		String tableName = "deviceWarnHistory_" + modedevice.getHouseIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from Modedevice s,").append(tableName).append(" e where 1=1 and s.houseIeee = e.houseIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (modedevice.getHouseIeee() != null) {
			sql.append("and s.houseIeee = :houseIeee ");
			params.put("houseIeee", modedevice.getHouseIeee());
		}
		if (modedevice.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + modedevice.getName() + "%");
		}*/
		List<Object[]> t = modedeviceDao.findSql(sql.toString(), params, Modedevice.class, DevicewarnhistoryHouseidYear.class);
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
		// modedeviceDao.executeHql("delete Modedevice t where t.modeNodeId = :id ", params);	
		
		/*
		 delete u from modegroupsub u inner join modedevice d on u.DeviceID=d.id
							where d.modeNodeId=id;
		delete u from modescenesub u inner join modedevice d on u.DeviceID=d.id
							where d.modeNodeId=id;
		delete t from modemacrosub t inner join modedevice d on t.dest=d.id
							where d.modeNodeId=id and t.DestType=0;
		delete t from Modedevice t where t.modeNodeId = id;
		 */
		params.put("behavior", 21);
		params.put("houseId", -9999);
		// 调用存储过程
		String sql = "{CALL ModedeleteTest(:behavior,:houseId,:id)}";			
		modedeviceDao.executeSql2(sql, params);
		params = new HashMap<String, Object>();
		params.put("id", modenode.getId());
		modenodeDao.executeHql(hql.toString(), params);
		return 1;
	}
	
	@Override
	public int delete(Modedevice modedevice) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Modedevice t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		/*if (modedevice.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", modedevice.getHouseIeee());
			params2.put("houseIeee", modedevice.getHouseIeee());
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String tableName = "deviceAttributeHistory_" + modedevice.getHouseIeee() + "_" + year;
			tableName = "deviceOperateHistory_" + modedevice.getHouseIeee() + "_" + year;
			tableName = "deviceWarnHistory_" + modedevice.getHouseIeee() + "_" + year;
		}*/
		/*if (modedevice.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + modedevice.getName() + "%");
		}
		if (modedevice.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", modedevice.getSecretKey());
		}
		if (modedevice.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", modedevice.getVendorCode());
		}
		if (modedevice.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + modedevice.getVendorName() + "%");
		}
		if (modedevice.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", modedevice.getEnableFlag());
		}		*/
		
		/* 删除设备id */
		/*
		WHEN behavior=20 THEN	
				delete u from modegroupsub u where u.DeviceID=id;
				delete u from modescenesub u where u.DeviceID=id;
				delete t from modemacrosub t where t.Dest=id and t.DestType=0;		
				*/
		return modedeviceDao.executeHql(hql.toString(), params);
	}

	@Override
	public Modedevice login(Modedevice modedevice) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(modedevice.getPwd()));
//		params.put("name", modedevice.getName());
		Modedevice t = modedeviceDao.get("from Modedevice t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return modedevice;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(Modedevice modedevice) {
		DataGrid dg = new DataGrid();
		String hql = "from Modedevice t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(modedevice, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(modedevice, hql);
		List<Modedevice> l = modedeviceDao.find(hql, params, modedevice.getPage(), modedevice.getRows());
		List<Modedevice> nl = new ArrayList<Modedevice>();
		changeModel(l, nl);
		dg.setTotal(modedeviceDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Modedevice> l, List<Modedevice> nl) {
		if (l != null && l.size() > 0) {
			for (Modedevice t : l) {
				Modedevice u = new Modedevice();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Modedevice modedevice, String hql) {
		/*if (modedevice.getSort() != null) {
			hql += " order by " + modedevice.getSort() + " " + modedevice.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Modedevice modedevice, String hql, Map<String, Object> params) {
		/*if (modedevice.getName() != null && !modedevice.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + modedevice.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Modedevice t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		modedeviceDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}

	@Override
	public List<Map> modenodeCount(Map<String, Object> map) {
		String sqlString="select count(*) as aCount from modenode where houseId =:houseId";
		List<Map> list = mapDao.executeSql(sqlString, map);
		return list;
	}
	@Override
	public List<Map> modedeviceCount(Map<String, Object> map) {
		String sqlString="select count(*) as aCount from modedevice where houseId =:houseId";
		List<Map> list = mapDao.executeSql(sqlString, map);
		return list;
	}
	@Override
	public int addEpPage(List<Map> list,String houseid) {
		int a= 0 ;
		if(list.size()>0){
			//对familyeppage操作
			StringBuffer sql1= new StringBuffer();
			sql1.append("delete from familyeppage where ");
//			for (int i = 0; i < list.size(); i++) {
				sql1 = sql1.append("houseid = ").append(list.get(0).get("houseId"));
//			}
//			sql1.delete(sql1.length()-3,sql1.length());
			int b = mapDao.executeSql2(sql1.toString());
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO familyeppage(ep_id,IEEE,houseid,ep,oldId) select id,ieee,houseId,ep,oldId from modedevice where ");
			for(int i = 0 ; i <list.size(); i++){
				sql = sql.append("id = ").append(list.get(i).get("id")).append(" or ");
			}
			sql.delete(sql.length()-3,sql.length());
			int d = mapDao.executeSql2(sql.toString());
			
			//对smarteppage操作
			StringBuffer sql2= new StringBuffer();
			sql2.append("delete from smarteppage where ");
//			for (int i = 0; i < list.size(); i++) {
				sql2 = sql2.append("houseid = ").append(list.get(0).get("houseId"));
//			}
//			sql2.delete(sql2.length()-3,sql2.length());
			int c = mapDao.executeSql2(sql2.toString());
			StringBuffer sql3 = new StringBuffer();
			sql3.append("INSERT INTO smarteppage(ep_id,IEEE,houseid,ep,oldId) select id,ieee,houseId,ep,oldId from modedevice where ");
			for(int i = 0 ; i <list.size(); i++){
				sql3 = sql3.append("id = ").append(list.get(i).get("id")).append(" or ");
			}
			sql3.delete(sql3.length()-3,sql3.length());
			int e  = mapDao.executeSql2(sql3.toString());
			
			if(e>0 && d>0){
				a=1;
			}
			return a ;
		}
		else {
			//对familyeppage操作
			StringBuffer sql1= new StringBuffer();
			sql1.append("delete from familyeppage where ");
//			for (int i = 0; i < list.size(); i++) {
				sql1 = sql1.append("houseid = ").append(houseid);
//			}
//			sql1.delete(sql1.length()-3,sql1.length());
			int b = mapDao.executeSql2(sql1.toString());
			
			//对smarteppage操作
			StringBuffer sql2= new StringBuffer();
			sql2.append("delete from smarteppage where ");
//			for (int i = 0; i < list.size(); i++) {
				sql2 = sql2.append("houseid = ").append(houseid);
//			}
//			sql2.delete(sql2.length()-3,sql2.length());
			int c = mapDao.executeSql2(sql2.toString());
			return 1 ;
		}
	}
	@Override
	public Map findEpPage(Map<String, Object> map) {
		Map<String, Object> rt = new HashMap<String, Object>();
		String sql = "SELECT m.*,f.id as fid from modedevice m LEFT JOIN familyeppage f on m.id = f.ep_id and f.houseid = m.houseId WHERE m.houseId = :houseId and m.roomId = :roomId";
		List<Map> list = mapDao.executeSql(sql, map);
		if(list != null && list.size() > 0) {
			rt.put("modedevice",list);	
		} else
			rt.put("modedevice",null);
		
		return rt;
	}
	@Override
	public int addModePage(List<Map> list,String houseid) {
		int a= 0 ;
		if(list.size()>0){
			//对familyModepage操作
			StringBuffer sql1= new StringBuffer();
			sql1.append("delete from familymodepage where ");
//			for (int i = 0; i < list.size(); i++) {
				sql1 = sql1.append("houseId = ").append(list.get(0).get("houseId"));
//			}
//			sql1.delete(sql1.length()-3,sql1.length());
			int b = mapDao.executeSql2(sql1.toString());
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO familymodepage(ModeID,houseId,oldId) SELECT ID,HouseID,oldId FROM usermodemain WHERE ");
			for(int i = 0 ; i <list.size(); i++){
				sql = sql.append("ID=").append(list.get(i).get("id")).append(" or ");
			}
			sql.delete(sql.length()-3,sql.length());
			int d = mapDao.executeSql2(sql.toString());
			
			//对smartmodepage操作
			StringBuffer sql2= new StringBuffer();
			sql2.append("delete from smartmodepage where ");
//			for (int i = 0; i < list.size(); i++) {
				sql2 = sql2.append("houseId = ").append(list.get(0).get("houseId"));
//			}
//			sql2.delete(sql2.length()-3,sql2.length());
			int c = mapDao.executeSql2(sql2.toString());
			StringBuffer sql3 = new StringBuffer();
			sql3.append("INSERT INTO smartmodepage(ModeID,houseId,oldId) SELECT ID,HouseID,oldId FROM usermodemain WHERE ");
			for(int i = 0 ; i <list.size(); i++){
				sql3 = sql3.append("ID=").append(list.get(i).get("id")).append(" or ");
			}
			sql3.delete(sql3.length()-3,sql3.length());
			int e = mapDao.executeSql2(sql3.toString());
			
			if(e>0 && d>0){
				a=1;
			}
			return a ;
		}
		else {
			//对familyModepage操作
			StringBuffer sql1= new StringBuffer();
			sql1.append("delete from familymodepage where ");
//			for (int i = 0; i < list.size(); i++) {
				sql1 = sql1.append("houseId = ").append(houseid);
//			}
//			sql1.delete(sql1.length()-3,sql1.length());
			int b = mapDao.executeSql2(sql1.toString());
			
			//对smartmodepage操作
			StringBuffer sql2= new StringBuffer();
			sql2.append("delete from smartmodepage where ");
//			for (int i = 0; i < list.size(); i++) {
				sql2 = sql2.append("houseId = ").append(houseid);
//			}
//			sql2.delete(sql2.length()-3,sql2.length());
			int c = mapDao.executeSql2(sql2.toString());
			return 1 ;
		}
	}
	@Override
	public Object findroom(Target tar) {
		String roomSql = "select r.* from Moderoom r where r.houseId = :houseId";
		String mainSql = "select m.*,f.id as fid from Usermodemain m left join familymodepage f on m.id=f.ModeId and f.houseid = m.houseId where m.houseId = :houseId order by m.id";
	    	List<Moderoom> roomList=dao.executeSql(roomSql,
						QueryParameters.with("houseId", tar.getHouseId()).parameters());	
	    	List<Usermodemain> mainList= dao.executeSql(mainSql,
	    				QueryParameters.with("houseId", tar.getHouseId()).parameters());
	        Xmlmoderoom xmlroom=new Xmlmoderoom();  
	    	xmlroom.setMain(roomList);
	    	xmlroom.setSub(mainList);
	    	logger.info("1111--" +xmlroom);   
	    	return xmlroom;   		
	}
}
//select id,nodeType,imgdetails,modelId,deviceName,clusterId,clusterName,destType,source_id,deviceType,picName,description,powerType,activationMethod,resetDefault,remark from modenodelib left join imgdetailtab on modenodelib.id=imgdetailtab.modeNodeLibIds where 1=1 and deviceName like :deviceName 