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
		testDrivers = new HashMap<String,Semaphore>(3);
		for (String s : Arrays.asList(ILandVehicle.class.getSimpleName(), ISeaVehicle.class.getSimpleName(),
				IAirVehicle.class.getSimpleName()))
			testDrivers.put(s, new Semaphore(1));
	}

	public void addVehicle(Vehicle v) {
		db.addVehicle(v);
		firePropertyChange("addVehicle", null, v);
	}

	public void buyVehicle(Vehicle v) {
		db.buyVehicle(v);
		firePropertyChange("buyVehicle", v, null);
	}

	private HashMap<String,Semaphore> testDrivers;
	public void testDriveVehicle(Vehicle v, double d) {
		SwingUtilities.invokeLater(()->{
			List<String> busyTestDrivers = new ArrayList<String>();
			if (v instanceof ILandVehicle) {
				busyTestDrivers.add(ILandVehicle.class.getSimpleName());
			}
			if (v instanceof ISeaVehicle) {
				busyTestDrivers.add(ISeaVehicle.class.getSimpleName());
			}
			if (v instanceof IAirVehicle) {
				busyTestDrivers.add(IAirVehicle.class.getSimpleName());
			}
			Utilities.invokeInBackground(
				() -> {// background					
					try {	
						for (String driver : busyTestDrivers) testDrivers.get(driver).acquire();	
						Thread.sleep((long)(d*100));
					} catch (InterruptedException e) {
						for (String driver : busyTestDrivers) testDrivers.get(driver).release();
					}
				}, 
				() -> {// after
					db.testDriveVehicle(v, d);
					firePropertyChange("testDriveVehicle", null, d);
					for (String driver : busyTestDrivers) testDrivers.get(driver).release();
				}
			);
		});
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
