package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import sy.model.Modegrouplib;
import sy.model.Modenode;
import sy.pageModel.Modedevicebind2;

import com.flywind.app.entities.Modedevicebind;

public interface ModedevicebindServiceI {

	public Modedevicebind save(Modedevicebind modedevicebind);

	public Modedevicebind find(Modedevicebind modedevicebind);

	public List<Modedevicebind> findList(Modedevicebind modedevicebind);

	public List<Object[]> findListSql(Modedevicebind modedevicebind);

	public Modedevicebind login(Modedevicebind modedevicebind);

	public Modedevicebind get(Modedevicebind modedevicebind);

	public Modedevicebind update(Modedevicebind modedevicebind);

	public Map findBindableSourceDeviceList(Modedevicebind modedevicebind,String behavior);

	public int insertModedevicebind(Modedevicebind2 modedevicebind2);

	public Map findBindAbleVirtualEPList(Modegrouplib modegrouplib);

	public Map findBindAbleDestDeviceList(Modedevicebind modedevicebind,String language,String behavior);

	public Map findBindList(Modedevicebind modedevicebind,String language);

	public int deleteModeNode(Modenode modenode);

	public int delete(Modedevicebind modedevicebind);

	// public DataGrid datagrid(Modedevicebind modedevicebind);

	public void remove(String ids);

	public void test();

	public Map findBind(Modedevicebind modedevicebind);
	
	public int updateBind(Modedevicebind modedevicebind) ;
	
	public Modedevicebind getDeviceBind(Modedevicebind modedevicebind);
}
