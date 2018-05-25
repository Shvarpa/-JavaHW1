//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


import classes.Database;
import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import javafx.util.Pair;


public class DBConnect extends JComponent {
	private static final long serialVersionUID = 1L;

	long getWaitTime() {
//		return (long)Utilities.getRand(3000, 8000);
		return 10;
	}

	static volatile DBConnect self = null;
	private Database db;

	private DBConnect() {
		db = new Database();
	}

	public void addVehicle(Vehicle v) {
		new AddVehicleThread(v).execute();
	}
	private Lock addVehicleLock = new ReentrantLock(true); 
	class AddVehicleThread extends DBThread{
		private Vehicle vehicle;
		public AddVehicleThread(Vehicle vehicle) {
			this.vehicle = vehicle;
		}
		@Override
		final protected Status doInBackground() {
			addVehicleLock.lock();
			new WaitDialog(getWaitTime());
			db.addVehicle(vehicle);
			getConnection().firePropertyChange("addVehicle", null, vehicle);
			addVehicleLock.unlock();
			return Status.DONE;	
		}	
	}
	
	public void buyVehicle(Vehicle vehicle) {
		new buyVehicleThread(vehicle).execute();
	}
	
	class buyVehicleThread extends DBThread{
		private Vehicle vehicle;
		public buyVehicleThread(Vehicle vehicle) {
			this.vehicle = vehicle;
			}		
			public void buyVehicleMethod(Vehicle v) {
			db.buyVehicle(v);
			getConnection().firePropertyChange("buyVehicle", v, null);
			}
			@Override
			final protected DBConnect.Status doInBackground() {
				if(!transactionLock.aquireBuyVehicle(vehicle)) {
					return Status.RETRY;
				}
				try {
					Thread.sleep((long)Utilities.getRand(5000, 10000));
					int result = JOptionPane.showOptionDialog(null, "are you sure you want to buy this vehicle?\n" + vehicle.toString(), "buying confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
					if(result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
						transactionLock.releaseBuyVehicle(vehicle);
						return Status.CANCEL;
					}
					new WaitDialog(getWaitTime());
					buyVehicleMethod(vehicle);
					transactionLock.releaseBuyVehicle(vehicle);
					JOptionPane.showMessageDialog(null,"The vehicle bought succesfully!");
					return Status.DONE;
				} catch (InterruptedException e) {
					transactionLock.releaseBuyVehicle(vehicle);
					return Status.ABORT;
				}
		}
	}
	
	public void testDriveVehicle(Vehicle vehicle,double distance) {
		new testDriveVehicleThread(vehicle,distance).execute();
	}
	
	class testDriveVehicleThread extends DBThread{
		private Vehicle vehicle;
		private double distance;
		public testDriveVehicleThread(Vehicle vehicle, double distance) {
			this.vehicle = vehicle; this.distance = distance;
		}
		private void testDriveVehicleMehod() {
			db.testDriveVehicle(vehicle,distance);
			getConnection().firePropertyChange("testDriveVehicle", vehicle, vehicle.getTotalDistance());
		}
		
		@Override	
		final protected DBConnect.Status doInBackground() {
			try {
				if(!db.containsIdentical(vehicle)) {
					return Status.STOP;
				}
				if(!transactionLock.aquireTestDrive(vehicle)) {
					return Status.RETRY;
				}
				Thread.sleep((long)(distance*100));
				testDriveVehicleMehod();
				transactionLock.releaseTestDrive(vehicle);
				return Status.DONE;
				} 
			catch (InterruptedException e) {
				Utilities.log("thread sleep interrupted");
				transactionLock.releaseTestDrive(vehicle);
				return Status.STOP;
			}
		}
	}
	

	public void resetDistances() {
		new ResetDistancesThread().execute();
	}
	
	class ResetDistancesThread extends DBThread{
		@Override
		final protected Status doInBackground() {
			new WaitDialog(getWaitTime());
			if(db.isEmpty()) {
				return Status.FAILED;
			}
			db.resetDistances();
			getConnection().firePropertyChange("resetDistances", null, null);
			return Status.DONE;	
		}	
	}

	public void changeFlags(String flag) {
		new ChangeFlagsThread(flag).execute();
	}	
	
	class ChangeFlagsThread	extends DBThread{
		private String flag;
		ChangeFlagsThread(String flag){
			this.flag=flag;
		}
		@Override
		final protected Status doInBackground() {
			new WaitDialog(getWaitTime());
			if(!db.hasSeaVehicles()) {
				return Status.FAILED;
			}
			db.changeFlags(flag);
			getConnection().firePropertyChange("changeFlags", null, flag);
			return Status.DONE;	
		}
		
	}
	
	public List<Vehicle> getVehicles() {
		return db.getVehicles();
	}

	public boolean hasSeaVehicles() {
		return db.hasSeaVehicles();
	}
	
	public boolean containsIdentical(Vehicle v) {
		return db.containsIdentical(v);
	}
	
	public boolean isEmpty() {
		return db.isEmpty();
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
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	enum Status{STOP,RETRY,DONE,CANCEL,ABORT,FAILED};
	public static String duringTransactionMessege = "vehicle during transaction, please retry later...";
	private TransactionLock transactionLock = new TransactionLock();
	///// locking vehicle test riding

	
	abstract class DBThread extends SwingWorker<DBConnect.Status,Object>{
		public Status getStatus() {
			try {
				return get();
			} catch (InterruptedException | ExecutionException e) {
				return Status.ABORT;
			}
		}
	}
	
}
