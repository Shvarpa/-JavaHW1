package base;

import java.util.Arrays;

public abstract class LandVehicle extends Vehicle {
	private static StringRange possibleRoadType= new StringRange(Arrays.asList("dirt","constructed"));
	
	private static boolean checkRoadTypeInput(String roadType) {
		return LandVehicle.possibleRoadType.containsIgnoreCaps(roadType);
	}
	
	private boolean setRoadType(String roadType) {
		if (LandVehicle.checkRoadTypeInput(roadType)) {
			this.roadType=LandVehicle.possibleRoadType.FixCaps(roadType);
			return true;
		}
		return false;
	}
	
	private int wheels;
	private String roadType;
	
	public int getWheels() {return this.wheels;}
	public String getRoadType() {return this.roadType;}	
	private void setWheels(int wheels) {this.wheels=wheels;}

	protected LandVehicle(String model, int seats, float speed,int wheels, String roadType) {
		super(model,seats,speed);
		setWheels(wheels);
		setRoadType(roadType);
	}
	
	public String toString() {return super.toString()+" has "+this.wheels+" wheels, can ride on "+this.roadType+" roads.";}
	public boolean equals(LandVehicle other) {return super.equals(other) && this.wheels==other.getWheels() && this.roadType.equals(other.getRoadType());}
}
