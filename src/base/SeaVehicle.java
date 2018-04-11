package base;

public abstract class SeaVehicle extends Vehicle {
	private boolean withWindDiraction;
	private String flag;
	
	public boolean getWithWindDiraction() {return this.withWindDiraction;}
	public String getFlag() {return this.flag;}

	
	private void setFlag(String flag) {this.flag=flag;}
	
	protected SeaVehicle(String model, int seats, float speed,boolean withWindDiraction, String flag) {
		super(model,seats,speed);
		this.withWindDiraction=withWindDiraction;
		setFlag(flag);
	}
	
	public String toString() {
		String wind=(this.withWindDiraction ?"with" :"against");
		return super.toString()+" Under "+this.flag+" flag, "+wind+" the wind";
	}
	
	public boolean equals(SeaVehicle other) {
		return super.equals(other) && this.withWindDiraction==other.getWithWindDiraction() && this.flag.equals(other.getFlag());
	}
}
