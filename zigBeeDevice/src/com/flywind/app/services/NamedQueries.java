package com.flywind.app.services;


public class NamedQueries {

	public static final String Moderoom_houseId = "select r from Moderoom r where r.houseId = :houseId";	
	public static final String Usermodesub_houseId = "select u from Usermodesub u , Usermodemain m "
													+ " where u.mid=m.id and m.houseId = :houseId order by u.id";
	public static final String Usermodemain_houseId = "select m from Usermodemain m where m.houseId = :houseId order by m.id";
	public static final String Usermodedevice_houseId = "select d from Usermodedevice d where d.houseId=:houseId order by d.id";
	
	public static final String Modeschemesub_houseId= "select u from Modeschemesub u , Modeschememain m "
													+ " where u.mid=m.id and m.houseId = :houseId order by u.id";
	public static final String Modeschememain_houseId= "select m from Modeschememain m where m.houseId = :houseId order by m.id";
	
	public static final String Modemacrosub_houseId= "select u from Modemacrosub u , Modemacromain m , Modedevice d"
													+ " where u.mid=m.id and m.houseId = :houseId and u.destType=0 and u.dest=d.id and u.houseId=d.houseId order by u.id";
	public static final String Modemacrosub_houseId2= "select u from Modemacrosub u , Modemacromain m , Modegroupmain d"
													+ " where u.mid=m.id and m.houseId = :houseId and u.destType=1 and u.dest=d.id and u.houseId=d.houseId order by u.id";
	public static final String Modemacrosub_houseId3= "select u from Modemacrosub u , Modemacromain m , Modescenemain d"
													+ " where u.mid=m.id and m.houseId = :houseId and u.destType=3 and u.dest=d.id and u.houseId=d.houseId order by u.id";
	public static final String Modemacromain_houseId= "select m from Modemacromain m where m.houseId = :houseId  order by m.id";	
	
	public static final String Modeiassub_houseId= "select u from Modeiassub u where u.houseId = :houseId order by u.id";
	public static final String Modeiasmain_houseId= "select m from Modeiasmain m , Modedevice d "
													+ " where m.houseId = :houseId and m.modeEpId=d.id and m.houseId=d.houseId order by m.id";
	
	public static final String Modehvacsub_houseId= "select u,a from Modehvacsub u , Modehvacmain m , Modemacrosub a "
													+ " where u.mid=m.mainId and a.mid=u.dest and m.houseId = :houseId  order by u.id";
	public static final String Modehvacmain_houseId= "select m from Modehvacmain m , Modedevice d "
													+ " where m.houseId = :houseId and m.modeEpId=d.id and m.houseId=d.houseId  order by m.id";		
	
	public static final String Modegroupsub_houseId = "Select new Modegroupsub(u.id,u.mid,u.houseId,u.deviceId,u.deviceOldId,d.roomId,d.deviceName)"
													+ " from Modegroupsub u , Modegroupmain m , Modedevice d"
													+ " where u.mid=m.id and m.houseId = :houseId and u.deviceId=d.id and u.houseId=d.houseId  order by u.id";													
	public static final String Modegroupmain_houseId = "select m from Modegroupmain m where m.houseId = :houseId  order by m.id";
	
	public static final String Modescenesub_houseId ="Select new Modescenesub(u.id,u.mid,u.houseId,u.deviceId,u.deviceOldId,u.actlibId,u.transTime,lib.scenePara,u.para)"
													+ " from Modescenesub u , Modescenemain m , Modedevice d,Modeactlib lib"
													+ " where u.mid=m.id and m.houseId = :houseId and u.deviceId=d.id and u.houseId=d.houseId and u.actlibId=lib.id  order by u.id";	
	public static final String Modescenemain_houseId= "select m from Modescenemain m where m.houseId = :houseId  order by m.id";
	
	public static final String Modeschemesub_macroId= "select u from Modeschemesub u where u.dest = :macroId and (u.act=:act1 or u.act=:act2)";
	public static final String Modeschememain_macroId = "select m from Modeschemesub u , Modeschememain m "
														+ "where u.mid=m.id and u.dest = :macroId and (u.act=:act1 or u.act=:act2)";	

}
