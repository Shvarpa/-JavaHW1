//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.Motorized;

public class Frigate extends SeaVehicle implements Motorized {
    private double avgFuelConsumption; //Motorized Interface
    private double avgMotorLifespan;
    
	public final static String defaultFlag="Israel";

    
    public Frigate(String model, int seats, float speed, boolean withWindDiraction) {
        super(model, seats, speed, withWindDiraction, Frigate.defaultFlag);
        setAvgFuelConsumption(500);
        setAvgMotorLifespan(4);
    }

    public static Frigate inputFrigate() {
        try {
            String model = Input.input("model:");
            int seats = Input.inputInteger("seats:");
            float speed = Input.inputFloat("speed:");
            boolean withWindDiraction = Input.inputBoolean("with wind diraction?:");
            return new Frigate(model, seats, speed, withWindDiraction);
        } catch (NumberFormatException e) {
            System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
            return null;
        }
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
        return super.toString() + " " + Motorized.toString(this);
    }

    public boolean equals(Object other) {
        return super.equals(other) && Motorized.equals(this, other);
    }

}

