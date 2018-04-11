package base;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
	
	private List<Vehicle> vehicleDatabase;
	
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
//		
//	}
	private void addVehicle(Vehicle newVehicle) {}
	
	
	public MainMenu() {
		vehicleDatabase=new ArrayList<>();
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
