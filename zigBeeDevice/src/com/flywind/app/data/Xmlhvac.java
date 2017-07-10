package com.flywind.app.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

import com.flywind.app.dal.StartDAO;
import com.flywind.app.entities.Modehvacmain;
import com.flywind.app.entities.Modehvacsub;
import com.flywind.app.entities.Modemainclause;
import com.flywind.app.services.QueryParameters;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("HVAC")
public class Xmlhvac implements Interface3<Modehvacmain,Modehvacsub,Modemainclause>{

	private List<Modehvacmain> Main;
	private List<Modehvacsub> Sub;
	private List<Modemainclause> modemainclauses;
	private StartDAO dao;
	private Long houseId;
	
	public Xmlhvac() {
		
	}
	
	public Xmlhvac(StartDAO daoTmp, Long houseTmp) {
		this.dao = daoTmp;
		this.houseId = houseTmp;
	}
	
	public Long getHouseId() {
		return houseId;
	}
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}
	
	public StartDAO getDao() {
		return dao;
	}
//    @Autowired
	public void setDao(StartDAO dao) {
		this.dao = dao;
	}
	private int checked=0;	
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	@Override
	public List<Modehvacmain> getMain() {
		return Main;
	}
	@Override
	public void setMain(List<Modehvacmain> main) {
		Main = main;
	}
	@Override
	public List<Modehvacsub> getSub() {
		return Sub;
	}
	@Override
	public void setSub(List<Modehvacsub> sub) {
		Sub = sub;
	}
	
	@Override
	public List<Modemainclause> getModemainclauses() {
		return modemainclauses;
	}
	@Override
	public void setModemainclauses(List<Modemainclause> modemainclauses) {
		this.modemainclauses = modemainclauses;
	}
	@Override
	public Element toxml() {
		Element elmode = new BaseElement("HVAC");
		Element elmodemain = elmode.addElement("Main_clause");
		List<Modemainclause> Modemainclause=dao.find("Select m from Modemainclause m where m.houseId=:houseId", QueryParameters.with("houseId", houseId).parameters());
		Element elmain=elmode.addElement("Main");
		for(Modemainclause mc:Modemainclause){
			Element elmodemainid=elmodemain.addElement("ID");
			elmodemainid.addAttribute("MID", mc.getId()+"");
			elmodemainid.addAttribute("bActive", mc.getBactive()+"");
			elmodemainid.addAttribute("bAllmodeActive", mc.getBallmodeActive()+"");
		}
		for(Modehvacmain m:Main){
			Element elid=elmain.addElement("ID");
			elid.addAttribute("SubID", m.getId()+"");
			elid.addAttribute("MID", m.getMainId()+"");
//			elid.addAttribute("Device", m.getModeEpId()+"");
			if (m.getModeEpOldId() == 0) {
				elid.addAttribute("Device", m.getModeEpId()+"");
			} else {
				elid.addAttribute("Device", m.getModeEpOldId()+"");
			}
			elid.addAttribute("Attr", "ClusterID="+m.getClusterId()+",AttrID="+m.getAttrId()+",MinValue="+m.getMinValues()+",MaxValue="+m.getMaxValues());
			elid.addAttribute("bActive", m.getBactive()+"");
			elid.addAttribute("ClusterID",m.getClusterId()+"");
			elid.addAttribute("AttrID",m.getAttrId()+"");
			elid.addAttribute("MinValue",m.getMinValues()+"");
			elid.addAttribute("MaxValue",m.getMaxValues()+"");
			elid.addAttribute("bAllmodeActive", m.getBallmodeActive()+"");	
			elid.addAttribute("attrlibId", m.getAttrlibId()+"");
			elid.addAttribute("clause", m.getClause()+"");
			if (m.getCondition2() == null) {
				elid.addAttribute("condition2", "");
			} else {
				elid.addAttribute("condition2", m.getCondition2());
			}
			if (Modemainclause == null || Modemainclause.size() == 0) {
				Element elmodemainid=elmodemain.addElement("ID");
				elmodemainid.addAttribute("MID", m.getMainId()+"");
				elmodemainid.addAttribute("bActive", "1");
				elmodemainid.addAttribute("bAllmodeActive", "0");
			}
		}
		List<Map> mList=dao.executeSql("SELECT u.* FROM	modehvacsub u WHERE u.houseId =:houseId ORDER BY u.id", QueryParameters.with("houseId", houseId).parameters());
		
		Element elsub=elmode.addElement("Sub");	
		for(int i = 0; i<mList.size(); i++){
			Element elid=elsub.addElement("ID");
			elid.addAttribute("SubID", mList.get(i).get("ID")+"");			
			elid.addAttribute("MID", mList.get(i).get("MID")+"");		
			elid.addAttribute("ActType",mList.get(i).get("ActType")+"");			
			elid.addAttribute("Act", "ApplyMacro");				
			elid.addAttribute("Dest", mList.get(i).get("Dest")+"");		
			elid.addAttribute("DurationSec", mList.get(i).get("DurationSec")+"");		
			elid.addAttribute("ResumeAct", "ApplyMacro");		
			elid.addAttribute("ResumeDest", mList.get(i).get("ResumeDest")+"");	
//			elid.addAttribute("DelaySec", mList.get(i).get("DelaySec2")+"");
			elid.addAttribute("DelaySec", "0");
		}
//		for(Modehvacsub u:Sub){
//			Element elid=elsub.addElement("ID");
//			elid.addAttribute("SubID", u.getId()+"");			
//			elid.addAttribute("MID", u.getMid()+"");		
//			elid.addAttribute("ActType", u.getActType()+"");			
//			elid.addAttribute("Act", "ApplyMacro");				
//			elid.addAttribute("Dest", u.getDest()+"");		
//			elid.addAttribute("DurationSec", u.getDurationSec()+"");		
//			elid.addAttribute("ResumeAct", "ApplyMacro");		
//			elid.addAttribute("ResumeDest", u.getResumeDest()+"");	
//		}
		return elmode;
	}
	static public Xmlhvac fromxml(Document doc,long houseId,String xmlVersion){
		List<Modehvacmain> mains=new ArrayList<Modehvacmain>();
		List<Modemainclause> modemainclauses=new ArrayList<Modemainclause>();
		List<Modehvacsub> subs=new ArrayList<Modehvacsub>();
		List tableList2 = doc.selectNodes("//HVAC/Main_clause");
		 for (Object tableObj : tableList2 ) {
	    	   Element table = (Element) tableObj;
	    	   if(null != table){
	    	      List columnList = table.getDocument().selectNodes("//HVAC/Main_clause/ID");
	    	      for(Object columnObj : columnList){
	    	         Element column= (Element) columnObj;
	    	         Modemainclause main=new Modemainclause();
	    	         main.setOldId(Long.parseLong(column.attributeValue("MID")));
	    	         main.setBactive(Short.parseShort(column.attributeValue("bActive")));
	    	         main.setBallmodeActive(Short.parseShort(column.attributeValue("bAllmodeActive")));
	    	         String text = column.getTextTrim();
	    	         modemainclauses.add(main);
	    	      }
	    	   } 
	    }

		List tableList = doc.selectNodes("//HVAC/Main");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//HVAC/Main/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         if (!("1.0.1.5_150123_release").equals(xmlVersion)) {
    	        	 Modemainclause main2=new Modemainclause();
        	         main2.setOldId(Long.parseLong(column.attributeValue("MID")));
        	         main2.setBactive(Short.parseShort("1"));
        	         main2.setBallmodeActive(Short.parseShort("0"));
        	         modemainclauses.add(main2); 
    	         }
    	         Modehvacmain main=new Modehvacmain();
    	         main.setOldId(Long.parseLong(column.attributeValue("MID")));
    	         main.setMainId(Long.parseLong(column.attributeValue("MID")));
    	         main.setModeEpId(Long.parseLong(column.attributeValue("Device")));
    	         main.setModeEpOldId(Long.parseLong(column.attributeValue("Device")));
    	         main.setAttributes(column.attributeValue("Attr"));    	         
    	         main.setBactive(Short.parseShort(column.attributeValue("bActive")));
    	         if (StringUtils.isBlank(column.attributeValue("clause"))) {
                     main.setClause(Short.parseShort("0"));
    	         } else {
                     main.setClause(Short.parseShort(column.attributeValue("clause")));
    	         }
    	         if (column.attributeValue("condition2") == null) {
                     main.setCondition2("1");
    	         } else {
                     main.setCondition2(column.attributeValue("condition2"));
    	         }
    	         main.setClusterId(column.attributeValue("ClusterID"));
    	         main.setAttrId(column.attributeValue("AttrID"));
    	         main.setMinValues(Double.parseDouble(column.attributeValue("MinValue")));
    	         main.setMaxValues(Double.parseDouble(column.attributeValue("MaxValue")));
    	         main.setBallmodeActive(Short.parseShort(column.attributeValue("bAllmodeActive")));
    	         main.setAttrlibId(Long.parseLong(column.attributeValue("attrlibId")));
    	         String text = column.getTextTrim();    	         
    	         mains.add(main);
    	      }
    	   }
    	}
        tableList = doc.selectNodes("//HVAC/Sub");
        for (Object tableObj : tableList ) {
    	   Element table = (Element) tableObj;
    	   if(null != table){
    	      List columnList = table.getDocument().selectNodes("//HVAC/Sub/ID");
    	      for(Object columnObj : columnList){
    	         Element column= (Element) columnObj;
    	         Modehvacsub sub=new Modehvacsub();
    	         sub.setId(Long.parseLong(column.attributeValue("SubID")));
    	         sub.setMid(Long.parseLong(column.attributeValue("MID")));
    	         sub.setActType(Long.parseLong(column.attributeValue("ActType")));
    	         sub.setAct(column.attributeValue("Act"));    	         
    	         sub.setDest(Long.parseLong(column.attributeValue("Dest")));   
    	         sub.setDurationSec(Long.parseLong(column.attributeValue("DurationSec"))); 
    	         sub.setResumeAct(column.attributeValue("ResumeAct"));    	         
    	         sub.setResumeDest(Long.parseLong(column.attributeValue("ResumeDest")));
    	         if (StringUtils.isBlank(column.attributeValue("DelaySec"))) {
        	         sub.setDelaySec(Long.parseLong("0"));
    	         } else {
        	         sub.setDelaySec(Long.parseLong(column.attributeValue("DelaySec")));
    	         }
    	         String text = column.getTextTrim();
    	         subs.add(sub);
    	      }
    	   }
    	}		
        Xmlhvac xmlhvac=new Xmlhvac();
        xmlhvac.setModemainclauses(modemainclauses); 
        xmlhvac.setMain(mains);
        xmlhvac.setSub(subs);    	
    	return xmlhvac;			
	}
}
