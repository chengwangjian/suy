package sy.model;

import java.util.List;

public class QueryParameter {

	private Integer id;
	private String name;
	private List<Conditionsub> condition;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Conditionsub> getCondition() {
		return condition;
	}
	public void setCondition(List<Conditionsub> condition) {
		this.condition = condition;
	}
}
