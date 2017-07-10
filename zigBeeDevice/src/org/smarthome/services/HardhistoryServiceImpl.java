package org.smarthome.services;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smarthome.dal.StartDAO;
import org.smarthome.domain.Hardhistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zbHouse.util.TestLog4j;

@Service("Hardhistory")
public class HardhistoryServiceImpl implements HardhistoryServiceI {

	private StartDAO dao;
    public StartDAO getDao() {
		return dao;
	}
    @Autowired
	public void setDao(StartDAO dao) {
		this.dao = dao;
	} 

	@Override
	public Hardhistory keyUpdate(Hardhistory data){
		String hql="Select Count(*) as aCount from Hardhistory h where h.releaseHistoryId=0 and h.hwVersion=:hwVersion";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hwVersion", data.getHwVersion());
//		hql+=" and h.hwVersion='"+data.getHwVersion()+"'";
		List<Map> cList=dao.executeSql(hql, params);
		int aCount=((BigInteger)(cList.get(0).get("aCount"))).intValue();
		TestLog4j.testInfo("keyUpdate----"+aCount);
		if(aCount>1)throw new RuntimeException();//修改只能1条
		return dao.update(data);
		// TODO Auto-generated method stub		
	}
	
	@Override
	public Hardhistory saveOrUpdate(Hardhistory data) {
		String hql="Select Count(*) as aCount from Hardhistory h where h.releaseHistoryId=0 and h.hwVersion=:hwVersion";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hwVersion", data.getHwVersion());
//		hql+=" and h.hwVersion='"+data.getHwVersion()+"'";
		List<Map> cList=dao.executeSql(hql, params);
		int aCount=((BigInteger)(cList.get(0).get("aCount"))).intValue();
		TestLog4j.testInfo("saveOrUpdate----"+aCount);
		if(aCount>0)throw new RuntimeException();//添加只能0条
		return dao.saveOrUpdate(data);	
		// TODO Auto-generated method stub	
	}

	@Override
	public Hardhistory delete(Hardhistory data) {

		dao.delete(data);
		return data;
		// TODO Auto-generated method stub	
	}
	
	@Override
	public int getCount(Map<String, Object> hard){
		String hql ="Select Count(*) as aCount from Hardhistory h  where h.releaseHistoryId=0";
		Map<String, Object> params = new HashMap<String, Object>();
		if(hard.get("search")!=null){//搜索
			String s=hard.get("search").toString();
//			hql+=" and (h.extention like '%"+s
//				+"%'  or  h.hwVersion like '%"+s+"%')";	
			hql += " and (h.extention like :searchText or h.hwVersion like :searchText)";
			params.put("searchText", "%"+s+"%");
		}
		TestLog4j.testInfo("getCount----"+hql+"params----"+hard);
		List<Map> cList=dao.executeSql(hql, params);
		return ((BigInteger)(cList.get(0).get("aCount"))).intValue();
	}	
	@Override
	public List<Hardhistory> findList(Map<String, Object> hard) {
		Map<String, Object> params = new HashMap<String, Object>();
		//String hql = "from Device t where t.houseIeee='11' ";
		String hql = "Select h.* from Hardhistory h ";
		if(hard.get("id")!=null&&!hard.get("id").equals(0)){//检索单条记录 
			hql+=" where h.id=:id ";
			params.put("id", Long.valueOf(hard.get("id").toString()));
		}else hql+="  where h.releaseHistoryId=0 ";//多条记录
		if(hard.get("search")!=null){//搜索
			String s=hard.get("search").toString();
//			hql+="  and (h.extention like '%"+s
//				+"%' or  h.hwVersion like '%"+s+"%')";	
			hql += " and (h.extention like :searchText or h.hwVersion like :searchText)";
			params.put("searchText", "%"+s+"%");
		}
		if(hard.get("orderBy")!=null)//排序
			hql+=" order by "+hard.get("orderBy");
		else
			hql+=" order by h.updateTime desc";
		if(hard.get("pageIndex")!=null&&hard.get("pageSize")!=null){//添加分页
			int rowstart=Integer.valueOf(hard.get("pageIndex").toString());
			int rowsize=Integer.valueOf(hard.get("pageSize").toString());
			rowstart=rowstart*rowsize;
			hql+=" LIMIT "+rowstart+","+rowsize;			
		}
		TestLog4j.testInfo("findList----"+hql+"params----"+params);
		List<Hardhistory> dg=dao.executeSql(hql,params,Hardhistory.class);
		return dg;
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql,Map<String, Object> hard,Map<String, Object> params) {
		if (hard!= null){
			params.put("daemonDeviceId", hard.get("type"));
			hql += "where t.daemonDeviceId =:daemonDeviceId";
		}
		return hql;
	}
	@Override
	public int deletehardhistory(String id) {
		// TODO Auto-generated method stub
//		Releasehistory releasehistory=dao.get(Releasehistory.class, hardhistory.getReleaseHistoryId());
//		hardhistory=dao.get(Hardhistory.class, hardhistory.getId());
		String sql = "select b.id rid from hardhistory a inner join releasehistory b on a.ReleaseHistoryId = b.ID where a.HwVersion=:hwVersion";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hwVersion", id);
		List<Map> hList = dao.executeSql(sql, param);
		if (hList.isEmpty()) {
			String delSql = "delete from hardhistory where HwVersion=:hwVersion";
			dao.executeHql(delSql, param);
			return 1;
		}
		else
			return 0;
	}

}
