package sy.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.controller.ApponlineinfoController;
import sy.dao.BaseDaoI;
import sy.model.Apponlineinfo;
import sy.service.ApponlineinfoControllerServiceI;

@Service("ApponlineinfoService")
public class ApponlineinfoControllerServiceImpl implements
		ApponlineinfoControllerServiceI {
	private static final Logger logger = Logger.getLogger(ApponlineinfoControllerServiceImpl.class);	
	private BaseDaoI<Apponlineinfo> ApponlineinfoDao;
	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	public BaseDaoI<Apponlineinfo> getApponlineinfo() {
		return ApponlineinfoDao;
	}
	@Autowired
	public void setApponlineinfo(BaseDaoI<Apponlineinfo> apponlineinfo) {
		ApponlineinfoDao = apponlineinfo;
	}

	

	@Override
	public int update(Apponlineinfo apponlineinfo) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("update apponlineinfo set ");
//		if(apponlineinfo.getUsername()!=null){
//			sql.append("username = :username,");
//			params.put("username", apponlineinfo.getUsername());
//		}
		if(apponlineinfo.getAppOs()!=null){
			sql.append("app_os = :appos,");
			params.put("appos", apponlineinfo.getAppOs());
		}
		if(apponlineinfo.getAppVersion()!=null){
			sql.append("app_version = :version,");
			params.put("version", apponlineinfo.getAppVersion());
		}
		if(apponlineinfo.getIpaddress()!=null){
			sql.append("IPaddress = :ipaddress, ");
			params.put("ipaddress",apponlineinfo.getIpaddress());
		}
		if(apponlineinfo.getInformation()!=null){
			sql.append("information = :information ");
			params.put("information",apponlineinfo.getInformation());
		}
		sql.append("where username = :username ");
//		sql.append("where houseIeee = :houseIeee ");
		params.put("username",apponlineinfo.getUsername());
		int t = ApponlineinfoDao.executeSql2(sql.toString(), params);
		return t;
//		ApponlineinfoDao.update(apponlineinfo);
//		return (List<Apponlineinfo>) apponlineinfo;
	}

	@Override
	public int delete(Apponlineinfo apponlineinfo) {
		StringBuffer hql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		int t = ApponlineinfoDao.executeHql(hql.toString(), params);
		return t;
	}

	@Override
	public Apponlineinfo add(Apponlineinfo apponlineinfo) {
//		StringBuffer hql = new StringBuffer();
//		Map<String, Object> params = new HashMap<String, Object>();
//		int t = ApponlineinfoDao.executeHql(hql.toString(), params);
		ApponlineinfoDao.save(apponlineinfo);
		return apponlineinfo;
	}
	
	@Override
	public void addbatchapp(List<Apponlineinfo> onlineApp,List<Apponlineinfo> offlineApp) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (list != null && list.size() > 0) {
			for (Apponlineinfo apponlineinfo : list) { 
				params.clear();
				params.put("username", apponlineinfo.getUsername());
				StringBuffer hql = new StringBuffer();
				hql.append("update Apponlineinfo a set a.isonline = '0' where a.username not in ( :username )");
				ApponlineinfoDao.executeHql(hql.toString(), params);
			}
			
		} else {
			StringBuffer hql3 = new StringBuffer();
			hql3.append("update Apponlineinfo a set a.isonline = '0' where a.isonline = '1'");
			ApponlineinfoDao.executeHql(hql3.toString());
		} */
		
		/*StringBuffer hql3 = new StringBuffer();
		hql3.append("update Apponlineinfo a set a.isonline = '0' where a.isonline = '1'");
		ApponlineinfoDao.executeHql(hql3.toString());*/
		String sql = "update Apponlineinfo a set a.isonline ='0' where a.isonline = '1' and username in (";
		if (offlineApp != null && offlineApp.size() > 0) {
    		for (Apponlineinfo apponlineinfo:offlineApp) { 
    			sql += "'"+ apponlineinfo.getUsername() + "',";
        	}
    		sql = sql.substring(0,sql.length()-1);
    		sql += ")";
    	} else {
    		sql = sql + "'')";
    	}
		ApponlineinfoDao.executeSql2(sql);		
		if (onlineApp != null && onlineApp.size() > 0) {
    		for (Apponlineinfo apponlineinfo : onlineApp) { 
    			params.clear();
    			params.put("username", apponlineinfo.getUsername());
    			StringBuffer hql = new StringBuffer();
    			hql.append("select a from Apponlineinfo a where a.username =:username");
    			List<Apponlineinfo> app=ApponlineinfoDao.find(hql.toString(), params);
    			if(app!=null &&app.size()==0){
    				/*apponlineinfo.setReadstate("2");
    				apponlineinfo.setIsonline("1");
        			this.add(apponlineinfo);*/
    			}else{
        			params.put("ipaddress", apponlineinfo.getIpaddress());
    				StringBuffer hql2 = new StringBuffer();
	    			hql2.append("update Apponlineinfo a set a.isonline = '1' ,a.ipaddress = :ipaddress where a.username =:username ");
	    			ApponlineinfoDao.executeHql(hql2.toString(), params);
    			}
        	}
    	} 
	}
	
	@Override
	public void addbatchapp(List<Apponlineinfo> list, String xmppIp) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("update apponlineinfo inner join proxyserver on apponlineinfo.houseIEEE = proxyserver.houseIEEE set apponlineinfo.isonline = '0' where apponlineinfo.isonline = '1'  and proxyserver.serverIp = :serverIp ");
		StringBuilder userNameStr = new StringBuilder("(");
		
		if (!list.isEmpty()) {
    		for (Apponlineinfo apponlineinfo:list) {
    			userNameStr.append("'").append(apponlineinfo.getUsername()).append("',");
    			//更新在线状态及ip地址
    			params.put("ipaddress", apponlineinfo.getIpaddress());
    			params.put("username", apponlineinfo.getUsername());
				StringBuilder onSql = new StringBuilder();
    			onSql.append("update Apponlineinfo a set a.isonline = '1' ,a.ipaddress = :ipaddress where a.username =:username ");
    			logger.info("得到的onSql语句是：-----》：" + onSql);
    			ApponlineinfoDao.executeHql(onSql.toString(), params);
        	}
    		userNameStr = userNameStr.deleteCharAt(userNameStr.length() - 1).append(")");
    		sql.append(" and username not in ").append(userNameStr);
    		logger.info("得到的用户名语句是：-----》：" + userNameStr);
    	} 
		params.clear();
		params.put("serverIp", xmppIp);
		logger.info("得到的sql语句是：-----》：" + sql);
		ApponlineinfoDao.executeSql2(sql.toString(), params);
	}
	
	
	
	@Override
	public int getCount(Map<String, Object> house){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("Select Count(*) as aCount from Apponlineinfo h INNER JOIN house b on h.houseIEEE = b.houseIEEE");
		hql.append(" INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id = :userid) c ON b.client_id = c.client_id");
		hql.append(" where 1=1 ");
		params.put("userid", house.get("userid"));
//		StringBuilder hql = new StringBuilder("Select Count(*) as aCount from House h");
		if(house.get("sText")!=null){//搜索
			String sText = (String) house.get("sText");
			if(StringUtils.isNotBlank(sText)){//搜索
				hql.append("and  h.houseIeee like :sText ");	
				hql.append(" or h.username like :sText");	
				hql.append(" or h.title like :sText");	
//				hql.append("or h.clientCode like :sText ");		
//				hql.append("or h.regionCode like :sText ");		
//				hql.append("or h.name like :sText) ");	
				params.put("sText", "%"+sText+"%");
			}
				
		}
		List<Map> cList=mapDao.executeSql(hql.toString(), params);
		return ((BigInteger)(cList.get(0).get("aCount"))).intValue();
	}
	/*云端手动新增修改广告*/
	@Override
	public int update1(Apponlineinfo apponlineinfo, Map<String, Object> appMap) {
		Map<String, Object> params = new HashMap<String, Object>();
//		String[] houseIeeeArr = Houseservice.getHouseIeee().split(","); 
		
		String[] houseIeeeArr = ((String)appMap.get("houseIEEEs")).split(",");
		int a=houseIeeeArr.length-1;
		for(int i=0;i<=a;i++){
			String sql1 = "update apponlineinfo set houseIeee =:houseIeee where houseIeee = :houseIeee";
			params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
			int t = ApponlineinfoDao.executeSql2(sql1, params);
			if(t==0){
				String sql2="insert into apponlineinfo(houseIeee,readstate,username,adurl,title,createtime) value(:houseIeee,:readstate,:username,:adurl,:title,:createtime)";
				params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
				params.put("username",  apponlineinfo.getUsername());
				params.put("adurl",  apponlineinfo.getAdurl());
				params.put("readstate",  apponlineinfo.getReadstate());
				params.put("title",  apponlineinfo.getTitle());
				params.put("createtime",  apponlineinfo.getCreatetime());
				int t1 = ApponlineinfoDao.executeSql2(sql2,params);
			}else{
				String sql = "update apponlineinfo  set readstate = :readstate,adurl=:adurl,createtime = :createtime,title=:title where 1=1 and houseIeee = :houseIeee";
				params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
				params.put("adurl",  apponlineinfo.getAdurl());
				params.put("readstate",  apponlineinfo.getReadstate());
				params.put("createtime", apponlineinfo.getCreatetime());
				params.put("title", apponlineinfo.getTitle());
				int t2= ApponlineinfoDao.executeSql2(sql, params);
			}
			params.clear();
		}
		return 1;
		
	}
	/*云端查询广告*/
	@Override
	public List<Apponlineinfo> find1(String startRow, String pageSize,
			String orderBy, String userid, Apponlineinfo apponlineinfo) {
		StringBuffer sql = new StringBuffer();
		if (apponlineinfo.getHouseIeee() == null || apponlineinfo.getHouseIeee().equals("")) {
			sql.append("select a.* from apponlineinfo a INNER JOIN house b on a.houseIEEE = b.houseIEEE ");
			sql.append("INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id = :userid) c ON b.client_id = c.client_id");
//			hql.append("select b.username,b.adurl,b.readstate,a.houseIeee from house a left join advertisement b on a.houseIeee =b.houseIeee ");
			sql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userid", userid);
			List<Apponlineinfo> t = ApponlineinfoDao.executeSql(sql.toString(),params);
			return t;
		} else {
			sql.append("select a.* from apponlineinfo a INNER JOIN house b on a.houseIEEE = b.houseIEEE ");
			sql.append("INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id = :userid) c ON b.client_id = c.client_id");
			sql.append(" where a.houseIeee like :houseIeee or a.username like :username or a.title like :title");
//			hql.append("select b.username,b.adurl,b.readstate,a.houseIeee from house a left join advertisement b  on a.houseIeee =b.houseIeee where a.houseIeee like :houseIeee");
			sql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userid", userid);
			params.put("houseIeee", "%"+apponlineinfo.getHouseIeee() + "%");
			params.put("username", "%"+apponlineinfo.getHouseIeee() + "%");
			params.put("title", "%"+apponlineinfo.getHouseIeee() + "%");
			List<Apponlineinfo> t = ApponlineinfoDao.executeSql(sql.toString(), params);
			return t;
		}
	}

	
	/*APP修改广告读取状态*/
	@Override
	public int appupdatereadstate(Apponlineinfo apponlineinfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		/*String sql1 = "update apponlineinfo set username =:username where username = :username";
		params.put("houseIeee", apponlineinfo.getHouseIeee());
		int t = ApponlineinfoDao.executeSql2(sql1, params);
		if(t==0){
			String sql2="insert into apponlineinfo(houseIeee,username) value(:houseIeee,:username)";
			params.put("houseIeee",apponlineinfo.getHouseIeee() );
			params.put("username",  apponlineinfo.getUsername());
			int t1 = ApponlineinfoDao.executeSql2(sql2,params);
		}else{
			String sql = "update apponlineinfo  set readstate = 1,lasttime = :lasttime where houseIeee = :houseIeee and username=:username";
			params.put("houseIeee",apponlineinfo.getHouseIeee());
			params.put("lasttime", apponlineinfo.getLasttime());
			params.put("username",  apponlineinfo.getUsername());
			int t2= ApponlineinfoDao.executeSql2(sql, params);
		}
		params.clear();*/		
		String sql = "update apponlineinfo  set readstate = 1,lasttime = :lasttime where houseIeee = :houseIeee and username=:username";
		params.put("houseIeee",apponlineinfo.getHouseIeee());
		params.put("lasttime", apponlineinfo.getLasttime());
		params.put("username",  apponlineinfo.getHouseIeee()+"_"+apponlineinfo.getUsername());
		int t2= ApponlineinfoDao.executeSql2(sql, params);
		return 1;
		
	}
	
	/*APP查询广告*/
	@Override
	public List<Apponlineinfo> find(Apponlineinfo apponlineinfo) {
		 StringBuffer hql = new StringBuffer();
		hql.append("from Apponlineinfo a where a.houseIeee = :houseIeee and  a.username = :username and a.readstate=0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", apponlineinfo.getHouseIeee());
		params.put("username",  apponlineinfo.getHouseIeee()+"_"+apponlineinfo.getUsername());
		List<Apponlineinfo> t = ApponlineinfoDao.find(hql.toString(), params);
		return t;
	}
	@Override
	public List<Apponlineinfo> findList(Apponlineinfo apponlineinfo)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", apponlineinfo.getHouseIeee()+"_"+apponlineinfo.getUsername());
		hql.append("from Apponlineinfo a where a.username = :username ");
		List<Apponlineinfo> t = ApponlineinfoDao.find(hql.toString(), params);
		return t;
	}
	

	@Override
	public List<Map> find2(Map app) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Apponlineinfo a where a.houseIeee = :houseIeee and isonline = '1'");
		params.put("houseIeee", app.get("houseIeee"));
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		if(list != null) {
			return list;
		}
		return null;
	}

}
