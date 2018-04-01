package base;

public abstract class Drone extends AirVehicle implements NonMotorized{
	
	private String energyRating; //NonMotorized Interface
	
	private boolean setEnergyRating(String energyRating) {
		if(NonMotorized.checkEnergyRatingInput(energyRating)) {
			this.energyRating=energyRating;
			return true;
		}
		return false;
	}
	
	public String getEnergyRating() {return energyRating;}
	
	protected Drone(String model, int seats, float speed,String vehicleUse,String energyRating) {
		super(model,seats,speed,vehicleUse);
		setEnergyRating(energyRating);
	}
}
