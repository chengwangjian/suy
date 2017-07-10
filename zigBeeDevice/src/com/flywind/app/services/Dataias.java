package com.flywind.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlias;
import com.flywind.app.entities.Modeiasmain;
import com.flywind.app.entities.Modeiassub;
import com.flywind.app.entities.Usermodesub;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dias")
public class Dataias
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Dataias.class);

	private StartDAO dao;
    public StartDAO getDao() {
		return dao;
	}
    @Autowired
	public void setDao(StartDAO dao) {
		this.dao = dao;
	} 

    public void initialize()
    {
        
        List<Usermodesub> modes = new ArrayList<Usermodesub>();
        List<Modeiasmain> iass = new ArrayList<Modeiasmain>();
        List<Modeiassub> iassubs = new ArrayList<Modeiassub>();
//        modes.add(new Moderoom(111, "111", "111"));
//        modes.add(new Moderoom(222, "222", "222"));
//        iass.add(new Modeiasmain(1, 222, (short)1));
//        iass.add(new Modeiasmain(2, 333, (short)0));
//        iassubs.add(new Modeiassub(1,(short)1, (short)1,"zzz", "xxx"));
//        iassubs.add(new Modeiassub(2,(short)0, (short)0,"yyy", "xxx")); 
        
        LOGGER.info("-- Loading initial demo data");
//        rooms=dao.findWithNamedQuery(Moderoom.BY_houseId,
//				QueryParameters.with("houseId", 111L).parameters());
//        delete(rooms);
        
//      create(modes);
//      create(iass);   
//      create(iassubs);      
        LOGGER.info("Users " + iass.toString());
        LOGGER.info("Users " + iassubs.toString());
        LOGGER.info("-- Data Loaded. Exit");
        
    }

    public Xmlias xmlias=new Xmlias();
    public Xmlias create(List<?> main,List<?> sub,Target tar)
    {
        List iass = new ArrayList();
        List iassubs = new ArrayList();
//        Modemainclause m1 =new Modemainclause(); 
    	for (Object e : main)
        { 		
    		Modeiasmain e1=(Modeiasmain)e;
    		Modeiasmain e2=dao.get("Select m from Modeiasmain m where m.modeEpId=:epid and m.attrlibId=:libid", 
    				QueryParameters.with("epid", e1.getModeEpId()).and("libid", e1.getAttrlibId()).parameters());
    		LOGGER.info("test-----"+e2);     		
    		if(e2!=null)
    			xmlias.setChecked(1);
    		else{	
//    			for( int i= 1;i<=1;i++ ){
//	    			  m1.setBactive(e1.getBactive());
//	    			  m1.setBallmodeActive(e1.getBallmodeActive());
//	    		      dao.create(m1);
//	    		}
//    			e1.setMainId(m1.getId());
    			iass.add(dao.create(e1));
    			xmlias.setChecked(0);
    		}
        }
		for(Object u :sub){
			Modeiassub u1=(Modeiassub)u;		
			if(tar.getType()==00L){
				u1.setCiestatus((short) 0);u1.setZoneActType((short) 0);
				}
			else if(tar.getType()==02L){
				u1.setCiestatus((short) 0);u1.setZoneActType((short) 1);
				}
			else if(tar.getType()==20L){
				u1.setCiestatus((short) 1);u1.setZoneActType((short) 0);
				}
			else if(tar.getType()==22L){
				u1.setCiestatus((short) 1);u1.setZoneActType((short) 1);
				}		
			//单个添加
			if(tar.getBehavior()<=1)u1.setDest(tar.getDest());
			iassubs.add(dao.create(u1));  
		}
       	
    	xmlias.setMain(iass);
    	xmlias.setSub(iassubs);  
        return xmlias;
    }
    public Xmlias update(List<?> main,List<?> sub,Target tar)
    {
        List iass = new ArrayList();
        List iassubs = new ArrayList();
    	for (Object e : main)
        {
    		iass.add(dao.update(e));
        }
        for (Object u : sub)
        {
        	Modeiassub u1=(Modeiassub)u;
        	Modeiassub u2=dao.get(Modeiassub.class, u1.getId());
        	BeanUtils.copyProperties(u1,u2,new String[]{"id","dest"});
        	iassubs.add(dao.update(u2));
        }
    	xmlias.setMain(iass);
    	xmlias.setSub(iassubs);  
        return xmlias;        
    }
    public void delete(List<?> main,List<?> sub) {  
    	for (Object e : main)
        {
    		Modeiasmain e1=(Modeiasmain)e;
    		e1=dao.get(Modeiasmain.class, e1.getId());
//    		dao.delete(e1);
    		deletecheck(e1); 
        }                
    	for (Object u : sub)
        {
    		Modeiassub u1 = (Modeiassub)u;
    		u1=dao.get(Modeiassub.class, u1.getId());
    		dao.delete(u1);
//    		deletecheck(u1);    		
        }
    	LOGGER.info("2222 " + sub.size());
    }
    
    public Object findmacro(Target tar){
    	List<Modeiassub> subsList = null;
    	List<Modeiasmain> iasList = null;
    	List<Usermodesub> modeList = null;

    	if(tar.getBehavior()==1){//按house选    		
	    	subsList = dao.executeSql("{CALL ModeiashvacProc(1 ,:houseId,:id,:mid)}",
					QueryParameters.with("houseId", tar.getHouseId()).and("id", tar.getId()).and("mid", tar.getMid()).parameters());
	    	iasList = dao.executeSql("{CALL ModeiashvacProc(2 ,:houseId,:id,:mid)}",
					QueryParameters.with("houseId", tar.getHouseId()).and("id", tar.getId()).and("mid", tar.getMid()).parameters());    		
    	}
    	else if(tar.getBehavior()==2){
	    	subsList = dao.executeSql("{CALL ModeiashvacProc(5 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
	    	iasList = dao.executeSql("{CALL ModeiashvacProc(6 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());    		
    	}
    	else if(tar.getBehavior()==3){//按main选    
	    	subsList = dao.executeSql("{CALL ModeiashvacProc(9 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
	    	iasList = dao.executeSql("{CALL ModeiashvacProc(10 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
    		modeList= dao.executeSql("{CALL ModeiashvacProc(13 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());	    	
    	}

    	Map<String, Object> iasMap = new HashMap<String, Object>();
    	if(tar.getBehavior()==3){
    		iasMap.put("sub", subsList);
    		iasMap.put("main", iasList);
    		iasMap.put("mode", modeList);    		
    	}else{
    		iasMap.put("sub", subsList);
    		iasMap.put("main", iasList);
    	}
  	    LOGGER.info("1111 " + iasMap);    	
    	return iasMap;   	
    }
    public int deletecheck(Modeiasmain u1){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("houseId", u1.getHouseId());    	
    	params.put("id", u1.getId());
    	int count=0;
    	count=dao.executeHql("{CALL ModedeleteTest(7,:houseId,:id)}",params); 
    	return 0; 
    }   
    public int updatecheck(Modeiasmain e1){		
    	return 0;
    }

    
}
