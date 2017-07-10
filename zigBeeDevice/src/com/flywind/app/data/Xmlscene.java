package com.flywind.app.data;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

import com.flywind.app.entities.Modescenemain;
import com.flywind.app.entities.Modescenesub;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Scene")
public class Xmlscene implements Interface<Modescenemain,Modescenesub>{

	private List<Modescenemain> Main;
	private List<Modescenesub> Sub;
	private int checked=0;	
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	@Override
	public List<Modescenemain> getMain() {
		return Main;
	}
	@Override
	public void setMain(List<Modescenemain> main) {
		Main = main;
	}
	@Override
	public List<Modescenesub> getSub() {
		return Sub;
	}
	@Override
	public void setSub(List<Modescenesub> sub) {
		Sub = sub;
	}
	@Override
	public Element toxml() {
		Element elmode = new BaseElement("Scene");
		Element elmain=elmode.addElement("Main");
		int groupId=0;		
		for(Modescenemain m:Main){
			Element elid=elmain.addElement("ID");
			elid.addAttribute("MID", m.getId()+"");
			elid.addAttribute("Name", m.getGroupName()+"");			
			elid.addAttribute("SceneName", m.getGroupName()+"");
			elid.addAttribute("SceneID", ++groupId+"");
			elid.addAttribute("GroupID", "0");
		}
		Element elsub=elmode.addElement("Sub");	
		for(Modescenesub u:Sub){
			Element elid=elsub.addElement("ID");
			elid.addAttribute("SubID", u.getId()+"");			
			elid.addAttribute("MID", u.getMid()+"");		
//			elid.addAttribute("Dest", u.getDeviceId()+"");	
			if (u.getDeviceOldId() == 0) {
				elid.addAttribute("Dest", u.getDeviceId()+"");
			} else {
				elid.addAttribute("Dest", u.getDeviceOldId()+"");
			}
			if(u.getPara()!=null)elid.addAttribute("Para",u.getPara());
			else elid.addAttribute("Para", "TransTime="+u.getTransTime()+",Extention field sets="+u.getScenePara());
			elid.addAttribute("ActlibID", u.getActlibId()+"");
			elid.addAttribute("Status", u.getStatus());							
		}
		return elmode;
	}
	
	static public Xmlscene fromxml(Document doc,long houseId){
		List<Modescenemain> mains=new ArrayList<Modescenemain>();
		List<Modescenesub> subs=new ArrayList<Modescenesub>();
		List tableList = doc.selectNodes("//Scene/Main");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Scene/Main/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modescenemain main=new Modescenemain();
    	         main.setOldId(Long.parseLong(column.attributeValue("MID")));
    	         main.setGroupName(column.attributeValue("SceneName"));    	         
    	         main.setSceneId(Long.parseLong(column.attributeValue("SceneID")));  
    	         String text = column.getTextTrim();
    	         mains.add(main);
    	      }
    	   }
    	}
        tableList = doc.selectNodes("//Scene/Sub");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//Scene/Sub/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modescenesub sub=new Modescenesub();
    	         sub.setId(Long.parseLong(column.attributeValue("SubID")));
    	         sub.setMid(Long.parseLong(column.attributeValue("MID")));
    	         sub.setDeviceId(Long.parseLong(column.attributeValue("Dest")));
    	         sub.setDeviceOldId(Long.parseLong(column.attributeValue("Dest"))); 
    	         sub.setPara(column.attributeValue("Para")); 
    	         sub.setActlibId(Integer.parseInt(column.attributeValue("ActlibID")));
    	         sub.setStatus(column.attributeValue("Status"));
    	         String text = column.getTextTrim();
    	         subs.add(sub);
    	      }
    	   }
    	}		
        Xmlscene xmlscene=new Xmlscene();
        xmlscene.setMain(mains);
        xmlscene.setSub(subs);    	
    	return xmlscene;			
	}
}
