//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

public class PlayDrone extends Drone {

    public PlayDrone() {
        super("Toy", 0, 10, "Civillian", "Manual", "a");
    }

    public static PlayDrone inputPlayDrone() {
        return new PlayDrone();
    }
}
