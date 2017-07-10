package sy.controller;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

/**
 * 3/4G Dongle短信推送类
 * @author ZhangHC
 *
 */
public class SendSMSDongle {
	private static final Logger logger = Logger.getLogger(SendSMS.class);
	private String dongleUrl = null;
	private static SendSMSDongle dongle;

	private SendSMSDongle(){}

	public static SendSMSDongle getDongle(){
		if(dongle==null){
			dongle = new SendSMSDongle();
		}
		return dongle;
	}

	/**
	 * 推送短信
	 * @param msg 短信内容
	 * @param phone 目标手机号
	 * @return
	 */
	public String sendSMS(String msg, String param, String ipAddress){
		if (StringUtils.isBlank(dongleUrl)) {
			dongleUrl = PropertiesUtil.getProperty("dongle.url");
		}
		String url = "http://" + ipAddress + dongleUrl + param;
		logger.info("url=" + dongleUrl);
		try{
			String result = TestHttpclient.getUrl(url, "UTF-8");
			logger.info("Dongle send result:" + result);
			return msg;
		} catch (IOException e) {
			logger.error("Dongle send error", e);
		}
		return "error";
	}
}
