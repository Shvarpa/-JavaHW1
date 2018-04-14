package base;
import base.Inputable;

public abstract class Drone extends AirVehicle implements NonMotorized{
	
	private String energyRating; //NonMotorized Interface
	private String energySource;
	
	private boolean setEnergyRating(String energyRating) {
		if(NonMotorized.checkEnergyRatingInput(energyRating)) {
			this.energyRating=energyRating;
			return true;
		}
		return false;
	}
	
	private boolean setEnergySource(String energySource) {
		this.energySource=energySource;
		return true;
	}
	
	public String getEnergyRating() {return energyRating;}
	public String getEnergySource() {return energySource;}

	
	protected Drone(String model, int seats, float speed,String vehicleUse,String energySource,String energyRating) {
		super(model,seats,speed,vehicleUse);
		setEnergySource(energySource);
		setEnergyRating(energyRating);
	}
	
	public String toString() {return super.toString()+" "+NonMotorized.toString(this);}
	public boolean equals(Drone other) {return super.equals(other) && NonMotorized.equals(this, other);}
}
