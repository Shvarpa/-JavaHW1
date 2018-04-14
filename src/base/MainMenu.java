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
		case 1:break;
		case 2:break;
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
	
	private Jeep inputJeep() throws ClassCastException {
		String model=input("model (String):");
		float speed=Float.parseFloat(input("speed (float):"));
		double avgFuelConsumption=Double.parseDouble(input("average fuel consumption (double):"));
		double avgMotorLifespan=Double.parseDouble(input("average motor lifespan (double):"));
		return new Jeep(model,speed,avgFuelConsumption,avgMotorLifespan);
	}
	
	private Frigate inputFrigate() throws ClassCastException {
		String model=input("model (String):");
		int seats=Integer.parseInt(input("seats (int):"));
		float speed=Float.parseFloat(input("speed (float):"));
		boolean withWindDiraction=Boolean.parseBoolean(input("with wind diraction? (boolean):"));
		return new Frigate(model,seats,speed,withWindDiraction);
	}
	
	private SpyDrone inputSpyDrone() throws ClassCastException {
		String enertgySource=input("energy source (String):");
		return new SpyDrone(enertgySource);
	}
	
	private PlayDrone inputPlayDrone() throws ClassCastException {
		return new PlayDrone();
	}
	
	private Object inputVehicle() {
		String type;
		Vehicle result;
		do {
		type=input("what is the vehicle's type?");
		if (!possibleVehicleTypes.containsIgnoreCaps(type)) {
			System.out.println("this type is undefined. ("+possibleVehicleTypes.toString()+")");
			continue;
			}
		type=possibleVehicleTypes.FixCaps(type);
		switch(type) {
		case "Jeep": try {result=inputJeep();}
		break;
		case "Frigate": try {result=inputFrigate();}
		break;
		case "SpyDrone": try {result=inputSpyDrone();}
		break;
		case "PlayDrone": try {result=inputPlayDrone();}
		break;
		}
		
		}while(true);
	}
	
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
