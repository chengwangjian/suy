package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import sy.model.Energyreferencelibrary;

public interface EnergyreferencelibraryServiceI {

	public Energyreferencelibrary save(Energyreferencelibrary energyreferencelibrary);
	
	public Energyreferencelibrary get(Energyreferencelibrary energyreferencelibrary);
	
	public Energyreferencelibrary getById(long id);
	
	public int saveSql(Energyreferencelibrary energyreferencelibrary,String language);
	
	public int saveSqlLib(Energyreferencelibrary energyreferencelibrary);
	
	public Energyreferencelibrary getExisit(Energyreferencelibrary energyreferencelibrary);
	
	public Energyreferencelibrary update(Energyreferencelibrary energyreferencelibrary);
	
	public List<Map> findList(Map amp);
	
	public List<Map> findListFieldTime(Energyreferencelibrary energyreferencelibrary);
	
	public Map findListFieldTimeLib(Map map);
	
	public int delete(Map map);

//	public DataGrid datagrid(Energyreferencelibrary energyreferencelibrary);

	public void remove(String ids);
	
	public void test();	

}
