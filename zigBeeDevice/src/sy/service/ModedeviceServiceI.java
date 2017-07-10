package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import com.flywind.app.data.Target;

import sy.model.Modedevice;
import sy.model.Modeeplib;
import sy.model.Modenode;
import sy.model.Modenodelib;
import sy.pageModel.ModeNodeAndDevice;

public interface ModedeviceServiceI {

	public Modedevice save(Modedevice modedevice);
	
	public Modedevice find(Modedevice modedevice);
	
	public List<Modedevice> findList(Modedevice modedevice);
	
	public List<Object[]> findListSql(Modedevice modedevice);

	public Modedevice login(Modedevice modedevice);
	
	public Modedevice get(Modedevice modedevice);
	
	public Modenode updateModenode(Modenode modenode);
	
	public Modedevice update(Modedevice modedevice);
	
	public Map findModeNodeLibDataByDeviceID(Modeeplib modeeplib);
	
	public Modenode getModenode2(Modenode modenode);
	
	public int updateNodeAndDeviceIeee(Modenode modenode);
	
	public Map findModeNodeLibDataByNodeType(Modenodelib modenodelib);
	
	public Modenode getModenode(Modenode modenode);
	
	public int insertDeviceToRoom2(ModeNodeAndDevice modeNodeAndDevice);
	
	public Map findModeEPLibDataByNode(Modeeplib modeeplib);
	
	public Map findModeNodeList(Modenode modenode,List<Integer> romidlias);
	
	public Map findModeNodeLibDataByNodeLibId(Modenodelib modenodelib);
	
	public Map findModeDeviceList(Modedevice modedevice);
	
	public Map findModeDeviceByNodeId(Modenode modenode);
	
	public Map findModeNodeList(Modenode modenode);
	
	public Modedevice getDevice(Modedevice modedevice);
	
	public int insertDeviceToRoom(Map map);
	
	public int insertDeviceToRoom(ModeNodeAndDevice modeNodeAndDevice);
	
	public int deleteModeNode(Modenode modenode);
	
	public int delete(Modedevice modedevice);
	
//	public DataGrid datagrid(Modedevice modedevice);

	public void remove(String ids);
	
	public void test();	

	public List<Map> modenodeCount(Map<String, Object> map) ;
	
	public List<Map> modedeviceCount(Map<String, Object> map) ;
	
	public int addEpPage(List<Map> list,String houseid) ;
	
	public Map findEpPage(Map<String, Object> map) ;
	
	public int addModePage(List<Map> list,String houseid) ;
	
    public Object findroom(Target tar);
	
}
