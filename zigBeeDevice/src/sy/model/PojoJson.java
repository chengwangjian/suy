package sy.model;

import java.util.List;

public class PojoJson {
	private int id;
	private Template template;
	private List<Action> actions;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public List<Action> getActions() {
		return actions;
	}
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
}
