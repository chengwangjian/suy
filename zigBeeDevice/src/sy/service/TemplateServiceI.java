package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.DeviceTemplateRel;


/**
 * @author huanglw  模板设备服务接口
 *
 */
public interface TemplateServiceI {

	/**
	 * 保存模板
	 * 请求参数格式:
	 * 新增
	 * {
	 * "id":-1,
	 * "templateName":"aa",
	 * 	"actions":[
	 * 		{
	 * 		"id":-1,
	 *  	"actionName":"开",
	 *  	"funcNum":"1"
	 * 		}
	 * 		...
	 * 	]
	 * }
	 * 编辑
	 * {
	 * "id":10,
	 * "templateName":"aa",
	 * 	"actions":[
	 * 		{
	 * 		"id":9,
	 *  	"actionName":"开",
	 *  	"funcNum":"2",
	 * 		}
	 * 		...
	 * 	]
	 * }
	 * @param templMap
	 * @throws Exception
	 */
	public int saveTemplate(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式:
	 * 新增
	 * {
	 * "id":-1,
	 * "deviceName":"aa",
	 * "tempId":1,
	 * 	"actions":[
	 * 		{
	 * 		"id":-1,
	 * 		"tempActionId":1,
	 *  	"actionCmd":"1234"
	 * 		}
	 * 		...
	 * 	]
	 * }
	 * 编辑
	 * {
	 * "id":10,
	 * "deviceName":"aa",
	 * "tempId":1,
	 * 	"actions":[
	 * 		{
	 * 		"id":11,
	 * 		"tempActionId":2,
	 *  	"actionCmd":"1234"
	 * 		}
	 * 		...
	 * 	]
	 * }
	 * @return
	 * @throws Exception
	 */
	public int saveDevice(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "id":10
	 * }
	 * 删除模板
	 * @param id
	 * @throws Exception
	 */
	public void deleteTemplate(Long id) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "id":10
	 * }
	 * 删除模板
	 * @param id
	 * @throws Exception
	 */
	public void deleteDevice(Long id) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "searchText":"aa"
	 * }
	 * 获取模板总数
	 * @return
	 * @throws Exception
	 */
	public Integer getTemplateCount(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "searchText":"aa"
	 * }
	 * 获取模板总数
	 * @return
	 * @throws Exception
	 */
	public Integer getDeviceCount(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "startRow":0,
	 * "pageSize":30,
	 * "searchText":"aa"
	 * }
	 * 分页获取模板
	 * @return
	 * @throws Exception
	 */
	public List<Map> getTempldates(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "id":1
	 * }
	 * 分页获取模板
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTemplate(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "startRow":0,
	 * "pageSize":30,
	 * "searchText":"aa"
	 * }
	 * 分页获取模板
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDevices(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "id":10
	 * }
	 * 分页获取模板
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDevice(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "templdateId":0,
	 * }
	 * 分页获取模板
	 * @return
	 * @throws Exception
	 */
	public List<Map> getTempldateAction(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {"page":"0","size":100}
	 * 
	 * APP获取云端模拟设备列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getEmDeviceList(Map<String, Object> params) throws Exception;
	
	/**
	 * 请求参数格式
	 * {"ieee":"00137A0000012548","ep":"01","type":"10001","houseId":"2e4d345"}
	 * 
	 * 203获取云端指令数据
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveAndGetCmdData(Map<String, Object> params) throws Exception;
	
	/**
	 * 获取云端模拟设备及相应指令数据
	 * @param params {"ieee":"00137A0000012548","ep":"01","type":"10001","houseId":"2e4d345"}
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> mergeDataByType(DeviceTemplateRel params) throws Exception;
	
	/**
	 * 删除指令
	 * @param id 指令id
	 * @return
	 */
	public int deleteCmdData(Long id) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "ids":"10,12"
	 * }
	 * 删除模板
	 * @param ids
	 * @throws Exception
	 */
	public void deleteTemplates(String ids) throws Exception;
	
	/**
	 * 请求参数格式
	 * {
	 * "ids":"10,12"
	 * }
	 * 删除模板
	 * @param ids
	 * @throws Exception
	 */
	public void deleteDevices(String ids) throws Exception;
	
	/**
	 * 获取模板指令
	 * @param templateId
	 * @throws Exception
	 */
	public List<Map> getTemplateActionById(Map<String, Object> params) throws Exception;
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map> getFuncNumLib(Map<String, Object> params) throws Exception;
	
	/**
	 * 刷新指令
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> refreshCmdData(Map<String, Object> params) throws Exception;
}
