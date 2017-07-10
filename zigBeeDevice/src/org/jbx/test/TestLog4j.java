package org.jbx.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;


public class TestLog4j {

	private static final Logger logger = Logger.getLogger(TestLog4j.class);

	@Test
	public static void testInfo(Object object) {
		//读取日志
		String folder = TestLog4j.class.getResource("/log4j.properties").toString();
	      if (folder.indexOf("file:") >= 0) {
	          folder = folder.substring(6);
	       } else if (folder.indexOf("jar:") >= 0) {
	          folder = folder.substring(4);
	       }
	       PropertyConfigurator.configure(folder);
		logger.info(object);
			
	}
	public static String testConfig(String key) {
		//InputStream in = new FileInputStream("config.properties");	
		InputStream in = TestLog4j.class.getClassLoader().getResourceAsStream("/resources.properties");		
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return p.getProperty(key);
	}

}
