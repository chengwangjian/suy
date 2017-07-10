package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import sy.model.Warnsend;

public interface WarnsendServiceI {

	public Warnsend save(Warnsend warnsend);
	
	public Warnsend find(Warnsend warnsend);
	
	public List<Warnsend> findList(Warnsend warnsend);
	
	public List<Object[]> findListSql(Warnsend warnsend);

	public Warnsend login(Warnsend warnsend);
	
	public Warnsend get(Warnsend warnsend);
	
	public Warnsend update(Warnsend warnsend);
	public int update2(Map<String,Object> mapparame);
	public int delete(Warnsend warnsend);
	
	/**
	 * 根据失效的设备唯一标识（deviceToken）删除推送的目标对象
	 * 用以减少无效标识的推送，提高推送效率
	 * @param deviceToken 设备唯一标识
	 * @return
	 */
	public int deleteByDeviceToken(String deviceToken);
	
	public int addDeviceAttributeHistoryTable(String warnsendIeee);
	
	public int addDeviceWarnHistoryTable(String warnsendIEEE);
	
	public int addDeviceOperateHistoryTable(String warnsendIEEE);

//	public DataGrid datagrid(Warnsend warnsend);

	public void remove(String ids);
	
	public void test();	

}
