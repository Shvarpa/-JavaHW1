package classes;

import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import interfaces.Motorized;

public class HybridPlane extends Vehicle implements ILandVehicle, ISeaVehicle, IAirVehicle, Motorized {

	public static String defaultVehicleUse = "Army";
	public static String defaultRoadType = "Constructed";

	private int wheels;
	private boolean withWindDiraction;
	private String flag;
	private double avgFuelConsumption;
	private double avgMotorLifespan;

	public HybridPlane(String model, int seats, float speed, int wheels, boolean withWindDiraction, String flag,
			double avgFuelConsumption, double avgMotorLifespan) {
		super(model, seats, speed);
		setWheels(wheels);
		setWithWindDiraction(withWindDiraction);
		setFlag(flag);
		setAvgFuelConsumption(avgFuelConsumption);
		SetAvgMotorLifespan(avgMotorLifespan);
	}

	private void setWithWindDiraction(boolean withWindDiraction) {
		this.withWindDiraction = withWindDiraction;
	}

	private void SetAvgMotorLifespan(double avgMotorLifespan) {
		this.avgMotorLifespan = avgMotorLifespan;
	}

	private void setAvgFuelConsumption(double avgFuelConsumption) {
		this.avgFuelConsumption = avgFuelConsumption;
	}

	private void setWheels(int wheels) {
		this.wheels = wheels;
	}

	// ILandVehicle
	@Override
	public int getWheels() {
		return this.wheels;
	}

	@Override
	public String getRoadType() {
		return HybridPlane.defaultVehicleUse;
	}

	// ISeaVehicle
	@Override
	public boolean getWithWindDiraction() {
		return this.withWindDiraction;
	}

	@Override
	public String getFlag() {
		return this.flag;
	}

	@Override
	public void setFlag(String flag) {
		this.flag = flag;
	}

	// IAirVehicle
	@Override
	public String getVehicleUse() {
		return HybridPlane.defaultRoadType;
	}

	// Motorized
	@Override
	public double getAvgFuelConsumption() {
		return this.avgFuelConsumption;
	}

	@Override
	public double getAvgMotorLifespan() {
		return this.avgMotorLifespan;

	}

	public String toString() {
		return super.toString() + " " + ILandVehicle.toString(this) + " " + ISeaVehicle.toString(this) + " "
				+ IAirVehicle.toString(this) + " " + Motorized.toString(this);
	}

	public boolean equals(Object other) {
		return super.equals(other) && ILandVehicle.equals(this, other) && ISeaVehicle.equals(this, other)
				&& IAirVehicle.equals(this, other) && Motorized.equals(this, other);
	}
	
	public HybridPlane(HybridPlane toCopy) {
		super(toCopy);
		setWheels(toCopy.getWheels());
		setWithWindDiraction(toCopy.getWithWindDiraction());
		setFlag(toCopy.getFlag());
		setAvgFuelConsumption(toCopy.getAvgFuelConsumption());
		SetAvgMotorLifespan(toCopy.getAvgMotorLifespan());
	}
	@Override
	public HybridPlane clone() {
		return new HybridPlane(this);
	}
}
