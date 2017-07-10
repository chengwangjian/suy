package sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.FarmUser;
import sy.model.FarmUserHousePhone;
import sy.service.FarmSmsServiceI;

@Service("farmSmsService")
@SuppressWarnings("all")
public class FarmSmsServiceImpl implements FarmSmsServiceI
{
	private static final Logger logger = Logger.getLogger(FarmSmsServiceImpl.class);
	private BaseDaoI<FarmUser> farmuserDao;
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<FarmUser> getFarmuserDao()
	{
		return this.farmuserDao;
	}

	@Autowired
	public void setFarmuserDao(BaseDaoI<FarmUser> farmuserDao) {
		this.farmuserDao = farmuserDao;
	}

	public BaseDaoI<Map> getMapDao() {
		return this.mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public int addPhone(FarmUserHousePhone userHousePhone)
	{
		//用户名、网关ieee地址、手机号不能为空
		String username = userHousePhone.getUsername();
		String houseIeee = userHousePhone.getHouseIeee();
		String phone = userHousePhone.getPhone();
		if(StringUtils.isBlank(username)||StringUtils.isBlank(houseIeee)||StringUtils.isBlank(phone)){
			return -1;
		}
		//验证用户是否关联了该区域
		if(!validUserArea(username,houseIeee))
			return -1;
		Map<String,Object> params = new HashMap<>();
		params.put("username", username);
		params.put("houseIeee", houseIeee);
		params.put("phone", phone);
		//根据唯一索引判断插入数据或更新数据
		StringBuilder sqlBld = new StringBuilder("INSERT INTO farmuserhousephone(user_name,house_ieee,phone) VALUES(:username,:houseIeee,:phone)");
		sqlBld.append(" ON DUPLICATE KEY UPDATE phone=:phone");
		return mapDao.executeSql2(sqlBld.toString(),params);
	}

	@Override
	public int deletePhone(FarmUserHousePhone userHousePhone)
	{
		//用户名、网关ieee地址不能为空
		String username = userHousePhone.getUsername();
		String houseIeee = userHousePhone.getHouseIeee();
		if(StringUtils.isBlank(username)||StringUtils.isBlank(houseIeee)){
			return -1;
		}
		Map<String,Object> params = new HashMap<>();
		params.put("username", username);
		params.put("houseIeee", houseIeee);
		String sql = "DELETE FROM farmuserhousephone WHERE user_name=:username AND house_ieee=:houseIeee";
		return mapDao.executeSql2(sql, params);
	}

	@Override
	public int setSmsFlag(FarmUserHousePhone userHousePhone) throws Exception
	{
		//用户名、网关ieee地址不能为空
		String username = userHousePhone.getUsername();
		String houseIeee = userHousePhone.getHouseIeee();
		String smsFlag = userHousePhone.getSmsFlag();
		if(StringUtils.isBlank(username)||StringUtils.isBlank(houseIeee)||StringUtils.isBlank(smsFlag)){
			return -1;
		}
		if(!smsFlag.contains(",")){
			//上传smsFlag数据不包含“,”时,统一加入“,”以区分告警类型启用情况
			smsFlag = gapInsertion(smsFlag,",");
		}
		Map<String,Object> params = new HashMap<>();
		params.put("username", username);
		params.put("houseIeee", houseIeee);
		params.put("smsFlag", smsFlag);
		StringBuilder sqlBld = new StringBuilder("UPDATE farmuserhousephone SET sms_flag=:smsFlag WHERE user_name=:username AND house_ieee=:houseIeee");
		return mapDao.executeSql2(sqlBld.toString(),params);
	}

	@Override
	public List<Map> getSmsList(FarmUserHousePhone userHousePhone)
	{
		//用户名、网关ieee地址不能为空
		String username = userHousePhone.getUsername();
		String houseIeee = userHousePhone.getHouseIeee();
		if(StringUtils.isBlank(username)||StringUtils.isBlank(houseIeee)){
			return null;
		}
		Map<String,Object> params = new HashMap<>();
		params.put("username", username);
		params.put("houseIeee", houseIeee);
		String sql = "SELECT user_name username,house_ieee houseIeee,phone,sms_flag smsFlag FROM farmuserhousephone WHERE user_name=:username AND house_ieee=:houseIeee";
		return mapDao.executeSql(sql, params);
	}
	
	public List<FarmUser> getPhoneList(String username)
	{
		Map<String,Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.user_name,t.phone,t.smsflag from farmuser").append(" t where 1=1");
		if (StringUtils.isNotBlank(username)) {
			sql.append(" and t.user_name =:user_name");
			map.put("user_name", username);
		}
		List<FarmUser> list = this.farmuserDao.executeSql(sql.toString(), map);
		if (list != null) {
			return list;
		}
		return null;
	}

	public List<Map> getsendPhone(String houseIeee)
	{
		if(StringUtils.isBlank(houseIeee))
			return null;
		Map<String,Object> params = new HashMap<>();
		params.put("houseIeee", houseIeee);
		String sql = "SELECT f.phone,f.sms_flag smsFlag FROM farmuserhousephone f INNER JOIN house h ON f.house_ieee=h.houseIEEE INNER JOIN farmuser u ON f.user_name=u.user_name WHERE house_ieee=:houseIeee";
		return mapDao.executeSql(sql, params);
	}

	public List<Map> getsmsflag(String houseieee) throws Exception
	{
		Map<String,Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.smsflag,t.phone,t.user_name from farmuser t inner join farmuserarea d on t.id = d.user_id").append(" where 1=1");
		if (StringUtils.isNotBlank(houseieee)) {
			sql.append(" and d.house_ieee =:house_ieee");
			map.put("house_ieee", houseieee);
		}
		List<Map> list = this.mapDao.executeSql(sql.toString(), map);
		if (list != null) {
			return list;
		}
		return null;
	}

	@Override
	public Map getSMSType(String ipAddress){
		String sql = "SELECT smstype,smsip FROM serverlib WHERE serverip = :serverip";
		Map<String,Object> params = new HashMap<>();
		params.put("serverip", ipAddress);

		List<Map> list = this.mapDao.executeSql(sql, params);
		if ((list == null) || (list.isEmpty())) {
			return null;
		}
		return (Map)list.get(0);
	}
	
	/**
	 * 将一个字符串每隔以为插入固定内容
	 * @param target 目标字符串
	 * @param regex 插入内容
	 * @return
	 */
	private String gapInsertion(String target,String regex) throws Exception{
		if(StringUtils.isBlank(target))
			throw new IllegalAccessException("目标字符串不能为空");
		if(StringUtils.isBlank(regex))
			throw new IllegalAccessException("插入字符不能为空");
		StringBuilder result = new StringBuilder();
		for(int i=0;i<target.length();i++){
			result.append(target.charAt(i));
			if(i!=target.length()-1){
				result.append(regex);
			}
		}
		return result.toString();
	}
	
	/**
	 * 验证用用户是否对区域有操作权限
	 * @param username 用户名（唯一）
	 * @param houseIeee 区域ieee
	 * @return
	 */
	private boolean validUserArea(String username,String houseIeee){
		String sql = "SELECT 1 FROM farmuser u INNER JOIN farmuserarea a ON u.id=a.user_id WHERE u.user_name=:username AND a.house_ieee=:houseIeee";
		Map<String,Object> params = new HashMap<>();
		params.put("username", username);
		params.put("houseIeee", houseIeee);
		List<Map> list = mapDao.executeSql(sql, params);
		if(list==null||list.isEmpty())
			return false;
		return true;
	}
}