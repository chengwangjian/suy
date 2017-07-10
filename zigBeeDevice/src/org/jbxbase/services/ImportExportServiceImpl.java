package org.jbxbase.services;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.jbx.util.CustomerContextHolder;
import org.jbx.util.DataSoureProvider;
import org.jbxbase.dao.UserMapper;
import org.springframework.stereotype.Service;


@Service("ImportExport")
public class ImportExportServiceImpl implements ImportExportServiceI {

	private UserMapper dao;

//	public UserMapper getDao() {
//		return dao;
//	}
//	@Autowired
//	public void setDao(UserMapper dao) {
//		this.dao = dao;
//	}
	
	private SqlSession session;
	
//	public SqlSession getSession() {
//		return session;
//	}
//	@Autowired
//	public void setSession(SqlSession session) {
//		this.session = session;
//	}
	
	@Override
	public String Export(Map data) throws Exception{

		//导出ieee
			DataSoureProvider.setDataSource1(data);
			DataSoureProvider.setDataSource2(data);
	        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_1);
	        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_2);
			Connection conn = session.getConnection();  
	        ScriptRunner runner = new ScriptRunner(conn);  
	        StringWriter stringWriter= new StringWriter();
	        PrintWriter writer= new PrintWriter(stringWriter);
	        runner.setLogWriter(writer);  

			Reader reader = Resources.getResourceAsReader("export.sql");
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			StringBuffer sb=new StringBuffer();
			while ((line = br.readLine()) != null)
				sb.append(line);
	        //执行第一次sql
			String rep=StringUtils.replace(sb.toString(), "00137a000001013b", data.get("ieee").toString());
			rep=StringUtils.replace(rep, ";", ";\n\r");	
	        String sqlA=StringUtils.substringBefore(rep,"#");

			StringReader sreader=new StringReader(sqlA);
	        runner.runScript(sreader);	  
//	        System.out.println("----\n"+stringWriter.getBuffer().toString());
	        //执行第二次sql
	        String sqlB=StringUtils.substringBetween(rep,"#");

	        sreader=new StringReader(sqlB); 
	        runner.runScript(sreader);	  
//	        System.out.println("----\n"+stringWriter.getBuffer().toString());
	        sreader.close();
	        reader.close();  
	        
		return "success";
		// TODO Auto-generated method stub		
	}
	
	@Override
	public String Import(Map data) throws Exception {
   
		//导入ieee 
			DataSoureProvider.setDataSource3(data);
			DataSoureProvider.setDataSource4(data);	
	        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_3);
	        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_4);	        
			Connection conn = session.getConnection();  
	        ScriptRunner runner = new ScriptRunner(conn);  
	        StringWriter stringWriter= new StringWriter();
	        PrintWriter writer= new PrintWriter(stringWriter);
	        runner.setLogWriter(writer);  
    
			Reader reader = Resources.getResourceAsReader("import.sql");
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			StringBuffer sb=new StringBuffer();
			while ((line = br.readLine()) != null)
				sb.append(line);
	        //执行第一次sql
			String rep=StringUtils.replace(sb.toString(), "00137a000001013b", data.get("ieee").toString());
			rep=StringUtils.replace(rep, ";", ";\n\r");
			String sqlA=StringUtils.substringBefore(rep,"#");

			StringReader sreader=new StringReader(sqlA);
	        runner.runScript(sreader);	  
//	        System.out.println("----\n"+stringWriter.getBuffer().toString());
	        //执行第二次sql	        
	        String sqlB=StringUtils.substringBetween(rep,"#");	

	        sreader=new StringReader(sqlB); 
	        runner.runScript(sreader);	  
//	        System.out.println("----\n"+stringWriter.getBuffer().toString());	        	        
	        sreader.close();
	        reader.close();  
		
		return "success";
		// TODO Auto-generated method stub	
	}
	
	@Override
	public String findList(Map<String, Object> hard) throws Exception {		
        // create a SqlSessionFactory  
//        Reader reader = Resources.getResourceAsReader("config/mybatis/files/mybatis-config.xml");  
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
//        reader.close();  
        // populate in-memory database  
//        SqlSession session = sqlSessionFactory.openSession();  
		DataSoureProvider.setDataSource1(null);
		DataSoureProvider.setDataSource2(null);
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_2);	
		
		Connection conn = session.getConnection();    
        ScriptRunner runner = new ScriptRunner(conn);  
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        runner.setLogWriter(writer); 
		
			//这是一个测试	
			Reader reader = Resources.getResourceAsReader("export.sql");
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			StringBuffer sb=new StringBuffer();
			while ((line = br.readLine()) != null)
				sb.append(line);
			String rep=StringUtils.replace(sb.toString(), "00137A000001013b", "00137A0000010136");
			rep=StringUtils.replace(sb.toString(), ";", ";\n\r");
	        rep=StringUtils.substringBefore(rep,"#");	 
//	        System.out.println("----\n"+rep);	
	
			StringReader sreader=new StringReader(rep); 
	        runner.runScript(sreader);	  
	        System.out.println("----\n"+stringWriter.getBuffer().toString());
	        sreader.close();
	        reader.close();  
//	        session.close();
		
		return "success";

		// TODO Auto-generated method stub		
	}
	
	private String addWhere(String hql,Map<String, Object> hard,Map<String, Object> params) {
		if (hard!= null){
			params.put("daemonDeviceId", hard.get("type"));
			hql += "where t.daemonDeviceId =:daemonDeviceId";
		}
		return hql;
	}

}
