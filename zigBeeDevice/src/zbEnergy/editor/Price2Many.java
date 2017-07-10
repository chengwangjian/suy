package zbEnergy.editor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zbEnergy.model.Energy;
import zbEnergy.model.Energyfield;
import zbEnergy.model.Energytime;
import zbEnergy.model.Priceparam;
import sy.dao.BaseDaoI;
import zbHouse.pageModel.General;
import zbHouse.util.TestLog4j;

@Service("priceCheck")
public class Price2Many {

	private BaseDaoI<Priceparam> priceparamDao;
	private BaseDaoI<Energyfield> energyfieldDao;
	private BaseDaoI<Energytime> energytimeDao;
	private BaseDaoI<Energy> energyDao;
	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	public BaseDaoI<Energy> getEnergyDao() {
		return energyDao;
	}
	@Autowired
	public void setEnergyDao(BaseDaoI<Energy> energyDao) {
		this.energyDao = energyDao;
	}

	public BaseDaoI<Priceparam> getPriceparamDao() {
		return priceparamDao;
	}
	@Autowired
	public void setPriceparamDao(BaseDaoI<Priceparam> priceparamDao) {
		this.priceparamDao = priceparamDao;
	}
	public BaseDaoI<Energyfield> getEnergyfieldDao() {
		return energyfieldDao;
	}
	@Autowired
	public void setEnergyfieldDao(BaseDaoI<Energyfield> energyfieldDao) {
		this.energyfieldDao = energyfieldDao;
	}
	public BaseDaoI<Energytime> getEnergytimeDao() {
		return energytimeDao;
	}
	@Autowired
	public void setEnergytimeDao(BaseDaoI<Energytime> energytimeDao) {
		this.energytimeDao = energytimeDao;
	}
	
	
	public void priceUpdateCheck(General js){
		//Energy已经更新
		Energy ob=js.getEnergy();
		if(ob!=null &&ob.getId()!=0){
			List<Priceparam> lt1=js.getPrice();	
			for(Priceparam ob1:lt1){
				if(ob.getDstflag()){
					Date date=new Date();				
					if(ob.getDstendTime().getTime()>date.getTime()&&date.getTime()>ob.getDstbeginTime().getTime()){
					double dst=	ob1.getPriceDst();
					ob1.setPriceDst(ob1.getPrice());
					ob1.setPrice(dst);
					}
					}
				ob1.setEnergyId(ob.getId());
				priceparamDao.saveOrUpdate(ob1);
			}
			List<Energyfield> lt2=js.getEnergyfield();
			if (lt2 != null && lt2.size() > 0) {
				for(Energyfield ob2:lt2){
					ob2.setEnergyId(ob.getId());
					energyfieldDao.saveOrUpdate(ob2);
				}
			}
			List<Energytime> lt3=js.getEnergytime();
			if (lt3 != null && lt3.size() > 0) {
				for(Energytime ob3:lt3){
					ob3.setEnergyId(ob.getId());
					energytimeDao.saveOrUpdate(ob3);
				}
			}
		}
		TestLog4j.testInfo("EnergyId="+ob.getId());
	}	
	
	public void priceDeleteCheck(Map<String, Object> params){
		
		String hql1 = "delete Priceparam t ";
		hql1 = addWhere(hql1, params);
		priceparamDao.executeHql(hql1,params);
		String hql2 = "delete Energyfield t ";
		hql2 = addWhere(hql2, params);
		energyfieldDao.executeHql(hql2,params);
		String hql3 = "delete Energytime t ";
		hql3 = addWhere(hql3, params);
		energytimeDao.executeHql(hql3,params);
	}
	
	public Map find(Map<String, Object> params) {
		List<Priceparam> priceList=null ;
		String hql = "from Priceparam t ";
		hql = addWhere(hql, params);
		String totalHql = "select count(*) " + hql;	
		
		Map<String, Object> rt = new HashMap<String, Object>();
		if(priceparamDao.find(hql,params).size()!= 0){
			
			 priceList=priceparamDao.find(hql,params);
			 }
		
		hql = "from Energy t ";
		hql = addWhere(hql, params);	
	 if(energyDao.find(hql,params).size()!= 0){
			Energy tEnergy=	energyDao.find(hql,params).get(0);
			if(tEnergy.getDstflag()){
			Date date=new Date();
			if(tEnergy.getDstendTime().getTime()>date.getTime()&&date.getTime()>tEnergy.getDstbeginTime().getTime()){
				for(int i=0;i<priceList.size();i++){
				double price=priceList.get(i).getPrice();
			    priceList.get(i).setPrice(priceList.get(i).getPriceDst());	
			    priceList.get(i).setPriceDst(price);
				}
				}
			}
			rt.put("price",priceList);	
	 }
		hql = "from Energyfield t ";
		hql = addWhere(hql, params);
		totalHql = "select count(*) " + hql;
		
		if(energyfieldDao.find(hql,params).size()!= 0)
			rt.put("energyfield",energyfieldDao.find(hql,params));
		else
			rt.put("energyfield",null);


		hql = "from Energytime t ";
		hql = addWhere(hql, params);
		totalHql = "select count(*) " + hql;
		
		if(energytimeDao.find(hql,params).size()!= 0)
			rt.put("energytime",energytimeDao.find(hql,params));
		else
			rt.put("energytime",null);
		
		return rt;
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql, Map<String, Object> params) {
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
