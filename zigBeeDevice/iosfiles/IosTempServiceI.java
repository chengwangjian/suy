package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.IosTemp;

public interface IosTempServiceI {
	public void saveIosTemp(IosTemp iosTemp) throws Exception;
	public int getIosTempCount() throws Exception;
	public List<Map> getIosTemps(int pageSize, int startRow) throws Exception;
	public void deleteIosTemp(Integer id) throws Exception;
}
