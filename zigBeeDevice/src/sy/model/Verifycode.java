package sy.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Verifycode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "verifycode")
public class Verifycode implements java.io.Serializable {

	// Fields

	private long id;
	private String userName;
	private String verifyCode;
	private Date codeCreateTime = new Date();

	// Constructors

	/** default constructor */
	public Verifycode() {
	}

	/** full constructor */
	public Verifycode(String userName, String verifyCode, Date codeCreateTime) {
		this.userName = userName;
		this.verifyCode = verifyCode;
		this.codeCreateTime = codeCreateTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "userName", length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "verifyCode", length = 50)
	public String getVerifyCode() {
		return this.verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Column(name = "codeCreateTime", length = 19)
	public Date getCodeCreateTime() {
		return this.codeCreateTime;
	}

	public void setCodeCreateTime(Date codeCreateTime) {
		this.codeCreateTime = codeCreateTime;
	}

}