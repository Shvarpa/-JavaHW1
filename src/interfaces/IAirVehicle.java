//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package interfaces;

import java.util.Arrays;

import classes.StringRange;

public interface IAirVehicle extends IVehicle{
    public static StringRange possibleVehicleUse = new StringRange(Arrays.asList("Army", "Civilian"));
    public static Boolean checkVehicleUseInput(String vehicleUse) {
        return IAirVehicle.possibleVehicleUse.containsIgnoreCaps(vehicleUse);
    }
        
	public static Boolean equals(IAirVehicle self,Object other) {
    	if (other instanceof IAirVehicle) {
    		return self.getVehicleUse().equalsIgnoreCase(((IAirVehicle)other).getVehicleUse()) ;
    	}
    	else return false;
    }
	
	public static String toString(IAirVehicle self) {
		return "intended for " + possibleVehicleUse.FixCaps(self.getVehicleUse()) + " use.";
	}
	
    public String getVehicleUse();
}
