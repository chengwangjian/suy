package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;
import java.util.Map;

import sy.model.House;
import sy.model.Proxyserver;

public interface ProxyserverServiceI {

	public Proxyserver save(Proxyserver proxyserver);
	
	public Proxyserver find(Proxyserver proxyserver);
	
	public List<Proxyserver> findList(Proxyserver proxyserver);
	
	public List<Object[]> findListSql(Proxyserver proxyserver);

	public Proxyserver login(Proxyserver proxyserver);
	
	public Proxyserver get(Proxyserver proxyserver);
	
	public Proxyserver update(Proxyserver proxyserver);
	
	public int delete(Proxyserver proxyserver);

//	public DataGrid datagrid(Proxyserver proxyserver);

	public void remove(String ids);
	
	public void test();

	public Proxyserver get1(Proxyserver proxyserver);

	public int delete1(Proxyserver proxyserver);
	
	public int findBurnin(House house);

	
	public int updateHouseisonline(House house);


    public int updatehousenode(Map<String,Object> params);
    
    public float spaceWarn();
    
    public float serverMemoryWarn();
}
