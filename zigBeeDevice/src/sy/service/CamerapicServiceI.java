package sy.service;

import java.util.List;
import java.util.Map;

public interface CamerapicServiceI {
	/**
	 * 保存记录
	 * @return
	 * @throws Exception
	 */
	public int saveLog(Map map, String path, String type);

	/**
	 * 查询总数可以用于哪个时间段的查询
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	 public abstract List<Map> getPicListCount(Map<String, Object> paramMap);
	//public abstract List<Map> getPicListCount(Map<String, Object> conditionMap);
	//public int getPicListCount(Map<String, Object> paramMap);
	/**
	 * 查询数量可以用于哪个时间段的查询
	 * @return
	 * @throws Exception
	 */
	//public List<Map> getPicList(String searchText, int pageSize, int startRow) throws Exception;
	public abstract List<Map> getPicList(String paramString1, String paramString2, String paramString3, Map<String, Object> paramMap);
	 /**
		 * 上传文件到临时库
		 * @param 
		 * @return 该文件的唯一标识 
		 */
	/*public String uploadPic(Picupload picUpload) throws Exception;*/
		
		/**
		 * 查询总数1
		 * @param searchText
		 * @return
		 * @throws Exception
		 */
	public long getPicLogCount1(String searchText) throws Exception;
		/**
		 * 查询数量1
		 * @return
		 * @throws Exception
		 */
	public List<Map> getPicLogs(String searchText, int pageSize, int startRow) throws Exception;
	
	/**
	 * 判断上传图片是否存在
	 * @param userName 用户名
	 * @return
	 * @throws Exception
	 */
	public boolean isUserExist(String picname) throws Exception;

}

