package base;

import java.util.Arrays;

public abstract class AirVehicle extends Vehicle{
	private String vehicleUse;
	static String[] possibleVehicleUse= {"Army","Civilian"};
	
	public String getVehicleUse() {return this.vehicleUse;}
	
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
	
	public String toString() {return super.toString()+" intended for "+this.vehicleUse+" use.";}
	public boolean equals(AirVehicle other) {return super.equals(other) && this.vehicleUse.equals(other.getVehicleUse());}
}