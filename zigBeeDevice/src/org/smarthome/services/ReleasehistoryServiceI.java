package org.smarthome.services;

import java.util.List;
import java.util.Map;

import org.smarthome.domain.Releasehistory;

public interface ReleasehistoryServiceI {

	public Releasehistory keyUpdate(Releasehistory release);

	public Releasehistory saveOrUpdate(Releasehistory release);
	
	public Releasehistory delete(Releasehistory release);
	
	public int getCount(Map<String, Object> device);
	public List<Releasehistory> findList(Map<String, Object> device);
	
	public List<Releasehistory> findhouse(Map<String, Object> device);
	public Releasehistory find(Map<String, Object> device);
	public Releasehistory get(Map<String, Object> device);
	
	public Releasehistory find2(Map<String, Object> device);


}
