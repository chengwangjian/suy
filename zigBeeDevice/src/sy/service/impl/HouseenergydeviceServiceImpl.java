package sy.service.impl;

import java.util.Date;
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
import sy.model.Houseenergydevice;
import sy.service.HouseenergydeviceServiceI;

@Service("houseenergydeviceService")
public class HouseenergydeviceServiceImpl implements HouseenergydeviceServiceI {

	private static final Logger logger = Logger.getLogger(HouseenergydeviceServiceImpl.class);

	private BaseDaoI<Houseenergydevice> houseenergydeviceDao;

	public BaseDaoI<Houseenergydevice> getHouseenergydeviceDao() {
		return houseenergydeviceDao;
	}

	@Autowired
	public void setHouseenergydeviceDao(BaseDaoI<Houseenergydevice> houseenergydeviceDao) {
		this.houseenergydeviceDao = houseenergydeviceDao;
	}

	@Override
	public Houseenergydevice save(Houseenergydevice houseenergydevice) {
		houseenergydeviceDao.save(houseenergydevice);
		return houseenergydevice;
	}

	@Override
	public Houseenergydevice get(Houseenergydevice houseenergydevice) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseenergydevice.getHouseIeee());
		params.put("deviceType", houseenergydevice.getDeviceType());
		params.put("ieee", houseenergydevice.getIeee());
		params.put("ep", houseenergydevice.getEp());
		Houseenergydevice t = houseenergydeviceDao.get("from Houseenergydevice t where t.houseIeee = :houseIeee " +
				"and t.deviceType = :deviceType and t.ieee = :ieee and t.ep = :ep", params);
		if (t != null) {
			return t;
		}
		return null;		
	}
	
	@Override
	public Houseenergydevice getExisit(Houseenergydevice houseenergydevice) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseenergydevice.getHouseIeee());
		params.put("ieee", houseenergydevice.getIeee());
		params.put("ep", houseenergydevice.getEp());
		if (houseenergydevice.getDeviceType().equals("0")) {
			return null;
		}
		Houseenergydevice t = houseenergydeviceDao.get("from Houseenergydevice t where t.houseIeee = :houseIeee " +
				"and t.deviceType in ('1','2','3','4') and t.ieee = :ieee and t.ep = :ep", params);
		if (t != null) {
			return t;
		}			
		return null;		
	}
	
	@Override
	public List<Houseenergydevice> findList(Houseenergydevice houseenergydevice) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Houseenergydevice t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (houseenergydevice.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", houseenergydevice.getHouseIeee());
		}
		if (houseenergydevice.getDeviceType() != null) {
			hql.append("and t.deviceType = :deviceType ");
			params.put("deviceType", houseenergydevice.getDeviceType());
		}
		if (houseenergydevice.getIeee() != null) {
			hql.append("and t.ieee = :ieee ");
			params.put("ieee", houseenergydevice.getIeee());
		}
		if (houseenergydevice.getEp() != null) {
			hql.append("and t.ep = :ep ");
			params.put("ep", houseenergydevice.getEp());
		}
		List<Houseenergydevice> t = houseenergydeviceDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Houseenergydevice update(Houseenergydevice houseenergydevice) {
		houseenergydeviceDao.update(houseenergydevice);
		return houseenergydevice;
	}
	
	@Override
	public int delete(Houseenergydevice houseenergydevice) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Houseenergydevice t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (houseenergydevice.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", houseenergydevice.getHouseIeee());
		}
		if (houseenergydevice.getDeviceType() != null) {
			hql.append("and t.deviceType = :deviceType ");
			params.put("deviceType", houseenergydevice.getDeviceType());
		}
		if (houseenergydevice.getIeee() != null) {
			hql.append("and t.ieee = :ieee ");
			params.put("ieee", houseenergydevice.getIeee());
		}
		if (houseenergydevice.getEp() != null) {
			hql.append("and t.ep = :ep ");
			params.put("ep", houseenergydevice.getEp());
		}
		return houseenergydeviceDao.executeHql(hql.toString(), params);
	}
	
	@Override
	public int replaceDevice(Houseenergydevice houseenergydevice) {
		StringBuffer hql = new StringBuffer();
		hql.append("update Houseenergydevice t set t.ieee = :newIeee ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNoneBlank(houseenergydevice.getNewEp())) {
			hql.append(", t.ep = :newEp ");
			params.put("newEp", houseenergydevice.getNewEp());
		}
		hql.append(" where t.houseIeee = :houseIeee and t.ieee = :ieee ");		
		params.put("houseIeee", houseenergydevice.getHouseIeee());
		params.put("newIeee", houseenergydevice.getNewIeee());
		params.put("ieee", houseenergydevice.getIeee());
		if (StringUtils.isNoneBlank(houseenergydevice.getEp())) {
			hql.append("and t.ep = :ep ");
			params.put("ep", houseenergydevice.getEp());
		}
		houseenergydeviceDao.executeHql(hql.toString(), params);
		
		hql = new StringBuffer();
		hql.append("update Device t set t.lasttime= :lasttime, t.ieee = :newIeee ");
		params = new HashMap<String, Object>();
		params.put("lasttime", new Date());
		if (StringUtils.isNoneBlank(houseenergydevice.getNewEp())) {
			hql.append(", t.ep = :newEp ");
			params.put("newEp", houseenergydevice.getNewEp());
		}
		hql.append(" where t.houseIeee = :houseIeee and t.ieee = :ieee ");		
		params.put("houseIeee", houseenergydevice.getHouseIeee());
		params.put("newIeee", houseenergydevice.getNewIeee());
		params.put("ieee", houseenergydevice.getIeee());
		if (StringUtils.isNoneBlank(houseenergydevice.getEp())) {
			hql.append("and t.ep = :ep ");
			params.put("ep", houseenergydevice.getEp());
		}
		return houseenergydeviceDao.executeHql(hql.toString(), params);
	}

	private void changeModel(List<Houseenergydevice> l, List<Houseenergydevice> nl) {
		if (l != null && l.size() > 0) {
			for (Houseenergydevice t : l) {
				Houseenergydevice u = new Houseenergydevice();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Houseenergydevice houseenergydevice, String hql) {
		/*if (houseenergydevice.getSort() != null) {
			hql += " order by " + houseenergydevice.getSort() + " " + houseenergydevice.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Houseenergydevice houseenergydevice, String hql, Map<String, Object> params) {
		/*if (houseenergydevice.getName() != null && !houseenergydevice.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + houseenergydevice.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Houseenergydevice t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		houseenergydeviceDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
}
