package com.flywind.app.data;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

import com.flywind.app.entities.Usermodedevice;
import com.flywind.app.entities.Usermodemain;
import com.flywind.app.entities.Usermodesub;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Mode")
public class Xmlmodemain implements Interface2<Usermodemain,Usermodesub,Usermodedevice>{

	private List<Usermodemain> Main;
	private List<Usermodesub> Sub;
	private List<Usermodedevice> DeviceCheck;
	@Override
	public List<Usermodemain> getMain() {
		return Main;
	}
	@Override
	public void setMain(List<Usermodemain> main) {
		Main = main;
	}
	@Override
	public List<Usermodesub> getSub() {
		return Sub;
	}
	@Override
	public void setSub(List<Usermodesub> sub) {
		Sub = sub;
	}	
	@Override
	public List<Usermodedevice> getDeviceCheck() {
		return DeviceCheck;
	}
	@Override
	public void setDeviceCheck(List<Usermodedevice> deviceCheck) {
		DeviceCheck = deviceCheck;
	}
	
	@Override
	public Element toxml(){
		Element elmode = new BaseElement("Mode");
		Element elmain=elmode.addElement("Main");
		for(Usermodemain m : Main){
			Element elid=elmain.addElement("ID");
			elid.addAttribute("MID", m.getOldId()+"");
			elid.addAttribute("Name", m.getModeName()+"");
			elid.addAttribute("RoomID", m.getRoomId()+"");
			elid.addAttribute("Picname", m.getPicName()+"");
			elid.addAttribute("Description", m.getDescription()+"");	
			elid.addAttribute("EnableCheckOnOff", m.getEnableCheckOnOff()+"");	
		}
		Element elsub=elmode.addElement("Sub");	
		for(Usermodesub u : Sub){		
			Element elid=elsub.addElement("ID");
			elid.addAttribute("SubID",u.getId()+"");
			elid.addAttribute("MID", u.getMid()+"");
			elid.addAttribute("Act", u.getAct()+"");
			elid.addAttribute("Dest", u.getDest()+"");			
		}
		Element eldevice=elmode.addElement("DeviceCheck");
		for(Usermodedevice d : DeviceCheck){	
			Element elid=eldevice.addElement("ID");
			elid.addAttribute("SubID",d.getId()+"");
			elid.addAttribute("MID",d.getMid()+"");
//			elid.addAttribute("Dest",d.getDest()+"");
			if (d.getOldDestId() == 0) {
				elid.addAttribute("Dest", d.getDest()+"");
			} else {
				elid.addAttribute("Dest", d.getOldDestId()+"");
			}
		}
		return elmode;
	}
	static public Xmlmodemain fromxml(Document doc,long houseId){
        List<Usermodemain> mains = new ArrayList<Usermodemain>();		
		List tableList = doc.selectNodes("//Mode/Main");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Mode/Main/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Usermodemain main=new Usermodemain();
    	         main.setOldId(Long.parseLong(column.attributeValue("MID")));
    	         main.setModeName(column.attributeValue("Name"));
    	         main.setRoomId(Long.parseLong(column.attributeValue("RoomID")));
    	         main.setPicName(column.attributeValue("Picname"));
    	         main.setDescription(column.attributeValue("Description")); 
    	         if (column.attributeValue("EnableCheckOnOff") != null && !"".equals(column.attributeValue("EnableCheckOnOff"))) {
    	        	 main.setEnableCheckOnOff(Short.parseShort(column.attributeValue("EnableCheckOnOff")));
    	         } else {
    	        	 main.setEnableCheckOnOff((short)0);
    	         }
    	         String text = column.getTextTrim();
    	         mains.add(main);
    	      }
    	   }
    	}
        List<Usermodesub> subs = new ArrayList<Usermodesub>();        
        tableList = doc.selectNodes("//Mode/Sub");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Mode/Sub/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Usermodesub sub=new Usermodesub();
    	         sub.setId(Long.parseLong(column.attributeValue("SubID")));
    	         sub.setMid(Long.parseLong(column.attributeValue("MID")));
    	         sub.setAct(column.attributeValue("Act"));
    	         if (column.attributeValue("Act").equals("ActiveIAS") || column.attributeValue("Act").equals("ActiveHVAC")) {
    	        	 sub.setSelectss(1);
    	         }
    	         if (column.attributeValue("Act").equals("DeactiveIAS") || column.attributeValue("Act").equals("DeactiveHVAC")) {
    	        	 sub.setSelectss(0);
    	         }
    	         if (column.attributeValue("Act").equals("IgnoreHVAC") || column.attributeValue("Act").equals("IgnoreIAS")) {
    	        	 sub.setSelectss(2);
    	         }
    	         sub.setDest(Long.parseLong(column.attributeValue("Dest")));    	            	         
    	         String text = column.getTextTrim();
    	         subs.add(sub);
    	      }
    	   }
    	} 
        List<Usermodedevice> devicechecks= new ArrayList<Usermodedevice>();
        tableList = doc.selectNodes("//Mode/DeviceCheck");
        for (Object tableObj : tableList ) {
     	   Element table = (Element) tableObj;
     	   if(null != table){
     	      List columnList = table.getDocument().selectNodes("//Mode/DeviceCheck/ID");
    	      for(Object columnObj : columnList){
     	         Element column= (Element) columnObj;
     	         Usermodedevice devicecheck=new Usermodedevice();
     	         devicecheck.setId(Long.parseLong(column.attributeValue("SubID")));
     	         devicecheck.setMid(Long.parseLong(column.attributeValue("MID")));
     	         devicecheck.setDest(Long.parseLong(column.attributeValue("Dest")));
     	         devicecheck.setOldDestId(Long.parseLong(column.attributeValue("Dest")));
     	         devicechecks.add(devicecheck);
    	      }
     	   }
        }
		Xmlmodemain xmlmode=new Xmlmodemain();
        xmlmode.setMain(mains);
        xmlmode.setSub(subs);  
        xmlmode.setDeviceCheck(devicechecks);
    	return xmlmode;
	}
}
