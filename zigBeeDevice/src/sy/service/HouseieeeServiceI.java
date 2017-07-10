package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import sy.model.Houseieee;

public interface HouseieeeServiceI {

	public Houseieee save(Houseieee houseieee);
	
	public Houseieee find(Houseieee houseieee);
	
	public List<Houseieee> findList(Houseieee houseieee);
	
	public List<Object[]> findListSql(Houseieee houseieee);

	public Houseieee login(Houseieee houseieee);
	
	public Houseieee isFilter(Houseieee houseieee);
	
	public Houseieee get(Houseieee houseieee);
	
	public Houseieee update(Houseieee houseieee);
	
	public int delete(Houseieee houseieee);
	
	public int addDeviceAttributeHistoryTable(String houseieeeIeee);
	
	public int addDeviceWarnHistoryTable(String houseieeeIEEE);
	
	public int addDeviceOperateHistoryTable(String houseieeeIEEE);

//	public DataGrid datagrid(Houseieee houseieee);

	public void remove(String ids);
	
	public void test();

	public Houseieee get1(Houseieee houseieee);

	public int delete1(Houseieee houseieee);

	public List<Map> getReliClientByRegion(Map<String ,Object> map,Object object);	

}
