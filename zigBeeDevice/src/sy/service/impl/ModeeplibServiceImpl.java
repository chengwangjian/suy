package sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flywind.app.entities.Deviceattrlib;

import sy.dao.BaseDaoI;
import sy.model.Modeeplib;
import sy.model.ModeeplibEn;
import sy.model.Modenodelib;
import sy.service.ModeeplibServiceI;


@Service("modeeplibService")
public class ModeeplibServiceImpl implements ModeeplibServiceI{
	
	private static final Logger logger = 
			Logger.getLogger(ModeeplibServiceImpl.class);
	
	private BaseDaoI<Modenodelib> modenodelibDao;
	private BaseDaoI<Modeeplib> modeeplibDao;
	private BaseDaoI<ModeeplibEn> modeeplibenDao;
	private BaseDaoI<Map> mapDao;
	private BaseDaoI<Deviceattrlib> deviceatrlib;
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<ModeeplibEn> getModeeplibenDao() {
		return modeeplibenDao;
	}
	
	@Autowired
	public void setModeeplibenDao(BaseDaoI<ModeeplibEn> modeeplibenDao) {
		this.modeeplibenDao = modeeplibenDao;
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

	@Override
	public Modeeplib save(Modeeplib modeeplib) {
		modeeplibDao.save(modeeplib);
		return modeeplib;
	}
	
	@Override
	public ModeeplibEn save(ModeeplibEn modeepliben) {
		modeeplibenDao.save(modeepliben);
		return modeepliben;
	}

	@Override
	public Modeeplib find(Modeeplib modeeplib) {
		Map<String, Object> params = new HashMap<String, Object>();
		Modeeplib t = modeeplibDao.get("from Modeeplib t where 1=1 ", params);
		if (t != null) {
			return t;
		}
		return null;
	}

	@Override
	public List<Map> findList(Modeeplib modeeplib) {
//		StringBuffer hql = new StringBuffer();
//		hql.append("from Modeeplib t where 1=1 ");
		StringBuffer sql = new StringBuffer();
//		if(language.equals("1")){
//			sql.append("select id,nodeId,deviceId,internelModelId,deviceNameEn,clusterId,clusterNameEn,picName,destType,deviceType,deviceTypeV2,ep,descriptionEn,groupable ");
//			sql.append("from modeeplib where 1=1 ");
//		}
//		else {
			sql.append("select id,nodeId,deviceId,internelModelId,deviceName,deviceNameEn,clusterId,clusterName,clusterNameEn,picName,destType,deviceType,deviceTypeV2,ep,description,groupable,SolidModelID ");
			sql.append("from modeeplib where 1=1 ");
//		}
		Map<String, Object> params = new HashMap<String, Object>();
		if (modeeplib.getId()!=0) {
			sql.append("and id = :id ");
			params.put("id",  modeeplib.getId());
		}
		if (modeeplib.getNodeId()!=0) {
			sql.append("and nodeId = :nodeId ");
			params.put("nodeId",  modeeplib.getNodeId());
		}
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}

	@Override
	public Modeeplib update(Modeeplib modeeplib) {
		modeeplibDao.update(modeeplib);
		return modeeplib;
	}

	@Override
	public int delete(Modeeplib modeeplib) {
//		StringBuffer hql = new StringBuffer();
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("id", modeeplib.getId());
//		hql.append("delete Modeeplib t where t.id = :id");
//		return modeeplibDao.executeHql(hql.toString(), params);
		String sql = "delete m.*,a.* from modeeplib m left join modeepactlib a on m.SolidModelID = a.SolidModelID where m.id="+modeeplib.getId();
		return mapDao.executeSql2(sql);
	}

	@Override
	public Modeeplib get(Modeeplib modeeplib) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modeeplib.getId());
		Modeeplib t = modeeplibDao.get("from Modeeplib t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}

	/**
	 * 用户管理后台设备管理ep查询
	 */
	@Override
	public List<ModeeplibEn> findListen(ModeeplibEn modeepliben) {
		StringBuffer hql = new StringBuffer();
		hql.append("from ModeeplibEn t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (modeepliben.getId()!=0) {
			hql.append("and t.id = :id ");
			params.put("id",  modeepliben.getId());
		}
		if (modeepliben.getNodeId()!=0) {
			hql.append("and t.nodeId = :nodeId ");
			params.put("nodeId",  modeepliben.getNodeId());
		}
		List<ModeeplibEn> t = modeeplibenDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}

	/**
	 * 英文版用户管理判读EP是否存在
	 */
	@Override
	public ModeeplibEn geten(ModeeplibEn modeepliben) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modeepliben.getId());
		ModeeplibEn t = modeeplibenDao.get("from ModeeplibEn t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}

	@Override
	public ModeeplibEn updaten(ModeeplibEn modeepliben) {
		modeeplibenDao.update(modeepliben);
		return modeepliben;
	}

	/**
	 * 英文版用户管理删除EP
	 */
	@Override
	public int deleten(ModeeplibEn modeepliben) {
		StringBuffer hql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modeepliben.getId());
		hql.append("delete ModeeplibEn t where t.id = :id");
		return modeeplibDao.executeHql(hql.toString(), params);
	}
	@Override
	public int saveModeEpactlib(List<Map> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into modeepactlib(SolidModelID,ActID) values");
		for (int i = 0; i < list.size(); i++) {
			sb=sb.append("('").append(list.get(i).get("SolidModelID")).append("',").append("'").append(list.get(i).get("actId")).append("'),");
		}
		String sbStr=sb.deleteCharAt(sb.length()-1).toString();
		int i = mapDao.executeSql2(sbStr);
		return i;
	}
	@Override
	public List<Map> findActList(Modeeplib modeeplib) {
		String sql ="select a.ActID from modeeplib m left join modeepactlib a on m.SolidModelID = a.SolidModelID where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		if (modeeplib.getId()!=0) {
			sql+="and m.id = :id ";
			params.put("id",  modeeplib.getId());
		}
		if (modeeplib.getNodeId()!=0) {
			sql+="and nodeId = :nodeId ";
			params.put("nodeId",  modeeplib.getNodeId());
		}
		List<Map> t = mapDao.executeSql(sql, params);
		if (t != null) {
			return t;
		}
		return null;
	}
	@Override
	public int updateModeEpactlib(List<Map> list, Modeeplib modeeplib) {
		String sql = "delete from modeepactlib where SolidModelID ="+list.get(0).get("SolidModelID");
		mapDao.executeSql2(sql);
		StringBuffer sb = new StringBuffer();
		sb.append("insert into modeepactlib(SolidModelID,ActID) values");
		for (int i = 0; i < list.size(); i++) {
			sb=sb.append("('").append(modeeplib.getSolidModelID()).append("',").append("'").append(list.get(i).get("actId")).append("'),");
		}
		String sbStr=sb.deleteCharAt(sb.length()-1).toString();
		int i = mapDao.executeSql2(sbStr);
		return i;
	}
	@Override
	public List<Deviceattrlib> getAttrLib() {
		// TODO Auto-generated method stub
		return deviceatrlib.executeSql("select DISTINCT ClusterID,AttrID,UniqueName from DeviceAttrLib");
		//return null;
	}
	@Override
	public List<Deviceattrlib> listAttrLib(int page) {
		// TODO Auto-generated method stub
		return deviceatrlib.executeSql("select *  from DeviceAttrLib LIMIT "+(page*10)+",10");
	}
	@Override
	public int getcountattr() {
		// TODO Auto-generated method stub
		return deviceatrlib.executeSql("select count(*)as counts from DeviceAttrLib").size();
	}
}
