package sy.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Common {

    private final static Log log = LogFactory.getLog(Common.class);
    private static Common instance = null;
    public static final String GET_URL = "http://www.jiucool.com/request.php?key=j0r56u2"; 
    public static final String POST_URL = "http://www.jiucool.com/request.php"; 


    public static Common getInstance() {
        if(instance == null){
            instance = new Common();
        }
        return instance;
    }

    /**
     * java中，发送http的post请求
     * 参数param可以是xml串，也可以是是参数对，比如名称=值&名称=值
     */
    public static void sendHttp(String urlStr, String param) throws Exception {
    	
    	URL url = null;
    	HttpURLConnection url_con = null;
    	StringBuffer tempStr = new StringBuffer();
    	BufferedReader rd = null;
        String line;
//        param = URLEncoder.encode(param, "gbk");
    	try {
    		url = new URL(urlStr);
    		url_con = (HttpURLConnection) url.openConnection();
    		url_con.setRequestMethod("POST"); // post请求
    		url_con.setDoOutput(true);
    		// 进行编码
    		url_con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//    		url_con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 两种方式结果一样。

    		// 发送请求
    		url_con.getOutputStream().write(param.getBytes());
    		
    		/*url_con.setRequestMethod("GET"); // post请求
    		DataOutputStream out = new DataOutputStream(url_con 
                    .getOutputStream()); 
    		out.writeBytes(URLEncoder.encode(param, "utf-8"));*/

    		url_con.getOutputStream().flush();
    		url_con.getOutputStream().close();   
    		InputStream in = url_con.getInputStream();
    		
    		// 接收数据
            rd = new BufferedReader(new InputStreamReader(in));
            while ((line = rd.readLine()) != null) {
//            	log.debug(line);
            	tempStr.append(line);
            }
            log.info(tempStr.toString());
    	}catch (Exception e) {
//    		log.error("发送http请求失败", e);
    		throw e;
    	} finally {
    		if (url_con != null) {
    			url_con.disconnect();
    		}
    		if (rd != null) {
    			rd.close();
    		}
    	}
    }
    
    /**
     * java中，发送http的post请求   解决乱码
     * 参数param可以是xml串，也可以是是参数对，比如名称=值&名称=值
     */
    public static void sendHttpPost(String urlStr, String param) throws Exception {
    	
    	URL url = null;
    	HttpURLConnection url_con = null;
    	StringBuffer tempStr = new StringBuffer();
    	BufferedReader rd = null;
        String line;
//        param = URLEncoder.encode(param, "gbk");
    	try {
    		url = new URL(urlStr);
    		url_con = (HttpURLConnection) url.openConnection();
    		url_con.setRequestMethod("POST"); // post请求
    		url_con.setDoOutput(true);
    		// 进行编码
    		url_con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//    		url_con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    		DataOutputStream out = new DataOutputStream(url_con 
                    .getOutputStream()); 
    		// 发送请求
//    		out.writeBytes(URLEncoder.encode(param, "utf-8"));
    		out.write(param.getBytes());
    		
    		/*url_con.setRequestMethod("GET"); // post请求
    		DataOutputStream out = new DataOutputStream(url_con 
                    .getOutputStream()); 
    		out.writeBytes(URLEncoder.encode(param, "utf-8"));*/

    		out.flush();
    		out.close();   
    		InputStream in = url_con.getInputStream();
    		
    		// 接收数据
            rd = new BufferedReader(new InputStreamReader(in));
            while ((line = rd.readLine()) != null) {
//            	log.debug(line);
            	tempStr.append(line);
            }
            log.info(tempStr.toString());
    	}catch (Exception e) {
//    		log.error("发送http请求失败", e);
    		throw e;
    	} finally {
    		if (url_con != null) {
    			url_con.disconnect();
    		}
    		if (rd != null) {
    			rd.close();
    		}
    	}
    }
    
    /**
     * java中，发送http的GET请求
     * 参数param可以是xml串，也可以是是参数对，比如名称=值&名称=值
     * 解决乱码
     */
    public static void sendHttpGet(String urlStr,String param) throws Exception {
    	// Common.sendHttpGet("http://192.168.1.123:8080/zigBeeDevice/houseController/find.do","json=" + URLEncoder.encode("{\"houseIEEE\":\"4443455322222276\",\"name\":\"用户李\",\"longitude\":\"22\",\"latitude\":\"33\",\"networkAddress\":\"192.168.1.123\",\"port\":\"8081\",\"description\":\"192.168.1.123\"}","gbk"));
    	URL url = null;
    	HttpURLConnection url_con = null;
    	StringBuffer tempStr = new StringBuffer();
    	BufferedReader rd = null;
        String line;
//        param = URLEncoder.encode(param, "gbk");
        urlStr = urlStr + "?" + param;
    	try {
    		url = new URL(urlStr);
    		url_con = (HttpURLConnection) url.openConnection();
//    		url_con.addRequestProperty("Content-Type", "text/html;charset=gbk");
    		url_con.connect();
    		InputStream in = url_con.getInputStream();
    		
    		// 接收数据
            rd = new BufferedReader(new InputStreamReader(in));
            while ((line = rd.readLine()) != null) {
//            	log.debug(line);
            	tempStr.append(line);
            }
            log.info(tempStr.toString());
    	}catch (Exception e) {
//    		log.error("发送http请求失败", e);
    		throw e;
    	} finally {
    		if (url_con != null) {
    			url_con.disconnect();
    		}
    		if (rd != null) {
    			rd.close();
    		}
    	}
    }
    
    /** 
	 * 创建指定数量的随机字符串 (随机生成手机短信验证码)
	 * @param numberFlag 是否是数字 
	 * @param length 
	 * @return 
	 */
	public static String createRandom(boolean numberFlag, int length){ 
		String retStr = ""; 
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz"; 
		int len = strTable.length(); 
		boolean bDone = true; 
		do { 
			retStr = ""; 
			int count = 0; 
			for (int i = 0; i < length; i++) { 
				double dblR = Math.random() * len; 
				int intR = (int) Math.floor(dblR); 
				char c = strTable.charAt(intR); 
				if (('0' <= c) && (c <= '9')) { 
					count++; 
				} 
				retStr += strTable.charAt(intR); 
			} 
			if (count >= 2) { 
				bDone = false; 
			} 
		} while (bDone); 

		return retStr; 
	}
}
