package classes;

import gui.VehicleSelectButton;
import interfaces.IVehicle;

public class StatusReporter implements IVehicle {
	
	private IVehicle vehicle;
	private String status;
	public StatusReporter(IVehicle v,String status) {
		this.vehicle = v;
		this.status = status;
	}
	
	@Override
	public VehicleSelectButton draw() {
		VehicleSelectButton vS = vehicle.draw();
		vS.setToolTipText(toString());
		return vS;
	}

	@Override
	public String getModel() {
		return vehicle.getModel();
	}

	@Override
	public double getTotalDistance() {
		return vehicle.getTotalDistance();
	}

	@Override
	public int getSeats() {
		return vehicle.getSeats();
	}

	@Override
	public float getSpeed() {
		return vehicle.getSpeed();
	}


	@Override
	public void resetTotalDistance() {
		vehicle.resetTotalDistance();
	}

	@Override
	public boolean moveDistance(double distance) {
		return vehicle.moveDistance(distance);
	}
	
	@Override
	public String toString() {
		return vehicle.toString() + status;
	}
	
	@Override
	public String getUniqueID() {
		return vehicle.getUniqueID();
	}
}
