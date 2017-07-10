package sy.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.service.LogManagementServiceI;

@Service("logManagementService")
public class LogManagementServiceImpl implements LogManagementServiceI {
	private BaseDaoI<Map> mapdao;

	public BaseDaoI<Map> getMapdao() {
		return mapdao;
	}

	@Autowired
	public void setMapdao(BaseDaoI<Map> mapdao) {
		this.mapdao = mapdao;
	}

	@Override
	public void commitLog(Map<String, Object> params) {
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT INTO devicelog (houseIeee,logType,deviceSN,clazz,content,time)VALUES(:houseIeee,:logType,:deviceSN,:clazz,:content,:time)";
			this.mapdao.executeSql2(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
