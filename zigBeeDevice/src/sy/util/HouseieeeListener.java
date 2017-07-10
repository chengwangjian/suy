package sy.util;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

import sy.controller.HouseController;
import sy.model.FarmUser;
import sy.model.Houseieee;
import sy.model.Proxyserver;
import sy.service.FarmUserServiceI;
import sy.service.HouseieeeServiceI;
import sy.service.ProxyserverServiceI;

/**
 * 监听器，tomcat启动时，加载数据
 * @author: zhuangxd
 * 时间：2014-9-9 上午9:07:12
 */
public class HouseieeeListener implements ServletContextListener {
	public static Map houseieeeMap = new HashMap();
	public static Map houseieeeProxyserverMap = new HashMap();
	public static Map<String,FarmUser> farmUserMap = new HashMap<>();
	private static final Logger LOGGER = Logger.getLogger(HouseieeeListener.class);
	private static HouseieeeServiceI houseieeeService;
	private static ProxyserverServiceI proxyserverService;
	private static FarmUserServiceI userService;
	
	/**
	 * 销毁 当servlet容器终止web应用时调用该方法
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	/**
	 * 初始化 当servlet容器启动web应用时调用该方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
		houseieeeService = (HouseieeeServiceI) ctx.getBean("houseieeeService"); // 填写要注入的类,注意第一个字母小写
		proxyserverService = (ProxyserverServiceI) ctx.getBean("proxyserverService"); // 填写要注入的类,注意第一个字母小写
		userService = (FarmUserServiceI) ctx.getBean("farmUserService");
		
		Houseieee houseieee = new Houseieee();
		houseieee.setEnableFlag("1");
		List<Houseieee> t = houseieeeService.findList(houseieee);
		if (t !=null && t.size() > 0) {
			for (Houseieee m:t) {
				houseieeeMap.put(m.getHouseIeee(), m);
				HouseController.maponline.put(m.getHouseIeee(), 0);
			}
		}
		Proxyserver proxyserver = new Proxyserver();
		proxyserver.setEnableFlag("1");
		proxyserver.setType("1");// 查找云端服务器的ip
    	List<Proxyserver> t2 = proxyserverService.findList(proxyserver);
    	if (t2 !=null && t2.size() > 0) {
			for (Proxyserver m:t2) {
				houseieeeProxyserverMap.put(m.getHouseIeee(), m.getServerIp());
			}
		}
    	
    	//缓存所有农业用户
    	cacheFarmUser();
	}
	
	/**
	 * 重新加载
	 * @author: zhuangxd
	 * 时间：2014-9-9 上午11:23:07
	 */
	public void initHouseieee() {
		Houseieee houseieee = new Houseieee();
		houseieee.setEnableFlag("1");
		List<Houseieee> t = houseieeeService.findList(houseieee);
		if (t !=null && t.size() > 0) {
			for (Houseieee m:t) {
				houseieeeMap.put(m.getHouseIeee(), m);
			}
		}
		
		Proxyserver proxyserver = new Proxyserver();
		proxyserver.setEnableFlag("1");
		proxyserver.setType("1");
    	List<Proxyserver> t2 = proxyserverService.findList(proxyserver);
    	if (t2 !=null && t2.size() > 0) {
			for (Proxyserver m:t2) {
				houseieeeProxyserverMap.put(m.getHouseIeee(), m.getServerIp());
			}
		}
    	
    	//缓存所有农业用户
//    	List<FarmUser> farmUserList = userService.getFarmUsers();
//    	if(farmUserList!=null){
//    		for(FarmUser user:farmUserList){
//    			farmUserMap.put(user.getUsername(), user);
//    		}
//    	}
	}

	public static void cacheFarmUser() {
		List<Map> userList = Collections.emptyList();
		String strIsMain = PropertiesUtil.getProperty("cloudMain");
		if(StringUtils.isBlank(strIsMain)){
			LOGGER.info("the flag for server type is blank ···");
			return;
		}
    	int isMain = Integer.parseInt(strIsMain);
    	if(isMain == 1) {
    		userList = userService.getFarmUsers();
    	}
    	else {
			try {
	    		String proxyId = PropertiesUtil.getProperty("cloudAddress");
				String proxyPort = PropertiesUtil.getProperty("cloudPort");
				if(StringUtils.isBlank(proxyId)||StringUtils.isBlank(proxyPort)){
					LOGGER.info("the proxy server ip or port is blank ···");
					return;
				}
	    		//从代理服务器获取用户密码
	    		String url = "http://" + proxyId + ":" + proxyPort + "/zigBeeDevice/farmUser/getFarmUsers.do";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("callback", "1234");
				params.put("encodemethod", "NONE");
				params.put("sign", "AAA");
				String reslStr = TestHttpclient.postUrlWithParams2(url, params, "utf-8").trim();
				Map<String, Object> userMap = JSON.parseObject(reslStr);
				Map<String, Object> paramMap = (Map<String, Object>) userMap.get("response_params");
				if(paramMap != null) {
					int result = (Integer) paramMap.get("result");
					if(result == 1) {
						userList = (List<Map>) paramMap.get("userList");
					}
				}
			}
			catch(Exception e) {
				LOGGER.error("getFarmUsers", e);
				userList = Collections.emptyList();
			}
    	}
    	LOGGER.info("获取的用户总量是：" + userList.size());
		for(Map<String, Object> userMap : userList){
			FarmUser farmUser = new FarmUser();
			String userName = (String) userMap.get("user_name");
			farmUser.setUsername(userName);
			String pwd = (String) userMap.get("pwd");
			farmUser.setPassword(pwd);
			Object tempPid = userMap.get("parent_id");
			if(tempPid instanceof BigInteger){
				BigInteger parentid = (BigInteger)tempPid;
				farmUser.setParentId(parentid==null?null:parentid.longValue());
			}else{
				Integer parentid = (Integer)tempPid;
				farmUser.setParentId(parentid==null?null:parentid.longValue());
			}
			farmUserMap.put(userName, farmUser);			
    	}
	}
	
	/**
	 * 重载用户
	 */
	public static void reloadFarmUser(String userName){
		StringBuilder strUrl = new StringBuilder("http://");
		strUrl.append(PropertiesUtil.getProperty("cloud.agenthost")).append(":").append(PropertiesUtil.getProperty("cloud.agentport"));
		strUrl.append("/zigBeeDevice/farmUser/getFarmUser.do?");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userName", userName);
		try {
			String result = TestHttpclient.postUrlWithParams2(strUrl.toString(), params, "utf-8");
			if(StringUtils.isNotBlank(result)){
				Map<String,Object> rMap = JSON.parseObject(result, Map.class);
				if((Integer)rMap.get("result")==0){
					FarmUser user = JSON.parseObject((String)rMap.get("user"),FarmUser.class);
			    	if(user!=null){
			    		farmUserMap.put(user.getUsername(), user);
			    	}else{
			    		LOGGER.info("no users");
			    	}
				}else{
					LOGGER.info("reload farm users faild");
				}
			}else{
				LOGGER.error("Test Http Error");
			}
		} catch (IOException e) {
			LOGGER.error("reload farm users error");
		}
	}
}
