package com.flywind.app.data;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import com.flywind.app.entities.Modeschememain;
import com.flywind.app.entities.Modeschemesub;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Scheme")
public class Xmlscheme implements Interface<Modeschememain,Modeschemesub> {

	private List<Modeschememain> Main;
	private List<Modeschemesub> Sub;
	@Override
	public List<Modeschememain> getMain() {
		return Main;
	}
	@Override
	public void setMain(List<Modeschememain> main) {
		Main = main;
	}
	@Override
	public List<Modeschemesub> getSub() {
		return Sub;
	}
	@Override
	public void setSub(List<Modeschemesub> sub) {
		Sub = sub;
	}
	@Override
	public Element toxml() {
		Element elmode = new BaseElement("Scheme");
		Element elmain=elmode.addElement("Main");
		for(Modeschememain m:Main){
			Element elid=elmain.addElement("ID");
			elid.addAttribute("MID", m.getId()+"");
			elid.addAttribute("Name", m.getSchemeName()+"");
			elid.addAttribute("Picname", m.getPicName()+"");			
			elid.addAttribute("bActive", m.getBactive()+"");	
			elid.addAttribute("bAllmodeActive", m.getBallmodeActive()+"");				
		}
		Element elsub=elmode.addElement("Sub");	
		for(Modeschemesub u:Sub){
			Element elid=elsub.addElement("ID");
			elid.addAttribute("SubID", u.getId()+"");			
			elid.addAttribute("MID", u.getMid()+"");		
			elid.addAttribute("Sun", u.getSun()+"");		
			elid.addAttribute("Mon", u.getMon()+"");					
			elid.addAttribute("TUE", u.getTue()+"");		
			elid.addAttribute("WED", u.getWed()+"");		
			elid.addAttribute("THR", u.getThr()+"");		
			elid.addAttribute("FRI", u.getFri()+"");		
			elid.addAttribute("Sat", u.getSat()+"");		
			elid.addAttribute("Time", u.getTime()+"");		
			elid.addAttribute("Act", u.getAct()+"");		
			elid.addAttribute("Dest", u.getDest()+"");					
		}
		return elmode;
	}

	static public Xmlscheme fromxml(Document doc,long houseId){
		List<Modeschememain> mains=new ArrayList<Modeschememain>();
		List<Modeschemesub>	subs=new ArrayList<Modeschemesub>();
		List tableList = doc.selectNodes("//Scheme/Main");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Scheme/Main/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modeschememain main=new Modeschememain();
    	         main.setOldId(Long.parseLong(column.attributeValue("MID")));
    	         main.setSchemeName(column.attributeValue("Name"));
    	         main.setPicName(column.attributeValue("Picname"));
    	         main.setBactive(Short.parseShort(column.attributeValue("bActive")));    
    	         main.setBallmodeActive(Short.parseShort(column.attributeValue("bAllmodeActive")));
    	         String text = column.getTextTrim();
    	         mains.add(main);
    	      }
    	   }
    	}
        tableList = doc.selectNodes("//Scheme/Sub");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Scheme/Sub/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modeschemesub sub=new Modeschemesub();
    	         sub.setId(Long.parseLong(column.attributeValue("SubID")));
    	         sub.setMid(Long.parseLong(column.attributeValue("MID")));
    	         sub.setSun(Short.parseShort(column.attributeValue("Sun")));
    	         if ("null".equals(column.attributeValue("Mon"))) {
        	         sub.setMon((short)1);   
    	         } else {
        	         sub.setMon(Short.parseShort(column.attributeValue("Mon")));   
    	         }
    	         sub.setTue(Short.parseShort(column.attributeValue("TUE"))); 
    	         sub.setWed(Short.parseShort(column.attributeValue("WED"))); 
    	         sub.setThr(Short.parseShort(column.attributeValue("THR"))); 
    	         sub.setFri(Short.parseShort(column.attributeValue("FRI"))); 
    	         sub.setSat(Short.parseShort(column.attributeValue("Sat"))); 
    	         sub.setTime(column.attributeValue("Time")); 
    	         sub.setAct(column.attributeValue("Act")); 
    	         sub.setDest(Long.parseLong(column.attributeValue("Dest")));     	         
    	         String text = column.getTextTrim();
    	         subs.add(sub);
    	      }
    	   }
    	}		
		Xmlscheme xmlscheme=new Xmlscheme();
		xmlscheme.setMain(mains);
        xmlscheme.setSub(subs);    	
    	return xmlscheme;		
	}
	
}
