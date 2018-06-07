//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import gui.Utilities;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import interfaces.IVehicle;


public class Database {
	
	private HashMap<String,IVehicle> vehicleDatabase;
	private HashMap<String,ISeaVehicle> seaVehicleDatabase;
	private HashMap<String,IAirVehicle> airVehicleDatabase;
	private HashMap<String,ILandVehicle> landVehicleDatabase;
	private ReadWriteLock lock = new ReentrantReadWriteLock(true);

	
	public boolean isEmpty() {
		lock.readLock().lock();
		if (vehicleDatabase.isEmpty()) {
			Utilities.log("no vehicles in database, returning\n");
			lock.readLock().unlock();
			return true;
		}
		lock.readLock().unlock();
		return false;
	}
	
	public boolean hasSeaVehicles() {
		lock.readLock().lock();
		boolean result =!seaVehicleDatabase.isEmpty();
		lock.readLock().unlock();
		return result;
	}
	
	
	public boolean addVehicle(IVehicle currVehicle) {
		if (currVehicle != null) {
			lock.writeLock().lock();
			this.vehicleDatabase.put(currVehicle.getUniqueID(),currVehicle);
			if (currVehicle instanceof ISeaVehicle) {
				this.seaVehicleDatabase.put(currVehicle.getUniqueID(),(ISeaVehicle) currVehicle);
			} 
			if (currVehicle instanceof ILandVehicle) {
				this.landVehicleDatabase.put(currVehicle.getUniqueID(),(ILandVehicle) currVehicle);
			}
			if (currVehicle instanceof IAirVehicle) {
				this.airVehicleDatabase.put(currVehicle.getUniqueID(),(IAirVehicle) currVehicle);
			}
			lock.writeLock().unlock();
			Utilities.log("the vehicle: " + currVehicle.toString() + " was added succesfully, returning");
			return true;
		}
		return false;
	}

	public boolean buyVehicle(IVehicle currVehicle) {
		if (vehicleDatabase.containsKey(currVehicle.getUniqueID())) {
			lock.writeLock().lock();
			for (HashMap<String,?> hM:Arrays.asList(vehicleDatabase,seaVehicleDatabase,airVehicleDatabase,landVehicleDatabase))
				hM.remove(currVehicle.getUniqueID());
			lock.writeLock().unlock();
			Utilities.log("the vehicle: " + currVehicle.toString() + " was bought succesfully, returning");
			return true;
		}
		Utilities.log("the vehicle doesnt exist, returning");
		return false;
	}

	public boolean testDriveVehicle(IVehicle currVehicle, double distance) {
		if (vehicleDatabase.containsKey(currVehicle.getUniqueID())) {
			lock.writeLock().lock();
			vehicleDatabase.get(currVehicle.getUniqueID()).moveDistance(distance);
			lock.writeLock().unlock();
			Utilities.log("the vehicle: " + currVehicle.toString() + " was taken for a " + distance + "km test-drive succesfully, returning");
			return true;
		}
		Utilities.log("the vehicle doesnt exist, returning");
		return false;
	}

	public boolean resetDistances() {
		if (isEmpty()) {
			Utilities.log("no vehicles to reset distance, returning");
			return false;
		}
		lock.writeLock().lock();
		for (IVehicle v : vehicleDatabase.values()) {
			v.resetTotalDistance();
		}
		lock.writeLock().unlock();
		Utilities.log("all vehicle distances were reset succesfully, returning");
		return true;
	}

	public boolean changeFlags(String flag) {
		lock.readLock().lock();
		if (seaVehicleDatabase.isEmpty()) {
			lock.readLock().unlock();
			Utilities.log("no vehicles to change flags, returning");
			return false;
		}
		lock.readLock().unlock();
		lock.writeLock().lock();
		for (ISeaVehicle sV : seaVehicleDatabase.values()) {
			sV.setFlag(flag);
		}
		lock.writeLock().unlock();
		Utilities.log("all vehicle flags were changed to " + flag + " succesfully, returning\n");
		return true;
	}


	public Database() {
		vehicleDatabase = new HashMap<String, IVehicle>();
		seaVehicleDatabase = new HashMap<String, ISeaVehicle>();
		airVehicleDatabase = new HashMap<String, IAirVehicle>();
		landVehicleDatabase = new HashMap<String, ILandVehicle>();
	}
	
	public IVehicle findVehicle(String vehicleID) {
		return vehicleDatabase.get(vehicleID);
	}
	
	public List<IVehicle> getVehicles() {
		lock.readLock().lock();
		HashMap<String,IVehicle> result = vehicleDatabase;
		lock.readLock().unlock();
		return (List<IVehicle>) result.values();
	}
	
}
