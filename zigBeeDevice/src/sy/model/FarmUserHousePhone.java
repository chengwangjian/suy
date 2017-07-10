package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * IES用户家庭手机绑定相关信息表实体类
 * @author ZhangHuaCan
 * @since 2017-06-15
 *
 */
@Entity
@Table(name="farmuserhousephone")
public class FarmUserHousePhone {
	
	@GenericGenerator(name = "generator", strategy = "identity")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/*用户名*/
	@Column(name="user_name")
	private String username;
	
	/*网关ieee地址*/
	@Column(name="house_ieee")
	private String houseIeee;
	
	/*手机号*/
	@Column(name="phone")
	private String phone;
	
	/*
	 * 短信类型功能开启标识字段（如：1111）
	 * 功能类型开启标志1-开启，0-关闭
	 * 第一位表示短信功能，若未开启（值为0），则后三位设置无效
	 * 第二位表示设备异常通知功能，第三位表示条件触发通知功能，第四位表示设备告警通知功能
	 * */
	@Column(name="sms_flag")
	private String smsFlag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHouseIeee() {
		return houseIeee;
	}
	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSmsFlag() {
		return smsFlag;
	}
	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}
	
}
