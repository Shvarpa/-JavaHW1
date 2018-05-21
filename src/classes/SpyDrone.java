//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

public class SpyDrone extends Drone {
	
	public final static String defaultModel="Classified";
	public final static String defaultVehicleUse="Army";
	public final static String defaultEnergyRating="C";


    public SpyDrone(String energySource) {
        super(SpyDrone.defaultModel, 1, 50, SpyDrone.defaultVehicleUse, energySource, SpyDrone.defaultEnergyRating);
    }

}
