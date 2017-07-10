package sy.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;

import sy.model.Modenodelib;
import sy.service.ModenodelibServiceI;
import sy.util.FileUploadUtils;

@Controller
@RequestMapping("/modenodelibController")
public class ModenodelibController {
	
	private static final Logger LOGGER = Logger.getLogger(ModenodelibController.class);
	
	private ModenodelibServiceI modenodelibService;
	
//	private static final String[] allowTypes = {".jpg",".gif",".jpeg",".png",".bmp"};
	private static final String[] allowTypes = {".jpg"};
	
	/**
	 * 验证字符串是否为正确路径名的正则表达式 
	 * 通过 sPath.matches(matches) 方法的返回值判断是否正确  
	 * sPath 为路径字符串  
	 */
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
	
	private boolean flag;
	
	public ModenodelibServiceI getModenodelibService() {
		return modenodelibService;
	}
	
	@Autowired
	public void setModenodelibService(ModenodelibServiceI modenodelibService) {
		this.modenodelibService = modenodelibService;
	}
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	 public String upload(HttpServletRequest request, @RequestParam(value = "myFile", required = false) MultipartFile[] files) {
	          try {
	          for(int i=0;i<files.length;i++){
	          FileUploadUtils.upload(request, files[i]);
	          }
	 
		} catch (Exception e) {
			LOGGER.info("upload", e);
		}
	    return "success";
	  }
	/*@RequestMapping(value = "/fileUpload.do", method = RequestMethod.POST)
	public void fileUpload(String json, HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		  
		  //文件的上传部分
		  boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		  
		  if(isMultipart){
		   try {
		    FileItemFactory factory = new DiskFileItemFactory();
		    ServletFileUpload fileload = new ServletFileUpload(factory);
		    
		    //设置最大文件尺寸，这里是4MB    
		    fileload.setSizeMax(4194304);
		    List<FileItem> files = fileload.parseRequest(request);
		    Iterator<FileItem> iterator = files.iterator(); 
		    while(iterator.hasNext()){
		     FileItem item = iterator.next();
		     if(item.isFormField()){
		      String name = item.getFieldName();
		      String value = item.getString();
		      System.out.println("表单域名为: " + name + "值为: " + value);
		     }else{
		      //获得获得文件名，此文件名包括路径
		      String filename = item.getName();
		      if(filename != null){
		       File file = new File(filename);
		       //如果此文件存在
		       if(file.exists()){
		        File filetoserver = new File("d:\\upload\\",file.getName());
		        item.write(filetoserver);
		        System.out.println("文件 " + filetoserver.getName() + " 上传成功!!");
		       }
		      }
		     }
		    }
		   } catch (Exception e) {
		    System.out.println(e.getStackTrace());
		   }
		  }
	}*/
	/**
	 * 判断文件是否是允许上传的类型 @chengqin
	 * @param fileName
	 * @return
	 */
	public boolean isAllowTypes(String fileName){
		if (fileName!= null && !fileName.equals("")) {
			fileName = fileName.toLowerCase();
			for (String s : allowTypes) {
				if (fileName.endsWith(s)) {
					return true;
				}
			}
		} return false;
	}
	/**
	 * 将内容写入制定文件  @chengqin
	 * @param filePath
	 * @param desc
	 * @return
	 * @throws IOException
	 */
	private String writeFile(String filePath,String desc) throws IOException{
		StringBuilder result = new StringBuilder();
		File file = new File(filePath);
		if(!file.exists()){
			if(!file.createNewFile()){
				result.append("无法创建文件!" + filePath);
			}
		}
		FileOutputStream out = new FileOutputStream(file,true);
		OutputStreamWriter out2 = new OutputStreamWriter(out, Charset.forName("utf-8"));
		out2.write(desc);
		out2.close();
		out.close();
	
		return result.toString();
		
	}
	/**
	 * 将一段字符串追加到一个结果字符串中。如果结果字符串的初始内容不为空， 在追加当前这段字符串之前先最加一个逗号（,）。在组合sql语句的查询条件时，
	 * 经常要用到类似的方法，第一条件前没有"and"，而后面的条件前都需要用"and"
	 * 作连词，如果没有选择第一个条件，第二个条件就变成第一个，依此类推。@chengqin
	 * @param result
	 * @param fragment
	 */
	private void makeUpList(StringBuffer result, String fragment) {
		if (fragment != null) {
			if (result.length() != 0) {
				result.append(",");
			}
			result.append(fragment);
		}
	}
	
    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String sPath) {
        flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
	@RequestMapping("/add")
	public void add(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
//			logger.info("add json:"+json);
			
//			------------------------------------------------------------------------------------
			String spetor = File.separator;
			// 设置保存上传文件的目录
			String uploadDir = request.getSession().getServletContext().getRealPath(spetor + "model_editor/images/product");
			// F:\apache-tomcat-7.0.41\webapps\zigBeeDevice\model_editor\images\product
//			File directory = new File("images/product");//设定为当前文件夹  
//			String uploadDir = (ModenodelibController.class.getClassLoader().getResource("images/product")).getPath();
//			String uploadDir = directory.getAbsolutePath(); // 获取绝对路径
//			String usedir = System.getProperty("user.dir");
			/*if (uploadDir == null) {
				out.println("无法访问存储目录");
				return;
			}
			File fUploadDir = new File(uploadDir);
			if (!fUploadDir.exists()) {
				if (!fUploadDir.mkdir()) {
					out.println("无法创建存储目录" + uploadDir + "");
					return;
				}
			}*/
//			out.println("ServletFileUpload!!!!!!!!!!!!!!!");
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			/*if (!ServletFileUpload.isMultipartContent(request)) {
				out.println("只能处理multipart/form-data类型的数据");
				return;
			}*/
			if (isMultipart) {
				
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 超过1M的字段数据采用临时文件缓存
//			factory.setSizeThreshold(1024 * 1024);
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 最多上传30M数据
//			upload.setSizeMax(1024 * 1024 * 30);
			upload.setSizeMax(1024 * 24); // 最多24KB
			// 每个文件最大1M
//			upload.setFileSizeMax(1024 * 1024 * 1);
			upload.setFileSizeMax(1024 * 24); // 最多24KB
			// 采用默认的临时文件存储位置
			// diskFileItem.setRepositoryPath(...);
			// 设置上传的普通字段的名称和文件字段的文件名所采用的字符集编码
			upload.setHeaderEncoding("utf-8");
			//upload.setProgressListener(new UploadProgressListener(request));
			// 得到所有表单字段对象的集合
			List fileItems = null;
			try {
				fileItems = upload.parseRequest(request);
			}catch (FileSizeLimitExceededException e) {
//				out.print("单个照片的大小不能超出1M"); 最多24KB
				LOGGER.info("add", e);
				String resultJson = "{\"result\":-3}";//成功1 失败0 
//				String callback = request.getParameter("callback");
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
				return;
			}catch(SizeLimitExceededException e){
//				out.print("所有照片的总大小不能超出30M"); 最多24KB
				LOGGER.info("add", e);
				String resultJson = "{\"result\":-4}";//成功1 失败0 
//				String callback = request.getParameter("callback");
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
				return;
			}catch (FileUploadException e) {
//				out.print("解析数据时出现如下问题");
				LOGGER.info("add", e);
				String resultJson = "{\"result\":0}";//成功1 失败0
//				String callback = request.getParameter("callback");
				out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
				return;
			}
			// 处理每个表单字段
			Iterator i = fileItems.iterator();
			ArrayList<String> keys = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (fi.isFormField()){
					/*String content = fi.getString("utf-8");
					String fieldName = fi.getFieldName();
					if (content != null && !content.equals("")) {
						uploadDir = fUploadDir.getAbsolutePath() + spetor + content;
						File file = new File(uploadDir);
						if (!file.exists()) {
							if (!file.mkdir()){
								out.print("无法创建存储目录!" + uploadDir + "");
								return;
							}
						}
					}
					request.setAttribute(fieldName, content);
					keys.add(fieldName);
					values.add(content);*/
				} else {
					try {
						String pathSrc = fi.getName();
						//如果用户没有在FORM表单的文件字段中选择任何文件， 那么忽略对该字段项的处理
						if (pathSrc == null || pathSrc.trim().equals("")){
							continue;
						}
						int start = pathSrc.lastIndexOf('\\');
						String fileName = pathSrc.substring(start + 1);
						//处理文件名乱码的问题
						fileName = new String(fileName.getBytes(), "utf-8");
						if(!isAllowTypes(fileName)){ // 只允许上传jpg文件格式
//							out.print("" + fileName + "为不被允许的上传类型");
							String resultJson = "{\"result\":-2}";//成功1 失败0
//							String callback = request.getParameter("callback");
							out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
							return;
						}
						File pathDest = new File(uploadDir, fileName);
						//如果文件存在不做处理
						if(!pathDest.exists()){
							fi.write(pathDest);
						} else {
							LOGGER.info("文件已经存在" + pathDest);
							fi.write(pathDest); // 覆盖文件
						}
						/*String fieldName = fi.getFieldName();
						request.setAttribute(fieldName, fileName);
						keys.add(fieldName);
						values.add(pathSrc);*/
					} catch (Exception e) {
						//out.print("存储文件时出现如下问题：");
						LOGGER.info("add", e);
						String resultJson = "{\"result\":0}";//成功1 失败0
//						String callback = request.getParameter("callback");
						out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
						return;
					} finally{
						// 总是立即删除保存表单字段内容的临时文件
						fi.delete();
					}
				}
			}
			// 显示处理结果
			StringBuilder txtContent = new StringBuilder(); 
			Enumeration attributes = request.getAttributeNames();
			String attribute;
			txtContent.append("=====================" + new Date().toString() + "=================\r\n");
			/*for (int j=0; j < keys.size(); j++){
				attribute = keys.get(j);
				txtContent.append(attribute);
				txtContent.append(":");
				txtContent.append(values.get(j));
				txtContent.append("\r\n");
			}*/
//			writeFile(uploadDir + spetor +"log.txt", txtContent.toString());
			/*StringBuffer filelist = new StringBuffer();
			for(int k=1; k < 11; k++){
				String file1 = (String) request.getAttribute("file"+k);
				makeUpList(filelist, file1);
			}*/
//			out.print("成功上传文件："+ (filelist.length() == 0 ? "无" : filelist.toString()) + "");
//			out.flush();
			}
			Map<String, Object> params = JSON.parseObject(json,Map.class);
			String language= (String)params.get("language");
			Modenodelib modenodelib = JSON.parseObject(json,Modenodelib.class);
//			ModenodelibEn modenodeliben = JSON.parseObject(json,ModenodelibEn.class);
//			if(language.equals("1")){
//				ModenodelibEn te = modenodelibService.getModenodelibEn(modenodeliben);
//				if (te != null) { // 设备已有不能添加
//					String resultJson = "{\"result\":-1}";//成功1 失败0
////					String callback = request.getParameter("callback");
//					out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
//					return;
//				}else {
//					modenodelibService.save(modenodeliben);
//				}
//			}
//			else {
				Modenodelib t = modenodelibService.getModenodelib(modenodelib);
				if (t != null) { // 设备已有不能添加
					String resultJson = "{\"result\":-1}";//成功1 失败0
//					String callback = request.getParameter("callback");
					out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}"); 
					return;
				}else {
//					if(language.equals("1")){
//						if(modenodelib.getDeviceName()!=null){
//							modenodelib.setDeviceNameEn(modenodelib.getDeviceName());
//							modenodelib.setDeviceName(null);
//						}
//						if(modenodelib.getClusterName()!=null){
//							modenodelib.setClusterNameEn(modenodelib.getClusterName());
//							modenodelib.setClusterName(null);
//						}
//						if(modenodelib.getDescription()!=null){
//							modenodelib.setDescriptionEn(modenodelib.getDescription());
//							modenodelib.setDescription(null);
//						}
//						if(modenodelib.getPowerType()!=null){
//							modenodelib.setPowerTypeEn(modenodelib.getPowerType());
//							modenodelib.setPowerType(null);
//						}
//						if(modenodelib.getActivationMethod()!=null){
//							modenodelib.setActivationMethodEn(modenodelib.getActivationMethod());
//							modenodelib.setActivationMethod(null);
//						}
//						if(modenodelib.getResetDefault()!=null){
//							modenodelib.setResetDefaultEn(modenodelib.getResetDefault());
//							modenodelib.setResetDefault(null);
//						}
//						if(modenodelib.getRemark()!=null){
//							modenodelib.setRemarkEn(modenodelib.getRemark());
//							modenodelib.setRemark(null);
//						}
//					}
					modenodelibService.save(modenodelib);
				}
//			}
			
//			------------------------------------------------------------------------------------
			String resultJson = "{\"result\":1}";//成功1 失败0
//			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");  
		} catch (Exception e) {
//			String resultJson = "{\"result\":0,\"msg\":'" + e.getMessage() + "'}";//成功1 失败0
			LOGGER.info("upload", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
//			String callback = request.getParameter("callback");
			out.println("{\"request_id\": 1234, \"response_params\":" + resultJson + "}");
		}
	}
	@RequestMapping("/find")
	public void GetModeNodeLibDataByID(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		Map<String , Object> param = JSON.parseObject(json,Map.class);
//		String language= (String)param.get("language");
		try {
			Modenodelib modenodelib = JSON.parseObject(json,Modenodelib.class);
			LOGGER.info("find json:"+json);
			List<Map> t = modenodelibService.findList(modenodelib);
			String resultJson= JSON.toJSONStringWithDateFormat(t, "yyyy-MM-dd HH:mm:ss");
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("GetModeNodeLibDataByID", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
	@RequestMapping("/update")
	public void update(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> params = JSON.parseObject(json,Map.class);
//		String language= (String)params.get("language");
        try {
        	Modenodelib modenodelib = JSON.parseObject(json,Modenodelib.class); 
//        	ModenodelibEn modenodeliben = JSON.parseObject(json,ModenodelibEn.class); 
        	String resultJson = "{\"result\":0}";//成功1 失败0
//        	if(language.equals("1")){
//        		ModenodelibEn t = modenodelibService.geten(modenodeliben);
//        		if (t != null) {
//	        		//modenodelib.setId(t.getId());
//	        		//modenodelibService.update(t);
//	        		if (modenodeliben.getNodeType()!=null) {
//						t.setNodeType(modenodeliben.getNodeType());
//					}
//	        		if (modenodeliben.getModelId()!=null) {
//						t.setModelId(modenodeliben.getModelId());
//					}
//	        		if (modenodeliben.getDeviceName()!=null) {
//						t.setDeviceName(modenodeliben.getDeviceName());
//					}
//	        		if (modenodeliben.getClusterId()!=null) {
//						t.setClusterId(modenodeliben.getClusterId());
//					}
//	        		if (modenodeliben.getClusterName()!=null) {
//						t.setClusterName(modenodeliben.getClusterName());
//					}
//	        		if (modenodeliben.getDestType()!=null) {
//						t.setDestType(modenodeliben.getDestType());
//					}
//	        		if (modenodeliben.getDeviceType()!=null) {
//						t.setDeviceType(modenodeliben.getDeviceType());
//					}
//	        		if (modenodeliben.getPicName()!=null) {
//						t.setPicName(modenodeliben.getPicName());
//					}
//	        		if (modenodeliben.getDescription()!=null) {
//						t.setDescription(modenodeliben.getDescription());
//					}
//	        		if (modenodeliben.getPowerType()!=null) {
//						t.setPowerType(modenodeliben.getPowerType());
//					}
//	        		if (modenodeliben.getActivationMethod()!=null) {
//						t.setActivationMethod(modenodeliben.getActivationMethod());
//					}
//	        		if (modenodeliben.getResetDefault()!=null) {
//						t.setResetDefault(modenodeliben.getResetDefault());
//					}
//	        		if (modenodeliben.getRemark()!=null) {
//						t.setRemark(modenodeliben.getRemark());
//					}
//	        		modenodelibService.updaten(t);
//	            	resultJson = "{\"result\":1}";//成功1 失败0
//	        	}
////        	}
//        	else {
        		Modenodelib t = modenodelibService.get(modenodelib);
        		if (t != null) {
	        		//modenodelib.setId(t.getId());
	        		//modenodelibService.update(t);
//        			if(language.equals("1")){
        				if (modenodelib.getDeviceNameEn()!=null) {
    						t.setDeviceNameEn(modenodelib.getDeviceNameEn());
    					}
        				if (modenodelib.getClusterNameEn()!=null) {
    						t.setClusterNameEn(modenodelib.getClusterNameEn());
    					}
        				if (modenodelib.getDescriptionEn()!=null) {
    						t.setDescriptionEn(modenodelib.getDescriptionEn());
    					}
        				if (modenodelib.getPowerTypeEn()!=null) {
    						t.setPowerTypeEn(modenodelib.getPowerTypeEn());
    					}
        				if (modenodelib.getActivationMethodEn()!=null) {
    						t.setActivationMethodEn(modenodelib.getActivationMethodEn());
    					}
    	        		if (modenodelib.getResetDefaultEn()!=null) {
    						t.setResetDefaultEn(modenodelib.getResetDefaultEn());
    					}
    	        		if (modenodelib.getRemarkEn()!=null) {
    						t.setRemarkEn(modenodelib.getRemarkEn());
    					}
//        			}
//        			else{
        				if (modenodelib.getDeviceName()!=null) {
    						t.setDeviceName(modenodelib.getDeviceName());
    					}
        				if (modenodelib.getClusterName()!=null) {
    						t.setClusterName(modenodelib.getClusterName());
    					}
        				if (modenodelib.getDescription()!=null) {
    						t.setDescription(modenodelib.getDescription());
    					}
        				if (modenodelib.getPowerType()!=null) {
    						t.setPowerType(modenodelib.getPowerType());
    					}
        				if (modenodelib.getActivationMethod()!=null) {
    						t.setActivationMethod(modenodelib.getActivationMethod());
    					}
    	        		if (modenodelib.getResetDefault()!=null) {
    						t.setResetDefault(modenodelib.getResetDefault());
    					}
    	        		if (modenodelib.getRemark()!=null) {
    						t.setRemark(modenodelib.getRemark());
    					}
//        			}
	        		if (modenodelib.getNodeType()!=null) {
						t.setNodeType(modenodelib.getNodeType());
					}
	        		if (modenodelib.getModelId()!=null) {
						t.setModelId(modenodelib.getModelId());
					}
	        		
	        		if (modenodelib.getClusterId()!=null) {
						t.setClusterId(modenodelib.getClusterId());
					}
	        		
	        		if (modenodelib.getDestType()!=null) {
						t.setDestType(modenodelib.getDestType());
					}
	        		if (modenodelib.getDeviceType()!=null) {
						t.setDeviceType(modenodelib.getDeviceType());
					}
	        		if (modenodelib.getPicName()!=null) {
						t.setPicName(modenodelib.getPicName());
					}
	        		modenodelibService.update(t);
	            	resultJson = "{\"result\":1}";//成功1 失败0
	        	}
//			}
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})"); 
		} catch (Exception e) {
			LOGGER.info("update", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");   
		}        
	}
	@RequestMapping("/delete")
	public void delete(String json, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			Map<String, Object> params = JSON.parseObject(json,Map.class);
			Modenodelib modenodelib = JSON.parseObject(json,Modenodelib.class);
			LOGGER.info("delete json:"+json);
//			if(language.equals("1")){
//				ModenodelibEn modenodeliben = JSON.parseObject(json,ModenodelibEn.class);
//				modenodelibService.deleten(modenodeliben);
//			}else {
				modenodelibService.delete(modenodelib);
//			}
			
			String resultJson = "{\"result\":1}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");  
		} catch (Exception e) {
			LOGGER.info("delete", e);
			String resultJson = "{\"result\":0}";//成功1 失败0
			String callback = request.getParameter("callback");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
	}
}
