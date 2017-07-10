package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import sy.pageModel.PageBean;

@Entity
@Table(name="reliclient")
public class ReliClient extends PageBean{
	private long id;
	private String clientCode;
	private String region;
	
	public ReliClient(){}
	
	public ReliClient(String clientCode,String region){
		super();
		this.clientCode = clientCode;
		this.region = region;
	}
	
	public ReliClient(long id, String clientCode, String region) {
		super();
		this.id = id;
		this.clientCode = clientCode;
		this.region = region;
	}	
	
	@GenericGenerator(name="generator",strategy="identity")
	@GeneratedValue(generator="generator")
	@Id
	@Column(name="id",unique=true,nullable=false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name="client_code")
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	@Column(name="region")
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "ReliClient [id=" + id + ", clientCode=" + clientCode
				+ ", region=" + region + "]";
	}
}
