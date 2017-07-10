package sy.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class TestLog4j {

	private static final Logger logger = Logger.getLogger(TestLog4j.class);

	public static void testInfo(Object object) {
		
		String folder = TestLog4j.class.getResource("/log4j.properties").toString();
	      if (folder.indexOf("file:") >= 0) {
	          folder = folder.substring(6);
	       } else if (folder.indexOf("jar:") >= 0) {
	          folder = folder.substring(4);
	       }
	       PropertyConfigurator.configure(folder);
		logger.info(object);
			
	}

}
