package com.flywind.app.data;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

import com.flywind.app.entities.Modegroupmain;
import com.flywind.app.entities.Modegroupsub;

//@XStreamAlias("Group")
public class Xmlgroup implements Interface<Modegroupmain,Modegroupsub>{

	private List<Modegroupmain> Main;
	private List<Modegroupsub> Sub;
	private int checked=0;	
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	@Override
	public List<Modegroupmain> getMain() {
		return Main;
	}
	@Override
	public void setMain(List<Modegroupmain> main) {
		Main = main;
	}
	@Override
	public List<Modegroupsub> getSub() {
		return Sub;
	}
	@Override
	public void setSub(List<Modegroupsub> sub) {
		Sub = sub;
	}
	@Override
	public Element toxml() {
		Element elmode = new BaseElement("Group");
		Element elmain=elmode.addElement("Main");
		int groupId=0;		
		for(Modegroupmain m:Main){
			Element elid=elmain.addElement("ID");
			elid.addAttribute("MID", m.getId()+"");
			elid.addAttribute("Name", m.getId()+"");	
			elid.addAttribute("GroupName",m.getGroupName()+"");
			elid.addAttribute("GroupID", ++groupId+"");
		}
		Element elsub=elmode.addElement("Sub");	
		for(Modegroupsub u:Sub){
			Element elid=elsub.addElement("ID");
			elid.addAttribute("SubID", u.getId()+"");			
			elid.addAttribute("MID", u.getMid()+"");		
//			elid.addAttribute("Dest", u.getDeviceId()+"");
			if (u.getDeviceOldId() == 0) {
				elid.addAttribute("Dest", u.getDeviceId()+"");
			} else {
				elid.addAttribute("Dest", u.getDeviceOldId()+"");
			}
//			elid.addAttribute("DeviceName", u.getDeviceId()+"");			
			elid.addAttribute("Status", u.getStatus());							
		}
		return elmode;
	}
	
	static public Xmlgroup fromxml(Document doc,long houseId){
		List<Modegroupmain> mains=new ArrayList<Modegroupmain>();
		List<Modegroupsub> subs=new ArrayList<Modegroupsub>();
		List tableList = doc.selectNodes("//Group/Main");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Group/Main/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modegroupmain main=new Modegroupmain();
    	         main.setOldId(Long.parseLong(column.attributeValue("MID")));
    	         main.setGroupName(column.attributeValue("GroupName"));  
    	         main.setGroupId(Long.parseLong(column.attributeValue("GroupID")));      	         
    	         String text = column.getTextTrim();
    	         mains.add(main);
    	      }
    	   }
    	}
        tableList = doc.selectNodes("//Group/Sub");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Group/Sub/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modegroupsub sub=new Modegroupsub();
    	         sub.setId(Long.parseLong(column.attributeValue("SubID")));
    	         sub.setMid(Long.parseLong(column.attributeValue("MID")));
    	         sub.setDeviceId(Long.parseLong(column.attributeValue("Dest")));        	    	         
    	         sub.setDeviceOldId(Long.parseLong(column.attributeValue("Dest")));        	    	         
//    	         sub.setDeviceId(Long.parseLong(column.attributeValue("DeviceName")));  
    	         sub.setStatus(column.attributeValue("Status"));
    	         String text = column.getTextTrim();
    	         subs.add(sub);
    	      }
    	   }
    	}		
        Xmlgroup xmlgroup=new Xmlgroup();
        xmlgroup.setMain(mains);
        xmlgroup.setSub(subs);    	
    	return xmlgroup;			
	}
}
