package sy.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.smarthome.model.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import sy.service.CamerapicServiceI;
import zbHouse.pageModel.Json;
import zbHouse.util.TestLog4j;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


@Controller
@RequestMapping("/cameraPicController")
public class CameraPicController {
	  private static final Logger LOGGER = Logger.getLogger(CameraPicController.class);
	  private CamerapicServiceI camerapicService;
	    
	public CamerapicServiceI getCamerapicService() {
			return camerapicService;
	}
	@Autowired
	public void setCamerapicService(CamerapicServiceI camerapicService) {
		this.camerapicService = camerapicService;
	}	
	
	//pic上传图片
	@RequestMapping(value="/uploadPic", method = RequestMethod.POST)
	public void uploadPic(String json, HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String resultJson = "";	
		String loadpath = request.getSession().getServletContext().getRealPath("/WEB-INF/uploadpic/"); //上传文件存放目录
	    File f = new File(loadpath); 
		if(!f.exists()){ 
			f.mkdir(); 
		}
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);    
	        if (isMultipart) {  
	            DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024 * 20, null);  
	            ServletFileUpload upload = new ServletFileUpload(factory);  
	            upload.setHeaderEncoding("UTF-8");  
	            upload.setSizeMax(1024 * 1024 * 20);  
	        try {  
	        	List<FileItem> fileItems = upload.parseRequest(request);  
	        	String type = fileItems.get(0).getContentType();
	        	String path = loadpath + "\\" + fileItems.get(0).getName();
	        	Iterator<FileItem> iter = fileItems.iterator();  
		   while(iter.hasNext()){
			   	FileItem item = iter.next();  
                if (item.isFormField()) {  
                    String name = item.getFieldName();  
                    String value = item.getString("UTF-8");  
                    TestLog4j.testInfo("name----"+name+"value----"+value);  
                }else {
            	    System.out.println(item.getFieldName() + "　 " + item.getName() + "　 " + item.isInMemory() + "　 " +item.getContentType() + "　 " + item.getSize());
            	    File file = new File(loadpath+"//"+item.getName()); 
            	    item.write(file); 
                }
                Map map = JSON.parseObject(json, Map.class);               
                int status = this.camerapicService.saveLog(map, path, type);
                String callback = request.getParameter("callback");
                resultJson = "{\"status\":1,\"status_msg\":\"success\"}";
                out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");                 
		   		}  		    
			    }catch (Exception e) {
				// TODO: handle exception
				   String callback = request.getParameter("callback");
				   resultJson = "{\"status\":0,\"status_msg\":\"fail\"}";
				   out.println("{\"request_id\": 1234, \"response_params\":" + resultJson  +"}"); 	
			   }
	        }
		}
	
		
	//pic图片信息总数可以根据具体的时间进行查询哪个时间段的图片信息总数
	 @RequestMapping("/getPicListCount")
	  public void getPicListCount(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
	    Map param = JSON.parseObject(json, Map.class);
	    PrintWriter out = response.getWriter();   
	    try {
	      List tList = this.camerapicService.getPicListCount(param);
	      String resultJson = JSON.toJSONStringWithDateFormat(tList.get(0), "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
	      String callback = request.getParameter("callback");
	      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	    } catch (Exception e) {
	      LOGGER.info("successCount", e);
	      String resultJson = "{\"result\":0}";
	      String callback = request.getParameter("callback");
	      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	      return;
	    }
	  }
	 
	//pic图片数量可以根据具体的时间进行查询哪个时间段的图片信息数量
	 @RequestMapping("/getPicList")
	  public void getPicList(String json, HttpServletRequest request, HttpServletResponse response) throws IOException
	  {
	    Map map = JSON.parseObject(json);
	    PrintWriter out = response.getWriter();
	    response.setContentType("text/html;charset=utf-8");
	    Json j = new Json(); 
	    DataGrid dg = new DataGrid();
	    String startRow = (String)map.get("startRow");
	    String pageSize = (String)map.get("pageSize");
	    String orderBy = (String)map.get("orderBy");
	    try {
	      if (StringUtils.isBlank(startRow))
	        startRow = "0";
	      if (StringUtils.isBlank(pageSize))
	        pageSize = "30";
	      List t = this.camerapicService.getPicList(startRow, pageSize, orderBy, map);
	      String resultJson = JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
	      String callback = request.getParameter("callback");
	      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	    } catch (Exception e) {
	      LOGGER.info("getPicList", e);
	      String resultJson = "{\"result\":0}";
	      String callback = request.getParameter("callback");
	      out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
	    }
	  }
		
	/*@RequestMapping("/updown")
	public void updown(String path,HttpServletRequest request, HttpServletResponse response) throws IOException
	{         
		BufferedInputStream fis = null;
		BufferedOutputStream fos = null;
		try {
		     // path是指欲下载的文件的路径。
		     File file = new File(path);
		     // 取得文件名。
		     String filename = file.getName();
		     // 取得文件的后缀名。
		     String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();		            
		     // 设置response的Header
		     response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		     response.addHeader("Content-Length", "" + file.length());
		     response.setContentType("multipart/form-data");
		     // 以流的形式下载文件。
		     fis = new BufferedInputStream(new FileInputStream(path));
		     fos =  new BufferedOutputStream(response.getOutputStream());
		     byte[] buffer = new byte[20 * 1024];
		     int result = fis.read(buffer);
		     while(result != -1) {
		          fos.write(buffer);
		          buffer = new byte[20 * 1024];
		          result = fis.read(buffer);
		     }
		       fos.flush();		 		        
		     } catch (Exception e) {
				// TODO: handle exception
		        LOGGER.info("updown", e);
			 }
			 finally {
				try {
					 if(fos != null )
					   fos.close();
					 if(fis != null)
					 	fis.close();
				    }catch (Exception e) {
					// TODO: handle exception
					  LOGGER.info("updown close stream", e);
					}
			  		}
	  }*/
		
	//获取图片总数
	@RequestMapping("/getPicListCount1")
	public void getPicListCount1(String json, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, Object> param = JSON.parseObject(json, Map.class);
			long enmgCount = camerapicService.getPicLogCount1((String) param.get("searchText"));
			String resultJson = "{\"result\":1, \"total\":" + enmgCount + "}";
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			} catch (Exception e) {
				LOGGER.error("getGatewayLogCount", e);
				String resultJson = "{\"result\":0}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			}
		}
	//pic图片总数
	@RequestMapping("/getPicList1")
	public void getPicList1(String json, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map jsonMap = JSON.parseObject(json, Map.class);
			List<Map> iosList = camerapicService.getPicLogs(	 					
						(String) jsonMap.get("searchText"),
						(Integer) jsonMap.get("pageSize"),
						(Integer) jsonMap.get("startRow"));
			String resultJson = JSON.toJSONStringWithDateFormat(iosList, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			} catch (Exception e) {
				LOGGER.error("getPicList", e);
				String resultJson = "{\"result\":0}";// 成功1 失败0
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
			}
		}
	
	 //pic图片下载
	 @RequestMapping("download")
	 public void download(String filename,HttpServletRequest request, HttpServletResponse response)
	{
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		response.setContentType("application/x-msdownload;charset=utf-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String currPath = CameraPicController.class.getResource("/").getPath();
			String appBugPath = currPath.substring(0, currPath.indexOf("zigBeeDevice") + 13) + "WEB-INF/uploadpic/" + filename;
			bis = new BufferedInputStream(new FileInputStream(appBugPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] fileBytes = new byte[1024];
			int result = bis.read(fileBytes);
			while(result != -1) {
				bos.write(fileBytes);
				fileBytes = new byte[1024];
				result = bis.read(fileBytes);
			}
			    bos.flush();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.info("download", e);
			}
			finally {
			    try {
				   if(bis != null)
					 bis.close();
				   if(bos != null)
					 bos.close();
					} catch(Exception e) {
						LOGGER.info("downlaod close stream", e);
					}
				    }
			}
	
}
