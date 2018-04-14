package base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import base.Jeep;
public class MainMenu {
	static final StringRange possibleVehicleTypes=new StringRange(Arrays.asList("Jeep","Frigate","SpyDrone","PlayDrone"));
	
	private List<Vehicle> vehicleDatabase;
	private List<SeaVehicle> seaVehicleDatabase;
	private List<AirVehicle> airVehicleDatabase;
	private List<LandVehicle> landVehicleDatabase;
	
	
	
	private boolean selectOption() {
		Scanner in=new Scanner(System.in);
		System.out.println(
				"Select form the following options:\r\n"
				+ "1)add vehicle\r\n"
				+ "2)buy vehicle\r\n"
				+ "3)take a vehicle for a test-drive\r\n"
				+ "4)reset all vehicle distances\r\n"
				+ "5)change flags\r\n"
				+ "6)exit\r\n");
		int option=in.nextInt();
		switch(option) {
		case 1:
			this.addVehicle();
			break;
		case 2:
			this.buyVehicle();
			break;
		case 3:break;
		case 4:break;
		case 5:break;
		case 6:return false;
		}
		return true;
	}
	
	static Scanner in=new Scanner(System.in);
	private String input(String msg) {
		System.out.println(msg);
		return in.next();
	}
		
		
	private Vehicle inputVehicle() {
		String type;
		do {
		type=input("what is the vehicle's type? (type 'exit' to return)");
		if (type=="exit") {return null;}
		if (!possibleVehicleTypes.containsIgnoreCaps(type)) {
			System.out.println("this type is undefined. ("+possibleVehicleTypes.toString()+")");
			continue;
			}
		type=possibleVehicleTypes.FixCaps(type);
		switch(type) {
		case "Jeep": return Jeep.inputJeep();
		case "Frigate":return Frigate.inputFrigate();
		case "SpyDrone":return SpyDrone.inputSpyDrone();
		case "PlayDrone":return PlayDrone.inputPlayDrone();
		}

		}while(true);
	}
	
	private void addVehicle() {
		Vehicle nVehicle=inputVehicle();
		if (nVehicle!=null) {
			this.vehicleDatabase.add(nVehicle);
			if (nVehicle instanceof SeaVehicle) {this.seaVehicleDatabase.add((SeaVehicle) nVehicle);}
			else if (nVehicle instanceof LandVehicle) {this.landVehicleDatabase.add((LandVehicle) nVehicle);}
			else if (nVehicle instanceof AirVehicle) {this.airVehicleDatabase.add((AirVehicle) nVehicle);}
			else;
		}
		else {
			System.out.println("bad vehicle input, returning");
		}
	}
	
	private void removeVehicle(Vehicle nVehicle) {
		if (nVehicle!=null) {
			if (vehicleDatabase.contains(nVehicle)) {
				this.vehicleDatabase.remove(nVehicle);
				if (nVehicle instanceof SeaVehicle) {this.seaVehicleDatabase.remove((SeaVehicle) nVehicle);}
				else if (nVehicle instanceof LandVehicle) {this.landVehicleDatabase.remove((LandVehicle) nVehicle);}
				else if (nVehicle instanceof AirVehicle) {this.airVehicleDatabase.remove((AirVehicle) nVehicle);}
				else;
			}
			else {System.out.println("vehicle doesnt exist, returning");}
		}
	}
	
	private void buyVehicle() {
		Vehicle nVehicle=inputVehicle();
		this.removeVehicle(nVehicle);
	}
	
	
	
	
	public MainMenu() {
		vehicleDatabase=new ArrayList<>();
		seaVehicleDatabase=new ArrayList<>();
		airVehicleDatabase=new ArrayList<>();
		landVehicleDatabase=new ArrayList<>();
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
