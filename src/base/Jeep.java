package base;

import base.Inputable;

public class Jeep extends LandVehicle implements Commercial,Motorized{
	
	static Jeep inputJeep() {
		String model=Inputable.input("model (String):");
		float speed=Float.parseFloat(Inputable.input("speed (float):"));
		double avgFuelConsumption=Double.parseDouble(Inputable.input("average fuel consumption (double):"));
		double avgMotorLifespan=Double.parseDouble(Inputable.input("average motor lifespan (double):"));
		return new Jeep(model,speed,avgFuelConsumption,avgMotorLifespan);
	}
	
	private String licence;//Commercial Interface
	
	public String getLicence() {return licence;}
	
	private boolean setLicense(String licence) {
		if (Commercial.checkLicenseInput(licence)) {
			this.licence=licence;
			return true;
		}
		return false;
	}
	
	private double avgFuelConsumption; //Motorized Interface
	private double avgMotorLifespan;
	
	public double getAvgFuelConsumption() {return this.avgFuelConsumption;}
	public double getAvgMotorLifespan() {return avgMotorLifespan;}
	
	private void setAvgFuelConsumption(double avgFuelConsumption) {this.avgFuelConsumption=avgFuelConsumption;}
	private void setAvgMotorLifespan(double avgMotorLifespan) {this.avgMotorLifespan=avgMotorLifespan;}
	
	public Jeep(String model, float speed,double avgFuelConsumption,double avgMotorLifespan) {
		super(model,5,speed,4,"Dirt");
		setLicense("MINI");
		setAvgFuelConsumption(avgFuelConsumption);
		setAvgMotorLifespan(avgMotorLifespan);
	}
	

	public String toString() {return super.toString()+" "+Commercial.toString(this)+" "+Motorized.toString(this);}
	public boolean equals(Jeep other) {return super.equals(other) && Commercial.equals(this, other) && Motorized.equals(this, other);}
	
}
