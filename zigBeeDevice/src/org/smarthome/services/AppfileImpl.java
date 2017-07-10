package org.smarthome.services;

import java.util.Map;

import org.smarthome.domain.FileAppinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flywind.app.dal.StartDAO;

import sy.dao.BaseDaoI;
@Service("appfileImpl")
public class AppfileImpl implements AppfileI{
	private StartDAO dao2;
    public StartDAO getDao2() {
		return dao2;
	}
    @Autowired
	public void setDao2(StartDAO dao2) {
		this.dao2 = dao2;
	}  
	
    public BaseDaoI<FileAppinfo> basefileBaseDaoI;
	public BaseDaoI<FileAppinfo> getBasefileBaseDaoI() {
		return basefileBaseDaoI;
	}
	@Autowired
	public void setBasefileBaseDaoI(BaseDaoI<FileAppinfo> basefileBaseDaoI) {
		this.basefileBaseDaoI = basefileBaseDaoI;
	}
//	public FileAppinfo savefile(FileAppinfo fileAppinfo) {
//		// TODO Auto-generated method stub
//		dao2.create(fileAppinfo);
//		return fileAppinfo;
//	}
	@Override
	public FileAppinfo savefile(Map<String,Object> mappramMap) {
		// TODO Auto-generated method stub
		String sqlString="INSERT INTO file_appinfo" +
				" (houseIEEE, device_type, sys_ver, description, " +
				"opdatetime, file_name, user_name, error_no,oderfilename) VALUES " +
				"('00137A000000DB97', 'deviceType', 'sysVer', 'description', '2015-01-14 02:50:43', '2015012101.doc', 'userName', 'errorNo', '000')";
		basefileBaseDaoI.executeSql2(sqlString);
		return null;
	}
}
