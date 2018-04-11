package base;

public interface Motorized {
	
	static String toString(Motorized self) {return "Engine: "+self.getAvgFuelConsumption()+"L, lifetime of "+self.getAvgMotorLifespan()+" years.";}
	static boolean equals(Motorized self, Motorized other) {return self.getAvgFuelConsumption()==other.getAvgFuelConsumption() && self.getAvgMotorLifespan()==other.getAvgMotorLifespan();}
	
	public double getAvgFuelConsumption();
	public double getAvgMotorLifespan();
}
