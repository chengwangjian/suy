package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import sy.model.ModeSchcUser;
import sy.model.Modeeplib;
import sy.model.Modenode;
import sy.model.Modenodelib;
import sy.model.Userordermain;
import sy.pageModel.ModeNodeAndDevice;

public interface UserordermainServiceI {

	public Userordermain save(Userordermain userordermain);
	
	public Userordermain find(Userordermain userordermain);
	
	public List<Userordermain> findList(Userordermain userordermain);
	
	public List<Object[]> findListSql(Userordermain userordermain);

	public Userordermain login(Userordermain userordermain);
	
	public Userordermain get(Userordermain userordermain);
	
	public Userordermain update(Userordermain userordermain);
	
	public Map findNodeDeviceList(Userordermain userordermain);
	
	public Map findModeNodeLibDataByDeviceID(Modeeplib modeeplib);
	
	public long updateCreateOrder(long houseId);
	
	public Map findModeNodeLibDataByNodeType(Modenodelib modenodelib);
	
	public Map findModeEPLibDataByNode(Modeeplib modeeplib);
	
	public Map findModeNodeList(Modenode modenode);
	
	public List<Userordermain> findStandardModeList(Userordermain userordermain);
	
	public long ImportZ203ModelAndNode(Userordermain userordermain) throws Exception;
	
	public int createOrder(Userordermain userordermain);
	
	public long ImportFromLib(Userordermain userordermain) throws Exception;
	
	public long ImportFromLib2(long houseId);
	
	public Userordermain findOrder(Userordermain userordermain);
	
	public Map findModeNodeSumList(Userordermain userordermain);
	
	public Map findModeNodeLibDataByNodeLibId(Modenodelib modenodelib);
	
	public Map findModeDeviceList(Userordermain userordermain);
	
	public Map findModeDeviceByNodeId(Modenode modenode);
	
	public int insertDeviceToRoom(Map map);
	
	public int insertDeviceToRoom(ModeNodeAndDevice modeNodeAndDevice);
	
	public int deleteModeNode(Modenode modenode);
	
	public int delete(Userordermain userordermain);
	
//	public DataGrid datagrid(Userordermain userordermain);

	public void remove(String ids);
	
	public void test();	
	
	/**
	 * 查询用户的家对应的xml文件路径
	 * @author hlw
	 * @param userId 用户id
	 * @param houseId 家id
	 * @return
	 */
	public String findXmlPath(long userId, long houseId) throws Exception;

	/**
	 * 获取SCHC用户
	 * @param userId
	 * @param houseIEEE
	 * @return
	 * @throws Exception
	 */
	public ModeSchcUser getSCHCUser(long userId, String houseIEEE) throws Exception;
	
	/**
	 * 保存SCHC用户
	 * @param modesu
	 * @throws Exception
	 */
	public void saveSCHCUser(ModeSchcUser modesu) throws Exception;
	
	/**
	 * 更新SCHC
	 * @param modesu
	 * @throws Exception
	 */
	public void updateSCHCUser(ModeSchcUser modesu) throws Exception;
	
	/**
	 * 删除用户
	 * @param modesu
	 * @throws Exception
	 */
	public void deleteSCHCUser(ModeSchcUser modesu) throws Exception;
}
