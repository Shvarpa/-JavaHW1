package base;

public abstract class SeaVehicle extends Vehicle {
	private boolean withWindDiraction;
	private String flag;
	
	private void setFlag(String flag) {this.flag=flag;}
	
	protected SeaVehicle(String model, int seats, float speed,boolean withWindDiraction, String flag) {
		super(model,seats,speed);
		this.withWindDiraction=withWindDiraction;
		setFlag(flag);
	}
}
