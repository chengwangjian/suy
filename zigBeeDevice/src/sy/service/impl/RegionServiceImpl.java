package sy.service.impl;

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
import sy.model.Region;
import sy.service.RegionServiceI;

@Service("regionService")
public class RegionServiceImpl implements RegionServiceI {

	private static final Logger logger = Logger.getLogger(RegionServiceImpl.class);

	private BaseDaoI<Region> regionDao;
	
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<Region> getRegionDao() {
		return regionDao;
	}

	@Autowired
	public void setRegionDao(BaseDaoI<Region> regionDao) {
		this.regionDao = regionDao;
	}

	@Override
	public Region save(Region region) {
		regionDao.save(region);
		return region;
	}

	@Override
	public Region get(Region region) {
		Map<String, Object> params = new HashMap<String, Object>();
		/*params.put("houseIeee", region.getHouseIeee());
		params.put("deviceType", region.getDeviceType());
		params.put("ieee", region.getIeee());
		params.put("ep", region.getEp());*/
		Region t = regionDao.get("from Region t where t.houseIeee = :houseIeee " +
				"and t.deviceType = :deviceType and t.ieee = :ieee and t.ep = :ep", params);
		if (t != null) {
			return t;
		}
		return null;		
	}
	
	@Override
	public Region getExisit(Region region) {
		Map<String, Object> params = new HashMap<String, Object>();
		/*params.put("houseIeee", region.getHouseIeee());
		params.put("ieee", region.getIeee());
		params.put("ep", region.getEp());*/
		Region t = regionDao.get("from Region t where t.houseIeee = :houseIeee " +
				"and t.deviceType in ('1','2','3','4') and t.ieee = :ieee and t.ep = :ep", params);
		if (t != null) {
			return t;
		}			
		return null;		
	}
	
	@Override
	public List<Map> findList(Region region,String language) {
		StringBuffer sql = new StringBuffer();
		List<Map> list=null;
		HashMap<String,Object> params=new HashMap<String, Object>();
         if(!"".equals(language)){
            sql.append("SELECT region.id,regionlang.regionName,region.regionCode,region.description from regionlang INNER JOIN region on regionlang.regionId=region.id WHERE regionlang.languageIdentity=:type"); 
        	params.put("type",language);
        	list=mapDao.executeSql(sql.toString(), params);
         }else {
         	sql.append("SELECT * from  region"); 
     	   list= mapDao.executeSql(sql.toString());
		}
	   if(list!=null){
		
	     return list;	
	}
	     return null;
	}
	
	@Override
	public Region update(Region region) {
		regionDao.update(region);
		return region;
	}
	
	@Override
	public int delete(Region region) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Region t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (region.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", region.getHouseIeee());
		}
		if (region.getDeviceType() != null) {
			hql.append("and t.deviceType = :deviceType ");
			params.put("deviceType", region.getDeviceType());
		}
		if (region.getIeee() != null) {
			hql.append("and t.ieee = :ieee ");
			params.put("ieee", region.getIeee());
		}
		if (region.getEp() != null) {
			hql.append("and t.ep = :ep ");
			params.put("ep", region.getEp());
		}*/
		return regionDao.executeHql(hql.toString(), params);
	}

	private void changeModel(List<Region> l, List<Region> nl) {
		if (l != null && l.size() > 0) {
			for (Region t : l) {
				Region u = new Region();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Region region, String hql) {
		/*if (region.getSort() != null) {
			hql += " order by " + region.getSort() + " " + region.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Region region, String hql, Map<String, Object> params) {
		/*if (region.getName() != null && !region.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + region.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Region t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		regionDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}

	@Override
	public List<Map> findRegionList(Region region) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from region where 1=1");
		Map<String,Object> params = new HashMap<String,Object>();
		if(region!=null){
			if(region.getId()>0){
				sql.append(" and id = :id");
				params.put("id", region.getId());
			}
			if(StringUtils.isNotBlank(region.getRegionCode())){
				sql.append(" and regionCode = :regionCode");
				params.put("regionCode", region.getRegionCode());
			}
			if(StringUtils.isNotBlank(region.getRegionName())){
				sql.append(" and regionName = :regionName");
				params.put("regionName", region.getRegionName());
			}
			if(StringUtils.isNotBlank(region.getDescription())){
				sql.append(" and description = :description");
				params.put("description", region.getDescription());
			}
		}
		return mapDao.executeSql(sql.toString(), params);
	}
}
