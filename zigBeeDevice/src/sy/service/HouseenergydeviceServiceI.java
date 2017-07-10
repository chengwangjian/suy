package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;

import sy.model.Houseenergydevice;

public interface HouseenergydeviceServiceI {

	public Houseenergydevice save(Houseenergydevice houseenergydevice);
	
	public Houseenergydevice get(Houseenergydevice houseenergydevice);
	
	public Houseenergydevice getExisit(Houseenergydevice houseenergydevice);
	
	public Houseenergydevice update(Houseenergydevice houseenergydevice);
	
	public List<Houseenergydevice> findList(Houseenergydevice houseenergydevice);
	
	public int delete(Houseenergydevice houseenergydevice);
	
	public int replaceDevice(Houseenergydevice houseenergydevice);

//	public DataGrid datagrid(Houseenergydevice houseenergydevice);

	public void remove(String ids);
	
	public void test();	

}
