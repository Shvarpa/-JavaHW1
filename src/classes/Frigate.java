//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.Collection;

import interfaces.IAirVehicle;
import interfaces.Motorized;

public class Frigate extends SeaVehicle implements Motorized {   
	public final static String defaultFlag="Israel";
	public final static double defaultAvgFuelConsumption=500;
	public final static double defaultAvgMotorLifespan=4;
    
    public Frigate(String model, int seats, float speed, boolean withWindDiraction) {
        super(model, seats, speed, withWindDiraction, Frigate.defaultFlag);
    }

    public Double getAvgFuelConsumption() {
        return Frigate.defaultAvgFuelConsumption;
    }

    public Double getAvgMotorLifespan() {
        return Frigate.defaultAvgMotorLifespan;
    }

    public String toString() {
        return super.toString() + " " + Motorized.toString(this);
    }

    public boolean equals(Object other) {
        return super.equals(other) && Motorized.equals(this, other);
    }
    
    public Frigate(Frigate toCopy) {
    	super(toCopy);
    }
    @Override
    public Frigate clone() {
    	return new Frigate(this);
    }
    
	@Override
	public Collection<Class<?>> getInterfaces(){
		Collection<Class<?>> interfaces = super.getInterfaces();
		interfaces.add(Motorized.class);
		return interfaces;
	}
}

