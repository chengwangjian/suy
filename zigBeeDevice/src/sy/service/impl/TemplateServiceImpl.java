package sy.service.impl;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DeviceTemplateRel;
import sy.model.Template;
import sy.model.TemplateDevice;
import sy.service.TemplateServiceI;
import sy.util.Constants;

@Service("templateService")
public class TemplateServiceImpl implements TemplateServiceI {
	private static final Logger log = Logger.getLogger(TemplateServiceImpl.class);
	private BaseDaoI<Template> tDao;
	private BaseDaoI<TemplateDevice> tdDao;
	private BaseDaoI<DeviceTemplateRel> dtRelDao;
	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<Template> gettDao() {
		return tDao;
	}
	@Autowired
	public void settDao(BaseDaoI<Template> tDao) {
		this.tDao = tDao;
	}
	
	public BaseDaoI<TemplateDevice> getTdDao() {
		return tdDao;
	}
	@Autowired
	public void setTdDao(BaseDaoI<TemplateDevice> tdDao) {
		this.tdDao = tdDao;
	}

	public BaseDaoI<DeviceTemplateRel> getDtRelDao() {
		return dtRelDao;
	}
	@Autowired
	public void setDtRelDao(BaseDaoI<DeviceTemplateRel> dtRelDao) {
		this.dtRelDao = dtRelDao;
	}
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public int saveTemplate(Map<String, Object> params) throws Exception {
		if(params == null || params.isEmpty()) {
			return -1;
		}
		String templateName = (String) params.get("templateName");
		if(StringUtils.isBlank(templateName)) {
			return -2;
		}
//		Integer funcIndex = 0;
		Integer id = (Integer) params.get("id");
		List<Integer> tempIdList = new ArrayList<Integer>();
		List<Map> actions = (List<Map>) params.get("actions");
		if(id == null || id == -1) {
			String getSql = "SELECT MAX(template_id) template_id FROM z485template";
			List<Map> tempList = mapDao.executeSql(getSql);
			Integer templateId = null;
			if(tempList.isEmpty()) {
				templateId = Constants.TEMPLATE_START_ID; 
			}
			else {
				templateId = (Integer) tempList.get(0).get("template_id");
				if(templateId == null) {
					templateId = Constants.TEMPLATE_START_ID;
				}
				else {
					templateId = templateId.intValue() + 1;
				}
			}
			Template template = new Template();
			template.setTemplateName(templateName);
			template.setTemplateId(templateId);
			tDao.save(template);
			id = template.getId().intValue();
		}
		else {
			//查询func_num的最大编号
//			String indexSql = "SELECT MAX(SUBSTRING_INDEX(func_num,'_',-1)+0) func_index FROM z485templateaction WHERE temp_id=" + id;
//			List<Map> indexList = mapDao.executeSql(indexSql);
//			if(!indexList.isEmpty()) {
//				Double tFuncIndex = (Double) indexList.get(0).get("func_index");
//				if(tFuncIndex != null) {
//					funcIndex = tFuncIndex.intValue();
//				}
//			}
			//更新数据
			String upSql = "update z485template set template_name=:templateName,template_id=:templateId where id=:id";
			Map<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("templateId", params.get("templateId"));
			pMap.put("templateName", templateName);
			pMap.put("id", id);
			mapDao.executeSql2(upSql, pMap);
			//查询已存在指令
			pMap.remove("templateName");
			pMap.remove("templateId");
			String getSql = "SELECT id FROM z485templateaction WHERE temp_id=:id";
			List<Map> tempList = mapDao.executeSql(getSql, pMap);
			Iterator<Map> itor = tempList.iterator();
			while(itor.hasNext()) {
				Map tempActMap = itor.next();
				int tempActionId = ((BigInteger) tempActMap.get("id")).intValue();
				boolean exist = false;
				for(int i = 0; i < actions.size(); i++) {
					Map actMap = actions.get(i);
					int actId = (Integer) actMap.get("id");
					if(tempActionId == actId) {
						exist = true;
						break;
					}
				}
				if(!exist) {
					tempIdList.add(tempActionId);
				}
			}
			//删除该指令对应的已经存在的指令数据
			if(!tempIdList.isEmpty()) {
				String delActSql = "DELETE z485action FROM z485action LEFT JOIN z485templateaction a ON z485action.temp_action_id=a.id " +
				"WHERE a.id in (";
				StringBuilder tempIdSql = new StringBuilder();
				Iterator<Integer> idItor = tempIdList.iterator();
				while(idItor.hasNext()) {
					tempIdSql.append(idItor.next()).append(",");
				}
				delActSql += tempIdSql.deleteCharAt(tempIdSql.length() - 1);
				delActSql += ")";
				mapDao.executeSql2(delActSql);
			}
			//删除已有的模板指令
			String delSql = "DELETE FROM z485templateaction WHERE temp_id=:id";
			mapDao.executeSql2(delSql, pMap);
		}
		
		
		if(actions != null && !actions.isEmpty()) {
			StringBuilder inSql = new StringBuilder("insert into z485templateaction(id,action_name,func_num,temp_id) values");
			StringBuilder paramSql = new StringBuilder();
			Iterator<Map> itor = actions.iterator();
			while(itor.hasNext()) {
				Map actMap = itor.next();
				Integer actId = (Integer) actMap.get("id");
				if(actId == -1) {
					actId = null;
				}
				paramSql.append("(").append(actId).append(",'").append(actMap.get("actionName")).append("','")
				.append(actMap.get("funcNum")).append("',").append(id).append("),");
			}
			String inStr = inSql.append(paramSql.deleteCharAt(paramSql.length() - 1)).toString();
			mapDao.executeSql2(inStr);
		}
		return 1;
	}

	@Override
	public int saveDevice(Map<String, Object> params) throws Exception {
		if(params == null || params.isEmpty()) {
			return -1;
		}
		String deviceName = (String) params.get("deviceName");
		if(StringUtils.isBlank(deviceName)) {
			return -2;
		}
		Integer id = (Integer) params.get("id");
		if(id == null || id == -1) {
			//判断名称是否存在
			String getSql = "select 1 from z485device where device_name=:deviceName limit 1";
			Map<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("deviceName", deviceName);
			List<Map> devList = mapDao.executeSql(getSql, pMap);
			if(!devList.isEmpty()) {
				return -3;
			}
			TemplateDevice td = new TemplateDevice();
			td.setDeviceName(deviceName);
			td.setTempId((Integer) params.get("tempId"));
			tdDao.save(td);
			id = td.getId().intValue();
		}
		else {
			//判断名称是否存在
			String getSql = "select 1 from z485device where device_name=:deviceName and device_name<>:oldDeviceName limit 1";
			Map<String, Object> pTmpMap = new HashMap<String, Object>();
			pTmpMap.put("deviceName", deviceName);
			pTmpMap.put("oldDeviceName", params.get("oldDeviceName"));
			List<Map> devList = mapDao.executeSql(getSql, pTmpMap);
			if(!devList.isEmpty()) {
				return -3;
			}
			String upSql = "update z485device set device_name=:deviceName,temp_id=:tempId where id=:id";
			Map<String, Object> pMap = new HashMap<String, Object>();
			pMap.put("deviceName", deviceName);
			pMap.put("tempId", params.get("tempId"));
			pMap.put("id", id);
			mapDao.executeSql2(upSql, pMap);
			//删除已有的模板指令
			pMap.remove("deviceName");
			pMap.remove("tempId");
			String delSql = "DELETE FROM z485action WHERE device_id=:id";
			mapDao.executeSql2(delSql, pMap);
//			String getSql = "select * from z485action WHERE device_id=:id";
//			log.info("----------List:" + mapDao.executeSql(getSql, pMap));
		}
		List<Map> actions = (List<Map>) params.get("actions");
		if(actions != null && !actions.isEmpty()) {
			StringBuilder inSql = new StringBuilder("insert into z485action(id,action_cmd,device_id,temp_action_id) values");
			StringBuilder paramSql = new StringBuilder();
			Iterator<Map> itor = actions.iterator();
			while(itor.hasNext()) {
				Map actMap = itor.next();
				Integer actId = (Integer) actMap.get("id");
				if(actId == -1) {
					actId = null;
				}
				paramSql.append("(").append(actId).append(",'").append(actMap.get("actionCmd")).append("',")
				.append(id).append(",").append(actMap.get("tempActionId")).append("),");
			}
			String inStr = inSql.append(paramSql.deleteCharAt(paramSql.length() - 1)).toString();
//			log.info("------inSql:" + inSql);
			mapDao.executeSql2(inStr);
		}
		return 1;
	}
	
	@Override
	public void deleteTemplate(Long id) throws Exception {
		if(id == null) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tId", id);
		String delTempActSql = "delete z485templateaction from z485templateaction left join z485template t on " +
				"z485templateaction.temp_id=t.id where t.id=:tId";
		mapDao.executeSql2(delTempActSql, params);
		String delActSql = "delete z485action from z485action left join z485device t on z485action.device_id=t.id " +
				"left join z485template a on t.temp_id=a.id where a.id=:tId";
		mapDao.executeSql2(delActSql, params);
		String delRelSql = "delete z485devicetempldaterel from z485devicetempldaterel left join z485device t on " +
				"z485devicetempldaterel.device_id=t.id left join z485template a on t.temp_id=a.id where a.id=:tId";
		mapDao.executeSql2(delRelSql, params);
		String delDeviceSql = "delete z485device from z485device left join z485template t on z485device.temp_id=t.id where t.id=:tId";
		mapDao.executeSql2(delDeviceSql, params);
		String delTemplSql = "delete from z485template where id=:tId";
		mapDao.executeSql2(delTemplSql, params);
	}

	@Override
	public Integer getTemplateCount(Map<String, Object> params) throws Exception {
		StringBuilder sql = new StringBuilder("select count(*) as count from z485template where 1=1");
		String searchText = (String) params.get("searchText");
		//若参数为空或空串时，默认搜索全部
		List<Map> list = null;
		if(StringUtils.isNotBlank(searchText)){   
			sql.append(" and (template_name like '%"+searchText+"%' or template_id like '%"+searchText+"%')");
		}
		list = mapDao.executeSql(sql.toString());
		
		if(!list.isEmpty()){
			return ((BigInteger) list.get(0).get("count")).intValue();
		}
		return 0;
	}
	
	@Override
	public Integer getDeviceCount(Map<String, Object> params) throws Exception {
		StringBuilder sql = new StringBuilder("select count(*) as count from z485device where 1=1");
		String searchText = (String) params.get("searchText");
		//若参数为空或空串时，默认搜索全部
		if(StringUtils.isNotBlank(searchText)){   
			sql.append(" and device_name like '%"+searchText+"%'");
		}
		List<Map> list = mapDao.executeSql(sql.toString());
		if(!list.isEmpty()){
			return ((BigInteger)list.get(0).get("count")).intValue();
		}
		return 0;
	}
	
	@Override
	public List<Map> getTempldates(Map<String, Object> params) throws Exception {
		StringBuilder sql = new StringBuilder("select * from z485template where 1=1");
		Integer startRow = (Integer) params.get("startRow");
		Integer pageSize = (Integer) params.get("pageSize");
		String searchText = (String)params.get("searchText");
		Map<String,Object> param = new HashMap<>();
		if(startRow==null){
			startRow = 0;
		}
		if(pageSize == null){
			pageSize = 30;
		}
		if(StringUtils.isNotBlank(searchText)){   
			sql.append(" and (template_name like '%"+searchText+"%' or template_id like '%"+searchText+"%')");
		}
		sql.append(" limit :startRow,:pageSize");
		param.put("startRow", startRow);
		param.put("pageSize", pageSize);
		return mapDao.executeSql(sql.toString(),param);
	}
	
	@Override
	public Map<String, Object> getTemplate(Map<String, Object> params) throws Exception {
		if(params == null || params.isEmpty()) {
			return null;
		}
		Integer id = (Integer) params.get("id");
		if(id == null) {
			return null;
		}
		StringBuilder getSql = new StringBuilder("SELECT a.id,a.template_name,a.template_id,b.id taId,b.action_name,b.func_num FROM ");
		getSql.append("z485template a INNER JOIN z485templateaction b ON a.id=b.temp_id WHERE a.id=").append(id).append(" ORDER BY b.id ASC");
		List<Map> tempList = mapDao.executeSql(getSql.toString());
		if(tempList.isEmpty()) {
			return null;
		}
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("id", tempList.get(0).get("id"));
		tempMap.put("templateName", tempList.get(0).get("template_name"));
		tempMap.put("templateId", tempList.get(0).get("template_id"));
		List<Map> actionList = new ArrayList<Map>();
		Iterator<Map> itor = tempList.iterator();
		while(itor.hasNext()) {
			Map tTempMap = itor.next();
			Map<String, Object> rTMap = new HashMap<String, Object>();
			rTMap.put("taId", tTempMap.get("taId"));
			rTMap.put("actionName", tTempMap.get("action_name"));
			rTMap.put("funcNum", tTempMap.get("func_num"));
			actionList.add(rTMap);
		}
		tempMap.put("actions", actionList);
		return tempMap;
	}
	
	@Override
	public List<Map> getDevices(Map<String, Object> params) throws Exception {
		StringBuilder sql = new StringBuilder("SELECT a.id,a.device_name,b.template_name,b.template_id FROM z485device a LEFT JOIN z485template b ON a.temp_id=b.id WHERE 1=1 ");
		Integer startRow = (Integer) params.get("startRow");
		Integer pageSize = (Integer) params.get("pageSize");
		Map<String,Object> param = new HashMap<>();
		if(startRow==null){
			startRow = 0;
		}
		if(pageSize == null){
			pageSize = 30;
		}
		String searchText = (String) params.get("searchText");
		//若参数为空或空串时，默认搜索全部
		List<Map> list = null;
		if(StringUtils.isNotBlank(searchText)){   
			sql.append("and a.device_name like '%"+searchText+"%' or b.template_name like '%"+searchText+"%'");
		}
		sql.append("limit :startRow,:pageSize");
		param.put("startRow", startRow);
		param.put("pageSize", pageSize);
		list = mapDao.executeSql(sql.toString(), param);
		return list;
	}
	
	@Override
	public Map<String, Object> getDevice(Map<String, Object> params) throws Exception {
		if(params == null || params.isEmpty()) {
			return null;
		}
		Integer id = (Integer) params.get("id");
		if(id == null) {
			return null;
		}
		StringBuilder getSql = new StringBuilder("SELECT a.id,d.id taId,a.device_name,a.temp_id,d.action_cmd,c.id temp_action_id,c.action_name,c.func_num,");
		getSql.append("b.template_id FROM z485device a LEFT JOIN z485template b ON a.temp_id=b.id LEFT JOIN z485templateaction c ON b.id = c.temp_id ")
		.append("LEFT JOIN z485action d ON c.id = d.temp_action_id AND a.id=d.device_id WHERE a.id=").append(id);
		List<Map> tempList = mapDao.executeSql(getSql.toString());
		if(tempList.isEmpty()) {
			return null;
		}
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("id", tempList.get(0).get(id));
		tempMap.put("deviceName", tempList.get(0).get("device_name"));
		tempMap.put("tempId", tempList.get(0).get("temp_id"));
		tempMap.put("templateId", tempList.get(0).get("template_id"));
		List<Map> actionList = new ArrayList<Map>();
		Iterator<Map> itor = tempList.iterator();
		while(itor.hasNext()) {
			Map tTempMap = itor.next();
			Map<String, Object> rTMap = new HashMap<String, Object>();
			rTMap.put("taId", tTempMap.get("taId"));
			rTMap.put("tempActionId", tTempMap.get("temp_action_id"));
			rTMap.put("actionName", tTempMap.get("action_name"));
			rTMap.put("actionCmd", tTempMap.get("action_cmd"));
			rTMap.put("funcNum", tTempMap.get("func_num"));
			actionList.add(rTMap);
		}
		tempMap.put("actions", actionList);
		return tempMap;
	}
	
	@Override
	public List<Map> getTempldateAction(Map<String, Object> params) throws Exception {
		if(params == null || params.get("id") == null) {
			return null;
		}
		String getSql = "SELECT a.id,a.action_name,a.func_num FROM z485templateaction a INNER JOIN z485template b " +
				"ON a.temp_id=b.id WHERE b.id=:id";
		return mapDao.executeSql(getSql, params);
	}
	
	@Override
	public List<Map> getEmDeviceList(Map<String, Object> params) throws Exception {
		if(params == null) {
			params = new HashMap<String, Object>();
			params.put("page", 0);
			params.put("size", 30);
		}else{
			if(params.get("page") == null) {
				params.put("page", 0);
			}
			if(params.get("size") == null) {
				params.put("size", 30);
			}
		}
		long startRow = (Integer)params.get("page")*(Integer)params.get("size");
		params.put("startRow",startRow);
		params.remove("page");
		StringBuilder getSql = new StringBuilder("SELECT a.id emDeviceId,a.device_name name,b.template_id type FROM z485device a INNER JOIN ");
		getSql.append("z485template b ON a.temp_id=b.id limit :startRow,:size");
		return mapDao.executeSql(getSql.toString(), params);
	}
	
	@Override
	public Map<String, Object> saveAndGetCmdData(Map<String, Object> params) throws Exception {
		if(params == null || params.isEmpty()) {
			return null;
		}
		String ieee = (String) params.get("ieee");
		if(StringUtils.isBlank(ieee)) {
			return null;
		}
		String ep = (String) params.get("ep");
		if(StringUtils.isBlank(ep)) {
			return null;
		}
		Integer deviceId = (Integer) params.get("emDeviceId");
		if(deviceId == null) {
			return null;
		}
		//查询串口设备名
		String qNameSql = "SELECT device_name FROM z485device WHERE id=:deviceId";
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("deviceId", deviceId);
		List<Map> devList = mapDao.executeSql(qNameSql, pMap);
		String dName = devList.isEmpty()? "设备":(String) devList.get(0).get("device_name");
		//查询houseId
		String qHouseSql = "select id from modehouse where hosueGuid=:houseId order by createtime desc limit 1";
		pMap = new HashMap<String, Object>();
		pMap.put("houseId", params.get("houseId"));
		List<Map> houList = mapDao.executeSql(qHouseSql, pMap);
		Long houseId = houList.isEmpty()? -1L : ((BigInteger) houList.get(0).get("id")).longValue();
		//先判断关联关系是否存在，不存在则保存
		String relSql = "select id,house_id from z485devicetempldaterel where device_id=:deviceId and ieee=:ieee and ep=:ep";
		Map<String, Object> relParams = new HashMap<String, Object>();
		relParams.put("deviceId", deviceId);
		relParams.put("ieee", ieee);
		relParams.put("ep", ep);
//		relParams.put("houseId", houseId);
		List<Map> relList = mapDao.executeSql(relSql, relParams);
		Long relId = null;
		if(relList.isEmpty()) {
			DeviceTemplateRel  dtr = new DeviceTemplateRel();
			dtr.setDeviceId(Long.valueOf(deviceId));
			dtr.setIeee(ieee);
			dtr.setEp(ep);
			dtr.setName(dName);
			dtr.setHouseId(houseId);
			dtRelDao.save(dtr);
			relId = dtr.getId();
		}
		else {
			//存在houseId时换成houseId
			int tmpHouseId = ((BigInteger) relList.get(0).get("house_id")).intValue();
			if(houseId != -1 && tmpHouseId == -1) {
				String upStr = "update z485devicetempldaterel set house_id=:houseId where device_id=:deviceId and ieee=:ieee and ep=:ep and house_id=-1";
				relParams.put("houseId", houseId);
				mapDao.executeSql2(upStr, relParams);
			}
			relId = ((BigInteger) relList.get(0).get("id")).longValue();
		}
		
		pMap = new HashMap<String, Object>();
		pMap.put("deviceId", deviceId);
		StringBuilder getSql = new StringBuilder("SELECT a.id,d.action_name name,d.func_num func,a.action_cmd cmd,");
		getSql.append("b.device_name,c.template_id type FROM z485action a INNER JOIN z485device b ON a.device_id=b.id ")
		.append("INNER JOIN z485templateaction d ON a.temp_action_id=d.id INNER JOIN z485template c ON b.temp_id=c.id ")
		.append("WHERE b.id=:deviceId");
		List<Map> actions = mapDao.executeSql(getSql.toString(), pMap);
		//返回数据
		if(actions==null || actions.isEmpty()) {
			return null;
		}
		Map<String, Object> actionMap = new HashMap<String, Object>();
		actionMap.put("result",1);
		actionMap.put("type", actions.get(0).get("type"));
		actionMap.put("name", actions.get(0).get("device_name"));
		actionMap.put("relId", relId);
		for(Map<String, Object> actMap : actions) {
			actMap.remove("type");
			actMap.remove("device_name");
		}
		actionMap.put("cmds", actions);
		return actionMap;
	}
	
	@Override
	public Map<String,Object> mergeDataByType(DeviceTemplateRel params) throws Exception{
		Map<String,Object> param = new HashMap<String,Object>();
//		if(params==null){
//			return param;
//		}
//		//获取模板信息
//		String hql = "from Template where template_type=:type";
//		param.put("type", params.getType());
//		Template template = templDao.get(hql, param);
//		//根据模板类型获取指令集
//		StringBuilder getSql = new StringBuilder("select a.id,a.action_name name,a.func_num func,a.action_cmd cmd from z485action a ");
//		getSql.append("left join z485template b on a.template_id = b.id ")
//			.append(" where b.template_type=:type");
//		List<Map> actions = mapDao.executeSql(getSql.toString(), param);
//		param.clear();
//		if(template!=null&&StringUtils.isNotBlank(template.getTemplateName())){
//			param.put("result", 1);
//			param.put("name", template.getTemplateName());
//			param.put("type", template.getTemplateType());
//			param.put("cmds", actions);
//			//保存模板、指令和家与ieee的关联
//			//saveDeviceTempldateRel(params);
//		}
		return param;
	}
	/**
	 * 添加模拟设备与houseIeee关联
	 * @param params
	 */
	private void saveDeviceTempldateRel(DeviceTemplateRel params){
//		if(StringUtils.isNotBlank(params.getEp())&&StringUtils.isNotBlank(params.getHouseId())
//				&&StringUtils.isNotBlank(params.getIeee())){
//			String secSql = "select id from modehouse where hosueGuid=:houseId";
//			List<Map> list = mapDao.executeSql(secSql, QueryParameters.with("houseId", params.getHouseId()).parameters());
//			if(list!=null&&list.size()==1){
//				params.setHouseId(list.get(0).get("id").toString());
//			}else{
//				params.setHouseId("");
//			}
//			StringBuilder sql = new StringBuilder("insert into z485devicetempldaterel values(NULL,");
//			sql.append("'").append(params.getIeee()).append("','").append(params.getEp()).append("','")
//				.append(params.getType()).append("',").append(params.getHouseId()).append(")");
//			sql.append(" ON DUPLICATE KEY UPDATE ieee=:ieee,ep=:ep,template_type=:type,house_id=:houseId");
//			Map<String,Object> param = new HashMap<String,Object>();
//			param.put("ieee", params.getIeee());
//			param.put("ep", params.getEp());
//			param.put("type", params.getType());
//			param.put("houseId", Long.parseLong(params.getHouseId()));
//			mapDao.executeSql2(sql.toString(),param);
//		}
	}
	
	@Override
	public int deleteCmdData(Long id) throws Exception{
		String sql = "delete from z485action where id=:id";
		if(id!=null&&id>0){
			Map<String,Object> params = new HashMap<>();
			params.put("id", id);
			return mapDao.executeSql2(sql, params);
		}
		return 0;
	}
	
	@Override
	public void deleteTemplates(String ids) throws Exception {
		if(StringUtils.isBlank(ids)) {
			return;
		}
		String delTempActSql = "delete z485templateaction from z485templateaction left join z485template t on " +
				"z485templateaction.temp_id=t.id where t.id in (" + ids + ")";
		mapDao.executeSql2(delTempActSql);
		String delActSql = "delete z485action from z485action left join z485device t on z485action.device_id=t.id " +
				"left join z485template a on t.temp_id=a.id where a.id in (" + ids + ")";
		mapDao.executeSql2(delActSql);
		String delRelSql = "delete z485devicetempldaterel from z485devicetempldaterel left join z485device t on " +
				"z485devicetempldaterel.device_id=t.id left join z485template a on t.temp_id=a.id where a.id in (" + ids + ")";
		mapDao.executeSql2(delRelSql);
		String delDeviceSql = "delete z485device from z485device left join z485template t on z485device.temp_id=t.id " +
				"where a.id in (" + ids + ")";
		mapDao.executeSql2(delDeviceSql);
		String delTemplSql = "delete from z485template where a.id in (" + ids + ")";
		mapDao.executeSql2(delTemplSql);
	}
	
	@Override
	public void deleteDevice(Long id) throws Exception {
		// TODO Auto-generated method stub
		if(id == null) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tId", id);
		String delActSql = "delete z485action from z485action left join z485device t on z485action.device_id=t.id where t.id=:tId";
		mapDao.executeSql2(delActSql, params);
		String delRelSql = "delete z485devicetempldaterel from z485devicetempldaterel left join z485device t on " +
				"z485devicetempldaterel.device_id=t.id where t.id=:tId";
		mapDao.executeSql2(delRelSql, params);
		String delDeviceSql = "delete from z485device where id=:tId";
		mapDao.executeSql2(delDeviceSql, params);
	}
	
	@Override
	public void deleteDevices(String ids) throws Exception {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(ids)) {
			return;
		}
		String delActSql = "delete z485action from z485action left join z485device t on z485action.device_id=t.id where t.id in (" + ids + ")";
		mapDao.executeSql2(delActSql);
		String delRelSql = "delete z485devicetempldaterel from z485devicetempldaterel left join z485device t on " +
				"z485devicetempldaterel.device_id=t.id where t.id in (" + ids + ")";
		mapDao.executeSql2(delRelSql);
		String delDeviceSql = "delete from z485device where id in (" + ids + ")";
		mapDao.executeSql2(delDeviceSql);
	}
	
	@Override
	public List<Map> getTemplateActionById(Map<String, Object> params) throws Exception {
		if(params == null || params.isEmpty()) {
			throw new Exception("params_empty");
		}
		Integer id = (Integer) params.get("id");
		String getSql = "SELECT a.id,a.action_name FROM z485templateaction a WHERE a.temp_id=" + id + " order by a.id asc";
		return mapDao.executeSql(getSql);
	}
	
	@Override
	public List<Map> getFuncNumLib(Map<String, Object> params) throws Exception {
		String getSql = "SELECT * FROM z485funcnumlib";
		return mapDao.executeSql(getSql);
	}
	
	@Override
	public Map<String, Object> refreshCmdData(Map<String, Object> params) throws Exception {
		if(params == null || params.isEmpty()) {
			return null;
		}
		Integer relId = (Integer) params.get("relId");
		if(relId == null) {
			return null;
		}
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("relId", relId);
		StringBuilder getSql = new StringBuilder("SELECT a.id,d.action_name name,d.func_num func,a.action_cmd cmd,");
		getSql.append("b.device_name,c.template_id type FROM z485action a INNER JOIN z485device b ON a.device_id=b.id ")
		.append("INNER JOIN z485templateaction d ON a.temp_action_id=d.id INNER JOIN z485template c ON b.temp_id=c.id ")
		.append("INNER JOIN z485devicetempldaterel e ON e.device_id=b.id WHERE e.id=:relId");
		List<Map> actions = mapDao.executeSql(getSql.toString(), pMap);
		//返回数据
		if(actions==null || actions.isEmpty()) {
			return null;
		}
		Map<String, Object> actionMap = new HashMap<String, Object>();
		actionMap.put("result",1);
		actionMap.put("type", actions.get(0).get("type"));
		actionMap.put("name", actions.get(0).get("device_name"));
		actionMap.put("relId", relId);
		for(Map<String, Object> actMap : actions) {
			actMap.remove("type");
			actMap.remove("device_name");
		}
		actionMap.put("cmds", actions);
		return actionMap;
	}
}
