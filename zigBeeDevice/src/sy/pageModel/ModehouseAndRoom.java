package sy.pageModel;

import java.util.List;

import sy.model.Modehouse;
import sy.model.Moderoom;

public class ModehouseAndRoom {

	private Modehouse modehouse;
	private List<Moderoom> moderoom;
	private String language;
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	/** default constructor */
	public ModehouseAndRoom() {
	}

	public Modehouse getModehouse() {
		return modehouse;
	}

	public void setModehouse(Modehouse modehouse) {
		this.modehouse = modehouse;
	}

	public List<Moderoom> getModeroom() {
		return moderoom;
	}

	public void setModeroom(List<Moderoom> moderoom) {
		this.moderoom = moderoom;
	}

}
