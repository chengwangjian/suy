package sy.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 判断服务器是否注册工具类
 * @author R&D1
 *
 */
public class ServerRegisterUtil {
	private static final Logger LOGGER = Logger.getLogger(ServerRegisterUtil.class);
	private static final String REGISTER_FILE_PASSWORD = "0112BV35FFNETCSH";
	private static final String REGISTER_MAC_PASSWORD = "024EFM814CWSHVOX";
	public static int isRegisterValid = 1; //判断注册是否有效

	public static void isServerValid() {
		LOGGER.info("-------------validServer");
    	String registerFilePath = ServerRegisterUtil.class.getResource("/").getPath() + File.separator + "register.rg";
    	File registerFile = new File(registerFilePath);
    	if(!registerFile.exists()) {
    		rewriteMACToRegisterFile(registerFile);
    		isRegisterValid = 0;
    	}
    	else {
    		InputStream is = null;
    		BufferedReader br = null; 
    		OutputStream os = null;
    		SAXReader reader = new SAXReader();
    		try {
    			br = new BufferedReader(new InputStreamReader(new FileInputStream(registerFile)));
    			String line = null;
    			StringBuilder lines = new StringBuilder();
    			while((line = br.readLine()) != null) {
    				lines.append(line);
    			}
    			if(StringUtils.isBlank(lines.toString())) {
    				isRegisterValid = 0;
    				rewriteMACToRegisterFile(registerFile);
    				return;
    			}
				String decodeStr = Httpproxy.urlCodec(lines.toString().trim(), REGISTER_FILE_PASSWORD);
				if(decodeStr.toLowerCase().indexOf("mac") != -1) {
					isRegisterValid = 0;
					rewriteMACToRegisterFile(registerFile);
    				return;
				}
				is = new ByteArrayInputStream(decodeStr.getBytes("UTF-8"));
	    		Document document = reader.read(is);// 读取XML文件
	    		Element root = document.getRootElement();
	    		Element registCodeEle = root.element("registCode");
	    		if(registCodeEle == null) {
	    			isRegisterValid = 0;
	    			rewriteMACToRegisterFile(registerFile);
    				return;
	    		}
    			String macs = registCodeEle.getText();
    			//判断验证码是否合法
    			if(StringUtils.isBlank(macs)) {
    				isRegisterValid = 0;
    				rewriteMACToRegisterFile(registerFile);
    				return;
    			}
				String decodeMacs = Httpproxy.urlCodec16(macs, REGISTER_MAC_PASSWORD);
				String[] macArr = decodeMacs.split(",");
				List<String> macList = MacAddressUtil.getMacAddress();
				boolean isFinded = false;
				for(String mac : macArr) {
					for(String mac2 : macList) {
						if(mac.equals(mac2)) {
							isFinded = true;
							break;
						}
					}
					if(isFinded) {
						break;
					}
				}
				if(!isFinded) {
					isRegisterValid = 0;
					rewriteMACToRegisterFile(registerFile);
    				return;
				}
				//判断是否过期
				Element firstUseTimeEle = root.element("firstUseTime");
				String firstUseTime = firstUseTimeEle.getText();
				Element lastUseTimeEle = root.element("lastUseTime");
				String lastUseTime = lastUseTimeEle.getText();
				String maxUseTime = root.element("maxUseTime").getText();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long maxUseTimes = sdf.parse(maxUseTime).getTime();
				long currTimes = System.currentTimeMillis();
				if(currTimes > maxUseTimes) {
					isRegisterValid = 0;
					rewriteMACToRegisterFile(registerFile);
    				return;
				}
				if(StringUtils.isBlank(firstUseTime)) {
					String firstUseTimeTmp = sdf.format(new Date());
					firstUseTimeEle.setText(firstUseTimeTmp);
					lastUseTimeEle.setText(firstUseTimeTmp);
				} 
				else {
					long firstUseTimes = sdf.parse(firstUseTime).getTime();
					if(currTimes < firstUseTimes) {
						isRegisterValid = 0;
						rewriteMACToRegisterFile(registerFile);
	    				return;
					}
					if(StringUtils.isBlank(lastUseTime)) {
						String lastUseTimeTmp = sdf.format(new Date());
						lastUseTimeEle.setText(lastUseTimeTmp);
						isRegisterValid = 1;
					}
					else {
						long lastUseTimes = sdf.parse(lastUseTime).getTime();
						if(currTimes < lastUseTimes) {
							isRegisterValid = 0;
							rewriteMACToRegisterFile(registerFile);
		    				return;
						}
						else {
							isRegisterValid = 1;
						}
					}
				}
				//回写数据
				String xmlStr = document.asXML();
				String encodeXmlStr = null;
				if(MacAddressUtil.getOSName().equals("windows"))
					encodeXmlStr = Httpproxy.urlEncryptGBK(xmlStr, REGISTER_FILE_PASSWORD);
				else
					encodeXmlStr = Httpproxy.urlEncrypt(xmlStr, REGISTER_FILE_PASSWORD);
				os = new FileOutputStream(registerFile);
				os.write(encodeXmlStr.getBytes());
    		} 
    		catch(Exception e) {
    			isRegisterValid = 0;
    			LOGGER.info("read register.rg exception", e);
    		}
    		finally {
    			try {
    				if(is != null)
    					is.close();
    				if(br != null)
    					br.close();
    				if(os != null)
    					os.close();
    			} 
    			catch(Exception e) {
    				LOGGER.info("close IO Exception", e);
    			}
    		}
    	}
	} 
	
	//若注册文件不存在或注册文件不合法或注册文件过期，重写注册文件，写入mac地址
	private static void rewriteMACToRegisterFile(File registerFile) {
		List<String> macList = MacAddressUtil.getMacAddress();
		String macs = null;
		if(macList.isEmpty()) {
			macs = "无法获取MAC地址，请手动填写";
		} 
		else {
			macs = macList.toString();
			macs = macs.substring(1, macs.length() - 1);
		}
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><register><mac>" + macs + "</mac></register>";
		OutputStream os = null;
		try {
			String encodeXmlStr = null;
			if(MacAddressUtil.getOSName().equals("windows"))
				encodeXmlStr = Httpproxy.urlEncryptGBK(xmlStr, REGISTER_FILE_PASSWORD);
			else
				encodeXmlStr = Httpproxy.urlEncrypt(xmlStr, REGISTER_FILE_PASSWORD);
			os = new BufferedOutputStream(new FileOutputStream(registerFile));
			os.write(encodeXmlStr.getBytes());
		} 
		catch(Exception e) {
			isRegisterValid = 0;
			LOGGER.info("write register.rg exception", e);
		} 
		finally {
			try {
				if(os != null)
					os.close();
			} 
			catch(Exception e) {
				isRegisterValid = 0;
				LOGGER.info("close first IO exception", e);
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerRegisterUtil.isServerValid();
		System.out.println(ServerRegisterUtil.isRegisterValid);
	}

}
