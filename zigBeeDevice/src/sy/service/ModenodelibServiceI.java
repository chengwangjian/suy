package sy.service;

import java.util.List;
import java.util.Map;

import sy.model.Modenodelib;
import sy.model.ModenodelibEn;

public interface ModenodelibServiceI {
	
	public Modenodelib save(Modenodelib modenodelib);
	
	public Modenodelib find(Modenodelib modenodelib);
	
	public List<Map> findList(Modenodelib modenodelib);
	
	public Modenodelib get(Modenodelib modenodelib);
	
	public ModenodelibEn geten(ModenodelibEn Modenodeliben);
	
	public Modenodelib update(Modenodelib modenodelib);
	
	public ModenodelibEn updaten(ModenodelibEn modenodeliben);
	
	public int delete(Modenodelib modenodelib);
	
	public int deleten(ModenodelibEn modenodeliben);
	
	public Modenodelib getModenodelib(Modenodelib modenodelib);
	
	public ModenodelibEn getModenodelibEn(ModenodelibEn ModenodelibEn);

	public ModenodelibEn save(ModenodelibEn Modenodeliben);

}
