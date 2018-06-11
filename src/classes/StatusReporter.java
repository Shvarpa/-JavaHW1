package classes;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import gui.VehicleSelectButton;
import interfaces.IVehicle;

public class StatusReporter implements IVehicle {
	
	public enum VehicleStatus{
		BUY,TEST_DRIVE,STANDBY;
		@Override
		public String toString() {
			switch(this) {
			case BUY:				return "during buying process";
			case TEST_DRIVE:		return "during test drive process";
			case STANDBY: default:	return "in standby";
			}
		}
	};

	private IVehicle vehicle;
	private VehicleStatus status;
	private List<VehicleSelectButton> drawings;
	private PropertyChangeSupport eventHandler;
	
	public StatusReporter(IVehicle vehicle, VehicleStatus status) {
		if (vehicle instanceof StatusReporter) {
			StatusReporter other = (StatusReporter)vehicle;
			other.setStatus(status);
			clone(other);
			updateDrawings();
		}
		else {
			setVehicle(vehicle);
			setStatus(status);
			this.drawings = new ArrayList<VehicleSelectButton>();
			this.eventHandler = new PropertyChangeSupport(this);
		}
	}
	
	private void setVehicle(IVehicle v) {
		this.vehicle = v;
	}
	public IVehicle getVehicle() {
		return vehicle;
	}
	private void setStatus(VehicleStatus s) {
		this.status = s;
	}
	public VehicleStatus getStatus() {
		return status;
	}
	
	///intended shallow clone of other StatusReporter
	private void clone(StatusReporter other) {
		this.drawings = other.drawings;
		setVehicle(other.vehicle);
		setStatus(other.status);
		this.eventHandler = other.eventHandler;
	}
	
	private void updateDrawings() {
		eventHandler.firePropertyChange("statusUpdate", null, null);
	}
	
	private void updateDrawing(VehicleSelectButton drawing) {
		drawing.setVehicle(this);
	}
	
	@Override
	public VehicleSelectButton draw() {
		VehicleSelectButton drawing = vehicle.draw();
		updateDrawing(drawing);
		drawings.add(drawing);
		eventHandler.addPropertyChangeListener("statusUpdate",(event)->{
			updateDrawing(drawing);
		});
		return drawing;
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
		return vehicle.toString() + " " +  status + ".";
	}
	
	@Override
	public String getUniqueID() {
		return vehicle.getUniqueID();
	}
	
	////copy restores status to default, which is in standby, this method is made for 
	public StatusReporter(StatusReporter toCopy) {
		this.drawings = new ArrayList<VehicleSelectButton>();
		this.eventHandler = new PropertyChangeSupport(this);
		setVehicle(toCopy.getVehicle().clone());
		setStatus(VehicleStatus.STANDBY);
	}
	
	@Override
	public StatusReporter clone() {
		return new StatusReporter(this);
	}
}
