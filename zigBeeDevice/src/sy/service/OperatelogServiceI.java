package sy.service;

import java.util.List;

import sy.model.Operatelog;

public interface OperatelogServiceI {
	
	public List<Operatelog> findList2(Operatelog operatelog);
	
	public Operatelog save(Operatelog operatelog);

}
