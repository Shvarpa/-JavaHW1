//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.NonMotorized;

public class Bike extends LandVehicle implements NonMotorized {
	
	public static final String defaultEnergyRating = "a";
	public static final String defaultEnergySource = "Manual";

	
    public Bike(String model, int seats, float speed, String roadType) {
        super(model, seats, speed, 2, roadType);
    }

    public static Bike inputBike() {
        try {
            String model = Input.input("model:");
            int seats = Input.inputInteger("seats:");
            float speed = Input.inputFloat("speed:");
            String roadType = Input.input("road type:");
            return new Bike(model, seats, speed, roadType);
        } catch (NumberFormatException e) {
            System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
            return null;
        }
    }

    public String getEnergyRating() {
        return Bike.defaultEnergyRating;
    }

    public String getEnergySource() {
        return Bike.defaultEnergySource;
    }

    public boolean equals(Object other) {
        return super.equals(other) && NonMotorized.equals(this, other);
    }

    public String toString() {
        return super.toString() + " " + NonMotorized.toString(this);
    }
}
