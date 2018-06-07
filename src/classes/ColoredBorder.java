package classes;

import java.awt.Color;

import javax.swing.BorderFactory;

import gui.VehicleSelectButton;
import interfaces.IVehicle;

public class ColoredBorder implements IVehicle {
	private static int borderThickness = 3;
	private IVehicle vehicle;
	private Color color;

	public ColoredBorder(IVehicle v,Color c) {
		this.vehicle = v;
		this.color = c;
	}


	@Override
	public VehicleSelectButton draw() {
		VehicleSelectButton vS = vehicle.draw();
		if (color!=null)
			vS.setBorder(BorderFactory.createCompoundBorder(vS.getBorder(),BorderFactory.createLineBorder(color, ColoredBorder.borderThickness)));
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
		return vehicle.toString();
	}


	@Override
	public String getUniqueID() {
		return vehicle.getUniqueID();
	}

}
