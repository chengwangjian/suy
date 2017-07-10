package org.smarthome.services;

import java.util.List;
import java.util.Map;

import org.smarthome.domain.Hardhistory;

public interface HardhistoryServiceI {

	public Hardhistory keyUpdate(Hardhistory update);

	public Hardhistory saveOrUpdate(Hardhistory update);
	
	public Hardhistory delete(Hardhistory update);
	
	public int getCount(Map<String, Object> update);
	public List<Hardhistory> findList(Map<String, Object> update);
    //hardhistory删除
	public int deletehardhistory(String hId);
}
