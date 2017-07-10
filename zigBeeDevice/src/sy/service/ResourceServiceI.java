package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.Resource;

public interface ResourceServiceI {
	/**
	 * 保存记录
	 * @param savaupload()是在上传的时候将上传资源的名称重新命名
	 * @param savaupload1()是上传资源名称不变
	 * @return
	 * @throws Exception
	 */
	  public int appupload(Resource resource,String path, String type, long length, String uuid, String name1) throws Exception;
	  public int savaupload(Map map, String path, String type, long length, String uuid, String name1);
	  
	/**
	 * 查询总数可以用于哪个时间段的查询
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	public abstract List<Map> getListCount(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询数量可以用于哪个时间段的查询
	 * @return
	 * @throws Exception
	 */
	//public List<Map> getPicList(String searchText, int pageSize, int startRow) throws Exception;
	public abstract List<Map> getList(int paramString1, int paramString2, String paramString3, Map<String, Object> paramMap);
	
	/**
	 * 查询数量1
	 * @return
	 * @throws Exception
	 */
	public List<Map> getLogs(String searchText, int pageSize, int startRow) throws Exception;
	
	/**
	 * 根据uui资源查找下载名称
	 * @param uuid 资源唯一编码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<Map> getresList(Map map) throws Exception;
	
	/**
	 * 根据resname进行删除上传资源过程的文件
	 * @param resname 资源唯一编码
	 * @return
	 * @throws Exception
	 */
	public void deleteres() throws Exception;
	/**
	 * 根据resname判断上传文件是否存在不存在就行删除
	 * @param resname
	 * @return
	 * @throws Exception
	 */
	public void deletepath() throws Exception;
	/**
	 * 根据resname判断上传的图片文件是否完整没有出错如果出错就删除
	 * @param resname
	 * @return
	 * @throws Exception
	 */
	public void deletepic() throws Exception;
	
	//public void dropresource(Map params) throws Exception;
	/**
	 * 根据resname对上传的资源进行删除
	 * @param resname
	 * @return
	 * @throws Exception
	 */
	public int dropres(String resname) throws Exception;


}
