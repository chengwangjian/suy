package sy.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;













import sy.model.Resource;
import sy.model.TempObject;
import sy.service.ResourceServiceI;
import sy.util.PropertiesUtil;



@Controller
@RequestMapping("/resourceController")
public class ResourceController {
	private static final Logger LOGGER = Logger.getLogger(ResourceController.class);
	private ResourceServiceI resourceService;

	public ResourceServiceI getResourceService() {
		return resourceService;
	}
	@Autowired
	public void setResourceService(ResourceServiceI resourceService) {
		this.resourceService = resourceService;
	}

	//共享资源上传,资源名称重新定义
	@RequestMapping(value = "/upload" , method = RequestMethod.POST)
	public void upload(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String resultJson = "";			
		String uuid = UUID.randomUUID().toString();
		TempObject temp = JSON.parseObject(json, TempObject.class);
		String loadpath = PropertiesUtil.getProperty("fileAddress");
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
				String filename = fileItems.get(0).getName();
				String type = filename.substring(filename.lastIndexOf(".")+1);	        	
				String path = loadpath + "\\" + uuid + "." + type;	     
				long length = fileItems.get(0).getSize();
				String name1 = uuid + "." + type;
				Iterator<FileItem> iter = fileItems.iterator();  
				while(iter.hasNext()){
					FileItem item = iter.next();  
					if (item.isFormField()) {  //普通字段
						String name = item.getFieldName();  
						String value = item.getString("UTF-8");
						Map<String, Object> params = JSON.parseObject(temp.getParameters(), Map.class);
						Resource resource = new Resource();
						resource.setHouseieee((String) params.get("houseieee"));
						resource.setDeviceuuid((String) params.get("deviceuuid"));
						resource.setResname((String) params.get("resname"));
						resource.setTaketime((String) params.get("taketime"));               
						int tt = this.resourceService.appupload(resource, path, type, length, uuid, name1);
						String callback = request.getParameter("callback");                              
						resultJson = "{\"status\":1,\"status_msg\":\"success\",\"resname\":" + '"' + name1 + '"'+ ",\"type\":"+ '"' + type + '"'+ ",\"uuid\":" + '"'  + uuid + '"' + "}";
						// out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");                  
						LOGGER.info("name-->:"+ name+ "  ,"+"value--->:" +value);
					} else {
						LOGGER.info(item.getFieldName() + "　 " + item.getName() + "　 " + item.isInMemory() + "　 " +item.getContentType() + "　 " + item.getSize());           
						String fileName = item.getName();
						String type1 = fileName.substring(fileName.lastIndexOf(".")+1);            	
						File file = new File(loadpath + "//" + uuid + "." +type1);
						item.write(file); 
						Map map = JSON.parseObject(json, Map.class);               
						int status = this.resourceService.savaupload(map, path, type, length, uuid, name1);                
						String callback = request.getParameter("callback");                              
						resultJson = "{\"status\":1,\"status_msg\":\"success\",\"resname\":" + '"' + name1 + '"'+ ",\"type\":"+ '"' + type + '"'+ ",\"uuid\":" + '"'  + uuid + '"' + "}";
						//out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");                 
					}  
					out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
				}
			}catch (Exception e) {
				// TODO: handle exception
				String callback = request.getParameter("callback");
				resultJson = "{\"status\":0,\"status_msg\":\"fail\"}";
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson  + ",\"msg\":上传失败！！！}"); 	
			}
		}
	}

	//可以查询一定时间里的共享资源信息
	@RequestMapping("/getList")
	public void getList(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		Map map = JSON.parseObject(json);
		int startRow = (Integer)map.get("startRow");
		int pageSize = (Integer)map.get("pageSize");
		String orderBy = (String)map.get("orderBy");
		try {
			if ((Integer)map.get("startRow") != null)
				startRow = 0;
			if ((Integer)map.get("pageSize") != null)
				pageSize = 30;
			List t = this.resourceService.getList(startRow, pageSize, orderBy, map);  
			String resultJson =  JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + "{\"result\":1" + "," + "\"getList\": " + resultJson + "}})");
		} catch (Exception e) {
			LOGGER.info("getList", e);
			String resultJson = "{\"result\":0}";
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}	    

	}

	//可以查询一定时间里共享资源总数
	@RequestMapping("/getListCount")
	public void getListCount(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {		
		PrintWriter out = response.getWriter();   
		response.setContentType("text/html;charset=utf-8");
		try {
			Map param = JSON.parseObject(json, Map.class);
			List tList = this.resourceService.getListCount(param);
			String resultJson = JSON.toJSONStringWithDateFormat(tList.get(0), "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getListCount", e);
			String resultJson = "{\"result\":0}";
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234,\"response_params\":" + resultJson + "})");
			return;
		}
	}

	//根据资源名称进行共享资源的下载
	@RequestMapping("/download")
	public void download(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		Map map = JSON.parseObject(json, Map.class);
		String name = map.get("resname").toString();
		File file = new File(name);
		String filename = file.getName();		
		String filetype = filename.substring(filename.lastIndexOf(".")+1);	  
		LOGGER.info("file是==》：" + file + ",filename的值是===>:" + filename);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String currPath = PropertiesUtil.getProperty("fileAddress");
			String appBugPath = currPath + filename;
			File path = new File(appBugPath);
			if (!path.exists()) {
				out = response.getWriter();
				String  resultJson = "{\"result\":0 ,\"msg\":您要下载的文件或图片路径可能被不存在或被清除了，请确认后在进行下载！！！}";
				String callback = request.getParameter("callback");
				out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
				return;
			}
			if(filetype.equalsIgnoreCase("jpg") || filetype.equalsIgnoreCase("jpeg") || filetype.equalsIgnoreCase("png") || filetype.equalsIgnoreCase("gif") || filetype.equalsIgnoreCase("bmp")) {
				BufferedImage img = ImageIO.read(path);
				if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
					out = response.getWriter();
					String  resultJson = "{\"result\":0 ,\"msg\":您要下载的图片可能出错或图片不完整！！！}";
					String callback = request.getParameter("callback");
					out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
					return;
				}
			}
			response.setContentType("multipart/form-data;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			bis = new BufferedInputStream(new FileInputStream(appBugPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] fileBytes = new byte[1024];
			int result = bis.read(fileBytes);
			while (result != -1) {
				bos.write(fileBytes);
				fileBytes = new byte[1024];
				result = bis.read(fileBytes);
			}
			bos.flush();		
		} catch(Exception e) {
			LOGGER.info("download getWriter", e);
			String resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println(resultJson + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
		finally {
			try {
				if (bis != null)
					bis.close();
			}catch (Exception ex) {
				LOGGER.info("download getWriter", ex);
				String resultJson = "{\"result\":0,\"msg\":\"" + ex.getMessage() + "\"}";
				out.println(resultJson + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");	
			}
		}
	}

	@RequestMapping("/dropres")
	public void dropres(String json, HttpServletRequest request, HttpServletResponse response ) throws IOException {		
		LOGGER.info("json=" + json);
		String resultJson = "{\"result\":1}";// 成功1 失败0
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, Object> params = JSON.parseObject(json, Map.class);
			String resname = (String)params.get("resname");
			resourceService.dropres(resname);
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");	
		} catch (Exception e) {
			LOGGER.info("dropres", e);
			resultJson = "{\"result\":0,\"msg\":\"" + e.getMessage() + "\"}";
			out.println("{\"request_id\": 1234,\"dropmsg\":删除失败！！,\"response_params\":" + resultJson + "}");
		}	
	}

}
