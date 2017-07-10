package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * IRData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "irdata")
public class IRData implements java.io.Serializable {

	// Fields

	private long id;
	private String data;
	private Integer pulseCount;
	private String decodeType;
	private long modelId;
	private String actionName;
	private Integer ircommdid; //IR数据发送的时候要用到的序号
	private Integer commdSequence;//IR数据发送的时候要用到的序号
	private String actionNameDescription;

	// Constructors

	/** default constructor */
	public IRData() {
	}

	/** full constructor */
	public IRData(String data, Integer pulseCount, String decodeType,
			long modelId, String actionName, Integer ircommdid,
			Integer commdSequence, String actionNameDescription) {
		this.data = data;
		this.pulseCount = pulseCount;
		this.decodeType = decodeType;
		this.modelId = modelId;
		this.actionName = actionName;
		this.ircommdid = ircommdid;
		this.commdSequence = commdSequence;
		this.actionNameDescription = actionNameDescription;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "data", length = 2000)
	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column(name = "pulseCount")
	public Integer getPulseCount() {
		return this.pulseCount;
	}

	public void setPulseCount(Integer pulseCount) {
		this.pulseCount = pulseCount;
	}

	@Column(name = "decodeType", length = 4)
	public String getDecodeType() {
		return this.decodeType;
	}

	public void setDecodeType(String decodeType) {
		this.decodeType = decodeType;
	}

	@Column(name = "modelId")
	public long getModelId() {
		return this.modelId;
	}

	public void setModelId(long modelId) {
		this.modelId = modelId;
	}

	@Column(name = "actionName", length = 100)
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Column(name = "IRCommdid")
	public Integer getIrcommdid() {
		return this.ircommdid;
	}

	public void setIrcommdid(Integer ircommdid) {
		this.ircommdid = ircommdid;
	}

	@Column(name = "CommdSequence")
	public Integer getCommdSequence() {
		return this.commdSequence;
	}

	public void setCommdSequence(Integer commdSequence) {
		this.commdSequence = commdSequence;
	}

	@Column(name = "actionNameDescription", length = 200)
	public String getActionNameDescription() {
		return this.actionNameDescription;
	}

	public void setActionNameDescription(String actionNameDescription) {
		this.actionNameDescription = actionNameDescription;
	}

}