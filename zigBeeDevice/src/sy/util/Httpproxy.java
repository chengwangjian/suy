package sy.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.model.Houseieee;
import sy.service.HouseieeeServiceI;

@Service("httpproxy")
public class Httpproxy {
	
	private static final Logger logger = Logger.getLogger(Httpproxy.class);  
	
	private HouseieeeServiceI houseieeeService;

	public HouseieeeServiceI getHouseieeeService() {
		return houseieeeService;
	}

	@Autowired
	public void setHouseieeeService(HouseieeeServiceI houseieeeService) {
		this.houseieeeService = houseieeeService;
	}
	
	public static void main(String[] args) {
		Httpproxy proxy=new Httpproxy();
		String content = "613C4F785787384495B30C2BBF25DECE6E3682AFB3F4AA37FDBBA59A3EB3CA5FB47C5AE92B54192FD19967465CBDDDB321BCD33A3DABD3C4B87A8CBCCAE9621C9C3FC8987583B654617B8F551E6D5CB7";
		String password = "000000DBB5NETVOX";
		System.out.println(Httpproxy.urlCodec(content, password));
//		proxy.urlEncrypt("5717188", "00137A0000012E8A", null);
//		proxy.parsejson("http://localhost:8080/z203chat/poll?context=xmllogin/netvox/123456&username=00137a000001013b");
//		proxy.parsejson("http://localhost:8080/z203chat/poll?context=xmlup&username=00137a0000010136");
	}
	
	public static String httpCallback(HttpServletRequest req,String ip,String port){ 
		
		String str=null;
		try {
			
			String url="";
			if (StringUtils.isBlank(port)) {
				url="http://"+ ip + req.getRequestURI().substring(13);
			} else if (StringUtils.isNotBlank(port) && port.equals("80")) {
				// 访问外网ip
				// https://218.104.133.243:80/cgi-bin/rest/network/getZBNode.cgi?callback=1234&encodemethod=NONE&sign=AAA
				url="https://"+ ip + ":" + port + req.getRequestURI().substring(13);
			} else if (StringUtils.isNotBlank(port) && !port.equals("80")) {
				url="http://"+ ip + ":" + port + req.getRequestURI().substring(13);
			}
			if (StringUtils.isNotBlank(req.getQueryString())) { 
				// get中文乱码
				url = url + "?" + EncodeUtils.encodeStr(req.getQueryString(), "gbk"); 
			} 
			//url= ip +url.substring(13);
			logger.info(url);
			str=TestHttpclient.getUrl(url,"utf-8");
			
		} catch (Exception e) {			 
//			e.printStackTrace();
			logger.info("httpCallback", e);
		}
		return str;
	}
	
	/**
	 * AES加密算法,打印密钥日志,优先 houseIeeeSecret获取密钥，否则默认值password密钥
	 * @author: zhuangxd
	 * 时间：2014-7-30 下午4:15:49
	 * @param sign
	 * @param password
	 * @return
	 */
	public String urlEncrypt(String content, String houseIeeeSecret, String password){	
//		AESCodec.content=sign;	
//		AESCodec.password="1234567887654321";//路由器向云端post推送数据，请求.do密钥
		//AESCodec.password="00137A000000DBC3";//android get请求转发cgi密钥
//		AESCodec.password="0000007785NETVOX";
		if (StringUtils.isNotBlank(password)) {
			password = "00137A000000DBC3";
		}
		if (StringUtils.isNotBlank(houseIeeeSecret)) {
			Houseieee houseieee = new Houseieee();
    		houseieee.setHouseIeee(houseIeeeSecret);
			Houseieee t = houseieeeService.isFilter(houseieee);
    		if (t != null) {
    			password = t.getSecretKey() + t.getVendorCode();
    		} else {
    			password = houseIeeeSecret.substring(6) + "NETVOX";
    		}
		}
		String encryptResultStr = "";
		while(content.getBytes().length%16!=0)
			content=content+"#";
		//加密   
		logger.info("加密前：" + content);  
		try {
			byte[] encryptResult = AESCodec.encrypt2(content, password);  
			encryptResultStr = AESCodec.parseByte2HexStr(encryptResult); 
			logger.info("加密后：" + encryptResultStr); 
		} catch (Exception e) {
		}
		
		return encryptResultStr;
	}
	
	/**
	 * AES加密算法,打印密钥日志
	 * @author: hlw
	 * @param sign
	 * @param password
	 * @return
	 */
	public static String urlEncrypt(String content, String password){	
		String encryptResultStr = "";
		while(password.getBytes().length%16!=0)
			password += "0";
		//加密   
		logger.info("加密前：" + content);  
		try {
			while(password.getBytes().length % 16!=0)
				password=password+"0";
			while(content.getBytes().length % 16!=0)
				content=content+"#";
			byte[] encryptResult = AESCodec.encrypt2(content, password);  
			encryptResultStr = AESCodec.parseByte2HexStr(encryptResult);
			
			logger.info("加密后：" + encryptResultStr); 
		} catch (Exception e) {
			logger.info("encrypt exception", e);
		}
		return encryptResultStr;
	}
	
	/**
	 * AES加密算法,打印密钥日志
	 * @author: hlw
	 * @param sign
	 * @param password
	 * @return
	 */
	public static String urlEncryptGBK(String content, String password){	
		String encryptResultStr = "";
		//加密   
		logger.info("加密前：" + content);  
		try {
			while(password.getBytes().length % 16!=0) {
				password=password+"0";
			}
			while(content.getBytes().length % 16!=0)
				content=content+"#";
			byte[] encryptResult = AESCodec.encryptGBK(content, password);  
			encryptResultStr = AESCodec.parseByte2HexStr(encryptResult);
			
			logger.info("加密后：" + encryptResultStr); 
		} catch (Exception e) {
			logger.info("encrypt exception", e);
		}
		return encryptResultStr;
	}
	
	
	/**
	 * AES解密算法,打印密钥日志
	 * @author: zhuangxd
	 * 时间：2014-7-30 下午4:15:49
	 * @param sign
	 * @param password
	 * @return
	 */
	public static String urlCodec(String sign, String password){	
//		AESCodec.content=sign;	
//		AESCodec.password="1234567887654321";//路由器向云端post推送数据，请求.do密钥
		//AESCodec.password="00137A000000DBC3";//android get请求转发cgi密钥
//		AESCodec.password="0000007785NETVOX";
		
		//解密   
		byte[] decryptFrom = null;  
		byte[] decryptResult=null;
		String result=null;
		try {
//			logger.info(sign);			
			decryptFrom = AESCodec.parseHexStr2Byte(sign);  
			decryptResult = AESCodec.decrypt2(decryptFrom,password);
			
			// 解决中文乱码  不足16位补#
			/*while(content.getBytes().length%16!=0)
			content=content+"#";*/
			result=new String(decryptResult, "utf-8");
//			logger.info("原始结果decrypt:" + result);
			// 移除所有#号
			result=StringUtils.remove(result,'#');
			// 移除所有空格(转发cgi)
			result = StringUtils.remove(result,'\0');
			// 解决中文乱码  不足16位补#
//			result=StringUtils.remove(new String(decryptResult, "utf-8"),'#');
//			logger.info("最终结果decrypt:" + result); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.info("urlCodec", e);
		}  				
		return result;
	}
	
	/**
	 * AES解密算法,抛出异常
	 * @author: hlw
	 * @param sign
	 * @param password
	 * @return
	 */
	public static String urlDecodeAddExp(String sign, String password) throws Exception {	
		int yu = password.length() % 16;
		if(yu != 0) {
			int addZero = 16 - yu;
			for(int i = 0; i < addZero; i++) {
				password += "0";
			}
		}
		//解密   
		byte[] decryptFrom = null;  
		byte[] decryptResult=null;
		String result=null;
		decryptFrom = AESCodec.parseHexStr2Byte(sign);  
		decryptResult = AESCodec.decrypt2(decryptFrom,password);
		// 解决中文乱码  不足16位补#
		result=new String(decryptResult, "utf-8");
		// 移除所有#号
		result=StringUtils.remove(result,'#');
		// 移除所有空格(转发cgi)
		result = StringUtils.remove(result,'\0');
		return result;
	}
	
	public static String urlCodecGBK(String sign, String password){	
		//解密   
		byte[] decryptFrom = null;  
		byte[] decryptResult=null;
		String result=null;
		try {
			logger.info(sign);			
			decryptFrom = AESCodec.parseHexStr2Byte(sign);  
			decryptResult = AESCodec.decrypt2(decryptFrom,password);
			
			// 解决中文乱码  不足16位补#
			result=new String(decryptResult, "GBK");
			logger.info("原始结果decryptGBK:" + result);
			// 移除所有#号
			result=StringUtils.remove(result,'#');
			// 移除所有空格(转发cgi)
			result = StringUtils.remove(result,'\0');
			logger.info("最终结果decryptGBK:" + result); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("解码GBK异常", e);
//			e.printStackTrace();
		}  				
		return result;
	}
	
	/**
	 * AES解密算法,打印密钥日志，密钥不足16位补0
	 * @author: hlw
	 * @param sign
	 * @param password
	 * @return
	 */
	public static String urlCodec16(String sign, String password){	
		//解密   
		byte[] decryptFrom = null;  
		byte[] decryptResult=null;
		String result=null;
		try {
			while(password.getBytes().length % 16 != 0)
				password += "0";
			decryptFrom = AESCodec.parseHexStr2Byte(sign);  
			decryptResult = AESCodec.decrypt2(decryptFrom,password);
			result=new String(decryptResult, "utf-8");
			// 移除所有#号
			result=StringUtils.remove(result,'#');
			// 移除所有空格(转发cgi)
			result = StringUtils.remove(result,'\0');
		} catch (Exception e) {
			logger.info("urlCodec16", e);
		}  				
		return result;
	}
	
	/**
	 * AES解密算法，不打印密钥日志
	 * @author: zhuangxd
	 * 时间：2013-11-20 下午1:46:26
	 * @param sign
	 * @param password
	 * @return
	 */
	public static String urlCodec2(String sign, String password){	
//		AESCodec.content=sign;	
//		AESCodec.password="1234567887654321";//路由器向云端post推送数据，请求.do密钥
		//AESCodec.password="00137A000000DBC3";//android get请求转发cgi密钥
//		AESCodec.password="0000007785NETVOX";
		
		//解密   
		byte[] decryptFrom = null;  
		byte[] decryptResult=null;
		String result=null;
		try {
			//logger.info(sign);			
			decryptFrom = AESCodec.parseHexStr2Byte(sign);  
			decryptResult = AESCodec.decrypt2(decryptFrom,password);
			
			// 解决中文乱码  不足16位补#
			/*while(content.getBytes().length%16!=0)
			content=content+"#";*/
			result=new String(decryptResult, "utf-8");
//			logger.info("原始结果decrypt:" + result);
			// 移除所有#号
			result=StringUtils.remove(result,'#');
			// 移除所有空格(转发cgi)
			result = StringUtils.remove(result,'\0');
			// 解决中文乱码  不足16位补#
//			result=StringUtils.remove(new String(decryptResult, "utf-8"),'#');
			//logger.info("最终结果decrypt:" + result); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.info("urlCodec2", e);
		}  				
		return result;
	}
	
	public static String getKey(String key) {
		int strLen = key.length();
		String keyStr = key;
		if(strLen < 16) {		
			int strSize = 16 - strLen;
			for(int i = 0; i < strSize; i++) {
				keyStr += "0";
			}
		}
		else if(strLen > 16) {
			keyStr = keyStr.substring(0, 16);
		}	
		return keyStr;
	}
}
