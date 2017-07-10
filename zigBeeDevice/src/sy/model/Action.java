package sy.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="z485action")
public class Action {
	private long id;
	private String actionName;
	private String actionCmd;
	private String funcNum;
	private long templateId;
	
	@Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name="action_name")
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	@Column(name="action_cmd")
	public String getActionCmd() {
		return actionCmd;
	}
	public void setActionCmd(String actionCmd) {
		this.actionCmd = actionCmd;
	}
	@Column(name="func_num")
	public String getFuncNum() {
		return funcNum;
	}
	public void setFuncNum(String funcNum) {
		this.funcNum = funcNum;
	}
	@Column(name="template_id")
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	@Override
	public String toString() {
		return "Action [id=" + id + ", actionName=" + actionName + ", actionCmd=" + actionCmd + ", funcNum=" + funcNum
				+ ", templateId=" + templateId + "]";
	}
	
}
