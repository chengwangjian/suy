package sy.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sy.service.UserServiceI;


public class TestJson {

	private static final Logger logger = Logger.getLogger(TestJson.class);

	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	@Test
	public void test() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] { "classpath:spring.xml", "classpath:hibernatecfg.xml"});
		UserServiceI userService = (UserServiceI) ac.getBean("userService");
		userService.test();
		//User u = userService.getUserById("1");	
		//logger.info(JSON.toJSONStringWithDateFormat(u, "yyyy-MM-dd HH:mm:ss"));
		
	}

}
