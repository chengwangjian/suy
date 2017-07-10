package org.jbx.test.servlet;

import java.io.IOException;  

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
public class setServletToJsp extends  HttpServlet{  
  
    @Override  
    protected void service(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
          
        String data="{\"barAge\":2039073210,\"barDate\":1375259684828,\"barName\":\"sss_0.37491536\"}";  
        req.setAttribute("json", data);
        req.getRequestDispatcher("/getServletToJsp").forward(req, resp);  
    }  
  
}  
