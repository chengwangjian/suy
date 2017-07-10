package sy.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 设备型号常见的问题和解答
 * @author HuangJX
 * @since 2016.11.24
 *
 */

@Entity
@Table(name="deviceProblem")
public class DeviceFAQ {

	private int id;
	private String modelNo;
	private String descriptionCn;
	private String descriptionEn;
	private String createTime;
	private String lastTime;
	public DeviceFAQ(){}
	
	
	@Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="modelNo")
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	
	@Column(name="descriptionCn")
	public String getDescriptionCn() {
		return descriptionCn;
	}
	public void setDescriptionCn(String descriptionCn) {
		this.descriptionCn = descriptionCn;
	}
	
	
	@Column(name="descritionEn")
	public String getDescriptionEn() {
		return descriptionEn;
	}
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	
	@Column(name="creatTime")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="lastTime")
	public String getLastTime() {
		return lastTime;
	}


	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}


	public DeviceFAQ(int id, String modelNo, String descriptionCn,
			String descriptionEn, String createTime,String lastTime) {
		super();
		this.id = id;
		this.modelNo = modelNo;
		this.descriptionCn = descriptionCn;
		this.descriptionEn = descriptionEn;
		this.createTime = createTime;
		this.lastTime = lastTime;
	}
	
}
