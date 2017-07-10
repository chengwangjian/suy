package sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.House;
import sy.model.HouseWeixin;
import sy.service.HouseWeixinServiceI;

/**
 * 家、微信绑定数据操作类
 * @author zhanghc
 * @since 2016-06-22
 */
@Service("houseWeixinService")
public class HouseWeixinServiceImpl implements HouseWeixinServiceI {
	
	private BaseDaoI<HouseWeixin> houseWeixinDao;
	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<HouseWeixin> getHouseWeixinDao() {
		return houseWeixinDao;
	}
	@Autowired
	public void setHouseWeixinDao(BaseDaoI<HouseWeixin> houseWeixinDao) {
		this.houseWeixinDao = houseWeixinDao;
	}
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public List<HouseWeixin> getHouseWeixinAccounts(String houseIeee) {
		String hql = "from HouseWeixin where houseIeee = :houseIeee";
		Map<String,Object> params = new HashMap<>();
		params.put("houseIeee", houseIeee);
		List<HouseWeixin> weixinList = houseWeixinDao.find(hql, params);
		if(weixinList!=null&&!weixinList.isEmpty())
			return weixinList;
		return null;
	}
	@Override
	public void updateHouseName(House house) {
		String sql = "update house_weixin set user_name=:houseName where house_ieee=:houseIeee";
		Map<String,Object> params = new HashMap<>();
		params.put("houseName", house.getName());
		params.put("houseIeee", house.getHouseIeee());
		mapDao.executeSql2(sql, params);
	}
	

	

}
