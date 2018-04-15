package base;
import base.Inputable;

public class SpyDrone extends Drone implements Inputable{
	
	static SpyDrone inputSpyDrone() {
		String enertgySource=Inputable.input("energy source (String):");
		return new SpyDrone(enertgySource);
	}
	
	public SpyDrone(String energySource) {
		super("Classified",1,50,"Army",energySource,"c");
	}
}
