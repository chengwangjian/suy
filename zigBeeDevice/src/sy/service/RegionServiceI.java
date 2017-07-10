package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import sy.model.Region;

public interface RegionServiceI {

	public Region save(Region region);
	
	public Region get(Region region);
	
	public Region getExisit(Region region);
	
	public Region update(Region region);
	
	public List<Map> findList(Region region,String type);
	
	public int delete(Region region);

//	public DataGrid datagrid(Region region);

	public void remove(String ids);
	
	public void test();	
	
	public List<Map> findRegionList(Region region);

}
