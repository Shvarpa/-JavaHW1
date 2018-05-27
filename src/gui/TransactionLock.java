package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;

enum Operation{ADD_VEHICLE,BUY_VEHICLE,TEST_DRIVE,RESET_DISTANCES,CHANGE_FLAGS}

class Triplet{
	private Vehicle vehicle;
	private Operation type;	///true==testdrive, false==buyVehicle;
	private ReentrantLock lock;
	public Triplet(Vehicle vehicle,Operation type) {
		this.vehicle = vehicle;
		this.type = type;
		this.lock = new ReentrantLock();
	}
	public Vehicle getVehicle() {return vehicle;}
	public Operation getType() {return type;}
	public ReentrantLock getLock() {return lock;}
}

public class TransactionLock{
		
	private ArrayList<Vehicle> waiting = new ArrayList<Vehicle>() {
		@Override
		public int indexOf(Object o) {
			for(int i=0;i<size();i++)
				if(get(i)==o) {
					return i;
				}
			return -1;
		}
		@Override
		public boolean contains(Object o) {
			return indexOf(o)!=-1;
		}
	}; 
	
	private ArrayList<Triplet> transaction = new ArrayList<Triplet>() {
		@Override
		public int indexOf(Object o) {
			for(int i=0;i<size();i++)
				if(get(i)==o) {
					return i;
				}
			return -1;
		}
		@Override
		public boolean contains(Object o) {
			return indexOf(o)!=-1;
		}
	}; 

	boolean aquire(Vehicle vehicle,Operation type) {
		int index = transaction.indexOf((Object)vehicle);
		if(index != -1) {
			if (transaction.get(index).getType()==type) {
				return false;
			}
			Utilities.log("aquireing " + index);
			waiting.add(vehicle);
			ReentrantLock l = transaction.get(index).getLock();
			l.lock();
			waiting.remove(waiting.indexOf(vehicle));
			Utilities.log("aquired " + index);
			
		}
		else {
				Triplet temp = new Triplet(vehicle, type);
				transaction.add(temp);
				Utilities.log("adding " + (transaction.size()-1));
				temp.getLock().lock();
		}
		return true;
	}
	void release(Vehicle vehicle,Operation type) {
		int index = transaction.indexOf((Object)vehicle);
		if(index == -1) {
			return;
		}
		Utilities.log("waiting " + waiting);
		if(waiting.contains(vehicle)) {
			transaction.get(index).getLock().unlock();
		}
		else {
			Utilities.log("removing " + index);
			transaction.remove((int)index);
		}
			
	}
	
	public boolean aquireTestDrive(Vehicle v) {
		try {
			for(String s:reqTestRiders(v))
				testDrivers.get(s).acquire();
		} catch (InterruptedException e) {
			for(String s:reqTestRiders(v))
				testDrivers.get(s).release();
		}
		return aquire(v,Operation.TEST_DRIVE);
	}
	
	public void releaseTestDrive(Vehicle v) {
		for(String s:reqTestRiders(v)) testDrivers.get(s).release();
		release(v, Operation.TEST_DRIVE);
	}
		
	
/////////////////////////////////////////////////////////////
	
	static private HashMap<String, Semaphore> testDrivers;
	private static List<String> reqTestRiders(Vehicle v){
		List<String> reqTestRiders = new ArrayList<String>();
		if (v instanceof ILandVehicle) {
			reqTestRiders.add(ILandVehicle.class.getSimpleName());
		}
		if (v instanceof ISeaVehicle) {
			reqTestRiders.add(ISeaVehicle.class.getSimpleName());
		}
		if (v instanceof IAirVehicle) {
			reqTestRiders.add(IAirVehicle.class.getSimpleName());
		}
		return reqTestRiders;
	}

	
	static {
		testDrivers = new HashMap<String, Semaphore>(3);
		for (String s : Arrays.asList(ILandVehicle.class.getSimpleName(), ISeaVehicle.class.getSimpleName(),
				IAirVehicle.class.getSimpleName()))
			testDrivers.put(s, new Semaphore(1));
	}
}
