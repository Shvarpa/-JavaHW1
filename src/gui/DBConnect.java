//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import classes.Database;
import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;

public class DBConnect extends JComponent {

	static volatile DBConnect self = null;
	private Database db;

	private DBConnect() {
		db = new Database();
	}

	public void addVehicle(Vehicle v) {
		db.addVehicle(v);
		firePropertyChange("addVehicle", null, v);
	}

	public void buyVehicle(Vehicle v) {
		db.buyVehicle(v);
		firePropertyChange("buyVehicle", v, null);
	}

	public void testDriveVehicle(Vehicle v, double d) {
			db.testDriveVehicle(v, d);
			firePropertyChange("testDriveVehicle", v, v.getTotalDistance());
	}

	public void resetDistances() {
		db.resetDistances();
		firePropertyChange("resetDistances", null, null);
	}

	public void changeFlags(String flag) {
		db.changeFlags(flag);
		firePropertyChange("changeFlags", null, flag);
	}

	public List<Vehicle> getVehicles() {
		return db.getVehicles();
	}

	public boolean hasSeaVehicles() {
		return db.hasSeaVehicles();
	}

	public static DBConnect getConnection() {
		if (self == null) {
			synchronized (DBConnect.class) {
				if (self == null) {
					self = new DBConnect();
				}
			}
		}
		return self;
	}
}
