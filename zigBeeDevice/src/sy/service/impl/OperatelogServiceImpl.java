package sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.BaseDaoI;
import sy.model.Operatelog;
import sy.service.OperatelogServiceI;

@Service("operatelogService")
public class OperatelogServiceImpl implements OperatelogServiceI {

	private static final Logger logger = Logger.getLogger(OperatelogServiceImpl.class);
	
	private BaseDaoI<Operatelog> OperatelogDao;

	public BaseDaoI<Operatelog> getOperatelogDao() {
		return OperatelogDao;
	}

	@Autowired
	public void setOperatelogDao(BaseDaoI<Operatelog> OperatelogDao) {
		this.OperatelogDao = OperatelogDao;
	}

	@Override
	public List<Operatelog> findList2(Operatelog operatelog) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Operatelog t where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (operatelog.getType() != null){
			hql.append("and t.type = :type ");
			params.put("type",  operatelog.getType());
		}
		
    	hql.append(" order by t.optime desc ");
		List<Operatelog> t = OperatelogDao.find(hql.toString(), params);
		if (t != null) {
			return t;
		}
		return null;
	}
	
	@Override
	public Operatelog save(Operatelog operatelog) {
		OperatelogDao.save(operatelog);
		return operatelog;
	}
	
}