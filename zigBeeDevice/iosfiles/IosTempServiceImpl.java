package sy.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.IosTemp;
import sy.service.IosTempServiceI;

@Service("iosTempService")
public class IosTempServiceImpl implements IosTempServiceI {
	private BaseDaoI<IosTemp> iosTempService;
	private BaseDaoI<Map> mapDao;
	
	
	public BaseDaoI<IosTemp> getIosTempService() {
		return iosTempService;
	}

	@Autowired
	public void setIosTempService(BaseDaoI<IosTemp> iosTempService) {
		this.iosTempService = iosTempService;
	}

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}

	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public void saveIosTemp(IosTemp iosTemp) throws Exception {
		// TODO Auto-generated method stub
		iosTempService.save(iosTemp);
	}

	@Override
	public int getIosTempCount() throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) iosCount from iosTemp";
		List<Map> iosList = mapDao.executeSql(sql);
		return ((BigInteger) ((Map) iosList.get(0)).get("iosCount")).intValue();
	}

	@Override
	public List<Map> getIosTemps(int pageSize, int startRow) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from iosTemp order by id desc limit " + startRow + "," + pageSize;
		List<Map> iosList = mapDao.executeSql(sql);
		return iosList;
	}

	public void deleteIosTemp(Integer id) throws Exception {
		String sql = "delete from iosTemp where id = :id";
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		mapDao.executeSql2(sql, params);
	}
}
