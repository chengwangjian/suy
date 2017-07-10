package sy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.CacheWarnMessage;
import sy.service.CacheWarnMessageServiceI;

@Service("cacheWarnMessageService")
public class CacheWarnMessageServiceImpl implements CacheWarnMessageServiceI {

	private BaseDaoI<CacheWarnMessage> messageDao;
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	public BaseDaoI<CacheWarnMessage> getMessageDao() {
		return messageDao;
	}
	@Autowired
	public void setMessageDao(BaseDaoI<CacheWarnMessage> messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void saveMessage(CacheWarnMessage message) {
		messageDao.save(message);
	}

	@Override
	public void deleteMessage() {

	}

	@Override
	public List<Map> getTimeInfo(Map<String, Object> map) {
		//获取刚好能够满屏的告警日期列表
		String sql="select DISTINCT left(push_time,10) as push_time from cache_warn_message where house_ieee=:weixinopenid ORDER BY push_time DESC limit "+map.get("count");
		map.remove("count");
		List<Map> list=this.mapDao.executeSql(sql,map);
		if(list.isEmpty()){
			return null;	
		}
		return list;
	}

	@Override
	public List<Map> getTimeInfo2(Map<String, Object> map) {
		//获取十条告警日期列表
		String sql="select DISTINCT left(push_time,10) as push_time from cache_warn_message WHERE house_ieee=:weixinopenid and push_time<:time ORDER BY push_time DESC LIMIT 10";
		List<Map> list=this.mapDao.executeSql(sql, map);
		if(list.isEmpty()){
			return null;	
		}
		return list;
	}
	
	@Override
	public List<Map> getDeviceInfoDetail(Map<String,Object> map) throws Exception{
		//获取某一天中的前20条告警消息列表
		map.put("pushtime", map.get("pushtime")+"%");
		String sql="SELECT * FROM cache_warn_message WHERE house_ieee=:weixinopenid and push_time like :pushtime ORDER BY push_time DESC LIMIT 20";
		List<Map> list=this.mapDao.executeSql(sql, map);
		if(list==null||list.isEmpty()){
			return null;	
		}else{
			return list;
		}
	}
	
	@Override
	public List<Map> getDeviceInfoDetail2(Map<String,Object> map) throws Exception{
		//获取某一天中的前20条告警消息列表
		map.put("pushtime", map.get("pushtime")+"%");
		String sql="SELECT * FROM cache_warn_message WHERE house_ieee=:weixinopenid push_time like :pushtime AND push_time<:lastpushtime ORDER BY push_time DESC LIMIT 20";
		List<Map> list=this.mapDao.executeSql(sql, map);
		if(list==null||list.isEmpty()){
			return null;	
		}else{
			return list;
		}
	}
}
