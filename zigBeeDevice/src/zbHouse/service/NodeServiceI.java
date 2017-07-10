package zbHouse.service;

import java.util.Map;

import zbHouse.model.Node;
import zbHouse.pageModel.DataGrid;

public interface NodeServiceI {

	public Node keyUpdate(Node node);

	public Node saveOrUpdate(Node node,Map<String, Object> params);
	
	public int delete(Map<String, Object> params);
	
	public DataGrid find(Map<String, Object> params);	
	/**
	 * node定时保存
	 */
	public int dingshiSave();
}
