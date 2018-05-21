//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.ArrayList;
import java.util.List;

import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;

public class Database {
	private List<Vehicle> vehicleDatabase;
	private List<ISeaVehicle> seaVehicleDatabase;
	private List<IAirVehicle> airVehicleDatabase;
	private List<ILandVehicle> landVehicleDatabase;

	private boolean isEmpty() {
		if (vehicleDatabase.isEmpty()) {
			System.out.println("no vehicles in database, returning\n");
			return true;
		}
		return false;
	}
	
	public boolean hasSeaVehicles() {
		return !seaVehicleDatabase.isEmpty();
	}
	
	
	public boolean addVehicle(Vehicle currVehicle) {
		if (currVehicle != null) {
			this.vehicleDatabase.add(currVehicle);
			if (currVehicle instanceof ISeaVehicle) {
				this.seaVehicleDatabase.add((ISeaVehicle) currVehicle);
			} 
			if (currVehicle instanceof ILandVehicle) {
				this.landVehicleDatabase.add((ILandVehicle) currVehicle);
			}
			if (currVehicle instanceof IAirVehicle) {
				this.airVehicleDatabase.add((IAirVehicle) currVehicle);
			} 
			else;
			System.out.println("the vehicle: " + currVehicle.toString() + " was added succesfully, returning");
			return true;
		}
		return false;
	}

	public boolean buyVehicle(Vehicle currVehicle) {
		if (vehicleDatabase.contains(currVehicle)) {
			this.vehicleDatabase.remove(currVehicle);
			if (currVehicle instanceof ISeaVehicle)
				this.seaVehicleDatabase.remove((ISeaVehicle)currVehicle);
			if (currVehicle instanceof ILandVehicle)
				this.landVehicleDatabase.remove((ILandVehicle)currVehicle);
			System.out.println("the vehicle: " + currVehicle.toString() + " was bought succesfully, returning");
			return true;
		}
		System.out.println("vehicle doesnt exist, returning");
		return false;
	}

	public boolean testDriveVehicle(Vehicle currVehicle, double distance) {
		if (vehicleDatabase.contains(currVehicle)) {
			vehicleDatabase.get(vehicleDatabase.indexOf(currVehicle)).moveDistance(distance);
			System.out.println("the vehicle: " + currVehicle.toString() + " was taken for a " + distance
					+ "km test-drive succesfully, returning");
			return true;
		}
		return false;
	}

	public boolean testDriveVehicle(Vehicle currVehicle) {
		if (vehicleDatabase.contains(currVehicle)) {
			double distance;
			try {
				distance = Input.inputDouble("enter test drive distance:");
			}
			catch (NumberFormatException e) {
				System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
				return false;
			}
			return testDriveVehicle(currVehicle, distance);
		}
		return false;
	}

	public boolean resetDistances() {
		if (isEmpty()) {
			System.out.println("no vehicles to reset distance, returning");
			return false;
		}
		for (Vehicle v : vehicleDatabase) {
			v.resetTotalDistance();
		}
		System.out.println("all vehicle distances were reset succesfully, returning");
		return true;
	}

	public boolean changeFlags(String flag) {
		if (seaVehicleDatabase.isEmpty()) {
			System.out.println("no vehicles to change flags, returning");
			return false;
		}
		for (ISeaVehicle sV : seaVehicleDatabase) {
			sV.setFlag(flag);
		}
		System.out.println("all vehicle flags were changed to " + flag + " succesfully, returning\n");
		return true;
	}


	public Database() {
		vehicleDatabase = new ArrayList<>();
		seaVehicleDatabase = new ArrayList<>();
		airVehicleDatabase = new ArrayList<>();
		landVehicleDatabase = new ArrayList<>();
	}

	public List<Vehicle> getVehicles() {
		return vehicleDatabase;
	}

}
