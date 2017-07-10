package sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Modeeplib;
import sy.model.ModeeplibEn;
import sy.model.Modenodelib;
import sy.model.ModenodelibEn;
import sy.service.ModenodelibServiceI;


@Service("modenodelibService")
public class ModenodelibServiceImpl implements ModenodelibServiceI{
	
	private static final Logger logger = 
			Logger.getLogger(ModenodelibServiceImpl.class);
	private BaseDaoI<ModenodelibEn> modenodelibenDao;
	
	public BaseDaoI<ModenodelibEn> getModenodelibenDao() {
		return modenodelibenDao;
	}
	
	private BaseDaoI<Map> mapDao;
	@Autowired
	public void setModenodelibenDao(BaseDaoI<ModenodelibEn> modenodelibenDao) {
		this.modenodelibenDao = modenodelibenDao;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	private BaseDaoI<Modenodelib> modenodelibDao;
	private BaseDaoI<Modeeplib> modeeplibDao;
	private BaseDaoI<ModeeplibEn> modeeplibenDao;


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
	public Modenodelib save(Modenodelib modenodelib) {
		modenodelibDao.save(modenodelib);
		return modenodelib;
	}

	@Override
	public ModenodelibEn save(ModenodelibEn Modenodeliben) {
		modenodelibenDao.save(Modenodeliben);
		return Modenodeliben;
	}
	
	@Override
	public Modenodelib find(Modenodelib modenodelib) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenodelib.getId());
		Modenodelib t = modenodelibDao.get("from Modenodelib t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}

	@Override
	public List<Map> findList(Modenodelib modenodelib) {
//		StringBuffer hql = new StringBuffer();
		StringBuffer sql = new StringBuffer();
//		if(language.equals("1")){
//			sql.append("select id,nodeType,modelId,deviceNameEn,clusterId,clusterNameEn,destType,source_id,deviceType,picName,descriptionEn,powerTypeEn,activationMethodEn,resetDefaultEn,remarkEn ");
//			sql.append("from modenodelib where 1=1 ");
////			hql.append("from ModenodelibEn t where 1=1 ");
//		}
//		else{
			sql.append("select id,nodeType,modelId,deviceName,deviceNameEn,clusterId,clusterName,clusterNameEn,destType,source_id,deviceType,picName,description,powerType,activationMethod,resetDefault,remark,");
			sql.append("descriptionEn,powerTypeEn,activationMethodEn,resetDefaultEn,remarkEn ");
			sql.append("from modenodelib where 1=1 ");
//			hql.append("from Modenodelib t where 1=1 ");
//		}
			Map<String, Object> params = new HashMap<String, Object>();
			if (modenodelib.getId() != 0) {
				sql.append("and id = :id ");
				params.put("id",  modenodelib.getId());
			}
			sql.append(" order by id desc");
			List<Map> t = mapDao.executeSql(sql.toString(), params);
//			List<Modenodelib> t = modenodelibDao.find(sql.toString(),params);
			if (t != null) {
				return t;
			}
		
		return null;
	}

	@Override
	public Modenodelib update(Modenodelib modenodelib) {
		modenodelibDao.update(modenodelib);
		return modenodelib;
	}

	@Override
	public int delete(Modenodelib modenodelib) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenodelib.getId());
		StringBuffer hql = new StringBuffer();
		hql.append("delete Modenodelib t where  t.id = :id ");
		modenodelibDao.executeHql(hql.toString(), params);
		modeeplibDao.executeHql("delete Modeeplib t where t.nodeId = :id", params);
		return 1;
	}
	
	@Override
	public int deleten(ModenodelibEn modenodeliben) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenodeliben.getId());
		StringBuffer hql = new StringBuffer();
		hql.append("delete ModenodelibEn t where  t.id = :id ");
		modenodelibenDao.executeHql(hql.toString(), params);
		modeeplibenDao.executeHql("delete ModeeplibEn t where t.nodeId = :id", params);
		return 1;
	}
	@Override
	public Modenodelib get(Modenodelib modenodelib) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenodelib.getId());
		Modenodelib t = modenodelibDao.get("from Modenodelib t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Modenodelib getModenodelib(Modenodelib modenodelib) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modelId", modenodelib.getModelId());
		Modenodelib t = new Modenodelib();
		ModenodelibEn te = new ModenodelibEn();
		t = modenodelibDao.get("from Modenodelib t where t.modelId = :modelId ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	@Override
	public ModenodelibEn getModenodelibEn(ModenodelibEn ModenodelibEn) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modelId", ModenodelibEn.getModelId());
		ModenodelibEn t = new ModenodelibEn();
		t = modenodelibenDao.get("from ModenodelibEn t where t.modelId = :modelId ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	@Override
	public ModenodelibEn geten(ModenodelibEn Modenodeliben) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", Modenodeliben.getId());
		ModenodelibEn t = modenodelibenDao.get("from ModenodelibEn t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public ModenodelibEn updaten(ModenodelibEn modenodeliben) {
		modenodelibenDao.update(modenodeliben);
		return modenodeliben;
	}
}
