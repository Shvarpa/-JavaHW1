package base;

public abstract class AirVehicle extends Vehicle{
	private String vehicleUse;
	
	private void setVehicleUse(String vehicleUse) {this.vehicleUse=vehicleUse;}
	
	protected AirVehicle(String model, int seats, float speed,String vehicleUse) {
		super(model,seats,speed);
		setVehicleUse(vehicleUse);
	}
}
