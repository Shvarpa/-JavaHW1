package interfaces;

import java.util.Arrays;
import classes.StringRange;

public interface ISeaVehicle {
		
	public static StringRange possibleFlags = new StringRange(Arrays.asList("Israel","USA","Germany","Italy","Greece","Somalia","Pirate"));
	
	public static StringRange getPossibleFlags() {
		return ISeaVehicle.possibleFlags;
	}
	
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
	public void setFlag(String flag);
}
