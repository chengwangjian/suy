package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.FarmWarnHandle;

public interface FarmWarnHandleServiceI {
    public List<FarmWarnHandle> find(Map<String,Object> jsonMap);
   
	public int update(FarmWarnHandle farmWarnHandle);
	
	public int delete(FarmWarnHandle farmWarnHandle);
	
	public List<FarmWarnHandle> find1(String startRow, String pageSize, String orderBy, FarmWarnHandle farmWarnHandle);
	
	public FarmWarnHandle add(FarmWarnHandle farmWarnHandle); 
	
	public void addbatchapp(List<FarmWarnHandle> list) throws Exception;

	public int appupdatereadstate(FarmWarnHandle farmWarnHandle);
	
	public int getCount(Map<String, Object> update);

	int update(Map<String,Object> paramter);

}
