package org.smarthome.services;

import java.util.List;
import java.util.Map;

import org.smarthome.dal.GenericDao;
import org.smarthome.domain.BfBugAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("BfBugAction")
public class BfBugActionServiceImpl implements BfBugActionServiceI {

	private GenericDao<BfBugAction, ?> dao;
    public GenericDao<BfBugAction, ?> getDao() {
		return dao;
	}
    @Autowired
	public void setDao(GenericDao<BfBugAction, ?> dao) {
		this.dao = dao;
	} 

	@Override
	public BfBugAction keyUpdate(BfBugAction data){
		dao.merge(data);
		return data;
		
		// TODO Auto-generated method stub		
	}
	
	@Override
	public BfBugAction saveOrUpdate(BfBugAction data) {
		dao.create(data);
		return data;
			
		// TODO Auto-generated method stub	
	}

	@Override
	public BfBugAction delete(BfBugAction data) {

		dao.delete(data);
		return data;
		// TODO Auto-generated method stub	
	}
	
	@Override
	public List<BfBugAction> findList(Map<String, Object> hard) {
		String hql="select t from BfBugInfo t where t.id=769";
		List<BfBugAction> dg=dao.find(hql);
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

}
