package base;

import java.util.Arrays;

public abstract class LandVehicle extends Vehicle {
	private static String[] possibleRoadType= {"dirt","constructed"};
	
	private int wheels;
	private String roadType;
	
	private void setWheels(int wheels) {this.wheels=wheels;}
	
	private static boolean checkRoadTypeInput(String roadType) {
		return Arrays.asList(LandVehicle.possibleRoadType).contains(roadType);
	}
	
	private boolean setRoadType(String roadType) {
		if (LandVehicle.checkRoadTypeInput(roadType)) {
			this.roadType=roadType;
			return true;
		}
		return false;
	}
		
	protected LandVehicle(String model, int seats, float speed,int wheels, String roadType) {
		super(model,seats,speed);
		setWheels(wheels);
		setRoadType(roadType);
	}
	
}
