package zbEnergy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zbEnergy.editor.Price2Many;
import zbEnergy.model.Energy;
import zbEnergy.service.PriceServiceI;
import zbHouse.model.Device;
import zbHouse.pageModel.General;
import zbHouse.util.TestLog4j;

@Service("priceService")
public class PriceServiceImpl implements PriceServiceI {

	private Price2Many priceCheck;	

	public Price2Many getPriceCheck() {
		return priceCheck;
	}
	@Autowired
	public void setPriceCheck(Price2Many priceCheck) {
		this.priceCheck = priceCheck;
	}

	@Override
	public Device keyUpdate(Device device) {
			
		return device;
		// TODO Auto-generated method stub		
	}
	
	@Override
	public int saveOrUpdate(General js) {
			
		this.delete(js);
		priceCheck.priceUpdateCheck(js);		
		return 1;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int delete(General js) {
				
		Energy ob1=js.getEnergy();
		Map<String, Object> params = new HashMap<String, Object>();
		addWhere(ob1,params);
		priceCheck.priceDeleteCheck(params);		
		return 1;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map find(General js) {
	
		Energy ob1=js.getEnergy();
		Map<String, Object> params = new HashMap<String, Object>();
		addWhere(ob1,params);
		Map<String, Object> rt = new HashMap<String, Object>();
		rt=priceCheck.find(params);
		return rt;
		// TODO Auto-generated method stub		
	}
	private String addWhere(Energy ob1,Map<String, Object> params) {
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
