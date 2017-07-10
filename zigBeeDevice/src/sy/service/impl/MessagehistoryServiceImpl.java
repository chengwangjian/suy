package sy.service.impl;

import java.io.BufferedOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sy.dao.BaseDaoI;
import sy.model.Messagehistory;
import sy.service.MessagehistoryServiceI;

@Service("messagehistoryService")
public class MessagehistoryServiceImpl implements MessagehistoryServiceI{
  private static final Logger logger = Logger.getLogger(MessagehistoryServiceImpl.class);
  private BaseDaoI<Map> mapDao;
  private BaseDaoI<Messagehistory> messagDaoI;

	public BaseDaoI<Messagehistory> getMessagDaoI() {
		return this.messagDaoI;
	}

	@Autowired
	public void setMessagDaoI(BaseDaoI<Messagehistory> messagDaoI) {
		this.messagDaoI = messagDaoI;
	}

	public BaseDaoI<Map> getMapDao() {
		return this.mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

  @Override
public List<Messagehistory> findList(String startRow, String pageSize, String orderBy, Map<String, Object> param)
  {
    Map params = new HashMap();
    StringBuilder hql = new StringBuilder("select count(*)  from messagehistory t where 1=1");
    String sText = (String)param.get("search");
    if (StringUtils.isNotBlank(sText)) {
      hql.append(" and (t.state = :state ");
      params.put("state", "%" + sText + "%");
      hql.append(" and (t.type = :type ");
      params.put("type", "%" + sText + "%");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = new Date();
      try {
        date = sdf.parse(param.get("starttime").toString());
        System.out.println(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 1);
        String strcal = sdf.format(calendar.getTime());
        System.out.println(param.get("starttime").toString());
        hql.append("and t.sendtime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
      } catch (ParseException e) {
    	  logger.info("findList", e);
//        e.printStackTrace();
      }
      if (StringUtils.isBlank(orderBy))
        hql.append(" order by t.id desc");
      else {
        hql.append(" order by ").append(orderBy);
      }
      hql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
    }
    List t = this.messagDaoI.executeSql(hql.toString(), params);
    if (t != null) {
      return t;
    }
    return null;
  }

  @Override
public List<Map> findList2(String startRow, String pageSize, String orderBy, Map<String, Object> param)
  {
	Map<String, Object> params = new HashMap<String, Object>();
    StringBuilder hql = new StringBuilder("select * from messagehistory t where (type=0 or type=1) ");
    if (StringUtils.isNotBlank((String)param.get("state"))) {
    	hql.append(" and t.state = :state ");
    	params.put("state", param.get("state"));
    }
    if (StringUtils.isNotBlank((String)param.get("type"))) {
    	hql.append(" and t.type = :type ");
    	params.put("type", param.get("type"));
    }
    if (StringUtils.isNotBlank((String)param.get("starttime"))) {
    	hql.append(" and t.sendtime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
    }
    if(StringUtils.isBlank(orderBy)) {
    	hql.append(" order by t.id desc");
    }
    else {
    	hql.append("order by ").append(orderBy);
    }
    hql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
    List<Map> t = this.mapDao.executeSql(hql.toString(), params);
    if (t != null) {
      return t;
    }
    return null;
  }

  @Override
public int exportMessageExcel(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response)
  {
    Map<String, Object> params = new HashMap<String, Object>();
    StringBuilder hql = new StringBuilder("select t.id,t.sendtime,t.type,t.phonenumber,t.content,t.state,t.remark from messagehistory t where t.type in (0,1)");
    if (StringUtils.isNotBlank((String)param.get("state"))) {
    	hql.append(" and t.state = :state ");
    	params.put("state", param.get("state"));
    }
    if (StringUtils.isNotBlank((String)param.get("type"))) {
    	hql.append(" and t.type = :type ");
    	params.put("type", param.get("type"));
    }
    if (StringUtils.isNotBlank((String)param.get("starttime"))) {
    	hql.append(" and t.sendtime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
    }
    hql.append(" order by t.sendtime desc ");
    List t = mapDao.executeSql(hql.toString(), params);
    BufferedOutputStream os = null;
    try
    {
      HSSFWorkbook workbook = new HSSFWorkbook();
      HSSFSheet sheet = workbook.createSheet();
      sheet.setColumnWidth(1, 6000);
      sheet.setColumnWidth(2, 6000);
      sheet.setColumnWidth(3, 6000);
      sheet.setColumnWidth(4, 6000);
      sheet.setColumnWidth(5, 6000);
      sheet.setColumnWidth(6, 6000);
      workbook.setSheetName(0, "短信列表");
      HSSFRow row = sheet.createRow(0);
      HSSFCellStyle titleStyle = workbook.createCellStyle();
      HSSFFont titleFont = workbook.createFont();
      titleFont.setBoldweight((short)700);
      titleStyle.setFont(titleFont);
      HSSFCell cell = row.createCell(0, 1);
      cell.setCellValue("发送时间");
      cell.setCellStyle(titleStyle);
      cell = row.createCell(1, 1);
      cell.setCellValue("类型");
      cell.setCellStyle(titleStyle);
      cell = row.createCell(2, 1);
      cell.setCellValue("手机号码");
      cell.setCellStyle(titleStyle);
      cell = row.createCell(3, 1);
      cell.setCellValue("短信内容");
      cell.setCellStyle(titleStyle);
      cell = row.createCell(4, 1);
      cell.setCellValue("状态");
      cell.setCellStyle(titleStyle);
      cell = row.createCell(5, 1);
      cell.setCellValue("备注");
      cell.setCellStyle(titleStyle);
      int messageSize = t.size();
      for (int i = 0; i < messageSize; i++) {
        row = sheet.createRow((short)(i + 1));
        cell = row.createCell(0, 1);
        cell.setCellValue(((Map)t.get(i)).get("sendtime")==null?"":((Map)t.get(i)).get("sendtime").toString());
        cell = row.createCell(1, 1);
        Object type = ((Map)t.get(i)).get("type");
        cell.setCellValue(type==null||type.toString().isEmpty()?"":Integer.parseInt(type.toString())==0? "预警短信":"系统短信");
        cell = row.createCell(2, 1);
        cell.setCellValue(((Map)t.get(i)).get("phonenumber")==null?"":((Map)t.get(i)).get("phonenumber").toString());
        cell = row.createCell(3, 1);
        cell.setCellValue(((Map)t.get(i)).get("content")==null?"":((Map)t.get(i)).get("content").toString());
        cell = row.createCell(4, 1);
        Object state = ((Map)t.get(i)).get("state");
        cell.setCellValue(state==null||state.toString().isEmpty()?"":Integer.parseInt(state.toString())==0? "失败":"成功");
        cell = row.createCell(5, 1);
        cell.setCellValue(((Map)t.get(i)).get("remark")==null?"":((Map)t.get(i)).get("remark").toString());
      }
      String fileName = "短信列表.xls";
      fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
      response.addHeader("Content-Disposition", "attachment;filename=" +fileName);
      response.setContentType("application/vnd.ms-excel;charset=utf-8");
      os = new BufferedOutputStream(response.getOutputStream());
      workbook.write(os);
      os.flush();
    } catch (Exception e) {
//      e.printStackTrace();
      logger.info("exportMessageExcel", e);
    }
    finally
    {
      try
      {
        if (os != null)
          os.close();
      } catch (Exception e) {
    	  logger.info("exportMessageExcel close BufferedOutputStream", e);
      }
    }
    return 0;
  }

  @Override
public List<Map> successCount(Map<String, Object> param) {
    StringBuffer hql = new StringBuffer("select count(*) as total,sum(case when t.state='0' then 1 else 0 end) as fail,sum(case when t.state='1' then 1 else 0 end) as success from messagehistory t where (type=0 or type=1) ");
    Map<String, Object> params = new HashMap<String, Object>();
    if(StringUtils.isNotBlank((String)param.get("state"))) {
    	hql.append("and t.state = :state ");
    	params.put("state", param.get("state"));
    }
    if(StringUtils.isNotBlank((String)param.get("type"))) {
    	hql.append("and t.type = :type ");
    	params.put("type", param.get("type"));
    }
    if (StringUtils.isNotBlank((String)param.get("starttime"))) {
    	hql.append("and t.sendtime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
    }
    List<Map> list = mapDao.executeSql(hql.toString(), params);
    return list;
  }

  @Override
public List<Map> findmessage(String startRow, String pageSize, String orderBy, Map<String, Object> params)
  {
    StringBuffer sql = new StringBuffer();
    sql.append("select t.* from messagehistory t where 1=1 ");
    Map parmMap = new HashMap();
    if (params.get("houseIeee") != null) {
      sql.append(" and t.houseIeee = :houseIeee ");
      parmMap.put("houseIeee", params.get("houseIeee"));
    }
    if (params.get("type") != null) {
      sql.append("and t.type = :type ");
      parmMap.put("type", params.get("type"));
    }
    if (params.get("messageid") != null) {
      sql.append("and t.messageid = :messageid ");
      parmMap.put("messageid", params.get("messageid"));
    }
    if (StringUtils.isBlank(orderBy))
      sql.append("order by t.id desc");
    else {
      sql.append("order by").append(orderBy);
    }
    sql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
    List t = this.mapDao.executeSql(sql.toString(), parmMap);
    if (t != null) {
      return t;
    }
    return null;
  }
}