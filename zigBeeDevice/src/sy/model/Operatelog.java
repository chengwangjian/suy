package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Operatelog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "operatelog")
public class Operatelog implements java.io.Serializable {

	// Fields
	// 系统日志表

	private long id;
	private String title; // 标题
	private String content; // 内容
	private String type; // 日志类型(MonitorServer表示监控服务器是否正常）
	private String remark; // 备注
	private Date optime = new Date(); // 日志时间

	// Constructors

	/** default constructor */
	public Operatelog() {
	}

	/** full constructor */
	public Operatelog(String title, String content, String type, String remark,
			Date optime) {
		this.title = title;
		this.content = content;
		this.type = type;
		this.remark = remark;
		this.optime = optime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "title", length = 500)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", length = 4000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "type", length = 100)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "remark", length = 2000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "optime", length = 19)
	public Date getOptime() {
		return this.optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

}