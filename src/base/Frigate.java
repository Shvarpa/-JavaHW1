package base;

import base.Inputable;


public class Frigate extends SeaVehicle implements Motorized{	
	static Frigate inputFrigate() {
		String model=Inputable.input("model (String):");
		int seats=Integer.parseInt(Inputable.input("seats (int):"));
		float speed=Float.parseFloat(Inputable.input("speed (float):"));
		boolean withWindDiraction=Boolean.parseBoolean(Inputable.input("with wind diraction? (boolean):"));
		return new Frigate(model,seats,speed,withWindDiraction);
	}
	
	private double avgFuelConsumption; //Motorized Interface
	private double avgMotorLifespan;
	
	private void setAvgFuelConsumption(double avgFuelConsumption) {this.avgFuelConsumption=avgFuelConsumption;}
	public double getAvgFuelConsumption() {return this.avgFuelConsumption;}
	private void setAvgMotorLifespan(double avgMotorLifespan) {this.avgMotorLifespan=avgMotorLifespan;}
	public double getAvgMotorLifespan() {return avgMotorLifespan;}
	
	public Frigate(String model, int seats, float speed,boolean withWindDiraction) {
		super(model,seats,speed,withWindDiraction,"Israel");
		setAvgFuelConsumption(500);
		setAvgMotorLifespan(4);
	}
	
	
	public String toString() {return super.toString()+" "+Motorized.toString(this);}
	public boolean equals(Frigate other) {return super.equals(other) && Motorized.equals(this, other);}
	
}

