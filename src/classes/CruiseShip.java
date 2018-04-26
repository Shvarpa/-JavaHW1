//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.Motorized;
import interfaces.Commercial;
import interfaces.Inputable;

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
	
    public static CruiseShip inputCruiseShip() {
        try {
            String model = Inputable.input("model:");
            int seats= Inputable.inputInteger("seats:");
            float speed = Inputable.inputFloat("speed:");
            String flag = Inputable.input("flag:");
            double avgFuelConsumption = Inputable.inputDouble("average fuel consumption:");
            double avgMotorLifespan = Inputable.inputDouble("average motor lifespan:");
            return new CruiseShip(model,seats,speed,flag,avgFuelConsumption,avgMotorLifespan);
        } catch (NumberFormatException e) {
            System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
            return null;
        }
    }
}
