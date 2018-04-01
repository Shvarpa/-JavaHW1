package base;

public abstract class LandVehicle extends Vehicle {
	private static String[] roadTypes= {"dirt","constructed"};
	
	private int wheels;
	private String roadType;
	
	private void setWheels(int wheels) {this.wheels=wheels;}
	private void setRoadType(String roadType) {this.roadType=roadType;}
	
	protected LandVehicle(String model, int seats, float speed,int wheels, String roadType) {
		super(model,seats,speed);
		setWheels(wheels);
		setRoadType(roadType);
	}
	
}
