//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

public class PlayDrone extends Drone {
	
	public final static String defaultModel="Toy";
	public final static String defaultVehicleUse="Civilian";
	public final static String defaultEnergyRating="A";
	
    public PlayDrone() {
        super(PlayDrone.defaultModel, 0, 10, PlayDrone.defaultVehicleUse, "Manual", PlayDrone.defaultEnergyRating);
    }

}
