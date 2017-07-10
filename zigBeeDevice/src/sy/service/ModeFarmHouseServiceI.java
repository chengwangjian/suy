package sy.service;

import java.util.Map;

import sy.model.House;
import sy.model.ModeFarmHouse;

public interface ModeFarmHouseServiceI {
	
	public int update(Map<String, Object> map);
	public House get(Map<String, Object> map);
	
}
