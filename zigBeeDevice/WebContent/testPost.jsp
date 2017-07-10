<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'testPost.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery.1.9.1.min.js"></script>
<script type="text/javascript" src="cloudManageWeb/jquary/encodeToGb2312.js"></script>
<script type="text/javascript" src="cloudManageWeb/jquary/aes.js"></script>
<script>
	var url = "http://192.168.1.75:8081/zigBeeDevice/";
	var cloudUrl = "http://192.168.1.26:8081/zigBeeDevice/";
	function chooseRequest() {
		switch($("#requestContext").val()){
			case "1":
				$("#url").val(cloudUrl+"farmDevice/pushDeviceInfo.do");
				$("#json").val('{"request_id": "1234","response_params": [{"allcount": 2,"curcount": 1,"house_ieee":"00137A0000012DFF","device_id": 7,"rid": -1,"picname": "","profileid": "0104","powersource": "DC source","curpowersource": 1,"curpowersourcelevel": 100,"devparam": {"node": {"ieee": "00137A000002EA2C","nwk_addr": "0000","name": "Z108A","manufactory": "netvox","zcl_version": "03","stack_version": "35","app_version": "30","hw_version": "0B","date_code": "20151126","model_id": "Z108AE3","solid_model_id": "Z103AE3C","node_type":0,"status":0},"ep": "0A","name": "Combined Interface","ep_model_id": "000010000080"},"device_level":1}]}');
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "2":
				$("#url").val(cloudUrl+"farmDevice/getEndPoint.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "3":
				$("#url").val(cloudUrl+"farmDevice/getChartData.do");
				$("#json").val('{"chartType":0,"startTime":"2016-05-06 00:00:00","endTime":"2016-05-07 00:00:00","houseIeee":"00137A0000012DFF","unit":0}');
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "4":
				$("#url").val(url+"farmUser/getUserInfo.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "5":
				$("#url").val(url+"farmUser/LoginUserInfo.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "6":
				//encryptPwd();
				$("#url").val(url+"farmUser/saveUser.do");
				$("#json").val("['00137A0000012DFF','00137A000007A65A']");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "7":
				//encryptPwd();
				$("#url").val(url+"farmUser/deleteUser.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "8":
				$("#url").val(url+"farmArea/getarealist.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "9":
				$("#url").val(url+"farmUser/getAppUserInfo.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "10":
				$("#url").val(url+"farmUser/modifyAppPassword.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "11":
				$("#url").val(cloudUrl+"deviceoperatehistoryHouseidYearController/getLogcat.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "12":
				$("#url").val(cloudUrl+"devicewarnhistoryHouseidYearController/getFarmHistoryWarnData.do");
				$("#json").val('{"beginDateTime":"2016-06-10 00:00:00","endDateTime":"2016-06-17 23:59:59","houseIeee":"00137A0000012DFF"}');
				$("#submitUrl").attr("action",$("#url").val());
				break;
			case "13":
				$("#url").val(cloudUrl+"deviceoperatehistoryHouseidYearController/getOperator.do");
				$("#json").val("{}");
				$("#submitUrl").attr("action",$("#url").val());
				break;
		}
	}
		
	function callCould() {
		var isEncrypt = $("#isEncrypt").val();
		var houseIEEE = $("#houseIEEE").val();
		if(isEncrypt=='true') {
			getpassword();
			var pwd = $("#password").val();
			var en = new AES.Crypto(getKey(pwd));
			switch($("#requestContext").val()){
			case "5":
				var user_name = $("#user_name").val();
				var password = $("#pwd").val();
				
				en = new AES.Crypto(getKey(password));
				
				var key = new AES.Crypto(getKey(user_name));
				password = key.encrypt(password);
				
				var encodeStr = "id="+$("#id").val()+"user_name="+user_name+"name="+$("#username").val()+"password="+password+"user_type="+$("#userType").val()+"callback="+$("#callback").val()+"json=" + $("#json").val() + "houseIeee=" + houseIEEE + "houseIeeeSecret=" + houseIEEE;
				var sign = en.encrypt(encodeStr);
				//alert("sign="+sign+"\ndecrypt="+en.decrypt(sign));
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'id':$("#id").val(),'user_name':user_name,'password':password,'user_type':$("#userType").val(),'name':$("#username").val(),'json':$("#json").val(),'encodemethod':'AES','sign':sign,'houseIeee':houseIEEE,'houseIeeeSecret':houseIEEE,'callback': $("#callback").val()},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
				break;
			case "6":
			case "7":
			case "8":
			case "9":
			case "10":
				var user_name = $("#user_name").val();
				var password = $("#pwd").val();
				
				var key = new AES.Crypto(getKey(user_name));
				password = key.encrypt(password);
				
				var encodeStr = "id="+$("#id").val()+"user_name="+user_name+"username="+$("#username").val()+"password="+password+"user_type="+$("#userType").val()+"callback="+$("#callback").val()+"json=" + $("#json").val() +"areas=" + $("#json").val() + "houseIeee=" + houseIEEE + "houseIeeeSecret=" + houseIEEE;
				var sign = en.encrypt(encodeStr);
				//$("body").append("sign= "+sign+"\ndecrypt= "+encodeStr);
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'id':$("#id").val(),'user_name':user_name,'password':password,'user_type':$("#userType").val(),'username':$("#username").val(),'json':$("#json").val(),'areas':$("#json").val(),'encodemethod':'AES','sign':sign,'houseIeee':houseIEEE,'houseIeeeSecret':houseIEEE,'callback': $("#callback").val()},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
				break;
			case "11":
				var user_name = $("#user_name").val();
				var startTime = $("#opStartTime").val();
				var endTime = $("#opEndTime").val();
				var pageIndex = $("#opPageIndex").val();
				var pageSize = $("#opPageSize").val();
				var encodeStr = "user_name="+user_name+"startTime="+startTime+"endTime="+endTime+"count=" + pageSize + "startIndex=" + pageIndex + "callback="+$("#callback").val();
				var sign = en.encrypt(encodeStr);
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'user_name':user_name,'startTime':startTime,'endTime':endTime,'count':pageSize,'startIndex':pageIndex,'encodemethod':'AES','sign':sign,'callback':$("#callback").val()},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
				break;
			case "12":
				var user_name = $("#user_name").val();
				var json = $("#json").val();
				var encodeStr = "user_name="+user_name+"json="+json+ "callback="+$("#callback").val();
				var sign = en.encrypt(encodeStr);
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'user_name':user_name,'json':json,'encodemethod':'AES','sign':sign,'callback':$("#callback").val()},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
				break;
			case "13":
				var user_name = $("#user_name").val();
				var userName = $("#username").val();
				var startTime = $("#opStartTime").val();
				var endTime = $("#opEndTime").val();
				var pageIndex = $("#opPageIndex").val();
				var pageSize = $("#opPageSize").val();
				var encodeStr = "user_name="+user_name+"username="+userName+"startTime="+startTime+"endTime="+endTime+"count=" + pageSize + "startIndex=" + pageIndex + "callback="+$("#callback").val();
				var sign = en.encrypt(encodeStr);
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'user_name':user_name,'username':userName,'startTime':startTime,'endTime':endTime,'count':pageSize,'startIndex':pageIndex,'encodemethod':'AES','sign':sign,'callback':$("#callback").val()},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
				break;
			case "1":
			case "4":
				var vendorCode = $("#vendorCode").val();
				en = new AES.Crypto(houseIEEE.substring(6) + vendorCode);
				var networkMode = $("#networkMode").val();
				var encodeStr = "json=" + $("#json").val() + "houseIeee=" + houseIEEE + "houseIeeeSecret=" + houseIEEE + "mode=" + networkMode +"callback=req1234";
				var sign = en.encrypt(encodeStr);
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'json':$("#json").val(),'encodemethod':'AES','sign':sign,'houseIeee':houseIEEE,'houseIeeeSecret':houseIEEE,'callback':'req1234','mode':networkMode},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
				break;
			case '2':
				pwd = $("#pwd").val();
				var pwdYu = pwd.length % 16;
				if(pwdYu != 0) {
					for(var i = 0; i < pwdYu; i++) {
						pwd += '0';
					}					
				}
				en = new AES.Crypto(getKey(pwd));
				var userName = $("#username").val();
				var pageIndex = $("#opPageIndex").val();
				var pageSize = $("#opPageSize").val();
				var encodeStr = "json=" + $("#json").val() + "houseIeee=" + houseIEEE + "houseIeeeSecret=" + houseIEEE + "pageIndex=" + pageIndex + "pageSize=" + pageSize + "callback=req1234user_name=" + userName;
				var sign = en.encrypt(encodeStr);
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'json':$("#json").val(),'encodemethod':'AES','sign':sign,'houseIeee':houseIEEE,'houseIeeeSecret':houseIEEE,'callback':'req1234','user_name':userName,'pageIndex':pageIndex,'pageSize':pageSize},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
				break;
			default:
				pwd = $("#pwd").val();
				var pwdYu = pwd.length % 16;
				if(pwdYu != 0) {
					for(var i = 0; i < pwdYu; i++) {
						pwd += '0';
					}					
				}
				en = new AES.Crypto(getKey(pwd));
				var userName = $("#username").val();
				var encodeStr = "json=" + $("#json").val() + "houseIeee=" + houseIEEE + "houseIeeeSecret=" + houseIEEE + "callback=req1234user_name=" + userName;
				var sign = en.encrypt(encodeStr);
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'json':$("#json").val(),'encodemethod':'AES','sign':sign,'houseIeee':houseIEEE,'houseIeeeSecret':houseIEEE,'callback':'req1234','user_name':userName},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
			}
		}
		else {
			
			switch($("#requestContext").val()){
			case "5":
			case "6":
			case "7":
			case "8":
				callCould0();
				break;
			default:
				$.ajax({
					type:"POST",
					url:$("#url").val(),
					async:false,
					data:{'json':encodeToGb2312($("#json").val()),'encodemethod':'NONE','sign':'aa','houseIeee':houseIEEE,'houseIeeeSecret':houseIEEE},
					success:function(msg){
						$("#result").val(msg.toString());
					},
					error:function(msg){
						$("#result").val(msg.toString());
					}
				});
			}
		}
	}
	
	function getKey(str) {
		var strLen = str.length;
		var keyStr = str;
		if(strLen < 16) {		
			var strSize = 16 - strLen;
			for(var i = 0; i < strSize; i++) {
				keyStr += "0";
			}
		}
		else if(strLen > 16) {
			keyStr = keyStr.substring(0, 16);
		}	
		return keyStr;
	}
	
	function callCould0() {
		$("#submitUrl").attr("action",$("#url").val());
		$("#submitUrl").submit();
	}
	 //根据用户名获取密码
	function getpassword(){
		var username = $("#user_name").val();
		var url = "/zigBeeDevice/farmUser/getpassword.do?username="+username;
		$.ajaxSettings.async = false;
		$.getJSON(url,function(rs){
			var result = rs.response_params.status;
			if(result==0){
				var password = rs.response_params.status_msg;
				$("#password").val(password);
			}
		});
	}
	</script>
</head>

<body>
	<form action="" method="post" id="submitUrl">
		<table>
			<tr>
				<th>是否需加密：</th>
				<td>
					<select id="isEncrypt" style="width:200px;height:30px;font-size:18px;">
						<option value="true" selected="selected">加密</option>
						<option value="false">不加密</option>
					</select>
					<span style="color:red;font-size:18px;"><strong>&nbsp;&nbsp;*&nbsp;&nbsp;（加*为必填或必选项）</strong></span>
				</td>
			</tr>
			<tr>
				<th>houseIEEE：</th>
				<td>
					<input id="houseIEEE" type="text" style="width:670px;height:30px;"/>
					<span style="color:red;font-size:18px;"><strong>&nbsp;&nbsp;*</strong></span>
				</td>
			</tr>
			<tr>
				<th>当前用户名：</th>
				<td>
					<input id="user_name" name="user_name" type="text" style="width:400px;height:30px;"/>
					<span style="color:red;font-size:18px;"><strong>&nbsp;*</strong></span>&nbsp;&nbsp;&nbsp;
					<strong>用户id：</strong><input id="id" name="id" type="text" value="-1" style="width:100px;height:30px;"/>
					<input type="hidden" id="password" value=""/>
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<label>用户名：</label><input id="username" name="username" type="text" style="width:160px;height:30px;"/>
					<label>密码：</label><input id="pwd" type="text" name="password" style="width:160px;height:30px;"/>
					<label>userType：</label><input id="userType" name="user_type" type="text" style="width:80px;height:30px;"/>
					<span style="color:red;font-size:16px;"><strong>（用户相关接口需填参数）</strong></span>
					<br><label>网络类型（1为嘉义网络，0位IES）</label><input type="text" id="networkMode" style="width:160px;height:30px;"/>
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<label>厂商代码：</label><input id="vendorCode" type="text" value="NETVOX" style="width:300px;height:30px;"/>
					<label>callback：</label><input id="callback" name="callback" type="text" value="1234" style="width:300px;height:30px;"/>
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<label>起始时间：</label><input id="opStartTime" type="text" value="2016-05-01 00:00:00" style="width:150px;height:30px;"/>
					<label>结束时间：</label><input id="opEndTime" type="text" value="2016-05-31 23:59:59" style="width:150px;height:30px;"/>
					<label>起始页：</label><input id="opPageIndex" type="text" value="1" style="width:50px;height:30px;"/>
					<label>每页行数：</label><input id="opPageSize" type="text" value="20" style="width:50px;height:30px;"/>
				</td>
			</tr>
			<tr>
				<th>选择请求：</th>
				<td><select id="requestContext" name="reqeustContext"
					style="width:200px;height:30px;font-size:18px;" onchange="chooseRequest();">
						<option>请选择</option>
						<option value="1">推送设备信息</option>
						<option value="2">获取设备列表</option>
						<option value="3">获取图表</option>
						<option value="4">获取用户信息</option>
						<option value="5">登陆</option>
						<option value="6">添加或修改用户</option>
						<option value="7">删除用户</option>
						<option value="8">获取区域列表</option>
						<option value="9">APP获取用户信息</option>
						<option value="10">APP修改老板账户密码</option>
						<option value="11">获取设备运行记录</option>
						<option value="12">农业获取告警历史记录</option>
						<option value="13">根据用户名获取操作历史记录</option>
				</select></td>
			</tr>
			<tr>
				<th>输入URL:</th>
				<td><input type="text" id="url"	style="width:670px;height:30px;" /></td>
			</tr>
			<tr>
				<th>json:</th>
				<td><textarea id="json" name="json" rows="5" cols="92"></textarea>
				</td>
			</tr>
			<tr>
				<th></th>
				<td><input type="button" value="提交" style="width:100px;height:30px;font-size:18px;" onclick="callCould()" id="submitCommit"/>
				</td>
			</tr>
			<tr>
				<th>返回结果：</th>
				<td><textarea id="result" name="result" rows="15" cols="120"></textarea>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
