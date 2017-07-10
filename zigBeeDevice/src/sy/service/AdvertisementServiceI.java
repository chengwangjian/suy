package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.Advertisement;

public interface AdvertisementServiceI {
	
	public List<Advertisement> find(Advertisement advertisement);
	
	public int update(Advertisement advertisement);
	
	public int delete(Advertisement advertisement);
	
	public List<Advertisement> find1(String startRow, String pageSize, String orderBy, Advertisement advertisement);
	
	public Advertisement add(Advertisement advertisement); 
	
	public void addbatchapp(List<Advertisement> list) throws Exception;

	public int appupdatereadstate(Advertisement advertisement);
	
	public int getCount(Map<String, Object> update);
}
