//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import gui.Utilities;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;


public class Database {
	
	private List<Vehicle> vehicleDatabase;
	private List<ISeaVehicle> seaVehicleDatabase;
	private List<IAirVehicle> airVehicleDatabase;
	private List<ILandVehicle> landVehicleDatabase;
	private ReadWriteLock lock = new ReentrantReadWriteLock(true);
		
	private boolean isEmpty() {
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
	
	
	public boolean addVehicle(Vehicle currVehicle) {
		if (currVehicle != null) {
			lock.writeLock().lock();
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
			lock.writeLock().unlock();
			Utilities.log("the vehicle: " + currVehicle.toString() + " was added succesfully, returning");
			return true;
		}
		return false;
	}

	public boolean buyVehicle(Vehicle currVehicle) {
		if (vehicleDatabase.contains(currVehicle)) {
			lock.writeLock().lock();
			Integer index = getIdentical(vehicleDatabase, currVehicle);
			if(index != null)this.vehicleDatabase.remove((int)index); 
			else this.vehicleDatabase.remove(currVehicle);
			
			if (currVehicle instanceof ISeaVehicle) {
				index = getIdentical(seaVehicleDatabase, currVehicle);
				if(index != null) this.seaVehicleDatabase.remove((int)index); 
				else this.seaVehicleDatabase.remove((ISeaVehicle)currVehicle);

			}
			
			if (currVehicle instanceof ILandVehicle) {
				index = getIdentical(landVehicleDatabase, currVehicle);
				if(index != null) this.landVehicleDatabase.remove((int)index); 
				else this.landVehicleDatabase.remove((ILandVehicle)currVehicle);
			}
			
			if (currVehicle instanceof IAirVehicle) {
				index = getIdentical(airVehicleDatabase, currVehicle);
				if(index != null) this.airVehicleDatabase.remove((int)index); 
				else this.airVehicleDatabase.remove((IAirVehicle)currVehicle);
			}
			lock.writeLock().unlock();
			Utilities.log("the vehicle: " + currVehicle.toString() + " was bought succesfully, returning");
			return true;
		}
		Utilities.log("vehicle" + currVehicle.toString() + "doesnt exist, returning");
		return false;
	}

	public boolean testDriveVehicle(Vehicle currVehicle, double distance) {
		if (vehicleDatabase.contains(currVehicle)) {
			lock.writeLock().lock();
			Integer index = getIdentical(vehicleDatabase, currVehicle);
			if(index!=null) vehicleDatabase.get((int)index).moveDistance(distance);
			else vehicleDatabase.get(vehicleDatabase.indexOf(currVehicle)).moveDistance(distance);
			lock.writeLock().unlock();
			Utilities.log("the vehicle: " + currVehicle.toString() + " was taken for a " + distance + "km test-drive succesfully, returning");
			return true;
		}
		return false;
	}

	public boolean resetDistances() {
		if (isEmpty()) {
			Utilities.log("no vehicles to reset distance, returning");
			return false;
		}
		lock.writeLock().lock();
		for (Vehicle v : vehicleDatabase) {
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
		for (ISeaVehicle sV : seaVehicleDatabase) {
			sV.setFlag(flag);
		}
		lock.writeLock().unlock();
		Utilities.log("all vehicle flags were changed to " + flag + " succesfully, returning\n");
		return true;
	}


	public Database() {
		vehicleDatabase = new ArrayList<>();
		seaVehicleDatabase = new ArrayList<>();
		airVehicleDatabase = new ArrayList<>();
		landVehicleDatabase = new ArrayList<>();
	}

	public List<Vehicle> getVehicles() {
		lock.readLock().lock();
		List<Vehicle> result = vehicleDatabase;
		lock.readLock().unlock();
		return result;
	}
	
	
	private Integer getIdentical(List vehicles,Vehicle v) {
		lock.readLock().lock();
		for(int i=0;i<vehicles.size();i++)
			if(vehicles.get(i)==v) {
				lock.readLock().unlock();
				return i;
			}
		lock.readLock().unlock();
		return null;
	}
	
	public boolean containsIdentical(Vehicle v) {
		return (getIdentical(vehicleDatabase, v)!=null);
	}
}
