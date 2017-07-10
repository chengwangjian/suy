package org.jbx.test.servlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.JSON;

import java.io.IOException;  
import java.io.PrintWriter;  
import java.util.Map;
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
public class getServletToJsp extends HttpServlet{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(getServletToJsp.class);  
  
    @Override  
    protected void service(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
          
/*        resp.setContentType("text/html;charset=gb2312");  
        PrintWriter out=resp.getWriter();  
          
        String name=req.getParameter("name");  
        String pwd=req.getParameter("pwd");   
          
        out.println("<html>");  
        out.println("<body>");  
        out.println(name);  
        out.println("<hr>");  
        out.println(pwd);   
        out.println("</body>");  
        out.println("</html>");         
                out.flush();  
                
       PropertyConfigurator.configure("D:/log4j.properties");
       logger.info(name);
       logger.info(pwd);*/
       
       
       String json=(String)req.getAttribute("json");        
       resp.setContentType("text/html;charset=gb2312");  
       PrintWriter out=resp.getWriter();  
         
       out.println("<html>");  
       out.println("<body>");  
       out.println(json);  
       out.println("</body>");  
       out.println("</html>");  
               out.flush();  
       
       PropertyConfigurator.configure("D:/log4j.properties");        
       Map<String,Object> map1 = (Map<String,Object>)JSON.parse(json); 
       for (String key : map1.keySet()) { 
    	   logger.info(key+":"+map1.get(key)); 
       }
    }  
        
}  