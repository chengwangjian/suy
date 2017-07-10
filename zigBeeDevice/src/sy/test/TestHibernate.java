package sy.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sy.service.UserServiceI;

public class TestHibernate {

	@Test
	public void test() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] { "classpath:spring.xml", "classpath:hibernatecfg.xml"});
		UserServiceI userService = (UserServiceI) ac.getBean("userService");
/*		Tuser t = new Tuser();
		t.setId(UUID.randomUUID().toString());
		t.setName("孙宇");
		t.setPwd("123465");
		t.setCreatedatetime(new Date());*/
		userService.test();
	}

}
