package sy.pageModel;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author: zhuangxd
 * 时间：2013-12-5 下午4:11:24
 */
@XStreamAlias("DeviceInfo")
public class DeviceInfo {
	
	@XStreamAlias("House")  
	private House house;

	public DeviceInfo() {
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

}
