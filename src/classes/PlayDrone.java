//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

public class PlayDrone extends Drone {
	
	public final static String defaultModel="Classified";
	public final static String defaultVehicleUse="Civilian";
	public final static String defaultEnergyRating="a";
	
    public PlayDrone() {
        super(PlayDrone.defaultModel, 0, 10, PlayDrone.defaultVehicleUse, "Manual", PlayDrone.defaultEnergyRating);
    }

    public static PlayDrone inputPlayDrone() {
        return new PlayDrone();
    }
}
