//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;


import java.util.ArrayList;
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
	
	private List<Vehicle> duringTestDrive = new ArrayList<Vehicle>();
	public Integer duringTestDriveIndex(Vehicle vehicle) {
		synchronized (duringTestDrive) {
			for(int i=0;i<duringTestDrive.size();i++)
				if (duringTestDrive.get(i)==vehicle)
					return i;
			return null;
		}
	}
	public boolean duringTestDriveContains(Vehicle vehicle) {
		return (duringTestDriveIndex(vehicle) != null);
	}
	public boolean duringTestDriveAdd(Vehicle vehicle) {
		if(duringTestDriveContains(vehicle)) return false;
		else
			synchronized (duringTestDrive) {
				duringTestDrive.add(vehicle);
			}
		return true;
	}
	public void duringTestDriveRemove(Vehicle vehicle) {
		Integer index = duringTestDriveIndex(vehicle);
		if(index == null) return;
		synchronized (duringTestDrive) {
			duringTestDrive.remove((int)index);
		}
	}
}
