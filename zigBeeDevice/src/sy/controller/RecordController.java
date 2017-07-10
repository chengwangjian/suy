package sy.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.service.RecordServiceI;

@Controller
@RequestMapping("/recordController")
public class RecordController {
	private static final Logger LOGGER = Logger.getLogger(RecordController.class);
	
	private RecordServiceI recordService;
	public static int count = 1;

	public RecordServiceI getRecordService() {
		return recordService;
	}
	@Autowired
	public void setRecordService(RecordServiceI recordService) {
		this.recordService = recordService;
	}
	/**
	 * 页面分流器
	 * @author pengcq 2014-11-17
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * http://localhost:8081/zigBeeDevice/recordController/gotoURL.do?json={"urlId":1,"url":"222","ipaddress":"333","param":"444","optime":"2014-11-17 14:12:00"}
	 */
	@RequestMapping("/gotoURL")
	public void gotoURL(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
//		PrintWriter out=response.getWriter(); 
//		response.setContentType("text/html;charset=utf-8");  
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		
//		String urlStr = (String) HouseieeeListener.recordMap.get((long)count);
//		count++;
//		if (count > HouseieeeListener.recordMap.size()) {
//			count = 1;
//		}
//		
//		String param = "";
//        try {
//        	Historyrecord historyrecord = JSON.parseObject(json,Historyrecord.class); 
//        	Historyrecord t = recordService.HistoryrecordCacheSave(historyrecord);
//        	String url = TestHttpclient.getUrl(urlStr.toString(),"utf-8");
//        	//out.println(str);
//        	RequestDispatcher dispatcher = request.getRequestDispatcher(url + "?" + param);
//    		dispatcher.forward(request, response);
//        	String resultJson = "{\"result\":1}";//成功1 失败0
//        	//String resultJson = JSON.toJSONStringWithDateFormat(t,"yyyy-MM-dd HH:mm:ss");
//        	out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			LOGGER.info("urlrecord gotoURL log==" + resultJson);
//			
//		} catch (Exception e) {
//			LOGGER.info("gotoURL",e);
//			String resultJson = "{\"result\":0}";//成功1 失败0
//			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//		}   
	}
	
}
