package classes;

import java.util.Collection;

import interfaces.IAirVehicle;
import interfaces.Motorized;

public class ElectricBike extends LandVehicle implements Motorized {

	public static int defaultWheels = 2;
	public static double defaultAvgFuelConsumption = 20;
	
	private double avgMotorLifespan;
	
	public ElectricBike(String model, int seats, float speed, String roadType, double avgMotorLifespan) {
		super(model, seats, speed, ElectricBike.defaultWheels, roadType);
		setAvgMotorLifespan(avgMotorLifespan);
	}

	private void setAvgMotorLifespan(double avgMotorLifespan) {
		this.avgMotorLifespan = avgMotorLifespan;
	}

	@Override
	public Double getAvgFuelConsumption() {
		return ElectricBike.defaultAvgFuelConsumption;
	}

	@Override
	public Double getAvgMotorLifespan() {
		return this.avgMotorLifespan;
	}
	
	public String toString() {
		return super.toString() + " " + Motorized.toString(this);
	}
	
	public boolean equals(Object other) {
		return super.equals(other) && Motorized.equals(this, other);
	}
	
	public ElectricBike(ElectricBike toCopy) {
		super(toCopy);
		setAvgMotorLifespan(toCopy.getAvgMotorLifespan());
	}
	@Override
	public ElectricBike clone() {
		return new ElectricBike(this);
	}
	
	@Override
	public Collection<Class<?>> getInterfaces(){
		Collection<Class<?>> interfaces = super.getInterfaces();
		interfaces.add(Motorized.class);
		return interfaces;
	}
}
