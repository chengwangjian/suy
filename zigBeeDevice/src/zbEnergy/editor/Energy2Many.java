package zbEnergy.editor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zbEnergy.model.Energy;
import zbEnergy.model.Houseparam;
import sy.dao.BaseDaoI;
import zbHouse.pageModel.General;
import zbHouse.util.TestLog4j;

@Service("energyCheck")
public class Energy2Many {

	private BaseDaoI<Energy> energyDao;
	private BaseDaoI<Houseparam> houseparamDao;
		
	public BaseDaoI<Energy> getEnergyDao() {
		return energyDao;
	}
	@Autowired
	public void setEnergyDao(BaseDaoI<Energy> energyDao) {
		this.energyDao = energyDao;
	}

	public BaseDaoI<Houseparam> getHouseparamDao() {
		return houseparamDao;
	}
	@Autowired	
	public void setHouseparamDao(BaseDaoI<Houseparam> houseparamDao) {
		this.houseparamDao = houseparamDao;
	}
	
	public void energyUpdateCheck(General js){
		
		Energy ob1=js.getEnergy();
		energyDao.saveOrUpdate(ob1);
		Houseparam ob2=js.getHouse();
		houseparamDao.saveOrUpdate(ob2);
	}
	
	public void energyDeleteCheck(Map<String, Object> params){
		
		String hql1 = "delete Houseparam t ";
		hql1 = addWhere(hql1,1, params);		
		houseparamDao.executeHql(hql1,params);
		String hql2 = "delete Energy t ";
		hql2 = addWhere(hql2,2, params);
		energyDao.executeHql(hql2,params);

	}
	
	public Map find(Map<String, Object> params) {
		
		Map<String, Object> rt = new HashMap<String, Object>();
		
		String hql = "from Houseparam t ";
		hql = addWhere(hql,1, params);
		String totalHql = "select count(*) " + hql;
		
		if(houseparamDao.find(hql,params).size()!= 0)
			rt.put("house",houseparamDao.find(hql,params).get(0));
		else
			rt.put("house",null);
		
		hql = "from Energy t ";
		hql = addWhere(hql,2, params);
		totalHql = "select count(*) " + hql;	
		
		if(energyDao.find(hql,params).size()!= 0)
			rt.put("energy",energyDao.find(hql,params).get(0));		
		else
			rt.put("energy",null);			
		return rt;
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql,int flag, Map<String, Object> params) {
		if (params!= null){
			for (String key : params.keySet()){
				if(key.equalsIgnoreCase("houseIeee")){				
					hql += "where t.houseIeee =:houseIeee ";
				}			
			}
		}
		TestLog4j.testInfo(hql);
		return hql;
	}	
	
}
