
<%@ page import="
				com.alibaba.fastjson.JSON,
				java.util.Map" %>

<html>
  <head>
    <title>JSP 2.0 Expression Language - Functions</title>
  </head>
  <body>

      <form action="http://192.168.1.72:8080/demo/priceController/find.do" method="post">
<!--           name = <input type="text" name="name" value="111">
          password=<input type="text" name="pwd" value="222">  -->
          json=<input type="text" name="json" value="aaa" style="width:1111px;height:111px">   
          <input type="submit">
      </form>      
      <br>

 <%
/*  String name = request.getParameter ("name"); 
 String password = request.getParameter ("pwd");  
out.println(name);
out.println(password); */

/* String json = request.getParameter ("json");
out.println(json); */

/* String json="";
request.setAttribute("json",json) */;

%>
  </body>
</html>

