package org.jbxbase.services;

import java.util.Map;

public interface ImportExportServiceI {

	public String Export(Map update) throws Exception;

	public String Import(Map update) throws Exception;
	
	public String findList(Map<String, Object> update) throws Exception;
}
