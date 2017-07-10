package com.holy.test;

import com.holy.bean.AddressBook;
import com.holy.bean.Page;
import com.holy.bean.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		
		System.out.println("-----------------写xml文件-------------------");
		
		/** 添加十个Person放入List */
	    List list = new ArrayList();
	    for (int i = 0; i < 10; i++) {
	
	        Person address = new Person();
	        address.setName("张三");
	        address.setAge("26");
	        address.setSex("男");
	        list.add(address);
	
	    }
	
	    AddressBook addes = new AddressBook();
	    addes.setPersons(list);
	    Page page = new Page();
	    page.setAddressBook(addes);
//	    Util.makeXML(page);
	    Util.writeXML(Util.makeXML(page));
	    System.out.println("-----------------xml文件内容-------------------");
	    System.out.println(Util.makeXML(page));
	    System.out.println("-----------------读xml文件(1) dom4j解析-------------------");
	    File f = new File("e:/testXtram.xml");
	    try {

	        InputStream in = new FileInputStream(f);
	        Page page1 = Util.readXML(in);
	        int personCount = page1.getAddressBook().getPersons().size();
	        for (int i = 0; i < personCount; i++) {

	            Person person = page1.getAddressBook().getPersons().get(i);
	            System.out.println(person.getName() + "  " + person.getAge() + "  " + person.getSex());

	        }

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    System.out.println("-----------------读xml文件(2) xstream转换-------------------");
	    File f2 = new File("e://testXtram.xml");

	    try {
	
	        InputStream in = new FileInputStream(f2);
	        Page page2 = Util.readXML2(in);
	        for (int i = 0; i < page2.getAddressBook().getPersons().size(); i++) {
	
	            Person person = page2.getAddressBook().getPersons().get(i);
	            System.out.println(person.getName() + "  " + person.getAge() + "  " + person.getSex());
	
	        }
	
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
}
