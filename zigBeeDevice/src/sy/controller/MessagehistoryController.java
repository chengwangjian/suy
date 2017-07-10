package sy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.smarthome.model.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sy.service.MessagehistoryServiceI;
import zbHouse.pageModel.Json;

@Controller
@RequestMapping("/messagehistoryController")
public class MessagehistoryController
{
  private MessagehistoryServiceI messagehistoryServiceI;
  private static final Logger LOGGER = Logger.getLogger(MessagehistoryController.class);
  public MessagehistoryServiceI getMessagehistoryServiceI()
  {
    return this.messagehistoryServiceI;
  }
  @Autowired
  public void setMessagehistoryServiceI(MessagehistoryServiceI messagehistoryServiceI) {
    this.messagehistoryServiceI = messagehistoryServiceI;
  }
  @RequestMapping("/findList2")
  public void findList2(String json, HttpServletRequest request, HttpServletResponse response) throws IOException
  {
    Map map = JSON.parseObject(json);
    PrintWriter out = response.getWriter();
    response.setContentType("text/html;charset=utf-8");
    Json j = new Json(); 
    DataGrid dg = new DataGrid();
    String startRow = (String)map.get("startRow");
    String pageSize = (String)map.get("pageSize");
    String orderBy = (String)map.get("orderBy");
    try {
      if (StringUtils.isBlank(startRow))
        startRow = "0";
      if (StringUtils.isBlank(pageSize))
        pageSize = "30";
      List t = this.messagehistoryServiceI.findList2(startRow, pageSize, orderBy, map);
      String resultJson = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
      String callback = request.getParameter("callback");
      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
    } catch (Exception e) {
      LOGGER.info("findList2", e);
      String resultJson = "{\"result\":0}";
      String callback = request.getParameter("callback");
      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
    }
  }

  @RequestMapping("/exportMessageExcel")
  public void exportMessageExcel(String json, HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    LOGGER.info("json=" + json);
    String resultJson = "{\"result\":1}";
    PrintWriter out = null;
    String callback = request.getParameter("callback");
    try
    {
      Map paramMap = JSON.parseObject(json, Map.class);
      int t = this.messagehistoryServiceI.exportMessageExcel(paramMap, request, response);
    }
    catch (Exception e)
    {     
      LOGGER.info("exportMonLogExcel", e);
      try {
        out = response.getWriter();
      } catch (Exception ex) {
        LOGGER.info("exportMessageExcel getWriter", ex);
        resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
      }
      resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
      out.println(callback + "{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
    }
  }

  @RequestMapping("/successCount")
  public void successCount(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map param = JSON.parseObject(json, Map.class);
    PrintWriter out = response.getWriter();   
    try {
      List tList = this.messagehistoryServiceI.successCount(param);
      String resultJson = JSON.toJSONStringWithDateFormat(tList.get(0), "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
      String callback = request.getParameter("callback");
      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
    } catch (Exception e) {
      LOGGER.info("successCount", e);
      String resultJson = "{\"result\":0}";
      String callback = request.getParameter("callback");
      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
      return;
    }
  }
  @RequestMapping("/findmessage")
  public void findmessage(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map map = new HashMap();
    Map params = JSON.parseObject(json, Map.class);
    PrintWriter out = response.getWriter();
    response.setContentType("text/html;charset=utf-8");
    Json j = new Json();
    DataGrid dg = new DataGrid();
    String startRow = (String)params.get("startRow");
    String pageSize = (String)params.get("pageSize");
    String orderBy = (String)params.get("orderBy");
    try {
      if (StringUtils.isBlank(startRow))
        startRow = "0";
      if (StringUtils.isBlank(pageSize))
        pageSize = "30";
      map = JSON.parseObject(json);
      List t = this.messagehistoryServiceI.findmessage(startRow, pageSize, orderBy, params);
      String resultJson = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
      String callback = request.getParameter("callback");
      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
    } catch (Exception e) {
      LOGGER.info("findmessage", e);
      String resultJson = "{\"result\":0}";
      String callback = request.getParameter("callback");
      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
    }
  }
}