package zbEnergy.service;

import java.util.Map;

import zbHouse.model.Device;
import zbHouse.pageModel.Analysis;

public interface AnalysisServiceI {

	public Device keyUpdate(Device device);

	public Map findAll(Analysis analysis,Map<String, Object> params);
	
	public Map findEach(Analysis analysis,Map<String, Object> params);

	public Map findRegion(Analysis analysis,Map<String, Object> params);
	public Map temperature_hum_valuelist(Map<String, Object> params);
}
