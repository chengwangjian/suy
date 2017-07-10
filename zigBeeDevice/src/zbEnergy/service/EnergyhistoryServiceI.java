package zbEnergy.service;

import java.util.Map;

import zbHouse.model.Device;
import zbHouse.pageModel.General;

public interface EnergyhistoryServiceI {

	public Device keyUpdate(Device device);

	public int saveOrUpdate(General js);
	
	public int delete(General js);
	
	public Map find(General js);

}
