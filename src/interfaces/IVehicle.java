package interfaces;

import gui.VehicleSelectButton;

public interface IVehicle {
	public VehicleSelectButton draw();
	public String getModel();
	public double getTotalDistance();
	public int getSeats();
	public float getSpeed();
	public void resetTotalDistance();
	public boolean moveDistance(double distance);
	public String toString();
	public String getUniqueID();
	public IVehicle clone();
}
