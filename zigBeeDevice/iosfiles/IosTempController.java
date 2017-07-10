package sy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.smarthome.model.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.IosTemp;
import sy.service.IosTempServiceI;
import sy.util.TestHttpclient;
import zbHouse.pageModel.Json;

import com.alibaba.fastjson.JSON;

@Controller()
@RequestMapping("/iosTempController")
public class IosTempController {
	private static final Logger LOGGER = Logger.getLogger(IosTempController.class);
	private IosTempServiceI iosTempService;
	
	public IosTempServiceI getIosTempService() {
		return iosTempService;
	}
	
	@Autowired
	public void setIosTempService(IosTempServiceI iosTempService) {
		this.iosTempService = iosTempService;
	}

	@RequestMapping("/saveIosTemp")	
	public void saveIosTemp(String json,String callback,HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		DataGrid dg = new DataGrid();
		try {
			IosTemp iosTemp = JSON.parseObject(json, IosTemp.class);
			//控制开关
			String[] positStr = iosTemp.getDistance().split("_");
			int position = Integer.parseInt(positStr[0]);
			int onoff = Integer.parseInt(positStr[1]);
			if(position == 1) {
				String url = "http://192.168.1.45/cgi-bin/rest/network/mainsOutLetOperation.cgi?ieee=00137A000000F63E&ep=01&operatortype=" + onoff + "&param1=1&param2=2&param3=3&callback=1234&encodemethod=NONE&sign=AAA";
				TestHttpclient.getUrl(url, "utf-8");
			}
			else if(position == 2) {
				String url = "http://192.168.1.45/cgi-bin/rest/network/mainsOutLetOperation.cgi?ieee=00137A000000F609&ep=01&operatortype=" + onoff + "&param1=1&param2=2&param3=3&callback=1234&encodemethod=NONE&sign=AAA";
				TestHttpclient.getUrl(url, "utf-8");
			}
			else if(position == 3){
				String url = "http://192.168.1.45/cgi-bin/rest/network/mainsOutLetOperation.cgi?ieee=00137A000000F5E0&ep=01&operatortype=" + onoff + "&param1=1&param2=2&param3=3&callback=1234&encodemethod=NONE&sign=AAA";
				TestHttpclient.getUrl(url, "utf-8");
			}
			iosTempService.saveIosTemp(iosTemp);
			dg.setStatus(1);
			dg.setStatus_msg("success");
			j.setResponse_params(dg);
		} catch (Exception e) {
			LOGGER.info("saveIosTemp",e);
			dg.setStatus(0);
			dg.setStatus_msg("fail");
			j.setResponse_params(dg);
		}
		writeJson(j,callback,response);		
	}
	
	@RequestMapping("/getIosTempCount")
	public void getIosTempCount(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			int iosCount = iosTempService.getIosTempCount();
			String resultJson= "{\"result\":1, \"total\":" + iosCount + "}";
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getIosTempCount",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	
	 /*云端查询广告*/
    @RequestMapping("/getIosTemps")
	public void getIosTemps(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html;charset=utf-8");
    	try {
    		Map jsonMap = JSON.parseObject(json, Map.class); 
    		List<Map> iosList = iosTempService.getIosTemps((int) jsonMap.get("pageSize"), (int) jsonMap.get("startRow"));
    		String resultJson= JSON.toJSONStringWithDateFormat(iosList, "yyyy-MM-dd HH:mm:ss");
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getIosTemps",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
    }

    @RequestMapping("/deleteIosTemp")
 	public void deleteIosTemp(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
     	PrintWriter out = response.getWriter();
     	response.setContentType("text/html;charset=utf-8");
     	try {
     		IosTemp iosTemp = JSON.parseObject(json, IosTemp.class);
     		iosTempService.deleteIosTemp(iosTemp.getId());
     		String callback = request.getParameter("callback");
     		out.println(callback + "({\"request_id\": 1234, \"response_params\":{\"result\":1}})");
 		} catch (Exception e) {
 			LOGGER.info("getIosTemps",e);
 			String resultJson = "{\"result\":0}";//成功1 失败0
 			String callback = request.getParameter("callback");
 			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
 		}
     }
    
    @RequestMapping("/downloadFile")
 	public void downloadFile(String json,HttpServletRequest request, HttpServletResponse response) throws Exception{ 
     	InputStream is = null;
     	try {
     		String fileName = request.getParameter("fileName");
     		File file = new File("C:\\Users\\R&D1\\Desktop\\bug脚本\\" + fileName);
     		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
     		response.setContentType("application/x-msdownload;charset=utf-8");
     		OutputStream os = response.getOutputStream();
     		is = new FileInputStream(file);
     		byte[] buffer = new byte[1024];
     		while(is.read(buffer) != -1) {
     			os.write(buffer);
     			buffer = new byte[1024];
     		}
     		os.flush();
 		} catch (Exception e) {
 			LOGGER.info("getIosTemps",e);
 			PrintWriter out = response.getWriter();
 			String resultJson = "{\"result\":0}";//成功1 失败0
 			String callback = request.getParameter("callback");
 			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
 		} finally {
 			try {
 				if(is != null)
 					is.close();
 			} catch(Exception e) {
 				LOGGER.error("io close exception", e);
 			}
 		}
     }
    
    public void writeJson(Object object,String callback,HttpServletResponse response) {
		try {
			String jstr=callback+"("+JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss")+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson",e);
		}
	}	

    public static void main(String[] args) throws Exception {
    	String url = "http://192.168.1.45/cgi-bin/rest/network/mainsOutLetOperation.cgi?ieee=00137A000000F609&ep=01&operatortype=0&param1=1&param2=2&param3=3&callback=1234&encodemethod=NONE&sign=AAA";
		TestHttpclient.getUrl(url, "utf-8");
    }
}

