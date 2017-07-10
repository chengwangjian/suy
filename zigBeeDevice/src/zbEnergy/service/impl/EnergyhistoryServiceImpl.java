package zbEnergy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zbEnergy.model.Energy;
import zbEnergy.model.Energyhistory;
import zbEnergy.service.EnergyServiceI;
import sy.dao.BaseDaoI;
import zbHouse.model.Device;
import zbHouse.pageModel.General;
import zbHouse.util.TestLog4j;

@Service("historyService")
public class EnergyhistoryServiceImpl implements EnergyServiceI {

	private BaseDaoI<Energyhistory> energyhistoryDao;	

	public BaseDaoI<Energyhistory> getEnergyhistoryDao() {
		return energyhistoryDao;
	}
	@Autowired
	public void setEnergyhistoryDao(BaseDaoI<Energyhistory> energyhistoryDao) {
		this.energyhistoryDao = energyhistoryDao;
	}

	@Override
	public Device keyUpdate(Device device) {
			
		return device;
		// TODO Auto-generated method stub		
	}
	
	@Override
	public int saveOrUpdate(General js) {
		

		return 1;	
		// TODO Auto-generated method stub
		
	}

	@Override
	public int delete(General js) {
		
			
		return 1;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map find(General js) {
		

		Map<String, Object> rt = new HashMap<String, Object>();
		return rt;
		// TODO Auto-generated method stub		
	}
	private String addWhere(Energy ob1, Map<String, Object> params) {
		if (params!= null){
			if(ob1.getHouseIeee() != null){
				params.put("houseIeee", ob1.getHouseIeee());
			}
		}
		TestLog4j.testInfo(ob1.getHouseIeee());
		return null;
	}
	private String addWhere2(String hql, Map<String, Object> params) {
		if (params!= null){
			boolean first=true;
			hql += "where ";
			for (String key : params.keySet()) {
				if(first){  
					first=false;
					hql += "t." + key + " ='" + params.get(key)+ "'";
				}else{
					hql += " and t." + key + " ='" + params.get(key)+ "'";					
				}
			}			
		}
		TestLog4j.testInfo(hql);
		return hql;
	}	
}
