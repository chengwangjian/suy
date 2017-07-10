package zbHouse.service;

import java.util.Map;

import zbHouse.model.Mode;
import zbHouse.pageModel.DataGrid;

public interface ModeServiceI {

	public Mode keyUpdate(Mode mode);

	public Mode saveOrUpdate(Mode mode,Map<String, Object> params);
	
	public int delete(Map<String, Object> params);
	
	public DataGrid find(Map<String, Object> params);	
	/**
	 * Mode定时保存
	 */
	public int dingshiSave() ;
}
