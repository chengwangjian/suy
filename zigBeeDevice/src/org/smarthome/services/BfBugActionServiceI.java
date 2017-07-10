package org.smarthome.services;

import java.util.List;
import java.util.Map;

import org.smarthome.domain.BfBugAction;

public interface BfBugActionServiceI {

	public BfBugAction keyUpdate(BfBugAction update);

	public BfBugAction saveOrUpdate(BfBugAction update);
	
	public BfBugAction delete(BfBugAction update);
	
//	public int getCount(Map<String, Object> update);
	public List<BfBugAction> findList(Map<String, Object> update);

}
