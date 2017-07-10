package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.FarmUser;
import sy.model.FarmUserHousePhone;

public interface FarmSmsServiceI
{
	/**
	 * 为用户添加接受短信的手机号
	 * @param userHousePhone 绑定信息对象
	 * @return
	 */
	public int addPhone(FarmUserHousePhone userHousePhone);
	
	/**
	 * 删除用户绑定的手机号
	 * @param userHousePhone 绑定信息对象
	 * @return
	 */
	public int deletePhone(FarmUserHousePhone userHousePhone);

	
	public List<FarmUser> getPhoneList(String paramString);
	
	/**
	 * 获取告警类型短信启/禁用情况
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map> getSmsList(FarmUserHousePhone userHousePhone);

	/**
	 * 设置启/禁用不同类型短信告警
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int setSmsFlag(FarmUserHousePhone userHousePhone) throws Exception;

	public List<Map> getsendPhone(String houseIeee);

	public List<Map> getsmsflag(String paramString) throws Exception;
	/**
	 * 根据服务器真实ip获取其短信推送方式
	 * @param ipAddress 服务器ip
	 * @return
	 */
	public Map getSMSType(String ipAddress);
}