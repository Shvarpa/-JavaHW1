//Pavel Shvarchov - 319270583
//Mordy Dabah - 203507017

package base;

public class SpyDrone extends Drone implements Inputable {

    public SpyDrone(String energySource) {
        super("Classified", 1, 50, "Army", energySource, "c");
    }

    static SpyDrone inputSpyDrone() {
        String enertgySource = Inputable.input("energy source:");
        return new SpyDrone(enertgySource);
    }
}
