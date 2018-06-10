package classes;

import java.awt.Color;

import gui.VehicleSelectButton;
import interfaces.IVehicle;

public class IconDraw implements IVehicle {
	
	private IVehicle vehicle;
	private String picturePath;
	
	public IconDraw(IVehicle v,String p) {
		setVehicle(v);
		setPicturePath(p);
	}
	
	private void setVehicle(IVehicle v) {
		this.vehicle = v;
	}
	public IVehicle getVehicle() {
		return vehicle;
	}
	private void setPicturePath(String p) {
		this.picturePath = p;
	}
	public String getPicturePath() {
		return picturePath;
	}

	
	@Override
	public VehicleSelectButton draw() {
		VehicleSelectButton drawing = vehicle.draw();
		drawing.setImage(picturePath);
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
		return vehicle.toString();
	}
	
	@Override
	public String getUniqueID() {
		return vehicle.getUniqueID();
	}
	
	public IconDraw(IconDraw toCopy) {
		setVehicle(toCopy.getVehicle().clone());
		setPicturePath(toCopy.getPicturePath());
	}
	
	@Override
	public IconDraw clone() {
		return new IconDraw(this);
	}
}
