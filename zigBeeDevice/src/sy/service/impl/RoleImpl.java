package sy.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Role;
import sy.service.RoleI;
import sy.util.StringUtil;

/**
 * @author huanglw
 *
 */
@Service("roleService")
public class RoleImpl implements RoleI {
	private static final Logger log = Logger.getLogger(IRServiceImpl.class);
	private BaseDaoI<Role> roleDao;
	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<Role> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Role> roleDao) {
		this.roleDao = roleDao;
	}
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public List<Map> getLevel() throws Exception {
		String sql = "select id,level_name levelName,level_name_en levelNameEn from relilevel";
		return mapDao.executeSql(sql);
	}
	
	@Override
	public int saveRole(Role role, String oldRoleName) throws Exception {
		if(role.getId() != null && role.getId() != -1 && role.getId() != 0) {
			/*判断是否重名*/
			String sqlStr = "select 1 from relirole where role_name = :roleName and role_name <> :oldRoleName limit 1";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("roleName", role.getRoleName());
			param.put("oldRoleName", oldRoleName);
			List<Map> roles = mapDao.executeSql(sqlStr, param);
			if(!roles.isEmpty()) {
				return -10;
			}
			
			sqlStr = "delete from reliroleprivilege where role_id = :roleId";
			param = new HashMap<String, Object>();
			param.put("roleId", role.getId());
			roleDao.executeSql2(sqlStr, param);
			
			sqlStr = "delete from reliroleclient where role_id = :roleId";
			param = new HashMap<String, Object>();
			param.put("roleId", role.getId());
			roleDao.executeSql2(sqlStr, param);
			
			List<Integer> priList = role.getPrivilegeList();
			if(!priList.isEmpty()) {
				StringBuilder sql = new StringBuilder("insert into reliroleprivilege(id, role_id, privilege_id) values");
				for(Integer privilegeId : priList) {
					sql.append("(NULL,").append(role.getId()).append(",").append(privilegeId).append("),");
				}
				sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
				roleDao.executeSql2(sqlStr);
			}
			
			List<Integer> clientList = role.getClientList();
			if(!clientList.isEmpty()) {
				StringBuilder sql = new StringBuilder("insert into reliroleclient(id, role_id, client_id) values");
				for(Integer clientId : clientList) {
					sql.append("(NULL,").append(role.getId()).append(",").append(clientId).append("),");
				}
				sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
				roleDao.executeSql2(sqlStr);
			}
			roleDao.update(role);
		}
		else {
			/*判断是否重名*/
			String sqlStr = "select 1 from relirole where role_name = :roleName limit 1";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("roleName", role.getRoleName());
			List<Map> roles = mapDao.executeSql(sqlStr, param);
			if(!roles.isEmpty()) {
				return -10;
			}
			
			roleDao.save(role);
			
			List<Integer> priList = role.getPrivilegeList();
			if(!priList.isEmpty()) {
				StringBuilder sql = new StringBuilder("insert into reliroleprivilege(id, role_id, privilege_id) values");
				for(Integer privilegeId : priList) {
					sql.append("(NULL,").append(role.getId()).append(",").append(privilegeId).append("),");
				}
				sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
				roleDao.executeSql2(sqlStr);
			}
			
			List<Integer> clientList = role.getClientList();
			if(!clientList.isEmpty()) {
				StringBuilder sql = new StringBuilder("insert into reliroleclient(id, role_id, client_id) values");
				for(Integer clientId : clientList) {
					sql.append("(NULL,").append(role.getId()).append(",").append(clientId).append("),");
				}
				sqlStr = sql.deleteCharAt(sql.length() - 1).toString();
				roleDao.executeSql2(sqlStr);
			}
		}
		return 1;
	}
	
	@Override
	public Integer getRoleCount(Map condition) throws Exception {
		String sql = "select count(*) role_count from relirole ";
		String roleName = (String) condition.get("roleName");
		Integer levelId = (Integer) condition.get("levelId");
		Map<String, Object> params = new HashMap<>();
		if(StringUtils.isNotBlank(roleName) && levelId != null && levelId != 0) {
			sql += "where role_name like :roleName and level_id = :levelId"; 
			params.put("roleName", "%" + roleName + "%");
			params.put("levelId", levelId);
		}
		else if(StringUtils.isNotBlank(roleName)) {
			sql += "where role_name like :roleName";
			params.put("roleName", "%" + roleName + "%");
		}
		else if(levelId != null && levelId != 0) {
			sql += "where level_id = :levelId"; 
			params.put("levelId", levelId);
		}
		List<Map> roleCount = mapDao.executeSql(sql, params);
		return ((BigInteger) roleCount.get(0).get("role_count")).intValue();
	}
	
	@Override
	public List<Map> getRoles(Map condition) throws Exception {
		String sql = "select a.id,a.role_name roleName,a.level_id levelId,b.level_name levelName,b.level_name_en levelNameEn from relirole a left join reliLevel b on a.level_id = b.id ";
		String roleName = (String) condition.get("roleName");
		Integer levelId = (Integer) condition.get("levelId");
		Map<String, Object> params = new HashMap<>();
		if(StringUtils.isNotBlank(roleName) && levelId != null && levelId != 0) {
			sql += "where role_name like :roleName and level_id = :levelId "; 
			params.put("roleName", "%" + roleName + "%");
			params.put("levelId", levelId);
		}
		else if(StringUtils.isNotBlank(roleName)) {
			sql += "where role_name like :roleName ";
			params.put("roleName", "%" + roleName + "%");
		}
		else if(levelId != null && levelId != 0) {
			sql += "where level_id = :levelId "; 
			params.put("levelId", levelId);
		}
		
		Integer pageSize = (Integer) condition.get("pageSize");
		Integer startRow = (Integer) condition.get("startRow");
		if(pageSize == null || pageSize == 0 || startRow == null || startRow < 0) {
			pageSize = 30;
			startRow = 0;
		}
		sql += "limit " + startRow + "," + pageSize;
		List<Map> roles = mapDao.executeSql(sql, params);
		return roles;
	}
	
	@Override
	public int deleteRole(Role role) throws Exception {
		String sqlStr = "select a.id from relirole a inner join reliuserrole b on a.id = b.role_id where a.id = :roleId limit 1";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", role.getId());
		List<Role> roleUsers = roleDao.executeSql(sqlStr, param);
		if(!roleUsers.isEmpty()) {
			return -10;
		}
		
		sqlStr = "delete from reliroleprivilege where role_id = :roleId";
		param = new HashMap<String, Object>();
		param.put("roleId", role.getId());
		roleDao.executeSql2(sqlStr, param);
		
		sqlStr = "delete from reliroleclient where role_id = :roleId";
		param = new HashMap<String, Object>();
		param.put("roleId", role.getId());
		roleDao.executeSql2(sqlStr, param);
		
		roleDao.delete(role);
		return 1;
	}
	
	@Override
	public Map<String, Object> getRole(Role role) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		if(role.getId() != -1) {
			String sqlStr = "select a.id,a.role_name roleName,a.level_id levelId,b.level_name levelName from relirole a left join relilevel b on a.level_id = b.id where a.id = :roleId";
			params.put("roleId", role.getId());
			List<Map> roles = mapDao.executeSql(sqlStr, params);
			Map<String, Object> roleMap = roles.get(0);
			resultMap.put("role", roleMap);
		}
		
		StringBuilder sql = new StringBuilder("select a.id,a.privilege_name privilegeName,a.privilege_name_en privilegeNameEN,a.is_leaf isLeaf, ");
		sql.append("c.role_id roleId,a.parent_id parentId from reliprivilege a ");
		sql.append("left join (select b.role_id,b.privilege_id from reliroleprivilege b where b.role_id = :roleId) c on a.id = c.privilege_id ");
		sql.append("order by a.parent_id asc, a.order_index asc");
		params = new HashMap<>();
		params.put("roleId", role.getId());
		List<Map> privileges = mapDao.executeSql(sql.toString(), params);
		Map<String, Object> priMap = compositeList(privileges);
		addCheck(priMap);
		resultMap.put("privileges", priMap);
		
		return resultMap;
	}
	
	/**
	 * 组装权限的树形关系
	 * @param privileges
	 * @param cacheList
	 * @return
	 */
	private Map<String, Object> compositeList(List<Map> privileges) {
		List<Map> cacheList = new ArrayList<>();
		for(Map priMap : privileges) {
			boolean hasParentCache = false;
			for(Map cacheMap : cacheList) {
				if(compositeDom(cacheMap, priMap)) {
					hasParentCache = true;
					break;
				}
			}
			if(!hasParentCache) {
				cacheList.add(priMap);
			}
		}
		
		Map<String, Object> rootMap = null;
		boolean isRoot = true;
		int cacheListLen = cacheList.size();
		for(int i = cacheListLen - 1; i >= 0; i--) {
			Map<String, Object> compMap = cacheList.get(i);
			for(int j = 0; j < i; j++) {
				Map<String, Object> cacheMap = cacheList.get(i);
				if(compositeDom(cacheMap, compMap)) {
					cacheList.remove(i);
					isRoot = false;
					break;
				}
			}
			if(isRoot) {
				rootMap = compMap;
				isRoot = false;
			}
		}
		return rootMap;
	}
	
	/**
	 * 组成树形结构
	 * @param domMap
	 * @param targetMap
	 * @return
	 */
	private boolean compositeDom(Map<String, Object> domMap, Map<String, Object> targetMap) {
		boolean hasParent = false;
		Integer isLeaf = (Integer) domMap.get("isLeaf");
		if(isLeaf == 1) {
			return hasParent;
		}
		Iterator<String> itor = domMap.keySet().iterator();
		while(itor.hasNext()) {
			String key = itor.next();
			if(key.equals("id")) {
				Integer id = (Integer) domMap.get(key);
				Integer parentId = (Integer) targetMap.get("parentId");
				Integer childId = (Integer) targetMap.get("id");
				if(id.intValue() == parentId.intValue()) {
					domMap.put(childId.toString(), targetMap);
					hasParent = true;
					break;
				}
			}
			else if(StringUtil.isNumber(key)) {
				Map<String, Object> childMap = (Map<String, Object>) domMap.get(key);
				if(!hasParent) {
					if(compositeDom(childMap, targetMap)) {
						hasParent = true;
						break;
					}
				}
			}
		}
		return hasParent;
	}
	
	/**
	 * 计算是否勾选
	 * @param currMap
	 */
	private void addCheck(Map<String, Object> currMap) {
		Integer isLeaf = (Integer) currMap.get("isLeaf");
		Integer roleId = (Integer) currMap.get("roleId");
		if(isLeaf != 1) {
			Boolean isCheckAll = true;
			Boolean isCheckFiew = true;
			Boolean isCheckNo = true;
			Iterator<String> itor = currMap.keySet().iterator();
			while(itor.hasNext()) {
				String key = itor.next();
				if(StringUtil.isNumber(key)) {
					Map<String, Object> childMap = (Map<String, Object>) currMap.get(key);
					addCheck(childMap);
					if(roleId != null) { //roleId有值，说明已经勾选
						continue;
					}
					if((Boolean) childMap.get("isCheckNo")) {
						isCheckAll = false;
					} 
					if((Boolean) childMap.get("isCheckFiew")) {
						isCheckFiew = true;
						isCheckAll = false;
						isCheckNo = false;
					} 
					if((Boolean) childMap.get("isCheckAll")) {
						isCheckNo = false;
					}
					if(!isCheckAll && !isCheckNo) {
						isCheckFiew = true;
					}
				}
			}
			if(roleId == null) { //role没有才需要做子节点判断
				if(isCheckAll || isCheckNo) {
					isCheckFiew = false;
				}
				currMap.put("isCheckAll", isCheckAll);
				currMap.put("isCheckFiew", isCheckFiew);
				currMap.put("isCheckNo", isCheckNo);
			}
			else {
				currMap.put("isCheckAll", true);
				currMap.put("isCheckFiew", false);
				currMap.put("isCheckNo", false);
			}
		}
		else {
			if(roleId != null) {
				currMap.put("isCheckAll", true);
				currMap.put("isCheckFiew", false);
				currMap.put("isCheckNo", false);
			}
			else {
				currMap.put("isCheckAll", false);
				currMap.put("isCheckFiew", false);
				currMap.put("isCheckNo", true);
			}
		}
	}
	
	@Override
	public List<Map> getClientCodeNotRels(Map<String, Object> condition) throws Exception {
		StringBuilder sqlStr = new StringBuilder("select distinct a.id,a.client_code clientCode,a.region from reliclient a ");
		sqlStr.append("where not exists (select b.id from reliroleclient b where b.client_id = a.id and b.role_id = :roleId) ");
		Map<String, Object> params = new HashMap<>();
		params.put("roleId", condition.get("roleId"));
		String clientCode = (String) condition.get("clientCode");
		if(StringUtils.isNotBlank(clientCode)) {
			sqlStr.append("and a.client_code like :clientCode ");
			params.put("clientCode", "%" + clientCode + "%");
		}
		String region = (String) condition.get("region");
		if(StringUtils.isNotBlank(region)) {
			sqlStr.append("and a.region = :region");
			params.put("region", region);
		}
		List<Map> noneClients = mapDao.executeSql(sqlStr.toString(), params);
		return noneClients;
	}
	
	@Override
	public List<Map> getClientCodeRels(Map<String, Object> condition) throws Exception {
		StringBuilder sqlStr = new StringBuilder("select distinct a.id,a.client_code clientCode,a.region from reliclient a ");
		sqlStr.append("inner join reliroleclient b on a.id = b.client_id where b.role_id = :roleId ");
		Map<String, Object> params = new HashMap<>();
		params.put("roleId", condition.get("roleId"));
		String clientCode = (String) condition.get("clientCode");
		if(StringUtils.isNotBlank(clientCode)) {
			sqlStr.append("and a.client_code like :clientCode ");
			params.put("clientCode", "%" + clientCode + "%");
		}
		String region = (String) condition.get("region");
		if(StringUtils.isNotBlank(region)) {
			sqlStr.append("and a.region = :region");
			params.put("region", region);
		}
		List<Map> clients = mapDao.executeSql(sqlStr.toString(), params);
		return clients;
	}
	
	@Override
	public List<Map> getRegions() throws Exception {
		String sql = "select distinct a.region from reliclient a";
		List<Map> regionList = mapDao.executeSql(sql);
		return regionList;
	}
	
	@Override
	public Map<String, Object> getRoleByLevel(Integer levelId) throws Exception {
		StringBuilder sql = new StringBuilder("select a.id,a.privilege_name privilegeName,a.privilege_name_en privilegeNameEN,a.is_leaf isLeaf, ");
		sql.append("c.role_id roleId,a.parent_id parentId from reliprivilege a ");
		sql.append("left join (select b.level_id role_id,b.privilege_id from relilevelprivilege b where b.level_id = :levelId) c on a.id = c.privilege_id ");
		sql.append("order by a.parent_id asc, a.order_index asc");
		Map<String, Object> params = new HashMap<>();
		params.put("levelId", levelId);
		List<Map> privileges = mapDao.executeSql(sql.toString(), params);
		Map<String, Object> priMap = compositeList(privileges);
		addCheck(priMap);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("privileges", priMap);
		return resultMap;
	}
}