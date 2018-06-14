//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.Collection;

public class PlayDrone extends Drone {

	public final static String defaultModel = "Toy";
	public final static String defaultVehicleUse = "Civilian";
	public final static String defaultEnergyRating = "A";
	public final static String defaultEnergySource = "Manual";
	public final static int defaultSeats = 0;
	public final static float defaultSpeed = 10;

	public PlayDrone() {
		super(PlayDrone.defaultModel, PlayDrone.defaultSeats, PlayDrone.defaultSpeed, PlayDrone.defaultVehicleUse,
				PlayDrone.defaultEnergySource, PlayDrone.defaultEnergyRating);
	}
	
	public PlayDrone(PlayDrone toCopy) {
		super(toCopy);
	}
	@Override
	public PlayDrone clone() {
		return new PlayDrone(this);
	}
}
