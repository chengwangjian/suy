package sy.service.impl;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Advertisement;
import sy.service.AdvertisementServiceI;
@Service("AdvertisementService")
public class AdvertisementServiceImpl implements AdvertisementServiceI {
	private BaseDaoI<Advertisement> AdvertisementoDao;
	private BaseDaoI<Map> mapDao;
	public BaseDaoI<Advertisement> getAdvertisementoDao() {
		return AdvertisementoDao;
	}
	@Autowired
	public void setAdvertisementoDao(BaseDaoI<Advertisement> advertisementoDao) {
		AdvertisementoDao = advertisementoDao;
	}
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	/*APP查询广告*/
	public List<Advertisement> find(Advertisement advertisement) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Advertisement a where a.houseIeee = :houseIeee and a.readstate=0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", advertisement.getHouseIeee());
//		params.put("username",  advertisement.getUsername());
		List<Advertisement> t = AdvertisementoDao.find(hql.toString(), params);
		return t;
	}
	/*云端查询广告*/
	@Override
	public List<Advertisement> find1(String startRow, String pageSize,String orderBy, Advertisement advertisement) {
		StringBuffer hql = new StringBuffer();
		if (advertisement.getHouseIeee() == null || advertisement.getHouseIeee().equals("")) {
			hql.append("select b.username,b.adurl,b.readstate,a.houseIeee from house a left join advertisement b on a.houseIeee =b.houseIeee ");
			hql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
			Map<String, Object> params = new HashMap<String, Object>();
			List<Advertisement> t = AdvertisementoDao.executeSql(hql.toString());
			return t;
		} else {
			hql.append("select b.username,b.adurl,b.readstate,a.houseIeee from house a left join advertisement b  on a.houseIeee =b.houseIeee where a.houseIeee like :houseIeee");
			hql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("houseIeee", "%"+advertisement.getHouseIeee() + "%");
			List<Advertisement> t = AdvertisementoDao.executeSql(hql.toString(), params);
			return t;
		}
	}
	/*云端手动新增修改广告*/
	@Override
	public int update(Advertisement advertisement) {
		Map<String, Object> params = new HashMap<String, Object>();
//		String[] houseIeeeArr = Houseservice.getHouseIeee().split(","); 
		
		String[] houseIeeeArr = advertisement.getHouseIeee().split(",");
		int a=houseIeeeArr.length-1;
		for(int i=0;i<=a;i++){
			String sql1 = "update advertisement set houseIeee =:houseIeee where houseIEEE = :houseIeee";
			params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
			int t = AdvertisementoDao.executeSql2(sql1, params);
			if(t==0){
				String sql2="insert into advertisement(houseIeee,readstate,username,adurl) value(:houseIeee,:readstate,:username,:adurl)";
				params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
				params.put("username",  advertisement.getUsername());
				params.put("adurl",  advertisement.getAdurl());
				params.put("readstate",  advertisement.getReadstate());
				int t1 = AdvertisementoDao.executeSql2(sql2,params);
			}else{
				String sql = "update advertisement  set readstate = :readstate,username =:username,adurl=:adurl where 1=1 and houseIeee = :houseIeee";
				params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
				params.put("username",  advertisement.getUsername());
				params.put("adurl",  advertisement.getAdurl());
				params.put("readstate",  advertisement.getReadstate());
				int t2= AdvertisementoDao.executeSql2(sql, params);
			}
			params.clear();
		}
		return 1;
		
	}
	
	@Override
	/*APP修改广告读取状态*/
	public int appupdatereadstate(Advertisement advertisement) {
		Map<String, Object> params = new HashMap<String, Object>();
			String sql1 = "update advertisement set houseIeee =:houseIeee where houseIeee = :houseIeee";
			params.put("houseIeee", advertisement.getHouseIeee());
			int t = AdvertisementoDao.executeSql2(sql1, params);
			if(t==0){
				String sql2="insert into advertisement(houseIeee,username) value(:houseIeee,:username)";
				params.put("houseIeee",advertisement.getHouseIeee() );
				params.put("username",  advertisement.getUsername());
				int t1 = AdvertisementoDao.executeSql2(sql2,params);
			}else{
				String sql = "update advertisement  set readstate = 1,lasttime = :lasttime where houseIeee = :houseIeee";
				params.put("houseIeee",advertisement.getHouseIeee());
				params.put("lasttime", advertisement.getLasttime());
//				params.put("username",  advertisement.getUsername());
				int t2= AdvertisementoDao.executeSql2(sql, params);
			}
			params.clear();
			return 1;
		}
		
		
	
	@Override
	public int delete(Advertisement advertisement) {
		return 0;
	}
	@Override
	public Advertisement add(Advertisement advertisement) {
		return null;
	}
	@Override
	public void addbatchapp(List<Advertisement> list) throws Exception {
		
	}


	@Override
	public int getCount(Map<String, Object> house){
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuilder hql = new StringBuilder("Select Count(*) as aCount from house h where h.regionCode != 'none' ");
//		StringBuilder hql = new StringBuilder("Select Count(*) as aCount from House h");
		if(house.get("houseIeee")!=null){//搜索
			String sText = (String) house.get("houseIeee");
			if(StringUtils.isNotBlank(sText)){//搜索
				hql.append("  and (h.houseIeee like :sText) ");	
//				hql.append("or h.enableFlag like :sText ");		
//				hql.append("or h.clientCode like :sText ");		
//				hql.append("or h.regionCode like :sText ");		
//				hql.append("or h.name like :sText) ");	
				params.put("sText", sText);
			}
				
		}
		List<Map> cList=mapDao.executeSql(hql.toString(), params);
		return ((BigInteger)(cList.get(0).get("aCount"))).intValue();
	}

	
}
