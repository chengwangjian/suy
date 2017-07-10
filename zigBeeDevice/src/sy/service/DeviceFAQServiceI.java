package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.DeviceFAQ;

/**
 * 设备型号常见问题和解答的接口
 * @author HuangJX
 *
 */
public interface DeviceFAQServiceI {

	/**
	 * 添加设备型号常见的问题及其解答方式
	 * @param deviceFaq
	 * @return
	 * @throws Exception
	 */
	public int addFAQ(DeviceFAQ deviceFaq) throws Exception;
	
	
	/**
	 * 删除设备型号常见的问题及其解答方式
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	public int updateFAQ(DeviceFAQ deviceFaq,String modelNoOld) throws Exception;
	
	
	/**
	 * 修改编辑设备型号或者常见的问题及其解答方式
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	public int deleteFAQ(String modelNo) throws Exception;
	
	
	/**
	 * 获取所有的设备型号的问题/解决方案的总数
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @param searchText      查询的关键字
	 * @return
	 * @throws Exception
	 */
	public long getFAQCount(Map<String,Object> params,String searchText) throws Exception;
	
	
	/**
	 * 获取设备型号问题/解决方式的列表
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @param searchText      查询关键字
	 * @return
	 * @throws Exception
	 */
	public List<Map> getFAQList(String startRow,String pageSize,String searchText) throws Exception;
	
	
	/**
	 * 通过设备型号获取设备信息
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	public List<Map> getFAQByModelNo(String modelNo) throws Exception;
	
	/**
	 * 添加设备问题信息
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	public int add(Map params,String createTime, String lastTime) throws Exception;
	
	/**
	 * 更新设备问题信息
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	public int updatedeviceFAQ(String modelNo, String descriptionCn, String descriptionEn,String lastTime) throws Exception;
	
	/**
	 * 获取设备问题信息
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	public List<Map> getModelList(Map params) throws Exception;
	

	/**
	 * 获取设备问题信息
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	public List<Map> getModelList2(Map params) throws Exception;
	
}
