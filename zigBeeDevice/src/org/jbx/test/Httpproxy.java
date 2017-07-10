package org.jbx.test;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import sy.pageModel.Json;
import sy.util.AESCodec;

public class Httpproxy {
	
	private static final Logger logger = Logger.getLogger(Httpproxy.class);  
	
	public static void main(String[] args) {
		Httpproxy proxy=new Httpproxy();
//		proxy.parsejson("http://localhost:8080/z203chat/poll?context=xmllogin/netvox/123456&username=00137a000001013b");
		proxy.parsejson("http://localhost:8080/z203chat/poll?context=xmlup&username=00137a0000010136");
	}
	
	static final String CONTENT_TYPE = "text/plain";
	private HttpClient client;
	
	public static void post() throws ClientProtocolException, IOException {  
        HttpClient httpclient = new DefaultHttpClient();  
        HttpPost post = new HttpPost("http://localhost:8080/action.jsp");  
        FileBody fileBody = new FileBody(new File("/home/sendpix0.jpg"));  
        StringBody stringBody = new StringBody("文件的描述");  
        MultipartEntity entity = new MultipartEntity();  
        entity.addPart("file", fileBody);  
        entity.addPart("desc", stringBody);  
        post.setEntity(entity);  
        HttpResponse response = httpclient.execute(post);  
        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){    
              
            HttpEntity entitys = response.getEntity();  
            if (entity != null) {  
                System.out.println(entity.getContentLength());  
                System.out.println(EntityUtils.toString(entitys));  
            }  
        }  
        httpclient.getConnectionManager().shutdown();  
    }  
	
	public String parsejson(String sign){	//ServletResponse resp) {
		
		String str=null;
		try {
			
	        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
	        connectionManager.setDefaultMaxPerRoute(0xFFFF);
	        connectionManager.setMaxTotal(0xFFFF);
	        client = new DefaultHttpClient(connectionManager);	
	        HttpGet backendRequest= new HttpGet(sign);
	        HttpResponse response = client.execute(backendRequest);
//	        resp.setContentType(CONTENT_TYPE);
//	        final ServletOutputStream outputStream = resp.getOutputStream();
//	        response.getEntity().writeTo(outputStream);
	        if(response!=null)str=EntityUtils.toString(response.getEntity());
	        logger.info(str);     
	    	Json js=JSON.parseObject(str, Json.class);
	    	if(js.getStatus()>=0){
		        Object ob=js.getMessage();
		        str=ob.toString();
	    	}
	        logger.info(str);	        
		} catch (Exception e) {			 
			e.printStackTrace();
		} finally{   
			client.getConnectionManager().shutdown();
		}
		return str;
	}
	
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
			logger.info(sign);			
			decryptFrom = AESCodec.parseHexStr2Byte(sign);  
			decryptResult = AESCodec.decrypt2(decryptFrom,password);
			
			// 解决中文乱码  不足16位补#
			/*while(content.getBytes().length%16!=0)
			content=content+"#";*/
			result=new String(decryptResult, "utf-8");
			logger.info("原始结果decrypt:" + result);
			// 移除所有#号
			result=StringUtils.remove(result,'#');
			// 移除所有空格(转发cgi)
			result = StringUtils.remove(result,'\0');
			// 解决中文乱码  不足16位补#
//			result=StringUtils.remove(new String(decryptResult, "utf-8"),'#');
			logger.info("最终结果decrypt:" + result); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}  				
		return result;
	}
	
	
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
