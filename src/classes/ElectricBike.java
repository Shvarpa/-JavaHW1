package classes;

import interfaces.Motorized;

public class ElectricBike extends LandVehicle implements Motorized {

	public static int defaultWheels = 2;
	public static double defaultAvgFuelConsumption = 20;
	
	private double avgMotorLifespan;
	
	protected ElectricBike(String model, int seats, float speed, String roadType, double avgMotorLifespan) {
		super(model, seats, speed, ElectricBike.defaultWheels, roadType);
		setAvgMotorLifespan(avgMotorLifespan);
	}

	private void setAvgMotorLifespan(double avgMotorLifespan) {
		this.avgMotorLifespan = avgMotorLifespan;
	}

	@Override
	public double getAvgFuelConsumption() {
		return ElectricBike.defaultAvgFuelConsumption;
	}

	@Override
	public double getAvgMotorLifespan() {
		return this.avgMotorLifespan;
	}
	
	public String toString() {
		return super.toString() + " " + Motorized.toString(this);
	}
	
	public boolean equals(Object other) {
		return super.equals(other) && Motorized.equals(this, other);
	}
}
