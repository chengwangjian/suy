package sy.service.impl;

import java.util.Date;
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
import sy.model.Energyreferencelibrary;
import sy.model.Priceparamreferencelibrary;
import sy.service.EnergyreferencelibraryServiceI;
import zbEnergy.model.Energy;
import zbEnergy.model.Houseparam;

@Service("energyreferencelibraryService")
public class EnergyreferencelibraryServiceImpl implements EnergyreferencelibraryServiceI {

	private static final Logger logger = Logger.getLogger(EnergyreferencelibraryServiceImpl.class);

	private BaseDaoI<Energyreferencelibrary> energyreferencelibraryDao;
	private BaseDaoI<Map> energyreferencelibraryDao2;
	private BaseDaoI<Energy> energyDao;
	private BaseDaoI<Priceparamreferencelibrary> priceparamDao;

	public BaseDaoI<Priceparamreferencelibrary> getPriceparamDao() {
		return priceparamDao;
	}
	@Autowired
	public void setPriceparamDao(BaseDaoI<Priceparamreferencelibrary> priceparamDao) {
		this.priceparamDao = priceparamDao;
	}

	public BaseDaoI<Energyreferencelibrary> getEnergyreferencelibraryDao() {
		return energyreferencelibraryDao;
	}

	@Autowired
	public void setEnergyreferencelibraryDao(BaseDaoI<Energyreferencelibrary> energyreferencelibraryDao) {
		this.energyreferencelibraryDao = energyreferencelibraryDao;
	}
	
	public BaseDaoI<Map> getEnergyreferencelibraryDao2() {
		return energyreferencelibraryDao2;
	}

	@Autowired
	public void setEnergyreferencelibraryDao2(BaseDaoI<Map> energyreferencelibraryDao2) {
		this.energyreferencelibraryDao2 = energyreferencelibraryDao2;
	}
	
	public BaseDaoI<Energy> getEnergyDao() {
		return energyDao;
	}

	@Autowired
	public void setEnergyDao(BaseDaoI<Energy> energyDao) {
		this.energyDao = energyDao;
	}

	@Override
	public Energyreferencelibrary save(Energyreferencelibrary energyreferencelibrary) {
		energyreferencelibraryDao.save(energyreferencelibrary);
		return energyreferencelibrary;
	}

	@Override
	public Energyreferencelibrary get(Energyreferencelibrary energyreferencelibrary) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
		/*params.put("deviceType", energyreferencelibrary.getDeviceType());
		params.put("ieee", energyreferencelibrary.getIeee());
		params.put("ep", energyreferencelibrary.getEp());*/
		Energyreferencelibrary t = energyreferencelibraryDao.get("from Energyreferencelibrary t where t.houseIeee = :houseIeee " +
				"and t.deviceType = :deviceType and t.ieee = :ieee and t.ep = :ep", params);
		if (t != null) {
			return t;
		}
		return null;		
	}
	
	@Override
	public Energyreferencelibrary getById(long id) {
		Energyreferencelibrary t = energyreferencelibraryDao.get(Energyreferencelibrary.class, id);
		if (t != null) {
			return t;
		}
		return null;		
	}
	
	@Override
	public Energyreferencelibrary getExisit(Energyreferencelibrary energyreferencelibrary) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
		/*params.put("ieee", energyreferencelibrary.getIeee());
		params.put("ep", energyreferencelibrary.getEp());*/
		Energyreferencelibrary t = energyreferencelibraryDao.get("from Energyreferencelibrary t where t.houseIeee = :houseIeee " +
				"and t.deviceType in ('1','2','3','4') and t.ieee = :ieee and t.ep = :ep", params);
		if (t != null) {
			return t;
		}			
		return null;		
	}
	
	@Override
	public List<Map> findList(Map map) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT e.id,g.`name`,e.houseIEEE as houseIeee,e.priceType,e.DSTFlag as dstflag,e.DSTbeginTime as dstbeginTime,e.DSTendTime as dstendTime,e.regionCode,e.syncFlag,e.enabledFlag from energyreferencelibrary e INNER JOIN energyreferencelibrarylang g on e.id=g.energyId WHERE 1=1 ");
		if (StringUtils.isNotBlank((String) map.get("language"))) {
		sql.append("and g.languageIdentity=:type ");
			params.put("type",map.get("language"));
		}else {
			sql.append("and g.languageIdentity=:type ");
			params.put("type","zh-cn");	
		}
		if (StringUtils.isNotBlank((String) map.get("priceType"))) {
			sql.append("and e.priceType = :priceType ");
			params.put("priceType", map.get("priceType"));
		}
		if (StringUtils.isNotBlank((String)map.get("regionCode"))) {
			sql.append("and e.regionCode = :regionCode ");
			params.put("regionCode", map.get("regionCode"));
		}
		List<Map> list=energyreferencelibraryDao2.executeSql(sql.toString(), params);

		return list;
	}
	
	@Override
	public List<Map> findListFieldTime(Energyreferencelibrary energyreferencelibrary) {
		StringBuffer hql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (energyreferencelibrary.getPriceType().equals("1") && energyreferencelibrary.getDstflag() == true) {
			params.put("houseIeee", energyreferencelibrary.getHouseIeee());
			Energy t = energyDao.get("from Energy t where t.houseIeee = :houseIeee ", params);
			params2.put("energy", t);
			hql.append("select distinct new map(p.energyFieldId as energyFieldId,p.name as name,e.startField as startField,e.endField as endField," +
					"p.priceDst as price) from Priceparam p,Energyfield e " +
					"where p.houseIeee = e.houseIeee and p.energyFieldId = e.energyFieldId ");
			List<Map> map = energyreferencelibraryDao2.find(hql.toString(), params);
			params2.put("priceparam", map);
		}
		if (energyreferencelibrary.getPriceType().equals("1") && energyreferencelibrary.getDstflag() == false) {
			params.put("houseIeee", energyreferencelibrary.getHouseIeee());
			Energy t = energyDao.get("from Energy t where t.houseIeee = :houseIeee ", params);
			params2.put("energy", t);
			hql.append("select distinct new map(p.energyFieldId as energyFieldId,p.name as name,e.startField as startField,e.endField as endField," +
					"p.price as price) from Priceparam p,Energyfield e " +
					"where p.houseIeee = e.houseIeee and p.energyFieldId = e.energyFieldId ");
			List<Map> map = energyreferencelibraryDao2.find(hql.toString(), params);
			params2.put("priceparam", map);
		}
		if (energyreferencelibrary.getPriceType().equals("2") && energyreferencelibrary.getDstflag() == true) {
			params.put("houseIeee", energyreferencelibrary.getHouseIeee());
			Energy t = energyDao.get("from Energy t where t.houseIeee = :houseIeee ", params);
			params2.put("energy", t);
			hql.append("select distinct new map(p.energyTimeId as energyTimeId,p.name as name,e.beginTime as beginTime,e.endTime as endTime," +
					"p.priceDst as price) from Priceparam p,Energytime e " +
					"where p.houseIeee = e.houseIeee and p.energyTimeId = e.energyTimeId ");
			List<Map> map = energyreferencelibraryDao2.find(hql.toString(), params);
			params2.put("priceparam", map);
		}
		if (energyreferencelibrary.getPriceType().equals("2") && energyreferencelibrary.getDstflag() == false) {
			params.put("houseIeee", energyreferencelibrary.getHouseIeee());
			Energy t = energyDao.get("from Energy t where t.houseIeee = :houseIeee ", params);
			params2.put("energy", t);
			hql.append("select distinct new map(p.energyTimeId as energyTimeId,p.name as name,e.beginTime as beginTime,e.endTime as endTime," +
					"p.price as price) from Priceparam p,Energytime e " +
					"where p.houseIeee = e.houseIeee and p.energyTimeId = e.energyTimeId ");
			List<Map> map = energyreferencelibraryDao2.find(hql.toString(), params);
			params2.put("priceparam", map);
		}
		
		if (energyreferencelibrary.getPriceType() != null) {
			hql.append("and t.priceType = :priceType ");
			params.put("priceType", energyreferencelibrary.getPriceType());
		}
		if (energyreferencelibrary.getRegionCode() != null) {
			hql.append("and t.regionCode = :regionCode ");
			params.put("regionCode", energyreferencelibrary.getRegionCode());
		}
		List<Map> t = energyreferencelibraryDao2.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Map findListFieldTimeLib(Map map) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		StringBuffer sql4 = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		String language="";
		if(StringUtils.isBlank((String) map.get("language"))){
			language="zh-cn";
		}else {
			language=(String) map.get("language");
		}
		params.put("type",language);
		params.put("id", map.get("id"));
		sql.append("SELECT e.id,g.name,e.houseIEEE as houseIeee,e.priceType,e.DSTFlag as dstflag,e.DSTbeginTime as dstbeginTime,e.DSTendTime as dstendTime,e.regionCode,e.syncFlag,e.enabledFlag from energyreferencelibrary e INNER JOIN energyreferencelibrarylang g on e.id=g.energyId WHERE 1=1 and g.languageIdentity=:type and e.id=:id");
		List<Map> energyList=this.energyreferencelibraryDao2.executeSql(sql.toString(), params);
		sql2.append("SELECT e.id,e.energyId,e.energyFieldId,g.name,e.startField,e.endField from energyfieldreferencelibrary e INNER JOIN energyfieldreferencelibrarylang g on e.id=g.energyfieldId WHERE g.languageIdentity=:type and e.energyId=:id");
		List<Map> fieldList=this.energyreferencelibraryDao2.executeSql(sql2.toString(), params);
		sql3.append("SELECT e.id,e.energyId,e.energyTimeId,g.`name`,e.beginTime,e.endTime from energytimereferencelibrary e INNER JOIN energytimereferencelibrarylang g on e.id=g.energytimerId WHERE e.energyId=:id and g.languageIdentity=:type");
		List<Map> energyTimeList= this.energyreferencelibraryDao2.executeSql(sql3.toString(), params);
		sql4.append("SELECT p.id,p.energyId,p.energyFieldId,p.energyTimeId,g.`name`,p.price,p.priceDST as priceDst from priceparamreferencelibrary p INNER JOIN priceparamreferencelibrarylang g on p.id=g.priceparamId WHERE g.languageIdentity=:type and p.energyId=:id");
		List<Map> priceList=this.energyreferencelibraryDao2.executeSql(sql4.toString(), params);
		Energyreferencelibrary energyreferencelibrary=this.energyreferencelibraryDao.get("from Energyreferencelibrary e where e.id="+map.get("id"));
		String hql = "from Priceparamreferencelibrary t where t.energyId="+map.get("id");
		 if(energyreferencelibrary.getDstflag()){
				Date date=new Date();
				if(energyreferencelibrary.getDstendTime().getTime()>date.getTime()&&date.getTime()>energyreferencelibrary.getDstbeginTime().getTime()){
					for(int i=0;i<priceList.size();i++){
					double price=(double) priceList.get(i).get("price");
					double priceDst=(double) priceList.get(i).get("priceDst");
				    priceList.get(i).remove("price");
				    priceList.get(i).remove("priceDst");
				    priceList.get(i).put("price",priceDst);	
				    priceList.get(i).put("priceDst",price);

					}
					}
				}
		
		
		params2.put("energy",energyList.get(0));
		params2.put("house", new Houseparam());
		params2.put("energyfield", fieldList);
		params2.put("energytime", energyTimeList);
		params2.put("price", priceList);
		return params2;
	}
	
	@Override
	public Energyreferencelibrary update(Energyreferencelibrary energyreferencelibrary) {
		energyreferencelibraryDao.update(energyreferencelibrary);
		return energyreferencelibrary;
	}
	
	@Override
	public int delete(Map map) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee",map.get("houseIeee"));
		energyreferencelibraryDao.executeHql("delete Energy t where t.houseIeee = :houseIeee ", params);
		energyreferencelibraryDao.executeHql("delete Energyfield t where t.houseIeee = :houseIeee ", params);
		energyreferencelibraryDao.executeHql("delete Energytime t where t.houseIeee = :houseIeee ", params);
		energyreferencelibraryDao.executeHql("delete Priceparam t where t.houseIeee = :houseIeee ", params);		
		return 1;
	}
	
	@Override
	public int saveSql(Energyreferencelibrary energyreferencelibrary,String language) {
		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		String sql2="SELECT g.`name` from energyreferencelibrarylang g WHERE g.energyId=:id and languageIdentity=:language";
		params2.put("id", energyreferencelibrary.getId());
		if("".equals(language)){
			language="zh-cn";
		}
		params2.put("language", language);
		List<Map> name=energyreferencelibraryDao2.executeSql(sql2, params2);
		String sql = "insert into energy(name,houseIeee,priceType,dstflag,dstbeginTime,dstendTime,regionCode,energyRefLibId,syncFlag,enabledFlag) select " +
				" :name,:houseIeee,priceType,dstflag,dstbeginTime,dstendTime,regionCode,0,1,1 from energyreferencelibrary where id = :id";
	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
		params.put("id", energyreferencelibrary.getId());
		params.put("name",name.get(0).get("name"));
		energyreferencelibraryDao.executeSql2(sql, params);
		params = new HashMap<String, Object>();
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
		Energy t = energyDao.get("from Energy t where t.houseIeee = :houseIeee ", params);
		params = new HashMap<String, Object>();
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
		params.put("id", energyreferencelibrary.getId());
		params.put("language", language);
		params.put("energyId",t.getId());
		sql = "insert into energyfield(houseIeee,energyId,energyFieldId,name,startField,endField) select " +
				" :houseIeee,:energyId,e.energyFieldId,g.name,e.startField,e.endField from energyfieldreferencelibrary e INNER JOIN energyfieldreferencelibrarylang  g on e.id=g.energyfieldId WHERE e.energyId = :id and g.languageIdentity=:language";  
		energyreferencelibraryDao.executeSql2(sql, params);	
		sql = "insert into energytime(houseIeee,energyId,energyTimeId,name,beginTime,endTime) select " +
				" :houseIeee,:energyId,e.energyTimeId,g.name,e.beginTime,e.endTime from energytimereferencelibrary e INNER JOIN energytimereferencelibrarylang g on e.id=g.energytimerId where e.energyId = :id and g.languageIdentity=:language";
		energyreferencelibraryDao.executeSql2(sql, params);
		sql = "insert into priceparam(houseIeee,energyId,energyFieldId,energyTimeId,name,price,priceDst) select " +
				" :houseIeee,:energyId,e.energyFieldId,e.energyTimeId,g.name,e.price,e.priceDst from priceparamreferencelibrary e INNER JOIN priceparamreferencelibrarylang g on e.id=g.priceparamId where e.energyId = :id and g.languageIdentity=:language";
		energyreferencelibraryDao.executeSql2(sql, params);
		return 1;
	}
	
	@Override
	public int saveSqlLib(Energyreferencelibrary energyreferencelibrary) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
		energyreferencelibraryDao.executeHql("delete Energyreferencelibrary t where t.houseIeee = :houseIeee ", params);
		energyreferencelibraryDao.executeHql("delete Energyfieldreferencelibrary t where t.houseIeee = :houseIeee ", params);
		energyreferencelibraryDao.executeHql("delete Energytimereferencelibrary t where t.houseIeee = :houseIeee ", params);
		energyreferencelibraryDao.executeHql("delete Priceparamreferencelibrary t where t.houseIeee = :houseIeee ", params);	
		String sql = "insert into energyreferencelibrary(name,houseIeee,priceType,dstflag,dstbeginTime,dstendTime,regionCode,syncFlag,enabledFlag) select " +
				" name,houseIeee,priceType,dstflag,dstbeginTime,dstendTime,regionCode,syncFlag,enabledFlag from energy where houseIeee = :houseIeee";
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
//		params.put("name", energyreferencelibrary.getHouseIeee() + "配置信息");
		energyreferencelibraryDao.executeSql2(sql, params);
		
		params = new HashMap<String, Object>();
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
		Energyreferencelibrary t = energyreferencelibraryDao.get("from Energyreferencelibrary t where t.houseIeee = :houseIeee ", params);
		
		sql = "insert into energyfieldreferencelibrary(houseIeee,energyId,energyFieldId,name,startField,endField) select " +
				" houseIeee,:energyId,energyFieldId,name,startField,endField from energyfield where houseIeee = :houseIeee";
		params = new HashMap<String, Object>();
		params.put("houseIeee", energyreferencelibrary.getHouseIeee());
		params.put("energyId", t.getId());
		energyreferencelibraryDao.executeSql2(sql, params);
		sql = "insert into energytimereferencelibrary(houseIeee,energyId,energyTimeId,name,beginTime,endTime) select " +
				" houseIeee,:energyId,energyTimeId,name,beginTime,endTime from energytime where houseIeee = :houseIeee";
		energyreferencelibraryDao.executeSql2(sql, params);
		sql = "insert into priceparamreferencelibrary(houseIeee,energyId,energyFieldId,energyTimeId,name,price,priceDst) select " +
				" houseIeee,:energyId,energyFieldId,energyTimeId,name,price,priceDst from priceparam where houseIeee = :houseIeee";
		energyreferencelibraryDao.executeSql2(sql, params);
		return 1;
	}

	private void changeModel(List<Energyreferencelibrary> l, List<Energyreferencelibrary> nl) {
		if (l != null && l.size() > 0) {
			for (Energyreferencelibrary t : l) {
				Energyreferencelibrary u = new Energyreferencelibrary();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Energyreferencelibrary energyreferencelibrary, String hql) {
		/*if (energyreferencelibrary.getSort() != null) {
			hql += " order by " + energyreferencelibrary.getSort() + " " + energyreferencelibrary.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Energyreferencelibrary energyreferencelibrary, String hql, Map<String, Object> params) {
		/*if (energyreferencelibrary.getName() != null && !energyreferencelibrary.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + energyreferencelibrary.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Energyreferencelibrary t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		energyreferencelibraryDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
}
