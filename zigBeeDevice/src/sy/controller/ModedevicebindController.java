package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sy.model.Modegrouplib;
import sy.pageModel.Modedevicebind2;
import sy.service.ModedevicebindServiceI;

import com.alibaba.fastjson.JSON;
import com.flywind.app.entities.Modedevicebind;

@Controller
@RequestMapping("/modedevicebindController")
public class ModedevicebindController {

	private ModedevicebindServiceI modedevicebindService;
	
	private static final Logger LOGGER = Logger.getLogger(ModedevicebindController.class);  

	public ModedevicebindServiceI getModedevicebindService() {
		return modedevicebindService;
	}

	@Autowired
	public void setModedevicebindService(ModedevicebindServiceI modedevicebindService) {
		this.modedevicebindService = modedevicebindService;
	}
	
	/**
	 * 保存单个设备绑定（正常绑定）
	 * @author: zhuangxd
	 * 时间：2013-12-26 下午8:20:41
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/NewBind")
	public void saveModedevicebind(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*modedevicebindService.test();
		return "showModedevicebind";*/
		
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
		
		/*List<Modedevicebind> modedevicebindList = JSON.parseArray(json,Modedevicebind.class); 
        for (Modedevicebind modedevicebind : modedevicebindList) { 
        	modedevicebindService.save(modedevicebind);
        } */
		
        try {
        	Modedevicebind modedevicebind = JSON.parseObject(json,Modedevicebind.class);
        	Modedevicebind t = modedevicebindService.get(modedevicebind);
        	/*if (StringUtils.isBlank(modedevicebind.getHouseIeee()) || modedevicebind.getHouseIeee().length() < 16 ) {
    			String resultJson = "{\"result\":0}";//成功1 失败0
        		    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
    		}*/
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t == null) {
        		/*if (StringUtils.isBlank(modedevicebind.getHouseIeee()) || modedevicebind.getHouseIeee().length() != 16) {
            		return;
            	}*/
        		/*if (StringUtils.isBlank(modedevicebind.getSecretKey())) {
        			modedevicebind.setSecretKey(modedevicebind.getHouseIeee().substring(6));
        		}
        		if (modedevicebind.getVendorCode() == null) {
        			modedevicebind.setVendorCode("NETVOX");
        		}
        		if (modedevicebind.getEncodemethod() == null) {
        			modedevicebind.setEncodemethod("0");
        		}*/
        		Modedevicebind modedevicebind2 = new Modedevicebind();
        		if (modedevicebind.getClusterId().contains(":")) {
        			String[] CluIdArr =  modedevicebind.getClusterId().split("@");
        			String[] sourclusterIdArr = CluIdArr[0].split(":");
        			String[] destclusterIdArr = CluIdArr[1].split(":");
        			String[] CluNameArr =  modedevicebind.getClusterName().split("@");
        			String[] sourclusterNameArr = CluNameArr[0].split(":");
        			String[] destclusterNameArr = CluNameArr[1].split(":");
//        			String[] clusterNameArr = modedevicebind.getClusterName().split(":");
        			String[] CluNameEnArr = null;
        			String sourclusterIdArrStr = "";
        			for(int i=0;i<sourclusterIdArr.length;i++){
        				sourclusterIdArrStr +=sourclusterIdArr[i]+"#";
        			}
        			List<String> tempList = new ArrayList<String>();
        			int isMacth=0;
        			for(int i=0;i<destclusterIdArr.length;i++){
        				if(sourclusterIdArrStr.contains(destclusterIdArr[i])){
        					tempList.add(destclusterIdArr[i]);
        					isMacth=1;
        				}
        			}
        			
        			if(isMacth!=0){
        				String sourclusterNameArrStr = "";
            			List<String> tempNameList = new ArrayList<String>();
            			for(int i=0;i<sourclusterNameArr.length;i++){
            				sourclusterNameArrStr+=sourclusterNameArr[i]+"#";
            			}
            			for(int i =0;i<destclusterNameArr.length;i++){
            				if(sourclusterNameArrStr.contains(destclusterNameArr[i])){
            					tempNameList.add(destclusterNameArr[i]);
            				}
            			}
            			List<String> tempNameEnList = new ArrayList<String>();
            			if(modedevicebind.getClusterNameEn()!=null){
            				CluNameEnArr = modedevicebind.getClusterNameEn().split("@");
            				String[] sourclusterNameEnArr = CluNameEnArr[0].split(":");
                			String[] destclusterNameEnArr = CluNameEnArr[1].split(":");
            				String sourclusterNameEnArrStr = "";
                			for(int i=0;i<sourclusterNameEnArr.length;i++){
                				sourclusterNameEnArrStr+=sourclusterNameEnArr[i]+"#";
                			}
                			for(int i =0;i<destclusterNameEnArr.length;i++){
                				if(sourclusterNameEnArrStr.contains(destclusterNameEnArr[i])){
                					tempNameEnList.add(destclusterNameEnArr[i]);
                				}
                			}
            				
            			}
//            			String[] clusterIdArr = modedevicebind.getClusterId().split(":");
            			for (int i=0;i<tempList.size();i++) {
            				modedevicebind2 = new Modedevicebind();
            				BeanUtils.copyProperties(modedevicebind, modedevicebind2);
            				modedevicebind2.setClusterId(tempList.get(i));
            				modedevicebind2.setBindType((short)0);
            				modedevicebind2.setDestType("3");
            				modedevicebind2.setClusterName(tempNameList.get(i));
            				if(tempNameEnList.size()>0){
            					modedevicebind2.setClusterNameEn(tempNameEnList.get(i));
            				}
            				modedevicebindService.save(modedevicebind2);
            				resultJson = "{\"result\":1}";//成功1 失败0
            			}
        			}else{
        				resultJson = "{\"result\":2}";//成功1 失败0主控和被控属性不匹配2
        			}
        		} else {
        			String[] cluIdArr = modedevicebind.getClusterId().split("@");
        			if(cluIdArr[0].equals(cluIdArr[1])){
        				modedevicebind.setClusterId(cluIdArr[0]);
        				String[] cluIdNameArr = modedevicebind.getClusterName().split("@");
        				modedevicebind.setClusterName(cluIdNameArr[0]);
        				if(modedevicebind.getClusterNameEn()!=null){
        					String[] cluIdNameEnArr = modedevicebind.getClusterNameEn().split("@");
            				modedevicebind.setClusterNameEn(cluIdNameEnArr[0]);
        				}
        				modedevicebind.setBindType((short)0);
        				modedevicebind.setDestType("3");
            			modedevicebindService.save(modedevicebind);
            			resultJson = "{\"result\":1}";//成功1 失败0
        			}else {
        				resultJson = "{\"result\":2}";//成功1 失败0主控和被控属性不匹配2
					}
        			
        		}
        		/*modedevicebindService.addDeviceAttributeHistoryTable(modedevicebind.getHouseIeee());
        		modedevicebindService.addDeviceOperateHistoryTable(modedevicebind.getHouseIeee());
        		modedevicebindService.addDeviceWarnHistoryTable(modedevicebind.getHouseIeee());*/
        	} else { 
        		// 设备已绑定，不能再绑定！
        		resultJson = "{\"result\":-1}";//成功1 失败0
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("saveModedevicebind", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveModedevicebind";
	}
	
	/**
	 * 组设备绑定(虚拟EP绑定)
	 * @author: zhuangxd
	 * 时间：2013-12-26 下午8:24:02
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/NewBindGroup")
	public void NewBindGroup(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		/*modedevicebindService.test();
		return "showModedevicebind";*/
		
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
		
		/*List<Modedevicebind> modedevicebindList = JSON.parseArray(json,Modedevicebind.class); 
        for (Modedevicebind modedevicebind : modedevicebindList) { 
        	modedevicebindService.save(modedevicebind);
        } */
		
        try {
        	// json post请求
    		response.setContentType("application/json");  
        	json = URLDecoder.decode(json, "gbk");
        	LOGGER.info("json2=" + json);
        	Modedevicebind2 modedevicebind2 = JSON.parseObject(json,Modedevicebind2.class);
        	modedevicebindService.insertModedevicebind(modedevicebind2);
        	/*if (StringUtils.isBlank(modedevicebind.getHouseIeee()) || modedevicebind.getHouseIeee().length() < 16 ) {
    			String resultJson = "{\"result\":0}";//成功1 失败0
        		    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
        		return;
    		}*/
        	String resultJson = "{\"result\":1}";//成功1 失败0
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
    			String callback = request.getParameter("callback");out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
		} catch (Exception e) {
			LOGGER.info("NewBindGroup", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
//			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
			    			String callback = request.getParameter("callback");out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");   
		}

//        String resultJson = "{\"result\":\"1\"}";
        
//		out.flush();
		
//		System.out.println("resultJson==" + resultJson);
		
//		return "saveModedevicebind";
	}
/**
 * 绑定编辑修改
 * @param json
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
	@RequestMapping("/update")
	public void update(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();  
		
        try {
        	Modedevicebind modedevicebind = JSON.parseObject(json,Modedevicebind.class); 
        	Modedevicebind t = modedevicebindService.getDeviceBind(modedevicebind);
        	String resultJson = "{\"result\":0}";//成功1 失败0
        	if (t != null) {
        		/*modedevicebind.setId(t.getId());
            	modedevicebindService.update(modedevicebind);*/
        		if (Long.toString(modedevicebind.getHouseId())!=null) {
        			t.setHouseId(modedevicebind.getHouseId());
        		}
        		if (Long.toString(modedevicebind.getSourceId()) != null) {
        			t.setSourceId((modedevicebind.getSourceId()));
        		}
        		if (modedevicebind.getSourceDevicename() != null) {
        			t.setSourceDevicename(modedevicebind.getSourceDevicename());
        		}
        		if (modedevicebind.getSourceDeviceUniqueId()!= null) {
        			t.setSourceDeviceUniqueId(modedevicebind.getSourceDeviceUniqueId());
        		}
        		if (modedevicebind.getSourceVirtualEp() != null) {
        			t.setSourceVirtualEp(modedevicebind.getSourceVirtualEp());
        		}
        		if (modedevicebind.getClusterId()!= null) {
        			t.setClusterId(modedevicebind.getClusterId());
        		}
        		if (modedevicebind.getClusterName()!= null) {
        			t.setClusterName(modedevicebind.getClusterName());
        		}
        		if (modedevicebind.getClusterNameEn() != null) {
        			t.setClusterNameEn(modedevicebind.getClusterNameEn());
        		}
        		if (Short.toString(modedevicebind.getBindType()) != null) {
        			t.setBindType(modedevicebind.getBindType());
        		}
        		if (Long.toString(modedevicebind.getDestId()) != null) {
        			t.setDestId(modedevicebind.getDestId());
        		}
        		if (modedevicebind.getDestName() != null) {
        			t.setDestName(modedevicebind.getDestName());
        		}
        		if (modedevicebind.getDestDeviceUniqueId() != null) {
        			t.setDestDeviceUniqueId(modedevicebind.getDestDeviceUniqueId());
        		}
        		if (modedevicebind.getDestEp() != null) {
        			t.setDestEp(modedevicebind.getDestEp());
        		}
            	modedevicebindService.update(t);
            	resultJson = "{\"result\":1}";//成功1 失败0
        	}
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
	/**
	 * 获取某一个House所有的绑定记录
	 * @author: zhuangxd
	 * 时间：2013-12-26 下午4:51:10
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetBindList")
	public void GetModeNodeList(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		String language = request.getParameter("language");
        try {
        	Modedevicebind modedevicebind=JSON.parseObject(json, Modedevicebind.class);
        	Map t = modedevicebindService.findBindList(modedevicebind,language);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetModeNodeList", e);
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
	 * 返回source设备列表
	 * @author: zhuangxd
	 * 时间：2013-12-26 下午5:24:05
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetBindableSourceDeviceList")
	public void GetBindableSourceDeviceList(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		String behavior = request.getParameter("behavior");
        try {
        	Modedevicebind modedevicebind=JSON.parseObject(json, Modedevicebind.class);
        	Map t = modedevicebindService.findBindableSourceDeviceList(modedevicebind,behavior);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetBindableSourceDeviceList", e);
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
	 * 返回dest设备列表
	 * @author: zhuangxd
	 * 时间：2013-12-26 下午5:24:05
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetBindAbleDestDeviceList")
	public void GetBindAbleDestDeviceList(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		String language = request.getParameter("language");
		String behavior = request.getParameter("behavior");
        try {
        	Modedevicebind modedevicebind=JSON.parseObject(json, Modedevicebind.class);
        	Map t = modedevicebindService.findBindAbleDestDeviceList(modedevicebind,language,behavior);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetBindAbleDestDeviceList", e);
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
	 * 返回虚拟ep信息
	 * @author: zhuangxd
	 * 时间：2013-12-26 下午7:30:20
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/GetBindAbleVirtualEPList")
	public void GetBindAbleVirtualEPList(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		PrintWriter out=response.getWriter(); 
		response.setContentType("text/html;charset=utf-8");  
		
        try {
        	Modegrouplib modegrouplib=JSON.parseObject(json, Modegrouplib.class);
        	Map t = modedevicebindService.findBindAbleVirtualEPList(modegrouplib);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
//    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			/*String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   */
			LOGGER.info("GetBindAbleVirtualEPList", e);
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
	 * 删除绑定
	 * @author: zhuangxd
	 * 时间：2013-12-27 上午9:33:21
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
        	Modedevicebind modedevicebind = JSON.parseObject(json,Modedevicebind.class); 
        	modedevicebindService.delete(modedevicebind);
        	String resultJson = "{\"result\":1}";//成功1 失败0
    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("delete", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			    			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}

/**
 * 编辑显示主、被控设备
 * @param json
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
	@RequestMapping("/find")
	public void find(String json,HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out=response.getWriter();  
		response.setContentType("text/html;charset=utf-8");  
        try {
        	Modedevicebind modedevicebind = JSON.parseObject(json,Modedevicebind.class); 
        	Map t = modedevicebindService.findBind(modedevicebind);
        	String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("find", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	
}
