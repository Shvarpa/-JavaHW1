package base;

public abstract class Vehicle {
	private String model;
	private double totalDistance;
	private int seats;
	private float speed;
	
	protected boolean moveDistance(float distance)
	{
		if (distance>0) {
			totalDistance+=distance; 
			return true;
			}
		else return false;
	}
	
	protected Vehicle(String model, int seats, float speed) {
		//might need to add totalDistance as default 0 
		this.model=model;
		this.totalDistance=0;
		this.seats=seats;
		this.speed=speed;
	}
}
