package org.jbx.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zbHouse.model.Node;
import zbHouse.service.NodeServiceI;

public class TestHibernate {
	
	@Test
	public void test1() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] { "classpath:spring.xml" });
		
		NodeServiceI nodeService = (NodeServiceI) ac.getBean("nodeService");
		Node n = new Node();
		n.setIeee("test");
		n.setIp("test");	
		nodeService.keyUpdate(n);
	
	}

}
