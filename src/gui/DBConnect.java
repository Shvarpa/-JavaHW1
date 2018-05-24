//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;


import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import classes.Database;
import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import javafx.scene.media.MediaPlayer.Status;


public class DBConnect extends JComponent {

	static volatile DBConnect self = null;
	private Database db;

	private DBConnect() {
		db = new Database();
	}

	public void addVehicle(Vehicle v) {
		db.addVehicle(v);
		firePropertyChange("addVehicle", null, v);
	}
	
	
	class buyVehicle extends SwingWorker<Boolean, Object>{
		private Vehicle vehicle;
		public buyVehicle(Vehicle vehicle) {
			this.vehicle = vehicle;
		}		
		
			public void buyVehicleMethod(Vehicle v) {
			db.buyVehicle(v);
			DBConnect.getConnection().firePropertyChange("buyVehicle", v, null);
		}
		
		@Override
		protected Boolean doInBackground() throws Exception {
			if(!duringTransactionAdd(vehicle)) {
				return false;
			}
			try {
				Thread.sleep((long)Utilities.getRand(5000, 10000));
				return true;
			} catch (InterruptedException e) {
				duringTransactionRemove(vehicle);
				return false;
			}
		}
		
		@Override
		protected void done() {
			try {
				if (!get()) {return;}
			} catch (InterruptedException | ExecutionException e) {
				return;
			}
			int result = JOptionPane.showOptionDialog(null, "are you sure you want to buy this vehicle?\n" + vehicle.toString(), "buying confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if(result == JOptionPane.NO_OPTION) {
				duringTransactionRemove(vehicle);
				return;
			}
			new WaitDialog((long) Utilities.getRand(3000, 8000),()->{
				buyVehicleMethod(vehicle);
				duringTransactionRemove(vehicle);
				JOptionPane.showMessageDialog(null,"The vehicle bought succesfully!");
			});		
		}
	}
	
	
	///	testDriveVehicle class is a SwingWorker background thread that was created in order to perform the the testDriving task in the background, 
	/// without stalling the window during the action, and give the ability to the user to receive the status of the action through the Status enum.
	/// if you want to execute the thread you can: <DBConnect instance>.new testDriveVehicle(vehicle,distance).execute();
	/// you can override the done() method to include your own post background method thread
	
	class testDriveVehicle extends SwingWorker<DBConnect.Status,Object>{
		private Vehicle vehicle;
		private double distance;
		public testDriveVehicle(Vehicle vehicle, double distance) {
			this.vehicle = vehicle; this.distance = distance;
		}
		private void testDriveVehicleMehod() {
			db.testDriveVehicle(vehicle,distance);
			DBConnect.getConnection().firePropertyChange("testDriveVehicle", vehicle, vehicle.getTotalDistance());
		}
		
		public DBConnect.Status getStatus(){
			try {
				return get();
			} catch (InterruptedException | ExecutionException e) {
				return Status.STOP;
			}
		}
		
		@Override	
		protected DBConnect.Status doInBackground() throws Exception {
			try {
				if(!db.containsIdentical(vehicle)) {
					return Status.STOP;
				}
				if(!aquireTestRide(vehicle)) {
					return Status.RETRY;
				}
				Thread.sleep((long)(distance*100));
				return Status.CONTINUE;
				} 
			catch (InterruptedException e) {
				Utilities.log("thread sleep interrupted");
				releaseTestRide(vehicle);
				return Status.STOP;
			}
		}
		
		@Override
		protected void done() {
			try {
				switch(get()) {
				case CONTINUE:
					testDriveVehicleMehod();
					releaseTestRide(vehicle);
				default:
					return;
				}
			} catch (HeadlessException | InterruptedException | ExecutionException e) {
				releaseTestRide(vehicle);
			}
		}
		
	}
	

	public void resetDistances() {
		db.resetDistances();
		firePropertyChange("resetDistances", null, null);
	}

	public boolean changeFlags(String flag) {
		boolean s = db.changeFlags(flag);
		firePropertyChange("changeFlags", null, flag);
		return s;
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
	
	public static String duringTransactionMessege = "vehicle during transaction, please retry later...";
	private List<Vehicle> duringTransaction = new ArrayList<Vehicle>();
	private Integer duringTransactionIndex(Vehicle vehicle) {
		synchronized (duringTransaction) {
			for(int i=0;i<duringTransaction.size();i++)
				if (duringTransaction.get(i)==vehicle)
					return i;
			return null;
		}
	}
	public boolean duringTransactionContains(Vehicle vehicle) {
		return (duringTransactionIndex(vehicle) != null);
	}
	public boolean duringTransactionAdd(Vehicle vehicle) {
		if(duringTransactionContains(vehicle)) return false;
		else
			synchronized (duringTransaction) {
				duringTransaction.add(vehicle);
			}
		return true;
	}
	public void duringTransactionRemove(Vehicle vehicle) {
		Integer index = duringTransactionIndex(vehicle);
		if(index == null) return;
		synchronized (duringTransaction) {
			duringTransaction.remove((int)index);
		}
	}
	
	enum Status{STOP,CONTINUE,RETRY};
	
	///// locking vehicle test riding
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
		private boolean aquireTestRide(Vehicle vehicle) {
			if (duringTransactionContains(vehicle)) return false;
			try {
			for(String s:reqTestRiders(vehicle)) testDrivers.get(s).acquire();
			} catch (InterruptedException e) {
				for(String s:reqTestRiders(vehicle)) testDrivers.get(s).release();
				return false;
			}
			return duringTransactionAdd(vehicle);
		}
		private void releaseTestRide(Vehicle vehicle) {
			duringTransactionRemove(vehicle);
			for(String s:reqTestRiders(vehicle)) testDrivers.get(s).release();
		}
		static {
			testDrivers = new HashMap<String, Semaphore>(3);
			for (String s : Arrays.asList(ILandVehicle.class.getSimpleName(), ISeaVehicle.class.getSimpleName(),
					IAirVehicle.class.getSimpleName()))
				testDrivers.put(s, new Semaphore(1));
		}	
	
}
