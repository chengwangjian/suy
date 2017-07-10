/**
 * 
 */
package sy.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.ReliUser;
import sy.service.ReliUserServiceI;

import com.flywind.app.data.RoleInfo;

/**
 * @author hlw
 * 
 */
@Service("reliUserService")
public class ReliUserServiceImpl implements ReliUserServiceI {

	private static final Logger logger = Logger
			.getLogger(ReliUserServiceImpl.class);
	private BaseDaoI<ReliUser> reliUserDao;
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<ReliUser> getReliUserDao() {
		return reliUserDao;
	}

	@Autowired
	public void setReliUserDao(BaseDaoI<ReliUser> reliUserDao) {
		this.reliUserDao = reliUserDao;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sy.service.ReliUserServiceI#addReliUser(sy.model.ReliUser)
	 */
	@Override
	public Serializable addReliUser(ReliUser reliUser) throws Exception {
		// TODO Auto-generated method stub
		return reliUserDao.save(reliUser);
	}

	@Override
	public boolean isUserExist(String userName) throws Exception {
//		StringBuilder hql = new StringBuilder(
//				"from ReliUser a where a.user_name='");
//		hql.append(userName).append("'");
		StringBuilder hql = new StringBuilder(
				"from ReliUser a where a.user_name=:userName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		List<ReliUser> ruList = reliUserDao.find(hql.toString(), params);
		if (ruList.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public int getUserCount(Map<String, Object> condition) throws Exception {
		StringBuilder sql = new StringBuilder(
				"select count(*) as user_count from reliuser ");
		if (condition != null) {
			sql.append("where ");
			Iterator itor = condition.keySet().iterator();
			while (itor.hasNext()) {
				String key = (String) itor.next();
//				sql.append(key).append("='").append(condition.get(key))
//						.append("' and ");
				sql.append(key).append("=:").append(key).append(" and ");
			}
			sql.delete(sql.length() - 5, sql.length() - 1);
		}
		List<Map> cList = mapDao.executeSql(sql.toString(), condition);
		return ((BigInteger) cList.get(0).get("user_count")).intValue();
	}

	@Override
	public List<Map> queryRole(Map requestparamsMap) {
		StringBuilder sql = new StringBuilder("select relirole.*,relilevel.level_name,relilevel.level_name_en from  relirole LEFT JOIN relilevel on relirole.level_id=relilevel.id where 1=1 ");
		HashMap<String,Object>params=new HashMap<String, Object>();
		String pageSize="";
		String pageIndex="";
		pageIndex = (String) requestparamsMap.get("pageIndex");
		 pageSize = (String) requestparamsMap.get("pageSize");
		if (StringUtils.isBlank(pageIndex))
			pageIndex = "1";
		if (StringUtils.isBlank(pageSize))
			pageSize = "30";
	
		if(!requestparamsMap.isEmpty()&&requestparamsMap!=null){
			if(StringUtils.isNotBlank((String)requestparamsMap.get("level_id"))){
				sql.append("and relirole.level_id=:level_id ");
				params.put("level_id", requestparamsMap.get("level_id"));	
			}
			if(StringUtils.isNotBlank((String)requestparamsMap.get("roleName"))){
				sql.append("and relirole.role_name like :roleName ");
				params.put("roleName", "%" + requestparamsMap.get("roleName") + "%");	
			}
		}
		int pageindex=Integer.valueOf(pageIndex);
		int pagesize=Integer.valueOf(pageSize);
		int startRow = (pageindex - 1) * pagesize;
		sql.append("limit "+startRow).append(",").append(pagesize);
		List<Map>list=this.mapDao.executeSql(sql.toString(), params);
		return list;
	}
	

	@Override
	public void addRoleToUser(RoleInfo roleInfo) {
	  
	  try {
		  String deleteSql=" DELETE FROM reliuserrole  WHERE user_id=:userId";
		HashMap<String,Object>params=new HashMap<String, Object>();
    	params.put("userId",roleInfo.getUserId());
		this.mapDao.executeSql2(deleteSql,params);
		List<Long>  roleIdList=roleInfo.getRoleIdList();
		  if(!roleIdList.isEmpty()){
		    for(int i=0;i<roleIdList.size();i++){
		    	/*	HashMap<String,Object>params=new HashMap<String, Object>();
		    	params.put("userId",roleInfo.getUserId());
		    	params.put("roleId",roleIdList.get(i));*/
		       String addSql="INSERT INTO reliuserrole(user_id,role_id) VALUES('"+roleInfo.getUserId()+"','"+roleIdList.get(i)+"')";
		     	int result=	this.mapDao.executeSql2(addSql);
		    	 }  
		    	   
		       }
				
	} catch (Exception e) {
		// TODO: handle exception
	}
		
	}
	
	
	@Override
	public List<Map> queryUser(Map<String, Object> condition, int pageIndex,
			int pageSize) throws Exception {
		//StringBuilder sql = new StringBuilder(
		//"select a.id as id,b.id as level_id,a.user_name as user_name,b.level_name as level_name from reliuser a left join relilevel b on a.level_id=b.id ");
		StringBuilder sql = new StringBuilder("select a.id as id,b.id as level_id,a.user_name as user_name,b.level_name as level_name,GROUP_CONCAT(r.role_name SEPARATOR ',') as role_name from reliuser a left join relilevel b on a.level_id=b.id LEFT JOIN reliuserrole rr on a.id=rr.user_id LEFT JOIN relirole r on r.id=rr.role_id GROUP BY id ");	

		if (condition != null) {
			sql.append("where ");
			Iterator itor = condition.keySet().iterator();
			while (itor.hasNext()) {
				String key = (String) itor.next();
//				sql.append(key).append("='").append(condition.get(key))
//						.append("' and ");
				sql.append(key).append("=:").append(key).append(" and ");
			}
			sql.delete(sql.length() - 5, sql.length() - 1);
		}
		sql.append("order by regist_time desc limit ");
		int startRow = (pageIndex - 1) * pageSize;
		sql.append(startRow).append(",").append(pageSize);
		List<Map> uList = mapDao.executeSql(sql.toString(), condition);
		return uList;
	}

	@Override
	public void updateReliUser(ReliUser reliUser) throws Exception {
//		StringBuilder sql = new StringBuilder("update reliuser set pwd='");
//		sql.append(reliUser.getPwd()).append("' where id=")
//				.append(reliUser.getId());
		StringBuilder sql = new StringBuilder("update reliuser set pwd=:password where id=:id");
//		sql.append(reliUser.getPwd()).append("' where id=")
//				.append(reliUser.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("password", reliUser.getPwd());
		params.put("id", reliUser.getId());
		mapDao.executeSql2(sql.toString(), params);
	}

	@Override
	public boolean isPwdRight(Long id, String pwd) throws Exception {
		StringBuilder sql = new StringBuilder("select 1 from reliuser where ");
//		sql.append("id=").append(id).append(" and pwd='").append(pwd)
//				.append("'");
		sql.append("id=:id and pwd=:password");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("password", pwd);
		params.put("id", id);
		List<Map> pwdList = mapDao.executeSql(sql.toString(), params);
		if (pwdList.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public void deleteUser(Long id) throws Exception {
		StringBuilder sql = new StringBuilder("delete from reliuser where id=:id");
//		sql.append(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mapDao.executeSql2(sql.toString(), params);
		String deleteSql=" DELETE r.* FROM reliuserrole r WHERE r.user_id=:userId";
		Map<String,Object>param=new HashMap<String, Object>();
    	param.put("userId",id);
		this.mapDao.executeSql2(deleteSql,param);
	}

	@Override
	public ReliUser checkUser(String user_name, String pwd) throws Exception {
//		StringBuilder hql = new StringBuilder(
//				"from ReliUser where user_name='");
		StringBuilder hql = new StringBuilder(
				"from ReliUser where user_name=:userName and pwd=:password");
//		hql.append(user_name).append("' and pwd='").append(pwd).append("'");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", user_name);
		params.put("password", pwd);
		List<ReliUser> uList = reliUserDao.find(hql.toString(), params);
		if (uList.isEmpty())
			return null;
		else
			return uList.get(0);
//			return ((BigInteger) ((Map) uList.get(0)).get("id")).longValue();
	}

	@Override
	public Map<String, Integer> getPrivilege(Long id) throws Exception {
//		StringBuilder sql = new StringBuilder(
//				"select c.level_id,a.privilege_code as privilege_code from reliprivilege a left join relilevelprivilege b ");
//		sql.append("on a.id = b.privilege_id left join reliuser c on b.level_id = c.level_id where c.id =:id");
////		sql.append(id);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("id", id);
//		List<Map> pList = mapDao.executeSql(sql.toString(), params);
//		return pList;
		Map<String, Integer> pObject = new HashMap<String, Integer>();
		String sql = "SELECT a.id,a.is_leaf isLeaf,a.parent_id parentId FROM reliprivilege a INNER JOIN reliroleprivilege b ON a.id = b.privilege_id INNER JOIN reliuserrole c ON b.role_id = c.role_id WHERE c.user_id = :userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", id);
		List<Map> pList = mapDao.executeSql(sql.toString(), params);
		if(!pList.isEmpty()) {
			sql = "SELECT a.id,a.is_leaf isLeaf,a.parent_id parentId FROM reliprivilege a WHERE a.is_leaf = 0 ORDER BY a.parent_id DESC";
			List<Map> ppList = mapDao.executeSql(sql.toString());
			Iterator<Map> pItor = pList.iterator();
			while(pItor.hasNext()) {
				Map<String, Object> pMap = pItor.next();
				Integer parentId = (Integer) pMap.get("parentId");
				String priId = pMap.get("id").toString();
				pObject.put(priId, 1);
				isParentChecked(parentId, ppList, pObject);
			}
		}
		return pObject;
	}
	
	private void isParentChecked(Integer parentId, List<Map> ppList, Map<String, Integer> pObject) {
		Iterator<Map> pItor = ppList.iterator();
		while(pItor.hasNext()) {
			Map ppMap = pItor.next();
			Integer id = (Integer) ppMap.get("id");
			Integer parentIdTmp = (Integer) ppMap.get("parentId");
			if(parentId.intValue() == id.intValue()) {
				Boolean isChecked = (Boolean) ppMap.get("isChecked");
				if(isChecked != null) {
					break;
				}
				pObject.put(id.toString(), 1);
				ppMap.put("isChecked", true);
				isParentChecked(parentIdTmp, ppList, pObject);
			}
		}
	}
	
	@Override
	public String getPass(ReliUser reliUser) throws Exception {
		StringBuilder sql = new StringBuilder("select pwd from reliuser where id=:id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", reliUser.getId());
		List<Map> u = mapDao.executeSql(sql.toString(), params);
		if(u.isEmpty())
			return null;
		else
			return (String) u.get(0).get("pwd");
	}
	
	@Override
	public ReliUser getReliUserByName(ReliUser reliUser) throws Exception {
		StringBuilder hql = new StringBuilder(
				"from ReliUser where user_name=:userName");
//		hql.append(user_name).append("' and pwd='").append(pwd).append("'");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", reliUser.getUser_name());
		List<ReliUser> uList = reliUserDao.find(hql.toString(), params);
		if (uList.isEmpty())
			return null;
		else
			return uList.get(0);
	}

	@Override
	public List<Map> function(String userid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid );
		String sql = "SELECT a.id,a.level_id,b.privilege_id from reliuser a left JOIN relilevelprivilege b on a.level_id = b.level_id where a.id = :userid ";
		List<Map> u = mapDao.executeSql(sql.toString(), params);
		String sql1 = "select distinct(privilege_id) from relilevelprivilege where privilege_id not in (";
		if(u.size()>0){
			for(int i = 0; i<u.size() ; i++){
				sql1+=u.get(i).get("privilege_id");
				sql1+=",";
			}
			sql1 = sql1.substring(0, sql1.length()-1);
			sql1+=")";
		}
		else {
			sql1+="'')";//不会有userid为空的情况吧。。。
		}
		List<Map> pMaps = mapDao.executeSql(sql1.toString());
		return pMaps;
	}

	@Override
	public List<Map> functionAble(String userid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid );
		String sql = "SELECT a.id,a.level_id,b.privilege_id from reliuser a left JOIN relilevelprivilege b on a.level_id = b.level_id where a.id = :userid ";
		List<Map> u = mapDao.executeSql(sql.toString(), params);
		String sql1 = "select distinct(privilege_id) from relilevelprivilege where privilege_id in (";
		if(u.size()>0){
			for(int i = 0; i<u.size() ; i++){
				sql1+=u.get(i).get("privilege_id");
				sql1+=",";
			}
			sql1 = sql1.substring(0, sql1.length()-1);
			sql1+=")";
		}
		else {
			sql1+="'')";//不会有userid为空的情况吧。。。
		}
		List<Map> pMaps = mapDao.executeSql(sql1.toString());
		return pMaps;
	}

	@Override
	public int checkUserid(String userid, String level_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql ="select 1 from reliuser where id = :userid and level_id = :level_id limit 1";
		params.put("userid", userid);
		params.put("level_id", level_id);
		List<Map> u = mapDao.executeSql(sql.toString(), params);
		if(u.size()>0){
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public ReliUser checkUseridAndName(Long userid) {
		Map<String, Object> params = new HashMap<String,Object>();
		String hql = "from ReliUser a where a.id=:userid";
		params.put("userid", userid);
		List<ReliUser> uList = reliUserDao.find(hql.toString(), params);
		if (uList.isEmpty())
			return null;
		else
			return uList.get(0);
	}

	@Override
	public List<Map> queryReliUserLevel() {
	
		return this.mapDao.executeSql("select * from  relilevel");
	}

	@Override
	public List<Map> getRoleByUserid(Map<String, Object> requestparamsMap) {
		HashMap<String,Object>params=new HashMap<String, Object>();
		List<Map>list=null;
		try {
			String sql="select r.role_id,rr.role_name from reliuserrole r inner join relirole rr on r.role_id=rr.id WHERE r.user_id=:userId";
			params.put("userId",requestparamsMap.get("userId"));
		 list =	this.mapDao.executeSql(sql, requestparamsMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return list;
	}

	@Override
	public List<Map> queryRoleCount(Map requestparamsMap) {
		String sql="select count(*) as roleCount from relirole where 1=1 ";
		HashMap<String,Object> params=new HashMap<String, Object>();
		if(!requestparamsMap.isEmpty()&&requestparamsMap!=null){	
			if(StringUtils.isNotBlank((String)requestparamsMap.get("level_id"))){
				sql+=" and relirole.level_id=:level_id ";
				params.put("level_id", requestparamsMap.get("level_id"));	
			}
			if(StringUtils.isNotBlank((String)requestparamsMap.get("roleName"))){
				sql+=" and relirole.role_name like :roleName ";
				params.put("roleName", "%" + requestparamsMap.get("roleName") + "%");	
			}
		}
		return this.mapDao.executeSql(sql,params);
	}

}
