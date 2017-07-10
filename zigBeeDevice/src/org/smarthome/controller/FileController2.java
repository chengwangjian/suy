package org.smarthome.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jbx.test.FileTest;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.smarthome.model.FileMeta2;

import sy.util.PropertiesUtil;

import com.alibaba.fastjson.JSON;

import zbHouse.util.TestLog4j;

@Controller
@RequestMapping("/controller2")
public class FileController2 {

	private static final Logger LOGGER = Logger.getLogger(FileController2.class);
	FileMeta2 fileMeta = null;
	/***************************************************
	 * URL: /rest/controller/upload  
	 * upload(): receives files
	 * @param request : MultipartHttpServletRequest auto passed
	 * @param response : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 ****************************************************/
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public @ResponseBody void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		List<FileMeta2> files=new ArrayList<FileMeta2>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
//        HashMap<String, String> request = new HashMap<String, String>();  
        if (isMultipart) {  
            DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024 * 20, null);  
            ServletFileUpload upload = new ServletFileUpload(factory);  
            upload.setHeaderEncoding("UTF-8");  
            upload.setSizeMax(1024 * 1024 * 20);  
        try{
            List<FileItem> fileItems = upload.parseRequest(request);  
            Iterator<FileItem> iter = fileItems.iterator();  
            while (iter.hasNext()) {  
                FileItem item = iter.next();  
                if (item.isFormField()) {  
                    String name = item.getFieldName();  
                    String value = item.getString("UTF-8");  
                    TestLog4j.testInfo("name----"+name+"value----"+value);  
                } else {  
//                    byte[] filebytes = item.get();  
                    if (StringUtils.isBlank(item.getName())) {  
                        continue;  
                    }
	       			fileMeta = new FileMeta2();
	       			fileMeta.setFileName(item.getName());
                    if(StringUtils.contains(fileMeta.getFileName(), "\\")){
                    	int pos=StringUtils.lastIndexOf(fileMeta.getFileName(), "\\");                  	
                    	fileMeta.setFileName(StringUtils.substring(fileMeta.getFileName(), pos+1));
                    	TestLog4j.testInfo(fileMeta.getFileName());
                    }	    			 
	    			 fileMeta.setFileSize(item.getSize()/1024+" Kb");
	    			 fileMeta.setDownloadInfo("ftp://" + PropertiesUtil.getProperty("ftpUser") + ":" +
	 						PropertiesUtil.getProperty("ftpPass") + "@" +
							PropertiesUtil.getProperty("ftpIp") + ":" + 
							PropertiesUtil.getProperty("ftpPort") + "/release/"+
	    					 	fileMeta.getFileName());//item.getContentType());  			 
	    			 fileMeta.setMd5(SaveFileFromInputStream(item.getInputStream(),PropertiesUtil.getProperty("versionPath"),
	    					 PropertiesUtil.getProperty("versionPath")+fileMeta.getFileName()));
	    			 files.add(fileMeta);
                }  
            }
        }catch (Exception e) {
        	LOGGER.info("upload", e);
        }
    }	 
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Pragma","No-cache");
//        response.setHeader("Cache-Control","no-cache");
        response.setContentType("text/html;charset=utf-8"); 
        int a;
        if(files.size()!=0){
        	 a=1;
        }
        else {
        	a=0;
		}
        String jstr=JSON.toJSONString(files);
		out.println("{\"request_id\": 1234, \"response_params\":" + jstr + ",\"result\":"+a+"}"); 
//  	  response.getWriter().write(jstr);
  	  response.getWriter().flush();
  	  response.getWriter().close();
//		return files;
 
	}
	//写入文件md5编码
	public String SaveFileFromInputStream(InputStream stream,String filepath,String filename) {      
		FileOutputStream fs=null;
		try {
			FileTest.getFile(filepath);//创建目录
			fs=new FileOutputStream(filename);
	        byte[] buffer =new byte[1024*1024];
	        int bytesum = 0;
	        int byteread = 0;
	        MessageDigest md=MessageDigest.getInstance("MD5");	
	        while ((byteread=stream.read(buffer))!=-1)
	        {
	        	md.update(buffer, 0, byteread);
	        	bytesum+=byteread;
	        	fs.write(buffer,0,byteread);
	        	fs.flush();
	        } 
	        fs.close();
	        stream.close(); 
	        byte[] b = md.digest();
	        return hex(b).toUpperCase();    
		}catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.info("SaveFileFromInputStream", e);
		}finally {
			try {
				if(fs != null) {
					fs.close();
				}
			} catch(Exception e) {
				LOGGER.info("SaveFileFromInputStream", e);
			}
		}
		return "";
	 }    
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
	
	@RequestMapping(value = "/preupload", method = RequestMethod.GET)
	 public void preupload(HttpServletResponse response,HttpServletRequest request, String json) throws Exception {
		PrintWriter out = response.getWriter();
		Map<String, Object> dg = new HashMap<String, Object>();
		try{
		  Map<String, Object> params = JSON.parseObject(json,Map.class);
		  final String json1=URLDecoder.decode((String)params.get("value"), "gbk");
		  File dir = new File(PropertiesUtil.getProperty("versionPath"));
	      FilenameFilter filter = new FilenameFilter() {
	         @Override
			public boolean accept(File dir, String name) {
	        	 TestLog4j.testInfo("----------file name: "+json1);
	        	 TestLog4j.testInfo(name.contentEquals(json1));
	            return false/*name.contentEquals(json1)*/;
	        }
	      };
	      String[] children = dir.list(filter);
//	      TestLog4j.testInfo((children != null)+JSON.toJSONString(children)+children.length);
	      if (children != null &&children.length>0) {
	    	  dg.put("result", -1);
	      }else{
	    	  dg.put("result", 1);
	      }
	      response.setHeader("Access-Control-Allow-Origin", "*");
	      response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
    	  response.setContentType("text/html;charset=utf-8");
    	  String jstr=JSON.toJSONString(dg);
    	  out.println("{\"request_id\": 1234,\"result\":"+jstr+"}"); 
//    	  response.getWriter().write(jstr);
    	  response.getWriter().flush();
    	  response.getWriter().close();
		}catch(Exception e){
			 dg.put("result", 0);
			 String jstr=JSON.toJSONString(dg);
	    	 out.println("{\"request_id\": 1234,\"result\":"+jstr+"}"); 
  			LOGGER.info("preupload", e);
  		}
	}
	/***************************************************
	 * URL: /rest/controller/get/{value}
	 * get(): get file as an attachment
	 * @param response : passed by the server
	 * @param value : value from the URL
	 * @return void
	 ****************************************************/
	@RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
	 public void get(HttpServletResponse response,@PathVariable String value){
		 FileMeta2 getFile =fileMeta;
		 try {		
			 	response.setContentType(getFile.getFileType());
			 	response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
		        FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
		 }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
	 }
 
}
