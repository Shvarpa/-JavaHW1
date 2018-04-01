package base;

import java.util.Arrays;

public abstract class AirVehicle extends Vehicle{
	private String vehicleUse;
	static String[] possibleVehicleUse= {"Army","Civilian"};
	
	private static boolean checkVehicleUseInput(String vehicleUse) {
		return Arrays.asList(AirVehicle.possibleVehicleUse).contains(vehicleUse);
	}
	
	private boolean setVehicleUse(String vehicleUse) {
		if (AirVehicle.checkVehicleUseInput(vehicleUse)) {
			this.vehicleUse=vehicleUse;
			return true;
		}
		return false;
	}
	
	protected AirVehicle(String model, int seats, float speed,String vehicleUse) {
		super(model,seats,speed);
		setVehicleUse(vehicleUse);
	}
}