package sy.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * openfire 实用类
 * @author R&D1
 *
 */

public class OpenfireUtil {
	private static final Integer OPENFIRE_PORT = 9090;
	
	/**
	 * 加入组
	 * @param userName
	 * @param pwd
	 * @param groupName
	 * @throws Exception
	 */
	public static void addGroupUser(String userName, String pwd, String groupName) throws Exception {
		String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(groupName);
		String addUrl = "http://" + serverIp + ":" + OPENFIRE_PORT + "/plugins/userService/usergroup";
		String json = "{\"username\":\"" + userName + "\",\"password\":\"" + pwd + "\",\"houseIeee\":\"" + groupName + "\",\"oper\":\"addUser\"}";
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("json", json);
		String result = TestHttpclient.postUrlWithParams(addUrl, postMap, "utf-8");
		Map resultMap = JSON.parseObject(result, Map.class);
		if((Integer) ((Map) resultMap.get("response_params")).get("status") != 1) {
			throw new Exception("add group user error");
		}
	}
	
	/**
	 * 加入组
	 * @param userName
	 * @param pwd
	 * @param groupName
	 * @throws Exception
	 */
	public static void addMemberToGroup(String userName, String pwd, String groupName) throws Exception {
		String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(groupName);
		String addUrl = "http://" + serverIp + ":" + OPENFIRE_PORT + "/plugins/userService/usergroup";
		String json = "{\"username\":\"" + userName + "\",\"password\":\"" + pwd + "\",\"houseIeee\":\"" + groupName + "\",\"oper\":\"addGroup\"}";
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("json", json);
		String result = TestHttpclient.postUrlWithParams(addUrl, postMap, "utf-8");
		Map resultMap = JSON.parseObject(result, Map.class);
		if((Integer) ((Map) resultMap.get("response_params")).get("status") != 1) {
			throw new Exception("add group member error");
		}
	}
	
	/**
	 * 从组中删除
	 * @param userName
	 * @param pwd
	 * @param groupName
	 * @throws Exception
	 */
	public static void removeMemberFromGroup(String userName, String pwd, String groupName) throws Exception {
		String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(groupName);
		String addUrl = "http://" + serverIp + ":" + OPENFIRE_PORT + "/plugins/userService/usergroup";
		String json = "{\"username\":\"" + userName + "\",\"password\":\"" + pwd + "\",\"houseIeee\":\"" + groupName + "\",\"oper\":\"removeGroup\"}";
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("json", json);
		String result = TestHttpclient.postUrlWithParams(addUrl, postMap, "utf-8");
		Map resultMap = JSON.parseObject(result, Map.class);
		if((Integer) ((Map) resultMap.get("response_params")).get("status") != 1) {
			throw new Exception("remove group member error");
		}
	}
	
	/**
	 * 删除用户
	 * @param userName
	 * @param pwd
	 * @param groupName
	 * @throws Exception
	 */
	public static void removeGroupUser(String userName, String pwd, String groupName) throws Exception {
		String serverIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(groupName);
		String addUrl = "http://" + serverIp + ":" + OPENFIRE_PORT + "/plugins/userService/usergroup";
		String json = "{\"username\":\"" + userName + "\",\"password\":\"" + pwd + "\",\"houseIeee\":\"" + groupName + "\",\"oper\":\"deleteUser\"}";
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("json", json);
		String result = TestHttpclient.postUrlWithParams(addUrl, postMap, "utf-8");
		Map resultMap = JSON.parseObject(result, Map.class);
		if((Integer) ((Map) resultMap.get("response_params")).get("status") != 1) {
			throw new Exception("remove group user error");
		}
	}
}
