package zbHouse.pageModel;

import java.util.Date;

public class Analysis {
	
	private String houseIeee;
	private String deviceType;
	private String ieee;
	private String ep;
	private String dateType;
	private Date startTime;
	private Date endTime;
	private String regionCode;	
	
	/** default constructor */
	public Analysis() {
	}

	public String getHouseIeee() {
		return houseIeee;
	}

	public void setHouseIeee(String houseIeee) {
		this.houseIeee = houseIeee;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getIeee() {
		return ieee;
	}

	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	public String getEp() {
		return ep;
	}

	public void setEp(String ep) {
		this.ep = ep;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
}
