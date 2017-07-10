package org.smarthome.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jbx.test.TestLog4j;
import org.smarthome.domain.Releasehistory;
import org.smarthome.model.DataGrid;
import org.smarthome.model.Json;
import org.smarthome.services.ReleasehistoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.service.HouseServiceI;
import sy.util.FtpClient;
import sy.util.HouseieeeListener;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

import com.alibaba.fastjson.JSON;


@Controller			
@RequestMapping("/ReleasehistoryController")
public class ReleasehistoryController {

	private static final Logger LOGGER = Logger.getLogger(ReleasehistoryController.class);
	private ReleasehistoryServiceI releasehistory;	
	
	public ReleasehistoryServiceI getReleasehistory() {
		return releasehistory;
	}
	@Autowired
	public void setReleasehistory(ReleasehistoryServiceI releasehistory) {
		this.releasehistory = releasehistory;
	}
	
	private HouseServiceI hosueservice;
	
	public HouseServiceI getHosueservice() {
		return hosueservice;
	}
	@Autowired
	public void setHosueservice(HouseServiceI hosueservice) {
		this.hosueservice = hosueservice;
	}
	@RequestMapping("/add")
	public void showSave(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);	
		Json j = new Json();
		try {
			json=URLDecoder.decode(json, "utf-8");
			Releasehistory release=JSON.parseObject(json,Releasehistory.class);	
			Releasehistory rel = releasehistory.saveOrUpdate(release);
			if(rel.getHwVersion()==null){
				dg.put("status", -1);
				j.setResponse_params(dg);
			}
			else{
				j.setResponse_params(rel);
			}
		} catch (Exception e) {		
			LOGGER.info("showSave", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);				
		}
		writeJson(j,callback,response);		
		//request.setAttribute("j",j);			
	}

	@RequestMapping("/update")
	public void showUpdate(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);	
		Json j = new Json();
		try {
			json=URLDecoder.decode(json, "utf-8");
			Releasehistory release=JSON.parseObject(json,Releasehistory.class);	
			j.setResponse_params(releasehistory.keyUpdate(release));
		} catch (Exception e) {	
			LOGGER.info("showUpdate", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);				
		}
		writeJson(j,callback,response);		
		//request.setAttribute("j",j);			
	}	
	
	@RequestMapping("/delete")
	public void showDelete(String json,String callback,HttpServletRequest request,HttpServletResponse response) {	
		Map<String, Object> dg = new HashMap<String, Object>();
		//request.setAttribute("js",js);		
		Json j = new Json();
		try {
			Releasehistory release=JSON.parseObject(json,Releasehistory.class);				
			j.setResponse_params(releasehistory.delete(release));			
		} catch (Exception e) {
			LOGGER.info("showDelete", e);
			dg.put("status", 0);
			dg.put("status_msg", "fail");
			j.setResponse_params(dg);		
		}
		writeJson(j,callback,response);
		//request.setAttribute("j",j);		
	}
	@RequestMapping("/AssistanceUpdateVersion")
	public void Assistance(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();DataGrid dg = new DataGrid();
		try {
			json=URLDecoder.decode(json, "utf-8");
			map=JSON.parseObject(json,Map.class);	
			Releasehistory r=releasehistory.get(map);
//			Releasehistory r2=new Releasehistory();
//			BeanUtils.copyProperties(r,r2,new String[]{});
			String z203Json="{\"msg_type\":\"upgrade_auto\",\"msg\":"
					 +"{\"HWVersion\":\""+map.get("HWVersion")+"\",\"ID\":"+r.getId()+",\"UpdateTime\":\""+new Date()+"\",\"Version\":\""+r.getVersion()+"\",\"Description\":\""+r.getDescription()+"\",\"md5\":\""+r.getMd5()+"\",\"DownloadInfo\":\""+r.getDownloadInfo()+"\"}}";
			Map<String, Object> z203PMap = new HashMap<String, Object>();
			z203PMap.put("context", z203Json);
			z203PMap.put("username", map.get("HouseIEEE"));
			String houseIEEE = (String)map.get("HouseIEEE");
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIEEE);
			StringBuilder callUrl = new StringBuilder("http://");
			callUrl.append(serverIp).append(":").append(PropertiesUtil.getProperty("xmpp.port")).append("/spring-async/z203chat/polla");
			//解析调用返回
			String str =TestHttpclient.postUrlWithParams(callUrl.toString(), z203PMap, "utf-8");
			Map<String, Object> result = JSON.parseObject(str, Map.class);
			int status = (int) ((Map) result.get("message")).get("status");
//			if(status == 0) {				
//				hosueservice.updateIPKversion(map);//更新house表软件版本
//				TestLog4j.testInfo("--------------AssistanceUpdateVersion.updateIPKversion(map)");
//			}
			j.setResponse_params(result.get("message"));
		} catch (Exception e) {
			LOGGER.info("Assistance", e);
			dg.setStatus(-1);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);	
		}
		writeJson(j,callback,response);			
	}
	@RequestMapping("/CheckShapUpdateVersion")
	public void CheckShap(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();DataGrid dg = new DataGrid();
		//218.104.133.243:8081/zigBeeDevice/ReleasehistoryController/CheckShapUpdateVersion.do?json=
		//{"type":"all","id":0,"search":"203","order":"r.updateTime desc","pageIndex":"0","pageSize":"5"}
		//{"type":"house/z203","HouseIEEE":"00137a000001013b","CurVersion":"1.1.0.167 ","HWVersion":"203"}
		try {
			json=URLDecoder.decode(json, "utf-8");
			map=JSON.parseObject(json);	
			if(map.get("type").equals("all")){	
				System.out.println("all");
				List<Releasehistory> r=releasehistory.findList(map);
				j.setResponse_params(r);
			}else if(map.get("type").equals("house")){
				System.out.println("house");
				List<Releasehistory> r=releasehistory.findhouse(map);
				j.setResponse_params(r);
			}else{
				//跳转到cloudAddress、cloudPort
				String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
				String cloudPort = PropertiesUtil.getProperty("cloudPort");
				String serverHost = request.getServerName();
				String callback2 = request.getParameter("callback");
				int port = request.getLocalPort();
				map.put("callback", callback2);
				map.put("serverip", serverHost);
				map.put("port", port);
				LOGGER.info("CheckShapUpdateVersion cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);
				if(!cloudAddress.equals(serverHost) 
					&& !cloudAddress.equals("localhost") 
					&& !cloudAddress.equals("127.0.0.1")
					&& !serverHost.equals("localhost") 
					&& !serverHost.equals("127.0.0.1")) {
					String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/ReleasehistoryController/CheckShapUpdateVersion.do";
					LOGGER.info("得到的值为："+url);
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("json", json);
					paramMap.put("encodemethod", request.getParameter("encodemethod"));
					paramMap.put("sign", request.getParameter("sign"));
					paramMap.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
					String rsStr = TestHttpclient.postUrlWithParams(url, paramMap, "utf-8");					
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(rsStr);
					response.getWriter().flush();
//					return;
				}
//				if("1".equals(cloudMain)) {//IPK版本推送到代理服务器，云端服务器同步
//					String url = "http://" + cloudAddressdata + ":" + cloudPort + "/zigBeeDevice/ReleasehistoryController/CheckShapUpdateVersion.do";
//					Map<String, Object> paramMap = new HashMap<String, Object>();
//					paramMap.put("json", json);
//					paramMap.put("encodemethod", request.getParameter("encodemethod"));
//					paramMap.put("sign", request.getParameter("sign"));
//					paramMap.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//					String rsStr = TestHttpclient.postUrlWithParams(url, paramMap, "utf-8");
//					response.setContentType("text/html;charset=utf-8");
//					response.getWriter().write(rsStr);
//					response.getWriter().flush();
//					return;
//				}
				hosueservice.updateIPKversion(map);//更新house表软件版本
				LOGGER.info("更新house表软件版本！！！");
				TestLog4j.testInfo("--------------CheckShapUpdateVersion.updateIPKversion(map)");
				Releasehistory r2 = releasehistory.find(map);//查找比硬件版本大的软件版本				
				//为空的情况，需返回数据
				if(r2 == null) {
					j.setResponse_params(getEmptyStr());
				}
				else {
					List<String> arrays=Arrays.asList(StringUtils.split(map.get("CurVersion").toString(),"."));
					dg.setStatus(0);//默认返回失败
					dg.setStatus_msg("fail");
					Object ob=dg;
					for(int i=0;i<4;i++){//判断版本大小
						int diff=0;	
						if(i==0)	diff=r2.getVer1().compareTo(Integer.parseInt(arrays.get(i).replace(" ", "")));
						else if(i==1)diff=r2.getVer2().compareTo(Integer.parseInt(arrays.get(i).replace(" ", "")));
						else if(i==2)diff=r2.getVer3().compareTo(Integer.parseInt(arrays.get(i).replace(" ", "")));
						else if(i==3)diff=r2.getVer4().compareTo(Integer.parseInt(arrays.get(i).replace(" ", "")));
						if(diff>0){	ob=r2;break;}
						else if(diff<0)	break;	
						else continue;
					}
					//获取版本号为空
					if(ob == dg)						
						ob = getEmptyStr();
						j.setResponse_params(ob);						
				}
			}
		} catch (Exception e) {
			LOGGER.info("CheckShap", e);
//			LOGGER.info(TestLog4j.getTrace(e));
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);	
		}
		//1为云端管理后台，需要返回，0表示xmpp服务器，不需要返回
		int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
		if(cloudMain == 1)
			response.toString();
			writeJson(j,callback,response);
		//request.setAttribute("j",j);			
	}
	
	/**
	 * 获取版本号若为空返回的字符串
	 * @return
	 */
	private Map<String, Object> getEmptyStr() {
		String ftpIp = PropertiesUtil.getProperty("ftpIp");
		String ftpPort = PropertiesUtil.getProperty("ftpPort");
		String ftpUser = PropertiesUtil.getProperty("ftpUser");
		String ftpPass = PropertiesUtil.getProperty("ftpPass");
		String url = "ftp://" + ftpUser + ":" + ftpPass + "@" + ftpIp + ":" + ftpPort;
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("downloadInfo", url);
		rsMap.put("status", 0);
		return rsMap;
	}
	
	@RequestMapping("/getCount")
	public void getCount(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();DataGrid dg = new DataGrid();
		try {	
			map=JSON.parseObject(json);	
			map.put("total", releasehistory.getCount(map));
			j.setResponse_params(map);
		} catch (Exception e) {
			LOGGER.info("getCount", e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);		
	}
	
	public void writeJson(Object object,String callback,HttpServletResponse response) {

		try {
			String jstr=callback+"("+JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss")+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson", e);
		}
				
	}
	
	@RequestMapping("/getUpdateVersion")
	public void getUpdateVersion(String json, HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		String name = request.getParameter("file");
		File file = new File(name);
		String filename = file.getName();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {			
			String appBugPath =  "release/"+filename;
			FtpClient ftpClient = new FtpClient();
			ByteArrayOutputStream outputStream = ftpClient.downLoadFilebyName(appBugPath);
			//File path = new File(appBugPath);
			response.setContentType("multipart/form-data;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			response.getOutputStream().write(outputStream.toByteArray());
			
		}catch(Exception e){
			LOGGER.info("getUpdateVersion", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(resultJson + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
		
	}
	
	@RequestMapping("/CheckShapUpdateVersion2")
	public void CheckShap2(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Json j = new Json();DataGrid dg = new DataGrid();
		//218.104.133.243:8081/zigBeeDevice/ReleasehistoryController/CheckShapUpdateVersion.do?json=
		//{"type":"all","id":0,"search":"203","order":"r.updateTime desc","pageIndex":"0","pageSize":"5"}
		//{"type":"house/z203","HouseIEEE":"00137a000001013b","CurVersion":"1.1.0.167 ","HWVersion":"203"}
		try {
			json=URLDecoder.decode(json, "utf-8");
			map=JSON.parseObject(json);	
			if(map.get("type").equals("all")){	
				System.out.println("all");
				List<Releasehistory> r=releasehistory.findList(map);
				j.setResponse_params(r);
			}else if(map.get("type").equals("house")){
				System.out.println("house");
				List<Releasehistory> r=releasehistory.findhouse(map);
				j.setResponse_params(r);
			}else{
				//跳转到cloudAddress、cloudPort
				String cloudAddress = PropertiesUtil.getProperty("cloudAddress");
				String cloudPort = PropertiesUtil.getProperty("cloudPort");
				String serverHost = request.getServerName();
				String callback2 = request.getParameter("callback");
				int port = request.getLocalPort();
				String encodemethod = "NONE";
				String sign = "AAA";
				String houseIeeeSecret = request.getParameter("houseIeeeSecret");
				map.put("callback", callback2);
				map.put("encodemethod", encodemethod);
				map.put("sign", sign);
				map.put("houseIeeeSecret", houseIeeeSecret);
				map.put("serverip", serverHost);
				map.put("port", port);
				LOGGER.info("CheckShapUpdateVersion cloudAddress:" + cloudAddress + ",cloudPort:" + cloudPort + ",localHost:" + serverHost);
				if(!cloudAddress.equals(serverHost) 
					&& !cloudAddress.equals("localhost") 
					&& !cloudAddress.equals("127.0.0.1")
					&& !serverHost.equals("localhost") 
					&& !serverHost.equals("127.0.0.1")) {
					String url = "http://" + cloudAddress + ":" + cloudPort + "/zigBeeDevice/ReleasehistoryController/CheckShapUpdateVersion2.do";
					LOGGER.info("得到的值为："+url);
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("json", json);
					paramMap.put("callback", request.getParameter("callback"));
					paramMap.put("encodemethod", "NONE");
					paramMap.put("sign", "AAA");
					paramMap.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
					String rsStr = TestHttpclient.postUrlWithParams(url, paramMap, "utf-8");					
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().write(rsStr);
					response.getWriter().flush();
					return;
				}
//				if("1".equals(cloudMain)) {//IPK版本推送到代理服务器，云端服务器同步
//					String url = "http://" + cloudAddressdata + ":" + cloudPort + "/zigBeeDevice/ReleasehistoryController/CheckShapUpdateVersion.do";
//					Map<String, Object> paramMap = new HashMap<String, Object>();
//					paramMap.put("json", json);
//					paramMap.put("encodemethod", request.getParameter("encodemethod"));
//					paramMap.put("sign", request.getParameter("sign"));
//					paramMap.put("houseIeeeSecret", request.getParameter("houseIeeeSecret"));
//					String rsStr = TestHttpclient.postUrlWithParams(url, paramMap, "utf-8");
//					response.setContentType("text/html;charset=utf-8");
//					response.getWriter().write(rsStr);
//					response.getWriter().flush();
//					return;
//				}
				LOGGER.info("是否继续！！！");
				hosueservice.updateIPKversion(map);//更新house表软件版本
				LOGGER.info("更新house表软件版本！！！");
				TestLog4j.testInfo("--------------CheckShapUpdateVersion.updateIPKversion(map)");
				Releasehistory r2 = releasehistory.find2(map);//查找比硬件版本大的软件版本
				
				//为空的情况，需返回数据
				if(r2 == null) {
					Map<String, Object> ppt = new HashMap<String, Object>();
					String url = "http://"+map.get("serverip")+":"+ map.get("port") +"/zigBeeDevice/ReleasehistoryController/getUpdateVersion.do?file=" +""+"&callback="+callback2+"&encodemethod="+encodemethod+"&sign="+sign+"&houseIeeeSecret="+houseIeeeSecret;
					ppt.put("downloadInfo", url);
					ppt.put("status", 0);
					j.setResponse_params(ppt);
					//j.setResponse_params(getEmptyStr());
				}
				else {
					List<String> arrays=Arrays.asList(StringUtils.split(map.get("CurVersion").toString(),"."));
					dg.setStatus(0);//默认返回失败
					dg.setStatus_msg("fail");
					Object ob=dg;
					for(int i=0;i<4;i++){//判断版本大小
						int diff=0;	
						if(i==0)	diff=r2.getVer1().compareTo(Integer.parseInt(arrays.get(i).replace(" ", "")));
						else if(i==1)diff=r2.getVer2().compareTo(Integer.parseInt(arrays.get(i).replace(" ", "")));
						else if(i==2)diff=r2.getVer3().compareTo(Integer.parseInt(arrays.get(i).replace(" ", "")));
						else if(i==3)diff=r2.getVer4().compareTo(Integer.parseInt(arrays.get(i).replace(" ", "")));
						if(diff>0){	ob=r2;break;}
						else if(diff<0)	break;	
						else continue;
					}
					//获取版本号为空
					if(ob == dg){
						Map<String, Object> ppt = new HashMap<String, Object>();
						String url = "http://"+map.get("serverip")+":"+ map.get("port") +"/zigBeeDevice/ReleasehistoryController/getUpdateVersion.do?file=" +""+"&callback="+callback2+"&encodemethod="+encodemethod+"&sign="+sign+"&houseIeeeSecret="+houseIeeeSecret;
						ppt.put("downloadInfo", url);
						ppt.put("status", 0);
						j.setResponse_params(ppt);
						/*ob = getEmptyStr();
						j.setResponse_params(ob);*/
					}else {
						j.setResponse_params(ob);
					}	
				}
			}
		} catch (Exception e) {
			LOGGER.info("CheckShap", e);
//			LOGGER.info(TestLog4j.getTrace(e));
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);	
		}
		//1为云端管理后台，需要返回，0表示xmpp服务器，不需要返回
		int cloudMain = Integer.parseInt(PropertiesUtil.getProperty("cloudMain"));
		if(cloudMain == 1)
			response.toString();
			writeJson(j,callback,response);
		//request.setAttribute("j",j);			
	}	
}
