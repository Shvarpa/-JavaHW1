//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.Motorized;
import interfaces.Commercial;

public class CruiseShip extends SeaVehicle implements Motorized, Commercial{
	
	public static final String defaultLicence = "Unlimited";
	
	private double avgFuelConsumption;
	private double avgMotorLifespan;
	
	public CruiseShip(String model, int seats, float speed, String flag,double avgFuelConsumption, double avgMotorLifespan) {
		super(model, seats, speed, true, flag);
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
	
    public static CruiseShip inputCruiseShip() {
        try {
            String model = Input.input("model:");
            int seats= Input.inputInteger("seats:");
            float speed = Input.inputFloat("speed:");
            String flag = Input.input("flag:");
            double avgFuelConsumption = Input.inputDouble("average fuel consumption:");
            double avgMotorLifespan = Input.inputDouble("average motor lifespan:");
            return new CruiseShip(model,seats,speed,flag,avgFuelConsumption,avgMotorLifespan);
        } catch (NumberFormatException e) {
            System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
            return null;
        }
    }
}
