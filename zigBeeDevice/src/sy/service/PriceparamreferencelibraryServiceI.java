package sy.service;

//import sy.pageModel.DataGrid;
import java.util.List;

import sy.model.Priceparamreferencelibrary;

public interface PriceparamreferencelibraryServiceI {

	public Priceparamreferencelibrary save(Priceparamreferencelibrary priceparamreferencelibrary);
	
	public Priceparamreferencelibrary get(Priceparamreferencelibrary priceparamreferencelibrary);
	
	public Priceparamreferencelibrary getExisit(Priceparamreferencelibrary priceparamreferencelibrary);
	
	public Priceparamreferencelibrary update(Priceparamreferencelibrary priceparamreferencelibrary);
	
	public List<Priceparamreferencelibrary> findList(Priceparamreferencelibrary priceparamreferencelibrary);
	
	public int delete(Priceparamreferencelibrary priceparamreferencelibrary);

//	public DataGrid datagrid(Priceparamreferencelibrary priceparamreferencelibrary);

	public void remove(String ids);
	
	public void test();	

}
