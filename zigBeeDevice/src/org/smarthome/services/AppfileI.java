package org.smarthome.services;

import java.util.Map;

import org.smarthome.domain.FileAppinfo;

public interface AppfileI {
	public FileAppinfo savefile(Map<String,Object> mappramMap);
}
