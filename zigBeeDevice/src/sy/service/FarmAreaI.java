package sy.service;

import java.util.List;
import java.util.Map;

public interface FarmAreaI {
	public List<Map> getAreaList(String username) throws Exception;
	
	/**
	 * 获取区域接口
	 * @param userName
	 * @return
	 */
	public List<Map> getAreas(String userName, int pageIndex, int pageSize);
	
	/**
	 * 获取温室属性
	 * @param hIeees
	 * @return
	 */
	public Map<String, Map> getAreaAttrs(List<String> hIeees);
	
	/**
	 * 添加区域api
	 * 创建时间2017-07-4
	 * @author chengwangjian
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	
		
	public int JoinHouse(Map<String, Object> map, Map userMap) throws Exception;
}
