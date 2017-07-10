package sy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.House;
import sy.model.ModeFarmHouse;
import sy.service.ModeFarmHouseServiceI;

@Service("ModeFarmHouseService")
public class ModeFarmHouseServiceImpl implements ModeFarmHouseServiceI {

	@Autowired
	private BaseDaoI<ModeFarmHouse> modeFarmHouseDao;
	@Autowired
	private BaseDaoI<House> houseDao;
	@Override
	public int update(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
	     hql.append("UPDATE House SET note = '" + (String)map.get("note") + "' WHERE houseIEEE = '" + (String)map.get("houseIeee") + "'");
	     
	     modeFarmHouseDao.executeSql2(hql.toString());
	    
		return 0;
	}
	
	/**
	 * 获得一个ModeFarmHuose对象
	 * 时间 2017-6-3
	 * @author chengwangjian
	 * @param map
	 */
	
   public House get(Map<String, Object> map){
	   Map<String,Object> params = new HashMap<String, Object>();
	   String houseIEEE = (String) map.get("houseIEEE");
	   StringBuffer hql = new StringBuffer();
	   
	   hql.append("from House where houseIEEE=:houseIEEE");
	   params.put("houseIEEE",houseIEEE);
	   return houseDao.get(hql.toString(), params);
	  
   }
	
}
