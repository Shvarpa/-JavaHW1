package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import base.Jeep;
import base.Inputable;

public class MainMenu{
	static final StringRange possibleVehicleTypes=new StringRange(Arrays.asList("Jeep","Frigate","SpyDrone","PlayDrone"));
	
	private List<Vehicle> vehicleDatabase;
	private List<SeaVehicle> seaVehicleDatabase;
	private List<AirVehicle> airVehicleDatabase;
	private List<LandVehicle> landVehicleDatabase;
	
	private void printDatabase() {
		for(int i=0;i<vehicleDatabase.size();i++) {
			System.out.println("index:"+i+", "+(vehicleDatabase.get(i)).toString());
		}	}
	
	private boolean selectOption() {
		if (!vehicleDatabase.isEmpty()) {printDatabase();}
		System.out.println(
				"\n"
				+ "Select form the following options:\n"
				+ "1)add vehicle\n"
				+ "2)buy vehicle\n"
				+ "3)take a vehicle for a test-drive\n"
				+ "4)reset all vehicle distances\n"
				+ "5)change flags\n"
				+ "6)exit\n\n");
		int option=Inputable.in.nextInt();
		switch(option) {
		case 1:
			this.addVehicle();
			break;
		case 2:
			this.buyVehicle();
			break;
		case 3:
			testDriveVehicle();
			break;
		case 4:
			resetDistances();
			break;
		case 5:
			changeFlags();
			break;
		case 6:return false;
		}
		return true;
	}
	
	private boolean validIndex(int index) {
		return (index>=0 && index<=vehicleDatabase.size());
	}
		
	private Vehicle inputVehicle() {
		String type;
		type=Inputable.input("what is the vehicle's type?");
		type=possibleVehicleTypes.FixCaps(type);
		switch(type) {
			case "Jeep": return Jeep.inputJeep();
			case "Frigate":return Frigate.inputFrigate();
			case "SpyDrone":return SpyDrone.inputSpyDrone();
			case "PlayDrone":return PlayDrone.inputPlayDrone();
			default: System.out.println("this type is undefined. ("+possibleVehicleTypes.toString()+")");return null;
		}
	}
	private boolean isEmpty() {
		if (vehicleDatabase.isEmpty()){
			System.out.println("no vehicles in database, returning\n");
			return true;
		}
		return false;
	}
	
	private int selectVehicle() {
		int index=Inputable.inputIntger("enter vehicle index:");
		if (!validIndex(index)) {
			System.out.println("the index "+index+" is out of bounds, returning\n");
			return -1;
		}
		return index;
	}
	
	private int selectVehicle(Vehicle inputedVehicle) {
		if(vehicleDatabase.contains(inputedVehicle)) {return vehicleDatabase.indexOf(inputedVehicle);}
		else return -1;
	}
	
	private int selectOrInputVehicle() {
		if (!isEmpty()) {
			if (Inputable.inputBoolean("would you like to select a vehicle by index?")){return selectVehicle();}
		}
		return selectVehicle(inputVehicle());
	}
	
	private boolean addVehicle() {
		Vehicle nVehicle=inputVehicle();
		if (nVehicle!=null) {
			this.vehicleDatabase.add(nVehicle);
			if (nVehicle instanceof SeaVehicle) {this.seaVehicleDatabase.add((SeaVehicle) nVehicle);}
			else if (nVehicle instanceof LandVehicle) {this.landVehicleDatabase.add((LandVehicle) nVehicle);}
			else if (nVehicle instanceof AirVehicle) {this.airVehicleDatabase.add((AirVehicle) nVehicle);}
			else;
			System.out.println("the vehicle: "+nVehicle.toString()+" was added succesfully, returning\n");
			return true;
		}
		return false;
	}
	
	private boolean removeVehicle(int index) {
		if (validIndex(index)) {
			Vehicle currVehicle=this.vehicleDatabase.get(index);
			this.vehicleDatabase.remove(index);
			if (currVehicle instanceof SeaVehicle) {
				for(int i=0;i<seaVehicleDatabase.size();i++) {
					if (seaVehicleDatabase.get(i)==currVehicle) {
						seaVehicleDatabase.remove(i);
						break;
					}
				}
			}
			else if (currVehicle instanceof LandVehicle) {
				for(int i=0;i<landVehicleDatabase.size();i++) {
					if (landVehicleDatabase.get(i)==currVehicle) {
						landVehicleDatabase.remove(i);
						break;
					}
				}
			}
			else if (currVehicle instanceof AirVehicle) {
				for(int i=0;i<airVehicleDatabase.size();i++) {
					if (airVehicleDatabase.get(i)==currVehicle) {
						landVehicleDatabase.remove(i);
						break;
					}				
				}
			}
			else;
			return true;
			}
		System.out.println("vehicle doesnt exist, returning\n");
		return false;
	}
		
	private boolean buyVehicle() {
		if(!isEmpty()) {
			int vehicleIndex=selectOrInputVehicle();
			if (validIndex(vehicleIndex)) {
				Vehicle currVehicle=this.vehicleDatabase.get(vehicleIndex);
				removeVehicle(vehicleIndex);
				System.out.println("the vehicle: "+currVehicle.toString()+" was bought succesfully, returning\n");
				return true;
			}
		}
		return false;
	}
	
	
	private boolean driveVehicle(int index,double distance) {
		if (validIndex(index)){
			vehicleDatabase.get(index).moveDistance(distance);
			return true;
		}
		return false;
	}
	
	private boolean testDriveVehicle() {
		if(!isEmpty()) {
			int vehicleIndex=selectOrInputVehicle();
			if (!validIndex(vehicleIndex)) {return false;}
			try {
				double distance=Inputable.inputDouble("enter test drive distance:");
				if(driveVehicle(vehicleIndex,distance)){
					Vehicle currVehicle=this.vehicleDatabase.get(vehicleIndex);
					System.out.println("the vehicle: "+currVehicle.toString()+" was taken for a "+distance+"km test-drive succesfully, returning\n");
					return true;
				}
				return false;
			}
			catch (NumberFormatException e) {System.out.println("bad input "+e.getLocalizedMessage()+", returning"); return false;}
		}
		return false;

	}
	
	private boolean resetDistances() {
		if (isEmpty()){return false;}
		for(Vehicle v:vehicleDatabase) {v.resetTotalDistance();}
		System.out.println("all vehicle distances were reset succesfully, returning\n");
		return true;
	}
	
	private boolean changeFlags() {
		if (isEmpty()){return false;}
		String flag=Inputable.input("Enter new flag name:");
		for(SeaVehicle sV: seaVehicleDatabase) {sV.setFlag(flag);}
		System.out.println("all vehicle flags were changed to "+flag+" succesfully, returning\n");
		return true;
	}
	
	
	public MainMenu() {
		vehicleDatabase=new ArrayList<>();
		seaVehicleDatabase=new ArrayList<>();
		airVehicleDatabase=new ArrayList<>();
		landVehicleDatabase=new ArrayList<>();
	}
	
	
	
	public static void main(String[] args) {
		MainMenu main=new MainMenu();
		boolean retry=false;
		do{retry=main.selectOption();}while(retry);
	}

}
