package sy.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.ReliClient;
import sy.service.ReliClientServiceI;

@Service("reliClientService")
public class ReliClientServiceImpl implements ReliClientServiceI {

	private BaseDaoI<ReliClient> reliClientDao;
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<ReliClient> getReliClientDao() {
		return reliClientDao;
	}
	@Autowired
	public void setReliClientDao(BaseDaoI<ReliClient> reliClientDao) {
		this.reliClientDao = reliClientDao;
	}
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	
	@Override
	public ReliClient find(ReliClient reliClient) {
		Map<String,Object> param = new HashMap<String,Object>();
		if(reliClient!=null){
			String sql = "from ReliClient where id = :clientId";
			param.put("clientId", reliClient.getId());
			return reliClientDao.get(sql, param);
		}
		return null;
	}

	@Override
	public ReliClient save(ReliClient reliClient) {
		reliClientDao.save(reliClient);	
		return reliClient;
	}
	
	@Override
	public int add(ReliClient reliClient) {
		Map<String,Object> param = new HashMap<String,Object>();
		if(reliClient!=null){
			String sql = "insert into reliclient(client_code,region) values(:clientCode,:region)";
			param.put("clientCode", reliClient.getClientCode());
			param.put("region", reliClient.getRegion());
			return reliClientDao.executeSql2(sql, param);
		}else{
			return 0;
		}
	}

	@Override
	public int delete(ReliClient reliClient) {
		if(reliClient!=null&&StringUtils.isNotBlank(reliClient.getClientCode())){
			String sql = "delete from reliclient where id not in(select client_id from house) and client_code = :clientCode";
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("clientCode", reliClient.getClientCode());
			int result = reliClientDao.executeSql2(sql,param);
			if(result>0){
				this.deleteReliRoleClientByClientId(reliClient);
				return result;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}

	@Override
	public int update(ReliClient reliClient) {
		Map<String,Object> param = new HashMap<String,Object>();
		if(reliClient!=null){
			String sql = "update reliclient set region = :region,client_code = :clientCode where id = :id";
			param.put("region", reliClient.getRegion());
			param.put("clientCode", reliClient.getClientCode());
			param.put("id", reliClient.getId());
			return reliClientDao.executeSql2(sql, param);
		}else{
			return 0;
		}
	}

	@Override
	public List<Map> findReliClient(ReliClient reliClient) {
		Map<String,Object> param = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reliclient where 1=1");
		if(reliClient!=null){
			if(StringUtils.isNotBlank(reliClient.getClientCode())){
				sql.append(" and client_code like :clientCode");
				param.put("clientCode", "%"+reliClient.getClientCode()+"%");
			}else if(StringUtils.isNotBlank(reliClient.getRegion())){
				sql.append(" and region = :region");
				param.put("region", reliClient.getRegion());
			}
		}
		return mapDao.executeSql(sql.toString(), param);
	}
	
	@Override
	public List<Map> findList(ReliClient reliClient) {
		Map<String,Object> param = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reliclient");
		if(reliClient!=null){
			if(StringUtils.isNotBlank(reliClient.getClientCode())){
				sql.append(" where client_code = :clientCode");
				param.put("clientCode", reliClient.getClientCode());
			}
		}
		return mapDao.executeSql(sql.toString(), param);
	}
	
	//获取总记录条目数
	@Override
	public int getCount(ReliClient reliClient){
		Map<String,Object> params = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as count from reliclient where 1=1");
		if(StringUtils.isNotBlank(reliClient.getClientCode())){
			sql.append(" and client_code like :clientCode");
			params.put("clientCode", "%"+ reliClient.getClientCode()+"%");
		}
		if(StringUtils.isNotBlank(reliClient.getRegion())){
			sql.append(" and region like :region");
			params.put("region", "%"+reliClient.getRegion()+"%");
		}
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		if(t!=null){
			return ((BigInteger) t.get(0).get("count")).intValue();
		}
		return 0;
	}
	@Override
	public List<Map> getClientList(ReliClient reliClient, String startRow, String pageSize,String orderBy){
		Map<String,Object> param = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reliclient where 1=1");
		if(reliClient!=null){
			if(StringUtils.isNotBlank(reliClient.getClientCode())){
				sql.append(" and client_code like :clientCode");
				param.put("clientCode","%"+ reliClient.getClientCode()+"%");
			}
			if(StringUtils.isNotBlank(reliClient.getRegion())){
				sql.append(" and region = :region");
				param.put("region", reliClient.getRegion());
			}
		}
		if(StringUtils.isNotBlank(orderBy)){
			sql.append(" order by ").append(orderBy);
		}else{
			sql.append(" order by id desc");
		}
		sql.append(" limit ").append(startRow).append(",").append(pageSize);
		
		List<Map> clientList = mapDao.executeSql(sql.toString(),param);
		if(clientList!=null)
			return clientList;
		return null;
	}

	private int deleteReliRoleClientByClientId(ReliClient reliClient) {
		String sql = "delete from reliroleclient where client_id = :clientId";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("clientId", reliClient.getId());
		return reliClientDao.executeSql2(sql, params);
	}
}
