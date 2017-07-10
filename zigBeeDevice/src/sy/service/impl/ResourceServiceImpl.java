package sy.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Resource;
import sy.service.ResourceServiceI;
import sy.util.PropertiesUtil;

@Service("resourceServiceI")
public class ResourceServiceImpl implements ResourceServiceI {
	private BaseDaoI<Resource> resourceDao;
	private BaseDaoI<Map> mapDao;

	public BaseDaoI<Map> getMapDao() {
		return mapDao;
	}
	@Autowired
	public void setMapDao(BaseDaoI<Map> mapDao) {
		this.mapDao = mapDao;
	}

	public BaseDaoI<Resource> getResourceDao() {
		return resourceDao;
	}
	@Autowired
	public void setResourceDao(BaseDaoI<Resource> resourceDao) {
		this.resourceDao = resourceDao;
	}
	
	@Override
	public int appupload(Resource resource, String path, String type, long length, String uuid, String name1) throws Exception {
		String houseieee = resource.getHouseieee();
		String deviceuuid = resource.getDeviceuuid();
		String taketime = resource.getTaketime();
		if(StringUtils.isNotBlank(resource.getResname())) {
			resource.setResname(name1);
		}
		if(StringUtils.isBlank(resource.getRestype())) {
			resource.setRestype(type);
		}
		if(StringUtils.isBlank(resource.getPath()) || StringUtils.isBlank(resource.getSpath())) {
			resource.setPath(path);
			resource.setSpath(path);
		}
		if(StringUtils.isBlank(resource.getUuid())) {
			resource.setUuid(uuid);
		}
		if(StringUtils.isBlank(resource.getSize())) {
			long sun = length/(1024);
			long sun1 = length/(1024*1024);
			long sun2 = length/(1024*1024*1024);
			if (sun >= 0 && sun < 1024) {
				String length1 = sun + "KB";
				resource.setSize(length1);
			}
			
			if( sun >= 1024 && sun < 1024*1024) {
				String length1 = sun1 + "MB";
				resource.setSize(length1);
			}
			if( sun >= 1024*1024) {
				String length1 = sun2 + "G";
				resource.setSize(length1);
			}					
		}
		if(resource.getUploadtime() == null) {
			Date time = new Date();
			DateFormat timeset = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String newtime = timeset.format(time);
			resource.setUploadtime(newtime);
		}
		StringBuilder sql = new StringBuilder("insert into resource(uuid,houseieee,deviceuuid,resname,restype,"+		
				"size, path, spath, " +	"taketime,uploadtime) values");
				sql.append("('"+resource.getUuid()+"','").append(resource.getHouseieee()+"','").append(resource.getDeviceuuid()+"','").append(resource.getResname()+"',")	
				.append(resource.getRestype() + ",").append(resource.getSize() + ",").append(resource.getPath() + ",")
				.append((resource.getSpath() + "'") + ",'")
				.append(resource.getTaketime()+"','").append(resource.getUploadtime()+"','").append("')");
				return	resourceDao.executeSql2(sql.toString());
		 
	}
	
	@Override
	public int savaupload(Map map, String path, String type, long length, String uuid, String name1) {
		// TODO Auto-generated method stub
		try{
			if(StringUtils.isBlank((String) map.get("uploadtime"))) {
				map.put("uploadtime", new Date());
			}
			if(StringUtils.isBlank((String) map.get("restype"))) {
				map.put("restype", type);
			}
			if(StringUtils.isNotBlank((String) map.get("resname"))) {
				map.put("resname", name1);
			}
			if(StringUtils.isBlank((String) map.get("path"))||org.apache.commons.lang.StringUtils.isBlank((String) map.get("spath"))) {
				map.put("spath", path);
				map.put("path",path);				
			}
			if(StringUtils.isBlank((String) map.get("uuid"))) {
				map.put("uuid", uuid);
			}
			if(StringUtils.isBlank((String) map.get("size"))) {
				long sun = length/(1024);
				long sun1 = length/(1024*1024);
				long sun2 = length/(1024*1024*1024);
 				if (sun >= 0 && sun < 1024) {
					String length1 = sun + "KB";
					map.put("size", length1);
				}
				
				if( sun >= 1024 && sun < 1024*1024) {
					String length1 = sun1 + "MB";
					map.put("size", length1);
				}
				if( sun >= 1024*1024) {
					String length1 = sun2 + "G";
					map.put("size", length1);
				}					
			}
			String sql = "insert into resource (uuid, houseieee, deviceuuid, resname, restype, size, path, spath, taketime, uploadtime) values(:uuid, :houseieee, :deviceuuid, :resname, :restype, :size, :path, :spath, :taketime, :uploadtime)";	
		    this.mapDao.executeSql2(sql, map);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
			return 1;
	}
	

	@Override
	public List<Map> getListCount(Map<String, Object> param) throws Exception {
		StringBuffer hql = new StringBuffer("select count(*) as total from resource t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank((String)param.get("houseieee1"))) {
	    	hql.append("and t.houseieee like :houseieee ");
	    	params.put("houseieee", "%" + param.get("houseieee1") + "%" );
	    }
		if(StringUtils.isNotBlank((String) param.get("deviceuuid"))) {
			hql.append(" and t.deviceuuid like :region_name");
			params.put("deviceuuid", "%" + param.get("deviceuuid") + "%");
		}
		if(StringUtils.isNotBlank((String)param.get("restype"))){
	    	hql.append(" and t.restype = :restype ");
	    	params.put("restype", param.get("restype"));
	    }
		
		if(StringUtils.isNotBlank((String) param.get("starttime"))) {
			hql.append("and t.uploadtime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
			//params.put("registTime", param.get("registTime"));
		}		
		List<Map> list = mapDao.executeSql(hql.toString(), params);
		return list;
	}

	@Override
	public List<Map> getList(int startRow, int pageSize, String orderBy, Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
	    StringBuilder hql = new StringBuilder("select * from resource t where 1=1 ");
	    if (StringUtils.isNotBlank((String)param.get("houseieee1"))) {
	    	hql.append("and t.houseieee like :houseieee ");
	    	params.put("houseieee", "%" + param.get("houseieee1") + "%");
	    }
	    if (StringUtils.isNotBlank((String)param.get("deviceuuid"))) {
	    	hql.append(" and t.deviceuuid like :deviceuuid ");
	    	params.put("deviceuuid", "%" + param.get("deviceuuid") + "%");
	    }
	    if(StringUtils.isNotBlank((String)param.get("restype"))){
	    	hql.append(" and t.restype = :restype ");
	    	params.put("restype", param.get("restype"));
	    }
	    if (StringUtils.isNotBlank((String)param.get("starttime"))) {
	    	hql.append(" and t.uploadtime between '").append(param.get("starttime").toString()).append("' and '").append(param.get("endtime").toString()).append("'");
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
	public List<Map> getLogs(String searchText, int pageSize, int startRow)
			throws Exception {
		// TODO Auto-generated method stub
		String tableName = "resource";
		String sql = "select * from " + tableName + " order by id asc limit " + startRow + "," + pageSize;
		List<Map> enmgList = mapDao.executeSql(sql);
		return enmgList;
	}
	@Override
	public List<Map> getresList(Map map) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from resource where 1=1");
		if(StringUtils.isNotBlank((String)map.get("uuid"))) {
			 sql.append(" and uuid like :uuid");
			 param.put("uuid", "%" + map.get("uuid") + "%");
		}		
		List<Map> t = mapDao.executeSql(sql.toString(),param);	
		if( t!=null )
			return t;		
		return null;
	}
	
	@Override
	public void deleteres() throws Exception {
		long timer = 10*24*60*60;//10天是几秒
		StringBuffer sql = new StringBuffer();
		sql.append(" select resname, uploadtime, restype from resource t where 1=1");
	    List<Map> t = mapDao.executeSql(sql.toString());
	    for(int i = 0; i < t.size(); i++ ) {
	    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	Date date = new Date();
	    	String time = df.format(date);
	    	String uptime = t.get(i).get("uploadtime").toString();
	    	String type = t.get(i).get("restype").toString();
	    	Date da1 = df.parse(time);
	    	Date da2 = df.parse(uptime);
	    	//相差时长daff
	    	long daff = (da1.getTime() - da2.getTime())/1000;	    		    		 
	    	if (daff > timer && type.equalsIgnoreCase("ndd")) {
	    		Map<String, Object> map = new HashMap<String, Object>();
	    		String hql = "delete from resource where resname = :resname";
	    		map.put("resname", t.get(i).get("resname"));
	    		mapDao.executeSql2(hql, map);	
	    		String name = t.get(i).get("resname").toString();	    		 
	    		File file = new File(name);
	    		String filename = file.getName();
	    		String currPath = PropertiesUtil.getProperty("fileAddress");
	    		String appBugPath = currPath + filename;
	 			File path = new File(appBugPath);
	 			//if(path.exists()) {
	 			path.delete(); 
	 			//}
	    	 }
	    }
	    
	}
	@Override
	public void deletepath() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select resname, uploadtime, restype from resource t where 1=1");
	    List<Map> t = mapDao.executeSql(sql.toString());
	    for(int i = 0; i < t.size(); i++ ) {	    		    		 	    	
	    	String name = t.get(i).get("resname").toString();	    		 
	    	File file = new File(name);
	    	String filename = file.getName();
	    	String currPath = PropertiesUtil.getProperty("fileAddress");
	    	String appBugPath = currPath + filename;
	 		File path = new File(appBugPath);
	 		if(!path.exists()) {
	 		path.delete(); 
	 		Map<String, Object> map = new HashMap<String, Object>();
	    	String hql = "delete from resource where resname = :resname";
	    	map.put("resname", t.get(i).get("resname"));
	    	mapDao.executeSql2(hql, map);	
	 		}
	    }
		
	}
	@Override
	public void deletepic() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select resname, uploadtime, restype from resource t where 1=1");
	    List<Map> t = mapDao.executeSql(sql.toString());
	    for(int i = 0; i < t.size(); i++ ) {	    		    		 	    	
	    	String name = t.get(i).get("resname").toString();	
	    	String type = t.get(i).get("restype").toString();
	    	File file = new File(name);
	    	String filename = file.getName();
	    	String currPath = PropertiesUtil.getProperty("fileAddress");
	    	String appBugPath = currPath + filename;
	 		File path = new File(appBugPath);
	 	    //BMP、JPG、JPEG、PNG、GIF
	 		if(path.exists() && (type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("png") || type.equalsIgnoreCase("gif") || type.equalsIgnoreCase("bmp"))) {
	 			BufferedImage cam = ImageIO.read(path);
	 			if(cam == null || cam.getWidth(null) <= 0 || cam.getHeight(null) <= 0) {
	 				path.delete();
	 				Map<String, Object> map = new HashMap<String, Object>();
	 		    	String hql = "delete from resource where resname = :resname";
	 		    	map.put("resname", t.get(i).get("resname"));
	 		    	mapDao.executeSql2(hql, map);
	 			}	
	 		}
	    }
		
	}
	@Override
	public int dropres(String resname) throws Exception {
		File file = new File(resname);
		String filename = file.getName();
		String currPath = PropertiesUtil.getProperty("fileAddress");
		String appBugPath = currPath + filename;
		File path = new File(appBugPath);
		if(path.exists()) {
			path.delete();
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = "delete from resource where resname = :resname";
			map.put("resname", resname);
			mapDao.executeSql2(hql, map);
		}
		return 1;
		
	}
	
		
}
