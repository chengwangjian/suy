package sy.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.HouseWeixin;
import sy.service.DevicewarnhistoryHouseidYearServiceI;
import sy.service.WechatServiceI;

/**
 * 绑定微信、推送微信告警消息实现类
 * @author zhanghc
 * @since 2016-06-21
 */
@Service("wechatService")
public class WechatServiceImpl implements WechatServiceI {

	private final static Logger LOGGER = Logger.getLogger(WechatServiceImpl.class);

	private BaseDaoI<Map> mapDao;
	private BaseDaoI<HouseWeixin> houseWeixinDao;
	private DevicewarnhistoryHouseidYearServiceI devicewarnhistoryHouseidYearService;

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<HouseWeixin> getHouseWeixinDao() {
		return houseWeixinDao;
	}
	@Autowired
	public void setHouseWeixinDao(BaseDaoI<HouseWeixin> houseWeixinDao) {
		this.houseWeixinDao = houseWeixinDao;
	}
	
	public DevicewarnhistoryHouseidYearServiceI getDevicewarnhistoryHouseidYearService() {
		return devicewarnhistoryHouseidYearService;
	}
	@Autowired
	public void setDevicewarnhistoryHouseidYearService(
			DevicewarnhistoryHouseidYearServiceI devicewarnhistoryHouseidYearService) {
		this.devicewarnhistoryHouseidYearService = devicewarnhistoryHouseidYearService;
	}

	@Override
	public int bindUser(Map<String, Object> userMap) {
		int status = 1;
		HashMap<String,Object> params=new HashMap<String, Object>();
		String name="";
		//验证绑定时，用户输入的账号是否存在
		params.put("houseIeee", userMap.get("houseIeee"));
		String sql2="SELECT * FROM  house WHERE houseIEEE=:houseIeee";
		List<Map> wei=this.mapDao.executeSql(sql2, params);
		if(wei.isEmpty()){
			//家不存在
			status = -1;
		}else{
			//验证家是否已绑定
			params.put("account", userMap.get("openid"));
			String select="SELECT * FROM house_weixin WHERE house_ieee=:houseIeee and account=:account";
			List<Map> weixin= this.mapDao.executeSql(select, params);
			if(!weixin.isEmpty()){
				//该微信账号已与该家绑定
				status = 0;
			}else{
				try {
					String houseName = (String) wei.get(0).get("name");
					//解码微信账号名
					name=URLDecoder.decode((String) userMap.get("account_name"),"gbk");
					params.put("account_name", name);
					params.put("username", houseName);
					//将绑定信息存入绑定信息表中
					String sql="INSERT INTO house_weixin(account,house_ieee,account_name,user_name) VALUES(:account,:houseIeee,:account_name,:username)";
					status = this.mapDao.executeSql2(sql, params);
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("URLDecode Exception",e);
					status = 0;
				}
			}
		}
		return status;
	}

	@Override
	public String getweixinopenid(Map<String,Object> map) {
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("userName", map.get("userName"));
		String selectsql="select w.* from house_weixin w where w.account=:openid and house_ieee=:userName";
		List<Map> list=this.mapDao.executeSql(selectsql, params);
		if(list!=null&&!list.isEmpty())
			//返回微信账号的openid
			return (String) list.get(0).get("house_ieee");
		return "";
	}

	@Override
	public int disbind(Map<String,Object> map) {
		//删除绑定信息
		String sql="delete w from house_weixin w where w.account=:openid and house_ieee=:userName";
		return this.mapDao.executeSql2(sql, map);
	}

	@Override
	public List<Map> getUserInfo(Map<String,Object> map) {
		String sql="select id,account as openid,house_ieee as userName,account_name as accountName,user_name as nickName,2 as accountType"
				+" from house_weixin where account=:openid";
		List<Map> weixinList = mapDao.executeSql(sql, map);
		return 	weixinList;
	}

	@Override
	public List<Map> getTimeInfo(Map<String,Object> map) throws Exception{
		Calendar a=Calendar.getInstance();
		//根据家IEEE地址得到告警历史表表名
		String tableName = "devicewarnhistory_"+map.get("weixinopenid")+"_"+a.get(Calendar.YEAR);
		//获取刚好能够满屏的告警日期列表
		String sql="select DISTINCT left(warndatetime,10) as push_time from "+tableName+" ORDER BY warndatetime DESC limit "+map.get("count");
		List<Map> list=this.mapDao.executeSql(sql);
		if(list.isEmpty()){
			return null;	
		}
		return list;
	}

	@Override
	public List<Map> getTimeInfo2(Map<String,Object> map) throws Exception{
		Calendar a=Calendar.getInstance();
		//获取表名
		String tableName = "devicewarnhistory_"+map.get("weixinopenid")+"_"+a.get(Calendar.YEAR);
		//获取十条告警日期列表
		String sql="select DISTINCT left(warndatetime,10) as warndatetime from "+tableName+" WHERE push_time<:time ORDER BY warndatetime DESC LIMIT 10";
		map.remove("weixinopenid");
		List<Map> list=this.mapDao.executeSql(sql, map);
		if(list.isEmpty()){
			return null;	
		}
		return list;
	}
	
	@Override
	public List<Map> getDeviceInfoDetail(Map<String,Object> map) throws Exception{
		List<Map> warnInfoList = new ArrayList<>();
		Calendar a=Calendar.getInstance();
		//获取表名
		String tableName = "devicewarnhistory_"+map.get("weixinopenid")+"_"+a.get(Calendar.YEAR);
		//获取某一天中的前20条告警消息列表
		map.put("pushtime", map.get("pushtime")+"%");
		String sql="SELECT * FROM "+tableName+" WHERE warndatetime like :pushtime ORDER BY warndatetime DESC LIMIT 20";
		map.remove("weixinopenid");
		List<Map> list=this.mapDao.executeSql(sql, map);
		if(list==null||list.isEmpty()){
			return null;	
		}else{
			for(Map m:list){
				Map<String,Object> param = new HashMap<>();
				//设备IEEE地址
				Object deviceIeee = m.get("zone_ieee");
				//告警类型
				Object warnType = m.get("w_description");
				//告警时间
				Object pushTime = m.get("warndatetime");
				//组装告警消息
				String desctiption = "您家IEEE地址为："+deviceIeee+"的设备发生了"+warnType+"类型的告警，请重视！";
				param.put("description", desctiption);
				param.put("push_time", pushTime);
				warnInfoList.add(param);
			}
		}
		return warnInfoList;

	}
	@Override
	public List<Map> getDeviceInfoDetail2(Map<String,Object> map) throws Exception{
		List<Map> warnInfoList = new ArrayList<>();
		Calendar a=Calendar.getInstance();
		//获取表名
		String tableName = "devicewarnhistory_"+map.get("weixinopenid")+"_"+a.get(Calendar.YEAR);
		//获取某一天中的前20条告警消息列表
		map.put("pushtime", map.get("pushtime")+"%");
		String sql="SELECT * FROM "+tableName+" WHERE warndatetime like :pushtime AND warndatetime<:lastpushtime ORDER BY warndatetime DESC LIMIT 20";
		map.remove("weixinopenid");
		List<Map> list=this.mapDao.executeSql(sql, map);
		if(list==null||list.isEmpty()){
			return null;	
		}else{
			for(Map m:list){
				Map<String,Object> param = new HashMap<>();
				//设备IEEE地址
				Object deviceIeee = m.get("zone_ieee");
				//告警类型
				Object warnType = m.get("w_description");
				//告警时间
				Object pushTime = m.get("warndatetime");
				//组装告警消息
				String desctiption = "您家IEEE地址为："+deviceIeee+"的设备发生了"+warnType+"类型的告警，请重视！";
				param.put("decription", desctiption);
				param.put("push_time", pushTime);
				warnInfoList.add(param);
			}
		}
		return warnInfoList;
	}
}
