package classes;

import interfaces.Motorized;
import interfaces.Commercial;

public class CruiseShip extends SeaVehicle implements Motorized, Commercial{
	
	private double avgFuelConsumption;
	private double avgMotorLifespan;
	
	protected CruiseShip(String model, int seats, float speed, String flag,double avgFuelConsumption, double avgMotorLifespan) {
		super(model, seats, speed, true, flag);
		setAvgFuelConsumption(avgFuelConsumption);
		setAvgMotorLifespan(avgMotorLifespan);
	}

	public String getLicence() {return "Unlimited";}
	
	public double getAvgFuelConsumption() {return avgFuelConsumption;}
	public double getAvgMotorLifespan() {return avgMotorLifespan;}
	
	private void setAvgFuelConsumption(double avgFuelConsumption) {this.avgFuelConsumption=avgFuelConsumption;}
	private void setAvgMotorLifespan(double avgMotorLifespan) {this.avgMotorLifespan=avgMotorLifespan;}
	
	public boolean equals(Object other) {
		return super.equals(other) && Motorized.equals(this, other);
	}
	
	public String toString() {
		return super.toString() + " " + Motorized.toString(this) + " " + Commercial.toString(this);
	}
}
