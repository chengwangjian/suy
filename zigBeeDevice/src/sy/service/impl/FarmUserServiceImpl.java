package sy.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.FarmUser;
import sy.model.FarmUserRole;
import sy.model.RespParams;
import sy.service.FarmUserServiceI;
import sy.util.HouseieeeListener;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

import com.alibaba.fastjson.JSON;

@Service("farmUserService")
public class FarmUserServiceImpl implements FarmUserServiceI {

	private Logger logger = Logger.getLogger(FarmUserServiceImpl.class);

	private BaseDaoI<FarmUserRole> furDao;
	private BaseDaoI<FarmUser> fUserDao;
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<FarmUser> getfUserDao() {
		return fUserDao;
	}
	@Autowired
	public void setfUserDao(BaseDaoI<FarmUser> fUserDao) {
		this.fUserDao = fUserDao;
	}

	public BaseDaoI<FarmUserRole> getFurDao() {
		return furDao;
	}
	@Autowired
	public void setFurDao(BaseDaoI<FarmUserRole> furDao) {
		this.furDao = furDao;
	}

	@Override
	public FarmUser getFUser(String username){
		String hql = "from FarmUser u where u.username=:userName";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userName", username);
		return fUserDao.get(hql, params);
	}

	@Override
	public Map<String,Object> getFarmUser(String username) {
		if(StringUtils.isBlank("username"))
			return null;
		String sql = "select u.*,r.level_id as roleId from farmuser u left join farmuserrole ur on u.id=ur.user_id left join farmrole r on ur.role_id=r.id where u.user_name=:username";
		Map<String,Object> params = new HashMap<>();
		params.put("username", username);
		List<Map> list = mapDao.executeSql(sql, params);
		if(list!=null&&list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map> isExistence (String username) {
		if(StringUtils.isBlank(username)){
			return null;
		}
		String sql = "select f.user_name,f.parent_id from farmuser f where f.user_name=:username";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		List<Map> list = mapDao.executeSql(sql, params);
		if(list != null && list.size()==1) {
			return list;
		}
		return null;
	}
	

	@Override
	public int saveUser(String user_name,String username,String password,int userType, List<String> areaList, 
			List<Map> noticeNList, List<Map> noticeRMList) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> params = new HashMap<>();
		params.put("user_name", user_name);
		Map<String,Object> pUser = getFarmUser(user_name);
		int roleId = (int)pUser.get("roleId");

		if (pUser==null || roleId != 1 || userType == 1 || (user_name.equals(username) && userType != 1)) {
			return -1;//权限不足,不能添加或修改用户
		}
		
		String pass = Httpproxy.getKey(user_name);
		//密码解密
		String pwd = Httpproxy.urlCodec(password, pass);
		if(password.equals(pwd))
			return -2;//解密出错
		
		//判断新增的houseIEEE是否在管理员账户中
		String getSql = "select a.house_ieee,u.serverIp,u.id from farmuserarea a left join farmuser u on a.user_id=u.id where u.user_name=:username";
		params.clear();
		params.put("username", user_name);
		List<Map> aHList = mapDao.executeSql(getSql, params);
		String adminIp = aHList.get(0).get("serverIp").toString()==null?"":aHList.get(0).get("serverIp").toString();
		String adminId = aHList.get(0).get("id").toString()==null?"":aHList.get(0).get("id").toString();
		logger.info("获取到的管理员adminIp地址====++："+adminIp);
		if(aHList.isEmpty()) {
			return -4;
		}
		else {
			Iterator<String> aItor = areaList.iterator();
			int aHLength = aHList.size();
			while(aItor.hasNext()) {
				String aHIeee = aItor.next();
				boolean exist = false;
				for(int i = 0; i < aHLength; i++) {
					Map aHMap = aHList.get(i);
					String aHHIeee = (String) aHMap.get("house_ieee");
					if(aHIeee.equals(aHHIeee)) {
						exist = true;
						break;
					} 
				}
				if(!exist) {
					return -4;
				}
			}
		}
		
		//新增或更新操作
		StringBuilder sql = new StringBuilder();
		int status = 0;
		Map<String,Object> rMap = getFarmUser(username);
		
		List<Map> existList = isExistence(username);
		if (existList != null && existList.size() != 0) {
			String eparentId = existList.get(0).get("parent_id").toString()==null?"":existList.get(0).get("parent_id").toString();
			if(StringUtils.isNotBlank(eparentId) && StringUtils.isNotBlank(adminId) && !adminId.equals(eparentId)) {
				return -33;//用户名称已存在
			}
		}	
		
		int userId = 0;
		List<Map> hList = null;
		if(rMap!=null){
			sql.append("update farmuser set pwd=:pwd,enabled=:enabled,regist_time=:registTime,last_time=:lastTime,parent_id=:parentId,serverIp=:serverIp")
			.append(" where id=:userId");

			//保存用户
			params.clear();
			params.put("pwd", pwd);
			params.put("enabled", 1);
			params.put("registTime", sdf.format(new Date()));
			params.put("lastTime", sdf.format(new Date()));
			params.put("parentId", pUser.get("id"));
			params.put("userId", rMap.get("id"));
			params.put("serverIp", adminIp);
			status = mapDao.executeSql2(sql.toString(), params);
			//保存用户角色表
			String strSql = "update farmuserrole set role_id=:userType where user_id=:userId";
			params.clear();

			params.put("userType", userType);
			params.put("userId", rMap.get("id"));
			mapDao.executeSql2(strSql,params);
			FarmUser user= HouseieeeListener.farmUserMap.get(username);//更新缓存
			user.setUsername(username);
			user.setPassword(pwd);
			HouseieeeListener.farmUserMap.put(username,user);
//			if(status>0){
//				//群发通知农场的所有203/206更新用户列表
//				String sql2 = "select distinct a.house_ieee,h.cloudIp from farmuserarea a left join farmuser u on a.user_id=u.id left join modefarmhouse h on a.house_ieee=h.houseIEEE where u.user_name=:username";
//				params.clear();
//				params.put("username", user_name);
//				final List<Map> userList = mapDao.executeSql(sql2, params);
//				noticeUpdate(userList);
//				//给xmpp添加或修改203用户
//				updateUserInfo(userList,new FarmUser(username,pwd),1);
//				//已绑定的houseIEEE
//				hList = userList;
//			}
			//查询username已经关联的houseIeee
			String sql2 = "select a.house_ieee from farmuserarea a left join farmuser u on a.user_id=u.id where u.user_name=:username";
			params.clear();
			params.put("username", username);
			hList = mapDao.executeSql(sql2, params);
			userId = ((BigInteger) rMap.get("id")).intValue();
		}else{
			//新增用户
			FarmUser user = new FarmUser();
			user.setEnabled("1");
			user.setLastTime(sdf.format(new Date()));
			user.setRegistTime(sdf.format(new Date()));
			user.setParentId(((BigInteger) pUser.get("id")).longValue());
			user.setPassword(pwd);
			user.setUsername(username);
			user.setLevelId((byte)3);
			user.setServerIp(adminIp);
			fUserDao.save(user);
			status = 1;
			//保存用户角色表
			FarmUserRole role  = new FarmUserRole();
			role.setRole_id(userType);
			role.setUser_id(user.getId());
			furDao.save(role);
			HouseieeeListener.farmUserMap.put(user.getUsername(), user);//加入缓存
//			if(status>0){
//				//群发通知农场的所有203/206更新用户列表
//				String sql2 = "select distinct a.house_ieee,h.cloudIp from farmuserarea a left join farmuser u on a.user_id=u.id left join modefarmhouse h on a.house_ieee=h.houseIEEE where u.user_name=:username";
//				params.clear();
//				params.put("username", user_name);
//				final List<Map> userList = mapDao.executeSql(sql2, params);
//				noticeUpdate(userList);
//				//给xmpp添加或修改203用户
//				updateUserInfo(userList,new FarmUser(username,pwd),0);
//				//已绑定的houseIEEE，这里应该为空
//				hList = userList;
//			}
			userId = user.getId().intValue();
		}

		
		//保存用户与区域的关联关系
		if(hList != null && !hList.isEmpty()) {
			//要删除的
			List<String> rmHList = new ArrayList<String>();
			//新增的
			List<String> nHList = new ArrayList<String>();
			int hLength = hList.size();
			int nLength = areaList.size();
			for(int j = 0; j < hLength; j++) {
				Map hMap = hList.get(j);
				String hIeee = (String) hMap.get("house_ieee");
				boolean exist = false;
				for(int i = 0; i < nLength; i++) {
					String nHIeee = areaList.get(i);
					if(hIeee.equals(nHIeee)) {
						exist = true;
						break;
					}
				}
				if(!exist) {
					rmHList.add(hIeee);
				}
			}
			for(int j = 0; j < nLength; j++) {
				String nHIeee = areaList.get(j);
				boolean exist = false;
				for(int i = 0; i < hLength; i++) {
					Map hMap = hList.get(i);
					String hIeee = (String) hMap.get("house_ieee");
					if(nHIeee.equals(hIeee)) {
						exist = true;
						break;
					}
				}
				if(!exist) {
					nHList.add(nHIeee);
				}
			}
			//删除houseIeee
			if(!rmHList.isEmpty()) {
				StringBuilder hParam = new StringBuilder();
				List<Map> rmMList = new ArrayList<Map>();
				Iterator<String> rmItor = rmHList.iterator();
				while(rmItor.hasNext()) {
					String hIeee = rmItor.next();
					Map<String, Object> rmMap = new HashMap<String, Object>();
					rmMap.put("house_ieee", hIeee);
					rmMList.add(rmMap);
					hParam.append("'").append(hIeee).append("',");
				}
				String hParamStr = hParam.deleteCharAt(hParam.length() - 1).toString();
				//从openfire中删除组成员
				removeMemberFromGroup(rmMList, new FarmUser(username, pwd));
				//删除数据库中不存在的
				String delSql = "delete from farmuserarea where user_id=:userId and house_ieee in (" + hParamStr + ")";
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("userId", userId);
				mapDao.executeSql2(delSql, paramMap);
				//返回要通知203删除用户
				noticeRMList.addAll(rmMList);
			}
			areaList = nHList;
		}
		//新增新成员
		if(areaList != null && !areaList.isEmpty()) {
			//加入到openfire的组中
			List<Map> nmList = new ArrayList<Map>();
			Iterator<String> nItor = areaList.iterator();
			while(nItor.hasNext()) {
				String hIeee = nItor.next();
				Map<String, Object> rmMap = new HashMap<String, Object>();
				rmMap.put("house_ieee", hIeee);
				nmList.add(rmMap);
			}
			this.updateUserInfo(nmList, new FarmUser(username, pwd), 0);
			//插入到数据库中
			Iterator<String> itor = areaList.iterator();
			StringBuilder inSql = new StringBuilder("insert into farmuserarea(user_id,house_ieee) values");
			StringBuilder pSql = new StringBuilder();
			while(itor.hasNext()) {
				String houseIeee = itor.next();
				pSql.append("(").append(userId).append(",'").append(houseIeee).append("'),");
			}
			String inStr = inSql.append(pSql.deleteCharAt(pSql.length() - 1)).toString();
			mapDao.executeSql2(inStr);
			//返回要通知206新增用户
			noticeNList.addAll(nmList);
		}
		return status;
	}

	@Override
	public int deleteUser(String username,String password,int userType) throws Exception{
		//先获取用户
		Map<String,Object> params = new HashMap<>();
		String sql2 = "select distinct a.house_ieee,h.cloudIp from farmuserarea a left join farmuser u on a.user_id=u.id left join modefarmhouse h on a.house_ieee=h.houseIEEE where u.user_name=:username";
		params.put("username", username);
		final List<Map> userList = mapDao.executeSql(sql2, params);
		//删除用户
		StringBuilder sql = new StringBuilder("delete u,r,a from farmuser u left join farmuserrole r on u.id = r.user_id left join farmuserarea a on u.id=a.user_id where u.user_name=:username");
		params.put("username", username);
		//params.put("password", password);
		//		if(userType>1){
		//			sql.append(" and r.role_id=:userType");
		//			params.put("userType", userType);
		//		}else if(userType==1){
		//			return -1;
		//		}
		int status = mapDao.executeSql2(sql.toString(), params);
		//清除缓存中被删除用户信息
		HouseieeeListener.farmUserMap.remove(username);
		//群发通知203/206更新用户列表
		noticeUpdate(userList);
		//给xmpp添加或修改203用户
		deleteZ203User(userList,new FarmUser(username,password));

		return status;
	}

	@Override
	public List<Map> updateUserInfo(String houseIeee, int mode) throws Exception {
		if(StringUtils.isBlank(houseIeee)) {
			throw new Exception("getUserInfo houseIeee can't be empty");
		}
		//先更新用户的mode值
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseIeee);
		params.put("mode", mode);
		String upSqlStr = "UPDATE farmuser INNER JOIN farmuserarea b ON farmuser.id=b.user_id SET mode=:mode WHERE b.house_ieee=:houseIeee";
		mapDao.executeSql2(upSqlStr, params);
		//获取用户列表
		StringBuilder getUSql = new StringBuilder("SELECT a.user_name user,a.serverIp,a.pwd password,b.role_id user_type,d.vendorCode FROM farmuser a INNER JOIN ");
		getUSql.append("farmuserrole b ON a.id=b.user_id INNER JOIN farmuserarea c ON a.id=c.user_id INNER JOIN houseieee d ON c.house_ieee=d.houseIEEE ")
		.append("WHERE c.house_ieee=:houseIeee");
		params.remove("mode");
		List<Map> userList = mapDao.executeSql(getUSql.toString(), params);
		Iterator<Map> itor = userList.iterator();
		while(itor.hasNext()) {
			Map uMap = itor.next();
			String password = (String) uMap.get("password");
			String vendorCode = (String) uMap.get("vendorCode");
			String encryptPass = Httpproxy.urlEncrypt(password, houseIeee.substring(6) + vendorCode);
			uMap.put("password", encryptPass);
			uMap.remove("vendorCode");
		}
		return userList;
	}
	@Override
	public List<Map> getBossUsers(String startRow,String pageSize,String searchParam){
		StringBuilder sql = new StringBuilder("select u.*,r.role_name as roleName,r.role_name_en as roleNameEn from farmuser u ");
		sql.append(" left join farmuserrole ur on u.id=ur.user_id left join farmrole r on ur.role_id=r.id")
		.append(" where u.parent_id=0 or u.parent_id is NULL");
		Map<String,Object> params = new HashMap<>();
		List<Map> list = null;
		if(StringUtils.isNotBlank(searchParam)&&!searchParam.equals("搜索")){
			sql.append(" and (u.user_name like :searchParam");
			sql.append(" or r.role_name like :searchParam)");
			params.put("searchParam", "%"+searchParam+"%");

		}
		sql.append(" limit :startRow,:pageSize");
		if(StringUtils.isBlank(startRow))
			startRow = "0";
		if(StringUtils.isBlank(pageSize))
			pageSize = "30";
		params.put("startRow", Integer.parseInt(startRow));
		params.put("pageSize", Integer.parseInt(pageSize));
		return list = mapDao.executeSql(sql.toString(),params);
	}
	@Override
	public int getUsersCount(String searchParam) {
		StringBuilder sql = new StringBuilder("select count(u.id) as count from farmuser u ");
		sql.append(" left join farmuserrole ur on u.id=ur.user_id left join farmrole r on ur.role_id=r.id")
		.append(" where (u.parent_id=0 or u.parent_id is NULL)");
		Map<String,Object> params = new HashMap<>();
		List<Map> list = null;
		System.out.println(sql.toString());
		if(StringUtils.isNotBlank(searchParam)&&!searchParam.equals("搜索")){
			sql.append(" and (u.user_name like :searchParam");
			sql.append(" or r.role_name like :searchParam)");
			params.put("searchParam", "%"+searchParam+"%");
			list = mapDao.executeSql(sql.toString(), params);
		}else{
			list = mapDao.executeSql(sql.toString());
		}
		if(list!=null&&!list.isEmpty()){
			return ((BigInteger)list.get(0).get("count")).intValue();
		}
		return 0;
	}
	@Override
	public int resetPwd(long id, String newPwd, String serverIp) throws Exception {
		FarmUser user = fUserDao.get(FarmUser.class, id);
		if(user==null)
			return 0; //用户不存在
		//if(StringUtils.isNotBlank(serverIp)){
			user.setServerIp(serverIp);
		//}
		user.setPassword(newPwd);
		fUserDao.save(user);

		HouseieeeListener.farmUserMap.put(user.getUsername(),user);

		//群发通知农场的所有203/206更新用户列表
		String sql2 = "select distinct a.house_ieee,h.cloudIp from farmuserarea a left join farmuser u on a.user_id=u.id left join modefarmhouse h on a.house_ieee=h.houseIEEE where u.id=:userId";
		Map<String,Object> params = new HashMap<>();
		params.put("userId", user.getId());
		final List<Map> userList = mapDao.executeSql(sql2, params);
		noticeUpdate(userList);
		//给xmpp添加或修改203用户
		updateUserInfo(userList,user,1);

		return 1; //密码重置成功
	}
	
	@Override
	public int delUserById(long id) throws Exception{
		//获取所有用户
		Map<String,Object> params = new HashMap<>();
		params.put("id", id);
		String hql = "from FarmUser where id=:id or parent_id=:id";
		List<FarmUser> userList = fUserDao.find(hql, params);
		//获取家列表
		String sql2 = "select distinct a.house_ieee,h.cloudIp from farmuserarea a left join farmuser u on a.user_id=u.id left join modefarmhouse h on a.house_ieee=h.houseIEEE where u.id=:id";
		final List<Map> list = mapDao.executeSql(sql2, params);
		//删除用户及其子用户,以及该老板用户下的所有区域及相关数据
		String sql = "delete u,r,a,h,d,n,mc,mhm,mhs,mmm,mms from farmuser u left join farmuserrole r on u.id=r.user_id "
				+"left join farmuserarea a on u.id=a.user_id left join modefarmhouse h on a.house_ieee=h.houseIEEE "
				+"left join modefarmdevice d on h.id=d.houseId left join modefarmnode n on h.id=n.houseId "
				+"left join modefarmmainclause mc on h.id=mc.houseId left join modefarmhvacmain mhm on mc.id=mhm.mainID "
				+"left join modefarmhvacsub mhs on mc.id=mhs.MID left join modefarmmacromain mmm on h.id=mmm.HouseID "
				+"left join modefarmmacrosub mms on mmm.ID=mms.MID where u.id=:id or u.parent_id=:id";
		int status = mapDao.executeSql2(sql, params);

		//xmpp删除所有203用户
		for(FarmUser user:userList){
			deleteZ203User(list,user);
			//删除该管理员用户及其所有子用户缓存
			HouseieeeListener.farmUserMap.remove(user.getUsername());
		}
		return status;
	}


	@Override
	public int checkNameUnique(String username){
		String sql = "select count(id) as count from farmuser where user_name = :username limit 1";
		Map<String,Object> params = new HashMap<>();
		params.put("username", username);
		List<Map> list = mapDao.executeSql(sql, params);
		if(list!=null&&!list.isEmpty()){
			return ((BigInteger)list.get(0).get("count")).intValue();
		}
		return 0;
	}
	@Override
	public int addBossUser(FarmUser user) throws Exception{
		user.setLevelId((byte)3);
		user.setEnabled("1");
		user.setRegistTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		fUserDao.save(user);
		FarmUserRole urole = new FarmUserRole();
		urole.setRole_id(user.getRoleId());
		urole.setUser_id(user.getId());
		furDao.save(urole);
		//final List<Map> userList = new ArrayList<>();
		//给xmpp添加或修改203用户
		//updateUserInfo(userList,user,0);
		HouseieeeListener.farmUserMap.put(user.getUsername(), user);//加入缓存

		return 1;
	}

	/**
	 * 删除203用户
	 * @param userList
	 */
	private void deleteZ203User(final List<Map> userList,final FarmUser user) throws Exception {
		if(userList!=null){
			for(Map map:userList){
				Object ieee = map.get("house_ieee");
				if(ieee!=null){
					Object cloudIp = HouseieeeListener.houseieeeProxyserverMap.get(ieee);
					String cloudPort = PropertiesUtil.getProperty("openfire.port");
					if(cloudIp!=null){
						Map<String,Object> params = new HashMap<>();
						StringBuilder strUrl = new StringBuilder("http://");
						String json = "{\"username\":\""+user.getUsername()+"\",\"password\":\""+user.getPassword()+"\",\"houseIeee\":\""+ieee+"\",\"oper\":\"deleteUser\"}";
						params.put("json", json);
						//不论用户存不存在，先删除用户，再添加用户、添加组
						strUrl.append(cloudIp).append(":").append(cloudPort).append("/plugins/userService/usergroup?");
						//String result = TestHttpclient.postUrlWithParams(strUrl.toString(), params, "utf-8");
						//在这里应用get跳转，而不能使用POST跳转；
						String result = TestHttpclient.getUrl(strUrl.toString(), params, "utf-8");
						RespParams resp = JSON.parseObject(result, RespParams.class);
						Map<String,Object> m = (Map)(resp.getResponse_params());
						int status = (Integer)(m.get("status"));
						if(status!=1)
							throw new Exception("修改openfire用户信息失败");
					}
				}
			}
		}
	}

	/**
	 * 给xmpp添加或修改203用户
	 * @param userList
	 */
	private void updateUserInfo(final List<Map> userList,final FarmUser user,final int func) throws Exception{
		if(userList!=null&&!userList.isEmpty()){
			for(Map map:userList){
				Object ieee = map.get("house_ieee");
				if(ieee!=null){
					Object openfireIp = HouseieeeListener.houseieeeProxyserverMap.get(ieee);
					String openfirePort = PropertiesUtil.getProperty("openfire.port");
					if(openfireIp!=null){
						Map<String,Object> params = new HashMap<>();
						StringBuilder strUrl = new StringBuilder("http://");
						String json = "";
						if(func==0){//新增用户
							json = "{\"username\":\""+user.getUsername()+"\",\"password\":\""+user.getPassword()+"\",\"houseIeee\":\""+ieee+"\",\"oper\":\"addUser\"}";
						}else{//修改用户
							json = "{\"username\":\""+user.getUsername()+"\",\"password\":\""+user.getPassword()+"\",\"houseIeee\":\""+ieee+"\",\"oper\":\"updatePass\"}";
						}
						params.put("json", json);
						strUrl.append(openfireIp).append(":").append(openfirePort).append("/plugins/userService/usergroup?");
						String result = TestHttpclient.getUrl(strUrl.toString(), params, "utf-8");
						RespParams resp = JSON.parseObject(result, RespParams.class);
						Map<String,Object> m = (Map)(resp.getResponse_params());
						int status = (Integer)(m.get("status"));
						if(status!=1)
							throw new Exception("修改openfire用户信息失败");
					}
				}
			}
		}
	}

	/**
	 * 群发通知203更新用户信息
	 * @param userList
	 */
	@Override
	public void noticeUpdate(final List<Map> userList){
		Thread th = new Thread(){
			@Override
			public void run(){
				if(userList!=null&&!userList.isEmpty()){
					List<String> ieeeList = new ArrayList<>();
					//由于下发通知是群发，因此剔除重复cloudIp
					Set<String> ipList = new HashSet<>();
					for(Map map:userList){
						if(map!=null){
							String ieee = (String)map.get("house_ieee");
							if(StringUtils.isNotBlank(ieee)){
								ieeeList.add(ieee);
								String cloudIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(ieee);
								ipList.add(cloudIp);
							}
						}
					}
					String cloudPort = PropertiesUtil.getProperty("xmpp.port");
					String context = "{\"comm_state\":\"1251\",\"data_type\":20,\"data\":{\"message\":\"getuserinfo\"}}";
					for(String cloudIp:ipList){
						Map<String,Object> params = new HashMap<>();
						StringBuilder strUrl = new StringBuilder("http://");
						strUrl.append(cloudIp).append(":").append(cloudPort).append("/spring-async/z203chat/pollb?");

						params.put("context", context);
						params.put("username", JSON.toJSONString(ieeeList));
						try {
							TestHttpclient.postUrlWithParams(strUrl.toString(), params, "utf-8");
						} catch (IOException e) {
							logger.error("notice z203 error");
						}
					}
				}
			}
		};
    	th.setName("noticeUpdate");
    	th.start();
	}

	@Override
	public List<Map> getFarmUsers() {
		String sql = "select user_name,pwd,parent_id from farmuser";
		return mapDao.executeSql(sql);
	}

	@Override
	public void scheduleUpdateUserPassword() {
		logger.info("---------update user password");
		HouseieeeListener.cacheFarmUser();
	}

	@Override
	public List<Map> getAppUserInfo(String userName) throws Exception {
		if(StringUtils.isBlank(userName)) {
			throw new Exception("getAppUserInfo userName can't be empty");
		}
		StringBuilder getUSql = new StringBuilder("SELECT a.id,a.serverIp,a.user_name user,a.pwd password,c.role_id user_type FROM farmuser a ");
		getUSql.append("INNER JOIN farmuser b ON a.parent_id=b.id INNER JOIN farmuserrole c ON a.id=c.user_id WHERE b.user_name=:userName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		List<Map> userList = mapDao.executeSql(getUSql.toString(), params);
		if(!userList.isEmpty()) {
			//userName补0
			StringBuilder userNameCrp = new StringBuilder(userName);
			int yuCount = userName.length() % 16;
			if(yuCount != 0) {
				for(int i = 0; i < (16-yuCount); i++) {
					userNameCrp.append("0");
				}
			}
			//获取管理员的区域数
			StringBuilder getASql = new StringBuilder("SELECT COUNT(*) aCount FROM farmuserarea a INNER JOIN farmuser b ON a.user_id=b.id ");
			getASql.append("INNER JOIN modefarmhouse c ON a.house_ieee=c.houseIEEE AND a.user_id=c.userId WHERE b.user_name=:userName");
			List<Map> aList = mapDao.executeSql(getASql.toString(), params);
			int aCount = 0;
			if(!aList.isEmpty()) {
				aCount = ((BigInteger) aList.get(0).get("aCount")).intValue();
			}
			//获取每个用户的区域
			StringBuilder getPSql = new StringBuilder("SELECT a.house_ieee houseIeee,b.houseName name FROM farmuserarea a INNER JOIN modefarmhouse b ");
			getPSql.append("ON a.house_ieee=b.houseIEEE INNER JOIN farmuser c ON b.userId=c.id WHERE a.user_id=:pUId AND c.user_name=:userName");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userName", userName);
			Iterator<Map> itor = userList.iterator();
			while(itor.hasNext()) {
				Map uMap = itor.next();
				int allArea = 0;
				int pUId = ((BigInteger) uMap.get("id")).intValue();
				paramMap.put("pUId", pUId);
				List<Map> areaList = mapDao.executeSql(getPSql.toString(), paramMap);
				if(areaList.size() == aCount) {
					allArea = 1;
				}
				uMap.put("allarea", allArea);
				uMap.put("areas", areaList);
				String password = (String) uMap.get("password");
				String encryptPass = Httpproxy.urlEncrypt(password, userNameCrp.toString());
				uMap.put("password", encryptPass);
				uMap.remove("id");
			}
		}
		//get区域
		return userList;
	}

	@Override
	public void modifyAppPassword(String userName, String password) throws Exception {
		if(StringUtils.isBlank(userName)) {
			throw new RuntimeException("userName can't be empty");
		}
		if(StringUtils.isBlank(password)) {
			throw new RuntimeException("password can't be empty");
		}
		//更新密码
		String upSql = "update farmuser set pwd=:password where user_name=:userName";
		String decodePwd = Httpproxy.urlDecodeAddExp(password, userName);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("password", decodePwd);
		params.put("userName", userName);
		mapDao.executeSql2(upSql, params);
		FarmUser user = new FarmUser();
		user.setUsername(userName);
		user.setPassword(decodePwd);
		HouseieeeListener.farmUserMap.put(userName,user);

		//群发通知农场的所有203/206更新用户列表
		String sql2 = "select distinct a.house_ieee,h.cloudIp from farmuserarea a left join farmuser u on a.user_id=u.id left join modefarmhouse h on a.house_ieee=h.houseIEEE where u.user_name=:userName";
		params.clear();
		params.put("userName", userName);
		final List<Map> userList = mapDao.executeSql(sql2, params);
		noticeUpdate(userList);
		//给xmpp添加或修改203用户
		updateUserInfo(userList,user,1);
	}
	
	/**
	 * 从组中删除
	 * @param userName
	 * @param pwd
	 * @param groupName
	 * @throws Exception
	 */
	private void removeMemberFromGroup(List<Map> hList, FarmUser user) throws Exception {
		if(hList == null || hList.isEmpty()) {
			return;
		}
		Iterator<Map> hItor = hList.iterator();
		String openfirePort = PropertiesUtil.getProperty("openfire.port");
		while(hItor.hasNext()) {
			Map hMap = hItor.next();
			String hIeee = (String) hMap.get("house_ieee");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(hIeee);
			String addUrl = "http://" + serverIp + ":" + openfirePort + "/plugins/userService/usergroup";
			String json = "{\"username\":\"" + user.getUsername() + "\",\"password\":\"" + user.getPassword() + "\",\"houseIeee\":\"" + hIeee + "\",\"oper\":\"removeGroup\"}";
			Map<String, Object> postMap = new HashMap<String, Object>();
			postMap.put("json", json);
			String result = TestHttpclient.postUrlWithParams(addUrl, postMap, "utf-8");
			Map resultMap = JSON.parseObject(result, Map.class);
			if((Integer) ((Map) resultMap.get("response_params")).get("status") != 1) {
				throw new Exception("remove group member error");
			}
		}
	}
	@Override
	public List<Map> getUserServer(Map param) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select f.id,f.user_name,f.serverIp from farmuser f where 1=1 ");
		if(StringUtils.isNotBlank((String)param.get("username"))){
			sql.append(" and f.user_name =:userName" );
			params.put("userName", param.get("username"));
		}
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		//String ll = list.get(0).toString();
		if(list != null) {
			return list;
		}
		return null;
	}
}
