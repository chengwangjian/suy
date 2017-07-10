package sy.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.House;
import sy.model.Operatelog;
import sy.service.HouseServiceI;
import sy.service.OperatelogServiceI;
import sy.util.Httpproxy;
import sy.util.socket.ListenSocket;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/cgi-bin/rest")
public class CgiController {
	
	private HouseServiceI houseService;
	private OperatelogServiceI operatelogService;
	
	private static final Logger LOGGER = Logger.getLogger(CgiController.class);  
	
	public HouseServiceI getHouseService() {
		return houseService;
	}

	@Autowired
	public void setHouseService(HouseServiceI houseService) {
		this.houseService = houseService;
	}

	public OperatelogServiceI getOperatelogService() {
		return operatelogService;
	}

	@Autowired
	public void setOperatelogService(OperatelogServiceI operatelogService) {
		this.operatelogService = operatelogService;
	}

	@RequestMapping("/getTimer")
	public void getTimer(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	String resultJson = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":\"" + resultJson + "\"})"); 
        	response.setContentType("text/html;charset=utf-8");  // 设置编码，否则乱码
		} catch (Exception e) {
			LOGGER.info("getTimer",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}
	}
	
	/**
	 * WEB页面上通过输入IEEE及发送的内容可以向连接上来的Z206发送数据
	 * @author: zhuangxd
	 * 时间：2015-2-3 下午2:52:44
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/sendClientMsg")
	public void sendClientMsg(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
//		http://192.168.1.123:8080/zigBeeDevice/cgi-bin/rest/sendClientMsg.cgi?json={"houseIEEE":"00137A0000012E5F","content":"command"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000012E5F
			
		PrintWriter out=response.getWriter();
		
        try {
        	String resultJson = "{\"result\":1}";//成功1 失败0
        	Map houseieeeMap = JSON.parseObject(json,Map.class);
        	Socket client = (Socket)ListenSocket.clientMap.get(houseieeeMap.get("houseIEEE"));
        	OutputStream out2 = null;//返回给客户端的信息
        	out2 = client.getOutputStream();//写入缓存
        	int i = ((String)(houseieeeMap.get("content"))).length();
        	String a = Integer.toHexString(i);
        	StringBuffer buf = new StringBuffer();
        	 for(int j=0;j<4-a.length();j++){
        		 buf.append('0');   //初始化为len个0.
            }
        	 buf.append(a);
			out2.write(("\n\n"+buf.toString()+(String)(houseieeeMap.get("content"))).getBytes());
			System.out.println("\n\n"+buf.toString()+(String)(houseieeeMap.get("content")));
//			out2.write(("\n\n").getBytes());
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("sendClientMsg",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}
	}
	
	/**
	 * 创建一个WEB页面，端口为8083，在这个页面能显示从TCP客户端发上来的数据
	 * @author: zhuangxd
	 * 时间：2015-2-3 下午3:24:15
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/findClientMsg")
	public void findClientMsg(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
//		http://192.168.1.123:8080/zigBeeDevice/cgi-bin/rest/findClientMsg.cgi?json={"type":"ClientMsg"}&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000012E5F
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");
		
        try {
        	Operatelog operatelog = JSON.parseObject(json,Operatelog.class); 
        	List<Operatelog> t = operatelogService.findList2(operatelog);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find", e);
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//			e.printStackTrace();
		}   
	}
	
	@RequestMapping("/network/*")
	public void findCgi(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
//		String json = request.getParameter("json");
	
		/*resp.setContentType("text/html;charset=gb2312");  
		PrintWriter out=resp.getWriter();  

		out.println(json);  
		out.flush();  */

//		PropertyConfigurator.configure("D:/log4j.properties");        
		/*Map<String,Object> map1 = (Map<String,Object>)JSON.parse(json);
		for (String key : map1.keySet()) { 
			logger.info(key+":"+map1.get(key)); 
		}*/
		
		PrintWriter out=response.getWriter();  
//		System.out.println("json==" + json);
		
		/*List<Cgi> houseenergydeviceList = JSON.parseArray(json,Cgi.class); 
        for (Cgi houseenergydevice : houseenergydeviceList) { 
        	houseenergydeviceService.save(houseenergydevice);
        } */
		
        try {
        	// http://192.168.1.184/cgi-bin/rest/network/getendpoint.cgi?callback=android&sign=9A7E0C7F0DA47030D65A185E0F53F831&encodemethod=AES
        	// http://192.168.1.123:8080/zigBeeDevice/cgi-bin/rest/network/getendpoint.cgi?callback=android&sign=9A7E0C7F0DA47030D65A185E0F53F831&encodemethod=AES&houseIeee=a0137A0000008131
        	// GET请求
        	String houseIeee = request.getParameter("houseIeeeSecret");
        	System.out.println("houseIeeeSecret==" + houseIeee);
        	House t = new House();
        	if (houseIeee != null) {
        		House house = new House();
        		house.setHouseIeee(houseIeee);
            	t = houseService.get(house);
            	if (t == null) {
            		House house2 = new House();
            		house2.setHouseIeee("ipipipipipipipip");
            		t = houseService.get(house2);
            		if (t == null) {
            			t = new House();
            			t.setNetworkAddress("192.168.1.184"); // cgi内网地址
//                		t.setNetworkAddress("192.168.15.1"); // cgi内网地址
//                		t.setNetworkAddress("218.104.133.243"); // cgi外网地址
            		}
            	}
        	} else {
        		House house2 = new House();
        		house2.setHouseIeee("ipipipipipipipip");
        		t = houseService.get(house2);
        		if (t == null) {
        			t = new House();
        			t.setNetworkAddress("192.168.1.184"); // cgi内网地址
//            		t.setNetworkAddress("192.168.15.1"); // cgi内网地址
//            		t.setNetworkAddress("218.104.133.243"); // cgi外网地址
        		}
        	}
        	String resultJson = Httpproxy.httpCallback(request, t.getNetworkAddress(), t.getPort());
        	
        	/*Cgi houseenergydevice = JSON.parseObject(json,Cgi.class); 
        	Cgi t = houseenergydeviceService.get(houseenergydevice);
        	if (t == null) {
        		if (houseenergydeviceService.getExisit(houseenergydevice) == null) {
        			houseenergydeviceService.save(houseenergydevice);
        		}
        	} else {
        		if (houseenergydevice.getDeviceType() != null) {
        			t.setDeviceType(houseenergydevice.getDeviceType());
        		}
        		houseenergydeviceService.update(t);
        	}*/
        	if (resultJson == null) {
        		resultJson = "{\"result\":0}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
    			return;
        	}
        	response.setContentType("text/html;charset=utf-8");  // 设置编码，否则乱码
        	out.println(resultJson);
		} catch (Exception e) {
			LOGGER.info("findCgi",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveCgi";
	}
	
}
