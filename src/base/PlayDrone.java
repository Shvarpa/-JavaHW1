package base;

public class PlayDrone extends Drone{
	
	static PlayDrone inputPlayDrone() {
		return new PlayDrone();
	}
	
	public PlayDrone() {
		super("Toy",0,10,"Civillian","Manual","a");
	}
}
