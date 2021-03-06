//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import gui.Utilities;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import interfaces.IVehicle;


public class Database {
	
	private LinkedHashMap<String,IVehicle> vehicleDatabase;
	private LinkedHashMap<String,ISeaVehicle> seaVehicleDatabase;
	private LinkedHashMap<String,IAirVehicle> airVehicleDatabase;
	private LinkedHashMap<String,ILandVehicle> landVehicleDatabase;
	private double totalDistances = 0; 
	private ReadWriteLock lock = new ReentrantReadWriteLock(true);
	private boolean verbose = true;
	
	public void setVerbose(boolean verbose) {this.verbose = verbose;}
	private void log(String data) {if(verbose) Utilities.log(data);}
	
	public boolean isEmpty() {
		lock.readLock().lock();
		if (vehicleDatabase.isEmpty()) {
			log("no vehicles in database, returning\n");
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
			Collection<Class<?>> interfaces = currVehicle.getInterfaces();
			if (interfaces.contains(ISeaVehicle.class)) {
				this.seaVehicleDatabase.put(currVehicle.getUniqueID(),(ISeaVehicle) currVehicle);
			} 
			if (interfaces.contains(ILandVehicle.class)) {
				this.landVehicleDatabase.put(currVehicle.getUniqueID(),(ILandVehicle) currVehicle);
			}
			if (interfaces.contains(IAirVehicle.class)) {
				this.airVehicleDatabase.put(currVehicle.getUniqueID(),(IAirVehicle) currVehicle);
			}
			lock.writeLock().unlock();
			log("the vehicle: " + currVehicle.toString() + " was added succesfully, returning");
			return true;
		}
		return false;
	}

	public boolean buyVehicle(IVehicle currVehicle) {
		lock.readLock().lock();
		boolean contains = vehicleDatabase.containsKey(currVehicle.getUniqueID());
		lock.readLock().unlock();
		if (contains) {
			lock.writeLock().lock();
			for (LinkedHashMap<String,?> hM:Arrays.asList(vehicleDatabase,seaVehicleDatabase,airVehicleDatabase,landVehicleDatabase))
				while(hM.containsKey(currVehicle.getUniqueID()))
					hM.remove(currVehicle.getUniqueID());
			lock.writeLock().unlock();
			log("the vehicle: " + currVehicle.toString() + " was bought succesfully, returning");
			return true;
		}
		log("the vehicle " + currVehicle.toString() + " doesnt exist, returning");
		return false;
	}

	public boolean testDriveVehicle(IVehicle currVehicle, double distance) {
		boolean contains = vehicleDatabase.containsKey(currVehicle.getUniqueID());
		if (contains) {
			lock.writeLock().lock();
			vehicleDatabase.get(currVehicle.getUniqueID()).moveDistance(distance);
			this.totalDistances+=distance;
			lock.writeLock().unlock();
			log("the vehicle: " + currVehicle.toString() + " was taken for a " + distance + "km test-drive succesfully, returning");
			return true;
		}
		log("the vehicle " + currVehicle.toString() + " doesnt exist, returning");
		return false;
	}

	public boolean resetDistances() {
		lock.writeLock().lock();
		totalDistances = 0;
		lock.writeLock().unlock();
		if (isEmpty()) {
			log("no vehicles to reset distance, returning");
			return false;
		}
		lock.writeLock().lock();
		for (IVehicle v : vehicleDatabase.values()) {
			v.resetTotalDistance();
		}
		lock.writeLock().unlock();
		log("all vehicle distances were reset succesfully, returning");
		return true;
	}

	public boolean changeFlags(String flag) {
		if (seaVehicleDatabase.isEmpty()) {
			log("no vehicles to change flags, returning");
			return false;
		}
		lock.writeLock().lock();
		for (ISeaVehicle sV : seaVehicleDatabase.values()) {
			sV.setFlag(flag);
		}
		lock.writeLock().unlock();
		log("all vehicle flags were changed to " + flag + " succesfully, returning\n");
		return true;
	}


	public Database() {
		vehicleDatabase = new LinkedHashMap<String, IVehicle>();
		seaVehicleDatabase = new LinkedHashMap<String, ISeaVehicle>();
		airVehicleDatabase = new LinkedHashMap<String, IAirVehicle>();
		landVehicleDatabase = new LinkedHashMap<String, ILandVehicle>();
	}
	
	public IVehicle findVehicle(String vehicleID) {
		return vehicleDatabase.get(vehicleID);
	}
	
	public Collection<IVehicle> getVehicles() {
		return vehicleDatabase.values();
	}
	
	public double getTotalDistances() {
		return this.totalDistances;
	}
		
	public Database(Database toClone) {
		synchronized (toClone) {
			this.vehicleDatabase = new LinkedHashMap<String, IVehicle>(toClone.vehicleDatabase.size());
			this.seaVehicleDatabase = new LinkedHashMap<String, ISeaVehicle>(toClone.seaVehicleDatabase.size());
			this.airVehicleDatabase = new LinkedHashMap<String, IAirVehicle>(toClone.airVehicleDatabase.size());
			this.landVehicleDatabase = new LinkedHashMap<String, ILandVehicle>(toClone.landVehicleDatabase.size());
			this.totalDistances = toClone.totalDistances;
			setVerbose(false);
			for(IVehicle v:toClone.vehicleDatabase.values()) {
				addVehicle(v.clone());
			}
			setVerbose(true);
		}
	}
	
	public Database clone() {
		return new Database(this);
	}
}
