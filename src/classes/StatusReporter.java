package classes;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import gui.VehicleSelectButton;
import interfaces.IVehicle;


public class StatusReporter extends VehicleDelegator{
	
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

	private VehicleStatus status;
	private List<VehicleSelectButton> drawings;
	private PropertyChangeSupport eventHandler;
	
	public StatusReporter(IVehicle vehicle, VehicleStatus status) {
		super(vehicle);
		if (vehicle instanceof StatusReporter) {
			StatusReporter other = (StatusReporter)vehicle;
			other.setStatus(status);
			replaceSelf(other);
			updateDrawings();
		}
		else {
			setStatus(status);
			this.drawings = new ArrayList<VehicleSelectButton>();
			this.eventHandler = new PropertyChangeSupport(this);
		}
	}
	
	private void setStatus(VehicleStatus s) {
		this.status = s;
	}
	
	public VehicleStatus getStatus() {
		return status;
	}
	
	///intended shallow clone of other StatusReporter
	private void replaceSelf(StatusReporter other) {
		this.drawings = other.drawings;
		setVehicle(other.getVehicle());
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
		VehicleSelectButton drawing = getVehicle().draw();
		updateDrawing(drawing);
		drawings.add(drawing);
		eventHandler.addPropertyChangeListener("statusUpdate",(event)->{
			updateDrawing(drawing);
		});
		return drawing;
	}
	
	@Override
	public String toString() {
		return getVehicle().toString() + " " +  status + ".";
	}
	
	
	////copy restores status to default, which is in standby, this method is made for clone purposes only
	public StatusReporter(StatusReporter toCopy) {
		super(toCopy.getVehicle().clone());
		this.drawings = new ArrayList<VehicleSelectButton>();
		this.eventHandler = new PropertyChangeSupport(this);
		setStatus(VehicleStatus.STANDBY);
	}
	
	@Override
	public StatusReporter clone() {
		return new StatusReporter(this);
	}
}
