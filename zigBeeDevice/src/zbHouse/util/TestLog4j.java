package zbHouse.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.alibaba.fastjson.JSON;


public class TestLog4j {

	private static final Logger logger = Logger.getLogger(TestLog4j.class);

	public static void testInfo(Object object) {
		
		String folder = TestLog4j.class.getResource("/log4j.properties").toString();
	      if (folder.indexOf("file:") >= 0) {
	          folder = folder.substring(6);
	       } else if (folder.indexOf("jar:") >= 0) {
	          folder = folder.substring(4);
	       }
	       PropertyConfigurator.configure(folder);
		logger.info(object);
			
	}
	
	/**
	 * 输出callback json格式
	 * @author: zhuangxd
	 * 时间：2014-5-26 下午3:42:20
	 * @param object
	 * @param response
	 * @param request
	 */
	public static void writeJson2(Object object,HttpServletResponse response, HttpServletRequest request) {

		try {
			String jstr= JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
        	String callback = request.getParameter("callback"); 
        	jstr = callback + "(" + jstr + ")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
//			e.printStackTrace();
			logger.info("writeJson2", e);
		}
	}	

}
