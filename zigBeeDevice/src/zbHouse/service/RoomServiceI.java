package zbHouse.service;

import java.util.Map;

import zbHouse.model.Room;
import zbHouse.pageModel.DataGrid;

public interface RoomServiceI {

	public Room keyUpdate(Room room);

	public Room saveOrUpdate(Room room,Map<String, Object> params);
	
	public int delete(Map<String, Object> params);
	
	public DataGrid find(Map<String, Object> params);	
	/**
	 * room定时保存
	 */
	public int dingshiSave();
}
