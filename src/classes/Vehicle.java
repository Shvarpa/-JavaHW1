//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.UUID;

import gui.VehicleSelectButton;
import interfaces.IVehicle;

public abstract class Vehicle implements IVehicle{
		
	@Override
	public VehicleSelectButton draw() {
		return new VehicleSelectButton(this);
	}
			
	private double totalDistance;
	private String model;
	private int seats;
	private float speed;
	private String id = generateUniqueId();
	
	private String generateUniqueId() {
		return UUID.randomUUID().toString();
	}

	protected Vehicle(String model, int seats, float speed) {
		setModel(model);
		setTotalDistance(0);
		setSeats(seats);
		setSpeed(speed);
	}
	
	@Override
	public String getModel() {
		return this.model;
	}

	private void setModel(String model) {
		this.model = model;
	}
	
	@Override
	public double getTotalDistance() {
		return this.totalDistance;
	}

	private void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	@Override
	public int getSeats() {
		return this.seats;
	}

	private void setSeats(int seats) {
		if (seats < 0) {
			System.out.println("\n incorrect number of seats , 0 was set to default");
			seats = 0;
		}
		this.seats = seats;
	}
	
	@Override
	public float getSpeed() {
		return this.speed;
	}

	private void setSpeed(float speed) {
		if (speed < 0) {
			System.out.println("\n incorrect maximum speed , 0 was set to default");
			speed = 0;
		}
		this.speed = speed;
	}
	@Override
	public void resetTotalDistance() {
		this.setTotalDistance(0);
	}
	
	@Override
	public boolean moveDistance(double distance) {
		if (distance > 0) {
			totalDistance += distance;
			return true;
		} else
			return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":Model: " + this.model + ", traveled:" + this.totalDistance
				+ " Km, Max Speed of " + this.speed + " Mph, can carry max of " + this.seats + " people.";
	}

	public boolean equals(Object other) {
		if (other instanceof IVehicle) {
			return this.model.equals(((Vehicle) other).getModel())
					&& this.totalDistance == ((Vehicle) other).getTotalDistance()
					&& this.seats == ((Vehicle) other).getSeats() && this.speed == ((Vehicle) other).getSpeed()
					&& this.getTotalDistance() == ((Vehicle) other).getTotalDistance();
		}
		return false;
	}
	
	@Override
	public String getUniqueID() {
		return id;
	}
	
	public Vehicle (Vehicle toCopy) {
		setModel(toCopy.getModel());
		setSeats(toCopy.getSeats());
		setSpeed(toCopy.getSpeed());
		setTotalDistance(toCopy.getTotalDistance());
	}
	
	abstract public Vehicle clone();
}
