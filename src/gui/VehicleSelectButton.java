//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import classes.Vehicle;
import gui.Images.ImageOpener;
import interfaces.IVehicle;

public class VehicleSelectButton extends JRadioButton {
	private static final long serialVersionUID = 1L;
	///a radio button containing a vehicle and it's image
	private IVehicle vehicle;
	
	static Dimension preferedImageSize = new Dimension(50, 50);
	public static void setPreferedImageSize(Dimension size) {
		VehicleSelectButton.preferedImageSize = size;
	}
	
	public VehicleSelectButton(Vehicle v) {
		setVehicle(v);
	}
	
	public void setVehicle(IVehicle v) {
		vehicle=v;
		setToolTipText(vehicle.toString());
		firePropertyChange("revalidate", null, null);
	}
	
	public boolean setImage(String path) {
		ImageIcon img = ImageOpener.createImageIcon(path, preferedImageSize);
		return setImage(img);
	}
	
	public boolean setImage(String path, Dimension d) {
		ImageIcon img = ImageOpener.createImageIcon(path, d);
		return setImage(img);
	}
		
	public boolean setImage(ImageIcon img) {
		if(img!=null) {
			setIcon(img);
			setBorderPainted(true);
			setFocusPainted(true);
			return true;
		}
		else {
			setText(vehicle.getModel());
			return false;
		}
	}
	
	public IVehicle getVehicle() {
		return vehicle;
	}
}
