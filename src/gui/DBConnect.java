//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;


import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


import classes.Database;
import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;


public class DBConnect extends JComponent {
	
	long getWaitTime() {
		return (long)Utilities.getRand(3000, 8000);
	}

	static volatile DBConnect self = null;
	private Database db;

	private DBConnect() {
		db = new Database();
	}

	public void addVehicle(Vehicle v) {
		new AddVehicleThread(v).execute();
	}
	class AddVehicleThread extends DBThread{
		private Vehicle vehicle;
		public AddVehicleThread(Vehicle vehicle) {
			this.vehicle = vehicle;
		}
		@Override
		protected Status doInBackground() {
			new WaitDialog(getWaitTime());
			db.addVehicle(vehicle);
			firePropertyChange("addVehicle", null, vehicle);
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
			DBConnect.getConnection().firePropertyChange("buyVehicle", v, null);
		}
		@Override
		protected DBConnect.Status doInBackground() {
			if(!duringTransactionAdd(vehicle)) {
				return Status.RETRY;
			}
			try {
				Thread.sleep((long)Utilities.getRand(5000, 10000));
				int result = JOptionPane.showOptionDialog(null, "are you sure you want to buy this vehicle?\n" + vehicle.toString(), "buying confirmation",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if(result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
					duringTransactionRemove(vehicle);
					return Status.CANCEL;
				}
				new WaitDialog(getWaitTime(),()->{
					buyVehicleMethod(vehicle);
					duringTransactionRemove(vehicle);
					JOptionPane.showMessageDialog(null,"The vehicle bought succesfully!");
				});	
				return Status.DONE;
			} catch (InterruptedException e) {
				duringTransactionRemove(vehicle);
				return Status.RETRY;
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
			DBConnect.getConnection().firePropertyChange("testDriveVehicle", vehicle, vehicle.getTotalDistance());
		}
		
		@Override	
		protected DBConnect.Status doInBackground() {
			try {
				if(!db.containsIdentical(vehicle)) {
					return Status.STOP;
				}
				if(!aquireTestRide(vehicle)) {
					return Status.RETRY;
				}
				Thread.sleep((long)(distance*100));
				testDriveVehicleMehod();
				releaseTestRide(vehicle);
				return Status.DONE;
				} 
			catch (InterruptedException e) {
				Utilities.log("thread sleep interrupted");
				releaseTestRide(vehicle);
				return Status.STOP;
			}
		}
	}
	

	public void resetDistances() {
		new ResetDistancesThread().execute();
	}
	
	class ResetDistancesThread extends DBThread{
		@Override
		protected Status doInBackground() {
			new WaitDialog(getWaitTime());
			if(db.isEmpty()) {
				return Status.FAILED;
			}
			db.resetDistances();
			firePropertyChange("resetDistances", null, null);
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
		protected Status doInBackground() {
			new WaitDialog(getWaitTime());
			if(!db.hasSeaVehicles()) {
				return Status.FAILED;
			}
			db.changeFlags(flag);
			firePropertyChange("changeFlags", null, flag);
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
	
	enum Status{STOP,RETRY,DONE,CANCEL,ABORT,FAILED};
	
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
