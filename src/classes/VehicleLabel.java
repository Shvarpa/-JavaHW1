package classes;

import javax.swing.JLabel;

public class VehicleLabel extends JLabel {
	
	private Vehicle vehicle;
	
	public VehicleLabel(Vehicle v) {
		this.vehicle = v;
		setText(v.toString());
	}
	
	public Vehicle getVehicle() {return vehicle;}
}
