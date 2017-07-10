package sy.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.DeviceFAQ;
import sy.service.DeviceFAQServiceI;

@Service("/DeviceFAQService")
public class DeviceFAQServiceImpl  implements DeviceFAQServiceI {

	private static final Logger LOGGER = Logger.getLogger(DeviceFAQServiceImpl.class);
	private BaseDaoI<DeviceFAQ> deviceFaqDao;
	private BaseDaoI<Map> mapDao;
	
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<DeviceFAQ> getDeviceFaqDao() {
		return deviceFaqDao;
	}
	@Autowired
	public void setDeviceFaqDao(BaseDaoI<DeviceFAQ> deviceFaqDao) {
		this.deviceFaqDao = deviceFaqDao;
	}

	
	/**
	 * 添加设备型号常见的问题及其解答方式
	 * @param deviceFaq
	 * @return
	 * @throws Exception
	 */
	@Override
	public int addFAQ(DeviceFAQ deviceFaq) throws Exception {
		// TODO Auto-generated method stub
		String modelNo = deviceFaq.getModelNo();
		String sql = "select * from deviceProblem dp where dp.modelNo=:modelNo";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("modelNo", modelNo);
		List<DeviceFAQ> list = deviceFaqDao.executeSql(sql, params);
		if(!list.isEmpty()){
			return -1;
		}else{
			StringBuilder sql2 = new StringBuilder("insert into deviceProblem ");
			sql2.append("(modelNo,descriptionCn,descriptionEN,createTime,lastTime) values ");
			sql2.append("('").append(deviceFaq.getModelNo()).append("','");
			sql2.append(deviceFaq.getDescriptionCn()).append("','");
			sql2.append(deviceFaq.getDescriptionEn()).append("','");
			sql2.append(deviceFaq.getCreateTime()).append("','");
			sql2.append(deviceFaq.getLastTime()).append("')");
			deviceFaqDao.executeSql2(sql2.toString());
			return 1;
		}
		
	}
	
	/**
	 * 修改编辑设备型号或者常见的问题及其解答方式
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateFAQ(DeviceFAQ deviceFaq,String modelNoOld) throws Exception {
		// TODO Auto-generated method stub
			StringBuilder sql = new StringBuilder("update deviceProblem dp set ");
			sql.append("dp.modelNo='").append(deviceFaq.getModelNo()).append("',");
			sql.append("dp.descriptionCn='").append(deviceFaq.getDescriptionCn()).append("',");
			sql.append("dp.descriptionEn='").append(deviceFaq.getDescriptionEn()).append("',");
			sql.append("dp.lastTime='").append(deviceFaq.getLastTime()).append("'");
			sql.append(" where dp.modelNo=:modelNoOld");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("modelNoOld", modelNoOld);
			mapDao.executeSql2(sql.toString(),params);
			return 1;	
	}
	
	
	/**
	 * 删除设备型号常见的问题及其解答方式
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteFAQ(String modelNo) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete from deviceProblem where modelNo=:modelNo";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("modelNo", modelNo);
		deviceFaqDao.executeSql2(sql, params);
		return 1;
	}

	
	
	
	
	
	/**
	 * 获取所有的设备型号的问题/解决方案的总数
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @param searchText      查询的关键字
	 * @return
	 * @throws Exception
	 */
	@Override
	public long getFAQCount(Map<String, Object> params, String searchText)
			throws Exception {
		// TODO Auto-generated method stub
		List<Map> devicelist ;
		StringBuilder sql= new StringBuilder("select count(*) from deviceProblem dp ");
		if(StringUtils.isNoneBlank(searchText)){
			sql.append("where dp.modelNo like :searchText");
			params.put("searchText","%"+searchText+"%");
			devicelist = mapDao.executeSql(sql.toString(),params);
		}else{
			devicelist = mapDao.executeSql(sql.toString());
		}	
		return  ((BigInteger) ((Map) devicelist.get(0)).get("count(*)")).intValue();
	}

	
	/**
	 * 获取设备型号问题/解决方式的列表
	 * @param deviceFaq
	 * @param modelNo         设备的型号
	 * @param searchText      查询关键字
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map> getFAQList(String startRow,String pageSize,String searchText) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder sql= new StringBuilder("select * from deviceProblem dp ");
		Map<String,Object> params = new HashMap<String,Object>();
		List<Map> devicelist;
		if(StringUtils.isNoneBlank(searchText)){
			sql.append("where dp.modelNo like :searchText ");
			params.put("searchText","%"+searchText+"%");
			sql.append(" order by dp.id desc limit ").append(startRow).append(",").append(pageSize);
			devicelist = mapDao.executeSql(sql.toString(),params);
		}
		else{
			sql.append(" order by dp.id asc limit ").append(startRow).append(",").append(pageSize);
			devicelist = mapDao.executeSql(sql.toString());
		}
		return  devicelist;
	}
	
	
	@Override
	public List<Map> getFAQByModelNo(String modelNo) throws Exception {
		// TODO Auto-generated method stub
		String sql = new String("select * from deviceProblem dp where dp.modelNo like :modelNo");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("modelNo", modelNo);
		List<Map>devicelist = mapDao.executeSql(sql, params);
		return devicelist;
	}
	
	@Override
	public int add(Map params, String createTime, String lastTime) throws Exception {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>(); 
		sql.append("select * from deviceProblem dp where 1=1");
		if(StringUtils.isNotBlank((String) params.get("modelNo"))){
			sql.append(" and modelNo =:modelNo ");
			map.put("modelNo", params.get("modelNo"));
		}
		List<Map> list = mapDao.executeSql(sql.toString(), map);
		if(!list.isEmpty()){
			return -1;
		}else {
			Map<String, Object> param = new HashMap<String, Object>();
			StringBuffer sql2 = new StringBuffer("insert into deviceProblem ");
			sql2.append("(modelNo,descriptionCn,descriptionEn,createTime,lastTime) values (:modelNo,:descriptionCn,:descriptionEn,:createTime,:lastTime)");
			param.put("modelNo", params.get("modelNo"));
			param.put("descriptionCn", params.get("descriptionCn"));
			param.put("descriptionEn", params.get("descriptionEn"));
			param.put("createTime", createTime);
			param.put("lastTime", lastTime);
			mapDao.executeSql2(sql2.toString(),param);
			return 1;
		}
		
	}
	
	@Override
	public int updatedeviceFAQ(String modelNo,String descriptioCn,String descriptionEn,String lastTime) throws Exception {
		// TODO Auto-generated method stub
			StringBuilder sql = new StringBuilder("update deviceProblem dp set ");
			sql.append("dp.modelNo='").append(modelNo).append("',");
			sql.append("dp.descriptionCn='").append(descriptioCn).append("',");
			sql.append("dp.descriptionEn='").append(descriptionEn).append("',");
			sql.append("dp.lastTime='").append(lastTime).append("'");
			sql.append(" where dp.modelNo=:modelNo");
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("modelNo", modelNo);			
			//param.put("modelNoOld", modelNoOld);
			mapDao.executeSql2(sql.toString(),param);
			return 1;	
	}
	
	@Override
	public List<Map> getModelList(Map params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id,modelNo,descriptionCn,descriptionEn from deviceproblem where 1=1 ");
		if(StringUtils.isNotBlank((String) params.get("modelNo"))) {
			sql.append(" and modelNo like:modelNo ");
			map.put("modelNo","%" + params.get("modelNo") +"%");
		}
		List<Map> list = mapDao.executeSql(sql.toString(), map);
		if(list != null){
			return list;
		}
		return null;
	}
	@Override
	public List<Map> getModelList2(Map params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id,modelNo,descriptionCn,descriptionEn from deviceproblem where 1=1 ");
		if(StringUtils.isNotBlank((String) params.get("modelNo"))) {
			sql.append(" and modelNo =:modelNo ");
			map.put("modelNo",params.get("modelNo"));
		}
		List<Map> list = mapDao.executeSql(sql.toString(), map);
		if(list != null){
			return list;
		}
		return null;
	}
	
}
