//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.util.List;

import javax.swing.JComponent;
import classes.Database;
import classes.Vehicle;

public class DBConnect extends JComponent {
	static volatile DBConnect self = null;
	private Database db;

	private DBConnect() {
		db = new Database();
	}

	public synchronized void addVehicle(Vehicle v) {
		db.addVehicle(v);
		firePropertyChange("addVehicle", null, v);
	}

	public synchronized void buyVehicle(Vehicle v) {
		db.buyVehicle(v);
		firePropertyChange("buyVehicle", v, null);
	}

	public synchronized void testDriveVehicle(Vehicle v, double d) {
		db.testDriveVehicle(v, d);
		firePropertyChange("testDriveVehicle", null, d);
	}

	public synchronized void resetDistances() {
		db.resetDistances();
		firePropertyChange("resetDistances", null, null);
	}

	public synchronized void changeFlags(String flag) {
		db.changeFlags(flag);
		firePropertyChange("changeFlags", null, flag);
	}

	public synchronized List<Vehicle> getVehicles() {
		return db.getVehicles();
	}

	public synchronized boolean hasSeaVehicles() {
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
