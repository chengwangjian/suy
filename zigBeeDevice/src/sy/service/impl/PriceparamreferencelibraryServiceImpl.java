package sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Priceparamreferencelibrary;
import sy.service.PriceparamreferencelibraryServiceI;

@Service("priceparamreferencelibraryService")
public class PriceparamreferencelibraryServiceImpl implements PriceparamreferencelibraryServiceI {

	private static final Logger logger = Logger.getLogger(PriceparamreferencelibraryServiceImpl.class);

	private BaseDaoI<Priceparamreferencelibrary> priceparamreferencelibraryDao;

	public BaseDaoI<Priceparamreferencelibrary> getPriceparamreferencelibraryDao() {
		return priceparamreferencelibraryDao;
	}

	@Autowired
	public void setPriceparamreferencelibraryDao(BaseDaoI<Priceparamreferencelibrary> priceparamreferencelibraryDao) {
		this.priceparamreferencelibraryDao = priceparamreferencelibraryDao;
	}

	@Override
	public Priceparamreferencelibrary save(Priceparamreferencelibrary priceparamreferencelibrary) {
		priceparamreferencelibraryDao.save(priceparamreferencelibrary);
		return priceparamreferencelibrary;
	}

	@Override
	public Priceparamreferencelibrary get(Priceparamreferencelibrary priceparamreferencelibrary) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", priceparamreferencelibrary.getHouseIeee());
		/*params.put("deviceType", priceparamreferencelibrary.getDeviceType());
		params.put("ieee", priceparamreferencelibrary.getIeee());
		params.put("ep", priceparamreferencelibrary.getEp());*/
		Priceparamreferencelibrary t = priceparamreferencelibraryDao.get("from Priceparamreferencelibrary t where t.houseIeee = :houseIeee " +
				"and t.deviceType = :deviceType and t.ieee = :ieee and t.ep = :ep", params);
		if (t != null) {
			return t;
		}
		return null;		
	}
	
	@Override
	public Priceparamreferencelibrary getExisit(Priceparamreferencelibrary priceparamreferencelibrary) {
		Map<String, Object> params = new HashMap<String, Object>();
		/*params.put("houseIeee", priceparamreferencelibrary.getHouseIeee());
		params.put("ieee", priceparamreferencelibrary.getIeee());
		params.put("ep", priceparamreferencelibrary.getEp());*/
		Priceparamreferencelibrary t = priceparamreferencelibraryDao.get("from Priceparamreferencelibrary t where t.houseIeee = :houseIeee " +
				"and t.deviceType in ('1','2','3','4') and t.ieee = :ieee and t.ep = :ep", params);
		if (t != null) {
			return t;
		}			
		return null;		
	}
	
	@Override
	public List<Priceparamreferencelibrary> findList(Priceparamreferencelibrary priceparamreferencelibrary) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Priceparamreferencelibrary t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (priceparamreferencelibrary.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", priceparamreferencelibrary.getHouseIeee());
		}
		if (priceparamreferencelibrary.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", priceparamreferencelibrary.getHouseIeee());
		}
		/*if (priceparamreferencelibrary.getDeviceType() != null) {
			hql.append("and t.deviceType = :deviceType ");
			params.put("deviceType", priceparamreferencelibrary.getDeviceType());
		}
		if (priceparamreferencelibrary.getIeee() != null) {
			hql.append("and t.ieee = :ieee ");
			params.put("ieee", priceparamreferencelibrary.getIeee());
		}
		if (priceparamreferencelibrary.getEp() != null) {
			hql.append("and t.ep = :ep ");
			params.put("ep", priceparamreferencelibrary.getEp());
		}*/
		List<Priceparamreferencelibrary> t = priceparamreferencelibraryDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Priceparamreferencelibrary update(Priceparamreferencelibrary priceparamreferencelibrary) {
		priceparamreferencelibraryDao.update(priceparamreferencelibrary);
		return priceparamreferencelibrary;
	}
	
	@Override
	public int delete(Priceparamreferencelibrary priceparamreferencelibrary) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Priceparamreferencelibrary t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (priceparamreferencelibrary.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", priceparamreferencelibrary.getHouseIeee());
		}
		/*if (priceparamreferencelibrary.getDeviceType() != null) {
			hql.append("and t.deviceType = :deviceType ");
			params.put("deviceType", priceparamreferencelibrary.getDeviceType());
		}
		if (priceparamreferencelibrary.getIeee() != null) {
			hql.append("and t.ieee = :ieee ");
			params.put("ieee", priceparamreferencelibrary.getIeee());
		}
		if (priceparamreferencelibrary.getEp() != null) {
			hql.append("and t.ep = :ep ");
			params.put("ep", priceparamreferencelibrary.getEp());
		}*/
		return priceparamreferencelibraryDao.executeHql(hql.toString(), params);
	}

	private void changeModel(List<Priceparamreferencelibrary> l, List<Priceparamreferencelibrary> nl) {
		if (l != null && l.size() > 0) {
			for (Priceparamreferencelibrary t : l) {
				Priceparamreferencelibrary u = new Priceparamreferencelibrary();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Priceparamreferencelibrary priceparamreferencelibrary, String hql) {
		/*if (priceparamreferencelibrary.getSort() != null) {
			hql += " order by " + priceparamreferencelibrary.getSort() + " " + priceparamreferencelibrary.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Priceparamreferencelibrary priceparamreferencelibrary, String hql, Map<String, Object> params) {
		/*if (priceparamreferencelibrary.getName() != null && !priceparamreferencelibrary.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + priceparamreferencelibrary.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Priceparamreferencelibrary t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		priceparamreferencelibraryDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
}
