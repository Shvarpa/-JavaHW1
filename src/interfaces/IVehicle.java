package interfaces;

import java.util.Collection;

import gui.VehicleSelectButton;

public interface IVehicle {
	public VehicleSelectButton draw();
	public String getModel();
	public Double getTotalDistance();
	public Integer getSeats();
	public Float getSpeed();
	public void resetTotalDistance();
	public Boolean moveDistance(double distance);
	public String toString();
	public String getUniqueID();
	public IVehicle clone();
	public Collection<Class<?>> getInterfaces();
}
