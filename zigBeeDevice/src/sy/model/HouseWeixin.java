package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 奈伯思家与微信绑定表实体类
 * @author zhanghc
 * @since 2016-06-22
 */
@Entity
@Table(name="house_weixin")
public class HouseWeixin {
	private Long id;
	/**微信账号（openid）*/
	private String account;
	/**家Ieee地址*/
	private String houseIeee;
	private Byte state;
	private String startDate;
	private String endDate;
	/**微信名*/
	private String accountName;
	/**奈伯思智慧家app用户名*/
	private String userName;
	
	public HouseWeixin(){
		
	}
	
	public HouseWeixin(Long id, String account, String houseIeee, Byte state, String startDate, String endDate,
			String accountName,String userName) {
		super();
		this.id = id;
		this.account = account;
		this.houseIeee = houseIeee;
		this.state = state;
		this.startDate = startDate;
		this.endDate = endDate;
		this.accountName = accountName;
		this.userName = userName;
	}
	
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="account")
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Column(name="house_ieee")
	public String getHouseIeee() {
		return houseIeee;
	}
	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}
	@Column(name="state")
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	@Column(name="StartDate")
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Column(name="EndDate")
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Column(name="account_name")
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	@Override
	public String toString() {
		return "HouseWeixin [id=" + id + ", account=" + account + ", houseIeee=" + houseIeee + ", state=" + state
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", accountName=" + accountName +", userName="+userName+ "]";
	}
	

}
