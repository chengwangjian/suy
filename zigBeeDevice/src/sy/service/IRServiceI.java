package sy.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;

import sy.model.IRBrand;
import sy.model.IRBranddictionary;
import sy.model.IRTempData;

/**
 * @author huanglw  IR数据服务接口
 *
 */
public interface IRServiceI {

	/**
	 * 根据IR数据获取品牌、型号列表
	 * @param IRData
	 * @return
	 * @throws Exception
	 */
	public List<Map> abtainIRBrandModels(String IRData, Integer irType,String houseIeeeSecret) throws Exception;
	
	/**
	 * 获取品牌列表
	 * @param irType IR类型Id
	 * @param brandName 品牌名称
	 * @return
	 * @throws Exception
	 */
	public List<Map> abtainBrand(Integer irType, String brandName) throws Exception;
	
	/**
	 * 获取型号列表
	 * @param brandId 品牌id
	 * @param brandName 品牌名称
	 * @return
	 * @throws Exception
	 */
	public List<Map> abtainModel(Integer brandId, String brandName) throws Exception;
	
	/**
	 * 获取IR数据
	 * @param brandId 品牌id
	 * @param modelId 型号id
	 * @return
	 * @throws Exception
	 */
	public String abtainIRXmlData(String brandName, String modelName) throws Exception;
	
	/**
	 * 根据品牌名称或型号名称获取品牌及型号列表
	 * @param name 品牌名称或型号名称
	 * @return
	 * @throws Exception
	 */
	public List<Map> abtainIRBrandModelsByName(String name, Integer irType) throws Exception;
	
	/**
	 * 创建ir索引数据表
	 * @param tableName
	 * @throws Exception
	 */
	public int createIrIndexDataTable(String tableName) throws Exception;
	
	/**
	 * 解析IR数据
	 * @param param 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public int saveIRxml(Document doc, String userId, String checked, int fileSize, Map<String, Object> param) throws Exception;
	
	/**
	 * 判断该品牌、型号是否存在
	 * @param param 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> isModelExist(Document doc, Map<String, Object> param) throws Exception;
	
	/**
	 * 删除该品牌、型号的data数据
	 * @param brandName
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public int deleteIrData(Document doc, String brandName, String modelName) throws Exception;
	
	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public int isTableExist(String tableName) throws Exception;
	
	/**
	 * 品牌是否存在
	 * @param brandName
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> isBrandExist(String brandName) throws Exception;
	
	/**
	 * 更新下载次数
	 * @param dlTimes
	 * @return
	 * @throws Exception
	 */
	public int updateModeldlTimes(Integer dlTimes, String modelId) throws Exception;
	
	/**
	 * 删除型号对
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public int deleteModelAndIRData(String modelId) throws Exception;
	
	/**
	 * 查询条数
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	public int getIRCount(String searchText) throws Exception;
	
	/**
	 * 查询列表
	 * @param searchText
	 * @param startRow
	 * @param pageSize
	 * @param orderBy
	 * @return
	 */
	public List<Map> queryIR(String searchText, int startRow, int pageSize, String orderBy);
	
	/**
	 * 更新模式表的是否验证
	 * @param checked
	 * @param userId
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public int updateModeldlCheck(String checked, String userId, String modelId) throws Exception;
	
	/**
	 * IR库下载
	 * @param modelId 型号id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String IRdown(Long modelId, HttpServletRequest request,HttpServletResponse  response) throws Exception;
	/**
	 * 编辑ir库品牌、型号、类型、英文品牌
	 * @param irbrand
	 * @return
	 * @throws Exception
	 */
	public int update(Map<String, Object> irbrand) throws Exception;
	/**
	 * 根据某个品牌获取poweron的所有型号
	 * @param irType
	 * @param brandName
	 * @return
	 * @throws Exception
	 */
	public List<Map> getPowerOn(Integer irType,String brandName,int pageIndex, int pageSize) throws Exception;
	/**
	 * IR实现分包返回品牌列表
	 * @param irType
	 * @param brandName
	 * @param startRow
	 * @param pageSize
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<Map> getBrandList(Integer irType,int pageIndex, int pageSize) throws Exception;
	/**
	 * 通过某个品牌获取所有的型号的IR数据
	 * @param brandId
	 * @param brandName
	 * @return
	 * @throws Exception
	 */
	public List<Map> getModelList(Integer irType,String brandName,int pageIndex, int pageSize) throws Exception;
	/**
	 * 返回品牌列表
	 * @param irbrand
	 * @return
	 * @throws Exception
	 */
	public List<IRBrand> findbrand(IRBrand irbrand,IRBranddictionary irbranddictionary) throws Exception;
	
	/**
	 * 导入时读取xml文件部分信息显示
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> showIRxml(Document doc) throws Exception;
	/**
	 * 根据条件返回所有的品牌列表，查询参数包含过滤条件（过滤条件包含IRType，品牌名称）、包序号，
       品牌名称为模糊查询方式，如果为空的话，返回所有符合IRType条件的品牌列表
	 * @param irType
	 * @param brandName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Map> getBrandByIRType(Integer irType,String brandName,int pageIndex, int pageSize) throws Exception;
	/**
	 * 编辑显示接口，不能共用
	 * @param irbrand
	 * @return
	 * @throws Exception
	 */
	public List<IRBrand> findbrandedit(IRBrand irbrand) throws Exception;
	/**
	 * 未解码的IR数据保存
	 * @param irMatch
	 * @throws Exception
	 */
	public void addUnMatch(String houseIeee,String ieee,String ep,Integer hadaemonIrindex,String data,String createtime) throws Exception;
	
	/**
	 * IR定时保存
	 */
	public int dingshiaddUnMatch();
	
	/**
	 * 上传文件到临时库
	 * @param 
	 * @return 该文件的唯一标识 
	 */
	public String uploadIRxml(IRTempData irTmpData) throws Exception;
	
	/**
	 * 下载分享的IR文件
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String downloadIRxml(Map<String, Object> params) throws Exception;
	
	/**
	 * 获取IR临时库总数
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	public int getIRTempCount(String searchText) throws Exception;
	
	/**
	 * 分页获取IR临时库数据
	 * @param searchText
	 * @param startRow
	 * @param pageSize
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryIRTemp(String searchText, int startRow, int pageSize, String orderBy) throws Exception;
	
	/**
	 * IRTempData的主键id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteIRTemp(String id) throws Exception;
	
	/**
	 * IR临时库认证通过
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int passIRTemp(String id, String userId) throws Exception;
}
