//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.Collection;
import interfaces.NonMotorized;

public abstract class Drone extends AirVehicle implements NonMotorized {

    private String energyRating; //NonMotorized Interface
    private String energySource;

    protected Drone(String model, int seats, float speed, String vehicleUse, String energySource, String energyRating) {
        super(model, seats, speed, vehicleUse);
        setEnergySource(energySource);
        setEnergyRating(energyRating);
    }

    private boolean setEnergyRating(String energyRating) {
        if (NonMotorized.checkEnergyRatingInput(energyRating)) {
            this.energyRating = energyRating;
            return true;
        }
        System.out.println("\n incorrect 'energy Rating' was entered");
        return false;
    }

    private boolean setEnergySource(String energySource) {
        this.energySource = energySource;
        return true;
    }

    public String getEnergyRating() {
        return energyRating;
    }

    public String getEnergySource() {
        return energySource;
    }

    public String toString() {
        return super.toString() + " " + NonMotorized.toString(this);
    }

    public boolean equals(Object other) {
        return super.equals(other) && NonMotorized.equals(this, other);
    }
    
    public Drone(Drone toCopy) {
    	super(toCopy);
    	setEnergyRating(toCopy.getEnergyRating());
    	setEnergySource(toCopy.getEnergySource());
    }
    
	@Override
	public Collection<Class<?>> getInterfaces(){
		Collection<Class<?>> interfaces = super.getInterfaces();
		interfaces.add(NonMotorized.class);
		return interfaces;
	}
}
