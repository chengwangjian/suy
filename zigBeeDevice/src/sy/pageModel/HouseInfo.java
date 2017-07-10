package sy.pageModel;

import java.util.List;

import sy.model.House;
import sy.model.Houseieee;
import sy.model.Proxyserver;
/**
 * 
 * @author steven
 *
 */
public class HouseInfo {
	
	private List<House> house;
	
	private Houseieee houseieee;
	
	private Proxyserver proxyserver;

	public HouseInfo() {
	
	}

	public List<House> getHouse() {
		return house;
	}

	public void setHouse(List<House> house) {
		this.house = house;
	}
	
	public Houseieee getHouseieee() {
		return houseieee;
	}

	public void setHouseieee(Houseieee houseieee) {
		this.houseieee = houseieee;
	}
	
	public Proxyserver getProxyserver() {
		return proxyserver;
	}

	public void setProxyserver(Proxyserver proxyserver) {
		this.proxyserver = proxyserver;
	}
}
