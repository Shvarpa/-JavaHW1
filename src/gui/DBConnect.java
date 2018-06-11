//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import classes.Database;
import classes.FixedStack;
import interfaces.IVehicle;
import interfaces.Stack;


public class DBConnect extends JComponent {
	private static final long serialVersionUID = 1L;
	///a singleton connection to a database adapting the database to swing capable of firing swing propertyChange events
	///each operation is a SwingWorker background thread returning the operations ending status with the capability of overriding the SwingWorker's done method,
	///or just used as a normal method call invoking the background thread.  
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////// Utilities
	enum Status{STOP,RETRY,DONE,CANCEL,ABORT,FAILED};
	private TransactionLock transactionLock = new TransactionLock();
	
	abstract class DBThread implements Runnable{
		///made for overriding SwingWorker methods not made for overriding
		private DBThread self = this;
		private SwingWorker<DBConnect.Status,Object> thread = new SwingWorker<DBConnect.Status,Object>(){
			@Override
			protected Status doInBackground() throws Exception {
				return self.doInBackground();
			}
			@Override
			protected void done() {
				self.done();
			}
		};
		public Status getStatus() {
			try {
				return thread.get();
			} catch (InterruptedException | ExecutionException e) {
				return Status.ABORT;
			}
		}
		abstract protected DBConnect.Status doInBackground();
		protected void done() {}
		public void run() {thread.execute();}
	}
	
	long getWaitTime() {
		return (long)Utilities.getRand(3000, 8000);
	}

	private static volatile DBConnect self = null;
	private Database db;
	private DBConnect() {
		db = new Database();
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
	
	
	private Stack<Database> mementos = new FixedStack<Database>(3);
	public void saveMemento() {
		mementos.push(db.clone());
	}
	public boolean restoreMemento() {
		Database restoriee = mementos.pop();
		if (restoriee == null) 
			return false;
		synchronized (this) {
			this.db = restoriee;
			firePropertyChange("restoreMemento", null, null);
		}
		return true;
	}
	public boolean hasMementos() {
		return !mementos.isEmpty();
	}
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addVehicle(IVehicle v) {
		new AddVehicleThread(v).run();
	}
	private Lock addVehicleLock = new ReentrantLock(true); 
	class AddVehicleThread extends DBThread{
		private IVehicle vehicle;
		private void addVehicleMethod() {
			db.addVehicle(vehicle);
			getConnection().firePropertyChange("addVehicle", null, vehicle);
		}
		public AddVehicleThread(IVehicle vehicle) {
			this.vehicle = vehicle;
		}
		@Override
		final protected Status doInBackground() {
			addVehicleLock.lock();
			try {
				new DBWaitDialog(getWaitTime(),()-> {
					addVehicleMethod();
				});
			} catch (InterruptedException e) {
				addVehicleLock.unlock();
				JOptionPane.showMessageDialog(null, "adding\n"+ vehicle.toString() +"\nwas canceled");
			}
			addVehicleLock.unlock();
			JOptionPane.showMessageDialog(null, "the vehicle:\n"+ vehicle.toString() +"\nwas added succesfully!");
			return Status.DONE;	
		}	
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void buyVehicle(IVehicle vehicle) {
		new buyVehicleThread(vehicle).run();
	}
	
	class buyVehicleThread extends DBThread{
		private IVehicle vehicle;

		public buyVehicleThread(IVehicle vehicle) {
			this.vehicle = vehicle;
			}		
			public void buyVehicleMethod() {
			db.buyVehicle(vehicle);
			getConnection().firePropertyChange("buyVehicle", vehicle, null);
			}
			@Override
			final protected DBConnect.Status doInBackground() {
				if(!transactionLock.aquireBuyVehicle(vehicle)) {
					JOptionPane.showMessageDialog(null, "the vehicle:\n" + vehicle.toString() + "\nis during/awaiting the buying process, please try again later");
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
					new DBWaitDialog(getWaitTime(),()->{
						buyVehicleMethod();
					});
					JOptionPane.showMessageDialog(null,"The vehicle bought succesfully!");
					transactionLock.releaseBuyVehicle(vehicle);
					return Status.DONE;
				} catch (InterruptedException e) {
					transactionLock.releaseBuyVehicle(vehicle);
					JOptionPane.showMessageDialog(null, "the buying process for\n"+ vehicle.toString() +"\nwas canceled");
					return Status.ABORT;
				}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void testDriveVehicle(IVehicle vehicle,double distance) {
		new TestDriveVehicleThread(vehicle,distance).execute();
	}
	
	private static final ExecutorService testDrivePool = Executors.newFixedThreadPool(7);
	class TestDriveVehicleThread extends DBThread{
		private IVehicle vehicle;
		private double distance;
		public TestDriveVehicleThread(IVehicle vehicle, double distance) {
			this.vehicle = vehicle;
			this.distance = distance;
		}
		private void testDriveVehicleMehod() {
			db.testDriveVehicle(vehicle,distance);
			getConnection().firePropertyChange("testDriveVehicle", vehicle, vehicle.getTotalDistance());
		}
				
		public void execute() {
			testDrivePool.submit(this);
		}
		@Override
		protected Status doInBackground() {
			try {
				if(!transactionLock.aquireTestDrive(vehicle)) {
					JOptionPane.showMessageDialog(null,"the vehicle:\n" + vehicle.toString() + "\nis during/awaiting a test drive, please retry later");
					return Status.RETRY;
				}
				if(db.findVehicle(vehicle.getUniqueID())==null) {
					transactionLock.releaseTestDrive(vehicle);
					JOptionPane.showMessageDialog(null,"The vehicle\n"+ vehicle.toString() +"\nwas bought already, closing...");
					return Status.STOP;
				}
				Thread.sleep((long)(distance*100));
				testDriveVehicleMehod();
				transactionLock.releaseTestDrive(vehicle);
				JOptionPane.showMessageDialog(null, "the vehicle \n"+ vehicle.toString() +"\nwas taken for a test drive of " + distance + "km succesfully!");
				return Status.DONE;
				} 
			catch (InterruptedException e) {
				transactionLock.releaseTestDrive(vehicle);
				JOptionPane.showMessageDialog(null, "the "+distance+" testdrive with the vehicle \n"+ vehicle.toString() +"\nwas canceled");
				return Status.ABORT;
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void resetDistances() {
		new ResetDistancesThread().run();
	}
	
	private Lock resetDistancesLock = new ReentrantLock(true); 
	class ResetDistancesThread extends DBThread{
		private Status status;
		private void resetDistancesMethod() {
			db.resetDistances();
			getConnection().firePropertyChange("resetDistances", null, null);
		}
		@Override
		final protected Status doInBackground() {
			resetDistancesLock.lock();
			try {
				new DBWaitDialog(getWaitTime(),()-> {
					if(db.isEmpty()) {
						resetDistancesLock.unlock();
						JOptionPane.showMessageDialog(null, "the database doesnt have any vehicles, canceling!");
						status = Status.FAILED;
						return;
					}
					resetDistancesMethod();
					resetDistancesLock.unlock();
					JOptionPane.showMessageDialog(null, "all vehicles distances were reset succesfully!");
					status = Status.DONE;
				});
			} catch (InterruptedException e) {
				resetDistancesLock.unlock();
				JOptionPane.showMessageDialog(null, "reseting all vehicle's distances was canceled");
				status = Status.ABORT;
			}
			resetDistancesLock.unlock();
			return status;	
		}	
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void changeFlags(String flag) {
		new ChangeFlagsThread(flag).run();
	}	
	
	private Lock changeFlagsLock = new ReentrantLock(true); 
	class ChangeFlagsThread	extends DBThread{
		private String flag;
		private Status status;
		ChangeFlagsThread(String flag){
			this.flag=flag;
		}
		private void changeFlagsMethod() {
			db.changeFlags(flag);
			getConnection().firePropertyChange("changeFlags", null, flag);
		}
		@Override
		final protected Status doInBackground() {
			changeFlagsLock.lock();
			try {
				new DBWaitDialog(getWaitTime(),()->{
					if(!db.hasSeaVehicles()) {
						changeFlagsLock.unlock();
						JOptionPane.showMessageDialog(null, "the database doesnt have vehicles with flags, canceling!");
						status = Status.FAILED;
						return;
					}
					changeFlagsMethod();
					changeFlagsLock.unlock();
					JOptionPane.showMessageDialog(null, "changing all vehicle's flags was done succesfully!");
					status = Status.DONE;
				});
			} catch (InterruptedException e) {
				changeFlagsLock.unlock();
				JOptionPane.showMessageDialog(null, "changing all vehicle's flags was canceled");
				status = Status.ABORT;
			}
			return status;
		}
		
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double getTotalDistances() {
		return db.getTotalDistances();
	}
	public Collection<IVehicle> getVehicles() {
		return db.getVehicles();
	}

	public boolean hasSeaVehicles() {
		return db.hasSeaVehicles();
	}
		
	public boolean isEmpty() {
		return db.isEmpty();
	}
	
	public boolean hasDuringTransaction() {
		return !transactionLock.isEmpty();
	}
	
	public IVehicle findVehicle(String vehicleID) {
		return db.findVehicle(vehicleID);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	
}
