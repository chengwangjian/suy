package org.smarthome.services;

import java.util.List;
import java.util.Map;

import org.smarthome.domain.BfBugInfo;
import sy.model.QueryParameter;

public interface BfBugInfoServiceI {

	public BfBugInfo keyUpdate(BfBugInfo update);

	public BfBugInfo saveOrUpdate(BfBugInfo update);
	
	public BfBugInfo delete(BfBugInfo update);
	
//	public int getCount(Map<String, Object> update);
	public List<BfBugInfo> findList(Map<String, Object> update);

	public void addCondition(QueryParameter parameter, String userid);

	public List<Map> findCondition(int userid);

	public void updateConditionmain(Map map);

	public void deleteCondition(Map map);

	public List<Map> findConditionSub(Map map);

	public List<Map> findappinfo(Map map);

}
