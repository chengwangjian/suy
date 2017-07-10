package org.smarthome.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.smarthome.domain.BfBugAction;
import org.smarthome.domain.BfBugInfo;
import org.smarthome.domain.BfTestFile;
import org.smarthome.domain.FileAppinfo;
import org.smarthome.model.DataGrid;
import org.smarthome.model.FileMeta;
import org.smarthome.services.BfBugActionServiceI;
import org.smarthome.services.BfBugInfoServiceI;
import org.smarthome.services.BfTestFileServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sy.model.QueryParameter;
import sy.util.PropertiesUtil;
import zbHouse.pageModel.Json;
import zbHouse.util.TestLog4j;

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping("/controller")
public class FileController extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(FileController.class);
	@Autowired
	@Qualifier("BfBugInfo")
	private BfBugInfoServiceI bfBugInfo;
	@Autowired
	@Qualifier("BfBugAction")
	private BfBugActionServiceI bfBugAction;
	@Autowired
	@Qualifier("BfTestFile")
	private BfTestFileServiceI bfTestFile;
	FileMeta fileMeta = new FileMeta();
	HashMap<String, Object> bugfree=new HashMap<String, Object>(); 
	List fileupdate =new ArrayList();
	//	@Autowired
	//	@Qualifier("fileAppinfo")
	//	private FileAppinfo fileAppinfo;
	/***************************************************
	 * URL: /rest/controller/upload  
	 * upload(): receives files
	 * @param request : MultipartHttpServletRequest auto passed
	 * @param response : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 ****************************************************/
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public @ResponseBody void upload(String json, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String serverHost = request.getServerName();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		//        HashMap<String, String> request = new HashMap<String, String>(); 
		//		{"title":"aaa","infomation":"user:ssss\nmobile:ss\n","description":"cccc"}
		Json j = new Json();
		DataGrid dg = new DataGrid();
		if (isMultipart) {
			LOGGER.info("是文件流");
			DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024 * 20, null);  
			ServletFileUpload upload = new ServletFileUpload(factory);  
			upload.setHeaderEncoding("UTF-8");  
			try{
				List<FileItem> fileItems = upload.parseRequest(request);  
				Iterator<FileItem> iter = fileItems.iterator(); 
				bugfree.clear();	//清空map
				fileupdate.clear();	//清空list
				while (iter.hasNext()) {  
					FileItem item = iter.next();  
					if (item.isFormField()) { 
						if(item.getFieldName().equals("json")) {
							String value = item.getString("UTF-8");                   
							FileMeta formField=JSON.parseObject(value,FileMeta.class);                   
							preupload(formField,true);  
						}
					} else {  
						if (StringUtils.isBlank(item.getName())) {  
							continue;  
						}
						fileMeta.setFileName(item.getName());  			 
						fileMeta.setFileSize(item.getSize()/1024+" Kb");
						fileMeta.setFileType(item.getContentType().substring(item.getContentType().length()-3));
						fileMeta.setDownloadInfo("image/"+fileMeta.getFileName());
						//判断当前的操作系统 
						if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
							String imagePath = PropertiesUtil.getProperty("bugfree.imagePathWindows");
							File file = new File(imagePath);
							//判断文件夹是否存在,如果不存在则创建文件夹
							if (!file.exists()) {
								file.mkdirs();//可以在不存在的目录中创建文件夹
							}
							SaveFileFromInputStream1(item.getInputStream(), imagePath+fileMeta.getFileName(), serverHost);
						}else {
							String imagePath = PropertiesUtil.getProperty("bugfree.imagePathLinux");
							File file = new File(imagePath);
							//判断文件夹是否存在,如果不存在则创建文件夹
							if (!file.exists()) {
								file.mkdirs();//可以在不存在的目录中创建文件夹
							}
							SaveFileFromInputStream1(item.getInputStream(), imagePath+fileMeta.getFileName(), serverHost);
						}
						preupload(fileMeta,false);  
					}  
				}      
//				屏蔽掉必须附件的判断
//				if(bugfree.size()<3){
//					dg.setStatus(0);
//					dg.setStatus_msg("fail");	
//				}
//				else{
					//动态切换到bugfree数据源
					BfBugInfo info=(BfBugInfo)bugfree.get("Info");
					bfBugInfo.saveOrUpdate(info);	
					BfBugAction action=(BfBugAction)bugfree.get("Action");
					action.setBugInfoId(info.getId());
					bfBugAction.saveOrUpdate(action);
					List<BfTestFile> updates=(List<BfTestFile>)bugfree.get("File");
					if(updates != null) {
						for(BfTestFile update : updates){
							update.setTargetId(info.getId());
							update.setAddActionId(action.getId());
							bfTestFile.saveOrUpdate(update);
						}
					}
//				}
				j.setResponse_params(dg);
			}catch (Exception e) {
				LOGGER.info("upload", e);
				dg.setStatus(0);
				dg.setStatus_msg("fail");
				j.setResponse_params(dg);	
			}
		}
		else {
			LOGGER.info("不是文件流");
			try {
				json=URLDecoder.decode(json, "gbk");
				BfBugInfo hard=JSON.parseObject(json,BfBugInfo.class);
				j.setResponse_params(bfBugInfo.saveOrUpdate(hard));
			} catch (Exception e) {
				LOGGER.info("upload", e);
				dg.setStatus(0);
				dg.setStatus_msg("fail");
				j.setResponse_params(dg);			
			}
		}
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType("text/html;charset=utf-8");        
		String jstr=JSON.toJSONString(j);
		response.getWriter().write(jstr);
		response.getWriter().flush();
		response.getWriter().close();
	}

	public static String postUrlWithFile2(String uploadUrl,File file){
		LOGGER.info("uploadUrl:"+uploadUrl);
		HttpPost post = new HttpPost(uploadUrl);
		MultipartEntity entity = new MultipartEntity();
		HttpClient httpClient = new DefaultHttpClient();
		try {
			if (file != null && file.exists()) { 
				entity.addPart("file", new FileBody(file)); 
			}
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post); 
			int stateCode = response.getStatusLine().getStatusCode();
			StringBuffer sb = new StringBuffer(); 
			if (stateCode == HttpStatus.SC_OK) {
				LOGGER.info("upload success");
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
			LOGGER.info("postUrlWithFile2", e);
		} finally {
			//释放连接
			post.releaseConnection();
		}
		return uploadUrl;
	}

	public void SaveFileFromInputStream1(InputStream stream,String filename,String serverHost) throws IOException{      
		try{    
			FileOutputStream fs=new FileOutputStream(filename);
			byte[] buffer =new byte[1024*1024];
			int bytesum = 0;
			int byteread = 0; 
			while ((byteread=stream.read(buffer))!=-1)
			{
				bytesum+=byteread;
				fs.write(buffer,0,byteread);
				fs.flush();
			} 
			fs.close();
			stream.close();  
			String bugfreeIp =  PropertiesUtil.getProperty("bugfree.ip");
			if(!serverHost.equals(bugfreeIp)){
				File file = new File(filename);
				String bugfreePort =  PropertiesUtil.getProperty("bugfree.port");
				String uploadUrl = "http://"+bugfreeIp+":"+bugfreePort+"/zigBeeDevice/controller/upload.do";
				postUrlWithFile2(uploadUrl,file);
				file.delete();//删除不是保存在bugfree服务器的文件
			}
		}catch(IOException e){
			LOGGER.info("SaveFileFromInputStream", e);
		}
	}    


	public void SaveFileFromInputStream(InputStream stream,String filename) throws IOException{      
		try{    
			FileOutputStream fs=new FileOutputStream(filename);
			byte[] buffer =new byte[1024*1024];
			int bytesum = 0;
			int byteread = 0; 
			while ((byteread=stream.read(buffer))!=-1)
			{
				bytesum+=byteread;
				fs.write(buffer,0,byteread);
				fs.flush();
			} 
			fs.close();
			stream.close();    
		}catch(IOException e){
			LOGGER.info("SaveFileFromInputStream", e);
		}
	}    

	@RequestMapping(value = "/preupload", method = RequestMethod.GET)
	public void preupload(FileMeta fileMeta, boolean value) {
		if(value){
			BfBugInfo info=new BfBugInfo();	
			//				info.setId(823);
			info.setTitle(fileMeta.getTitle());
			info.setRepeatStep(fileMeta.getInfomation()+fileMeta.getDescription());
			bugfree.put("Info", info);
			BfBugAction action=new BfBugAction();
			//				action.setId(2990);
			bugfree.put("Action", action);
		}else{
			BfTestFile update=new BfTestFile();
			//				update.setId(122);
			update.setFileTitle(fileMeta.getFileName());
			update.setFileLocation(fileMeta.getDownloadInfo());
			update.setFileType(fileMeta.getFileType());
			update.setFileSize(fileMeta.getFileSize());
			fileupdate.add(update);
			bugfree.put("File", fileupdate);
		}

		//	      TestLog4j.testInfo((children != null)+JSON.toJSONString(children)+children.length);
		//	      response.setHeader("Access-Control-Allow-Origin", "*");
		//	      response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
		//    	  response.setContentType("text/html;charset=utf-8");
		//    	  String jstr=JSON.toJSONString(dg);
		//    	  response.getWriter().write(jstr);
		//    	  response.getWriter().flush();
		//    	  response.getWriter().close();
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
		FileMeta getFile =fileMeta;
		try {		
			response.setContentType(getFile.getFileType());
			response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
			FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//&callback=android&sign=AAA&encodemethod=NONE&houseIeeeSecret=00137A0000007785"
	@RequestMapping(value="/add_debug_log", method = RequestMethod.POST)
	public  void add_debug_log(String json,String callback,String houseIeeeSecret, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
		DataGrid dg = new DataGrid();
		FileAppinfo fileAppinfo=new FileAppinfo();
		String namefielString="";
		String ffString="";
		String odernameString="";
		String kksString="";
		//Map<String,Object> mappramMap=new HashMap<String,Object>();
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024 * 20, null);  
			ServletFileUpload upload = new ServletFileUpload(factory);  
			upload.setHeaderEncoding("UTF-8");  
			try{
				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> iter = fileItems.iterator(); 
				Map<String,Object> map = new HashMap<String,Object>();
				TestLog4j.testInfo("bugfree----"+bugfree.size());   
				int j=0;
				while (iter.hasNext()) {
					FileItem item = iter.next();  
					j++;
					if (item.isFormField()) { 
						if("json".equals(item.getFieldName())){
							String name = item.getFieldName();  
							String value = item.getString("UTF-8");
							value = value.replaceAll("device_type", "deviceType");
							TestLog4j.testInfo("name----"+name+"value----"+value);
							StringBuilder strBuider = new StringBuilder("{");
							map=JSON.parseObject(value,Map.class);
							for(String key:map.keySet()){
								if(!"description".equalsIgnoreCase(key)){
									strBuider.append("\""+key+"\":\""+map.get(key)+"\",");
								}else{
									//获取description中的各属性字段与值，并讲个属性存入数组
									String[] strArray = ((String)map.get("description")).split("<BR/>");
									for(String s:strArray){
										//获取个属性的属性名与值
										String keys = s.substring(0, s.indexOf(":"));
										String values = s.substring(s.indexOf(":")+1, s.length());
										if(!"Protocol".equals(keys)&&!"HouseIEEE".equalsIgnoreCase(keys)&&!"Loginuser".equalsIgnoreCase(keys)){
											if("package".equals(keys)){
												strBuider.append("\"packages\":\""+values+"\",");
												continue;
											}
											if("VERSION.SDK".equals(keys)){
												strBuider.append("\"versionSdk\":\""+values+"\",");
												continue;
											}
											if("VERSION.RELEASE".equals(keys)){
												strBuider.append("\"verRelease\":\""+values+"\",");
												continue;
											}
											if("Channel,Ip Address".equalsIgnoreCase(keys)){
												strBuider.append("\"serIp\":\""+values+"\",");
												continue;
											}
											strBuider.append("\""+keys+"\":\""+values+"\",");
										}
									}
								}
							}
							strBuider.append("}");
							fileAppinfo = JSON.parseObject(strBuider.toString(), FileAppinfo.class);
						}
						if("callback".equals(item.getFieldName())){
							callback=item.getString("UTF-8");  
							System.out.println("callback1:"+item.getFieldName()+":"+callback);
						}
						if("houseIeeeSecret".equals(item.getFieldName())){
							houseIeeeSecret=item.getString("UTF-8");  
							System.out.println("houseIeeeSecret2:"+item.getFieldName()+":"+houseIeeeSecret);
						}
					}else {
						if (StringUtils.isBlank(item.getName())) {  
							continue;  
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");  
						ffString=sdf.format(new Date());
						fileMeta.setFileName(item.getName());
						fileMeta.setFileSize(item.getSize()/1024+" Kb");
						fileMeta.setFileType(item.getContentType().substring(item.getContentType().length()-3));
						fileMeta.setDownloadInfo("image/"+fileMeta.getFileName());
						// namefielString=PropertiesUtil.getProperty("appurlerr.url");//"D:/apache-ftpserver-1.0.6/res/home/appfile/";
						namefielString = FileController.class.getResource("/").getPath();//WEB-INF/classes/appbugfile/
						namefielString=namefielString.substring(0, namefielString.indexOf("zigBeeDevice") + 13) + "cloudManageWeb/appbugfile/";
						File files=new File(namefielString);
						if(!files.exists())
						{
							files.mkdirs();//文件夹
						}
						kksString=item.getName();
						int kk=kksString.indexOf(".");
						odernameString=kksString.substring(0,kk);
						kksString=kksString.substring(kk,kksString.length());
						if (kksString!=".exe") {
							SaveFileFromInputStream(item.getInputStream(),
								namefielString+fileMeta.getFileName());	
						}
					}
				}
				File oldfile=new File(namefielString+fileMeta.getFileName());
				ffString=ffString.replace(" ", "");
				ffString=ffString.replace(":", "");
				File newfile=new File(namefielString+ffString+kksString); 
				oldfile.renameTo(newfile); 
				fileAppinfo.setFileName(ffString+kksString);
				fileAppinfo.setFileSize(fileMeta.getFileSize());
				fileAppinfo.setOderfilename(odernameString+kksString);//odernameString+kksString
				bfTestFile.savefile(fileAppinfo);
			}catch (Exception e) {
				dg.setStatus(0);
				dg.setStatus_msg("error");
				LOGGER.info("add_debug_log", e);
			}
		}
		else {
			dg.setStatus(0);
			dg.setStatus_msg("error");
		}
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		String resultJson=JSON.toJSONString(dg);
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(callback+"({\"request_id\": 1234, \"response_params\":"
				+ resultJson + "})");
		response.getWriter().flush();
		response.getWriter().close();
	}
	//appbug文件信息
	@RequestMapping("getappdbuginfo")
	public void getappdbuginfo(String json,Integer startRow,Integer pageSize,String callback,HttpServletRequest request, HttpServletResponse response)
	{
		PrintWriter out=null;
		try {
			response.setHeader("Pragma","No-cache");
			response.setHeader("Cache-Control","no-cache");
			response.setContentType("application/json");
			out=response.getWriter();
			//String sql=request.getParameter("sql");
			Map<String, Object> conditionMap=json==null?null:JSON.parseObject(json,Map.class);
			List<Map> lisfielAppinfos=bfTestFile.getappdbugfileinfo(conditionMap, startRow==null?0:startRow, pageSize==null?10:pageSize,"1");
			String jsonString=JSON.toJSONStringWithDateFormat(lisfielAppinfos, "yyyy-MM-dd HH:mm:ss");
			jsonString="{\"count\":\""+lisfielAppinfos.size()+"\",\"appbuglist\":"+jsonString+"}";
			out.write(jsonString);
		}catch (Exception e){
			// TODO: handle exception
			LOGGER.info("getappdbuginfo", e);
			out.write("{\"status\":0}");//status 加载列表失败
		}
	}
	//appdug总数量
	@RequestMapping("getappcount")
	public void getappcount(String json,HttpServletRequest request, HttpServletResponse response,String callback)
	{
		PrintWriter out=null;
		try {
			response.setHeader("Pragma","No-cache");
			response.setHeader("Cache-Control","no-cache");
			response.setContentType("text/html;charset=utf-8");
			out=response.getWriter();
			Map<String, Object> conditionMap=json==null?null:JSON.parseObject(json,Map.class);
			int fileCount =bfTestFile.getAppFileCount(conditionMap);
			String jsonString=callback+"({\"count\":\""+fileCount+"\"})";
			out.write(jsonString);
		} catch (Exception e) {
			// TODO: handle exception
			//jsonString="{\"count\":\""+lisfielAppinfos.size()+"\"}";
			LOGGER.info("getappcount", e);
			out.write(callback+"({\"count\":\"0\"})");//status 加载列表失败
		}
	}
	//appdugfile下载
	@RequestMapping("updown")
	public void updown(String filename,HttpServletRequest request, HttpServletResponse response)
	{
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		response.setContentType("application/x-msdownload;charset=utf-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String currPath = FileController.class.getResource("/").getPath();
			String appBugPath = currPath.substring(0, currPath.indexOf("zigBeeDevice") + 13) + "cloudManageWeb/appbugfile/" + filename;
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
			LOGGER.info("updown", e);
		}
		finally {
			try {
				if(bis != null)
					bis.close();
				if(bos != null)
					bos.close();
			} catch(Exception e) {
				LOGGER.info("updown close stream", e);
			}
		}
	}
	
	@RequestMapping("exportAppDbugLogExcel")
	public void exportAppDbugLogExcel(String json,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> conditionMap=json==null?null:JSON.parseObject(json,Map.class);
		try{
			List<Map> appFileInfoList=bfTestFile.getappdbugfileinfo(conditionMap, 0, 0, "2");
			if(appFileInfoList!=null&&!appFileInfoList.isEmpty()){
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				
				workbook.setSheetName(0, "APP闪退日志");
				HSSFRow row = sheet.createRow(0);
				HSSFCellStyle titleStyle = workbook.createCellStyle();
				HSSFFont titleFont = workbook.createFont();
				titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				titleStyle.setFont(titleFont);
				HSSFCell cell = row.createCell(0,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(0, 1000);
				cell.setCellValue("序号");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(1,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(1, 3500);
				cell.setCellValue("代理服务器IP");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(2,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(2, 3500);
				cell.setCellValue("服务器IP");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(3,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(3, 5000);
				cell.setCellValue("HouseIEEE");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(4,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(4, 3000);
				cell.setCellValue("用户名");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(5,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(5, 3500);
				cell.setCellValue("包");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(6,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(6, 2000);
				cell.setCellValue("设备类型");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(7,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(7, 2000);
				cell.setCellValue("品牌");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(8,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(8, 3000);
				cell.setCellValue("型号");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(9,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(9, 3000);
				cell.setCellValue("分辨率");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(10,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(10, 2000);
				cell.setCellValue("SDK版本");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(11,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(11, 2000);
				cell.setCellValue("系统版本");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(12,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(12, 2000);
				cell.setCellValue("cpu");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(13,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(13, 3000);
				cell.setCellValue("内存");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(14,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(14, 2000);
				cell.setCellValue("APP版本");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(15,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(15, 3000);
				cell.setCellValue("上传时间");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(16,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(16, 7000);
				cell.setCellValue("旧文件名");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(17,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(17, 7000);
				cell.setCellValue("新文件名");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(18,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(18, 2000);
				cell.setCellValue("文件大小");
				cell.setCellStyle(titleStyle);
				cell = row.createCell(19,Cell.CELL_TYPE_STRING);
				sheet.setColumnWidth(19, 2500);
				cell.setCellValue("错误版本");
				cell.setCellStyle(titleStyle);
				
				for(int i = 0; i < appFileInfoList.size(); i++){
					row = sheet.createRow(i+1);
					cell = row.createCell(0,Cell.CELL_TYPE_STRING);
					cell.setCellValue(i+1);
					cell = row.createCell(1,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("proxy_server"));
					cell = row.createCell(2,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("server_ip"));
					cell = row.createCell(3,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("houseIEEE"));
					cell = row.createCell(4,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("user_name"));
					cell = row.createCell(5,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("package"));
					cell = row.createCell(6,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("device_type"));
					cell = row.createCell(7,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("brand"));
					cell = row.createCell(8,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("model"));
					cell = row.createCell(9,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("resolution"));
					cell = row.createCell(10,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("version_sdk"));
					cell = row.createCell(11,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("sys_ver"));
					cell = row.createCell(12,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("cpu"));
					cell = row.createCell(13,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("memory"));
					cell = row.createCell(14,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("app_version"));
					cell = row.createCell(15,Cell.CELL_TYPE_STRING);
					cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(appFileInfoList.get(i).get("opdatetime")));
					cell = row.createCell(16,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("oderfilename"));
					cell = row.createCell(17,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("file_name"));
					cell = row.createCell(18,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("file_size"));
					cell = row.createCell(19,Cell.CELL_TYPE_STRING);
					cell.setCellValue((String)appFileInfoList.get(i).get("error_no"));
					
				}
				String exportDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String fName = "APP闪退日志-"+exportDate+".xls";
				String fileName = new String(fName.getBytes("gb2312"),"iso8859-1");
				response.addHeader("Content-Disposition", "attachment;filename="+fileName);
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				BufferedOutputStream os = new BufferedOutputStream(response.getOutputStream());
				workbook.write(os);
				os.flush();
				os.close();
			}
		}catch(Exception e){
			LOGGER.error("exportExcelException", e);
//			e.printStackTrace();
		}
	}
   
   
   @RequestMapping("/addCondition")
   public void addCondition(String json,HttpServletRequest request, HttpServletResponse response,String callback)
   {
	   String resultJson="";
	   PrintWriter out=null;
	  try {
		  String userid=request.getParameter("userid");
		  out = response.getWriter();
		  QueryParameter parameter= JSON.parseObject(json,QueryParameter.class);
		    this.bfBugInfo.addCondition(parameter,userid);
		    resultJson = "{\"result\":1}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getappcount", e);
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
   }
   
   public void writeJson(Object object,String callback,HttpServletResponse response) {
		try {
			String jstr=callback+"("+JSON.toJSONString(object)+")";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jstr);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.info("writeJson", e);
		}
	}
   
   @RequestMapping("/findCondition")
   public void findCondition(String json,HttpServletRequest request, HttpServletResponse response,String callback)
   {
	   //PrintWriter out=null;
	  try { int userid=Integer.parseInt(request.getParameter("userid"));
		    Json j = new Json();
		    Map map= JSON.parseObject(json,Map.class);
		   List<Map> list= this.bfBugInfo.findCondition(userid);
		   j.setResponse_params(list);
		   writeJson(j,callback,response);
		} catch (Exception e) {
			LOGGER.info("getappcount", e);
			//out.write(callback+"({\"count\":\"0\"})");//status 加载列表失败
		}
   }
   
   @RequestMapping("/findConditionSub")
   public void findConditionSub(String json,HttpServletRequest request, HttpServletResponse response,String callback)
   {
	   //PrintWriter out=null;
	  try {  Json j = new Json();
		    Map map= JSON.parseObject(json,Map.class);
		   List<Map> list= this.bfBugInfo.findConditionSub(map);
		   j.setResponse_params(list);
		   writeJson(j,callback,response);
		} catch (Exception e) {
			LOGGER.info("getappcount", e);
			//out.write(callback+"({\"count\":\"0\"})");//status 加载列表失败
		}
   }
   
   
   @RequestMapping("/updateConditionmain")
   public void updateConditionmain(String json,HttpServletRequest request, HttpServletResponse response,String callback)
   {
	   String resultJson="";
	   PrintWriter out=null;
	  try { 
	       out = response.getWriter();
		   Json j = new Json();
		    Map map= JSON.parseObject(json,Map.class);
		    this.bfBugInfo.updateConditionmain(map);
		    resultJson = "{\"result\":1}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getappcount", e);
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
   }
   
   @RequestMapping("/deleteCondition")
   public void deleteCondition(String json,HttpServletRequest request, HttpServletResponse response,String callback)
   {
	   String resultJson="";
	   PrintWriter out=null;
	  try { 
	       out = response.getWriter();
		   Json j = new Json();
		    Map map= JSON.parseObject(json,Map.class);
		    this.bfBugInfo.deleteCondition(map);
		    resultJson = "{\"result\":1}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("getappcount", e);
			resultJson = "{\"result\":0}";
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		}
   }
   @RequestMapping("/findappinfo")
   public void findappinfo(String json,HttpServletRequest request, HttpServletResponse response,String callback)
   {     
	        String resultJson="";
	        PrintWriter out=null;
	  try {   
		    out = response.getWriter();
		    Map map= JSON.parseObject(json,Map.class);
		    List<Map> list= this.bfBugInfo.findappinfo(map);
		    resultJson=JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			out.println(callback + "({\"request_id\": 1234, \"response_params\":" + resultJson + "})");
		} catch (Exception e) {
			LOGGER.info("findappinfo", e);
			//out.write(callback+"({\"count\":\"0\"})");//status 加载列表失败
		}
   }
   
}
