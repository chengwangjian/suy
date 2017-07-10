package zbHouse.pageModel;

import java.util.List;

import zbEnergy.model.Energy;
import zbEnergy.model.Energyfield;
import zbEnergy.model.Energytime;
import zbEnergy.model.Houseparam;
import zbEnergy.model.Priceparam;


public class General {
	
	private Energy energy;
	private Houseparam house;
	private List<Priceparam> price;
	private List<Energyfield> energyfield;
	private List<Energytime> energytime;
	
	/** default constructor */
	public General() {
	}

	public Energy getEnergy() {
		return energy;
	}

	public void setEnergy(Energy energy) {
		this.energy = energy;
	}

	public Houseparam getHouse() {
		return house;
	}

	public void setHouse(Houseparam house) {
		this.house = house;
	}

	public List<Priceparam> getPrice() {
		return price;
	}

	public void setPrice(List<Priceparam> price) {
		this.price = price;
	}

	public List<Energyfield> getEnergyfield() {
		return energyfield;
	}

	public void setEnergyfield(List<Energyfield> energyfield) {
		this.energyfield = energyfield;
	}

	public List<Energytime> getEnergytime() {
		return energytime;
	}

	public void setEnergytime(List<Energytime> energytime) {
		this.energytime = energytime;
	}


	
}
