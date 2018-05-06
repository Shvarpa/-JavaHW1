package gui;

import java.util.List;

import javax.swing.JComponent;
import classes.Database;
import classes.Vehicle;

public class DBConnect extends JComponent {
	static DBConnect self=null;
	private Database db;
	
	private DBConnect() {
		db=new Database();
	}
	
	public void addVehicle(Vehicle v) {
		db.addVehicle(v);
		firePropertyChange("addVhicle", null, v);
	}
	
	public void buyVehicle(Vehicle v) {
		db.buyVehicle(v);
		firePropertyChange("buyVehicle", v, null);
	}
	
	public void testDriveVehicle(Vehicle v,double d) {
		db.testDriveVehicle(v,d);
		firePropertyChange("testDriveVehicle", null, d);
	}
	
	public void resetDistances() {
		db.resetDistances();
		firePropertyChange("resetDistances", null, null);
	}
	
	public void changeFlags(String flag) {
		db.changeFlags(flag);
		firePropertyChange("changeFlags", null, flag);
	}
	
	public List<Vehicle> getVehicles(){
		return db.getVehicles();
	}
	
	public boolean hasSeaVehicles() {
		return db.hasSeaVehicles();
	}
	
	public static DBConnect getConnection() {
		if(self==null) {
			self=new DBConnect();
		}
		return self;
	}
}
