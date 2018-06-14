package classes;

import java.util.Collection;

import gui.VehicleSelectButton;
import interfaces.Commercial;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import interfaces.IVehicle;
import interfaces.Motorized;
import interfaces.NonMotorized;

public class IconDraw extends VehicleDelegator {
	
	private String picturePath;
	public IconDraw(IVehicle v,String p) {
		super(v);
		setPicturePath(p);
	}
	
	private void setPicturePath(String p) {
		this.picturePath = p;
	}
	
	public String getPicturePath() {
		return picturePath;
	}

	
	@Override
	public VehicleSelectButton draw() {
		VehicleSelectButton drawing = getVehicle().draw();
		drawing.setImage(picturePath);
		return drawing;
	}

	public IconDraw(IconDraw toCopy) {
		super(toCopy.getVehicle().clone());
		setPicturePath(toCopy.getPicturePath());
	}
	
	@Override
	public IconDraw clone() {
		return new IconDraw(this);
	}
}
