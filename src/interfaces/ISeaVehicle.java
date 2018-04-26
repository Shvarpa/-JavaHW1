package interfaces;

public interface ISeaVehicle {
	
	public static boolean equals(ISeaVehicle self,Object other) {
    	if (other instanceof ISeaVehicle) {
    		return self.getWithWindDiraction()==((ISeaVehicle)other).getWithWindDiraction()&& self.getFlag().equalsIgnoreCase(((ISeaVehicle)other).getFlag()) ;
    	}
    	else return false;
    }
	
	public static String toString(ISeaVehicle self) {
		String wind=(self.getWithWindDiraction() ?"with" :"against");
		return "Under "+self.getFlag()+" flag, "+wind+" the wind.";
	}
	
	public boolean getWithWindDiraction();
	public String getFlag();
}
