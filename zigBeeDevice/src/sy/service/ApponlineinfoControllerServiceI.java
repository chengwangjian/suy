package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.Apponlineinfo;


public interface ApponlineinfoControllerServiceI {
	
	public List<Apponlineinfo> find(Apponlineinfo Apponlineinfo);
	
	public int update1(Apponlineinfo apponlineinfo, Map<String, Object> appMap);
	
	public List<Apponlineinfo> find1(String startRow, String pageSize, String orderBy, String userid, Apponlineinfo apponlineinfo);
	
	public int appupdatereadstate(Apponlineinfo advertisement);
	
	public int getCount(Map<String, Object> update);
	
	public void addbatchapp(List<Apponlineinfo> onlineApp,List<Apponlineinfo> offlineApp) throws Exception;
	
	
	
	public List<Map> find2(Map app);
	
	public int update(Apponlineinfo apponlineinfo);
	
	public int delete(Apponlineinfo apponlineinfo);
	
	public Apponlineinfo add(Apponlineinfo apponlineinfo); 
	
	/**
	 * 获得App在线
	 * @param list
	 * @return
	 */
	public void addbatchapp(List<Apponlineinfo> list, String xmppIp) throws Exception;
	
	/**
	 * app在线推送操作系统，系统版本号，描述信息
	 * @param apponlineinfo
	 * @return
	 * @throws Exception
	 */
	public List<Apponlineinfo> findList(Apponlineinfo apponlineinfo) throws Exception;
}
