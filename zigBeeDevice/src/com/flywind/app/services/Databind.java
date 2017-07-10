package com.flywind.app.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Xmldevicebind;
import com.flywind.app.entities.Modedevicebind;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dbind")
public class Databind
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Databind.class);

	private StartDAO dao;
    public StartDAO getDao() {
		return dao;
	}
    @Autowired
	public void setDao(StartDAO dao) {
		this.dao = dao;
	} 

    public Xmldevicebind xmlbind;
    public void initialize()
    {
        
        List<Modedevicebind> acts = new ArrayList<Modedevicebind>();
//        rooms.add(new Moderoom(111, "111", "111"));
//        rooms.add(new Moderoom(222, "222", "222"));
//        modes.add(new Usermodemain(17, "aaa", "aaa", "aaa"));
//        modes.add(new Usermodemain(18, "bbb", "bbb", "bbb"));
//        modesubs.add(new Usermodesub(1, "zzz", 111));
//        modesubs.add(new Usermodesub(2, "yyy", 222)); 
 
        
        LOGGER.info("-- Loading initial demo data");
//        rooms=dao.findWithNamedQuery(Moderoom.BY_houseId,
//				QueryParameters.with("houseId", 111L).parameters());
//        delete(rooms);
        
//      create(rooms);
//      create(modes);   
//      create(modesubs);
        LOGGER.info("Users " + acts.toString());
        LOGGER.info("Users " );
        LOGGER.info("-- Data Loaded. Exit");
        
    }

    public void create(List<?> entities)
    {
        for (Object e : entities)
        {
            dao.create(e);
        }
    }
    public void update(List<?> entities)
    {
        for (Object e : entities)
        {
            dao.update(e);
        }
        LOGGER.info("3333 " + entities.size());
    }
    public void delete(List<?> entities) {  
    	for (Object e : entities)
        {
    		dao.delete(e);
        }                
    	LOGGER.info("2222 " + entities.size());
    }
    public Xmldevicebind findhouse(long houseId){
    
//    	List<Modedevicebind> bindList = dao.find(NamedQueries.Modedevicebind_houseId,
//				QueryParameters.with("houseId",houseId).parameters());
//    	xmlbind=new Xmldevicebind();
//    	xmlbind.setMain(bindList);
        LOGGER.info("1111 " + xmlbind);
    	return xmlbind;
    }
    
}
