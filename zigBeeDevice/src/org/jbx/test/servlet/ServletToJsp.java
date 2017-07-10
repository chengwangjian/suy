package org.jbx.test.servlet;

import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
public class ServletToJsp extends HttpServlet{  
  
    @Override  
    protected void service(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        response.setContentType("text/html;charset=gb2312");  
        PrintWriter out=response.getWriter();  
        out.println("<html>");  
        out.println("<body>");  
        out.println("<form action='/demo/getServletToJsp' method='post'>");  
        out.println("<table align='center'>");  
          
        out.println("<tr>");  
        out.println("<td>");  
        out.println("username:");  
        out.println("</td>");  
        out.println("<td>");  
        out.println("<input type='text' name='name'>");  
        out.println("</td>");  
        out.println("</tr>");  
          
        out.println("<tr>");  
        out.println("<td>");  
        out.println("password:");  
        out.println("</td>");  
        out.println("<td>");  
        out.println("<input type='text' name='pwd'>");  
        out.println("</td>");  
        out.println("</tr>");  
          
        out.println("<tr >");  
        out.println("<td colspan='2'>");  
        out.println("<input type='submit' value='提交'>");  
        out.println("</td>");  
        out.println("</tr>");  
          
        out.println("</table>");  
        out.println("</form>");  
        out.println("</body>");  
        out.println("</html>");     
                out.flush();  
    }  
}  
  
