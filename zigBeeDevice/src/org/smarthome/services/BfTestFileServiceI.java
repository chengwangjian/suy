package org.smarthome.services;

import java.util.List;
import java.util.Map;

import org.smarthome.domain.BfTestFile;
import org.smarthome.domain.FileAppinfo;

public interface BfTestFileServiceI {

	public BfTestFile keyUpdate(BfTestFile update);

	public BfTestFile saveOrUpdate(BfTestFile update);
	
	public BfTestFile delete(BfTestFile update);
	
//	public int getCount(Map<String, Object> update);
	public List<BfTestFile> findList(Map<String, Object> update);
	public FileAppinfo savefile(FileAppinfo fileAppinfo);
	//appdug上传
	public List<Map> getappdbugfileinfo(Map<String, Object> conditionMap,int startRow, int pageSize,String count);
	
	/**
	 * 获取APP BUG文件总数
	 * @param conditionMap
	 * @return
	 */
	public int getAppFileCount(Map<String, Object> conditionMap);
}
