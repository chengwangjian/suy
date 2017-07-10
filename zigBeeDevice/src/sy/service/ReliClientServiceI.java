package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.ReliClient;
/**
 * 
 * @author zhanghc
 * 客户代码操作借口
 *
 */
public interface ReliClientServiceI {
	/**
	 * 查询ReliClient
	 * @param reliClient
	 * @return ReliClient对象
	 */
	public ReliClient find(ReliClient reliClient);
	/**
	 * 
	 * @param reliClient
	 * @return
	 */
	public int add(ReliClient reliClient);
	/**
	 * 将ReliClient持久化到数据库，并返回持久化对象
	 * @param reliClient
	 * @return
	 */
	public ReliClient save(ReliClient reliClient);
	/**
	 * 删除客户代码
	 * @param reliClient
	 * @return 删除结果
	 */
	public int delete(ReliClient reliClient);
	/**
	 * 修改ReliClient并持久化
	 * @param reliClient
	 * @return
	 */
	public int update(ReliClient reliClient);
	/**
	 * @param clientCode
	 * @param region
	 * @return
	 */
	public List<Map> findReliClient(ReliClient reliClient);
	/**
	 * 精确查询客户代码
	 * @param reliClient
	 * @return
	 */
	public List<Map> findList(ReliClient reliClient);
	/**
	 * 获取总记录条目数
	 * @return
	 */
	public int getCount(ReliClient reliClient);
	/**
	 * 获取分页查询的内容
	 * @param conditionMap
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	public List<Map> getClientList(ReliClient reliClient, String startRow, String pageSize,String orderBy);
}
