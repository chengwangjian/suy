package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import sy.model.FarmWarnHandle;
import sy.service.FarmWarnHandleServiceI;

@Controller()
@RequestMapping("/FarmWarnHandleController")
public class FarmWarnHandleController {
	private static final Logger LOGGER = Logger.getLogger(FarmWarnHandleController.class);
	
	private FarmWarnHandleServiceI FarmWarnHandleService;
	
	public FarmWarnHandleServiceI getFarmWarnHandleService() {
		return FarmWarnHandleService;
	}
	
	@Autowired
	public void setFarmWarnHandleService(FarmWarnHandleServiceI farmWarnHandleService) {
		FarmWarnHandleService = farmWarnHandleService;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
	
	/*pc端查询获取字段*/
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Map<String,Object> jsonMap = JSON.parseObject(json,Map.class); 
            List<FarmWarnHandle> t = FarmWarnHandleService.find(jsonMap);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
	}    
	
	/*pc端添加和修改*/
     @RequestMapping("addupdate") 
     public void addUpdate(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
    	 int n ;
         PrintWriter out=response.getWriter();
         try {
         @SuppressWarnings("unchecked")
		Map<String,Object> jsonMap = JSON.parseObject(json,Map.class);
         n = FarmWarnHandleService.update(jsonMap);
         String resultJson= JSON.toJSONStringWithDateFormat(n, "yyyy-MM-dd HH:mm:ss");
         String callback = request.getParameter("callback");
	     out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
         } catch (Exception e) {
        	 LOGGER.info("find",e);
 			String resultJson = "{\"result\":1}";//成功0 失败1
 			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
         }
        

	}
}
