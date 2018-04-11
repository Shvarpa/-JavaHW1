package base;

import java.util.Arrays;

public abstract class LandVehicle extends Vehicle {
	private static String[] possibleRoadType= {"dirt","constructed"};
	
	private static boolean checkRoadTypeInput(String roadType) {
		return Arrays.asList(LandVehicle.possibleRoadType).contains(roadType);
	}
	
	private int wheels;
	private String roadType;
	
	public int getWheels() {return this.wheels;}
	public String getRoadType() {return this.roadType;}	
	private void setWheels(int wheels) {this.wheels=wheels;}
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
	
	public String toString() {
		return super.toString()+" has "+this.wheels+" wheels, can ride on "+this.roadType+" roads.";
	}
	
	public boolean equals(LandVehicle other) {
		return super.equals(other) && this.wheels==other.getWheels() && this.roadType.equals(other.getRoadType());
	}
}
