package com.flywind.app.services;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.flywind.app.dal.StartDAO;
import com.flywind.app.data.Interface;
import com.flywind.app.data.Interface2;
import com.flywind.app.data.Interface3;
import com.flywind.app.data.Intermain;
import com.flywind.app.data.Intermain2;
import com.flywind.app.data.Intersub;
import com.flywind.app.data.Xmldevicebind;
import com.flywind.app.data.Xmlgroup;
import com.flywind.app.data.Xmlhvac;
import com.flywind.app.data.Xmlias;
import com.flywind.app.data.Xmlmacro;
import com.flywind.app.data.Xmlmodemain;
import com.flywind.app.data.Xmlscene;
import com.flywind.app.data.Xmlscheme;
import com.flywind.app.entities.Modemainclause;
import com.flywind.app.entities.Usermodedevice;
import com.flywind.app.entities.Usermodemain;
import com.flywind.app.entities.Usermodesub;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Service("dxmlutil")
public class XmlUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtil.class);
	public static String path="g:/tmp";
	public static void main(String[] args) {
//		new Datamodemain().initialize();
//		new XmlUtil().makeXML(Datamodemain.xmlmode);
		new XmlUtil().readFILE();
	}
	
	private StartDAO dao;
    public StartDAO getDao() {
		return dao;
	}
    @Autowired
	public void setDao(StartDAO dao) {
		this.dao = dao;
	} 	
	
    public Interface loadEntity(long houseId,int i){
    	//从数据库导出xml
    	String str = "";
    	String[] strArray={
    			NamedQueries.Modeschemesub_houseId,NamedQueries.Modeschememain_houseId,
				NamedQueries.Modeiassub_houseId,NamedQueries.Modeiasmain_houseId,
				NamedQueries.Modehvacsub_houseId,NamedQueries.Modehvacmain_houseId,    			
    			NamedQueries.Usermodesub_houseId,NamedQueries.Usermodemain_houseId,
    						NamedQueries.Modemacrosub_houseId,NamedQueries.Modemacromain_houseId,
    						NamedQueries.Modegroupsub_houseId,NamedQueries.Modegroupmain_houseId,
    						NamedQueries.Modescenesub_houseId,NamedQueries.Modescenemain_houseId};
    	Interface<?, ?> inter = null;
        try{
	    	List ls=dao.find(strArray[i],
					QueryParameters.with("houseId", houseId).parameters());	
	        List lm=dao.find(strArray[i+1],
					QueryParameters.with("houseId", houseId).parameters());
	        
	        if(i==0){//检查bAllmode标志位
	        	inter=new Xmlscheme();
	        	dao.executeHql("{CALL Modescheme(4 ,:houseId, 0, 0)}", 
	        			QueryParameters.with("houseId", houseId).parameters()); 
	        }
	        if(i==2){//检查bAllmode标志位
	        	inter=new Xmlias();
	        	dao.executeHql("{CALL Modescheme(5 ,:houseId, 0, 0)}", 
	        			QueryParameters.with("houseId", houseId).parameters()); 	        	
	        }
	        if(i==4){//检查bAllmode标志位
	        	inter=new Xmlhvac(dao, houseId);
	        	dao.executeHql("{CALL Modescheme(6 ,:houseId, 0, 0)}", 
	        			QueryParameters.with("houseId", houseId).parameters()); 
	        }
	        if(i==6){//模式表导出
	        	Interface2 inter2=new Xmlmodemain();	
	        	List<Usermodedevice> append=dao.find(NamedQueries.Usermodedevice_houseId,
						QueryParameters.with("houseId", houseId).parameters());		
	        	dao.getSession().clear();
	        	int mainMax=0;
	        	for (Object m:lm){
		        Usermodemain m1=(Usermodemain)m;
		        if(m1.getOldId()!=null&&m1.getOldId()!=0){
		        	long newid=m1.getId();
		        	long oldid=m1.getOldId();
		        	for(Object u:ls){
		        		Usermodesub u1=(Usermodesub)u;
		        		if(u1.getMid()==newid)	u1.setMid(oldid);
		        	}
		        	for(Usermodedevice d:append){
		        		if(d.getMid()==newid)	d.setMid(oldid);
		        	}
		        	mainMax++;
		        }else{//否则拷贝
		        	m1.setOldId(m1.getId());
		        }
		        }
	        	LOGGER.info("mainMax.size:"+mainMax);
		        inter2.setSub(ls);
		        inter2.setMain(lm);
	        	inter2.setDeviceCheck(append);
	        	inter=inter2;
	        }
	        if(i==8){//检查desttype关联id是否存在
	        	inter=new Xmlmacro();
	        	List append=dao.find(NamedQueries.Modemacrosub_houseId2,QueryParameters.with("houseId", houseId).parameters());
	        	ls.addAll(append);
	        	LOGGER.info("macro2.size:"+append.size()+"----"+"ls.size:"+ls.size());
	        	append=dao.find(NamedQueries.Modemacrosub_houseId3,QueryParameters.with("houseId", houseId).parameters());
	        	ls.addAll(append);
	        	LOGGER.info("macro3.size:"+append.size()+"----"+"ls.size:"+ls.size());	        	
	        }
	        if(i==10)inter=new Xmlgroup();	  
	        if(i==12)inter=new Xmlscene();	
	        if(i!=6){//一般导出
		        inter.setSub(ls); 
		        inter.setMain(lm);
	        }
        }catch (Exception e) {
//        	System.out.println("Exception----i:" + i);          	
//        	System.out.println("Exception----i:"+e.getLocalizedMessage());
        	LOGGER.info("loadEntity---" + i, e);
        } 
//	        LOGGER.info("loadEntity--" + JSON.toJSONString(inter));   	
//	        str+=makeXML(inter);
//        writeXML(str);
        return inter;
    }

    public Interface saveEntity(Document doc,long houseId,int i,String xmlVersion){
    	//从xml导入数据库
    	Interface inter=null; 
        if(i==0)inter=Xmlscheme.fromxml(doc, houseId);
        if(i==2)inter=Xmlias.fromxml(doc, houseId);
        if(i==4) {
        	Interface3 inter3=Xmlhvac.fromxml(doc, houseId,xmlVersion);
        	modecreate2(inter3.getMain(),inter3.getSub(),inter3.getModemainclauses(),houseId,xmlVersion);
        }
    	if(i==6){
    		Interface2 inter2=Xmlmodemain.fromxml(doc, houseId);
    		LOGGER.info("houseId--"+houseId+"--i--"+i+"before--" +JSON.toJSONString(inter2));
    		modecreate(inter2.getMain(),inter2.getSub(),inter2.getDeviceCheck(),houseId);
    		LOGGER.info("saveEntity--"+JSON.toJSONString(inter2));
    		inter=inter2;
    	}
        if(i==8)inter=Xmlmacro.fromxml(doc, houseId);
        if(i==10)inter=Xmlgroup.fromxml(doc, houseId);  
        if(i==12)inter=Xmlscene.fromxml(doc, houseId);  
        if(i!=6 && i!=4){
	        LOGGER.info("houseId--"+houseId+"--i--"+i+"before--" +JSON.toJSONString(inter));
	    	create(inter.getMain(),inter.getSub(),houseId);
	        LOGGER.info("saveEntity--"+JSON.toJSONString(inter));
        }
        return inter;
    }//一般导入
    public void create(List<Intermain> main,List<Intersub> sub,long houseId)
    {
    	int mainMax=0,subMax=0;
    	for (Intermain m : main)
        {
    		m.setHouseId(houseId);
    		long oldid=m.getOldId();
        	long newid=dao.create(m).getId();
        	mainMax++;
        	for(Intersub u :sub)
        	{
        		if(oldid==u.getMid()){
        			u.setHouseId(houseId);
        			u.setMid(newid);
        			dao.create(u);
        			subMax++;
        		}
        	}
        }  
        LOGGER.info("1111-m----" +mainMax+"u----"+subMax);
    }//模式表导入
    public void modecreate(List<Intermain> main,List<Intersub> sub,List<Usermodedevice> devicecheck,long houseId) {
    	int mainMax=0,subMax=0,checkMax=0;
    	for (Intermain m : main)
        {
    		m.setHouseId(houseId);
    		long oldid=m.getOldId();
        	long newid=dao.create(m).getId();
        	mainMax++;
        	for(Intersub u :sub)
        	{
        		if(oldid==u.getMid()){
        			u.setHouseId(houseId);
        			u.setMid(newid);
        			dao.create(u);
        			subMax++;
        		}
        	}
        	for(Usermodedevice d :devicecheck)
        	{
        		if(oldid==d.getMid()){   
        			d.setHouseId(houseId);
        			d.setMid(newid);
        			dao.create(d);
        			checkMax++;       			
        		}
        	}        	
        }
    	LOGGER.info("2222-m----" +mainMax+"u----"+subMax+"d----"+checkMax);
    }
    
    public void modecreate2(List<Intermain2> main,List<Intersub> sub,List<Modemainclause> modemainclauses,long houseId,String xmlVersion) {
    	int mainMax=0,subMax=0,checkMax=0;
    	for(Modemainclause m :modemainclauses)
    	{
    		m.setHouseId(houseId);
    		long oldid=m.getOldId();
        	long newid=dao.create(m).getId();
        	mainMax++;
        	for (Intermain2 u : main)
            {
        		if(oldid==u.getMainId()){
        			u.setHouseId(houseId);
        			u.setMainId(newid);
        			dao.create(u);
        			subMax++;
        		}
            }
        	for(Intersub u1 :sub)
        	{
        		if(oldid==u1.getMid()){
        			u1.setHouseId(houseId);
        			u1.setMid(newid);
        			dao.create(u1);
        		}
        	}
    	}
    	LOGGER.info("2222-m----" +mainMax+"u----"+subMax+"d----"+checkMax);
    }
    
	public void writeXML(String s) {
    	 
        FileWriter fw = null;        
        try {
			
			fw = new FileWriter(path +"/testXtram.xml");
			fw.write(s, 0, s.length());
            fw.flush();
            System.out.println("写锟侥硷拷锟缴癸拷!");
 
        } catch (IOException e) {
//            e.printStackTrace();
        	LOGGER.info("writeXML", e);
        }
 
    }	
	public String makeXML(Object page) {
		        
		XStream stream = new XStream(); 		
		if(page instanceof Xmlmodemain)stream.processAnnotations(Xmlmodemain.class);
		if(page instanceof Xmlscheme)stream.processAnnotations(Xmlscheme.class);
		if(page instanceof Xmlmacro)stream.processAnnotations(Xmlmacro.class);
		if(page instanceof Xmlias)stream.processAnnotations(Xmlias.class);
		if(page instanceof Xmlhvac)stream.processAnnotations(Xmlhvac.class);
		if(page instanceof Xmlgroup)stream.processAnnotations(Xmlgroup.class);
		if(page instanceof Xmldevicebind)stream.processAnnotations(Xmldevicebind.class);
        String str = stream.toXML(page);
        return str;
 
    }
		
    public Object readXML(String type,long houseId,InputStream inputStream) {
    	 
        XStream stream = new XStream(new DomDriver());  
        if(type.equalsIgnoreCase("Xmlmodemain"))stream.processAnnotations(Xmlmodemain.class);
        if(type.equalsIgnoreCase("Xmlscheme"))stream.processAnnotations(Xmlscheme.class);
        if(type.equalsIgnoreCase("Xmlmacro"))stream.processAnnotations(Xmlmacro.class);
        if(type.equalsIgnoreCase("Xmlias"))stream.processAnnotations(Xmlias.class);
        if(type.equalsIgnoreCase("Xmlhvac"))stream.processAnnotations(Xmlhvac.class);
        if(type.equalsIgnoreCase("Xmlgroup"))stream.processAnnotations(Xmlgroup.class);
        if(type.equalsIgnoreCase("Xmldevicebind"))stream.processAnnotations(Xmldevicebind.class);
        Object o=stream.fromXML(inputStream);
//        saveEntity(o);
        LOGGER.info(JSON.toJSONString(o));
        return o;
        
    }
    
	public void readFILE(){

	    try {
		    File f2 = new File(path+"//Xmlgroup.xml");	
	        InputStream in = new FileInputStream(f2);
	        Object o=readXML("Xmlgroup",2,in);
//	        saveEntity(o,2);
	    } catch (FileNotFoundException e) {
//	        e.printStackTrace();
	    	LOGGER.info("readFILE", e);
	    }
	}

}
