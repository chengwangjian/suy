package org.smarthome.services;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.smarthome.dal.GenericDao;
import org.smarthome.domain.BfTestFile;
import org.smarthome.domain.FileAppinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;

import com.flywind.app.dal.StartDAO;


@Service("BfTestFile")
public class BfTestFileServiceImpl implements BfTestFileServiceI {

	private static final Logger LOGGER = Logger.getLogger(BfTestFileServiceImpl.class);
	private GenericDao<BfTestFile, ?> dao;
	private StartDAO dao2;
	
    public StartDAO getDao2() {
		return dao2;
	}
    @Autowired
	public void setDao2(StartDAO dao2) {
		this.dao2 = dao2;
	}  
    public BaseDaoI<Map> basefileBaseDaoI;
	public BaseDaoI<Map> getBasefileBaseDaoI() {
		return basefileBaseDaoI;
	}
	@Autowired
	public void setBasefileBaseDaoI(BaseDaoI<Map> basefileBaseDaoI) {
		this.basefileBaseDaoI = basefileBaseDaoI;
	}
	public GenericDao<BfTestFile, ?> getDao() {
		return dao;
	}
    @Autowired
	public void setDao(GenericDao<BfTestFile, ?> dao) {
		this.dao = dao;
	} 

	@Override
	public BfTestFile keyUpdate(BfTestFile data){
		dao.merge(data);
		return data;
		
		// TODO Auto-generated method stub		
	}
	
	@Override
	public BfTestFile saveOrUpdate(BfTestFile data) {
		dao.create(data);
		return data;
			
		// TODO Auto-generated method stub	
	}

	@Override
	public BfTestFile delete(BfTestFile data) {

		dao.delete(data);
		return data;
		// TODO Auto-generated method stub	
	}
	
	@Override
	public List<BfTestFile> findList(Map<String, Object> hard) {
		String hql="select t from BfBugInfo t where t.id=769";
		List<BfTestFile> dg=dao.find(hql);
		return dg;
		// TODO Auto-generated method stub		
	}
	private String addWhere(String hql,Map<String, Object> hard,Map<String, Object> params) {
		if (hard!= null){
			params.put("daemonDeviceId", hard.get("type"));
			hql += " where t.daemonDeviceId =:daemonDeviceId";
		}
		return hql;
	}
	@Override
	public FileAppinfo savefile(FileAppinfo fileAppinfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//fileAppinfo.setSerIp(getServerIp(fileAppinfo.getHouseIeee()));
		System.out.println(fileAppinfo.getOpdatetime());
		String tableName = "file_appinfo_"+(1900+fileAppinfo.getOpdatetime().getYear());
		try{
			//插入数据语句
			StringBuilder sql = new StringBuilder("insert into ");
			sql.append(tableName);
			sql.append("(houseIEEE,device_type,sys_ver,opdatetime,file_name,user_name,error_no,oderfilename,version_sdk,proxy_server,cpu,package,");
			sql.append("model,server_ip,brand,resolution,memory,app_version,file_size,version_release)");

			sql.append("values('")
			.append(fileAppinfo.getHouseIeee()+"','").append(fileAppinfo.getDeviceType()+"','").append(fileAppinfo.getSysVer()+"','")
			.append(sdf.format(fileAppinfo.getOpdatetime())+"','").append(fileAppinfo.getFileName()+"','").append(fileAppinfo.getUserName()+"','")
			.append(fileAppinfo.getErrorNo()+"','").append(fileAppinfo.getOderfilename()+"','").append(fileAppinfo.getVersionSdk()+"','")
			.append(fileAppinfo.getProxyServer()+"','").append(fileAppinfo.getCpu()+"','").append(fileAppinfo.getPackages()+"','")
			.append(fileAppinfo.getModel()+"','").append(fileAppinfo.getSerIp()+"','").append(fileAppinfo.getBrand()+"','")
			.append(fileAppinfo.getResolution()+"','").append(fileAppinfo.getMemory()+"','").append(fileAppinfo.getAppVersion()+"','")
			.append(fileAppinfo.getFileSize()+"','").append(fileAppinfo.getVerRelease()+"')");
			
			//创建表的sql语句
			StringBuilder sql2 = new StringBuilder("Create TABLE if not exists ").append(tableName).append(" LIKE file_appinfo");
			try{
				dao2.executeSql(sql.toString());
			}catch(Exception e){
				String msg = e.getMessage();
				//当表不存在的时候，创建表然后插入数据
				if(msg.indexOf(tableName.toLowerCase())!=-1){
					dao2.executeSql(sql2.toString());
					dao2.executeSql(sql.toString());
				}else{
					LOGGER.info("save "+tableName+"exception:",e);
					return null;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileAppinfo;
	}
	@Override
	public List<Map> getappdbugfileinfo(Map<String, Object> conditionMap,int startRow, int pageSize,String count) {
		String sql2="";
		int year=0;
		List<Map> t =null;
		if(conditionMap!=null&&StringUtils.isNotBlank((String) conditionMap.get("sql"))){
			sql2=(String) conditionMap.get("sql");
			year=(Integer) conditionMap.get("year");
			sql2=sql2.replace("sss","%");
			 if(sql2.endsWith("and")){
				    int	index=sql2.lastIndexOf("and");
				    sql2=sql2.substring(0, index);
			 }else if(sql2.endsWith("or")){	
				    int index=sql2.lastIndexOf("or");
	 			    sql2=sql2.substring(0, index);
	 			}
			sql2="SELECT * FROM file_appinfo_"+year+sql2;
			if(pageSize != 0)
				sql2+=" order by opdatetime desc limit "+startRow+","+pageSize;	
			else
				sql2+=" order by opdatetime desc ";
			t =basefileBaseDaoI.executeSql(sql2);
		}else {
		StringBuilder sql = new StringBuilder();
		String tableName = "file_appinfo_"+Calendar.getInstance().get(Calendar.YEAR);
	//	List<String> tableNameList = new ArrayList<String>(); //跨年查询时使用
		sql.append("SELECT * FROM ");
		if(conditionMap!=null&&!"".equals(conditionMap.get("opdatetimestar"))){
			try {
				int yearStart = 1900+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)conditionMap.get("opdatetimestar")).getYear();
				int yearEnd = 1900+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)conditionMap.get("opdatetimeend")).getYear();
				if(yearEnd!=yearStart)
					tableName = "file_appinfo_"+yearEnd;
				else
					tableName = "file_appinfo_"+yearStart;
			} catch (ParseException e) {
				LOGGER.info(e);
			}
		}
		sql.append(tableName);
		sql.append(" where ");
		Map<String, Object> params = new HashMap<String, Object>();
		boolean one=false;
		boolean two=false;
		if (conditionMap != null && !conditionMap.isEmpty()) {
			Iterator itor = conditionMap.keySet().iterator();
			while (itor.hasNext()) {
				String key = (String) itor.next();
				if (!key.equals("opdatetimeend")&&!key.equals("opdatetimestar")&&StringUtils.isNotBlank((String)conditionMap.get(key))) {
					if("houseIeeee".equalsIgnoreCase(key)){
						key = "houseIEEE";
						sql.append("houseIEEE").append("=:"+key+" and ");
						params.put(key,conditionMap.get("houseIeeee"));
					}else if("model".equalsIgnoreCase(key)){
						sql.append(key).append(" like :"+key+" and ");
						params.put(key,"%" + conditionMap.get(key) + "%");
					}else{
						sql.append(key).append("= :"+key+" and ");
						params.put(key,conditionMap.get(key));
					}
					one=true;
			    }
			}
			if (conditionMap.get("opdatetimestar")!=null&&conditionMap.get("opdatetimeend")!=null) {
				if (!conditionMap.get("opdatetimestar").equals("")&&!conditionMap.get("opdatetimeend").equals("")) {
					sql.append("opdatetime BETWEEN :").append("opdatetimestar and ");
					params.put("opdatetimestar", conditionMap.get("opdatetimestar"));
					sql.append(" :opdatetimeend");
					params.put("opdatetimeend", conditionMap.get("opdatetimeend"));	
					two=true;
				}
				
			}
		}
		if (two==false&&one==false) {
			sql.replace(sql.length()-7,sql.length()," ");
		}
		if (two==false&&one==true) {
			sql.replace(sql.length()-5, sql.length(),"");
		}
		if (count.equals("1")) {
			sql.append(" order by opdatetime desc limit ").append(startRow).append(",").append(pageSize);
		}
		String hduw=sql.toString();
		t =basefileBaseDaoI.executeSql(hduw,params);
		}
		if (t != null) {
			return t;
		}
		return null;
	}

	@Override
	public int getAppFileCount(Map<String, Object> conditionMap) {
		String sql2="";
		int year=0;
		List<Map> t =null;
		if(conditionMap!=null&&StringUtils.isNotBlank((String) conditionMap.get("sql"))){
			sql2=(String) conditionMap.get("sql");
			year=(Integer) conditionMap.get("year");
			sql2=sql2.replace("sss","%");

         if(sql2.endsWith("and")){
			    int	index=sql2.lastIndexOf("and");
			    sql2=sql2.substring(0, index);
		 }else if(sql2.endsWith("or")){	
			    int index=sql2.lastIndexOf("or");
 			    sql2=sql2.substring(0, index);
 			}
			    sql2="SELECT count(*) appBugFileCount FROM file_appinfo_"+year+sql2;
			    t =basefileBaseDaoI.executeSql(sql2);
		}else {
		
		StringBuilder sql = new StringBuilder();
		boolean one=false;
		boolean two=false;
		String tableName = "file_appinfo_"+Calendar.getInstance().get(Calendar.YEAR);
		sql.append("SELECT count(*) appBugFileCount FROM ");
		if(conditionMap!=null&&!"".equals(conditionMap.get("opdatetimestar"))){
			try {
				int yearStart = 1900+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)conditionMap.get("opdatetimestar")).getYear();
				int yearEnd = 1900+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)conditionMap.get("opdatetimeend")).getYear();
				//当开始时间与结束时间不是同一年时，按结束时间所在年份表的
				if(yearEnd!=yearStart)
					tableName = "file_appinfo_"+yearEnd;
				else
					tableName = "file_appinfo_"+yearStart;
			} catch (ParseException e) {
				LOGGER.info(e);
			}
		}
		sql.append(tableName);
		sql.append(" where ");
		Map<String, Object> params = new HashMap<String, Object>();		
		if (conditionMap != null && !conditionMap.isEmpty()) {
			Iterator itor = conditionMap.keySet().iterator();
//			sql.append(" where ");
			while (itor.hasNext()) {
				String key = (String) itor.next();
				//SELECT * FROM file_appinfo where  :opdatetimeend and opdatetime BETWEEN :opdatetimestar order by id limit 0,20
//				if (key.equals("opdatetimestar")) {
//					sql.append("opdatetime BETWEEN :").append(key+" and ");
//					params.put(key, conditionMap.get(key));
//				}
//				if (key.equals("opdatetimeend")) {
//					sql.append(" :"+key+" and ");
//					params.put(key, conditionMap.get(key));
//				}
				if (!key.equals("opdatetimeend")&&!key.equals("opdatetimestar")&&StringUtils.isNotBlank((String)conditionMap.get(key))) {
					if("houseIeeee".equalsIgnoreCase(key)){
						key = "houseIEEE";
						sql.append("houseIEEE").append("=:"+key+" and ");
						params.put(key,conditionMap.get("houseIeeee"));
					}else if("model".equalsIgnoreCase(key)){
						sql.append(key).append(" like :"+key+" and ");
						params.put(key,"%" + conditionMap.get(key) + "%");
					}else {
						sql.append(key).append("= :"+key+" and ");
						params.put(key,conditionMap.get(key));
					}
					one=true;
			    }
			}
			//sql.replace(sql.length()-5, sql.length(),"");
			if (conditionMap.get("opdatetimestar")!=null&&conditionMap.get("opdatetimeend")!=null) {
				if (!conditionMap.get("opdatetimestar").equals("")&&!conditionMap.get("opdatetimeend").equals("")) {
					sql.append("opdatetime BETWEEN :").append("opdatetimestar and ");
					params.put("opdatetimestar", conditionMap.get("opdatetimestar"));
					sql.append(" :opdatetimeend");
					params.put("opdatetimeend", conditionMap.get("opdatetimeend"));	
					two=true;
				}
			}
		}
		if (two==false&&one==false) {
			sql.replace(sql.length()-7,sql.length()," ");
		}
		if (two==false&&one==true) {
			sql.replace(sql.length()-5, sql.length(),"");
		}
		String hduw=sql.toString();
		try{
			t =dao2.executeSql(hduw,params);
		}catch(Exception e){
			LOGGER.info("GetAppFileCount Exception",e);
			String msg = e.getMessage();
			if(msg.indexOf(tableName.toLowerCase())!=-1);
				return -1;//表不 在时
		}
		}
		return ((BigInteger)t.get(0).get("appBugFileCount")).intValue();
	}
	/**
	 * 获取houseIEEE所在的服务器ip
	 * @param houseIEEE
	 * @return
	 */
	/*private String getServerIp(String houseIEEE){
		String sql = "select p.* from proxyserver p left join house h on p.houseIEEE = h.houseIEEE where h.houseIEEE = :houseIEEE";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("houseIEEE", houseIEEE);
		List<Map>  aList= basefileBaseDaoI.executeSql(sql, params);
		if(aList!=null&&!aList.isEmpty()){
			return (String)aList.get(0).get("serverIp");
		}
		return null;
	}*/
}
