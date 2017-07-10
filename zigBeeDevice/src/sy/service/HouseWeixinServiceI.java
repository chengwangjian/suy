package sy.service;

import java.util.List;

import sy.model.House;
import sy.model.HouseWeixin;

/**
 * 家、微信绑定数据操作类
 * @author ZhangHC
 * @since 2016-06-22
 */
public interface HouseWeixinServiceI {
	/**
	 * 根据houseIeee获取绑定的微信账号列表
	 * @param houseIeee 家houseIeee
	 * @return
	 */
	public List<HouseWeixin> getHouseWeixinAccounts(String houseIeee);
	
	/**
	 * 修改奈伯思智慧家名称
	 * @param house
	 */
	public void updateHouseName(House house);
}
