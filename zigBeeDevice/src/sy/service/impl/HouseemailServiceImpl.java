package sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.House;
import sy.model.Houseemail;
import sy.service.HouseemailServiceI;

@Service("houseemailService")
public class HouseemailServiceImpl implements HouseemailServiceI{
	
	private static final Logger logger = Logger
			.getLogger(HouseemailServiceImpl.class);
	
	public BaseDaoI<Houseemail> houseemailDao;
	public BaseDaoI<House> houseDao;
	
	public BaseDaoI<Houseemail> getHouseemailDao() {
		return houseemailDao;
	}
	
	@Autowired
	public void setHouseemailDao(BaseDaoI<Houseemail> houseemailDao) {
		this.houseemailDao = houseemailDao;
	}
	
	public BaseDaoI<House> getHouseDao() {
		return houseDao;
	}
	@Autowired
	public void setHouseDao(BaseDaoI<House> houseDao) {
		this.houseDao = houseDao;
	}

	@Override
	public Houseemail save(Houseemail houseemail) {
		houseemailDao.save(houseemail);
		return houseemail;
	}
	
	@Override
	public Houseemail get(Houseemail houseemail)  {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseemail.getHouseIeee());
		Houseemail t = houseemailDao.get("from Houseemail t where t.houseIeee = :houseIeee",params);
		if (t!=null) {
			return t;
		}
		return null;
	}
	@Override
	public Houseemail find(Houseemail houseemail) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseemail.getHouseIeee());
		Houseemail t = houseemailDao.get("from Houseemail t where t.houseIeee = :houseIeee",params);
		if (t!=null) {
			return t;
		}
		return null;
	}

	@Override
	public List<Houseemail> findList(Houseemail houseemail) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id as id,a.houseIeee as houseIeee,a.email as email,a.createtime as createtime," +
				"a.lasttime as lasttime from Houseemail a join House b on a.houseIeee = b.houseIeee ");
		List<Houseemail> t = houseemailDao.findSql(sql.toString(),null,Houseemail.class);
		if (t!=null) {
			return t;
		}
		return null;
	}

	@Override
	public Houseemail update(Houseemail houseemail) {
		houseemailDao.update(houseemail);
		return houseemail;
	}

	@Override
	public int delete(Houseemail houseemail) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseemail.getHouseIeee());
		StringBuffer hql = new StringBuffer();
		hql.append("delete Houseemail t where  1=1 ");
		if (houseemail.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", houseemail.getHouseIeee());
		}
		houseemailDao.executeHql(hql.toString(), params);
		House house = houseDao.get("FROM House t WHERE t.houseIeee=:houseIeee",params);
		params.clear();
		params.put("id", house.getId());
		houseDao.executeHql("delete House t where t.id = :id", params);
		return 1;
	}
}
