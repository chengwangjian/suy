package com.flywind.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;

import com.alibaba.fastjson.JSON;
import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlmacro;
import com.flywind.app.entities.Modeactlib;
import com.flywind.app.entities.Modemacrocasc;
import com.flywind.app.entities.Modemacromain;
import com.flywind.app.entities.Modemacroproc;
import com.flywind.app.entities.Modemacrosub;
import com.flywind.app.entities.Modescenesub;
import com.flywind.app.entities.Usermodemain;


/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dmacro")
public class Datamacro
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Datamacro.class);
    private BaseDaoI<Map> mapDao;
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

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
        
        List<Usermodemain> modes = new ArrayList<Usermodemain>();
        List<Modemacromain> macros = new ArrayList<Modemacromain>();
        List<Modemacrosub> macrosubs = new ArrayList<Modemacrosub>();
//        modes.add(new Moderoom(111, "111", "111"));
//        modes.add(new Moderoom(222, "222", "222"));
//        macros.add(new Modemacromain(5,6, "aaa", "aaa"));
//        macros.add(new Modemacromain(6,7, "bbb", "bbb"));
//        macrosubs.add(new Modemacrosub(1,2,"zzz", 111,"ttt","ddd","1","xxx"));
//        macrosubs.add(new Modemacrosub(2,3,"yyy", 222,"ttt","ddd","2","xxx"));
        xmlmacro=new Xmlmacro();
        xmlmacro.setMain(macros);
        xmlmacro.setSub(macrosubs);
        
//        LOGGER.info("-- Loading initial demo data");
//        rooms=dao.findWithNamedQuery(Moderoom.BY_houseId,
//				QueryParameters.with("houseId", 111L).parameters());
        
//      create(modes);
//      create(macros);   
//      create(macrosubs); 
        LOGGER.info("Users " + macros.toString());
        LOGGER.info("Users " + macrosubs.toString());
        LOGGER.info("-- Data Loaded. Exit");
        
    }
        
    public Xmlmacro xmlmacro=new Xmlmacro();
    public Xmlmacro create(List<?> main,List<?> sub,Target tar)
    {
        List macros = new ArrayList();
        List macrosubs = new ArrayList();  
        if(main.size()>0)
	    	for (Object e : main)
	        {
	    		Modemacromain e1=(Modemacromain)e;
	    		e1.setMacroName(tar.getAct()+"-"+tar.getMid()+"-"+getDefaultActionNumber(tar.getAct(), tar.getMid()));
	    		macros.add(dao.create(e1));	 		
	        }
        else if(sub.size()>0)
			for(Object u :sub){
				Modemacrosub u1=(Modemacrosub)u;
//    			u1.setMid(e1.getId());
				if(tar.getId()!=null)u1.setDest(tar.getId());
				macrosubs.add(dao.create(updatecheck2(u1)));       	        
	    	} 
        xmlmacro.setMain(macros);
        xmlmacro.setSub(macrosubs);
    	LOGGER.info("1111----" + JSON.toJSONString(xmlmacro));
        return xmlmacro;
    }
    public Xmlmacro update(List<?> main,List<?> sub,Target tar)
    {
        List macros = new ArrayList();
        List macrosubs = new ArrayList();  
    	for (Object e : main)
        {
    		Modemacromain mmMain = (Modemacromain) e;
    		//判断名称是否已存在
    		if(isNameExist(mmMain.getId(), mmMain.getMacroName(), mmMain.getHouseId()))
    			return null;
    		else
    			macros.add(dao.update(e));
        }
    	for (Object e : sub)
    	{
    		Modemacrosub u1=(Modemacrosub)e;
    		if(tar.getId()!=null)u1.setDest(tar.getId());
    		macrosubs.add(dao.update(updatecheck2(u1)));
    	}
        xmlmacro.setMain(macros);
        xmlmacro.setSub(macrosubs);  
    	LOGGER.info("1111----" + JSON.toJSONString(xmlmacro));        
        return xmlmacro;    	
    }
    public void delete(List<?> main,List<?> sub) {  
    	for (Object e : main)
        {
    		Modemacromain e1 = (Modemacromain)e;
    		e1=dao.get(Modemacromain.class, e1.getId());
//    		dao.delete(e1);
       		deletecheck(e1);    		
        }                
    	for (Object u : sub)
        {
    		Modemacrosub u1 = (Modemacrosub)u;
    		u1=dao.get(Modemacrosub.class, u1.getId());
    		dao.delete(u1);
//     		deletecheck(u1); 
        }
    	LOGGER.info("2222 " + sub.size());
    }
    public Object findmode(Target tar){
    	List<Modemacroproc> subsList = null;
    	List<Modescenesub> sceneList = null;
    	List<Modemacrocasc> macroList = null;
    	if(tar.getBehavior()==4){//按模式查
	       	subsList = dao.executeSql("{CALL ModemacrosubProc(1 ,:macroId ,0)}",
					QueryParameters.with("macroId", tar.getMacroId()).parameters(),Modemacroproc.class);
	    	macroList =dao.executeSql("{CALL ModemacrosubProc(2 ,:macroId ,0)}",
					QueryParameters.with("macroId", tar.getMacroId()).parameters(),Modemacrocasc.class);
    	}
    	else if(tar.getBehavior()==6){//按主表查
	       	subsList = dao.executeSql("{CALL ModemacrosubProc(3 ,:macroId ,0)}",
					QueryParameters.with("macroId", tar.getMacroId()).parameters(),Modemacroproc.class);
	    	macroList =dao.executeSql("{CALL ModemacrosubProc(4 ,:macroId ,0)}",
					QueryParameters.with("macroId", tar.getMacroId()).parameters(),Modemacrocasc.class);
    	}
    	else if(tar.getBehavior()==61){//按主表查
	       	subsList = dao.executeSql("{CALL ModemacrosubProc(3 ,:macroId ,0)}",
					QueryParameters.with("macroId", tar.getMacroId()).parameters());
	    	macroList =dao.executeSql("{CALL ModemacrosubProc(4 ,:macroId ,0)}",
					QueryParameters.with("macroId", tar.getMacroId()).parameters(),Modemacrocasc.class);
    	}
    	else if(tar.getBehavior()==8){//按子表查
    		subsList = dao.executeSql("{CALL ModemacrosubProc(5 ,:macroId ,0)}",
    				QueryParameters.with("macroId", tar.getMacroId()).parameters(),Modemacroproc.class);
    		sceneList= dao.executeSql("{CALL ModemacrosubProc(6 ,:macroId ,0)}",
    				QueryParameters.with("macroId", tar.getMacroId()).parameters(),Modescenesub.class);
    	}    	
    	else if(tar.getBehavior()==1){//按ias查
    		if(tar.getType()==00){
				tar.setType(0L);
			}
			if(tar.getType()==02){
				tar.setType(1L);
			}
			if(tar.getType()==20){
				tar.setType(2L);
			}
			if(tar.getType()==22){
				tar.setType(3L);
			}
	    	macroList =dao.executeSql("{CALL ModemacrosubProc(7 ,:macroId,:type)}",
					QueryParameters.with("macroId", tar.getMacroId()).and("type", tar.getType()).parameters(),Modemacrocasc.class);	       	
    	}
    	else if(tar.getBehavior()==2){//按hvac正向
	    	macroList =dao.executeSql("{CALL ModemacrosubProc(8 ,:macroId,:type)}",
					QueryParameters.with("macroId", tar.getMacroId()).and("type", tar.getType()).parameters(),Modemacrocasc.class);	       	 
    	}
    	else if(tar.getBehavior()==3){
	    	macroList =dao.executeSql("{CALL ModemacrosubProc(9 ,:macroId,:type)}",
					QueryParameters.with("macroId", tar.getMacroId()).and("type", tar.getType()).parameters(),Modemacrocasc.class);
    	}
    	else if(tar.getBehavior()==5){//按house查
	    	macroList =dao.executeSql("{CALL ModemacrosubProc(10 ,:macroId,0)}",
					QueryParameters.with("macroId", tar.getMacroId()).parameters(),Modemacrocasc.class);   		
    	}
        LOGGER.info("1111 " + subsList+"-----"+macroList+"-----"+sceneList);
        Map<String, Object> groupMap = new HashMap<String, Object>();
    	if(tar.getBehavior()==8){
    		groupMap.put("macro", subsList);   
        	groupMap.put("group", sceneList);
    	}
    	else{       	
        	groupMap.put("sub", subsList);
    		groupMap.put("main", macroList);   
    	}
    	return groupMap;
    }
    
    public int deletecheck(Modemacromain e1){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("houseId", e1.getHouseId());    	
    	params.put("id", e1.getId());   	

    	int count=dao.executeHql("{CALL ModedeleteTest(2,:houseId,:id)}",params);    	 
    	LOGGER.info("macrodelete----"+count+"----"+JSON.toJSONString(params));
    	return count; 
    }
    public int updatecheck(Target tar){
    	List<Modemacrocasc> macroList = null;		
		if(tar.getAct().equalsIgnoreCase("Macro"))
			macroList=dao.executeSql("{CALL ModemacrosubProc(2,:macroId,:type)}", 
					QueryParameters.with("macroId", tar.getMid()).and("type", tar.getType()).parameters(),Modemacrocasc.class);
		else if(tar.getAct().equalsIgnoreCase("IAS"))
			macroList=dao.executeSql("{CALL ModemacrosubProc(7,:macroId,:type)}", 
					QueryParameters.with("macroId", tar.getMid()).and("type", tar.getType()).parameters(),Modemacrocasc.class);			
		else if(tar.getAct().equalsIgnoreCase("HVAC")||tar.getAct().equalsIgnoreCase("HVAC2"))	
			macroList=dao.executeSql("{CALL ModemacrosubProc(8,:macroId,:type)}", 
					QueryParameters.with("macroId", tar.getMid()).and("type", tar.getType()).parameters(),Modemacrocasc.class);			
		LOGGER.info("updatecheck--count--"+macroList.size());
		return macroList.size()+1;   	
    }
    public Modemacrosub updatecheck2(Modemacrosub u1){
		Modeactlib lib=dao.get("Select m from Modeactlib m where m.id=:libId",
				QueryParameters.with("libId", new Long(u1.getActlibId())).parameters());
    	LOGGER.info("updatecheck2--Modeactlib--:"+JSON.toJSONString(lib));			
		if(lib==null)//如果是scene
			u1.setAct("RecallDeviceScene");
		else{
			u1.setAct(lib.getActName());
	    	if(lib.getDataType()==2){
				//MoveToLevelWithOnOff拷贝Level=,TransTime=		
				u1.setExtension("Level="+lib.getExtentionSet()+",TransTime="+u1.getParam()); 
	    	}else if(lib.getDataType()==5){	    			    		
	    		//拆分色彩值
	    		List<String> out= Arrays.asList(StringUtils.split(u1.getParam(), ","));
	    		List<Integer> in =new ArrayList<Integer>();
	    		for(int i=0;i<6;i++){
	    			int pos=out.get(i).indexOf("=")+1; 	    			
	    			in.add(Integer.parseInt(out.get(i).substring(pos)));
//	    			s=s.substring(0,s.indexOf("=")+1);
	    		}    		
	    		String params = "";int[] xyy=new int[3];
	    		RGBUtil.rgb2xyY(in.get(3),in.get(4),in.get(5),xyy);
	    		params+="Hue="+in.get(0)/360*255;
	    		params+=",Satulation="+in.get(1)/100*255;
	    		params+=",ColorTemperature="+in.get(2)/100*65279;
	    		params+=",ColorX="+xyy[0];
	    		params+=",ColorY="+xyy[1];
	    		params+=",TransTime=";
	    		LOGGER.info("color=params="+params);
	    		u1.setExtension(params);
	    		/*替换等于插入删除
	    		StringBuffer stb = new StringBuffer(u1.getParam());
	    		int pos=stb.indexOf("nationcode=");
	    		String insert=stb.substring(stb.indexOf("=", pos), stb.indexOf(",", pos));
	    		pos=stb.indexOf("TelNo=");
	    		stb.insert(stb.indexOf("=", pos), insert);
	    		u1.setExtension(stb.toString());*/
	    	}else if(u1.getActlibId()==9||u1.getActlibId()==10){
	    		//CIEBypassZone拷贝DeviceID
	    		u1.setExtension(u1.getParam());
	    	}else if(lib.getDataType()==6){//MoveToColor
				u1.setPara(u1.getParam());//para要和param一致
			}
		} 
    	return u1;
    }
    
    /**
     * 获取默认动作集的序号
     * @param act
     * @param mid
     * @return
     */
    public int getDefaultActionNumber(String act, Long mid) {
    	StringBuilder sql = null;
    	if(act.equalsIgnoreCase("HVAC") || act.equalsIgnoreCase("HVAC2")){
    		sql = new StringBuilder("SELECT Max(substring_index(m.MacroName,'-',-1)+0) mNumber from modemacromain m inner join Modehvacsub h on m.ID=h.Dest where h.MID=");
    		sql.append(mid).append(" and m.MacroName REGEXP 'HVAC-").append(mid).append("-[0-9]+'");
    	}
    	else if(act.equalsIgnoreCase("Macro")) {
    		sql = new StringBuilder("SELECT Max(substring_index(m.MacroName,'-',-1)+0) mNumber from modemacromain m inner join usermodesub um on m.ID=um.Dest and um.Act='ApplyMacro' ");
    		sql.append("where um.MID=").append(mid).append(" and m.MacroName REGEXP 'Macro-").append(mid).append("-[0-9]+'");
    	}
    	else {
			sql = new StringBuilder("SELECT Max(substring_index(m.MacroName,'-',-1)+0) mNumber from modemacromain m inner join modeiassub h on m.ID=h.Dest ");
    		sql.append("where h.MID=").append(mid).append(" and ");
    		sql.append("m.MacroName REGEXP 'IAS-").append(mid).append("-[0-9]+'");
    	}
    	List<Map> mNameList = dao.executeSql(sql.toString(), null);
    	if(mNameList.isEmpty())
    		return 1;
    	else {
    		Double mNumber = (Double) mNameList.get(0).get("mNumber");
    		if(mNumber != null)
    			return mNumber.intValue() + 1;
    		else
    			return 1;
    	}
    }
    
    /**
     * 判断动作集是否
     * @param id
     * @param mName
     * @return
     */
    public boolean isNameExist(Long id, String mName, Long houseId) {
    	StringBuilder sql = new StringBuilder("select * from modemacromain a where a.ID <> ");
    	sql.append(id).append(" and MacroName='").append(mName).append("' and a.HouseID = ");
    	sql.append(houseId).append(" limit 1");
    	List<Map> mList = dao.executeSql(sql.toString(), null);
    	if(mList.isEmpty())
    		return false;
    	else 
    		return true;
    }
    
    
    public int deleteByMid(int mid) {
    	Map<String, Object> params = new HashMap<String,Object>();
    	params.put("mid", mid);
		String sql = "delete from modemacrosub where MID=:mid";
		int i= mapDao.executeSql2(sql, params);
		String sql1 = "delete from modemacromain where ID=:mid";
		int i1= mapDao.executeSql2(sql1, params);
		return i;
	}
    
    public int deleteActList(Map<String,Object> param){
    	if(param.get("tar").toString().equals("HVAC")){
    		String sql = "delete from modehvacsub where MID =:mid and ActType=:type";
        	Map<String, Object> params = new HashMap<String,Object>();
        	params.put("mid", param.get("mid"));
        	params.put("type", param.get("type"));
        	int i= mapDao.executeSql2(sql, params);
    		return i;
    	}else {
    		String sql = "delete from modeiassub where MID =:mid and CIEStatus=:CIEStatus and ZoneActType=:ZoneActType";
        	Map<String, Object> params = new HashMap<String,Object>();
        	params.put("mid", param.get("mid"));
        	if(param.get("type").toString().equals("0")){
        		params.put("CIEStatus", 0);
        		params.put("ZoneActType", 0);
    		}
        	if(param.get("type").toString().equals("2")){
        		params.put("CIEStatus", 0);
        		params.put("ZoneActType", 1);
        	}
        	if(param.get("type").toString().equals("20")){
        		params.put("CIEStatus", 1);
        		params.put("ZoneActType", 0);
        	}
        	if(param.get("type").toString().equals("22")){
        		params.put("CIEStatus", 1);
        		params.put("ZoneActType", 1);
        	}
        	int i= mapDao.executeSql2(sql, params);
    		return i;
		}
    	
    }
}
