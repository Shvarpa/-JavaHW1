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
	
	private void setModel(String model) {this.model=model;}
	private void setTotalDistance(double totalDistance) {this.totalDistance=totalDistance;}
	private void setSeats(int seats){this.seats=seats;}
	private void setSpeed(float speed){this.speed=speed;}

	protected boolean moveDistance(float distance)
	{
		if (distance>0) {
			totalDistance+=distance; 
			return true;
			}
		else return false;
	}
	
	protected Vehicle(String model, int seats, float speed) {
		setModel(model);
		setTotalDistance(0);
		setSeats(seats);
		setSpeed(speed);
	}
	
	public String toString() {
		return this.getClass().getName()+": Model: "+this.model+", traveled:"+this.totalDistance+" Km, Max Speed of "+this.speed+" Mph, can carry max of "+this.seats+" people.";		
	}
	
	public boolean equals(Vehicle other) {
		return this.model.equals(other.getModel()) && this.totalDistance==other.getTotalDistance() && this.seats==other.getSeats() && this.speed==other.getSpeed();
	}
}
