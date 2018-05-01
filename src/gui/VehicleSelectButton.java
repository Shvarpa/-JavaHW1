package gui;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import classes.Vehicle;

public class VehicleSelectButton extends JRadioButton {
	
	private Vehicle vehicle;
	
	public VehicleSelectButton(Vehicle v) {
		this.vehicle=v;
		ImageIcon img = null;
		String path=this.vehicle.getImagePath();
		if (path!=null) {
			img = ImageOpener.createImageIcon(path);
		}
		if (img!=null) {
			setIcon(img);
		}
		else {
			setText(v.getModel());
		}
	}
	
	public Vehicle getVehicle() {return vehicle;}
}
