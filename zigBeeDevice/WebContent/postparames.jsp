<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'postparames.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<form action="/zigBeeDevice/controller/add_debug_log.do" method="post" enctype="multipart/form-data">
     <input type="file" value="浏览">
     json<input type="text" value="" name="json">
     callback<input type="text" value="" name="callback">
     houseIeeeSecret<input type="text" value="" name="houseIeeeSecret">
     <input type="submit" value="post请求"/>
    <div>post请求模拟请求</div>
    </form>
  </body>
</html>
