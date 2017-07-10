package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import sy.model.Modehouse;
import sy.model.Moderoom;
import sy.pageModel.ModehouseAndRoom;

public interface ModehouseServiceI {

	public Modehouse save(Modehouse modehouse);
	
	public int updateModehouseAndRoom(ModehouseAndRoom modehouseAndRoom);
	
	public int addModehouseAndRoom(ModehouseAndRoom modehouseAndRoom);
	
	public Modehouse find(Modehouse modehouse);
	
	public List<Modehouse> findList(Modehouse modehouse);
	
	public List<Object[]> findListSql(Modehouse modehouse);

	public Modehouse login(Modehouse modehouse);
	
	public Modehouse get(Modehouse modehouse);
	
	public Moderoom getModeroom(Moderoom moderoom);
	
	/**
	 * 判断家是否存在
	 * @author: zhuangxd
	 * 时间：2014-2-18 上午11:36:19
	 * @param modehouse
	 * @return
	 */
	public Modehouse getModehouse(Modehouse modehouse);
	
	public Modehouse update(Modehouse modehouse);
	
	public Map findModehouse(ModehouseAndRoom modehouseAndRoom);
	
	public int delete(Modehouse modehouse);
	
	public int deleteModeroom(Moderoom moderoom);
	
	public Map findModehouseAndRoom(ModehouseAndRoom modehouseAndRoom);

//	public DataGrid datagrid(Modehouse modehouse);

	public void remove(String ids);
	
	public void test();	
	
	public int hasDevice(Long houseId, Long roomId);
}
