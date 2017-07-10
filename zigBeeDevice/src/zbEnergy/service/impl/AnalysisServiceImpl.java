package zbEnergy.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import zbEnergy.service.AnalysisServiceI;
import zbHouse.model.Device;
import zbHouse.pageModel.Analysis;
import zbHouse.util.TestLog4j;

@Service("analysisService")
public class AnalysisServiceImpl implements AnalysisServiceI {
    private static final Logger log = Logger.getLogger(AnalysisServiceImpl.class);
	private BaseDaoI<Map> temperature_hum;
	public BaseDaoI<Map> getTemperature_hum() {
		return temperature_hum;
	}
	@Autowired
	public void setTemperature_hum(BaseDaoI<Map> temperature_hum) {
		this.temperature_hum = temperature_hum;
	}
	private BaseDaoI<Analysis> analysisDao;

	public BaseDaoI<Analysis> getAnalysisDao() {
		return analysisDao;
	}
	@Autowired
	public void setAnalysisDao(BaseDaoI<Analysis> analysisDao) {
		this.analysisDao = analysisDao;
	}

	@Override
	public Device keyUpdate(Device device) {
			
		return device;
		// TODO Auto-generated method stub		
	}
	
	@Override
	public Map findAll(Analysis analysis,Map<String, Object> params) {
		
		//hql="{CALL EnergyStatist('111')}";
		//List l =analysisDao.executeSql(hql);
		Map<String, Object> rt = new HashMap<String, Object>();
		String hql = "All";	
		hql = addWhere(hql, analysis, params);
		List lt =analysisDao.executeSql(hql, params);
		rt.put("total",lt);
		return rt;
		// TODO Auto-generated method stub		
	}

	@Override
	public Map findEach(Analysis analysis,Map<String, Object> params) {
		
		//hql="{CALL EnergyStatist('a' ,'1' ,'111' ,'2013-8-19' ,'2013-8-21')}";
		//List l =analysisDao.executeSql(hql);
		Map<String, Object> rt = new HashMap<String, Object>();
		String hql = "Each";
		hql = addWhere(hql, analysis, params);
		List lt1 =analysisDao.executeSql(hql, params);
		rt.put("records", lt1);
		hql = "Each";
		hql = addWhere2(hql, analysis, params);
		List lt2 =analysisDao.executeSql(hql, params);
		rt.put("summary", lt2.get(0));
		return rt;
		// TODO Auto-generated method stub		
	}
	
	@Override
	public Map findRegion(Analysis analysis,Map<String, Object> params) {
		
		//hql="{CALL EnergyStatist('111' ,'xm')}";
		//List l =analysisDao.executeSql(hql);
		Map<String, Object> rt = new HashMap<String, Object>();
		String hql = "Region";		
		hql = addWhere(hql, analysis, params);
		List lt =analysisDao.executeSql(hql, params);
		rt.put("region",lt);
		return rt;
	}
	
	private String addWhere(String hql, Analysis analysis,Map<String, Object> params) {
		if (analysis!= null){
			if(hql.equalsIgnoreCase("All")){
				params.put("houseIeee", analysis.getHouseIeee());
				hql = "{CALL EnergyTotal(:houseIeee)}";	
			}else if(hql.equalsIgnoreCase("Each")){
				params.put("dateType", analysis.getDateType());			
				params.put("deviceType", analysis.getDeviceType());			
				params.put("houseIeee", analysis.getHouseIeee());			
				params.put("startTime", analysis.getStartTime());
				params.put("endTime", analysis.getEndTime());			
				hql = "{CALL EnergyStatist(:dateType ,:deviceType ,:houseIeee ,:startTime ,:endTime)}";			
			}else if(hql.equalsIgnoreCase("Region")){
				params.put("houseIeee", analysis.getHouseIeee());
				params.put("regionCode", analysis.getRegionCode());				
				hql = "{CALL EnergyRegion(:houseIeee, :regionCode)}";					
			}
		}
		TestLog4j.testInfo(analysis.getHouseIeee()+"-"+hql);
		return hql;
	}
	private String addWhere2(String hql, Analysis analysis,Map<String, Object> params) {
		if (analysis!= null){
			if(hql.equalsIgnoreCase("Each")){
				params.put("dateType", analysis.getDateType());			
				params.put("deviceType", analysis.getDeviceType());			
				params.put("houseIeee", analysis.getHouseIeee());			
				params.put("startTime", analysis.getStartTime());
				params.put("endTime", analysis.getEndTime());			
				hql = "{CALL EnergyStatisticSum(:dateType ,:deviceType ,:houseIeee ,:startTime ,:endTime)}";				
			}
		}
		TestLog4j.testInfo(analysis.getHouseIeee()+"-"+hql);
		return hql;
	}
	@Override
	public Map temperature_hum_valuelist(Map<String, Object> params) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableName = "deviceAttributeHistory_" + (String) params.get("houseIEEE") + "_" + year;
		StringBuffer sql = new StringBuffer();
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		Map<String, Object> paramMp = new HashMap<String, Object>();
		//温度
		Calendar c = Calendar.getInstance(); 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String timString=(String)params.get("starttime");
		//timString=timString.r
		Date sta=new Date(),end=new Date();
		Date da;
		try {
			da = format.parse(timString);
			sta=da;
			c.setTime(da);
	
			String dString=((String)params.get("type"));
			if (dString.equals("0")) {
				
				
				end=DateUtils.addHours(da, 24);
			}
			if ( dString.equals("1")) {
				  Date d = null;

				  try

				  {

				   d =sta;

				  }

				  catch(Exception e)

				  {
					  log.info("temperature_hum_valuelist", e);
//				   e.printStackTrace();

				  }

				  Calendar cal = Calendar.getInstance();

				  cal.setTime(d);

				  //关于DAY_OF_WEEK的说明

				  //Field number for get and set indicating 

				  //the day of the week. This field takes values 

				  //SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, 

				  //and SATURDAY

				  cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
				  sta=cal.getTime();
				  c.setTime(sta);
				  cal.add(Calendar.DATE, 6);
				  end=cal.getTime();
				
			}
			if ( dString.equals("2")) {
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); 
				String dasString=(String) params.get("starttime");
				String[] dfgStrings=dasString.split("-");
				String dyear=dfgStrings[0]; String dmouth=dfgStrings[1];
				Calendar rightNow = Calendar.getInstance();
				try{ 
					rightNow.setTime(simpleDate.parse(dyear+"/"+dmouth));
					}
				catch(ParseException e){ 
//					e.printStackTrace();
					log.info("temperature_hum_valuelist", e);
					} 			
				int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);//根据年月 获取月份天数  
				sta=format.parse(dyear+"-"+dmouth+"-01 00:00:00");
				c.setTime(sta);
				
				c.add(Calendar.DATE, days-1);
			}
			if ( dString.equals("3")) {
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy"); 
				String dasString=(String) params.get("starttime");
				String[] dfgStrings=dasString.split("-");
				String dyear=dfgStrings[0]; String dmouth=dfgStrings[1];
				Calendar rightNow = Calendar.getInstance();
				try{ 
					rightNow.setTime(simpleDate.parse(dyear+"/01"));
					}
				catch(ParseException e){ 
//					e.printStackTrace(); 
					log.info("temperature_hum_valuelist", e);
					} 			
				int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);//根据年月 获取月份天数  
				sta=format.parse(dyear+"-"+dmouth+"-01 00:00:00");
				c.setTime(sta);
				c.add(Calendar.MONTH, 11);
				end=c.getTime();
			}
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			log.info("temperature_hum_valuelist", e1);
		}
		
		 //c.add(c.MONDAY, 1);//属性很多也有月等等，可以操作各种时间日期   
		String selectfromString="select max(value+0.0) as maxvale,opdatetime from ";
		sql.append(selectfromString+tableName+" where 1=1 and cluster_id='0402' ");
		String dd=(String) params.get("room_id");
		if (StringUtils.isNotBlank((String) params.get("room_id"))) {
			sql.append(" and room_id=:room_id ");
			paramMp.put("room_id",params.get("room_id"));
		}
		if (StringUtils.isNotBlank((String) params.get("starttime"))) {
			sql.append(" and opdatetime BETWEEN:starttime and :endtime");
			paramMp.put("starttime",sta);
			paramMp.put("endtime",end);
		}
		List<Map> t = temperature_hum.executeSql(sql.toString(), paramMp);
	    String sqlall=sql.toString();
	    String sqlmin2=sqlall.replace(selectfromString, "select min(value+0.0) as minvalue,opdatetime from ");
	    System.out.println("sql数据"+sqlmin2);
	    List<Map> min = temperature_hum.executeSql(sqlmin2, paramMp);
	    String sqlavg=sqlmin2.replace("select min(value+0.0) as minvalue,opdatetime from ", "select avg(value+0.0) as avgvalue from ");
	    System.out.println("sql数据"+sqlavg);
	    List<Map> avg = temperature_hum.executeSql(sqlavg, paramMp);
	    t.add(min.get(0));
	    t.add(avg.get(0));
	    //湿度
	    List<Map> humlis=new ArrayList<Map>();
	    String sqlhum_max=sqlall.replace("cluster_id='0402'", "cluster_id='0405'");
	    List<Map>hum_max= temperature_hum.executeSql(sqlhum_max, paramMp);
	    humlis.add(hum_max.get(0));
	    String sqlhum_minString=sqlhum_max.replace(selectfromString, "select min(value+0.0) as minvalue,opdatetime from ");
	    List<Map>hum_min= temperature_hum.executeSql(sqlhum_minString, paramMp);
	    humlis.add(hum_min.get(0));
	    String sqlhum_agvString=sqlhum_minString.replace("select min(value+0.0) as minvalue,opdatetime from ", "select avg(value+0.0) as avgvalue from ");
	    List<Map>hum_avg= temperature_hum.executeSql(sqlhum_agvString, paramMp);
	    humlis.add(hum_avg.get(0));
	    Map<String, Object> statistics=new HashMap<String, Object>();
	    statistics.put("tem_min",t.get(1).get("minvalue")==null?"0":t.get(1).get("minvalue"));
	    statistics.put("tem_min_time",t.get(1).get("opdatetime")==null?"0":t.get(1).get("opdatetime"));
	    statistics.put("tem_max",t.get(0).get("maxvale")==null?"0":t.get(0).get("maxvale"));
	    statistics.put("tem_max_time",t.get(0).get("opdatetime")==null?"0":t.get(0).get("opdatetime"));
	    statistics.put("tem_avg",Float.parseFloat(dcmFmt.format(t.get(2).get("avgvalue")!=null?t.get(2).get("avgvalue"):0)));
	    statistics.put("hum_min",humlis.get(1).get("minvalue")==null?"0":humlis.get(1).get("minvalue"));
	    statistics.put("hum_min_time",humlis.get(1).get("opdatetime")==null?"0":humlis.get(1).get("opdatetime"));
	    statistics.put("hum_max",humlis.get(0).get("maxvale")==null?"0":humlis.get(0).get("maxvale"));
	    statistics.put("hum_max_time",humlis.get(0).get("opdatetime")==null?"0":humlis.get(0).get("opdatetime"));
	    statistics.put("hum_avg",Float.parseFloat(dcmFmt.format(humlis.get(2).get("avgvalue")!=null?humlis.get(2).get("avgvalue"):0)));
	    statistics.put("houseieee", params.get("houseIEEE"));
	    statistics.put("roomid",(String)params.get("room_id")==null?"0":(String)params.get("room_id"));
	    statistics.put("starttime", params.get("starttime"));
	    statistics.put("endtime", params.get("endtime"));
	    statistics.put("type", params.get("type"));
	    List<List<Map>> lis=new ArrayList<List<Map>>();
	    try {
			lis=humtemtable(params);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			log.info("temperature_hum_valuelist", e);
		}
	    statistics.put("tem",lis.get(0));
	    statistics.put("hum",lis.get(1));
	    if (statistics != null) {
			return statistics;
		}
		return null;
	}	
	public List<List<Map>> humtemtable(Map<String, Object> params) throws ParseException
	{
		List<Map> humlis=new ArrayList<Map>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<Map> temList=new ArrayList<Map>();
		List<Map> humList=new ArrayList<Map>();
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		//温度
		Calendar c = Calendar.getInstance(); 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String timString=(String)params.get("starttime");
		//timString=timString.r
		Date da=format.parse(timString);
		c.setTime(da);
		Date sta=da,end;
		 //c.add(c.MONDAY, 1);//属性很多也有月等等，可以操作各种时间日期   
		int couttype=0;
		int cdae=0;
		String dString=((String)params.get("type"));
		if (dString.equals("0")) {
			couttype=24;
			//DateUtils.addHours(da, 1);
		}
		if ( dString.equals("1")) {
			 

			  Calendar cal = Calendar.getInstance();

			  cal.setTime(sta);

			  //关于DAY_OF_WEEK的说明

			  //Field number for get and set indicating 

			  //the day of the week. This field takes values 

			  //SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, 

			  //and SATURDAY

			  cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			  sta=cal.getTime();
			  c.setTime(sta);
			couttype=7;
			cdae=Calendar.DATE;
		}
		String dmouth,dyear;
		String[] dfgStrings;
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); 
		String dasString=(String) params.get("starttime");
		dfgStrings=dasString.split("-");
		dyear=dfgStrings[0];  dmouth=dfgStrings[1];
		Calendar rightNow = Calendar.getInstance();
		if ( dString.equals("2")) {
			try{ 
				rightNow.setTime(simpleDate.parse(dyear+"/"+dmouth));
				}
			catch(ParseException e){ 
//				e.printStackTrace(); 
				log.info("humtemtable", e);
				} 			
			int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);//根据年月 获取月份天数  
			c.setTime(sta);
			couttype=days;
			cdae=Calendar.DATE;
			sta=format.parse(dyear+"-"+dmouth+"-01 00:00:00");
		}
		if ( dString.equals("3")) {
			dmouth="01";
			rightNow.setTime(simpleDate.parse(dyear+"/"+dmouth));
			cdae=Calendar.MONTH;
			couttype=12;
			sta=format.parse(dyear+"-"+dmouth+"-01 00:00:00");
		}
		for (int i = 0; i <couttype; i++) {
			Map<String, Object> paramMp = new HashMap<String, Object>();
			String tableName = "deviceAttributeHistory_" + (String) params.get("houseIEEE") + "_" + year;
			StringBuffer sql = new StringBuffer();
			if (!((String)params.get("type")).equals("0")) {
				c.add(cdae,1);
		         Date temp_date = c.getTime();
		         end=temp_date;
			}
			else {
				
				end=DateUtils.addHours(sta, 1);
			}
			System.out.println("结束时间"+end);
	         String selectfromString="select max(value+0.0) as maxvale,opdatetime from ";
	 		sql.append(selectfromString+tableName+" where 1=1 and cluster_id='0402' ");
	 		if (StringUtils.isNotBlank((String) params.get("room_id"))) {
	 			sql.append(" and room_id=:room_id ");
	 			paramMp.put("room_id",params.get("room_id"));
	 		}
	 		if (StringUtils.isNotBlank((String) params.get("starttime"))) {
	 			sql.append(" and opdatetime BETWEEN:starttime and :endtime");
	 			paramMp.put("starttime",sta);
	 			paramMp.put("endtime",end);
	 		}
	 		List<Map> t = temperature_hum.executeSql(sql.toString(), paramMp);
	 	    String sqlall=sql.toString();
	 	    String sqlmin2=sqlall.replace(selectfromString, "select min(value+0.0) as minvalue,opdatetime from ");
	 	    System.out.println("sql数据"+sqlmin2);
	 	    List<Map> min = temperature_hum.executeSql(sqlmin2, paramMp);
	 	    String sqlavg=sqlmin2.replace("select min(value+0.0) as minvalue,opdatetime from ", "select avg(value+0.0) as avgvalue from ");
	 	    System.out.println("sql数据"+sqlavg);
	 	    List<Map> avg = temperature_hum.executeSql(sqlavg, paramMp);
	 	    t.add(min.get(0));
	 	    t.add(avg.get(0));
	 	    Map<String, Object> teM=new HashMap<String, Object>();
//	 	    teM.put("max", t.get(0).get("maxvale"));
//	 	    teM.put("min",t.get(1).get("minvalue"));
//	 	    teM.put("avg", t.get(2).get("avgvalue"));
	 		  teM.put("max", t.get(0).get("maxvale")!=null?t.get(0).get("maxvale"):0);
				
	 	 
	 		 teM.put("min",t.get(1).get("minvalue")!=null?t.get(1).get("minvalue"):0);
				
	 
	 		  teM.put("avg", Float.parseFloat(dcmFmt.format(t.get(2).get("avgvalue")!=null?t.get(2).get("avgvalue"):0)));
					
	 	    temList.add(teM);
	 	    //湿度
	 	    humlis=new ArrayList<Map>();
	 	    String sqlhum_max=sqlall.replace("cluster_id='0402'", "cluster_id='0405'");
	 	    List<Map>hum_max= temperature_hum.executeSql(sqlhum_max, paramMp);
	 	    humlis.add(hum_max.get(0));
	 	    t.add(hum_max.get(0));
	 	    String sqlhum_minString=sqlhum_max.replace(selectfromString, "select min(value+0.0) as minvalue,opdatetime from ");
	 	    List<Map>hum_min= temperature_hum.executeSql(sqlhum_minString, paramMp);
	 	    humlis.add(hum_min.get(0));
	 	    t.add(hum_min.get(0));
	 	    String sqlhum_agvString=sqlhum_minString.replace("select min(value+0.0) as minvalue,opdatetime from ", "select avg(value+0.0) as avgvalue from ");
	 	    List<Map>hum_avg= temperature_hum.executeSql(sqlhum_agvString, paramMp);
	 	    humlis.add(hum_avg.get(0));	
	 	    t.add(hum_avg.get(0));
	 	   Map<String, Object> humM=new HashMap<String, Object>();
	 	   
	 		  humM.put("max", humlis.get(0).get("maxvale")!=null?humlis.get(0).get("maxvale"):0);

	 

	 		 humM.put("min",humlis.get(1).get("minvalue")!=null?humlis.get(1).get("minvalue"):0);


	 		 humM.put("avg",Float.parseFloat(dcmFmt.format(humlis.get(2).get("avgvalue")!=null?humlis.get(2).get("avgvalue"):0)));
	 	humList.add(humM);
		    sta=end;
		}
		List<List<Map>> thList=new ArrayList<List<Map>>();
		thList.add(temList);
	    thList.add(humList);
	 return thList;
	}
}
