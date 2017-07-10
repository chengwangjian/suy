package sy.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import sy.dao.BaseDaoI;
import sy.model.FarmUser;
import sy.model.ModeFarmDevice;
import sy.model.ModeFarmHouse;
import sy.model.ModeFarmNode;
import sy.service.FarmAreaI;
import sy.util.ChartAttribute;
import sy.util.DeviceAttrUtils;
import sy.util.HouseieeeListener;
import sy.util.OpenfireUtil;
import sy.util.PropertiesUtil;
import sy.util.TestHttpclient;

@SuppressWarnings("rawtypes")

@Service("farmAreaService")
public class FarmAreaImpl implements FarmAreaI {
	private static Logger logger = Logger.getLogger(FarmAreaImpl.class);
	@Autowired
	private BaseDaoI<ModeFarmHouse> fDao;
	@Autowired
	private BaseDaoI<ModeFarmDevice> mfdDao;
	@Autowired
	private BaseDaoI<ModeFarmNode> mfnDao;
	@Autowired
	private BaseDaoI<FarmUser> mBaseDaof;
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Map> getAreaList(String username) throws Exception {
		// 获取该用户下的所有区域IEEE
		String sql = "select mfh.houseName as name,mfh.houseIEEE as house_ieee,"
				+ "mfh.createtime as createtime,mfh.lasttime as lasttime,h.note as note,"
				+ "h.longitude as longitude,h.latitude as latitude "
				+ "from farmuserarea a left join farmuser u on a.user_id=u.id "
				+ "left join modefarmhouse mfh on a.house_ieee = mfh.houseIEEE "
				+ "left join house h on h.houseIEEE = mfh.houseIEEE "
				+ "and (u.id=mfh.userId or u.parent_id=mfh.userId) " + "where u.user_name=:username";
		Map<String, Object> params = new HashMap<>();
		params.put("username", username);
		List<Map> IEEEList = mapDao.executeSql(sql, params);
		Map<String, List<String>> cloudIpCache = new HashMap<String, List<String>>();
		for (Map hIeeeMap : IEEEList) {
			String hIeee = (String) hIeeeMap.get("house_ieee");
			String cloudIp = (String) HouseieeeListener.houseieeeProxyserverMap.get(hIeee);
			if (cloudIp != null) {
				List<String> hIeeeList = cloudIpCache.get(cloudIp);
				if (hIeeeList == null || hIeeeList.isEmpty()) {
					hIeeeList = new ArrayList<String>();
					hIeeeList.add(hIeee);
					cloudIpCache.put(cloudIp, hIeeeList);
				} else {
					hIeeeList.add(hIeee);
				}
			}
		}
		String cloudPort = PropertiesUtil.getProperty("cloud.port");
		Map<String, Object> areaMap = new HashMap<String, Object>();
		// 获取属性
		Iterator<String> itor = cloudIpCache.keySet().iterator();
		while (itor.hasNext()) {
			String cloudIp = itor.next();
			String callUrl = "http://" + cloudIp + ":" + cloudPort + "/zigBeeDevice/farmArea/getAreaAttrs.do";
			Map<String, Object> jMap = new HashMap<String, Object>();
			List<String> hIeeeList = cloudIpCache.get(cloudIp);
			jMap.put("json", JSON.toJSONStringWithDateFormat(hIeeeList, "yyyy-MM-dd HH:mm:ss",
					SerializerFeature.WriteMapNullValue));
			String resultStr = TestHttpclient.postUrlWithParams(callUrl, jMap, "utf-8");
			resultStr = resultStr.trim();
			Map<String, Object> tMap = JSON.parseObject(resultStr, Map.class);
			Integer result = (Integer) tMap.get("result");
			if (result == null || result.intValue() == 0) {
				continue;
			}
			Map<String, Object> aMap = (Map<String, Object>) tMap.get("areas");
			areaMap.putAll(aMap);
		}
		// 组装温室度属性
		Iterator<Map> ieeeItor = IEEEList.iterator();
		while (ieeeItor.hasNext()) {
			Map<String, Object> ieeeMap = ieeeItor.next();
			String hIeee = (String) ieeeMap.get("house_ieee");
			Map<String, Object> attrMap = (Map<String, Object>) areaMap.get(hIeee);
			if (attrMap == null || attrMap.isEmpty()) {
				continue;
			}
			ieeeMap.putAll(attrMap);
		}
		return IEEEList;
	}

	@Override
	public List<Map> getAreas(String userName, int pageIndex, int pageSize) {
		int startRow = (pageIndex - 1) * pageSize;
		// 获取区域
		StringBuilder sql = new StringBuilder("select h.houseName,h.houseIEEE from farmuserarea a ");
		sql.append("inner join farmuser u on a.user_id=u.id ")
				.append("inner join modefarmhouse h on a.house_ieee = h.houseIEEE AND (u.id=h.userId OR u.parent_id=h.userId) ")
				.append("where u.user_name=:userName order by a.id asc limit :startRow, :pageSize");
		Map<String, Object> params = new HashMap<>();
		params.put("userName", userName);
		params.put("startRow", startRow);
		params.put("pageSize", pageSize);
		List<Map> IEEEList = mapDao.executeSql(sql.toString(), params);
		return IEEEList;
	}

	@Override
	public Map<String, Map> getAreaAttrs(List<String> hIeees) {

		if (hIeees == null || hIeees.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, Map> areaMap = new HashMap<String, Map>();
		for (String hIeee : hIeees) {
			Map<String, Object> rMap = new HashMap<>();
			String tableName = "devicenewestattribute_" + hIeee;
			try {
				StringBuilder strAllSql = new StringBuilder();
				// StringBuilder strSql = new StringBuilder("select
				// a.attributeName,FORMAT(SUM(a.value)/COUNT(*),2)+0
				// value,a.opdatetime,b.solid_model_id,b.ep from ");
				// strSql.append(tableName).append(" d1 left join farmdevice f1
				// on d1.device_ieee=f1.ieee and d1.houseIEEE=f1.house_ieee and
				// d1.device_ep=f1.ep where opdatetime in (")
				// .append("select max(opdatetime) from
				// ").append(tableName).append(" d2 left join farmdevice f2 on
				// d2.device_ieee=f2.ieee and d2.houseIEEE=f2.house_ieee and
				// d2.device_ep=f2.ep")
				// .append(" where d2.attribute_id='0000' and
				// (d2.cluster_id='0400'or d2.cluster_id='0402' or
				// d2.cluster_id='0405') group by f2.ep,d2.attributeName)")
				// .append(" and d1.attribute_id='0000' and
				// (d1.cluster_id='0400'or d1.cluster_id='0402' or
				// d1.cluster_id='0405')")
				// .append(" group by f1.ep,d1.attributeName");
				// 判断属性是否为空
				int[] charTypeArr = { 0, 1, 5, 2 };
				int charTypeArrLength = charTypeArr.length;
				for (int i = 0; i < charTypeArrLength; i++) {
					StringBuilder strSql = new StringBuilder(
							"select a.attributeName,FORMAT(SUM(a.value)/COUNT(*),2)+0 value,a.opdatetime,b.solid_model_id,b.ep from ");
					Map<String, Object> attrMap = ChartAttribute.getValue(charTypeArr[i]);
					if (attrMap == null || attrMap.get("clusterId") == null || attrMap.get("attributeId") == null) {
						continue;
					}
					strSql.append(tableName)
							.append(" a INNER JOIN farmdevice b ON a.houseIEEE=b.house_ieee AND a.device_ieee=b.ieee AND a.device_ep=b.ep ")
							.append("WHERE a.cluster_id='").append(attrMap.get("clusterId"))
							.append("' AND a.attribute_id='").append(attrMap.get("attributeId")).append("' ");
					String[][] solidModelIdArr = (String[][]) attrMap.get("solidModelIdArr");
					if (solidModelIdArr != null && solidModelIdArr.length != 0) {
						strSql.append("and (");
						Iterator<String[]> itor = Arrays.asList(solidModelIdArr).iterator();
						while (itor.hasNext()) {
							String[] dSmia = itor.next();
							String solidModelId = dSmia[0];
							String ep = dSmia[1];
							if (solidModelId == null) {
								strSql.append("(b.solid_model_id is null OR b.solid_model_id='') OR ");
							} else {
								strSql.append("(b.solid_model_id='").append(solidModelId).append("' AND b.ep='")
										.append(ep).append("') OR ");
							}
						}
						String tempSqlStr = strSql.substring(0, strSql.length() - 4) + ") ";
						strAllSql.append(tempSqlStr).append("GROUP BY a.houseIeee UNION ALL ");
					} else {
						strAllSql.append(strSql).append("GROUP BY a.houseIeee UNION ALL ");
					}
				}
				String qAttrSqlStr = strAllSql.substring(0, strAllSql.length() - 11);
				List<Map> attrList = mapDao.executeSql(qAttrSqlStr);
				if (attrList != null && !attrList.isEmpty()) {
					for (Map m : attrList) {
						String attrbuteName = (String) m.get("attributeName");
						String solidModelId = (String) m.get("solid_model_id");
						String ep = (String) m.get("ep");
						// 温度
						if (rMap.get("tem") == null)
							if ("temp".equalsIgnoreCase(attrbuteName)) {
								rMap.put("tem", m.get("value"));
								continue;
							}
						// 空气湿度
						if (rMap.get("hum") == null) {
							if ("hum".equalsIgnoreCase(attrbuteName)) {
								if (!DeviceAttrUtils.isSoilHum(solidModelId, true)) {
									rMap.put("hum", m.get("value"));
									continue;
								} else if (DeviceAttrUtils.isSoilHum(solidModelId, false)) {
									if ("01".equals(ep)) {
										rMap.put("hum", m.get("value"));
										continue;
									}
								}
							}
						}
						// 土壤湿度
						if (rMap.get("soil_hum") == null) {
							if ("hum".equalsIgnoreCase(attrbuteName)) {
								if (DeviceAttrUtils.isSoilHum(solidModelId, true)) {
									rMap.put("soil_hum", m.get("value"));
									continue;
								} else if (DeviceAttrUtils.isSoilHum(solidModelId, false)) {
									if ("02".equals(ep)) {
										rMap.put("soil_hum", m.get("value"));
										continue;
									}
								}
							}
						}
						// 紫外线
						if (rMap.get("lux") == null) {
							if ("brightness".equalsIgnoreCase(attrbuteName)) {
								rMap.put("lux", m.get("value"));
								continue;
							}
						}
					}
					// 检查属性为空时，设置返回值为0
					if (rMap.get("tem") == null) {
						rMap.put("tem", 0);
					}
					// 湿度
					if (rMap.get("hum") == null) {
						rMap.put("hum", 0);
					}
					// 湿度
					if (rMap.get("soil_hum") == null) {
						rMap.put("soil_hum", 0);
					}
					// 紫外线
					if (rMap.get("lux") == null) {
						rMap.put("lux", 0);
					}
				} else {
					rMap.put("tem", 0);
					rMap.put("hum", 0);
					rMap.put("soil_hum", 0);
					rMap.put("lux", 0);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				if (StringUtils.containsIgnoreCase(e.getMessage(), tableName)) {
					rMap.put("tem", 0);
					rMap.put("hum", 0);
					rMap.put("soil_hum", 0);
					rMap.put("lux", 0);
				}
			}
			areaMap.put(hIeee, rMap);
		}
		return areaMap;
	}

	/**
	 * 创建关联的方法
	 * 
	 * @author Administrator
	 * @return
	 */

	public int JoinHouse(Map<String, Object> map, Map userMap) throws Exception {
		String houseIEEE = (String) userMap.get("houseIEEE");
		String houseName = (String) userMap.get("houseName");
		String userName = (String) userMap.get("userName");
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from FarmUser where user_name=:username";
		params.put("username", userName);
		FarmUser farmUser = mBaseDaof.get(hql, params);
		
		String password = farmUser.getPassword();
		Long userId = farmUser.getId();
		if (userId == null || userId.intValue() == 0 || StringUtils.isBlank(houseName)) {
			throw new Exception("userId or houseName can't be empty");
		}
		/*StringBuilder inSql = new StringBuilder("insert into modefarmhouse(userId,houseName,createtime) values");
		inSql.append("(:userId,:houseName,now())");
		
		params = new HashMap<>();
		params.put("userId", userId);
		params.put("houseName", houseName);
		mapDao.executeSql2(inSql.toString(), params);


		// log.info("------params:" + params);
		params = new HashMap<>();*/
		
		String hql1 = "from ModeFarmHouse where houseIEEE=:houseIEEE";
		Map<String,Object> params1 = new HashMap<String, Object>();
		params1.put("houseIEEE", houseIEEE);
		List<ModeFarmHouse> list = fDao.find(hql1.toString(), params1);
		if(list.size() != 0){
			throw new Exception("houseIEEE从复");
			
		}else{
		
		ModeFarmHouse house = new ModeFarmHouse();
		house.setUserId(userId.intValue());
		house.setHouseName(houseName);
		house.setCreatetime(new Date());
		fDao.save(house);
		Long houseId = (long) house.getId();
		String gateWays = PropertiesUtil.getProperty("GatWayModel");

		// 判断是否存在或重复网关
		/*StringBuilder getGwSql = new StringBuilder(
				"SELECT 1 FROM modefarmdevice a WHERE a.houseId=:houseId AND a.modelId IN (");
		getGwSql.append(gateWays).append(") LIMIT 2");
		Map<String, Object> gwParams = new HashMap<String, Object>();
		gwParams.put("houseId", houseId);
		List<Map> gwList = mapDao.executeSql(getGwSql.toString(), gwParams);
		
		if(!gwList.isEmpty()) {
			throw new Exception("multi");
		}*/
		Long roomId = -1L;

		// 保存

		ModeFarmNode mfn = new ModeFarmNode();
		Integer id = -1;
		String duqId = "Z206";
		// 添加的设备是网关
		String sql = "select 1 from modefarmhouse h inner join farmuser u on h.userId=u.id where u.parent_id is NULL and h.houseIEEE=:houseIEEE";
		Map<String, Object> rMap = new HashMap<>();
		rMap.put("houseIEEE", houseIEEE);
		List<Map> map1 = mapDao.executeSql(sql, rMap);
		if (map1 != null && !map1.isEmpty()) {
			// 网关已经被关联到其他老板账号上（限制一个网管只能有一个老板账号）
			throw new Exception("already used");
		}
		if (id != null && id != -1) {
			mfn.setId(Long.valueOf(id));
		}
		mfn.setHouseId(houseId);
		mfn.setDeviceUniqueId(duqId);
		mfn.setModeNodeLibId(145);
		mfn.setDeviceName("Z206");
		mfn.setIeee(houseIEEE);
		mfn.setRoomId(-1);
		mfnDao.saveOrUpdate(mfn);
		/*
		 * List<Map> epList = (List<Map>) nodeMap.get("eps"); Iterator<Map>
		 * epItor = epList.iterator(); while(epItor.hasNext()) { Map epMap =
		 * epItor.next(); Integer epId = (Integer) epMap.get("id"); if(epId !=
		 * null && epId != -1) { mfd.setId(Long.valueOf(epId)); }
		 */
		ModeFarmDevice mfd = new ModeFarmDevice();
		mfd.setModeNodeId(-1);
		mfd.setHouseId(houseId);
		mfd.setModelId(duqId);
		mfd.setDeviceName("Z206");
		mfd.setRoomId(roomId);
		mfd.setEp("0A");
		mfd.setDeviceId("0007");
		mfd.setSolidModelId("Z206-10007");
		mfdDao.saveOrUpdate(mfd);

		// 创建关联
		if (StringUtils.isNotBlank(houseIEEE)) {
			/* Integer userId1 = "userId"; */
			/*
			 * String userName1 = (String) userMap.get("user_name"); String
			 * password = (String) userMap.get("pwd");
			 */
			Map<String, Object> paramMap = new HashMap<String, Object>();
			// 编辑时查询houseIEEE是否一致
			/*
			 * if(isEdit) { paramMap.put("houseId", houseId);
			 * paramMap.put("userId", userId); StringBuilder getSql = new
			 * StringBuilder(
			 * "SELECT a.houseIEEE FROM modefarmhouse a INNER JOIN modefarmhouse b "
			 * ); getSql.append(
			 * "ON a.houseIEEE=b.houseIEEE AND a.userId=b.userId WHERE b.id=:houseId AND b.userId=:userId limit 2"
			 * ); List<Map> hIeeeList = mapDao.executeSql(getSql.toString(),
			 * paramMap); String tHouseIeee = (String)
			 * hIeeeList.get(0).get("houseIEEE");
			 * if(!tHouseIeee.equalsIgnoreCase(houseIeee)) { //只有关联一个区域删除后再重新添加
			 * if(hIeeeList.size() < 2) { //删除关联 String delSql =
			 * "delete from farmuserarea where house_ieee=:houseIeee and user_id=:userId"
			 * ; Map<String, Object> hMap = new HashMap<String, Object>();
			 * hMap.put("houseIeee", tHouseIeee); hMap.put("userId", userId1);
			 * mapDao.executeSql2(delSql, hMap); //查询用户名,删除组成员
			 * OpenfireUtil.removeMemberFromGroup(userName, "123", tHouseIeee);
			 * OpenfireUtil.addGroupUser(userName, password, houseIeee);
			 * //将houseIEEE重新访问，供外部调用 params.put("outHouseIeee", houseIeee); }
			 * else { //不是关联一个区域，则加入新组 OpenfireUtil.addGroupUser(userName,
			 * password, houseIeee); } } }
			 */

			// 查询用户名，加入组
			OpenfireUtil.addGroupUser(userName, password, houseIEEE);
			// 将houseIEEE重新访问，供外部调用
			params.put("outHouseIeee", houseIEEE);

			// 创建关联关系
			/*String inSql1 = "replace into farmuserarea(house_ieee,user_id) values(:houseIeee,:userId)";
			paramMap.clear();
			paramMap.put("houseIeee", houseIEEE);
			paramMap.put("userId", userId);
			//mapDao.executeSql2(inSql1, paramMap);*/
			house.setHouseIEEE(houseIEEE);
			fDao.saveOrUpdate(house);
			// 更新modefarmhouse
			String upSql = "update modefarmhouse set houseIEEE=:houseIeee where id=:houseId";
			paramMap.remove("userId");
			paramMap.put("houseIeee", houseIEEE);
			paramMap.put("houseId", houseId);
			mapDao.executeSql2(upSql, paramMap);
		}
		// 往回传
		params.put("houseId", houseId);
		params.put("roomId", roomId);
		// Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("houseId", houseId);
		// paramMap.put("roomId", roomId);
		// // 更新deviceUniqueId
		// String upSql = "update modefarmnode set
		// deviceUniqueId=concat(deviceUniqueId,id) where houseId=:houseId " +
		// "and roomId=:roomId and LOCATE(id, deviceUniqueId)=0";
		// mapDao.executeSql2(upSql, paramMap);
		// upSql = "update modefarmdevice inner join modefarmnode on
		// modefarmdevice.modeNodeId=modefarmnode.id set " +
		// "modefarmdevice.deviceUniqueId=concat(modefarmnode.deviceUniqueId,
		// modefarmdevice.ep) where modefarmdevice.houseId=:houseId " +
		// "and modefarmdevice.roomId=:roomId";
		// mapDao.executeSql2(upSql, paramMap);
		return 0;
		}
	}


}
