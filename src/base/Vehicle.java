package base;

public abstract class Vehicle {
	private String model;
	private double totalDistance;
	private int seats;
	private float speed;
	
	public String getModel() {return this.model;}
	public double getTotalDistance() {return this.totalDistance;}
	public int getSeats() {return this.seats;}
	public float getSpeed() {return this.speed;}

	
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
	
	public String toString() {
		return this.getClass().getName()+": Model: "+this.model+", traveled:"+this.totalDistance+" Km, Max Speed of "+this.speed+" Mph, can carry max of "+this.seats+" people.";		
	}
	
	public boolean equals(Vehicle other) {
		return this.model.equals(other.getModel()) && this.totalDistance==other.getTotalDistance() && this.seats==other.getSeats() && this.speed==other.getSpeed();
	}
}
