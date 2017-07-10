package com.holy.test;

import com.holy.bean.AddressBook;
import com.holy.bean.Page;
import com.holy.bean.Person;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Util {
	
	public static String makeXML(Page page) {
		
        XStream stream = new XStream();
        stream = setXStream(stream);
        String str = stream.toXML(page);
        return str;
 
    }
 
    public static void writeXML(String s) {
 
        FileWriter fw = null;
        
        try {
			
			fw = new FileWriter("e:/testXtram.xml");
			fw.write(s, 0, s.length());
            fw.flush();
            System.out.println("写文件成功!");
 
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
 
    public static Page readXML2(InputStream inputStream) {
 
        XStream stream = new XStream(new DomDriver());
        stream = setXStream(stream);
        return (Page) stream.fromXML(inputStream);
        
    }
 
    private static XStream setXStream(XStream stream) {
 
        stream.alias("Page", Page.class);
        stream.alias("Person", Person.class);
        stream.aliasAttribute(Person.class, "name", "name");
        stream.aliasAttribute(Person.class, "age", "age");
        stream.aliasAttribute(Person.class, "sex", "sex");
        stream.alias("AddressBook", AddressBook.class);
        return stream;
 
    }
 
    public static Page readXML(InputStream inputStream) {
 
        System.out.println("开始读取xml文件并封装为对象!");
        String str = getInputStreamToString(inputStream);
        Page page = new Page();
        AddressBook addressBook = new AddressBook();
        SAXReader reader = new SAXReader();
        Document document = null;

        try {
 
            document = reader.read(new StringReader(str));
            Element root = document.getRootElement();
            Element root2 = root.element("addressBook").element("persons");
            List<Person> persons = new ArrayList<Person>();
            for (Iterator item = root2.elementIterator(); item.hasNext();) {

            	/** 解析属性 */
                Element element = (Element) item.next();
                Person person = new Person();
                person.setName(String.valueOf(element.attribute("name").getText()));
                person.setAge(String.valueOf(element.attribute("age").getText()));
                person.setSex(String.valueOf(element.attribute("sex").getText()));
                persons.add(person);
 
            }
 
            addressBook.setPersons(persons);
 
        } catch (DocumentException e) {
            e.printStackTrace();
        }
 
        page.setAddressBook(addressBook);
        return page;
 
    }
 
    public static String getInputStreamToString(InputStream inputStream) {
 
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
 
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
 
        }
        return sb.toString();
 
    }

}
