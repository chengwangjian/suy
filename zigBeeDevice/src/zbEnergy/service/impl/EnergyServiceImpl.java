package zbEnergy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zbEnergy.editor.Energy2Many;
import zbEnergy.model.Energy;
import zbEnergy.service.EnergyServiceI;
import zbHouse.model.Device;
import zbHouse.pageModel.General;
import zbHouse.util.TestLog4j;

@Service("energyService")
public class EnergyServiceImpl implements EnergyServiceI {

	private Energy2Many energyCheck;	

	public Energy2Many getEnergyCheck() {
		return energyCheck;
	}
	@Autowired
	public void setEnergyCheck(Energy2Many energyCheck) {
		this.energyCheck = energyCheck;
	}

	@Override
	public Device keyUpdate(Device device) {
			
		return device;
		// TODO Auto-generated method stub		
	}
	
	@Override
	public int saveOrUpdate(General js) {
		
		this.delete(js);
		energyCheck.energyUpdateCheck(js);
		return 1;	
		// TODO Auto-generated method stub
		
	}

	@Override
	public int delete(General js) {
		
		Energy ob1=js.getEnergy();
		Map<String, Object> params = new HashMap<String, Object>();
		addWhere(ob1,params);
		energyCheck.energyDeleteCheck(params);				
		return 1;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map find(General js) {
		
		Energy ob1=js.getEnergy();
		Map<String, Object> params = new HashMap<String, Object>();
		addWhere(ob1,params);
		Map<String, Object> rt = new HashMap<String, Object>();
		rt=energyCheck.find(params);
		return rt;
		// TODO Auto-generated method stub		
	}
	private String addWhere(Energy ob1, Map<String, Object> params) {
		if (params!= null){
			if(ob1.getHouseIeee() != null){
				params.put("houseIeee", ob1.getHouseIeee());
			}
			if(ob1.getPriceType() != null){
				params.put("priceType", ob1.getPriceType());
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
