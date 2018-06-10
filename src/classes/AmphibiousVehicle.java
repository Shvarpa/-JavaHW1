//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import interfaces.IVehicle;
import interfaces.Motorized;

public class AmphibiousVehicle extends Vehicle implements ILandVehicle,ISeaVehicle,Motorized{
	
	int wheels;	//ILandVehicle interface
	public final static String defaultRoadType= "Constructed";
	
	boolean withWindDiraction;//ISeaVehicle interface
	String flag;
	
	double avgFuelConsumption;//Motorized interface
	double avgMotorLifespan;

	
	public AmphibiousVehicle(String model, int seats, float speed,int wheels,boolean withWindDiraction,String flag,double avgFuelConsumption,double avgMotorLifespan) {
		super(model, seats, speed);
		setWheels(wheels);
		setWithWindDiraction(withWindDiraction);
		setFlag(flag);
		setAvgFuelConsumption(avgFuelConsumption);
		setAvgMotorLifespan(avgMotorLifespan);
	}

	/////////////////////////////////////////////////////////get////////////////////////////////////////////////////////////////////////////////////////////
	public int getWheels() {return wheels;}
	public String getRoadType() {return AmphibiousVehicle.defaultRoadType;}
	
	public boolean getWithWindDiraction() {return withWindDiraction;}
	public String getFlag() {return flag;}

	public double getAvgFuelConsumption() {return avgFuelConsumption;}
	public double getAvgMotorLifespan() {return avgMotorLifespan;}

	/////////////////////////////////////////////////////////set////////////////////////////////////////////////////////////////////////////////////////////
	private void setWheels(int wheels) {this.wheels=wheels;}
	
	private void setWithWindDiraction(boolean windDiraction) {this.withWindDiraction=windDiraction;}
	public void setFlag(String flag) {this.flag=flag;}
	
	private void setAvgFuelConsumption(double avgFuelConsumption) {this.avgFuelConsumption=avgFuelConsumption;}
	private void setAvgMotorLifespan(double avgMotorLifespan) {this.avgMotorLifespan=avgMotorLifespan;}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean equals(Object other) {
		return super.equals(other) && ILandVehicle.equals(this, other) && ISeaVehicle.equals(this, other) && Motorized.equals(this, other);
	}
	
	public String toString() {
		return super.toString()+ " " +ILandVehicle.toString(this)+ " " + ISeaVehicle.toString(this) + " " + Motorized.toString(this);
	}
	
	public AmphibiousVehicle(AmphibiousVehicle toCopy) {
		super(toCopy);
		setWheels(toCopy.getWheels());
		setWithWindDiraction(toCopy.getWithWindDiraction());
		setFlag(toCopy.getFlag());
		setAvgFuelConsumption(toCopy.getAvgFuelConsumption());
		setAvgMotorLifespan(toCopy.getAvgMotorLifespan());
	}
	@Override
	public AmphibiousVehicle clone() {
		return new AmphibiousVehicle(this);
	}
}
