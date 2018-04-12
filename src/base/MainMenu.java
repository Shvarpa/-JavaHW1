package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import base.Jeep;
public class MainMenu {
	
	static ClassDict possibleVehicleTypes=new ClassDict();
	static {
		try {
		possibleVehicleTypes.put("Jeep", Class.forName("Jeep"));
		possibleVehicleTypes.put("Frigate", Class.forName("Frigate"));
		possibleVehicleTypes.put("SpyDrone", Class.forName("SpyDrone"));
		possibleVehicleTypes.put("PlayDrone", Class.forName("PlayDrone"));
		} catch (ClassNotFoundException e) {
			// impossible code
			e.printStackTrace();
		}
	}
	
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
		case 1:break;
		case 2:break;
		case 3:break;
		case 4:break;
		case 5:break;
		case 6:return false;
		}
		return true;
	}
	
//	private Vehicle inputVehicle() {
//		Scanner input=new Scanner(System.in);
//		System.out.println("what is the vehicle's type?");
//	}
	
	private void addVehicle(Vehicle newVehicle) {
		
	}
	
	
	public MainMenu() {
		vehicleDatabase=new ArrayList<>();
		seaVehicleDatabase=new ArrayList<>();
		airVehicleDatabase=new ArrayList<>();
		landVehicleDatabase=new ArrayList<>();
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(("yes".equals("yes")?"True": "False"));
	}

}
