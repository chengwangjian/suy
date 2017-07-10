package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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

import sy.model.Modedevice;
import sy.model.Modeeplib;
import sy.model.Modenode;
import sy.model.Modenodelib;
import sy.pageModel.ModeNodeAndDevice;
import sy.service.ModedeviceServiceI;

import com.alibaba.fastjson.JSON;
import com.flywind.app.data.DataGrid;
import com.flywind.app.data.Json;
import com.flywind.app.data.Target;


@Controller
@RequestMapping("/modedeviceController")
public class ModedeviceController {

	private ModedeviceServiceI modedeviceService;
	
	private static final Logger LOGGER = Logger.getLogger(ModedeviceController.class);  

	public ModedeviceServiceI getModedeviceService() {
		return modedeviceService;
	}

	@Autowired
	public void setModedeviceService(ModedeviceServiceI modedeviceService) {
		this.modedeviceService = modedeviceService;
	}
	
	@RequestMapping("/addOrUpdate")
	public void addOrUpdate(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*modedeviceService.test();
		return "showModedevice";*/
		
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
		
		/*List<Modedevice> modedeviceList = JSON.parseArray(json,Modedevice.class); 
        for (Modedevice modedevice : modedeviceList) { 
        	modedeviceService.save(modedevice);
        } */
		
        try {
        	/*ModedeviceAndRoom modedeviceAndRoom=JSON.parseObject(json, ModedeviceAndRoom.class);
        	if (modedeviceAndRoom.getModedevice() == null || modedeviceAndRoom.getModeroom() == null) {
        		String resultJson = "{\"result\":0,\"houseId\":0}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	modedeviceService.saveOrUpdateModedeviceAndRoom(modedeviceAndRoom);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	resultJson = "{\"result\":1,\"houseId\":" + modedeviceAndRoom.getModedevice().getId() + "}";//成功1 失败0;
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); */
		} catch (Exception e) {
			LOGGER.info("addOrUpdate",e);
			String resultJson = "{\"result\":0,\"houseId\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveModedevice";
	}
	
	@RequestMapping("/update")
	public void update(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
        	Modedevice t = modedeviceService.get(modedevice);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t != null) {
        		/*modedevice.setId(t.getId());
            	modedeviceService.update(modedevice);*/
        		/*if (modedevice.getName() != null) {
        			t.setName(modedevice.getName());
        		}
        		if (modedevice.getSecretKey() != null) {
        			t.setSecretKey(modedevice.getSecretKey());
        		}
        		if (modedevice.getVendorCode() != null) {
        			t.setVendorCode(modedevice.getVendorCode());
        		}
        		if (modedevice.getVendorName() != null) {
        			t.setVendorName(modedevice.getVendorName());
        		}
        		if (modedevice.getEncodemethod() != null) {
        			t.setEncodemethod(modedevice.getEncodemethod());
        		}
        		if (modedevice.getXmppIp() != null) {
        			t.setXmppIp(modedevice.getXmppIp());
        		}
        		if (modedevice.getXmppPort() != null) {
        			t.setXmppPort(modedevice.getXmppPort());
        		}
        		if (modedevice.getCloudIp1() != null) {
        			t.setCloudIp1(modedevice.getCloudIp1());
        		}
        		if (modedevice.getCloudPort1() != null) {
        			t.setCloudPort1(modedevice.getCloudPort1());
        		}
        		if (modedevice.getCloudIp2() != null) {
        			t.setCloudIp2(modedevice.getCloudIp2());
        		}
        		if (modedevice.getCloudPort2() != null) {
        			t.setCloudPort2(modedevice.getCloudPort2());
        		}
        		if (modedevice.getEnableFlag() != null) {
        			t.setEnableFlag(modedevice.getEnableFlag());
        		}
        		if (modedevice.getDescription() != null) {
        			t.setDescription(modedevice.getDescription());
        		}*/
        		t.setLasttime(new Date());
            	modedeviceService.update(t);
            	resultJson = "{\"result\":1}";//成功1 失败0
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 更改Device的房间信息RoomID和设备显示名称
	 * @author: zhuangxd
	 * 时间：2013-11-28 下午2:37:22
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/ChangeDeviceInfo")
	public void ChangeDeviceInfo(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
        	//Modedevice t = modedeviceService.getDevice(modedevice);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	//if (t == null) {
        	Modedevice t = modedeviceService.get(modedevice);
            if (t != null) {
        	//if (t == null) {
            	t.setDeviceName(modedevice.getDeviceName());
        		if (modedevice.getRoomId() != 0) {
            		t.setRoomId(modedevice.getRoomId());
            	}
//            	if (modedevice.getDeviceName() != null && !modedevice.getDeviceName().equals(t.getDeviceName())) {
//            		Modedevice t2 = modedeviceService.getDevice(modedevice);
//            		if (t2 != null) { // 设备同名
//            			resultJson = "{\"result\":-1}";//成功1 失败0
//                    	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//                    	return;
//            		}
//            		t.setDeviceName(modedevice.getDeviceName());            		
//            	}
            	/*modedevice.setId(t.getId());
                	modedeviceService.update(modedevice);*/
            	/*if (modedevice.getName() != null) {
            			t.setName(modedevice.getName());
            		}
            		if (modedevice.getEnableFlag() != null) {
            			t.setEnableFlag(modedevice.getEnableFlag());
            		}
            		if (modedevice.getDescription() != null) {
            			t.setDescription(modedevice.getDescription());
            		}*/
            	t.setLasttime(new Date());
            	modedeviceService.update(t);
        	}
        	resultJson = "{\"result\":1}";//成功1 失败0
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("ChangeDeviceInfo",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 更改node的房间信息RoomID和显示名称
	 * @author: zhuangxd
	 * 时间：2014-2-21 上午11:37:02
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/ChangeNodeInfo")
	public void ChangeNodeInfo(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modenode modenode = JSON.parseObject(json,Modenode.class); 
        	//Modedevice t = modedeviceService.getDevice(modedevice);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	//if (t == null) {
        	Modenode t = modedeviceService.getModenode(modenode);
        	if (t != null) {
        		if (modenode.getRoomId() != 0) {
            		t.setRoomId(modenode.getRoomId());
            	}
            	if (modenode.getDeviceName() != null && !modenode.getDeviceName().equals(t.getDeviceName())) {
            		Modenode t2 = modedeviceService.getModenode2(modenode);
            		if (t2 != null) { // 设备同名
            			resultJson = "{\"result\":-1}";//成功1 失败0
                    	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
                    	return;
            		}
            		t.setDeviceName(modenode.getDeviceName());            		
            	}
            	/*modedevice.setId(t.getId());
                	modedeviceService.update(modedevice);*/
            	/*if (modedevice.getName() != null) {
            			t.setName(modedevice.getName());
            		}
            		if (modedevice.getEnableFlag() != null) {
            			t.setEnableFlag(modedevice.getEnableFlag());
            		}
            		if (modedevice.getDescription() != null) {
            			t.setDescription(modedevice.getDescription());
            		}*/
            	t.setLasttime(new Date());
            	modedeviceService.updateModenode(t);
        	}
        	resultJson = "{\"result\":1}";//成功1 失败0
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("ChangeNodeInfo",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 修改节点ieee和设备ieee
	 * @author: zhuangxd
	 * 时间：2014-1-10 上午10:43:10
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/ChangeNodeIeee")
	public void ChangeNodeIeee(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modenode modenode = JSON.parseObject(json,Modenode.class); 
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	modedeviceService.updateNodeAndDeviceIeee(modenode);
        	resultJson = "{\"result\":1}";//成功1 失败0
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("ChangeNodeIeee",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 选择设备类型，获取node列表库信息(获取node列表库)
	 * @author: zhuangxd
	 * 时间：2013-11-27 下午2:32:49
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeNodeLibDataByNodeType")
	public void GetModeNodeLibDataByNodeType(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
        try {
//        	Modeeplib modeeplib=JSON.parseObject(json, Modeeplib.class);
//        	Map t = modedeviceService.findModeNodeLibDataByDeviceID(modeeplib);
        	Modenodelib modenodelib=JSON.parseObject(json, Modenodelib.class);
        	String language = request.getParameter("language"); 
        	modenodelib.setLanguage(language);
        	Map t = modedeviceService.findModeNodeLibDataByNodeType(modenodelib);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeNodeLibDataByNodeType",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
//        	Modedevice t = modedeviceService.find(modedevice);
        	List<Modedevice> t = modedeviceService.findList(modedevice);
        	List<Object[]> s = modedeviceService.findListSql(modedevice);
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		DevicewarnhistoryModedeviceidYear devicewarnhistoryModedeviceidYear = (DevicewarnhistoryModedeviceidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}
	
	/**
	 * 获取nodelib的描述信息
	 * @author: zhuangxd
	 * 时间：2013-11-27 下午2:32:49
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeNodeLibDataByNodeLibId")
	public void GetModeNodeLibDataByNodeLibId(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Modenodelib modenodelib=JSON.parseObject(json, Modenodelib.class);
        	Map t = modedeviceService.findModeNodeLibDataByNodeLibId(modenodelib);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeNodeLibDataByNodeLibId",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
//        	Modedevice t = modedeviceService.find(modedevice);
        	List<Modedevice> t = modedeviceService.findList(modedevice);
        	List<Object[]> s = modedeviceService.findListSql(modedevice);
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		DevicewarnhistoryModedeviceidYear devicewarnhistoryModedeviceidYear = (DevicewarnhistoryModedeviceidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}
	
	/**
	 * 输入添加数量，回车后，获取添加的设备列表（nodeid或型号查找，输入型号，确认后，获取添加的设备列表）
	 * @author: zhuangxd
	 * 时间：2013-11-27 下午4:43:28
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeEPLibDataByNode")
	public void GetModeEPLibDataByNode(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Modeeplib modeeplib=JSON.parseObject(json, Modeeplib.class);
        	String language = request.getParameter("language");
        	modeeplib.setLanguage(language);
        	Map t = modedeviceService.findModeEPLibDataByNode(modeeplib);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	t = null;
        	String ResultJson=(resultJson.equals("{}")?"{\"result\":0}":resultJson);
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + ResultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeEPLibDataByNode",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
//        	Modedevice t = modedeviceService.find(modedevice);
        	List<Modedevice> t = modedeviceService.findList(modedevice);
        	List<Object[]> s = modedeviceService.findListSql(modedevice);
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		DevicewarnhistoryModedeviceidYear devicewarnhistoryModedeviceidYear = (DevicewarnhistoryModedeviceidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}
	
	/**
	 * 返回指定房间号的所有的Node列表
	 * @author: zhuangxd
	 * 时间：2013-11-28 上午11:48:56
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeNodeList2")
	public void GetModeNodeList2(String json,String roomidlist,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Modenode modenode=JSON.parseObject(json, Modenode.class);
//        	System.out.print(roomidlist);
        	List<Integer> romidlias=JSON.parseObject(roomidlist, List.class);
        	Map t = modedeviceService.findModeNodeList(modenode,romidlias);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeNodeList",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
//        	Modedevice t = modedeviceService.find(modedevice);
        	List<Modedevice> t = modedeviceService.findList(modedevice);
        	List<Object[]> s = modedeviceService.findListSql(modedevice);
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		DevicewarnhistoryModedeviceidYear devicewarnhistoryModedeviceidYear = (DevicewarnhistoryModedeviceidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}	
	
	/**
	 * 返回某一个房间的所有的Node列表
	 * @author: zhuangxd
	 * 时间：2013-11-28 上午11:48:56
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeNodeList")
	public void GetModeNodeList(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Modenode modenode=JSON.parseObject(json, Modenode.class);
        	Map t = modedeviceService.findModeNodeList(modenode);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeNodeList",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
//        	Modedevice t = modedeviceService.find(modedevice);
        	List<Modedevice> t = modedeviceService.findList(modedevice);
        	List<Object[]> s = modedeviceService.findListSql(modedevice);
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		DevicewarnhistoryModedeviceidYear devicewarnhistoryModedeviceidYear = (DevicewarnhistoryModedeviceidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}	
	
	/**
	 * 返回某一个房间的所有Device列表
	 * @author: zhuangxd
	 * 时间：2013-11-28 下午2:23:30
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeDeviceList")
	public void GetModeDeviceList(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Modedevice modedevice=JSON.parseObject(json, Modedevice.class);
        	Map t = modedeviceService.findModeDeviceList(modedevice);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeDeviceList",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
//        	Modedevice t = modedeviceService.find(modedevice);
        	List<Modedevice> t = modedeviceService.findList(modedevice);
        	List<Object[]> s = modedeviceService.findListSql(modedevice);
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		DevicewarnhistoryModedeviceidYear devicewarnhistoryModedeviceidYear = (DevicewarnhistoryModedeviceidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}	
	
	/**
	 * 删除某一个node前，先返回某一个node的所有Device列表
	 * @author: zhuangxd
	 * 时间：2013-11-28 下午2:23:30
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeDeviceByNodeId")
	public void GetModeDeviceByNodeId(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Modenode modenode=JSON.parseObject(json, Modenode.class);
        	Map t = modedeviceService.findModeDeviceByNodeId(modenode);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeDeviceByNodeId",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
//        	Modedevice t = modedeviceService.find(modedevice);
        	List<Modedevice> t = modedeviceService.findList(modedevice);
        	List<Object[]> s = modedeviceService.findListSql(modedevice);
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modedevice modedevice2 = (Modedevice)objects[0];
        		DevicewarnhistoryModedeviceidYear devicewarnhistoryModedeviceidYear = (DevicewarnhistoryModedeviceidYear)objects[1];
        	}
//        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
        	String resultJson= JSON.toJSONStringWithDateFormat(s, "yyyy-MM-dd HH:mm:ss");
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}    */    
	}	
	
	/**
	 * 删除某一个node，同时删除device表关联的数据
	 * @author: zhuangxd
	 * 时间：2013-11-25 下午5:31:22
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/DeleteModeNode")
	public void DeleteModeNode(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
        try {
        	Modenode modenode=JSON.parseObject(json, Modenode.class);
        	modedeviceService.deleteModeNode(modenode);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("DeleteModeNode",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
            String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 保存设备到房间
	 * @author: zhuangxd
	 * 时间：2013-11-27 下午5:43:47
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/InsertDeviceToRoom")
	public void InsertDeviceToRoom(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*modedeviceService.test();
		return "showModedevice";*/
		
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
		System.out.println(json);
		/*List<Modedevice> modedeviceList = JSON.parseArray(json,Modedevice.class); 
        for (Modedevice modedevice : modedeviceList) { 
        	modedeviceService.save(modedevice);
        } */
		
        try {
//        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class);
        	// json post请求
    		response.setContentType("application/json");  
        	json = URLDecoder.decode(json, "gbk");
        	LOGGER.info("json2=" + json);
        	ModeNodeAndDevice modeNodeAndDevice=JSON.parseObject(json, ModeNodeAndDevice.class);
//        	Map<String,ModeNodeAndDevice2> map1 = (Map<String,ModeNodeAndDevice2>)JSON.parse(json);
//        	String text = ...; // {"name":{"name":"ljw",age:18}} Map<String, User> userMap = JSON.parseObject(text, new TypeReference<Map<String, User>>() {}); 

//        	modedeviceService.insertDeviceToRoom(map1);
        	int i = modedeviceService.insertDeviceToRoom(modeNodeAndDevice);
        	if (i == -1) { // 设备同名时
        		String resultJson = "{\"result\":-1}";//成功1 失败0
//            	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
            	String callback = request.getParameter("callback");out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
            	return;
        	}
        	// 更新deviceUniqueId
        	modedeviceService.insertDeviceToRoom2(modeNodeAndDevice);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	resultJson = "{\"result\":1}";//成功1 失败0;
//        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        	String callback = request.getParameter("callback");
        	out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("InsertDeviceToRoom",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
//			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        	String callback = request.getParameter("callback");out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveModedevice";
	}
	
	@RequestMapping("/delete")
	public void delete(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modedevice modedevice = JSON.parseObject(json,Modedevice.class); 
        	modedeviceService.delete(modedevice);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	/**
	 * 区域设备列表设备数量统计
	 */
	@RequestMapping("/modenodeCount")
	public void modenodeCount(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter();  
		String callback = request.getParameter("callback");
		try {
			Map<String , Object> param = JSON.parseObject(json, Map.class);
			List<Map> list = modedeviceService.modenodeCount(param);
			String resultJson=JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("modenodeCount", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
//			e.printStackTrace();
			}
		}
	/**
	 * 客户端设备列表设备数量统计
	 */
	@RequestMapping("/modedeviceCount")
	public void modedeviceCount(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter();  
		response.addHeader("Access-Control-Allow-Origin", "*");
		String callback = request.getParameter("callback");
		try {
			Map<String , Object> param = JSON.parseObject(json, Map.class);
			List<Map> list = modedeviceService.modedeviceCount(param);
			String resultJson=JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("modedeviceCount", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
//			e.printStackTrace();
			}
		}

	
	
	//设备添加到APP首页
	@RequestMapping("/addEpPage")
	public void addEpPage(String json,HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter();  
//		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");  
		String callback = request.getParameter("callback");
		String houseid = request.getParameter("houseid");
		try {
			List<Map> list = JSON.parseObject(json, List.class);
			int a = modedeviceService.addEpPage(list,houseid);
			String resultJson = "{\"result\":"+a+"}";//成功1 失败0 
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("addEpPage", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			}
	}
	
	//返回模式编辑器APP首页设备列表
	@RequestMapping("/findEpPage")
	public void findEpPage(String json,HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter();  
//		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");  
		String callback = request.getParameter("callback");
		try {
			Map<String, Object> map = JSON.parseObject(json, Map.class);
			Map t = modedeviceService.findEpPage(map);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("findEpPage",e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}  
	}
	
	//设备添加到APP首页
	@RequestMapping("/addModePage")
	public void addModePage(String json,HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter();  
//		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=utf-8");  
		String callback = request.getParameter("callback");
		String houseid  =request.getParameter("houseid");
		try {
			List<Map> list = JSON.parseObject(json, List.class);
			int a = modedeviceService.addModePage(list,houseid);
			String resultJson = "{\"result\":"+a+"}";//成功1 失败0 
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("addModePage", e);
			String resultJson = "{\"result\":0}";//成功1 失败0 
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			}
	}
	//返回模式编辑器APP首页模式列表
	@RequestMapping("/findModePage")
	public void findModePage(String target,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object params=null;DataGrid dg=new DataGrid();
    	Json j = new Json();
    	String callback = request.getParameter("callback");
    	try {
    		Target tar=JSON.parseObject(target,Target.class);    		
    		params=modedeviceService.findroom(tar);
			j.setResponse_params(params);
		} catch (Exception e) {
			LOGGER.info("mode",e);
			dg.setStatus(0);dg.setStatus_msg("fail");j.setResponse_params(dg);
		}
		 LOGGER.info("----------"+params);		
		 writeJson(j,callback,response);
//		 return params;
	}
	
	
	public void writeJson(Object object,String callback,HttpServletResponse response) {
		try {
			String jstr=callback+"("+JSON.toJSONString(object)+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson",e);
		}
	}
}
