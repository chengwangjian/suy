package sy.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;


public class TestHttpclient {
	private static final Logger log = Logger.getLogger(TestHttpclient.class);
    public static String getUrl(String url, String encoding)  
            throws IOException {
    	log.info("url=" + url);
        // 默认的client类。  
    	HttpClient client = new DefaultHttpClient();
//    	client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");  
    	
    	// 设置为get取连接的方式.  
    	HttpGet get = new HttpGet(url);  
        // 得到返回的response. 
    	HttpResponse response = null;
    	String str=null;
    	// HttpClient 4 请一定设置超时时间
    	client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);//连接超时时间60s
    	client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);//数据传输超时时间60s
    	try {
        	response = client.execute(get); 
        	HttpEntity entity = response.getEntity();  
            if (entity != null) {  
//                System.out.println("内容编码是：" + entity.getContentEncoding());  
//                System.out.println("内容类型是：" + entity.getContentType());              
                // 得到返回的主体内容.  
                str=EntityUtils.toString(entity,"UTF-8");
                log.info("get:\n"+str); 
                /*InputStream instream = entity.getContent();  
                try {  
                    BufferedReader reader = new BufferedReader(  
                            new InputStreamReader(instream, encoding));  
                    System.out.println(reader.readLine());  
                } catch (Exception e) {  
                    e.printStackTrace();  
                } finally {  
                    instream.close();  
                }*/ 
            } 
		} catch (Exception e) {
//			e.printStackTrace();
			log.info("getUrl", e);
		}
 
        // 关闭连接.  
        client.getConnectionManager().shutdown();  
        return str;
    }  
    public static String postUrlWithFile2(String url,Map<String,Object> param,FileItem fileItem){
    	
    	org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
    	HttpPost post = new HttpPost(url);
    	String str = "";
    	MultipartEntity entity = new MultipartEntity();
    	try {
			HttpClient httpClient = new DefaultHttpClient();
	    	if (param != null && !param.isEmpty()) { 
				for (Map.Entry<String, Object> entry : param.entrySet()) {
					entity.addPart(entry.getKey(), new StringBody((String) entry.getValue(),Charset.forName("UTF-8")));
				}
			}
            File fullFile = new File(fileItem.getName());  

	    	if(fileItem!=null)
			{
				entity.addPart(fileItem.getName(), new FileBody(fullFile)); 
			}
	    	post.setEntity(entity);
			HttpResponse response = httpClient.execute(post); 
			int stateCode = response.getStatusLine().getStatusCode();
			StringBuffer sb = new StringBuffer(); 
			if (stateCode == HttpStatus.SC_OK) {
				System.out.println("upload success");
				HttpEntity result = response.getEntity(); 
				if (result != null) { 
					InputStream is = result.getContent(); 
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String tempLine; 
					while ((tempLine = br.readLine()) != null) { 
						sb.append(tempLine); 
					}
				}
			}
			post.abort(); 
			return sb.toString(); 
        } catch (Exception e) {
//            e.printStackTrace();
        	log.info("postUrlWithFile2", e);
        } finally {
        	 //释放连接
        	post.releaseConnection();
        }
		return str;
    }
    
public static String postUrlWithFile(String url,Map params,FileInputStream inputStream){
    	
    	org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
    	PostMethod postMethod = new PostMethod(url); 
    	String str = "";
//    	File targetFile = null;
    	try {
            if (params != null && params.keySet().size() > 0) {  
                Iterator iterator = params.entrySet().iterator();  
                while (iterator.hasNext()) {  
                    Map.Entry entry = (Map.Entry) iterator.next(); 
                    postMethod.setParameter((String) entry.getKey(),  
                            (String) entry.getValue());
                     
                }  
            }    
            RequestEntity requestEntity = new InputStreamRequestEntity(inputStream);
            postMethod.setRequestEntity(requestEntity);
            //对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
//            File file = new File("c:/新闻.xml");     
//            Part[] parts = new Part[] {new CustomFilePart(file.getName(), file)};     
//            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));     
//            int statuscode=client.executeMethod(filePost); 
//          Part[] parts = new Part[]{new CustomFilePart(file.getName(), file)};    
//			MultipartRequestEntity mre=new MultipartRequestEntity(parts, postMethod.getParams());
//			postMethod.setRequestEntity(mre);           
//          client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);//设置超时
            int status;
            status = client.executeMethod(postMethod);
            if(status == HttpStatus.SC_OK){
            	str = postMethod.getResponseBodyAsString();
            	log.info("上传成功--------->服务器返回值:"+str);
            } else {
				log.info("上传失败--------->status:"+status);
			}
             
        } catch (Exception e) {
//            e.printStackTrace();
        	log.info("postUrlWithFile", e);
        } finally {
        	 //释放连接
        	postMethod.releaseConnection();
        }
		return str;
    }

    public static String postUrlWithParams(String url, Map params, String encoding)  
            throws IOException {
    	log.info("url:" + url);
        log.info("params:" + params);
        if(url.contains("plugins/userService/usergroup")){
        	System.out.println();
        }
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        HttpPost httpost = new HttpPost(url);  
        // 添加参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if (params != null && params.keySet().size() > 0) {  
            Iterator iterator = params.entrySet().iterator();  
            while (iterator.hasNext()) {  
                Map.Entry entry = (Map.Entry) iterator.next();  
                nvps.add(new BasicNameValuePair((String) entry.getKey(),  
                        (String) entry.getValue()));  
            }  
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
  
        HttpResponse response = httpclient.execute(httpost); 
        HttpEntity entity = response.getEntity();  
    	String str=null;
        if (entity != null) { 
        	str=EntityUtils.toString(entity);
        	log.info("post:\n"+str);
        }
        // 关闭请求  
        httpclient.getConnectionManager().shutdown();  
        return str;
 
    } 
    
    public static String postUrlWithParams2(String url, Map params, String encoding)  
            throws IOException {
    	log.info("url:" + url);
        log.info("params:" + params);
        if(url.contains("plugins/userService/usergroup")){
        	System.out.println();
        }
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        HttpPost httpost = new HttpPost(url);  
        // 添加参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if (params != null && params.keySet().size() > 0) {  
            Iterator iterator = params.entrySet().iterator();  
            while (iterator.hasNext()) {  
                Map.Entry entry = (Map.Entry) iterator.next();  
                nvps.add(new BasicNameValuePair((String) entry.getKey(),  
                        (String) entry.getValue()));  
            }  
        }
        //设置连接超时时间为20秒（以防止某些定时器因执行时长超过定时时长而停止）
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 18000);
        //设置数据传输超时20秒
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 18000);
        httpost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
  
        HttpResponse response = httpclient.execute(httpost); 
        HttpEntity entity = response.getEntity();  
    	String str=null;
        if (entity != null) { 
        	str=EntityUtils.toString(entity);
        	log.info("post:\n"+str);
        }
        // 关闭请求  
        httpclient.getConnectionManager().shutdown();  
        return str;
 
    } 
    
     
   /* public static void main(String[] args) throws Exception {
    	String url = "http://192.168.1.187:8081/zigBeeDevice/deviceController/SendStandRequest.do";
//    	String data = "{data_type:1,data:/cgi-bin/rest/network/RebootZ203.cgi?name=watchdog&user_name=watchdog&callback=1213[haier123]&encodemethod=aes&sign=3FE02734053F2F816EB5F6E1A0423F6524E54F1E1B7B34B93BA226052493BAC70689B509663F18EB0EF4A9222E98C845E4B6D177183F9266D49ADDDE55C32BA559D517E11E94858E766294A34959B04B4B73BDD0CC802DE50A488670F53B0784}";
    	String data = "{data_type:1,data:/cgi-bin/rest/network/getversion.cgi?encodemethod=NONE&sign=AAA&callback=[weixin2]}";
    	String dataEncode = "json={\"houseIeee\":\"00137A0000012DFF\",\"Data\":\"" + data+ "\"}";
    	data = "{\"houseIeee\":\"00137A0000012DFF\",\"Data\":\"" + data+ "\"}";
    	String password = "0000012DFFNETVOX";
    	String sign = Httpproxy.urlEncrypt(dataEncode, password);
    	Map param = new HashMap();
    	param.put("json", data);
    	param.put("encodemethod", "AES");
    	param.put("sign", sign);
    	TestHttpclient.postUrlWithParams(url, param, "utf-8");
    }*/
    /**
     * 添加自定义头部的http post请求
     * @param url 请求地址
     * @param params 接口参数
     * @param timeOut 接口超时时长
     * @param header 自定义请求头部参数
     * @return 请求结果（0：成功；其他：失败<接口返回信息直接写入日志>）
     */
    public static String postByCostomHeader(String url,Map<String,Object> params,Map<String,String> header,Integer timeOut){
    	if(params==null){
    		params = new HashMap<>();
    		log.info("The request params are empty······");
    	}
    	if(header==null){
    		header = new HashMap<>();
    		log.info("The request header is empty······");
    	}
		log.info("The post request begin······");
			//添加的拾联账户类型
			HttpURLConnection connection = null;
			DataOutputStream dataout = null;
			BufferedReader bf = null;
			try{
				URL postUrl = new URL(url);
				connection = (HttpURLConnection) postUrl.openConnection();
				// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)  
				connection.setDoOutput(true);  
				// 设置连接输入流为true  
				connection.setDoInput(true);
				// 设置请求方式为post  
				connection.setRequestMethod("POST");  
				// post请求缓存设为false  
				connection.setUseCaches(false); 
				// 设置该HttpURLConnection实例是否自动执行重定向  
				connection.setInstanceFollowRedirects(true);
				//设置请求头
				for(String key:header.keySet()){
					connection.setRequestProperty(key, header.get(key));
				}
				//设置连接12s超时
			    connection.setConnectTimeout(timeOut);
				connection.setReadTimeout(timeOut);
				// 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)  
				connection.connect();  
				// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)  
				dataout = new DataOutputStream(connection.getOutputStream());  
				//json参数  
				String parm = JSON.toJSONString(params);
				log.info("传递参数为："+parm);  
				// 将参数输出到连接  
				dataout.writeBytes(parm);  
				// 输出完成后刷新并关闭流  
				dataout.flush();  
				 // 重要且易忽略步骤 (关闭流,切记!)
				dataout.close(); 
				
				// 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)  
				bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
				String line;  
				StringBuilder sb = new StringBuilder(); // 用来存储响应数据  

				// 循环读取流,若不到结尾处  
				while ((line = bf.readLine()) != null) {  
					sb.append(line).append(System.getProperty("line.separator"));  
				}  
				String resultStr = sb.toString();
				log.info("请求返回的数据是：" + resultStr); 
				return resultStr;
			}catch(Exception e){
				log.error("connect to shilian platform error", e);
			}finally{
				if(dataout!=null){
					try {
						dataout.flush();
						dataout.close();
					} catch (IOException e) {
						log.error("close DataOutputStream error ···", e);
					}
				}
				if(bf!=null)
					try {
						bf.close();
					} catch (IOException e) {
						log.error("close BufferedReader error ···", e);
					}
				if(connection!=null)
					connection.disconnect();
			}
		return "";
    }
    
    public static String getUrl(String url,Map params,String encoding)  
            throws IOException {
    	log.info("url=" + url);
        // 默认的client类。  
    	HttpClient client = new DefaultHttpClient();
    	
    	 // 添加参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if (params != null && params.keySet().size() > 0) {  
            Iterator iterator = params.entrySet().iterator();  
            while (iterator.hasNext()) {  
                Map.Entry entry = (Map.Entry) iterator.next();  
                nvps.add(new BasicNameValuePair((String) entry.getKey(),  
                        (String) entry.getValue()));  
            }  
        }    
        String sendParams = EntityUtils.toString(new UrlEncodedFormEntity(nvps, encoding));
        // 设置为get取连接的方式.
        url = (url.indexOf("?") > -1)?url + sendParams : url + "?" + sendParams ;
    	HttpGet get = new HttpGet(url);  
        // 得到返回的response. 
    	HttpResponse response = null;
    	String str=null;
    	// HttpClient 4 请一定设置超时时间
    	client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);//连接超时时间60s
    	client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);//数据传输超时时间60s
    	try {
        	response = client.execute(get); 
        	HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                // 得到返回的主体内容.  
                str=EntityUtils.toString(entity,"UTF-8");
                log.info("get:\n"+str); 
            } 
		} catch (Exception e) {
//			e.printStackTrace();
			log.info("getUrl", e);
		}
 
        // 关闭连接.  
        client.getConnectionManager().shutdown();  
        return str;
    }  
    
}
