package sy.service;

import java.util.List;

import sy.model.Houseemail;

public interface HouseemailServiceI {

	public Houseemail save(Houseemail houseemail);
	
	public Houseemail get(Houseemail houseemail);
	
	public Houseemail find(Houseemail houseemail);
	
	public List<Houseemail> findList(Houseemail houseemail);
	
	public Houseemail update(Houseemail houseemail);
	
	public int delete(Houseemail houseemail);
}
