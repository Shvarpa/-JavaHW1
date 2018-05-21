package classes;

import interfaces.Motorized;

public class ElectricBike extends LandVehicle implements Motorized {

	static private double avgFuelConsumption = 20;
	private double avgMotorLifespan;
	
	protected ElectricBike(String model, int seats, float speed, String roadType, double avgMotorLifespan) {
		super(model, seats, speed, 2, roadType);
		setAvgMotorLifespan(avgMotorLifespan);
	}

	private void setAvgMotorLifespan(double avgMotorLifespan) {
		this.avgMotorLifespan = avgMotorLifespan;
	}

	@Override
	public double getAvgFuelConsumption() {
		return ElectricBike.avgFuelConsumption;
	}

	@Override
	public double getAvgMotorLifespan() {
		return this.avgMotorLifespan;
	}

}
