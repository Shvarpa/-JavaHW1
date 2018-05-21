//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.Motorized;
import interfaces.Commercial;

public class CruiseShip extends SeaVehicle implements Motorized, Commercial{
	
	public static final String defaultLicence = "Unlimited";
	public static final boolean defaultWithWindDiraction = true;
	
	private double avgFuelConsumption;
	private double avgMotorLifespan;
	
	public CruiseShip(String model, int seats, float speed, String flag,double avgFuelConsumption, double avgMotorLifespan) {
		super(model, seats, speed, defaultWithWindDiraction, flag);
		setAvgFuelConsumption(avgFuelConsumption);
		setAvgMotorLifespan(avgMotorLifespan);
	}

	public String getLicence() {return defaultLicence;}
	
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
