package sy.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.IRBrand;
import sy.model.IRBranddictionary;
import sy.model.IRControlltype;
import sy.model.IRData;
import sy.model.IRModel;
import sy.model.IRTempData;
import sy.service.IRServiceI;
import sy.util.PropertiesUtil;

/**
 * @author huanglw
 *
 */
@Service("IRService")
public class IRServiceImpl implements IRServiceI {
	private static final Logger log = Logger.getLogger(IRServiceImpl.class);
	private BaseDaoI<Map> mapDao;
	private BaseDaoI<IRData> irDataDao;
	private BaseDaoI<IRBrand> irBrandDao;
	private BaseDaoI<IRBranddictionary> irBranddictionaryDao;
	private BaseDaoI<IRControlltype> irControllTypeDao;
	private BaseDaoI<IRModel> irModelDao;
	private BaseDaoI<IRTempData> irTempDataDao;
	//批量插入sql字符串
	private StringBuilder sql = new StringBuilder(3000);
	//批量计数器
	private int batchCount = 0;
	//批量数量
	private int cacheSize = Integer.parseInt(PropertiesUtil.getProperty("batch.IR"));
	//IR匹配返回数量
	private double returnLevel = Double.parseDouble(PropertiesUtil.getProperty("ir.returnLevel"));

	/* (non-Javadoc)
	 * @see sy.service.IRServiceI#getIRBrandModels(java.lang.String)
	 */
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<IRData> getIrDataDao() {
		return irDataDao;
	}
	
	@Autowired
	public void setIrDataDao(BaseDaoI<IRData> irDataDao) {
		this.irDataDao = irDataDao;
	}

	public BaseDaoI<IRBrand> getIrBrandDao() {
		return irBrandDao;
	}

	@Autowired
	public void setIrBrandDao(BaseDaoI<IRBrand> irBrandDao) {
		this.irBrandDao = irBrandDao;
	}

	public BaseDaoI<IRModel> getIrModelDao() {
		return irModelDao;
	}
	
	@Autowired
	public void setIrModelDao(BaseDaoI<IRModel> irModelDao) {
		this.irModelDao = irModelDao;
	}

	public BaseDaoI<IRControlltype> getIrControllTypeDao() {
		return irControllTypeDao;
	}
	@Autowired
	public void setIrControllTypeDao(BaseDaoI<IRControlltype> irControllTypeDao) {
		this.irControllTypeDao = irControllTypeDao;
	}
	
	public BaseDaoI<IRTempData> getIrTempDataDao() {
		return irTempDataDao;
	}
	
	@Autowired
	public void setIrTempDataDao(BaseDaoI<IRTempData> irTempDataDao) {
		this.irTempDataDao = irTempDataDao;
	}

	public static Logger getLog() {
		return log;
	}
	
	public double getMatchLevel() {
		return returnLevel;
	}
	public BaseDaoI<IRBranddictionary> getIrBranddictionaryDao() {
		return irBranddictionaryDao;
	}
	@Autowired
	public void setIrBranddictionaryDao(
			BaseDaoI<IRBranddictionary> irBranddictionaryDao) {
		this.irBranddictionaryDao = irBranddictionaryDao;
	}
	
	@Override
	public List<Map> abtainIRBrandModels(String IRData, Integer irType, String houseIeeeSecret) throws Exception {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(IRData) || irType == null)
			return null;
		String sql = "{CALL IRDataSumBrandStyle(:irType,:IRData)}";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("irType", irType);
		paramMap.put("IRData", IRData);
		List<Map> matchList = mapDao.executeSql(sql, paramMap);
		//将匹配记录保存到记录表中
		if(!matchList.isEmpty()) {
			StringBuilder sqlM = new StringBuilder("insert into irmatchrec(houseIEEE,brandName,modelName," +
					"controllType,checked,enbrandName,matchPercent) values");
			for(Map rMap : matchList) {
				sqlM.append("(").append("'"+houseIeeeSecret+"',").append(rMap.get("brandName") == null? null : "'" + rMap.get("brandName") + "',")
				.append(rMap.get("modelName") == null? null : "'" + rMap.get("modelName") + "',")
				.append(rMap.get("controllType")).append(",")
				.append(rMap.get("checked")).append(",")
				.append(rMap.get("enbrandName") == null? null : "'" + rMap.get("enbrandName") + "',")
				.append(rMap.get("matchPercent")).append("),");
			}
			String sqlE = sqlM.deleteCharAt(sqlM.length() - 1).toString();
			mapDao.executeSql2(sqlE);
		}
		
		return matchList;
//		String encodeFlag = IRData.substring(0, 2);
//		String remainData = IRData.substring(2);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("data", IRData);
////		StringBuilder cSql = new StringBuilder("select count(*) stageCount from IRTemp where data='");
//		StringBuilder cSql = new StringBuilder("select count(*) stageCount from IRTemp where data=:data");
////		cSql.append(IRData).append("'");
//		List<Map> cList = mapDao.executeSql(cSql.toString(), params);
//		DecimalFormat formater = new DecimalFormat("#0.##");
//		formater.setRoundingMode(RoundingMode.FLOOR);
//		int stageCount = ((BigInteger)((Map)cList.get(0)).get("stageCount")).intValue();//段数
//		String irIndTblName = "irindexdata_" + encodeFlag;
//		if(isTableExist(irIndTblName) == 0)
//			return null;
//		if(encodeFlag.equalsIgnoreCase("FF")) { //未解码
//			//解析IR数据，比如FFFF0001FF0002FE1F					 
//			if(stageCount == 0) {//若不存在则解析插入数据库中				
//				StringBuilder sql = new StringBuilder("insert into IRTemp(seq,pulseWidth,data) values");				
//				int charCount = remainData.length();
//				int index = 0;
//				while(index < charCount) {					
//					if((index + 2) > charCount)
//						break;
//					String stageType = remainData.substring(index, index + 2);
//					if(stageType.equals("FF")) {
//						String data = remainData.substring(index + 2, index + 6);
//						index += 6;
//						int dataInt = Integer.parseInt(data,16);
//						stageCount++;
//						sql.append("(").append(stageCount).append(",").append(dataInt).append(",'").append(IRData).append("'),");
//					}
//					else {
//						index += 2;
//						int dataInt = Integer.parseInt(stageType, 16);
//						stageCount++;
//						sql.append("(").append(stageCount).append(",").append(dataInt).append(",'").append(IRData).append("'),");
//					}
//				}
//				sql = sql.deleteCharAt(sql.length() - 1);
//				mapDao.executeSql2(sql.toString());
//			}
//			//查询IR品牌及型号列表
//			StringBuilder qSql = new StringBuilder();
////			qSql.append("select c.modelName,d.brandName,d.enbrandName,c.checked,a.data,");
////			qSql.append("(select count(*) from IRTemp z "); 
////			qSql.append("inner join ").append(irIndTblName).append(" y on z.seq = y.seq ");
////			qSql.append("where z.pulseWidth BETWEEN y.pulseWidthMin and y.pulseWidthMax AND ");
////			qSql.append("y.dataId = a.id and z.data = '").append(IRData).append("') matchCount ");
////			qSql.append("from IRData a ");
////			qSql.append("left join IRModel c on a.modelId = c.id ");
////			qSql.append("left join IRBrand d on c.brandId = d.id ");
////			qSql.append("where a.decodeType='").append(encodeFlag).append("'");
////			qSql.append("order by matchCount desc "); 
////			qSql.append("limit 3;");
//			
//			qSql.append("select	e.modelName,e.brandName,e.enbrandName,e.checked,e.matchCount,g.data ");
//			qSql.append("from ");
//			qSql.append("(select d.id brandId,c.id modelId,c.modelName,d.brandName,d.enbrandName,c.checked,");
//			qSql.append("max((select count(*) from IRTemp z  ");
//			qSql.append("inner join ").append(irIndTblName).append(" y on z.seq = y.seq "); 
//			qSql.append("where z.pulseWidth BETWEEN y.pulseWidthMin and y.pulseWidthMax AND "); 
//			qSql.append("y.dataId = a.id and z.data = '").append(IRData).append("')) matchCount ");
//			qSql.append("from (select h.id,h.modelId from IRData h where h.decodeType='").append(encodeFlag).append("') a ");
//			qSql.append("left join IRModel c on a.modelId = c.id "); 
//			qSql.append("left join IRBrand d on c.brandId = d.id ");
//			qSql.append("where c.controllType=").append(irType).append(" ");
//			qSql.append("group by brandId,modelId ");
//			qSql.append("order by matchCount desc ");
//			qSql.append("limit ").append(returnMatchCount);
//			qSql.append(") e ");
//			qSql.append("left JOIN ");
//			qSql.append("(select f.modelId,f.data from irdata f where f.CommdSequence=1) g ");
//			qSql.append("on e.modelId = g.modelId ");			
//			List<Map> qList = mapDao.executeSql(qSql.toString());
//			List<Map> rList = new ArrayList<Map>(qList.size());
//			for(Map map : qList) {
//				BigInteger mCount = (BigInteger) map.get("matchCount");
//				double matchPercentD = mCount.doubleValue() / Double.valueOf(stageCount);
//				if(matchPercentD >= returnLevel) {
//					String matchPercent = formater.format(matchPercentD * 100.0) + "%";
//					map.put("matchPercent", matchPercent);
//					rList.add(map);
//				}
//			}
//			return rList;
//		} 
//		else { //已解码的匹配
//			if(stageCount == 0) {//若不存在则解析插入数据库中
//				StringBuilder sql = new StringBuilder("insert into IRTemp(seq,pulseWidth,data) values");				
//				int charCount = remainData.length();
//				int index = 0;
//				while(index < charCount) {
//					String byteStr = remainData.substring(index, index + 2);
//					int byteInt = Integer.parseInt(byteStr, 16);
//					index += 2;
//					stageCount += 1;
//					sql.append("(").append(stageCount).append(",").append(byteInt).append(",'").append(IRData).append("'),");
//				}
//				sql = sql.deleteCharAt(sql.length() - 1);
//				mapDao.executeSql2(sql.toString());	
//			}
//			//查询IR品牌及型号列表
//			StringBuilder qSql = new StringBuilder();
//			qSql.append("select	e.modelName,e.brandName,e.enbrandName,e.checked,e.matchCount,g.data ");
//			qSql.append("from ");
//			qSql.append("(select d.id brandId,c.id modelId,c.modelName,d.brandName,d.enbrandName,c.checked,");
//			qSql.append("max((select count(*) from IRTemp z "); 
//			qSql.append("inner join ").append(irIndTblName).append(" y on z.pulseWidth = y.bandwidth ");
//			qSql.append("where y.dataId = a.id and z.seq = y.seq and z.data='").append(IRData).append("')) matchCount ");
//			qSql.append("from (select h.id,h.modelId from IRData h where h.decodeType='").append(encodeFlag).append("') a ");
////			qSql.append("from IRData a ");
//			qSql.append("left join IRModel c on a.modelId = c.id ");
//			qSql.append("left join IRBrand d on c.brandId = d.id ");
//			qSql.append("where c.controllType=").append(irType).append(" ");
//			qSql.append("group by brandId,modelId ");
//			qSql.append("order by matchCount desc "); 
//			qSql.append("limit ").append(returnMatchCount).append(") e ");
//			qSql.append("left JOIN ");
//			qSql.append("(select f.modelId,f.data from irdata f where f.CommdSequence=1) g ");
//			qSql.append("on e.modelId = g.modelId ");
//			List<Map> qList = mapDao.executeSql(qSql.toString());
//			List<Map> rList = new ArrayList<Map>(qList.size());
//			for(Map map : qList) {
//				BigInteger mCount = (BigInteger) map.get("matchCount");
//				double matchPercentD = mCount.doubleValue() / Double.valueOf(stageCount);
//				if(matchPercentD >= returnLevel) {
//					String matchPercent = formater.format(matchPercentD * 100.0) + "%";
//					map.put("matchPercent", matchPercent);
//					rList.add(map);
//				}
//			}
//			return rList;
//		}
	}

	@Override
	public List<Map> abtainBrand(Integer irType, String brandName) throws Exception {
		StringBuilder sql = new StringBuilder("select distinct a.id,a.brandName,a.enbrandName from irbrand a left join irmodel b ");
		sql.append("on a.id = b.brandId ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(irType != null && StringUtils.isNotBlank(brandName)) {
			sql.append("where b.controllType=:controllType and a.brandName like :brandName ");
//			sql.append("where b.controllType=").append(irType).append(" and ");
//			sql.append("a.brandName like '%").append(brandName).append("%'");
			params.put("controllType", irType);
			params.put("brandName", "%" + brandName + "%");
		}
		else if(irType != null) {
//			sql.append("where b.controllType=").append(irType);
			sql.append("where b.controllType=:controllType");
			params.put("controllType", irType);
		}
		else if(StringUtils.isNotBlank(brandName)){
//			sql.append("where a.brandName like '%").append(brandName).append("%'");
			sql.append("where a.brandName like :brandName");
			params.put("brandName", "%" + brandName + "%");
		}
		
		
		List<Map> brandList = mapDao.executeSql(sql.toString(), params);
		return brandList;
	}
	
	@Override
	public List<Map> abtainModel(Integer brandId, String brandName) throws Exception {
		StringBuilder sql = new StringBuilder("select a.id,a.modelName,a.checked from irmodel a left join irbrand b ");
		sql.append("on a.brandId = b.id ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(brandId != null) {
//			sql.append("where b.id=").append(brandId);
			sql.append("where b.id=:brandId");
			params.put("brandId", brandId);
		}
		else if(StringUtils.isNotBlank(brandName)) {
//			sql.append("where b.brandName like '%").append(brandName).append("%'");
			sql.append("where b.brandName like :brandName");
			params.put("brandName", "%" + brandName + "%");
		}
		List<Map> modelList = mapDao.executeSql(sql.toString(), params);
		return modelList;
	}
	
	/**
	 * 根据IR列表生成xml文档
	 * @param irList
	 * @param brandName
	 * @param modelName
	 * @return
	 */
	private Document createXmlDoc(List<Map> irList, String brandName, String modelName) {
		//组装xml文件
		if(!irList.isEmpty()) {			
			//DocumentHelper提供了创建Document对象的方法   
			Document rootElement = DocumentHelper.createDocument();  
			// 设置编码
			rootElement.setXMLEncoding("UTF-8"); 
			//添加节点信息
			Element mainRecord = rootElement.addElement("MainRecord"); 
			String brandId = irList.get(0).get("brandId").toString();
			Long modelId = ((BigInteger) irList.get(0).get("modelId")).longValue();
			mainRecord.addAttribute("mid", "" + modelId);
			mainRecord.addAttribute("ALLCOUNT", "" + irList.size());
			Element style = mainRecord.addElement("Style");
			String brandNameEN = irList.get(0).get("enbrandName") == null? "":(String) irList.get(0).get("enbrandName");
			String region = irList.get(0).get("region") == null? "":(String) irList.get(0).get("region");
			String firstchar = irList.get(0).get("firstchar") == null? "":(String) irList.get(0).get("firstchar");
	//		String deviceStyle = (String) ((Map) irList.get(0)).get("deviceStyle");
	//		String remoteControlStyle = (String) ((Map) irList.get(0)).get("remoteControlStyle");
			style.setText(modelName);
			Element brand = mainRecord.addElement("Brand");
			brand.setText(brandName);
			Element brandEN = mainRecord.addElement("BrandEN");
			brandEN.setText(brandNameEN);
			Element unitCode = mainRecord.addElement("UnitCode");
			unitCode.setText(region);
			Element nameIndex = mainRecord.addElement("NameIndex");
			nameIndex.setText(firstchar);
			Element deviceStyleEle = mainRecord.addElement("DeviceStyle");
	//		deviceStyleEle.setText(deviceStyle);
			Element remoteControlStyleEle = mainRecord.addElement("RemoteControlStyle");
	//		remoteControlStyleEle.setText(remoteControlStyle);
			Element deviceType = mainRecord.addElement("DeviceType");
			Integer cType = (Integer) irList.get(0).get("controllType");
			deviceType.setText("" + cType);
			int irSize = irList.size();
			for(int i = 0; i < irSize; i++) {
				Map map = irList.get(i);
				String modelNameTmp = irList.get(i).get("modelName") == null? "":(String) irList.get(i).get("modelName");
				String brandNameTmp = irList.get(i).get("brandName") == null? "":(String) irList.get(i).get("brandName");
				String brandNameENTmp = irList.get(i).get("enbrandName") == null? "":(String) irList.get(i).get("enbrandName");
				Element childRecord = mainRecord.addElement("ChildRecord");
				Element irData = childRecord.addElement("IRData");
				irData.setText( map.get("data") == null? "":(String) map.get("data"));
				Element id = childRecord.addElement("ID");
				id.setText(((BigInteger) map.get("id")).toString());
				Element iRCommdid = childRecord.addElement("IRCommdid");
				//iRCommdid.setText((i + 1) + "");
				iRCommdid.setText(map.get("IRCommdid") == null? "" : map.get("IRCommdid").toString());
				Element commdSequence = childRecord.addElement("CommdSequence");
				//commdSequence.setText((i + 1) + "");
				commdSequence.setText( map.get("CommdSequence") == null? "" : map.get("CommdSequence").toString());
				Element functionName = childRecord.addElement("FunctionName");
				functionName.setText(map.get("actionName") == null? "":(String) map.get("actionName"));
				Element functionDescription  = childRecord.addElement("FunctionDescription ");
				functionDescription.setText(map.get("actionNameDescription") == null? "":(String) map.get("actionNameDescription"));
				Element brandStyle = childRecord.addElement("BrandStyle");
				brandStyle.setText(brandNameTmp + brandNameENTmp + modelNameTmp);
			}
			return rootElement;
		}
		else
			return null;
	}
	
	/**
	 * IR库下载
	 * @param brandNamet
	 * @param modelNamet
	 * @return
	 */
	@Override
	public String IRdown(Long modelId, HttpServletRequest request,HttpServletResponse  response) throws Exception{
		//查询IR数据
		StringBuilder sql = new StringBuilder("select c.id brandId,c.brandName,c.enbrandName,c.region,c.firstchar,b.id modelId,b.modelName,b.controllType,a.id,a.data,a.actionName,a.IRCommdid,a.CommdSequence,a.actionNameDescription from irdata a ");
		sql.append("left join irmodel b on a.modelId = b.id ");
		sql.append("left join irbrand c on b.brandId = c.id ");
		sql.append("where b.id=:modelId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modelId", modelId);
		
		List<Map> irList = mapDao.executeSql(sql.toString(), params);
		if(!irList.isEmpty()) {
			String modelName = irList.get(0).get("modelName") == null? "":(String) irList.get(0).get("modelName"); 
			String brandName = irList.get(0).get("brandName") == null? "":(String) irList.get(0).get("brandName");
			Document rootElement = this.createXmlDoc(irList, brandName, modelName);
			if(rootElement != null) {
				BufferedOutputStream os = null;
				XMLWriter xmlWriter = null;
				try {
					String fileName = "ir_" + brandName + "_" + modelName + "_" + (new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date()) + ".xml";
					fileName=fileName.replaceAll(" ", "");
					//对中文字符转码
					//fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1" );
					fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
					//备注：这样浏览器会提示保存还是打开，即使选择打开，也会使用相关联的程序比如记事本打开，而不是IE直接打开了。
					response.addHeader("Content-Disposition", "attachment;filename="+fileName);
//						response.setContentType("test/xml;charset=utf-8");
					
					response.setContentType("application/x-msdownload;charset=utf-8");
					os = new BufferedOutputStream(response.getOutputStream());
					OutputFormat of = OutputFormat.createPrettyPrint();  
					// 设置编码
					of.setEncoding("UTF-8"); //改变编码方式
					xmlWriter = new XMLWriter(os,of);  
					xmlWriter.write(rootElement);  
					xmlWriter.flush();  
					//计算下载次数
					updateModeldlTimes(null, modelId.toString());
					return "1";
				} catch(Exception e) {
					log.info("download xml exception",e);
//						e.printStackTrace();
				} finally {
					try {
						if(os != null)
							os.close();
						if(xmlWriter != null)
							xmlWriter.close();
					} catch(Exception e) {
//							e.printStackTrace();
						log.info("download xml close output exception",e);
					}
				}
			}		
		}
		return "1";
	}
	
	@Override
	public String abtainIRXmlData(String brandNamet, String modelNamet) throws Exception {
		//查询IR数据
		StringBuilder sql = new StringBuilder("select c.id brandId,c.brandName,c.enbrandName,c.region,c.firstchar,b.id modelId,b.modelName,b.controllType,a.id,a.data,a.actionName,a.IRCommdid,a.CommdSequence,a.actionNameDescription from irdata a ");
		sql.append("left join irmodel b on a.modelId = b.id ");
		sql.append("left join irbrand c on b.brandId = c.id ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(brandNamet) && StringUtils.isNotBlank(modelNamet)) {
//			sql.append("where b.modelName = '").append(modelNamet).append("' and c.brandName = '").append(brandNamet).append("'");
			sql.append("where b.modelName = :modelName and c.brandName = :brandName");
			params.put("modelName", modelNamet);
			params.put("brandName", brandNamet);
		}
		else if(StringUtils.isNotBlank(modelNamet)) {
//			sql.append("where b.modelName = '").append(modelNamet).append("'");
			sql.append("where b.modelName = :modelName");
			params.put("modelName", modelNamet);
		}
		else if(StringUtils.isNotBlank(brandNamet)) {
//			sql.append("where c.brandName = '").append(brandNamet).append("'");
			sql.append("where c.brandName = :brandName");
			params.put("brandName", brandNamet);
		}
		else {
			return "0";
		}		
		
		List<Map> irList = mapDao.executeSql(sql.toString(), params);
		//组装xml文件
		if(!irList.isEmpty()) {
			String brandId = irList.get(0).get("brandId").toString();
			String modelId = irList.get(0).get("modelId").toString();
			String modelName = irList.get(0).get("modelName") == null? "":(String) irList.get(0).get("modelName"); 
			String brandName = irList.get(0).get("brandName") == null? "":(String) irList.get(0).get("brandName");
			Document rootElement = this.createXmlDoc(irList, brandName, modelName);
			if(rootElement != null) {				
				FileOutputStream fileWriter = null;
				XMLWriter xmlWriter = null;				
				try {
					String dirPath = PropertiesUtil.getProperty("IRxmlPath");
					log.info("IRxmlPath=" + dirPath);
					File dir = new File(dirPath);
					if(!dir.exists())
						dir.mkdirs();
					String fileName = "ir_" + brandId + "_" + modelId + "_" + (new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date()) + ".xml";
					String filePath = dirPath + fileName;
					log.info("IRxml file path=" + filePath);
					fileWriter = new FileOutputStream(filePath); 
					OutputFormat of = OutputFormat.createPrettyPrint();  
					// 设置编码
					of.setEncoding("UTF-8"); //改变编码方式
					xmlWriter = new XMLWriter(fileWriter,of);  
					xmlWriter.write(rootElement);  
					xmlWriter.flush();  
					//返回ftp地址
					String irXmlDir = dirPath.substring(dirPath.lastIndexOf("/", dirPath.length() - 2));
					String ftpPath = "ftp://" + PropertiesUtil.getProperty("ftpUser") + ":" +
							PropertiesUtil.getProperty("ftpPass") + "@" +
							PropertiesUtil.getProperty("ftpIp") + ":" + PropertiesUtil.getProperty("ftpPort") +
							irXmlDir + fileName;
	//				String ftpPath="D:/Program Files/Apache Software Foundation/Tomcat 7.0/down";
					//计算下载次数
					updateModeldlTimes(null, modelId);
					log.info("ftp file name=" + ftpPath);
					return ftpPath;
				} catch(Exception e) {
//					e.printStackTrace();
					log.info("abtainIRXmlData", e);
				} finally {
					try {
						if(xmlWriter != null)
							xmlWriter.close();
						if(fileWriter != null)
							fileWriter.close();
					} catch(Exception e) {
//						e.printStackTrace();
						log.info("abtainIRXmlData", e);
					}
				}
			}
		}
		return "1";
	}
	
	@Override
	public int updateModeldlTimes(Integer dlTimes, String modelId) throws Exception {
		String sql = "update irmodel set dlTimes=dlTimes+1 where id=:modelId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modelId", modelId);
		if(dlTimes != null) {
			sql = "update irmodel set dlTimes=:dlTimes where id=:modelId";
			params.put("dlTimes", dlTimes);
		}
		return mapDao.executeSql2(sql.toString(), params);
	}
	
	@Override
	public List<Map> abtainIRBrandModelsByName(String name, Integer irType) throws Exception {
		StringBuilder sql = new StringBuilder("select a.id brandId,a.brandName,a.enbrandName,b.id modelId,b.modelName,b.checked from irbrand a ");
		sql.append("left join irmodel b on a.id = b.brandId ");
		Map<String, Object> params = new HashMap<String, Object>();			
		if(StringUtils.isNoneBlank(name) && irType != null) {
//			sql.append("where (a.brandName like '%").append(name).append("%' or ");
//			sql.append("b.modelName like '%").append(name).append("%') and b.controllType=").append(irType);
			sql.append("where (a.brandName like :brandName or ");
			sql.append("b.modelName like :modelName) and b.controllType=:controllType");
			params.put("modelName", "%" + name + "%");
			params.put("brandName", "%" + name + "%");
			params.put("controllType", irType);
		}
		else if(StringUtils.isNoneBlank(name)){
//			sql.append("where (a.brandName like '%").append(name).append("%' or ");
//			sql.append("b.modelName like '%").append(name).append("%'");
			sql.append("where (a.brandName like :brandName or ");
			sql.append("b.modelName like :modelName)");
			params.put("modelName", "%" + name + "%");
			params.put("brandName", "%" + name + "%");
		}
		else {
//			sql.append("where b.controllType=").append(irType);
			sql.append("where b.controllType=:controllType");
			params.put("controllType", irType);
		}
		List<Map> bmList = mapDao.executeSql(sql.toString(), params);
		return bmList;
	}
	
	@Override
	public int createIrIndexDataTable(String tableName) throws Exception {
		StringBuilder sql = new StringBuilder("CREATE TABLE if not exists ");
		  sql.append(tableName);
		  sql.append("(id bigint(20) NOT NULL AUTO_INCREMENT,");
		  sql.append("seq int(11) DEFAULT NULL,");
		  sql.append("dataId bigint(20) DEFAULT NULL,");
		  sql.append("pulseType int(11) DEFAULT NULL,");
		  sql.append("bandwidth int(11) DEFAULT NULL,");
		  sql.append("pulseWidthMin int(11) DEFAULT NULL,");
		  sql.append("pulseWidthMax int(11) DEFAULT NULL,");
		  sql.append("PRIMARY KEY (id),");
		  sql.append("KEY seq_index (seq),");
		  sql.append("KEY dataId_index (dataId)");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8");
		return mapDao.executeSql2(sql.toString());
	}
	
	/**
	 * 建立每个IRIndex表
	 * @param mainRecord IRData数据条
	 * @return
	 */
	private int createIRdataTable(Element mainRecord) {
		for (Iterator i = mainRecord.elementIterator("ChildRecord"); i.hasNext();) {
			Element cr = (Element) i.next();
			Element irdata = cr.element("IRData");
			String irData = irdata.getText();
			if(StringUtils.isBlank(irData))
				continue;
			if(irData.length() > 1) {				
				//缓存解析后的各脉冲数据
				String encodeFlag = irData.substring(0, 2);
				//建表
				String tableName = "irindexdata_" + encodeFlag;
				StringBuilder sql = new StringBuilder("CREATE TABLE if not exists ");
				  sql.append(tableName);
				  sql.append("(id bigint(20) NOT NULL AUTO_INCREMENT,");
				  sql.append("seq int(11) DEFAULT NULL,");
				  sql.append("dataId bigint(20) DEFAULT NULL,");
				  sql.append("pulseType int(11) DEFAULT NULL,");
				  sql.append("bandwidth int(11) DEFAULT NULL,");
				  sql.append("pulseWidthMin int(11) DEFAULT NULL,");
				  sql.append("pulseWidthMax int(11) DEFAULT NULL,");
				  sql.append("PRIMARY KEY (id),");
				  sql.append("KEY seq_index (seq),");
				  sql.append("KEY dataId_index (dataId)");
				sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8");
				mapDao.executeSql2(sql.toString());
			}
		}
		return 1;
	}
	
	/**
	 * 保存ir数据动作
	 * @param doc
	 * @param mainRecord
	 * @param userId
	 * @param checked
	 * @param fileSize
	 * @param param
	 * @param cType
	 * @param brandName
	 * @param modelName
	 * @param brandNameEN
	 * @param region
	 * @param firstchar
	 * @throws Exception
	 */
	private void saveIRxmlAction(Document doc, Element mainRecord, String userId, String checked, int fileSize,
			Map<String,Object> param, String cType, String brandName, String modelName, String brandNameEN,
			String region, String firstchar) throws Exception {
		//建立IRData表
		this.createIRdataTable(mainRecord);
		Map<String, Object> bm = isModelExist(doc,param);
		Long modelId = null;
		
		if(bm != null) {
			//删除已有的数据
			deleteIrData(doc, brandName, modelName);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = sdf.format(new Date());
			modelId = ((BigInteger) bm.get("modelId")).longValue();
			//更新irModel表
			StringBuilder sql = new StringBuilder("update irmodel left join irbrand on irmodel.brandId = irbrand.id ");
			sql.append("set irmodel.controllType=:cType,irmodel.checked=:checked,irmodel.checkUserId=:checkUserId,");
			sql.append("irmodel.fileSize=:fileSize,irmodel.uploadTime=:uploadTime,irmodel.uploadUserId=:uploadUserId where irmodel.id=:modelId");
			//irmodel.deviceStyle=:deviceStyle,irmodel.remoteControlStyle=:remoteControlStyle 
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cType", cType);
			params.put("checked", checked);
			params.put("checkUserId", userId);
			params.put("fileSize", fileSize);
			params.put("modelId", modelId);
			params.put("uploadTime", dateStr);
			params.put("uploadUserId", userId);
//			params.put("deviceStyle", deviceStyle);
//			params.put("remoteControlStyle", remoteControlStyle);
			mapDao.executeSql2(sql.toString(), params);
		} 
		else {
			Map<String, Object> brandMap = isBrandExist(brandName);
			IRModel irm = new IRModel();
			irm.setModelName(modelName);
			irm.setChecked(Short.parseShort(checked));
			irm.setFileSize(fileSize);
			irm.setCheckUserId(Long.parseLong(userId));
			irm.setControllType(Integer.parseInt(cType));
			irm.setUploadTime(new Date());
			irm.setUploadUserId(Long.parseLong(userId));
//			irm.setDeviceStyle(deviceStyle);
//			irm.setRemoteControlStyle(remoteControlStyle);
			//插入品牌数据
			if(brandMap == null) {
				IRBrand irb = new IRBrand();
				irb.setBrandName(brandName);
				irb.setEnbrandName(brandNameEN);
				irb.setRegion(region);
				irb.setFirstchar(firstchar);
				Serializable mid = irBrandDao.save(irb);
				irm.setBrandId((Long) mid);
				modelId = (Long) irModelDao.save(irm);				
			} 
			else {
				BigInteger id=(BigInteger) brandMap.get("brandId");
				//修改品牌表
				IRBrand irb = new IRBrand();
				irb.setId(id.longValue());
				irb.setBrandName(brandName);
				irb.setEnbrandName(brandNameEN);
				irb.setRegion(region);
				irb.setFirstchar(firstchar);
				irBrandDao.update(irb);
				//插入型号表
				irm.setBrandId(((BigInteger) brandMap.get("brandId")).longValue());
				modelId = (Long) irModelDao.save(irm);
			}
		}
		
		for (Iterator i = mainRecord.elementIterator("ChildRecord"); i.hasNext();) {
			Element cr = (Element) i.next();
			Element irdata = cr.element("IRData");
			String irData = irdata.getText();
			Element fname = cr.element("FunctionName");
			String fnameStr = fname.getText();
//			Element MidEle = cr.element("ID");
//			String idStr = MidEle.getText();
			Element iRCommdidEle = cr.element("IRCommdid");
			String iRCommdidStr = iRCommdidEle.getText();
			Element commdSequenceEle = cr.element("CommdSequence");
			String commdSequenceStr = commdSequenceEle.getText();
//			Element fnameDescEle = cr.element("FunctionDescription");
//			String fnameDescStr = fnameDescEle.getText();
			//二进制数据中文转码，ISO-8859-1编码是单字节编码
			if(fnameStr.equals(new String(fnameStr.getBytes("iso8859-1"), "iso8859-1"))){
			 fnameStr=new String(fnameStr.getBytes("iso8859-1"),"gbk");
	        }
//			if(fnameDescStr.equals(new String(fnameDescStr.getBytes("iso8859-1"), "iso8859-1"))){
//			 fnameDescStr=new String(fnameDescStr.getBytes("iso8859-1"),"gbk");
//	        }
			
			if(StringUtils.isBlank(irData))
				continue;
			if(irData.length() > 1) {					
				//缓存解析后的各脉冲数据
				Map<Integer, String> cacheMap = new HashMap<Integer, String>();
				String encodeFlag = irData.substring(0, 2);
				String remainData = irData.substring(2);
				int stageCount = 0;
				int charCount = remainData.length();
				int index = 0;
				//建表
				String tableName = "irindexdata_" + encodeFlag;
				if(encodeFlag.equals("FF")) {
					int dataInt = 0; //脉宽
					while(index < charCount) {					
						if((index + 2) > charCount)
							break;
						String stageType = remainData.substring(index, index + 2);
						if(stageType.equals("FF")) {
							String data = remainData.substring(index + 2, index + 6);
							index += 6;
							dataInt = Integer.parseInt(data,16);							
							stageCount++;
						}
						else {
							index += 2;
							dataInt = Integer.parseInt(stageType, 16);
							stageCount++;
						}
						int minDi = dataInt - (dataInt / 8 + 10);
						int maxDi = dataInt + (dataInt / 8 + 10);
						StringBuilder v = new StringBuilder("1,");
						v.append(dataInt).append(",").append(minDi).append(",").append(maxDi);
						cacheMap.put(stageCount, v.toString());
					}
					
				} else {
					while(index < charCount) {
						String byteStr = remainData.substring(index, index + 2);
						int byteInt = Integer.parseInt(byteStr, 16);
						index += 2;
						stageCount += 1;
						int minDi = byteInt - (byteInt / 8 + 10);
						int maxDi = byteInt + (byteInt / 8 + 10);
						StringBuilder v = new StringBuilder("2,");
						v.append(byteInt).append(",").append(minDi).append(",").append(maxDi);
						cacheMap.put(stageCount, v.toString());
					}
				}
				//插入IRData表
				IRData ird = new IRData();
				ird.setData(irData);
				ird.setPulseCount(stageCount);
				ird.setDecodeType(encodeFlag);
				ird.setModelId(modelId);
				ird.setActionName(fnameStr);
				ird.setCommdSequence(Integer.parseInt(commdSequenceStr));
				ird.setIrcommdid(Integer.parseInt(iRCommdidStr));
//				ird.setActionNameDescription(fnameDescStr);
				Serializable mId = irDataDao.save(ird);
				//插入到IR分表
				StringBuilder sql = new StringBuilder("insert into ");
				sql.append(tableName).append("(seq,dataId,pulseType,bandwidth,pulseWidthMin,pulseWidthMax) values");
//				sql.append("(:seq,:dataId,:pulseType,:bandwidth,:pulseWidthMin,:pulseWidthMax)");
				Iterator itor = cacheMap.keySet().iterator();
				int j = 1;
				Map<String, Object> params = new HashMap<String, Object>();
				while(itor.hasNext()) {
					Integer seq = (Integer) itor.next();
					String[] value = cacheMap.get(seq).split(",");
					sql.append("(:seq").append(j).append(",:dataId").append(j).append(",:pulseType").append(j).append(",:bandwidth").append(j);
					sql.append(",:pulseWidthMin").append(j).append(",:pulseWidthMax").append(j).append("),");
					params.put("seq" + j, seq);
					params.put("dataId" + j, mId);
					params.put("pulseType" + j, value[0]);
					params.put("bandwidth" + j, value[1]);
					params.put("pulseWidthMin" + j, value[2]);
					params.put("pulseWidthMax" + j, value[3]);		
					++j;
				}
				sql = sql.deleteCharAt(sql.length() - 1);
				mapDao.executeSql2(sql.toString(), params);
			}
		}
	}
	
	@Override
	public int saveIRxml(Document doc, String userId, String checked, int fileSize,Map<String,Object> param) throws Exception {
		if(doc == null)
			return -1;
////		判断是否存在		
//		long modelId = -1;
		Element mainRecord = doc.getRootElement();
		List<Element> list = mainRecord.selectNodes("BrandEN");
//		System.out.println(list.size());
		if (list.size()>0) {
			Element cTypeEle = mainRecord.element("DeviceType");
			String cType = cTypeEle.getText();
//			Element brand = mainRecord.element("Brand");
//			String brandName = brand.getText();
			String brandName = (String) param.get("brand");
	//			Element brandEN = mainRecord.element("BrandEN");
	//			String brandNameEN = brandEN.getText();
			String brandNameEN = (String) param.get("brandEN");
	//			Element unitCode = mainRecord.element("UnitCode");
	//			String region = unitCode.getText();
			String region = (String) param.get("UnitCode");
	//			Element nameIndex = mainRecord.element("NameIndex");
	//			String firstchar = nameIndex.getText();
			String firstchar = (String) param.get("NameIndex");
	//			Element model = mainRecord.element("Style");
	//			String modelName = model.getText();
			String modelName = (String) param.get("model");
			saveIRxmlAction(doc, mainRecord, userId, checked, fileSize, param, cType, 
					brandName, modelName, brandNameEN, region, firstchar);
		}  
		else{
		
			Element cTypeEle = mainRecord.element("DeviceType");
			String cType = cTypeEle.getText();
			if (cType.equals("2")) {
				
	//			Element brand = mainRecord.element("Brand");
	//			String brandName = brand.getText();
				String brandNameCN = (String) param.get("brand");
				String brandNameEN = (String) param.get("brandEN");
	//			region = brandNameEN.substring(0,1).toUpperCase();
				String region = (String) param.get("UnitCode");
	//			firstchar = brandNameEN.substring(0, 1).toUpperCase();
				String firstchar = (String) param.get("NameIndex");
				if(region.equals(new String(region.getBytes("iso8859-1"), "iso8859-1"))){
					region = new String(region.getBytes("iso8859-1"),"gbk");
	//	        }
				}
				//机顶盒    格力(gree)@厦门
	//			String[] str = brandName.split("[()]");
	//			String brandNameCN = str[0];//品牌中文
	//			String brandNameEN = null;//品牌英文
	//			String region = null;
	//			String firstchar = "$";
	//			int strLength = str.length;
				//二进制数据中文转码，ISO-8859-1编码是单字节编码
				if(brandNameCN.equals(new String(brandNameCN.getBytes("iso8859-1"), "iso8859-1"))){
					brandNameCN = new String(brandNameCN.getBytes("iso8859-1"),"gbk");
				}
	//			if(strLength > 1) {
	//				brandNameEN = str[1];
	////				if(brandNameEN.equals(new String(brandNameEN.getBytes("iso8859-1"), "iso8859-1"))){
	////					brandNameEN = new String(brandNameEN.getBytes("iso8859-1"),"gbk");
	////				}
	//			}
	//			if(strLength > 2) {
	//				String regionStr = str[2];
	//				region = regionStr.substring(1, regionStr.length());
	//				if(region.equals(new String(region.getBytes("iso8859-1"), "iso8859-1"))){
	//					region = new String(region.getBytes("iso8859-1"),"gbk");
	//				}			
	//				String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(region.charAt(0));
	//				if(pinYin!=null&&pinYin.length>0){
	//			    	 firstchar = pinYin[0].toUpperCase().charAt(0)+"";
	//			     }
	//			}	
	//		
	//		Element model = mainRecord.element("Style");
	//		String modelName = model.getText();
				String modelName = (String) param.get("model");
				//二进制数据中文转码，ISO-8859-1编码是单字节编码
				if(modelName.equals(new String(modelName.getBytes("iso8859-1"), "iso8859-1"))){
					modelName=new String(modelName.getBytes("iso8859-1"),"gbk");
		        }
			
	//		Element deviceStyleEle = mainRecord.element("DeviceStyle");
	//		String deviceStyle = deviceStyleEle.getText();
	//		Element remoteControlStyleEle = mainRecord.element("RemoteControlStyle");
	//		String remoteControlStyle = remoteControlStyleEle.getText();
		
			saveIRxmlAction(doc, mainRecord, userId, checked, fileSize, param, cType, 
					brandNameCN, modelName, brandNameEN, region, firstchar);
		
		}
		else {
	//			Element brand = mainRecord.element("Brand");
	//			String brandName = brand.getText();
				String brandNameCN = (String) param.get("brand");
				//不是机顶盒    格力(gree)
	//			String[] str = brandName.split("[()]");
	//			String brandNameCN = str[0];//品牌中文
	//			String firstchar = null;
	//			String region = null;
	//			int strLength = str.length;
	//			String brandNameEN = null;//品牌英文
	//			if(strLength > 1) {
//				brandNameEN = str[1];
				String brandNameEN = (String) param.get("brandEN");
//				region = brandNameEN.substring(0,1).toUpperCase();
				String region = (String) param.get("UnitCode");
//				firstchar = brandNameEN.substring(0, 1).toUpperCase();
				String firstchar = (String) param.get("NameIndex");
				if(region.equals(new String(region.getBytes("iso8859-1"), "iso8859-1"))){
					region = new String(region.getBytes("iso8859-1"),"gbk");
				}
				//品牌英文
		//		Element brandEN = mainRecord.element("BrandEN");
		//		String brandNameEN = brandEN.getText();
				//二进制数据中文转码，ISO-8859-1编码是单字节编码
				if(brandNameCN.equals(new String(brandNameCN.getBytes("iso8859-1"), "iso8859-1"))){
					brandNameCN = new String(brandNameCN.getBytes("iso8859-1"),"gbk");
		        }		
		//		Element model = mainRecord.element("Style");
		//		String modelName = model.getText();
				
				String modelName = (String) param.get("model");
				//二进制数据中文转码，ISO-8859-1编码是单字节编码
				if(modelName.equals(new String(modelName.getBytes("iso8859-1"), "iso8859-1"))){
					modelName=new String(modelName.getBytes("iso8859-1"),"gbk");
		        }
				
		//		Element deviceStyleEle = mainRecord.element("DeviceStyle");
		//		String deviceStyle = deviceStyleEle.getText();
		//		Element remoteControlStyleEle = mainRecord.element("RemoteControlStyle");
		//		String remoteControlStyle = remoteControlStyleEle.getText();		
				saveIRxmlAction(doc, mainRecord, userId, checked, fileSize, param, cType, 
						brandNameCN, modelName, brandNameEN, region, firstchar);			
			}
		}
		return 1;
	}
	
	@Override
	public Map<String, Object> isBrandExist(String brandName) throws Exception {
		StringBuilder sql = new StringBuilder("select a.id brandId from irbrand a ");
		sql.append("where a.brandName=:brandName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brandName", brandName);
		List<Map> bmList = mapDao.executeSql(sql.toString(), params);
		if(bmList.isEmpty())
			return null;
		else
			return bmList.get(0);
	}
	
	/**
	 * 显示XML文件内容
	 */
	@Override
	public Map<String, Object> showIRxml(Document doc) throws Exception{
		if(doc == null){
			return null;
			}
	
		Element mainRecord = doc.getRootElement();
		List<Element> li = mainRecord.selectNodes("BrandEN");
		List<Element> liU = mainRecord.selectNodes("UnitCode");
		if(li.size()>0){
			Element brandEN = mainRecord.element("BrandEN");
			String brandENname = brandEN.getText();
		}
		else{
			String brandENname= "brandENname";
		}
		Element brand = mainRecord.element("Brand");
		String brandName = brand.getText();
		Element model = mainRecord.element("Style");
		String modelName = model.getText();
		
		//中文转码
		if(brandName.equals(new String(brandName.getBytes("iso8859-1"), "iso8859-1")))
	      {
			  brandName=new String(brandName.getBytes("iso8859-1"),"gbk");
	      }
		//中文转码
		if(modelName.equals(new String(modelName.getBytes("iso8859-1"), "iso8859-1")))
		  {
				 modelName=new String(modelName.getBytes("iso8859-1"),"gbk");
		  }
		Element DeviceType = mainRecord.element("DeviceType");
		String DeviceTypeName = DeviceType.getText();
		StringBuilder sql = new StringBuilder("select * from ircontrolltype where id = :id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", DeviceTypeName);
		List<Map> list = mapDao.executeSql(sql.toString(), params);
		Map DeviceTypeparams = list.get(0);
		
		Iterator it=DeviceTypeparams.keySet().iterator();
//		while(it.hasNext()){
		String key = (String)it.next();
		Object DeviceTypeName1 =  DeviceTypeparams.get("controllTypeName");
//		showparams.put(key,deviceStyleName);
//		}
		Map<String, Object> showparams = new HashMap<String, Object>();
		showparams.put("DeviceTypeid",DeviceTypeName);
		if(brandName.contains("(")){
			String[] a = brandName.split("\\(");
			brandName = a[0];
			if(brandName.equals(new String(brandName.getBytes("iso8859-1"), "iso8859-1")))
		      {
				  brandName=new String(brandName.getBytes("iso8859-1"),"gbk");
		      }
			String brandENname1 =a[1].split("\\)")[0];
			showparams.put("brandName",brandName );
			showparams.put("brandENname",brandENname1);
			showparams.put("modelName", modelName);
			showparams.put("DeviceTypeName",DeviceTypeName1 );
		}
		else{
			List<Element> li1 = mainRecord.selectNodes("BrandEN");
			if(li1.size()>0){
			Element brandEN = mainRecord.element("BrandEN");
			String brandENname = brandEN.getText();
			showparams.put("brandENname",brandENname);
			}
			else{
				showparams.put("brandENname","brandENname");
			}
			showparams.put("brandName",brandName );
			showparams.put("modelName", modelName);
			showparams.put("DeviceTypeName",DeviceTypeName1 );
		}
		Element brandUnit = mainRecord.element("Brand");
		String brandName1 = brandUnit.getText();
		if(brandName1.equals(new String(brandName1.getBytes("iso8859-1"), "iso8859-1")))
	      {
			brandName1=new String(brandName1.getBytes("iso8859-1"),"gbk");
	      }
		if(brandName1.contains("@")){
			String[] b = brandName1.split("\\@");
			showparams.put("UnitCodeName", b[1]);
		}
		if(liU.size()>0){
			Element UnitCode = mainRecord.element("UnitCode");
			String UnitCodeName = UnitCode.getText();
			showparams.put("UnitCodeName", UnitCodeName);
		}
		return showparams;
		
	}
	
	
	@Override
	public Map<String, Object> isModelExist(Document doc, Map<String, Object> param) throws Exception {
		if(doc == null)
			return null;
		if(param.isEmpty()==false){
			String brandName = (String) param.get("brand");
			String modelName = (String)param.get("model");
			StringBuilder sql = new StringBuilder("select a.id brandId,b.id modelId from irbrand a left join irmodel b on a.id = b.brandId ");
			sql.append("where a.brandName=:brandName and b.modelName=:modelName");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("brandName", brandName);
			params.put("modelName", modelName);
			List<Map> bmList = mapDao.executeSql(sql.toString(), params);
			if(bmList.isEmpty())
				return null;
			else
				return bmList.get(0);
		}
		else{
		Element mainRecord = doc.getRootElement();
		Element brand = mainRecord.element("Brand");
		String brandNameStr = brand.getText();
		if(!param.get("brand").equals("brandNameStr")){
			brandNameStr=(String) param.get("brand");
		}
		String[] Str = brandNameStr.split("[()]");
		String brandName = Str[0];
	
		Element model = mainRecord.element("Style");
		String modelName = model.getText();
		if(!param.get("model").equals(modelName)){
			modelName = (String)param.get("model");
		}
		//中文转码
	    if(brandName.equals(new String(brandName.getBytes("iso8859-1"), "iso8859-1"))){
		  brandName=new String(brandName.getBytes("iso8859-1"),"gbk");
        }
		//中文转码
		if(modelName.equals(new String(modelName.getBytes("iso8859-1"), "iso8859-1"))){
		 modelName=new String(modelName.getBytes("iso8859-1"),"gbk");
        }
		StringBuilder sql = new StringBuilder("select a.id brandId,b.id modelId from irbrand a left join irmodel b on a.id = b.brandId ");
		sql.append("where a.brandName=:brandName and b.modelName=:modelName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brandName", brandName);
		params.put("modelName", modelName);
		List<Map> bmList = mapDao.executeSql(sql.toString(), params);
		if(bmList.isEmpty())
			return null;
		else
			return bmList.get(0);
		}
		
	}
	
	@Override
	public int deleteIrData(Document doc, String brandName, String modelName) throws Exception {
		if(doc == null)
			return -1;
		Element mainRecord = doc.getRootElement();
//		Element brand = mainRecord.element("Brand");
//		String brandName = brand.getText();
//		Element model = mainRecord.element("Style");
//		String modelName = model.getText();
		//中文转码
		  if(brandName.equals(new String(brandName.getBytes("iso8859-1"), "iso8859-1")))
	        {
			  brandName=new String(brandName.getBytes("iso8859-1"),"gbk");
	        }
		//中文转码
			 if(modelName.equals(new String(modelName.getBytes("iso8859-1"), "iso8859-1")))
		        {
				 modelName=new String(modelName.getBytes("iso8859-1"),"gbk");
		        }
		//删除IR分表数据
		for (Iterator i = mainRecord.element("ChildRecord").elementIterator("IRData"); i.hasNext();) {
			Element cr = (Element) i.next();
			String irData = cr.getText();
			if(StringUtils.isBlank(irData))
				continue;
			if(irData.length() > 1) {
				String encodeFlag = irData.substring(0, 2);
				String tableName = "irindexdata_" + encodeFlag;
				//表存在则删除数据
				if(isTableExist(tableName) == 1) {
					StringBuilder sql = new StringBuilder("delete ");
					sql.append(tableName).append(" from ").append(tableName).append(" left join irdata on ");
					sql.append(tableName).append(".dataId = irdata.id ");
					sql.append("left join irmodel on irdata.modelId = irmodel.id ");
					sql.append("left join irbrand on irmodel.brandId = irbrand.id ");
					sql.append("where irbrand.brandName=:brandName and irmodel.modelName=:modelName and irdata.data=:irData");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("brandName", brandName);
					params.put("modelName", modelName);
					params.put("irData", irData);
					mapDao.executeSql2(sql.toString(), params);
					//判断表数据为空则删除表
					sql = new StringBuilder("select 1 from ");
					sql.append(tableName).append(" limit 1");
					List<Map> count = mapDao.executeSql(sql.toString());
					if(count.isEmpty()) {
						sql = new StringBuilder("drop table ");
						sql.append(tableName);
						mapDao.executeSql2(sql.toString());
					}
				}				
			}
		}
		//删除irData中的数据
		StringBuilder sql = new StringBuilder("delete irdata ");
		sql.append("from irdata left join irmodel on irdata.modelId=irmodel.id ");
		sql.append("left join irbrand on irmodel.brandId=irbrand.id ");
		sql.append("where irbrand.brandName=:brandName and irmodel.modelName=:modelName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brandName", brandName);
		params.put("modelName", modelName);
		mapDao.executeSql2(sql.toString(), params);
		return 1;
	}
	
	@Override
	public int isTableExist(String tableName) throws Exception {
		StringBuilder sql = new StringBuilder("select 1 from INFORMATION_SCHEMA.TABLES where ");
		sql.append("TABLE_SCHEMA=:tableSchema and TABLE_NAME=:tableName");
		String dbName = PropertiesUtil.getProperty("jdbc.jdbcUrl");
		if(StringUtils.isNoneBlank(dbName)) {
			int qmIndex = dbName.indexOf("?");
			if(qmIndex != -1) {
				dbName = dbName.substring(dbName.indexOf("3306/") + 5, qmIndex);
			}
			else {
				dbName = dbName.substring(dbName.indexOf("3306/") + 5);
			}
		}
		else
			dbName = "zigbeedevice";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableSchema", dbName);
		params.put("tableName", tableName);
		List<Map> ml = mapDao.executeSql(sql.toString(), params);
		if(ml.isEmpty())
			return 0;
		else
			return 1;
	}
	
	@Override
	public int deleteModelAndIRData(String modelId) throws Exception {
		String sql = "select data from irdata where modelId=:modelId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modelId", modelId);
		List<Map> mList = mapDao.executeSql(sql.toString(), params);
		int mSize = mList.size();
		for(int i = 0; i < mSize; i++) {
			String irData = (String) mList.get(i).get("data");
			if(StringUtils.isBlank(irData))
				continue;
			if(irData.length() > 1) {
				String encodeFlag = irData.substring(0, 2);
				String tableName = "irindexdata_" + encodeFlag;
				//表存在则删除数据
				if(isTableExist(tableName) == 1) {
					StringBuilder sqlIr = new StringBuilder("delete ");
					sqlIr.append(tableName).append(" from ").append(tableName).append(" left join irdata on ");
					sqlIr.append(tableName).append(".dataId = irdata.id ");
					sqlIr.append("left join irmodel on irdata.modelId = irmodel.id ");
					sqlIr.append("left join irbrand on irmodel.brandId = irbrand.id ");
					sqlIr.append("where irmodel.id=:modelId and irdata.data=:irData");
					params = new HashMap<String, Object>();
					params.put("modelId", modelId);
					params.put("irData", irData);
					mapDao.executeSql2(sqlIr.toString(), params);
					//判断表数据为空则删除表
					sqlIr = new StringBuilder("select 1 from ");
//					sqlIr = new StringBuilder("select max(id) dataCount from ");
					sqlIr.append(tableName).append(" limit 1");
					List<Map> count = mapDao.executeSql(sqlIr.toString());
//					long dataCount = ((BigInteger) ((Map) count.get(0)).get("dataCount")).longValue();
//					if(dataCount == 0) {
					if(count.size() == 0) {
						sqlIr = new StringBuilder("drop table ");
						sqlIr.append(tableName);
						mapDao.executeSql2(sqlIr.toString());
					}
				}				
			}
		}
		//删除irData中的数据
		StringBuilder sqlB = new StringBuilder("delete irdata ");
		sqlB.append("from irdata left join irmodel on irdata.modelId=irmodel.id ");
		sqlB.append("where irmodel.id=:modelId");
		params = new HashMap<String, Object>();
		params.put("modelId", modelId);
		mapDao.executeSql2(sqlB.toString(), params);
		//判断Brand表是否还包含其他model,不包含则删除品牌
		sqlB = new StringBuilder("select 1 from irbrand a inner join irmodel b on a.id = b.brandId where b.id <> :modelId ");
		sqlB.append("and a.id = (select c.id from irbrand c left join irmodel d on c.id = d.brandId where d.id = :modelId) limit 1");
		params = new HashMap<String, Object>();
		params.put("modelId", modelId);
		List<Map> bList = mapDao.executeSql(sqlB.toString(), params);
		if(bList.isEmpty()) {
			sqlB = new StringBuilder("delete irbrand from irbrand left join irmodel on irbrand.id = irmodel.brandId ");
			sqlB.append("where irmodel.id = :modelId");
			mapDao.executeSql2(sqlB.toString(), params);
		}
		//删除model表
		sqlB = new StringBuilder("delete from irmodel ");
		sqlB.append("where irmodel.id=:modelId");
		params = new HashMap<String, Object>();
		params.put("modelId", modelId);
		mapDao.executeSql2(sqlB.toString(), params);		
		return 1;
	}
	
	@Override
	public int getIRCount(String searchText) throws Exception {
		StringBuilder sql = new StringBuilder("select count(*) count from irbrand a inner join irmodel b on a.id = b.brandId ");
		sql.append("left join ircontrolltype c on b.controllType = c.id ");
		sql.append("left join reliuser d on b.checkUserId = d.id ");
		sql.append("left join reliuser e on b.uploadUserId = e.id ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(searchText)) {
			sql.append("where c.controllTypeName like :searchText or ");
			sql.append("a.brandName like :searchText or ");
			sql.append("b.modelName like :searchText or ");
			sql.append("b.fileSize like :searchText or ");
			sql.append("d.user_name like :searchText or ");
			sql.append("convert(b.uploadTime using gb2312) like :searchText or ");
			sql.append("e.user_name like :searchText or ");
			sql.append("b.dlTimes like :searchText");
			params.put("searchText", "%" + searchText + "%");
		}
		List<Map> irList = mapDao.executeSql(sql.toString(), params);
		return ((BigInteger)irList.get(0).get("count")).intValue();
	}
	
	@Override
	public List<Map> queryIR(String searchText, int startRow, int pageSize, String orderBy) {
		dingshiaddUnMatch();//IR列表显示前定时保存
		StringBuilder sql = new StringBuilder("select a.id brandId,a.brandName,a.enbrandName,a.region,a.firstchar,b.id modelId,b.modelName,");
		sql.append("c.controllTypeName cTypeName,b.fileSize,d.user_name checkUserName,b.checked,e.user_name uploadUserName,b.uploadTime,");
		sql.append("b.dlTimes from irbrand a inner join irmodel b on a.id = b.brandId ");
		sql.append("left join ircontrolltype c on b.controllType = c.id ");
		sql.append("left join reliuser d on b.checkUserId = d.id ");
		sql.append("left join reliuser e on b.uploadUserId = e.id ");
		Map<String, Object> params = new HashMap<String, Object>();		
		if(StringUtils.isNotBlank(searchText)) {
			sql.append("where c.controllTypeName like :searchText or ");
			sql.append("a.brandName like :searchText or ");
			sql.append("a.enbrandName like :searchText or ");
			sql.append("a.region like :searchText or ");
			sql.append("a.firstchar like :searchText or ");
			sql.append("b.modelName like :searchText or ");
			sql.append("b.fileSize like :searchText or ");
			sql.append("d.user_name like :searchText or ");
			sql.append("convert(b.uploadTime using gb2312) like :searchText or ");
			sql.append("e.user_name like :searchText or ");
			sql.append("b.dlTimes like :searchText");
			params.put("searchText", "%" + searchText + "%");
		}
		if(StringUtils.isNotBlank(orderBy)) {
			sql.append(" order by ").append(orderBy).append(" ");
		} 
		else {
			sql.append(" order by b.uploadTime desc ");
		}
		sql.append("limit ").append(startRow).append(",").append(pageSize);
		List<Map> irList = mapDao.executeSql(sql.toString(), params);
		return irList;
	}
	
	@Override
	public int updateModeldlCheck(String checked, String userId, String modelId) throws Exception {
		String sql = "update irmodel set checkUserId=:userId,checked=:checked where id=:modelId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modelId", modelId);
		params.put("userId", userId);
		params.put("checked", checked);
		return mapDao.executeSql2(sql.toString(), params);
	}
	
	public static void main(String[] arg) {
		String IRData = "6B00BD807F01";
		String byteStr1 = IRData.substring(2, 4);
		int byte1 = Integer.parseInt(byteStr1, 16);
		int min = byte1 - ((byte1 / 8) + 10);
		int max = byte1 + ((byte1 / 8) + 10);
		String byteStr2 = IRData.substring(4, 6);
		int byte2 = Integer.parseInt(byteStr2, 16);
		int min2 = byte2 - ((byte1 / 8) + 10);
		int max2 = byte2 + ((byte1 / 8) + 10);
		String byteStr3 = IRData.substring(6, 8);
		int byte3 = Integer.parseInt(byteStr3, 16);
		int min3 = byte3 - ((byte1 / 8) + 10);
		int max3 = byte3 + ((byte1 / 8) + 10);
		String byteStr4 = IRData.substring(8, 10);
		int byte4 = Integer.parseInt(byteStr4, 16);
		int min4 = byte4 - ((byte1 / 8) + 10);
		int max4 = byte4 + ((byte1 / 8) + 10);
		String byteStr5 = IRData.substring(10, 12);
		int byte5 = Integer.parseInt(byteStr5, 16);
		int min5 = byte5 - ((byte1 / 8) + 10);
		int max5 = byte5 + ((byte1 / 8) + 10);
//		String byteStr6 = IRData.substring(12, 14);
//		int byte6 = Integer.parseInt(byteStr6, 16);
//		System.out.println(byte1 + "," + byte2 + "," + byte3 + "," + byte4 + "," + byte5 + "," + byte6);
		System.out.println(byte1 + "," + min + "," + max  + ":" + byte2 + "," + min2 + "," + max2  + "," + byte3 + "," + min3 + "," + max3 + ":" + byte4 + "," + min4 + "," + max4 + ":" + byte5  + "," + min5 + "," + max5);
	}

///**
// * IR编辑
// */
//	@Override
//	public int update(Map<String, Object> irbrand) throws Exception {
//		String sql = "update irbrand a left join irmodel b on a.id=b.brandId "+
//					 " set a.enbrandName =:enbrandName,a.brandName = :brandName,b.modelName=:modelName,b.controllType=:controllType"+
//					 " where b.id=:modelId";
////		String sql = "update irbrand a left join irmodel b on a.id=b.brandId left join ircontrolltype c on c.id=b.controlltype"+
////				" set a.enbrandName =:enbrandName,a.brandName = :brandName,b.modelName=:modelName,c.controllTypeName=:controllTypeName"+
////				" where b.id=:modelId";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("enbrandName",irbrand.get("enbrandName"));
//		params.put("brandName",irbrand.get("brandName"));
//		params.put("modelName",irbrand.get("modelName"));
//		params.put("controllType",irbrand.get("controllType"));
////		params.put("controllTypeName",irbrand.get("controllTypeName"));
//		params.put("modelId",irbrand.get("modelId"));
//		return irBrandDao.executeSql2(sql,params);
//	}
	/**
	 * IR编辑
	 */
	@Override
	public int update(Map<String, Object> irbrand) throws Exception {
//		IRBrand IRbrand = new IRBrand();
		String sql1 = "select * from irbrand where brandName =:brandName and enbrandName = :enbrandName and region =:region and firstchar =:firstchar";
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("brandName", irbrand.get("brandName"));
		params1.put("enbrandName", irbrand.get("enbrandName"));
		params1.put("region", irbrand.get("region"));
		params1.put("firstchar", irbrand.get("firstchar"));
		List<IRBrand> t = irBrandDao.findSql(sql1, params1,IRBrand.class);
		long brandId;
		if(t.size()>0){
			 brandId = t.get(0).getId();
		}
		else{
			String sql2 = "insert into irbrand(brandName,enbrandName,region,firstchar) values(:brandName,:enbrandName,:region,:firstchar)";
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("brandName", irbrand.get("brandName"));
			params2.put("enbrandName", irbrand.get("enbrandName"));
			params2.put("region", irbrand.get("region"));
			params2.put("firstchar", irbrand.get("firstchar"));
			int i = irBrandDao.executeSql2(sql2.toString(),params2);
//			String sql3 = "select * from irbrand where brandName =:brandName ";
//			Map<String, Object> params3 = new HashMap<String, Object>();
//			params3.put("brandName", irbrand.get("brandName"));
			List<IRBrand> t3 = irBrandDao.findSql(sql1, params1,IRBrand.class);
			brandId = t3.get(0).getId();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
//		String sql = "update irbrand a left join irmodel b on a.id=b.brandId "+
//				 " set a.region=:region,a.firstchar=:firstchar,a.enbrandName=:enbrandName,b.brandId =:brandId,b.modelName =:modelName,b.checked =:checked"+
//				 " where b.id=:modelId";	
		String sql = "update irbrand a left join irmodel b on a.id=b.brandId "+
				 " set b.brandId =:brandId,b.modelName =:modelName,b.checked =:checked"+
				 " where b.id=:modelId";	
		params.put("modelId",irbrand.get("modelId"));
		params.put("brandId",brandId);
		params.put("modelName",irbrand.get("modelName"));
		params.put("checked",irbrand.get("checked"));
//		params.put("region", irbrand.get("region"));
//		params.put("firstchar", irbrand.get("firstchar"));
//		params.put("enbrandName", irbrand.get("enbrandName"));
		return irBrandDao.executeSql2(sql,params);
	}
	@Override
	public List<Map> getPowerOn(Integer irType,String brandName,int pageIndex, int pageSize)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.id modelId,a.brandId,a.modelName,a.controllType,a.fileSize,a.uploadTime,c.data,a.checked from irmodel a left join irbrand b on a.brandId = b.id  " +
				"left join irdata c on a.id = c.modelId where c.CommdSequence='1' ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(irType != null && StringUtils.isNotBlank(brandName)) {
			sql.append("and a.controllType= :controllType and b.brandName= :brandName ");
			params.put("controllType", irType);
			params.put("brandName", brandName);
		}
		else if(irType != null){
			sql.append("and a.controllType= :controllType ");
			params.put("controllType", irType);
		}
		else if(StringUtils.isNotBlank(brandName)){
			sql.append("and b.brandName= :brandName ");
			params.put("brandName", brandName);
		}
		sql.append("limit ").append(pageIndex).append(",").append(pageSize);
		List<Map> IRmodelList = mapDao.executeSql(sql.toString(), params);
		return IRmodelList;
	}
	@Override
	public List<Map> getModelList(Integer irType,String brandName,int pageIndex, int pageSize)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from irmodel a left join irbrand b on a.brandId = b.id " );
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(irType != null && StringUtils.isNotBlank(brandName)) {
			sql.append("where a.controllType= :controllType and b.brandName= :brandName ");
			params.put("controllType", irType);
			params.put("brandName", brandName);
		}
		else if(irType != null){
			sql.append("where a.controllType= :controllType ");
			params.put("controllType", irType);
		}
		else if(StringUtils.isNotBlank(brandName)){
			sql.append("where b.brandName= :brandName ");
			params.put("brandName", brandName);
		}
		
		sql.append("limit ").append(pageIndex).append(",").append(pageSize);
		List<Map> IRmodelList = mapDao.executeSql(sql.toString(), params);
		return IRmodelList;
	}
	@Override
	public List<Map> getBrandList(Integer irType,int pageIndex, int pageSize) 
			throws Exception {
		StringBuilder sql = new StringBuilder("select distinct a.* from irbrand a left join irmodel b ");
		sql.append("on a.id = b.brandId ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(irType != null) {
			sql.append("where b.controllType= :controllType ");
			params.put("controllType", irType);
		}
		sql.append("limit ").append(pageIndex).append(",").append(pageSize);
		List<Map> IRbrandList = mapDao.executeSql(sql.toString(), params);
		return IRbrandList;
	}
	/**
	 * 编辑显示接口，不能共用
	 */
	@Override
	public List<IRBrand> findbrandedit(IRBrand irbrand) throws Exception {
		String sql = "select * from irbrand where 1=1";
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(irbrand.getBrandName())){
			sql=sql+" and brandName =:brandName";
			params.put("brandName",irbrand.getBrandName());
		}
		if(StringUtils.isNotBlank(irbrand.getEnbrandName())){
			sql=sql+" or enbrandName = :enbrandName";
			params.put("enbrandName", irbrand.getEnbrandName());
		}
		List<IRBrand> t = irBrandDao.executeSql(sql, params);
		return t;
	}
	
	@Override
	public List<IRBrand> findbrand(IRBrand irbrand,IRBranddictionary irbranddictionary) throws Exception {
		String sql1= "select * from ircontrolltype where controllTypeName = :controllTypeName";
		Map<String,Object> params1 = new HashMap<String,Object>();
		params1.put("controllTypeName",irbranddictionary.getIrtype());
		List<IRControlltype> t1 = irControllTypeDao.findSql(sql1, params1,IRControlltype.class);
		String sql = "select * from irbranddictionary where 1=1";
		Map<String,Object> params = new HashMap<String,Object>();
		if(t1.size()>0){
			sql=sql+" and IRType like :IRType";
			params.put("IRType",t1.get(0).getId());
		}
		if(StringUtils.isNotBlank(irbrand.getBrandName())){
			sql=sql+" and (BrandCN like :brandName";
			params.put("brandName","%"+irbrand.getBrandName()+"%");
		}
		if(StringUtils.isNotBlank(irbrand.getEnbrandName())){
			sql=sql+" or BrandEN like :enbrandName";
			params.put("enbrandName", "%"+irbrand.getEnbrandName()+"%");
		}
		if(StringUtils.isNotBlank(irbrand.getEnbrandName())){
			sql=sql+" or NameIndex like :NameIndex";
			params.put("NameIndex", "%"+irbrand.getRegion()+"%");
		}
		if(StringUtils.isNotBlank(irbrand.getEnbrandName())){
			sql=sql+" or UnitCode like :UnitCode)";
			params.put("UnitCode", "%"+irbrand.getFirstchar()+"%");
		}
		List<IRBrand> t = irBrandDao.executeSql(sql, params);
		return t;
	}

	@Override
	public List<Map> getBrandByIRType(Integer irType, String brandName,int pageIndex, int pageSize)
			throws Exception {
		StringBuilder sql = new StringBuilder("select a.* from irbrand a left join irmodel b ");
		sql.append("on a.id = b.brandId ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(irType != null && StringUtils.isNotBlank(brandName)) {
			sql.append("where b.controllType= :controllType and a.brandName like :brandName ");
			params.put("controllType", irType);
			params.put("brandName","%"+brandName+"%");
		}
		else if(irType != null){
			sql.append("where b.controllType= :controllType ");
			params.put("controllType", irType);
		}
		else if(StringUtils.isNotBlank(brandName)){
			sql.append("where a.brandName like :brandName ");
			params.put("brandName","%"+brandName+"%");
		}
		sql.append("limit ").append(pageIndex).append(",").append(pageSize);
		List<Map> IRbrandList = mapDao.executeSql(sql.toString(), params);
		return IRbrandList;
	}
	@Override
	public void addUnMatch(String houseIeee,String ieee,String ep,Integer hadaemonIrindex,String data,String createtime) throws Exception {
		++batchCount;
		sql.append("('").append(houseIeee).append("','").append(ieee).append("','")
		.append(ep).append("',").append(hadaemonIrindex).append(",'")
		.append(data).append("','").append(createtime).append("'),");
		if(batchCount >= cacheSize) {
			dingshiaddUnMatch();
//			batchCount = 0;
//			StringBuilder tmpSqlB = this.sql;
//			this.sql = new StringBuilder(cacheSize * 1000);
//			final String tmpSql = tmpSqlB.deleteCharAt(tmpSqlB.length() - 1).toString();
////			new Thread() {
////	    		public void run() {
//	    			log.info("IR batch add notencode data begin" );
//					String tmpStr = "insert into irmatch(houseIeee,ieee,ep,hadaemonIrindex,data,createtime) value";
//					tmpStr = tmpStr + tmpSql;
//					Connection conn = null;
//	    			Statement stmt = null;
//	    			try {
//	    				Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
//	    				conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
//	    						PropertiesUtil.getProperty("jdbc.user"),
//	    						PropertiesUtil.getProperty("jdbc.password"));
//	    				stmt = conn.createStatement();
//	    				int i = stmt.executeUpdate(tmpStr);
//	    				log.info("IR batch add notencode data end, result: " + i);
//	    			} catch(Exception e) {
//	    				log.info("IR batch add notencode data exception", e);
//	    			} finally {
//	    				try {
//	    					if(stmt != null)
//	    						stmt.close();
//	    					if(conn != null)
//	    						conn.close();
//	    				} catch(Exception e) {
//	    					log.info("jdbc close exception", e);
//	    				}
//	    			}
//	    			log.info("执行IR保存  end");
////	    		}
////	    	}.start();
		}
	}	
	/**
	 * 定时执行IR保存
	 */
	@Override
	public int dingshiaddUnMatch() {
		if(sql.length()==0){
			log.info("定时执行IR保存，缓存为空");
			return 0;
		}
		batchCount = 0;
		StringBuilder tmpSqlB = this.sql;
		this.sql = new StringBuilder(cacheSize * 1000);
		final String tmpSql = tmpSqlB.deleteCharAt(tmpSqlB.length() - 1).toString();
		Thread th = new Thread() {
			@Override
			public void run() {
				log.info("IR batch add notencode data begin" );
				String tmpStr = "insert into irmatch(houseIeee,ieee,ep,hadaemonIrindex,data,createtime) value";
				tmpStr = tmpStr + tmpSql;
				Connection conn = null;
				Statement stmt = null;
				try {
					Class.forName(PropertiesUtil.getProperty("jdbc.driverClass"));
					conn = DriverManager.getConnection(PropertiesUtil.getProperty("jdbc.jdbcUrl"),
							PropertiesUtil.getProperty("jdbc.user"),
							PropertiesUtil.getProperty("jdbc.password"));
					stmt = conn.createStatement();
		    		int i = stmt.executeUpdate(tmpStr);
		    		log.info("IR batch add notencode data end, result: " + i);
		    	} catch(Exception e) {
		    		log.info("IR batch add notencode data exception", e);
		    	} finally {
		    		try {
		    			if(stmt != null)
		    				stmt.close();
		    			if(conn != null)
							conn.close();
		    		} catch(Exception e) {
		    			log.info("jdbc close exception", e);
		    		}
		    	}
		    	log.info("定时执行IR保存  end");
			}
		};
    	th.setName("saveIR");
    	th.start();    	
    	return 1;
	}
	
	@Override
	public String uploadIRxml(IRTempData irTmpData) throws Exception {
		String irUID = irTmpData.getHouseIEEE().substring(10) + irTmpData.getDeviceIEEE().substring(10) +
						irTmpData.getEp() + irTmpData.getIrType();
		//读取新的文件
		InputStream is = null;
		File newFile = new File(PropertiesUtil.getProperty("shareIRxmlPath") + irTmpData.getFileName());
		if(!newFile.exists()) {
			return null;
		}
		//检索记录是否存在
		String hql = "FROM IRTempData WHERE irUID = :irUID";
		Map<String, Object> params = new HashMap<>();
		params.put("irUID", irUID);
		IRTempData irTmpData_ = irTempDataDao.get(hql, params);
		SAXReader reader = new SAXReader();
		try {
			is = new FileInputStream(newFile);
			Document doc = reader.read(is);
			Element mainRecord = doc.getRootElement();
			Element brand = mainRecord.element("Brand");
			String brandName = brand.getText();
			Element model = mainRecord.element("Style");
			String modelName = model.getText();
			//中文转码
			if(brandName.equals(new String(brandName.getBytes("iso8859-1"), "iso8859-1"))) {
				brandName=new String(brandName.getBytes("iso8859-1"),"gbk");
		    }
			//中文转码
			if(modelName.equals(new String(modelName.getBytes("iso8859-1"), "iso8859-1"))) {
				modelName=new String(modelName.getBytes("iso8859-1"),"gbk");
			}
			if(irTmpData_ == null) {
				irTmpData.setBrandName(brandName);
				irTmpData.setModelName(modelName);
				irTmpData.setIrUID(irUID);
				irTempDataDao.save(irTmpData);
			}
			else {
				irTmpData_.setBrandName(brandName);
				irTmpData_.setModelName(modelName);
				irTmpData_.setFileName(irTmpData.getFileName());
				irTempDataDao.update(irTmpData_);
			}
		} 
		finally {
			try {
				if(is != null)
					is.close();
			} catch(Exception e) {
				throw e;
			}
		}
		return irUID;
	}
	
	@Override
	public String downloadIRxml(Map<String, Object> params) throws Exception {
		String irUID = (String) params.get("irUID");
		String hql = "FROM IRTempData WHERE irUID = :irUID";
		Map<String, Object> param = new HashMap<>();
		param.put("irUID", irUID);
		IRTempData irTmpData_ = irTempDataDao.get(hql, param);
		if(irTmpData_ != null) {
			String sharePath = PropertiesUtil.getProperty("shareIRxmlPath");
			String tmpFilePath = sharePath + irTmpData_.getFileName();
			File tmpFile = new File(tmpFilePath);
			if(tmpFile.exists()) {
				sharePath = sharePath.substring(sharePath.lastIndexOf("/", sharePath.length() - 2));
				String downloadUrl = "ftp://" + PropertiesUtil.getProperty("ftpUser") + ":" +
						PropertiesUtil.getProperty("ftpPass") + "@" +
						PropertiesUtil.getProperty("ftpIp") + ":" + PropertiesUtil.getProperty("ftpPort") +
						sharePath + irTmpData_.getFileName();
				return downloadUrl;
			}
			else {
				return abtainIRXmlData(irTmpData_.getBrandName(), irTmpData_.getModelName());
			}
		}
		return "";
	}
	
	@Override
	public int getIRTempCount(String searchText) throws Exception {
		StringBuilder sql = new StringBuilder("select count(*) count from irtempdata a ");
		sql.append("left join ircontrolltype c on a.irType = c.id ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(searchText)) {
			sql.append("where a.brandName like :searchText or ");
			sql.append("a.modelName like :searchText");
			params.put("searchText", "%" + searchText + "%");
		}
		List<Map> irList = mapDao.executeSql(sql.toString(), params);
		return ((BigInteger)irList.get(0).get("count")).intValue();
	}
	
	@Override
	public List<Map> queryIRTemp(String searchText, int startRow, int pageSize, String orderBy) {
		StringBuilder sql = new StringBuilder("select a.*, c.controllTypeName from irtempdata a ");
		sql.append("left join ircontrolltype c on a.irType = c.id ");
		Map<String, Object> params = new HashMap<String, Object>();		
		if(StringUtils.isNotBlank(searchText)) {
			sql.append("where a.brandName like :searchText or ");
			sql.append("a.modelName like :searchText");
			params.put("searchText", "%" + searchText + "%");
		}
		if(StringUtils.isNotBlank(orderBy)) {
			sql.append(" order by ").append(orderBy).append(" ");
		} 
		else {
			sql.append(" order by a.id desc ");
		}
		sql.append("limit ").append(startRow).append(",").append(pageSize);
		List<Map> irList = mapDao.executeSql(sql.toString(), params);
		return irList;
	}
	
	@Override
	public int deleteIRTemp(String id) throws Exception {
		String hql = "FROM IRTempData WHERE id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", Long.valueOf(id));
		IRTempData irTD = irTempDataDao.get(hql, params);
		File file = new File(PropertiesUtil.getProperty("shareIRxmlPath") + irTD.getFileName());
		if(file.exists()) {
			file.delete();
		}
		String sql = "DELETE FROM irtempdata WHERE id = :id";
		return mapDao.executeSql2(sql, params);
	}
	
	@Override
	public int passIRTemp(String id, String userId) throws Exception {
		String hql = "FROM IRTempData WHERE id = :id";
		Map<String, Object> params = new HashMap<>();
		params.put("id", Long.valueOf(id));
		IRTempData irTempData = irTempDataDao.get(hql, params);
		if(irTempData != null) {
			File irTempFile = new File(PropertiesUtil.getProperty("shareIRxmlPath") + irTempData.getFileName());
			if(irTempFile.exists()) {
				InputStream is = null;
				SAXReader reader = new SAXReader();
				try {
					is = new FileInputStream(irTempFile);
					Document doc = reader.read(is);
					Map<String, Object> params2 = new HashMap<>();
					params2.put("brandName", irTempData.getBrandName());
					params2.put("modelName", irTempData.getModelName());
					if(isModelExist(doc, params2) == null) {
						Map<String, Object> result = showIRxml(doc);
						Map<String, Object> pMap = new HashMap<>();
						pMap.put("userId", userId);
						pMap.put("checked", '1');
						pMap.put("brand", result.get("brandName"));
						pMap.put("model", result.get("modelName"));
						String brandENname = (String) result.get("brandENname"); 
						if("brandENname".equals(brandENname)) {
							pMap.put("brandEN", "");
						}
						else {
							pMap.put("brandEN", brandENname);
						}
						int deviceTypeId = Integer.parseInt((String) result.get("DeviceTypeid"));
						if(deviceTypeId == 2) {
							char letter = brandENname.charAt(0);
							String unitCodeName = (String) result.get("UnitCodeName");
							if("其他地区".equals(unitCodeName)) {
								pMap.put("NameIndex", "#");
							}
							else {
								pMap.put("NameIndex", letter + "");
							}
							pMap.put("UnitCode", unitCodeName);
						}
						else {
							char letter = brandENname.charAt(0);
							if(Character.isDigit(letter)) {
								pMap.put("UnitCode", "#");
								pMap.put("NameIndex", "#");
							}
							else {
								pMap.put("UnitCode", letter + "");
								pMap.put("NameIndex", letter + "");
							}
						}
						
						if(saveIRxml(doc, userId, "1", new Long(irTempFile.length() / 1024L).intValue(), pMap) == 1) {
							//更新认证状态
							irTempData.setChecked(1);
							irTempDataDao.update(irTempData);
						}
					} 
					else {
						//更新认证状态
						irTempData.setChecked(1);
						irTempDataDao.update(irTempData);
					}
				}
				finally {
					try {
						if(is != null)
							is.close();
					} catch(Exception e) {
						throw e;
					}
				}
			}
			else {
				return -1;
			}
		}
		else {
			return -2;
		}
		return 1;
	}	
}
