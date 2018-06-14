package interfaces;

import java.util.Arrays;

import classes.StringRange;

public interface ILandVehicle extends IVehicle{
	
	public static StringRange possibleRoadType = new StringRange(Arrays.asList("Dirt", "Constructed"));

	public static Boolean checkRoadTypeInput(String roadType) {
        return ILandVehicle.possibleRoadType.containsIgnoreCaps(roadType);
    }
    
	public static Boolean equals(ILandVehicle self,Object other) {
    	if (other instanceof ILandVehicle) {
    		return self.getRoadType().equalsIgnoreCase(((ILandVehicle)other).getRoadType()) && self.getWheels()==((ILandVehicle)other).getWheels();
    	}
    	else return false;
    }
    
    public static String toString(ILandVehicle self) {
    	return "has " + self.getWheels() + " wheels, can ride on " + possibleRoadType.FixCaps(self.getRoadType()) + " roads.";
    }
    
    public Integer getWheels();
    public String getRoadType();
}
