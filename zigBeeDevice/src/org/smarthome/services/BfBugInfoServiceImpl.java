package org.smarthome.services;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smarthome.dal.GenericDao;
import org.smarthome.domain.BfBugInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Conditionmain;
import sy.model.Conditionsub;
import sy.model.QueryParameter;


@Service("BfBugInfo")
public class BfBugInfoServiceImpl implements BfBugInfoServiceI {

	private GenericDao<BfBugInfo, ?> dao;
	
	private BaseDaoI<Map> mapDao;
	
   private BaseDaoI<Conditionmain> conditionmainDao;     
	
   public BaseDaoI<Conditionsub> getConditionsubDao() {
	return conditionsubDao;
}
   @Autowired
   public void setConditionsubDao(BaseDaoI<Conditionsub> conditionsubDao) {
	this.conditionsubDao = conditionsubDao;
}
   private BaseDaoI<Conditionsub> conditionsubDao;
   
	public BaseDaoI<Conditionmain> getConditionmainDao() {
	return conditionmainDao;
}
	@Autowired
  public void setConditionmainDao(BaseDaoI<Conditionmain> conditionmainDao) {
	this.conditionmainDao = conditionmainDao;
}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
    public GenericDao<BfBugInfo, ?> getDao() {
		return dao;
	}
    @Autowired
	public void setDao(GenericDao<BfBugInfo, ?> dao) {
		this.dao = dao;
	} 

	@Override
	public BfBugInfo keyUpdate(BfBugInfo data){
		dao.merge(data);
		return data;
		
		// TODO Auto-generated method stub		
	}
	
	@Override
	public BfBugInfo saveOrUpdate(BfBugInfo data) {
		dao.create(data);
		return data;
			
		// TODO Auto-generated method stub	
	}

	@Override
	public BfBugInfo delete(BfBugInfo data) {

		dao.delete(data);
		return data;
		// TODO Auto-generated method stub	
	}
	
	@Override
	public List<BfBugInfo> findList(Map<String, Object> hard) {
		String hql="select t from BfBugInfo t where t.id=769";
		List<BfBugInfo> dg=dao.find(hql);
		return dg;
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql,Map<String, Object> hard,Map<String, Object> params) {
		if (hard!= null){
			params.put("daemonDeviceId", hard.get("type"));
			hql += "where t.daemonDeviceId =:daemonDeviceId";
		}
		return hql;
	}
	@Override
	public void addCondition(QueryParameter parameter,String userid) {
		HashMap< String,Object> params=new HashMap<String, Object>();
	    int id=parameter.getId();
	    if(id!=0){
	    	this.mapDao.executeSql2("DELETE from conditionmain WHERE id="+id);
	    	this.mapDao.executeSql2("DELETE FROM conditionsub WHERE mid="+id);
	    }
		params.put("name", parameter.getName());
		Conditionmain  main=new Conditionmain();
		main.setUserid(Integer.parseInt(userid));
		main.setName(parameter.getName());
		this.conditionmainDao.save(main);
		int mid=main.getId();
		List<Conditionsub> subs= parameter.getCondition();
		//this.mapDao.executeSql2("INSERT INTO conditionmain(name) VALUES(:name)", params);
		 for(int i=0;i<subs.size();i++){
			 subs.get(i).setMid(mid);
			 this.conditionsubDao.save(subs.get(i));
			 
		 }
	 
	
	}
	@Override
	public List<Map> findCondition(int userid) {
	  List<Map> list=this.mapDao.executeSql("select * from conditionmain where userid="+userid);
		return list;
	}
	@Override
	public void updateConditionmain(Map map) {
	HashMap<String,Object> params=new HashMap<String, Object>();
	List<Conditionmain> list=this.conditionmainDao.find("from Conditionmain where id="+map.get("id"));
	 Conditionmain conditionmain=list.get(0);
	 conditionmain.setName((String) map.get("name"));
	 this.conditionmainDao.update(conditionmain);
	}
	@Override
	public void deleteCondition(Map map) {
	  HashMap<String,Object> params=new HashMap<String, Object>();
	 this.mapDao.executeSql2("DELETE from conditionmain WHERE id="+map.get("id"));
     this.mapDao.executeSql2("DELETE from conditionsub WHERE mid="+map.get("id"));		
		
	}
	@Override
	public List<Map> findConditionSub(Map map) {
		
		 HashMap<String,Object> params=new HashMap<String, Object>();
		 params.put("mid",map.get("id")); 
		 List<Map> list=this.mapDao.executeSql("SELECT * from conditionsub WHERE mid=:mid", params);
		 return list;
	}
	@Override
	public List<Map> findappinfo(Map map) {
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("id",map.get("id")); 
		String sql="SELECT id,houseIEEE,device_type,sys_ver,opdatetime,file_name,user_name,error_no,oderfilename,version_sdk,proxy_server,version_release,cpu,package as mypackage,model,brand,resolution,memory,app_version,server_ip,file_size FROM file_appinfo_"+Calendar.getInstance().get(Calendar.YEAR)+" where id=:id";
		List<Map> list=this.mapDao.executeSql(sql, params);
		if(list.isEmpty()||list.size()<0||list==null){
		 return null;	
		}
		return list;
	}

}
