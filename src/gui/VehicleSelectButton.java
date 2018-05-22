//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import classes.Vehicle;
import gui.Images.ImageOpener;

public class VehicleSelectButton extends JRadioButton {

	private Vehicle vehicle;

	public VehicleSelectButton(Vehicle v) {
		setVehicle(v);
		ImageIcon img = ImageOpener.createImageIcon(this.vehicle.getImagePath());
		setImage(img);
	}
	
	public VehicleSelectButton(Vehicle v,Dimension d) {
		setVehicle(v);
		ImageIcon img = ImageOpener.scaleImg(ImageOpener.createImageIcon(this.vehicle.getImagePath()), d);
		setImage(img);
	}
	
	public VehicleSelectButton(Vehicle v,int width,int height) {
		setVehicle(v);
		ImageIcon img = ImageOpener.scaleImg(ImageOpener.createImageIcon(this.vehicle.getImagePath()), width,height);
		setImage(img);
	}
	
	private void setVehicle(Vehicle v) {
		vehicle=v;
		setToolTipText(vehicle.toString());
	}
	
	
	private void setImage(ImageIcon img) {
		if(img!=null) {
			setIcon(img);
			setBorderPainted(true);
		}
		else {
			setText(vehicle.getModel());
		}
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
}
