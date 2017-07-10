package sy.service;

import java.util.List;

import sy.model.Historyrecord;
import sy.model.Urlrecord;


public interface RecordServiceI {
	
	public List<Urlrecord> findList(Urlrecord urlrecord);
	
	public Historyrecord HistoryrecordCacheSave(Historyrecord historyrecord);
	
	//public int UrlrecordQuartzSave();
	
	public int HistoryrecordQuartzSave();
}
