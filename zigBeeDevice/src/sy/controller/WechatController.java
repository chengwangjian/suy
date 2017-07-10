package sy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import sy.model.HouseWeixin;
import sy.model.Houseieee;
import sy.service.CacheWarnMessageServiceI;
import sy.service.HouseWeixinServiceI;
import sy.service.WechatServiceI;
import sy.util.AESCodec;
import sy.util.HouseieeeListener;
import sy.util.Httpproxy;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

/**
 * 用于绑定微信、推送微信告警信息
 * @author zhanghc
 * @since 2016-06-21
 */
@Controller
@RequestMapping("/wchat")
public class WechatController {
	private final static Logger LOGGER = Logger.getLogger(WechatController.class);

	private WechatServiceI wechatService;
	private HouseWeixinServiceI weixinService;
	private CacheWarnMessageServiceI messageService;

	public WechatServiceI getWechatService() {
		return wechatService;
	}
	@Autowired
	public void setWechatService(WechatServiceI wechatService) {
		this.wechatService = wechatService;
	}
	public HouseWeixinServiceI getWeixinService() {
		return weixinService;
	}
	@Autowired
	public void setWeixinService(HouseWeixinServiceI weixinService) {
		this.weixinService = weixinService;
	}
	public CacheWarnMessageServiceI getMessageService() {
		return messageService;
	}
	@Autowired
	public void setMessageService(CacheWarnMessageServiceI messageService) {
		this.messageService = messageService;
	}

	/**
	 * 奈伯思用户绑定微信账号
	 * @param json 绑定信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/bindUser")
	public void bindUser(String json,HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		//设置允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		try {
			Map<String,Object> userMap = JSON.parseObject(json,Map.class);	
			//验证用户名及密码
			String houseIeee = (String) userMap.get("houseIeee");
			String userName = (String) userMap.get("username");
			String password = (String) userMap.get("password");
			String password2 = (String) userMap.get("password2");
			//用户密码解密
			if(StringUtils.isNotBlank(password2)&&StringUtils.isNotBlank(password))
				password = Httpproxy.urlCodec(password, Httpproxy.getKey(password2));

			//获取houseIeee连接的云端ip与port
			String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(houseIeee);
			String serverPort = PropertiesUtil.getProperty("xmpp.port");
			String vendorCode = ((Houseieee) HouseieeeListener.houseieeeMap.get(houseIeee)).getVendorCode();

			//通过xmpp向网关发送登陆请求，以验证该账户是奈伯思智慧家的用户
			StringBuilder loginUrl = new StringBuilder("http://");
			loginUrl.append(serverIp).append(":").append(serverPort).append("/spring-async/z203chat/poll?context=xmllogin/")
			.append(userName).append("/").append(password).append("/").append(vendorCode)
			.append("&username=").append(houseIeee);
			//接口参数加密
			String content="context=xmllogin/"+userName+"/"+password+"&username="+houseIeee;
			while(content.getBytes().length%16!=0)
				content=content+"#";
			String key = houseIeee.substring(6, 16)+"NETVOX";
			byte[] encryptResult = AESCodec.encrypt2(content, key);
			String encryptResultStr = AESCodec.parseByte2HexStr(encryptResult);
			loginUrl.append("&sign=").append(encryptResultStr).append("&encodemethod=AES&houseIeeeSecret=").append(houseIeee);
			String str = TestHttpclient.getUrl(loginUrl.toString(),"utf-8");
			Map<String, Object> result = JSON.parseObject(str, Map.class);
			int status = (int) ((Map) result.get("message")).get("status");
			int res = 0;
			if(status != 0) {
				if(status==4||status==6)
					//用户名错误
					res = -1;
				else if(status==5)
					//密码错误
					res = -2;
				else if(status==-1)
					//登陆超时
					res = -3;
				else
					//其他原因登陆失败
					res = -4;
			}else{
				//绑定奈伯思智慧家与微信号
				res = this.wechatService.bindUser(userMap);
			}
			out.print("{\"result\":"+res+"}");
		} catch (Exception e) {
			LOGGER.error("binduser", e);
			out.print("{\"result\":-4}");
		}
	}

	/**
	 * 解绑
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value ="/disbind")
	public void disbind(String json,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		out = response.getWriter();
		try {
			Map<String,Object> map = JSON.parseObject(json,Map.class);	
			String openId = (String)map.get("openid");//this.wechatService.getweixinopenid(map);	
			int result = this.wechatService.disbind(map);

			out.print("{\"result\":" + result + ", \"openId\":\"" + openId + "\"}");
		} catch (Exception e) {
			LOGGER.error("disbind", e);
			out.print("{\"result\":0}");
		}
	}

	@RequestMapping(value = "/getUserInfo")
	public void getUserInfo(String json,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		out = response.getWriter();
		try {
			Map<String,Object> map=JSON.parseObject(json,Map.class);	
			List<Map> userList=this.wechatService.getUserInfo(map);	
			out.print("{\"response_params\":"+JSON.toJSONStringWithDateFormat(userList, "yyyy-MM-dd HH:mm:ss")+"}");
		} catch (Exception e) {
			LOGGER.error("getUserInfo", e);
			out.print("{\"result\":0}");
		}	
	}
	
	/**
	 * 根据houseIeee获取绑定的微信账号
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getWeixinAccount")
	public void getWeixinAccount(String json,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		out = response.getWriter();
		try {
			String houseIeee = request.getParameter("houseIeee");
			List<HouseWeixin> userList=this.weixinService.getHouseWeixinAccounts(houseIeee);
			out.print(JSON.toJSONStringWithDateFormat(userList, "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			LOGGER.error("getUserInfo", e);
			out.print("");
		}	
	}

	/**
	 * 进入页面默认取10条时间  2016-01-01
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getTimeInfo")
	public void getTimeInfo(String json,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		out = response.getWriter();
		try {
			Map map=JSON.parseObject(json,Map.class);	
			List<Map> list=this.messageService.getTimeInfo(map);	
			out.print("{\"response_params\":"+JSON.toJSONString(list)+"}");
		} catch (Exception e) {
			LOGGER.error("getTimeInfo exception", e);
			out.print("{\"result\":0}");
		}
	}

	/**
	 * 滚动条滚动获取10个时间
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getTimeInfo2")
	public void getTimeInfo2(String json,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		out = response.getWriter();
		try {
			Map map=JSON.parseObject(json,Map.class);	
			List<Map> list=this.messageService.getTimeInfo2(map);	
			out.print("{\"response_params\":"+JSON.toJSONString(list)+"}");
		} catch (Exception e) {
			LOGGER.error("getTimeInfo2", e);
			out.print("{\"result\":0}");
		}
	}
	/**
	 * 进入页面默认取20条告警消息
	 */
	@RequestMapping(value = "/getDeviceInfoDetail")
	public void getDeviceInfoDetail(String json,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		out = response.getWriter();
		try {
			Map map=JSON.parseObject(json,Map.class);	
			List<Map> list=this.messageService.getDeviceInfoDetail(map);	
			out.print("{\"response_params\":"+	 JSON.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm:ss")+"}");
		} catch (Exception e) {
			LOGGER.error("getDeviceInfoDetail", e);
			out.print("{\"result\":0}");
		}


	}
	/**
	 * 滚动时获取20条告警消息
	 * @param json
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getDeviceInfoDetail2")
	public void getDeviceInfoDetail2(String json,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Content-Type", "text/html; charset=utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = null;
		out = response.getWriter();
		try {
			Map map=JSON.parseObject(json,Map.class);	
			List<Map> list=this.messageService.getDeviceInfoDetail2(map);	
			out.print("{\"response_params\":"+	 JSON.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm:ss")+"}");
		} catch (Exception e) {
			LOGGER.error("getDeviceInfoDetail2", e);
			out.print("{\"result\":0}");
		}
	}
}
