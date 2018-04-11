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
	
	public String toString() {return super.toString()+" "+NonMotorized.toString(this);}
	public boolean equals(Drone other) {return super.equals(other) && NonMotorized.equals(this, other);}
}
