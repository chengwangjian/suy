package zbEnergy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zbEnergy.editor.Energy2Many;
import zbEnergy.editor.Price2Many;
import zbEnergy.model.Energy;
import zbEnergy.model.Energyfield;
import zbEnergy.model.Energytime;
import zbEnergy.model.Houseparam;
import zbEnergy.model.Priceparam;
import zbEnergy.service.GeneralServiceI;
import zbHouse.model.Device;
import zbHouse.pageModel.General;
import zbHouse.util.TestLog4j;

@Service("generalService")
public class GeneralServiceImpl implements GeneralServiceI {

	private Energy2Many energyCheck;
	private Price2Many priceCheck;	

	public Energy2Many getEnergyCheck() {
		return energyCheck;
	}
	@Autowired
	public void setEnergyCheck(Energy2Many energyCheck) {
		this.energyCheck = energyCheck;
	}

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
					
		if(this.delete(js)!=0){
			energyCheck.energyUpdateCheck(js);
			priceCheck.priceUpdateCheck(js);
			TestLog4j.testInfo("UPDATE ENERGY,"+js.getEnergy().getHouseIeee());
			return 1;
		}else{
			return 0;			
		}
		// TODO Auto-generated method stub		
	}

	@Override
	public int delete(General js) {
						
		Map<String, Object> params = new HashMap<String, Object>();
		String hql=null;
		if(addWhere2(hql,js).equals("success")){
			addWhere(js.getEnergy(),params);	
			energyCheck.energyDeleteCheck(params);
			priceCheck.priceDeleteCheck(params);
			TestLog4j.testInfo("DELETE ENERGY,"+js.getEnergy().getHouseIeee());
			return 1;
		}else{
			return 0;
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map find(General js) {

		Energy ob1=js.getEnergy();
		Map<String, Object> params = new HashMap<String, Object>();
		addWhere(ob1,params);	
		Map<String, Object> rt = new HashMap<String, Object>();
		rt.putAll(energyCheck.find(params));
		rt.putAll(priceCheck.find(params));
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
	private String addWhere2(String hql,General js) {
		Energy ob1=js.getEnergy();
		Houseparam ob2=js.getHouse();
		if(ob1==null ||ob1.getHouseIeee()==null ||ob1.getHouseIeee().equals("")){
			hql= "EnergyNULL";
			return hql;			
		}
		if(ob2==null ||ob2.getHouseIeee()==null ){
			//hql+= "HouseparamNULL";				
		}else{
			ob2.setHouseIeee(ob1.getHouseIeee());
		}
		List<Priceparam> lt1=js.getPrice();	
		List<Energyfield> lt2=js.getEnergyfield();
		List<Energytime> lt3=js.getEnergytime();
		if(lt1==null ||lt1.size() == 0 ){
			//hql+= "PriceparamNULL";		
		}else{
			for(Priceparam ob3:lt1){
				ob3.setHouseIeee(ob1.getHouseIeee());
			}
		}
		if(lt2==null ||lt2.size() == 0 ){
			//hql+= "EnergyfieldNULL";			
		}else{
			for(Energyfield ob4:lt2){
				ob4.setHouseIeee(ob1.getHouseIeee());
			}						
		}
		if(lt3==null ||lt3.size() == 0 ){
			//hql+= "EnergytimeNULL";						
		}else{
			for(Energytime ob5:lt3){
				ob5.setHouseIeee(ob1.getHouseIeee());
			}
		}
		hql="success";
		TestLog4j.testInfo(hql);
		return hql;
	}	
}
