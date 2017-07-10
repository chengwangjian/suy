package com.flywind.app.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sy.dao.BaseDaoI;
import sy.model.Command;
import sy.model.ModeCommand;
import sy.model.Modedevice;
import sy.model.Moderoom;
import com.alibaba.fastjson.JSON;
import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Target;
import com.flywind.app.data.Xmlmodemain;
import com.flywind.app.data.Xmlmoderoom;
import com.flywind.app.entities.Modeactlib;
import com.flywind.app.entities.Modemacromain;
import com.flywind.app.entities.Modemacrosub;
import com.flywind.app.entities.Modeschememain;
import com.flywind.app.entities.Modeschemesub;
import com.flywind.app.entities.Usermodedevice;
import com.flywind.app.entities.Usermodemain;
import com.flywind.app.entities.Usermodesub;

/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
@Service("dmodemain")
public class Datamodemain {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Datamodemain.class);

	private StartDAO dao;
	
	@Autowired
	private BaseDaoI<Map> mapDao;

	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

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

	public void initialize() {

		List<Moderoom> rooms = new ArrayList<Moderoom>();
		List<Usermodemain> modes = new ArrayList<Usermodemain>();
		List<Usermodesub> modesubs = new ArrayList<Usermodesub>();
		// rooms.add(new Moderoom(111, "111", "111"));
		// rooms.add(new Moderoom(222, "222", "222"));
		modes.add(new Usermodemain(1, 7, "aaa", "aaa", "aaa"));
		modes.add(new Usermodemain(1, 8, "bbb", "bbb", "bbb"));
		// modesubs.add(new Usermodesub(1,1, "zzz", 111,"",1));
		// modesubs.add(new Usermodesub(2,2, "yyy", 222,"",0));
		xmlmode = new Xmlmodemain();
		xmlmode.setMain(modes);
		xmlmode.setSub(modesubs);

		// LOGGER.info("-- Loading initial demo data");
		// List<Object> l=dao.find(NamedQueries.Moderoom_houseId,
		// QueryParameters.with("houseId", 111L).parameters());
		// for(Object i:l)LOGGER.info("Users " + i);
		// LOGGER.info("Users " + JSON.toJSONString(l));

		// create(rooms);
		// create(modes);
		// create(modesubs);
		LOGGER.info("Users " + modes.toString());
		LOGGER.info("Users " + modesubs.toString());
		LOGGER.info("-- Data Loaded. Exit");

	}

	public Xmlmodemain xmlmode = new Xmlmodemain();
	
	public Integer findModeByName(String name,Long houseid){
		String sqlString="select * from usermodemain where ModeName=:modename and HouseID=:houseid";
		HashMap<String, Object>params=new HashMap<String, Object>();
		params.put("modename", name);
		params.put("houseid",houseid);
     List<Map> lMaps=mapDao.executeSql(sqlString, params);
     if(!lMaps.isEmpty()&&lMaps.size()>0){
    	 return lMaps.size();
     }
		return null;
		
		
	}
	public Integer findModeByName(String modename, Long houseid, Long modeid) {
		// TODO Auto-generated method stub
		String sqlString="select * from usermodemain where ModeName=:modename and HouseID=:houseid and id<>:modeid";
		HashMap<String, Object>params=new HashMap<String, Object>();
		params.put("modename",modename);
		params.put("houseid",houseid);
		params.put("modeid", modeid);
     List<Map> lMaps=mapDao.executeSql(sqlString, params);
     if(!lMaps.isEmpty()&&lMaps.size()>0){
    	 return lMaps.size();
     }
		return null;
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
    public long getCount(Long e1) {
		// #模式计数
    	HashMap<String,Object>params=new HashMap<String, Object>();
    	params.put("houseId", e1);
		List<Map> cList = dao.executeSql("{CALL Modescheme(9 ,:houseId,0,0)}",params);
		LOGGER.info("houseId ----" + e1 + "oldId+1 ----"
				+ JSON.toJSONString(cList));
		return ((BigInteger) (cList.get(0).get("aCount"))).longValue();
	}
	@Transactional
	public Long create(ModeCommand command) {
		Usermodemain  main = new Usermodemain();
		try {
			List<Command> commands = command.getShows();
			for (Command command2 : commands) {
				if (command2.isTest()) {
					main.setEnableCheckOnOff((short) 1);
				}
			}		
			main.setHouseId(command.getHouseid());
			main.setPicName(command.getPicName());
			main.setRoomId(command.getModeroomid());
			main.setModeName(command.getModename());
			main.setPicName(command.getPicName());
			main.setDescription(command.getDescription());
			main.setOldId(getCount(command.getHouseid()));
			dao.create(main);
			Modemacromain modemacromain=new Modemacromain();
			modemacromain.setHouseId(command.getHouseid());
//			modemacromain.setMacroName("用户自定义");
			modemacromain.setMacroName("Macro"+"-"+main.getId()+"-"+getDefaultActionNumber("Macro", main.getId()));
			modemacromain.setModeId(main.getId());
			dao.create(modemacromain);
			Usermodesub sub=null;
			Modemacrosub msub=null;
			Usermodedevice usermodedevice=null;
			HashMap<String, Object>params=new HashMap<String, Object>();
			HashMap<String, Object>params2=new HashMap<String, Object>();
			sub=new Usermodesub();
			sub.setMid(main.getId());
			sub.setHouseId(command.getHouseid());
			
//			sub.setAct( lMaps.get(0).get("ActName").toString());
			sub.setAct("ApplyMacro");
//			sub.setDest(command2.getDeviceid());
			sub.setDest(modemacromain.getId());
			dao.create(sub);
			for(Command command2 : commands){
//				String sqlString="select * from modeactlib where id=:id";
//				params.put("id", command2.getActid());
			//	List<Modeactlib> sList=dao.executeSql(sqlString, params); 
//			   List<Map>lMaps=mapDao.executeSql(sqlString, params);
				msub=new Modemacrosub();
				Modeactlib lib=dao.get("Select m from Modeactlib m where m.id=:libId",
						QueryParameters.with("libId", new Long(command2.getActid())).parameters());
		    	LOGGER.info("updatecheck2--Modeactlib--:"+JSON.toJSONString(lib));			
				if(lib==null)//如果是scene
					msub.setAct("RecallDeviceScene");
				else{
					msub.setAct(lib.getActName());
			    	if(lib.getDataType()==2){
						//MoveToLevelWithOnOff拷贝Level=,TransTime=		
			    		msub.setExtension("Level="+lib.getExtentionSet()+",TransTime="+command2.getParam()); 
			    	}else if(lib.getDataType()==5){	    			    		
			    		//拆分色彩值
			    		List<String> out= Arrays.asList(StringUtils.split(command2.getParam(), ","));
			    		List<Integer> in =new ArrayList<Integer>();
			    		for(int i=0;i<6;i++){
			    			int pos=out.get(i).indexOf("=")+1; 	    			
			    			in.add(Integer.parseInt(out.get(i).substring(pos)));
			    		}    		
			    		String paramssString = "";int[] xyy=new int[3];
			    		RGBUtil.rgb2xyY(in.get(3),in.get(4),in.get(5),xyy);
			    		paramssString+="Hue="+in.get(0)/360*255;
			    		paramssString+=",Satulation="+in.get(1)/100*255;
			    		paramssString+=",ColorTemperature="+in.get(2)/100*65279;
			    		paramssString+=",ColorX="+xyy[0];
			    		paramssString+=",ColorY="+xyy[1];
			    		paramssString+=",TransTime=";
			    		LOGGER.info("color=params="+params);
			    		msub.setExtension(paramssString);
			    	}else if(command2.getActid()==9||command2.getActid()==10){
			    		//CIEBypassZone拷贝DeviceID
			    		msub.setExtension(command2.getParam());
			    	}
			    	else if(lib.getDataType() == 6) {
			    		msub.setPara(command2.getParam());
			    	}
				} 
				msub.setHouseId(command.getHouseid());
//				msub.setMid(main.getId());// ???
				msub.setMid(modemacromain.getId());// ???
				//msub.setAct(lMaps.get(0).get("ActName").toString());
				msub.setDelaySec(command2.getDelaySec());
				msub.setDest(command2.getDeviceid());
				msub.setActlibId(command2.getActid());
				msub.setDestType("0");
				msub.setParam(command2.getParam());
				msub.setName(command2.getName());
				dao.create(msub);
				  if(command2.isTest()){
				usermodedevice=new Usermodedevice();
				usermodedevice.setDest(command2.getDeviceid());
				usermodedevice.setHouseId(command.getHouseid());
				usermodedevice.setMid(main.getId());
				usermodedevice.setOldDestId(0);
				dao.create(usermodedevice);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return main.getId();

	}

	public Xmlmodemain create(List<?> main, List<?> sub, Target tar) {
		List modes = new ArrayList();
		List modesubs = new ArrayList();
		for (Object e : main) {
			Usermodemain e1 = (Usermodemain) e;
			e1.setOldId(getCount(e1));
			modes.add(dao.create(e1));
		}
		for (Object u : sub) {
			Usermodesub u1 = (Usermodesub) u;
			// 单个添加
			if (tar.getBehavior() <= 1)
				u1.setDest(tar.getDest());
			modesubs.add(dao.create(u1));
		}
		xmlmode.setMain(modes);
		xmlmode.setSub(modesubs);
		return xmlmode;
	}

	public Xmlmodemain update(List<?> main, List<?> sub) {
		List modes = new ArrayList();
		List modesubs = new ArrayList();
		for (Object e : main) {
			Usermodemain e1 = (Usermodemain) e;
			Usermodemain e2 = dao.get(Usermodemain.class, e1.getId());
			BeanUtils.copyProperties(e1, e2, new String[] { "id", "orderId",
					"oldId" });
			getCount(e2);
			modes.add(dao.update(e2));
		}
		for (Object e : sub) {
			modesubs.add(dao.update(e));
		}
		xmlmode.setMain(modes);
		xmlmode.setSub(modesubs);
		return xmlmode;
	}

	public void delete(List<?> main, List<?> sub) {
		for (Object e : main) {
			Usermodemain e1 = (Usermodemain) e;
			e1 = dao.get(Usermodemain.class, e1.getId());
			// dao.delete(e1);
			deletecheck(e1);
		}
		for (Object u : sub) {
			dao.delete(u);
		}
		LOGGER.info("2222 " + sub.size());
	}

   public List<Map> findModeById(String modeId) {
   String sql="select * from usermodemain where id=:id";
   HashMap<String,Object>params=new HashMap<String, Object>();
   params.put("id",modeId);
   List<Map>modeList=mapDao.executeSql(sql, params);
   return modeList;
	}
	
	public Object findroom(Target tar) {
		if (tar.getBehavior() != null && tar.getBehavior() == 1) {// 查询电能列表和设备列表
			List<Modedevice> deviceList = dao.executeSql(
					"{CALL Sorted_Sub_List0(3 ,:houseId,:mid,0)}",
					QueryParameters.with("houseId", tar.getHouseId())
							.and("mid", tar.getMid()).parameters());
			List<Usermodedevice> subList = dao.executeSql(
					"{CALL Sorted_Sub_List0(4 ,:houseId,:mid,0)}",
					QueryParameters.with("houseId", tar.getHouseId())
							.and("mid", tar.getMid()).parameters());
			Map<String, Object> deviceMap = new HashMap<String, Object>();
			deviceMap.put("main", deviceList);
			deviceMap.put("sub", subList);
			LOGGER.info("1111--" + deviceMap);
			return deviceMap;
		} else {
			List<Moderoom> roomList = dao.find(NamedQueries.Moderoom_houseId,
					QueryParameters.with("houseId", tar.getHouseId())
							.parameters());
			List<Usermodemain> mainList = dao.find(
					NamedQueries.Usermodemain_houseId,
					QueryParameters.with("houseId", tar.getHouseId())
							.parameters());
			Xmlmoderoom xmlroom = new Xmlmoderoom();
			xmlroom.setMain(roomList);
			xmlroom.setSub(mainList);
			LOGGER.info("1111--" + xmlroom);
			return xmlroom;
		}
	}

	public long getCount(Usermodemain e1) {
		// #模式计数
		List<Map> cList = dao.executeSql("{CALL Modescheme(9 ,:houseId,0,0)}",
				QueryParameters.with("houseId", e1.getHouseId()).parameters());
		LOGGER.info("houseId ----" + e1.getHouseId() + "oldId+1 ----"
				+ JSON.toJSONString(cList));
		return ((BigInteger) (cList.get(0).get("aCount"))).longValue();
	}

	public int deletecheck(Usermodemain e1) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseId", e1.getHouseId());
		params.put("id", e1.getId());

		int count = dao.executeHql("{CALL ModedeleteTest(1,:houseId,:id)}",
				params);
		
					String deString="from Modemacrosub where mid=(select id from Modemacromain where modeId=:modeid)";
//					String deString="delete from modemacrosub where MID=(SELECT ID from modemacromain where ModeID=:modeid)";
					HashMap<String, Object>parHashMap=new HashMap<String, Object>();
					parHashMap.put("modeid", e1.getId());
					List<Modemacrosub> subList=dao.find(deString, parHashMap);
					if(!subList.isEmpty()&&subList.size()>0){
					for(Modemacrosub s:subList){
						dao.delete(s);                  //删除Modemacrosub
					} 
					}
					String deString2="from Modemacromain where modeId=:modeid";
//					String deString="delete from modemacrosub where MID=(SELECT ID from modemacromain where ModeID=:modeid)";
					HashMap<String, Object>parHashMap2=new HashMap<String, Object>();
					parHashMap2.put("modeid", e1.getId());
					List<Modemacromain> usermodedevice2=dao.find(deString2, parHashMap2);
					if(!usermodedevice2.isEmpty()&&usermodedevice2.size()>0){
					for(Modemacromain s:usermodedevice2){
						dao.delete(s);                  //删除Modemacromain
					} 
					}
					String deString3="from Usermodedevice where mid=:modeid";
//					String deString="delete from modemacrosub where MID=(SELECT ID from modemacromain where ModeID=:modeid)";
					HashMap<String, Object>parHashMap3=new HashMap<String, Object>();
					parHashMap3.put("modeid", e1.getId());
					List<Usermodedevice> usermodedevice3=dao.find(deString3, parHashMap3);
					if(!usermodedevice3.isEmpty()&&usermodedevice3.size()>0){
					for(Usermodedevice s:usermodedevice3){
						dao.delete(s);                  //删除Usermodedevice
					} 
					}
					String deString4="from Modeschememain where modeId=:modeid";
//					String deString="delete from modemacrosub where MID=(SELECT ID from modemacromain where ModeID=:modeid)";
					HashMap<String, Object>parHashMap4=new HashMap<String, Object>();
					parHashMap4.put("modeid", e1.getId());
					List<Modeschememain> usermodedevice4=dao.find(deString4, parHashMap4);
					if(!usermodedevice4.isEmpty()&&usermodedevice4.size()>0){
					for(Modeschememain s:usermodedevice4){
						dao.delete(s);                  //删除Modeschememain
					} 
					}
					String deString5="from Modeschemesub where mid=(select id from Modeschememain where modeId=:modeid)";
//					String deString="delete from modemacrosub where MID=(SELECT ID from modemacromain where ModeID=:modeid)";
					HashMap<String, Object>parHashMap5=new HashMap<String, Object>();
					parHashMap5.put("modeid", e1.getId());
					List< Modeschemesub> usermodedevice5=dao.find(deString5, parHashMap5);
					if(!usermodedevice5.isEmpty()&&usermodedevice5.size()>0){
					for( Modeschemesub s:usermodedevice5){
						dao.delete(s);                  //删除 Modeschemesub
					}
					}
		return count;
	}

	public int updatecheck2(Target tar) {
		Map<String, Object> params = new HashMap<String, Object>();
		int count = 0;
		params.put("houseId", tar.getHouseId());
		params.put("mid", tar.getMid());
		params.put("str", tar.getAct().substring(1, tar.getAct().length() - 1));
		if (tar.getBehavior() == 0)// modedevice删除
			count = dao.executeHql(
					"{CALL Sorted_Sub_List0(0,:houseId,:mid,:str)}", params);
		else if (tar.getBehavior() == 1)// modedevice插入
			count = dao.executeHql(
					"{CALL Sorted_Sub_List0(1,:houseId,:mid,:str)}", params);
		else if (tar.getBehavior() == 2)// modemain排序
			count = dao.executeHql(
					"{CALL Sorted_Sub_List0(2,:houseId,:mid,:str)}", params);
		return count;
	}

	public int updatecheck(List<?> main, List<?> sub, Target tar) {
		Map<String, Object> params = new HashMap<String, Object>();
		String mid = "";
		int count = 0;
		params.put("dest", tar.getDest());// ias/hvac主表id
		params.put("houseId", tar.getHouseId());
		// params.put("mainId", tar.getMainId());

		for (Object u : sub) {
			Usermodesub u1 = (Usermodesub) u;
			if (u1.getSelectss() == 0)
				mid += u1.getMid() + ",";
		}
		if ((count = mid.length()) > 0) {
			mid = mid.substring(0, mid.length() - 1);
		}
		mid = mid + ";";// 0禁用
		for (Object u : sub) {
			Usermodesub u1 = (Usermodesub) u;
			if (u1.getSelectss() == 1)
				mid += u1.getMid() + ",";
		}
		if ((count = mid.length()) > 1) {
			mid = mid.substring(0, mid.length() - 1);
		}
		mid = mid + ";"; // 1启用
		for (Object u : sub) {
			Usermodesub u1 = (Usermodesub) u;
			if (u1.getSelectss() == 2)
				mid += u1.getMid() + ",";
		}
		if ((count = mid.length()) > 2) {
			mid = mid.substring(0, mid.length() - 1);
		}
		mid = mid + ";";// 2忽略
		params.put("mid", mid);
		LOGGER.info("---count:" + count + "---act---" + tar.getAct() + "---"
				+ tar.getBehavior() + "---houseId---" + tar.getHouseId()
				+ "---dest---" + tar.getDest() + "---mid---" + mid);

		if (tar.getBehavior() == 2 && tar.getAct().equalsIgnoreCase("ias")) {
			count = dao.executeHql(
					"{CALL ModesubTest(1,:houseId,:dest ,:mid)}", params);
		} else if (tar.getBehavior() == 2
				&& tar.getAct().equalsIgnoreCase("hvac")) {
			count = dao.executeHql(
					"{CALL ModesubTest(2,:houseId,:dest ,:mid)}", params);
		}
		return count;
	}

	/**
	 * 
	 * @param houseId
	 * @return
	 */
	public List<Map> getDeviceList(String houseId) {
		StringBuilder sql = new StringBuilder(
				"SELECT a.ID,a.ModeName,a.oldId,e.UniqueName,f1.id as fid, ");
		sql.append("CASE WHEN d.DestType=0 then f.deviceName WHEN d.DestType=1 then g.GroupName WHEN d.DestType=3 then s.GroupName end as deviceName ");
		sql.append("from usermodemain a left join usermodesub b on a.ID = b.MID ");
		sql.append("left join modemacromain c on b.Dest = c.ID ");
		sql.append("left join modemacrosub d on c.id = d.mid ");
		sql.append("left join modeactlib e on d.ActlibID = e.id ");
		sql.append("left join modedevice f on d.Dest = f.id ");
		sql.append("left join modegroupmain g on d.Dest=g.ID ");
		sql.append("left join modescenemain s on d.Dest=s.ID ");
		sql.append("left join familymodepage f1 on a.id=f1.ModeId and f1.houseid = a.houseId ");
		sql.append("where a.HouseID = :houseId and b.Act='ApplyMacro'");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("houseId", houseId);
		List<Map> modeList = dao.executeSql(sql.toString(), param);
		return modeList;
	}

	public List<Map> findDestByMid(String mid) {
		List<Map>lMaps=null;
		HashMap<String, Object>params=new HashMap<String, Object>();
		params.put("mid",mid);
 String sql="Select DISTINCT modedevice.deviceName,modedevice.roomId,modemacrosub.Act ,modemacrosub.Dest,modemacrosub.DelaySec,modemacrosub.ActlibID,modemacrosub.Param,u.Dest Test,b.ActName,b.UniqueName,modedevice.modelId from usermodeMain INNER JOIN usermodesub on usermodeMain.ID=usermodesub.MID INNER JOIN modemacromain on usermodesub.Dest=modemacromain.ID INNER JOIN modemacrosub on modemacromain.ID=modemacrosub.MID INNER JOIN modedevice on modemacrosub.Dest=modedevice.id INNER JOIN modeactlib  b on modemacrosub.ActlibID=b.ID LEFT JOIN  usermodedevice u on  u.MID=usermodemain.ID and u.Dest=modemacrosub.Dest where usermodeMain.id=:mid and usermodesub.Act='ApplyMacro'";
		lMaps=dao.executeSql(sql, params);
	 return lMaps;
		
	}

	public List<Map> findActIdByMid(String string) {
		// TODO Auto-generated method stub
		String sql="select Dest from usermodesub where MID=:mid";
		HashMap<String, Object>params=new HashMap<String, Object>();
		return null;
	}

	public void update(ModeCommand command) {
		Usermodemain  main = null;
		Usermodesub sub=null;
		Modemacrosub msub=null;
		Usermodedevice usermodedevice=null;
		 Modemacromain modemacromain=null;
		HashMap<String, Object>params=new HashMap<String, Object>();
		HashMap<String, Object>params2=new HashMap<String, Object>();
	    main=dao.get(Usermodemain.class, command.getModeid());
		try {
			List<Command> commands = command.getShows();
			for (Command command2 : commands) {
				if (command2.isTest()) {
					main.setEnableCheckOnOff((short) 1);
			}
			}
			main.setHouseId(command.getHouseid());
			main.setPicName(command.getPicName());
			main.setRoomId(command.getModeroomid());
			main.setModeName(command.getModename());
			main.setPicName(command.getPicName());
			main.setDescription(command.getDescription());
			main.setOldId(getCount(command.getHouseid()));
			  dao.update(main);
			  HashMap<String, Object>pHashMap=new HashMap<String, Object>();
			  String sql2="from Modemacromain where modeId=:modeid";
			  pHashMap.put("modeid", main.getId());
		     List<Modemacromain>modemacromainlList= dao.find(sql2, pHashMap);
		     if(modemacromainlList.isEmpty()||modemacromainlList.size()==0){
		    	modemacromain=new Modemacromain(); 
				modemacromain.setHouseId(command.getHouseid());
				modemacromain.setMacroName("Macro"+"-"+main.getId()+"-"+getDefaultActionNumber("Macro", main.getId()));
				modemacromain.setModeId(main.getId());
				dao.create(modemacromain);
		     }else {
		    	    modemacromain=modemacromainlList.get(0);
			}
			String deString="from Modemacrosub where mid=(select id from Modemacromain where modeId=:modeid)";
//			String deString="delete from modemacrosub where MID=(SELECT ID from modemacromain where ModeID=:modeid)";
			HashMap<String, Object>parHashMap=new HashMap<String, Object>();
			parHashMap.put("modeid", command.getModeid());
			List<Modemacrosub> subList=dao.find(deString, parHashMap);
			if(!subList.isEmpty()&&subList.size()>0){
			for(Modemacrosub s:subList){
				dao.delete(s);                  //删除Modemacrosub
			} 
			}
			String deString2="from Usermodesub where mid=:modeid";
//			String deString="delete from modemacrosub where MID=(SELECT ID from modemacromain where ModeID=:modeid)";
			HashMap<String, Object>parHashMap2=new HashMap<String, Object>();
			parHashMap2.put("modeid", command.getModeid());
			List<Usermodesub> usermodesubs=dao.find(deString2, parHashMap2);
			if(!usermodesubs.isEmpty()&&usermodesubs.size()>0){
			for(Usermodesub s:usermodesubs){
				dao.delete(s);                  //删除usermodesub
			} 
			}
			String deString3="from Usermodedevice where mid=:modeid";
//			String deString="delete from modemacrosub where MID=(SELECT ID from modemacromain where ModeID=:modeid)";
			HashMap<String, Object>parHashMap3=new HashMap<String, Object>();
			parHashMap3.put("modeid", command.getModeid());
			List<Usermodedevice> usermodedevice3=dao.find(deString3, parHashMap3);
			if(!usermodedevice3.isEmpty()&&usermodedevice3.size()>0){
			for(Usermodedevice s:usermodedevice3){
				dao.delete(s);                  //删除Usermodedevice
			} 
			}
			sub=new Usermodesub();
			sub.setMid(main.getId());
			sub.setHouseId(command.getHouseid());
			
			sub.setAct("ApplyMacro");
//			sub.setDest(command2.getDeviceid());
			sub.setDest(modemacromain.getId());
			dao.create(sub);
           for(Command command2 : commands){
				
//				String sqlString="select * from modeactlib where id=:id";
//				params.put("id", command2.getActid());
			//	List<Modeactlib> sList=dao.executeSql(sqlString, params); 
//			   List<Map>lMaps=mapDao.executeSql(sqlString, params);
//				sub.setAct( lMaps.get(0).get("ActName").toString());
				msub=new Modemacrosub();
				Modeactlib lib=dao.get("Select m from Modeactlib m where m.id=:libId",
						QueryParameters.with("libId", new Long(command2.getActid())).parameters());
		    	LOGGER.info("updatecheck2--Modeactlib--:"+JSON.toJSONString(lib));			
				if(lib==null)//如果是scene
					msub.setAct("RecallDeviceScene");
				else{
					msub.setAct(lib.getActName());
			    	if(lib.getDataType()==2){
						//MoveToLevelWithOnOff拷贝Level=,TransTime=		
			    		msub.setExtension("Level="+lib.getExtentionSet()+",TransTime="+command2.getParam()); 
			    	}else if(lib.getDataType()==5){	    			    		
			    		//拆分色彩值
			    		List<String> out= Arrays.asList(StringUtils.split(command2.getParam(), ","));
			    		List<Integer> in =new ArrayList<Integer>();
			    		for(int i=0;i<6;i++){
			    			int pos=out.get(i).indexOf("=")+1; 	    			
			    			in.add(Integer.parseInt(out.get(i).substring(pos)));
//			    			s=s.substring(0,s.indexOf("=")+1);
			    		}    		
			    		String paramssString = "";int[] xyy=new int[3];
			    		RGBUtil.rgb2xyY(in.get(3),in.get(4),in.get(5),xyy);
			    		paramssString+="Hue="+in.get(0)/360*255;
			    		paramssString+=",Satulation="+in.get(1)/100*255;
			    		paramssString+=",ColorTemperature="+in.get(2)/100*65279;
			    		paramssString+=",ColorX="+xyy[0];
			    		paramssString+=",ColorY="+xyy[1];
			    		paramssString+=",TransTime=";
			    		LOGGER.info("color=params="+params);
			    		msub.setExtension(paramssString);
			    		
			    		
			    	}else if(command2.getActid()==9||command2.getActid()==10){
			    		//CIEBypassZone拷贝DeviceID
			    		msub.setExtension(command2.getParam());
			    	}
			    	else if(lib.getDataType() == 6) {
			    		msub.setPara(command2.getParam());
			    	}
				} 
				msub.setHouseId(command.getHouseid());
//				msub.setMid(main.getId());// ???
				msub.setMid(modemacromain.getId());// ???
				//msub.setAct(lMaps.get(0).get("ActName").toString());
				msub.setDelaySec(command2.getDelaySec());
				msub.setDest(command2.getDeviceid());
				msub.setActlibId(command2.getActid());
				msub.setDestType("0");
				msub.setParam(command2.getParam());
				msub.setName(command2.getName());
				dao.create(msub);
				  if(command2.isTest()){
				usermodedevice=new Usermodedevice();
				usermodedevice.setDest(command2.getDeviceid());
				usermodedevice.setHouseId(command.getHouseid());
				usermodedevice.setMid(main.getId());
				usermodedevice.setOldDestId(0);
				dao.create(usermodedevice);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	

//	public List<Map> getDeviceAction(String deviceId) {
//		String sql = "select id,UniqueName from modeActLib where deviceId=:deviceId";
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("deviceId", deviceId);
//		List<Map> dList = mapDao.executeSql(sql, paramMap);
//		return dList;
//	}
}
