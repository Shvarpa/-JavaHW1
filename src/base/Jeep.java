package base;

public class Jeep extends LandVehicle implements Commercial,Motorized {
	
	private String license;//Commercial Interface
	
	private boolean setLicense(String license) {
		if (Commercial.checkLicenseInput(license)) {
			this.license=license;
			return true;
		}
		return false;
	}
	public String getLicenses() {
		return this.license;
	}
	
	private double avgFuelConsumption; //Motorized Interface
	private double avgMotorLifespan;
	
	private void setAvgFuelConsumption(double avgFuelConsumption) {this.avgFuelConsumption=avgFuelConsumption;}
	public double getAvgFuelConsumption() {return this.avgFuelConsumption;}
	private void setAvgMotorLifespan(double avgMotorLifespan) {this.avgMotorLifespan=avgMotorLifespan;}
	public double getAvgMotorLifespan() {return avgMotorLifespan;}
	
	public Jeep(String model, float speed,double avgFuelConsumption,double avgMotorLifespan) {
		super(model,5,speed,4,"Dirt");
		setLicense("MINI");
		setAvgFuelConsumption(avgFuelConsumption);
		setAvgMotorLifespan(avgMotorLifespan);
	}
	
}
