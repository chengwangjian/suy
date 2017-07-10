package org.smarthome.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smarthome.dal.StartDAO;
import org.smarthome.domain.Updatehistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zbHouse.util.TestLog4j;

@Service("Updatehistory")
public class UpdatehistoryServiceImpl implements org.smarthome.services.UpdatehistoryServiceI {

	private StartDAO dao;
    public StartDAO getDao() {
		return dao;
	}
    @Autowired
	public void setDao(StartDAO dao) {
		this.dao = dao;
	} 

	@Override
	public Updatehistory keyUpdate(Updatehistory data) {
			
		return data;
		// TODO Auto-generated method stub		
	}
	
	@Override
	public Updatehistory saveOrUpdate(Updatehistory data) {

		return dao.saveOrUpdate(data);	
		// TODO Auto-generated method stub	
	}

	@Override
	public Updatehistory delete(Updatehistory data) {

		dao.delete(data);
		return data;
		// TODO Auto-generated method stub	
	}

	@Override
	public List<Updatehistory> find(Updatehistory warn) {
		Map<String, Object> params = new HashMap<String, Object>();
		//String hql = "from Device t where t.houseIeee='11' ";
		String hql = "from Sdkwarnhistory t ";
		hql = addWhere(hql,warn, params);
		List<Updatehistory> dg=dao.find(hql);
		return dg;
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql,Updatehistory warn,Map<String, Object> params) {
		if (warn!= null){
			params.put("daemonDeviceId", warn.getId());
			hql += "where t.daemonDeviceId =:daemonDeviceId";
		}
		TestLog4j.testInfo("warn:");
		return hql;
	}

}
