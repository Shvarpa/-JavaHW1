package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;

class Triplet{
	private Vehicle vehicle;
	private boolean type;	///true==testdrive, false==buyVehicle;
	private ReentrantLock lock;
	public Triplet(Vehicle vehicle,boolean type) {
		this.vehicle = vehicle;
		this.type = type;
		this.lock = new ReentrantLock();
	}
	public Vehicle getVehicle() {return vehicle;}
	public boolean getType() {return type;}
	public ReentrantLock getLock() {return lock;}
}



public class TransactionLock extends ArrayList<Triplet>{
		
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
	
	public TransactionLock() {
		super();
	}
		
	private static final long serialVersionUID = 1L;
	
	@Override
	public int indexOf(Object v) {
		for(int i=0;i<size();i++)
			if (get(i).getVehicle()==v)
			{
				return i;
			}
		return -1;
	}
	
	public boolean contains(Vehicle v) {
		return (indexOf((Object)v) != -1);
	}
	private boolean aquire(Vehicle vehicle,boolean type) {
		int index = indexOf((Object)vehicle);
		if(index != -1) {
			if (get(index).getType()==type) {
				return false;
			}
			Utilities.log("aquireing " + index);
			waiting.add(vehicle);
			ReentrantLock l = get(index).getLock();
			l.lock();
			waiting.remove(waiting.indexOf(vehicle));
			Utilities.log("aquired " + index);
			
		}
		else {
				Triplet temp = new Triplet(vehicle, type);
				add(temp);
				Utilities.log("adding " + (size()-1));
				temp.getLock().lock();
		}
		return true;
	}
	private void release(Vehicle vehicle,boolean type) {
		int index = indexOf((Object)vehicle);
		if(index == -1) {
			return;
		}
		Utilities.log("waiting " + waiting);
		if(waiting.contains(vehicle)) {
			while(waiting.contains(vehicle)) {
					get(index).getLock().unlock();
				}
		}
		Utilities.log("removing " + index);
		remove((int)index);
			
	}
	
	public boolean aquireTestDrive(Vehicle v) {
		try {
			for(String s:reqTestRiders(v))
				testDrivers.get(s).acquire();
		} catch (InterruptedException e) {
			for(String s:reqTestRiders(v))
				testDrivers.get(s).release();
		}
		return aquire(v,true);
	}
	
	public void releaseTestDrive(Vehicle v) {
		for(String s:reqTestRiders(v)) testDrivers.get(s).release();
		release(v, true);
	}
	
	public boolean aquireBuyVehicle(Vehicle v) {
		return aquire(v,false);
	}
	
	public void releaseBuyVehicle(Vehicle v) {
		release(v, false);
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
