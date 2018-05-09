//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.Commercial;
import interfaces.Motorized;

public class Jeep extends LandVehicle implements Commercial, Motorized {

    private String licence;//Commercial Interface
    private double avgFuelConsumption; //Motorized Interface
    private double avgMotorLifespan;
    
	public final static String defaultRoadType="Dirt";
	
    
    public Jeep(String model, float speed, double avgFuelConsumption, double avgMotorLifespan) {
        super(model, 5, speed, 4, Jeep.defaultRoadType);
        setLicense("MINI");
        setAvgFuelConsumption(avgFuelConsumption);
        setAvgMotorLifespan(avgMotorLifespan);
    }

    public static Jeep inputJeep() {
        try {
            String model = Input.input("model:");
            float speed = Input.inputFloat("speed:");
            double avgFuelConsumption = Input.inputDouble("average fuel consumption:");
            double avgMotorLifespan = Input.inputDouble("average motor lifespan:");
            return new Jeep(model, speed, avgFuelConsumption, avgMotorLifespan);
        } catch (NumberFormatException e) {
            System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
            return null;
        }
    }

    public String getLicence() {
        return licence;
    }

    private boolean setLicense(String licence) {
        if (Commercial.checkLicenseInput(licence)) {
            this.licence = licence;
            return true;
        }
        System.out.println("\n incorrect 'licence' was entered,'MINI' use set by default.");
        this.licence = "MINI";
        return false;
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

}
