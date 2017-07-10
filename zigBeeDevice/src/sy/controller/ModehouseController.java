package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Modehouse;
import sy.model.Moderoom;
import sy.pageModel.ModehouseAndRoom;
import sy.service.ModehouseServiceI;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/modehouseController")
public class ModehouseController {

	private ModehouseServiceI modehouseService;
	
	private static final Logger LOGGER = Logger.getLogger(ModehouseController.class);  

	public ModehouseServiceI getModehouseService() {
		return modehouseService;
	}

	@Autowired
	public void setModehouseService(ModehouseServiceI modehouseService) {
		this.modehouseService = modehouseService;
	}
	
	/**
	 * 新增家、房间信息
	 * @author: zhuangxd
	 * 时间：2013-11-22 下午4:59:05
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/add")
	public void add(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*modehouseService.test();
		return "showModehouse";*/
		
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
		
		/*List<Modehouse> modehouseList = JSON.parseArray(json,Modehouse.class); 
        for (Modehouse modehouse : modehouseList) { 
        	modehouseService.save(modehouse);
        } */
		
        try {
//        	Modehouse modehouse = JSON.parseObject(json,Modehouse.class);
        	ModehouseAndRoom modehouseAndRoom=JSON.parseObject(json, ModehouseAndRoom.class);
        	String language = request.getParameter("language");
        	modehouseAndRoom.setLanguage(language);
        	if (modehouseAndRoom.getModehouse() == null || modehouseAndRoom.getModeroom() == null) {
        		String resultJson = "{\"result\":0,\"houseId\":0}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	int i = modehouseService.addModehouseAndRoom(modehouseAndRoom);
        	if (i == -1) { // 家同名，保存失败
        		String resultJson = "{\"result\":-1,\"houseId\":0}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	resultJson = "{\"result\":1,\"houseId\":" + modehouseAndRoom.getModehouse().getId() + "}";//成功1 失败0;
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("add", e);
			String resultJson = "{\"result\":0,\"houseId\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveModehouse";
	}
	
	/**
	 * 修改家、房间信息
	 * @author: zhuangxd
	 * 时间：2013-11-22 下午4:59:05
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public void addOrUpdate(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*modehouseService.test();
		return "showModehouse";*/
		
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
		
		/*List<Modehouse> modehouseList = JSON.parseArray(json,Modehouse.class); 
        for (Modehouse modehouse : modehouseList) { 
        	modehouseService.save(modehouse);
        } */
		
        try {
//        	Modehouse modehouse = JSON.parseObject(json,Modehouse.class);
        	ModehouseAndRoom modehouseAndRoom=JSON.parseObject(json, ModehouseAndRoom.class);
        	if (modehouseAndRoom.getModehouse() == null || modehouseAndRoom.getModeroom() == null) {
        		String resultJson = "{\"result\":0,\"houseId\":0}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
    			return;
        	}
        	modehouseService.updateModehouseAndRoom(modehouseAndRoom);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	resultJson = "{\"result\":1,\"houseId\":" + modehouseAndRoom.getModehouse().getId() + "}";//成功1 失败0;
        	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("addOrUpdate", e);
			String resultJson = "{\"result\":0,\"houseId\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveModehouse";
	}
			
	/**
	 * 获取家、房间信息
	 * @author: zhuangxd
	 * 时间：2013-11-22 下午4:59:21
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeHouseAndRoomByHouseID")
	public void GetModeHouseAndRoomByHouseID(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	ModehouseAndRoom modehouseAndRoom=JSON.parseObject(json, ModehouseAndRoom.class);
        	Map t = modehouseService.findModehouseAndRoom(modehouseAndRoom);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeHouseAndRoomByHouseID", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modehouse modehouse = JSON.parseObject(json,Modehouse.class); 
//        	Modehouse t = modehouseService.find(modehouse);
        	List<Modehouse> t = modehouseService.findList(modehouse);
        	List<Object[]> s = modehouseService.findListSql(modehouse);
        	for (Object[] objects : s) {
        		Modehouse modehouse2 = (Modehouse)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modehouse modehouse2 = (Modehouse)objects[0];
        		DevicewarnhistoryModehouseidYear devicewarnhistoryModehouseidYear = (DevicewarnhistoryModehouseidYear)objects[1];
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
	 * 返回家列表
	 * @author: zhuangxd
	 * 时间：2013-11-25 下午4:19:52
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetModeHouseByUserID")
	public void GetModeHouseByUserID(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	ModehouseAndRoom modehouseAndRoom=JSON.parseObject(json, ModehouseAndRoom.class);
        	Map t = modehouseService.findModehouse(modehouseAndRoom);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeHouseByUserID", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}   
        
        // 多表关联
      /*  try {
        	Modehouse modehouse = JSON.parseObject(json,Modehouse.class); 
//        	Modehouse t = modehouseService.find(modehouse);
        	List<Modehouse> t = modehouseService.findList(modehouse);
        	List<Object[]> s = modehouseService.findListSql(modehouse);
        	for (Object[] objects : s) {
        		Modehouse modehouse2 = (Modehouse)objects[0];
        		Deviceattribute deviceattribute = (Deviceattribute)objects[1];
			}
        	for (Object[] objects : s) {
        		Modehouse modehouse2 = (Modehouse)objects[0];
        		DevicewarnhistoryModehouseidYear devicewarnhistoryModehouseidYear = (DevicewarnhistoryModehouseidYear)objects[1];
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
	 * 删除房间
	 * @author: zhuangxd
	 * 时间：2013-11-25 下午5:31:22
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/DeleteRoom")
	public void DeleteRoom(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Moderoom moderoom = JSON.parseObject(json,Moderoom.class); 
        	modehouseService.deleteModeroom(moderoom);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("DeleteRoom", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 删除家
	 * @author: zhuangxd
	 * 时间：2014-1-23 下午4:39:11
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modehouse modehouse = JSON.parseObject(json,Modehouse.class); 
        	modehouseService.delete(modehouse);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 重命名家
	 * @author: zhuangxd
	 * 时间：2014-2-18 上午11:28:39
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/renameHouse")
	public void renameHouse(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*modehouseService.test();
		return "showModehouse";*/
		
//		String json = request.getParameter("json");
	
		/*resp.setContentType("text/html;charset=gb2312");  
		PrintWriter out=resp.getWriter();  

		out.println(json);  
		out.flush();  */

		PrintWriter out=response.getWriter();  
//		System.out.println("json==" + json);
        
        try {
        	Modehouse modehouse = JSON.parseObject(json, Modehouse.class);
        	Modehouse t = modehouseService.getModehouse(modehouse);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t != null) {
        		/*modeuser.setId(t.getId());
            	modeuserService.update(modeuser);*/
        		/*if (modeuser.getName() != null) {
        			t.setName(modeuser.getName());
        		}
        		if (modeuser.getEnableFlag() != null) {
        			t.setEnableFlag(modeuser.getEnableFlag());
        		}
        		*/
        		if (modehouse.getHouseName() != null && !modehouse.getHouseName().equals(t.getHouseName())) {
        			Modehouse t2 = modehouseService.get(modehouse);
            		if (t2 != null) { // 家同名
            			resultJson = "{\"result\":-1}";//成功1 失败0
                    	String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
                    	return;
            		}
            	}
        		t.setHouseName(modehouse.getHouseName());
        		t.setLasttime(new Date());
        		modehouseService.update(t);
            	resultJson = "{\"result\":1}";//成功1 失败0
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("renameHouse", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}
	}

	@RequestMapping("/hasDevice")
	public void hasDevice(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
        try {
        	Moderoom moderoom = JSON.parseObject(json,Moderoom.class); 
        	int result = modehouseService.hasDevice(moderoom.getHouseId(), moderoom.getRoomId());
        	String resultJson = "{\"result\":1,\"hasDevice\":" + result + "}";//0没有、1有
    		String callback = request.getParameter("callback");
    		out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("hasDevice", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
}
