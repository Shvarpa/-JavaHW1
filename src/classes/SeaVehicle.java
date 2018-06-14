//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.Collection;

import interfaces.IAirVehicle;
import interfaces.ISeaVehicle;

public abstract class SeaVehicle extends Vehicle implements ISeaVehicle {
	private boolean withWindDiraction;
	private String flag;
	
	public Boolean getWithWindDiraction() {return this.withWindDiraction;}
	
	private void setWithWindDiraction(boolean withWindDiraction) {this.withWindDiraction=withWindDiraction;}

	public String getFlag() {return this.flag;}
	
	public void setFlag(String flag) {this.flag=flag;}
	
	protected SeaVehicle(String model, int seats, float speed,boolean withWindDiraction, String flag) {
		super(model,seats,speed);
		setWithWindDiraction(withWindDiraction);
		setFlag(flag);
	}
	
	public String toString() {
		return super.toString()+ " " +ISeaVehicle.toString(this);
	}
	public boolean equals(Object other) {
		return super.equals(other) && ISeaVehicle.equals(this, other);
	}
	
	public SeaVehicle(SeaVehicle toCopy) {
		super(toCopy);
		setWithWindDiraction(toCopy.getWithWindDiraction());
		setFlag(toCopy.getFlag());
	}
	
	@Override
	abstract public SeaVehicle clone();
	
	@Override
	public Collection<Class<?>> getInterfaces(){
		Collection<Class<?>> interfaces = super.getInterfaces();
		interfaces.add(ISeaVehicle.class);
		return interfaces;
	}
}
