package org.smarthome.services;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jbx.test.FileTest;
import org.smarthome.dal.StartDAO;
import org.smarthome.domain.Hardhistory;
import org.smarthome.domain.Releasehistory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.util.PropertiesUtil;
import sy.util.TestLog4j;

import com.alibaba.fastjson.JSON;

@Service("Releasehistory")
public class ReleasehistoryServiceImpl implements ReleasehistoryServiceI {
	private static final Logger log = Logger.getLogger(ReleasehistoryServiceImpl.class);
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

	@Override
	public Releasehistory keyUpdate(Releasehistory data) {
		//先删除硬件版本
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "delete h from Hardhistory h where h.releaseHistoryId =:id";
		params.put("id", data.getId());
		dao.executeHql(hql, params);
		//更新软件版本
		Releasehistory update=dao.get(Releasehistory.class, data.getId());
		BeanUtils.copyProperties(data,update,new String[]{"id","md5"});
		dao.update(update);
		//更新硬件版本
		List<String> hw=JSON.parseArray(data.getHwVersion(), String.class);
		for(String s:hw)
		dao.saveOrUpdate(new Hardhistory(s,s,data.getId()));		
		return data;	
		// TODO Auto-generated method stub		
	}
	
	@Override
	public Releasehistory saveOrUpdate(Releasehistory data) {	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Ver1", data.getVer1());
		params.put("Ver2", data.getVer2());
		params.put("Ver3", data.getVer3());
		params.put("Ver4", data.getVer4());
		String hql = "select r.* from releasehistory r where r.ver1=:Ver1 and r.ver2=:Ver2 and r.ver3=:Ver3 and r.ver4=:Ver4";
		List<Releasehistory> list =dao.executeSql(hql, params, Releasehistory.class);
		if(list.size()!=0){
			Releasehistory r = new Releasehistory();
			return r;
		}
		else{
			dao.saveOrUpdate(data);//添加
			List<String> hw=JSON.parseArray(data.getHwVersion(), String.class);
			for(String s:hw)
			dao.saveOrUpdate(new Hardhistory(s,s,data.getId()));		
			return data;
		}
		// TODO Auto-generated method stub		
	}

	@Override
	public Releasehistory delete(Releasehistory data) {
		//先删除硬件版本
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "delete h from Hardhistory h where h.releaseHistoryId =:id";
		params.put("id", data.getId());
		dao.executeHql(hql, params);
		//删除软件版本
		Releasehistory del=dao.get(Releasehistory.class, data.getId());
//		String delfile="D:/apache-ftpserver-1.0.6/res/home/release/"+del.getFileName();
		String delfile = PropertiesUtil.getProperty("versionPath") + del.getFileName();
//		log.info("--------------delfilepath:" + delfile);
		FileTest.delFile(delfile);
		dao.delete(del);
		return data;	
		// TODO Auto-generated method stub	
	}
	
	@Override
	public int getCount(Map<String, Object> device){
		String hql ="Select Count(*) as aCount from Releasehistory r";
		if(device.get("search")!=null){//搜索
			String s=device.get("search").toString();
			hql+="  where (r.Version like '%"+s
				+"%' or  r.HwVersion like '%"+s+"%')";			
		}
		TestLog4j.testInfo("getCount----"+hql+"params----"+device);
		List<Map> cList=dao.executeSql(hql, null);
		return ((BigInteger)(cList.get(0).get("aCount"))).intValue();
	}
	@Override
	public List<Releasehistory> findList(Map<String, Object> device) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "Select r.* from Releasehistory r ";
		if(device.get("id")!=null&&!device.get("id").equals(0)){//检索单条记录
			hql+=" where r.id=:id";
			params.put("id", Long.valueOf(device.get("id").toString()));
		}
		if(device.get("search")!=null){//搜索
			String s=device.get("search").toString();
			hql+="  where (r.Version like '%"+s
				+"%' or  r.HwVersion like '%"+s//+"%')";
				+"%' or  r.description like '%"+s+"%')";
		}
		if(device.get("orderBy")!=null)//排序
			hql+=" order by "+device.get("orderBy");						
		else 
			hql+=" order by r.updateTime desc";
		if(device.get("pageIndex")!=null&&device.get("pageSize")!=null){//添加分页
			int rowstart=Integer.valueOf(device.get("pageIndex").toString());
			int rowsize=Integer.valueOf(device.get("pageSize").toString());
			rowstart=rowstart*rowsize;
			hql+=" LIMIT "+rowstart+","+rowsize;
		}
		TestLog4j.testInfo("findList----"+hql+"params----"+params);
		List<Releasehistory> dg=dao.executeSql(hql,params,Releasehistory.class);
		return dg;
	}
	@Override
	public List<Releasehistory> findhouse(Map<String, Object> device) {
		Map<String, Object> params = new HashMap<String, Object>();
		//String hql = "from Device t where t.houseIeee='11' ";
		String hql = "Select r.* from Releasehistory r,Hardhistory h1,House h2 ";
		hql += " where r.id=h1.releaseHistoryId and h1.hwVersion=h2.HW_version and h2.houseIeee =:houseIeee " 
			+"order by r.ver1 desc,r.ver2 desc,r.ver3 desc,r.ver4 desc";
		params.put("houseIeee", device.get("HouseIEEE"));
		TestLog4j.testInfo("find----"+hql+"params----"+params);
		List<Releasehistory> dg=dao.executeSql(hql,params,Releasehistory.class);	
		return dg;
		// TODO Auto-generated method stub		
	}
	@Override
	public Releasehistory get(Map<String, Object> device){		
		return dao.get(Releasehistory.class, Long.valueOf(device.get("id").toString()));
	}
	@Override
	public Releasehistory find(Map<String, Object> device) {
		Map<String, Object> params = new HashMap<String, Object>();
		//String hql = "from Device t where t.houseIeee='11' ";
		String hql = "Select r.* from Releasehistory r,Hardhistory h ";
		hql += "where r.id=h.releaseHistoryId and h.hwVersion =:HwVersion order by r.ver1 desc,r.ver2 desc,r.ver3 desc,r.ver4 desc";
		params.put("HwVersion", device.get("HWVersion"));
		TestLog4j.testInfo("find----"+hql+"params----"+params);
		List<Releasehistory> dg=dao.executeSql(hql,params,Releasehistory.class);
		if(dg.isEmpty()) {
			return null;
		}else {	
			return dg.get(0);
		}
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql,Map<String, Object> device,Map<String, Object> params) {
		if (device!= null){
			params.put("HwVersion", device.get("HWVersion"));
			hql += "where r.id=h.releaseHistoryId and h.hwVersion =:HwVersion order by r.ver1 desc,r.ver2 desc,r.ver3 desc,r.ver4 desc";
		}
		return hql;
	}
	
	@Override
    public Releasehistory find2(Map<String, Object> device) {
        Map<String, Object> params = new HashMap<String, Object>();
        //String hql = "from Device t where t.houseIeee='11' ";
        String hql = "Select r.* from Releasehistory r,Hardhistory h ";
        hql += "where r.id=h.releaseHistoryId and h.hwVersion =:HwVersion order by r.ver1 desc,r.ver2 desc,r.ver3 desc,r.ver4 desc";
        params.put("HwVersion", device.get("HWVersion"));
        TestLog4j.testInfo("find----"+hql+"params----"+params);
        List<Releasehistory> dg=dao.executeSql(hql,params,Releasehistory.class);
        //((Map)aList.get(0)).put("a",3);//修改值
        // adapter.notifyDataSetChanged();//刷新列表
         
        if(dg.isEmpty()) {
            return null;
        }else {
            if(StringUtils.isNoneBlank(dg.get(0).getFileName())){
                String url = "http://"+device.get("serverip")+":"+ device.get("port") +"/zigBeeDevice/ReleasehistoryController/getUpdateVersion.do?file="+ dg.get(0).getFileName() +"&callback="+device.get("callback2")+"&encodemethod="+device.get("encodemethod")+"&sign="+device.get("sign")+"&houseIeeeSecret="+device.get("houseIeeeSecret");
                dg.get(0).setDownloadInfo(url);
            }       
            return dg.get(0);
        }
        // TODO Auto-generated method stub      
    }
}
