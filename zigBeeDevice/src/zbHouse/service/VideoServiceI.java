package zbHouse.service;

import java.util.Map;

import zbHouse.model.Video;
import zbHouse.pageModel.DataGrid;

public interface VideoServiceI {

	public Video keyUpdate(Video video);

	public Video saveOrUpdate(Video video,Map<String, Object> params);
	
	public int delete(Map<String, Object> params);
	
	public DataGrid find(Map<String, Object> params);	
	/**
	 * video定时保存
	 */
	public int dingshiSave();
}
