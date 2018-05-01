package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import classes.Database;
import classes.Vehicle;
import classes.VehicleLabel;


public class DataPanel extends JPanel{
	
	private Database db = Database.getInstance();
	private FlowLayout flow= new FlowLayout(FlowLayout.LEFT, 15, 10);
	public List<VehicleLabel> vehicleLables= new ArrayList<VehicleLabel>();
	
	public void refresh() {
		for(VehicleLabel vL:vehicleLables) {
			remove(vL);
		}
		vehicleLables.clear();
		for(Vehicle v:db.getVehicles()) {
			vehicleLables.add(new VehicleLabel(v));
		}
		for(VehicleLabel vL:vehicleLables) {
			add(vL);
		}
		revalidate();
	}
	
	public DataPanel() {
		setLayout(flow);
		refresh();
	}

}
