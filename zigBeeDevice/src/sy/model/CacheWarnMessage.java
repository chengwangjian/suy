package sy.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 存储推送IOS(微信)的告警消息
 * @author zhanghc
 * @since 2016-07-08 16:00
 */

@Entity
@Table(name="cache_warn_message")
public class CacheWarnMessage {
	private long id;
	/**消息内容(中文)*/
	private String messageCN;
	/**消息内容(英文)*/
	private String messageEN;
	/**家IEEE地址*/
	private String houseIeee;
	/**推送(告警)时间*/
	private String pushTime;
	/**设备名称*/
	private String deviceName;

	public CacheWarnMessage(){}

	public CacheWarnMessage(long id, String messageCN, String messageEN, String houseIeee, String pushTime,
			String deviceName) {
		super();
		this.id = id;
		this.messageCN = messageCN;
		this.messageEN = messageEN;
		this.houseIeee = houseIeee;
		this.pushTime = pushTime;
		this.deviceName = deviceName;
	}

	@Id 
	@GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Column(name="message_cn")
	public String getMessageCN() {
		return messageCN;
	}

	public void setMessageCN(String messageCN) {
		this.messageCN = messageCN;
	}
	@Column(name="message_en")
	public String getMessageEN() {
		return messageEN;
	}

	public void setMessageEN(String messageEN) {
		this.messageEN = messageEN;
	}

	@Column(name="push_time")
	public String getPushTime() {
		return pushTime;
	}

	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
	@Column(name="device_name")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	@Column(name="house_ieee")
	public String getHouseIeee() {
		return houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	@Override
	public String toString() {
		return "CacheWarnMessage [id=" + id + ", messageCN=" + messageCN + ", messageEN=" + messageEN + ", houseIeee="
				+ houseIeee + ", pushTime=" + pushTime + ", deviceName=" + deviceName + "]";
	}
	
}
