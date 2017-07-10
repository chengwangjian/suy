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

import sy.dao.BaseDaoI;

import com.alibaba.fastjson.JSON;
import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlhvac;
import com.flywind.app.entities.Deviceattrlib;
import com.flywind.app.entities.Modehvacmain;
import com.flywind.app.entities.Modehvacsub;
import com.flywind.app.entities.Modeiasmain;
import com.flywind.app.entities.Modeiassub;
import com.flywind.app.entities.Modemainclause;
import com.flywind.app.entities.Usermodesub;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dhvac")
public class Datahvac
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Datahvac.class);

	private StartDAO dao;
	private BaseDaoI<Map> mapDao;
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

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
        List<Modehvacmain> hvacs = new ArrayList<Modehvacmain>();
        List<Modehvacsub> hvacsubs = new ArrayList<Modehvacsub>();
//        modes.add(new Moderoom(111, "111", "111"));
//        modes.add(new Moderoom(222, "222", "222"));
//        hvacs.add(new Modehvacmain(0, 0, 0, 0,0,0,(short)1));
//        hvacs.add(new Modehvacmain(0, 0, 0, 0,0,0,(short)0));
//        hvacsubs.add(new Modehvacsub(1, 1, "yyy",111,111,"xxx",0));
//        hvacsubs.add(new Modehvacsub(2, 0, "zzz",222,222,"xxx",0)); 
        
        LOGGER.info("-- Loading initial demo data");
//        rooms=dao.findWithNamedQuery(Moderoom.BY_houseId,
//				QueryParameters.with("houseId", 111L).parameters());
//        delete(rooms);
        
//        create(modes);
//        create(hvacs);   
//        create(hvacsubs);      
        LOGGER.info("Users " + hvacs.toString());
        LOGGER.info("Users " + hvacsubs.toString());
        LOGGER.info("-- Data Loaded. Exit");
        
    }

    public Xmlhvac xmlhvac =new Xmlhvac();    
    public Xmlhvac create(List<?> main,List<?> sub,Target tar)
    {
        List hvacs = new ArrayList();
        List hvacsubs = new ArrayList();
        Modemainclause m1 =new Modemainclause(); 
    	for (Object e : main)
        {
    		Modehvacmain e1=(Modehvacmain)e;
    		Modehvacmain e2=dao.get("Select m from Modehvacmain m where m.modeEpId=:epid and m.attrlibId=:libid ", 
    				QueryParameters.with("epid", e1.getModeEpId()).and("libid", e1.getAttrlibId()).parameters());
    		LOGGER.info("test-----"+e2);     		
    		if(e2!=null)
//    		if(false)
    			xmlhvac.setChecked(1);
    		else{
    			for( int i= 1;i<=1;i++ ){
	    			  m1.setBactive(e1.getBactive());
	    			  m1.setBallmodeActive(e1.getBallmodeActive());
	    			  m1.setHouseId(e1.getHouseId());
	    		      dao.create(m1);
	    		}
	    		Deviceattrlib lib=dao.get("Select m from Deviceattrlib m where m.id=:libId",
	    				QueryParameters.with("libId", e1.getAttrlibId()).parameters());
	    		e1.setAttrId(lib.getAttrId());
	    		e1.setClusterId(lib.getClusterId()); 
	    		e1.setMainId(m1.getId());
	    		hvacs.add(dao.create(e1));
	    		xmlhvac.setChecked(0);
	    		
    		}
    	
        }
		for(Object u :sub){
			Modehvacsub u1=(Modehvacsub)u;
//        	Modehvacsub u2=dao.get("Select u from Modehvacsub u where u.mid=:mid and u.actType=:actType", 
//        			QueryParameters.with("mid", u1.getMid()).and("actType", tar.getType()).parameters());
//        	LOGGER.info("test-----"+u2);        	 			
			u1.setActType(tar.getType());
			//单个添加
			if(tar.getBehavior()<=1)u1.setDest(tar.getDest()); 
			hvacsubs.add(dao.create(u1));
    	}    		
    	xmlhvac.setMain(hvacs);
    	xmlhvac.setSub(hvacsubs);
		LOGGER.info("tar-----"+JSON.toJSONString(xmlhvac));	    	
        return xmlhvac;
    }
    public Xmlhvac update(List<?> main,List<?> sub,Target tar)
    {
        List hvacs = new ArrayList();
        List hvacsubs = new ArrayList();
    	for (Object e : main)
        {  
    		Modehvacmain e1=(Modehvacmain)e;
    		Deviceattrlib lib=dao.get("Select m from Deviceattrlib m where m.id=:libId",
    				QueryParameters.with("libId", e1.getAttrlibId()).parameters());
    		e1.setAttrId(lib.getAttrId());
    		e1.setClusterId(lib.getClusterId());  
    		if(e1.getId()==0){
    			hvacs.add(dao.create(e1));
    		} 
    		else {
    			hvacs.add(dao.update(e1));
    		}
        }
        for (Object u : sub)
        {
        	Modehvacsub u1=(Modehvacsub)u;
        	Modehvacsub u2=dao.get(Modehvacsub.class, u1.getId());
        	BeanUtils.copyProperties(u1,u2,new String[]{"id","dest"});        	
			u2.setActType(tar.getType()); 
			//单个添加
			if(tar.getBehavior()<=1)u2.setResumeDest(tar.getDest());  
        	hvacsubs.add(dao.update(u2));
        }
    	xmlhvac.setMain(hvacs);
    	xmlhvac.setSub(hvacsubs);  
        return xmlhvac;       
    }
    public void delete(List<?> main,List<?> sub) {  
    	for (Object e : main)
        {
    		Modehvacmain e1=(Modehvacmain)e;
    		Map<String, Object> paramMap = new HashMap<String , Object>();
    		paramMap.put("mainId",e1.getId());
    		String hql = "from Modehvacmain m where m.mainId=:mainId";
    		List<Modehvacmain> e2 =dao.find(hql, paramMap);
//    		e1=dao.get(Modehvacmain.class, e1.getId());
//    		dao.delete(e1);
    		deletecheck(e2.get(0));    		
        }                
    	for (Object u : sub)
        {
    		Modehvacsub u1 = (Modehvacsub)u;
    		u1=dao.get(Modehvacsub.class, u1.getId());
    		dao.delete(u1);
//    		deletecheck(u1);
        }
    	LOGGER.info("2222 " + sub.size());               
    }
    public Object findmacro(Target tar){   	
    	List<Modehvacsub> subsList = null;
    	List<Modehvacmain> hvacList = null;
    	List<Usermodesub> modeList = null;
    	
    	if(tar.getBehavior()==1){//按house选
	    	subsList = dao.executeSql("{CALL ModeiashvacProc(3 ,:houseId,:id,:mid)}",
					QueryParameters.with("houseId", tar.getHouseId()).and("id", tar.getId()).and("mid", tar.getMid()).parameters());
	    	hvacList = dao.executeSql("{CALL ModeiashvacProc(4 ,:houseId,:id,:mid)}",
					QueryParameters.with("houseId", tar.getHouseId()).and("id", tar.getId()).and("mid", tar.getMid()).parameters());   		
    	}
    	else if(tar.getBehavior()==2){
	    	subsList = dao.executeSql("{CALL ModeiashvacProc(7 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
	    	hvacList = dao.executeSql("{CALL ModeiashvacProc(8 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());   	    	
    	}
    	else if(tar.getBehavior()==3){//按main选
	    	subsList = dao.executeSql("{CALL ModeiashvacProc(11 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
	    	hvacList = dao.executeSql("{CALL ModeiashvacProc(14 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
    		modeList= dao.executeSql("{CALL ModeiashvacProc(13 ,:houseId,:id,:mid)}",
					//QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());	    	
    				QueryParameters.with("id", tar.getCcid()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
    	}else if(tar.getBehavior()==4){//按main选
	    	subsList = dao.executeSql("{CALL ModeiashvacProc(11 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
	    	hvacList = dao.executeSql("{CALL ModeiashvacProc(14 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());
    		modeList= dao.executeSql("{CALL ModeiashvacProc(13 ,:houseId,:id,:mid)}",
					QueryParameters.with("id", tar.getId()).and("mid", tar.getMid()).and("houseId", tar.getHouseId()).parameters());	    	
    	}     	

    	Map<String, Object> hvacMap = new HashMap<String, Object>();
    	if(tar.getBehavior()==3||tar.getBehavior()==4){
    		hvacMap.put("sub", subsList);
    		hvacMap.put("main", hvacList);        		
    		hvacMap.put("mode", modeList);		
    	}else{
    		hvacMap.put("sub", subsList);
    		hvacMap.put("main", hvacList);    		
    	}  
        LOGGER.info("1111 " + hvacMap);    	
    	return hvacMap;
    }
    
    public int deletecheck(Modehvacmain u1){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("houseId", u1.getHouseId());    	
    	params.put("id", u1.getId());   	
    	int count=0;
    	count=dao.executeHql("{CALL ModedeleteTest(8,:houseId,:id)}",params); 
    	return 0;   	
    }      
    public int updatecheck(Modehvacmain e1){
    	return 0;
    }
    public List<Modehvacmain>  getCondition(Long mainID)
    {
  	  try {
  		List<Modehvacmain> modeminList=dao.find("Select m from Modehvacmain m where m.mainId=:mainId", QueryParameters.with("mainId", mainID).parameters());
  		return modeminList;
  	} catch (Exception e) {
  		// TODO: handle exception
  		return null;
  	}
    }
    public void deleteguizhe(long id)
    {
    	String hql= "from Modehvacsub where mid = :id";	
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("id", id);
    	List<Modehvacsub> list = dao.find(hql,paramMap);
    	if(list.size()>0){
    		StringBuffer sql = new StringBuffer("delete from modemacrosub where MID in (");
        	StringBuffer sql1 =  new StringBuffer("delete from modemacromain where ID in (");
        	for(Modehvacsub mahvacSub : list){
        		sql =sql.append(mahvacSub.getDest()+",");
        		sql1 =sql1.append(mahvacSub.getDest()+",");
        	}
        	sql.delete(sql.length()-1, sql.length());
        	sql1.delete( sql.length(), sql1.length());
        	sql.append(")");
        	sql1.append(")");
    		mapDao.executeSql2(sql.toString());
    		mapDao.executeSql2(sql1.toString());
    	}
    	
    	 Modemainclause claues=new Modemainclause();
    	 claues.setId(id);
    	 claues=dao.get(Modemainclause.class,claues.getId());
    	 if(claues != null)
    		 dao.delete(claues);
//    	 List<Object> subList=getCondition2(id,"Modehvacsub","mid");
//    	 Modehvacsub csub=new Modehvacsub();
//    	 for (Object csub2: subList) {
//    		 csub=(Modehvacsub)csub2;
//		      dao.delete(csub);
//    	 }
    	 
    	 //同时删除hvacsub、schememain、schemesub
    	 Map<String, Object> params = new HashMap<String,Object>();
    	 String sql = "DELETE modehvacsub.*,modeschemesub.*,modeschememain.* FROM modehvacsub LEFT JOIN modeschemesub ON modehvacsub.MID = modeschemesub.Dest ";
    	 sql+="LEFT JOIN modeschememain ON modeschemesub.MID = modeschememain.ID WHERE modehvacsub.MID =:mid";
    	 params.put("mid", id);
    	 mapDao.executeSql2(sql, params);
    	 Modehvacmain momin=new Modehvacmain();
    	 List<Object> mominlist=getCondition2(id,"Modehvacmain","mainId");
    	 for (Object momin2 : mominlist) {
			momin=(Modehvacmain)momin2;
			dao.delete(momin);
		}
    	//删除usermodesub表
    	String delUMS = "delete from usermodesub where houseid=:houseId and dest=:dest";
    	Map param = new HashMap();
    	param.put("houseId", claues.getHouseId());
    	param.put("dest", id);
    	dao.executeHql(delUMS, param);
    }
    public void deleteguizheias(long id)
    {
//    	 Modemainclause claues=new Modemainclause();
//    	 claues.setId(id);
//    	 claues=dao.get(Modemainclause.class,claues.getId());
//    	 if(claues != null)
//    		 dao.delete(claues);
    	String hql= "from Modeiassub where mid = :id";	
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("id", id);
    	List<Modeiassub> list = dao.find(hql,paramMap);
    	if(list.size()>0){
    		StringBuffer sql = new StringBuffer("delete from modemacrosub where MID in (");
        	StringBuffer sql1 =  new StringBuffer("delete from modemacromain where ID in (");
        	for(Modeiassub maiasSub : list){
        		sql =sql.append(maiasSub.getDest()+",");
        		sql1 =sql1.append(maiasSub.getDest()+",");
        	}
        	sql.delete(sql.length()-1, sql.length());
        	sql1.delete( sql.length(), sql1.length());
        	sql.append(")");
        	sql1.append(")");
    		mapDao.executeSql2(sql.toString());
    		mapDao.executeSql2(sql1.toString());
    	}
//    	 List<Object> subList=getCondition2(id,"Modeiassub","mid");
//    	 Modeiassub csub=new Modeiassub();
//    	 for (Object csub2: subList) {
//    		 csub=(Modeiassub)csub2;
//		      dao.delete(csub);
//    	 }
    	String sql = "DELETE modeiassub.*,modeschemesub.*,modeschememain.* FROM	modeiassub " +
    			"LEFT JOIN modeschemesub ON modeiassub.MID = modeschemesub.Dest " +
    			"LEFT JOIN modeschememain ON modeschemesub.MID = modeschememain.ID " +
    			"WHERE modeiassub.MID = :mid";
    	Map<String, Object> parMap = new HashMap<String,Object>();
    	parMap.put("mid", id);
    	mapDao.executeSql2(sql, parMap);
    	 Modeiasmain momin=new Modeiasmain();
    	 List<Object> mominlist=getCondition2(id,"Modeiasmain","id");
    	 for (Object momin2 : mominlist) {
			momin=(Modeiasmain)momin2;
			dao.delete(momin);
		}
    	//删除usermodesub表
     	String delUMS = "delete from usermodesub where houseid=:houseId and dest=:dest";
     	Map param = new HashMap();
     	param.put("houseId", ((Modeiasmain)mominlist.get(0)).getHouseId());
     	param.put("dest", id);
     	dao.executeHql(delUMS, param);
    }

   public List<Object>  getCondition2(Long mainID,String table,String coum)
    {
  	  try {
  		List<Object> modeminList=dao.find("Select m from "+table+"  m where  m."+coum+"=:mainId", QueryParameters.with("mainId", mainID).parameters());
  		return modeminList;
  	} catch (Exception e) {
  		// TODO: handle exception
  		return null;
  	}
    }
   
   /**
    * 删除条件二
    * @param id
    */
   public void twocondit(long id) throws Exception {
	   //删除动作
	   String delSubSql = "delete from modehvacsub where mid=" + id;
	   //删除条件
	   String delMainSql = "delete from modehvacmain where id=" + id;
	   dao.executeHql(delSubSql, null);
	   dao.executeHql(delMainSql, null);
//	 Modehvacmain momin=new Modehvacmain();
//  	 List<Object> subList=getCondition2(id,"Modehvacsub","mid");
//  	 List<Object> mominlist=getCondition2(id,"Modehvacmain","mainId");
//  	 if (subList.size()==2&&mominlist.size()==2) {
//  		 Modehvacsub csub=new Modehvacsub();
//  	  	// for (Object csub2: subList) {
//  	  		 csub=(Modehvacsub)subList.get(1);
//  	  		 
//  			      dao.delete(csub);
//  	  	 //}
//  	  	 for (Object momin2 : mominlist) {
//  				momin=(Modehvacmain)momin2;
//  				if(momin.getCondition2()==null)
//  				{
//  					dao.delete(momin);
//  					
//  				}
//  				else {
//  					momin.setCondition2("1");
//  					dao.update(momin);
//				}
//  			}	
//	}
  	
   }
   
   public  List<Map> showhvac(String houseid) {
	   Map<String , Object> param = new HashMap<String,Object>();
	String sql = "SELECT m.*,d.deviceName,r.roomName,lib.attrName,lib.DataType from modehvacmain m " +
			"INNER JOIN Deviceattrlib lib on m.attrlibId=lib.id " +
			"INNER JOIN modemainclause c on c.id = m.mainID " +
			"inner JOIN modedevice d ON m.ModeEpID = d.id " +
			" inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1 " +
			"inner join modeeplib EPL on EPL.nodeId=nodeL.id and d.deviceId=EPL.deviceId and d.ep=epl.ep  " +
			"inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId " +
			"WHERE m.HouseID = :houseId";
	param.put("houseId", houseid);
	List<Map> list =dao.executeSql(sql, param);
	return list;
   }
   
   public  List<Map> showias(String houseid) {
	   Map<String , Object> param = new HashMap<String,Object>();
	String sql = "SELECT m.*,d.deviceName,r.roomName,lib.attrName,lib.DataType from modeiasmain m " +
			"INNER JOIN Deviceattrlib lib on m.attrlibId=lib.id " +
			"inner JOIN modedevice d ON m.ModeEpID = d.id " +
			"inner join modenodelib nodeL on d.modelId=nodeL.modelId or LOCATE(nodeL.modelId,d.modelId)=1 " +
			"inner join modeeplib EPL on EPL.nodeId=nodeL.id and d.deviceId=EPL.deviceId and d.ep=epl.ep  " +
			"inner join moderoom r on d.roomId=r.roomId and d.houseId=r.houseId " +
			"WHERE m.HouseID = :houseId";
	param.put("houseId", houseid);
	List<Map> list =dao.executeSql(sql, param);
	return list;
   }
   
}
