package org.smarthome.services;

import java.util.List;
import org.smarthome.domain.Updatehistory;

public interface UpdatehistoryServiceI {

	public Updatehistory keyUpdate(Updatehistory update);

	public Updatehistory saveOrUpdate(Updatehistory update);
	
	public Updatehistory delete(Updatehistory update);
	
	public List<Updatehistory> find(Updatehistory update);

}
