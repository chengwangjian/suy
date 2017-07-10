package sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.service.SMSServiceI;

@Service
public class SMSServiceImpl implements SMSServiceI {
	private BaseDaoI<Map> mapDao;
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	private static final Logger logger = Logger.getLogger(SMSServiceImpl.class);
	@Override
	public int addSMSandWE(String houseIeee, String phoneNO, String warnEmail ,String behavior) {
		if(behavior.equals("SMS")){
			String sql = "select 1 from phoneNO where houseIeee=:houseIeee and phoneNO =:phoneNO";
			Map<String ,Object> param = new HashMap<String, Object>();
			param.put("houseIeee", houseIeee);
			param.put("phoneNO", phoneNO);
			List<Map> list = mapDao.executeSql(sql, param);
			if(list.size()==0){
				String addsql = "insert into phoneNO(houseIEEE,phoneNO) value(:houseIeee,:phoneNO)";
				int i = mapDao.executeSql2(addsql, param);
				return i ;
			}else {
				return -1;//已存在
			}
		}
		else if(behavior.equals("warnEmail")){
			String sql = "select 1 from warnEmail where houseIeee=:houseIeee and warnEmail =:warnEmail";
			Map<String ,Object> param = new HashMap<String, Object>();
			param.put("houseIeee", houseIeee);
			param.put("warnEmail", warnEmail);
			List<Map> list = mapDao.executeSql(sql, param);
			if(list.size()==0){
				String addsql = "insert into warnEmail(houseIEEE,warnEmail) value(:houseIeee,:warnEmail)";
				int i = mapDao.executeSql2(addsql, param);
				return i ;
			}else {
				return -1;//已存在
			}
		}
		return -2;//不是SMS也不是Email
		
	}
	@Override
	public List<Map> findSMS(String houseIeee) {
		String sql = "select phoneNO from phoneno where houseIEEE=:houseIeee";
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("houseIeee", houseIeee);
		List<Map> list = mapDao.executeSql(sql, params);
		return list;
	}
	@Override
	public List<Map> findEmail(String houseIeee) {
		String sql = "select warnEmail from warnemail where houseIEEE=:houseIeee";
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("houseIeee", houseIeee);
		List<Map> list = mapDao.executeSql(sql, params);
		return list;
	}
	@Override
	public int deleteSW(Map<String, Object> params,String behavior) {
		if(behavior.equals("SMS")){//删除短信
			String sql = "delete from phoneno where houseIEEE=:houseIeee and phoneNO=:phoneNO";
			int i =mapDao.executeSql2(sql, params);
			return i;
		}else if(behavior.equals("warnEmail")){//删除邮箱
			String sql = "delete from warnemail where houseIEEE=:houseIeee and warnEmail=:warnEmail";
			int i =mapDao.executeSql2(sql, params);
			return i;
		}
		else {
			return -2;//不是SMS,也不是warnEmain
		}
	}
	
	public int sendSMS(List<Map> hlist,List<Map> plist, String SMSmsg) {
		
		if(plist==null){//报警历史推送过来无手机号码的情况
			String sql= "select DISTINCT(phoneNO) where houseIEEE in(";
			for (int i=0;i<hlist.size();i++){
				sql+="'";
				sql+=plist.get(i).get("houseIeee");
				sql+="',";
			}
			sql.substring(0, sql.length()-1);
			List<Map> phoneno = mapDao.executeSql(sql);
			//-----------------------此处调用发送短信接口
		}
		else {//批量发送预警消息、批量
			//-----------------------此处调用发送短信接口
		}
		return 0;
	}
}
