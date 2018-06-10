//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.Commercial;
import interfaces.Motorized;

public class Jeep extends LandVehicle implements Commercial, Motorized {

	private double avgFuelConsumption; // Motorized Interface
	private double avgMotorLifespan;

	public final static String defaultRoadType = "Dirt";
	public final static String defaultLicence = "MINI";
	public final static int defaultSeats = 5;
	public final static int defaultWheels = 4;

	public Jeep(String model, float speed, double avgFuelConsumption, double avgMotorLifespan) {
		super(model, Jeep.defaultSeats, speed, Jeep.defaultWheels, Jeep.defaultRoadType);
		setAvgFuelConsumption(avgFuelConsumption);
		setAvgMotorLifespan(avgMotorLifespan);
	}

	public String getLicence() {
		return Jeep.defaultLicence;
	}

	public double getAvgFuelConsumption() {
		return this.avgFuelConsumption;
	}

	private void setAvgFuelConsumption(double avgFuelConsumption) {
		this.avgFuelConsumption = avgFuelConsumption;
	}

	public double getAvgMotorLifespan() {
		return avgMotorLifespan;
	}

	private void setAvgMotorLifespan(double avgMotorLifespan) {
		this.avgMotorLifespan = avgMotorLifespan;
	}

	public String toString() {
		return super.toString() + " " + Commercial.toString(this) + " " + Motorized.toString(this);
	}

	public boolean equals(Object other) {
		return super.equals(other) && Commercial.equals(this, other) && Motorized.equals(this, other);
	}
	
	public Jeep(Jeep toCopy) {
		super(toCopy);
		setAvgFuelConsumption(toCopy.getAvgFuelConsumption());
		setAvgMotorLifespan(toCopy.getAvgMotorLifespan());
	}
	@Override
	public Jeep clone() {
		return new Jeep(this);
	}
}
