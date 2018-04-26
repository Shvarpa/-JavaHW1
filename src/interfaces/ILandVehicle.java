package interfaces;

import java.util.Arrays;

import classes.StringRange;

public interface ILandVehicle {
	
	public static StringRange possibleRoadType = new StringRange(Arrays.asList("dirt", "constructed"));

	public static boolean checkRoadTypeInput(String roadType) {
        return ILandVehicle.possibleRoadType.containsIgnoreCaps(roadType);
    }
    
	public static boolean equals(ILandVehicle self,Object other) {
    	if (other instanceof ILandVehicle) {
    		return self.getRoadType().equalsIgnoreCase(((ILandVehicle)other).getRoadType()) && self.getWheels()==((ILandVehicle)other).getWheels();
    	}
    	else return false;
    }
    
    public static String toString(ILandVehicle self) {
    	return "has " + self.getWheels() + " wheels, can ride on " + possibleRoadType.FixCaps(self.getRoadType()) + " roads.";
    }
    
    public int getWheels();
    public String getRoadType();
}
