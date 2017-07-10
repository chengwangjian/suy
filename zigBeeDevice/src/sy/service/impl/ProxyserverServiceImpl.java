package sy.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.envers.tools.Tools;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.House;
import sy.model.Operatelog;
import sy.model.Proxyserver;
import sy.service.ProxyserverServiceI;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

@Service("proxyserverService")
public class ProxyserverServiceImpl implements ProxyserverServiceI {

	private static final Logger logger = Logger.getLogger(ProxyserverServiceImpl.class);
	private BaseDaoI<Map> mapDao;
	private BaseDaoI<House>haDaoI;
	public BaseDaoI<House> getHaDaoI() {
		return haDaoI;
	}
	@Autowired
	public void setHaDaoI(BaseDaoI<House> haDaoI) {
		this.haDaoI = haDaoI;
	}
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	private BaseDaoI<Proxyserver> proxyserverDao;

	public BaseDaoI<Proxyserver> getProxyserverDao() {
		return proxyserverDao;
	}

	@Autowired
	public void setProxyserverDao(BaseDaoI<Proxyserver> proxyserverDao) {
		this.proxyserverDao = proxyserverDao;
	}
	
	private BaseDaoI<Operatelog> operatelogDao;
	
	public BaseDaoI<Operatelog> getOperatelogDao() {
		return operatelogDao;
	}

	@Autowired
	public void setOperatelogDao(BaseDaoI<Operatelog> operatelogDao) {
		this.operatelogDao = operatelogDao;
	}

	@Override
	public Proxyserver save(Proxyserver proxyserver) {
		/*Proxyserver t = new Proxyserver();
		BeanUtils.copyProperties(proxyserver, t, new String[] { "pwd" });*/
		/*t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		t.setPwd(Encrypt.e(proxyserver.getPwd()));*/
		proxyserverDao.save(proxyserver);
//		BeanUtils.copyProperties(t, proxyserver);
		return proxyserver;
	}
	
	@Override
	public Proxyserver update(Proxyserver proxyserver) {
		proxyserverDao.update(proxyserver);
		return proxyserver;
	}
	
	@Override
	public Proxyserver get(Proxyserver proxyserver) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", proxyserver.getHouseIeee());
		params.put("type", proxyserver.getType());
		params.put("secondType", proxyserver.getSecondType());
		/*Proxyserver t = proxyserverDao.get("from Proxyserver t where t.houseIeee = :houseIeee " +
				" and t.type = :type and t.secondType = :secondType and enableFlag = '1' ", params);*/
		Proxyserver t = proxyserverDao.get("from Proxyserver t where t.houseIeee = :houseIeee " +
				" and t.type = :type and t.secondType = :secondType ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	/*查询Proxyserver表数据*/
	@Override
	public Proxyserver get1(Proxyserver proxyserver) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", proxyserver.getHouseIeee());
		params.put("type", proxyserver.getType());
		params.put("secondType", proxyserver.getSecondType());
		Proxyserver t = proxyserverDao.get("from Proxyserver t where t.houseIeee = :houseIeee " +
				" and t.type = :type and t.secondType = :secondType", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	
	@Override
	public Proxyserver find(Proxyserver proxyserver) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", proxyserver.getHouseIeee());
		Proxyserver t = proxyserverDao.get("from Proxyserver t where t.houseIeee = :houseIeee and enableFlag = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Proxyserver> findList(Proxyserver proxyserver) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", proxyserver.getHouseIeee());
		List<Proxyserver> t = proxyserverDao.find("from Proxyserver t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;*/
		
		StringBuffer hql = new StringBuffer();
		hql.append("from Proxyserver t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (proxyserver.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", proxyserver.getHouseIeee());
		}
		if (proxyserver.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + proxyserver.getName() + "%");
		}
		if (proxyserver.getServerIp() != null) {
			hql.append("and t.serverIp = :serverIp ");
			params.put("serverIp", proxyserver.getServerIp());
		}
		if (proxyserver.getServerPort() != null) {
			hql.append("and t.serverPort = :serverPort ");
			params.put("serverPort", proxyserver.getServerPort());
		}
		if (proxyserver.getType() != null) {
			hql.append("and t.type = :type ");
			params.put("type", proxyserver.getType());
		}
		if (proxyserver.getSecondType() != null) {
			hql.append("and t.secondType = :secondType ");
			params.put("secondType", proxyserver.getSecondType());
		}
		if (proxyserver.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", proxyserver.getEnableFlag());
		}
		if (proxyserver.getEnableFlag() == null) {
			hql.append("and t.enableFlag = '1' ");
		}
		/*if (proxyserver.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", proxyserver.getLongitude());
		}
		if (proxyserver.getLatitude() != null) {
			hql.append("and t.latitude = :latitude ");
			params.put("latitude", proxyserver.getLatitude());
		}*/
/*		if (proxyserver.getMinLongitude() != null) {
			hql.append("and t.longitude >= :minlongitude ");
			params.put("minlongitude", proxyserver.getMinLongitude());
		}
		if (proxyserver.getMaxLongitude() != null) {
			hql.append("and t.longitude <= :maxlongitude ");
			params.put("maxlongitude", proxyserver.getMaxLongitude());
		}
		if (proxyserver.getMinLatitude() != null) {
			hql.append("and t.latitude >= :minlatitude ");
			params.put("minlatitude", proxyserver.getMinLatitude());
		}			
		if (proxyserver.getMaxLatitude() != null) {
			hql.append("and t.latitude <= :maxlatitude ");
			params.put("maxlatitude", proxyserver.getMaxLatitude());
		}	
		if (proxyserver.getNetworkAddress() != null) {
			hql.append("and t.networkAddress = :networkAddress ");
			params.put("networkAddress", proxyserver.getNetworkAddress());
		}
		if (proxyserver.getPort() != null) {
			hql.append("and t.port = :port ");
			params.put("port", proxyserver.getPort());
		}
*/		List<Proxyserver> t = proxyserverDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Object[]> findListSql(Proxyserver proxyserver) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", proxyserver.getHouseIeee());
		List<Proxyserver> t = proxyserverDao.find("from Proxyserver t where t.houseIeee = :houseIeee", params);
		if (t != null) {
			return t;
		}
		return null;*/
		
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceWarnHistory_" + proxyserver.getHouseIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from Proxyserver s,Deviceattribute e where 1=1 and s.houseIeee = e.houseIEEE ");
		sql.append("select {s.*}, {e.*} from Proxyserver s,").append(tableName).append(" e where 1=1 and s.houseIeee = e.houseIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (proxyserver.getHouseIeee() != null) {
			sql.append("and s.houseIeee = :houseIeee ");
			params.put("houseIeee", proxyserver.getHouseIeee());
		}
		if (proxyserver.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + proxyserver.getName() + "%");
		}
		/*if (proxyserver.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", proxyserver.getLongitude());
		}
		if (proxyserver.getLatitude() != null) {
			hql.append("and s.latitude = :latitude ");
			params.put("latitude", proxyserver.getLatitude());
		}*/
	/*	if (proxyserver.getMinLongitude() != null) {
			sql.append("and s.longitude >= :minlongitude ");
			params.put("minlongitude", proxyserver.getMinLongitude());
		}
		if (proxyserver.getMaxLongitude() != null) {
			sql.append("and s.longitude <= :maxlongitude ");
			params.put("maxlongitude", proxyserver.getMaxLongitude());
		}
		if (proxyserver.getMinLatitude() != null) {
			sql.append("and s.latitude >= :minlatitude ");
			params.put("minlatitude", proxyserver.getMinLatitude());
		}		
		if (proxyserver.getMaxLatitude() != null) {
			sql.append("and s.latitude <= :maxlatitude ");
			params.put("maxlatitude", proxyserver.getMaxLatitude());
		}	
		if (proxyserver.getNetworkAddress() != null) {
			sql.append("and s.networkAddress = :networkAddress ");
			params.put("networkAddress", proxyserver.getNetworkAddress());
		}*/
//		List<Object[]> t = proxyserverDao.findSql(sql.toString(), params, Proxyserver.class, Deviceattribute.class);
		List<Object[]> t = proxyserverDao.findSql(sql.toString(), params, Proxyserver.class, DevicewarnhistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public int delete(Proxyserver proxyserver) {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseIeee", proxyserver.getHouseIeee());
		String hql = "delete Proxyserver t where t.houseIeee = :houseIeee";
		return proxyserverDao.executeHql(hql, params);*/
		
		StringBuffer hql = new StringBuffer();
		hql.append("delete Proxyserver t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (proxyserver.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", proxyserver.getHouseIeee());
			params2.put("houseIeee", proxyserver.getHouseIeee());
			// proxyserverDao.executeHql("delete from Deviceattribute where houseIeee = :houseIeee", params2);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String tableName = "deviceAttributeHistory_" + proxyserver.getHouseIeee() + "_" + year;
//			proxyserverDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
			// proxyserverDao.executeSql2("drop table IF EXISTS " + tableName);
			tableName = "deviceOperateHistory_" + proxyserver.getHouseIeee() + "_" + year;
			//proxyserverDao.executeSql2("drop table IF EXISTS " + tableName);
//			proxyserverDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
			tableName = "deviceWarnHistory_" + proxyserver.getHouseIeee() + "_" + year;
			//proxyserverDao.executeSql2("drop table IF EXISTS " + tableName);
//			proxyserverDao.executeSql2("delete from " + tableName + " where houseIeee = :houseIeee", params2);
		}
		if (proxyserver.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + proxyserver.getName() + "%");
		}
		if (proxyserver.getServerIp() != null) {
			hql.append("and t.serverIp = :serverIp ");
			params.put("serverIp", proxyserver.getServerIp());
		}
		if (proxyserver.getServerPort() != null) {
			hql.append("and t.serverPort = :serverPort ");
			params.put("serverPort", proxyserver.getServerPort());
		}
		if (proxyserver.getType() != null) {
			hql.append("and t.type = :type ");
			params.put("type", proxyserver.getType());
		}
		if (proxyserver.getSecondType() != null) {
			hql.append("and t.secondType = :secondType ");
			params.put("secondType", proxyserver.getSecondType());
		}
		if (proxyserver.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", proxyserver.getEnableFlag());
		}
		if (proxyserver.getEnableFlag() == null) {
			hql.append("and t.enableFlag = '1' ");
		}
		/*if (proxyserver.getLongitude() != null) {
			hql.append("and t.longitude = :longitude ");
			params.put("longitude", proxyserver.getLongitude());
		}
		if (proxyserver.getLatitude() != null) {
			hql.append("and t.latitude = :latitude ");
			params.put("latitude", proxyserver.getLatitude());
		}*/
		/*if (proxyserver.getMinLongitude() != null) {
			hql.append("and t.longitude >= :longitude ");
			params.put("longitude", proxyserver.getMinLongitude());
		}
		if (proxyserver.getMaxLongitude() != null) {
			hql.append("and t.longitude <= :longitude ");
			params.put("longitude", proxyserver.getMaxLongitude());
		}
		if (proxyserver.getMinLatitude() != null) {
			hql.append("and t.latitude >= :latitude ");
			params.put("latitude", proxyserver.getMinLatitude());
		}			
		if (proxyserver.getMaxLatitude() != null) {
			hql.append("and t.latitude <= :latitude ");
			params.put("latitude", proxyserver.getMaxLatitude());
		}	
		if (proxyserver.getNetworkAddress() != null) {
			hql.append("and t.networkAddress = :networkAddress ");
			params.put("networkAddress", proxyserver.getNetworkAddress());
		}
		if (proxyserver.getPort() != null) {
			hql.append("and t.port = :port ");
			params.put("port", proxyserver.getPort());
		}*/
		return proxyserverDao.executeHql(hql.toString(), params);
	}

	/*删除proxyserver表的数据*/
	@Override
	public int delete1(Proxyserver proxyserver) {

		StringBuffer hql = new StringBuffer();
		hql.append("delete Proxyserver t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (proxyserver.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", proxyserver.getHouseIeee());
		}
		return proxyserverDao.executeHql(hql.toString(), params);
	}
	
	@Override
	public Proxyserver login(Proxyserver proxyserver) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(proxyserver.getPwd()));
		params.put("name", proxyserver.getName());
		Proxyserver t = proxyserverDao.get("from Proxyserver t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return proxyserver;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(Proxyserver proxyserver) {
		DataGrid dg = new DataGrid();
		String hql = "from Proxyserver t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(proxyserver, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(proxyserver, hql);
		List<Proxyserver> l = proxyserverDao.find(hql, params, proxyserver.getPage(), proxyserver.getRows());
		List<Proxyserver> nl = new ArrayList<Proxyserver>();
		changeModel(l, nl);
		dg.setTotal(proxyserverDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Proxyserver> l, List<Proxyserver> nl) {
		if (l != null && l.size() > 0) {
			for (Proxyserver t : l) {
				Proxyserver u = new Proxyserver();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Proxyserver proxyserver, String hql) {
		/*if (proxyserver.getSort() != null) {
			hql += " order by " + proxyserver.getSort() + " " + proxyserver.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Proxyserver proxyserver, String hql, Map<String, Object> params) {
		if (proxyserver.getName() != null && !proxyserver.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + proxyserver.getName().trim() + "%%");
		}
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Proxyserver t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		proxyserverDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
	
	/**
	 * quartz定时执行任务 监控服务器是否正常
	 * @author: zhuangxd
	 * 时间：2014-10-17 下午2:31:34
	 */
    public void monitorServerOk() {  
//		http://192.168.1.72:8081/zigBeeDevice/proxyserverController/find.do?json={"houseIEEE":"00137A0000007795"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000007795
		logger.info("监控服务器是否正常定时任务进行中。。。" + (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
		String[] serverAddressArr = PropertiesUtil.getPropertyLoad("monitorServer.address").split(";");
		String[] monitorServerMailArr = PropertiesUtil.getPropertyLoad("monitorServerMail.to").split(";");
		for (String serverAddress : serverAddressArr) {
			String[] server = serverAddress.split(",");
			String serverIp = server[0];
	    	String serverPort = server[1];
	    	StringBuilder downUrl = new StringBuilder("http://");
	    	downUrl.append(serverIp).append(":").append(serverPort).append("/zigBeeDevice/proxyserverController/find.do");
	    	Map<String, Object> paramMap2 = new HashMap<String, Object>();
			paramMap2.put("json", "{\"houseIEEE\":\"00137A0000010136\"}");
			paramMap2.put("callback", "android");
			paramMap2.put("sign", "AAA");
			paramMap2.put("encodemethod", "NONE");
			paramMap2.put("houseIeeeSecret", "00137A0000010136");
			try {
				String rsStr = TestHttpclient.postUrlWithParams(downUrl.toString(), paramMap2, "utf-8");
			} catch (Exception e) {
				logger.info("http exception",e);
				for (String monitorServerMail : monitorServerMailArr) {
					JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
					String subject = "云端服务器" + serverIp + "访问不了";
					// 设定mail server
					senderImpl.setHost(PropertiesUtil.getProperty("mail.host"));
					senderImpl.setUsername(PropertiesUtil.getProperty("mail.username"));
					// 密码解密
					senderImpl.setPassword(Httpproxy.urlCodec2(PropertiesUtil.getProperty("mail.password"), "0000007785NETVOX"));
					// 建立HTML邮件消息
					MimeMessage mailMessage = senderImpl.createMimeMessage();
					// true表示开始附件模式
					try {
						MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
						// 设置收件人，寄件人
						messageHelper.setTo(monitorServerMail);
						messageHelper.setFrom(PropertiesUtil.getProperty("mail.from"));
						messageHelper.setSubject(subject);
						messageHelper.setText(subject + ",访问时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), true);
						logger.info(subject + ",访问时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + ",收件人是" + monitorServerMail);
						Operatelog operatelog = new Operatelog();
						operatelog.setTitle(subject);
						operatelog.setType("MonitorServer"); // 监控服务器是否正常
						operatelog.setContent(subject + ",访问时间是:"+(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
						operatelog.setRemark("监控服务器是否正常，并发送邮件");
						operatelogDao.save(operatelog);
						senderImpl.send(mailMessage);
					} catch (Exception e2) {
						logger.info("mail exception",e);
					}
				}
			}
		}
    }

	@Override
	public int findBurnin(House house) {
		Map<String, Object> param = new HashMap<String , Object>();
		param.put("houseIEEE", house.getHouseIeee());
		String sql = "select 1 from brinhouse where houseIEEE = :houseIEEE";
		List<Map> list = mapDao.executeSql(sql, param);
		if(list!=null&&list.size()>0){
			return 1;
		}else {
			return -1;
		}
	}

	@Override
	public int updateHouseisonline(House house) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "FROM House WHERE houseIeee=:houseIeee";
		params.put("houseIeee", house.getHouseIeee());
		House h = haDaoI.get(hql,params);
		params.clear();
		String sql = "update house "+" set isonline = 1 where id = :id";
		params.put("id", h.getId());
		int i = mapDao.executeSql2(sql,params);
		return i;
	}

	@Override
	public int updatehousenode(Map<String,Object> params) {
		try {
			//现根据houseIEEE获取house对象
			Map<String,Object> param = new HashMap<>();
			param.put("houseIEEE", params.get("houseIEEE"));
			House house = haDaoI.get("FROM House WHERE houseIEEE=:houseIEEE", param);
			if(house!=null){
				params.remove("houseIEEE");
				params.put("id", house.getId());
				int i=haDaoI.executeSql2("update house set nodeNum=:nodeNum where id=:id",params);
				return i;
			}else
				return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 服务器磁盘告警
	 */
	@Override
	public float spaceWarn(){
		String phoneNo = PropertiesUtil.getProperty("phoneNo");
		
		String classPath = Tools.class.getClassLoader().getResource("").getPath();
		System.out.println(classPath);
		String rootPath  = "";
		//windows下
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		//linux下
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		try{
			File file = new File(URLDecoder.decode(rootPath, "utf-8"));
			//获取项目所在磁盘的空间使用状况
			long freeSpace = file.getFreeSpace();
			long totleSpace = file.getTotalSpace();
			long usable = totleSpace - freeSpace;
			
			float usage = usable/(float)totleSpace;
			//当已使用内存空间超过总空间的85%时，发送预警短信
			return usage;
		}catch(IOException e){
			logger.info("Get File Exception", e);
			return -1;
		}
	}
	/**
	 * 服务器内存告警
	 * @return 已使用物理内存占总内存的比例
	 */
	@Override
	public float serverMemoryWarn(){
		long totalMemory = 0l;//总物理内存
		long usedMemory = 0l;//已使用的物理内存
		//需要在项目lib文件夹下添加sigar.jar以及与以下相对应的依赖文件（以实际操作系统而定）
		//sigar-x86-winnt.dll 		32位windows操作系统
		//libsigar-x86-linux.so 	32位linux操作系统
		//libsigar-amd64-linux.so 	64位linux操作系统
		//sigar-amd64-winnt.dll		64位windows操作系统
		Sigar sigar = new Sigar();
		try {
			Mem mem = sigar.getMem();
			totalMemory = mem.getTotal();
			usedMemory = mem.getUsed();
		} catch (SigarException e) {
			logger.info("Access System Memory Exception ", e);
		}
		return usedMemory/(float)totalMemory;
	}
}
