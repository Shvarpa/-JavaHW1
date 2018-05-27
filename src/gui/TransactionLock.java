package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;

enum Operation {
	BUY_VEHICLE, TEST_DRIVE
}

public class TransactionLock {

	class OperationLock {
		private Operation type;
		private ReentrantLock lock;

		public Operation getOperation() {
			return type;
		}

		public void setOperation(Operation t) {
			this.type = t;
		}

		public void lock() {
			lock.lock();
		}

		public void unlock() {
			lock.unlock();
		}

		private OperationLock(Operation o) {
			setOperation(o);
			this.lock = new ReentrantLock(true);
		}

		public String toString() {
			return getOperation().toString();
		}
	}

	private IdentityHashMap<Vehicle, LinkedBlockingDeque<OperationLock>> transactionQueue = new IdentityHashMap<Vehicle, LinkedBlockingDeque<OperationLock>>();
	private ReentrantLock lock = new ReentrantLock(true);
	
	private boolean aquire(Vehicle vehicle, Operation type) {
		if (transactionQueue.containsKey(vehicle) && transactionQueue.get(vehicle).size()>=1) {
			Utilities.log(" in queue " + transactionQueue.get(vehicle).toString());//////////////////////////////////////////////////
			for (OperationLock op : transactionQueue.get(vehicle)) {
				Utilities.log(op.toString() + " ? " + type);////////////////////////////////////////////////////////////////
				if (op.getOperation().equals(type))
					return false;
			}
			Utilities.log("aquireing " + type);/////////////
			OperationLock last = transactionQueue.get(vehicle).getLast();
			OperationLock curr = new OperationLock(type);
			lock.lock();
			transactionQueue.get(vehicle).add(curr);
			lock.unlock();
			curr.lock();
			last.lock();
			Utilities.log("aquired " + last);//////////////

		} else {
			OperationLock oL = new OperationLock(type);
			LinkedBlockingDeque<OperationLock> queue = new LinkedBlockingDeque<OperationLock>();
			lock.lock();
			queue.add(oL);
			transactionQueue.put(vehicle, queue);
			lock.unlock();
			Utilities.log("adding " + type);////////
			oL.lock();
		}
		return true;

	}

	private void release(Vehicle vehicle) {
		Utilities.log("waiting list: " + transactionQueue.get(vehicle));//////////////////////
		lock.lock();
		OperationLock l = transactionQueue.get(vehicle).removeFirst();
		Utilities.log("releasing: " + l.getOperation());//////////////////////
		if (transactionQueue.get(vehicle).isEmpty())
			transactionQueue.remove(vehicle);
		lock.unlock();
		l.unlock();
	}

	public boolean aquireTestDrive(Vehicle v) {
		if(!aquire(v, Operation.TEST_DRIVE)) return false;
		try {
			for (String s : reqTestRiders(v))
				testDrivers.get(s).acquire();
		} catch (InterruptedException e) {
			for (String s : reqTestRiders(v))
				testDrivers.get(s).release();
		}
		return true;
	}

	public void releaseTestDrive(Vehicle v) {
		for (String s : reqTestRiders(v))
			testDrivers.get(s).release();
		release(v);
	}

	public boolean aquireBuyVehicle(Vehicle v) {
		return aquire(v, Operation.BUY_VEHICLE);
	}

	public void releaseBuyVehicle(Vehicle v) {
		release(v);
	}

	/////////////////////////////////////////////////////////////

	static private HashMap<String, Semaphore> testDrivers;

	private static List<String> reqTestRiders(Vehicle v) {
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
