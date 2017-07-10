package sy.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Picupload;
import sy.service.CamerapicServiceI;


@Service("camerapicService")
public class CamerapicServiceImpl implements CamerapicServiceI {
	private BaseDaoI<Picupload> picUploadDao;
	private BaseDaoI<Map> mapDao;
	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
    @Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public int saveLog(Map map, String path, String type) {
		// TODO Auto-generated method stub			
		try{
			if(StringUtils.isBlank((String) map.get("uploadtime"))) {
				map.put("uploadtime", new Date());
			}
			if(StringUtils.isBlank((String) map.get("pictype"))){
				map.put("pictype", type);
			}	
			if(StringUtils.isBlank((String) map.get("picpath"))||org.apache.commons.lang.StringUtils.isBlank((String) map.get("picspath"))) {
				map.put("picpath",path);
				map.put("picspath", path);
			}
			String sql = "insert into picupload (houseieee, camuuid, picname, pictype, picpath, picspath, taketime, uploadtime) values(:houseieee, :camuuid, :picname, :pictype, :picpath, :picspath, :taketime, :uploadtime)";	
		    this.mapDao.executeSql2(sql, map);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
			return 1;
	}
	
	@Override
	public List<Map> getPicListCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(*) as total from picupload t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (params.get("houseieee") != null) {
			hql.append("and t.houseieee = :houseieee ");
			param.put("houseieee", params.get("houseieee"));
		}
		if(params.get("camuuid") != null) {
			hql.append("and t.camuuid = :camuuid ");
			param.put("camuuid", params.get("camuuid"));
		}
		if(StringUtils.isNotBlank((String) param.get("starttime"))) {
			hql.append("and t.taketime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
		}
		List<Map> list = mapDao.executeSql(hql.toString(), params);
		return list;
	}

	@Override
	public List<Map> getPicList(String startRow, String pageSize, String orderBy, Map<String, Object> param)
	  {
			Map<String, Object> params = new HashMap<String, Object>();
		    StringBuilder hql = new StringBuilder("select * from picupload t where 1=1 ");
		    if (StringUtils.isNotBlank((String)param.get("houseieee"))) {
		    	hql.append("and t.houseieee = :houseieee ");
		    	params.put("houseieee", param.get("houseieee"));
		    }
		    if (StringUtils.isNotBlank((String)param.get("camuuid"))) {
		    	hql.append(" and t.camuuid = :camuuid ");
		    	params.put("camuuid", param.get("camuuid"));
		    }
		    if (StringUtils.isNotBlank((String)param.get("starttime"))) {
		    	hql.append(" and t.taketime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
		    }
		    if(StringUtils.isBlank(orderBy)) {
		    	hql.append(" order by t.id desc");
		    }
		    else {
		    	hql.append("order by ").append(orderBy);
		    }
		    hql.append(" LIMIT ").append(startRow).append(",").append(pageSize);
		    List<Map> t = this.mapDao.executeSql(hql.toString(), params);
		    if (t != null) {
		      return t;
		    }
		    return null;
		  }

	
	
	@Override
	public long getPicLogCount1(String searchText) throws Exception {
		String tableName = "picupload";
		String sql = "select count(*) enmgCount from " + tableName;
		List<Map> iosList = mapDao.executeSql(sql);
		return ((BigInteger) iosList.get(0).get("enmgCount"))
				.intValue();
	}
	
	@Override
	public List<Map> getPicLogs(String searchText, int pageSize, int startRow)
			throws Exception {
		String tableName = "picupload";
		String sql = "select * from " + tableName + " order by id asc limit " + startRow + "," + pageSize;
		List<Map> enmgList = mapDao.executeSql(sql);
		return enmgList;
	}
	
	@Override
	public boolean isUserExist(String picname) throws Exception {
		StringBuilder hql = new StringBuilder(
				"from Picupload a where a.picname=:picname");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("picname", picname);
		List<Picupload> ruList = picUploadDao.find(hql.toString(), params);
		if (ruList.isEmpty())
			return false;
		else
			return true;
	}
}

