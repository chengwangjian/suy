package sy.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author: zhuangxd 时间：2014-2-26 下午1:26:42
 */
public class TheFreemarker {
	private static final Logger log = Logger.getLogger(TheFreemarker.class);
	private Configuration configuration = null;

	public TheFreemarker() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	public void createDoc() {
		Map dateMap = new HashMap();
		getData(dateMap);

		// 模板放在com.canyou.template包下面，通过classpath装载
		configuration.setClassForTemplateLoading(this.getClass(),
				"/sy/util/ftl");

		Template t = null;
		try {
			t = configuration.getTemplate("exportModelHelpWord.ftl");// 设置要装载的模板
		} catch (IOException e) {
//			e.printStackTrace();
			log.info("createDoc", e);
		}
//		File outFile = new File("D:/modexml/exportModelHelpWord"+"-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date())+".doc");
		File outFile = new File(PropertiesUtil.getProperty("modexmlPath")+"exportModelHelpWord"+"-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date())+".doc");
//		File outFile = new File("D:/modexml/exportModelHelpWord.doc");

		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "utf-8"));
		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
			log.info("createDoc", e);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			log.info("createDoc", e);
		}

		try {
			t.process(dateMap, out);
			out.close();
		} catch (TemplateException e) {
//			e.printStackTrace();
			log.info("createDoc", e);
		} catch (IOException e) {
//			e.printStackTrace();
			log.info("createDoc", e);
		}

	}

	private void getData(Map dataMap) {
		dataMap.put("name", "经理");
		// dataMap.put("Tdate", "2013-05-15");
		List list = new ArrayList();
		for (int i = 0; i < 5; i++) {
			/*Table2 tt = new Table2();
			tt.setCode("1");
			tt.setUsername("小明");
			tt.setDate("2013-15-13");
			tt.setQujian("aaa");
			list.add(tt);*/
		}
		dataMap.put("table2", list);
	}
	
//	String filename = "d:\\modexml\\mode-userid为" + modehouse.getUserId() + "-orderid为" + userordermain.getId() + "-" + modehouse.getHouseName() + "-" +(new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());

	
/*	public class DownloadAction extends ActionSupport {  
	    private static final long serialVersionUID = 8448176878783491691L;  
	    @Override  
	    public String execute() throws Exception {  
	        HttpServletResponse resp =  ServletActionContext.getResponse();  
	        String path = ServletActionContext.getServletContext().getRealPath("/");      
	          String filename =  "数据报告.mht.doc"; //生成文件的文件名称 这个需要动态的获取   
	          OutputStream out;//输出响应正文的输出流   
	          InputStream in;//读取本地文件的输入流   
	          //获得本地输入流   
	          File file = new File(path + filename);  
	          in = new FileInputStream(file);  
	          //设置响应正文的MIME类型   
	          resp.setContentType("Content-Disposition;charset=GB2312");  
	          resp.setHeader("Content-Disposition", "attachment;"   + " filename="+ new String(filename.getBytes(), "ISO-8859-1"));  
	          //把本地文件发送给客户端   
	          out = resp.getOutputStream();  
	          int byteRead = 0;  
	          byte[] buffer = new byte[512];  
	          while((byteRead = in.read(buffer)) != -1) {  
	           out.write(buffer, 0, byteRead);  
	          }  
	          in.close();  
	          out.close();  
	        return null;  
	    }  
	}  */
	
/*	*//**
	  * @Description: 导出出票通知word文档
	  * @param @param id
	  * @param @param response 
	  * @return void
	  *//*
	 @RequestMapping(value = "/chupiaotongzhi/{id}")
	  public void exportWord(@PathVariable("id") Integer id,HttpServletResponse response){
	    byte b[] = new byte[1024];       // the file to download.      
	  User user = (User) getSession().getAttribute("currentUser");
	  ExportWord word = new ExportWord(); 
	  File outFile = word.createDoc(id,user,GlobalStatic.uploadFilePath3+"chupiaotongzhi.doc");
	  FileInputStream in = null;
	  OutputStream o = null;
	  try {
	  in = new FileInputStream(outFile);  
	  o = response.getOutputStream();  
	  response.setContentType("application/x-tar"); 
	  response.setHeader("Content-disposition", "attachment; filename="
	     + URLEncoder.encode("出票通知书0"+id+".doc", "UTF-8"));// 指定下载的文件名 
	  response.setHeader("Content_Length",String.valueOf( outFile.length()));       // download the file.
	        int n = 0;       
	        while ((n = in.read (b))!= -1)
	        {        
	         o.write(b, 0, n);   
	        } 
	  } catch (Exception e) {
	   e.printStackTrace();
	  }finally{
	    try {
	     in.close();
	     o.flush();
	     o.close();
	   } catch (IOException e) {
	    e.printStackTrace();
	   }
	   
	  }*/

	public static void main(String[] args) {
		TheFreemarker th = new TheFreemarker();
		th.createDoc();
	}
}
