//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import classes.StatusReporter;
import classes.StatusReporter.VehicleStatus;
import interfaces.IVehicle;

enum Operation {
	BUY_VEHICLE, TEST_DRIVE
}

public class TransactionLock {
	///the transaction lock is a lock designed to work with the BUT_VEHICLE & TEST_DRIVE with designated acquire and release for each operation.
	///the lock logs vehicles attempting to do a transaction and stops them from doing the same transaction twice.
	///if the same vehicle is attempting to do a different transaction/operation on the database the lock will be locked in a queue until the the operation's turn in the queue

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

	private HashMap<String, LinkedBlockingDeque<OperationLock>> transactionQueue = new HashMap<String, LinkedBlockingDeque<OperationLock>>();
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
	
	private boolean aquire(IVehicle vehicle, Operation type) {
		lock.readLock().lock();
		if (transactionQueue.containsKey(vehicle.getUniqueID()) && transactionQueue.get(vehicle.getUniqueID()).size()>=1) {
			for (OperationLock op : transactionQueue.get(vehicle.getUniqueID())) {
				if (op.getOperation().equals(type)) {
					lock.readLock().unlock();
					return false;
				}
			}
			OperationLock last = transactionQueue.get(vehicle.getUniqueID()).getLast();
			OperationLock curr = new OperationLock(type);
			lock.readLock().unlock();
			lock.writeLock().lock();
			transactionQueue.get(vehicle.getUniqueID()).add(curr);
			lock.writeLock().unlock();
			curr.lock();
			last.lock();
		} else {
			OperationLock oL = new OperationLock(type);
			LinkedBlockingDeque<OperationLock> queue = new LinkedBlockingDeque<OperationLock>();
			lock.readLock().unlock();
			lock.writeLock().lock();
			queue.add(oL);
			transactionQueue.put(vehicle.getUniqueID(), queue);
			lock.writeLock().unlock();
			oL.lock();
		}
		return true;

	}

	private void release(IVehicle vehicle) {
		lock.writeLock().lock();
		OperationLock l = transactionQueue.get(vehicle.getUniqueID()).removeFirst();
		if (transactionQueue.get(vehicle.getUniqueID()).isEmpty())
			transactionQueue.remove(vehicle.getUniqueID());
		lock.writeLock().unlock();
		l.unlock();
	}

	public boolean aquireTestDrive(IVehicle vehicle) {
		if(!aquire(vehicle, Operation.TEST_DRIVE)) return false;
//		try {
//			for (String s : reqTestRiders(vehicle))
//				testDrivers.get(s).acquire();
//		} catch (InterruptedException e) {
//			for (String s : reqTestRiders(vehicle))
//				testDrivers.get(s).release();
//		}
		vehicle = new StatusReporter(vehicle,VehicleStatus.TEST_DRIVE);
		return true;
	}

	public void releaseTestDrive(IVehicle vehicle) {
//		List<String> x = reqTestRiders(vehicle);
//		Collections.reverse(x);
//		for (String s : x)
//			testDrivers.get(s).release();
		vehicle = new StatusReporter(vehicle,VehicleStatus.STANDBY);
		release(vehicle);
	}

	public boolean aquireBuyVehicle(IVehicle vehicle) {
		vehicle = new StatusReporter(vehicle,VehicleStatus.BUY);
		return aquire(vehicle, Operation.BUY_VEHICLE);
	}

	public void releaseBuyVehicle(IVehicle vehicle) {
		vehicle = new StatusReporter(vehicle,VehicleStatus.STANDBY);
		release(vehicle);
	}
	
	public boolean isEmpty() {
		return transactionQueue.isEmpty();
	}
	/////////////////////////////////////////////////////////////

//	static private HashMap<String, Semaphore> testDrivers;
//
//	private static List<String> reqTestRiders(IVehicle v) {
//		List<String> reqTestRiders = new ArrayList<String>();
//		if (v instanceof ILandVehicle) {
//			reqTestRiders.add(ILandVehicle.class.getSimpleName());
//		}
//		if (v instanceof ISeaVehicle) {
//			reqTestRiders.add(ISeaVehicle.class.getSimpleName());
//		}
//		if (v instanceof IAirVehicle) {
//			reqTestRiders.add(IAirVehicle.class.getSimpleName());
//		}
//		return reqTestRiders;
//	}
//
//	static {
//		testDrivers = new HashMap<String, Semaphore>(3);
//		for (String s : Arrays.asList(ILandVehicle.class.getSimpleName(), ISeaVehicle.class.getSimpleName(),
//				IAirVehicle.class.getSimpleName()))
//			testDrivers.put(s, new Semaphore(1));
//	}
}
