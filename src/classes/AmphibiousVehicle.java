//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import interfaces.Inputable;
import interfaces.Motorized;

public class AmphibiousVehicle extends Vehicle implements ILandVehicle,ISeaVehicle,Motorized{
	
	int wheels;	//ILandVehicle interface

	boolean withWindDiraction;//ISeaVehicle interface
	String flag;
	
	double avgFuelConsumption;//Motorized interface
	double avgMotorLifespan;
	
	LandVehicle LandPart;
	SeaVehicle SeaPart;
	
	protected AmphibiousVehicle(String model, int seats, float speed,int wheels,boolean withWindDiraction,String flag,double avgFuelConsumption,double avgMotorLifespan) {
		super(model, seats, speed);
		setWheels(wheels);
		setWithWindDiraction(withWindDiraction);
		setFlag(flag);
		setAvgFuelConsumption(avgFuelConsumption);
		setAvgMotorLifespan(avgMotorLifespan);
	}

	/////////////////////////////////////////////////////////get////////////////////////////////////////////////////////////////////////////////////////////
	public int getWheels() {return wheels;}
	public String getRoadType() {return "constructed";}
	
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
	
    public static AmphibiousVehicle inputAmphibiousVehicle() {
        try {
            String model = Inputable.input("model:");
            int seats= Inputable.inputInteger("seats:");
            float speed = Inputable.inputFloat("speed:");
            int wheels= Inputable.inputInteger("Wheels:");
            boolean withWindDiraction=Inputable.inputBoolean("with wind diraction?:");
            String flag = Inputable.input("flag:");
            double avgFuelConsumption = Inputable.inputDouble("average fuel consumption:");
            double avgMotorLifespan = Inputable.inputDouble("average motor lifespan:");
            return new AmphibiousVehicle(model,seats,speed,wheels,withWindDiraction,flag,avgFuelConsumption,avgMotorLifespan);
        } catch (NumberFormatException e) {
            System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
            return null;
        }
    }
}
