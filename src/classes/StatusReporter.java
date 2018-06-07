package classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.property.adapter.PropertyDescriptor.Listener;

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
    private final PropertyChangeSupport eventDispatcher = new PropertyChangeSupport(this);
	public void notifyDrawings() {
		eventDispatcher.firePropertyChange("statusChanged", null, this);
	}
	public StatusReporter(IVehicle v,VehicleStatus status) {
		if(v instanceof StatusReporter) {
			((StatusReporter)v).notifyDrawings();
		}
		this.vehicle = v;
		this.status = status;
	}
	
	List<VehicleSelectButton> drawings = new ArrayList<VehicleSelectButton>();
	private void changeOwner(VehicleSelectButton drawing, IVehicle newOwner) {
		drawings.remove(drawing);
		if (newOwner instanceof StatusReporter) {
			StatusReporter owner = ((StatusReporter)newOwner);
			owner.drawings.add(drawing);
			updateDrawing(drawing, owner);
		}
	}
	
	private void updateDrawing(VehicleSelectButton drawing, IVehicle owner) {
		drawing.setVehicle(owner);
		drawing.setToolTipText(owner.toString());
		PropertyChangeListener listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				drawing.removePropertyChangeListener(this);
				changeOwner(drawing, (IVehicle)event.getNewValue());
			}
		};
		drawing.addPropertyChangeListener("statusChanged",listener);
		drawing.revalidate();
		drawing.repaint();
	}
	
	@Override
	public VehicleSelectButton draw() {
		VehicleSelectButton drawing = vehicle.draw();
		updateDrawing(drawing, this);
		drawings.add(drawing);
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
	
	private String originalToString() {
		if(vehicle instanceof StatusReporter) {
			return ((StatusReporter) vehicle).originalToString();
		}
		return vehicle.toString();
	}
	@Override
	public String toString() {
		return originalToString() + status;
	}
	
	@Override
	public String getUniqueID() {
		return vehicle.getUniqueID();
	}
}
