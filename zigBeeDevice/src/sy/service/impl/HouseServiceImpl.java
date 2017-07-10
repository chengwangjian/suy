package sy.service.impl;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import sy.controller.HouseController;
import sy.controller.SendSMS;
import sy.dao.BaseDaoI;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.House;
import sy.model.Houseieee;
import sy.model.Houseservice;
import sy.model.Messagehistory;
import sy.model.Modenode;
import sy.model.Notewarncontrol;
import sy.model.Proxyserver;
import sy.pageModel.HouseInfo;
import sy.service.HouseServiceI;
import sy.service.HouseieeeServiceI;
import sy.service.ProxyserverServiceI;
import sy.util.HouseieeeListener;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.ServerRegisterUtil;
import sy.util.TestHttpclient;

import com.alibaba.fastjson.JSON;

@Service("houseService")
public class HouseServiceImpl implements HouseServiceI {

	private static final Logger logger = Logger.getLogger(HouseServiceImpl.class);
	//添加的拾联账户类型
	private String type = PropertiesUtil.getProperty("account.type");
	//添加账户接口访问IP
	private String ip = PropertiesUtil.getProperty("shilian.platform.ip");
	//添加账户接口访问I端口
	private String port = PropertiesUtil.getProperty("shilian.platform.port");
	//拾联创建账号账户名
	private String username = PropertiesUtil.getProperty("shilian.platform.user");

	private BaseDaoI<House> houseDao;
	private BaseDaoI<Houseservice> houseserviceDao;
	private BaseDaoI<Houseieee> houseieeeDao;
	private BaseDaoI<Map> mapDao;
	private int isMail = Integer.parseInt(PropertiesUtil.getProperty("is.cshc.mail"));
	private BaseDaoI<Notewarncontrol> noteBaseDao;
	public BaseDaoI<Notewarncontrol> getNoteBaseDao() {
		return noteBaseDao;
	}
	@Autowired
	public void setNoteBaseDao(BaseDaoI<Notewarncontrol> noteBaseDao) {
		this.noteBaseDao = noteBaseDao;
	}
	public BaseDaoI<Modenode> getModenodeDao() {
		return modenodeDao;
	}
	@Autowired
	public void setModenodeDao(BaseDaoI<Modenode> modenodeDao) {
		this.modenodeDao = modenodeDao;
	}

	private HouseieeeServiceI houseieeeService;	
	private ProxyserverServiceI proxyserverService;
	private BaseDaoI<Modenode> modenodeDao;
	
	public ProxyserverServiceI getProxyserverService() {
		return proxyserverService;
	}

	@Autowired
	public void setProxyserverService(ProxyserverServiceI proxyserverService) {
		this.proxyserverService = proxyserverService;
	}

	public HouseieeeServiceI getHouseieeeService() {
		return houseieeeService;
	}

	@Autowired
	public void setHouseieeeService(HouseieeeServiceI houseieeeService) {
		this.houseieeeService = houseieeeService;
	}

	public BaseDaoI<House> getHouseDao() {
		return houseDao;
	}

	@Autowired
	public void setHouseDao(BaseDaoI<House> houseDao) {
		this.houseDao = houseDao;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}
	
	public BaseDaoI<Houseieee> getHouseieeeDao() {
		return houseieeeDao;
	}
	@Autowired
	public void setHouseieeeDao(BaseDaoI<Houseieee> houseieeeDao) {
		this.houseieeeDao = houseieeeDao;
	}
	public BaseDaoI<Houseservice> getHouseserviceDao() {
		return houseserviceDao;
	}
	@Autowired
	public void setHouseserviceDao(BaseDaoI<Houseservice> houseserviceDao) {
		this.houseserviceDao = houseserviceDao;
	}

	@Override
	public House save(House house) {
		/*House t = new House();
		BeanUtils.copyProperties(house, t, new String[] { "pwd" });*/
		/*t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(house.getPwd()));*/
		houseDao.save(house);
//		BeanUtils.copyProperties(t, house);
		return house;
	}
	
	@Override
	public House update(House house) {
		houseDao.update(house);
		return house;
	}
	
	/**
	 * 查询house表是否有记录，用作修改时，查询数据。
	 * @author: zhuangxd
	 * 时间：2014-7-4 下午4:13:03
	 * @param house
	 * @return
	 */
	@Override
	public House get(House house) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", house.getHouseIeee());
//		params.put("enableFlag", house.getEnableFlag());
//		House t = houseDao.get("from House t where t.houseIeee = :houseIeee and enableFlag = 'enableFlag' ", params);
//		House t = houseDao.get("from House t where t.houseIeee = :houseIeee and enableFlag = '1' ", params);
		House t = houseDao.get("from House t where t.houseIeee = :houseIeee ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/*查询house表是否有记录*/
	@Override
	public House get1(House house) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", house.getHouseIeee());
		House t = houseDao.get("from House t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	@Override
	public House find(House house) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", house.getHouseIeee());
		House t = houseDao.get("from House t where t.houseIeee = :houseIeee and enableFlag = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/**
	 * 查找所有houseieee列表(ajax post方式)  关键字模糊查询
	 * @author: zhuangxd
	 * 时间：2014-7-4 下午4:14:11
	 * @param house
	 * @return
	 */
	@Override
	public List<Map> findList(House house,String userid) throws Exception {
		StringBuilder sql = new StringBuilder("select DISTINCT t.id,t.houseIEEE as houseIeee,t.name,t.lasttime,t.createtime,t.isonline,");
		sql.append("t.regionCode,t.clientCode,t.IPK_version,t.HW_version,r.client_code,r.region from house t INNER JOIN reliclient r on r.id=t.client_id");
		sql.append(" INNER JOIN reliroleclient rlc ON r.id = rlc.client_id");
		sql.append(" INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id");
		sql.append(" where t.regionCode != 'none' and rur.user_id = :userId ");
//		sql.append("and t.id in (select distinct id from house where client_id in (");
//		sql.append("select client_id from reliroleclient where role_id in(");
//		sql.append("select role_id from reliuserrole where user_id = :userid)))");;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId",userid);
		if(StringUtils.isNotBlank(house.getHouseIeee())) {
			params.put("searchText", "%" + house.getHouseIeee() + "%");
			sql.append("and (t.regionCode like :searchText or ");																																																																																																													
			sql.append("t.name like :searchText or ");
			sql.append("t.houseIEEE like :searchText or ");
			sql.append("r.client_code like :searchText or ");
			if( house.getHouseIeee()=="无版本"||"无版本".equals(house.getHouseIeee())){
				sql.append("t.IPK_version is null or ");
			}else{
				sql.append("t.IPK_version like :searchText or ");
			}
			sql.append("convert(t.lasttime using gb2312) like :searchText or ");
			sql.append("convert(t.createtime using gb2312) like :searchText) ");
//			sql.append("and (t.regionCode like '%").append(house.getHouseIeee()).append("%' or ");
//			sql.append("t.name like '%").append(house.getHouseIeee()).append("%' or ");
//			sql.append("t.houseIEEE like '%").append(house.getHouseIeee()).append("%' or ");
//			sql.append("t.clientCode like '%").append(house.getHouseIeee()).append("%' or ");
//			sql.append("convert(t.lasttime using gb2312) like '%").append(house.getHouseIeee()).append("%' or ");
//			sql.append("convert(t.createtime using gb2312) like '%").append(house.getHouseIeee()).append("%') ");
		}
		if (StringUtils.isNotBlank(house.getOrderBy())) {
			sql.append(" order by " + house.getOrderBy());
    	} else {
    		sql.append(" order by convert(t.regionCode using gb2312) asc,t.isonline asc,t.lasttime desc ");
    	}
		if (house.getPageSize() > 0 ) {
			sql.append(" limit " + house.getStartRow() + "," + house.getPageSize());
		} 
//		sql.append("order by ").append(house.getOrderBy());
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		int length = t.size();
		if(t != null){
			for(int i = 0; i < length; i++) {
				Map map1 = t.get(i);
				String houseIeee = (String)map1.get("houseIeee")==null?"":(String)map1.get("houseIeee");
				String Lasttime = map1.get("lasttime")==null?"":map1.get("lasttime").toString();
				String isonline = map1.get("isonline")==null?"0":map1.get("isonline").toString();
				int total = getStartOver(houseIeee, Lasttime, isonline);
				map1.put("total", total);
			}
			return t;
		}
			return null;	
	}
	
	
	public int getStartOver(String houseIeee, String lasttime, String isonline) throws Exception {
		if("1".equals(isonline)) {
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date endtime = sdf.parse(lasttime);
			Calendar date = Calendar.getInstance();
			date.setTime(endtime);
			date.set(Calendar.DATE, date.get(Calendar.DATE ) - 1);
			Date start = sdf.parse(sdf.format(date.getTime()));
			String starttime = sdf.format(start);
			//System.out.println("时间str==>:" + starttime);
			String tablename = "gatewaylog";
			Map<String, Object>  map = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) as total from ").append(tablename).append(" t where t.startover=1 and 1=1");
			if(StringUtils.isNotBlank(houseIeee)) {
				sql.append(" and t.houseIEEE =:houseIEEE ");
				map.put("houseIEEE", houseIeee);
			}
			if(StringUtils.isNotBlank(starttime)) {
				sql.append(" and t.time between '").append(starttime).append("' and '").append(lasttime).append("'");
			}		
			List<Map> list = mapDao.executeSql(sql.toString(), map);
			return ((BigInteger) ((Map) list.get(0)).get("total")).intValue();
		}else {
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date endtime = new Date(); 
			//Date endtime = sdf.parse(lasttime);
			Calendar date = Calendar.getInstance();
			date.setTime(endtime);
			date.set(Calendar.DATE, date.get(Calendar.DATE ) - 1);
			Date start = sdf.parse(sdf.format(date.getTime()));
			String starttime = sdf.format(start);
			//System.out.println("时间str==>:" + starttime);
			String tablename = "gatewaylog";
			Map<String, Object>  map = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) as total from ").append(tablename).append(" t where t.startover=1 and 1=1");
			if(StringUtils.isNotBlank(houseIeee)) {
				sql.append(" and t.houseIEEE =:houseIEEE ");
				map.put("houseIEEE", houseIeee);
			}
			if(StringUtils.isNotBlank(starttime)) {
				sql.append(" and t.time between '").append(starttime).append("' and '").append(lasttime).append("'");
			}		
			List<Map> list = mapDao.executeSql(sql.toString(), map);
			return ((BigInteger) ((Map) list.get(0)).get("total")).intValue();
		}
	}
	
	/**
	 * 获取记录总数
	 * @author: zhuangxd
	 * 时间：2014-8-5 上午11:14:00
	 * @param house
	 * @return
	 */
	@Override
	public Map getListoryCount(House house) {
		Map<String, Object> rt = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("select count(DISTINCT t.houseIEEE) hCount from house t INNER JOIN reliclient r on r.id=t.client_id ");
		sql.append(" INNER JOIN reliroleclient rlc ON r.id = rlc.client_id");
		sql.append(" INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id");
		sql.append(" where r.region <> 'none' and rur.user_id = :userId ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", house.getId());
		if(StringUtils.isNotBlank(house.getHouseIeee())) {
			params.put("searchText", "%" + house.getHouseIeee() + "%");
			sql.append("and (r.region like :searchText or ");
			sql.append("t.name like :searchText or ");
			sql.append("t.houseIEEE like :searchText or ");
			sql.append("r.client_code like :searchText or ");
			if( house.getHouseIeee()=="无版本"||"无版本".equals(house.getHouseIeee())){
				sql.append("t.IPK_version is null or ");
			}else{
				sql.append("t.IPK_version like :searchText or ");
			}
			sql.append("convert(t.lasttime using gb2312) like :searchText or ");
			sql.append("convert(t.createtime using gb2312) like :searchText) ");
		}
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		int total = ((BigInteger) list.get(0).get("hCount")).intValue();
		//在线数量
		sql.append("and t.isonline = 1");
		List<Map> onlineList = mapDao.executeSql(sql.toString(), params);
		int onlineTotal = ((BigInteger) onlineList.get(0).get("hCount")).intValue();
		rt.put("total", total);
		rt.put("onlineTotal", onlineTotal);
		rt.put("offlineTotal", (total - onlineTotal));
		rt.put("result", 1);
		return rt;
	}	
	
	/**
	 * 该接口不能修改，不能公用
	 * 查找所有houseieee列表(getjson) 安卓获取外网直连地址的接口
	 * @author: zhuangxd
	 * 时间：2014-7-4 下午4:14:25
	 * @param house
	 * @return
	 */
	@Override
	public List<House> findList2(House house) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", house.getHouseIeee());
		List<House> t = houseDao.find("from House t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;*/
		
		StringBuffer hql = new StringBuffer();
		hql.append("from House t where 1=1 and t.regionCode != 'none' ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (house.getHouseIeee() != null){
			hql.append("and t.houseIeee like :houseIeee ");
			params.put("houseIeee",  "%" + house.getHouseIeee() + "%");
		}
		if (house.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + house.getName() + "%");
		}
		if (house.getEnableFlag() != null && !"".equals(house.getEnableFlag())) { // 空字符串查找全部
			hql.append("and t.enableFlag like :enableFlag ");
			params.put("enableFlag", "%"+house.getEnableFlag()+"%");
		}
		if (house.getRegionCode() != null && !"".equals(house.getRegionCode())) { // 空字符串查找全部
			hql.append("and t.regionCode = :regionCode ");
			params.put("regionCode", house.getRegionCode());
		}
		if (house.getIsonline() != null && !"".equals(house.getIsonline())) { // 空字符串查找全部
			hql.append("and t.isonline = :isonline ");
			params.put("isonline", house.getIsonline());
		}
		/*if (house.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", house.getLongitude());
		}
		if (house.getLatitude() != null) {
			hql.append("and t.latitude = :latitude ");
			params.put("latitude", house.getLatitude());
		}*/
		if (house.getMinLongitude() != null) {
			hql.append("and t.longitude >= :minlongitude ");
			params.put("minlongitude", house.getMinLongitude());
		}
		if (house.getMaxLongitude() != null) {
			hql.append("and t.longitude <= :maxlongitude ");
			params.put("maxlongitude", house.getMaxLongitude());
		}
		if (house.getMinLatitude() != null) {
			hql.append("and t.latitude >= :minlatitude ");
			params.put("minlatitude", house.getMinLatitude());
		}			
		if (house.getMaxLatitude() != null) {
			hql.append("and t.latitude <= :maxlatitude ");
			params.put("maxlatitude", house.getMaxLatitude());
		}	
		if (house.getNetworkAddress() != null) {
			hql.append("and t.networkAddress = :networkAddress ");
			params.put("networkAddress", house.getNetworkAddress());
		}
		if (house.getPort() != null) {
			hql.append("and t.port = :port ");
			params.put("port", house.getPort());
		}
		if (StringUtils.isNotBlank(house.getOrderBy())) {
			hql.append(" order by " + house.getOrderBy());
    	} else {
    		hql.append(" order by t.regionCode asc,t.isonline asc,t.lasttime desc ");
    	}
		List<House> t = houseDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/**
	 * 监控SHC服务弹框邮箱显示
	 */
	@Override
	public List<Houseservice> getshc(Houseservice Houseservice) {
	
		StringBuffer hql = new StringBuffer();
		hql.append("from Houseservice t where 1=1 and t.houseIeee = :houseIeee ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee",  Houseservice.getHouseIeee().split(";")[0]);
		List<Houseservice> t = houseserviceDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	/*开启SHC监控*/
	@Override
	public int openshc(Houseservice Houseservice) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		String[] houseIeeeArr = Houseservice.getHouseIeee().split(","); 
		int a=houseIeeeArr.length-1;
		for(int i=0;i<=a;i++){
			String sql1 = "update houseservice set houseIEEE =:houseIeee where houseIEEE = :houseIeee";
			params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
			int t = houseserviceDao.executeSql2(sql1, params);
			if(t==0){
				String sql2="insert into houseservice(houseIEEE,monitor_state,email,defaultemail,emaildescription) value(:houseIeee,1,:email,:defaultemail,:emaildescription)";
				params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
				params.put("email",  Houseservice.getEmail());
				params.put("defaultemail",  Houseservice.getDefaultemail());
				params.put("emaildescription",  Houseservice.getEmaildescription());
				int t1 = houseserviceDao.executeSql2(sql2,params);
			}else{
				String sql = "update houseservice  set monitor_state = 1,email =:email,defaultemail=:defaultemail,emaildescription=:emaildescription where 1=1 and houseIEEE = :houseIeee";
				params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
				params.put("email",  Houseservice.getEmail());
				params.put("defaultemail",  Houseservice.getDefaultemail());
				params.put("emaildescription",  Houseservice.getEmaildescription());
				int t2= houseserviceDao.executeSql2(sql, params);
			}
			params.clear();
		}
		return 1;
		
	}
	
	/*如果houseservice表中没有数据，就新增*/
	public int addshc(Houseservice Houseservice){
		String sql="insert into houseservice(houseIEEE) value(houseIeee) where houseIEEE = :houseIeee";
		int t1 = houseserviceDao.executeSql2(sql);
		return 0;		
	}
	/*关闭SHC监控*/
	@Override
	public int closeshc(Houseservice Houseservice){
		String sql = "update houseservice  set monitor_state = 0 where 1=1 and houseIEEE = :houseIeee";
		Map<String, Object> params = new HashMap<String, Object>();
		String[] houseIeeeArr = Houseservice.getHouseIeee().split(",");
		int a= houseIeeeArr.length-1;
		params.put("houseIeee",  Houseservice.getHouseIeee().split(";")[0]);
		for(int i=0;i<=a;i++){
			params.put("houseIeee", houseIeeeArr[i].split(";")[0]);
			int t = houseserviceDao.executeSql2(sql, params);
		}
		return 1;
	}
	
	/*分页查询*/
	@Override
	public List<House> findList(String startRow, String pageSize,String orderBy, Map<String, Object> house) {
	
		Map<String, Object> params = new HashMap<String, Object>();
		//String hql = "from Device t where t.houseIeee='11' ";
		StringBuilder hql = new StringBuilder("SELECT * from House t where t.regionCode != 'none' ");
//		if(house.get("id")!=null&&!house.get("id").equals(0)){//检索单条记录 
//			hql+=" where t.id=:id ";
//			params.put("id", Long.valueOf(house.get("id").toString()));
//		}else hql+="  where h.ReleaseHistoryId=0 ";//多条记录
		String sText = (String) house.get("search");
		if(StringUtils.isNotBlank(sText)){//搜索
			hql.append(" and (t.enableFlag like :enableFlag ");		
			params.put("enableFlag", "%"+sText+"%");
			hql.append("or t.clientCode like :clientCode ");		
			params.put("clientCode", "%"+sText+"%");
			hql.append("or t.regionCode like :regionCode ");		
			params.put("regionCode","%"+ sText+"%");
			hql.append("or t.name like :name ");		
			params.put("name","%"+ sText+"%");
			hql.append("or t.houseIeee like :houseIeee) ");		
			params.put("houseIeee", "%"+sText+"%");
			
				
		}
//		if(house.get("startRow")!=null&&house.get("pageSize")!=null){//添加分页
//			int rowstart=Integer.valueOf(house.get("startRow").toString());
//			int rowsize=Integer.valueOf(house.get("pageSize").toString());
//			rowstart=rowstart*rowsize;
//			hql+=" LIMIT "+rowstart+","+rowsize;			
//		}
		if (StringUtils.isBlank(orderBy)) {
			hql.append(" order by t.id desc");
		}else {
			hql.append(" order by ").append(orderBy);
		}
		hql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
		
		//TestLog4j.testInfo("findList----"+hql+"params----"+params);
		
		// TODO Auto-generated method stub	
		
		
		
		List<House> t = houseDao.executeSql(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
		
		
	}
	
	@Override
	public int getCount(Map<String, Object> paramMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("select Count(distinct t.houseIeee) as aCount");
		sql.append(" from house t INNER JOIN proxyserver p on t.houseIeee=p.houseIeee INNER JOIN reliclient r on r.id=t.client_id");
		sql.append(" INNER JOIN reliroleclient rlc ON r.id = rlc.client_id");
		sql.append(" INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id");
		sql.append(" LEFT  JOIN slplatformuser s on t.houseIEEE = s.username ");
		sql.append(" where r.region <> 'none' and rur.user_id = :userId");

		params.put("userId",paramMap.get("userid"));
		if (StringUtils.isNotBlank((String) paramMap.get("houseIeee1"))) {
			sql.append(" and t.houseIeee like :houseIeee ");
			params.put("houseIeee","%"+ paramMap.get("houseIeee1")+"%");
		}
		
		if (StringUtils.isNotBlank((String)paramMap.get("name"))) {
			sql.append(" and t.name like :name ");
			params.put("name", "%"+paramMap.get("name")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("clientCode"))) {
			sql.append(" and r.client_code like :clientCode ");
			params.put("clientCode", "%"+paramMap.get("clientCode")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("enableFlag"))) {
			sql.append(" and t.enableFlag like :enableFlag ");
			params.put("enableFlag", "%"+paramMap.get("enableFlag")+"%");
		}
		if(StringUtils.isNotBlank((String)paramMap.get("isAdd"))) {
			if(paramMap.get("isAdd").equals("0")){
				sql.append(" and s.id IS NULL");
			}else if(paramMap.get("isAdd").equals("1")){
				sql.append(" and s.id IS NOT NULL");
			}
			//CASE WHEN s.id IS NULL THEN 0 ELSE 1 END AS isAddThinkBox
		}
		if (StringUtils.isNotBlank((String)paramMap.get("serverIp"))) {
			sql.append(" and p.serverIp like :serverIp ");
			params.put("serverIp","%"+paramMap.get("serverIp")+"%");
		}
		List<Map> cList=mapDao.executeSql(sql.toString(), params);
		return ((BigInteger)(cList.get(0).get("aCount"))).intValue();
	}	
	
	
	@Override
	public List<Map> findhouseAndhouseieee(House house,Houseieee houseieee,Proxyserver proxyserver){
	
		StringBuffer sql = new StringBuffer();
//		String tableName = "Houseieee" ;
//		sql.append("select * from house,houseieee where house.houseIEEE=houseieee.houseIEEE");
	
		sql.append("SELECT distinct t.id, t.houseIEEE, t.name, t.longitude, t.latitude, t.networkAddress, t.wanIp, t.lanIp, t.port,");
		sql.append("t.description, t.enableFlag, t.isonline, t.createtime, t.lasttime, t.IPK_version, t.nodeNum, t.HW_version, t.client_id,");
		sql.append("r.client_code clientCode,r.region, t.smsType,e.vendorCode,e.encodemethod, p.serverIp, ");
		sql.append("CASE WHEN s.id IS NULL THEN 0 ELSE 1 END AS isAddThinkBox ");
		sql.append("FROM house t ");
		sql.append("LEFT JOIN reliclient r on r.id=t.client_id LEFT JOIN houseieee e on e.houseIeee = t.houseIeee ");
		sql.append("LEFT JOIN proxyserver p on t.houseIeee=p.houseIeee ");
		sql.append("LEFT JOIN slplatformuser s on t.houseIEEE = s.username");
		sql.append(" where 1=1 ");
//		sql.append("select * from House s,Proxyserver p,Houseieee e where 1=1 s.houseIeee = e.houseIeee and s.houseIeee = p.houseIeee and e.houseIeee = p.houseIeee");
//		sql.append(" and s.houseIeee = p.houseIeee");
//		sql.append(" and e.houseIeee = p.houseIeee");
		Map<String, Object> params = new HashMap<String, Object>();
		if (house.getHouseIeee() != null) {
			sql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", house.getHouseIeee());
		}
//		if (houseieee.getHouseIeee() != null) {
//			sql.append("and e.houseIeee = :houseIeee ");
//			params.put("houseIeee", houseieee.getHouseIeee());
//		}
//		if (proxyserver.getHouseIeee() != null) {
//			sql.append("and p.houseIeee = :houseIeee ");
//			params.put("houseIeee", proxyserver.getHouseIeee());
//		}
//		if (house.getName() != null) {
//			sql.append("and s.name like :name ");
//			params.put("name", "%" + house.getName() + "%");
//		}
//		/*if (house.getLongitude() != null) {
//			hql.append("and t.longitude = :longitude ");
//			params.put("longitude", house.getLongitude());
//		}
//		if (house.getLatitude() != null) {
//			hql.append("and s.latitude = :latitude ");
//			params.put("latitude", house.getLatitude());
//		}*/
//		if (house.getMinLongitude() != null) {
//			sql.append("and s.longitude >= :minlongitude ");
//			params.put("minlongitude", house.getMinLongitude());
//		}
//		if (house.getMaxLongitude() != null) {
//			sql.append("and s.longitude <= :maxlongitude ");
//			params.put("maxlongitude", house.getMaxLongitude());
//		}
//		if (house.getMinLatitude() != null) {
//			sql.append("and s.latitude >= :minlatitude ");
//			params.put("minlatitude", house.getMinLatitude());
//		}		
//		if (house.getMaxLatitude() != null) {
//			sql.append("and s.latitude <= :maxlatitude ");
//			params.put("maxlatitude", house.getMaxLatitude());
//		}	
//		if (house.getNetworkAddress() != null) {
//			sql.append("and s.networkAddress = :networkAddress ");
//			params.put("networkAddress", house.getNetworkAddress());
//		}
//		List<Object[]> t = houseDao.findsep(sql.toString(), params, House.class, Houseieee.class,Proxyserver.class,);
		List<Map> t= mapDao.executeSql(sql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
		
	
		
	}
	
	@Override
	public List<Object[]> findListSql(House house) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", house.getHouseIeee());
		List<House> t = houseDao.find("from House t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;*/
		
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + house.getHouseIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from House s,Deviceattribute e where 1=1 and s.houseIeee = e.houseIEEE ");
		sql.append("select {s.*}, {e.*} from House s,").append(tableName).append(" e where 1=1 and s.houseIeee = e.houseIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (house.getHouseIeee() != null) {
			sql.append("and s.houseIeee = :houseIeee ");
			params.put("houseIeee", house.getHouseIeee());
		}
		if (house.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + house.getName() + "%");
		}
		/*if (house.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", house.getLongitude());
		}
		if (house.getLatitude() != null) {
			hql.append("and s.latitude = :latitude ");
			params.put("latitude", house.getLatitude());
		}*/
		if (house.getMinLongitude() != null) {
			sql.append("and s.longitude >= :minlongitude ");
			params.put("minlongitude", house.getMinLongitude());
		}
		if (house.getMaxLongitude() != null) {
			sql.append("and s.longitude <= :maxlongitude ");
			params.put("maxlongitude", house.getMaxLongitude());
		}
		if (house.getMinLatitude() != null) {
			sql.append("and s.latitude >= :minlatitude ");
			params.put("minlatitude", house.getMinLatitude());
		}		
		if (house.getMaxLatitude() != null) {
			sql.append("and s.latitude <= :maxlatitude ");
			params.put("maxlatitude", house.getMaxLatitude());
		}	
		if (house.getNetworkAddress() != null) {
			sql.append("and s.networkAddress = :networkAddress ");
			params.put("networkAddress", house.getNetworkAddress());
		}
//		List<Object[]> t = houseDao.findSql(sql.toString(), params, House.class, Deviceattribute.class);
		List<Object[]> t = houseDao.findSql(sql.toString(), params, House.class, DevicewarnhistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	/*删除house表的数据*/
	public int delete1(House house) {
	
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM House t WHERE 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(house==null){
			return 0;
		}
		if(StringUtils.isBlank(house.getHouseIeee())){
			return 0;
		}
		String hql2 = "FROM House h WHERE h.houseIeee = :houseIeee";
		params.put("houseIeee", house.getHouseIeee());
		House h = houseDao.get(hql2, params);
		if(h!=null){
			hql.append("and t.id = :id ");
			params.clear();
			params.put("id", h.getId());
			return houseDao.executeHql(hql.toString(), params);
		}
		return 0;
		
	}
	
	@Override
	public int delete(House house) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", house.getHouseIeee());
		String hql = "delete House t where t.houseIeee = :houseIeee";
		return houseDao.executeHql(hql, params);*/
		
		StringBuffer hql = new StringBuffer();
		hql.append("delete House t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (house.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", house.getHouseIeee());
			params2.put("houseIeee", house.getHouseIeee());
//			houseDao.executeHql("delete from Deviceattribute where houseIeee = :houseIeee", params2);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String tableName = "deviceAttributeHistory_" + house.getHouseIeee() + "_" + year;
//			houseDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
//			houseDao.executeSql2("drop table IF EXISTS " + tableName);
			tableName = "deviceOperateHistory_" + house.getHouseIeee() + "_" + year;
//			houseDao.executeSql2("drop table IF EXISTS " + tableName);
//			houseDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
			tableName = "deviceWarnHistory_" + house.getHouseIeee() + "_" + year;
//			houseDao.executeSql2("drop table IF EXISTS " + tableName);
//			houseDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
		}
		if (house.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + house.getName() + "%");
		}
		/*if (house.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", house.getLongitude());
		}
		if (house.getLatitude() != null) {
			hql.append("and t.latitude = :latitude ");
			params.put("latitude", house.getLatitude());
		}*/
		if (house.getMinLongitude() != null) {
			hql.append("and t.longitude >= :longitude ");
			params.put("longitude", house.getMinLongitude());
		}
		if (house.getMaxLongitude() != null) {
			hql.append("and t.longitude <= :longitude ");
			params.put("longitude", house.getMaxLongitude());
		}
		if (house.getMinLatitude() != null) {
			hql.append("and t.latitude >= :latitude ");
			params.put("latitude", house.getMinLatitude());
		}			
		if (house.getMaxLatitude() != null) {
			hql.append("and t.latitude <= :latitude ");
			params.put("latitude", house.getMaxLatitude());
		}	
		if (house.getNetworkAddress() != null) {
			hql.append("and t.networkAddress = :networkAddress ");
			params.put("networkAddress", house.getNetworkAddress());
		}
		if (house.getPort() != null) {
			hql.append("and t.port = :port ");
			params.put("port", house.getPort());
		}
		return houseDao.executeHql(hql.toString(), params);
	}

	@Override
	public House login(House house) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(house.getPwd()));
		params.put("name", house.getName());
		House t = houseDao.get("from House t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return house;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(House house) {
		DataGrid dg = new DataGrid();
		String hql = "from House t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(house, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(house, hql);
		List<House> l = houseDao.find(hql, params, house.getPage(), house.getRows());
		List<House> nl = new ArrayList<House>();
		changeModel(l, nl);
		dg.setTotal(houseDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<House> l, List<House> nl) {
		if (l != null && l.size() > 0) {
			for (House t : l) {
				House u = new House();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(House house, String hql) {
		/*if (house.getSort() != null) {
			hql += " order by " + house.getSort() + " " + house.getOrder();
		}*/
		return hql;
	}

	private String addWhere(House house, String hql, Map<String, Object> params) {
		if (house.getName() != null && !house.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + house.getName().trim() + "%%");
		}
		return hql;
	}
	
	@Override
	public int updateIPKversion(Map ipk) {
		//首先根据houseIEEE获取house对象（主要是获取id）
		Map<String,Object> params = new HashMap<>();
		params.put("houseIeee", ipk.get("HouseIEEE"));
		House house = houseDao.get("FROM House WHERE houseIeee=:houseIeee", params);
		if(house!=null && (!StringUtils.equalsIgnoreCase(house.getIPK_version(), (String)ipk.get("CurVersion"))
				|| !StringUtils.equalsIgnoreCase(house.getHW_version(), (String)ipk.get("HWVersion"))
				)
			){
			String sql = "update house set IPK_version = '"+ipk.get("CurVersion")
						+"',HW_Version ='"+ipk.get("HWVersion")
						+"' where id="+house.getId()+"";
			logger.info("updateIPKversion-----------------"+sql);
			return houseDao.executeSql2(sql);
		}else
			return 0;
	}
	
	/**
	 * 所有在线改成离线
	 * @author: zhuangxd
	 * 时间：2014-7-11 上午11:40:40
	 * @return
	 */
	/*@Override
	public int updateAllIsonline() {
		//获取所有在线的家
		List<House> houseList = houseDao.find("FROM House WHERE isonline='1'");
		if(houseList!=null&&houseList.size()>0){
			for(House house:houseList){
				String sql = "update house set isonline = '0',lasttime=now() where id=:id";
				Map<String,Object> params = new HashMap<>();
				params.put("id", house.getId());
				houseDao.executeSql2(sql, params);
			}
			return houseList.size();
		}
		return 0;
	}
	
	*//**
	 * 指定的houseieee数组都改成在线,不在改离线
	 * @author: zhuangxd
	 * 时间：2014-7-11 上午11:41:05
	 * @param list
	 * @return
	 *//*
	@Override
	public int updateAllIsonline(List<String> list) {
		String sql = "update house set isonline = '0',lasttime=now() where isonline = '1' and houseIeee not in (";
		if (list != null && list.size() > 0) {
    		for (String str : list) { 
    			sql += "'"+ str + "',";
        	}
    		sql = sql.substring(0,sql.length()-1);
    		sql += ")";
    	} else {
    		sql = sql + "'')";
    	}
		houseDao.executeSql2(sql);
		
		sql = "update house set isonline = '1',lasttime=now() where houseIeee in (";
		if (list != null && list.size() > 0) {
    		for (String str : list) { 
    			sql += "'"+ str + "',";
        	}
    		sql = sql.substring(0,sql.length()-1);
    		sql += ")";
    	} else {
    		sql = sql + "'')";
    	}
		return houseDao.executeSql2(sql);
	}*/
	
	@Override
	public int addDeviceAttributeHistoryTable(String houseIEEE) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + houseIEEE + "_" + year;
		String sql = "Create TABLE if not exists " + tableName + " LIKE deviceattributehistory_houseid_year";
		return houseDao.executeSql2(sql);
	}
	
	@Override
	public int addDeviceOperateHistoryTable(String houseIEEE) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceOperateHistory_" + houseIEEE + "_" + year;
		String sql = "CREATE TABLE IF not EXISTS " + "`" + tableName +"` ("+
				 "`id` bigint(20) NOT NULL AUTO_INCREMENT,`houseIEEE` varchar(50) DEFAULT NULL,`username` varchar(100) DEFAULT NULL," +
				  "`room_id` bigint(20) DEFAULT NULL,`daemonDeviceId` bigint(20) DEFAULT '0',`source_id` bigint(20) DEFAULT '1',`deviceType` bigint(20) DEFAULT '1'," +
				  "`device_ieee` varchar(50) DEFAULT NULL," +
				  "`device_ep` varchar(50) DEFAULT NULL,`id_string3` varchar(50) DEFAULT NULL, `opname` varchar(200) DEFAULT NULL,"+
				  "`optype` varchar(20) DEFAULT NULL,`opparam` varchar(2000) DEFAULT NULL," +
				  "`opdatetime` datetime DEFAULT CURRENT_TIMESTAMP,PRIMARY KEY (`id`)," +
				  "KEY `opname` (`opname`),KEY `optype` (`optype`),KEY `opdatetime` (`opdatetime`)," +
				  "KEY `houseIEEE, room_id, device_ieee, device_ep` (`houseIEEE`,`room_id`,`device_ieee`,`device_ep`) USING BTREE," +
				  "KEY `username` (`username`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
		return houseDao.executeSql2(sql);
	}
	
	@Override
	public int addDeviceWarnHistoryTable(String houseIEEE) {
		/*int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + houseIEEE + "_" + year;
		String sql = "CREATE TABLE IF not EXISTS " + "`" + tableName +"` ("+
				 "`id` bigint(20) NOT NULL AUTO_INCREMENT,`houseIEEE` varchar(50) DEFAULT NULL," +
				  "`room_id` bigint(20) DEFAULT NULL,`daemonDeviceId` bigint(20) DEFAULT '0',`source_id` bigint(20) DEFAULT '1',`deviceType` bigint(20) DEFAULT '1'," +
				  "`zone_ieee` varchar(50) DEFAULT NULL,"+
				  "`zone_ep` varchar(50) DEFAULT NULL,`id_string3` varchar(50) DEFAULT NULL,`cie_ieee` varchar(50) DEFAULT NULL," +
				  "`cie_ep` varchar(50) DEFAULT NULL, `w_mode` varchar(100) DEFAULT NULL,"+
				  "`w_description` varchar(1000) DEFAULT NULL,`warndatetime` datetime DEFAULT CURRENT_TIMESTAMP,`message_id` int DEFAULT NULL,`sendState` varchar(2) DEFAULT '0',"+
				  "PRIMARY KEY (`id`),KEY `w_mode` (`w_mode`),KEY `warndatetime` (`warndatetime`),"+
				  "KEY `houseIEEE, room_id, zone_ieee, zone_ep, cie_ieee, cie_ep ` (`houseIEEE`,`room_id`,`zone_ieee`,`zone_ep`,`cie_ieee`,`cie_ep` ) USING BTREE" +
				  ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";*/
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + houseIEEE + "_" + year;
		String sql ="Create TABLE if not exists "+tableName+" LIKE devicewarnhistory_houseid_year";
		return houseDao.executeSql2(sql);
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete House t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		houseDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
	
	@Override
	public int updateHouseSwitch(Map<String, Object> paramMap, List<String> houseIeeeList) {
		if(paramMap.isEmpty()) 
			return 0;
		StringBuilder sql = new StringBuilder("update houseservice set ");
		//组装更新的字符串
		Iterator itor = paramMap.keySet().iterator();
		while(itor.hasNext()) {
			String key = (String) itor.next();
			Object value = paramMap.get(key);
			if(value == null || value.toString().isEmpty())
				sql.append(key).append("=NULL,");
			else
				sql.append(key).append("='").append(value).append("',");
		}
		sql = sql.deleteCharAt(sql.length() - 1);
		//更新到数据库中，若返回为0，则将houseIeee加入到待插入的notExistHouseIeee中
		List<String> notExistHouseIeee = new ArrayList<String>();
		for(String houseIeee : houseIeeeList) {
			String sqlStr = sql.toString() + " where houseIEEE='" + houseIeee + "'";
			int count = houseDao.executeSql2(sqlStr);
			if(count == 0)
				notExistHouseIeee.add(houseIeee);
		}
		//插入到数据库中
		if(!notExistHouseIeee.isEmpty()) {
			StringBuilder insertSql = new StringBuilder("insert into houseservice(houseIEEE,");
			StringBuilder valueSql = new StringBuilder();
			Iterator itorTmp = paramMap.keySet().iterator();
			while(itorTmp.hasNext()) {
				String key = (String) itorTmp.next();
				Object value = paramMap.get(key);
				insertSql.append(key).append(",");
				if(value == null || value.toString().isEmpty())
					valueSql.append(",NULL");
				else
					valueSql.append(",'").append(value).append("'");
			}
			insertSql = insertSql.deleteCharAt(insertSql.length() - 1).append(") ");
			for(String houseIeee : notExistHouseIeee) {
				String valueSqlStr = "values('" + houseIeee + "'" + valueSql.toString() + ")";
				houseDao.executeSql2(insertSql.toString() + valueSqlStr);				
			}
		}
		return 1;
	}
	
	@Override
	public int getHouseCount(Map<String, Object> conditionMap,String userid) {
		StringBuilder sql = new StringBuilder(
				"select count(*) as house_count from house t ");
		sql.append("INNER JOIN houseservice a on t.houseIEEE = a.houseIEEE ");
		sql.append("INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id = :userid) c ON t.client_id = c.client_id");
		sql.append(" where 1=1");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		sql.append(" and t.regionCode <> 'none' and (");
		if (conditionMap != null && !conditionMap.isEmpty()) {
			Iterator itor = conditionMap.keySet().iterator();
			while (itor.hasNext()) {
				String key = (String) itor.next();
//				sql.append(key).append(" like '%").append(conditionMap.get(key))
//						.append("%' or ");
				sql.append(key).append(" like :").append(key).append(" or ");
				params.put(key, "%" + conditionMap.get(key) + "%");
//				sql.append(key).append(" like :").append(key).append(" or ");
			}	
			sql.delete(sql.length() - 4, sql.length());
			sql.append(")");
		} else
			sql.delete(sql.length() - 5, sql.length());
		List<Map> cList = mapDao.executeSql(sql.toString(), params);
		return ((BigInteger) cList.get(0).get("house_count")).intValue();
	}
	
	@Override
	public List<Map> getHouses(Map<String, Object> conditionMap,String userid, int startRow, int pageSize) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct t.id as id, t.houseIEEE as houseIeee,t.name,t.clientCode as clientCode, a.videoService as videoService, a.email as email, a.defaultemail as defaultemail, a.monitor_state as monitor_state, a.leaveHomeInform as leaveHomeInform, t.isonline as isonline,");
		sql.append("a.lqi_open as lqi_open, a.lqi_open_time as lqi_open_time, a.min_lqi as min_lqi, a.device_is_online as device_is_online,bx.StartDate,bx.EndDate,bx.state from house t ");
		sql.append("INNER JOIN houseservice a on t.houseIEEE = a.houseIEEE left join notewarncontrol bx on bx.houseIEEE=t.houseIEEE right join reliclient r on t.client_id = r.id ");
		sql.append("INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id = :userid) c ON t.client_id = c.client_id");
		sql.append(" where 1=1");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		sql.append(" and t.regionCode != 'none' and ( ");
		if (conditionMap != null && !conditionMap.isEmpty()) {
			Iterator itor = conditionMap.keySet().iterator();
			while (itor.hasNext()) {
				String key = (String) itor.next();
				sql.append(key).append(" like :").append(key).append(" or ");
				params.put(key, "%" + conditionMap.get(key) + "%");
			}	
			sql.delete(sql.length() - 4, sql.length());
			sql.append(")");
			
		} else
			sql.delete(sql.length() - 6, sql.length());
//		sql.append(" order by t.regionCode desc,t.lasttime desc limit ").append(startRow).append(",").append(pageSize);
		sql.append(" order by t.id desc limit ").append(startRow).append(",").append(pageSize);
		String hduw=sql.toString();
		List<Map> t = mapDao.executeSql(hduw,params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Map> getServiceFailHouses() {
		StringBuilder sql = new StringBuilder("select a.houseIEEE as houseIEEE,b.videoService as videoService,");
		sql.append("b.leaveHomeInform as leaveHomeInform,b.lqi_open as lqi_open,b.lqi_open_time as lqi_open_time,");
		sql.append("b.min_lqi as min_lqi,b.device_is_online as device_is_online from house a ");
		sql.append("left join houseservice b on a.houseIEEE = b.houseIEEE ");
		sql.append("where a.isonline=1 and (b.videoService in (2,3) or b.leaveHomeInform in (2,3) or b.lqi_open in (2,3) or b.device_is_online in (2,3))");
		return mapDao.executeSql(sql.toString());
	}
	
	@Override
	public void executeAction() {
		List<Map> houses = getServiceFailHouses();
		logger.info("在线未操作成功的house数: " + houses.size());
		try {
			for(Map house : houses) {//0表示关闭，1表示开启，2表示关闭未成功，3表示开启未成功
				String houseIeee = (String) house.get("houseIEEE");
				Byte vService = (Byte) house.get("videoService");
				Byte lHome = (Byte) house.get("leaveHomeInform");
				Byte lqi_open = (Byte) house.get("lqi_open");
				Byte device_is_online = (Byte) house.get("device_is_online");
				String lqi_open_time = (String) house.get("lqi_open_time");
				Short min_lqi = (Short) house.get("min_lqi");
				if(vService.byteValue() == 2)
					sendZ203Status(0, 1, houseIeee, null, null);
				else if(vService.byteValue() == 3)
					sendZ203Status(1, 1, houseIeee, null, null);
				if(lHome.byteValue() == 2)
					sendZ203Status(0, 2, houseIeee, null, null);
				else if(lHome.byteValue() == 3)
					sendZ203Status(1, 2, houseIeee, null, null);
				if(lqi_open.byteValue() == 2)
					sendZ203Status(0, 3, houseIeee, lqi_open_time, min_lqi.toString());
				else if(lqi_open.byteValue() == 3)
					sendZ203Status(1, 3, houseIeee, lqi_open_time, min_lqi.toString());
				if(device_is_online.byteValue() == 2)
					sendZ203Status(0, 4, houseIeee, null, null);
				else if(device_is_online.byteValue() == 3)
					sendZ203Status(1, 4, houseIeee, null, null);
			}
		} catch(Exception e) {
			//e.printStackTrace();
		}
	}
	
	private void sendZ203Status(int z203Status, int serviceType, String houseIeee,
			String lqi_open_time, String min_lqi) throws Exception {
		String actionStr = z203Status == 1? "start" : "stop";
//		String xmppHost = PropertiesUtil.getProperty("xmpp.host");	
        String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIeee);
    	String xmppPort = PropertiesUtil.getProperty("xmpp.port");
    	StringBuilder callUrl = new StringBuilder("http://");
    	callUrl.append(serverIp).append(":").append(xmppPort).append("/spring-async/z203chat/polla");	
    	Map<String, Object> z203PMap = new HashMap<String, Object>();
    	String z203Json = "{\"msg_type\":\"go_out_manage\",\"msg\":{\"house_ieee\":\"" + houseIeee + "\",\"bstartservice\":" + z203Status + "}}";
    	if(serviceType == 1)
    		z203Json = "{\"msg_type\":\"video_manage\",\"msg\":{\"house_ieee\":\"" + houseIeee + "\",\"oper\":\"" + actionStr + "\"}}";
    	else if(serviceType == 3) {
    		if(lqi_open_time.length() < 6)
    			lqi_open_time += ":00"; //时间格式是13:25:00
    		z203Json = "{\"msg_type\":\"lqi_monitor\",\"msg\":{\"house_ieee\":\"" + houseIeee + "\",\"bOpen\":\"" + actionStr + "\",\"open_time\":\"" + lqi_open_time + "\",\"min_lqi\":" + min_lqi + "}}";
    	}
    	else if(serviceType == 4)
    		z203Json = "{\"msg_type\":\"check_online_manage\",\"msg\":{\"house_ieee\":\"" + houseIeee + "\",\"bOpen\":\"" + actionStr + "\"}}";
    	z203PMap.put("context", z203Json);
    	z203PMap.put("username", houseIeee);
    	String str = TestHttpclient.postUrlWithParams(callUrl.toString(), z203PMap, "utf-8");
    	Map<String, Object> result = JSON.parseObject(str, Map.class);
    	int status = (Integer) ((Map) result.get("message")).get("status");
    	if(status == 0) {
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		if(serviceType == 1)
    			paramMap.put("videoService", z203Status);
    		else if(serviceType == 2)
    			paramMap.put("leaveHomeInform", z203Status);
    		else if(serviceType == 3)
    			paramMap.put("lqi_open", z203Status);
    		else 
    			paramMap.put("device_is_online", z203Status);
    		List<String> houseIeeeList = new ArrayList<String>();
    		houseIeeeList.add(houseIeee);
    		this.updateHouseSwitch(paramMap, houseIeeeList);
    	}
	}

	/*
	 * 获取数据库的username
	 * @see sy.service.HouseServiceI#get203user(java.lang.String)
	 */
	@Override
	public Map get203user(String houseIeee) throws Exception {
		StringBuffer sBuffer = new StringBuffer(
				"select * from apponlineinfo where houseIeee=:houseIeee limit 1");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", houseIeee);
		List<Map> list = mapDao.executeSql(sBuffer.toString(), params);
//		for (int i = 0; i < list.size(); i++) {
//			Map map = list.get(i);
//			Set set = map.keySet();
//			Iterator it = set.iterator();
//			while (it.hasNext()) {
//				logger.info(map.get(it.next()));
//			}
//		}
//		if (list != null) {
//			return list;
//		}
		return list.get(0);
	}

	/*关键字搜索*/
	@Override
	public List<House> findkeyword(String startRow, String pageSize, House house) {
		StringBuffer hql = new StringBuffer();
		hql.append("select * from House t where  t.enableFlag like :enableFlag or t.clientCode like :clientCode or t.regionCode like :regionCode or t.houseIeee like :houseIeee or t.name like :name and t.regionCode != 'none'");
		
		Map<String, Object> params = new HashMap<String, Object>();
//			hql.append(" t.regionCode = :regionCode ");
			params.put("regionCode",  "%" + house.getRegionCode() + "%");
//			hql.append("or t.houseIeee = :houseIeee ");
			params.put("houseIeee", "%" + house.getHouseIeee() + "%");
//			hql.append("or t.name = :name ");
			params.put("name", "%" + house.getName() + "%");
			params.put("clientCode", "%" + house.getClientCode() + "%");
//			hql.append("or t.enableFlag = :enableFlag ");
			params.put("enableFlag", "%" + house.getEnableFlag() + "%");
//			hql.append("or t.regionCode = :regionCode ");
//			params.put("regionCode", house.getRegionCode());
//		if (house.getIsonline() != null && !"".equals(house.getIsonline())) { // 空字符串查找全部
//			hql.append("and t.isonline = :isonline ");
//			params.put("isonline", house.getIsonline());
//		}
		/*if (house.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", house.getLongitude());
		}
		if (house.getLatitude() != null) {
			hql.append("and t.latitude = :latitude ");
			params.put("latitude", house.getLatitude());
		}*/
//		if (house.getMinLongitude() != null) {
//			hql.append("or t.longitude >= :minlongitude ");
//			params.put("minlongitude", house.getMinLongitude());
//		}
//		if (house.getMaxLongitude() != null) {
//			hql.append("or t.longitude <= :maxlongitude ");
//			params.put("maxlongitude", house.getMaxLongitude());
//		}
//		if (house.getMinLatitude() != null) {
//			hql.append("or t.latitude >= :minlatitude ");
//			params.put("minlatitude", house.getMinLatitude());
//		}			
//		if (house.getMaxLatitude() != null) {
//			hql.append("or t.latitude <= :maxlatitude ");
//			params.put("maxlatitude", house.getMaxLatitude());
//		}	
//		if (house.getNetworkAddress() != null) {
//			hql.append("or t.networkAddress = :networkAddress ");
//			params.put("networkAddress", house.getNetworkAddress());
//		}
//		if (house.getPort() != null) {
//			hql.append("or t.port = :port ");
//			params.put("port", house.getPort());
//		}
			hql.append(" LIMIT "+startRow+","+pageSize);
//		if (StringUtils.isNotBlank(house.getOrderBy())) {
//			hql.append(" order by " + house.getOrderBy());
//    	} else {
//    		hql.append(" order by t.regionCode desc");
//    		hql.append(" order by t.regionCode desc,t.isonline asc,t.lasttime desc ");
//    	}
		List<House> t = houseDao.executeSql(hql.toString(), params);
//		List<House> t = houseDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	
//	/*关键字搜索*/
//	@Override
	@Override
	public List<House> findkeyword(House house) {
//		StringBuffer hql = new StringBuffer();
//		hql.append("from House t where  t.enableFlag like :enableFlag or t.clientCode like :clientCode or t.regionCode like :regionCode or t.houseIeee like :houseIeee or t.name like :name and t.regionCode != 'none'");
//		Map<String, Object> params = new HashMap<String, Object>();
////			hql.append(" t.regionCode = :regionCode ");
//			params.put("regionCode",  "%" + house.getRegionCode() + "%");
////			hql.append("or t.houseIeee = :houseIeee ");
//			params.put("houseIeee", "%" + house.getHouseIeee() + "%");
////			hql.append("or t.name = :name ");
//			params.put("name", "%" + house.getName() + "%");
//			params.put("clientCode", "%" + house.getClientCode() + "%");
////			hql.append("or t.enableFlag = :enableFlag ");
//			params.put("enableFlag", "%" + house.getEnableFlag() + "%");
////			hql.append("or t.regionCode = :regionCode ");
////			params.put("regionCode", house.getRegionCode());
////		if (house.getIsonline() != null && !"".equals(house.getIsonline())) { // 空字符串查找全部
////			hql.append("and t.isonline = :isonline ");
////			params.put("isonline", house.getIsonline());
////		}
//		/*if (house.getLongitude() != null) {
//			hql.append("and t.longitude = :longitude ");
//			params.put("longitude", house.getLongitude());
//		}
//		if (house.getLatitude() != null) {
//			hql.append("and t.latitude = :latitude ");
//			params.put("latitude", house.getLatitude());
//		}*/
////		if (house.getMinLongitude() != null) {
////			hql.append("or t.longitude >= :minlongitude ");
////			params.put("minlongitude", house.getMinLongitude());
////		}
////		if (house.getMaxLongitude() != null) {
////			hql.append("or t.longitude <= :maxlongitude ");
////			params.put("maxlongitude", house.getMaxLongitude());
////		}
////		if (house.getMinLatitude() != null) {
////			hql.append("or t.latitude >= :minlatitude ");
////			params.put("minlatitude", house.getMinLatitude());
////		}			
////		if (house.getMaxLatitude() != null) {
////			hql.append("or t.latitude <= :maxlatitude ");
////			params.put("maxlatitude", house.getMaxLatitude());
////		}	
////		if (house.getNetworkAddress() != null) {
////			hql.append("or t.networkAddress = :networkAddress ");
////			params.put("networkAddress", house.getNetworkAddress());
////		}
////		if (house.getPort() != null) {
////			hql.append("or t.port = :port ");
////			params.put("port", house.getPort());
////		}
//		if (StringUtils.isNotBlank(house.getOrderBy())) {
//			hql.append(" order by " + house.getOrderBy());
//    	} else {
//    		hql.append(" order by t.regionCode desc");
////    		hql.append(" order by t.regionCode desc,t.isonline asc,t.lasttime desc ");
//    	}
//		List<House> t = houseDao.find(hql.toString(), params);
//		if (t != null) {
//			return t;
//		}
		return null;
		
	}

	@Override
	public int insertExeclBatch(HouseInfo houseInfo) {
		List<House> list = houseInfo.getHouse();
		Houseieee houseieee2 = houseInfo.getHouseieee();
		Proxyserver proxyserver2 = houseInfo.getProxyserver();
		Map<String, Object> params = new HashMap<String, Object>();
//		Houseieee houseieee = new Houseieee();
//		houseieee.setEncodemethod(houseieee2.getEncodemethod());
//		houseieee.setVendorCode(houseieee2.getVendorCode());
//		Proxyserver proxyserver = new Proxyserver();
//		proxyserver.setServerIp(proxyserver2.getServerIp());
		
		for(House house:list){
			Houseieee houseieee = new Houseieee();
			Proxyserver proxyserver = new Proxyserver();
			BeanUtils.copyProperties(houseieee2, houseieee);
			BeanUtils.copyProperties(proxyserver2, proxyserver);
			BeanUtils.copyProperties(house, proxyserver);
			BeanUtils.copyProperties(house, houseieee);
			House t = this.get(house);
			if (t == null) {
				if (house.getNetworkAddress() == null) {
        			house.setNetworkAddress("192.168.99.255"); // 默认值
        			house.setDescription("192.168.99.255"); // 默认值
        		}
        		if (house.getClientCode() == null) {
        			house.setClientCode("CS6");
        		}
//        		List<Map> list2=this.getReliClientByCode(house.getClientCode());
//                if(list2.isEmpty()){
//            	  	throw new Exception("some clientCode aren't correct");
//                }
        		this.save(house);
        		Houseieee t2 = houseieeeService.get(houseieee); 
            	if (t2 == null) {
            		if (houseieee.getHouseIeee().length() > 6) {
            			houseieee.setSecretKey(houseieee.getHouseIeee().substring(6));
            		}
            		if(houseieee.getVendorCode()==null||"".equals(houseieee.getVendorCode())){
            			houseieee.setVendorCode("NETVOX");
            		}
            		if(houseieee.getEncodemethod()==null||"".equals(houseieee.getEncodemethod())){
            			houseieee.setEncodemethod("0");
            		}
            		houseieeeService.save(houseieee);
            		HouseieeeListener.houseieeeMap.put(houseieee.getHouseIeee(), houseieee);
            	}
            	proxyserver.setType("0");
            	proxyserver.setSecondType("0");
            	Proxyserver t3 = proxyserverService.get(proxyserver);
            	if (t3 == null) {
//                	proxyserver.setServerIp("218.104.133.242");
                	proxyserver.setServerPort("5222");
            		proxyserverService.save(proxyserver);
            	}  
            	Proxyserver proxyserver3 = new Proxyserver();
            	BeanUtils.copyProperties(proxyserver, proxyserver3);
            	proxyserver3.setType("1");
            	proxyserver3.setSecondType("0");
            	Proxyserver t4 = proxyserverService.get(proxyserver3);
            	if (t4 == null) {
//            		proxyserver.setServerIp("218.104.133.242");
//                	proxyserver.setServerPort("8081");
                	proxyserver3.setServerPort(PropertiesUtil.getProperty("cloud.port"));
                	HouseieeeListener.houseieeeProxyserverMap.put(proxyserver3.getHouseIeee(), proxyserver3.getServerIp());
            		proxyserverService.save(proxyserver3);
            	}
            	Proxyserver proxyserver4 = new Proxyserver();
            	BeanUtils.copyProperties(proxyserver, proxyserver4);
            	proxyserver4.setType("2");
            	proxyserver4.setSecondType("0");
            	Proxyserver t5 = proxyserverService.get(proxyserver4);
            	if (t5 == null) {
//            		proxyserver.setServerIp("218.104.133.242");
                	proxyserver4.setServerPort("9090");
            		proxyserverService.save(proxyserver4);
            	}
			}
			// 家庭注册时，默认开启shc监控邮箱
			Houseservice houseservice = new Houseservice();
        	houseservice.setDefaultemail(PropertiesUtil.getProperty("mail.to"));
        	houseservice.setHouseIeee(house.getHouseIeee());
        	openshc(houseservice);
        	logger.info("批量注册家------>默认开启shc监控邮箱");
		}
		return 1;
	}

	@Override
	public List<Map> getHouseXY() {
		StringBuilder sql = new StringBuilder("select a.houseIEEE,a.longitude,a.latitude,a.isonline from house a where a.regionCode <> 'none' ");
		sql.append("and a.longitude is not null and a.longitude <> '' and a.latitude is not null and a.latitude <> ''");
		return mapDao.executeSql(sql.toString());
	}
	
	@Override
	public void abtainEmailAndSend(List<String> houseIeees) throws Exception {
		//查询要发送的邮箱地址
		String sql ="select b.name,a.houseIEEE,a.email,a.defaultemail,a.emaildescription from houseservice a left join house b " +
				"on a.houseIEEE = b.houseIEEE where b.isonline=1 and a.monitor_state=1 and b.houseIEEE not in (";
		if(houseIeees != null && houseIeees.size() >0){
			for (String str : houseIeees) {
				sql += "'" + str +"',";
			}
			sql = sql.substring(0, sql.length()-1);
			sql += ")";
		} else {
			sql = sql + "'')";
		}
		List<Map> emailList = mapDao.executeSql(sql);
		if(!emailList.isEmpty()) {
			for(Map map : emailList) {
				String email = (String) map.get("email");
				String defaultEmail = (String) map.get("defaultemail");
				String eDesString = (String) map.get("emaildescription");
				String houseIEEE = (String) map.get("houseIEEE");
				String houseName = (String) map.get("name");
				String subject = "z203-" + houseName + "-离线通知";
				/*StringBuffer body = new StringBuffer();
				body.append(houseName).append("的SHC离线了").append(houseIEEE).append("离线时间")
				.append((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				body.append("<html><head></head><body><h1>");
				body.append(eDesString).append("</h1><h1>203名称:")
				.append(houseName).append("</h1><h1>203 houseIEEE:")
				.append(houseIEEE).append("</h1><h1>当前时间:")
				.append((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())).append("</h1>");
				if(StringUtils.isNotBlank(email)) {
					Mail.getInstance().sendOutToOnly(email, subject, body.toString());
				}
				if(StringUtils.isNotBlank(defaultEmail)) {
					Mail.getInstance().sendOutToOnly(defaultEmail, subject, body.toString());
				}*/
				
				JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
				Properties prop = new Properties();
				InputStream is = HouseServiceImpl.class.getClassLoader().getResourceAsStream("resources.properties");
				try {
					// 读取配置文件
					prop.load(is);
				} catch (Exception e) {				
				}
				// 设定mail server
				senderImpl.setHost(prop.getProperty("mail.host"));
				senderImpl.setUsername(prop.getProperty("mail.username"));
				// 密码解密
				senderImpl.setPassword(Httpproxy.urlCodec2(prop.getProperty("mail.password"), "0000007785NETVOX"));
				// 建立HTML邮件消息
				MimeMessage mailMessage = senderImpl.createMimeMessage();
				// true表示开始附件模式
				MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
				// 设置收件人，寄件人
				messageHelper.setTo(email);
				messageHelper.setFrom(prop.getProperty("mail.from"));
				messageHelper.setSubject(subject);
				messageHelper.setText("家庭名称是:"+houseName+"的SHC离线了,IEEE是:"+houseIEEE+",离线时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
				senderImpl.send(mailMessage);
				logger.info("OK off-line");
				mailMessage = senderImpl.createMimeMessage();
				// true表示开始附件模式
				messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
				// 设置收件人，寄件人
				messageHelper.setTo(defaultEmail);
				messageHelper.setFrom(prop.getProperty("mail.from"));
				messageHelper.setSubject(subject);
				messageHelper.setText("家庭名称是:"+houseName+"的SHC离线了,IEEE是:"+houseIEEE+",离线时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
				senderImpl.send(mailMessage);
			}
		}
		
		//查询要发送的邮箱地址
			String sql2 ="select b.name,a.houseIEEE,a.email,a.defaultemail,a.emaildescription from houseservice a left join house b " +
					"on a.houseIEEE = b.houseIEEE where b.isonline=0 and a.monitor_state=1 and b.houseIEEE in (";
			if(houseIeees != null && houseIeees.size() >0){
				for (String str : houseIeees) {
					sql2 += "'" + str +"',";
				}
				sql2 = sql2.substring(0, sql2.length()-1);
				sql2 += ")";
			} else {
				sql2 = sql2 + "'')";
			}
			List<Map> emailList2 = mapDao.executeSql(sql2);
				if(!emailList2.isEmpty()) {
					for(Map map2 : emailList2) {
						String email2 = (String) map2.get("email");
						String defaultEmail2 = (String) map2.get("defaultemail");
						String eDesString2 = (String) map2.get("emaildescription");
						String houseIEEE2 = (String) map2.get("houseIEEE");
						String houseName2 = (String) map2.get("name");
						String subject2 = "z203-" + houseName2 + "-上线通知";
						/*StringBuffer body = new StringBuffer();
						body.append(houseName).append("的SHC离线了").append(houseIEEE).append("离线时间")
						.append((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
						body.append("<html><head></head><body><h1>");
						body.append(eDesString).append("</h1><h1>203名称:")
						.append(houseName).append("</h1><h1>203 houseIEEE:")
						.append(houseIEEE).append("</h1><h1>当前时间:")
						.append((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())).append("</h1>");
						if(StringUtils.isNotBlank(email)) {
							Mail.getInstance().sendOutToOnly(email, subject, body.toString());
						}
						if(StringUtils.isNotBlank(defaultEmail)) {
							Mail.getInstance().sendOutToOnly(defaultEmail, subject, body.toString());
						}*/
						
						JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
						Properties prop = new Properties();
						InputStream is = HouseServiceImpl.class.getClassLoader().getResourceAsStream("resources.properties");
						try {
							// 读取配置文件
							prop.load(is);
						} catch (Exception e) {				
						}
						// 设定mail server
						senderImpl.setHost(prop.getProperty("mail.host"));
						senderImpl.setUsername(prop.getProperty("mail.username"));
						// 密码解密
						senderImpl.setPassword(Httpproxy.urlCodec2(prop.getProperty("mail.password"), "0000007785NETVOX"));
						// 建立HTML邮件消息
						MimeMessage mailMessage = senderImpl.createMimeMessage();
						// true表示开始附件模式
						MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
						// 设置收件人，寄件人
						messageHelper.setTo(email2);
						messageHelper.setFrom(prop.getProperty("mail.from"));
						messageHelper.setSubject(subject2);
						messageHelper.setText("家庭名称是:"+houseName2+"的SHC上线了,IEEE是"+houseIEEE2+",上线时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
						senderImpl.send(mailMessage);
						logger.info("OK online");
						// 建立HTML邮件消息
						mailMessage = senderImpl.createMimeMessage();
						// true表示开始附件模式
						messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
						// 设置收件人，寄件人
						messageHelper.setTo(defaultEmail2);
						messageHelper.setFrom(prop.getProperty("mail.from"));
						messageHelper.setSubject(subject2);
						messageHelper.setText("家庭名称是:"+houseName2+"的SHC上线了,IEEE是"+houseIEEE2+",上线时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
						senderImpl.send(mailMessage);
					}
				}
	}
	
	@Override
	public void abtainEmailAndSendSHC(List<Map> houseonIeees,List<Map> houseoffIeees) throws Exception {		
		//查询要发送的邮箱地址
		if(houseoffIeees != null && houseoffIeees.size() >0){
			Map<String, Object> timeMap = new HashMap<String, Object>();
		String sql ="select b.name,a.houseIEEE,a.email,a.defaultemail,a.emaildescription from houseservice a left join house b " +
				"on a.houseIEEE = b.houseIEEE where  a.monitor_state=1 and b.houseIEEE in (";
		
			for (Map map : houseoffIeees) {
				String houseIEEE = (String) map.get("houseIEEE");
				timeMap.put(houseIEEE.toUpperCase(), map.get("offTime"));
				sql += "'" + houseIEEE +"',";
			}
			sql = sql.substring(0, sql.length()-1);
			sql += ")";
//		} 
////		else {
//			sql = sql + "'')";
//		}
		List<Map> emailList = mapDao.executeSql(sql);
		if(!emailList.isEmpty()) {
			for(Map map : emailList) {
				String email = (String) map.get("email");
				String defaultEmail = (String) map.get("defaultemail");
				String houseIEEE = (String) map.get("houseIEEE");
				String houseName = (String) map.get("name");
				String subject = "z203-" + houseName + "-XMPP离线通知";				
				JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
				// 设定mail server
				senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
				senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
				// 密码解密
				senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
				// 建立HTML邮件消息
				MimeMessage mailMessage = senderImpl.createMimeMessage();
				// true表示开始附件模式
				MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
				// 设置收件人，寄件人
				if(StringUtils.isNotBlank(email)) {
					messageHelper.setTo(email);
					messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
					messageHelper.setSubject(subject);
					messageHelper.setText("家庭名称是:"+houseName+"的SHC离线了,IEEE是:"+houseIEEE+",离线时间是:"+timeMap.get(houseIEEE.toUpperCase()), true);
					senderImpl.send(mailMessage);
					logger.info(houseIEEE + "离线了，监控邮箱" + email);
				}
				mailMessage = senderImpl.createMimeMessage();
				// true表示开始附件模式
				messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
				// 设置收件人，寄件人
				if(StringUtils.isNotBlank(defaultEmail)) {
					messageHelper.setTo(defaultEmail);
					messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
					messageHelper.setSubject(subject);
					messageHelper.setText("默认邮箱家庭名称是:"+houseName+"的SHC离线了,IEEE是:"+houseIEEE+",离线时间是:"+timeMap.get(houseIEEE.toUpperCase()), true);
					senderImpl.send(mailMessage);
					logger.info(houseIEEE + "离线了，默认邮箱" + defaultEmail);
				}
			}
		}
	}
		
		//查询要发送的邮箱地址
	if(houseonIeees != null && houseonIeees.size() >0){
		String sql2 ="select b.name,a.houseIEEE,a.email,a.defaultemail,a.emaildescription from houseservice a left join house b " +
				"on a.houseIEEE = b.houseIEEE where  a.monitor_state=1 and b.houseIEEE in (";
		Map<String, Object> timeMap = new HashMap<String, Object>();
			for (Map map : houseonIeees) {
				String houseIEEE = (String) map.get("houseIEEE");
				timeMap.put(houseIEEE.toUpperCase(), map.get("onTime"));
				sql2 += "'" + map.get("houseIEEE") +"',";
			}
			sql2 = sql2.substring(0, sql2.length()-1);
			sql2 += ")";
//		} 
//	else {
//			sql2 = sql2 + "'')";
//		}
		List<Map> emailList2 = mapDao.executeSql(sql2);
			if(!emailList2.isEmpty()) {
				for(Map map2 : emailList2) {
					String email2 = (String) map2.get("email");
					String defaultEmail2 = (String) map2.get("defaultemail");
					String houseIEEE2 = (String) map2.get("houseIEEE");
					String houseName2 = (String) map2.get("name");
					String subject2 = "z203-" + houseName2 + "-XMPP上线通知";					
					JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
					// 设定mail server
					senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
					senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
					// 密码解密
					senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
					// 建立HTML邮件消息
					MimeMessage mailMessage = senderImpl.createMimeMessage();
					// true表示开始附件模式
					MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
					// 设置收件人，寄件人
					if(StringUtils.isNotBlank(email2)) {
						messageHelper.setTo(email2);
						messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
						messageHelper.setSubject(subject2);
						messageHelper.setText("家庭名称是:"+houseName2+"的SHC上线了,IEEE是"+houseIEEE2+",上线时间是:"+timeMap.get(houseIEEE2.toUpperCase()), true);
						senderImpl.send(mailMessage);
						logger.info(houseIEEE2 + "上线了，监控邮箱" + email2);
					}
					// 建立HTML邮件消息
					mailMessage = senderImpl.createMimeMessage();
					// true表示开始附件模式
					messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
					// 设置收件人，寄件人
					if(StringUtils.isNotBlank(defaultEmail2)) {
						messageHelper.setTo(defaultEmail2);
						messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
						messageHelper.setSubject(subject2);
						messageHelper.setText("默认邮箱家庭名称是:"+houseName2+"的SHC上线了,IEEE是"+houseIEEE2+",上线时间是:"+timeMap.get(houseIEEE2.toUpperCase()), true);
						senderImpl.send(mailMessage);
						logger.info(houseIEEE2 + "上线了，默认邮箱" + defaultEmail2);
					}
				}
			}
	}
			

}
	
public void abtainEmailAndSendSHC(List<String> houseIeees,int type) throws Exception {
		// 离线
//	if (type.equals("0")) {
	if (type == 0 && !houseIeees.isEmpty()) {
		logger.info("发送离线邮件开始---------");
		//查询要发送的邮箱地址
		StringBuilder sql = new StringBuilder("select b.name,a.houseIEEE,a.email,a.defaultemail,a.emaildescription from houseservice a left join house b " +
				"on a.houseIEEE = b.houseIEEE where b.isonline=1 and a.monitor_state=1 and b.houseIEEE in (");
		Iterator hItor = houseIeees.iterator();
		while(hItor.hasNext())
			sql.append("'").append(hItor.next()).append("',");
		String sqlQuery = sql.deleteCharAt(sql.length() - 1).toString() + ")";
		List<Map> emailList = mapDao.executeSql(sqlQuery);
		if(!emailList.isEmpty()){
			for(Map map : emailList){
				String email = (String) map.get("email");
				String defaultEmail = (String) map.get("defaultemail");
				String houseIEEE = (String) map.get("houseIEEE");
				String houseName = (String) map.get("name");
				String subject = "z203-" + houseName + "-Cloud离线通知";
				JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
				// 设定mail server
				senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
				senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
				// 密码解密
				senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
				// 建立HTML邮件消息
				MimeMessage mailMessage = senderImpl.createMimeMessage();
				// true表示开始附件模式
				MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
				// 设置收件人，寄件人
				if(StringUtils.isNotBlank(email)) {
					messageHelper.setTo(email);
					messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
					messageHelper.setSubject(subject);
					messageHelper.setText("家庭名称是:"+houseName+"的SHC离线了,IEEE是:"+houseIEEE+",离线时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
					senderImpl.send(mailMessage);
//					logger.info(houseIEEE + "离线了，监控邮箱" + email);
				}
				mailMessage = senderImpl.createMimeMessage();
				// true表示开始附件模式
				messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
				// 设置收件人，寄件人
				if(StringUtils.isNotBlank(defaultEmail)) {
					messageHelper.setTo(defaultEmail);
					messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
					messageHelper.setSubject(subject);
					messageHelper.setText("默认邮箱家庭名称是:"+houseName+"的SHC离线了,IEEE是:"+houseIEEE+",离线时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
					senderImpl.send(mailMessage);
				}
//				logger.info(houseIEEE + "离线了，默认邮箱" + defaultEmail);
			}
		}
		logger.info("发送离线邮件结束---------");
	}
	
	// 在线
//	if (type.equals("1")) {
	if (type == 1 && !houseIeees.isEmpty()) {
		logger.info("发送在线邮件开始---------");
		//查询要发送的邮箱地址
		StringBuilder sql = new StringBuilder("select b.name,a.houseIEEE,a.email,a.defaultemail,a.emaildescription from houseservice a left join house b " +
				"on a.houseIEEE = b.houseIEEE where b.isonline=0 and a.monitor_state=1 and b.houseIEEE in (");
		Iterator hItor = houseIeees.iterator();
		while(hItor.hasNext())
			sql.append("'").append(hItor.next()).append("',");
		String sqlQuery = sql.deleteCharAt(sql.length() - 1).toString() + ")";
		   List<Map> emailList2 = mapDao.executeSql(sqlQuery);
			if(!emailList2.isEmpty()) {
				for(Map map2 : emailList2) {
					String email2 = (String) map2.get("email");
					String defaultEmail2 = (String) map2.get("defaultemail");
					String houseIEEE2 = (String) map2.get("houseIEEE");
					String houseName2 = (String) map2.get("name");
					String subject2 = "z203-" + houseName2 + "-Cloud上线通知";					
					JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
					// 设定mail server
					senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
					senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
					// 密码解密
					senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
					// 建立HTML邮件消息
					MimeMessage mailMessage = senderImpl.createMimeMessage();
					// true表示开始附件模式
					MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
					// 设置收件人，寄件人
					if(StringUtils.isNotBlank(email2)) {
						messageHelper.setTo(email2);
						messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
						messageHelper.setSubject(subject2);
						messageHelper.setText("家庭名称是:"+houseName2+"的SHC上线了,IEEE是"+houseIEEE2+",上线时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
						senderImpl.send(mailMessage);
//						logger.info(houseIEEE2 + "上线了，监控邮箱" + email2);
					}
					// 建立HTML邮件消息
					mailMessage = senderImpl.createMimeMessage();
					// true表示开始附件模式
					messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
					// 设置收件人，寄件人
					if(StringUtils.isNotBlank(defaultEmail2)) {
						messageHelper.setTo(defaultEmail2);
						messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
						messageHelper.setSubject(subject2);
						messageHelper.setText("默认邮箱家庭名称是:"+houseName2+"的SHC上线了,IEEE是"+houseIEEE2+",上线时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
						senderImpl.send(mailMessage);
//						logger.info(houseIEEE2 + "上线了，默认邮箱" + defaultEmail2);
					}
				}
			}
			logger.info("发送在线邮件结束---------");
		}

	}
	
	
	/**
	 * 监控日志添加记录时删除一直不在线信息
	 */
	public void deletestilloff(List<Map> houseIeees){
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);
		String sql = "delete from " + tableName + "  where staterecords='4' and houseIEEE in (";
		if(houseIeees != null && houseIeees.size()>0){
			for(int i=0;i<houseIeees.size();i++){
				sql += "'"+houseIeees.get(i).get("houseIEEE")+"',";
			}
			sql = sql.substring(0, sql.length()-1);
			sql += ")";
		} else {
			sql = sql + "'')";
		}
		int logList = mapDao.executeSql2(sql);
	}
	
	/**
	 * 添加监控日志
	 */
	public void insertMonitorlog(List<Map> houseonIeees,List<Map> houseoffIeees) {
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);
		//一直不在线	
		String sql3 = "select b.houseIEEE from house b left join houseservice a on a.houseIEEE = b.houseIEEE " +
				"left join " + tableName + " c on c.houseIEEE = b.houseIEEE where b.isonline=0 and a.monitor_state=1 and (c.staterecords is null) ";
		List<Map> logList = null;
		try {
			logList = mapDao.executeSql(sql3);
		} 
		catch(Exception e) {
			if(e != null && e.getMessage().indexOf(tableName) != -1) {
				createMonitorAndInsert(tableName, null);
			}
			else {
				logger.error("query 4 monitorlog exception", e);
			}
		}
		if(logList != null && logList.size()>0){
			String sql4 = "insert into " + tableName + "(houseIEEE,staterecords) values";
			for(int i=0;i<logList.size();i++){
				sql4+="('";
				sql4+=logList.get(i).get("houseIEEE");
				sql4+="',";
				sql4+="4),";//4表示一直不在线的状态
			}
			sql4 = sql4.substring(0, sql4.length()-1);
			mapDao.executeSql2(sql4);
		}
		//在线变离线
		if(houseoffIeees != null && houseoffIeees.size() >0){
		String sql5 ="select b.houseIEEE from houseservice a right join house b " +
				"on a.houseIEEE = b.houseIEEE where a.monitor_state=1 and b.houseIEEE in (";
			for (Map map : houseoffIeees) {
				sql5 += "'" + map.get("houseIEEE") +"',";
			}
			sql5 = sql5.substring(0, sql5.length()-1);
			sql5 += ")";
		
			List<Map> logList5 = mapDao.executeSql(sql5);
			if(logList5.size()>0){
				String sql6 = "insert into " + tableName + "(houseIEEE,staterecords) values";
				for(int i=0;i<logList5.size();i++){
					sql6+="('";
					sql6+=logList5.get(i).get("houseIEEE");
					sql6+="',";
					sql6+="10),";//10表示之前是在线，现在为离线的状态
				}
				sql6 = sql6.substring(0, sql6.length()-1);
				try {
					mapDao.executeSql2(sql6);
				} 
				catch(Exception e) {
					if(e != null && e.getMessage().indexOf(tableName) != -1) {
						createMonitorAndInsert(tableName, sql6);
					}
					else {
						logger.error("insert 10 monitorlog exception", e);
					}
				}
				this.deletestilloff(logList5);//由在线变成离线，插入离线信息时删除之前已经存在的一直不在线记录
			}	
		}
		//离线变在线
		if(houseonIeees != null && houseonIeees.size() >0){
			String sql7 ="select b.houseIEEE from houseservice a right join house b " +
					"on a.houseIEEE = b.houseIEEE where a.monitor_state=1 and b.houseIEEE  in (";
			
				for (Map map : houseonIeees) {
					sql7 += "'" + map.get("houseIEEE") +"',";
				}
				sql7 = sql7.substring(0, sql7.length()-1);
				sql7 += ")";
			
			List<Map> logList7 = mapDao.executeSql(sql7);
			if(logList7.size()>0){
				String sql8 = "insert into " + tableName + "(houseIEEE,staterecords) values";
				for(int i=0;i<logList7.size();i++){
					sql8+="('";
					sql8+=logList7.get(i).get("houseIEEE");
					sql8+="',";
					sql8+="11),";//11表示之前是离线，现在为在线的状态
				}
				sql8 = sql8.substring(0, sql8.length()-1);
				try {
					mapDao.executeSql2(sql8);
				} 
				catch(Exception e) {
					if(e != null && e.getMessage().indexOf(tableName) != -1) {
						createMonitorAndInsert(tableName, sql8);
					}
					else {
						logger.error("insert 11 monitorlog exception", e);
					}
				}
				this.deletestilloff(logList7);//由离线变成在线，插入在线信息时删除之前已经存在的一直不在线记录
			}
		}
	}
	
	/**
	 * 创建新表并插入数据
	 * @param tableName
	 * @param sql
	 */
	private void createMonitorAndInsert(String tableName, String sql) {
		String cSql = "create table if not exists " + tableName + " like monitorlog";
		mapDao.executeSql2(cSql);
		if(StringUtils.isNotBlank(sql)) {
			mapDao.executeSql2(sql);
		}
	}
	
	@Override
	public void updateZ203Isonline(final List<Map> liston, final List<Map> listoff,String xmppIp) throws Exception {		
		//在修改203在线状态之前添加监控日志
	    this.insertMonitorlog(liston,listoff);
	}

	@Override
	public List<Modenode> isExist203206(Modenode modenode) throws Exception {
		String sql = "select * from modenode where houseId = :houseId and (modeNodeLibId = 86 or modeNodeLibId = 145)";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("houseId",modenode.getHouseId());
		List<Modenode> t =modenodeDao.findSql(sql,param,Modenode.class);
		return t;
		
	}
	
	/**
	 * 查询监控日志
	 */
	@Override
	public Map findMonitorlog(Map<String, Object> param, int startRow, int pageSize,String orderBy) throws Exception {
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql =new StringBuilder("SELECT a.id,a.houseIEEE,a.staterecords,a.statechangetime,b.name,b.clientCode,b.client_id ")
		.append("FROM ").append(tableName).append(" a INNER JOIN house b on a.houseIEEE = b.houseIEEE ")
		.append("INNER JOIN reliclient r ON b.client_id = r.id INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc ")
		.append("INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id = :userid) c ON b.client_id = c.client_id ")
		.append("WHERE 1=1 ");
		Integer userid = (Integer) param.get("userid");
		params.put("userid", userid);
		String searchText = (String) param.get("searchText");
		if(StringUtils.isNotBlank(searchText)){
			params.put("searchText", "%"+searchText+"%");
			sql.append("and (a.houseIEEE like :searchText ");
			if(searchText.equals("在线")){
				sql.append("or (a.staterecords = 11 or a.staterecords = 1) ");
			}
			if(searchText.equals("离线")){
				sql.append("or (a.staterecords =10 or a.staterecords = 4 or a.staterecords = 0) ");
			}
			sql.append("or a.statechangetime like binary :searchText ");//在 MySQL 5.5 以上, 若欄位 Type 是 time,date,datetime在 select 時若使用 like '%中文%' 會出現 Illegal mix of collations for operation 'like'在寫程式時要對每個欄位進行搜尋,
																		//在執行時可能就會出現時間欄位 like '%中文%' 這種語法,這在舊版的 MySQL 是不會出現錯誤的.升到 MySQL 5.5 以上, 必需改成 like binary '%中文%' 即可避免出現錯誤.
			if(searchText.equals("无")){	
				sql.append("or (b.name is null or b.name ='' ) ");
			}
			else {
				sql.append("or b.name like  :searchText ");
			}
			
			sql.append("or r.client_code like :searchText) ");
		}
		if(StringUtils.isNotBlank((String)param.get("staterecords")) && !param.get("staterecords").equals("-1") ) {
			sql.append(" and a.staterecords =:staterecords ");
			params.put("staterecords", param.get("staterecords"));
		}
			Date t1 = new Date();
		if(param.get("fanwei").equals("today")){
			//日期转换成字符串：  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(param.get("fanwei").equals("yesterday")){
			//日期转换成字符串：  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(strcal).append("' and '").append(str).append("'");
		}
		if(param.get("fanwei").equals("nearseven")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.DAY_OF_MONTH, -7);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(param.get("fanwei").equals("nearmouth")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -1);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		
		if(param.get("fanwei").equals("bannian")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -6);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		
		if(param.get("fanwei").equals("yinian")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -12);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(StringUtils.isNotBlank((String)param.get("starttime"))) {
			sql.append(" and a.statechangetime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
		}
		if(StringUtils.isNotBlank(orderBy)&&!orderBy.equals("undefined")&&!(orderBy.equals("a.staterecords asc")||orderBy.equals("a.staterecords desc"))) { 
			sql.append(" order by ").append(orderBy).append(" ");
		} 
		else {
			sql.append(" order by a.statechangetime desc ");
		}
		sql.append("limit ").append(startRow).append(",").append(pageSize);
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		
		//String houseIeee = t.get(0).get("houseIEEE").toString();
		/*Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT DISTINCT t.staterecords FROM  ").append(tableName).append(" t where 1=1 ");
		if(StringUtils.isNotBlank(searchText)) {
			hql.append(" and t.houseIEEE =:houseIeee");
			map.put("houseIeee", searchText);
		}
		logger.info("查找日志列表的sql语句：=========》:"+ hql);
		List<Map> list = mapDao.executeSql(hql.toString(), map);
		//Map<String, Object> rt = new HashMap<String, Object>();
		rt.put("list", list);*/
		rt.put("t", t);
 		return rt;
	}
	
	
	@Override
	public int getMonitorlogCount(Map<String, Object> param){
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> params = new HashMap<String, Object>(); 
		StringBuilder sql = new StringBuilder("Select Count(*) as aCount from ").append(tableName).append(" a left join house b on a.houseIEEE=b.houseIEEE ");
		sql.append("INNER JOIN reliclient r ON b.client_id = r.id ");
		sql.append("INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id = :userid) c ON b.client_id = c.client_id ");
		sql.append("WHERE 1=1 ");
		Integer userid = (Integer) param.get("userid");
		params.put("userid", userid);
		String searchText = (String) param.get("searchText");
		if(StringUtils.isNotBlank(searchText)){
			params.put("searchText", "%"+searchText+"%");
			sql.append("and (a.houseIEEE like :searchText ");
			if(searchText.equals("在线")){
				sql.append("or (a.staterecords = 11 or a.staterecords = 1) ");
			}
			if(searchText.equals("离线")){
				sql.append("or (a.staterecords = 10 or a.staterecords = 4 or a.staterecords = 0)  ");
			}
			sql.append("or a.statechangetime like binary :searchText ");//在 MySQL 5.5 以上, 若欄位 Type 是 time,date,datetime在 select 時若使用 like '%中文%' 會出現 Illegal mix of collations for operation 'like'在寫程式時要對每個欄位進行搜尋,
																		//在執行時可能就會出現時間欄位 like '%中文%' 這種語法,這在舊版的 MySQL 是不會出現錯誤的.升到 MySQL 5.5 以上, 必需改成 like binary '%中文%' 即可避免出現錯誤.
			if(searchText.equals("无")){	
				sql.append("or (b.name is null or b.name ='') ");
			}
			else {
				sql.append("or b.name like  :searchText ");
			}
			
			sql.append("or r.client_code like :searchText) ");
		}
		if(StringUtils.isNotBlank((String)param.get("staterecords")) && !param.get("staterecords").equals("-1") ) {
			sql.append(" and a.staterecords =:staterecords ");
			params.put("staterecords", param.get("staterecords"));
		}
			Date t1 = new Date();
			
		if(param.get("fanwei").equals("today")){
			//日期转换成字符串：  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(param.get("fanwei").equals("yesterday")){
			//日期转换成字符串：  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(strcal).append("' and '").append(str).append("'");
		}
		if(param.get("fanwei").equals("nearseven")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.DAY_OF_MONTH, -7);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(param.get("fanwei").equals("nearmouth")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -1);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		
		if(param.get("fanwei").equals("bannian")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -6);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		
		if(param.get("fanwei").equals("yinian")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -12);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(StringUtils.isNotBlank((String)param.get("starttime"))) {
			sql.append(" and a.statechangetime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
		}
		logger.info("查找日志列表数量的sql语句：=========》:"+ sql);
		List<Map> cList=mapDao.executeSql(sql.toString(), params);
		return ((BigInteger)(cList.get(0).get("aCount"))).intValue();
	}

	/**
	 * 统计监控情况个数
	 * @return
	 */
	@Override
	public List<Map> shcCount(Integer userId){
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		StringBuilder sql = new StringBuilder("SELECT f.* FROM (");
		//计算监控SHC数量
		sql.append("SELECT 1 monitorlogCount,count(a.houseIEEE) mCount ");
		sql.append("FROM houseservice a "); 
		sql.append("INNER JOIN house b ON a.houseIEEE = b.houseIEEE "); 
		sql.append("INNER JOIN ( ");
		sql.append("SELECT DISTINCT c.client_id FROM reliroleclient c INNER JOIN reliuserrole d ON c.role_id = d.role_id WHERE d.user_id = :userId ");
		sql.append(") e ON b.client_id = e.client_id ");
		sql.append("WHERE monitor_state='1' ");
		sql.append("UNION ");
		//变化
		sql.append("SELECT 2 monitorchange,count(DISTINCT a.houseIEEE) mCount "); 
		sql.append("FROM ").append(tableName).append(" a "); 
		sql.append("INNER JOIN house b ON a.houseIEEE = b.houseIEEE "); 
		sql.append("INNER JOIN houseservice g ON a.houseIEEE = g.houseIEEE ");
		sql.append("INNER JOIN ( "); 
		sql.append("SELECT DISTINCT c.client_id FROM reliroleclient c INNER JOIN reliuserrole d ON c.role_id = d.role_id WHERE d.user_id = :userId "); 
		sql.append(") e ON b.client_id = e.client_id "); 
		sql.append("WHERE (staterecords = '0' or staterecords = '1') AND  g.monitor_state = '1' "); 
		sql.append("UNION ");
		//一直不在线
		sql.append("SELECT 3 stilloffline,count(DISTINCT a.houseIEEE) mCount "); 
		sql.append("FROM ").append(tableName).append(" a ");
		sql.append("INNER JOIN house b ON a.houseIEEE = b.houseIEEE "); 
		sql.append("INNER JOIN houseservice g ON a.houseIEEE = g.houseIEEE ");
		sql.append("INNER JOIN ( ");
		sql.append("SELECT DISTINCT c.client_id FROM reliroleclient c INNER JOIN reliuserrole d ON c.role_id = d.role_id WHERE d.user_id = :userId ");
		sql.append(") e ON b.client_id = e.client_id ");
		sql.append("WHERE a.staterecords = '4' AND  g.monitor_state = '1') f ORDER BY f.monitorlogCount ASC");
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		return t;
	}
	
	/**
	 * 导出监控日志
	 */
	@Override
	public int exportMonLogExcel(Map<String, Object> param, HttpServletRequest request,HttpServletResponse  response) {
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql =new StringBuilder("select distinct a.id,a.houseIEEE,a.staterecords,a.statechangetime,b.name,b.clientCode from ");
		sql.append(tableName).append(" a INNER join house b on a.houseIEEE=b.houseIEEE ")
		.append("INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id ")
		.append("WHERE rur.user_id = :userid) c ON b.client_id = c.client_id where 1=1 ");
		String userid = (String)param.get("userid");
		params.put("userid", userid);
		String searchText = (String) param.get("searchText");
		if(StringUtils.isNotBlank(searchText)){
			params.put("searchText", "%"+searchText+"%");
			sql.append("and (a.houseIEEE like :searchText ");
			if(searchText.equals("在线")){
				sql.append("or a.staterecords =1  ");
			}
			if(searchText.equals("离线")){
				sql.append("or (a.staterecords =0 or a.staterecords = 3)  ");
			}
			sql.append("or a.statechangetime like binary :searchText ");//在 MySQL 5.5 以上, 若欄位 Type 是 time,date,datetime在 select 時若使用 like '%中文%' 會出現 Illegal mix of collations for operation 'like'在寫程式時要對每個欄位進行搜尋,
																		//在執行時可能就會出現時間欄位 like '%中文%' 這種語法,這在舊版的 MySQL 是不會出現錯誤的.升到 MySQL 5.5 以上, 必需改成 like binary '%中文%' 即可避免出現錯誤.
			if(searchText.equals("无")){	
				sql.append("or (b.name is null or b.name ='') ");
			}
			else {
				sql.append("or b.name like  :searchText ");
			}
			
			sql.append("or b.clientCode like :searchText) ");
		}
		if(StringUtils.isNotBlank((String)param.get("staterecords")) && !param.get("staterecords").equals("-1")){
			sql.append(" and a.staterecords =:staterecords ");
			params.put("staterecords", param.get("staterecords"));
		}
			Date t1 = new Date();
			
		if(param.get("fanwei").equals("today")){
			//日期转换成字符串：  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(param.get("fanwei").equals("yesterday")){
			//日期转换成字符串：  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(t1);  
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, -1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(strcal).append("' and '").append(str).append("'");
		}
		if(param.get("fanwei").equals("nearseven")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.DAY_OF_MONTH, -7);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(param.get("fanwei").equals("nearmouth")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -1);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		
		if(param.get("fanwei").equals("bannian")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -6);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		
		if(param.get("fanwei").equals("yinian")){
			//日期转换成字符串：  
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			 calendar.setTime(t1);
			 calendar.add(Calendar.MONTH, -12);
			String str=sdf.format(calendar.getTime());  
		    calendar.setTime(t1);
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    String strcal=sdf.format(calendar.getTime());
			sql.append("and a.statechangetime between '").append(str).append("' and '").append(strcal).append("'");
		}
		if(StringUtils.isNotBlank((String)param.get("starttime"))) {
			sql.append(" and a.statechangetime between '").append(param.get("starttime")).append("' and '").append(param.get("endtime")).append("'");
		}
		sql.append(" order by a.statechangetime desc ");
		sql.append(" limit 50000 ");
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		BufferedOutputStream os = null;
		try {
		if(param.get("lang").equals("1")){
			//生成excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			sheet.setColumnWidth(1, 3500);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(5, 6000);
			workbook.setSheetName(0, "List of monitoring log");
			HSSFRow row = sheet.createRow((short)0);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue("Serial number");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue("Household name");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("IEEE address");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue("Customer code");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue("Status logging");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
			cell.setCellValue("Status change time");
			cell.setCellStyle(titleStyle);
			int monlogSize = t.size();
			for(int i = 0; i < monlogSize; i++) {
				row = sheet.createRow(i + 1);
				cell = row.createCell(0, Cell.CELL_TYPE_STRING);
				cell.setCellValue(i + 1);
				cell = row.createCell(2, Cell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("houseIEEE").toString());
				cell = row.createCell(1, Cell.CELL_TYPE_STRING);
				if(t.get(i).get("name")==null||t.get(i).get("name").equals("")){
					cell.setCellValue("");
				}else{
					cell.setCellValue( t.get(i).get("name").toString());
				}
				
				cell = row.createCell(3, Cell.CELL_TYPE_STRING);
				if(t.get(i).get("clientCode")==null||t.get(i).get("clientCode").equals("")){
					cell.setCellValue("");
				}else{
					cell.setCellValue( t.get(i).get("clientCode").toString());
				}
				cell = row.createCell(4, Cell.CELL_TYPE_STRING);
				if(t.get(i).get("staterecords").toString().equals("0")||t.get(i).get("staterecords").toString().equals("3")){
					cell.setCellValue("Offline");
				}
				else if(t.get(i).get("staterecords").toString().equals("1")||t.get(i).get("staterecords").toString().equals("2")){
					cell.setCellValue("Online");
				}
				else if(t.get(i).get("staterecords").toString().equals("11")){
					cell.setCellValue("Connect successfully");
				}else if(t.get(i).get("staterecords").toString().equals("10")) {
					cell.setCellValue("Disconnect connection");
				}else if(t.get(i).get("staterecords").toString().equals("8")) {
					cell.setCellValue("Reboot");
				}
				cell = row.createCell(5, Cell.CELL_TYPE_STRING);
				cell.setCellValue( t.get(i).get("statechangetime").toString());
			}
			//设置response参数
			//对中文字符转码
			String fileName = "List of monitoring log.xls";		
			fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//			response.addHeader("Content-Disposition", "inline;filename=" + fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
		}else {
			//生成excel文件
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			sheet.setColumnWidth(1, 3500);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(5, 6000);
			workbook.setSheetName(0, "监控日志");
			HSSFRow row = sheet.createRow((short)0);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFCell cell = row.createCell((short)0, Cell.CELL_TYPE_STRING);
			cell.setCellValue("序号");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)1, Cell.CELL_TYPE_STRING);
			cell.setCellValue("家的名称");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)2, Cell.CELL_TYPE_STRING);
			cell.setCellValue("IEEE地址");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)3, Cell.CELL_TYPE_STRING);
			cell.setCellValue("客户代码");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)4, Cell.CELL_TYPE_STRING);
			cell.setCellValue("状态记录");
			cell.setCellStyle(titleStyle);
			cell = row.createCell((short)5, Cell.CELL_TYPE_STRING);
			cell.setCellValue("状态变化时间");
			cell.setCellStyle(titleStyle);
			int monlogSize = t.size();
			for(int i = 0; i < monlogSize; i++) {
				row = sheet.createRow(i + 1);
				cell = row.createCell(0, Cell.CELL_TYPE_STRING);
				cell.setCellValue(i + 1);
				cell = row.createCell(2, Cell.CELL_TYPE_STRING);
				cell.setCellValue(t.get(i).get("houseIEEE").toString());
				cell = row.createCell(1, Cell.CELL_TYPE_STRING);
				if(t.get(i).get("name")==null||t.get(i).get("name").equals("")){
					cell.setCellValue("");
				}else{
					cell.setCellValue( t.get(i).get("name").toString());
				}
				
				cell = row.createCell(3, Cell.CELL_TYPE_STRING);
				if(t.get(i).get("clientCode")==null||t.get(i).get("clientCode").equals("")){
					cell.setCellValue("");
				}else{
					cell.setCellValue( t.get(i).get("clientCode").toString());
				}
				cell = row.createCell(4, Cell.CELL_TYPE_STRING);
				if(t.get(i).get("staterecords").toString().equals("0")||t.get(i).get("staterecords").toString().equals("3")){
					cell.setCellValue("离线");
				}
				else if(t.get(i).get("staterecords").toString().equals("1")||t.get(i).get("staterecords").toString().equals("2")){
					cell.setCellValue("在线");
				}
				else if(t.get(i).get("staterecords").toString().equals("11")){
					cell.setCellValue("连接成功");
				}else if(t.get(i).get("staterecords").toString().equals("10")) {
					cell.setCellValue("断开连接");
				}else if(t.get(i).get("staterecords").toString().equals("8")) {
					cell.setCellValue("重启");
				}
				cell = row.createCell(5, Cell.CELL_TYPE_STRING);
				cell.setCellValue( t.get(i).get("statechangetime").toString());
			}
			//设置response参数
			//对中文字符转码
			String fileName = "监控日志.xls";		
			fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//			response.addHeader("Content-Disposition", "inline;filename=" + fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			os = new BufferedOutputStream(response.getOutputStream());
			workbook.write(os);
			os.flush();
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
		logger.info("exportMonLogExcel", e);
	} finally {
		try {
			if(os != null)
				os.close();
		} catch(Exception e) {
//			e.printStackTrace();
			logger.info("exportMonLogExcel close BufferedOutputStream", e);
		}
	}
		return 0;
	}
	
	public void mailService() throws Exception {
		if(isMail == 1) {
			String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);
			logger.info("Z203 上线和掉线情况的每日报告汇总");
			String subject = "Z203 上线和掉线情况的每日报告";
			String serverIp = PropertiesUtil.getProperty("cloudAddress");
			String serverPort = PropertiesUtil.getProperty("cloudPort");
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
			// 设定mail server
			senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
			senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
			// 密码解密
			senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
			// 建立HTML邮件消息
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			// true表示开始附件模式
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			// 设置收件人，寄件人
			messageHelper.setTo(PropertiesUtil.getProperty("mail.to"));
			messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
			messageHelper.setSubject(subject);
			
			//已注册的shc总台数
			String sqlcount = new String("select count(a.houseIeee) as hcount from house a");
			List<Map> countshc2 = mapDao.executeSql(sqlcount.toString());
			int countshc = ((BigInteger) (countshc2.get(0).get("hcount"))).intValue();
			//查询shc已开启 
			String sql = "select DISTINCT a.houseIeee  from " + tableName + " a left join houseservice b " +
					"on a.houseIEEE = b.houseIEEE where b.monitor_state =1";
			List<Map> mList = mapDao.executeSql(sql.toString());
			if (mList.size()>0) {
				//一直不在线 SHC总台数
				String sqloffcount = "select  count(distinct a.houseIEEE) as hoff from " + tableName + " a where a.staterecords=3 " +
						"ORDER BY a.statechangetime DESC ";
				List<Map> offount2 = mapDao.executeSql(sqloffcount.toString());
				int offount = ((BigInteger) (offount2.get(0).get("hoff"))).intValue();
				//在线_离线状态变化的 SHC总台数
				String sqlonoffcount = new String("select houseIeee,count(a.houseIeee) as sum from " + tableName + " a " +
						"where (staterecords = 0 or staterecords = 1) and TIMESTAMPDIFF(Day, statechangetime, now()) <= 1 group by houseIeee  ");
				List<Map> onoffcount = mapDao.executeSql(sqlonoffcount.toString());
				int onoffcountStr = onoffcount.size();
				int onoff = ((BigInteger) (onoffcount.get(0).get("sum"))).intValue();
				int i =0;
				String houseIeee = "";
				String sum = "";
				String contentStr = "";
	//			logger.info(onoffcount.size());
				SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 ");//设置日期格式
				String content = df.format(new Date())+"奈伯思 SHC 在线_离线情况监控如下：</br>"+"已注册 SHC "
						+countshc+" 台，一直不在线 "+offount+" 台，发生在线_离线状态变化的 SHC "+onoffcountStr+" 台.具体如下："
								+" <!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"> <HTML> <HEAD><TITLE>A Servlet" +
								"</TITLE></HEAD> <BODY> <TABLE BORDER='1' CELLSPACING='0'><TR><TH>序号</TH><TH>HOUSEIEEE</TH>" +
								"<TH>发生次数</TH><TH>详情</TH></TR>";
				for(i=0;i < onoffcount.size();i++){
					
					houseIeee = onoffcount.get(i).get("houseIeee").toString();
					sum = onoffcount.get(i).get("sum").toString();
					
					contentStr +="<TR><TD>"+(i+1)+"</TD><TD>"+houseIeee+"</TD><TD>"+sum+"</TD><TD><A HREF='http://"+serverIp+":"+serverPort+"/zigBeeDevice/cloudManageWeb/manager_shc.html'>点击查看详情</A></TD></TR>" ;
				}
				contentStr.contains("</TABLE> </BODY></HTML>");
				content+=contentStr;
				messageHelper.setText(content, true);
				senderImpl.send(mailMessage);
			}
		}
	}
	
	/*
	 * 使用203的心跳来判断是否离线，时间频率是5分钟判断一次，如果连续3次没有收到心跳，那么判定该CSHC设备为离线状态,
                   如果收到心跳或者其他推送上来的数据，那么判断该HouseIEEE地址对应的CSHC设备为在线状态
	 */
	//离线
	public void updateCountOnline(){
		logger.info("update Count Online start···");
		try{
			final int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
			Iterator iterator=HouseController.maponline.keySet().iterator();
			//更新状态的houseIEEE字符串
			StringBuilder onIDs = new StringBuilder();
			StringBuilder offIDs = new StringBuilder();
			//记录在线和离线的houseIEEE
			List<String> onHIEEEList = new ArrayList<String>();
			List<String> offHIEEEList = new ArrayList<String>();
			//同步到代理服务器的数据
			List<Map<String, Object>> onMapList = new ArrayList<Map<String, Object>>();
			while (iterator != null && iterator.hasNext()) {
				String str = (String) iterator.next();
				Integer times = HouseController.maponline.get(str);
				if (times > 0) {
					onHIEEEList.add(str);
					HouseController.maponline.put(str, times - 1);
					String hql = "FROM House WHERE houseIeee='"+str+"'";
					House house = houseDao.get(hql);
					if(house!=null)
						onIDs.append(house.getId()).append(",");
					//缓存同步到代理服务器数据
					if(cloudMain != 1) {
						Map<String, Object> onMap = new HashMap<String, Object>();
						onMap.put("houseIEEE", str);
						onMap.put("times", times);
						onMapList.add(onMap);
					}
					//批量更新到数据库
					if(onIDs.length() >= 34200) {
						if(cloudMain == 1) {
							//发送邮件
							if(isMail == 1) {
								try {
									abtainEmailAndSendSHC(onHIEEEList,1);
								} catch (Exception e) {
									logger.info("updateCountOnline send email",e);
								}
							}
							//批量记录监控日志
							insertMonitorlog(onHIEEEList,1);
						}
						//更新状态
						String onIDsTmp = onIDs.deleteCharAt(onIDs.length() - 1).toString();
						String sql = "update house set isonline = '1',lasttime=now() where id in ("+ onIDsTmp +")";
						houseDao.executeSql2(sql);
						sql = "update device d inner join house h on d.houseIEEE = h.houseIEEE "
								+ "set d.isonline = '1' where d.houseIEEE =d.ieee and h.id in("+onIDsTmp+")";
						mapDao.executeSql2(sql);
						//同步代理服务器的houseIEEE是否在线
						syncProxyServerOnline(onMapList, cloudMain);
						onHIEEEList = new ArrayList<String>();
						onIDs = new StringBuilder();
						onMapList.clear();
					}
				} else {
					offHIEEEList.add(str);
					String hql = "FROM House WHERE houseIeee='"+str+"'";
					House house = houseDao.get(hql);
					if(house!=null)
						offIDs.append(house.getId()).append(",");
					if(offIDs.length() >= 34200) {
						if(cloudMain == 1) {
							//发送邮件
							if(isMail == 1) {
								try {
									abtainEmailAndSendSHC(offHIEEEList,0);
								} catch (Exception e) {
									logger.info("updateCountOffline send email",e);
								}
							}
							//批量记录监控日志
							insertMonitorlog(offHIEEEList,0);
						}
						//更新状态
						String offIDsTmp = offIDs.deleteCharAt(offIDs.length() - 1).toString();
						String sql = "update device d inner join house h on d.houseIEEE = h.houseIEEE "
								+ "set d.isonline = '0' where d.houseIEEE =d.ieee and h.id in("+offIDsTmp+")";
						//"update house set isonline = '0' where id in ("+ offIDsTmp +")";
						houseDao.executeSql2(sql);
						offHIEEEList = new ArrayList<String>();
						offIDs = new StringBuilder();
					}
				}
			}
			//记录监控日志
			if(!onHIEEEList.isEmpty()) {
				if(cloudMain == 1) {
					//发送邮件
					if(isMail == 1) {
						try {
							abtainEmailAndSendSHC(onHIEEEList,1);
						} catch (Exception e) {
							logger.info("updateCountOnline send email",e);
						}
					}
					//批量记录监控日志
					insertMonitorlog(onHIEEEList,1);
				}
			}
			if(!offHIEEEList.isEmpty()) {
				if(cloudMain == 1) {
					//发送邮件
					if(isMail == 1) {
						try {
							abtainEmailAndSendSHC(offHIEEEList,0);
						} catch (Exception e) {
							logger.info("updateCountOffline send email",e);
						}
					}
					//批量记录监控日志
					insertMonitorlog(offHIEEEList,0);
				}
			}
			//更新剩余的houseIEEE
			if(onIDs.length() > 0) {
				String onIDsTmp = onIDs.deleteCharAt(onIDs.length() - 1).toString();
				String sql = "update house set isonline = '1',lasttime=now() where id in ("+ onIDsTmp +")";
				houseDao.executeSql2(sql);
				sql = "update device d inner join house h on d.houseIEEE = h.houseIEEE "
						+ "set d.isonline = '1' where d.houseIEEE =d.ieee and h.id in("+onIDsTmp+")";
				//sql = "update device set isonline='1',lasttime=now() where houseIEEE=ieee and id in (" + onIDsTmp + ")";
				houseDao.executeSql2(sql);
				//同步代理服务器的houseIEEE是否在线
				syncProxyServerOnline(onMapList, cloudMain);
			}
			if(offIDs.length() > 0) {
				String offIDsTmp = offIDs.deleteCharAt(offIDs.length() - 1).toString();
				String sql = "update house set isonline = '0' where id in ("+ offIDsTmp +")";
				houseDao.executeSql2(sql);
				sql = "update device d inner join house h on d.houseIEEE = h.houseIEEE "
						+ "set d.isonline = '0' where d.houseIEEE =d.ieee and h.id in("+offIDsTmp+")";
				//"update house set isonline = '0' where id in ("+ offIDsTmp +")";
				houseDao.executeSql2(sql);
			}
			logger.info("更新203是否在线成功---------");
		}catch(Exception e){
			logger.error("更新203是否在线异常--------", e);
		}
	}		
	
	/**
	 * 同步代理服务器是否在线
	 * @param houseIEEEs
	 * @param cloudMain
	 * @throws Exception
	 */
	private void syncProxyServerOnline(List<Map<String, Object>> houseIEEEs, int cloudMain) throws Exception {
		if(cloudMain != 1) {
			String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
		    String cloudPort = PropertiesUtil.getProperty("cloudPort");
		    String serverHost = InetAddress.getLocalHost().getHostAddress();
			if(!cloudAddress.equals(serverHost)
			    	&& !cloudAddress.equals("localhost") 
					&& !cloudAddress.equals("127.0.0.1")) {
			      String callUrl = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/houseController/syncProxyServerOnline.do";
				Map<String, Object> pMap = new HashMap<String, Object>();
				pMap.put("json", JSON.toJSONStringWithDateFormat(houseIEEEs, "yyyy-MM-dd HH:mm:ss"));
				TestHttpclient.postUrlWithParams(callUrl, pMap, "utf-8");
		    }
	    }
	}
	
	/**
	 * 添加监控日志
	 */
	public void insertMonitorlog(List<String> houseIeees,int type){
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);
		// 离线
		if (type == 0 && !houseIeees.isEmpty()) {
			//一直不在线
			StringBuilder sql = new StringBuilder("select b.houseIEEE from house b left join houseservice a "+
					"on a.houseIEEE = b.houseIEEE left join " + tableName + " c on c.houseIEEE = b.houseIEEE where b.isonline=0 and " +
					"a.monitor_state=1 and (c.staterecords is null) and b.houseIEEE in (");
			Iterator hItor = houseIeees.iterator();
			while(hItor.hasNext()) {
				sql.append("'").append(hItor.next()).append("',");
			}
			String sql3 = sql.deleteCharAt(sql.length() - 1) + ")";
			List<Map> logList = null;
			try {
				logList = mapDao.executeSql(sql3);
			} 
			catch(Exception e) {
				if(e != null && e.getMessage().indexOf(tableName) != -1) {
					createMonitorAndInsert(tableName, null);
				}
				else {
					logger.error("insert2 4 monitorlog exception", e);
				}
			}
			if(logList != null && !logList.isEmpty()){
				StringBuilder sql4 = new StringBuilder("insert into " + tableName + "(houseIEEE,staterecords) values");
				Iterator<Map> logItor = logList.iterator();
				while(logItor.hasNext()) {
					sql4.append("('");
					sql4.append(logItor.next().get("houseIEEE"));
					sql4.append("',");
					sql4.append("4),");//4表示一直不在线的状态
				}
				String sqlTmp = sql4.deleteCharAt(sql4.length()-1).toString();
				mapDao.executeSql2(sqlTmp);
			}
			//在线变离线
			StringBuilder sql5 = new StringBuilder("select b.houseIEEE from houseservice a right join house b " +
					"on a.houseIEEE = b.houseIEEE where b.isonline=1 and a.monitor_state=1 and b.houseIEEE in (");
			hItor = houseIeees.iterator();
			while(hItor.hasNext())
					sql5.append("'").append(hItor.next()).append("',");
			String sqlTmp = sql5.deleteCharAt(sql5.length() - 1) + ")";
			List<Map> logList5 = mapDao.executeSql(sqlTmp);
			if(!logList5.isEmpty()){
				StringBuilder sql6 = new StringBuilder("insert into " + tableName + "(houseIEEE,staterecords) values");
				StringBuilder sqlVal = new StringBuilder();
				Iterator<Map> logItor = logList5.iterator();
				while(logItor.hasNext()) {
					String houseIEEE = (String) logItor.next().get("houseIEEE");
					sql6.append("('");
					sql6.append(houseIEEE);
					sql6.append("',");
					sql6.append("0),");//0表示之前是在线，现在为离线的状态
					sqlVal.append("'");
					sqlVal.append(houseIEEE);
					sqlVal.append("',");
				}
				String sqlInsert = sql6.deleteCharAt(sql6.length() - 1).toString();
				try {
					mapDao.executeSql2(sqlInsert);
				} 
				catch(Exception e) {
					if(e != null && e.getMessage().indexOf(tableName) != -1) {
						createMonitorAndInsert(tableName, sqlInsert);
					}
					else {
						logger.error("insert2 0 monitorlog exception", e);
					}
				}
				
				//由在线变成离线，插入离线信息时删除之前已经存在的一直不在线记录
				String sqlDel = "delete from " + tableName + "  where staterecords='4' and houseIEEE  in (" + sqlVal.deleteCharAt(sqlVal.length() - 1) + ")";
				mapDao.executeSql2(sqlDel);				
			}
		}
		// 在线
		if (type == 1 && !houseIeees.isEmpty()){
				//离线变在线
				StringBuilder sql7 = new StringBuilder("select b.houseIEEE from houseservice a right join house b " +
						"on a.houseIEEE = b.houseIEEE where b.isonline=0 and a.monitor_state=1 and b.houseIEEE  in (");
				Iterator hItor = houseIeees.iterator();
				while(hItor.hasNext())
					sql7.append("'").append(hItor.next()).append("',");
				String sqlQeury = sql7.deleteCharAt(sql7.length() - 1) + ")";
				List<Map> logList7 = mapDao.executeSql(sqlQeury);
				if(!logList7.isEmpty()){
					StringBuilder sql8 = new StringBuilder("insert into " + tableName + "(houseIEEE,staterecords) values");
					StringBuilder sqlVal = new StringBuilder();
					Iterator<Map> logItor = logList7.iterator();
					while(logItor.hasNext()) {
						String houseIEEE = (String) logItor.next().get("houseIEEE");
						sql8.append("('");
						sql8.append(houseIEEE);
						sql8.append("',");
						sql8.append("1),");//1表示之前是离线，现在为在线的状态
						sqlVal.append("'");
						sqlVal.append(houseIEEE);
						sqlVal.append("',");
					}
					String sqlInsert = sql8.deleteCharAt(sql8.length() - 1).toString();
					try {
						mapDao.executeSql2(sqlInsert);
					} 
					catch(Exception e) {
						if(e != null && e.getMessage().indexOf(tableName) != -1) {
							createMonitorAndInsert(tableName, sqlInsert);
						}
						else {
							logger.error("insert2 1 monitorlog exception", e);
						}
					}
					
					//由离线变成在线，插入在线信息时删除之前已经存在的一直不在线记录
					String sqlDel = "delete from " + tableName + "  where staterecords='4' and houseIEEE  in (" + sqlVal.deleteCharAt(sqlVal.length() - 1) + ")";
					mapDao.executeSql2(sqlDel);
				}
		}
				
	}
	
	@Override
	public int updateZ203DeviceIsonline(String onHouseIEEEs) {
		if(StringUtils.isNotBlank(onHouseIEEEs)) {
			String onHourseIEEEstr = onHouseIEEEs.substring(0, onHouseIEEEs.length() - 1);
			String sql = "update device set isonline=1 where houseIEEE=ieee and houseIEEE in (" + onHourseIEEEstr + ")";
			mapDao.executeSql2(sql);
			sql = "update device set isonline=0 where houseIEEE=ieee and houseIEEE not in (" + onHourseIEEEstr + ")";
			mapDao.executeSql2(sql);
		}
		return 1;
	}
	
	/**
	 * 修改Z203这个设备为在线，houseIEEE跟ieee字段值一样。
	 * @author: zhuangxd
	 * 时间：2014-10-29 上午11:47:27
	 * @param onHouseIEEEs
	 * @param offlineHouseIeee
	 * @return
	 */
	public int updateZ203DeviceIsonline(String onHouseIEEEs, String offlineHouseIeee) {
		if(StringUtils.isNotBlank(onHouseIEEEs)) {
			String onHourseIEEEstr = onHouseIEEEs.substring(0, onHouseIEEEs.length() - 1);
			String sql = "update device set isonline=1 where houseIEEE=ieee and houseIEEE in (" + onHourseIEEEstr + ")";
			mapDao.executeSql2(sql);
		}
		if(StringUtils.isNotBlank(offlineHouseIeee)) {
			String offlineHouseIeeeStr = offlineHouseIeee.substring(0, offlineHouseIeee.length() - 1);
			String sql = "update device set isonline=0 where houseIEEE=ieee and houseIEEE in (" + offlineHouseIeeeStr + ")";
			mapDao.executeSql2(sql);
		}
		return 1;
	}
	@Override
	public List<Map> findhouseList(String startRow, String pageSize,String orderBy, Map<String, Object> paramMap,String userid) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct t.*,r.id id1,r.client_code,r.region,p.serverIp,CASE WHEN s.id IS NULL THEN 0 ELSE 1 END AS isAddThinkBox FROM house t INNER JOIN reliclient r on r.id=t.client_id  INNER JOIN proxyserver p on t.houseIeee=p.houseIeee ");
		sql.append("INNER JOIN reliroleclient rlc ON r.id = rlc.client_id");
		sql.append(" INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id");
		sql.append(" LEFT  JOIN slplatformuser s on t.houseIEEE = s.username");
		sql.append(" WHERE t.regionCode != 'none' and rur.user_id =:userId");
		sql.append(" and p.serverPort <> :serverPort");
//				.append(" and t.id in (select distinct id from house where client_id in (")
//				.append("select client_id from reliroleclient where role_id in(")
//				.append("select role_id from reliuserrole where user_id = :userid)))");;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId",userid);
		//IOS服务认证端口，默认为8443
		String serverPort = PropertiesUtil.getProperty("domain.ios.port");
		if(StringUtils.isBlank(serverPort))
			serverPort = "8443";
		params.put("serverPort", serverPort);
		if (StringUtils.isNotBlank((String) paramMap.get("houseIeee1"))) {
			sql.append(" and t.houseIeee like :houseIeee ");
			params.put("houseIeee","%"+ paramMap.get("houseIeee1")+"%");
		}
		if (StringUtils.isNotBlank((String) paramMap.get("houseIeee"))) {
			sql.append(" and t.houseIeee like :houseIeee ");
			params.put("houseIeee","%"+ paramMap.get("houseIeee")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("name"))) {
			sql.append(" and t.name like :name ");
			params.put("name", "%"+paramMap.get("name")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("clientCode"))) {
			sql.append(" and r.client_code like :clientCode ");
			params.put("clientCode", "%"+paramMap.get("clientCode")+"%");
		}
		
		if(StringUtils.isNotBlank((String)paramMap.get("isAdd"))) {
			if(paramMap.get("isAdd").equals("0")){
				sql.append(" and s.id IS NULL");
			}else if(paramMap.get("isAdd").equals("1")){
				sql.append(" and s.id IS NOT NULL");
			}
		}
		
		if (StringUtils.isNotBlank((String)paramMap.get("enableFlag"))) {
			sql.append(" and t.enableFlag like :enableFlag ");
			params.put("enableFlag", "%"+paramMap.get("enableFlag")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("serverIp"))) {
			sql.append(" and p.serverIp like :serverIp ");
			params.put("serverIp","%"+paramMap.get("serverIp")+"%");
		}
		if (StringUtils.isBlank(orderBy)) {
			sql.append(" order by t.id  desc");
		}else {
			sql.append(" order by ").append(orderBy);
		}
		sql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}	
	@Override
	public List<Map> getServerIP(Map<String, Object> paramMap) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT DISTINCT p.serverIp serverIp from Proxyserver p where 1=1 ");
		if (StringUtils.isNotBlank((String) paramMap.get("serverIp"))) {
			params.put("serverIp",paramMap.get("serverIp"));
			sql.append(" and p.serverIp = :serverIp ");
		}
		List<Map> t =mapDao.executeSql(sql.toString(),params);
		return t;
	}
	@Override
	public List<Map> cloudCount(Map<String, Object> paramMap) {
//		StringBuilder sql = new StringBuilder("select * from reliuserrole where user_id = :userId");
		StringBuilder sql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT 1 hCount,count(DISTINCT p.serverIp) as tCount,null as serverIp from proxyserver p  INNER JOIN house t on t.houseIEEE=p.houseIEEE INNER JOIN reliroleclient rlc ON t.client_id = rlc.client_id INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id =:userId ");
		sql.append("union ");
		sql.append("SELECT 2 hCount,count(DISTINCT t.houseieee) as tCount,null as serverIp from house t INNER JOIN reliroleclient rlc ON t.client_id = rlc.client_id INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id =:userId ");
		params.put("userId", paramMap.get("userId"));
		List<Map> t = mapDao.executeSql(sql.toString(),params);
		return t;
	}
	
	@Override
	public List<Map> getHousesInfo(List<String> houseIEEEs) {
		if(!houseIEEEs.isEmpty()) {
			String sql = "select a.name,c.client_code clientCode,b.serverIp serverIp,a.regionCode houseArea,a.houseIEEE from house a left join (select distinct houseIEEE,serverIp from proxyserver) b on a.houseIEEE=b.houseIEEE inner join reliclient c on a.client_id = c.id where a.houseIEEE in (";
			StringBuilder houseIEEEStr = new StringBuilder();
			for(String houseIEEE : houseIEEEs)
				houseIEEEStr.append("'").append(houseIEEE).append("',");
			String tmpStr = houseIEEEStr.deleteCharAt(houseIEEEStr.length() - 1).toString();
			sql += tmpStr + ")";
			return mapDao.executeSql(sql);
		}
		return null;
	}
	@Override
	public int addServer(Map<String, Object> param) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		String selectsql = "select 1 from serverlib where serverip =:serverip";
		pMap.put("serverip", param.get("serverip"));
		List<Map> list = mapDao.executeSql(selectsql.toString(), pMap);
		int t = -1;
		if(list.size()==0){
			String insertsql = "insert into serverlib(servername,serverip,maxcshc,serverline,warnnum,warnmail,serverdescription,serverlineEn,serverdomain,smstype,smsip) value(:servername,:serverip,:maxcshc,:serverline,:warnnum,:warnmail,:serverdescription,:serverlineEn,:serverdomain,:smstype,:smsip)";
			t = houseserviceDao.executeSql2(insertsql,param);
			return t;
		}
		else{
			return t;
		}
	}
	@Override
	public List<Map> findServerlib(String startRow, String pageSize,
			String orderBy, Map<String, Object> param) {
		Map<String, Object> pMap = new HashMap<String , Object>();		
		StringBuilder sql =new StringBuilder("select * from serverlib where 1=1");
		if(StringUtils.isNotBlank(param.get("id").toString())){
			sql.append(" and id = :id ");
			pMap.put("id", param.get("id"));
		}
		if(StringUtils.isNotBlank(orderBy)){
			sql.append(" order by id desc ").append(orderBy);
		}
		if(startRow!=null){
			sql.append(" order by id desc limit ").append(startRow).append(",").append(pageSize);
		}
		
		List<Map> list= mapDao.executeSql(sql.toString(),pMap);
		return list;
	}
	
	@Override
	public int getServerCount(Map<String, Object> param) {
		String sql = "select count(*) as aCount from serverlib where 1=1";
		List<Map> list= mapDao.executeSql(sql.toString());
		int i = ((BigInteger)(list.get(0).get("aCount"))).intValue();
		return i;
	}
	@Override
	public int updateServer(Map<String, Object> param) {
		if(param.get("serverdomain")!=null){
			//若域名发生了变化，则修改proxyserver表中相应记录的serverIP（type为3，端口为8443的记录）
			Map<String,Object> params = new HashMap<>();
			String sql1 = "UPDATE proxyserver p INNER JOIN serverlib s ON p.serverIp=s.serverdomain AND s.serverdomain<>:serverdomain SET p.serverIp=:serverdomain WHERE s.id=:id AND p.type='3'";
			params.put("serverdomain", param.get("serverdomain"));
			params.put("id", param.get("id"));
			mapDao.executeSql2(sql1, params);
		}
		String sql ="update serverlib set servername =:servername,serverip = :serverip,maxcshc = :maxcshc,serverline = :serverline,serverlineEn = :serverlineEn,warnnum = :warnnum,warnmail = :warnmail,serverdescription = :serverdescription,serverdomain=:serverdomain,smstype=:smstype,smsip=:smsip where id = :id";
		int t = houseserviceDao.executeSql2(sql, param);
		
		return t;
	}
	@Override
	public int deleteServer(Map<String, Object> param) {
		if(param==null)
			return 0;
		/*//更新要删除的服务器在proxyserver表中的的域名为IP
		String sql = "UPDATE proxyserver p INNER JOIN serverlib s ON p.serverIp=s.serverdomain AND s.serverip<>s.serverdomain SET p.serverIp=s.serverip  WHERE s.id=:id";
		mapDao.executeSql2(sql, param);
		*/
		//删除服务器前需先检测是否已经在该服务器上注册了网关
		String sql = "SELECT 1 FROM proxyserver p INNER JOIN serverlib s ON p.serverIp=s.serverip WHERE s.id=:id";
		List<Map> list = mapDao.executeSql(sql, param);
		if(list!=null&&list.size()>0){
			return -1;
		}else{
			sql ="delete from serverlib where id =:id";
			return mapDao.executeSql2(sql,param);
		}
	}
	
	@Override
	public int getCount2(Map<String, Object> paramMap, String house, String uncheckedHouses) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("Select Count(distinct h.houseIeee) as aCount from house h INNER JOIN proxyserver p on h.houseIeee=p.houseIeee ");
		sql.append("INNER JOIN reliclient r ON h.client_id = r.id ");
		sql.append("INNER JOIN reliroleclient rrc ON r.id = rrc.client_id ");
		sql.append("INNER JOIN reliuserrole rur ON rrc.role_id = rur.role_id ");
		sql.append("WHERE h.regionCode <> 'none' AND rur.user_id = :userId ");
		params.put("userId", paramMap.get("userid"));
		if (StringUtils.isNotBlank(house)) {
			sql.append("and h.houseIeee in ("+house+") ");
		}
		//跨页全选用到
		if(StringUtils.isNotBlank(uncheckedHouses) && !uncheckedHouses.equals("-2")) {
			sql.append("AND h.houseIEEE NOT IN (").append(uncheckedHouses).append(")");
		}
		
		if (StringUtils.isNotBlank((String) paramMap.get("houseIeee1"))) {
			sql.append(" and h.houseIeee like :houseIeee ");
			params.put("houseIeee","%"+ paramMap.get("houseIeee1")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("name"))) {
			sql.append(" and h.name like :name ");
			params.put("name", "%"+paramMap.get("name")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("clientCode"))) {
			sql.append(" and r.client_code like :clientCode ");
			params.put("clientCode", "%"+paramMap.get("clientCode")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("enableFlag"))) {
			sql.append(" and h.enableFlag like :enableFlag ");
			params.put("enableFlag", "%"+paramMap.get("enableFlag")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("serverIp"))) {
			sql.append(" and p.serverIp like :serverIp ");
			params.put("serverIp","%"+paramMap.get("serverIp")+"%");
		}
		List<Map> cList=mapDao.executeSql(sql.toString(), params);
		return ((BigInteger)(cList.get(0).get("aCount"))).intValue();
	}
	@Override
	public List<Map> findhouseList2(String startRow, String pageSize,
			String orderBy, Map<String, Object> paramMap, String house, String uncheckedHouses) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct h.name,r.client_code clientCode,r.client_code,h.regionCode,h.regionCode houseArea,h.houseIEEE,p.serverIp FROM house h INNER JOIN proxyserver p on h.houseIeee=p.houseIeee ");
		sql.append("INNER JOIN reliclient r ON h.client_id = r.id ");
		sql.append("INNER JOIN reliroleclient rrc ON r.id = rrc.client_id ");
		sql.append("INNER JOIN reliuserrole rur ON rrc.role_id = rur.role_id ");
		sql.append("WHERE h.regionCode <> 'none' AND rur.user_id = :userId ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId",paramMap.get("userid"));
		if (StringUtils.isNotBlank(house)) {
			sql.append(" and h.houseIeee in ("+house+") ");
		}
		if (StringUtils.isNotBlank((String) paramMap.get("houseIeee1"))) {
			sql.append(" and h.houseIeee like :houseIeee ");
			params.put("houseIeee","%"+ paramMap.get("houseIeee1")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("name"))) {
			sql.append(" and h.name like :name ");
			params.put("name", "%"+paramMap.get("name")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("clientCode"))) {
			sql.append(" and r.client_code like :clientCode ");
			params.put("clientCode", "%"+paramMap.get("clientCode")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("enableFlag"))) {
			sql.append(" and h.enableFlag like :enableFlag ");
			params.put("enableFlag", "%"+paramMap.get("enableFlag")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("serverIp"))) {
			sql.append(" and p.serverIp like :serverIp ");
			params.put("serverIp","%"+paramMap.get("serverIp")+"%");
		}
		//跨页全选时用到
		if (StringUtils.isNotBlank(uncheckedHouses) && !uncheckedHouses.equals("-2")) {
			sql.append("AND h.houseIEEE NOT IN (").append(uncheckedHouses).append(")");
		}
		if (StringUtils.isBlank(orderBy)) {
			sql.append(" order by h.id  desc");
		}else {
			sql.append(" order by ").append("h.id  desc");
		}
		if(StringUtils.isNotBlank(startRow)) {
			sql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
		}
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	@Override
	public int NoteEmailBatchOpen(Map<String, Object> param,List<String> houseieee) {
		// TODO Auto-generated method stub
		int t=0;
		Map<String,Object> mapsMap=new HashMap<String,Object>();
		String stawdateString="";
		String enddatedate="";
		if(param.get("state").equals("1"))
	    {
			stawdateString=param.get("StartDate").toString();
			enddatedate=param.get("EndDate").toString();
	    }
		for (String huoi : houseieee) {
			mapsMap.put("houseIEEE",huoi);
			param.put("houseIEEE",huoi);
			String sqlString="SELECT * FROM notewarncontrol where houseIEEE=:houseIEEE";
		    List<Notewarncontrol> notewarncontrolsli=noteBaseDao.executeSql(sqlString,mapsMap);
		    String sqlupdate="update notewarncontrol set state=:state where houseIEEE=:houseIEEE";
		    if(param.get("state").equals("1"))
		    {
		     sqlupdate="update notewarncontrol set state=:state,EndDate=:EndDate,StartDate=:StartDate where houseIEEE=:houseIEEE";
		    }
			String sqlinsert="INSERT INTO notewarncontrol(StartDate, EndDate, state, houseIEEE) VALUES (:StartDate,:EndDate,:state,:houseIEEE)";
		    if (notewarncontrolsli.size()>0) {
		    	if(param.get("state").equals("1"))
			    {
		    		param.put("StartDate",stawdateString);
		    		param.put("EndDate",enddatedate);
			    }
		    	
		    	
		    	 t+= mapDao.executeSql2(sqlupdate,param);
			}
		    else {
//		    	if(!param.get("state").equals("1"))
//		    	{
//		    		t+= mapDao.executeSql2(sqlinsert,param);	
//		    	}
//		    	else {
//					t=-1;
//				}
		    	t+= mapDao.executeSql2(sqlinsert,param);
			}	
		}
		return t;
	}
	private BaseDaoI<Messagehistory> messagebade;
	public BaseDaoI<Messagehistory> getMessagebade() {
		return messagebade;
	}
	@Autowired
	public void setMessagebade(BaseDaoI<Messagehistory> messagebade) {
		this.messagebade = messagebade;
	}
	@Override
	public String notebatchSend(Messagehistory messagehistory, List<String> houseieee) {
		// TODO Auto-generated method stub
		if(houseieee.isEmpty()) {
			logger.info("没有系统消息---------");
			return "0";
		}
		logger.info("发送系统消息---------");
		int succPhone = 0, failPhone = 0, succEmail = 0, failEmail = 0;
		//缓存已发送的houseIEEE的号码及邮件地址
		try {
			//判断是否勾选
			boolean isNoteSend = messagehistory.getContent().equals("false");
			boolean isEmailSend = messagehistory.getEmailcontent().equals("false");
			 //将houseIEEE组成字符串
			 StringBuilder hIEEEs = new StringBuilder();
			 Iterator<String> itor = houseieee.iterator();
			 while(itor.hasNext())
				 hIEEEs.append("'").append(itor.next()).append("',");
			 String hIEEEsStr = hIEEEs.deleteCharAt(hIEEEs.length() - 1).toString();
			 //发送系统短信
			 if(!isNoteSend) {
				 String sqlPhone = "SELECT a.houseIEEE,a.phoneNO from phoneno a where a.houseIEEE in (";
				 String hIEEEsPhone = sqlPhone + hIEEEsStr + ")";
				 List<Map> liPhones=mapDao.executeSql(hIEEEsPhone);
				 logger.info("手机号码----------:" + liPhones);
				 Iterator<Map> pItor = liPhones.iterator();
				 while(pItor.hasNext()) {
					 Map phoneMap = pItor.next();
					 String phoneNo = (String) phoneMap.get("phoneNO");
					 if(StringUtils.isNotBlank(phoneNo)) {
						 String houseIEEE = (String) phoneMap.get("houseIEEE");
						 messagehistory.setHouseIeee(houseIEEE);
						 messagehistory.setPhonenumber(phoneNo);
						 messagehistory.setSendtime(new Date());
						 messagehistory.setRemark("系统短信");
						 int noteid=sendnote(messagehistory.getContent(), phoneNo);
						 if(noteid > 0) {
							 succPhone++;
							 messagehistory.setState("1");
							 messagehistory.setType("1");
						 }
						 else {
							 messagehistory.setState("0");
							 messagehistory.setType("1");
							 failPhone++;
						 }
						 Messagehistory mh = new Messagehistory();
						 BeanUtils.copyProperties(messagehistory, mh);
						 messagebade.save(mh);
					 }
				 }
			 }
			 //发送系统邮件
			 if(!isEmailSend) {
				 String sqlEmail = "SELECT a.houseIEEE,a.warnEmail from warnemail a where a.houseIEEE in (";
				 String hIEEEsEmail = sqlEmail + hIEEEsStr + ")";
				 List<Map> liEmails=mapDao.executeSql(hIEEEsEmail);
				 logger.info("邮箱地址----------:" + liEmails);
				 Iterator<Map> eItor = liEmails.iterator();
				 while(eItor.hasNext()) {
					 Map emailMap = eItor.next();
					 String warnEmail = (String) emailMap.get("warnEmail");
					 if(StringUtils.isNotBlank(warnEmail)) {
						 String houseIEEE = (String) emailMap.get("houseIEEE");
						 messagehistory.setHouseIeee(houseIEEE);
						 messagehistory.setPhonenumber(warnEmail);
						 messagehistory.setSendtime(new Date());
						 messagehistory.setRemark("系统邮件");
						 int maide=sendemail("系统消息", messagehistory.getEmailcontent(), warnEmail);
						 if(maide > 0) {
							 succEmail++;
							 messagehistory.setState("1");
							 messagehistory.setType("3");
						 }
						 else {
							 messagehistory.setState("0");
							 messagehistory.setType("3");
							 failEmail++;
						 }
						 Messagehistory mh = new Messagehistory();
						 BeanUtils.copyProperties(messagehistory, mh);
						 messagebade.save(mh);
					 }
				 }
			 }
			logger.info("发送系统消息结束---------------");
			return "{\"noteSucc\":\""+succPhone+"\",\"noteFail\":\""+failPhone+"\",\"emailSucc\":\""+succEmail+"\",\"emailFail\":\""+failEmail+"\"}";
		} catch (Exception e) {
			logger.info("notebatchsend service", e);
			return "0";
			// TODO: handle exception
		}
	}
	
	public int sendnote(String messString,String noublen)
	{
		SendSMS sms=SendSMS.getSingleSendSMS();
		String back = sms.sendSMS(noublen, messString);
		return back.equals(messString)? 1:0;
	}
	
	public int sendemail(String messString,String noublen)throws Exception {
		logger.info("Z203 上线和掉线情况的每日报告汇总");
		String subject = "Z203 上线和掉线情况的每日报告";
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
		senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
		// 密码解密
		senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
		// 建立HTML邮件消息
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// true表示开始附件模式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
		// 设置收件人，寄件人
		messageHelper.setTo(noublen);
		messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
		messageHelper.setSubject(subject);
		messageHelper.setText(messString, true);
		senderImpl.send(mailMessage);
		return 1;
	}
	
	private int sendemail(String subject, String messString, String noublen) throws Exception {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
		senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
		// 密码解密
		senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
		// 建立HTML邮件消息
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// true表示开始附件模式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
		// 设置收件人，寄件人
		messageHelper.setTo(noublen);
		messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
		messageHelper.setSubject(subject);
		messageHelper.setText(messString, true);
		senderImpl.send(mailMessage);
		return 1;
	}
	
	@Override
	public int sendemail(String subject, String messString, List<String> emailToList) throws Exception {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
		senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
		// 密码解密
		senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
		// 建立HTML邮件消息
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// true表示开始附件模式
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
		// 设置收件人，寄件人
		messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
		messageHelper.setSubject(subject);
		messageHelper.setText(messString, true);
		for(String emailTo : emailToList) {
			messageHelper.setTo(emailTo);
			senderImpl.send(mailMessage);
		}
		return 1;
	}
	
	@Override
	public List<Map> findimport(
			List<Map<String, Object>> huosmapli) {
		// TODO Auto-generated method stub
		String huString="";
		List<String> lishuoe=new ArrayList<String>();
		for (Map<String, Object> map : huosmapli) {
			if(!(map.get("houseIEEE").toString()).equals("houseIEEE"))
			{
				lishuoe.add(map.get("houseIEEE").toString());
			}
			
		}
		//String sql="select houseIEEE,clientCode,name from house where houseIEEE in ("+huString+")";
		String sql="select houseIEEE,clientCode,name from house where  houseIEEE in (:houseIEEE)";
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("houseIEEE",lishuoe);
		List<Map> impor=mapDao.executeSql(sql,param);
		return impor;
	}
	
	@Override
	public void updateSMSEmailState() {
		logger.info("更新过期house的短信、邮件状态---------");
		String sql = "update notewarncontrol set state=0,StartDate=null,EndDate=null where EndDate < now()";
		mapDao.executeSql2(sql);
	}
	
	@Override
	public void loadProperties() {
		//重新载入配置文件
		logger.info("load properties-------------");
		PropertiesUtil.loadProperties();
	}
	
	@Override
	public int getRegisterCount(String cloudIp) throws Exception {
		String sql = "SELECT count(DISTINCT a.houseIEEE) serverCount from house a LEFT JOIN proxyserver b on a.houseIEEE = b.houseIEEE WHERE b.serverIp = :serverIp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serverIp", cloudIp);
		List<Map> countList = mapDao.executeSql(sql, params);
		return ((BigInteger)countList.get(0).get("serverCount")).intValue();
	}
	@Override
	public List<Map> getReliClient(Map<String, Object> pMap) {
		String sql="select a.id,a.client_code clientCode, a.region from reliclient a inner join reliroleclient b on a.id = b.client_id inner join reliuserrole c on b.role_id = c.role_id where c.user_id = :userId";
		return this.mapDao.executeSql(sql, pMap);
	}
	@Override
	public List<Map> getReliClientByregion(String code,String region) {
		List<Map> list=null;
		try {
			String sql="select * from reliclient where region='"+region+"'and client_code like '%"+code+"%'";
			 list=	this.mapDao.executeSql(sql);	
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return list;
	}
	
	@Override
	public List<Map> getReliClientByregion(Object region, Object userid) {
		// TODO Auto-generated method stub
		HashMap<String,Object> params=new HashMap<String, Object>();
		String sql="SELECT DISTINCT reliclient.client_code from reliuser inner JOIN reliuserrole on reliuserrole.user_id=reliuser.id inner JOIN relirole on relirole.id=reliuserrole.role_id LEFT JOIN  reliroleclient on reliroleclient.role_id=relirole.id inner JOIN reliclient on reliclient.id=reliroleclient.client_id where reliuser.id=:userid and reliclient.region=:region";					
		params.put("region",region);
		params.put("userid",userid);
		List<Map> list =	this.mapDao.executeSql(sql,params);	
		if(list.isEmpty()){
			return null;
		}
		return list;
	}
	
	@Override
	public List<Map> getReliClientByCode(String code) {
		// TODO Auto-generated method stub
		List<Map> list=null;
		HashMap<String,Object> params=new HashMap<String, Object>();
		try {
			String sql="select r.* from reliclient r where r.client_code = :clientCode";
			params.put("clientCode",code);
			 list=	this.mapDao.executeSql(sql,params);	
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return list;
	}
	@Override
	public void validServer() throws Exception {
		// TODO Auto-generated method stub
		ServerRegisterUtil.isServerValid();
		logger.info("----------isServerValid: " + ServerRegisterUtil.isRegisterValid);
	}
	@Override
	public List<Map> getServerInfo() {
		// TODO Auto-generated method stub
	    String sql="select * from serverlib";
	    List<Map> list=null;
	   list=this.mapDao.executeSql(sql);	
		return list;
	}
	
	@Override
	public List<Map> getReliClientByUserId(String clientCode, String userid){
		StringBuffer sql = new StringBuffer();
		Map<String,Object> params = new HashMap<String,Object>();
		sql.append("select rc.* from reliclient rc ");
		sql.append("inner join reliroleclient rrc on rc.id=rrc.client_id ");
		sql.append("inner join reliuserrole rur on rrc.role_id=rur.role_id where rur.user_id = :userid ");
		params.put("userid", userid);
		if(StringUtils.isNotBlank(clientCode)){
			sql.append(" and rc.client_code = :clientCode");
			params.put("clientCode", clientCode);
		}
		return mapDao.executeSql(sql.toString(), params);
	}
	
	@Override
	public boolean isServerAllTheSame(Map<String, Object> paramMap, String uncheckedHouses) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct p.serverIp FROM house t INNER JOIN reliclient r on r.id=t.client_id  INNER JOIN proxyserver p on t.houseIeee=p.houseIeee ");
		sql.append("INNER JOIN reliroleclient rlc ON r.id = rlc.client_id ");
		sql.append("INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id ");
		sql.append("WHERE t.regionCode != 'none' and rur.user_id =:userId ");
		if(StringUtils.isNotBlank(uncheckedHouses) && !uncheckedHouses.equals("-2")) {
			sql.append("AND t.houseIEEE NOT IN (").append(uncheckedHouses).append(")");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", paramMap.get("userid"));
		if (StringUtils.isNotBlank((String) paramMap.get("houseIeee1"))) {
			sql.append(" and t.houseIeee like :houseIeee ");
			params.put("houseIeee","%"+ paramMap.get("houseIeee1")+"%");
		}
		if (StringUtils.isNotBlank((String) paramMap.get("houseIeee"))) {
			sql.append(" and t.houseIeee like :houseIeee ");
			params.put("houseIeee","%"+ paramMap.get("houseIeee")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("name"))) {
			sql.append(" and t.name like :name ");
			params.put("name", "%"+paramMap.get("name")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("clientCode"))) {
			sql.append(" and r.client_code like :clientCode ");
			params.put("clientCode", "%"+paramMap.get("clientCode")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("enableFlag"))) {
			sql.append(" and t.enableFlag like :enableFlag ");
			params.put("enableFlag", "%"+paramMap.get("enableFlag")+"%");
		}
		if (StringUtils.isNotBlank((String)paramMap.get("serverIp"))) {
			sql.append(" and p.serverIp like :serverIp ");
			params.put("serverIp","%"+paramMap.get("serverIp")+"%");
		}
		List<Map> t = mapDao.executeSql(sql.toString(), params);
		if(t.size() == 1)
			return true;
		else
			return false;
	}
	
	@Override
	public List<Map> getGateWays(String houseIeee, String userid) throws Exception {
		Map<String, Object> rt = new HashMap<String, Object>();
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql =new StringBuilder("SELECT a.id,a.houseIEEE,a.staterecords,a.statechangetime,b.name,b.clientCode,b.client_id ")
		.append("FROM ").append(tableName).append(" a INNER JOIN house b on a.houseIEEE = b.houseIEEE ")
		.append("INNER JOIN reliclient r ON b.client_id = r.id INNER JOIN (SELECT DISTINCT rlc.client_id FROM reliroleclient rlc ")
		.append("INNER JOIN reliuserrole rur ON rlc.role_id = rur.role_id WHERE rur.user_id = :userid) c ON b.client_id = c.client_id ")
		.append("WHERE 1=1 ");
		params.put("userid", userid);
		if(StringUtils.isNotBlank(houseIeee)) {
			sql.append(" and a.houseIEEE =:houseIEEE ");
			params.put("houseIEEE", houseIeee);
		}
		sql.append(" order by a.statechangetime desc limit 1");
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		if(list != null) {
			return list;
		}
		return null;
	}
	@Override
	public List<Map> getMonitorState(String houseIeee) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		String tableName = "monitorlog_" + Calendar.getInstance().get(Calendar.YEAR);
		sql.append("SELECT DISTINCT staterecords FROM  ").append(tableName).append(" t where 1=1 ");
		if(StringUtils.isNotBlank(houseIeee)) {
			sql.append(" and t.houseIEEE =:houseIeee");
			param.put("houseIeee", houseIeee);
		}
		List<Map> list = mapDao.executeSql(sql.toString(), param);
		if(list != null) {
			return list;
		}
		return null;
	}
	@Override
	public Map<String, Object> getSLPlatformInfo(Map<String, Object> params) throws Exception
	  {
	    String sql = "select username as platformUser,password as platformPass,case when auth is NULL then '' else auth end as auth from slplatformuser where username=:houseIeee";
	    List list = this.mapDao.executeSql(sql, params);
	    Map rMap = new HashMap();
	    if ((list != null) && (!list.isEmpty())) {
	      rMap.putAll((Map)list.get(0));
	      String platformIP = PropertiesUtil.getProperty("shilian.platform.ip");
	      String platformPort = PropertiesUtil.getProperty("shilian.platform.port");
	      rMap.put("platFromIP", platformIP);
	      rMap.put("platformPort", platformPort);
	    }
	    return rMap;
	  }
	
	@Override
	public int saveSLPlatformUser(Map<String, Object> user) throws Exception{
	    String sql = "replace into slplatformuser(username,password,type,auth) values(:username,:password,:type,:auth)";
	    return this.mapDao.executeSql2(sql,user);
	}
	
	@Override
	public int deleteSLPlatformUser(Map<String,Object> user) throws Exception{
		if(user!=null){
			StringBuilder sql = new StringBuilder("delete from slplatformuser where 1=1");
			for(String key:user.keySet()){
				sql.append(" and ").append(key).append("=:").append(key);
			}
			return mapDao.executeSql2(sql.toString(), user);
		}else{
			return 0;
		}
	}
	
	/**
	 * 往拾联云平台创建账号
	 * @param house
	 * @return
	 * @throws Exception
	 */
	public int addSLAccount(House house) throws Exception{
		if(house!=null&&StringUtils.isNotBlank(house.getHouseIeee())){
			logger.info("添加拾联账户开始······");
			String houseIeee = house.getHouseIeee();
			String password = getPassword();
			//添加账户请求接口
			String uri = PropertiesUtil.getProperty("shilian.platform.uri.add");
			String url = "http://"+ip+":"+port+uri;
			//第三方应用标识
			String auth = PropertiesUtil.getProperty("platform.authorization");
			if(StringUtils.isBlank(ip)||StringUtils.isBlank(port)||StringUtils.isBlank(uri)||StringUtils.isBlank(auth)){
				throw new Exception("invalid url");
			}
			try{
				Map<String,Object> params = new HashMap<>();
				Map<String,String> header = new HashMap<>();
				
				params.put("account", houseIeee);
				params.put("password", password);
				params.put("type", Integer.valueOf(type));
				params.put("parent_account", username);
				
				header.put("Accept", "application/json");
				header.put("Content-Type", "application/json");
				header.put("Authorization", auth);
				
				String result = TestHttpclient.postByCostomHeader(url, params, header,12000);
				if(StringUtils.isNotBlank(result)){
					String secretKey = Httpproxy.getKey(houseIeee.substring(6)+"netvox");
					Map<String,Object> resultMap = JSON.parseObject(result);
					int status = (int) resultMap.get("ret");
					if(status==0){
						Object userAuth =  resultMap.get("auth");
						Map<String,Object> user = new HashMap<String,Object>();
						user.put("username", houseIeee);
						user.put("password", Httpproxy.urlEncrypt(password, secretKey));
						user.put("type", type);
						user.put("auth",auth);
						saveSLPlatformUser(user);
						return status;
					}else{
						String msg = (String) resultMap.get("msg");
						logger.error("addSLAccount:"+msg);
					}
				}else{
					logger.info("the request result is empty");
					return -1;
				}
			}catch(Exception e){
				logger.error("add slplatform user error",e);
			}
		}
		return -2;
	}
	
	/**
	 * 往拾联云平台删除账号
	 * @param house
	 * @return
	 * @throws Exception
	 */
	public int deleteSLAccount(House house) throws Exception{
		if(house!=null&&StringUtils.isNotBlank(house.getHouseIeee())){
			logger.info("删除拾联账户开始······");
			String houseIeee = house.getHouseIeee();
			//添加账户请求接口
			String uri = PropertiesUtil.getProperty("shilian.platform.uri.add");
			String url = "http://"+ip+":"+port+"/openapi/account/del";
			//第三方应用标识
			String auth = PropertiesUtil.getProperty("platform.authorization");
			if(StringUtils.isBlank(ip)||StringUtils.isBlank(port)||StringUtils.isBlank(uri)||StringUtils.isBlank(auth)){
				throw new Exception("invalid url");
			}
			try{
				Map<String,Object> params = new HashMap<>();
				Map<String,String> header = new HashMap<>();
				
				params.put("account", houseIeee);
				
				header.put("Accept", "application/json");
				header.put("Content-Type", "application/json");
				header.put("Authorization", auth);
				
				String result = TestHttpclient.postByCostomHeader(url, params, header,12000);
				if(StringUtils.isNotBlank(result)){
					String secretKey = Httpproxy.getKey(houseIeee.substring(6)+"netvox");
					Map<String,Object> resultMap = JSON.parseObject(result);
					int status = (int) resultMap.get("ret");
					if(status==0||status==-10){
						Map<String,Object> user = new HashMap<String,Object>();
						user.put("username", houseIeee);
						deleteSLPlatformUser(user);
						return 0;
					}else{
						String msg = (String) resultMap.get("msg");
						logger.error("delSLAccount:"+msg);
					}
				}else{
					logger.info("the request result is empty");
					return -1;
				}
			}catch(Exception e){
				logger.error("delete slplatform user error",e);
			}
		}
		return -2;
	}
	
	/**
	 * 获取八位随机字符串密码（由数字和小写字母组合而成）
	 * @return
	 * @throws Exception
	 */
	private String getPassword() throws Exception{
		int passLength = 8;
		String password = "";
		for(int i=0;i<passLength;i++){
			Random random = new Random();
			int num = random.nextInt(35);
			if(num>9){
				num += 87;
			}else{
				num += 48;
			}
			password += (char)num;
		}
		if(StringUtils.isBlank(password))
			throw new Exception("get password exception");
		return password;
	}
	
	/*@Override
	public Map<String,Object> getServerDomain(Map<String,Object> params){
		if(params==null)
			return null;
		StringBuilder sql = new StringBuilder("SELECT serverIp,serverPort FROM Proxyserver WHERE 1=1");
		Map<String,Object> pMap = new HashMap<>();
		for(String key:params.keySet()){
			if("houseIeee".equalsIgnoreCase(key)){
				sql.append(" and houseIEEE=:houseIeee");
				pMap.put("houseIeee", params.get(key));
			}else if("type".equalsIgnoreCase(key)){
				sql.append(" and type=:type");
				pMap.put("type", params.get(key));
			}
		}
		List<Map> result = mapDao.executeSql(sql.toString(), pMap);
		if(result==null||result.size()>1)
			return null;
		return result.get(0);
	}*/
	public String getServerDomain(String serverIp){
		String result = null;
		Map<String,Object> params = new HashMap<>();
		String sql = "SELECT serverdomain FROM serverlib WHERE serverip=:serverIp AND serverip<>serverdomain AND serverdomain is not null";
		params.put("serverIp",serverIp);
		List<Map> list = mapDao.executeSql(sql, params);
		if(list!=null&&list.size()==1){
			result = (String) list.get(0).get("serverdomain");
		}
		return result;
	}
	
	/*IES用户APP注册时获取Ip地址*/
	@Override
	public List<Map> FindServerUser() {
	    String sql="select * from serverlib";
	    List<Map> list=null;
	    list=this.mapDao.executeSql(sql);	
		return list;
	}
	

	/**
	 * 返回经纬度业务
	 * 创建时间 2017-6-6
	 * @author chengwangjian
	 * @return
	 */
	
	@Override
	public List<Map> getLong() {
		String sql = "select longitude,latitude from house";
		return this.mapDao.executeSql(sql);
	}
	
}