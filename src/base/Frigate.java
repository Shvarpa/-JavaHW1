package base;

import java.util.List;

public class Frigate extends SeaVehicle implements Motorized {	

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
	
	public Frigate(List<Object> parameters) throws ClassCastException{
		this((String)parameters.get(0),(int)parameters.get(1),(float)parameters.get(2),(boolean)parameters.get(4));
	}
	
	public String toString() {return super.toString()+" "+Motorized.toString(this);}
	public boolean equals(Frigate other) {return super.equals(other) && Motorized.equals(this, other);}
	
}

