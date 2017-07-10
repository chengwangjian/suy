package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import sy.model.Deviceattribute;

public interface DeviceattributeServiceI {

	public Deviceattribute save(Deviceattribute deviceattribute);
	
	public Deviceattribute get(Deviceattribute deviceattribute);
	
	public Deviceattribute update(Deviceattribute deviceattribute);
	
	public List<Deviceattribute> findList(Deviceattribute deviceattribute);
	
	public int addEnergyhistory(Deviceattribute deviceattribute);
	
	public int addEnergyhistoryBatch(List<Deviceattribute> attrList);
	
	public int addEnergyhistoryTest(Deviceattribute deviceattribute);
	
//	public int saveDeviceattribute(final Map<String, List<Deviceattribute>> tmpList,int cacheCount);
	
	public int delete(Deviceattribute deviceattribute);

//	public DataGrid datagrid(Deviceattribute deviceattribute);

	public void remove(String ids);
	
	public void test();	

//	public int saveCache(Map<String, List<Deviceattribute>> tmpList,int cacheCount);
	
	public int saveDeviceattribute(Deviceattribute deviceattribute,List<Deviceattribute> deviceAttrList);
	/**
	 * 设备属性定时保存
	 */
	public void saveAttrCache();
	
	public void saveDeviceAttr(Deviceattribute deviceattribute) throws Exception;
	public void saveDeviceAttrBatch(List<Deviceattribute> attrList) throws Exception;
}
