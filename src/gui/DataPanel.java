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


public class DataPanel extends JPanel{
	
	private Database db = Database.getInstance();
	private FlowLayout flow= new FlowLayout(FlowLayout.LEFT, 15, 10);
	public List<VehicleSelectButton> vehicleSelectButtons= new ArrayList<VehicleSelectButton>();
	
	public void refresh() {
		for(VehicleSelectButton vS:vehicleSelectButtons) {
			remove(vS);
		}
		vehicleSelectButtons.clear();
		for(Vehicle v:db.getVehicles()) {
			vehicleSelectButtons.add(new VehicleSelectButton(v));
		}
		for(VehicleSelectButton vS:vehicleSelectButtons) {
			add(vS);
		}
		revalidate();
	}
	
	public DataPanel() {
		setLayout(flow);
		refresh();
	}

}
