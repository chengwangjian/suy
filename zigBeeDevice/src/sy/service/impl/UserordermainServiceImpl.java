package sy.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Devicepicsetlib;
import sy.model.DevicewarnhistoryHouseidYear;
import sy.model.ModeSchcUser;
import sy.model.Modedevice;
import sy.model.Modeeplib;
import sy.model.Modehouse;
import sy.model.Modenode;
import sy.model.Modenodelib;
import sy.model.Moderoom;
import sy.model.Modeuser;
import sy.model.Userorderep;
import sy.model.Userordermain;
import sy.model.Userordernode;
import sy.model.Userorderroom;
import sy.pageModel.ModeNodeAndDevice;
import sy.pageModel.ModeNodeAndDevice2;
import sy.service.ModehouseServiceI;
import sy.service.UserordermainServiceI;
import sy.util.CharStream;
import sy.util.GUIDUtil;
import sy.util.GZIPUtil;
import sy.util.PropertiesUtil;

import com.flywind.app.entities.Modedevicebind;
import com.flywind.app.services.XmlUtil;


@Service("userordermainService")
public class UserordermainServiceImpl implements UserordermainServiceI {

	private static final Logger logger = Logger.getLogger(UserordermainServiceImpl.class);

	private BaseDaoI<Userordermain> userordermainDao;
	private BaseDaoI<Modedevicebind> modedevicebindDao;
	private BaseDaoI<Devicepicsetlib> devicepicsetlibDao;
	private BaseDaoI<Map> mapDao;
	private BaseDaoI<Userorderroom> userorderroomDao;
	private BaseDaoI<Userordernode> userordernodeDao;
	private BaseDaoI<Userorderep> userorderepDao;
	private BaseDaoI<Modenodelib> modenodelibDao;
	private BaseDaoI<Modeeplib> modeeplibDao;
	private BaseDaoI<Modenode> modenodeDao;
	private BaseDaoI<Modehouse> modehouseDao;
	private BaseDaoI<Moderoom> moderoomDao;
	private BaseDaoI<Modeuser> modeuserDao;
	private BaseDaoI<Modedevice> modedeviceDao;	
	private XmlUtil xmlutil; 
	private ModehouseServiceI modehouseService;
	private BaseDaoI<ModeSchcUser> modesuDao;
	
	public BaseDaoI<Devicepicsetlib> getDevicepicsetlibDao() {
		return devicepicsetlibDao;
	}

	@Autowired
	public void setDevicepicsetlibDao(BaseDaoI<Devicepicsetlib> devicepicsetlibDao) {
		this.devicepicsetlibDao = devicepicsetlibDao;
	}

	public ModehouseServiceI getModehouseService() {
		return modehouseService;
	}

	@Autowired
	public void setModehouseService(ModehouseServiceI modehouseService) {
		this.modehouseService = modehouseService;
	}

	public BaseDaoI<Modedevicebind> getModedevicebindDao() {
		return modedevicebindDao;
	}

	@Autowired
	public void setModedevicebindDao(BaseDaoI<Modedevicebind> modedevicebindDao) {
		this.modedevicebindDao = modedevicebindDao;
	}
	
	public XmlUtil getXmlutil() {
		return xmlutil;
	}
	
	@Autowired
	public void setXmlutil(XmlUtil xmlutil) {
		this.xmlutil = xmlutil;
	}

	public BaseDaoI<Modedevice> getModedeviceDao() {
		return modedeviceDao;
	}

	@Autowired
	public void setModedeviceDao(BaseDaoI<Modedevice> modedeviceDao) {
		this.modedeviceDao = modedeviceDao;
	}
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<Userorderep> getUserorderepDao() {
		return userorderepDao;
	}

	@Autowired
	public void setUserorderepDao(BaseDaoI<Userorderep> userorderepDao) {
		this.userorderepDao = userorderepDao;
	}

	public BaseDaoI<Userordernode> getUserordernodeDao() {
		return userordernodeDao;
	}

	@Autowired
	public void setUserordernodeDao(BaseDaoI<Userordernode> userordernodeDao) {
		this.userordernodeDao = userordernodeDao;
	}

	public BaseDaoI<Userorderroom> getUserorderroomDao() {
		return userorderroomDao;
	}

	@Autowired
	public void setUserorderroomDao(BaseDaoI<Userorderroom> userorderroomDao) {
		this.userorderroomDao = userorderroomDao;
	}

	public BaseDaoI<Modeuser> getModeuserDao() {
		return modeuserDao;
	}

	@Autowired
	public void setModeuserDao(BaseDaoI<Modeuser> modeuserDao) {
		this.modeuserDao = modeuserDao;
	}

	public BaseDaoI<Modehouse> getModehouseDao() {
		return modehouseDao;
	}

	@Autowired
	public void setModehouseDao(BaseDaoI<Modehouse> modehouseDao) {
		this.modehouseDao = modehouseDao;
	}
	
	public BaseDaoI<Moderoom> getModeroomDao() {
		return moderoomDao;
	}

	@Autowired
	public void setModeroomDao(BaseDaoI<Moderoom> moderoomDao) {
		this.moderoomDao = moderoomDao;
	}

	public BaseDaoI<Userordermain> getUserordermainDao() {
		return userordermainDao;
	}

	@Autowired
	public void setUserordermainDao(BaseDaoI<Userordermain> userordermainDao) {
		this.userordermainDao = userordermainDao;
	}

	public BaseDaoI<Modenodelib> getModenodelibDao() {
		return modenodelibDao;
	}

	@Autowired
	public void setModenodelibDao(BaseDaoI<Modenodelib> modenodelibDao) {
		this.modenodelibDao = modenodelibDao;
	}

	public BaseDaoI<Modeeplib> getModeeplibDao() {
		return modeeplibDao;
	}

	@Autowired
	public void setModeeplibDao(BaseDaoI<Modeeplib> modeeplibDao) {
		this.modeeplibDao = modeeplibDao;
	}

	public BaseDaoI<Modenode> getModenodeDao() {
		return modenodeDao;
	}

	@Autowired
	public void setModenodeDao(BaseDaoI<Modenode> modenodeDao) {
		this.modenodeDao = modenodeDao;
	}

	public BaseDaoI<ModeSchcUser> getModesuDao() {
		return modesuDao;
	}
	
	@Autowired
	public void setModesuDao(BaseDaoI<ModeSchcUser> modesuDao) {
		this.modesuDao = modesuDao;
	}

	@Override
	public Userordermain save(Userordermain userordermain) {
		userordermainDao.save(userordermain);
		return userordermain;
	}
	
	@Override
	public Map findModeNodeLibDataByDeviceID(Modeeplib modeeplib) {

		String hql = "";
		String deviceId = modeeplib.getDeviceId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", deviceId);
		Map<String, Object> rt = new HashMap<String, Object>();
		hql = "select t from Modenodelib t,Modeeplib e where t.id = e.nodeId  and e.deviceId = :deviceId";
		List<Modenodelib> list = modenodelibDao.find(hql,params);
		if(list != null && list.size() > 0)
			rt.put("modenodelib",list);
		else
			rt.put("modenodelib",null);
		
		return rt;
	}
	
	@Override
	public Map findModeNodeLibDataByNodeType(Modenodelib modenodelib) {

		String hql = "";
		String nodeType = modenodelib.getNodeType();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeType", nodeType);
		Map<String, Object> rt = new HashMap<String, Object>();
		hql = "from Modenodelib t where t.nodeType = :nodeType";
		List<Modenodelib> list = modenodelibDao.find(hql,params);
		if(list != null && list.size() > 0)
			rt.put("modenodelib",list);
		else
			rt.put("modenodelib",null);
		
		return rt;
	}
	
	@Override
	public Map findModeNodeLibDataByNodeLibId(Modenodelib modenodelib) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenodelib.getId());
		Map<String, Object> rt = new HashMap<String, Object>();
		hql = "from Modenodelib t where t.id = :id";
		List<Modenodelib> list = modenodelibDao.find(hql,params);
		if(list != null && list.size() > 0)
			rt.put("modenodelib",list.get(0));
		else
			rt.put("modenodelib",null);
		
		return rt;
	}
	
	@Override
	public Map findModeEPLibDataByNode(Modeeplib modeeplib) {

		String hql = "";
		long nodeId = modeeplib.getNodeId();
		int num = modeeplib.getNum();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("nodeId", nodeId);
		hql = "from Modenodelib t where t.id = :nodeId ";
		List<Modenodelib> list = modenodelibDao.find(hql,params);
		Modenodelib modenodelib = list.get(0);
		
		hql = "from Modeeplib t where t.nodeId = :nodeId ";
		List<Modeeplib> list2 = modeeplibDao.find(hql,params);
		
		int i = 1;
		List<Map> list3 = new ArrayList<Map>();
		while (i <= num) {
			Map<String, Object> rt2 = new HashMap<String, Object>();
			Modenodelib modenodelib2 = new Modenodelib();
			if(list != null && list.size() > 0) {
				BeanUtils.copyProperties(modenodelib,modenodelib2);
				modenodelib2.setDeviceName(modenodelib.getDeviceName() + i);
				rt2.put("modenodelib",modenodelib2);
			} else
				rt2.put("modenodelib",null);
			
			List<Modeeplib> list4 = new ArrayList<Modeeplib>();
			if(list2 != null && list2.size() > 0) {
				int j = 1;
				for(Modeeplib ob1:list2){
					Modeeplib ob2 = new Modeeplib();
					BeanUtils.copyProperties(ob1,ob2);
					ob2.setDeviceName(modenodelib.getDeviceName() + i + "-" + j);
					list4.add(ob2);
					j++;
				}
				rt2.put("modeeplib",list4);
			} else
				rt2.put("modeeplib",null);
			list3.add(rt2);
			i++;
		}
		
		rt.put("modeNodeAndEpLib",list3);		
		return rt;
	}
	/**
	 * 生成modeEpPage文件
	 */
	public String modeEpPage(Userordermain userordermain,Modehouse modehouse) {
		//DocumentHelper提供了创建Document对象的方法   
		Document document = DocumentHelper.createDocument();  
		// 设置编码
		document.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement = document.addElement("ModeEPPage");  
		Element smartPageElement = rootElement.addElement("SmartPage");
		Element familyPageElement = rootElement.addElement("FamilyPage");
		Element sepElement = smartPageElement.addElement("EP");;
		Element smodeElement = smartPageElement.addElement("Mode");
		Element fepElement = familyPageElement.addElement("EP");
		Element fmodeElement = familyPageElement.addElement("Mode");
		String sql = "select * from smarteppage where houseid ="+userordermain.getHouseId();
		List<Map> sep = mapDao.executeSql(sql);
		for (int i = 0; i < sep.size(); i++) {
			Element sepidElement = sepElement.addElement("ID");
			if(sep.get(i).get("IEEE")==null){
				sepidElement.addAttribute("IEEE", "");
			}else{
				sepidElement.addAttribute("IEEE", ""+sep.get(i).get("IEEE"));
			}
			sepidElement.addAttribute("EP", ""+sep.get(i).get("ep"));
			sepidElement.addAttribute("IRTYPE", ""+sep.get(i).get("IRType"));
			sepidElement.addAttribute("ORDER", ""+sep.get(i).get("OrderType"));
//			sepidElement.addAttribute("houseID", ""+sep.get(i).get("houseid"));
			if(sep.get(i).get("oldId")!=null&&Integer.parseInt(sep.get(i).get("oldId").toString())==0){
				sepidElement.addAttribute("ep_id", ""+sep.get(i).get("ep_id"));
//				sepidElement.addAttribute("OldID", ""+0);
			}else {
				sepidElement.addAttribute("ep_id", ""+sep.get(i).get("oldId"));
//				sepidElement.addAttribute("OldID", ""+sep.get(i).get("oldId"));
			}
		}
		String sql1 = "select * from smartmodepage where houseId ="+userordermain.getHouseId();
		List<Map> smode = mapDao.executeSql(sql1);
		for (int i = 0; i < smode.size(); i++) {
			Element smodeidElement = smodeElement.addElement("ID");
//			smodeidElement.addAttribute("ModeID", ""+smode.get(i).get("ModeID"));
//			smodeidElement.addAttribute("houseID", ""+smode.get(i).get("houseId"));
			if(smode.get(i).get("oldId")!=null&&Integer.parseInt(smode.get(i).get("oldId").toString())==0){
//				smodeidElement.addAttribute("OldID", ""+0);
				smodeidElement.addAttribute("ModeID", ""+smode.get(i).get("ModeID"));
			}else {
//				smodeidElement.addAttribute("OldID", ""+smode.get(i).get("oldId"));
				smodeidElement.addAttribute("ModeID", ""+smode.get(i).get("oldId"));
			}
		}
		String sql2 = "select * from familyeppage where houseid ="+userordermain.getHouseId();
		List<Map> fep = mapDao.executeSql(sql2);
		for (int i = 0; i < fep.size(); i++) {
			Element fepidElement = fepElement.addElement("ID");
			fepidElement.addAttribute("ep_id", ""+fep.get(i).get("ep_id"));
			if(fep.get(i).get("IEEE")==null){
				fepidElement.addAttribute("IEEE", "");
			}
			else {
				fepidElement.addAttribute("IEEE", ""+fep.get(i).get("IEEE"));
			}
			fepidElement.addAttribute("EP", ""+fep.get(i).get("ep"));
			fepidElement.addAttribute("IRTYPE", ""+fep.get(i).get("IRType"));
			fepidElement.addAttribute("ORDER", ""+fep.get(i).get("OrderType"));
//			fepidElement.addAttribute("houseID", ""+fep.get(i).get("houseid"));
			if(fep.get(i).get("oldId")!=null&&Integer.parseInt(fep.get(i).get("oldId").toString())==0){
//				fepidElement.addAttribute("OldID", ""+fep.get(i).get("id"));
				fepidElement.addAttribute("ep_id", ""+fep.get(i).get("ep_id"));
			}else {
//				fepidElement.addAttribute("OldID", ""+fep.get(i).get("oldId"));
				fepidElement.addAttribute("ep_id", ""+fep.get(i).get("oldId"));
			}
		}
		String sql3 = "select * from familymodepage where houseId ="+userordermain.getHouseId();
		List<Map> fmode = mapDao.executeSql(sql3);
		for (int i = 0; i < fmode.size(); i++) {
			Element fmodeidElement = fmodeElement.addElement("ID");
			fmodeidElement.addAttribute("ModeID", ""+fmode.get(i).get("ModeID"));
//			fmodeidElement.addAttribute("houseID", ""+fmode.get(i).get("houseId"));
			if(fmode.get(i).get("oldId")!=null&&Integer.parseInt(fmode.get(i).get("oldId").toString())==0){
//				fmodeidElement.addAttribute("OldID", ""+fmode.get(i).get("id"));
				fmodeidElement.addAttribute("ModeID", ""+fmode.get(i).get("ModeID"));
			}else {
//				fmodeidElement.addAttribute("OldID", ""+fmode.get(i).get("oldId"));
				fmodeidElement.addAttribute("ModeID", ""+fmode.get(i).get("oldId"));
			}
			
		}
		
		String filename2 = null;
		FileOutputStream fileWriter;
		try {
			filename2 = PropertiesUtil.getProperty("modexmlPath") + "modeEpPage-userid_" + modehouse.getUserId() + "_orderid_" + userordermain.getId() + "_" +(new Date().getTime()) + "1";
			fileWriter = new FileOutputStream(filename2+".xml");
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of = OutputFormat.createPrettyPrint();  
			// 设置编码
			of.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter2 = new XMLWriter(fileWriter,of);  
			xmlWriter2.write(document);  
			xmlWriter2.flush();  
			xmlWriter2.close();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return filename2;
	}
	
	
	
	/**
	 * 根据某一个HouseID的设备列表模板和用户输入的信息生成设备采购订单，
	 * 并且要根据XML的格式生成一个文件保存到主表的XMlFile字段
		XML文件的格式参考以前设计的Mode执行规则（203上面的Mode.XML文件）
	 * @author: zhuangxd
	 * 时间：2013-12-6 下午3:30:13
	 * @param userordermain
	 * @return
	 */
	@Override
	public int createOrder(Userordermain userordermain) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Modehouse modehouse = new Modehouse();
		params.put("houseId", userordermain.getHouseId());
		hql = "from Modehouse t where t.id = :houseId ";
		/*modenodeDao.executeSql2("update Modenode t,Modenodelib p set t.deviceUniqueId = concat(p.modelId,concat(t.id,'')) where t.modeNodeLibId" +
				"= p.id and t.houseId = :houseId", params);
		modedeviceDao.executeSql2("update Modedevice t,Modenode p set t.deviceUniqueId = concat(p.deviceUniqueId,t.ep) where t.modeNodeId" +
				"= p.id and t.houseId = :houseId", params);*/
		List<Modehouse> list = modehouseDao.find(hql,params);
		if(list != null && list.size() > 0)
			modehouse = list.get(0);
		else  modehouse = new Modehouse();
		
		params = new HashMap<String, Object>();
		params.put("houseId", userordermain.getHouseId());
		Modenode t2 = modenodeDao.get("from Modenode t where t.houseId = :houseId and (t.modeNodeLibId = 86 or t.modeNodeLibId = 145) ", params);
		//DocumentHelper提供了创建Document对象的方法   
		Document document = DocumentHelper.createDocument();  
		// 设置编码
		document.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement = document.addElement("DeviceInfo");  
		
		//DocumentHelper提供了创建Document对象的方法   Node文件
		Document document2 = DocumentHelper.createDocument();  
		// 设置编码
		document2.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement2 = document2.addElement("DeviceInfo"); 
		Element modehouseElement2 = rootElement2.addElement("House"); 
		Element modehouseMainElement2 = modehouseElement2.addElement("Main");  
		Element houseidElement2 = modehouseMainElement2.addElement("ID");
		
		//这里可以继续添加子节点，也可以指定内容   
//		rootElement.setText("这个是module标签的文本信息");  
		Element modehouseElement = rootElement.addElement("House"); 
		Element modehouseMainElement = modehouseElement.addElement("Main");  
		Element houseidElement = modehouseMainElement.addElement("ID");
		String guid = "";
		if (StringUtils.isBlank(modehouse.getHosueGuid())){
			guid = GUIDUtil.getGUID();
		} else {
			guid = modehouse.getHosueGuid();
		}
		houseidElement.addAttribute("HouseID", guid);
		houseidElement2.addAttribute("HouseID", guid);
		houseidElement2.addAttribute("Name", modehouse.getHouseName());//为节点添加属性值   
		String secretKey = "";
		if (t2 == null) {
			houseidElement2.addAttribute("IEEE", "");
			secretKey = "";
		} else if (t2.getIeee() == null || t2.getIeee() == "") {
			houseidElement2.addAttribute("IEEE", "");
			secretKey = "";
		} else {
			houseidElement2.addAttribute("IEEE", t2.getIeee());
//			houseidElement.addAttribute("IEEE", "");
			if (t2.getIeee().length() == 16) {
				secretKey = t2.getIeee().substring(6) + "NETVOX";
			} else {
				secretKey = "";
			}
		}
		houseidElement2.addAttribute("Description", modehouse.getDescription());
		houseidElement2.addAttribute("Longitude", "");
		houseidElement2.addAttribute("Latitude", "");
		houseidElement2.addAttribute("NetworkAddress", "");
		houseidElement2.addAttribute("Version", "");
		houseidElement2.addAttribute("Encodemethod", PropertiesUtil.getProperty("Encodemethod"));
		houseidElement2.addAttribute("SecretKey", secretKey);
//		if (StringUtils.isBlank(modehouse.getCloudIp())){
//			houseidElement2.addAttribute("CloudIP", PropertiesUtil.getProperty("cloudOrder.host"));
//		} else {
//			houseidElement2.addAttribute("CloudIP", modehouse.getCloudIp());
//		}
		houseidElement2.addAttribute("CloudIP", PropertiesUtil.getProperty("cloudOrder.host"));
		houseidElement2.addAttribute("CloudPort", PropertiesUtil.getProperty("cloudOrder.port"));
		// 模式编辑器版本号
		/* 软件版本号由四部分组成，第一个1为主版本号，第二个1为子版本号，第三个1为阶段版本号，
		 * 第四部分为日期版本号加希腊字母版本号，希腊字母版本号共有3种，分别为： alpha、beta、release*/
//		houseidElement2.addAttribute("XmlVersion", "1.0.1.4_140703_release");
		houseidElement2.addAttribute("XmlVersion", "1.0.1.5_150123_release");
		hql = "from Moderoom t where t.houseId = :houseId order by t.roomId asc";
		List<Moderoom> list2 = moderoomDao.find(hql,params);
		if(list2 != null && list2.size() > 0){	
			
		}else list2 = new ArrayList<Moderoom>();
		hql = "from Modedevicebind t where t.houseId = :houseId";
		List<Modedevicebind> list5 = modedevicebindDao.find(hql,params);
		if(list5 != null && list5.size() > 0){	
			
		}else list5 = new ArrayList<Modedevicebind>();
		hql = "from Devicepicsetlib where houseId = :houseId";
		List<Devicepicsetlib> list6 = devicepicsetlibDao.find(hql,params);
		if(list6 != null && list6.size() > 0){	
			
		}else {
			hql = "from Devicepicsetlib where houseId = -1";
			list6 = devicepicsetlibDao.find(hql);
		}
		params = new HashMap<String, Object>();
		params.put("userId", modehouse.getUserId());
		Modeuser t = modeuserDao.get("from Modeuser t where t.id = :userId " +
				" and t.enabled = '1' ", params);
		userordermain.setUserId(t.getId());
//		userordermain.setUserName(t.getUserName());
		userordermain.setHouseName(modehouse.getHouseName());
		userordermain.setOrderName(modehouse.getHouseName() + "的订单数为：" + userordermain.getCount());
		userordermainDao.save(userordermain);
		Userorderroom ob2 = null;
		Userordernode userordernode = null;
		Userorderep userorderep = null;	
		
		Element modeRoomElement = rootElement.addElement("Room"); 
		Element modeRoomMainElement = modeRoomElement.addElement("Main");  
		Element modeRoomidElement = null;
		
		/*Element modeNodeElement = rootElement.addElement("Node"); 
		Element modeNodeMainElement = modeNodeElement.addElement("Main");  
		Element modeNodeidElement = null;*/
		
		Element modeNodeElement2 = rootElement2.addElement("Node"); 
		Element modeNodeMainElement2 = modeNodeElement2.addElement("Main");  
		Element modeNodeidElement2 = null;
		
		Element modeDeviceElement = rootElement.addElement("Device"); 
		Element modeDeviceMainElement = modeDeviceElement.addElement("Main");  
		Element modeDeviceidElement = null;
		
		Element modedevicebindElement = rootElement.addElement("DeviceBind"); 
		Element modedevicebindElementMainElement = modedevicebindElement.addElement("Main");  
		Element modedevicebindElementidElement = null;
		
		Element devicePicSetElement = rootElement.addElement("DevicePicSet"); 
		Element devicePicSetElementMainElement = devicePicSetElement.addElement("Main");  
		Element devicePicSetElementidElement = null;
		
		long oldId = 0;
		
		if(list2 != null && list2.size() > 0) {
			for(Moderoom ob1:list2){
				ob2 = new Userorderroom();
				ob2.setRoomId(ob1.getRoomId());
				ob2.setOrderId(userordermain.getId());
				ob2.setRoomName(ob1.getRoomName());
				ob2.setRoomPic(ob1.getRoomPic());
				userorderroomDao.save(ob2);			
				
				modeRoomidElement = modeRoomMainElement.addElement("ID");  
				modeRoomidElement.addAttribute("RoomID", String.valueOf(ob1.getRoomId()));
				modeRoomidElement.addAttribute("RoomName", ob1.getRoomName());
//				modeRoomidElement.addAttribute("RoomPic", ob1.getRoomPic());
				modeRoomidElement.addAttribute("RoomPic", "");
				modeRoomidElement.addAttribute("temperature", "0.0");
				modeRoomidElement.addAttribute("temp_clause", "0");
				modeRoomidElement.addAttribute("humidity", "0.0");
				modeRoomidElement.addAttribute("hum_clause", "0");
				modeRoomidElement.addAttribute("active_warn", "1");		
				modeRoomidElement.addAttribute("tel_no", "");		
				
				params = new HashMap<String, Object>();
				params.put("houseId", userordermain.getHouseId());
				params.put("roomId", ob1.getRoomId());
//				hql = "from Modenode t  where t.houseId = :houseId and t.roomId = :roomId";
//				List<Modenode> list3 = modenodeDao.find(hql,params);
				hql="select distinct new map(a.id as id,a.roomId as roomId,a.modeNodeLibId as modeNodeLibId,b.modelId as modelId,a.ieee as ieee,a.deviceName as deviceName,a.description as description,a.oldId as oldId)"
						 +" from Modenode a, Modedevice b where a.id=b.modeNodeId and a.houseId = :houseId and a.roomId = :roomId group by a.id";
				logger.info(hql);
				List<Map> list3 = mapDao.find(hql,params);
				if(list3 != null && list3.size() > 0) {
					for(Map ob3:list3){
						userordernode = new Userordernode();
						userordernode.setOrderId(userordermain.getId());
						userordernode.setRoomId(ob1.getRoomId());
						userordernode.setModeNodeLibId((Long)ob3.get("modeNodeLibId"));//ob3.getModeNodeLibId());
						userordernode.setDeviceUniqueId((String)ob3.get("modelId"));//ob3.getDeviceUniqueId());
						userordernode.setIeee((String)ob3.get("ieee"));//ob3.getIeee());
						userordernode.setDeviceName((String)ob3.get("deviceName"));//ob3.getDeviceName());
						userordernode.setDescription((String)ob3.get("description"));//ob3.getDescription());
						userordernodeDao.save(userordernode);
						
						/*modeNodeidElement = modeNodeMainElement.addElement("ID");  
						modeNodeidElement.addAttribute("MID", String.valueOf(ob3.getId()));
						if (ob3.getIeee() == null) {
							modeNodeidElement.addAttribute("IEEE", "");
						} else {
							modeNodeidElement.addAttribute("IEEE", ob3.getIeee());
						}
						modeNodeidElement.addAttribute("RoomID", String.valueOf(ob3.getRoomId()));
						modeNodeidElement.addAttribute("ModeNodeLibId", String.valueOf(ob3.getModeNodeLibId()));
						modeNodeidElement.addAttribute("Devicename", String.valueOf(ob3.getId()));
						modeNodeidElement.addAttribute("DeviceUniqueId", ob3.getDeviceUniqueId());
						modeNodeidElement.addAttribute("Description", ob3.getDeviceName());
						modeNodeidElement.addAttribute("Type", "0");*/
						
						modeNodeidElement2 = modeNodeMainElement2.addElement("ID");
						if (ob3.get("oldId").equals(0L)){//(ob3.getOldId() == 0) {
							modeNodeidElement2.addAttribute("MID", String.valueOf(ob3.get("id")));//String.valueOf(ob3.getId()));
						} else {
							oldId = (Long) ob3.get("oldId");
							modeNodeidElement2.addAttribute("MID", String.valueOf(ob3.get("oldId")));//String.valueOf(ob3.getOldId()));
						}
//						modeNodeidElement2.addAttribute("MID", String.valueOf(ob3.getId()));
						if (ob3.get("ieee")==null){//(ob3.getIeee() == null) {
							modeNodeidElement2.addAttribute("IEEE", "");
						} else {
							modeNodeidElement2.addAttribute("IEEE", ob3.get("ieee")==null?"":(String)ob3.get("ieee"));//ob3.getIeee());
						}
						modeNodeidElement2.addAttribute("RoomID", String.valueOf(ob3.get("roomId")));//String.valueOf(ob3.getRoomId()));
						modeNodeidElement2.addAttribute("ModeNodeLibId", String.valueOf(ob3.get("modeNodeLibId")));//String.valueOf(ob3.getModeNodeLibId()));
//						modeNodeidElement2.addAttribute("Devicename", String.valueOf(ob3.getId()));
						modeNodeidElement2.addAttribute("DeviceUniqueId",ob3.get("modelId")==null?"":(String)ob3.get("modelId")); //ob3.getDeviceUniqueId());
						modeNodeidElement2.addAttribute("Description", ob3.get("deviceName")==null?"":(String)ob3.get("deviceName"));//ob3.getDeviceName());
						modeNodeidElement2.addAttribute("Type", "0");
						
						hql = "from Modedevice t where t.modeNodeId = :modeNodeId";
						params = new HashMap<String, Object>();
						params.put("modeNodeId", ob3.get("id"));//ob3.getId());
						List<Modedevice> list4 = modedeviceDao.find(hql,params);
						if(list4 != null && list4.size() > 0) {
							for(Modedevice ob4:list4){
								userorderep = new Userorderep();
								BeanUtils.copyProperties(ob4, userorderep);
								userorderep.setOrderId(userordermain.getId());
								userorderep.setUserOrderNodeId(userordernode.getId());
								userorderepDao.save(userorderep);
								
								modeDeviceidElement = modeDeviceMainElement.addElement("ID"); 
								if (ob4.getOldId() == 0) {
									modeDeviceidElement.addAttribute("SubID", String.valueOf(ob4.getId()));
								} else {
									modeDeviceidElement.addAttribute("SubID", String.valueOf(ob4.getOldId()));
								}
//								modeDeviceidElement.addAttribute("MID", String.valueOf(ob4.getModeNodeId()));
								if (ob4.getOldModeNodeId() == 0) {
									modeDeviceidElement.addAttribute("MID", String.valueOf(ob4.getModeNodeId()));
								} else {
									modeDeviceidElement.addAttribute("MID", String.valueOf(ob4.getOldModeNodeId()));
								}
//								modeDeviceidElement.addAttribute("DeviceName", String.valueOf(ob4.getId()));
								if (ob4.getDeviceUniqueId() == null) {
									modeDeviceidElement.addAttribute("DeviceUniqueId", "");
								} else {
									modeDeviceidElement.addAttribute("DeviceUniqueId", ob4.getDeviceUniqueId());
								}
								/*if (ob4.getIeee() == null) {
									modeDeviceidElement.addAttribute("IEEE", "");
								} else {
									modeDeviceidElement.addAttribute("IEEE", ob4.getIeee());
								}*/
								modeDeviceidElement.addAttribute("ModelID", ob4.getModelId());
								modeDeviceidElement.addAttribute("DeviceId", ob4.getDeviceId());
								modeDeviceidElement.addAttribute("EP", ob4.getEp());
								modeDeviceidElement.addAttribute("RoomID", String.valueOf(ob4.getRoomId()));
								modeDeviceidElement.addAttribute("type", "0");
								modeDeviceidElement.addAttribute("Description", ob4.getDeviceName());
								modeDeviceidElement.addAttribute("Picname", StringUtils.isBlank(ob4.getPicName())? "":ob4.getPicName());
							}
						}
					}
				}
				
			}
		}
		
		// 修改Z203导入模式编辑器后，新增的设备oldId赋值成id的值,新增的动作，oldId也赋值。导出模式编辑器时，保持新旧id不变。
		if (oldId > 0) {
			updateCreateOrder(userordermain.getHouseId());
		}
		
		if(list5 != null && list5.size() > 0) {
			for(Modedevicebind ob1:list5){
				modedevicebindElementidElement = modedevicebindElementMainElement.addElement("ID");  
				modedevicebindElementidElement.addAttribute("BindType", String.valueOf(ob1.getBindType()));
				/*if (ob1.getSourceIeee() == null) {
					modedevicebindElementidElement.addAttribute("SourceIEEE", "");
				} else {
					modedevicebindElementidElement.addAttribute("SourceIEEE", ob1.getSourceIeee());
				}*/
				/*if (ob1.gets() == null) {
					modeDeviceidElement.addAttribute("IEEE", "");
				} else {
					modeDeviceidElement.addAttribute("IEEE", ob4.getIeee());
				}*/
//				modedevicebindElementidElement.addAttribute("SourceId", "");
				modedevicebindElementidElement.addAttribute("SourceEP", ob1.getSourceVirtualEp());
//				modedevicebindElementidElement.addAttribute("Source", String.valueOf(ob1.getSourceId()));
				if (ob1.getSourceOldId() == 0) {
					modedevicebindElementidElement.addAttribute("Source", String.valueOf(ob1.getSourceId()));
				} else {
					modedevicebindElementidElement.addAttribute("Source", String.valueOf(ob1.getSourceOldId()));
				}
//				modedevicebindElementidElement.addAttribute("SourceDevicename", String.valueOf(ob1.getSourceId()));
				modedevicebindElementidElement.addAttribute("SourceDeviceUniqueId", ob1.getSourceDeviceUniqueId());
				modedevicebindElementidElement.addAttribute("SourceDescription", ob1.getSourceDevicename());
				modedevicebindElementidElement.addAttribute("ClusterID", ob1.getClusterId());
				modedevicebindElementidElement.addAttribute("ClusterName", ob1.getClusterName());
				modedevicebindElementidElement.addAttribute("DestType", ob1.getDestType());
				/*if (ob1.getDestIeee() == null) {
					modedevicebindElementidElement.addAttribute("DestIEEE", "");
				} else {
					modedevicebindElementidElement.addAttribute("DestIEEE", ob1.getDestIeee());
				}*/
//				modedevicebindElementidElement.addAttribute("DestId", "");
				modedevicebindElementidElement.addAttribute("DestEP", ob1.getDestEp());
//				modedevicebindElementidElement.addAttribute("Dest", String.valueOf(ob1.getDestId()));
				if (ob1.getDestOldId() == 0) {
					modedevicebindElementidElement.addAttribute("Dest", String.valueOf(ob1.getDestId()));
				} else {
					modedevicebindElementidElement.addAttribute("Dest", String.valueOf(ob1.getDestOldId()));
				}
//				modedevicebindElementidElement.addAttribute("Destname", String.valueOf(ob1.getDestId()));
				modedevicebindElementidElement.addAttribute("DestDeviceUniqueId", ob1.getDestDeviceUniqueId());
				modedevicebindElementidElement.addAttribute("DestDescription", ob1.getDestName());
				modedevicebindElementidElement.addAttribute("HasBind", String.valueOf(ob1.getHasBind()));
			}
		} 
		
		if(list6 != null && list6.size() > 0) {
			for(Devicepicsetlib ob1:list6){
				devicePicSetElementidElement = devicePicSetElementMainElement.addElement("ID");  
				devicePicSetElementidElement.addAttribute("ID", String.valueOf(ob1.getMid()));
				devicePicSetElementidElement.addAttribute("DeviceID", ob1.getDeviceId());
				if (ob1.getPicName() == null) {
					devicePicSetElementidElement.addAttribute("PicName", "");
				} else {
					devicePicSetElementidElement.addAttribute("PicName", ob1.getPicName());
				}
			}
		} 
		
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),0).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),2).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),4).toxml());		
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),6).toxml());	
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),8).toxml());	
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),10).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),12).toxml());			
		/*String str = dxmlutil.loadEntity(userordermain.getHouseId());
		try {
			Document document2 = DocumentHelper.parseText(str);
			rootElement.add(document2.getRootElement());
		} catch (Exception e) {
		}*/
		
		try {  
			// logger.info(document.asXML()); //将document文档对象直接转换成字符串输出
//			logger.info(System.getProperty("user.dir"));
			
			/*File file=new File("");
			String abspath=file.getAbsolutePath();
			System.out.println(abspath);
			
			String path =   getClass().getProtectionDomain().getCodeSource().getLocation().getPath();   
			System.out.println("path==" + path);
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());

			System.out.println(System.getProperty("user.dir"));
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));     

			  System.out.println(Test3.class.getClassLoader().getResource(""));        

			System.out.println(ClassLoader.getSystemResource(""));        
			  System.out.println(Test3.class.getResource(""));        
			  System.out.println(Test3.class.getResource("/")); //Class文件所在路径  
			  System.out.println(new File("/").getAbsolutePath());    */
			
//			String filename = "d:\\modexml\\mode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName() + "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
			String filename = PropertiesUtil.getProperty("modexmlPath") + "mode-userid_" + modehouse.getUserId() + "_orderid_" + userordermain.getId() + "_" +(new Date().getTime());
			String filename2 = PropertiesUtil.getProperty("modexmlPath") + "modeNode-userid_" + modehouse.getUserId() + "_orderid_" + userordermain.getId() + "_" +(new Date().getTime());
			/*String filename4 = "d:\\modexml\\Mode";
			String filename2 = "d:\\modexml\\Node";*/
//			System.out.println("filename--------"+filename);
//			Writer fileWriter = new FileWriter(filename);  // 中文乱码,FileOutputStream解决乱码
			FileOutputStream fileWriter = new FileOutputStream(filename);  
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of = OutputFormat.createPrettyPrint();  
			// 设置编码
			of.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter = new XMLWriter(fileWriter,of);  
			xmlWriter.write(document);  
			xmlWriter.flush();  
			xmlWriter.close();  
			
			FileOutputStream fileWriter2 = new FileOutputStream(filename2 + ".xml");  
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of2 = OutputFormat.createPrettyPrint();  
			// 设置编码
			of2.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter2 = new XMLWriter(fileWriter2,of2);  
			xmlWriter2.write(document2);  
			xmlWriter2.flush();  
			xmlWriter2.close();  
			
			//生成modeEpPage xml文件
			String filename_ = modeEpPage(userordermain, modehouse);
			String filename3 = PropertiesUtil.getProperty("modexmlPath") + "modeEpPage-userid_" + modehouse.getUserId() + "_orderid_" + userordermain.getId() + "_" +(new Date().getTime());
			
			CharStream.reader(filename);
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
			CharStream.writer(filename+".xml");
			
			CharStream.reader(filename_+".xml");
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
			CharStream.writer(filename3+".xml");
			
			/*CharStream.reader(filename2);
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
			CharStream.writer(filename2+".xml");*/
			logger.info(filename + "文档添加成功！"); 
			File[] sources = new File[] { new File(filename + ".xml"),
					new File(filename2 + ".xml"),
					new File(filename3 + ".xml")
			};
			String fname = PropertiesUtil.getProperty("modexmlPath") + "mode-userid_" + modehouse.getUserId() + "_orderid_" + userordermain.getId() + "_" +(new Date().getTime());
			File target = new File(fname +".ndd");
			// 打包成tar文件
			GZIPUtil.pack(sources, target);
			// 解包tar文件
//			List<String> list8 = GZIPUtil.readByZipInputStream(filename3 +".ndd", filename3 +"ndd");
			params = new HashMap<String, Object>();
			params.put("houseId", userordermain.getHouseId());
//			params.put("xmlFile", filename + ".xml" + ";" + filename2 + ".xml");
//			params.put("xmlFile", filename3 +".ndd");
			params.put("xmlFile", filename + ".xml" + ";" + filename2 + ".xml"+";" + filename3 + ".xml"+";"+fname +".ndd");
			userordermainDao.executeHql("update Userordermain t set xmlFile = :xmlFile where " +
					" t.houseId = :houseId", params);
		} catch (IOException e) {  
			logger.info("createOrder", e);  
		}  
		
		return 1;
	}
	
	/**
	 * 旧版本格式（没有houseid，house表没在node文件）
	 * 根据某一个HouseID的设备列表模板和用户输入的信息生成设备采购订单，
	 * 并且要根据XML的格式生成一个文件保存到主表的XMlFile字段
		XML文件的格式参考以前设计的Mode执行规则（203上面的Mode.XML文件）
	 * @author: zhuangxd
	 * 时间：2013-12-6 下午3:30:13
	 * @param userordermain
	 * @return
	 */
	/*@Override
	public int createOrder20140808(Userordermain userordermain) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Modehouse modehouse = new Modehouse();
		params.put("houseId", userordermain.getHouseId());
		hql = "from Modehouse t where t.id = :houseId ";
		modenodeDao.executeSql2("update Modenode t,Modenodelib p set t.deviceUniqueId = concat(p.modelId,concat(t.id,'')) where t.modeNodeLibId" +
				"= p.id and t.houseId = :houseId", params);
		modedeviceDao.executeSql2("update Modedevice t,Modenode p set t.deviceUniqueId = concat(p.deviceUniqueId,t.ep) where t.modeNodeId" +
				"= p.id and t.houseId = :houseId", params);
		List<Modehouse> list = modehouseDao.find(hql,params);
		if(list != null && list.size() > 0)
			modehouse = list.get(0);
		else  modehouse = new Modehouse();
		
		params = new HashMap<String, Object>();
		params.put("houseId", userordermain.getHouseId());
		Modenode t2 = modenodeDao.get("from Modenode t where t.houseId = :houseId and t.modeNodeLibId = 86 ", params);
		//DocumentHelper提供了创建Document对象的方法   
		Document document = DocumentHelper.createDocument();  
		// 设置编码
		document.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement = document.addElement("DeviceInfo");  
		
		//DocumentHelper提供了创建Document对象的方法   
		Document document2 = DocumentHelper.createDocument();  
		// 设置编码
		document2.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement2 = document2.addElement("DeviceInfo"); 
		Element modehouseElement2 = rootElement2.addElement("House"); 
		Element modehouseMainElement2 = modehouseElement2.addElement("Main");  
		Element houseidElement2 = modehouseMainElement2.addElement("ID");
		
		//这里可以继续添加子节点，也可以指定内容   
//		rootElement.setText("这个是module标签的文本信息");  
		Element modehouseElement = rootElement.addElement("House"); 
		Element modehouseMainElement = modehouseElement.addElement("Main");  
		Element houseidElement = modehouseMainElement.addElement("ID");
		String guid = "";
		if (StringUtils.isBlank(modehouse.getHosueGuid())){
			guid = GUIDUtil.getGUID();
		} else {
			guid = modehouse.getHosueGuid();
		}
//		houseidElement.addAttribute("HouseID", guid);
//		houseidElement2.addAttribute("HouseID", guid);
		houseidElement.addAttribute("Name", modehouse.getHouseName());//为节点添加属性值   
		String secretKey = "";
		if (t2 == null) {
			houseidElement.addAttribute("IEEE", "");
			secretKey = "";
		} else if (t2.getIeee() == null || t2.getIeee() == "") {
			houseidElement.addAttribute("IEEE", "");
			secretKey = "";
		} else {
			houseidElement.addAttribute("IEEE", t2.getIeee());
//			houseidElement.addAttribute("IEEE", "");
			if (t2.getIeee().length() == 16) {
				secretKey = t2.getIeee().substring(6) + "NETVOX";
			} else {
				secretKey = "";
			}
		}
		houseidElement.addAttribute("Description", modehouse.getDescription());
		houseidElement.addAttribute("Longitude", "");
		houseidElement.addAttribute("Latitude", "");
		houseidElement.addAttribute("NetworkAddress", "");
		houseidElement.addAttribute("Version", "");
		houseidElement.addAttribute("Encodemethod", PropertiesUtil.getProperty("Encodemethod"));
		houseidElement.addAttribute("SecretKey", secretKey);
		houseidElement.addAttribute("CloudIP", PropertiesUtil.getProperty("cloudOrder.host"));
		houseidElement.addAttribute("CloudPort", PropertiesUtil.getProperty("cloudOrder.port"));
		// 模式编辑器版本号
		 软件版本号由四部分组成，第一个1为主版本号，第二个1为子版本号，第三个1为阶段版本号，
		 * 第四部分为日期版本号加希腊字母版本号，希腊字母版本号共有3种，分别为： alpha、beta、release
		houseidElement.addAttribute("XmlVersion", "1.0.1.4_140703_release");
		hql = "from Moderoom t where t.houseId = :houseId order by t.roomId asc";
		List<Moderoom> list2 = moderoomDao.find(hql,params);
		if(list2 != null && list2.size() > 0){	
			
		}else list2 = new ArrayList<Moderoom>();
		hql = "from Modedevicebind t where t.houseId = :houseId";
		List<Modedevicebind> list5 = modedevicebindDao.find(hql,params);
		if(list5 != null && list5.size() > 0){	
			
		}else list5 = new ArrayList<Modedevicebind>();
		hql = "from Devicepicsetlib where houseId = :houseId";
		List<Devicepicsetlib> list6 = devicepicsetlibDao.find(hql,params);
		if(list6 != null && list6.size() > 0){	
			
		}else {
			hql = "from Devicepicsetlib where houseId = -1";
			list6 = devicepicsetlibDao.find(hql);
		}
		params = new HashMap<String, Object>();
		params.put("userId", modehouse.getUserId());
		Modeuser t = modeuserDao.get("from Modeuser t where t.id = :userId " +
				" and t.enabled = '1' ", params);
		userordermain.setUserId(t.getId());
//		userordermain.setUserName(t.getUserName());
		userordermain.setHouseName(modehouse.getHouseName());
		userordermain.setOrderName(modehouse.getHouseName() + "的订单数为：" + userordermain.getCount());
		userordermainDao.save(userordermain);
		Userorderroom ob2 = null;
		Userordernode userordernode = null;
		Userorderep userorderep = null;	
		
		Element modeRoomElement = rootElement.addElement("Room"); 
		Element modeRoomMainElement = modeRoomElement.addElement("Main");  
		Element modeRoomidElement = null;
		
		Element modeNodeElement = rootElement.addElement("Node"); 
		Element modeNodeMainElement = modeNodeElement.addElement("Main");  
		Element modeNodeidElement = null;
		
		Element modeNodeElement2 = rootElement2.addElement("Node"); 
		Element modeNodeMainElement2 = modeNodeElement2.addElement("Main");  
		Element modeNodeidElement2 = null;
		
		Element modeDeviceElement = rootElement.addElement("Device"); 
		Element modeDeviceMainElement = modeDeviceElement.addElement("Main");  
		Element modeDeviceidElement = null;
		
		Element modedevicebindElement = rootElement.addElement("DeviceBind"); 
		Element modedevicebindElementMainElement = modedevicebindElement.addElement("Main");  
		Element modedevicebindElementidElement = null;
		
		Element devicePicSetElement = rootElement.addElement("DevicePicSet"); 
		Element devicePicSetElementMainElement = devicePicSetElement.addElement("Main");  
		Element devicePicSetElementidElement = null;
		
		long oldId = 0;
		
		if(list2 != null && list2.size() > 0) {
			for(Moderoom ob1:list2){
				ob2 = new Userorderroom();
				ob2.setRoomId(ob1.getRoomId());
				ob2.setOrderId(userordermain.getId());
				ob2.setRoomName(ob1.getRoomName());
				ob2.setRoomPic(ob1.getRoomPic());
				userorderroomDao.save(ob2);			
				
				modeRoomidElement = modeRoomMainElement.addElement("ID");  
				modeRoomidElement.addAttribute("RoomID", String.valueOf(ob1.getRoomId()));
				modeRoomidElement.addAttribute("RoomName", ob1.getRoomName());
//				modeRoomidElement.addAttribute("RoomPic", ob1.getRoomPic());
				modeRoomidElement.addAttribute("RoomPic", "");
				
				params = new HashMap<String, Object>();
				params.put("houseId", userordermain.getHouseId());
				params.put("roomId", ob1.getRoomId());
				hql = "from Modenode t where t.houseId = :houseId and t.roomId = :roomId";
				List<Modenode> list3 = modenodeDao.find(hql,params);
				if(list3 != null && list3.size() > 0) {
					for(Modenode ob3:list3){
						userordernode = new Userordernode();
						userordernode.setOrderId(userordermain.getId());
						userordernode.setRoomId(ob1.getRoomId());
						userordernode.setModeNodeLibId(ob3.getModeNodeLibId());
						userordernode.setDeviceUniqueId(ob3.getDeviceUniqueId());
						userordernode.setIeee(ob3.getIeee());
						userordernode.setDeviceName(ob3.getDeviceName());
						userordernode.setDescription(ob3.getDescription());
						userordernodeDao.save(userordernode);
						
						modeNodeidElement = modeNodeMainElement.addElement("ID");  
						modeNodeidElement.addAttribute("MID", String.valueOf(ob3.getId()));
						if (ob3.getIeee() == null) {
							modeNodeidElement.addAttribute("IEEE", "");
						} else {
							modeNodeidElement.addAttribute("IEEE", ob3.getIeee());
						}
						modeNodeidElement.addAttribute("RoomID", String.valueOf(ob3.getRoomId()));
						modeNodeidElement.addAttribute("ModeNodeLibId", String.valueOf(ob3.getModeNodeLibId()));
						modeNodeidElement.addAttribute("Devicename", String.valueOf(ob3.getId()));
						modeNodeidElement.addAttribute("DeviceUniqueId", ob3.getDeviceUniqueId());
						modeNodeidElement.addAttribute("Description", ob3.getDeviceName());
						modeNodeidElement.addAttribute("Type", "0");
						
						modeNodeidElement2 = modeNodeMainElement2.addElement("ID");
						if (ob3.getOldId() == 0) {
							modeNodeidElement2.addAttribute("MID", String.valueOf(ob3.getId()));
						} else {
							oldId = ob3.getOldId();
							modeNodeidElement2.addAttribute("MID", String.valueOf(ob3.getOldId()));
						}
//						modeNodeidElement2.addAttribute("MID", String.valueOf(ob3.getId()));
						if (ob3.getIeee() == null) {
							modeNodeidElement2.addAttribute("IEEE", "");
						} else {
							modeNodeidElement2.addAttribute("IEEE", ob3.getIeee());
						}
						modeNodeidElement2.addAttribute("RoomID", String.valueOf(ob3.getRoomId()));
						modeNodeidElement2.addAttribute("ModeNodeLibId", String.valueOf(ob3.getModeNodeLibId()));
//						modeNodeidElement2.addAttribute("Devicename", String.valueOf(ob3.getId()));
						modeNodeidElement2.addAttribute("DeviceUniqueId", ob3.getDeviceUniqueId());
						modeNodeidElement2.addAttribute("Description", ob3.getDeviceName());
						modeNodeidElement2.addAttribute("Type", "0");
						
						hql = "from Modedevice t where t.modeNodeId = :modeNodeId";
						params = new HashMap<String, Object>();
						params.put("modeNodeId", ob3.getId());
						List<Modedevice> list4 = modedeviceDao.find(hql,params);
						if(list4 != null && list4.size() > 0) {
							for(Modedevice ob4:list4){
								userorderep = new Userorderep();
								BeanUtils.copyProperties(ob4, userorderep);
								userorderep.setOrderId(userordermain.getId());
								userorderep.setUserOrderNodeId(userordernode.getId());
								userorderepDao.save(userorderep);
								
								modeDeviceidElement = modeDeviceMainElement.addElement("ID"); 
								if (ob4.getOldId() == 0) {
									modeDeviceidElement.addAttribute("SubID", String.valueOf(ob4.getId()));
								} else {
									modeDeviceidElement.addAttribute("SubID", String.valueOf(ob4.getOldId()));
								}
//								modeDeviceidElement.addAttribute("MID", String.valueOf(ob4.getModeNodeId()));
								if (ob4.getOldModeNodeId() == 0) {
									modeDeviceidElement.addAttribute("MID", String.valueOf(ob4.getModeNodeId()));
								} else {
									modeDeviceidElement.addAttribute("MID", String.valueOf(ob4.getOldModeNodeId()));
								}
//								modeDeviceidElement.addAttribute("DeviceName", String.valueOf(ob4.getId()));
								if (ob4.getDeviceUniqueId() == null) {
									modeDeviceidElement.addAttribute("DeviceUniqueId", "");
								} else {
									modeDeviceidElement.addAttribute("DeviceUniqueId", ob4.getDeviceUniqueId());
								}
								if (ob4.getIeee() == null) {
									modeDeviceidElement.addAttribute("IEEE", "");
								} else {
									modeDeviceidElement.addAttribute("IEEE", ob4.getIeee());
								}
								modeDeviceidElement.addAttribute("ModelID", ob4.getModelId());
								modeDeviceidElement.addAttribute("DeviceId", ob4.getDeviceId());
								modeDeviceidElement.addAttribute("EP", ob4.getEp());
								modeDeviceidElement.addAttribute("RoomID", String.valueOf(ob4.getRoomId()));
								modeDeviceidElement.addAttribute("type", "0");
								modeDeviceidElement.addAttribute("Description", ob4.getDeviceName());
								modeDeviceidElement.addAttribute("Picname", "");
							}
						}
					}
				}
				
			}
		}
		
		// 修改Z203导入模式编辑器后，新增动作后，oldId没更新关联
		if (oldId > 0) {
			updateCreateOrder(userordermain.getHouseId());
		}
		
		if(list5 != null && list5.size() > 0) {
			for(Modedevicebind ob1:list5){
				modedevicebindElementidElement = modedevicebindElementMainElement.addElement("ID");  
				modedevicebindElementidElement.addAttribute("BindType", String.valueOf(ob1.getBindType()));
				if (ob1.getSourceIeee() == null) {
					modedevicebindElementidElement.addAttribute("SourceIEEE", "");
				} else {
					modedevicebindElementidElement.addAttribute("SourceIEEE", ob1.getSourceIeee());
				}
				if (ob1.gets() == null) {
					modeDeviceidElement.addAttribute("IEEE", "");
				} else {
					modeDeviceidElement.addAttribute("IEEE", ob4.getIeee());
				}
//				modedevicebindElementidElement.addAttribute("SourceId", "");
				modedevicebindElementidElement.addAttribute("SourceEP", ob1.getSourceVirtualEp());
//				modedevicebindElementidElement.addAttribute("Source", String.valueOf(ob1.getSourceId()));
				if (ob1.getSourceOldId() == 0) {
					modedevicebindElementidElement.addAttribute("Source", String.valueOf(ob1.getSourceId()));
				} else {
					modedevicebindElementidElement.addAttribute("Source", String.valueOf(ob1.getSourceOldId()));
				}
//				modedevicebindElementidElement.addAttribute("SourceDevicename", String.valueOf(ob1.getSourceId()));
				modedevicebindElementidElement.addAttribute("SourceDeviceUniqueId", ob1.getSourceDeviceUniqueId());
				modedevicebindElementidElement.addAttribute("SourceDescription", ob1.getSourceDevicename());
				modedevicebindElementidElement.addAttribute("ClusterID", ob1.getClusterId());
				modedevicebindElementidElement.addAttribute("ClusterName", ob1.getClusterName());
				modedevicebindElementidElement.addAttribute("DestType", ob1.getDestType());
				if (ob1.getDestIeee() == null) {
					modedevicebindElementidElement.addAttribute("DestIEEE", "");
				} else {
					modedevicebindElementidElement.addAttribute("DestIEEE", ob1.getDestIeee());
				}
//				modedevicebindElementidElement.addAttribute("DestId", "");
				modedevicebindElementidElement.addAttribute("DestEP", ob1.getDestEp());
//				modedevicebindElementidElement.addAttribute("Dest", String.valueOf(ob1.getDestId()));
				if (ob1.getDestOldId() == 0) {
					modedevicebindElementidElement.addAttribute("Dest", String.valueOf(ob1.getDestId()));
				} else {
					modedevicebindElementidElement.addAttribute("Dest", String.valueOf(ob1.getDestOldId()));
				}
//				modedevicebindElementidElement.addAttribute("Destname", String.valueOf(ob1.getDestId()));
				modedevicebindElementidElement.addAttribute("DestDeviceUniqueId", ob1.getDestDeviceUniqueId());
				modedevicebindElementidElement.addAttribute("DestDescription", ob1.getDestName());
				modedevicebindElementidElement.addAttribute("HasBind", String.valueOf(ob1.getHasBind()));
			}
		} 
		
		if(list6 != null && list6.size() > 0) {
			for(Devicepicsetlib ob1:list6){
				devicePicSetElementidElement = devicePicSetElementMainElement.addElement("ID");  
				devicePicSetElementidElement.addAttribute("ID", String.valueOf(ob1.getMid()));
				devicePicSetElementidElement.addAttribute("DeviceID", ob1.getDeviceId());
				if (ob1.getPicName() == null) {
					devicePicSetElementidElement.addAttribute("PicName", "");
				} else {
					devicePicSetElementidElement.addAttribute("PicName", ob1.getPicName());
				}
			}
		} 
		
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),0).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),2).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),4).toxml());		
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),6).toxml());	
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),8).toxml());	
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),10).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),12).toxml());			
		String str = dxmlutil.loadEntity(userordermain.getHouseId());
		try {
			Document document2 = DocumentHelper.parseText(str);
			rootElement.add(document2.getRootElement());
		} catch (Exception e) {
		}
		
		try {  
			// logger.info(document.asXML()); //将document文档对象直接转换成字符串输出
//			logger.info(System.getProperty("user.dir"));
			
			File file=new File("");
			String abspath=file.getAbsolutePath();
			System.out.println(abspath);
			
			String path =   getClass().getProtectionDomain().getCodeSource().getLocation().getPath();   
			System.out.println("path==" + path);
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());

			System.out.println(System.getProperty("user.dir"));
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));     

			  System.out.println(Test3.class.getClassLoader().getResource(""));        

			System.out.println(ClassLoader.getSystemResource(""));        
			  System.out.println(Test3.class.getResource(""));        
			  System.out.println(Test3.class.getResource("/")); //Class文件所在路径  
			  System.out.println(new File("/").getAbsolutePath());    
			
			String filename = "d:\\modexml\\mode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName() + "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
			String filename2 = "d:\\modexml\\modeNode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName() + "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
			String filename4 = "d:\\modexml\\Mode";
			String filename2 = "d:\\modexml\\Node";
//			System.out.println("filename--------"+filename);
//			Writer fileWriter = new FileWriter(filename);  // 中文乱码,FileOutputStream解决乱码
			FileOutputStream fileWriter = new FileOutputStream(filename);  
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of = OutputFormat.createPrettyPrint();  
			// 设置编码
			of.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter = new XMLWriter(fileWriter,of);  
			xmlWriter.write(document);  
			xmlWriter.flush();  
			xmlWriter.close();  
			
			FileOutputStream fileWriter2 = new FileOutputStream(filename2 + ".xml");  
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of2 = OutputFormat.createPrettyPrint();  
			// 设置编码
			of2.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter2 = new XMLWriter(fileWriter2,of2);  
			xmlWriter2.write(document2);  
			xmlWriter2.flush();  
			xmlWriter2.close();  
			
			java.io.OutputStream out=new java.io.FileOutputStream(fileName);
			  java.io.Writer wr=new java.io.OutputStreamWriter(out,"UTF-8");  
			  doc.write(wr);
			CharStream.reader(filename);
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
//			CharStream.writer(filename+".xml");
			CharStream.writer(filename+".xml");
			
			CharStream.reader(filename2);
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
			CharStream.writer(filename2+".xml");
			logger.info(filename + "文档添加成功！"); 
			File[] sources = new File[] { new File(filename + ".xml"),
					new File(filename2 + ".xml") };
			String filename3 = "d:\\modexml\\mode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName()+ "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
			File target = new File(filename3 +".ndd");
			// 打包成tar文件
			GZIPUtil.pack(sources, target);
			// 解包tar文件
//			List<String> list8 = GZIPUtil.readByZipInputStream(filename3 +".ndd", filename3 +"ndd");
			params = new HashMap<String, Object>();
			params.put("houseId", userordermain.getHouseId());
//			params.put("xmlFile", filename + ".xml" + ";" + filename2 + ".xml");
//			params.put("xmlFile", filename3 +".ndd");
			params.put("xmlFile", filename + ".xml" + ";" + filename2 + ".xml"+";"+filename3 +".ndd");
			userordermainDao.executeHql("update Userordermain t set xmlFile = :xmlFile where " +
					" t.houseId = :houseId", params);
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		
		return 1;
	}*/
	
	/**
	 * 根据某一个HouseID的设备列表模板和用户输入的信息生成设备采购订单，
	 * 并且要根据XML的格式生成一个文件保存到主表的XMlFile字段
		XML文件的格式参考以前设计的Mode执行规则（203上面的Mode.XML文件）
	 * @author: zhuangxd
	 * 时间：2013-12-6 下午3:30:13
	 * @param userordermain
	 * @return
	 *//*
	@Override
	public int createOrder20140719(Userordermain userordermain) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Modehouse modehouse = new Modehouse();
		params.put("houseId", userordermain.getHouseId());
		hql = "from Modehouse t where t.id = :houseId ";
		modenodeDao.executeSql2("update Modenode t,Modenodelib p set t.deviceUniqueId = concat(p.modelId,concat(t.id,'')) where t.modeNodeLibId" +
				"= p.id and t.houseId = :houseId", params);
		modedeviceDao.executeSql2("update Modedevice t,Modenode p set t.deviceUniqueId = concat(p.deviceUniqueId,t.ep) where t.modeNodeId" +
				"= p.id and t.houseId = :houseId", params);
		List<Modehouse> list = modehouseDao.find(hql,params);
		if(list != null && list.size() > 0)
			modehouse = list.get(0);
		else  modehouse = new Modehouse();
		
		params = new HashMap<String, Object>();
		params.put("houseId", userordermain.getHouseId());
		Modenode t2 = modenodeDao.get("from Modenode t where t.houseId = :houseId and t.modeNodeLibId = 86 ", params);
		//DocumentHelper提供了创建Document对象的方法   
		Document document = DocumentHelper.createDocument();  
		// 设置编码
		document.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement = document.addElement("DeviceInfo");  
		
		//DocumentHelper提供了创建Document对象的方法   
		Document document2 = DocumentHelper.createDocument();  
		// 设置编码
		document2.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement2 = document2.addElement("DeviceInfo");  
		
		//这里可以继续添加子节点，也可以指定内容   
//		rootElement.setText("这个是module标签的文本信息");  
		Element modehouseElement = rootElement.addElement("House"); 
		Element modehouseMainElement = modehouseElement.addElement("Main");  
		Element houseidElement = modehouseMainElement.addElement("ID");
		houseidElement.addAttribute("Name", modehouse.getHouseName());//为节点添加属性值   
		String secretKey = "";
		if (t2 == null) {
			houseidElement.addAttribute("IEEE", "");
			secretKey = "";
		} else if (t2.getIeee() == null || t2.getIeee() == "") {
			houseidElement.addAttribute("IEEE", "");
			secretKey = "";
		} else {
			houseidElement.addAttribute("IEEE", t2.getIeee());
			if (t2.getIeee().length() == 16) {
				secretKey = t2.getIeee().substring(6) + "NETVOX";
			} else {
				secretKey = "";
			}
		}
		houseidElement.addAttribute("Description", modehouse.getDescription());
		houseidElement.addAttribute("Longitude", "");
		houseidElement.addAttribute("Latitude", "");
		houseidElement.addAttribute("NetworkAddress", "");
		houseidElement.addAttribute("Version", "");
		houseidElement.addAttribute("Encodemethod", "0");
		houseidElement.addAttribute("SecretKey", secretKey);
		houseidElement.addAttribute("CloudIP", PropertiesUtil.getProperty("cloudOrder.host"));
		houseidElement.addAttribute("CloudPort", PropertiesUtil.getProperty("cloudOrder.port"));
		// 模式编辑器版本号
		 软件版本号由四部分组成，第一个1为主版本号，第二个1为子版本号，第三个1为阶段版本号，
		 * 第四部分为日期版本号加希腊字母版本号，希腊字母版本号共有3种，分别为： alpha、beta、release
		houseidElement.addAttribute("XmlVersion", "1.0.1.4_140703_release");
		hql = "from Moderoom t where t.houseId = :houseId order by t.roomId asc";
		List<Moderoom> list2 = moderoomDao.find(hql,params);
		if(list2 != null && list2.size() > 0){	
			
		}else list2 = new ArrayList<Moderoom>();
		hql = "from Modedevicebind t where t.houseId = :houseId";
		List<Modedevicebind> list5 = modedevicebindDao.find(hql,params);
		if(list5 != null && list5.size() > 0){	
			
		}else list5 = new ArrayList<Modedevicebind>();
		hql = "from Devicepicsetlib where houseId = :houseId";
		List<Devicepicsetlib> list6 = devicepicsetlibDao.find(hql,params);
		if(list6 != null && list6.size() > 0){	
			
		}else {
			hql = "from Devicepicsetlib where houseId = -1";
			list6 = devicepicsetlibDao.find(hql);
		}
		params = new HashMap<String, Object>();
		params.put("userId", modehouse.getUserId());
		Modeuser t = modeuserDao.get("from Modeuser t where t.id = :userId " +
				" and t.enabled = '1' ", params);
		userordermain.setUserId(t.getId());
//		userordermain.setUserName(t.getUserName());
		userordermain.setHouseName(modehouse.getHouseName());
		userordermain.setOrderName(modehouse.getHouseName() + "的订单数为：" + userordermain.getCount());
		userordermainDao.save(userordermain);
		Userorderroom ob2 = null;
		Userordernode userordernode = null;
		Userorderep userorderep = null;	
		
		Element modeRoomElement = rootElement.addElement("Room"); 
		Element modeRoomMainElement = modeRoomElement.addElement("Main");  
		Element modeRoomidElement = null;
		
		Element modeNodeElement = rootElement.addElement("Node"); 
		Element modeNodeMainElement = modeNodeElement.addElement("Main");  
		Element modeNodeidElement = null;
		
		Element modeNodeElement2 = rootElement2.addElement("Node"); 
		Element modeNodeMainElement2 = modeNodeElement2.addElement("Main");  
		Element modeNodeidElement2 = null;
		
		Element modeDeviceElement = rootElement.addElement("Device"); 
		Element modeDeviceMainElement = modeDeviceElement.addElement("Main");  
		Element modeDeviceidElement = null;
		
		Element modedevicebindElement = rootElement.addElement("DeviceBind"); 
		Element modedevicebindElementMainElement = modedevicebindElement.addElement("Main");  
		Element modedevicebindElementidElement = null;
		
		Element devicePicSetElement = rootElement.addElement("DevicePicSet"); 
		Element devicePicSetElementMainElement = devicePicSetElement.addElement("Main");  
		Element devicePicSetElementidElement = null;
		
		if(list2 != null && list2.size() > 0) {
			for(Moderoom ob1:list2){
				ob2 = new Userorderroom();
				ob2.setRoomId(ob1.getRoomId());
				ob2.setOrderId(userordermain.getId());
				ob2.setRoomName(ob1.getRoomName());
				ob2.setRoomPic(ob1.getRoomPic());
				userorderroomDao.save(ob2);			
				
				modeRoomidElement = modeRoomMainElement.addElement("ID");  
				modeRoomidElement.addAttribute("RoomID", String.valueOf(ob1.getRoomId()));
				modeRoomidElement.addAttribute("RoomName", ob1.getRoomName());
//				modeRoomidElement.addAttribute("RoomPic", ob1.getRoomPic());
				modeRoomidElement.addAttribute("RoomPic", "");
				
				params = new HashMap<String, Object>();
				params.put("houseId", userordermain.getHouseId());
				params.put("roomId", ob1.getRoomId());
				hql = "from Modenode t where t.houseId = :houseId and t.roomId = :roomId";
				List<Modenode> list3 = modenodeDao.find(hql,params);
				if(list3 != null && list3.size() > 0) {
					for(Modenode ob3:list3){
						userordernode = new Userordernode();
						userordernode.setOrderId(userordermain.getId());
						userordernode.setRoomId(ob1.getRoomId());
						userordernode.setModeNodeLibId(ob3.getModeNodeLibId());
						userordernode.setDeviceUniqueId(ob3.getDeviceUniqueId());
						userordernode.setIeee(ob3.getIeee());
						userordernode.setDeviceName(ob3.getDeviceName());
						userordernode.setDescription(ob3.getDescription());
						userordernodeDao.save(userordernode);
						
						modeNodeidElement = modeNodeMainElement.addElement("ID");  
						modeNodeidElement.addAttribute("MID", String.valueOf(ob3.getId()));
						if (ob3.getIeee() == null) {
							modeNodeidElement.addAttribute("IEEE", "");
						} else {
							modeNodeidElement.addAttribute("IEEE", ob3.getIeee());
						}
						modeNodeidElement.addAttribute("RoomID", String.valueOf(ob3.getRoomId()));
						modeNodeidElement.addAttribute("ModeNodeLibId", String.valueOf(ob3.getModeNodeLibId()));
						modeNodeidElement.addAttribute("Devicename", String.valueOf(ob3.getId()));
						modeNodeidElement.addAttribute("DeviceUniqueId", ob3.getDeviceUniqueId());
						modeNodeidElement.addAttribute("Description", ob3.getDeviceName());
						modeNodeidElement.addAttribute("Type", "0");
						
						modeNodeidElement2 = modeNodeMainElement2.addElement("ID");  
						modeNodeidElement2.addAttribute("MID", String.valueOf(ob3.getId()));
						if (ob3.getIeee() == null) {
							modeNodeidElement2.addAttribute("IEEE", "");
						} else {
							modeNodeidElement2.addAttribute("IEEE", ob3.getIeee());
						}
						modeNodeidElement2.addAttribute("RoomID", String.valueOf(ob3.getRoomId()));
						modeNodeidElement2.addAttribute("ModeNodeLibId", String.valueOf(ob3.getModeNodeLibId()));
//						modeNodeidElement2.addAttribute("Devicename", String.valueOf(ob3.getId()));
						modeNodeidElement2.addAttribute("DeviceUniqueId", ob3.getDeviceUniqueId());
						modeNodeidElement2.addAttribute("Description", ob3.getDeviceName());
						modeNodeidElement2.addAttribute("Type", "0");
						
						hql = "from Modedevice t where t.modeNodeId = :modeNodeId";
						params = new HashMap<String, Object>();
						params.put("modeNodeId", ob3.getId());
						List<Modedevice> list4 = modedeviceDao.find(hql,params);
						if(list4 != null && list4.size() > 0) {
							for(Modedevice ob4:list4){
								userorderep = new Userorderep();
								BeanUtils.copyProperties(ob4, userorderep);
								userorderep.setOrderId(userordermain.getId());
								userorderep.setUserOrderNodeId(userordernode.getId());
								userorderepDao.save(userorderep);
								
								modeDeviceidElement = modeDeviceMainElement.addElement("ID");  
								modeDeviceidElement.addAttribute("SubID", String.valueOf(ob4.getId()));
								modeDeviceidElement.addAttribute("MID", String.valueOf(ob4.getModeNodeId()));
//								modeDeviceidElement.addAttribute("DeviceName", String.valueOf(ob4.getId()));
								if (ob4.getDeviceUniqueId() == null) {
									modeDeviceidElement.addAttribute("DeviceUniqueId", "");
								} else {
									modeDeviceidElement.addAttribute("DeviceUniqueId", ob4.getDeviceUniqueId());
								}
								if (ob4.getIeee() == null) {
									modeDeviceidElement.addAttribute("IEEE", "");
								} else {
									modeDeviceidElement.addAttribute("IEEE", ob4.getIeee());
								}
								modeDeviceidElement.addAttribute("ModelID", ob4.getModelId());
								modeDeviceidElement.addAttribute("DeviceId", ob4.getDeviceId());
								modeDeviceidElement.addAttribute("EP", ob4.getEp());
								modeDeviceidElement.addAttribute("RoomID", String.valueOf(ob4.getRoomId()));
								modeDeviceidElement.addAttribute("type", "0");
								modeDeviceidElement.addAttribute("Description", ob4.getDeviceName());
								modeDeviceidElement.addAttribute("Picname", "");
							}
						}
					}
				}
				
			}
		} 
		
		if(list5 != null && list5.size() > 0) {
			for(Modedevicebind ob1:list5){
				modedevicebindElementidElement = modedevicebindElementMainElement.addElement("ID");  
				modedevicebindElementidElement.addAttribute("BindType", String.valueOf(ob1.getBindType()));
				if (ob1.getSourceIeee() == null) {
					modedevicebindElementidElement.addAttribute("SourceIEEE", "");
				} else {
					modedevicebindElementidElement.addAttribute("SourceIEEE", ob1.getSourceIeee());
				}
				if (ob1.gets() == null) {
					modeDeviceidElement.addAttribute("IEEE", "");
				} else {
					modeDeviceidElement.addAttribute("IEEE", ob4.getIeee());
				}
//				modedevicebindElementidElement.addAttribute("SourceId", "");
				modedevicebindElementidElement.addAttribute("SourceEP", ob1.getSourceVirtualEp());
				modedevicebindElementidElement.addAttribute("Source", String.valueOf(ob1.getSourceId()));
//				modedevicebindElementidElement.addAttribute("SourceDevicename", String.valueOf(ob1.getSourceId()));
				modedevicebindElementidElement.addAttribute("SourceDeviceUniqueId", ob1.getSourceDeviceUniqueId());
				modedevicebindElementidElement.addAttribute("SourceDescription", ob1.getSourceDevicename());
				modedevicebindElementidElement.addAttribute("ClusterID", ob1.getClusterId());
				modedevicebindElementidElement.addAttribute("ClusterName", ob1.getClusterName());
				modedevicebindElementidElement.addAttribute("DestType", ob1.getDestType());
				if (ob1.getDestIeee() == null) {
					modedevicebindElementidElement.addAttribute("DestIEEE", "");
				} else {
					modedevicebindElementidElement.addAttribute("DestIEEE", ob1.getDestIeee());
				}
//				modedevicebindElementidElement.addAttribute("DestId", "");
				modedevicebindElementidElement.addAttribute("DestEP", ob1.getDestEp());
				modedevicebindElementidElement.addAttribute("Dest", String.valueOf(ob1.getDestId()));
//				modedevicebindElementidElement.addAttribute("Destname", String.valueOf(ob1.getDestId()));
				modedevicebindElementidElement.addAttribute("DestDeviceUniqueId", ob1.getDestDeviceUniqueId());
				modedevicebindElementidElement.addAttribute("DestDescription", ob1.getDestName());
				modedevicebindElementidElement.addAttribute("HasBind", String.valueOf(ob1.getHasBind()));
			}
		} 
		
		if(list6 != null && list6.size() > 0) {
			for(Devicepicsetlib ob1:list6){
				devicePicSetElementidElement = devicePicSetElementMainElement.addElement("ID");  
				devicePicSetElementidElement.addAttribute("ID", String.valueOf(ob1.getMid()));
				devicePicSetElementidElement.addAttribute("DeviceID", ob1.getDeviceId());
				if (ob1.getPicName() == null) {
					devicePicSetElementidElement.addAttribute("PicName", "");
				} else {
					devicePicSetElementidElement.addAttribute("PicName", ob1.getPicName());
				}
			}
		} 
		
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),0).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),2).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),4).toxml());		
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),6).toxml());	
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),8).toxml());	
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),10).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),12).toxml());			
		String str = dxmlutil.loadEntity(userordermain.getHouseId());
		try {
			Document document2 = DocumentHelper.parseText(str);
			rootElement.add(document2.getRootElement());
		} catch (Exception e) {
		}
		
		try {  
			// logger.info(document.asXML()); //将document文档对象直接转换成字符串输出
//			logger.info(System.getProperty("user.dir"));
			
			File file=new File("");
			String abspath=file.getAbsolutePath();
			System.out.println(abspath);
			
			String path =   getClass().getProtectionDomain().getCodeSource().getLocation().getPath();   
			System.out.println("path==" + path);
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());

			System.out.println(System.getProperty("user.dir"));
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));     

			  System.out.println(Test3.class.getClassLoader().getResource(""));        

			System.out.println(ClassLoader.getSystemResource(""));        
			  System.out.println(Test3.class.getResource(""));        
			  System.out.println(Test3.class.getResource("/")); //Class文件所在路径  
			  System.out.println(new File("/").getAbsolutePath());    
			
			String filename = "d:\\modexml\\mode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName() + "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
			String filename2 = "d:\\modexml\\modeNode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName() + "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
			String filename4 = "d:\\modexml\\Mode";
			String filename2 = "d:\\modexml\\Node";
//			System.out.println("filename--------"+filename);
//			Writer fileWriter = new FileWriter(filename);  // 中文乱码,FileOutputStream解决乱码
			FileOutputStream fileWriter = new FileOutputStream(filename);  
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of = OutputFormat.createPrettyPrint();  
			// 设置编码
			of.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter = new XMLWriter(fileWriter,of);  
			xmlWriter.write(document);  
			xmlWriter.flush();  
			xmlWriter.close();  
			
			FileOutputStream fileWriter2 = new FileOutputStream(filename2 + ".xml");  
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of2 = OutputFormat.createPrettyPrint();  
			// 设置编码
			of2.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter2 = new XMLWriter(fileWriter2,of2);  
			xmlWriter2.write(document2);  
			xmlWriter2.flush();  
			xmlWriter2.close();  
			
			java.io.OutputStream out=new java.io.FileOutputStream(fileName);
			  java.io.Writer wr=new java.io.OutputStreamWriter(out,"UTF-8");  
			  doc.write(wr);
			CharStream.reader(filename);
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
//			CharStream.writer(filename+".xml");
			CharStream.writer(filename+".xml");
			
			CharStream.reader(filename2);
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
			CharStream.writer(filename2+".xml");
			logger.info(filename + "文档添加成功！"); 
			File[] sources = new File[] { new File(filename + ".xml"),
					new File(filename2 + ".xml") };
			String filename3 = "d:\\modexml\\mode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName()+ "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
			File target = new File(filename3 +".ndd");
			// 打包成tar文件
			GZIPUtil.pack(sources, target);
			// 解包tar文件
//			List<String> list8 = GZIPUtil.readByZipInputStream(filename3 +".ndd", filename3 +"ndd");
			params = new HashMap<String, Object>();
			params.put("houseId", userordermain.getHouseId());
//			params.put("xmlFile", filename + ".xml" + ";" + filename2 + ".xml");
//			params.put("xmlFile", filename3 +".ndd");
			params.put("xmlFile", filename + ".xml" + ";" + filename2 + ".xml"+";"+filename3 +".ndd");
			userordermainDao.executeHql("update Userordermain t set xmlFile = :xmlFile where " +
					" t.houseId = :houseId", params);
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		
		return 1;
	}*/
	
	/**
	 * 根据某一个HouseID的设备列表模板和用户输入的信息生成设备采购订单，
	 * 并且要根据XML的格式生成一个文件保存到主表的XMlFile字段
		XML文件的格式参考以前设计的Mode执行规则（203上面的Mode.XML文件）
	 * @author: zhuangxd
	 * 时间：2013-12-6 下午3:30:13
	 * @param userordermain
	 * @return
	 *//*
	@Override
	public int createOrder2(Userordermain userordermain) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Modehouse modehouse = new Modehouse();
		params.put("houseId", userordermain.getHouseId());
		hql = "from Modehouse t where t.id = :houseId ";
		modenodeDao.executeSql2("update Modenode t,Modenodelib p set t.deviceUniqueId = concat(p.modelId,concat(t.id,'')) where t.modeNodeLibId" +
				"= p.id and t.houseId = :houseId", params);
		modedeviceDao.executeSql2("update Modedevice t,Modenode p set t.deviceUniqueId = concat(p.deviceUniqueId,t.ep) where t.modeNodeId" +
				"= p.id and t.houseId = :houseId", params);
		List<Modehouse> list = modehouseDao.find(hql,params);
		if(list != null && list.size() > 0)
			modehouse = list.get(0);
		else  modehouse = new Modehouse();
		
		params = new HashMap<String, Object>();
		params.put("houseId", userordermain.getHouseId());
		Modenode t2 = modenodeDao.get("from Modenode t where t.houseId = :houseId and t.modeNodeLibId = 86 ", params);
		//DocumentHelper提供了创建Document对象的方法   
		Document document = DocumentHelper.createDocument();  
		// 设置编码
		document.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement = document.addElement("DeviceInfo");  
		
		//DocumentHelper提供了创建Document对象的方法   
		Document document2 = DocumentHelper.createDocument();  
		// 设置编码
		document2.setXMLEncoding("UTF-8"); 
		//添加节点信息   
		Element rootElement2 = document2.addElement("DeviceInfo");  
		
		//这里可以继续添加子节点，也可以指定内容   
//		rootElement.setText("这个是module标签的文本信息");  
		Element modehouseElement = rootElement.addElement("House"); 
		Element modehouseMainElement = modehouseElement.addElement("Main");  
		Element houseidElement = modehouseMainElement.addElement("ID");  
		houseidElement.addAttribute("Name", modehouse.getHouseName());//为节点添加属性值   
		String secretKey = "";
		if (t2 == null) {
			houseidElement.addAttribute("IEEE", "");
			secretKey = "";
		} else if (t2.getIeee() == null || t2.getIeee() == "") {
			houseidElement.addAttribute("IEEE", "");
			secretKey = "";
		} else {
			houseidElement.addAttribute("IEEE", t2.getIeee());
			if (t2.getIeee().length() == 16) {
				secretKey = t2.getIeee().substring(6) + "NETVOX";
			} else {
				secretKey = "";
			}
		}
		houseidElement.addAttribute("Description", modehouse.getDescription());
		houseidElement.addAttribute("Longitude", "");
		houseidElement.addAttribute("Latitude", "");
		houseidElement.addAttribute("NetworkAddress", "");
		houseidElement.addAttribute("Version", "");
		houseidElement.addAttribute("Encodemethod", "0");
		houseidElement.addAttribute("SecretKey", secretKey);
		houseidElement.addAttribute("CloudIP", PropertiesUtil.getProperty("cloudOrder.host"));
		houseidElement.addAttribute("CloudPort", PropertiesUtil.getProperty("cloudOrder.port"));
		hql = "from Moderoom t where t.houseId = :houseId order by t.roomId asc";
		List<Moderoom> list2 = moderoomDao.find(hql,params);
		if(list2 != null && list2.size() > 0){	
			
		}else list2 = new ArrayList<Moderoom>();
		hql = "from Modedevicebind t where t.houseId = :houseId";
		List<Modedevicebind> list5 = modedevicebindDao.find(hql,params);
		if(list5 != null && list5.size() > 0){	
			
		}else list5 = new ArrayList<Modedevicebind>();
		hql = "from Devicepicsetlib where houseId = :houseId";
		List<Devicepicsetlib> list6 = devicepicsetlibDao.find(hql,params);
		if(list6 != null && list6.size() > 0){	
			
		}else {
			hql = "from Devicepicsetlib where houseId = -1";
			list6 = devicepicsetlibDao.find(hql);
		}
		params = new HashMap<String, Object>();
		params.put("userId", modehouse.getUserId());
		Modeuser t = modeuserDao.get("from Modeuser t where t.id = :userId " +
				" and t.enabled = '1' ", params);
		userordermain.setUserId(t.getId());
//		userordermain.setUserName(t.getUserName());
		userordermain.setHouseName(modehouse.getHouseName());
		userordermain.setOrderName(modehouse.getHouseName() + "的订单数为：" + userordermain.getCount());
		userordermainDao.save(userordermain);
		Userorderroom ob2 = null;
		Userordernode userordernode = null;
		Userorderep userorderep = null;	
		
		Element modeRoomElement = rootElement.addElement("Room"); 
		Element modeRoomMainElement = modeRoomElement.addElement("Main");  
		Element modeRoomidElement = null;
		
		Element modeNodeElement = rootElement.addElement("Node"); 
		Element modeNodeMainElement = modeNodeElement.addElement("Main");  
		Element modeNodeidElement = null;
		
		Element modeNodeElement2 = rootElement2.addElement("Node"); 
		Element modeNodeMainElement2 = modeNodeElement2.addElement("Main");  
		Element modeNodeidElement2 = null;
		
		Element modeDeviceElement = rootElement.addElement("Device"); 
		Element modeDeviceMainElement = modeDeviceElement.addElement("Main");  
		Element modeDeviceidElement = null;
		
		Element modedevicebindElement = rootElement.addElement("DeviceBind"); 
		Element modedevicebindElementMainElement = modedevicebindElement.addElement("Main");  
		Element modedevicebindElementidElement = null;
		
		Element devicePicSetElement = rootElement.addElement("DevicePicSet"); 
		Element devicePicSetElementMainElement = devicePicSetElement.addElement("Main");  
		Element devicePicSetElementidElement = null;
		
		if(list2 != null && list2.size() > 0) {
			for(Moderoom ob1:list2){
				ob2 = new Userorderroom();
				ob2.setRoomId(ob1.getRoomId());
				ob2.setOrderId(userordermain.getId());
				ob2.setRoomName(ob1.getRoomName());
				ob2.setRoomPic(ob1.getRoomPic());
				userorderroomDao.save(ob2);			
				
				modeRoomidElement = modeRoomMainElement.addElement("ID");  
				modeRoomidElement.addAttribute("RoomID", String.valueOf(ob1.getRoomId()));
				modeRoomidElement.addAttribute("RoomName", ob1.getRoomName());
//				modeRoomidElement.addAttribute("RoomPic", ob1.getRoomPic());
				modeRoomidElement.addAttribute("RoomPic", "");
				
				params = new HashMap<String, Object>();
				params.put("houseId", userordermain.getHouseId());
				params.put("roomId", ob1.getRoomId());
				hql = "from Modenode t where t.houseId = :houseId and t.roomId = :roomId";
				List<Modenode> list3 = modenodeDao.find(hql,params);
				if(list3 != null && list3.size() > 0) {
					for(Modenode ob3:list3){
						userordernode = new Userordernode();
						userordernode.setOrderId(userordermain.getId());
						userordernode.setRoomId(ob1.getRoomId());
						userordernode.setModeNodeLibId(ob3.getModeNodeLibId());
						userordernode.setDeviceUniqueId(ob3.getDeviceUniqueId());
						userordernode.setIeee(ob3.getIeee());
						userordernode.setDeviceName(ob3.getDeviceName());
						userordernode.setDescription(ob3.getDescription());
						userordernodeDao.save(userordernode);
						
						modeNodeidElement = modeNodeMainElement.addElement("ID");  
						modeNodeidElement.addAttribute("MID", String.valueOf(ob3.getId()));
						if (ob3.getIeee() == null) {
							modeNodeidElement.addAttribute("IEEE", "");
						} else {
							modeNodeidElement.addAttribute("IEEE", ob3.getIeee());
						}
						modeNodeidElement.addAttribute("RoomID", String.valueOf(ob3.getRoomId()));
						modeNodeidElement.addAttribute("ModeNodeLibId", String.valueOf(ob3.getModeNodeLibId()));
						modeNodeidElement.addAttribute("Devicename", String.valueOf(ob3.getId()));
						modeNodeidElement.addAttribute("DeviceUniqueId", ob3.getDeviceUniqueId());
						modeNodeidElement.addAttribute("Description", ob3.getDeviceName());
						modeNodeidElement.addAttribute("Type", "0");
						
						modeNodeidElement2 = modeNodeMainElement2.addElement("ID");  
						modeNodeidElement2.addAttribute("MID", String.valueOf(ob3.getId()));
						if (ob3.getIeee() == null) {
							modeNodeidElement2.addAttribute("IEEE", "");
						} else {
							modeNodeidElement2.addAttribute("IEEE", ob3.getIeee());
						}
						modeNodeidElement2.addAttribute("RoomID", String.valueOf(ob3.getRoomId()));
						modeNodeidElement2.addAttribute("ModeNodeLibId", String.valueOf(ob3.getModeNodeLibId()));
//						modeNodeidElement2.addAttribute("Devicename", String.valueOf(ob3.getId()));
						modeNodeidElement2.addAttribute("DeviceUniqueId", ob3.getDeviceUniqueId());
						modeNodeidElement2.addAttribute("Description", ob3.getDeviceName());
						modeNodeidElement2.addAttribute("Type", "0");
						
						hql = "from Modedevice t where t.modeNodeId = :modeNodeId";
						params = new HashMap<String, Object>();
						params.put("modeNodeId", ob3.getId());
						List<Modedevice> list4 = modedeviceDao.find(hql,params);
						if(list4 != null && list4.size() > 0) {
							for(Modedevice ob4:list4){
								userorderep = new Userorderep();
								BeanUtils.copyProperties(ob4, userorderep);
								userorderep.setOrderId(userordermain.getId());
								userorderep.setUserOrderNodeId(userordernode.getId());
								userorderepDao.save(userorderep);
								
								modeDeviceidElement = modeDeviceMainElement.addElement("ID");  
								modeDeviceidElement.addAttribute("SubID", String.valueOf(ob4.getId()));
								modeDeviceidElement.addAttribute("MID", String.valueOf(ob4.getModeNodeId()));
//								modeDeviceidElement.addAttribute("DeviceName", String.valueOf(ob4.getId()));
								if (ob4.getDeviceUniqueId() == null) {
									modeDeviceidElement.addAttribute("DeviceUniqueId", "");
								} else {
									modeDeviceidElement.addAttribute("DeviceUniqueId", ob4.getDeviceUniqueId());
								}
								if (ob4.getIeee() == null) {
									modeDeviceidElement.addAttribute("IEEE", "");
								} else {
									modeDeviceidElement.addAttribute("IEEE", ob4.getIeee());
								}
								modeDeviceidElement.addAttribute("ModelID", ob4.getModelId());
								modeDeviceidElement.addAttribute("DeviceId", ob4.getDeviceId());
								modeDeviceidElement.addAttribute("EP", ob4.getEp());
								modeDeviceidElement.addAttribute("RoomID", String.valueOf(ob4.getRoomId()));
								modeDeviceidElement.addAttribute("type", "0");
								modeDeviceidElement.addAttribute("Description", ob4.getDeviceName());
								modeDeviceidElement.addAttribute("Picname", "");
							}
						}
					}
				}
				
			}
		} 
		
		if(list5 != null && list5.size() > 0) {
			for(Modedevicebind ob1:list5){
				modedevicebindElementidElement = modedevicebindElementMainElement.addElement("ID");  
				modedevicebindElementidElement.addAttribute("BindType", String.valueOf(ob1.getBindType()));
				if (ob1.getSourceIeee() == null) {
					modedevicebindElementidElement.addAttribute("SourceIEEE", "");
				} else {
					modedevicebindElementidElement.addAttribute("SourceIEEE", ob1.getSourceIeee());
				}
				if (ob1.gets() == null) {
					modeDeviceidElement.addAttribute("IEEE", "");
				} else {
					modeDeviceidElement.addAttribute("IEEE", ob4.getIeee());
				}
//				modedevicebindElementidElement.addAttribute("SourceId", "");
				modedevicebindElementidElement.addAttribute("SourceEP", ob1.getSourceVirtualEp());
				modedevicebindElementidElement.addAttribute("Source", String.valueOf(ob1.getSourceId()));
//				modedevicebindElementidElement.addAttribute("SourceDevicename", String.valueOf(ob1.getSourceId()));
				modedevicebindElementidElement.addAttribute("SourceDeviceUniqueId", ob1.getSourceDeviceUniqueId());
				modedevicebindElementidElement.addAttribute("SourceDescription", ob1.getSourceDevicename());
				modedevicebindElementidElement.addAttribute("ClusterID", ob1.getClusterId());
				modedevicebindElementidElement.addAttribute("ClusterName", ob1.getClusterName());
				modedevicebindElementidElement.addAttribute("DestType", ob1.getDestType());
				if (ob1.getDestIeee() == null) {
					modedevicebindElementidElement.addAttribute("DestIEEE", "");
				} else {
					modedevicebindElementidElement.addAttribute("DestIEEE", ob1.getDestIeee());
				}
//				modedevicebindElementidElement.addAttribute("DestId", "");
				modedevicebindElementidElement.addAttribute("DestEP", ob1.getDestEp());
				modedevicebindElementidElement.addAttribute("Dest", String.valueOf(ob1.getDestId()));
//				modedevicebindElementidElement.addAttribute("Destname", String.valueOf(ob1.getDestId()));
				modedevicebindElementidElement.addAttribute("DestDeviceUniqueId", ob1.getDestDeviceUniqueId());
				modedevicebindElementidElement.addAttribute("DestDescription", ob1.getDestName());
				modedevicebindElementidElement.addAttribute("HasBind", String.valueOf(ob1.getHasBind()));
			}
		} 
		
		if(list6 != null && list6.size() > 0) {
			for(Devicepicsetlib ob1:list6){
				devicePicSetElementidElement = devicePicSetElementMainElement.addElement("ID");  
				devicePicSetElementidElement.addAttribute("ID", String.valueOf(ob1.getMid()));
				devicePicSetElementidElement.addAttribute("DeviceID", ob1.getDeviceId());
				if (ob1.getPicName() == null) {
					devicePicSetElementidElement.addAttribute("PicName", "");
				} else {
					devicePicSetElementidElement.addAttribute("PicName", ob1.getPicName());
				}
			}
		} 
		
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),0).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),2).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),4).toxml());		
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),6).toxml());	
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),8).toxml());	
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),10).toxml());
		rootElement.add(xmlutil.loadEntity(userordermain.getHouseId(),12).toxml());			
		String str = dxmlutil.loadEntity(userordermain.getHouseId());
		try {
			Document document2 = DocumentHelper.parseText(str);
			rootElement.add(document2.getRootElement());
		} catch (Exception e) {
		}
		
		try {  
			// logger.info(document.asXML()); //将document文档对象直接转换成字符串输出
//			logger.info(System.getProperty("user.dir"));
			
			File file=new File("");
			String abspath=file.getAbsolutePath();
			System.out.println(abspath);
			
			String path =   getClass().getProtectionDomain().getCodeSource().getLocation().getPath();   
			System.out.println("path==" + path);
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());

			System.out.println(System.getProperty("user.dir"));
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));     

			  System.out.println(Test3.class.getClassLoader().getResource(""));        

			System.out.println(ClassLoader.getSystemResource(""));        
			  System.out.println(Test3.class.getResource(""));        
			  System.out.println(Test3.class.getResource("/")); //Class文件所在路径  
			  System.out.println(new File("/").getAbsolutePath());    
			
			String filename = "d:\\modexml\\mode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName() + "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
			String filename2 = "d:\\modexml\\modeNode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName() + "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
//			System.out.println("filename--------"+filename);
//			Writer fileWriter = new FileWriter(filename);  // 中文乱码,FileOutputStream解决乱码
			FileOutputStream fileWriter = new FileOutputStream(filename);  
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of = OutputFormat.createPrettyPrint();  
			// 设置编码
			of.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter = new XMLWriter(fileWriter,of);  
			xmlWriter.write(document);  
			xmlWriter.flush();  
			xmlWriter.close();  
			
			FileOutputStream fileWriter2 = new FileOutputStream(filename2 + ".xml");  
			//dom4j提供了专门写入文件的对象XMLWriter   
//			 OutputFormat of = new OutputFormat();  
			// 格式化输出
			OutputFormat of2 = OutputFormat.createPrettyPrint();  
			// 设置编码
			of2.setEncoding("UTF-8"); //改变编码方式
			XMLWriter xmlWriter2 = new XMLWriter(fileWriter2,of2);  
			xmlWriter2.write(document2);  
			xmlWriter2.flush();  
			xmlWriter2.close();  
			
			java.io.OutputStream out=new java.io.FileOutputStream(fileName);
			  java.io.Writer wr=new java.io.OutputStreamWriter(out,"UTF-8");  
			  doc.write(wr);
			CharStream.reader(filename);
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
			CharStream.writer(filename+".xml");
			
			CharStream.reader(filename2);
			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
			CharStream.writer(filename2+".xml");
			logger.info(filename + "文档添加成功！"); 
			params = new HashMap<String, Object>();
			params.put("houseId", userordermain.getHouseId());
			params.put("xmlFile", filename + ".xml" + ";" + filename2 + ".xml");
			userordermainDao.executeHql("update Userordermain t set xmlFile = :xmlFile where " +
					" t.houseId = :houseId", params);
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		
		return 1;
	}*/
	
	/**
	 * 奈伯思标准情景模式导入
	 * @author: zhuangxd
	 * 时间：2013-12-11 上午11:35:19
	 * @param userordermain
	 * @return
	 */
	@Override
	public long ImportFromLib(Userordermain userordermain) throws Exception {
		
		String hql = "";
		String xmlVersion = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Modehouse modehouse = new Modehouse();
		Moderoom moderoom= new Moderoom();
		Modenode modenode= new Modenode();
		Modedevice modedevice = new Modedevice();
		Modedevicebind modedevicebind = new Modedevicebind();
		Devicepicsetlib devicepicsetlib = new Devicepicsetlib();
		params.put("orderId", userordermain.getId());
		hql = "from Userordermain t where t.id = :orderId ";
		Userordermain userordermain2 = userordermainDao.get(hql,params);
		String filePath = userordermain2.getXmlFile();
		String[] fileArr = filePath.split(";");
		// fileArr[0]为加密的mode文件   fileArr[1]为明文的node文件
		String filename = fileArr[0].substring(0,fileArr[0].length()-4);
        
        // 解密
        CharStream.reader(fileArr[0]);
		CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
		CharStream.writer(filename+"明文"+"-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date())+".xml");
		File file = new File(filename+"明文"+"-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date())+".xml");
		File file3 = new File(fileArr[1]);
		Element houseElement2 = null;
		Element root2 = null;
		Element root = null;
		Document document = null;
		
        if (file.exists()) {
        	SAXReader reader = new SAXReader();
        	// node文件读取
    		if (file3.exists()) {
            	SAXReader reader2 = new SAXReader();
//            	try {
            		Document document2 = reader2.read(file3);// 读取XML文件
            		root2 = document2.getRootElement();// 得到根节点
            		document = reader.read(file);// 读取XML文件 mode文件
            		root = document.getRootElement();// 得到根节点
            		houseElement2 = root2.element("House"); 
            		if (houseElement2 != null) { // 新版node文件包含House字段
	                    Element houseidElement = null;
	             		for (Iterator i = houseElement2.element("Main").elementIterator("ID"); i.hasNext();) {
	             			houseidElement = (Element) i.next();
	             			modehouse = new Modehouse();
	             			modehouse.setUserId(userordermain.getUserId());
	             			modehouse.setHouseIeee(houseidElement.attributeValue("IEEE"));
	             			modehouse.setHosueGuid(houseidElement.attributeValue("HouseID"));
	             			modehouse.setCloudIp(houseidElement.attributeValue("CloudIP"));
	             			xmlVersion = houseidElement.attributeValue("XmlVersion");
	             			modehouse.setXmlVersion(xmlVersion);
	             			Element houseElement = root.element("House"); // mode文件 
	                        Element houseidElement2 = null;
	                 		for (Iterator k = houseElement.element("Main").elementIterator("ID"); k.hasNext();) {
	                 			houseidElement2 = (Element) k.next();
	                 			if (!houseidElement2.attributeValue("HouseID").equals(houseidElement.attributeValue("HouseID"))) {
	                 				return 0;
	                 			}
	                 		}
	             			modehouse.setHouseName(houseidElement.attributeValue("Name"));
	             			params = new HashMap<String, Object>();
	             			params.put("userId", modehouse.getUserId());
	             			params.put("houseName", modehouse.getHouseName());
	             			// 家不能同名
	             			Modehouse t = modehouseDao.get("from Modehouse t where t.userId = :userId " +
	             					" and t.houseName = :houseName ", params);
	             			if (t != null) {
	//             				return -1;
	             				// 导入前，先删除家
	                 			/*modehouseDao.executeHql("delete Modehouse t where t.userId = :userId " +
	                 					" and t.houseName = :houseName ", params);*/
	                 			modehouseService.delete(t);
	             			}        			
	             			modehouse.setDescription(houseidElement.attributeValue("Description"));
	             			modehouseDao.save(modehouse);
	             		}
	             		
	             		Element nodeElement = root2.element("Node");  
	                    Element nodeidElement = null;
	            		for (Iterator i = nodeElement.element("Main").elementIterator("ID"); i.hasNext();) {
	            			nodeidElement = (Element) i.next();
	            			modenode= new Modenode();
	            			modenode.setHouseId(modehouse.getId());
	            			modenode.setOldId(Long.parseLong(nodeidElement.attributeValue("MID")));
	            			modenode.setRoomId(Long.parseLong(nodeidElement.attributeValue("RoomID")));
	            			modenode.setIeee(nodeidElement.attributeValue("IEEE"));
	            			modenode.setDeviceUniqueId(nodeidElement.attributeValue("DeviceUniqueId"));
	            			if (nodeidElement.attribute("ModeNodeLibId") == null) {
	            				modenode.setModeNodeLibId(0);
	            			} else {
	            				modenode.setModeNodeLibId(Long.parseLong(nodeidElement.attributeValue("ModeNodeLibId")));
	            			}
	            			if (nodeidElement.attribute("Description") == null) {
//	            				modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
	            				modenode.setDeviceName(nodeidElement.attributeValue("MID")+":"+nodeidElement.attributeValue("IEEE"));
	            			} else {
	            				if (StringUtils.isBlank(nodeidElement.attributeValue("Description"))) {
//	            					modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
	                				modenode.setDeviceName(nodeidElement.attributeValue("MID")+":"+nodeidElement.attributeValue("IEEE"));
	            				} else {
	            					modenode.setDeviceName(nodeidElement.attributeValue("Description"));
	            				}
	            			}
	            			modenodeDao.save(modenode);
	            		}
            		}
//            	} catch (Exception e) {
//            		e.printStackTrace();
//    			}
            }
    		
    		// mode文件读取
//        	try {
        		if (houseElement2 == null) { // 旧版node文件不包含House字段
        			 Element houseElement = root.element("House");  
                     Element houseidElement = null;
             		for (Iterator i = houseElement.element("Main").elementIterator("ID"); i.hasNext();) {
             			houseidElement = (Element) i.next();
             			modehouse = new Modehouse();
             			modehouse.setUserId(userordermain.getUserId());
             			modehouse.setHouseIeee(houseidElement.attributeValue("IEEE"));
             			modehouse.setHosueGuid(houseidElement.attributeValue("HouseID"));
             			modehouse.setCloudIp(houseidElement.attributeValue("CloudIP"));
             			modehouse.setHouseName(houseidElement.attributeValue("Name"));
             			xmlVersion = houseidElement.attributeValue("XmlVersion");
             			modehouse.setXmlVersion(xmlVersion);
             			params = new HashMap<String, Object>();
             			params.put("userId", modehouse.getUserId());
             			params.put("houseName", modehouse.getHouseName());
             			// 家不能同名
             			Modehouse t = modehouseDao.get("from Modehouse t where t.userId = :userId " +
             					" and t.houseName = :houseName ", params);
             			if (t != null) {
//             				return -1;
             				// 导入前，先删除家
                 			/*modehouseDao.executeHql("delete Modehouse t where t.userId = :userId " +
                 					" and t.houseName = :houseName ", params);*/
                 			modehouseService.delete(t);
             			}        			
             			modehouse.setDescription(houseidElement.attributeValue("Description"));
             			modehouseDao.save(modehouse);
             		}
             		
             		Element nodeElement = root2.element("Node");  
                    Element nodeidElement = null;
            		for (Iterator i = nodeElement.element("Main").elementIterator("ID"); i.hasNext();) {
            			nodeidElement = (Element) i.next();
            			modenode= new Modenode();
            			modenode.setHouseId(modehouse.getId());
            			modenode.setOldId(Long.parseLong(nodeidElement.attributeValue("MID")));
            			modenode.setRoomId(Long.parseLong(nodeidElement.attributeValue("RoomID")));
            			modenode.setIeee(nodeidElement.attributeValue("IEEE"));
            			modenode.setDeviceUniqueId(nodeidElement.attributeValue("DeviceUniqueId"));
            			if (nodeidElement.attribute("ModeNodeLibId") == null) {
            				modenode.setModeNodeLibId(0);
            			} else {
            				modenode.setModeNodeLibId(Long.parseLong(nodeidElement.attributeValue("ModeNodeLibId")));
            			}
            			if (nodeidElement.attribute("Description") == null) {
//            				modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
            				modenode.setDeviceName(nodeidElement.attributeValue("MID")+":"+nodeidElement.attributeValue("IEEE"));
            			} else {
            				if (StringUtils.isBlank(nodeidElement.attributeValue("Description"))) {
//            					modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
                				modenode.setDeviceName(nodeidElement.attributeValue("MID")+":"+nodeidElement.attributeValue("IEEE"));
            				} else {
            					modenode.setDeviceName(nodeidElement.attributeValue("Description"));
            				}
            			}
            			modenodeDao.save(modenode);
            		}
        		}
        		Element roomElement = root.element("Room");  
                Element roomidElement = null;
        		for (Iterator i = roomElement.element("Main").elementIterator("ID"); i.hasNext();) {
        			roomidElement = (Element) i.next();
        			moderoom= new Moderoom();
        			moderoom.setUserId(userordermain.getUserId());
        			moderoom.setHouseId(modehouse.getId());
        			moderoom.setRoomId(Long.parseLong(roomidElement.attributeValue("RoomID")));
        			moderoom.setRoomName(roomidElement.attributeValue("RoomName"));
        			moderoom.setRoomPic(roomidElement.attributeValue("RoomPic"));
        			moderoomDao.save(moderoom);
        		}
        		
        		/*Element nodeElement = root.element("Node");  
                Element nodeidElement = null;
        		for (Iterator i = nodeElement.element("Main").elementIterator("ID"); i.hasNext();) {
        			nodeidElement = (Element) i.next();
        			modenode= new Modenode();
        			modenode.setHouseId(modehouse.getId());
        			modenode.setOldId(Long.parseLong(nodeidElement.attributeValue("MID")));
        			modenode.setRoomId(Long.parseLong(nodeidElement.attributeValue("RoomID")));
        			modenode.setIeee(nodeidElement.attributeValue("IEEE"));
        			modenode.setDeviceUniqueId(nodeidElement.attributeValue("DeviceUniqueId"));
        			if (nodeidElement.attribute("ModeNodeLibId") == null) {
        				modenode.setModeNodeLibId(0);
        			} else {
        				modenode.setModeNodeLibId(Long.parseLong(nodeidElement.attributeValue("ModeNodeLibId")));
        			}
        			if (nodeidElement.attribute("Description") == null) {
        				modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
        			} else {
        				if (StringUtils.isBlank(nodeidElement.attributeValue("Description"))) {
        					modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
        				} else {
        					modenode.setDeviceName(nodeidElement.attributeValue("Description"));
        				}
        			}
        			modenodeDao.save(modenode);
        		}*/
        		Element deviceElement = root.element("Device");  
                Element deviceidElement = null;
        		for (Iterator i = deviceElement.element("Main").elementIterator("ID"); i.hasNext();) {
        			deviceidElement = (Element) i.next();
        			modedevice = new Modedevice();
        			modedevice.setHouseId(modehouse.getId());
        			modedevice.setRoomId(Long.parseLong(deviceidElement.attributeValue("RoomID")));
        			modedevice.setDeviceUniqueId(deviceidElement.attributeValue("DeviceUniqueId"));
        			modedevice.setIeee(deviceidElement.attributeValue("IEEE"));
        			modedevice.setModelId(deviceidElement.attributeValue("ModelID"));
        			modedevice.setEp(deviceidElement.attributeValue("EP"));
        			modedevice.setDeviceId(deviceidElement.attributeValue("DeviceId"));
//        			modedevice.setOldId(Long.parseLong(deviceidElement.attributeValue("DeviceName")));
        			modedevice.setOldId(Long.parseLong(deviceidElement.attributeValue("SubID")));
        			modedevice.setModeNodeId(Long.parseLong(deviceidElement.attributeValue("MID")));
        			modedevice.setOldModeNodeId(Long.parseLong(deviceidElement.attributeValue("MID")));
        			if (deviceidElement.attribute("Description") == null) {
//        				modedevice.setDeviceName(deviceidElement.attributeValue("DeviceName"));
        				modedevice.setDeviceName(deviceidElement.attributeValue("MID") +":"+ deviceidElement.attributeValue("EP"));
        			} else {
        				if (StringUtils.isBlank(deviceidElement.attributeValue("Description"))) {
//            				modedevice.setDeviceName(deviceidElement.attributeValue("DeviceName"));
            				modedevice.setDeviceName(deviceidElement.attributeValue("MID") +":"+ deviceidElement.attributeValue("EP"));
        				} else {
            				modedevice.setDeviceName(deviceidElement.attributeValue("Description"));
        				}
        			}
        			if (StringUtils.isBlank(deviceidElement.attributeValue("DeviceId")) && StringUtils.isBlank(deviceidElement.attributeValue("Description"))) {
        				modedevice.setDeviceName(deviceidElement.attributeValue("Description") + ":"+deviceidElement.attributeValue("MID") +":"+ deviceidElement.attributeValue("EP"));
        			}  
        			//保存图片名称
        			modedevice.setPicName(StringUtils.isBlank(deviceidElement.attributeValue("Picname"))? "": deviceidElement.attributeValue("Picname"));
        			modedeviceDao.save(modedevice);
        		}  
				
				Element modedevicebindElement = root.element("DeviceBind");  
                Element modedevicebindElementidElement = null;
        		for (Iterator i = modedevicebindElement.element("Main").elementIterator("ID"); i.hasNext();) {
        			modedevicebindElementidElement = (Element) i.next();
        			modedevicebind = new Modedevicebind();
        			modedevicebind.setHouseId(modehouse.getId());
        			modedevicebind.setBindType(Short.parseShort(modedevicebindElementidElement.attributeValue("BindType")));
        			modedevicebind.setSourceId(Long.parseLong(modedevicebindElementidElement.attributeValue("Source")));
        			modedevicebind.setSourceOldId(Long.parseLong(modedevicebindElementidElement.attributeValue("Source")));
        			modedevicebind.setSourceDevicename(modedevicebindElementidElement.attributeValue("SourceDescription"));
        			modedevicebind.setSourceDeviceUniqueId(modedevicebindElementidElement.attributeValue("SourceDeviceUniqueId"));
        			modedevicebind.setSourceIeee(modedevicebindElementidElement.attributeValue("SourceIEEE"));
        			modedevicebind.setSourceVirtualEp(modedevicebindElementidElement.attributeValue("SourceEP"));
        			modedevicebind.setClusterId(modedevicebindElementidElement.attributeValue("ClusterID"));
        			if (StringUtils.isBlank(modedevicebindElementidElement.attributeValue("ClusterName"))) {
        				if (modedevicebindElementidElement.attributeValue("ClusterID").equals("0006")) {
                			modedevicebind.setClusterName("开关");
            			}
            			if (modedevicebindElementidElement.attributeValue("ClusterID").equals("0008")) {
                			modedevicebind.setClusterName("调级控制");
            			}
            			if (modedevicebindElementidElement.attributeValue("ClusterID").equals("0500")) {
                			modedevicebind.setClusterName("安防");
            			}
            			if (modedevicebindElementidElement.attributeValue("ClusterID").equals("0101")) {
                			modedevicebind.setClusterName("开关锁");
            			}
        			} else {
            			modedevicebind.setClusterName(modedevicebindElementidElement.attributeValue("ClusterName"));
        			}
//        			modedevicebind.setClusterName(modedevicebindElementidElement.attributeValue("ClusterName"));
        			modedevicebind.setDestType(modedevicebindElementidElement.attributeValue("DestType"));
        			modedevicebind.setDestId(Long.parseLong(modedevicebindElementidElement.attributeValue("Dest")));
        			modedevicebind.setDestOldId(Long.parseLong(modedevicebindElementidElement.attributeValue("Dest")));
        			modedevicebind.setDestName(modedevicebindElementidElement.attributeValue("DestDescription"));
        			modedevicebind.setDestIeee(modedevicebindElementidElement.attributeValue("DestIEEE"));
        			modedevicebind.setDestDeviceUniqueId(modedevicebindElementidElement.attributeValue("DestDeviceUniqueId"));
        			modedevicebind.setDestEp(modedevicebindElementidElement.attributeValue("DestEP"));
        			modedevicebind.setHasBind(Short.parseShort(modedevicebindElementidElement.attributeValue("HasBind")));        			
        			modedevicebindDao.save(modedevicebind);        			
        		} 
        		
        		/*Element devicePicSetElement = root.element("DevicePicSet");  
                Element devicePicSetElementidElement = null;
        		for (Iterator i = devicePicSetElement.element("Main").elementIterator("ID"); i.hasNext();) {
        			devicePicSetElementidElement = (Element) i.next();
        			devicepicsetlib = new Devicepicsetlib();
        			devicepicsetlib.setHouseId(modehouse.getId());
        			devicepicsetlib.setMid(Long.parseLong(devicePicSetElementidElement.attributeValue("ID")));
        			devicepicsetlib.setDeviceId(devicePicSetElementidElement.attributeValue("DeviceID"));
        			devicepicsetlib.setPicName(devicePicSetElementidElement.attributeValue("PicName"));
        			devicepicsetlibDao.save(devicepicsetlib);        			
        		}*/
        		
        		xmlutil.saveEntity(document, modehouse.getId(), 0,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 2,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 4,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 6,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 8,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 10,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 12,xmlVersion);
//        	} catch (Exception e) {
//        		e.printStackTrace();
//        		return -2;
//			}
        }
		
		return modehouse.getId();
	}
	
	/**
	 * 同步云端的情景模式(或本地上传导入模式编辑器))
	 * @author: zhuangxd
	 * 时间：2014-1-23 下午4:27:36
	 * @param userordermain
	 * @return
	 */
	@Override
	public long ImportZ203ModelAndNode(Userordermain userordermain) throws Exception {
		
		String xmlVersion = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Modehouse modehouse = new Modehouse();
		Moderoom moderoom= new Moderoom();
		Modenode modenode= new Modenode();
		Modedevice modedevice = new Modedevice();
		Modedevicebind modedevicebind = new Modedevicebind();
		String filePath = userordermain.getXmlFile();
		String filePathName = filePath;
		if (filePath.lastIndexOf("\\") != -1) {
			filePathName=filePath.substring(filePath.lastIndexOf("\\")+1);
		}
		String[] fileArr = filePath.split(";");
		if (filePath.contains(".ndd")) { // tar解包
			try {
				filePathName = filePath.substring(0,filePath.length() - 4) + "ndd";
//				filePathName = filePath.substring(0,filePath.length() - 4) + "ndd" + "_" +(new Date().getTime());
				fileArr = new String[3];
				List<String> list8 = GZIPUtil.readByZipInputStream(filePath, filePathName);
//				List<String> list8 = GZIPUtil.readByZipInputStream(filename3 +".ndd", filename3 +"ndd");

				if (list8 != null) {
					for (int i=0;i<list8.size();i++) {
						fileArr[i] = list8.get(i);
					}
				}
			} catch (Exception e) {
				
			}
		}
		// fileArr[0]为加密的mode文件   fileArr[1]为明文的node文件
		String filename = fileArr[0].substring(0,fileArr[0].length()-4);
        
        // 解密
        CharStream.reader(fileArr[0]);
		CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
		CharStream.writer(filename+"明文"+"-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date())+".xml");
		File file = new File(filename+"明文"+"-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date())+".xml");
		File file3 = new File(fileArr[1]);
		
		Element houseElement2 = null;
		Element root2 = null;
		Element root = null;
		Document document = null;
		
        if (file.exists()) {
        	SAXReader reader = new SAXReader();
        	// node文件读取
    		if (file3.exists()) {
            	SAXReader reader2 = new SAXReader();
//            	try {
            		Document document2 = reader2.read(file3);// 读取XML文件
            		root2 = document2.getRootElement();// 得到根节点
            		document = reader.read(file);// 读取XML文件 mode文件
            		root = document.getRootElement();// 得到根节点
            		houseElement2 = root2.element("House"); 
            		if (houseElement2 != null) { // 新版node文件包含House字段
	                    Element houseidElement = null;
	             		for (Iterator i = houseElement2.element("Main").elementIterator("ID"); i.hasNext();) {
	             			houseidElement = (Element) i.next();
	             			modehouse = new Modehouse();
	             			modehouse.setUserId(userordermain.getUserId());
	             			modehouse.setHouseIeee(houseidElement.attributeValue("IEEE"));
	             			modehouse.setHosueGuid(GUIDUtil.getGUID());
	             			modehouse.setCloudIp(houseidElement.attributeValue("CloudIP"));
	             			xmlVersion = houseidElement.attributeValue("XmlVersion");
	             			modehouse.setXmlVersion(xmlVersion);
	             			Element houseElement = root.element("House");  
	                        Element houseidElement2 = null;
	                 		for (Iterator k = houseElement.element("Main").elementIterator("ID"); k.hasNext();) {
	                 			houseidElement2 = (Element) k.next();
	                 			if (!houseidElement2.attributeValue("HouseID").equals(houseidElement.attributeValue("HouseID"))) {
	                 				return 0;
	                 			}
	                 		}
	             			modehouse.setHouseName(houseidElement.attributeValue("Name"));
	             			params = new HashMap<String, Object>();
	             			params.put("userId", modehouse.getUserId());
	             			params.put("houseName", modehouse.getHouseName());
	             			// 家不能同名
	             			Modehouse t = modehouseDao.get("from Modehouse t where t.userId = :userId " +
	             					" and t.houseName = :houseName ", params);
	             			if (t != null) {
//	             				oldHouseId = t.getId();
	//             				return -1;
	             				// 导入前，先删除家
	                 			/*modehouseDao.executeHql("delete Modehouse t where t.userId = :userId " +
	                 					" and t.houseName = :houseName ", params);*/
	                 			modehouseService.delete(t);
	             			}        			
	             			modehouse.setDescription(houseidElement.attributeValue("Description"));
	             			modehouseDao.save(modehouse);
	             		}
	             		
	             		Element nodeElement = root2.element("Node");  
	                    Element nodeidElement = null;
	            		for (Iterator i = nodeElement.element("Main").elementIterator("ID"); i.hasNext();) {
	            			nodeidElement = (Element) i.next();
	            			modenode= new Modenode();
	            			modenode.setHouseId(modehouse.getId());
	            			modenode.setOldId(Long.parseLong(nodeidElement.attributeValue("MID")));
	            			modenode.setRoomId(Long.parseLong(nodeidElement.attributeValue("RoomID")));
	            			modenode.setIeee(nodeidElement.attributeValue("IEEE"));
	            			modenode.setDeviceUniqueId(nodeidElement.attributeValue("DeviceUniqueId"));
	            			if (nodeidElement.attribute("ModeNodeLibId") == null) {
	            				modenode.setModeNodeLibId(0);
	            			} else {
	            				modenode.setModeNodeLibId(Long.parseLong(nodeidElement.attributeValue("ModeNodeLibId")));
	            			}
	            			if (nodeidElement.attribute("Description") == null) {
//	            				modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
	            				modenode.setDeviceName(nodeidElement.attributeValue("MID")+":"+nodeidElement.attributeValue("IEEE"));
	            			} else {
	            				if (StringUtils.isBlank(nodeidElement.attributeValue("Description"))) {
//	            					modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
	                				modenode.setDeviceName(nodeidElement.attributeValue("MID")+":"+nodeidElement.attributeValue("IEEE"));
	            				} else {
	            					modenode.setDeviceName(nodeidElement.attributeValue("Description"));
	            				}
	            			}
	            			modenodeDao.save(modenode);
	            		}
            		}
//            	} catch (Exception e) {
//            		e.printStackTrace();
//    			}
            }
    		//modeEpPage.xml
    		if(StringUtils.isNotBlank(fileArr[2])) {
	    		File file4 = new File(fileArr[2]);
	    		if(file4.exists()){//读取modeEpPage.xml内容保存到数据库
	    			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file4));
	    			SAXReader reader2 = new SAXReader();
	            	try {
	            		if(bis.read() != -1) {
	            			CharStream.reader(fileArr[2]);
	            			CharStream.EncryptionXmlCheck(CharStream.len, CharStream.xmlvalue);
	            			String epPageFileName = fileArr[2].substring(0,fileArr[2].length()-4) +"明文"+"-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date())+".xml"; 
	            			CharStream.writer(epPageFileName);
	            			file4 = new File(epPageFileName);
	            			
		            		Document modeEpDoc = reader2.read(file4);// 读取XML文件
		            		Element modeRoot = modeEpDoc.getRootElement();// 得到根节点
		            		Element smartPage = modeRoot.element("SmartPage");
		            		Element familyPage = modeRoot.element("FamilyPage");
		            		Element smartEp = smartPage.element("EP");
		            		Element smartMode = smartPage.element("Mode");
		            		Element familyEp = familyPage.element("EP");
		            		Element familyMode = familyPage.element("Mode");
		            		Element idElement = null;
		            		int ep_id=0;
		            		String irType="";
		            		String order="";
		            		String ieee="";
		            		String ep="";
		            		int oldId=0;
		            		long houseid = modehouse.getId();
		            		int modeID = 0;
		            		int oldIdm = 0;
		            		StringBuffer insSmartEp = new StringBuffer("INSERT INTO smarteppage(ep_id,IEEE,houseid,ep,IRType,OrderType,oldId) values");
		            		StringBuffer insSmartMode = new StringBuffer("INSERT INTO smartmodepage(ModeID,houseId,oldId) values");
		            		for (Iterator i = smartEp.elementIterator("ID"); i.hasNext();) {
		            			idElement = (Element) i.next();
		            			ep_id = Integer.parseInt(idElement.attributeValue("ep_id"));
		            			irType = idElement.attributeValue("IRTYPE");
		            			order = idElement.attributeValue("ORDER");
		            			ieee = idElement.attributeValue("IEEE");
		            			ep = idElement.attributeValue("EP");
		            			oldId = Integer.parseInt(idElement.attributeValue("ep_id"));
		            			insSmartEp.append("(").append(ep_id).append(",'").append(ieee).append("',").append(houseid).append(",'").append(ep).append("','").append(irType).append("','").append(order).append("','").append(oldId).append("'),");
		            		}
		            		for(Iterator i = smartMode.elementIterator("ID"); i.hasNext();) {
		            			idElement = (Element) i.next();
		            			modeID = Integer.parseInt(idElement.attributeValue("ModeID"));
		            			oldIdm = Integer.parseInt(idElement.attributeValue("ModeID"));
		            			insSmartMode.append("(").append(modeID).append(",").append(houseid).append(",").append(oldIdm).append("),");
		            		}
		            		
		            		StringBuffer insFamilyEp = new StringBuffer("INSERT INTO familyeppage(ep_id,IEEE,houseid,ep,IRType,OrderType,oldId) values");
		            		StringBuffer insFamilyMode = new StringBuffer("INSERT INTO familymodepage(ModeID,houseId,oldId) values");
		            		for (Iterator i = familyEp.elementIterator("ID"); i.hasNext();) {
		            			idElement = (Element) i.next();
		            			ep_id = Integer.parseInt(idElement.attributeValue("ep_id"));
		            			irType = idElement.attributeValue("IRTYPE");
		            			order = idElement.attributeValue("ORDER");
		            			ieee = idElement.attributeValue("IEEE");
		            			ep = idElement.attributeValue("EP");
		            			oldId = Integer.parseInt(idElement.attributeValue("ep_id"));
		            			insFamilyEp.append("(").append(ep_id).append(",'").append(ieee).append("',").append(houseid).append(",'").append(ep).append("','").append(irType).append("','").append(order).append("','").append(oldId).append("'),");
		            		}
		            		for (Iterator i = familyMode.elementIterator("ID"); i.hasNext();) {
		            			idElement = (Element) i.next();
		            			modeID = Integer.parseInt(idElement.attributeValue("ModeID"));
		            			oldIdm = Integer.parseInt(idElement.attributeValue("ModeID"));
		            			insFamilyMode.append("(").append(modeID).append(",").append(houseid).append(",").append(oldIdm).append("),");
		            		}
		            		insSmartEp.delete(insSmartEp.length()-1,insSmartEp.length());
		            		insFamilyEp.delete(insFamilyEp.length()-1,insFamilyEp.length());
		            		insSmartMode.delete(insSmartMode.length()-1,insSmartMode.length());
		            		insFamilyMode.delete(insFamilyMode.length()-1,insFamilyMode.length());
		            		if(smartEp.element("ID") != null || smartMode.element("ID") != null){
		            			logger.info("insert smart...");
		                		mapDao.executeSql2(insSmartEp.toString());
		                		mapDao.executeSql2(insSmartMode.toString());
		            		}
		            		if(familyEp.element("ID")!=null || familyMode.element("ID") != null){
		            			logger.info("insert family...");
		            			mapDao.executeSql2(insFamilyEp.toString());
		                		mapDao.executeSql2(insFamilyMode.toString());
		            		}
		            	}
		        	} catch(Exception e) {
		        		logger.info("modeeppage exception", e);
		        		throw e;
		        	} finally {
		        		try {
		        			bis.close();
		        		} catch(Exception e) {
		        			logger.info("modeeppage file close exception", e);
		        			throw e;
		        		}
		        	}
		           	}
	    		}
	        }
    		
    		// mode文件读取
//        	try {
        		if (houseElement2 == null) { // 旧版node文件不包含House字段
        			 Element houseElement = root.element("House");  
                     Element houseidElement = null;
             		for (Iterator i = houseElement.element("Main").elementIterator("ID"); i.hasNext();) {
             			houseidElement = (Element) i.next();
             			modehouse = new Modehouse();
             			modehouse.setUserId(userordermain.getUserId());
             			modehouse.setHouseIeee(houseidElement.attributeValue("IEEE"));
             			modehouse.setHosueGuid(houseidElement.attributeValue("HouseID"));
             			modehouse.setCloudIp(houseidElement.attributeValue("CloudIP"));
             			xmlVersion = houseidElement.attributeValue("XmlVersion");
             			modehouse.setXmlVersion(xmlVersion);
             			modehouse.setHouseName(houseidElement.attributeValue("Name"));
             			params = new HashMap<String, Object>();
             			params.put("userId", modehouse.getUserId());
             			params.put("houseName", modehouse.getHouseName());
             			// 家不能同名
             			Modehouse t = modehouseDao.get("from Modehouse t where t.userId = :userId " +
             					" and t.houseName = :houseName ", params);
             			if (t != null) {
//             				return -1;
             				// 导入前，先删除家
                 			/*modehouseDao.executeHql("delete Modehouse t where t.userId = :userId " +
                 					" and t.houseName = :houseName ", params);*/
                 			modehouseService.delete(t);
             			}        			
             			modehouse.setDescription(houseidElement.attributeValue("Description"));
             			modehouseDao.save(modehouse);
             		}
             		
             		Element nodeElement = root2.element("Node");  
                    Element nodeidElement = null;
            		for (Iterator i = nodeElement.element("Main").elementIterator("ID"); i.hasNext();) {
            			nodeidElement = (Element) i.next();
            			modenode= new Modenode();
            			modenode.setHouseId(modehouse.getId());
            			modenode.setOldId(Long.parseLong(nodeidElement.attributeValue("MID")));
            			modenode.setRoomId(Long.parseLong(nodeidElement.attributeValue("RoomID")));
            			modenode.setIeee(nodeidElement.attributeValue("IEEE"));
            			modenode.setDeviceUniqueId(nodeidElement.attributeValue("DeviceUniqueId"));
            			if (nodeidElement.attribute("ModeNodeLibId") == null) {
            				modenode.setModeNodeLibId(0);
            			} else {
            				modenode.setModeNodeLibId(Long.parseLong(nodeidElement.attributeValue("ModeNodeLibId")));
            			}
            			if (nodeidElement.attribute("Description") == null) {
//            				modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
            				modenode.setDeviceName(nodeidElement.attributeValue("MID")+":"+nodeidElement.attributeValue("IEEE"));
            			} else {
            				if (StringUtils.isBlank(nodeidElement.attributeValue("Description"))) {
//            					modenode.setDeviceName(nodeidElement.attributeValue("DeviceName"));
                				modenode.setDeviceName(nodeidElement.attributeValue("MID")+":"+nodeidElement.attributeValue("IEEE"));
            				} else {
            					modenode.setDeviceName(nodeidElement.attributeValue("Description"));
            				}
            			}
            			modenodeDao.save(modenode);
            		}
        		}
        		Element roomElement = root.element("Room");  
                Element roomidElement = null;
        		for (Iterator i = roomElement.element("Main").elementIterator("ID"); i.hasNext();) {
        			roomidElement = (Element) i.next();
        			moderoom= new Moderoom();
        			moderoom.setUserId(userordermain.getUserId());
        			moderoom.setHouseId(modehouse.getId());
        			moderoom.setRoomId(Long.parseLong(roomidElement.attributeValue("RoomID")));
        			moderoom.setRoomName(roomidElement.attributeValue("RoomName"));
        			moderoom.setRoomPic(roomidElement.attributeValue("RoomPic"));
        			moderoomDao.save(moderoom);
        		}
        		
        		Element deviceElement = root.element("Device");  
                Element deviceidElement = null;
        		for (Iterator i = deviceElement.element("Main").elementIterator("ID"); i.hasNext();) {
        			deviceidElement = (Element) i.next();
        			modedevice = new Modedevice();
        			modedevice.setHouseId(modehouse.getId());
        			modedevice.setRoomId(Long.parseLong(deviceidElement.attributeValue("RoomID")));
        			modedevice.setDeviceUniqueId(deviceidElement.attributeValue("DeviceUniqueId"));
        			modedevice.setIeee(deviceidElement.attributeValue("IEEE"));
        			modedevice.setModelId(deviceidElement.attributeValue("ModelID"));
        			modedevice.setEp(deviceidElement.attributeValue("EP"));
        			modedevice.setDeviceId(deviceidElement.attributeValue("DeviceId"));
//        			modedevice.setOldId(Long.parseLong(deviceidElement.attributeValue("DeviceName")));
        			modedevice.setOldId(Long.parseLong(deviceidElement.attributeValue("SubID")));
        			modedevice.setModeNodeId(Long.parseLong(deviceidElement.attributeValue("MID")));
        			modedevice.setOldModeNodeId(Long.parseLong(deviceidElement.attributeValue("MID")));
        			if (deviceidElement.attribute("Description") == null) {
//        				modedevice.setDeviceName(deviceidElement.attributeValue("DeviceName"));
        				modedevice.setDeviceName(deviceidElement.attributeValue("MID") +":"+ deviceidElement.attributeValue("EP"));
        			} else {
        				if (StringUtils.isBlank(deviceidElement.attributeValue("Description"))) {
//            				modedevice.setDeviceName(deviceidElement.attributeValue("DeviceName"));
            				modedevice.setDeviceName(deviceidElement.attributeValue("MID") +":"+ deviceidElement.attributeValue("EP"));
        				} else {
            				modedevice.setDeviceName(deviceidElement.attributeValue("Description"));
        				}
        			}
        			if (StringUtils.isBlank(deviceidElement.attributeValue("DeviceId")) && StringUtils.isBlank(deviceidElement.attributeValue("Description"))) {
        				modedevice.setDeviceName(deviceidElement.attributeValue("Description") + ":"+deviceidElement.attributeValue("MID") +":"+ deviceidElement.attributeValue("EP"));
        			}  
        			//保存图片名称
        			modedevice.setPicName(StringUtils.isBlank(deviceidElement.attributeValue("Picname"))? "": deviceidElement.attributeValue("Picname"));
        			modedeviceDao.save(modedevice);
        		}  
				
				Element modedevicebindElement = root.element("DeviceBind");  
                Element modedevicebindElementidElement = null;
        		for (Iterator i = modedevicebindElement.element("Main").elementIterator("ID"); i.hasNext();) {
        			modedevicebindElementidElement = (Element) i.next();
        			modedevicebind = new Modedevicebind();
        			modedevicebind.setHouseId(modehouse.getId());
        			modedevicebind.setBindType(Short.parseShort(modedevicebindElementidElement.attributeValue("BindType")));
        			modedevicebind.setSourceId(Long.parseLong(modedevicebindElementidElement.attributeValue("Source")));
        			modedevicebind.setSourceOldId(Long.parseLong(modedevicebindElementidElement.attributeValue("Source")));
        			modedevicebind.setSourceDevicename(modedevicebindElementidElement.attributeValue("SourceDescription"));
        			modedevicebind.setSourceDeviceUniqueId(modedevicebindElementidElement.attributeValue("SourceDeviceUniqueId"));
        			modedevicebind.setSourceIeee(modedevicebindElementidElement.attributeValue("SourceIEEE"));
        			modedevicebind.setSourceVirtualEp(modedevicebindElementidElement.attributeValue("SourceEP"));
        			modedevicebind.setClusterId(modedevicebindElementidElement.attributeValue("ClusterID"));
        			if (StringUtils.isBlank(modedevicebindElementidElement.attributeValue("ClusterName"))) {
        				if (modedevicebindElementidElement.attributeValue("ClusterID").equals("0006")) {
                			modedevicebind.setClusterName("开关");
            			}
            			if (modedevicebindElementidElement.attributeValue("ClusterID").equals("0008")) {
                			modedevicebind.setClusterName("调级控制");
            			}
            			if (modedevicebindElementidElement.attributeValue("ClusterID").equals("0500")) {
                			modedevicebind.setClusterName("安防");
            			}
            			if (modedevicebindElementidElement.attributeValue("ClusterID").equals("0101")) {
                			modedevicebind.setClusterName("开关锁");
            			}
        			} else {
            			modedevicebind.setClusterName(modedevicebindElementidElement.attributeValue("ClusterName"));
        			}
//        			modedevicebind.setClusterName(modedevicebindElementidElement.attributeValue("ClusterName"));
        			modedevicebind.setDestType(modedevicebindElementidElement.attributeValue("DestType"));
        			modedevicebind.setDestId(Long.parseLong(modedevicebindElementidElement.attributeValue("Dest")));
        			modedevicebind.setDestOldId(Long.parseLong(modedevicebindElementidElement.attributeValue("Dest")));
        			modedevicebind.setDestName(modedevicebindElementidElement.attributeValue("DestDescription"));
        			modedevicebind.setDestIeee(modedevicebindElementidElement.attributeValue("DestIEEE"));
        			modedevicebind.setDestDeviceUniqueId(modedevicebindElementidElement.attributeValue("DestDeviceUniqueId"));
        			modedevicebind.setDestEp(modedevicebindElementidElement.attributeValue("DestEP"));
        			modedevicebind.setHasBind(Short.parseShort(modedevicebindElementidElement.attributeValue("HasBind")));        			
        			modedevicebindDao.save(modedevicebind);        			
        		} 
        		
        		xmlutil.saveEntity(document, modehouse.getId(), 0,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 2,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 4,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 6,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 8,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 10,xmlVersion);
        		xmlutil.saveEntity(document, modehouse.getId(), 12,xmlVersion);
//        	} catch (Exception e) {
//        		e.printStackTrace();
//        		return -2;
//			}
//       }
        		
        		
        		
		return modehouse.getId();
	}
	
	/**
	 * 修改Z203导入模式编辑器后，新增的设备oldId赋值成id的值,新增的动作，oldId也赋值。导出模式编辑器时，保持新旧id不变。
	 * @author: zhuangxd
	 * 时间：2014-7-24 下午4:05:26
	 * @param houseId
	 * @return
	 */
	@Override
	public long updateCreateOrder(long houseId) {

		Map<String, Object> params = new HashMap<String, Object>();params.put("houseId", houseId);		     		

		modedeviceDao.executeSql2("update Modedevice t set t.oldId = t.id where " +
				" t.houseId = :houseId and t.oldId=0 ", params);
		modedevicebindDao.executeSql2("update Modedevice p,Modedevicebind t set t.sourceOldId = p.oldId " +
				" where t.houseId" +
				"= :houseId and p.id = t.sourceId and p.houseId = t.houseId and t.sourceOldId=0", params);
		modedevicebindDao.executeSql2("update Modedevice p,Modedevicebind t set t.destOldId = p.oldId" +
				" where t.houseId" +
				"= :houseId and p.id = t.destId and p.houseId = t.houseId and t.destOldId=0", params);
		
		modedevicebindDao.executeSql2("UPDATE Modegroupsub g INNER JOIN Modedevice d ON g.DeviceID=d.id " +
				" SET g.deviceOldId=d.oldId WHERE d.houseId= :houseId and g.deviceOldId=0 ", params);
		modedevicebindDao.executeSql2("UPDATE Modescenesub g INNER JOIN Modedevice d ON g.DeviceID=d.id " +
				" SET g.deviceOldId=d.oldId WHERE d.houseId= :houseId and g.deviceOldId=0 ", params);
		// 只更新a.DestType=0的值
		modedevicebindDao.executeSql2("UPDATE Modemacrosub a INNER JOIN Modedevice d ON a.Dest=d.id " +
				" SET a.destOldId=d.oldId WHERE d.houseId= :houseId and a.DestType=0 and a.destOldId=0 ", params);
		modedevicebindDao.executeSql2("UPDATE Modeiasmain m INNER JOIN Modedevice d ON m.ModeEpID=d.id " +
				" SET m.modeEpOldId=d.oldId WHERE d.houseId= :houseId and m.modeEpOldId=0 ", params);
		modedevicebindDao.executeSql2("UPDATE Modehvacmain m INNER JOIN Modedevice d ON m.ModeEpID=d.id " +
				" SET m.modeEpOldId=d.oldId WHERE d.houseId= :houseId and m.modeEpOldId=0 ", params);
		
		// 用电设备关联更新
		modedevicebindDao.executeSql2("update Usermodedevice a,Modedevice d set a.oldDestId=d.oldId" +
				" where d.houseId" +
				"= :houseId and a.Dest = d.id and a.oldDestId = 0 ", params);
		
		return 1;
	}
	
	@Override
	public long ImportFromLib2(long houseId) {

		Map<String, Object> params = new HashMap<String, Object>();params.put("houseId", houseId);		     		
	
		// 更新modeNodeId值，关联表 （该函数有配置事务，以下update执行不成功）
		modedeviceDao.executeSql2("update Modedevice t,Modenode p set t.modeNodeId = p.id where t.modeNodeId" +
						"= p.oldId and t.houseId = :houseId and p.houseId = t.houseId ", params);
		// 更新modeNodeLibId
		modedeviceDao.executeSql2("update Modedevice t1,Modenode p1,(Select t.id,Max(b.id) as MaxID from Modedevice t,Modenodelib b "+ 
		  "where  SubStr(t.modelId,1,Length(b.modelId))=b.modelId and t.houseId = :houseId group by t.id) mLIb set p1.modeNodeLibId =mLib.MaxID "+
		  "where t1.id=mLib.ID and p1.houseId = t1.houseId and p1.id=t1.modeNodeID",params);
		
		modedevicebindDao.executeSql2("update Modedevice p,Modedevicebind t set t.sourceId = p.id " +
				" where t.houseId" +
				"= :houseId and p.oldId = t.sourceId and p.houseId = t.houseId ", params);
		modedevicebindDao.executeSql2("update Modedevice p,Modedevicebind t set t.destId = p.id" +
				" where t.houseId" +
				"= :houseId and p.oldId = t.destId and p.houseId = t.houseId ", params);
		
		modedevicebindDao.executeSql2("update Modedevice p,Modedevicebind t set " +
				"t.sourceDevicename= concat(p.deviceName,concat(':',t.SourceVirtualEP)) where t.houseId" +
				"= :houseId and p.id = t.sourceId and p.houseId = t.houseId and t.sourceDevicename IS NULL", params);
		modedevicebindDao.executeSql2("update Modedevice p,Modedevicebind t set t.destName=p.deviceName" +
				" where t.houseId" +
				"= :houseId and p.id = t.destId and p.houseId = t.houseId and t.destName IS NULL", params);
		/*modedevicebindDao.executeSql2("update Modedevice p,Modedevicebind t, modeeplib m set t.sourceId = p.id, " +
				"t.sourceDevicename=p.deviceName,t.clusterName=m.clusterName where t.houseId" +
				"= :houseId and p.oldId = t.sourceId and p.houseId = t.houseId and t.clusterId=m.clusterId and t.bindType = 0", params);
		modedevicebindDao.executeSql2("update Modedevice p,Modedevicebind t, modeeplib m set t.destId = p.id,t.destName=p.deviceName,t.clusterName=m.clusterName" +
				" where t.houseId" +
				"= :houseId and p.oldId = t.destId and p.houseId = t.houseId and t.clusterId=m.clusterId and t.bindType = 0", params);*/
		
		//更新mode等其它表
		int count=xmlutil.getDao().executeHql("{CALL ModeimportTest(1,:houseId)}", params);//logger.info("houseId----"+houseId+"ModeimportTest1"+count);
		count=xmlutil.getDao().executeHql("{CALL ModeimportTest(3,:houseId)}", params);//logger.info("houseId----"+houseId+"ModeimportTest2"+count);
		count=xmlutil.getDao().executeHql("{CALL ModeimportTest(5,:houseId)}", params);//logger.info("houseId----"+houseId+"ModeimportTest3"+count);
		count=xmlutil.getDao().executeHql("{CALL ModeimportTest(6,:houseId)}", params);//logger.info("houseId----"+houseId+"ModeimportTest4"+count);
		modedevicebindDao.executeSql2("update Modedevice p,Usermodedevice t set t.Dest=p.id" +
				" where t.houseId" +
				"= :houseId and p.oldId = t.dest and p.houseId = t.houseId ", params);
		
		mapDao.executeSql2("update familyeppage f,modedevice m set f.ep_id = m.id where m.oldId = f.oldId and m.houseId=:houseId and m.houseId = f.houseid", params);
		mapDao.executeSql2("update smarteppage f,modedevice m set f.ep_id = m.id where m.oldId = f.oldId and m.houseId=:houseId and m.houseId = f.houseid", params);
		mapDao.executeSql2("update familymodepage f,usermodemain m set f.ModeID = m.id where m.oldId = f.oldId and m.houseId=:houseId and m.houseId = f.houseid", params);
		mapDao.executeSql2("update smartmodepage f,usermodemain m set f.ModeID = m.id where m.oldId = f.oldId and m.houseId=:houseId and m.houseId = f.houseid", params);
		return 1;
	}
	
	@Override
	public Userordermain findOrder(Userordermain userordermain) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Userordermain userordermain2 = new Userordermain();
		params.put("houseId", userordermain.getHouseId());
		hql = "from Userordermain t where t.houseId = :houseId ";
		List<Userordermain> list = userordermainDao.find(hql,params);
		if(list != null && list.size() > 0)
			userordermain2 = list.get(0);
		else  userordermain2 = null;
		
		return userordermain2;
	}
	
	/**
	 * 获取设备信息表
	 * @author: zhuangxd
	 * 时间：2014-2-26 下午4:32:27
	 * @param userordermain
	 * @return
	 */
	@Override
	public Map findNodeDeviceList(Userordermain userordermain) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", userordermain.getHouseId());
		hql = "select distinct new map(COALESCE(r.modelId,'') as modelId,COALESCE(s.deviceName,'') as nodeName,COALESCE(k.roomName,'') as roomName1,COALESCE(s.ieee,'') as ieee," +
				"COALESCE(t.ep,'') as ep,COALESCE(t.deviceName,'') as epName,COALESCE(j.roomName,'') as roomName2,COALESCE(r.powerType,'') as powerType" +
				",COALESCE(r.activationMethod,'') as activationMethod,COALESCE(r.resetDefault,'') as resetDefault,COALESCE(r.remark,'') as remark) from Modenode s," +
				"Moderoom k,Modenodelib r,Modedevice t,Modeeplib m,Moderoom j where s.roomId=k.roomId and s.houseId=k.houseId and" +
				" s.modeNodeLibId=r.id and s.id=t.modeNodeId and t.roomId=j.roomId and t.houseId=j.houseId" +
				" and s.houseId = :houseId order by r.modelId asc,s.id asc,t.ep asc ";
		List<Map> list = mapDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("modenode",list);	
		} else
			rt.put("modenode",new ArrayList<Map>());
		
		return rt;
	}
	
	/**
	 * 奈伯思标准情景模式列表userId=-1
	 * @author: zhuangxd
	 * 时间：2013-12-11 上午11:35:52
	 * @param userordermain
	 * @return
	 */
	@Override
	public List<Userordermain> findStandardModeList(Userordermain userordermain) {
		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userordermain.getUserId());
		if(userordermain.getUserType() == null)
			hql = "from Userordermain t where t.userId = :userId and t.houseId = -1";
		else {
			params.put("userType", userordermain.getUserType());
			hql = "from Userordermain t where t.userId = :userId and t.houseId = -1 and t.userType = :userType";
		}
		List<Userordermain> list = userordermainDao.find(hql,params);
		return list;
	}
	
	@Override
	public Map findModeNodeSumList(Userordermain userordermain) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", userordermain.getHouseId());
		hql = "select new map(n.deviceName as deviceName,count(t.modeNodeLibId) as num) from Modenodelib n,Modenode t " +
				"where n.id = t.modeNodeLibId " +
				"and t.houseId = :houseId " +
				"group by t.modeNodeLibId ";
		List<Map> list = mapDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("modenode",list);	
		} else
			rt.put("modenode",null);
		
		return rt;
	}
	
	@Override
	public Map findModeNodeList(Modenode modenode) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", modenode.getHouseId());
		params.put("roomId", modenode.getRoomId());
		hql = "from Modenode t where t.houseId = :houseId and t.roomId = :roomId";
		List<Modenode> list = modenodeDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("modenode",list);	
		} else
			rt.put("modenode",null);
		
		return rt;
	}
	
	@Override
	public Map findModeDeviceList(Userordermain userordermain) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("houseId", userordermain.getHouseId());
//		params.put("roomId", userordermain.getRoomId());
		hql = "from Userordermain t where t.houseId = :houseId and t.roomId = :roomId";
		List<Userordermain> list = userordermainDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("userordermain",list);	
		} else
			rt.put("userordermain",null);
		
		return rt;
	}
	
	@Override
	public Map findModeDeviceByNodeId(Modenode modenode) {

		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rt = new HashMap<String, Object>();
		params.put("modeNodeId", modenode.getId());
		hql = "from Userordermain t where t.modeNodeId = :modeNodeId";
		List<Userordermain> list = userordermainDao.find(hql,params);
		
		if(list != null && list.size() > 0) {
			rt.put("userordermain",list);	
		} else
			rt.put("userordermain",null);
		
		return rt;
	}
	
	@Override
	public int insertDeviceToRoom(Map map) {
    	
    	List<ModeNodeAndDevice2> list3 = new ArrayList<ModeNodeAndDevice2>();
    	list3 = (List<ModeNodeAndDevice2>)map.get("modeNodeAndDevice");
    	for(ModeNodeAndDevice2 ob1:list3){
    		Modenode modenode2 = new Modenode();
    		List<Userordermain> list2 = new ArrayList<Userordermain>();
    		modenode2 = ob1.getModenode();
    		modenodeDao.save(modenode2);
    		/*list2 = (List<Userordermain>)ob1.getUserordermain();
    		for(Userordermain ob2:list2){
    			ob2.setHouseId(modenode2.getHouseId());
    			ob2.setRoomId(modenode2.getRoomId());
    			ob2.setModeNodeId(modenode2.getId());
    			userordermainDao.save(ob2);
    		}*/
    	}
    	
		return 1;
	}
	
	@Override
	public int insertDeviceToRoom(ModeNodeAndDevice modeNodeAndDevice) {
    	
    	List<ModeNodeAndDevice2> list3 = new ArrayList<ModeNodeAndDevice2>();
    	list3 = modeNodeAndDevice.getModeNodeAndDevice();
    	for(ModeNodeAndDevice2 ob1:list3){
    		Modenode modenode2 = new Modenode();
    		List<Userordermain> list2 = new ArrayList<Userordermain>();
    		modenode2 = ob1.getModenode();
    		modenodeDao.save(modenode2);
    		/*list2 = (List<Userordermain>)ob1.getUserordermain();
    		for(Userordermain ob2:list2){
    			ob2.setHouseId(modenode2.getHouseId());
    			ob2.setRoomId(modenode2.getRoomId());
    			ob2.setModeNodeId(modenode2.getId());
    			userordermainDao.save(ob2);
    		}*/
    	}
    	
		return 1;
	}
	
	@Override
	public Userordermain update(Userordermain userordermain) {
		userordermainDao.update(userordermain);
		return userordermain;
	}
	
	@Override
	public Userordermain get(Userordermain userordermain) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", userordermain.getId());
		Userordermain t = userordermainDao.get("from Userordermain t where t.id = :id ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Userordermain find(Userordermain userordermain) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userName", userordermain.getUserName());
//		params.put("pwd", userordermain.getPwd());
		Userordermain t = userordermainDao.get("from Userordermain t where t.userName = :userName and t.pwd = :pwd and t.enabled = '1' ", params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Userordermain> findList(Userordermain userordermain) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Userordermain t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (userordermain.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", userordermain.getHouseIeee());
		}
		if (userordermain.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + userordermain.getName() + "%");
		}
		if (userordermain.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", userordermain.getSecretKey());
		}
		if (userordermain.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", userordermain.getVendorCode());
		}
		if (userordermain.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + userordermain.getVendorName() + "%");
		}
		if (userordermain.getEncodemethod() != null) {
			hql.append("and t.encodemethod = :encodemethod ");
			params.put("encodemethod", userordermain.getEncodemethod());
		}
		if (userordermain.getXmppIp() != null) {
			hql.append("and t.xmppIp = :xmppIp ");
			params.put("xmppIp", userordermain.getXmppIp());
		}
		if (userordermain.getXmppPort() != null) {
			hql.append("and t.xmppPort = :xmppPort ");
			params.put("xmppPort", userordermain.getXmppPort());
		}
		if (userordermain.getCloudIp1() != null) {
			hql.append("and t.cloudIp1 = :cloudIp1 ");
			params.put("cloudIp1", userordermain.getCloudIp1());
		}
		if (userordermain.getCloudPort1() != null) {
			hql.append("and t.cloudPort1 = :cloudPort1 ");
			params.put("cloudPort1", userordermain.getCloudPort1());
		}
		if (userordermain.getCloudIp2() != null) {
			hql.append("and t.cloudIp2 = :cloudIp2 ");
			params.put("cloudIp2", userordermain.getCloudIp2());
		}
		if (userordermain.getCloudPort2() != null) {
			hql.append("and t.cloudPort2 = :cloudPort2 ");
			params.put("cloudPort2", userordermain.getCloudPort2());
		}
		if (userordermain.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", userordermain.getEnableFlag());
		}*/
		List<Userordermain> t = userordermainDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public List<Object[]> findListSql(Userordermain userordermain) {
		StringBuffer sql = new StringBuffer();
		int year = Calendar.getInstance().get(Calendar.YEAR);
//		String tableName = "deviceWarnHistory_" + userordermain.getHouseIeee() + "_" + year;
//		sql.append("select {s.*}, {e.*} from Userordermain s,").append(tableName).append(" e where 1=1 and s.houseIeee = e.houseIEEE ");
		Map<String, Object> params = new HashMap<String, Object>();
		/*if (userordermain.getHouseIeee() != null) {
			sql.append("and s.houseIeee = :houseIeee ");
			params.put("houseIeee", userordermain.getHouseIeee());
		}
		if (userordermain.getName() != null) {
			sql.append("and s.name like :name ");
			params.put("name", "%" + userordermain.getName() + "%");
		}*/
		List<Object[]> t = userordermainDao.findSql(sql.toString(), params, Userordermain.class, DevicewarnhistoryHouseidYear.class);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public int deleteModeNode(Modenode modenode) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Modenode t where t.id = :id ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", modenode.getId());
		modenodeDao.executeHql(hql.toString(), params);
		userordermainDao.executeHql("delete Userordermain t where t.modeNodeId = :id ", params);		
		return 1;
	}
	
	@Override
	public int delete(Userordermain userordermain) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete Userordermain t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		if (userordermain.getId() != 0) {
			hql.append("and t.id = :id ");
			params.put("id", userordermain.getId());
		}
		/*if (userordermain.getHouseIeee() != null) {
			hql.append("and t.houseIeee = :houseIeee ");
			params.put("houseIeee", userordermain.getHouseIeee());
			params2.put("houseIeee", userordermain.getHouseIeee());
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String tableName = "deviceAttributeHistory_" + userordermain.getHouseIeee() + "_" + year;
			tableName = "deviceOperateHistory_" + userordermain.getHouseIeee() + "_" + year;
			tableName = "deviceWarnHistory_" + userordermain.getHouseIeee() + "_" + year;
		}*/
		/*if (userordermain.getName() != null) {
			hql.append("and t.name like :name ");
			params.put("name", "%" + userordermain.getName() + "%");
		}
		if (userordermain.getSecretKey() != null) {
			hql.append("and t.secretKey = :secretKey ");
			params.put("secretKey", userordermain.getSecretKey());
		}
		if (userordermain.getVendorCode() != null) {
			hql.append("and t.vendorCode = :vendorCode ");
			params.put("vendorCode", userordermain.getVendorCode());
		}
		if (userordermain.getVendorName() != null) {
			hql.append("and t.vendorName like :vendorName ");
			params.put("vendorName", "%" + userordermain.getVendorName() + "%");
		}
		if (userordermain.getEnableFlag() != null) {
			hql.append("and t.enableFlag = :enableFlag ");
			params.put("enableFlag", userordermain.getEnableFlag());
		}		*/
		return userordermainDao.executeHql(hql.toString(), params);
	}

	@Override
	public Userordermain login(Userordermain userordermain) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("pwd", Encrypt.e(userordermain.getPwd()));
//		params.put("name", userordermain.getName());
		Userordermain t = userordermainDao.get("from Userordermain t where t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			return userordermain;
		}
		return null;
	}

/*	@Override
	public DataGrid datagrid(Userordermain userordermain) {
		DataGrid dg = new DataGrid();
		String hql = "from Userordermain t ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(userordermain, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(userordermain, hql);
		List<Userordermain> l = userordermainDao.find(hql, params, userordermain.getPage(), userordermain.getRows());
		List<Userordermain> nl = new ArrayList<Userordermain>();
		changeModel(l, nl);
		dg.setTotal(userordermainDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}*/

	private void changeModel(List<Userordermain> l, List<Userordermain> nl) {
		if (l != null && l.size() > 0) {
			for (Userordermain t : l) {
				Userordermain u = new Userordermain();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	private String addOrder(Userordermain userordermain, String hql) {
		/*if (userordermain.getSort() != null) {
			hql += " order by " + userordermain.getSort() + " " + userordermain.getOrder();
		}*/
		return hql;
	}

	private String addWhere(Userordermain userordermain, String hql, Map<String, Object> params) {
		/*if (userordermain.getName() != null && !userordermain.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + userordermain.getName().trim() + "%%");
		}*/
		return hql;
	}

	@Override
	public void remove(String ids) {		
		String[] nids = ids.split(",");
		String hql = "delete Userordermain t where t.id in (";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		userordermainDao.executeHql(hql);
	}

	@Override
	public void test() {
		PropertyConfigurator.configure("D:/log4j.properties");
		logger.info("@@@@@@@@########");
	}
	
	@Override
	public String findXmlPath(long userId, long houseId) throws Exception {
		StringBuilder hql = new StringBuilder("select new map(a.xmlFile as xmlFile) from Userordermain a where ");
		hql.append("a.userId=").append(userId).append(" and a.houseId=").append(houseId);
		List<Map> xmlList = mapDao.find(hql.toString());
		if(xmlList.isEmpty())
			return null;
		else
			return (String) xmlList.get(0).get("xmlFile");
	}
	
	@Override
	public ModeSchcUser getSCHCUser(long userId, String houseIEEE) throws Exception {
		String hql = "from ModeSchcUser where userId = :userId and houseIEEE = :houseIEEE";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("houseIEEE", houseIEEE);
		List<ModeSchcUser> ms = modesuDao.find(hql, param);
		if(ms.isEmpty())
			return null;
		else
			return ms.get(0);
	}
	
	@Override
	public void saveSCHCUser(ModeSchcUser modesu) throws Exception {
		modesuDao.save(modesu);
	}
	
	@Override
	public void updateSCHCUser(ModeSchcUser modesu) throws Exception {
		String sql = "update modeschcuser set schcUserName=:schcUserName,schcPassword=:schcPassword where userId = :userId and houseIEEE = :houseIEEE";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schcUserName", modesu.getSchcUserName());
		param.put("schcPassword", modesu.getSchcPassword());
		param.put("userId", modesu.getUserId());
		param.put("houseIEEE", modesu.getHouseIeee());
		modesuDao.executeSql2(sql, param);
	}
	
	@Override
	public void deleteSCHCUser(ModeSchcUser modesu) throws Exception {
		String sql = "delete from modeschcuser where userId = :userId and houseIEEE = :houseIEEE";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", modesu.getUserId());
		param.put("houseIEEE", modesu.getHouseIeee());
		modesuDao.executeSql2(sql, param);
	}
}
