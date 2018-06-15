//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.Collection;
import interfaces.NonMotorized;

public class Bike extends LandVehicle implements NonMotorized {
	
	public static int defaultWheels = 2;
	public static final String defaultEnergyRating = "A";
	public static final String defaultEnergySource = "Manual";

	
    public Bike(String model, int seats, float speed, String roadType) {
        super(model, seats, speed, Bike.defaultWheels, roadType);
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
    public Bike(Bike toCopy) {
    	super(toCopy);
    }
    @Override
    public Bike clone() {
    	return new Bike(this);
    }
    
	@Override
	public Collection<Class<?>> getInterfaces(){
		Collection<Class<?>> interfaces = super.getInterfaces();
		interfaces.add(NonMotorized.class);
		return interfaces;
	}
}
