//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

public class SpyDrone extends Drone {
	
	public final static String defaultModel="Classified";
	public final static String defaultVehicleUse="Army";
	public final static String defaultEnergyRating="C";
	public final static int defaultSeats = 1;
	public final static float defaultSpeed = 50;

    public SpyDrone(String energySource) {
        super(SpyDrone.defaultModel, SpyDrone.defaultSeats, SpyDrone.defaultSpeed, SpyDrone.defaultVehicleUse, energySource, SpyDrone.defaultEnergyRating);
    }
    
    public SpyDrone(SpyDrone toCopy) {
    	super(toCopy);
    }
    
    @Override
    public SpyDrone clone() {
    	return new SpyDrone(this);
    }
}
