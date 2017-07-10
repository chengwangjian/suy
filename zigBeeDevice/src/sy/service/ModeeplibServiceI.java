package sy.service;

import java.util.List;
import java.util.Map;

import com.flywind.app.entities.Deviceattrlib;

import sy.model.Modeeplib;
import sy.model.ModeeplibEn;

public interface ModeeplibServiceI {
	
	public Modeeplib save(Modeeplib modeeplib);
	
	public ModeeplibEn save(ModeeplibEn modeepliben);
	
	public Modeeplib find(Modeeplib modeeplib);
	
	public List<Map> findList(Modeeplib modeeplib);
	
	public Modeeplib get(Modeeplib modeeplib);
	
	public ModeeplibEn geten(ModeeplibEn modeepliben);
	
	public Modeeplib update(Modeeplib modeeplib);
	
	public ModeeplibEn updaten(ModeeplibEn modeepliben);
	
	public int delete(Modeeplib modeeplib);
	
	public int deleten(ModeeplibEn modeepliben);
	
	public List<ModeeplibEn> findListen(ModeeplibEn modeepliben);
	
	public int saveModeEpactlib(List<Map> list);
	
	public List<Map> findActList(Modeeplib modeeplib);
	
	public int updateModeEpactlib(List<Map> list,Modeeplib modeeplib);
	public List<Deviceattrlib> getAttrLib();
	public List<Deviceattrlib> listAttrLib(int page);
	public int getcountattr();
}
