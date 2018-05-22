//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;

public class Database {
	
	
	private List<Vehicle> vehicleDatabase;
	private List<ISeaVehicle> seaVehicleDatabase;
	private List<IAirVehicle> airVehicleDatabase;
	private List<ILandVehicle> landVehicleDatabase;

	private void log(String data) {
		new Thread(()->{
			synchronized (System.out) {
				System.out.println(data);
			}
		}).start();
	}
	
	private boolean isEmpty() {
		if (vehicleDatabase.isEmpty()) {
			log("no vehicles in database, returning\n");
			return true;
		}
		return false;
	}
	
	public boolean hasSeaVehicles() {
		return !seaVehicleDatabase.isEmpty();
	}
	
	
	public boolean addVehicle(Vehicle currVehicle) {
		if (currVehicle != null) {
			synchronized (this) {
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
			}
			log("the vehicle: " + currVehicle.toString() + " was added succesfully, returning");
			return true;
		}
		return false;
	}

	public boolean buyVehicle(Vehicle currVehicle) {
		if (vehicleDatabase.contains(currVehicle)) {
			synchronized (this) {
				this.vehicleDatabase.remove(currVehicle);
				if (currVehicle instanceof ISeaVehicle)
					this.seaVehicleDatabase.remove((ISeaVehicle)currVehicle);
				if (currVehicle instanceof ILandVehicle)
					this.landVehicleDatabase.remove((ILandVehicle)currVehicle);
				if (currVehicle instanceof IAirVehicle)
					this.airVehicleDatabase.remove((IAirVehicle)currVehicle);
			}
			log("the vehicle: " + currVehicle.toString() + " was bought succesfully, returning");
			return true;
		}
		log("vehicle" + currVehicle.toString() + "doesnt exist, returning");
		return false;
	}

	public boolean testDriveVehicle(Vehicle currVehicle, double distance) {
		if (vehicleDatabase.contains(currVehicle)) {
			synchronized (this) {
				vehicleDatabase.get(vehicleDatabase.indexOf(currVehicle)).moveDistance(distance);
			}
			log("the vehicle: " + currVehicle.toString() + " was taken for a " + distance + "km test-drive succesfully, returning");
			return true;
		}
		return false;
	}

//	public boolean testDriveVehicle(Vehicle currVehicle) {
//		if (vehicleDatabase.contains(currVehicle)) {
//			double distance;
//			try {
//				distance = Input.inputDouble("enter test drive distance:");
//			}
//			catch (NumberFormatException e) {
//				System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
//				return false;
//			}
//			return testDriveVehicle(currVehicle, distance);
//		}
//		return false;
//	}

	public boolean resetDistances() {
		if (isEmpty()) {
			log("no vehicles to reset distance, returning");
			return false;
		}
		synchronized (this) {
			for (Vehicle v : vehicleDatabase) {
				v.resetTotalDistance();
			}
		}
		log("all vehicle distances were reset succesfully, returning");
		return true;
	}

	public boolean changeFlags(String flag) {
		if (seaVehicleDatabase.isEmpty()) {
			log("no vehicles to change flags, returning");
			return false;
		}
		synchronized (this) {
			for (ISeaVehicle sV : seaVehicleDatabase) {
				sV.setFlag(flag);
			}
		}
		log("all vehicle flags were changed to " + flag + " succesfully, returning\n");
		return true;
	}


	public Database() {
		vehicleDatabase = new ArrayList<>();
		seaVehicleDatabase = new ArrayList<>();
		airVehicleDatabase = new ArrayList<>();
		landVehicleDatabase = new ArrayList<>();
	}

	public synchronized List<Vehicle> getVehicles() {
		return vehicleDatabase;
	}
	
	public synchronized List<String> getVehicleContainerType(Vehicle currVehicle)	{
		if(!vehicleDatabase.contains(currVehicle)) return null;
		else {
			List<String> result = new ArrayList<String>();
			if(currVehicle instanceof ISeaVehicle && seaVehicleDatabase.contains((ISeaVehicle)currVehicle)) 
				result.add(ISeaVehicle.class.getSimpleName());
			if(currVehicle instanceof IAirVehicle && airVehicleDatabase.contains((IAirVehicle)currVehicle)) 
				result.add(IAirVehicle.class.getSimpleName());
			if(currVehicle instanceof ILandVehicle && landVehicleDatabase.contains((ILandVehicle)currVehicle)) 
				result.add(ILandVehicle.class.getSimpleName());
			return result;
		}
	}
}
