package com.flywind.app.data;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

import com.flywind.app.entities.Modeiasmain;
import com.flywind.app.entities.Modeiassub;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("IAS")
public class Xmlias implements Interface<Modeiasmain,Modeiassub> {

	private List<Modeiasmain> Main;
	private List<Modeiassub> Sub;
	private int checked=0;	
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	@Override
	public List<Modeiasmain> getMain() {
		return Main;
	}
	@Override
	public void setMain(List<Modeiasmain> main) {
		Main = main;
	}
	@Override
	public List<Modeiassub> getSub() {
		return Sub;
	}
	@Override
	public void setSub(List<Modeiassub> sub) {
		Sub = sub;
	}
	@Override
	public Element toxml() {
		Element elmode = new BaseElement("IAS");
		Element elmain=elmode.addElement("Main");
		for(Modeiasmain m:Main){
			Element elid=elmain.addElement("ID");
			elid.addAttribute("MID", m.getId()+"");
//			elid.addAttribute("Device", m.getModeEpId()+"");
			if (m.getModeEpOldId() == 0) {
				elid.addAttribute("Device", m.getModeEpId()+"");
			} else {
				elid.addAttribute("Device", m.getModeEpOldId()+"");
			}
			elid.addAttribute("bActive", m.getBactive()+"");	
			elid.addAttribute("bAllmodeActive", m.getBallmodeActive()+"");
			elid.addAttribute("attrlibId", m.getAttrlibId()+"");
		}
		Element elsub=elmode.addElement("Sub");	
		for(Modeiassub u:Sub){
			Element elid=elsub.addElement("ID");
			elid.addAttribute("SubID", u.getId()+"");			
			elid.addAttribute("MID", u.getMid()+"");		
			elid.addAttribute("Act", "ApplyMacro");			
			elid.addAttribute("DelaySec", "0");			
			elid.addAttribute("Dest", u.getDest()+"");	
			elid.addAttribute("DestType", "1");	
			elid.addAttribute("Para", "");
			elid.addAttribute("CIEStatus", u.getCiestatus()+"");	
			elid.addAttribute("ZoneActType", u.getZoneActType()+"");				
		}
		return elmode;
	}
	
	static public Xmlias fromxml(Document doc,long houseId){
		List<Modeiasmain> mains=new ArrayList<Modeiasmain>();
		List<Modeiassub> subs=new ArrayList<Modeiassub>();
		List tableList = doc.selectNodes("//IAS/Main");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//IAS/Main/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modeiasmain main=new Modeiasmain();
    	         Long mainId = Long.parseLong(column.attributeValue("MID"));
    	         main.setMainId(mainId);
    	         main.setOldId(mainId);
    	         main.setModeEpId(Long.parseLong(column.attributeValue("Device")));
    	         main.setModeEpOldId(Long.parseLong(column.attributeValue("Device")));
    	         main.setBactive(Short.parseShort(column.attributeValue("bActive")));   
    	         if (column.attributeValue("bAllmodeActive") == null || column.attributeValue("bAllmodeActive").equals("")) {
    	        	 main.setBallmodeActive(Short.parseShort("0"));
    	         } else {
        	         main.setBallmodeActive(Short.parseShort(column.attributeValue("bAllmodeActive")));
    	         }
    	         if(null!=column.attributeValue("attrlibId"))
    	        	 main.setAttrlibId(Long.parseLong(column.attributeValue("attrlibId")));
    	         else main.setAttrlibId(68L);
    	         String text = column.getTextTrim();
    	         mains.add(main);
    	      }
    	   }
    	}
        tableList = doc.selectNodes("//IAS/Sub");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//IAS/Sub/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modeiassub sub=new Modeiassub();
    	         sub.setId(Long.parseLong(column.attributeValue("SubID")));
    	         sub.setMid(Long.parseLong(column.attributeValue("MID")));
    	         sub.setAct(column.attributeValue("Act"));
    	         sub.setDest(Long.parseLong(column.attributeValue("Dest")));   
    	         sub.setCiestatus(Short.parseShort(column.attributeValue("CIEStatus"))); 
    	         sub.setZoneActType(Short.parseShort(column.attributeValue("ZoneActType")));    	         
    	         String text = column.getTextTrim();
    	         subs.add(sub);
    	      }
    	   }
    	}		
        Xmlias xmlias=new Xmlias();
        xmlias.setMain(mains);
        xmlias.setSub(subs);    	
    	return xmlias;		
	}		
}
