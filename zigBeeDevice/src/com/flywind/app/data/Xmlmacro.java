package com.flywind.app.data;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

import com.flywind.app.entities.Modemacromain;
import com.flywind.app.entities.Modemacrosub;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Macro")
public class Xmlmacro implements Interface<Modemacromain,Modemacrosub>{

	private List<Modemacromain> Main;
	private List<Modemacrosub> Sub;
	@Override
	public List<Modemacromain> getMain() {
		return Main;
	}
	@Override
	public void setMain(List<Modemacromain> main) {
		Main = main;
	}
	@Override
	public List<Modemacrosub> getSub() {
		return Sub;
	}
	@Override
	public void setSub(List<Modemacrosub> sub) {
		Sub = sub;
	}
	@Override
	public Element toxml() {
		Element elmode = new BaseElement("Macro");
		Element elmain=elmode.addElement("Main");
		for(Modemacromain m:Main){
			Element elid=elmain.addElement("ID");
			elid.addAttribute("MID", m.getId()+"");
			elid.addAttribute("Name", m.getMacroName()+"");
			elid.addAttribute("Picname", m.getPicName()+"");						
		}
		Element elsub=elmode.addElement("Sub");	
		for(Modemacrosub u:Sub){
			Element elid=elsub.addElement("ID");
			elid.addAttribute("SubID", u.getId()+"");			
			elid.addAttribute("MID", u.getMid()+"");		
			elid.addAttribute("Name", u.getName()+"");				
			elid.addAttribute("DelaySec", u.getDelaySec()+"");	
			elid.addAttribute("Act", u.getAct()+"");	
			elid.addAttribute("DestType", u.getDestType()+"");	
//			elid.addAttribute("Dest", u.getDest()+"");	
//			取Dest、Para、Extension、Param取值相同
			String Dest = null;
			if (u.getDestOldId() != null && u.getDestOldId() == 0) {
				Dest = u.getDest()+"";
			} else {
				Dest = u.getDestOldId()==null?"0":(u.getDestOldId()+"");
			}
			elid.addAttribute("Dest", Dest);
//			elid.addAttribute("Para", Dest);
//			elid.addAttribute("Extension",Dest);
//			elid.addAttribute("Param",Dest);	
//			if (u.getDestOldId() != null && u.getDestOldId() == 0) {
//				elid.addAttribute("Dest", u.getDest()+"");
//			} else {
//				elid.addAttribute("Dest", u.getDestOldId()==null?"0":(u.getDestOldId()+""));
//			}
			if(u.getExtension()!=null&&u.getExtension().length()>0)	elid.addAttribute("Para", u.getExtension());
			else elid.addAttribute("Para", u.getParam());
			elid.addAttribute("Extension",u.getExtension());
			elid.addAttribute("Param",u.getParam());
			
			elid.addAttribute("ActlibID", u.getActlibId()+"");				
		}
		return elmode;
	}
	
	static public Xmlmacro fromxml(Document doc,long houseId){
		List<Modemacromain> mains=new ArrayList<Modemacromain>();
		List<Modemacrosub>	subs=new ArrayList<Modemacrosub>();
		List tableList = doc.selectNodes("//Macro/Main");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Macro/Main/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modemacromain main=new Modemacromain();
    	         main.setOldId(Long.parseLong(column.attributeValue("MID")));
    	         main.setMacroName(column.attributeValue("Name"));
    	         main.setPicName(column.attributeValue("Picname"));    	         
    	         String text = column.getTextTrim();
    	         mains.add(main);
    	      }
    	   }
    	}
        tableList = doc.selectNodes("//Macro/Sub");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Macro/Sub/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modemacrosub sub=new Modemacrosub();
    	         sub.setId(Long.parseLong(column.attributeValue("SubID")));
    	         sub.setMid(Long.parseLong(column.attributeValue("MID")));
    	         sub.setName(column.attributeValue("Name"));
    	         sub.setDelaySec(Long.parseLong(column.attributeValue("DelaySec")));   
    	         sub.setAct(column.attributeValue("Act")); 
    	         sub.setDestType(column.attributeValue("DestType"));
    	         sub.setDest(Long.parseLong(column.attributeValue("Dest")));
    	         if ("0".equals(sub.getDestType())) {
    	        	 sub.setDestOldId(Long.parseLong(column.attributeValue("Dest")));
    	         }
      	         sub.setPara(column.attributeValue("Para")); 
      	         sub.setExtension(column.attributeValue("Extension"));       
    	         sub.setParam(column.attributeValue("Param")); 
    	         sub.setActlibId(Integer.parseInt(column.attributeValue("ActlibID")));
    	         String text = column.getTextTrim();
    	         subs.add(sub);
    	      }
    	   }
    	}		
        Xmlmacro xmlmacro=new Xmlmacro();
        xmlmacro.setMain(mains);
        xmlmacro.setSub(subs);    	
    	return xmlmacro;		
	}
	
}
